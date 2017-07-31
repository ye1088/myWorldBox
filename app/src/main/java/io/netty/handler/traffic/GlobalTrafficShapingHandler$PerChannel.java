package io.netty.handler.traffic;

import java.util.ArrayDeque;

final class GlobalTrafficShapingHandler$PerChannel {
    long lastReadTimestamp;
    long lastWriteTimestamp;
    ArrayDeque<GlobalTrafficShapingHandler$ToSend> messagesQueue;
    long queueSize;

    private GlobalTrafficShapingHandler$PerChannel() {
    }
}
