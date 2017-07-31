package com.baidu.frontia;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Process;
import android.text.TextUtils;
import com.baidu.android.silentupdate.SilentManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

public class FrontiaApplication extends Application {
    private static final String a = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYAFbG0oYmKgh6o7BhZIHf1njBpZXqyWBnYz2ip3Wp+s97OeA/pTe8xebuGJHwq4xbsGQrJWepIbUVrdjm6JRmdvuJhar7/hC/UNnUkJgYdYl10OZKlvcFFgK3V7XGBPplXldDnhbgscna3JG8U3025WSxZCP5vy/8cfxsEoVx5QIDAQAB";
    private static final String b = "f5de4bda49c00a19757289cd02a60f5d";
    private static final String c = "com.baidu.android.pushservice.PushService";

    private static boolean a(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int myPid = Process.myPid();
            for (RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == myPid) {
                    if (runningAppProcessInfo.processName.equalsIgnoreCase(c(context)) && !b(context.getApplicationContext())) {
                        Process.killProcess(myPid);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean b(Context context) {
        boolean z = false;
        try {
            SilentManager.setKey(a);
            SilentManager.enableRSA(false);
            z = SilentManager.loadLib(context.getApplicationContext(), "frontia_plugin", "plugin-deploy.jar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    private static String c(Context context) {
        ServiceInfo[] serviceInfoArr;
        try {
            serviceInfoArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4).services;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            serviceInfoArr = null;
        }
        if (serviceInfoArr == null) {
            return null;
        }
        for (int i = 0; i < serviceInfoArr.length; i++) {
            if (c.equals(serviceInfoArr[i].name)) {
                return serviceInfoArr[i].processName;
            }
        }
        return null;
    }

    private static boolean d(Context context) {
        boolean z = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("frontia_plugin/plugin-deploy.key")));
            String str = "";
            String str2 = "";
            while (true) {
                str2 = bufferedReader.readLine();
                if (str2 == null) {
                    break;
                }
                str = str + str2 + "\r\n";
            }
            Object decrypt = SilentManager.decrypt(a, str);
            if (!TextUtils.isEmpty(decrypt)) {
                z = new JSONObject(decrypt).optString("flag", "null").equals(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return z;
    }

    public static void initFrontiaApplication(Context context) {
        if (!d(context)) {
            b(context);
        } else if (!a(context)) {
            b(context);
        }
    }

    public void onCreate() {
        super.onCreate();
        initFrontiaApplication(getApplicationContext());
    }
}
