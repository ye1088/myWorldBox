package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public final class Delimiters {
    public static ByteBuf[] nulDelimiter() {
        ByteBuf[] byteBufArr = new ByteBuf[1];
        byteBufArr[0] = Unpooled.wrappedBuffer(new byte[]{(byte) 0});
        return byteBufArr;
    }

    public static ByteBuf[] lineDelimiter() {
        ByteBuf[] byteBufArr = new ByteBuf[2];
        byteBufArr[0] = Unpooled.wrappedBuffer(new byte[]{(byte) 13, (byte) 10});
        byteBufArr[1] = Unpooled.wrappedBuffer(new byte[]{(byte) 10});
        return byteBufArr;
    }

    private Delimiters() {
    }
}
