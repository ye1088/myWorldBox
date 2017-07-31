package hlx.launch.game;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/* compiled from: MCGameCheckUtils */
public class b {
    public static boolean a(PackageManager pm, String uri, String version) {
        try {
            return pm.getPackageInfo(uri, 1).versionName.equals(version);
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean a(PackageManager pm, String uri) {
        try {
            pm.getPackageInfo(uri, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static String b(PackageManager pm, String uri) {
        String version = "";
        try {
            return pm.getPackageInfo(uri, 1).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }
}
