package com.huawei.android.microkernel;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.huawei.android.pushagent.c.a.e;

public class MKService extends Service {
    private static final String TAG = "PushLogAC2705";
    private static Context appContext = null;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public Context getContext() {
        return appContext == null ? this : appContext;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void stopService() {
        if (appContext == null) {
            e.a(TAG, " stopService,stop Pushservice ");
            stopSelf();
            return;
        }
        e.a(TAG, " stopService,stop Push Microkernel service ");
        ComponentName componentName = new ComponentName(appContext, "com.huawei.deviceCloud.microKernel.push.PushMKService");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setPackage(appContext.getPackageName());
        appContext.stopService(intent);
    }
}
