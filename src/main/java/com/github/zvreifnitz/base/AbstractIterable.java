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

package com.github.zvreifnitz.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractIterable<T> implements Iterable<T>, Iterator<T> {

    private boolean init = false;
    private boolean nextSet = false;
    private boolean hasNext = false;
    private T next = null;

    protected abstract void setNext();

    protected final void setNext(final T next) {
        if (this.nextSet) {
            throw new IllegalStateException("next already set");
        }
        this.next = next;
        this.hasNext = true;
        this.nextSet = true;
    }

    @Override
    public final Iterator<T> iterator() {
        this.init();
        return this;
    }

    @Override
    public final boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public final T next() {
        if (!this.hasNext) {
            throw new NoSuchElementException();
        }
        final T result = this.next;
        this.fetchNext();
        return result;
    }

    private void init() {
        if (this.init) {
            throw new IllegalStateException("iterator() already invoked");
        }
        this.init = true;
        this.setNext();
    }

    private void fetchNext() {
        this.nextSet = false;
        this.hasNext = false;
        this.next = null;
        this.setNext();
    }
}
