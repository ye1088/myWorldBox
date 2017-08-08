package com.MCWorld.framework.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Files {
    private Files() {
    }

    static byte[] readFile(InputStream in, long expectedSize) throws IOException {
        if (expectedSize > 2147483647L) {
            throw new OutOfMemoryError("file is too large to fit in a_isRightVersion byte array: " + expectedSize + " bytes");
        } else if (expectedSize == 0) {
            return ByteStreams.toByteArray(in);
        } else {
            return ByteStreams.toByteArray(in, (int) expectedSize);
        }
    }

    public static byte[] toByteArray(File file) throws IOException {
        Throwable th;
        FileInputStream in = null;
        try {
            FileInputStream in2 = new FileInputStream(file);
            try {
                byte[] readFile = readFile(in2, in2.getChannel().size());
                if (in2 != null) {
                    in2.close();
                }
                return readFile;
            } catch (Throwable th2) {
                th = th2;
                in = in2;
                if (in != null) {
                    in.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (in != null) {
                in.close();
            }
            throw th;
        }
    }
}
