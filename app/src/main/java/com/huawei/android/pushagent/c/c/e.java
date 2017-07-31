package com.huawei.android.pushagent.c.c;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;

public class e {
    private static boolean a = false;
    private static boolean b = false;

    public static void a(int i, int i2) {
        RemoteException e;
        Throwable th;
        Parcel parcel = null;
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket cmd is " + i + ", param is " + i2);
        Parcel obtain;
        try {
            IBinder service = ServiceManager.getService("connectivity");
            if (service == null) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "get connectivity service failed ");
                if (parcel != null) {
                    parcel.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                    return;
                }
                return;
            }
            obtain = Parcel.obtain();
            try {
                obtain.writeInt(Process.myPid());
                obtain.writeInt(i);
                obtain.writeInt(i2);
                parcel = Parcel.obtain();
                service.transact(ErrorCode.ERROR_NOMATCH_COREVERSION, obtain, parcel, 0);
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } catch (RemoteException e2) {
                e = e2;
                try {
                    com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "ctrlSocket error:" + e.getMessage());
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                th = e3;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "ctrlSocket error:", th);
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            }
        } catch (RemoteException e4) {
            e = e4;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "ctrlSocket error:" + e.getMessage());
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
        } catch (Exception e5) {
            th = e5;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "ctrlSocket error:", th);
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
        } catch (Throwable th3) {
            th = th3;
            obtain = parcel;
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    public static void a(String str) {
        Parcel obtain;
        RemoteException e;
        Throwable th;
        Parcel parcel = null;
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlScoket registerPackage " + str);
        if (!TextUtils.isEmpty(str)) {
            try {
                IBinder service = ServiceManager.getService("connectivity");
                if (service == null) {
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "get connectivity service failed ");
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                        return;
                    }
                    return;
                }
                obtain = Parcel.obtain();
                try {
                    obtain.writeString(str);
                    parcel = Parcel.obtain();
                    service.transact(ErrorCode.ERROR_HOSTAPP_UNAVAILABLE, obtain, parcel, 0);
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    try {
                        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "registerPackage error:" + e.getMessage());
                        if (obtain != null) {
                            obtain.recycle();
                        }
                        if (parcel != null) {
                            parcel.recycle();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (obtain != null) {
                            obtain.recycle();
                        }
                        if (parcel != null) {
                            parcel.recycle();
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    th = e3;
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "registerPackage error:", th);
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                }
            } catch (RemoteException e4) {
                e = e4;
                obtain = parcel;
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "registerPackage error:" + e.getMessage());
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } catch (Exception e5) {
                th = e5;
                obtain = parcel;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "registerPackage error:", th);
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } catch (Throwable th3) {
                th = th3;
                obtain = parcel;
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
                throw th;
            }
        }
    }

    public static String[] a() {
        Parcel obtain;
        RemoteException e;
        Throwable th;
        Throwable e2;
        Parcel parcel = null;
        String[] strArr = new String[0];
        try {
            IBinder service = ServiceManager.getService("connectivity");
            if (service == null) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "get connectivity service failed ");
                if (parcel != null) {
                    parcel.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } else {
                obtain = Parcel.obtain();
                try {
                    parcel = Parcel.obtain();
                    service.transact(1004, obtain, parcel, 0);
                    Object readString = parcel.readString();
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket whitepackages is:" + readString);
                    if (!TextUtils.isEmpty(readString)) {
                        strArr = readString.split("\t");
                    }
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                } catch (RemoteException e3) {
                    e = e3;
                    try {
                        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "ctrlSocket error:" + e.getMessage());
                        if (obtain != null) {
                            obtain.recycle();
                        }
                        if (parcel != null) {
                            parcel.recycle();
                        }
                        return strArr;
                    } catch (Throwable th2) {
                        th = th2;
                        if (obtain != null) {
                            obtain.recycle();
                        }
                        if (parcel != null) {
                            parcel.recycle();
                        }
                        throw th;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "ctrlSocket error:", e2);
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    return strArr;
                }
            }
        } catch (RemoteException e5) {
            e = e5;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "ctrlSocket error:" + e.getMessage());
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            return strArr;
        } catch (Exception e6) {
            e2 = e6;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "ctrlSocket error:", e2);
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            return strArr;
        } catch (Throwable th3) {
            th = th3;
            obtain = parcel;
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
        return strArr;
    }

    public static int b() {
        RemoteException e;
        Throwable th;
        Throwable e2;
        Parcel parcel = null;
        int i = -1;
        Parcel obtain;
        try {
            IBinder service = ServiceManager.getService("connectivity");
            if (service == null) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "get connectivity service failed ");
                if (parcel != null) {
                    parcel.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
                return i;
            }
            obtain = Parcel.obtain();
            try {
                parcel = Parcel.obtain();
                service.transact(ErrorCode.ERROR_COREVERSION_CHANGED, obtain, parcel, 0);
                i = parcel.readInt();
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } catch (RemoteException e3) {
                e = e3;
                try {
                    com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "getCtrlSocketModel error:" + e.getMessage());
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket level is:" + i);
                    return i;
                } catch (Throwable th2) {
                    th = th2;
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e2 = e4;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "getCtrlSocketModel error:", e2);
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket level is:" + i);
                return i;
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket level is:" + i);
            return i;
        } catch (RemoteException e5) {
            e = e5;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "getCtrlSocketModel error:" + e.getMessage());
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket level is:" + i);
            return i;
        } catch (Exception e6) {
            e2 = e6;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "getCtrlSocketModel error:", e2);
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket level is:" + i);
            return i;
        } catch (Throwable th3) {
            th = th3;
            obtain = parcel;
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }

    public static void b(String str) {
        Parcel obtain;
        RemoteException e;
        Throwable th;
        Parcel parcel = null;
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlScoket deregisterPackage " + str);
        if (!TextUtils.isEmpty(str)) {
            try {
                IBinder service = ServiceManager.getService("connectivity");
                if (service == null) {
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                        return;
                    }
                    return;
                }
                obtain = Parcel.obtain();
                try {
                    obtain.writeString(str);
                    parcel = Parcel.obtain();
                    service.transact(ErrorCode.ERROR_FORCE_SYSWEBVIEW, obtain, parcel, 0);
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    try {
                        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "deregisterPackage error:" + e.getMessage());
                        if (obtain != null) {
                            obtain.recycle();
                        }
                        if (parcel != null) {
                            parcel.recycle();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (obtain != null) {
                            obtain.recycle();
                        }
                        if (parcel != null) {
                            parcel.recycle();
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    th = e3;
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "deregisterPackage error:", th);
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                }
            } catch (RemoteException e4) {
                e = e4;
                obtain = parcel;
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "deregisterPackage error:" + e.getMessage());
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } catch (Exception e5) {
                th = e5;
                obtain = parcel;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "deregisterPackage error:", th);
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } catch (Throwable th3) {
                th = th3;
                obtain = parcel;
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
                throw th;
            }
        }
    }

    public static synchronized boolean c() {
        boolean z;
        synchronized (e.class) {
            String str = "v2";
            if (!a) {
                a = true;
                b = str.equals(d());
            }
            z = b;
        }
        return z;
    }

    private static String d() {
        RemoteException e;
        Throwable th;
        Throwable e2;
        Parcel parcel = null;
        String str = "";
        Parcel obtain;
        try {
            IBinder service = ServiceManager.getService("connectivity");
            if (service == null) {
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "get connectivity service failed ");
                if (parcel != null) {
                    parcel.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
                return str;
            }
            obtain = Parcel.obtain();
            try {
                parcel = Parcel.obtain();
                service.transact(ErrorCode.ERROR_COREVERSION_TOOLOW, obtain, parcel, 0);
                str = parcel.readString();
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
            } catch (RemoteException e3) {
                e = e3;
                try {
                    com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "getCtrlSocketVersion error:" + e.getMessage());
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket version is:" + str);
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e2 = e4;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "getCtrlSocketVersion error:", e2);
                if (obtain != null) {
                    obtain.recycle();
                }
                if (parcel != null) {
                    parcel.recycle();
                }
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket version is:" + str);
                return str;
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket version is:" + str);
            return str;
        } catch (RemoteException e5) {
            e = e5;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "getCtrlSocketVersion error:" + e.getMessage());
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket version is:" + str);
            return str;
        } catch (Exception e6) {
            e2 = e6;
            obtain = parcel;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "getCtrlSocketVersion error:", e2);
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "ctrlSocket version is:" + str);
            return str;
        } catch (Throwable th3) {
            th = th3;
            obtain = parcel;
            if (obtain != null) {
                obtain.recycle();
            }
            if (parcel != null) {
                parcel.recycle();
            }
            throw th;
        }
    }
}
