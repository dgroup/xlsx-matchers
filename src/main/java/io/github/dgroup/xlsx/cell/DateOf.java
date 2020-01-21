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
import java.time.ZoneId;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;

/**
 * The excel cell with date.
 *
 * @since 0.1.0
 */
public final class DateOf extends CellOf<LocalDate> {

    /**
     * Ctor.
     * @param cell The Apache POI cell.
     */
    public DateOf(final XSSFCell cell) {
        this(
            cell::getColumnIndex,
            () -> cell.getDateCellValue()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(),
            (sheet, actual) -> actual.setCellStyle(cell.getCellStyle())
        );
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     * @param styling The procedure to apply the style to the excel cell.
     */
    public DateOf(final int cid, final LocalDate val, final Style styling) {
        this(() -> cid, () -> val, styling);
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     * @param styling The procedure to apply the style to the excel cell.
     */
    public DateOf(final String cid, final LocalDate val, final Style styling) {
        this(cid, () -> val, styling);
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     * @param styling The procedure to apply the style to the excel cell.
     */
    public DateOf(final String cid, final Scalar<LocalDate> val, final Style styling) {
        this(new Sticky<>(new IndexOf(cid)), val, styling);
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     * @param styling The procedure to apply the style to the excel cell.
     */
    public DateOf(final Scalar<Integer> cid, final Scalar<LocalDate> val, final Style styling) {
        super(cid, val, styling);
    }
}
