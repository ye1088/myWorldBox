package com.huluxia.controller.resource.zip;

import android.annotation.TargetApi;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.DiskLruCache;
import com.huluxia.framework.base.utils.DiskLruCache.Editor;
import com.huluxia.framework.base.utils.DiskLruCache.Snapshot;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsVersion;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: HpkUnzipHistory */
public class c {
    private static final int DEFAULT_DISK_CACHE_SIZE = 10485760;
    private static final int DEFAULT_MEM_CACHE_SIZE = 1048576;
    private static final int DISK_CACHE_INDEX = 0;
    private static final String TAG = "HpkUnzipHistory";
    private String mCachePath;
    private final Object mDiskCacheLock;
    private volatile boolean mDiskCacheStarting;
    private DiskLruCache mDiskLruCache;
    private CallbackHandler mDownloadCallback;
    private LruCache<String, b> mLruCache;
    private float mScale;
    private int memCacheSize;

    /* compiled from: HpkUnzipHistory */
    private static class a {
        public static c oK = new c();

        private a() {
        }
    }

    public static c ea() {
        return a.oK;
    }

    private c() {
        this.memCacheSize = 1048576;
        this.mDiskCacheLock = new Object();
        this.mDiskCacheStarting = true;
        this.mDownloadCallback = new 1(this);
        EventNotifyCenter.add(BaseEvent.class, this.mDownloadCallback);
    }

    public void init(float scale, String cachePath) {
        this.mCachePath = cachePath;
        this.mScale = scale;
        this.memCacheSize = Math.min(Math.round((((float) Runtime.getRuntime().maxMemory()) * scale) / 1024.0f), 1048576);
        this.mLruCache = new HpkUnzipHistory$2(this, this.memCacheSize);
        initDiskCache();
    }

    public void unit() {
        flush();
        EventNotifyCenter.remove(this.mDownloadCallback);
    }

    private void initDiskCache() {
        new Thread(this, "hpk-history-load") {
            final /* synthetic */ c oI;

            public void run() {
                synchronized (this.oI.mDiskCacheLock) {
                    if (this.oI.mDiskLruCache == null || this.oI.mDiskLruCache.isClosed()) {
                        File diskCacheDir = new File(this.oI.mCachePath);
                        if (diskCacheDir != null) {
                            if (!diskCacheDir.exists()) {
                                diskCacheDir.mkdirs();
                            }
                            if (c.getUsableSpace(diskCacheDir) > 10485760) {
                                try {
                                    this.oI.mDiskLruCache = DiskLruCache.open(diskCacheDir, UtilsVersion.getVersionCode(AppConfig.getInstance().getAppContext()), 1, 10485760);
                                } catch (IOException e) {
                                    HLog.error(c.TAG, "initDiskCache - " + e, new Object[0]);
                                }
                            }
                        }
                    }
                    this.oI.mDiskCacheStarting = false;
                    this.oI.mDiskCacheLock.notifyAll();
                    HLog.info(c.TAG, "hpk history init disk cache complete", new Object[0]);
                }
            }
        }.start();
    }

    public b aq(String url) {
        String key = hashKeyForDisk(url);
        b fileList = (b) this.mLruCache.get(key);
        if (fileList != null) {
            return fileList;
        }
        return ar(key);
    }

    public void a(String url, b fileList) {
        if (!UtilsFunction.empty(url) && fileList != null) {
            String key = hashKeyForDisk(url);
            try {
                this.mLruCache.put(key, fileList);
                if (this.mDiskCacheStarting) {
                    HLog.warn(TAG, "add entry when hpk disk cache is starting initialization", new Object[0]);
                    return;
                }
                this.mDiskLruCache.remove(key);
                flush();
                b(url, fileList);
                flush();
            } catch (Exception e) {
                HLog.error(TAG, "putHpkFileList key %s when putting %s", new Object[]{url, fileList});
            }
        }
    }

    @TargetApi(9)
    public static long getUsableSpace(File path) {
        if (UtilsVersion.hasGingerbread()) {
            return path.getUsableSpace();
        }
        StatFs stats = new StatFs(path.getPath());
        return ((long) stats.getBlockSize()) * ((long) stats.getAvailableBlocks());
    }

    public void b(String data, b value) {
        if (data != null && value != null) {
            synchronized (this.mDiskCacheLock) {
                if (this.mDiskLruCache != null) {
                    String key = hashKeyForDisk(data);
                    OutputStream out = null;
                    try {
                        Snapshot snapshot = this.mDiskLruCache.get(key);
                        if (snapshot == null) {
                            Editor editor = this.mDiskLruCache.edit(key);
                            if (editor != null) {
                                out = editor.newOutputStream(0);
                                out.write(Json.toJson(value).getBytes());
                                editor.commit();
                                out.close();
                            }
                        } else {
                            snapshot.getInputStream(0).close();
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                            }
                        }
                    } catch (IOException e2) {
                        HLog.error(TAG, "addFileListToDiskCache - " + e2, new Object[0]);
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e3) {
                            }
                        }
                    } catch (Exception e4) {
                        HLog.error(TAG, "addFileListToDiskCache - " + e4, new Object[0]);
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e5) {
                            }
                        }
                    } catch (Throwable th) {
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e6) {
                            }
                        }
                    }
                }
            }
            return;
        }
        return;
    }

    public static String hashKeyForDisk(String key) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            return bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(key.hashCode());
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public b ar(String key) {
        Exception e;
        Throwable th;
        b fileList = null;
        synchronized (this.mDiskCacheLock) {
            while (this.mDiskCacheStarting) {
                try {
                    this.mDiskCacheLock.wait();
                } catch (InterruptedException e2) {
                }
            }
            if (this.mDiskLruCache != null) {
                InputStream inputStream = null;
                BufferedInputStream bis = null;
                try {
                    Snapshot snapshot = this.mDiskLruCache.get(key);
                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(0);
                        if (inputStream != null) {
                            BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(((FileInputStream) inputStream).getFD()));
                            try {
                                byte[] bytes = new byte[bis2.available()];
                                bis2.read(bytes);
                                fileList = (b) Json.parseJsonObject(new String(bytes), b.class);
                                bis = bis2;
                            } catch (Exception e3) {
                                e = e3;
                                bis = bis2;
                                try {
                                    HLog.error(TAG, "getHpkFileListFromDiskCache - " + e, new Object[0]);
                                    if (bis != null) {
                                        try {
                                            bis.close();
                                        } catch (IOException e4) {
                                        }
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    return fileList;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (bis != null) {
                                        try {
                                            bis.close();
                                        } catch (IOException e5) {
                                            throw th;
                                        }
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                bis = bis2;
                                if (bis != null) {
                                    bis.close();
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                throw th;
                            }
                        }
                    }
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e6) {
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e7) {
                    e = e7;
                    HLog.error(TAG, "getHpkFileListFromDiskCache - " + e, new Object[0]);
                    if (bis != null) {
                        bis.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    return fileList;
                }
            }
        }
        return fileList;
    }

    public void clearCache() {
        synchronized (this.mDiskCacheLock) {
            this.mDiskCacheStarting = true;
            if (!(this.mDiskLruCache == null || this.mDiskLruCache.isClosed())) {
                try {
                    this.mDiskLruCache.delete();
                } catch (IOException e) {
                    HLog.error(TAG, "clearCache - " + e, new Object[0]);
                }
                this.mDiskLruCache = null;
                initDiskCache();
            }
        }
    }

    public void flush() {
        synchronized (this.mDiskCacheLock) {
            if (this.mDiskLruCache != null) {
                try {
                    this.mDiskLruCache.flush();
                } catch (IOException e) {
                    HLog.error(TAG, "flush - " + e, new Object[0]);
                }
            }
        }
    }
}
