package com.tencent.mm.sdk.storage;

import android.os.Looper;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public abstract class MStorage {
    private final MStorageEvent<IOnStorageChange, String> bV = new MStorageEvent<IOnStorageChange, String>(this) {
        final /* synthetic */ MStorage bX;

        {
            this.bX = r1;
        }

        protected /* synthetic */ void processEvent(Object obj, Object obj2) {
            IOnStorageChange iOnStorageChange = (IOnStorageChange) obj;
            String str = (String) obj2;
            MStorage mStorage = this.bX;
            iOnStorageChange.onNotifyChange(str);
        }
    };
    private final MStorageEvent<IOnStorageLoaded, String> bW = new MStorageEvent<IOnStorageLoaded, String>(this) {
        final /* synthetic */ MStorage bX;

        {
            this.bX = r1;
        }

        protected /* synthetic */ void processEvent(Object obj, Object obj2) {
            IOnStorageLoaded iOnStorageLoaded = (IOnStorageLoaded) obj;
            MStorage mStorage = this.bX;
            iOnStorageLoaded.onNotifyLoaded();
        }
    };

    public interface IOnStorageChange {
        void onNotifyChange(String str);
    }

    public interface IOnStorageLoaded {
        void onNotifyLoaded();
    }

    public void add(IOnStorageChange iOnStorageChange) {
        this.bV.add(iOnStorageChange, Looper.getMainLooper());
    }

    public void addLoadedListener(IOnStorageLoaded iOnStorageLoaded) {
        this.bW.add(iOnStorageLoaded, Looper.getMainLooper());
    }

    public void doNotify() {
        this.bV.event(WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD);
        this.bV.doNotify();
    }

    public void doNotify(String str) {
        this.bV.event(str);
        this.bV.doNotify();
    }

    public void lock() {
        this.bV.lock();
    }

    public void remove(IOnStorageChange iOnStorageChange) {
        this.bV.remove(iOnStorageChange);
    }

    public void removeLoadedListener(IOnStorageLoaded iOnStorageLoaded) {
        this.bW.remove(iOnStorageLoaded);
    }

    public void unlock() {
        this.bV.unlock();
    }
}
