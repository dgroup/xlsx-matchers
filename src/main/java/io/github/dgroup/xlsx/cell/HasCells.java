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

import java.time.ZoneId;
import java.util.Collection;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.list.ListOf;
import org.hamcrest.Description;
import org.llorllale.cactoos.matchers.MatcherEnvelope;

/**
 * The hamcrest matcher to check that particular excel row contains necessary cell(s).
 *
 * @param <T> The type of cell value.
 * @since 0.1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class HasCells<T> extends MatcherEnvelope<XSSFRow> {

    /**
     * Ctor.
     * @param cells The excel cell(s) from particular row.
     */
    @SafeVarargs
    public HasCells(final Cell<T>... cells) {
        this(new ListOf<>(cells));
    }

    /**
     * Ctor.
     * @param cells The excel cell(s) from particular row.
     */
    public HasCells(final Collection<Cell<T>> cells) {
        super(new Passed<>(cells), new Expected<>(cells), new Actual<>(cells));
    }

    /**
     * The function to check that particular {@link XSSFCell} has 'Number' type.
     * @param cell The excel cell to compare.
     * @return True if type is a "Number".
     */
    private static boolean number(final XSSFCell cell) {
        return cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
    }

    /**
     * The function to check that particular {@link XSSFCell} has 'Text' type.
     * @param cell The excel cell to compare.
     * @return True if type is a "Text"
     */
    private static boolean text(final XSSFCell cell) {
        return cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
    }

    /**
     * The function to compare that all cell(s) contains the required values.
     * @param <T> The type of value from {@link XSSFCell}.
     * @since 0.1.0
     */
    private static final class Passed<T> implements Func<XSSFRow, Boolean> {

        /**
         * The excel cell(s) to compare.
         */
        private final Iterable<Cell<T>> cells;

        /**
         * Ctor.
         * @param cells The excel cell(s) to compare.
         */
        private Passed(final Iterable<Cell<T>> cells) {
            this.cells = cells;
        }

        @Override
        public Boolean apply(final XSSFRow row) {
            boolean equal = true;
            for (final Cell<T> cell : this.cells) {
                final XSSFCell xcl = row.getCell(cell.index());
                if (xcl == null) {
                    equal = false;
                } else if (HasCells.number(xcl)) {
                    if (DateUtil.isCellDateFormatted(xcl)) {
                        equal = cell.value().equals(
                            xcl.getDateCellValue()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        );
                    } else {
                        equal = cell.value().toString().equals(xcl.getRawValue());
                    }
                } else {
                    equal = cell.value().toString().equals(xcl.getStringCellValue());
                }
                if (!equal) {
                    break;
                }
            }
            return equal;
        }
    }

    /**
     * Describes the expected Apache POI cells in terms of Hamcrest.
     * @param <T> The type of value from {@link XSSFCell}.
     * @since 0.1.0
     */
    private static final class Expected<T> implements Proc<Description> {

        /**
         * The expected excel cells to describe.
         */
        private final Iterable<Cell<T>> cells;

        /**
         * Ctor.
         * @param cells The expected excel cells to describe.
         */
        private Expected(final Iterable<Cell<T>> cells) {
            this.cells = cells;
        }

        @Override
        public void exec(final Description expected) {
            expected.appendText("cell(s)");
            for (final Cell<T> cell : this.cells) {
                expected.appendText(" ")
                    .appendText(String.valueOf(cell.index()))
                    .appendText(":")
                    .appendValue(cell.value().toString());
            }
        }
    }

    /**
     * Describes the actual Apache POI cells in terms of Hamcrest.
     * 'Actual' means the cells which were found in {@link XSSFRow}.
     * @param <T> The type of value from {@link XSSFCell}.
     * @since 0.1.0
     */
    private static final class Actual<T> implements BiProc<XSSFRow, Description> {

        /**
         * The actual excel cells to describe.
         */
        private final Iterable<Cell<T>> cells;

        /**
         * Ctor.
         * @param cells The actual excel cells to describe.
         */
        private Actual(final Iterable<Cell<T>> cells) {
            this.cells = cells;
        }

        @Override
        public void exec(final XSSFRow row, final Description actual) {
            actual.appendText("cell(s)");
            for (final Cell<T> cell : this.cells) {
                final XSSFCell xcl = row.getCell(cell.index());
                actual.appendText(" ")
                    .appendText(String.valueOf(cell.index()))
                    .appendText(":");
                if (xcl == null) {
                    actual.appendValue(null);
                } else if (HasCells.number(xcl)) {
                    if (DateUtil.isCellDateFormatted(xcl)) {
                        actual.appendValue(new DateOf(xcl).value());
                    } else {
                        actual.appendValue(xcl.getRawValue());
                    }
                } else if (HasCells.text(xcl)) {
                    actual.appendValue(xcl.getStringCellValue());
                }
            }
        }
    }
}
