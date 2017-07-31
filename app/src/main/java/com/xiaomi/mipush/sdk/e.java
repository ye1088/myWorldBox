package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import java.util.List;

final class e implements Runnable {
    final /* synthetic */ Context a;

    e(Context context) {
        this.a = context;
    }

    public void run() {
        try {
            List<PackageInfo> installedPackages = this.a.getPackageManager().getInstalledPackages(4);
            if (installedPackages != null) {
                for (PackageInfo access$100 : installedPackages) {
                    MiPushClient.access$100(this.a, access$100);
                }
            }
        } catch (Throwable th) {
        }
    }
}
