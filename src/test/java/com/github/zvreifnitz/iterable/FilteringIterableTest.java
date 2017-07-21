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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FilteringIterableTest {

    private final static int Max = 5;
    private FilteringIterable<Integer> testedIterable;

    @BeforeEach
    void setUp() {
        final List<Integer> allIterables = new ArrayList<>();
        for (int i = 1; i <= Max; i++) {
            allIterables.add(i);
        }
        this.testedIterable = new FilteringIterable<>(allIterables, i -> i == Max);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void iterator() {
        final FilteringIterable<Integer> iterable = this.testedIterable;
        final boolean result = (iterable == iterable.iterator());
        assert result;
    }

    @Test
    void hasNext() {
        final Iterator<Integer> iterator = this.testedIterable.iterator();
        final boolean result = iterator.hasNext();
        assert result;
        iterator.next();
        final boolean endOk = (!iterator.hasNext());
        assert endOk;
    }

    @Test
    void next() {
        final Iterator<Integer> iterator = this.testedIterable.iterator();
        final boolean result = (Max == iterator.next());
        assert result;
    }

}