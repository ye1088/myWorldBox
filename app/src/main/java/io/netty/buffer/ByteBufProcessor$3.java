package io.netty.buffer;

class ByteBufProcessor$3 implements ByteBufProcessor {
    ByteBufProcessor$3() {
    }

    public boolean process(byte value) throws Exception {
        return value != (byte) 13;
    }
}
