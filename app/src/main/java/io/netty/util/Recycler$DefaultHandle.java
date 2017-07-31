package io.netty.util;

final class Recycler$DefaultHandle<T> implements Recycler$Handle<T> {
    boolean hasBeenRecycled;
    private int lastRecycledId;
    private int recycleId;
    private Recycler$Stack<?> stack;
    private Object value;

    Recycler$DefaultHandle(Recycler$Stack<?> stack) {
        this.stack = stack;
    }

    public void recycle(Object object) {
        if (object != this.value) {
            throw new IllegalArgumentException("object does not belong to handle");
        }
        this.stack.push(this);
    }
}
