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

import org.cactoos.Scalar;

/**
 * Give excel column id by text representation.
 * {@code
 *    final int a = new IndexOf("A").value();   // where a is equal to 'A' excel column id
 *    final int az = new IndexOf("AZ").value(); // where az is equal to 'AZ' excel column id
 * }
 * @since 0.1.0
 */
public final class IndexOf implements Scalar<Integer> {

    /**
     * Count of symbols within English alphabet.
     */
    private static final int ENGLISH_ALPHABET = 26;

    /**
     * First excel columns has index starting from 0,
     *  thus alphabet should start from 'symbol - 1'.
     */
    private static final int EXCEL_INDEX_SHIFT = 1;

    /**
     * The label of excel column.
     */
    private final String column;

    /**
     * Ctor.
     * @param column The label of excel column like 'A', 'Z', 'AX', etc.
     */
    public IndexOf(final String column) {
        this.column = column;
    }

    @Override
    public Integer value() {
        int result = 0;
        for (int idx = 0; idx < this.column.length(); ++idx) {
            result *= IndexOf.ENGLISH_ALPHABET;
            result += this.column.charAt(idx) - 'A' + 1;
        }
        return result - IndexOf.EXCEL_INDEX_SHIFT;
    }
}
