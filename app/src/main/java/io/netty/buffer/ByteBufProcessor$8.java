package io.netty.buffer;

class ByteBufProcessor$8 implements ByteBufProcessor {
    ByteBufProcessor$8() {
    }

    public boolean process(byte value) throws Exception {
        return value == (byte) 13 || value == (byte) 10;
    }
}
