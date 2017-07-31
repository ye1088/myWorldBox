package com.huluxia.mcsdk.dtlib;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.huluxia.mcgame.h;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

/* compiled from: DTFileOperEx */
public class d {
    public static final int aoB = 1;
    public static final int aoC = 2;
    public static final int aoD = 3;
    public static final int aoE = 4;

    public static long dV(String filePath) {
        File file = new File(filePath);
        try {
            if (file.isDirectory()) {
                return getFileSizes(file);
            }
            return w(file);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
            return 0;
        }
    }

    public static double K(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = w(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return l(blockSize, sizeType);
    }

    public static String dW(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = w(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return aD(blockSize);
    }

    public static void I(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long w(File file) throws Exception {
        if (file.exists()) {
            long size = (long) new FileInputStream(file).available();
            String md5_Val = h.getFileMD5(file);
            I("/sdcard/yan/record.txt", "{\"");
            I("/sdcard/yan/record.txt", file.getPath());
            I("/sdcard/yan/record.txt", "\",\"");
            I("/sdcard/yan/record.txt", md5_Val);
            I("/sdcard/yan/record.txt", "\"},\r\n");
            return size;
        }
        file.createNewFile();
        Log.e("获取文件大小", "文件不存在!");
        return 0;
    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File[] flist = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size += getFileSizes(flist[i]);
            } else {
                size += w(flist[i]);
            }
        }
        return size;
    }

    private static String aD(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format(((double) fileS) / 1024.0d) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format(((double) fileS) / 1048576.0d) + "MB";
        } else {
            fileSizeString = df.format(((double) fileS) / 1.073741824E9d) + "GB";
        }
        return fileSizeString;
    }

    private static double l(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        switch (sizeType) {
            case 1:
                return Double.valueOf(df.format((double) fileS)).doubleValue();
            case 2:
                return Double.valueOf(df.format(((double) fileS) / 1024.0d)).doubleValue();
            case 3:
                return Double.valueOf(df.format(((double) fileS) / 1048576.0d)).doubleValue();
            case 4:
                return Double.valueOf(df.format(((double) fileS) / 1.073741824E9d)).doubleValue();
            default:
                return 0.0d;
        }
    }
}
