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

import java.io.*;

import static java.util.Objects.requireNonNull;

public final class FileLinesIterable extends AbstractIterable<String> implements AutoCloseable {

    private final BufferedReader reader;

    public FileLinesIterable(final String file) throws FileNotFoundException {
        this(new FileReader(requireNonNull(file, "file")));
    }

    public FileLinesIterable(final File file) throws FileNotFoundException {
        this(new FileReader(requireNonNull(file, "file")));
    }

    public FileLinesIterable(final FileDescriptor file) {
        this(new FileReader(requireNonNull(file, "file")));
    }

    public FileLinesIterable(final FileReader fileReader) {
        this.reader = new BufferedReader(requireNonNull(fileReader, "fileReader"));
    }

    @Override
    protected final void setNext() {
        try {
            final String line = this.reader.readLine();
            if (line == null) {
                this.close();
            }
            this.setNext(line);
        } catch (Exception e) {
            this.close();
            throw new RuntimeException(e);
        }
    }

    @Override
    public final void close() {
        try {
            this.reader.close();
        } catch (Exception ignored) {
        }
    }

}
