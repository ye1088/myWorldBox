package com.huluxia.framework.base.utils;

import com.huluxia.framework.base.log.HLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class UtilsCompressor {
    private static final String TAG = "UtilsCompressor";

    public static boolean compressFile(File file, String target) {
        List files = new ArrayList();
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return false;
            }
            for (Object add : listFiles) {
                files.add(add);
            }
        } else {
            files.add(file);
        }
        return compressFile(files, target);
    }

    public static boolean compressFile(List<File> files, String target) {
        Exception ex;
        FileOutputStream fileOutputStream;
        Throwable th;
        if (files == null || files.size() <= 0) {
            return false;
        }
        ZipOutputStream zos = null;
        byte[] buffer = new byte[1024];
        try {
            File zipFile = new File(target);
            if (zipFile.exists()) {
                zipFile.delete();
            }
            zipFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(zipFile.getAbsolutePath());
            try {
                ZipOutputStream zos2 = new ZipOutputStream(fos);
                try {
                    for (File file : files) {
                        if (file != null && file.exists()) {
                            zos2.putNextEntry(new ZipEntry(file.getName()));
                            FileInputStream in = new FileInputStream(file);
                            while (true) {
                                int len = in.read(buffer);
                                if (len <= 0) {
                                    break;
                                }
                                zos2.write(buffer, 0, len);
                            }
                            in.close();
                        }
                    }
                    if (zos2 != null) {
                        try {
                            zos2.flush();
                            zos2.closeEntry();
                            zos2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                } catch (Exception e2) {
                    ex = e2;
                    zos = zos2;
                    fileOutputStream = fos;
                } catch (Throwable th2) {
                    th = th2;
                    zos = zos2;
                    fileOutputStream = fos;
                }
            } catch (Exception e3) {
                ex = e3;
                fileOutputStream = fos;
                try {
                    HLog.error(TAG, "compress logs file error = " + ex.getMessage(), new Object[0]);
                    if (zos != null) {
                        return false;
                    }
                    try {
                        zos.flush();
                        zos.closeEntry();
                        zos.close();
                        return false;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        return false;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (zos != null) {
                        try {
                            zos.flush();
                            zos.closeEntry();
                            zos.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = fos;
                if (zos != null) {
                    zos.flush();
                    zos.closeEntry();
                    zos.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            ex = e5;
            HLog.error(TAG, "compress logs file error = " + ex.getMessage(), new Object[0]);
            if (zos != null) {
                return false;
            }
            zos.flush();
            zos.closeEntry();
            zos.close();
            return false;
        }
    }
}
