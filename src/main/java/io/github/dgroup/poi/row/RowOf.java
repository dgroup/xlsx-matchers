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

package io.github.dgroup.poi.row;

import io.github.dgroup.poi.cell.MutableCell;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Represents a single excel row from particular excel sheet.
 *
 * @see XSSFSheet
 * @see XSSFRow
 * @since 0.1.0
 */
public final class RowOf implements MutableRow {

    /**
     * The Apache POI sheet.
     */
    private final XSSFSheet sheet;

    /**
     * The excel index of particular row.
     */
    private final Unchecked<Integer> rowid;

    /**
     * Ctor.
     * @param sheet The Apache POI sheet.
     * @param rowid The excel index of particular row.
     */
    public RowOf(final XSSFSheet sheet, final AtomicInteger rowid) {
        this(sheet, rowid::get);
    }

    /**
     * Ctor.
     * @param sheet The Apache POI sheet.
     * @param rowid The excel index of particular row.
     */
    public RowOf(final XSSFSheet sheet, final Scalar<Integer> rowid) {
        this.sheet = sheet;
        this.rowid = new Unchecked<>(rowid);
    }

    @Override
    public XSSFRow value() {
        XSSFRow row = this.sheet.getRow(this.rowid.value());
        if (row == null) {
            row = this.sheet.createRow(this.rowid.value());
        }
        return row;
    }

    @Override
    public void change(final MutableCell cell) {
        cell.change(this.value());
    }

    @Override
    public String toString() {
        return String.format("Row %s, sheet '%s'.", this.rowid, this.sheet.getSheetName());
    }
}
