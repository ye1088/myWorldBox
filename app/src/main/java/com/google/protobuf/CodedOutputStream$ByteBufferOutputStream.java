package com.google.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

class CodedOutputStream$ByteBufferOutputStream extends OutputStream {
    private final ByteBuffer byteBuffer;

    public CodedOutputStream$ByteBufferOutputStream(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public void write(int b) throws IOException {
        this.byteBuffer.put((byte) b);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        this.byteBuffer.put(data, offset, length);
    }
}
