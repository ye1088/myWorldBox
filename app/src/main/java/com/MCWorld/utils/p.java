package com.MCWorld.utils;

import com.MCWorld.framework.base.log.HLog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/* compiled from: UtilsAntZipper */
public class p {
    private static void a(String srcRootDir, String specificFile, File file, ZipOutputStream zos) throws Exception {
        if (file != null) {
            if (file.isFile()) {
                byte[] data = new byte[1024];
                String subPath = file.getAbsolutePath();
                if (subPath.indexOf(srcRootDir) != -1) {
                    subPath = subPath.substring((srcRootDir.length() + File.separator.length()) - 1);
                }
                zos.putNextEntry(new ZipEntry(subPath));
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                while (true) {
                    int count = bis.read(data, 0, 1024);
                    if (count != -1) {
                        zos.write(data, 0, count);
                    } else {
                        bis.close();
                        zos.closeEntry();
                        return;
                    }
                }
            }
            File[] childFileList = file.listFiles();
            for (int n = 0; n < childFileList.length; n++) {
                if (specificFile == null) {
                    a(srcRootDir, null, childFileList[n], zos);
                } else if (childFileList[n].getAbsolutePath().equals(specificFile)) {
                    a(srcRootDir, null, childFileList[n], zos);
                } else {
                    HLog.verbose("", "ignore file = " + childFileList[n].getAbsolutePath(), new Object[0]);
                }
            }
        }
    }

    public static String b(String srcPath, String zipPath, String zipFileName, boolean keepCurrentSrcAsRoot) throws Exception {
        Exception e;
        String zipFilePath = null;
        if (ad.empty(srcPath) || ad.empty(zipPath) || ad.empty(zipFileName)) {
            throw new IllegalArgumentException("zip file param is INVALID");
        }
        ZipOutputStream zos = null;
        try {
            File srcFile = new File(srcPath);
            srcPath = srcFile.getAbsolutePath();
            if (!srcFile.isDirectory() || zipPath.indexOf(srcPath) == -1) {
                ZipOutputStream zos2;
                String srcRootDir;
                CheckedOutputStream checkedOutputStream;
                File zipDir = new File(zipPath);
                if (!(zipDir.exists() && zipDir.isDirectory())) {
                    zipDir.mkdirs();
                }
                zipFilePath = zipDir.getAbsolutePath() + File.separator + zipFileName;
                File zipFile = new File(zipFilePath);
                if (zipFile.exists()) {
                    new SecurityManager().checkDelete(zipFilePath);
                    zipFile.delete();
                }
                CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
                try {
                    zos2 = new ZipOutputStream(cos);
                    srcRootDir = srcPath;
                } catch (Exception e2) {
                    e = e2;
                    checkedOutputStream = cos;
                    try {
                        throw e;
                    } catch (Throwable th) {
                    }
                } catch (Throwable th2) {
                    checkedOutputStream = cos;
                    if (zos != null) {
                        try {
                            zos.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    return zipFilePath;
                }
                try {
                    if (srcFile.isFile()) {
                        int index = srcPath.lastIndexOf(File.separator);
                        if (index != -1) {
                            srcRootDir = srcPath.substring(0, index);
                        }
                    }
                    if (keepCurrentSrcAsRoot) {
                        a(srcFile.getParent(), srcRootDir, srcFile.getParentFile(), zos2);
                    } else {
                        a(srcRootDir, null, srcFile, zos2);
                    }
                    zos2.flush();
                    if (zos2 != null) {
                        try {
                            zos2.close();
                        } catch (Exception e32) {
                            e32.printStackTrace();
                        }
                    }
                    zos = zos2;
                    checkedOutputStream = cos;
                    return zipFilePath;
                } catch (Exception e4) {
                    e32 = e4;
                    zos = zos2;
                    checkedOutputStream = cos;
                    throw e32;
                } catch (Throwable th3) {
                    zos = zos2;
                    checkedOutputStream = cos;
                    if (zos != null) {
                        zos.close();
                    }
                    return zipFilePath;
                }
            }
            throw new IllegalStateException("zipPath must not be the child directory of srcPath.");
        } catch (Exception e5) {
            e32 = e5;
            throw e32;
        }
    }

    public static void ah(String zipFilePath, String unzipFilePath) throws Exception {
        if (ad.empty(zipFilePath) || ad.empty(unzipFilePath)) {
            throw new IllegalArgumentException("zip file param is INVALID");
        }
        ZipFile zf = new ZipFile(zipFilePath, "GBK");
        Enumeration e = zf.getEntries();
        while (e.hasMoreElements()) {
            ZipEntry ze2 = (ZipEntry) e.nextElement();
            String entryName = ze2.getName();
            String path = unzipFilePath + "/" + entryName;
            if (ze2.isDirectory()) {
                File decompressDirFile = new File(path);
                if (!decompressDirFile.exists()) {
                    decompressDirFile.mkdirs();
                }
            } else {
                File fileDirFile = new File(path.substring(0, path.lastIndexOf("/")));
                if (!fileDirFile.exists()) {
                    fileDirFile.mkdirs();
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath + "/" + entryName));
                BufferedInputStream bi = new BufferedInputStream(zf.getInputStream(ze2));
                byte[] readContent = new byte[1024];
                for (int readCount = bi.read(readContent); readCount != -1; readCount = bi.read(readContent)) {
                    bos.write(readContent, 0, readCount);
                }
                bos.close();
            }
        }
        zf.close();
    }
}
