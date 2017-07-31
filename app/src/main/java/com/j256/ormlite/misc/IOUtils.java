package com.j256.ormlite.misc;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeThrowSqlException(Closeable closeable, String label) throws SQLException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw SqlExceptionUtil.create("could not close " + label, e);
            }
        }
    }
}
