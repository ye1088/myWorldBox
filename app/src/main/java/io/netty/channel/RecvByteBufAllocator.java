package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.ObjectUtil;

public interface RecvByteBufAllocator {

    public interface Handle {
        ByteBuf allocate(ByteBufAllocator byteBufAllocator);

        int attemptedBytesRead();

        void attemptedBytesRead(int i);

        boolean continueReading();

        int guess();

        void incMessagesRead(int i);

        int lastBytesRead();

        void lastBytesRead(int i);

        void readComplete();

        void reset(ChannelConfig channelConfig);
    }

    public static class DelegatingHandle implements Handle {
        private final Handle delegate;

        public DelegatingHandle(Handle delegate) {
            this.delegate = (Handle) ObjectUtil.checkNotNull(delegate, "delegate");
        }

        protected final Handle delegate() {
            return this.delegate;
        }

        public ByteBuf allocate(ByteBufAllocator alloc) {
            return this.delegate.allocate(alloc);
        }

        public int guess() {
            return this.delegate.guess();
        }

        public void reset(ChannelConfig config) {
            this.delegate.reset(config);
        }

        public void incMessagesRead(int numMessages) {
            this.delegate.incMessagesRead(numMessages);
        }

        public void lastBytesRead(int bytes) {
            this.delegate.lastBytesRead(bytes);
        }

        public int lastBytesRead() {
            return this.delegate.lastBytesRead();
        }

        public boolean continueReading() {
            return this.delegate.continueReading();
        }

        public int attemptedBytesRead() {
            return this.delegate.attemptedBytesRead();
        }

        public void attemptedBytesRead(int bytes) {
            this.delegate.attemptedBytesRead(bytes);
        }

        public void readComplete() {
            this.delegate.readComplete();
        }
    }

    Handle newHandle();
}
