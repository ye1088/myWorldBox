package com.huluxia.framework.base.utils;

import com.huluxia.framework.base.log.HLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilsMD5 {
    private static final String HASH_ALGORITHM_MD5 = "MD5";
    private static final String TAG = "MD5Utils";
    public static MessageDigest messagedigest;

    static {
        messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance(HASH_ALGORITHM_MD5);
        } catch (NoSuchAlgorithmException e) {
            HLog.error(TAG, "get message digest failed! " + e.toString(), new Object[0]);
        }
    }

    public static String getFileMd5String(String path) throws IOException {
        if (path == null || path.length() == 0) {
            return null;
        }
        return getFileMD5String(new File(path));
    }

    public static String getFileMD5String(File file) throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }
        String md5 = null;
        try {
            FileInputStream in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(MapMode.READ_ONLY, 0, file.length());
            if (messagedigest != null) {
                messagedigest.update(byteBuffer);
                md5 = bufferToHex(messagedigest.digest());
            }
            in.close();
            return md5;
        } catch (Exception e) {
            HLog.error(TAG, e);
            return null;
        }
    }

    public static String getMD5String(String s) {
        if (s == null) {
            return null;
        }
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (messagedigest == null) {
            return null;
        }
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    public static String bufferToHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int start, int len) {
        if (bytes == null || start < 0 || len < 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(len * 2);
        int max = start + len;
        for (int i = start; i < max; i++) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    public static String MD5Code(String key) {
        try {
            MessageDigest hash = MessageDigest.getInstance(HASH_ALGORITHM_MD5);
            hash.update(key.getBytes());
            byte[] digest = hash.digest();
            StringBuilder builder = new StringBuilder();
            for (int b : digest) {
                builder.append(Integer.toHexString((b >> 4) & 15));
                builder.append(Integer.toHexString((b >> 0) & 15));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
