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
import java.time.ZoneId;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;

/**
 * The excel cell with date.
 *
 * @since 3.14.9
 */
public final class DateOf extends CellOf<LocalDate> {

    public DateOf(final XSSFCell cell) {
        this(
            cell::getColumnIndex,
            () -> cell.getDateCellValue()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );
    }

    public DateOf(final int cid, final LocalDate val) {
        this(() -> cid, () -> val);
    }

    public DateOf(final String cid, final LocalDate val) {
        this(cid, () -> val);
    }

    public DateOf(final String cid, final Scalar<LocalDate> val) {
        this(new Sticky<>(new IndexOf(cid)), val);
    }

    public DateOf(final Scalar<Integer> cid, final Scalar<LocalDate> val) {
        this(cid, val, "yyyy-mm-dd");
    }

    public DateOf(final Scalar<Integer> cid, final Scalar<LocalDate> val, final String pattern) {
        super(cid, val, sheet -> {
            final XSSFCreationHelper creationHelper = sheet.getWorkbook().getCreationHelper();
            final XSSFCellStyle date = sheet.getWorkbook().createCellStyle();
            date.setDataFormat(creationHelper.createDataFormat().getFormat(pattern));
            return date;
        });
    }
}
