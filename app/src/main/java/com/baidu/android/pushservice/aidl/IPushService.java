package com.baidu.android.pushservice.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPushService extends IInterface {

    public static abstract class Stub extends Binder implements IPushService {
        static final int a = 1;
        static final int b = 2;
        static final int c = 3;
        static final int d = 4;
        static final int e = 5;
        static final int f = 6;
        static final int g = 7;
        static final int h = 8;
        static final int i = 9;
        static final int j = 10;
        static final int k = 11;
        static final int l = 12;
        static final int m = 13;
        static final int n = 14;
        static final int o = 15;
        static final int p = 16;
        static final int q = 17;
        static final int r = 18;
        static final int s = 19;
        static final int t = 20;
        private static final String u = "com.baidu.android.pushservice.aidl.IPushService";

        private static class a implements IPushService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public String a() {
                return Stub.u;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public boolean clearNewMsgNum(String str) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int deleteAllMsg(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int deleteMsg(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getMsgs(String str, int i, boolean z, int i2, int i3) throws RemoteException {
                int i4 = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (z) {
                        i4 = 1;
                    }
                    obtain.writeInt(i4);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getNewMsgNum(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getPushVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getSubcribedApps() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getSubscribedAppids() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getSubscribedAppinfos(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getUnreadMsgNumber(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean register(String str, String str2) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean registerRunTime(String str, String str2, String str3, String str4) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean removeBlacklist(String str, String str2) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int setAllMsgRead(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int setMsgRead(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setNotifySwitch(String str, boolean z) throws RemoteException {
                boolean z2 = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z2 = false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z2;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void subscribeLight(String str, String str2, boolean z, IPushServiceListener iPushServiceListener) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iPushServiceListener != null ? iPushServiceListener.asBinder() : null);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void unbindLight(String str, String str2, IPushServiceListener iPushServiceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iPushServiceListener != null ? iPushServiceListener.asBinder() : null);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void unsubscribeLight(String str, String str2, IPushServiceListener iPushServiceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iPushServiceListener != null ? iPushServiceListener.asBinder() : null);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean updateBlacklist(String str, String str2, int i) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.u);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, u);
        }

        public static IPushService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(u);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPushService)) ? new a(iBinder) : (IPushService) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int i3 = 0;
            String subcribedApps;
            boolean register;
            switch (i) {
                case 1:
                    boolean z;
                    parcel.enforceInterface(u);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    subscribeLight(readString, readString2, z, com.baidu.android.pushservice.aidl.IPushServiceListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(u);
                    unsubscribeLight(parcel.readString(), parcel.readString(), com.baidu.android.pushservice.aidl.IPushServiceListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(u);
                    unbindLight(parcel.readString(), parcel.readString(), com.baidu.android.pushservice.aidl.IPushServiceListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(u);
                    subcribedApps = getSubcribedApps();
                    parcel2.writeNoException();
                    parcel2.writeString(subcribedApps);
                    return true;
                case 5:
                    parcel.enforceInterface(u);
                    subcribedApps = getSubscribedAppids();
                    parcel2.writeNoException();
                    parcel2.writeString(subcribedApps);
                    return true;
                case 6:
                    parcel.enforceInterface(u);
                    subcribedApps = getSubscribedAppinfos(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(subcribedApps);
                    return true;
                case 7:
                    parcel.enforceInterface(u);
                    subcribedApps = getMsgs(parcel.readString(), parcel.readInt(), parcel.readInt() != 0, parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(subcribedApps);
                    return true;
                case 8:
                    parcel.enforceInterface(u);
                    i3 = getUnreadMsgNumber(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 9:
                    parcel.enforceInterface(u);
                    i3 = setMsgRead(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 10:
                    parcel.enforceInterface(u);
                    i3 = setAllMsgRead(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 11:
                    parcel.enforceInterface(u);
                    i3 = deleteMsg(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 12:
                    parcel.enforceInterface(u);
                    i3 = deleteAllMsg(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 13:
                    parcel.enforceInterface(u);
                    register = register(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    if (register) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 14:
                    parcel.enforceInterface(u);
                    register = registerRunTime(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    if (register) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 15:
                    parcel.enforceInterface(u);
                    register = updateBlacklist(parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    if (register) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 16:
                    parcel.enforceInterface(u);
                    register = removeBlacklist(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    if (register) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 17:
                    parcel.enforceInterface(u);
                    i3 = getNewMsgNum(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 18:
                    parcel.enforceInterface(u);
                    register = clearNewMsgNum(parcel.readString());
                    parcel2.writeNoException();
                    if (register) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 19:
                    parcel.enforceInterface(u);
                    register = setNotifySwitch(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    if (register) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 20:
                    parcel.enforceInterface(u);
                    i3 = getPushVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 1598968902:
                    parcel2.writeString(u);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    boolean clearNewMsgNum(String str) throws RemoteException;

    int deleteAllMsg(String str, int i) throws RemoteException;

    int deleteMsg(String str) throws RemoteException;

    String getMsgs(String str, int i, boolean z, int i2, int i3) throws RemoteException;

    int getNewMsgNum(String str) throws RemoteException;

    int getPushVersion() throws RemoteException;

    String getSubcribedApps() throws RemoteException;

    String getSubscribedAppids() throws RemoteException;

    String getSubscribedAppinfos(String str) throws RemoteException;

    int getUnreadMsgNumber(String str, int i) throws RemoteException;

    boolean register(String str, String str2) throws RemoteException;

    boolean registerRunTime(String str, String str2, String str3, String str4) throws RemoteException;

    boolean removeBlacklist(String str, String str2) throws RemoteException;

    int setAllMsgRead(String str, int i) throws RemoteException;

    int setMsgRead(String str) throws RemoteException;

    boolean setNotifySwitch(String str, boolean z) throws RemoteException;

    void subscribeLight(String str, String str2, boolean z, IPushServiceListener iPushServiceListener) throws RemoteException;

    void unbindLight(String str, String str2, IPushServiceListener iPushServiceListener) throws RemoteException;

    void unsubscribeLight(String str, String str2, IPushServiceListener iPushServiceListener) throws RemoteException;

    boolean updateBlacklist(String str, String str2, int i) throws RemoteException;
}
