package com.mojang.minecraftpe;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import com.MCWorld.mcsdk.h;

/* compiled from: RedirectPackageManager */
public class c extends h {
    protected String nativeLibraryDir;

    public c(PackageManager mgr, String nativeLibraryDir) {
        super(mgr);
        this.nativeLibraryDir = nativeLibraryDir;
    }

    public ActivityInfo getActivityInfo(ComponentName className, int flags) throws NameNotFoundException {
        ActivityInfo retval = this.aop.getActivityInfo(className, flags);
        retval.applicationInfo.nativeLibraryDir = this.nativeLibraryDir;
        return retval;
    }
}
