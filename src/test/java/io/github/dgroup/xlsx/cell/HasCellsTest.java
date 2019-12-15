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

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Matches;

/**
 * Test case for {@link HasCells}.
 *
 * @since 0.1.0
 * @checkstyle MethodBodyCommentsCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class HasCellsTest {

    @Test
    public void matches() {
        // Testing prerequisites
        final XSSFRow row = new XSSFWorkbook().createSheet().createRow(1);
        final XSSFCell name = row.createCell(0);
        name.setCellValue("Name");
        final XSSFCell birth = row.createCell(1);
        birth.setCellValue("Birth");
        final XSSFCell phone = row.createCell(2);
        phone.setCellValue("Phone");
        // Testing ...
        new Assertion<>(
            "The mather matches row with all required cells",
            new HasCells<>(
                new CellOf<>(0, "Name"),
                new CellOf<>(1, "Birth"),
                new CellOf<>(2, "Phone")
            ),
            new Matches<>(row)
        ).affirm();
    }

}
