package com.huluxia.framework;

import android.content.Context;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsApkPackage;

public class AppHelper {
    public static String getApkSignatureMD5() throws Exception {
        return getApkSignatureMD5(AppConfig.getInstance().getAppContext().getPackageName());
    }

    public static String getApkSignatureMD5(String packageName) throws Exception {
        Context context = AppConfig.getInstance().getAppContext();
        String signature = UtilsApkPackage.getApkSignatureMD5(context, UtilsApkPackage.getInstalledApkFileAbsPath(context, packageName), true);
        HLog.verbose("", "get apk signature java = " + signature, new Object[0]);
        return signature;
    }

    public static String getDexManifestSha1() throws Exception {
        Context context = AppConfig.getInstance().getAppContext();
        return UtilsApkPackage.getClassesDexManifestSHA1Digest(UtilsApkPackage.getInstalledApkFile(context, context.getPackageName()).getAbsolutePath());
    }
}
