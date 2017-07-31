package io.netty.buffer;

class ByteBufProcessor$10 implements ByteBufProcessor {
    ByteBufProcessor$10() {
    }

    public boolean process(byte value) throws Exception {
        return value == (byte) 32 || value == (byte) 9;
    }
}
