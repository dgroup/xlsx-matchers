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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.Joined;

/**
 * Cells range which starts from the particular excel column number.
 *
 * @param <T> The type of excel cell(s) value.
 * @since 0.1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class Range<T> implements MutableCell<List<T>> {

    /**
     * The Apache POI index of excel cell.
     * By default the cell index starts from 0.
     */
    private final Unchecked<Integer> cid;

    /**
     * The range of values for excel cells.
     */
    private final List<T> values;

    /**
     * The function to evaluate the cell description.
     */
    private final Unchecked<String> tostr;

    /**
     * Ctor.
     * @param cid The number of excel column number.
     * @param values The cell(s) values.
     */
    public Range(final int cid, final T... values) {
        this(cid, new ListOf<>(values));
    }

    /**
     * Ctor.
     * @param cid The number of excel column number.
     * @param values The cell(s) values.
     */
    public Range(final int cid, final List<T> values) {
        this(() -> cid, values);
    }

    /**
     * Ctor.
     * @param cid The name of excel column number.
     * @param values The cell(s) values.
     */
    public Range(final String cid, final T... values) {
        this(cid, new ListOf<>(values));
    }

    /**
     * Ctor.
     * @param cid The name of excel column number.
     * @param values The cell(s) values.
     */
    public Range(final String cid, final List<T> values) {
        this(new Sticky<>(new IndexOf(cid)), values);
    }

    /**
     * Ctor.
     * @param cid The number of excel column number.
     * @param values The cell(s) values.
     */
    public Range(final Scalar<Integer> cid, final List<T> values) {
        this.cid = new Unchecked<>(cid);
        this.values = values;
        this.tostr = new Unchecked<>(
            new Sticky<>(
                () -> String.format(
                    "Cell(s) starting from %s, %s.",
                    this.cid,
                    new Joined(",", new Mapped<>(Object::toString, this.values))
                )
            )
        );
    }

    @Override
    public void change(final XSSFRow row) {
        final AtomicInteger column = new AtomicInteger(this.index());
        for (final T val : this.values) {
            new CellOf<>(column.getAndIncrement(), val)
                .change(row);
        }
    }

    @Override
    public int index() {
        return this.cid.value();
    }

    @Override
    public List<T> value() {
        return this.values;
    }

    @Override
    public String toString() {
        return this.tostr.value();
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean equal;
        if (this == obj) {
            equal = true;
        } else if (obj == null || getClass() != obj.getClass()) {
            equal = false;
        } else {
            final Cell<?> that = (Cell<?>) obj;
            equal = Objects.equals(this.index(), that.index())
                && Objects.equals(this.value(), that.value());
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cid.value(), this.values);
    }
}
