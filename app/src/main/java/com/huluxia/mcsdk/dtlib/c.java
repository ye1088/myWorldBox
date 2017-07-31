package com.huluxia.mcsdk.dtlib;

import android.os.Environment;
import com.huluxia.mcgame.h;
import com.mojang.minecraftpe.MainActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import org.apache.http.util.EncodingUtils;

/* compiled from: DTFileOper */
public class c {
    public static final String aoA = "storage/sdcard";
    public static final String aoz = "storage/emulated/";

    public static boolean CT() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String dO(String strSrc) {
        if (Pattern.compile("/?storage/emulated/\\d{1,2}").matcher(strSrc).find()) {
            return strSrc.replace("storage/emulated/", "storage/sdcard");
        }
        return strSrc;
    }

    public static String CU() {
        if (CT() && Environment.getExternalStorageDirectory().canWrite()) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return MainActivity.getAppContext().getFilesDir().getPath();
    }

    public static String getRootPath() {
        return CU() + File.separator + "mctool" + File.separator;
    }

    public static void dP(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void dQ(String path) {
        File localFile = new File(path);
        if (!localFile.exists()) {
            try {
                if (localFile.createNewFile()) {
                    localFile.setReadable(true);
                    localFile.setWritable(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        int bytesum = 0;
        try {
            if (new File(oldPath).exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while (true) {
                    int byteread = inStream.read(buffer);
                    if (byteread != -1) {
                        bytesum += byteread;
                        System.out.println(bytesum);
                        fs.write(buffer, 0, byteread);
                    } else {
                        fs.flush();
                        fs.flush();
                        inStream.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    public static String dR(String fileName) throws IOException {
        String res = "";
        FileInputStream fis = new FileInputStream(new File(fileName));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        res = EncodingUtils.getString(buffer, "UTF-8");
        fis.close();
        return res;
    }

    public void I(String fileName, String write_str) throws IOException {
        String res = "";
        FileOutputStream fos = new FileOutputStream(new File(fileName));
        fos.write(write_str.getBytes());
        fos.close();
    }

    public static String J(String filePath, String encoding) {
        String retFileData = "";
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                while (true) {
                    String lineTxt = bufferedReader.readLine();
                    if (lineTxt != null) {
                        retFileData = (retFileData + lineTxt) + "\r\n";
                    } else {
                        read.close();
                        return retFileData;
                    }
                }
            }
            System.out.println("找不到指定的文件");
            return null;
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String sPath) {
        File file = new File(sPath);
        if (!file.isFile() || !file.exists()) {
            return false;
        }
        file.delete();
        return true;
    }

    public static boolean dS(String sPath) {
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isFile()) {
                flag = dT(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (flag) {
            return true;
        }
        return false;
    }

    public static boolean dT(String sPath) {
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isFile()) {
                flag = dT(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (flag && dirFile.delete()) {
            return true;
        }
        return false;
    }

    public static boolean dU(String sPath) {
        File file = new File(sPath);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return deleteFile(sPath);
        }
        return dT(sPath);
    }

    public static String getFileMD5(String fileName) {
        try {
            return h.getFileMD5(new File(fileName));
        } catch (Exception e) {
            return null;
        }
    }
}
