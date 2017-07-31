package com.huluxia.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import com.huluxia.HTApplication;
import com.huluxia.framework.AppConstant;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.service.i;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.regex.Pattern;

public class UtilsFile {
    private static Context acz = null;
    public static final String aoA = "storage/sdcard";
    public static final String aoz = "storage/emulated/";
    static boolean bkd = false;
    static boolean bke = false;
    static boolean bkf = false;
    static boolean bkg = false;
    private static final String blM = ("huluxia" + File.separator + "xiugaiqi");
    static boolean blN = false;
    static boolean blO = false;
    static boolean blP = false;

    public static class ExtStorageNotFoundExecption extends Exception {
        public ExtStorageNotFoundExecption(String detail) {
            super(detail);
        }
    }

    public static void bj(Context context) {
        acz = context;
    }

    public static File getExtStorageRoot(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + File.separator + context.getPackageName());
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (!"mounted".equals(Environment.getExternalStorageState()) || isExternalStorageRemovable()) {
            cachePath = context.getCacheDir().getPath();
        } else {
            cachePath = getExternalCacheDir(context).getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (UtilsVersion.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static File getExternalCacheDir(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }

    public static File getInternalCacheDir(Context context) {
        return context.getCacheDir();
    }

    public static File getInternalFilesDir(Context context) {
        return context.getFilesDir();
    }

    @TargetApi(8)
    public static File h(Context context, String type, String uniqueName) {
        if (UtilsVersion.hasFroyo()) {
            return Environment.getExternalStoragePublicDirectory(type);
        }
        return new File(getExtStorageRoot(context).getPath() + File.separator + uniqueName);
    }

    @TargetApi(8)
    public static File i(Context context, String type, String uniqueName) {
        if (UtilsVersion.hasFroyo()) {
            return context.getExternalFilesDir(type);
        }
        return new File(getExtStorageRoot(context).getPath() + File.separator + uniqueName);
    }

    public boolean isExternalStorageWritable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state) || "mounted_ro".equals(state)) {
            return true;
        }
        return false;
    }

    public static File buildPath(File base, String... segments) {
        File cur = base;
        int length = segments.length;
        int i = 0;
        File cur2 = cur;
        while (i < length) {
            String segment = segments[i];
            if (cur2 == null) {
                cur = new File(segment);
            } else {
                cur = new File(cur2, segment);
            }
            i++;
            cur2 = cur;
        }
        return cur2;
    }

    public static String CU() {
        if (CT() && Environment.getExternalStorageDirectory().canWrite()) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return HTApplication.getAppContext().getFilesDir().getPath();
    }

    public static String dO(String strSrc) {
        if (Pattern.compile("/?storage/emulated/\\d{1,2}").matcher(strSrc).find()) {
            return strSrc.replace("storage/emulated/", "storage/sdcard");
        }
        return strSrc;
    }

    public static String getRootPath() {
        return CU() + File.separator + AppConstant.getHlxName() + File.separator + "mctool" + File.separator;
    }

    public static String KO() {
        return CU() + File.separator + AppConstant.getHlxName();
    }

    public static String KP() {
        return CU() + File.separator + AppConstant.getHlxName() + File.separator + "mctool" + File.separator + "SaveImage";
    }

    public static String KQ() {
        return getRootPath() + "Message" + File.separator;
    }

    public static String KR() {
        return getRootPath() + "ImageCache" + File.separator;
    }

    public static String KS() {
        return getRootPath() + "gif" + File.separator;
    }

    public static String KT() {
        return getRootPath() + "FileCache" + File.separator;
    }

    public static String getVoiceCachePath() {
        return getRootPath() + "VoiceCache" + File.separator;
    }

    public static String KU() {
        return getRootPath() + "SaveImage" + File.separator;
    }

    public static String KV() {
        return CU() + File.separator + "Pictures" + File.separator + "huluxia";
    }

    public static String KW() {
        return CU() + File.separator + "Pictures";
    }

    public static String getTempPath() {
        return getRootPath() + "Temp" + File.separator;
    }

    public static String fx(String dPath) {
        return CU() + File.separator + dPath + File.separator;
    }

    public static String cW(boolean useExternal) {
        if (useExternal && KG() > 0.0d) {
            return KD();
        }
        if (KF() > 0.0d) {
            return KC();
        }
        if (KE() > 0.0d) {
            return KB();
        }
        return HTApplication.getAppContext().getFilesDir().getPath();
    }

    public static boolean e(String path, long needMB) {
        StatFs stat = getStatFs(path);
        if (stat == null) {
            return false;
        }
        if (a(stat) > Double.valueOf((double) needMB).doubleValue()) {
            return true;
        }
        return false;
    }

    public static boolean f(String path, long needByte) {
        StatFs stat = getStatFs(path);
        if (stat == null) {
            return false;
        }
        if (a(stat) > ((double) (Float.valueOf(String.valueOf(needByte)).floatValue() / 1048576.0f)) * 1.6d) {
            return true;
        }
        return false;
    }

    public static void Kx() {
        mkdir(KO());
        boolean mkroot = mkdir(getRootPath());
        mkdir(KT());
        eU(KR());
        mkdir(KS());
        mkdir(KQ());
        mkdir(getVoiceCachePath());
        mkdir(getTempPath());
        mkdir(KU());
    }

    public static boolean isExist(String path) {
        return new File(path).exists();
    }

    public static void eU(String path) {
        mkdir(path);
        String str = "abcdef0123456789";
        for (int i = 0; i < str.length(); i++) {
            mkdir(path + File.separator + str.charAt(i) + File.separator);
        }
    }

    public static boolean mkdir(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    public static String fN(String fromfile) {
        try {
            String toFile = KU() + System.currentTimeMillis() + ".jpeg";
            InputStream fosfrom = new FileInputStream(fromfile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            while (true) {
                int c = fosfrom.read(bt);
                if (c > 0) {
                    fosto.write(bt, 0, c);
                } else {
                    fosfrom.close();
                    fosto.close();
                    i.eg(toFile);
                    return toFile;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String B(byte[] data) {
        try {
            String toFile = KU() + System.currentTimeMillis() + ".jpeg";
            OutputStream fosto = new FileOutputStream(toFile);
            fosto.write(data);
            fosto.close();
            i.eg(toFile);
            return toFile;
        } catch (Exception e) {
            return null;
        }
    }

    public static String eV(String filePath) {
        if (filePath == null) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static String eW(String urlString) {
        String filePath = KS() + eV(urlString);
        return filePath == null ? "" : filePath + ".ht";
    }

    public static String eX(String urlString) {
        String fName = eV(urlString);
        if (fName.length() <= 0) {
            fName = "a";
        }
        fName = UtilsMD5.MD5Code(fName);
        String filePath = (KR() + fName.substring(0, 1).toLowerCase(Locale.getDefault())) + File.separator + fName;
        return filePath == null ? "" : filePath + ".wj";
    }

    public static String eY(String urlString) {
        String filePath = getVoiceCachePath() + eV(urlString);
        return filePath == null ? "" : filePath + ".ht";
    }

    public static void f(final String path, final byte[] data) {
        ay.execute(new Runnable() {
            public void run() {
                UtilsFile.saveBytesToSD(path, data);
            }
        });
    }

    public static void deleteFile(String path) {
        if (path != null) {
            y(new File(path));
        }
    }

    public static void y(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File y : files) {
                y(y);
            }
        }
        file.delete();
    }

    public static boolean eZ(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        y(file);
        return true;
    }

    public static String getTempFileName() {
        return getTempPath() + System.currentTimeMillis() + ".ht";
    }

    public static String fO(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = fp(url);
        String subDir = KY() + File.separator + "TempFile";
        if (!blN) {
            blN = true;
            mkdir(subDir);
        }
        return subDir + File.separator + fName;
    }

    public static String fP(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = fp(url).replace('.', '_');
        String subDir = KY() + File.separator + "TempImage";
        if (!blO) {
            blO = true;
            mkdir(subDir);
        }
        return subDir + File.separator + fName + DiskFileUpload.postfix;
    }

    public static String fQ(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = fp(url).replace('.', '_');
        String subDir = KY() + File.separator + "TempSound";
        if (!blP) {
            blP = true;
            mkdir(subDir);
        }
        return subDir + File.separator + fName + DiskFileUpload.postfix;
    }

    public static String fa(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String type = fq(url);
        if (fu(type)) {
            return fO(url);
        }
        if (ft(type)) {
            return fQ(url);
        }
        return fO(url);
    }

    public static String fb(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = h(url, false);
        String subDir = getRootPath() + "DownFile";
        if (!bkd) {
            bkd = true;
            mkdir(subDir);
        }
        return subDir + File.separator + fName;
    }

    public static String fc(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = h(url, true);
        String subDir = getRootPath() + "DownApk";
        if (!bke) {
            bke = true;
            mkdir(subDir);
        }
        return subDir + File.separator + fName;
    }

    public static String fd(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = h(url, true);
        String subDir = getRootPath() + "DownZip";
        if (!bkf) {
            bkf = true;
            mkdir(subDir);
        }
        return subDir + File.separator + fName;
    }

    public static String KA() {
        String subDir = getRootPath() + "Installer";
        if (!bkg) {
            bkg = true;
            mkdir(subDir);
        }
        return subDir + File.separator;
    }

    public static long fe(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    public static boolean rename(String from, String to) {
        File fdFrom = new File(from);
        if (!fdFrom.exists()) {
            return false;
        }
        File fdTo = new File(to);
        if (!fdTo.exists() || fdTo.delete()) {
            return fdFrom.renameTo(fdTo);
        }
        return false;
    }

    public static String ff(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String type = fq(url);
        if (fr(type)) {
            return fc(url);
        }
        if (fs(type)) {
            return fd(url);
        }
        return fb(url);
    }

    private static String h(String path, boolean regularName) {
        String suffix = path.substring(path.lastIndexOf(File.separator) + 1);
        if (!regularName || suffix.length() > 30) {
            return String.valueOf(suffix.hashCode());
        }
        return suffix;
    }

    public static String fg(String path) {
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        byte[] Buffer = new byte[1024];
        FileInputStream in = null;
        ByteArrayOutputStream outputStream = null;
        try {
            FileInputStream in2 = new FileInputStream(new File(path));
            try {
                int len = in2.read(Buffer);
                ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                try {
                    outputStream2.write(Buffer, 0, len);
                    String str = new String(outputStream2.toByteArray());
                    if (in2 != null) {
                        try {
                            in2.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (outputStream2 != null) {
                        try {
                            outputStream2.flush();
                            outputStream2.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                    outputStream = outputStream2;
                    in = in2;
                    return str;
                } catch (FileNotFoundException e4) {
                    e2 = e4;
                    outputStream = outputStream2;
                    in = in2;
                    try {
                        e2.printStackTrace();
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e322) {
                                e322.printStackTrace();
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.flush();
                                outputStream.close();
                            } catch (IOException e3222) {
                                e3222.printStackTrace();
                            }
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e32222) {
                                e32222.printStackTrace();
                            }
                        }
                        if (outputStream != null) {
                            try {
                                outputStream.flush();
                                outputStream.close();
                            } catch (IOException e322222) {
                                e322222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e322222 = e5;
                    outputStream = outputStream2;
                    in = in2;
                    e322222.printStackTrace();
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e3222222) {
                            e3222222.printStackTrace();
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e32222222) {
                            e32222222.printStackTrace();
                        }
                    }
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = outputStream2;
                    in = in2;
                    if (in != null) {
                        in.close();
                    }
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e6) {
                e2 = e6;
                in = in2;
                e2.printStackTrace();
                if (in != null) {
                    in.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                return null;
            } catch (IOException e7) {
                e32222222 = e7;
                in = in2;
                e32222222.printStackTrace();
                if (in != null) {
                    in.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                return null;
            } catch (Throwable th4) {
                th = th4;
                in = in2;
                if (in != null) {
                    in.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e8) {
            e2 = e8;
            e2.printStackTrace();
            if (in != null) {
                in.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            return null;
        } catch (IOException e9) {
            e32222222 = e9;
            e32222222.printStackTrace();
            if (in != null) {
                in.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            return null;
        }
    }

    public static void fh(String path) {
        File f = new File(path);
        if (f != null && f.exists()) {
            f.delete();
        }
    }

    public static void fi(String dPath) {
        mkdir(fx(dPath));
    }

    public static void fj(String dPath) {
        mkdir(dPath);
    }

    public static long fk(String path) {
        StatFs fileStats = new StatFs(path);
        fileStats.restat(path);
        return ((long) fileStats.getAvailableBlocks()) * ((long) fileStats.getBlockSize());
    }

    public static String KB() {
        return Environment.getDataDirectory().getPath();
    }

    public static String KC() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String KD() {
        return "/mnt/sdcard1";
    }

    private static StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double a(StatFs stat) {
        if (stat != null) {
            return (double) (((float) stat.getAvailableBlocks()) * (((float) stat.getBlockSize()) / 1048576.0f));
        }
        return 0.0d;
    }

    public static double KE() {
        return a(getStatFs(KB()));
    }

    public static double KF() {
        return a(getStatFs(KC()));
    }

    public static double KG() {
        StatFs stat = getStatFs(KD());
        if (stat == null) {
            return 0.0d;
        }
        return a(stat);
    }

    public static boolean fl(String packName) {
        return O(packName, -1) >= 0;
    }

    public static int O(String packName, int verCode) {
        if (packName == null || packName.length() == 0) {
            return -1;
        }
        PackageInfo pi;
        try {
            pi = HTApplication.getAppContext().getPackageManager().getPackageInfo(packName, 0);
        } catch (NameNotFoundException e) {
            pi = null;
        }
        if (pi == null) {
            return -1;
        }
        if (verCode == -1 || pi.versionCode >= verCode) {
            return 1;
        }
        return 0;
    }

    public static int ae(String packName, String verName) {
        if (packName == null || packName.length() == 0) {
            return -1;
        }
        PackageInfo pi;
        try {
            pi = HTApplication.getAppContext().getPackageManager().getPackageInfo(packName, 0);
        } catch (NameNotFoundException e) {
            pi = null;
        }
        if (pi == null) {
            return -1;
        }
        if (verName == null || verName.length() == 0 || pi.versionName == null || pi.versionName.equals(verName)) {
            return 1;
        }
        return 0;
    }

    public static String A(Context context, String absPath) {
        PackageInfo pkgInfo = context.getPackageManager().getPackageArchiveInfo(absPath, 1);
        if (pkgInfo != null) {
            return pkgInfo.applicationInfo.packageName;
        }
        return null;
    }

    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs();
            String[] file = new File(oldPath).list();
            for (int i = 0; i < file.length; i++) {
                File temp;
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                    byte[] b = new byte[5120];
                    while (true) {
                        int len = input.read(b);
                        if (len == -1) {
                            break;
                        }
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String fm(String path) {
        File[] files = new File(path).listFiles();
        if (files == null) {
            return "";
        }
        for (File file : files) {
            if (file.isFile()) {
                String filePath = file.getPath();
                if (filePath.endsWith(".apk")) {
                    return filePath;
                }
            }
        }
        return "";
    }

    public static boolean copyFile(String oldPath, String newPath) {
        int bytesum = 0;
        try {
            if (!new File(oldPath).exists()) {
                return true;
            }
            FileInputStream inStream = new FileInputStream(oldPath);
            FileOutputStream outStream = new FileOutputStream(newPath);
            byte[] buffer = new byte[5120];
            while (true) {
                int byteread = inStream.read(buffer);
                if (byteread != -1) {
                    bytesum += byteread;
                    HLog.verbose("", "copy file old path = " + oldPath + ", newpath = " + newPath + ", byte = " + bytesum, new Object[0]);
                    outStream.write(buffer, 0, byteread);
                } else {
                    outStream.flush();
                    inStream.close();
                    outStream.close();
                    return true;
                }
            }
        } catch (Exception e) {
            HLog.verbose("", "copy file error, oldpath = " + oldPath + ", newpath = " + newPath, e);
            return false;
        }
    }

    public static String fn(String fileName) {
        String strIconPath = fo("icon");
        mkdir(strIconPath);
        if (fileName == null || fileName.length() <= 0) {
            return strIconPath;
        }
        return strIconPath + File.separator + fileName;
    }

    public static String fo(String fileName) {
        if (!CT()) {
            return null;
        }
        String strDataPath = Environment.getExternalStorageDirectory().toString() + File.separator + "pictures";
        mkdir(strDataPath);
        strDataPath = strDataPath + File.separator + "hlx_jietu";
        mkdir(strDataPath);
        if (fileName == null || fileName.length() <= 0) {
            return strDataPath;
        }
        return strDataPath + File.separator + fileName;
    }

    private static String KX() {
        if (CT()) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return acz.getFilesDir().getPath();
    }

    public static boolean CT() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    protected static String KY() {
        return KX() + File.separator + blM;
    }

    private static String fp(String path) {
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }

    private static String fq(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    private static boolean fr(String type) {
        return type.equalsIgnoreCase("apk");
    }

    private static boolean fs(String type) {
        if (type.equalsIgnoreCase("7z") || type.equalsIgnoreCase("zip") || type.equalsIgnoreCase("jar") || type.equalsIgnoreCase("tar")) {
            return true;
        }
        return false;
    }

    private static boolean ft(String type) {
        return false;
    }

    private static boolean fu(String type) {
        if (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("bmp") || type.equalsIgnoreCase("png") || type.equalsIgnoreCase("jpeg")) {
            return true;
        }
        return false;
    }

    public static void saveBytesToSD(String filePath, byte[] data) {
        IOException e;
        Throwable th;
        if (data != null) {
            FileOutputStream fos = null;
            try {
                FileOutputStream fos2 = new FileOutputStream(new File(filePath));
                try {
                    fos2.write(data);
                    fos2.flush();
                    if (fos2 != null) {
                        try {
                            fos2.close();
                            return;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            fos = fos2;
                            return;
                        }
                    }
                } catch (IOException e3) {
                    e2 = e3;
                    fos = fos2;
                    try {
                        e2.printStackTrace();
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fos = fos2;
                    if (fos != null) {
                        fos.close();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e222 = e4;
                e222.printStackTrace();
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    public static byte[] getBytesFromSD(String filePath) {
        Throwable th;
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis2 = new FileInputStream(file);
                try {
                    byte[] buf = new byte[((int) file.length())];
                    if (fis2.read(buf) == 0) {
                        if (fis2 != null) {
                            try {
                                fis2.close();
                            } catch (Exception e) {
                            }
                        }
                        fis = fis2;
                        return null;
                    }
                    if (fis2 != null) {
                        try {
                            fis2.close();
                        } catch (Exception e2) {
                        }
                    }
                    fis = fis2;
                    return buf;
                } catch (FileNotFoundException e3) {
                    fis = fis2;
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (Exception e4) {
                        }
                    }
                    return null;
                } catch (OutOfMemoryError e5) {
                    fis = fis2;
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (Exception e6) {
                        }
                    }
                    return null;
                } catch (IOException e7) {
                    fis = fis2;
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (Exception e8) {
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    fis = fis2;
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (Exception e9) {
                        }
                    }
                    throw th;
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e10) {
                }
            }
            return null;
        } catch (FileNotFoundException e11) {
            if (fis != null) {
                fis.close();
            }
            return null;
        } catch (OutOfMemoryError e12) {
            if (fis != null) {
                fis.close();
            }
            return null;
        } catch (IOException e13) {
            if (fis != null) {
                fis.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (fis != null) {
                fis.close();
            }
            throw th;
        }
    }

    public static String uriToPath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        if (new File(uri.getPath()).exists()) {
            return uri.getPath();
        }
        String path = null;
        try {
            Cursor c = context.getContentResolver().query(uri, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                path = c.getString(c.getColumnIndex("_data"));
            }
            if (c != null && c.moveToFirst()) {
                path = c.getString(c.getColumnIndex("_data"));
            }
            c.close();
            return path;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public static Uri fv(String path) {
        if (path == null) {
            return null;
        }
        File file = new File(path);
        if (file != null && file.exists() && file.isFile()) {
            return Uri.fromFile(file);
        }
        return null;
    }

    public static String getFileMD5(String path) {
        return getFileMD5(new File(path));
    }

    public static String getFileMD5(File file) {
        Exception e;
        if (!file.isFile()) {
            return null;
        }
        byte[] buffer = new byte[1024];
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            while (true) {
                try {
                    int len = in.read(buffer, 0, 1024);
                    if (len != -1) {
                        digest.update(buffer, 0, len);
                    } else {
                        in.close();
                        return new BigInteger(1, digest.digest()).toString(16);
                    }
                } catch (Exception e2) {
                    e = e2;
                    FileInputStream fileInputStream = in;
                }
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    public static long fR(String path) {
        File file = new File(path);
        if (file == null || !file.isFile()) {
            return 0;
        }
        long sum = 0;
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            for (int i = 0; ((long) i) < file.length() / 4; i++) {
                sum += (long) in.read(buf);
            }
            in.close();
            return sum;
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e2) {
            return 0;
        }
    }

    private static void Kp() {
        mkdir(CU() + File.separator + "games");
        mkdir(CU() + File.separator + "games" + File.separator + "com.mojang");
        mkdir(CU() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "minecraftWorlds");
        mkdir(Kt());
        mkdir(cT(true));
        mkdir(Ku());
        mkdir(cU(true));
        mkdir(Kv());
        mkdir(cX(true));
        mkdir(cX(false));
    }

    public static String Kq() {
        return CU() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "minecraftWorlds" + File.separator;
    }

    public static String eM(String title) {
        return getDownloadPath() + title + ".zip";
    }

    public static String Kt() {
        return getRootPath() + "modscriptzip" + File.separator;
    }

    public static String eN(String title) {
        return Kt() + title + ".zip";
    }

    public static String cT(boolean bEnd) {
        if (bEnd) {
            return getRootPath() + "modscript" + File.separator;
        }
        return getRootPath() + "modscript";
    }

    public static String Ku() {
        return getRootPath() + "skinzip" + File.separator;
    }

    public static String eO(String title) {
        return Ku() + title + ".zip";
    }

    public static String cU(boolean bEnd) {
        if (bEnd) {
            return getRootPath() + "skin" + File.separator;
        }
        return getRootPath() + "skin";
    }

    public static String Kv() {
        return getRootPath() + "woodzip" + File.separator;
    }

    public static String eP(String title) {
        return Kv() + title + ".zip";
    }

    public static String cX(boolean b110) {
        if (b110) {
            return getRootPath() + "gameres110";
        }
        return getRootPath() + "gameres105";
    }

    public static String ld(int flag) {
        if (flag == 0) {
            return getRootPath() + "gameres105";
        }
        if (1 == flag) {
            return getRootPath() + "gameres110";
        }
        return getRootPath() + "gameres";
    }

    public static String g(boolean b110, String rootPath) {
        if (b110) {
            return rootPath + File.separator + "gameres110";
        }
        return rootPath + File.separator + "gameres105";
    }

    public static boolean Z(String title, String MD5) {
        return ad(eM(title), MD5);
    }

    public static boolean aa(String title, String MD5) {
        return ad(eN(title), MD5);
    }

    public static boolean ab(String title, String MD5) {
        return ad(eO(title), MD5);
    }

    public static boolean ac(String title, String MD5) {
        return ad(eP(title), MD5);
    }

    public static boolean ad(String path, String MD5) {
        if (MD5 == null || MD5.equals("")) {
            return false;
        }
        String fileMD5 = getFileMD5(new File(path));
        if (fileMD5 != null) {
            return fileMD5.equalsIgnoreCase(MD5);
        }
        return false;
    }

    public static String Kw() {
        return getRootPath() + "game_pack" + File.separator;
    }

    public static String eQ(String name) {
        return Kq() + name + File.separator + "level.dat";
    }

    public static String fS(String name) {
        return Kq() + name + File.separator + "level.dat_old";
    }

    public static String getDownloadPath() {
        return CU() + File.separator + "Download" + File.separator;
    }

    public static String KI() {
        return getRootPath() + "Log" + File.separator;
    }
}
