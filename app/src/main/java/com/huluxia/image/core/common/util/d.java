package com.huluxia.image.core.common.util;

import com.huluxia.framework.base.utils.ByteStreams;
import com.huluxia.framework.base.utils.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamUtil */
public class d {
    public static byte[] v(InputStream is) throws IOException {
        return d(is, is.available());
    }

    public static byte[] d(InputStream inputStream, int hint) throws IOException {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(hint) {
            public byte[] toByteArray() {
                if (this.count == this.buf.length) {
                    return this.buf;
                }
                return super.toByteArray();
            }
        };
        ByteStreams.copy(inputStream, byteOutput);
        return byteOutput.toByteArray();
    }

    public static long a(InputStream inputStream, long bytesCount) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkArgument(bytesCount >= 0);
        long toSkip = bytesCount;
        while (toSkip > 0) {
            long skipped = inputStream.skip(toSkip);
            if (skipped > 0) {
                toSkip -= skipped;
            } else if (inputStream.read() == -1) {
                return bytesCount - toSkip;
            } else {
                toSkip--;
            }
        }
        return bytesCount;
    }
}
