/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bson;

import java.util.List;

public class LazyBSONCallback extends EmptyBSONCallback {
    private Object root;

    private Object getRoot() {
        return root;
    }

    private void setRoot(final Object root) {
        this.root = root;
    }

    @Override
    public void reset() {
        this.root = null;
    }

    @Override
    public Object get() {
        return getRoot();
    }

    @Override
    public void gotBinary(final String name, final byte type, final byte[] data) {
        setRoot(createObject(data, 0));
    }

    public Object createObject(final byte[] bytes, final int offset) {
        return new LazyBSONObject(bytes, offset, this);
    }

    @SuppressWarnings("rawtypes")
    public List createArray(final byte[] bytes, final int offset) {
        return new LazyBSONList(bytes, offset, this);
    }
}
