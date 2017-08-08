package com.MCWorld.login;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: IAsyncInterface */
class b$a$a implements b {
    private IBinder mRemote;

    b$a$a(IBinder remote) {
        this.mRemote = remote;
    }

    public IBinder asBinder() {
        return this.mRemote;
    }

    public String getInterfaceDescriptor() {
        return "com.huluxia.login.IAsyncInterface";
    }

    public void a(boolean succ, String email, int code, String msg, int error) throws RemoteException {
        int i = 1;
        Parcel _data = Parcel.obtain();
        try {
            _data.writeInterfaceToken("com.huluxia.login.IAsyncInterface");
            if (!succ) {
                i = 0;
            }
            _data.writeInt(i);
            _data.writeString(email);
            _data.writeInt(code);
            _data.writeString(msg);
            _data.writeInt(error);
            this.mRemote.transact(1, _data, null, 1);
        } finally {
            _data.recycle();
        }
    }

    public void a(boolean succ, String client, String email, String encryptPwd, String openid, String session, int code, String msg, int error) throws RemoteException {
        int i = 1;
        Parcel _data = Parcel.obtain();
        try {
            _data.writeInterfaceToken("com.huluxia.login.IAsyncInterface");
            if (!succ) {
                i = 0;
            }
            _data.writeInt(i);
            _data.writeString(client);
            _data.writeString(email);
            _data.writeString(encryptPwd);
            _data.writeString(openid);
            _data.writeString(session);
            _data.writeInt(code);
            _data.writeString(msg);
            _data.writeInt(error);
            this.mRemote.transact(2, _data, null, 1);
        } finally {
            _data.recycle();
        }
    }

    public void a(boolean succ, String client, String session, int error) throws RemoteException {
        int i = 1;
        Parcel _data = Parcel.obtain();
        try {
            _data.writeInterfaceToken("com.huluxia.login.IAsyncInterface");
            if (!succ) {
                i = 0;
            }
            _data.writeInt(i);
            _data.writeString(client);
            _data.writeString(session);
            _data.writeInt(error);
            this.mRemote.transact(3, _data, null, 1);
        } finally {
            _data.recycle();
        }
    }

    public void cP(int version) throws RemoteException {
        Parcel _data = Parcel.obtain();
        try {
            _data.writeInterfaceToken("com.huluxia.login.IAsyncInterface");
            _data.writeInt(version);
            this.mRemote.transact(4, _data, null, 1);
        } finally {
            _data.recycle();
        }
    }
}
