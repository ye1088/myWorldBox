package io.netty.buffer;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ByteBufInputStream extends InputStream implements DataInput {
    private final ByteBuf buffer;
    private final int endIndex;
    private final StringBuilder lineBuf;
    private final int startIndex;

    public ByteBufInputStream(ByteBuf buffer) {
        this(buffer, buffer.readableBytes());
    }

    public ByteBufInputStream(ByteBuf buffer, int length) {
        this.lineBuf = new StringBuilder();
        if (buffer == null) {
            throw new NullPointerException("buffer");
        } else if (length < 0) {
            throw new IllegalArgumentException("length: " + length);
        } else if (length > buffer.readableBytes()) {
            throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + length + ", maximum is " + buffer.readableBytes());
        } else {
            this.buffer = buffer;
            this.startIndex = buffer.readerIndex();
            this.endIndex = this.startIndex + length;
            buffer.markReaderIndex();
        }
    }

    public int readBytes() {
        return this.buffer.readerIndex() - this.startIndex;
    }

    public int available() throws IOException {
        return this.endIndex - this.buffer.readerIndex();
    }

    public void mark(int readlimit) {
        this.buffer.markReaderIndex();
    }

    public boolean markSupported() {
        return true;
    }

    public int read() throws IOException {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte() & 255;
        }
        return -1;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }
        len = Math.min(available, len);
        this.buffer.readBytes(b, off, len);
        return len;
    }

    public void reset() throws IOException {
        this.buffer.resetReaderIndex();
    }

    public long skip(long n) throws IOException {
        if (n > 2147483647L) {
            return (long) skipBytes(Integer.MAX_VALUE);
        }
        return (long) skipBytes((int) n);
    }

    public boolean readBoolean() throws IOException {
        checkAvailable(1);
        if (read() != 0) {
            return true;
        }
        return false;
    }

    public byte readByte() throws IOException {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte();
        }
        throw new EOFException();
    }

    public char readChar() throws IOException {
        return (char) readShort();
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        checkAvailable(len);
        this.buffer.readBytes(b, off, len);
    }

    public int readInt() throws IOException {
        checkAvailable(4);
        return this.buffer.readInt();
    }

    public String readLine() throws IOException {
        this.lineBuf.setLength(0);
        while (this.buffer.isReadable()) {
            int c = this.buffer.readUnsignedByte();
            switch (c) {
                case 10:
                    break;
                case 13:
                    if (this.buffer.isReadable() && ((char) this.buffer.getUnsignedByte(this.buffer.readerIndex())) == '\n') {
                        this.buffer.skipBytes(1);
                        break;
                    }
                default:
                    this.lineBuf.append((char) c);
            }
            return this.lineBuf.toString();
        }
        if (this.lineBuf.length() > 0) {
            return this.lineBuf.toString();
        }
        return null;
    }

    public long readLong() throws IOException {
        checkAvailable(8);
        return this.buffer.readLong();
    }

    public short readShort() throws IOException {
        checkAvailable(2);
        return this.buffer.readShort();
    }

    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    public int readUnsignedByte() throws IOException {
        return readByte() & 255;
    }

    public int readUnsignedShort() throws IOException {
        return readShort() & 65535;
    }

    public int skipBytes(int n) throws IOException {
        int nBytes = Math.min(available(), n);
        this.buffer.skipBytes(nBytes);
        return nBytes;
    }

    private void checkAvailable(int fieldSize) throws IOException {
        if (fieldSize < 0) {
            throw new IndexOutOfBoundsException("fieldSize cannot be a_isRightVersion negative number");
        } else if (fieldSize > available()) {
            throw new EOFException("fieldSize is too long! Length is " + fieldSize + ", but maximum is " + available());
        }
    }
}
