package io.netty.util;

public interface Attribute<T> {
    boolean compareAndSet(T t, T t2);

    T get();

    @Deprecated
    T getAndRemove();

    T getAndSet(T t);

    AttributeKey<T> key();

    @Deprecated
    void remove();

    void set(T t);

    T setIfAbsent(T t);
}
