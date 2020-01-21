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
package io.github.dgroup.xlsx.style;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * The style to be applied to the target excel cell(s).
 *
 * @since 0.2.1
 */
public final class StyleOf implements Style {

    /**
     * The expected style to be applied.
     */
    private final Unchecked<XSSFCellStyle> style;

    /**
     * Build default style from target sheet.
     * @param sheet The target excel sheet.
     */
    public StyleOf(final XSSFSheet sheet) {
        this(new Sticky<>(() -> sheet.getWorkbook().createCellStyle()));
    }

    /**
     * Build date style from target sheet.
     * @param sheet The target excel sheet.
     * @param pattern The date format for excel cell(s).
     */
    public StyleOf(final XSSFSheet sheet, final String pattern) {
        this(
            new Sticky<>(
                () -> {
                    final XSSFCellStyle date = sheet.getWorkbook().createCellStyle();
                    date.setDataFormat(
                        sheet.getWorkbook()
                            .getCreationHelper()
                            .createDataFormat()
                            .getFormat(pattern)
                    );
                    return date;
                }
            )
        );
    }

    /**
     * Ctor.
     * @param style The expected style to be applied to target excel cell(s).
     */
    public StyleOf(final XSSFCellStyle style) {
        this(() -> style);
    }

    /**
     * Ctor.
     * @param style The expected style to be applied to target excel cell(s).
     */
    public StyleOf(final Scalar<XSSFCellStyle> style) {
        this.style = new Unchecked<>(style);
    }

    @Override
    public void exec(final XSSFSheet sheet, final XSSFCell cell) {
        if (this.style != null) {
            cell.setCellStyle(this.style.value());
        }
    }
}
