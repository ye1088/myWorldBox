package io.netty.util;

public interface ResourceLeak {
    boolean close();

    void record();

    void record(Object obj);
}
