package io.netty.channel;

import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle;
import io.netty.channel.RecvByteBufAllocator.Handle;

public class FixedRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
    private final int bufferSize;

    private final class HandleImpl extends MaxMessageHandle {
        private final int bufferSize;

        public HandleImpl(int bufferSize) {
            super();
            this.bufferSize = bufferSize;
        }

        public int guess() {
            return this.bufferSize;
        }
    }

    public FixedRecvByteBufAllocator(int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must greater than 0: " + bufferSize);
        }
        this.bufferSize = bufferSize;
    }

    public Handle newHandle() {
        return new HandleImpl(this.bufferSize);
    }
}
