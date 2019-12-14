/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yurii Dubinka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.dgroup.poi.cell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Excel cell.
 *
 * @param <T> The type of cell value.
 * @since 0.1.0
 */
public class CellOf<T> implements MutableCell<T> {

    /**
     * The Apache POI index of excel cell.
     * By default the cell index starts from 0.
     */
    private final Unchecked<Integer> cid;

    /**
     * The value of Apache POI cell.
     */
    private final Unchecked<T> val;

    /**
     * The function to detect the cell's format from particular {@link XSSFSheet}.
     */
    private final UncheckedFunc<XSSFSheet, XSSFCellStyle> style;

    /**
     * The function to evaluate the cell description.
     */
    private final Unchecked<String> tostr;

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final int cid, final T val) {
        this(cid, () -> val);
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final int cid, final Scalar<T> val) {
        this(() -> cid, val);
    }

    /**
     * Ctor.
     * @param cid The name of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final String cid, final T val) {
        this(cid, () -> val);
    }

    /**
     * Ctor.
     * @param cid The name of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final String cid, final Scalar<T> val) {
        this(new Sticky<>(new IndexOf(cid)), val);
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final Scalar<Integer> cid, final Scalar<T> val) {
        this(cid, val, sheet -> sheet.getWorkbook().createCellStyle());
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     * @param style The excel cell formatting style.
     */
    protected CellOf(
        final Scalar<Integer> cid, final Scalar<T> val, final Func<XSSFSheet, XSSFCellStyle> style
    ) {
        this.cid = new Unchecked<>(new Sticky<>(cid));
        this.val = new Unchecked<>(new Sticky<>(val));
        this.style = new UncheckedFunc<>(style);
        this.tostr = new Unchecked<>(
            new Sticky<>(
                () -> String.format("Cell %s, %s.", this.cid, this.val.value())
            )
        );
    }

    @Override
    public final int index() {
        return this.cid.value();
    }

    @Override
    public final T value() {
        return this.val.value();
    }

    @Override
    public final String toString() {
        return this.tostr.value();
    }

    @Override
    public final boolean equals(final Object obj) {
        final boolean equal;
        if (this == obj) {
            equal = true;
        } else if (obj == null || getClass() != obj.getClass()) {
            equal = false;
        } else {
            final Cell<?> that = (Cell<?>) obj;
            equal = Objects.equals(this.index(), that.index())
                && Objects.equals(this.value(), that.value());
        }
        return equal;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.cid.value(), this.val.value());
    }

    @Override
    public final void change(final XSSFRow row) {
        if (row != null) {
            XSSFCell cell = row.getCell(this.index());
            if (cell == null) {
                cell = row.createCell(this.index());
                cell.setCellStyle(this.style.apply(row.getSheet()));
            }
            final T value = this.value();
            if (value instanceof Double) {
                cell.setCellValue(
                    Math.round(
                        (Double) value
                    )
                );
            }
            if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            }
            if (value instanceof Long) {
                cell.setCellValue((Long) value);
            }
            if (value instanceof String) {
                cell.setCellValue((String) value);
            }
            if (value instanceof LocalDateTime) {
                final Date date = Date.from(
                    ((LocalDateTime) value)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                );
                cell.setCellValue(date);
            }
            if (value instanceof LocalDate) {
                final Date date = Date.from(
                    ((LocalDate) value)
                        .atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                );
                cell.setCellValue(date);
            }
        }
    }
}
