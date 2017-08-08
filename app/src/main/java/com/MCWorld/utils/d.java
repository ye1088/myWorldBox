package com.MCWorld.utils;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.z;
import android.util.Log;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.video.recorder.a;
import com.tencent.mm.sdk.platformtools.Util;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: FileUtil */
public class d {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String bjX = (File.separator + AppConfig.getInstance().getRootPath());
    public static final String bjY = (bjX + File.separator + "media-recorder");

    public static boolean Km() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static Uri lb(int type) {
        File file = lc(type);
        if (file == null) {
            return null;
        }
        return Uri.fromFile(file);
    }

    public static File Kn() {
        return new File(Environment.getExternalStorageDirectory() + bjY);
    }

    public static File lc(int type) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + bjY);
        if (mediaStorageDir.exists() || mediaStorageDir.mkdirs()) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            if (type == 1) {
                return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + Util.PHOTO_DEFAULT_EXT);
            }
            if (type == 2) {
                return new File(mediaStorageDir.getPath() + File.separator + a.boU + timeStamp + a.boV);
            }
            return null;
        }
        Log.d("MyCameraApp", "failed to create directory");
        return null;
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    @z
    public static String o(String folder, String subfolder, String uniqueId) {
        File dir = new File(Environment.getExternalStorageDirectory(), folder);
        if (subfolder != null) {
            dir = new File(dir, subfolder);
        }
        if (dir.exists() || dir.mkdirs()) {
            return new File(dir, a.boU + uniqueId + a.boV).getAbsolutePath();
        }
        return null;
    }
}
