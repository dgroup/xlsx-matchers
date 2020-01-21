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
import java.util.Objects;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedProc;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Excel cell.
 *
 * @param <T> The type of cell value.
 * @since 0.1.0
 */
public class CellOf<T> implements MutableCell<T> {

    /**
     * The Apache POI index of excel cell.
     * By default the cell index starts from 0.
     */
    private final Unchecked<Integer> cid;

    /**
     * The value of Apache POI cell.
     */
    private final Unchecked<T> val;

    /**
     * The function to evaluate the cell description.
     */
    private final Unchecked<String> tostr;

    /**
     * Procedure to change the cell value.
     */
    private final UncheckedProc<XSSFRow> mutate;

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final int cid, final T val) {
        this(cid, () -> val);
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final int cid, final Scalar<T> val) {
        this(() -> cid, val);
    }

    /**
     * Ctor.
     * @param cid The name of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final String cid, final T val) {
        this(cid, () -> val);
    }

    /**
     * Ctor.
     * @param cid The name of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final String cid, final Scalar<T> val) {
        this(new Sticky<>(new IndexOf(cid)), val);
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     */
    public CellOf(final Scalar<Integer> cid, final Scalar<T> val) {
        this(cid, val, new Style.No());
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     * @param style The style of excel cell.
     */
    public CellOf(final Scalar<Integer> cid, final Scalar<T> val, final XSSFCellStyle style) {
        this(cid, val, new StyleOf(style));
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val The excel cell value.
     * @param styling The procedure to apply the style to the excel cell.
     */
    public CellOf(final Scalar<Integer> cid, final Scalar<T> val, final Style styling) {
        this(cid, val, new Change<>(cid, val, styling));
    }

    /**
     * Ctor.
     * @param cid The number of excel cell.
     * @param val Excel cell value.
     * @param mutate Procedure to change the cell value.
     */
    protected CellOf(final Scalar<Integer> cid, final Scalar<T> val, final Proc<XSSFRow> mutate) {
        this.cid = new Unchecked<>(new Sticky<>(cid));
        this.val = new Unchecked<>(new Sticky<>(val));
        this.mutate = new UncheckedProc<>(mutate);
        this.tostr = new Unchecked<>(
            new Sticky<>(
                () -> String.format("Cell %s, %s.", this.cid, this.val.value())
            )
        );
    }

    @Override
    public final int index() {
        return this.cid.value();
    }

    @Override
    public final T value() {
        return this.val.value();
    }

    @Override
    public final String toString() {
        return this.tostr.value();
    }

    @Override
    public final boolean equals(final Object obj) {
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
    public final int hashCode() {
        return Objects.hash(this.cid.value(), this.val.value());
    }

    @Override
    public final void change(final XSSFRow row) {
        this.mutate.exec(row);
    }
}
