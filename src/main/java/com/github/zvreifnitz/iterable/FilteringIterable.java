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

package com.github.zvreifnitz.iterable;

import com.github.zvreifnitz.iterable.base.AbstractIterable;

import java.util.Iterator;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public final class FilteringIterable<T> extends AbstractIterable<T> {

    private final Iterator<T> underlying;
    private final Predicate<T> filter;

    public FilteringIterable(final Iterable<T> iterable, final Predicate<T> filter) {
        this(requireNonNull(iterable, "iterable").iterator(), filter);
    }

    public FilteringIterable(final Iterator<T> iterator, final Predicate<T> filter) {
        this.underlying = requireNonNull(iterator, "iterator");
        this.filter = requireNonNull(filter, "filter");
    }

    @Override
    protected final void setNext() {
        while (this.underlying.hasNext()) {
            final T value = this.underlying.next();
            if (this.filter.test(value)) {
                this.setNext(value);
                return;
            }
        }
    }
}
