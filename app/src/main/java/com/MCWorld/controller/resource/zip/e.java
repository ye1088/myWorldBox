package com.MCWorld.controller.resource.zip;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import com.MCWorld.jni.InstallerJni;
import com.MCWorld.jni.InstallerJni.ZipCallback;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Zipper */
public class e implements ZipCallback {
    private static final String TAG = "zipper";
    private Handler mHandler;
    private Map<Integer, ZipCallback> oQ;
    private InstallerJni oR;

    /* compiled from: Zipper */
    public interface a {
        void al(String str);

        void d(String str, int i, int i2);

        void r(String str, int i);
    }

    /* compiled from: Zipper */
    private static class b {
        private static e oY = new e();

        private b() {
        }
    }

    /* compiled from: Zipper */
    public static class c {
        public float length;
        public int oZ;
        public float pa;
        public long pb;
    }

    public static e eb() {
        return b.oY;
    }

    private e() {
        this.oQ = new HashMap();
        init();
    }

    private void init() {
        this.oR = new InstallerJni(this);
        this.oR.InitInstaller(Environment.getExternalStorageDirectory().getAbsolutePath());
        HandlerThread postThread = new HandlerThread("unzip-post");
        postThread.start();
        this.mHandler = new Handler(postThread.getLooper());
    }

    public int a(String srcFile, String internalFilePath, String desDir, ZipCallback callback) {
        int retId = this.oR.UnzipTask(srcFile, internalFilePath, desDir);
        if (this.oQ.get(Integer.valueOf(retId)) == null) {
            this.oQ.put(Integer.valueOf(retId), callback);
        }
        return retId;
    }

    public void OnRecvZipResult(final int taskId, final int result) {
        this.mHandler.post(new Runnable(this) {
            final /* synthetic */ e oU;

            public void run() {
                if (this.oU.oQ.get(Integer.valueOf(taskId)) != null) {
                    ((ZipCallback) this.oU.oQ.get(Integer.valueOf(taskId))).OnRecvZipResult(taskId, result);
                }
                this.oU.oQ.remove(Integer.valueOf(taskId));
            }
        });
    }

    public void OnRecvZipProgress(final int taskId, final int progress, final int length) {
        this.mHandler.post(new Runnable(this) {
            final /* synthetic */ e oU;

            public void run() {
                if (this.oU.oQ.get(Integer.valueOf(taskId)) != null) {
                    ((ZipCallback) this.oU.oQ.get(Integer.valueOf(taskId))).OnRecvZipProgress(taskId, progress, length);
                }
            }
        });
    }

    public void onRecvUnzipInternalFile(final int taskId, final String filePath) {
        this.mHandler.post(new Runnable(this) {
            final /* synthetic */ e oU;

            public void run() {
                if (this.oU.oQ.get(Integer.valueOf(taskId)) != null) {
                    ((ZipCallback) this.oU.oQ.get(Integer.valueOf(taskId))).onRecvUnzipInternalFile(taskId, filePath);
                }
            }
        });
    }
}
