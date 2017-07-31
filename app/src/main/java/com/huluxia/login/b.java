package com.huluxia.login;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: IAsyncInterface */
public interface b extends IInterface {

    /* compiled from: IAsyncInterface */
    public static abstract class a extends Binder implements b {
        private static final String DESCRIPTOR = "com.huluxia.login.IAsyncInterface";
        static final int Mw = 1;
        static final int Mx = 2;
        static final int My = 3;
        static final int Mz = 4;

        public a() {
            attachInterface(this, DESCRIPTOR);
        }

        public static b b(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof b)) {
                return new a(obj);
            }
            return (b) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg0 = false;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    a(_arg0, data.readString(), data.readInt(), data.readString(), data.readInt());
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    a(_arg0, data.readString(), data.readString(), data.readString(), data.readString(), data.readString(), data.readInt(), data.readString(), data.readInt());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = true;
                    }
                    a(_arg0, data.readString(), data.readString(), data.readInt());
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    cP(data.readInt());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(boolean z, String str, int i, String str2, int i2) throws RemoteException;

    void a(boolean z, String str, String str2, int i) throws RemoteException;

    void a(boolean z, String str, String str2, String str3, String str4, String str5, int i, String str6, int i2) throws RemoteException;

    void cP(int i) throws RemoteException;
}
