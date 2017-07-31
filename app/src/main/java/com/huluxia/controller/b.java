package com.huluxia.controller;

import android.content.SharedPreferences;
import android.os.Environment;
import android.os.SystemClock;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.ExternalStorage;
import com.huluxia.framework.base.utils.SharedPref;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.io.File;

/* compiled from: CommonPref */
public class b extends SharedPref {
    private static b lZ;

    private b(SharedPreferences pref) {
        super(pref);
    }

    public static synchronized b dE() {
        b bVar;
        synchronized (b.class) {
            if (lZ == null) {
                lZ = new b(AppConfig.getInstance().getAppContext().getSharedPreferences("common-pref", 0));
            }
            bVar = lZ;
        }
        return bVar;
    }

    public String getDownloadPath() {
        String path = dE().getString("download-path");
        if (UtilsFunction.empty(path)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "huluxia" + File.separator + "downloads";
            dE().putString("download-path", path);
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public void ae(String path) {
        putString("download-path", path);
    }

    public String dF() {
        String defaultPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "huluxia" + File.separator + "downloads";
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return defaultPath;
    }

    public String dG() {
        String path = dE().get("emulator-path");
        if (!UtilsFunction.empty(path)) {
            return path;
        }
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "huluxia" + File.separator + "Emulator" + File.separator + "ROMS";
        dE().putString("emulator-path", path);
        return path;
    }

    public void af(String path) {
        putString("emulator-path", path);
    }

    public String dH() {
        String path = dE().getString("selected-sdcard");
        if (!UtilsFunction.empty(path)) {
            return path;
        }
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        dE().putString("selected-sdcard", path);
        return path;
    }

    public void ag(String path) {
        putString("selected-sdcard", path);
    }

    public static void dI() {
        String sdpath = dE().dH();
        if (sdpath.indexOf(Environment.getExternalStorageDirectory().getAbsolutePath()) < 0) {
            File extSdcard = new File(sdpath);
            String extDowoadPath = ExternalStorage.defaultDownloadPath(extSdcard);
            File testFile = new File(extDowoadPath, "huluxia.test" + SystemClock.elapsedRealtime());
            boolean testCreate = false;
            try {
                testCreate = testFile.createNewFile();
            } catch (Exception e) {
                HLog.error("", "test create failed, error %s, extsdcard %s ", e, new Object[]{extSdcard.getAbsolutePath()});
            }
            if (extSdcard.exists() && extSdcard.canWrite() && testCreate) {
                testFile.delete();
                dE().ag(extSdcard.getAbsolutePath());
                dE().ae(extDowoadPath);
                dE().c(extSdcard, extDowoadPath);
                return;
            }
            File internal = Environment.getExternalStorageDirectory();
            String olderDownloadPath = dE().g(internal);
            if (UtilsFunction.empty(olderDownloadPath)) {
                olderDownloadPath = ExternalStorage.defaultDownloadPath(internal);
            }
            dE().ag(internal.getAbsolutePath());
            dE().ae(olderDownloadPath);
            dE().c(internal, olderDownloadPath);
        }
    }

    public void c(File sdcard, String downloadPath) {
        putString(sdcard.getAbsolutePath(), downloadPath);
    }

    public String g(File sdcard) {
        return getString(sdcard.getAbsolutePath());
    }
}
