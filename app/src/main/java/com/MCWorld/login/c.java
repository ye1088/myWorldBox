package com.MCWorld.login;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: ILoginService */
public interface c extends IInterface {

    /* compiled from: ILoginService */
    public static abstract class a extends Binder implements c {
        private static final String DESCRIPTOR = "com.huluxia.login.ILoginService";
        static final int MA = 1;
        static final int MC = 2;
        static final int MD = 3;
        static final int ME = 4;
        static final int MF = 5;
        static final int MG = 6;

        /* compiled from: ILoginService */
        private static class a implements c {
            private IBinder mRemote;

            a(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return a.DESCRIPTOR;
            }

            public void a(String clientid, String name, String encryptPassword, String openid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(a.DESCRIPTOR);
                    _data.writeString(clientid);
                    _data.writeString(name);
                    _data.writeString(encryptPassword);
                    _data.writeString(openid);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void a(String clientid, String email, String encrytPwd, String nick, int gender, long birthday, String avatarFid, String openid, String accessToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(a.DESCRIPTOR);
                    _data.writeString(clientid);
                    _data.writeString(email);
                    _data.writeString(encrytPwd);
                    _data.writeString(nick);
                    _data.writeInt(gender);
                    _data.writeLong(birthday);
                    _data.writeString(avatarFid);
                    _data.writeString(openid);
                    _data.writeString(accessToken);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void a(String clientid, b callback) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(a.DESCRIPTOR);
                    _data.writeString(clientid);
                    if (callback != null) {
                        iBinder = callback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void bO(String clientid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(a.DESCRIPTOR);
                    _data.writeString(clientid);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void bP(String clientid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(a.DESCRIPTOR);
                    _data.writeString(clientid);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void pQ() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(a.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public a() {
            attachInterface(this, DESCRIPTOR);
        }

        public static c c(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof c)) {
                return new a(obj);
            }
            return (c) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    a(data.readString(), data.readString(), data.readString(), data.readString());
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    a(data.readString(), data.readString(), data.readString(), data.readString(), data.readInt(), data.readLong(), data.readString(), data.readString(), data.readString());
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    a(data.readString(), com.MCWorld.login.b.a.b(data.readStrongBinder()));
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    bO(data.readString());
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    bP(data.readString());
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    pQ();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void a(String str, b bVar) throws RemoteException;

    void a(String str, String str2, String str3, String str4) throws RemoteException;

    void a(String str, String str2, String str3, String str4, int i, long j, String str5, String str6, String str7) throws RemoteException;

    void bO(String str) throws RemoteException;

    void bP(String str) throws RemoteException;

    void pQ() throws RemoteException;
}
