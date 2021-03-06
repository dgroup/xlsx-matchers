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
import io.github.dgroup.xlsx.style.StyleOf;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.cactoos.scalar.Sticky;

/**
 * The excel cell with month.
 *
 * @since 0.1.0
 */
public final class MonthOf extends CellOf<String> {

    /**
     * Ctor.
     * @param cid The number of excel column number.
     * @param month The cell value.
     */
    public MonthOf(final int cid, final YearMonth month) {
        this(cid, month, new Style.No());
    }

    /**
     * Ctor.
     * @param cid The number of excel column number.
     * @param month The cell value.
     * @param style The style of excel cell.
     */
    public MonthOf(final int cid, final YearMonth month, final XSSFCellStyle style) {
        this(cid, month, new StyleOf(style));
    }

    /**
     * Ctor.
     * @param cid The number of excel column number.
     * @param month The cell value.
     * @param styling The procedure to apply the style to the excel cell.
     */
    public MonthOf(final int cid, final YearMonth month, final Style styling) {
        super(
            () -> cid,
            new Sticky<>(() -> month.getMonth().getDisplayName(TextStyle.SHORT, Locale.US)),
            styling
        );
    }
}
