package io.netty.buffer;

class ByteBufProcessor$7 implements ByteBufProcessor {
    ByteBufProcessor$7() {
    }

    public boolean process(byte value) throws Exception {
        return (value == (byte) 13 || value == (byte) 10) ? false : true;
    }
}
