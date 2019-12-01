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

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link IndexOf}.
 *
 * @since 0.1.0
 * @checkstyle MagicNumberCheck (500 lines)
 */
@RunWith(DataProviderRunner.class)
public class IndexOfTest {

    @Test
    public void index(final String scenario, final String column, final int index) {
        new Assertion<>(
            scenario,
            new IndexOf(column),
            new ScalarHasValue<>(index)
        ).affirm();
    }

    /**
     * The method which allows to generate a bulk data
     *  for {@link this#index(String, String, int)} test.
     *  For each row from archive there will be single test invocation,
     *  so, for 7 rows there'll be 7 tests executed.
     * @return The bulk test data.
     */
    @DataProvider
    public Object[][] columns() {
        return new Object[][]{
            {"The column 'A' in excel has '0' index", "A", 0},
            {"The column 'F' in excel has '5' index", "F", 5},
            {"The column 'Z' in excel has '25' index", "Z", 25},
            {"The column 'AA' in excel has '26' index", "AA", 26},
            {"The column 'AD' in excel has '29' index", "AD", 29},
            {"The column 'BD' in excel has '55' index", "BD", 55},
            {"The column 'BM' in excel has '64' index", "BM", 64},
        };
    }

}
