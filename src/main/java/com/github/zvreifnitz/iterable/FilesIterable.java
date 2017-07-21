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

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.LinkedList;

import static java.util.Objects.requireNonNull;

public final class FilesIterable extends AbstractIterable<File> {

    private final static File[] Empty = new File[0];

    private final LinkedList<File> directories = new LinkedList<>();
    private final LinkedList<File> files = new LinkedList<>();
    private final FileFilter filter;

    public FilesIterable(final String path) {
        this(path, NotNullFileFilter.Instance);
    }

    public FilesIterable(final String path, final FileFilter filter) {
        this.filter = requireNonNull(filter, "filter");
        final File root = new File(requireNonNull(path, "path"));
        if (root.exists() && NotNullFileFilter.Instance.accept(root)) {
            if (IsFileFilter.Instance.accept(root)) {
                this.files.add(root);
            } else if (IsDirectoryFilter.Instance.accept(root)) {
                this.directories.add(root);
            }
        }
    }

    private static File[] listFiles(final File file, final FileFilter filter) {
        final File[] files = file.listFiles(filter);
        return ((files == null) ? Empty : files);
    }

    private void fill(final File file) {
        Collections.addAll(this.directories, listFiles(file, IsDirectoryFilter.Instance));
        Collections.addAll(this.files, listFiles(file, IsFileFilter.Instance));
    }

    @Override
    protected final void setNext() {
        boolean searchInProgress = true;
        while (searchInProgress) {
            searchInProgress = !(this.files.isEmpty() ? this.searchDirectory() : this.searchFile());
        }
    }

    private boolean searchDirectory() {
        if (this.directories.isEmpty()) {
            return true;
        } else {
            final File directory = this.directories.removeFirst();
            this.fill(directory);
            return this.search(directory);
        }
    }

    private boolean searchFile() {
        final File file = this.files.removeFirst();
        return this.search(file);
    }

    private boolean search(final File file) {
        if (this.filter.accept(file)) {
            this.setNext(file);
            return true;
        } else {
            return false;
        }
    }

    private final static class IsDirectoryFilter implements FileFilter {
        private final static FileFilter Instance = new IsDirectoryFilter();

        @Override
        public boolean accept(final File file) {
            return ((file != null) && file.isDirectory());
        }
    }

    private final static class IsFileFilter implements FileFilter {
        private final static FileFilter Instance = new IsFileFilter();

        @Override
        public boolean accept(final File file) {
            return ((file != null) && file.isFile());
        }
    }

    private final static class NotNullFileFilter implements FileFilter {
        private final static FileFilter Instance = new NotNullFileFilter();

        @Override
        public boolean accept(final File file) {
            return (file != null);
        }
    }
}
