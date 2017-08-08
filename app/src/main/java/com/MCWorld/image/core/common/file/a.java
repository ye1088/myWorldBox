package com.MCWorld.image.core.common.file;

import java.io.File;

/* compiled from: FileTree */
public class a {
    public static void a(File directory, b visitor) {
        visitor.k(directory);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    a(file, visitor);
                } else {
                    visitor.l(file);
                }
            }
        }
        visitor.m(directory);
    }

    public static boolean deleteContents(File directory) {
        File[] files = directory.listFiles();
        boolean success = true;
        if (files != null) {
            for (File file : files) {
                success &= t(file);
            }
        }
        return success;
    }

    public static boolean t(File file) {
        if (file.isDirectory()) {
            deleteContents(file);
        }
        return file.delete();
    }
}
