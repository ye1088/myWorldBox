package org.bytedeco.javacpp;

import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Name;

@Name({"long"})
public class CLongPointer extends Pointer {
    private native void allocateArray(int i);

    @Cast({"long"})
    public native long get(int i);

    public native CLongPointer put(int i, long j);

    public CLongPointer(int size) {
        try {
            allocateArray(size);
        } catch (UnsatisfiedLinkError e) {
            throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
        }
    }

    public CLongPointer(Pointer p) {
        super(p);
    }

    public CLongPointer position(int position) {
        return (CLongPointer) super.position(position);
    }

    public CLongPointer limit(int limit) {
        return (CLongPointer) super.limit(limit);
    }

    public CLongPointer capacity(int capacity) {
        return (CLongPointer) super.capacity(capacity);
    }

    public long get() {
        return get(0);
    }

    public CLongPointer put(long l) {
        return put(0, l);
    }
}
