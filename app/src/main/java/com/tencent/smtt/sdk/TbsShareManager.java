package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.MCWorld.framework.BaseHttpMgr;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import com.tencent.smtt.utils.TbsLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class TbsShareManager {
    private static Context a;
    private static boolean b;
    private static String c = null;
    private static int d = 0;
    private static String e = null;
    private static boolean f = false;
    private static boolean g = false;
    private static String h = null;
    private static boolean i = false;

    private static File a(Context context, String str) {
        File f = aa.a().f(context);
        if (f == null) {
            return null;
        }
        File file = new File(f, str);
        if (file != null && file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String a(Context context) {
        g(context);
        return c;
    }

    private static void a(Context context, boolean z) {
        Throwable th;
        FileInputStream fileInputStream = null;
        FileInputStream fileInputStream2;
        try {
            File a = a(context, "core_info");
            if (a == null) {
                try {
                    fileInputStream.close();
                    return;
                } catch (Exception e) {
                    return;
                }
            }
            fileInputStream2 = new FileInputStream(a);
            try {
                Properties properties = new Properties();
                properties.load(fileInputStream2);
                properties.setProperty("core_disabled", String.valueOf(false));
                if (z) {
                    String absolutePath = aa.a().e(context).getAbsolutePath();
                    String packageName = context.getApplicationContext().getPackageName();
                    int b = a.b(context);
                    properties.setProperty("core_packagename", packageName);
                    properties.setProperty("core_path", absolutePath);
                    properties.setProperty(BaseHttpMgr.PARAM_APP_VERSION, String.valueOf(b));
                }
                properties.store(new FileOutputStream(a), null);
                try {
                    fileInputStream2.close();
                } catch (Exception e2) {
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    th.printStackTrace();
                    try {
                        fileInputStream2.close();
                    } catch (Exception e3) {
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        fileInputStream2.close();
                    } catch (Exception e4) {
                    }
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream2 = null;
            fileInputStream2.close();
            throw th;
        }
    }

    static int b(Context context) {
        g(context);
        return d;
    }

    private static Context b(Context context, String str) {
        Context context2 = null;
        try {
            context2 = context.createPackageContext(str, 2);
        } catch (NameNotFoundException e) {
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return context2;
    }

    static Context c(Context context) {
        g(context);
        return e == null ? null : b(context, e);
    }

    static synchronized String d(Context context) {
        Throwable th;
        Throwable th2;
        String str = null;
        synchronized (TbsShareManager.class) {
            FileInputStream fileInputStream = null;
            FileInputStream fileInputStream2;
            try {
                File a = a(context, "core_info");
                if (a != null) {
                    fileInputStream2 = new FileInputStream(a);
                    try {
                        Properties properties = new Properties();
                        properties.load(fileInputStream2);
                        String property = properties.getProperty("core_packagename", "");
                        if (!"".equals(property)) {
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (Exception e) {
                                }
                            }
                            str = property;
                        } else if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (Exception e2) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        try {
                            th.printStackTrace();
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (Exception e3) {
                                }
                            }
                            return str;
                        } catch (Throwable th4) {
                            th2 = th4;
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (Exception e4) {
                                }
                            }
                            throw th2;
                        }
                    }
                } else if (null != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e5) {
                    }
                }
            } catch (Throwable th5) {
                fileInputStream2 = null;
                th2 = th5;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                throw th2;
            }
        }
        return str;
    }

    static synchronized int e(Context context) {
        FileInputStream fileInputStream;
        Throwable th;
        int i = 0;
        synchronized (TbsShareManager.class) {
            FileInputStream fileInputStream2 = null;
            try {
                File a = a(context, "core_info");
                if (a == null) {
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    fileInputStream = new FileInputStream(a);
                    try {
                        Properties properties = new Properties();
                        properties.load(fileInputStream);
                        String property = properties.getProperty("core_version", "");
                        if ("".equals(property)) {
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } else {
                            i = Math.max(Integer.parseInt(property), 0);
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Exception e22) {
                                    e22.printStackTrace();
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            th.printStackTrace();
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            i = -2;
                            return i;
                        } catch (Throwable th3) {
                            th = th3;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Exception e222) {
                                    e222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        }
        return i;
    }

    static boolean f(Context context) {
        try {
            if (d == 0) {
                j(context);
            }
            if (d == 0) {
                return false;
            }
            if (d != 0 && getSharedTbsCoreVersion(context, e) == d) {
                return true;
            }
            c = null;
            d = 0;
            TbsLog.e("TbsShareManager", "isShareTbsCoreAvailableInner forceSysWebViewInner!");
            QbSdk.a();
            QbSdk.e.onCallBackErrorCode(ErrorCode.ERROR_COREVERSION_CHANGED, ErrorCode.INFO_ERROR_COREVERSION_CHANGED);
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static boolean forceLoadX5FromTBSDemo(Context context) {
        if (context == null) {
            return false;
        }
        int sharedTbsCoreVersion = getSharedTbsCoreVersion(context, TbsConfig.APP_DEMO);
        if (sharedTbsCoreVersion <= 0) {
            return false;
        }
        writeProperties(context, Integer.toString(sharedTbsCoreVersion), TbsConfig.APP_DEMO, aa.a().e(b(context, TbsConfig.APP_DEMO)).getAbsolutePath(), "1");
        return true;
    }

    static boolean g(Context context) {
        if (f(context)) {
            return true;
        }
        TbsLog.e("TbsShareManager", "isShareTbsCoreAvailable forceSysWebViewInner!");
        QbSdk.a();
        return false;
    }

    public static String[] getCoreProviderAppList() {
        return new String[]{"com.tencent.mm", "com.tencent.mobileqq", TbsConfig.APP_QB, "com.qzone", "com.tencent.qqpimsecure", TbsConfig.APP_DEMO, TbsConfig.APP_DEMO_TEST};
    }

    public static int getSharedTbsCoreVersion(Context context, String str) {
        Context b = b(context, str);
        return b == null ? 0 : aa.a().b(b);
    }

    private static boolean h(Context context) {
        return e != null && d == getSharedTbsCoreVersion(context, e);
    }

    private static boolean i(Context context) {
        String[] coreProviderAppList = getCoreProviderAppList();
        int length = coreProviderAppList.length;
        int i = 0;
        while (i < length) {
            String str = coreProviderAppList[i];
            if (d <= 0 || d != getSharedTbsCoreVersion(context, str)) {
                i++;
            } else {
                c = aa.a().a(context, b(context, str)).getAbsolutePath();
                e = str;
                return true;
            }
        }
        return false;
    }

    public static boolean isThirdPartyApp(Context context) {
        try {
            if (a != null && a.equals(context.getApplicationContext())) {
                return b;
            }
            a = context.getApplicationContext();
            String packageName = a.getPackageName();
            for (Object equals : getCoreProviderAppList()) {
                if (packageName.equals(equals)) {
                    b = false;
                    return false;
                }
            }
            b = true;
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static int j(Context context) {
        l(context);
        TbsLog.i("TbsShareManager", "core_info mAvailableCoreVersion is " + d + " mAvailableCorePath is " + c + " mSrcPackageName is " + e);
        if (!(h(context) || i(context))) {
            d = 0;
            c = null;
            e = null;
            TbsLog.i("TbsShareManager", "core_info error checkCoreInfo is false and checkCoreInOthers is false ");
            QbSdk.e.onCallBackErrorCode(ErrorCode.ERROR_NOMATCH_COREVERSION, ErrorCode.INFO_ERROR_NOMATCH_COREVERSION);
        }
        if (d > 0 && (QbSdk.a(context, d) || f)) {
            d = 0;
            c = null;
            e = null;
            TbsLog.i("TbsShareManager", "core_info error QbSdk.isX5Disabled ");
        }
        return d;
    }

    private static boolean k(Context context) {
        writeProperties(context, Integer.toString(0), "", "", Integer.toString(0));
        return true;
    }

    private static synchronized void l(Context context) {
        FileInputStream fileInputStream;
        Throwable th;
        synchronized (TbsShareManager.class) {
            if (!i) {
                FileInputStream fileInputStream2 = null;
                try {
                    File a = a(context, "core_info");
                    if (a != null) {
                        fileInputStream = new FileInputStream(a);
                        try {
                            Properties properties = new Properties();
                            properties.load(fileInputStream);
                            String property = properties.getProperty("core_version", "");
                            if (!"".equals(property)) {
                                d = Math.max(Integer.parseInt(property), 0);
                            }
                            property = properties.getProperty("core_packagename", "");
                            if (!"".equals(property)) {
                                e = property;
                            }
                            property = properties.getProperty("core_path", "");
                            if (!"".equals(property)) {
                                c = property;
                            }
                            property = properties.getProperty(BaseHttpMgr.PARAM_APP_VERSION, "");
                            if (!"".equals(property)) {
                                h = property;
                            }
                            f = Boolean.parseBoolean(properties.getProperty("core_disabled", "false"));
                            i = true;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                th.printStackTrace();
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                    } else if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            }
        }
    }

    public static void writeCoreInfoForThirdPartyApp(Context context, int i, boolean z) {
        int i2 = 0;
        if (i == 0) {
            k(context);
            return;
        }
        int e = e(context);
        if (e < 0) {
            return;
        }
        if (i == e) {
            a(context, z);
        } else if (i < e) {
            k(context);
        } else {
            String[] coreProviderAppList = getCoreProviderAppList();
            if (z) {
                coreProviderAppList = new String[]{context.getApplicationContext().getPackageName()};
            }
            int length = coreProviderAppList.length;
            while (i2 < length) {
                String str = coreProviderAppList[i2];
                if (i == getSharedTbsCoreVersion(context, str)) {
                    String absolutePath = aa.a().e(b(context, str)).getAbsolutePath();
                    length = a.b(context);
                    if (!str.equals(context.getApplicationContext().getPackageName())) {
                        TbsLog.i(TbsDownloader.LOGTAG, "thirdAPP pre--> delete old core_share Directory:" + i);
                        try {
                            h.a(aa.a().e(context));
                            TbsLog.i(TbsDownloader.LOGTAG, "thirdAPP success--> delete old core_share Directory");
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                    writeProperties(context, Integer.toString(i), str, absolutePath, Integer.toString(length));
                    try {
                        File a = a(context, "core_info");
                        if (!g && a != null) {
                            TbsLinuxToolsJni tbsLinuxToolsJni = new TbsLinuxToolsJni(a);
                            tbsLinuxToolsJni.a(a.getAbsolutePath(), "644");
                            tbsLinuxToolsJni.a(aa.a().f(context).getAbsolutePath(), "755");
                            g = true;
                            return;
                        }
                        return;
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                        return;
                    }
                }
                i2++;
            }
        }
    }

    public static void writeProperties(Context context, String str, String str2, String str3, String str4) {
        Throwable th;
        int i = 0;
        FileInputStream fileInputStream = null;
        try {
            File a = a(context, "core_info");
            if (a == null) {
                try {
                    fileInputStream.close();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            fileInputStream = new FileInputStream(a);
            try {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                try {
                    i = Integer.parseInt(str);
                } catch (Exception e2) {
                }
                if (i != 0) {
                    properties.setProperty("core_version", str);
                    properties.setProperty("core_disabled", String.valueOf(false));
                    properties.setProperty("core_packagename", str2);
                    properties.setProperty("core_path", str3);
                    properties.setProperty(BaseHttpMgr.PARAM_APP_VERSION, str4);
                } else {
                    properties.setProperty("core_disabled", String.valueOf(true));
                }
                properties.store(new FileOutputStream(a), null);
                i = false;
                try {
                    fileInputStream.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    th.printStackTrace();
                    try {
                        fileInputStream.close();
                    } catch (Exception e32) {
                        e32.printStackTrace();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        fileInputStream.close();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            fileInputStream.close();
            throw th;
        }
    }
}
