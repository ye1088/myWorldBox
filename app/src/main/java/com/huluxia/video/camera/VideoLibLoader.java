package com.huluxia.video.camera;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.y;
import android.support.annotation.z;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.DoNotStrip;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.utils.d;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avfilter;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacpp.postproc;
import org.bytedeco.javacpp.swresample;
import org.bytedeco.javacpp.swscale;

@DoNotStrip
public class VideoLibLoader {
    @DoNotStrip
    private static final String KEY_ACFORMAT = "libavformat.so";
    @DoNotStrip
    private static final String KEY_AVCODEC = "libavcodec.so";
    @DoNotStrip
    private static final String KEY_AVFILTER = "libavfilter.so";
    @DoNotStrip
    private static final String KEY_AVUTIL = "libavutil.so";
    @DoNotStrip
    private static final String KEY_JNI_ACFORMAT = "libjniavformat.so";
    @DoNotStrip
    private static final String KEY_JNI_AVCODEC = "libjniavcodec.so";
    @DoNotStrip
    private static final String KEY_JNI_AVFILTER = "libjniavfilter.so";
    @DoNotStrip
    private static final String KEY_JNI_AVUTIL = "libjniavutil.so";
    @DoNotStrip
    private static final String KEY_JNI_POSTPROC = "libjnipostproc.so";
    @DoNotStrip
    private static final String KEY_JNI_SWRESAMPLE = "libjniswresample.so";
    @DoNotStrip
    private static final String KEY_JNI_SWSCALE = "libjniswscale.so";
    @DoNotStrip
    private static final String KEY_POSTPROC = "libpostproc.so";
    @DoNotStrip
    private static final String KEY_SWRESAMPLE = "libswresample.so";
    @DoNotStrip
    private static final String KEY_SWSCALE = "libswscale.so";
    private static final String TAG = "VideoLibLoader";
    private static final String boI = "video-lib.zip";
    private static final String boJ = "armeabi";
    public static final int boK = 0;
    public static final int boL = 1;
    public static final int boM = 2;
    @DoNotStrip
    private static final String[] soFiles = new String[]{KEY_AVUTIL, KEY_POSTPROC, KEY_SWRESAMPLE, KEY_SWSCALE, KEY_AVCODEC, KEY_ACFORMAT, KEY_AVFILTER, KEY_JNI_AVUTIL, KEY_JNI_POSTPROC, KEY_JNI_SWRESAMPLE, KEY_JNI_SWSCALE, KEY_JNI_AVCODEC, KEY_JNI_ACFORMAT, KEY_JNI_AVFILTER};
    private Exception boN;
    private final Handler mHandler;

    public interface a {
        void cw(boolean z);
    }

    private VideoLibLoader() {
        this.boN = null;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public static VideoLibLoader MZ() {
        return b.boT;
    }

    public int gG(@y String url) {
        if (UtilsFunction.empty((CharSequence) url)) {
            return 0;
        }
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(url);
        if (record == null) {
            return 0;
        }
        File soZipFile = new File(record.dir, record.name);
        File dir = UtilsFile.getInternalFilesDir(AppConfig.getInstance().getAppContext());
        boolean allSoFiles = true;
        for (String so : soFiles) {
            File file = new File(dir, so);
            if (allSoFiles && file.exists()) {
                allSoFiles = true;
            } else {
                allSoFiles = false;
            }
            if (!allSoFiles) {
                break;
            }
        }
        if (allSoFiles) {
            return 2;
        }
        if (!soZipFile.exists() || allSoFiles) {
            return 0;
        }
        return 1;
    }

    public void a(@y String url, String md5, @z a callback) {
        if (gG(url) == 2) {
            b(url, md5, callback);
        } else {
            AsyncTaskCenter.getInstance().executeSingleThread(new 1(this, url, md5, callback));
        }
    }

    private void b(@y String url, String md5, @z a callback) {
        if (!UtilsFunction.empty((CharSequence) url)) {
            DownloadRecord record = DownloadMemCache.getInstance().getRecord(url);
            if (record == null) {
                a(url, d.Kn().getAbsolutePath(), boI, md5, callback);
                return;
            }
            File soZipFile = new File(record.dir, record.name);
            File soDir = UtilsFile.getInternalFilesDir(AppConfig.getInstance().getAppContext());
            boolean allSoFiles = true;
            for (String so : soFiles) {
                allSoFiles = allSoFiles && new File(soDir, so).exists();
                if (!allSoFiles) {
                    break;
                }
            }
            if (allSoFiles) {
                try {
                    tryLoad(soDir.getAbsolutePath());
                    a(true, callback);
                } catch (Exception e) {
                    HLog.error(TAG, "load exist so files failed", e, new Object[0]);
                    a(false, callback);
                }
            } else if (soZipFile.exists()) {
                try {
                    c(soZipFile, soDir);
                    HLog.info(TAG, "unzip so zip completed, begin load", new Object[0]);
                    tryLoad(soDir.getAbsolutePath());
                    a(true, callback);
                } catch (Exception e2) {
                    HLog.error(TAG, "unzip so files failed", e2, new Object[0]);
                    a(false, callback);
                }
            } else {
                a(url, d.Kn().getAbsolutePath(), boI, md5, callback);
            }
        }
    }

    private void a(String url, String dir, String name, String md5, a callback) {
        AppConfig.getInstance().getHttpMgr().getDownloadReqBuilder(url, dir, name).setSuccListener(new 3(this, url, md5, callback)).setErrListener(new 2(this, url, callback)).execute();
    }

    private String gH(String url) throws IOException {
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(url);
        if (record == null) {
            return null;
        }
        return UtilsMD5.getFileMd5String(new File(record.dir, record.name).getAbsolutePath());
    }

    private void a(boolean succ, a callback) {
        if (callback != null) {
            this.mHandler.post(new 4(this, callback, succ));
        }
    }

    public static void c(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
        FileOutputStream fout;
        try {
            byte[] buffer = new byte[8192];
            while (true) {
                ZipEntry ze = zis.getNextEntry();
                if (ze != null) {
                    File file = new File(targetDirectory, ze.getName());
                    File dir = ze.isDirectory() ? file : file.getParentFile();
                    if (!dir.isDirectory() && !dir.mkdirs()) {
                        throw new FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath());
                    } else if (!ze.isDirectory()) {
                        fout = new FileOutputStream(file);
                        while (true) {
                            int count = zis.read(buffer);
                            if (count == -1) {
                                break;
                            }
                            fout.write(buffer, 0, count);
                        }
                        fout.close();
                    }
                } else {
                    zis.close();
                    return;
                }
            }
        } catch (Throwable th) {
            zis.close();
        }
    }

    @DoNotStrip
    private void tryLoad(String dir) throws Exception {
        HLog.info(TAG, "begin load so file dir " + dir + ", loading ex " + this.boN, new Object[0]);
        if (this.boN != null) {
            throw this.boN;
        }
        try {
            for (String so : soFiles) {
                Loader.putLibLocalPath(so.replace("lib", "").replace(".so", ""), new File(dir, so).getAbsolutePath());
            }
            Loader.load(avutil.class);
            Loader.load(avcodec.class);
            Loader.load(avformat.class);
            Loader.load(postproc.class);
            Loader.load(swresample.class);
            Loader.load(swscale.class);
            Loader.load(avfilter.class);
            HLog.info(TAG, "load so file succ", new Object[0]);
        } catch (Throwable t) {
            HLog.error(TAG, "load so failed, err " + t, new Object[0]);
            if (t instanceof Exception) {
                this.boN = (Exception) t;
            } else {
                this.boN = new Exception("Failed to load ffmpeg lib so", t);
            }
        }
    }
}
