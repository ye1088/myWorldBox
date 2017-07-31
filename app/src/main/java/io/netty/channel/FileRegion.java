package io.netty.channel;

import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public interface FileRegion extends ReferenceCounted {
    long count();

    long position();

    FileRegion retain();

    FileRegion retain(int i);

    FileRegion touch();

    FileRegion touch(Object obj);

    long transferTo(WritableByteChannel writableByteChannel, long j) throws IOException;

    @Deprecated
    long transfered();

    long transferred();
}
