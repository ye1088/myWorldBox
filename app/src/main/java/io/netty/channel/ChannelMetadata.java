package io.netty.channel;

public final class ChannelMetadata {
    private final int defaultMaxMessagesPerRead;
    private final boolean hasDisconnect;

    public ChannelMetadata(boolean hasDisconnect) {
        this(hasDisconnect, 1);
    }

    public ChannelMetadata(boolean hasDisconnect, int defaultMaxMessagesPerRead) {
        if (defaultMaxMessagesPerRead <= 0) {
            throw new IllegalArgumentException("defaultMaxMessagesPerRead: " + defaultMaxMessagesPerRead + " (expected > 0)");
        }
        this.hasDisconnect = hasDisconnect;
        this.defaultMaxMessagesPerRead = defaultMaxMessagesPerRead;
    }

    public boolean hasDisconnect() {
        return this.hasDisconnect;
    }

    public int defaultMaxMessagesPerRead() {
        return this.defaultMaxMessagesPerRead;
    }
}
