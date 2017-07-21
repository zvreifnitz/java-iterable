package com.github.zvreifnitz.iterable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

class FileLinesIterableTest {

    private final static int Max = 5;
    private TransformingIterable<Integer, String> testedIterable;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final String path = classLoader.getResource("lines1to5.txt").getFile();
        final File file = new File(path);
        final Iterable<String> fileLinesIterable = new FileLinesIterable(file);
        this.testedIterable = new TransformingIterable<>(fileLinesIterable, Integer::parseInt);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void iterator() {
        final Iterable<Integer> iterable = this.testedIterable;
        final boolean result = (iterable == iterable.iterator());
        assert result;
    }

    @Test
    void hasNext() {
        final Iterator<Integer> iterator = this.testedIterable.iterator();
        for (int i = 1; i <= Max; i++) {
            final boolean result = iterator.hasNext();
            assert result;
            iterator.next();
        }
        final boolean endOk = (!iterator.hasNext());
        assert endOk;
    }

    @Test
    void next() {
        final Iterator<Integer> iterator = this.testedIterable.iterator();
        for (int i = 1; i <= Max; i++) {
            final boolean result = (i == iterator.next());
            assert result;
        }
    }
}