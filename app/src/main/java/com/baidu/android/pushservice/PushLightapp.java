package com.baidu.android.pushservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.baidu.android.pushservice.aidl.IPushService;
import com.baidu.android.pushservice.aidl.IPushService.Stub;
import com.baidu.android.pushservice.aidl.IPushServiceListener;
import java.util.List;

public class PushLightapp {
    public static final String ACTION_REGISTER_SYNC = "com.baidu.android.pushservice.action.BIND_SYNC";
    public static final int CONTEXT_FLAG_MODE_MULTI_PROCESS = 4;
    public static final String KEY_PRIORITY2 = "priority2";
    public static final String SETTINGS_KEY_CURRENT_PACKAGE_NAME = "com.baidu.push.cur_pkg";
    public static final String SHARED_NAME_SETTINGS = ".push_sync";
    private static final int b = -2;
    private static final boolean c = false;
    private static final String d = "PushLightapp";
    private static PushLightapp e = null;
    private static IPushLightappListener f = null;
    private static final String i = "com.baidu.android.pushservice.PushService";
    private static final String j = "com.baidu.android.pushservice.action.PUSH_SERVICE";
    private static boolean k = false;
    private static int l = 3500;
    private static int m = 0;
    private static final int n = 23;
    IPushService a;
    private Context g;
    private boolean h = false;
    private ServiceConnection o = new ServiceConnection(this) {
        final /* synthetic */ PushLightapp a;

        {
            this.a = r1;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (PushLightapp.k) {
                this.a.h = true;
                this.a.a = Stub.asInterface(iBinder);
                PushLightapp.m = this.a.g();
                if (PushLightapp.f == null) {
                    return;
                }
                if (PushLightapp.e != null) {
                    PushLightapp.f.initialComplete(PushLightapp.e);
                } else if (this.a.g != null) {
                    PushLightapp.e = new PushLightapp(this.a.g);
                }
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.a.e();
        }
    };

    public PushLightapp(Context context) {
        this.g = context.getApplicationContext();
        e();
    }

    private static PushLightapp a(Context context) {
        if (context == null) {
            return null;
        }
        if (e == null) {
            e = new PushLightapp(context);
        } else if (e.a == null) {
            e.e();
        }
        return e;
    }

    private String a(Context context, String str) {
        Context context2;
        String packageName = context.getPackageName();
        List<ResolveInfo> friendPackages = getFriendPackages(context.getApplicationContext());
        if (friendPackages.size() <= 1) {
            return packageName;
        }
        long j = context.getSharedPreferences(context.getPackageName() + SHARED_NAME_SETTINGS, 5).getLong(KEY_PRIORITY2, 0);
        long j2 = 23 << 4;
        if (j >= j2) {
            j2 = j;
        }
        j = j2;
        String str2 = packageName;
        for (ResolveInfo resolveInfo : friendPackages) {
            SharedPreferences sharedPreferences;
            String str3 = resolveInfo.activityInfo.packageName;
            Context context3 = null;
            SharedPreferences sharedPreferences2 = null;
            try {
                context3 = context.createPackageContext(str3, 2);
                sharedPreferences2 = context3.getSharedPreferences(str3 + SHARED_NAME_SETTINGS, 1);
                context2 = context3;
                sharedPreferences = sharedPreferences2;
            } catch (Exception e) {
                context2 = context3;
                sharedPreferences = sharedPreferences2;
            }
            if (sharedPreferences != null) {
                String str4;
                long j3 = sharedPreferences.getLong(KEY_PRIORITY2, 0);
                if ((j3 > j || (j3 == j && str3.equals(str))) && c(r6, str3)) {
                    str4 = str3;
                    j2 = j3;
                } else {
                    long j4 = j;
                    str4 = str2;
                    j2 = j4;
                }
                str2 = str4;
                j = j2;
            }
        }
        return str2;
    }

    private String b(Context context) {
        String string = System.getString(context.getContentResolver(), SETTINGS_KEY_CURRENT_PACKAGE_NAME);
        return (string != null && string.equals(context.getPackageName()) && c(context, string)) ? string : a(context, string);
    }

    private static boolean b(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return context.getPackageManager().getApplicationInfo(str, 8192) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean c(Context context, String str) {
        if (!b(context, str)) {
            return false;
        }
        try {
            Intent intent = new Intent(j);
            intent.setPackage(str);
            List queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
            for (int i = 0; i < queryIntentServices.size(); i++) {
                if (i.equals(((ResolveInfo) queryIntentServices.get(i)).serviceInfo.name)) {
                    boolean z = ((ResolveInfo) queryIntentServices.get(i)).serviceInfo.enabled && ((ResolveInfo) queryIntentServices.get(i)).serviceInfo.exported;
                    return z;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void e() {
        if (this.g == null) {
            f();
            return;
        }
        if (k || this.a != null || m > 0) {
            f();
        }
        k = true;
        Intent intent = new Intent();
        intent.setClassName(b(this.g), i);
        this.g.bindService(intent, this.o, 1);
        new Thread(this) {
            final /* synthetic */ PushLightapp a;

            {
                this.a = r1;
            }

            public void run() {
                try {
                    AnonymousClass1.sleep((long) PushLightapp.l);
                    if (PushLightapp.k && !this.a.h) {
                        this.a.f();
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }

    private void f() {
        try {
            this.a = null;
            k = false;
            m = 0;
            this.h = false;
            if (this.g != null) {
                this.g.unbindService(this.o);
            }
        } catch (Exception e) {
        }
    }

    private int g() {
        if (this.a != null) {
            try {
                return this.a.getPushVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static List<ResolveInfo> getFriendPackages(Context context) {
        return context.getPackageManager().queryBroadcastReceivers(new Intent(ACTION_REGISTER_SYNC), 0);
    }

    public static synchronized void getInstanceAsync(Context context, IPushLightappListener iPushLightappListener) {
        synchronized (PushLightapp.class) {
            if (e == null || e.a == null) {
                f = iPushLightappListener;
                a(context);
            } else if (iPushLightappListener != null) {
                iPushLightappListener.initialComplete(e);
            }
        }
    }

    public boolean clearNewMsgNum(String str) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.clearNewMsgNum(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public int deleteAllMsg(String str, int i) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.deleteAllMsg(str, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -2;
    }

    public int deleteMsg(String str) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.deleteMsg(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -2;
    }

    public void destroy() {
        if (this.h) {
            f();
            e = null;
        }
    }

    public String getMsgs(String str, int i, boolean z, int i2, int i3) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.getMsgs(str, i, z, i2, i3);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getNewMsgNum(String str) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.getNewMsgNum(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -2;
    }

    public String getSubcribeApps() {
        if (this.a != null && m >= 23) {
            try {
                return this.a.getSubcribedApps();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSubscribedAppids() {
        if (this.a != null && m >= 23) {
            try {
                return this.a.getSubscribedAppids();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSubscribedAppinfos(String str) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.getSubscribedAppinfos(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getUnreadMsgNumber(String str, int i) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.getUnreadMsgNumber(str, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -2;
    }

    public boolean isReady() {
        return this.a != null;
    }

    public boolean register(String str, String str2) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.register(str, str2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean register(String str, String str2, String str3, String str4) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.registerRunTime(str, str2, str3, str4);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean removeBlacklist(String str, String str2) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.removeBlacklist(str, str2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public int setAllMsgRead(String str, int i) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.setAllMsgRead(str, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -2;
    }

    public int setMsgRead(String str) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.setMsgRead(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -2;
    }

    public boolean setNotifySwitch(String str, boolean z) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.setNotifySwitch(str, z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void subscribeLight(String str, String str2, boolean z, final IPushLightappListener iPushLightappListener) {
        if (this.a != null && m >= 23) {
            try {
                this.a.subscribeLight(str, str2, z, new IPushServiceListener.Stub(this) {
                    final /* synthetic */ PushLightapp b;

                    public void onSubscribe(int i, String str) throws RemoteException {
                        if (iPushLightappListener != null) {
                            iPushLightappListener.onSubscribeResult(i, str);
                        }
                    }

                    public void onUnbindLight(int i, String str) throws RemoteException {
                    }

                    public void onUnsubscribe(int i, String str) throws RemoteException {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                if (iPushLightappListener != null) {
                    iPushLightappListener.onSubscribeResult(PushConstants.ERROR_AIDL_FAIL, PushConstants.ERROR_AIDL_FAIL_EXCEPTION);
                }
            }
        } else if (iPushLightappListener != null) {
            iPushLightappListener.onSubscribeResult(PushConstants.ERROR_AIDL_FAIL, PushConstants.ERROR_AIDL_FAIL_NO_PUSHSERVICE);
        }
    }

    public void unbindLight(String str, String str2, final IPushLightappListener iPushLightappListener) {
        if (this.a == null || m < 23) {
            iPushLightappListener.onUnbindLightResult(PushConstants.ERROR_AIDL_FAIL, PushConstants.ERROR_AIDL_FAIL_NO_PUSHSERVICE);
            return;
        }
        try {
            this.a.unbindLight(str, str2, new IPushServiceListener.Stub(this) {
                final /* synthetic */ PushLightapp b;

                public void onSubscribe(int i, String str) throws RemoteException {
                }

                public void onUnbindLight(int i, String str) throws RemoteException {
                    if (iPushLightappListener != null) {
                        iPushLightappListener.onUnbindLightResult(i, str);
                    }
                }

                public void onUnsubscribe(int i, String str) throws RemoteException {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (iPushLightappListener != null) {
                iPushLightappListener.onUnbindLightResult(PushConstants.ERROR_AIDL_FAIL, PushConstants.ERROR_AIDL_FAIL_EXCEPTION);
            }
        }
    }

    public void unsubscribeLight(String str, String str2, final IPushLightappListener iPushLightappListener) {
        if (this.a != null && m >= 23) {
            try {
                this.a.unsubscribeLight(str, str2, new IPushServiceListener.Stub(this) {
                    final /* synthetic */ PushLightapp b;

                    public void onSubscribe(int i, String str) throws RemoteException {
                    }

                    public void onUnbindLight(int i, String str) throws RemoteException {
                    }

                    public void onUnsubscribe(int i, String str) throws RemoteException {
                        if (iPushLightappListener != null) {
                            iPushLightappListener.onUnsubscribeResult(i, str);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                if (iPushLightappListener != null) {
                    iPushLightappListener.onUnsubscribeResult(PushConstants.ERROR_AIDL_FAIL, PushConstants.ERROR_AIDL_FAIL_EXCEPTION);
                }
            }
        } else if (iPushLightappListener != null) {
            iPushLightappListener.onUnsubscribeResult(PushConstants.ERROR_AIDL_FAIL, PushConstants.ERROR_AIDL_FAIL_NO_PUSHSERVICE);
        }
    }

    public boolean updateBlacklist(String str, String str2, int i) {
        if (this.a != null && m >= 23) {
            try {
                return this.a.updateBlacklist(str, str2, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
