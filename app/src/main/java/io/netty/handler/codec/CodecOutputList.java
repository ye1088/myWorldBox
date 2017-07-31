package io.netty.handler.codec;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.internal.ObjectUtil;
import java.util.AbstractList;
import java.util.RandomAccess;

final class CodecOutputList extends AbstractList<Object> implements RandomAccess {
    private static final Recycler<CodecOutputList> RECYCLER = new Recycler<CodecOutputList>() {
        protected CodecOutputList newObject(Handle<CodecOutputList> handle) {
            return new CodecOutputList(handle);
        }
    };
    private Object[] array;
    private final Handle<CodecOutputList> handle;
    private boolean insertSinceRecycled;
    private int size;

    static CodecOutputList newInstance() {
        return (CodecOutputList) RECYCLER.get();
    }

    private CodecOutputList(Handle<CodecOutputList> handle) {
        this.array = new Object[16];
        this.handle = handle;
    }

    public Object get(int index) {
        checkIndex(index);
        return this.array[index];
    }

    public int size() {
        return this.size;
    }

    public boolean add(Object element) {
        ObjectUtil.checkNotNull(element, "element");
        try {
            insert(this.size, element);
        } catch (IndexOutOfBoundsException e) {
            expandArray();
            insert(this.size, element);
        }
        this.size++;
        return true;
    }

    public Object set(int index, Object element) {
        ObjectUtil.checkNotNull(element, "element");
        checkIndex(index);
        Object old = this.array[index];
        insert(index, element);
        return old;
    }

    public void add(int index, Object element) {
        ObjectUtil.checkNotNull(element, "element");
        checkIndex(index);
        if (this.size == this.array.length) {
            expandArray();
        }
        if (index != this.size - 1) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        }
        insert(index, element);
        this.size++;
    }

    public Object remove(int index) {
        checkIndex(index);
        Object old = this.array[index];
        int len = (this.size - index) - 1;
        if (len > 0) {
            System.arraycopy(this.array, index + 1, this.array, index, len);
        }
        Object[] objArr = this.array;
        int i = this.size - 1;
        this.size = i;
        objArr[i] = null;
        return old;
    }

    public void clear() {
        this.size = 0;
    }

    boolean insertSinceRecycled() {
        return this.insertSinceRecycled;
    }

    void recycle() {
        for (int i = 0; i < this.size; i++) {
            this.array[i] = null;
        }
        clear();
        this.insertSinceRecycled = false;
        this.handle.recycle(this);
    }

    Object getUnsafe(int index) {
        return this.array[index];
    }

    private void checkIndex(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void insert(int index, Object element) {
        this.array[index] = element;
        this.insertSinceRecycled = true;
    }

    private void expandArray() {
        int newCapacity = this.array.length << 1;
        if (newCapacity < 0) {
            throw new OutOfMemoryError();
        }
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        this.array = newArray;
    }
}
