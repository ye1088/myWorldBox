package com.huluxia.image.base.imageutils;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamProcessor */
class c {
    c() {
    }

    public static int a(InputStream is, int numBytes, boolean isLittleEndian) throws IOException {
        int value = 0;
        for (int i = 0; i < numBytes; i++) {
            int b = is.read();
            if (b == -1) {
                throw new IOException("no more bytes");
            }
            if (isLittleEndian) {
                value |= (b & 255) << (i * 8);
            } else {
                value = (value << 8) | (b & 255);
            }
        }
        return value;
    }
}
