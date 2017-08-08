package hlx.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import com.MCWorld.t;
import com.MCWorld.utils.u;

/* compiled from: UtilsApkManager */
public class g {
    public static boolean a(PackageManager pm, String uri, String version) {
        try {
            return pm.getPackageInfo(uri, 1).versionName.equals(version);
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean a(PackageManager pm, String uri, int versionCode) {
        try {
            return pm.getPackageInfo(uri, 1).versionCode == versionCode;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean a(PackageManager pm, String uri) {
        try {
            PackageInfo _tmpPackInfo = pm.getPackageInfo(uri, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean b(PackageManager pm, String uri, int versionCode) {
        try {
            if (pm.getPackageInfo(uri, 1).versionCode < versionCode) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e) {
            return true;
        }
    }

    public static String c(PackageManager pm, String uri) {
        try {
            return pm.getPackageInfo(uri, 1).versionName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static void hP(String apkPath) {
        t.U(apkPath);
    }

    public static void uninstallApp(Context context, String packName) {
        Uri uri = Uri.parse("package:" + packName);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DELETE");
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void hQ(String packName) {
        u.C(packName);
    }
}
