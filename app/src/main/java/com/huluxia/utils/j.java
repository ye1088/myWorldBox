package com.huluxia.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import com.huluxia.HTApplication;
import com.huluxia.framework.base.utils.UtilsMD5;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: McUtilsFile */
public class j extends UtilsFile {
    static boolean bkd = false;
    static boolean bke = false;
    static boolean bkf = false;
    static boolean bkg = false;

    public static void Kp() {
        UtilsFile.mkdir(UtilsFile.getSdCardPath() + File.separator + "games");
        UtilsFile.mkdir(UtilsFile.getSdCardPath() + File.separator + "games" + File.separator + "com.mojang");
        UtilsFile.mkdir(UtilsFile.getSdCardPath() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "minecraftWorlds");
        UtilsFile.mkdir(Kt());
        UtilsFile.mkdir(cT(true));
        UtilsFile.mkdir(Ku());
        UtilsFile.mkdir(cU(true));
        UtilsFile.mkdir(Kv());
        UtilsFile.mkdir(UtilsFile.cX(true));
        UtilsFile.mkdir(UtilsFile.cX(false));
        UtilsFile.mkdir(cV(true));
        UtilsFile.mkdir(Ks());
        UtilsFile.mkdir(Kr());
        UtilsFile.mkdir(UtilsFile.KW());
        UtilsFile.mkdir(UtilsFile.KV());
    }

    public static String Kq() {
        return UtilsFile.getSdCardPath() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "minecraftWorlds" + File.separator;
    }

    public static String Kr() {
        return UtilsFile.getSdCardPath() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "minecraftpe" + File.separator;
    }

    public static String Ks() {
        return UtilsFile.getSdCardPath() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "resource_packs" + File.separator;
    }

    public static String eM(String title) {
        return getDownloadPath() + title + ".zip";
    }

    public static String Kt() {
        return UtilsFile.get_mctool_path() + "modscriptzip" + File.separator;
    }

    public static String eN(String title) {
        return Kt() + title + ".zip";
    }

    public static String cT(boolean bEnd) {
        if (bEnd) {
            return UtilsFile.get_mctool_path() + "modscript" + File.separator;
        }
        return UtilsFile.get_mctool_path() + "modscript";
    }

    public static String Ku() {
        return UtilsFile.get_mctool_path() + "skinzip" + File.separator;
    }

    public static String eO(String title) {
        return Ku() + title + ".zip";
    }

    public static String cU(boolean bEnd) {
        if (bEnd) {
            return UtilsFile.get_mctool_path() + "skin" + File.separator;
        }
        return UtilsFile.get_mctool_path() + "skin";
    }

    public static String Kv() {
        return UtilsFile.get_mctool_path() + "woodzip" + File.separator;
    }

    public static String eP(String title) {
        return Kv() + title + ".zip";
    }

    public static String cV(boolean bEnd) {
        if (bEnd) {
            return UtilsFile.get_mctool_path() + "maps" + File.separator;
        }
        return UtilsFile.get_mctool_path() + "maps";
    }

    public static String ld(int inputBootVersion) {
        if (inputBootVersion == 0) {
            return UtilsFile.get_mctool_path() + "gameres105";
        }
        if (1 == inputBootVersion) {
            return UtilsFile.get_mctool_path() + "gameres110";
        }
        if (2 == inputBootVersion) {
            return UtilsFile.get_mctool_path() + "gameres121";
        }
        if (3 == inputBootVersion) {
            return UtilsFile.get_mctool_path() + "gameres130";
        }
        return null;
    }

    public static String m(int flag, String rootPath) {
        if (flag == 0) {
            return rootPath + File.separator + "gameres105";
        }
        if (1 == flag) {
            return rootPath + File.separator + "gameres110";
        }
        if (2 == flag) {
            return rootPath + File.separator + "gameres121";
        }
        if (3 == flag) {
            return rootPath + File.separator + "gameres130";
        }
        if (4 == flag) {
            return rootPath + File.separator + "gameres131";
        }
        if (5 == flag) {
            return rootPath + File.separator + "gameres140";
        }
        if (6 == flag) {
            return rootPath + File.separator + "gameres140";
        }
        return rootPath + File.separator + "gameres";
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
        return UtilsFile.get_mctool_path() + "game_pack" + File.separator;
    }

    public static String eQ(String name) {
        return Kq() + name + File.separator + "level.dat";
    }

    public static String cW(boolean useExternal) {
        if (KG() > 0.0d && useExternal) {
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
        UtilsFile.mkdir(UtilsFile.KT());
        UtilsFile.eU(UtilsFile.KR());
        UtilsFile.mkdir(UtilsFile.KS());
        UtilsFile.mkdir(UtilsFile.KQ());
        UtilsFile.mkdir(KI());
        UtilsFile.mkdir(UtilsFile.getVoiceCachePath());
        UtilsFile.mkdir(UtilsFile.getTempPath());
        UtilsFile.mkdir(UtilsFile.KU());
        UtilsFile.mkdir(getDownloadPath());
        Kp();
    }

    public static long eR(String path) {
        long mModifiedtime = 0;
        try {
            File localFile = new File(path);
            if (localFile.exists()) {
                mModifiedtime = localFile.lastModified();
            }
        } catch (Exception e) {
        }
        return mModifiedtime;
    }

    public static boolean isExist(String path) {
        return new File(path).exists();
    }

    public static boolean eS(String mapDirName) {
        String mapsDir = Kq();
        return isExist(new StringBuilder().append(mapsDir).append(mapDirName).append(File.separator).toString()) && (isExist(mapsDir + mapDirName + File.separator + "level.dat") || isExist(mapsDir + mapDirName + File.separator + "level.dat_old"));
    }

    public static boolean Ky() {
        return isExist(UtilsFile.getSdCardPath() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "minecraftpe" + File.separator + "options.txt");
    }

    public static String Kz() {
        return UtilsFile.getSdCardPath() + File.separator + "games" + File.separator + "com.mojang" + File.separator + "minecraftpe" + File.separator + "options.txt";
    }

    public static boolean eT(String zipPath) {
        return isExist(zipPath);
    }

    public static void eU(String path) {
        UtilsFile.mkdir(path);
        String str = "abcdef0123456789";
        for (int i = 0; i < str.length(); i++) {
            UtilsFile.mkdir(path + File.separator + str.charAt(i) + File.separator);
        }
    }

    public static String eV(String filePath) {
        if (filePath == null) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static String eW(String urlString) {
        String filePath = UtilsFile.KS() + UtilsFile.eV(urlString);
        return filePath == null ? "" : filePath + ".ht";
    }

    public static String eX(String urlString) {
        String fName = UtilsFile.eV(urlString);
        if (fName.length() <= 0) {
            fName = "a_isRightVersion";
        }
        fName = UtilsMD5.MD5Code(fName);
        String filePath = (UtilsFile.KR() + fName.substring(0, 1).toLowerCase(Locale.getDefault())) + File.separator + fName;
        return filePath == null ? "" : filePath + ".wj";
    }

    public static String eY(String urlString) {
        String filePath = UtilsFile.getVoiceCachePath() + UtilsFile.eV(urlString);
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
        return UtilsFile.getTempPath() + System.currentTimeMillis() + ".ht";
    }

    public static String fa(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String type = fq(url);
        if (fu(type)) {
            return UtilsFile.fO(url);
        }
        if (ft(type)) {
            return UtilsFile.fQ(url);
        }
        return UtilsFile.fO(url);
    }

    public static String fb(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = h(url, false);
        String subDir = UtilsFile.get_mctool_path() + "DownFile";
        if (!bkd) {
            bkd = true;
            UtilsFile.mkdir(subDir);
        }
        return subDir + File.separator + fName;
    }

    public static String fc(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = h(url, true);
        String subDir = UtilsFile.get_mctool_path() + "DownApk";
        if (!bke) {
            bke = true;
            UtilsFile.mkdir(subDir);
        }
        return subDir + File.separator + fName;
    }

    public static String fd(String url) {
        if (url == null || url.length() == 0) {
            return null;
        }
        String fName = h(url, true);
        String subDir = UtilsFile.get_mctool_path() + "DownZip";
        if (!bkf) {
            bkf = true;
            UtilsFile.mkdir(subDir);
        }
        return subDir + File.separator + fName;
    }

    public static String KA() {
        String subDir = UtilsFile.get_mctool_path() + "Installer";
        if (!bkg) {
            bkg = true;
            UtilsFile.mkdir(subDir);
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
        UtilsFile.mkdir(fx(dPath));
    }

    public static void fj(String dPath) {
        UtilsFile.mkdir(dPath);
    }

    /**
     * 获取sdcard 可用空间
     * @param path sdcard的根目录
     * @return
     */
    public static long getSdcardAvailableSize(String path) {
        StatFs fileStats = getStatFs(path);
        if (fileStats == null) {
            return 0;
        }
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
        return a(getStatFs(KD()));
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

    public static int f(String packName, int verCodeBegin, int verCodeEnd) {
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
        if (pi.versionCode < verCodeEnd) {
            return 1;
        }
        return 0;
    }

    public static int KH() {
        String packName = "com.mojang.minecraftpe";
        if (packName == null || packName.length() == 0) {
            return -1;
        }
        PackageInfo pi;
        try {
            pi = HTApplication.getAppContext().getPackageManager().getPackageInfo(packName, 0);
        } catch (NameNotFoundException e) {
            pi = null;
        }
        if (pi != null) {
            return pi.versionCode;
        }
        return -1;
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

    public static String fn(String fileName) {
        String strIconPath = fo("icon");
        UtilsFile.mkdir(strIconPath);
        if (fileName == null || fileName.length() <= 0) {
            return strIconPath;
        }
        return strIconPath + File.separator + fileName;
    }

    public static String fo(String fileName) {
        if (!UtilsFile.CT()) {
            return null;
        }
        String strDataPath = Environment.getExternalStorageDirectory().toString() + File.separator + "pictures";
        UtilsFile.mkdir(strDataPath);
        strDataPath = strDataPath + File.separator + "hlx_jietu";
        UtilsFile.mkdir(strDataPath);
        if (fileName == null || fileName.length() <= 0) {
            return strDataPath;
        }
        return strDataPath + File.separator + fileName;
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

    public static List<String> fw(String path) {
        File[] files = new File(path).listFiles();
        ArrayList<String> fileNames = new ArrayList();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }

    public static boolean af(String srcFileName, String destDirName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
    }

    public static boolean ag(String srcDirName, String destDirName) {
        int i = 0;
        File srcDir = new File(srcDirName);
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }
        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File[] sourceFiles = srcDir.listFiles();
        int length = sourceFiles.length;
        while (i < length) {
            File sourceFile = sourceFiles[i];
            if (sourceFile.isFile()) {
                af(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
            } else if (sourceFile.isDirectory()) {
                ag(sourceFile.getAbsolutePath(), destDir.getAbsolutePath() + File.separator + sourceFile.getName());
            }
            i++;
        }
        return srcDir.delete();
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

    public static long w(File file) {
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                long size = (long) fis.available();
                fis.close();
                return size;
            }
            file.createNewFile();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getFileSizes(File f) {
        long size = 0;
        try {
            File[] flist = f.listFiles();
            if (flist == null) {
                return 0;
            }
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size += getFileSizes(flist[i]);
                } else {
                    size += w(flist[i]);
                }
            }
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String fx(String dPath) {
        return UtilsFile.getSdCardPath() + File.separator + dPath + File.separator;
    }

    public static String getDownloadPath() {
        return UtilsFile.getSdCardPath() + File.separator + "Download" + File.separator;
    }

    public static String KI() {
        return UtilsFile.get_mctool_path() + "Log" + File.separator;
    }

    public static String i(String path, boolean containsSuffix) {
        if (path == null) {
            return null;
        }
        String _temp = path;
        while (_temp.contains(File.separator)) {
            _temp = _temp.substring(_temp.indexOf(File.separator) + 1);
        }
        if (containsSuffix || !_temp.contains(".")) {
            return _temp;
        }
        return _temp.substring(0, _temp.lastIndexOf(46));
    }
}
