package io.netty.handler.codec.http;

import io.netty.util.ByteProcessor;

class DefaultHttpHeaders$1 implements ByteProcessor {
    DefaultHttpHeaders$1() {
    }

    public boolean process(byte value) throws Exception {
        DefaultHttpHeaders.access$000(value);
        return true;
    }
}
