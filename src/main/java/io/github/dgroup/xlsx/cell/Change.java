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

package io.github.dgroup.xlsx.cell;

import io.github.dgroup.xlsx.style.Style;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * The procedure to change the particular Excel cell's value considering different java types.
 *
 * @param <T> The type of the Excel cell value.
 * @since 0.2.0
 */
public final class Change<T> implements Proc<XSSFRow> {

    /**
     * The number of cell in Excel row.
     */
    private final Unchecked<Integer> index;

    /**
     * The value of cell in Excel row.
     */
    private final Unchecked<T> val;

    /**
     * Function to evaluate formatting style of the cell in Excel row.
     */
    private final Style styling;

    /**
     * Ctor.
     * @param cid The number of cell in Excel row.
     * @param val The value of cell in Excel row.
     * @param styling The procedure to apply the style to the excel cell.
     */
    public Change(
        final Scalar<Integer> cid,
        final Scalar<T> val,
        final Style styling
    ) {
        this.index = new Unchecked<>(cid);
        this.val = new Unchecked<>(val);
        this.styling = styling;
    }

    @Override
    public void exec(final XSSFRow row) {
        if (row != null) {
            XSSFCell cell = row.getCell(this.index.value());
            if (cell == null) {
                cell = row.createCell(this.index.value());
                this.styling.exec(row.getSheet(), cell);
            }
            final T value = this.val.value();
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
