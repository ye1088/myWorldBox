package io.netty.buffer;

class ByteBufProcessor$9 implements ByteBufProcessor {
    ByteBufProcessor$9() {
    }

    public boolean process(byte value) throws Exception {
        return (value == (byte) 32 || value == (byte) 9) ? false : true;
    }
}
