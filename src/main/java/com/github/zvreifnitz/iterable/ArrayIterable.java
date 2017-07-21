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

import static java.util.Objects.requireNonNull;

public final class ArrayIterable<T> extends AbstractIterable<T> {

    private final T[] array;
    private int index = 0;

    public ArrayIterable(final T[] array) {
        this.array = requireNonNull(array, "array");
    }

    @Override
    protected final void setNext() {
        if (this.index < this.array.length) {
            final T item = this.array[this.index++];
            this.setNext(item);
        }
    }
}
