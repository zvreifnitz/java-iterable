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
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public final class TransformingIterable<O, I> extends AbstractIterable<O> {

    private final Iterator<I> underlying;
    private final Function<I, O> transformation;

    public TransformingIterable(final Iterable<I> iterable, final Function<I, O> transformation) {
        this(requireNonNull(iterable, "iterable").iterator(), transformation);
    }

    public TransformingIterable(final Iterator<I> iterator, final Function<I, O> transformation) {
        this.underlying = requireNonNull(iterator, "iterator");
        this.transformation = requireNonNull(transformation, "transformation");
    }

    @Override
    protected final void setNext() {
        if (!this.underlying.hasNext()) {
            return;
        }
        final I input = this.underlying.next();
        final O output = this.transformation.apply(input);
        this.setNext(output);
    }

}
