package io.netty.channel;

import io.netty.util.concurrent.FastThreadLocal;
import java.nio.ByteBuffer;

class ChannelOutboundBuffer$1 extends FastThreadLocal<ByteBuffer[]> {
    ChannelOutboundBuffer$1() {
    }

    protected ByteBuffer[] initialValue() throws Exception {
        return new ByteBuffer[1024];
    }
}
