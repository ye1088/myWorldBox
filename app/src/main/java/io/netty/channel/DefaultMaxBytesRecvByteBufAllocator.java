package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator.Handle;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class DefaultMaxBytesRecvByteBufAllocator implements MaxBytesRecvByteBufAllocator {
    private volatile int maxBytesPerIndividualRead;
    private volatile int maxBytesPerRead;

    private final class HandleImpl implements Handle {
        private int attemptBytesRead;
        private int bytesToRead;
        private int individualReadMax;
        private int lastBytesRead;

        private HandleImpl() {
        }

        public ByteBuf allocate(ByteBufAllocator alloc) {
            return alloc.ioBuffer(guess());
        }

        public int guess() {
            return Math.min(this.individualReadMax, this.bytesToRead);
        }

        public void reset(ChannelConfig config) {
            this.bytesToRead = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerRead();
            this.individualReadMax = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerIndividualRead();
        }

        public void incMessagesRead(int amt) {
        }

        public void lastBytesRead(int bytes) {
            this.lastBytesRead = bytes;
            this.bytesToRead -= bytes;
        }

        public int lastBytesRead() {
            return this.lastBytesRead;
        }

        public boolean continueReading() {
            return this.bytesToRead > 0 && this.attemptBytesRead == this.lastBytesRead;
        }

        public void readComplete() {
        }

        public void attemptedBytesRead(int bytes) {
            this.attemptBytesRead = bytes;
        }

        public int attemptedBytesRead() {
            return this.attemptBytesRead;
        }
    }

    public DefaultMaxBytesRecvByteBufAllocator() {
        this(65536, 65536);
    }

    public DefaultMaxBytesRecvByteBufAllocator(int maxBytesPerRead, int maxBytesPerIndividualRead) {
        checkMaxBytesPerReadPair(maxBytesPerRead, maxBytesPerIndividualRead);
        this.maxBytesPerRead = maxBytesPerRead;
        this.maxBytesPerIndividualRead = maxBytesPerIndividualRead;
    }

    public Handle newHandle() {
        return new HandleImpl();
    }

    public int maxBytesPerRead() {
        return this.maxBytesPerRead;
    }

    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerRead(int maxBytesPerRead) {
        if (maxBytesPerRead <= 0) {
            throw new IllegalArgumentException("maxBytesPerRead: " + maxBytesPerRead + " (expected: > 0)");
        }
        synchronized (this) {
            int maxBytesPerIndividualRead = maxBytesPerIndividualRead();
            if (maxBytesPerRead < maxBytesPerIndividualRead) {
                throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + maxBytesPerIndividualRead + "): " + maxBytesPerRead);
            }
            this.maxBytesPerRead = maxBytesPerRead;
        }
        return this;
    }

    public int maxBytesPerIndividualRead() {
        return this.maxBytesPerIndividualRead;
    }

    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int maxBytesPerIndividualRead) {
        if (maxBytesPerIndividualRead <= 0) {
            throw new IllegalArgumentException("maxBytesPerIndividualRead: " + maxBytesPerIndividualRead + " (expected: > 0)");
        }
        synchronized (this) {
            int maxBytesPerRead = maxBytesPerRead();
            if (maxBytesPerIndividualRead > maxBytesPerRead) {
                throw new IllegalArgumentException("maxBytesPerIndividualRead cannot be greater than maxBytesPerRead (" + maxBytesPerRead + "): " + maxBytesPerIndividualRead);
            }
            this.maxBytesPerIndividualRead = maxBytesPerIndividualRead;
        }
        return this;
    }

    public synchronized Entry<Integer, Integer> maxBytesPerReadPair() {
        return new SimpleEntry(Integer.valueOf(this.maxBytesPerRead), Integer.valueOf(this.maxBytesPerIndividualRead));
    }

    private void checkMaxBytesPerReadPair(int maxBytesPerRead, int maxBytesPerIndividualRead) {
        if (maxBytesPerRead <= 0) {
            throw new IllegalArgumentException("maxBytesPerRead: " + maxBytesPerRead + " (expected: > 0)");
        } else if (maxBytesPerIndividualRead <= 0) {
            throw new IllegalArgumentException("maxBytesPerIndividualRead: " + maxBytesPerIndividualRead + " (expected: > 0)");
        } else if (maxBytesPerRead < maxBytesPerIndividualRead) {
            throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + maxBytesPerIndividualRead + "): " + maxBytesPerRead);
        }
    }

    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerReadPair(int maxBytesPerRead, int maxBytesPerIndividualRead) {
        checkMaxBytesPerReadPair(maxBytesPerRead, maxBytesPerIndividualRead);
        synchronized (this) {
            this.maxBytesPerRead = maxBytesPerRead;
            this.maxBytesPerIndividualRead = maxBytesPerIndividualRead;
        }
        return this;
    }
}
