package com.MCWorld.jni;

import com.MCWorld.framework.base.utils.soloader.SoLoaderShim;

public class InstallerJni {
    private static ZipCallback callback;

    public interface ZipCallback {
        void OnRecvZipProgress(int i, int i2, int i3);

        void OnRecvZipResult(int i, int i2);

        void onRecvUnzipInternalFile(int i, String str);
    }

    public native boolean InitInstaller(String str);

    public native int UnzipTask(String str, String str2, String str3);

    static {
        SoLoaderShim.loadLibrary("InstallMod");
    }

    public InstallerJni(ZipCallback callback) {
        callback = callback;
    }

    private static void OnRecvZipResult(int taskId, int result) {
        if (callback != null) {
            callback.OnRecvZipResult(taskId, result);
        }
    }

    private static void OnRecvZipProgress(int taskId, int progress, int length) {
        if (callback != null) {
            callback.OnRecvZipProgress(taskId, progress, length);
        }
    }

    private static void onRecvUnzipInternalFile(int taskId, String filePath) {
        if (callback != null) {
            callback.onRecvUnzipInternalFile(taskId, filePath);
        }
    }
}
