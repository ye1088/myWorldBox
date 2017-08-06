package com.huluxia.framework.base.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import com.huluxia.framework.AppConfig;
import com.tencent.mm.sdk.platformtools.Util;
import hlx.data.localstore.a;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.lingala.zip4j.core.c;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.p;

public class UtilsFile {
    private static final String TAG = "UtilsFile";

    public static File getExtStorageRoot(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + File.separator + context.getPackageName());
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        return new File(getSdPath(context) + File.separator + uniqueName);
    }

    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (UtilsVersion.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static File getInternalCacheDir(Context context) {
        return context.getCacheDir();
    }

    public static File getInternalFilesDir(Context context) {
        return context.getFilesDir();
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

    public static String getSdPath(Context context) {
        if (isSdcardMounted() && Environment.getExternalStorageDirectory().canWrite()) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return context.getApplicationContext().getFilesDir().getPath();
    }

    public static boolean isExist(String path) {
        if (UtilsFunction.empty((CharSequence) path)) {
            return false;
        }
        return new File(path).exists();
    }

    public static boolean mkdir(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    public static void deleteFile(String path) {
        if (path != null) {
            deleteFile(new File(path));
        }
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        boolean delete = false;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File deleteFile : files) {
                if (deleteFile(deleteFile) && delete) {
                    delete = true;
                } else {
                    delete = false;
                }
            }
        }
        if (file.delete() && delete) {
            delete = true;
        } else {
            delete = false;
        }
        return delete;
    }

    public static long fileSize(String path) {
        if (path == null) {
            return 0;
        }
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

    @TargetApi(9)
    public static long availableSpace(String path) {
        if (UtilsVersion.hasGingerbread()) {
            return new File(path).getUsableSpace();
        }
        return calculateSize(getStatFs(path));
    }

    public static long totalSpace(String path) {
        return calculateTotalSize(getStatFs(path));
    }

    private static StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long calculateSize(StatFs stat) {
        if (stat != null) {
            return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
        }
        return 0;
    }

    public static long calculateTotalSize(StatFs stat) {
        if (stat != null) {
            return ((long) stat.getBlockCount()) * ((long) stat.getBlockSize());
        }
        return 0;
    }

    public static boolean isSdcardMounted() {
        return "mounted".equals(Environment.getExternalStorageState());
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

    public static long getFileSizes(File directory) {
        long size = 0;
        File[] flist = directory.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size += getFileSizes(flist[i]);
            } else {
                size += fileSize(flist[i].getAbsolutePath());
            }
        }
        return size;
    }

    public static long getSize(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return 0;
        }
        if (file.isDirectory()) {
            return getFileSizes(file);
        }
        return fileSize(file.getAbsolutePath());
    }

    public static long getSize(File file) {
        if (file.isDirectory()) {
            return getFileSizes(file);
        }
        return fileSize(file.getAbsolutePath());
    }

    static byte[] readFile(InputStream in, long expectedSize) throws IOException {
        if (expectedSize > 2147483647L) {
            throw new OutOfMemoryError("file is too large to fit in a_isRightVersion byte array: " + expectedSize + " bytes");
        } else if (expectedSize == 0) {
            return ByteStreams.toByteArray(in);
        } else {
            return ByteStreams.toByteArray(in, (int) expectedSize);
        }
    }

    public static byte[] toByteArray(File file) throws IOException {
        Throwable th;
        FileInputStream in = null;
        try {
            FileInputStream in2 = new FileInputStream(file);
            try {
                byte[] readFile = readFile(in2, in2.getChannel().size());
                if (in2 != null) {
                    in2.close();
                }
                return readFile;
            } catch (Throwable th2) {
                th = th2;
                in = in2;
                if (in != null) {
                    in.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (in != null) {
                in.close();
            }
            throw th;
        }
    }

    public static void saveBytesToSD(String filePath, byte[] data) {
        IOException e;
        Throwable th;
        if (data != null) {
            int index = filePath.lastIndexOf(File.separator);
            if (index > 0) {
                mkdir(filePath.substring(0, index));
            }
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

    public static void appendToFile(String filePath, String content) {
        Exception e;
        Throwable th;
        if (content != null) {
            int index = filePath.lastIndexOf(File.separator);
            if (index > 0) {
                mkdir(filePath.substring(0, index));
            }
            BufferedWriter out = null;
            try {
                BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
                try {
                    out2.write(content);
                    out2.write("\r\n");
                    if (out2 != null) {
                        try {
                            out2.close();
                            return;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            out = out2;
                            return;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    out = out2;
                    try {
                        e.printStackTrace();
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    out = out2;
                    if (out != null) {
                        out.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                if (out != null) {
                    out.close();
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

    public static String getTempFileName() {
        return getTempPath() + System.currentTimeMillis() + ".ht";
    }

    public static String getTempFileName(int index) {
        return getTempPath() + System.currentTimeMillis() + "_" + index + ".ht";
    }

    public static String getTempPath() {
        File dir = new File(AppConfig.getInstance().getRootDir().getAbsolutePath() + File.separator + "Temp");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath() + File.separator;
    }

    public static String cutSuffix(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static boolean isPicture(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(a.bKa) || fileName.endsWith(Util.PHOTO_DEFAULT_EXT) || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp") || fileName.endsWith(".gif")) {
            return true;
        }
        return false;
    }

    public static boolean isAudio(String suffix) {
        if (suffix.equalsIgnoreCase("mp3")) {
            return true;
        }
        return false;
    }

    public static boolean isAudio(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".mp3") || fileName.endsWith(".ogg") || fileName.endsWith(".ape")) {
            return true;
        }
        return false;
    }

    public static boolean isVideo(String suffix) {
        if (suffix.equalsIgnoreCase("mp4") || suffix.equalsIgnoreCase("avi") || suffix.equalsIgnoreCase("wmv") || suffix.equalsIgnoreCase("mkv") || suffix.equalsIgnoreCase("mpg") || suffix.equals("mpeg") || suffix.equalsIgnoreCase("rm") || suffix.equalsIgnoreCase("rmvb") || suffix.equalsIgnoreCase("3gp") || suffix.equalsIgnoreCase("mov") || suffix.equalsIgnoreCase("flv") || suffix.equalsIgnoreCase("srt")) {
            return true;
        }
        return false;
    }

    public static boolean isVideo(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(com.huluxia.video.recorder.a.boV) || fileName.endsWith(".avi") || fileName.endsWith(".wmv") || fileName.endsWith(".mkv") || fileName.endsWith(".mpg") || fileName.endsWith(".mpeg") || fileName.endsWith(".rmvb") || fileName.endsWith(".rm") || fileName.endsWith(".3gp") || fileName.endsWith(".mov") || fileName.endsWith(".flv") || fileName.endsWith(".srt")) {
            return true;
        }
        return false;
    }

    public static boolean isInstallPackage(String suffix) {
        if (suffix.equalsIgnoreCase("apk") || suffix.equalsIgnoreCase("hpk")) {
            return true;
        }
        return false;
    }

    public static boolean isInstallPackage(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".apk") || fileName.endsWith(".hpk")) {
            return true;
        }
        return false;
    }

    public static boolean isCompressPackage(String suffix) {
        if (suffix.equalsIgnoreCase("zip") || suffix.equalsIgnoreCase("rar") || suffix.equalsIgnoreCase("iso") || suffix.equalsIgnoreCase("cso") || suffix.equalsIgnoreCase("7z") || suffix.equalsIgnoreCase("gz")) {
            return true;
        }
        return false;
    }

    public static boolean isCompressPackage(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".zip") || fileName.endsWith(".rar") || fileName.endsWith(".iso") || fileName.endsWith(".cso") || fileName.endsWith(".7z") || fileName.endsWith(".gz")) {
            return true;
        }
        return false;
    }

    public static boolean isDocument(String suffix) {
        if (suffix.equalsIgnoreCase("doc") || suffix.equalsIgnoreCase("docx") || suffix.equalsIgnoreCase("xls") || suffix.equalsIgnoreCase("ppt") || suffix.equalsIgnoreCase("wps") || suffix.equalsIgnoreCase("rtf")) {
            return true;
        }
        return false;
    }

    public static boolean isDocument(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".xls") || fileName.endsWith(".ppt") || fileName.endsWith(".wps") || fileName.endsWith(".rtf")) {
            return true;
        }
        return false;
    }

    public static boolean isEbook(String suffix) {
        if (suffix.equalsIgnoreCase("txt") || suffix.equalsIgnoreCase("pdf") || suffix.equalsIgnoreCase("umd") || suffix.equalsIgnoreCase("ebk") || suffix.equalsIgnoreCase("chm")) {
            return true;
        }
        return false;
    }

    public static boolean isEbook(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".txt") || fileName.endsWith(".pdf") || fileName.endsWith(".umd") || fileName.endsWith(".ebk") || fileName.endsWith(".chm")) {
            return true;
        }
        return false;
    }

    public static boolean isEmulator(File file) {
        String fileName = file.getName().toLowerCase();
        String path = file.getAbsolutePath();
        if (fileName.endsWith(".gba") || fileName.endsWith(".gbc") || fileName.endsWith("nds") || fileName.endsWith("nes") || fileName.endsWith("sfc") || fileName.endsWith("smd") || fileName.endsWith("n64") || fileName.endsWith("ngp") || path.contains("/MAME4all/roms") || path.contains("/MAME4droid/roms") || path.contains("/Arcade")) {
            return true;
        }
        return false;
    }

    public static String getObbPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android" + File.separator + "obb";
    }

    public static String getDataPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android" + File.separator + "data";
    }

    public static boolean renameFile(File fromDir, String fromPath, String toName) {
        File tempFile = new File(fromPath + File.separator + toName);
        if (tempFile.exists()) {
            return false;
        }
        return fromDir.renameTo(tempFile);
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

    public static void copyFile(String oldPath, String newPath) {
        int bytesum = 0;
        try {
            if (new File(oldPath).exists()) {
                FileInputStream inStream = new FileInputStream(oldPath);
                FileOutputStream outStream = new FileOutputStream(newPath);
                byte[] buffer = new byte[5120];
                while (true) {
                    int byteread = inStream.read(buffer);
                    if (byteread != -1) {
                        bytesum += byteread;
                        Log.v("", "copy file old path = " + oldPath + ", newpath = " + newPath + ", byte = " + bytesum);
                        outStream.write(buffer, 0, byteread);
                    } else {
                        outStream.flush();
                        inStream.close();
                        outStream.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("", "copy file error, oldpath = " + oldPath + ", newpath = " + newPath, e);
        }
    }

    public static net.lingala.zip4j.progress.a zipFiles(ArrayList<String> srcFilePaths, String destinationFilePath) throws ZipException {
        return zipFiles(srcFilePaths, destinationFilePath, null);
    }

    public static net.lingala.zip4j.progress.a zipFiles(ArrayList<String> srcFilePaths, String destinationFilePath, String password) throws ZipException {
        if (UtilsFunction.empty((Collection) srcFilePaths)) {
            return null;
        }
        p parameters = new p();
        parameters.od(8);
        parameters.setCompressionLevel(5);
        if (password.length() > 0) {
            parameters.eg(true);
            parameters.ox(99);
            parameters.oB(3);
            parameters.setPassword(password);
        }
        if (!UtilsFunction.empty((CharSequence) password)) {
            parameters.eg(true);
            parameters.ox(99);
            parameters.oB(3);
            parameters.setPassword(password);
        }
        c zipFile = new c(destinationFilePath);
        zipFile.b(srcFilePaths, parameters);
        return zipFile.Ws();
    }

    public static net.lingala.zip4j.progress.a zipFileAndFolder(ArrayList<String> srcFilePaths, String destinationFilePath) throws ZipException {
        return zipFileAndFolder(srcFilePaths, destinationFilePath, null);
    }

    public static net.lingala.zip4j.progress.a zipFileAndFolder(ArrayList<String> srcFilePaths, String destinationFilePath, String password) throws ZipException {
        if (UtilsFunction.empty((Collection) srcFilePaths)) {
            return null;
        }
        p parameters = new p();
        parameters.od(8);
        parameters.setCompressionLevel(5);
        if (!UtilsFunction.empty((CharSequence) password)) {
            parameters.eg(true);
            parameters.ox(99);
            parameters.oB(3);
            parameters.setPassword(password);
        }
        c zipFile = new c(destinationFilePath);
        Iterator it = srcFilePaths.iterator();
        while (it.hasNext()) {
            File targetFile = new File((String) it.next());
            if (targetFile.isFile()) {
                zipFile.b(targetFile, parameters);
            } else if (targetFile.isDirectory()) {
                zipFile.c(targetFile, parameters);
            }
        }
        return zipFile.Ws();
    }

    public static net.lingala.zip4j.progress.a zip(String targetFolderPath, String destinationFilePath, String password) throws ZipException {
        p parameters = new p();
        parameters.od(8);
        parameters.setCompressionLevel(5);
        if (password.length() > 0) {
            parameters.eg(true);
            parameters.ox(99);
            parameters.oB(3);
            parameters.setPassword(password);
        }
        c zipFile = new c(destinationFilePath);
        File targetFile = new File(targetFolderPath);
        if (targetFile.isFile()) {
            zipFile.b(targetFile, parameters);
        } else if (targetFile.isDirectory()) {
            zipFile.c(targetFile, parameters);
        }
        return zipFile.Ws();
    }

    public static void unzip(String targetZipFilePath, String destinationFolderPath, String password) {
        try {
            c zipFile = new c(targetZipFilePath);
            if (zipFile.Wm()) {
                zipFile.setPassword(password);
            }
            zipFile.hS(destinationFolderPath);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void mediaScan(Context ctx, String filepath) {
        if (filepath != null) {
            File file = new File(filepath);
            if (file != null && file.exists()) {
                MediaScannerConnection.scanFile(ctx, new String[]{file.getAbsolutePath()}, null, null);
            }
        }
    }
}
