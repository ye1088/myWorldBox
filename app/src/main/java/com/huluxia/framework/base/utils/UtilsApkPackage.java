package com.huluxia.framework.base.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.y;
import android.support.annotation.z;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.huluxia.framework.base.log.HLog;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsApkPackage {
    private static final String CLASSES_DEX = "classes.dex";
    private static final String SHA1_DIGEST = "SHA1-Digest";
    private static final String TAG = "UtilsApkPackage";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;

    public static File getInstalledApkFile(Context context, String packageName) throws NameNotFoundException {
        if (context == null || packageName == null) {
            return null;
        }
        return new File(context.getApplicationContext().getPackageManager().getApplicationInfo(packageName, 0).sourceDir);
    }

    public static String getInstalledApkFileAbsPath(Context context, String packageName) throws NameNotFoundException {
        File file = getInstalledApkFile(context, packageName);
        if (file == null) {
            return null;
        }
        return file.getAbsolutePath();
    }

    public static String getApkSignatureMD5(Context context, String apkPath, boolean reflect) throws Exception {
        if (context == null || apkPath == null) {
            return null;
        }
        String sign = null;
        Signature apkSignature = reflect ? getApkSignatureReflect(context, apkPath) : getApkSignature(context, apkPath);
        if (apkSignature != null) {
            sign = UtilsMD5.getMD5String(apkSignature.toByteArray());
        }
        HLog.verbose(TAG, "apk sign = " + sign, new Object[0]);
        return sign;
    }

    @z
    public static String getApkSignatureChar(Context context, String apkPath) {
        Signature apkSignature = null;
        try {
            apkSignature = getApkSignature(context, apkPath);
            if (apkSignature == null) {
                apkSignature = getApkSignatureReflect(context, apkPath);
            }
        } catch (Exception e) {
            HLog.error(TAG, "getApkSignatureReflect error " + e, new Object[0]);
        }
        if (apkSignature == null) {
            return null;
        }
        return apkSignature.toCharsString();
    }

    @z
    public static String getApkSignatureByPackagename(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 64);
            if (packageInfo != null && packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                return packageInfo.signatures[0].toCharsString();
            }
            Collection apps = null;
            try {
                apps = pm.getInstalledPackages(64);
            } catch (Exception e) {
                HLog.error(TAG, "getApkSignatureByPackagename error " + e, new Object[0]);
            }
            if (UtilsFunction.empty((Collection) apps)) {
                return null;
            }
            for (PackageInfo packageinfo : apps) {
                if (packageinfo.packageName.equals(packageName) && packageinfo != null && packageinfo.signatures != null && packageinfo.signatures.length > 0 && packageinfo.signatures[0] != null) {
                    return packageinfo.signatures[0].toCharsString();
                }
            }
            return null;
        } catch (Exception e2) {
            HLog.error(TAG, "getApkSignatureByPackagename unknown error " + e2, new Object[0]);
            return null;
        }
    }

    public static String copyDex(String apkPath, String dir) throws IOException {
        if (UtilsFunction.empty((CharSequence) apkPath) || UtilsFunction.empty((CharSequence) dir)) {
            return null;
        }
        if (!UtilsFile.isExist(dir)) {
            UtilsFile.mkdir(dir);
        }
        File file = new File(dir + File.separator + CLASSES_DEX);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        JarFile jarFile = new JarFile(apkPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry je = (JarEntry) entries.nextElement();
            if (!je.isDirectory() && je.getName().equals(CLASSES_DEX)) {
                BufferedInputStream bis = new BufferedInputStream(jarFile.getInputStream(je));
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                while (bis.read(bytes) != -1) {
                    fileOutputStream.write(bytes);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                bis.close();
            }
        }
        jarFile.close();
        return UtilsMD5.getFileMD5String(file);
    }

    public static String getClassesDexManifestSHA1Digest(String inputJarFile) throws IOException {
        return ManifestReader.readManifest(inputJarFile).getAttributes(CLASSES_DEX).getValue(SHA1_DIGEST);
    }

    public static String getCertSFSHA1Digest(String inputJarFile) throws IOException {
        return ManifestReader.readCertSF(inputJarFile).getAttributes(CLASSES_DEX).getValue(SHA1_DIGEST);
    }

    @z
    public static Signature getApkSignatureReflect(Context context, String apkPath) throws Exception {
        Class clazz = Class.forName("android.content.pm.PackageParser");
        Object packageParser = getParserObject(clazz);
        Object packag = getPackage(context, clazz, packageParser, apkPath);
        clazz.getMethod("collectCertificates", new Class[]{Class.forName("android.content.pm.PackageParser$Package"), Integer.TYPE}).invoke(packageParser, new Object[]{packag, Integer.valueOf(64)});
        Signature[] mSignatures = (Signature[]) packag.getClass().getField("mSignatures").get(packag);
        return mSignatures.length > 0 ? mSignatures[0] : null;
    }

    @z
    public static Signature getApkSignature(Context context, String apkPath) {
        Signature signature = null;
        PackageInfo pkgInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, 64);
        if (pkgInfo != null) {
            Signature[] mSignatures = pkgInfo.signatures;
            if (mSignatures.length > 0) {
                signature = mSignatures[0];
            }
        }
        return signature;
    }

    public static int getApkVersion(Context context, String apkPath) {
        return context.getPackageManager().getPackageArchiveInfo(apkPath, 16384).versionCode;
    }

    public static int getApkVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getApkVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getInstalledApkVersionName(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 16384).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAppMetadata(Context context, String key) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 128);
            if (packageInfo == null || packageInfo.applicationInfo == null || packageInfo.applicationInfo.metaData == null) {
                return "";
            }
            return packageInfo.applicationInfo.metaData.getString(key);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean getApkDebugable(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (NameNotFoundException e) {
        }
        if (appInfo != null) {
            return (appInfo.flags & 2) > 0;
        } else {
            return false;
        }
    }

    public static int getInstalledApkVersion(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 16384).versionCode;
        } catch (NameNotFoundException e) {
            Log.e("", "getInstalledApkVersion error", e);
            return -1;
        }
    }

    public static Drawable getApkIcon(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 16384).applicationInfo.loadIcon(context.getPackageManager());
        } catch (NameNotFoundException e) {
            Log.e("", "getInstalledApkIcon error", e);
            return null;
        }
    }

    public static boolean isApkInstalled(Context context, String packageName) {
        if (context == null || UtilsFunction.empty((CharSequence) packageName)) {
            return false;
        }
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = context.getPackageManager().getPackageInfo(packageName, 16384);
        } catch (NameNotFoundException e) {
        }
        if (pkgInfo != null) {
            return true;
        }
        return false;
    }

    public static boolean isApkInstalled(Context context, File apkFile) {
        boolean z = false;
        if (!(context == null || apkFile == null || !apkFile.exists())) {
            try {
                z = isApkInstalled(context, parsePackageLite(apkFile.getAbsolutePath()).packageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    public static boolean isApkNeedUpdate(Context context, String pkgName, int versionCode) {
        return getInstalledApkVersion(context, pkgName) < versionCode;
    }

    private static Object getParserObject(Class clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        if (VERSION.SDK_INT >= 21) {
            return clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        return clazz.getConstructor(new Class[]{String.class}).newInstance(new Object[]{""});
    }

    private static Object getPackage(Context c, Class clazz, Object instance, String path) throws Exception {
        if (VERSION.SDK_INT >= 21) {
            return clazz.getMethod("parsePackage", new Class[]{File.class, Integer.TYPE}).invoke(instance, new Object[]{new File(path), Integer.valueOf(4)});
        }
        return clazz.getMethod("parsePackage", new Class[]{File.class, String.class, DisplayMetrics.class, Integer.TYPE}).invoke(instance, new Object[]{new File(path), null, c.getResources().getDisplayMetrics(), Integer.valueOf(4)});
    }

    public static Certificate[][] getApkCerficate(Context context, String apkPath) throws Exception {
        Class clazz = Class.forName("android.content.pm.PackageParser");
        Object packageParser = getParserObject(clazz);
        Object packag = getPackage(context, clazz, packageParser, apkPath);
        clazz.getMethod("collectCertificates", new Class[]{Class.forName("android.content.pm.PackageParser$Package"), Integer.TYPE}).invoke(packageParser, new Object[]{packag, Integer.valueOf(64)});
        return (Certificate[][]) packag.getClass().getField("mCertificates").get(packag);
    }

    public static PackageLite parsePackageLite(String packageFilePath) throws Exception {
        Object packageLite;
        Method parsePackageLite = getParsePackageLiteMethod(Class.forName("android.content.pm.PackageParser"));
        if (VERSION.SDK_INT >= 21) {
            packageLite = parsePackageLite.invoke(null, new Object[]{new File(packageFilePath), Integer.valueOf(0)});
        } else {
            packageLite = parsePackageLite.invoke(null, new Object[]{packageFilePath, Integer.valueOf(0)});
        }
        return parsePackageLite(packageLite);
    }

    private static Method getParsePackageLiteMethod(Class clazz) throws NoSuchMethodException {
        if (VERSION.SDK_INT >= 21) {
            return clazz.getMethod("parsePackageLite", new Class[]{File.class, Integer.TYPE});
        }
        return clazz.getMethod("parsePackageLite", new Class[]{String.class, Integer.TYPE});
    }

    private static PackageLite parsePackageLite(Object packageLite) {
        if (packageLite == null) {
            return null;
        }
        Class clazz = packageLite.getClass();
        String packageName = null;
        try {
            packageName = (String) clazz.getField("packageName").get(packageLite);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        int versionCode = 0;
        try {
            versionCode = ((Integer) clazz.getField("versionCode").get(packageLite)).intValue();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        } catch (IllegalAccessException e22) {
            e22.printStackTrace();
        }
        int installLocation = 0;
        try {
            installLocation = ((Integer) clazz.getField("installLocation").get(packageLite)).intValue();
        } catch (NoSuchFieldException e32) {
            e32.printStackTrace();
        } catch (IllegalAccessException e222) {
            e222.printStackTrace();
        }
        Object[] verifiers = new Object[0];
        List<VerifierInfo> verifierInfos = null;
        try {
            verifierInfos = parseVerifierInfos((Object[]) clazz.getField("verifiers").get(packageLite));
        } catch (NoSuchFieldException e322) {
            e322.printStackTrace();
        } catch (IllegalAccessException e2222) {
            e2222.printStackTrace();
        }
        return new PackageLite(packageName, versionCode, installLocation, verifierInfos);
    }

    private static List<VerifierInfo> parseVerifierInfos(Object[] verifiers) throws NoSuchFieldException, IllegalAccessException {
        List<VerifierInfo> verifierInfos = new ArrayList();
        for (Object o : verifiers) {
            Class clazz = o.getClass();
            verifierInfos.add(new VerifierInfo((String) clazz.getField("packageName").get(o), (PublicKey) clazz.getField("publicKey").get(o)));
        }
        return verifierInfos;
    }

    public static boolean availableIntent(@y Context context, @y Intent intent) {
        if (context == null || intent == null || UtilsFunction.size(context.getPackageManager().queryIntentActivities(intent, 0)) <= 0) {
            return false;
        }
        return true;
    }

    public static void runApp(Context context, String packname) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packname);
        if (intent == null) {
            HLog.error(TAG, "can not run app " + packname, new Object[0]);
            return;
        }
        intent.setFlags(337641472);
        context.startActivity(intent);
    }

    public static void runInstallApp(Context context, String absPath) {
        HLog.verbose(TAG, " run install app %s", absPath);
        if (!UtilsFunction.empty((CharSequence) absPath) && context != null) {
            File file = new File(absPath);
            if (file.exists()) {
                Intent intent;
                if (UtilsVersion.hasICS()) {
                    intent = new Intent("android.intent.action.INSTALL_PACKAGE");
                    intent.setData(Uri.fromFile(file));
                } else {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }
                intent.addFlags(268435456);
                if (availableIntent(context, intent)) {
                    context.startActivity(intent);
                } else {
                    HLog.info(TAG, "can not handle this intent " + intent, new Object[0]);
                }
            }
        }
    }

    public static void uninstallApp(Context context, String packageName) {
        Intent intent = new Intent("android.intent.action.DELETE", Uri.parse("package:" + packageName));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static String getSourceApkPath(Context context, String packageName) {
        String str = null;
        if (!TextUtils.isEmpty(packageName)) {
            try {
                str = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getAppPackageName(Context context) {
        return ((RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
    }

    public static boolean isVMMultidexCapable() {
        String versionString = System.getProperty("java.vm.version");
        boolean isMultidexCapable = false;
        if (versionString != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
            if (matcher.matches()) {
                try {
                    int major = Integer.parseInt(matcher.group(1));
                    isMultidexCapable = major > 2 || (major == 2 && Integer.parseInt(matcher.group(2)) >= 1);
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i("isVMMultidexCapable", "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    public static List<AppInfo> getInstallAppInfo(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        List<AppInfo> resultList = new ArrayList();
        List<PackageInfo> packList = null;
        try {
            packList = pm.getInstalledPackages(0);
        } catch (Exception ex) {
            HLog.error(TAG, "getInstalledPackages Exception " + ex, new Object[0]);
        }
        if (packList != null) {
            String strSelfPackname = context.getPackageName();
            int count = 0;
            for (PackageInfo packageInfo : packList) {
                if (!(packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 1) > 0 || TextUtils.isEmpty(packageInfo.packageName) || strSelfPackname.equals(packageInfo.packageName))) {
                    int count2 = count + 1;
                    if (count > 100) {
                        count = count2;
                        break;
                    }
                    resultList.add(new AppInfo(packageInfo.packageName, packageInfo.versionCode));
                    count = count2;
                }
            }
        }
        return resultList;
    }
}
