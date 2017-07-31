package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public abstract class AbstractDiskHttpData extends AbstractHttpData {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractDiskHttpData.class);
    private File file;
    private FileChannel fileChannel;
    private boolean isRenamed;

    protected abstract boolean deleteOnExit();

    protected abstract String getBaseDirectory();

    protected abstract String getDiskFilename();

    protected abstract String getPostfix();

    protected abstract String getPrefix();

    protected AbstractDiskHttpData(String name, Charset charset, long size) {
        super(name, charset, size);
    }

    private File tempFile() throws IOException {
        String newpostfix;
        File tmpFile;
        String diskFilename = getDiskFilename();
        if (diskFilename != null) {
            newpostfix = '_' + diskFilename;
        } else {
            newpostfix = getPostfix();
        }
        if (getBaseDirectory() == null) {
            tmpFile = File.createTempFile(getPrefix(), newpostfix);
        } else {
            tmpFile = File.createTempFile(getPrefix(), newpostfix, new File(getBaseDirectory()));
        }
        if (deleteOnExit()) {
            tmpFile.deleteOnExit();
        }
        return tmpFile;
    }

    public void setContent(ByteBuf buffer) throws IOException {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        FileOutputStream outputStream;
        try {
            this.size = (long) buffer.readableBytes();
            checkSize(this.size);
            if (this.definedSize <= 0 || this.definedSize >= this.size) {
                if (this.file == null) {
                    this.file = tempFile();
                }
                if (buffer.readableBytes() != 0) {
                    outputStream = new FileOutputStream(this.file);
                    FileChannel localfileChannel = outputStream.getChannel();
                    ByteBuffer byteBuffer = buffer.nioBuffer();
                    int written = 0;
                    while (((long) written) < this.size) {
                        written += localfileChannel.write(byteBuffer);
                    }
                    buffer.readerIndex(buffer.readerIndex() + written);
                    localfileChannel.force(false);
                    outputStream.close();
                    setCompleted();
                    buffer.release();
                    return;
                } else if (this.file.createNewFile()) {
                    buffer.release();
                    return;
                } else {
                    throw new IOException("file exists already: " + this.file);
                }
            }
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        } catch (Throwable th) {
            buffer.release();
        }
    }

    public void addContent(ByteBuf buffer, boolean last) throws IOException {
        if (buffer != null) {
            try {
                int localsize = buffer.readableBytes();
                checkSize(this.size + ((long) localsize));
                if (this.definedSize <= 0 || this.definedSize >= this.size + ((long) localsize)) {
                    ByteBuffer byteBuffer;
                    if (buffer.nioBufferCount() == 1) {
                        byteBuffer = buffer.nioBuffer();
                    } else {
                        byteBuffer = buffer.copy().nioBuffer();
                    }
                    int written = 0;
                    if (this.file == null) {
                        this.file = tempFile();
                    }
                    if (this.fileChannel == null) {
                        this.fileChannel = new FileOutputStream(this.file).getChannel();
                    }
                    while (written < localsize) {
                        written += this.fileChannel.write(byteBuffer);
                    }
                    this.size += (long) localsize;
                    buffer.readerIndex(buffer.readerIndex() + written);
                } else {
                    throw new IOException("Out of size: " + (this.size + ((long) localsize)) + " > " + this.definedSize);
                }
            } finally {
                buffer.release();
            }
        }
        if (last) {
            if (this.file == null) {
                this.file = tempFile();
            }
            if (this.fileChannel == null) {
                this.fileChannel = new FileOutputStream(this.file).getChannel();
            }
            this.fileChannel.force(false);
            this.fileChannel.close();
            this.fileChannel = null;
            setCompleted();
        } else if (buffer == null) {
            throw new NullPointerException("buffer");
        }
    }

    public void setContent(File file) throws IOException {
        if (this.file != null) {
            delete();
        }
        this.file = file;
        this.size = file.length();
        checkSize(this.size);
        this.isRenamed = true;
        setCompleted();
    }

    public void setContent(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("inputStream");
        }
        if (this.file != null) {
            delete();
        }
        this.file = tempFile();
        FileOutputStream outputStream = new FileOutputStream(this.file);
        int written = 0;
        try {
            FileChannel localfileChannel = outputStream.getChannel();
            byte[] bytes = new byte[16384];
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            int read = inputStream.read(bytes);
            while (read > 0) {
                byteBuffer.position(read).flip();
                written += localfileChannel.write(byteBuffer);
                checkSize((long) written);
                read = inputStream.read(bytes);
            }
            localfileChannel.force(false);
            this.size = (long) written;
            if (this.definedSize <= 0 || this.definedSize >= this.size) {
                this.isRenamed = true;
                setCompleted();
                return;
            }
            if (!this.file.delete()) {
                logger.warn("Failed to delete: {}", this.file);
            }
            this.file = null;
            throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
        } finally {
            outputStream.close();
        }
    }

    public void delete() {
        if (this.fileChannel != null) {
            try {
                this.fileChannel.force(false);
                this.fileChannel.close();
            } catch (IOException e) {
                logger.warn("Failed to close a file.", e);
            }
            this.fileChannel = null;
        }
        if (!this.isRenamed) {
            if (!(this.file == null || !this.file.exists() || this.file.delete())) {
                logger.warn("Failed to delete: {}", this.file);
            }
            this.file = null;
        }
    }

    public byte[] get() throws IOException {
        if (this.file == null) {
            return EmptyArrays.EMPTY_BYTES;
        }
        return readFrom(this.file);
    }

    public ByteBuf getByteBuf() throws IOException {
        if (this.file == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.wrappedBuffer(readFrom(this.file));
    }

    public ByteBuf getChunk(int length) throws IOException {
        if (this.file == null || length == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (this.fileChannel == null) {
            this.fileChannel = new FileInputStream(this.file).getChannel();
        }
        int read = 0;
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        while (read < length) {
            int readnow = this.fileChannel.read(byteBuffer);
            if (readnow == -1) {
                this.fileChannel.close();
                this.fileChannel = null;
                break;
            }
            read += readnow;
        }
        if (read == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        byteBuffer.flip();
        ByteBuf buffer = Unpooled.wrappedBuffer(byteBuffer);
        buffer.readerIndex(0);
        buffer.writerIndex(read);
        return buffer;
    }

    public String getString() throws IOException {
        return getString(HttpConstants.DEFAULT_CHARSET);
    }

    public String getString(Charset encoding) throws IOException {
        if (this.file == null) {
            return "";
        }
        if (encoding == null) {
            return new String(readFrom(this.file), HttpConstants.DEFAULT_CHARSET.name());
        }
        return new String(readFrom(this.file), encoding.name());
    }

    public boolean isInMemory() {
        return false;
    }

    public boolean renameTo(File dest) throws IOException {
        IOException e;
        Throwable th;
        if (dest == null) {
            throw new NullPointerException("dest");
        } else if (this.file == null) {
            throw new IOException("No file defined so cannot be renamed");
        } else if (this.file.renameTo(dest)) {
            this.file = dest;
            this.isRenamed = true;
            return true;
        } else {
            IOException exception = null;
            FileInputStream inputStream = null;
            FileOutputStream outputStream = null;
            long chunkSize = 8196;
            long position = 0;
            try {
                FileInputStream inputStream2 = new FileInputStream(this.file);
                try {
                    FileOutputStream outputStream2 = new FileOutputStream(dest);
                    try {
                        FileChannel in = inputStream2.getChannel();
                        FileChannel out = outputStream2.getChannel();
                        while (position < this.size) {
                            if (chunkSize < this.size - position) {
                                chunkSize = this.size - position;
                            }
                            position += in.transferTo(position, chunkSize, out);
                        }
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                            } catch (IOException e2) {
                                if (null == null) {
                                    exception = e2;
                                } else {
                                    logger.warn("Multiple exceptions detected, the following will be suppressed {}", e2);
                                }
                            }
                        }
                        if (outputStream2 != null) {
                            try {
                                outputStream2.close();
                                outputStream = outputStream2;
                                inputStream = inputStream2;
                            } catch (IOException e22) {
                                if (exception == null) {
                                    exception = e22;
                                } else {
                                    logger.warn("Multiple exceptions detected, the following will be suppressed {}", e22);
                                }
                                outputStream = outputStream2;
                                inputStream = inputStream2;
                            }
                        } else {
                            inputStream = inputStream2;
                        }
                    } catch (IOException e3) {
                        e22 = e3;
                        outputStream = outputStream2;
                        inputStream = inputStream2;
                        exception = e22;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e222) {
                                if (exception == null) {
                                    exception = e222;
                                } else {
                                    logger.warn("Multiple exceptions detected, the following will be suppressed {}", e222);
                                }
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e2222) {
                                if (exception == null) {
                                    exception = e2222;
                                } else {
                                    logger.warn("Multiple exceptions detected, the following will be suppressed {}", e2222);
                                }
                            }
                        }
                        if (exception == null) {
                            throw exception;
                        } else if (position != this.size) {
                            if (!dest.delete()) {
                                logger.warn("Failed to delete: {}", dest);
                            }
                            return false;
                        } else {
                            if (!this.file.delete()) {
                                logger.warn("Failed to delete: {}", this.file);
                            }
                            this.file = dest;
                            this.isRenamed = true;
                            return true;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = outputStream2;
                        inputStream = inputStream2;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e22222) {
                                if (null == null) {
                                    exception = e22222;
                                } else {
                                    logger.warn("Multiple exceptions detected, the following will be suppressed {}", e22222);
                                }
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e222222) {
                                if (exception == null) {
                                    exception = e222222;
                                } else {
                                    logger.warn("Multiple exceptions detected, the following will be suppressed {}", e222222);
                                }
                            }
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e222222 = e4;
                    inputStream = inputStream2;
                    exception = e222222;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (exception == null) {
                        throw exception;
                    } else if (position != this.size) {
                        if (dest.delete()) {
                            logger.warn("Failed to delete: {}", dest);
                        }
                        return false;
                    } else {
                        if (this.file.delete()) {
                            logger.warn("Failed to delete: {}", this.file);
                        }
                        this.file = dest;
                        this.isRenamed = true;
                        return true;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    inputStream = inputStream2;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e222222 = e5;
                exception = e222222;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (exception == null) {
                    throw exception;
                } else if (position != this.size) {
                    if (this.file.delete()) {
                        logger.warn("Failed to delete: {}", this.file);
                    }
                    this.file = dest;
                    this.isRenamed = true;
                    return true;
                } else {
                    if (dest.delete()) {
                        logger.warn("Failed to delete: {}", dest);
                    }
                    return false;
                }
            } catch (Throwable th4) {
                th = th4;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th;
            }
            if (exception == null) {
                throw exception;
            } else if (position != this.size) {
                if (this.file.delete()) {
                    logger.warn("Failed to delete: {}", this.file);
                }
                this.file = dest;
                this.isRenamed = true;
                return true;
            } else {
                if (dest.delete()) {
                    logger.warn("Failed to delete: {}", dest);
                }
                return false;
            }
        }
    }

    private static byte[] readFrom(File src) throws IOException {
        long srcsize = src.length();
        if (srcsize > 2147483647L) {
            throw new IllegalArgumentException("File too big to be loaded in memory");
        }
        FileInputStream inputStream = new FileInputStream(src);
        byte[] array = new byte[((int) srcsize)];
        try {
            FileChannel fileChannel = inputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.wrap(array);
            for (int read = 0; ((long) read) < srcsize; read += fileChannel.read(byteBuffer)) {
            }
            return array;
        } finally {
            inputStream.close();
        }
    }

    public File getFile() throws IOException {
        return this.file;
    }

    public HttpData touch() {
        return this;
    }

    public HttpData touch(Object hint) {
        return this;
    }
}
