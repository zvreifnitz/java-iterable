/*
 * (C) Copyright 2017 zvreifnitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.zvreifnitz;

import com.github.zvreifnitz.base.AbstractIterable;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;

public final class ConcatingIterable<T> extends AbstractIterable<T> {

    private final Iterator<? extends Iterable<? extends T>> iterators;
    private Iterator<? extends T> underlying = null;

    public ConcatingIterable(final Iterable<? extends Iterable<? extends T>> iterables) {
        this(requireNonNull(iterables, "iterables").iterator());
    }

    public ConcatingIterable(final Iterator<? extends Iterable<? extends T>> iterators) {
        this.iterators = requireNonNull(iterators, "iterators");
    }

    @Override
    protected final void setNext() {
        if (this.setValueIfPossible()) {
            return;
        }
        if (this.findNextValue()) {
            this.setValue();
        }
    }

    private boolean setValueIfPossible() {
        if (this.isValueOk()) {
            this.setValue();
            return true;
        } else {
            return false;
        }
    }

    private boolean findNextValue() {
        while (this.isValueInvalid() && this.iterators.hasNext()) {
            final Iterable<? extends T> iterable = this.iterators.next();
            this.underlying = ((iterable == null) ? null : iterable.iterator());
        }
        return this.isValueOk();
    }

    private void setValue() {
        final T value = this.underlying.next();
        this.setNext(value);
    }

    private boolean isValueOk() {
        return ((this.underlying != null) && this.underlying.hasNext());
    }

    private boolean isValueInvalid() {
        return ((this.underlying == null) || (!this.underlying.hasNext()));
    }

}
