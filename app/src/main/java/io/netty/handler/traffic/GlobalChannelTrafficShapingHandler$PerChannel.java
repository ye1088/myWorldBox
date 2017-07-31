package io.netty.handler.traffic;

import java.util.ArrayDeque;

final class GlobalChannelTrafficShapingHandler$PerChannel {
    TrafficCounter channelTrafficCounter;
    long lastReadTimestamp;
    long lastWriteTimestamp;
    ArrayDeque<GlobalChannelTrafficShapingHandler$ToSend> messagesQueue;
    long queueSize;

    GlobalChannelTrafficShapingHandler$PerChannel() {
    }
}
