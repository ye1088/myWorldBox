package com.tencent.mm.sdk.platformtools;

import com.tencent.mm.algorithm.MD5;
import java.io.File;
import java.io.IOException;

public class FilePathGenerator {
    public static final String ANDROID_DIR_SEP = "/";
    public static final int HASH_TYPE_ALL_MD5 = 2;
    public static final int HASH_TYPE_HEAD_2_BYTE = 1;
    public static final String NO_MEDIA_FILENAME = ".nomedia";

    public enum DIR_HASH_TYPE {
        HEAD_2_BYTE,
        ALL_MD5
    }

    private static String b(String str) {
        return (!Util.isNullOrNil(str) && str.length() > 4) ? str.substring(0, 2) + "/" + str.substring(2, 4) + "/" : null;
    }

    private static boolean c(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
                file = new File(str + NO_MEDIA_FILENAME);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    public static String defGenPathWithOld(String str, String str2, String str3, String str4, String str5, int i) {
        String str6 = str + str3 + str4 + str5;
        String genPath = genPath(str2, str3, str4, str5, i);
        if (Util.isNullOrNil(str6) || Util.isNullOrNil(genPath)) {
            return null;
        }
        File file = new File(genPath);
        File file2 = new File(str6);
        if (file.exists() || !file2.exists()) {
            return genPath;
        }
        FilesCopy.copy(str6, genPath, false);
        return genPath;
    }

    public static String genPath(String str, String str2, String str3, String str4, int i) {
        if (Util.isNullOrNil(str) || !str.endsWith("/")) {
            return null;
        }
        String str5 = "";
        if (i == 1) {
            str5 = b(str3);
        } else if (i == 2) {
            str5 = Util.isNullOrNil(str3) ? null : b(MD5.getMessageDigest(str3.getBytes()));
        }
        if (Util.isNullOrNil(str5)) {
            return null;
        }
        str5 = str + str5;
        return c(str5) ? str5 + Util.nullAsNil(str2) + str3 + Util.nullAsNil(str4) : null;
    }
}
