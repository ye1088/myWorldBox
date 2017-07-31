package io.netty.channel;

import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle;

final class AdaptiveRecvByteBufAllocator$HandleImpl extends MaxMessageHandle {
    private boolean decreaseNow;
    private int index;
    private final int maxIndex;
    private final int minIndex;
    private int nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.access$100()[this.index];
    final /* synthetic */ AdaptiveRecvByteBufAllocator this$0;

    public AdaptiveRecvByteBufAllocator$HandleImpl(AdaptiveRecvByteBufAllocator adaptiveRecvByteBufAllocator, int minIndex, int maxIndex, int initial) {
        this.this$0 = adaptiveRecvByteBufAllocator;
        super();
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.index = AdaptiveRecvByteBufAllocator.access$000(initial);
    }

    public int guess() {
        return this.nextReceiveBufferSize;
    }

    private void record(int actualReadBytes) {
        if (actualReadBytes <= AdaptiveRecvByteBufAllocator.access$100()[Math.max(0, (this.index - 1) - 1)]) {
            if (this.decreaseNow) {
                this.index = Math.max(this.index - 1, this.minIndex);
                this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.access$100()[this.index];
                this.decreaseNow = false;
                return;
            }
            this.decreaseNow = true;
        } else if (actualReadBytes >= this.nextReceiveBufferSize) {
            this.index = Math.min(this.index + 4, this.maxIndex);
            this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.access$100()[this.index];
            this.decreaseNow = false;
        }
    }

    public void readComplete() {
        record(totalBytesRead());
    }
}
