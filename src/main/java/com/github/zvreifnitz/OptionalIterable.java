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

public final class OptionalIterable<T> extends AbstractIterable<T> {

    private final T value;
    private boolean done;

    public OptionalIterable() {
        this(null, true);
    }

    public OptionalIterable(final T value) {
        this(value, false);
    }

    private OptionalIterable(final T value, final boolean done) {
        this.value = value;
        this.done = done;
    }

    @Override
    protected void setNext() {
        if (!this.done) {
            this.done = true;
            this.setNext(this.value);
        }
    }
}
