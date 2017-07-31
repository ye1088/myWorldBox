package io.netty.channel;

public interface MessageSizeEstimator {

    public interface Handle {
        int size(Object obj);
    }

    Handle newHandle();
}
