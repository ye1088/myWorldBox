package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator.Handle;

public abstract class DefaultMaxMessagesRecvByteBufAllocator implements MaxMessagesRecvByteBufAllocator {
    private volatile int maxMessagesPerRead;

    public abstract class MaxMessageHandle implements Handle {
        private int attemptedBytesRead;
        private ChannelConfig config;
        private int lastBytesRead;
        private int maxMessagePerRead;
        private int totalBytesRead;
        private int totalMessages;

        public void reset(ChannelConfig config) {
            this.config = config;
            this.maxMessagePerRead = DefaultMaxMessagesRecvByteBufAllocator.this.maxMessagesPerRead();
            this.totalBytesRead = 0;
            this.totalMessages = 0;
        }

        public ByteBuf allocate(ByteBufAllocator alloc) {
            return alloc.ioBuffer(guess());
        }

        public final void incMessagesRead(int amt) {
            this.totalMessages += amt;
        }

        public final void lastBytesRead(int bytes) {
            this.lastBytesRead = bytes;
            this.totalBytesRead += bytes;
            if (this.totalBytesRead < 0) {
                this.totalBytesRead = Integer.MAX_VALUE;
            }
        }

        public final int lastBytesRead() {
            return this.lastBytesRead;
        }

        public boolean continueReading() {
            return this.config.isAutoRead() && this.attemptedBytesRead == this.lastBytesRead && this.totalMessages < this.maxMessagePerRead && this.totalBytesRead < Integer.MAX_VALUE;
        }

        public void readComplete() {
        }

        public int attemptedBytesRead() {
            return this.attemptedBytesRead;
        }

        public void attemptedBytesRead(int bytes) {
            this.attemptedBytesRead = bytes;
        }

        protected final int totalBytesRead() {
            return this.totalBytesRead;
        }
    }

    public DefaultMaxMessagesRecvByteBufAllocator() {
        this(1);
    }

    public DefaultMaxMessagesRecvByteBufAllocator(int maxMessagesPerRead) {
        maxMessagesPerRead(maxMessagesPerRead);
    }

    public int maxMessagesPerRead() {
        return this.maxMessagesPerRead;
    }

    public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int maxMessagesPerRead) {
        if (maxMessagesPerRead <= 0) {
            throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead + " (expected: > 0)");
        }
        this.maxMessagesPerRead = maxMessagesPerRead;
        return this;
    }
}
