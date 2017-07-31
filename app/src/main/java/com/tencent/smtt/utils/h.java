package com.tencent.smtt.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class h {
    public static final String a = a();
    public static final a b = new i();
    private static final int c = "lib/".length();

    public interface a {
    }

    private static String a() {
        try {
            return Environment.getExternalStorageDirectory() + File.separator + "tencent" + File.separator + "tbs" + File.separator + "tbslog";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String a(Context context, int i) {
        if (context == null) {
            return "";
        }
        try {
            String str = Environment.getExternalStorageDirectory() + File.separator + "tencent" + File.separator + "tbs";
            switch (i) {
                case 1:
                    return str + File.separator + context.getApplicationInfo().packageName;
                case 2:
                    return Environment.getExternalStorageDirectory() + File.separator + "tbs" + File.separator + "backup" + File.separator + context.getApplicationInfo().packageName;
                case 3:
                    return str + File.separator + "backup" + File.separator + context.getApplicationInfo().packageName;
                case 4:
                    return str + File.separator + context.getApplicationInfo().packageName;
                default:
                    return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void a(File file) {
        a(file, false);
    }

    public static void a(File file, boolean z) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File a : listFiles) {
                    a(a, z);
                }
                if (!z) {
                    file.delete();
                }
            }
        }
    }

    public static FileOutputStream b(File file) {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!(parentFile == null || parentFile.exists() || parentFile.mkdirs())) {
                throw new IOException("File '" + file + "' could not be created");
            }
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (!file.canWrite()) {
            throw new IOException("File '" + file + "' cannot be written to");
        }
        return new FileOutputStream(file);
    }
}
