package com.huluxia.mconline.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.huluxia.framework.base.log.HLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;

/* compiled from: MCOnlineUtilsContext */
public class a {
    private static final String TAG = "UtilsContext";
    private static String amX = null;
    private static final int amY = 1;
    private static String[] amZ = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    protected static Context mAppContext = null;

    public static void aN(Context c) {
        mAppContext = c;
    }

    public static void C(String strName) {
        if (strName != null && strName.length() != 0) {
            try {
                Intent intent = mAppContext.getPackageManager().getLaunchIntentForPackage(strName);
                intent.setFlags(268435456);
                mAppContext.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    public static void dq(String packName) {
        ((ActivityManager) mAppContext.getSystemService("activity")).killBackgroundProcesses(packName);
    }

    public static void l(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(activity, amZ, 1);
        }
    }

    public static String getHostAddress() {
        return "127.0.0.1";
    }

    public static void dr(String ip) {
        amX = ip;
    }

    public static String BW() {
        return amX;
    }

    private static String BX() {
        String IP = "";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://www.xcfuw.com/help/getip.php").openConnection();
            connection.setUseCaches(false);
            if (connection.getResponseCode() == 200) {
                return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            }
            IP = "";
            HLog.error(hlx.data.localstore.a.bKA_TIPS, "网络连接异常，无法获取IP地址！", new Object[0]);
            return IP;
        } catch (Exception e) {
            IP = "";
            HLog.error(hlx.data.localstore.a.bKA_TIPS, "获取IP地址时出现异常，异常信息是：" + e.toString(), new Object[0]);
            return IP;
        }
    }

    public static void e(String hint, byte[] b) {
        System.out.print(hint);
        for (byte b2 : b) {
            String hex = Integer.toHexString(b2 & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }

    public static int BY() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int port = serverSocket.getLocalPort();
        HLog.verbose(TAG, "系统分配的端口号 port=" + port, new Object[0]);
        return port;
    }
}
