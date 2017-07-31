package com.huluxia.framework.base.utils;

import android.support.annotation.z;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Closeables {
    @VisibleForTesting
    static final Logger logger = Logger.getLogger(Closeables.class.getName());

    private Closeables() {
    }

    public static void close(@z Closeable closeable, boolean swallowIOException) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                if (swallowIOException) {
                    logger.log(Level.WARNING, "IOException thrown while closing Closeable.", e);
                    return;
                }
                throw e;
            }
        }
    }

    public static void closeQuietly(@z InputStream inputStream) {
        try {
            close(inputStream, true);
        } catch (IOException impossible) {
            throw new AssertionError(impossible);
        }
    }

    public static void closeQuietly(@z Reader reader) {
        try {
            close(reader, true);
        } catch (IOException impossible) {
            throw new AssertionError(impossible);
        }
    }
}
