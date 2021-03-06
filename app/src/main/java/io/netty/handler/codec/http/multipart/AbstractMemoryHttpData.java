package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public abstract class AbstractMemoryHttpData extends AbstractHttpData {
    private ByteBuf byteBuf;
    private int chunkPosition;

    protected AbstractMemoryHttpData(String name, Charset charset, long size) {
        super(name, charset, size);
    }

    public void setContent(ByteBuf buffer) throws IOException {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        long localsize = (long) buffer.readableBytes();
        checkSize(localsize);
        if (this.definedSize <= 0 || this.definedSize >= localsize) {
            if (this.byteBuf != null) {
                this.byteBuf.release();
            }
            this.byteBuf = buffer;
            this.size = localsize;
            setCompleted();
            return;
        }
        throw new IOException("Out of size: " + localsize + " > " + this.definedSize);
    }

    public void setContent(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        ByteBuf buffer = Unpooled.buffer();
        byte[] bytes = new byte[16384];
        int read = inputStream.read(bytes);
        int written = 0;
        while (read > 0) {
            buffer.writeBytes(bytes, 0, read);
            written += read;
            checkSize((long) written);
            read = inputStream.read(bytes);
        }
        this.size = (long) written;
        if (this.definedSize <= 0 || this.definedSize >= this.size) {
            if (this.byteBuf != null) {
                this.byteBuf.release();
            }
            this.byteBuf = buffer;
            setCompleted();
            return;
        }
        throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
    }

    public void addContent(ByteBuf buffer, boolean last) throws IOException {
        if (buffer != null) {
            long localsize = (long) buffer.readableBytes();
            checkSize(this.size + localsize);
            if (this.definedSize <= 0 || this.definedSize >= this.size + localsize) {
                this.size += localsize;
                if (this.byteBuf == null) {
                    this.byteBuf = buffer;
                } else if (this.byteBuf instanceof CompositeByteBuf) {
                    this.byteBuf.addComponent(true, buffer);
                } else {
                    CompositeByteBuf cbb = Unpooled.compositeBuffer(Integer.MAX_VALUE);
                    cbb.addComponents(true, this.byteBuf, buffer);
                    this.byteBuf = cbb;
                }
            } else {
                throw new IOException("Out of size: " + (this.size + localsize) + " > " + this.definedSize);
            }
        }
        if (last) {
            setCompleted();
        } else if (buffer == null) {
            throw new NullPointerException("buffer");
        }
    }

    public void setContent(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file");
        }
        long newsize = file.length();
        if (newsize > 2147483647L) {
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
        checkSize(newsize);
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[((int) newsize)]);
        for (int read = 0; ((long) read) < newsize; read += fileChannel.read(byteBuffer)) {
        }
        fileChannel.close();
        inputStream.close();
        byteBuffer.flip();
        if (this.byteBuf != null) {
            this.byteBuf.release();
        }
        this.byteBuf = Unpooled.wrappedBuffer(Integer.MAX_VALUE, new ByteBuffer[]{byteBuffer});
        this.size = newsize;
        setCompleted();
    }

    public void delete() {
        if (this.byteBuf != null) {
            this.byteBuf.release();
            this.byteBuf = null;
        }
    }

    public byte[] get() {
        if (this.byteBuf == null) {
            return Unpooled.EMPTY_BUFFER.array();
        }
        byte[] array = new byte[this.byteBuf.readableBytes()];
        this.byteBuf.getBytes(this.byteBuf.readerIndex(), array);
        return array;
    }

    public String getString() {
        return getString(HttpConstants.DEFAULT_CHARSET);
    }

    public String getString(Charset encoding) {
        if (this.byteBuf == null) {
            return "";
        }
        if (encoding == null) {
            encoding = HttpConstants.DEFAULT_CHARSET;
        }
        return this.byteBuf.toString(encoding);
    }

    public ByteBuf getByteBuf() {
        return this.byteBuf;
    }

    public ByteBuf getChunk(int length) throws IOException {
        if (this.byteBuf == null || length == 0 || this.byteBuf.readableBytes() == 0) {
            this.chunkPosition = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        int sizeLeft = this.byteBuf.readableBytes() - this.chunkPosition;
        if (sizeLeft == 0) {
            this.chunkPosition = 0;
            return Unpooled.EMPTY_BUFFER;
        }
        int sliceLength = length;
        if (sizeLeft < length) {
            sliceLength = sizeLeft;
        }
        ByteBuf chunk = this.byteBuf.retainedSlice(this.chunkPosition, sliceLength);
        this.chunkPosition += sliceLength;
        return chunk;
    }

    public boolean isInMemory() {
        return true;
    }

    public boolean renameTo(File dest) throws IOException {
        if (dest == null) {
            throw new NullPointerException("dest");
        } else if (this.byteBuf != null) {
            int length = this.byteBuf.readableBytes();
            FileOutputStream outputStream = new FileOutputStream(dest);
            FileChannel fileChannel = outputStream.getChannel();
            int written = 0;
            if (this.byteBuf.nioBufferCount() == 1) {
                ByteBuffer byteBuffer = this.byteBuf.nioBuffer();
                while (written < length) {
                    written += fileChannel.write(byteBuffer);
                }
            } else {
                ByteBuffer[] byteBuffers = this.byteBuf.nioBuffers();
                while (written < length) {
                    written = (int) (((long) written) + fileChannel.write(byteBuffers));
                }
            }
            fileChannel.force(false);
            fileChannel.close();
            outputStream.close();
            if (written == length) {
                return true;
            }
            return false;
        } else if (dest.createNewFile()) {
            return true;
        } else {
            throw new IOException("file exists already: " + dest);
        }
    }

    public File getFile() throws IOException {
        throw new IOException("Not represented by a_isRightVersion file");
    }

    public HttpData touch() {
        return touch(null);
    }

    public HttpData touch(Object hint) {
        if (this.byteBuf != null) {
            this.byteBuf.touch(hint);
        }
        return this;
    }
}
