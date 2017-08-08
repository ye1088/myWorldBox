package com.MCWorld.framework.base.utils;

import java.util.List;

public class UtilsApkPackage$PackageLite {
    public final int installLocation;
    public final String packageName;
    public final UtilsApkPackage$VerifierInfo[] verifiers;
    public final int versionCode;

    public UtilsApkPackage$PackageLite(String packageName, int versionCode, int installLocation, List<UtilsApkPackage$VerifierInfo> verifiers) {
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.installLocation = installLocation;
        this.verifiers = (UtilsApkPackage$VerifierInfo[]) verifiers.toArray(new UtilsApkPackage$VerifierInfo[verifiers.size()]);
    }
}
