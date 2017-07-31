package com.huluxia.utils;

import com.huluxia.framework.base.image.Config;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.image.base.imageutils.b;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.apache.tools.tar.TarConstants;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avcodec.AVCodecContext;
import org.mozilla.classfile.ByteCode;

/* compiled from: UtilsZipper */
public class bc {

    /* compiled from: UtilsZipper */
    public interface a {
        void OnProgessCallback(long j, long j2);
    }

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

    private static void o(byte[] byteStream, int length) {
        int[] dict = new int[]{0, 1, 57, 78, 104, 130, 6, 182, 8, 164, 10, 11, 157, 153, 14, 150, 86, 22, 98, 124, 15, 136, 72, 23, 69, 65, 26, 27, 58, 29, 40, 31, 92, 118, 144, 165, 36, 37, 38, 94, 115, 141, 167, 43, 34, 45, 16, 47, 48, 9, 5, 51, 2, 123, 59, 135, 161, 52, 173, 109, 60, 106, 102, 63, 64, 95, 66, 77, 68, 129, 155, 181, 17, 73, 74, 75, 131, 152, 178, 19, 80, 71, 82, 53, 84, 85, 46, 42, 88, 39, 160, 96, 172, 13, 89, 25, 146, 97, 143, 139, 100, 101, 132, 103, 114, 105, 166, 7, 33, 54, 110, 111, 112, 168, 4, 30, 56, 117, 108, 119, 90, 121, 122, 83, 79, 125, 76, 12, 133, 24, 50, 126, 62, 183, 134, 180, 176, 137, 138, 169, 140, 151, 142, 18, 44, 70, 91, 147, 148, 149, 20, 41, 67, 93, 154, 145, 156, 127, 158, 159, 120, 116, 162, 113, 49, 170, 61, 87, 163, 99, 35, 171, 32, 28, 174, 175, 21, 177, 3, 179, 55, 81, 107, 128, 184, 185, avcodec.AV_CODEC_ID_HQX, 187, 188, ByteCode.ANEWARRAY, ByteCode.ARRAYLENGTH, ByteCode.ATHROW, 192, ByteCode.INSTANCEOF, ByteCode.MONITORENTER, ByteCode.MONITOREXIT, ByteCode.WIDE, ByteCode.MULTIANEWARRAY, ByteCode.IFNULL, ByteCode.IFNONNULL, 200, 201, 202, 203, 204, 205, 206, 207, 208, ErrorCode.DEXOPT_EXCEPTION, ErrorCode.ROM_NOT_ENOUGH, ErrorCode.EXCEED_COPY_RETRY_NUM, ErrorCode.COPY_FAIL, ErrorCode.COPY_SRCDIR_ERROR, ErrorCode.COPY_TMPDIR_ERROR, 215, 216, 217, 218, ErrorCode.RENAME_EXCEPTION, ErrorCode.COPY_INSTALL_SUCCESS, ErrorCode.INCRUPDATE_INSTALL_SUCCESS, 222, 223, 224, b.xB, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, com.huluxia.video.recorder.b.bpd, 241, 242, 243, AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE, 245, 246, 247, 248, 249, Config.DEFAULT_FADE_DURATION, 251, 252, 253, 254, 255};
        for (int i = 0; i < length; i++) {
            byteStream[i] = (byte) dict[byteStream[i] & 255];
        }
    }

    public static void gB(String zipFilePath) throws Exception {
        byte[] data = new byte[4194304];
        byte[] encryptHeader = new byte[]{(byte) 104, (byte) 108, TarConstants.LF_PAX_EXTENDED_HEADER_LC, (byte) 122};
        BufferedOutputStream zos = new BufferedOutputStream(new FileOutputStream(zipFilePath + ".encrypt.tmp"));
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFilePath));
        boolean bFirst = true;
        while (true) {
            int count = bis.read(data, 0, data.length);
            if (count != -1) {
                o(data, count);
                if (bFirst) {
                    data[0] = encryptHeader[0];
                    data[1] = encryptHeader[1];
                    data[2] = encryptHeader[2];
                    data[3] = encryptHeader[3];
                    bFirst = false;
                }
                zos.write(data, 0, count);
                zos.flush();
            } else {
                bis.close();
                zos.close();
                File f = new File(zipFilePath);
                File fbak = new File(zipFilePath + ".orig.bak");
                f.renameTo(fbak);
                new File(zipFilePath + ".encrypt.tmp").renameTo(new File(zipFilePath));
                fbak.delete();
                return;
            }
        }
    }

    public static void c(String srcPath, String zipPath, String zipFileName, boolean keepCurrentSrcAsRoot) throws Exception {
        if (ad.empty(srcPath) || ad.empty(zipPath) || ad.empty(zipFileName)) {
            throw new IllegalArgumentException("zip file param is INVALID");
        }
        b(srcPath, zipPath, zipFileName, keepCurrentSrcAsRoot);
        gB(zipPath + File.separator + zipFileName);
    }

    public static int gC(String srcPath) throws Exception {
        FileInputStream in = new FileInputStream(srcPath);
        byte[] header = new byte[4];
        if (in.read(header, 0, 4) != 4) {
            in.close();
            throw new Exception("header read error");
        }
        in.close();
        byte[] zipHeader = new byte[]{(byte) 80, TarConstants.LF_GNUTYPE_LONGLINK, (byte) 3, (byte) 4};
        byte[] encryptHeader = new byte[]{(byte) 104, (byte) 108, TarConstants.LF_PAX_EXTENDED_HEADER_LC, (byte) 122};
        if (header[0] == encryptHeader[0] && header[1] == encryptHeader[1] && header[2] == encryptHeader[2] && header[3] == encryptHeader[3]) {
            return 1;
        }
        if (header[0] == zipHeader[0] && header[1] == zipHeader[1] && header[2] == zipHeader[2] && header[3] == zipHeader[3]) {
            return 0;
        }
        return -1;
    }

    private static void p(byte[] byteStream, int length) {
        int[] dict = new int[]{0, 1, 52, 178, 114, 50, 6, 107, 8, 49, 10, 11, 127, 93, 14, 20, 46, 72, 143, 79, 150, 176, 17, 23, 129, 95, 26, 27, 173, 29, 115, 31, 172, 108, 44, 170, 36, 37, 38, 89, 30, 151, 87, 43, 144, 45, 86, 47, 48, 164, 130, 51, 57, 83, 109, 180, 116, 2, 28, 54, 60, 166, 132, 63, 64, 25, 66, 152, 68, 24, 145, 81, 22, 73, 74, 75, 126, 67, 3, 124, 80, 181, 82, 123, 84, 85, 16, 167, 88, 94, 120, 146, 32, 153, 39, 65, 91, 97, 18, 169, 100, 101, 62, 103, 4, 105, 61, 182, 118, 59, 110, 111, 112, 163, 104, 40, 161, 117, 33, 119, 160, 121, 122, 53, 19, 125, 131, 157, 183, 69, 5, 76, 102, 128, 134, 55, 21, 137, 138, 99, 140, 41, 142, 98, 34, 155, 96, 147, 148, 149, 15, 141, 77, 13, 154, 70, 156, 12, 158, 159, 90, 56, 162, 168, 9, 35, 106, 42, 113, 139, 165, 171, 92, 58, 174, 175, 136, 177, 78, 179, 135, 71, 7, 133, 184, 185, avcodec.AV_CODEC_ID_HQX, 187, 188, ByteCode.ANEWARRAY, ByteCode.ARRAYLENGTH, ByteCode.ATHROW, 192, ByteCode.INSTANCEOF, ByteCode.MONITORENTER, ByteCode.MONITOREXIT, ByteCode.WIDE, ByteCode.MULTIANEWARRAY, ByteCode.IFNULL, ByteCode.IFNONNULL, 200, 201, 202, 203, 204, 205, 206, 207, 208, ErrorCode.DEXOPT_EXCEPTION, ErrorCode.ROM_NOT_ENOUGH, ErrorCode.EXCEED_COPY_RETRY_NUM, ErrorCode.COPY_FAIL, ErrorCode.COPY_SRCDIR_ERROR, ErrorCode.COPY_TMPDIR_ERROR, 215, 216, 217, 218, ErrorCode.RENAME_EXCEPTION, ErrorCode.COPY_INSTALL_SUCCESS, ErrorCode.INCRUPDATE_INSTALL_SUCCESS, 222, 223, 224, b.xB, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, com.huluxia.video.recorder.b.bpd, 241, 242, 243, AVCodecContext.FF_PROFILE_H264_HIGH_444_PREDICTIVE, 245, 246, 247, 248, 249, Config.DEFAULT_FADE_DURATION, 251, 252, 253, 254, 255};
        for (int i = 0; i < length; i++) {
            byteStream[i] = (byte) dict[byteStream[i] & 255];
        }
    }

    public static void av(String srcPath, String dstPath) throws Exception {
        byte[] data = new byte[4194304];
        byte[] zipHeader = new byte[]{(byte) 80, TarConstants.LF_GNUTYPE_LONGLINK, (byte) 3, (byte) 4};
        BufferedOutputStream zos = new BufferedOutputStream(new FileOutputStream(dstPath));
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcPath));
        boolean bFirst = true;
        while (true) {
            int count = bis.read(data, 0, data.length);
            if (count != -1) {
                p(data, count);
                if (bFirst) {
                    data[0] = zipHeader[0];
                    data[1] = zipHeader[1];
                    data[2] = zipHeader[2];
                    data[3] = zipHeader[3];
                    bFirst = false;
                }
                zos.write(data, 0, count);
                zos.flush();
            } else {
                bis.close();
                zos.close();
                return;
            }
        }
    }

    public static void a(String zipFilePath, String unzipFilePath, a zcb) throws Exception {
        if (zipFilePath.isEmpty() || unzipFilePath.isEmpty()) {
            throw new IllegalArgumentException("zip file param is INVALID");
        }
        if (gC(zipFilePath) == 1) {
            String dstFilePath = zipFilePath + ".decrypt.tmp";
            av(zipFilePath, dstFilePath);
            File f = new File(zipFilePath);
            File fbak = new File(zipFilePath + ".orig.bak.xxx");
            f.renameTo(fbak);
            new File(dstFilePath).renameTo(new File(zipFilePath));
            fbak.delete();
        }
        ZipFile zipFile = new ZipFile(zipFilePath, "GBK");
        long total_size = 0;
        long progress = 0;
        Enumeration e = zipFile.getEntries();
        while (e.hasMoreElements()) {
            ZipEntry ze2 = (ZipEntry) e.nextElement();
            if (!ze2.isDirectory()) {
                total_size += ze2.getSize();
            }
        }
        e = zipFile.getEntries();
        while (e.hasMoreElements()) {
            ze2 = (ZipEntry) e.nextElement();
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
                BufferedInputStream bi = new BufferedInputStream(zipFile.getInputStream(ze2));
                byte[] readContent = new byte[1024];
                for (int readCount = bi.read(readContent); readCount != -1; readCount = bi.read(readContent)) {
                    bos.write(readContent, 0, readCount);
                    progress += (long) readCount;
                    if (zcb != null) {
                        zcb.OnProgessCallback(progress, total_size);
                    }
                }
                bos.close();
            }
        }
        zipFile.close();
    }
}
