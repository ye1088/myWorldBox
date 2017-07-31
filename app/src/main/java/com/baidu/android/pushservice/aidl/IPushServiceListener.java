package com.baidu.android.pushservice.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPushServiceListener extends IInterface {

    public static abstract class Stub extends Binder implements IPushServiceListener {
        private static final String a = "com.baidu.android.pushservice.aidl.IPushServiceListener";
        static final int c = 1;
        static final int d = 2;
        static final int e = 3;

        private static class a implements IPushServiceListener {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public String a() {
                return Stub.a;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void onSubscribe(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.a);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onUnbindLight(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.a);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onUnsubscribe(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.a);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, a);
        }

        public static IPushServiceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(a);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPushServiceListener)) ? new a(iBinder) : (IPushServiceListener) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface(a);
                    onSubscribe(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(a);
                    onUnsubscribe(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(a);
                    onUnbindLight(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString(a);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void onSubscribe(int i, String str) throws RemoteException;

    void onUnbindLight(int i, String str) throws RemoteException;

    void onUnsubscribe(int i, String str) throws RemoteException;
}
