package io.netty.channel;

import io.netty.util.AbstractReferenceCounted;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import net.lingala.zip4j.util.e;

public class DefaultFileRegion extends AbstractReferenceCounted implements FileRegion {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
    private final long count;
    private final File f;
    private FileChannel file;
    private final long position;
    private long transferred;

    public DefaultFileRegion(FileChannel file, long position, long count) {
        if (file == null) {
            throw new NullPointerException("file");
        } else if (position < 0) {
            throw new IllegalArgumentException("position must be >= 0 but was " + position);
        } else if (count < 0) {
            throw new IllegalArgumentException("count must be >= 0 but was " + count);
        } else {
            this.file = file;
            this.position = position;
            this.count = count;
            this.f = null;
        }
    }

    public DefaultFileRegion(File f, long position, long count) {
        if (f == null) {
            throw new NullPointerException("f");
        } else if (position < 0) {
            throw new IllegalArgumentException("position must be >= 0 but was " + position);
        } else if (count < 0) {
            throw new IllegalArgumentException("count must be >= 0 but was " + count);
        } else {
            this.position = position;
            this.count = count;
            this.f = f;
        }
    }

    public boolean isOpen() {
        return this.file != null;
    }

    public void open() throws IOException {
        if (!isOpen() && refCnt() > 0) {
            this.file = new RandomAccessFile(this.f, e.clf).getChannel();
        }
    }

    public long position() {
        return this.position;
    }

    public long count() {
        return this.count;
    }

    @Deprecated
    public long transfered() {
        return this.transferred;
    }

    public long transferred() {
        return this.transferred;
    }

    public long transferTo(WritableByteChannel target, long position) throws IOException {
        long count = this.count - position;
        if (count < 0 || position < 0) {
            throw new IllegalArgumentException("position out of range: " + position + " (expected: 0 - " + (this.count - 1) + ')');
        } else if (count == 0) {
            return 0;
        } else {
            if (refCnt() == 0) {
                throw new IllegalReferenceCountException(0);
            }
            open();
            long written = this.file.transferTo(this.position + position, count, target);
            if (written <= 0) {
                return written;
            }
            this.transferred += written;
            return written;
        }
    }

    protected void deallocate() {
        FileChannel file = this.file;
        if (file != null) {
            this.file = null;
            try {
                file.close();
            } catch (IOException e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close a file.", e);
                }
            }
        }
    }

    public FileRegion retain() {
        super.retain();
        return this;
    }

    public FileRegion retain(int increment) {
        super.retain(increment);
        return this;
    }

    public FileRegion touch() {
        return this;
    }

    public FileRegion touch(Object hint) {
        return this;
    }
}
