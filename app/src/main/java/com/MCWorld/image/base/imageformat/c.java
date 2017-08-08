package com.MCWorld.image.base.imageformat;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: GifFormatChecker */
public final class c {
    private static final int vv = 10;
    private static final byte[] vw = new byte[]{(byte) 0, BinaryMemcacheOpcodes.SASL_AUTH, (byte) -7, (byte) 4};
    private static final byte[] vx = new byte[]{(byte) 0, HttpConstants.COMMA};
    private static final byte[] vy = new byte[]{(byte) 0, BinaryMemcacheOpcodes.SASL_AUTH};

    private c() {
    }

    public static boolean e(InputStream source) {
        byte[] buffer = new byte[10];
        try {
            source.read(buffer, 0, 10);
            int offset = 0;
            int frameHeaders = 0;
            while (source.read(buffer, offset, 1) > 0) {
                if (a(buffer, offset + 1, vw) && (a(buffer, offset + 9, vx) || a(buffer, offset + 9, vy))) {
                    frameHeaders++;
                    if (frameHeaders > 1) {
                        return true;
                    }
                }
                offset = (offset + 1) % buffer.length;
            }
            return false;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @VisibleForTesting
    static boolean a(byte[] byteArray, int offset, byte[] pattern) {
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkNotNull(pattern);
        Preconditions.checkArgument(offset >= 0);
        if (pattern.length > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[(i + offset) % byteArray.length] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
