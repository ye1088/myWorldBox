package com.huluxia.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/* compiled from: UtilsIo */
public final class af {
    private af() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    public static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("not a_isRightVersion directory: " + dir);
        }
        int length = files.length;
        int i = 0;
        while (i < length) {
            File file = files[i];
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (file.delete()) {
                i++;
            } else {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }
}
