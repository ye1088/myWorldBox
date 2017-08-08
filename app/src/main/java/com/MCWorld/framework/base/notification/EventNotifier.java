package com.MCWorld.framework.base.notification;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class EventNotifier {
    private static final ExecutorService mThread = Executors.newCachedThreadPool();
    private final ConcurrentHashMap<Object, CopyOnWriteArraySet<ICallback>> mCallbacks = new ConcurrentHashMap();

    EventNotifier() {
    }

    public void add(Object key, ICallback callback) {
        CopyOnWriteArraySet<ICallback> set = getSet(key, true);
        removeCallbackFromSet(set, callback);
        set.add(callback);
    }

    private CopyOnWriteArraySet<ICallback> getSet(Object key, boolean create) {
        CopyOnWriteArraySet<ICallback> set = (CopyOnWriteArraySet) this.mCallbacks.get(key);
        if (set == null) {
            synchronized (this) {
                try {
                    if (this.mCallbacks.get(key) == null && create) {
                        CopyOnWriteArraySet<ICallback> set2 = new CopyOnWriteArraySet();
                        try {
                            this.mCallbacks.put(key, set2);
                            set = set2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            set = set2;
                            throw th2;
                        }
                    }
                    set = (CopyOnWriteArraySet) this.mCallbacks.get(key);
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return set;
    }

    private void removeCallbackFromSet(CopyOnWriteArraySet<ICallback> set, ICallback callback) {
        if (set != null && callback != null) {
            set.remove(callback);
        }
    }

    public void add(ICallback callback) {
        add(this, callback);
    }

    public void remove(ICallback callback) {
        for (CopyOnWriteArraySet<ICallback> set : this.mCallbacks.values()) {
            removeCallbackFromSet(set, callback);
        }
    }

    public boolean notifyCallbacks(int message) {
        return notifyCallbacks(this, message, (Object[]) null);
    }

    public boolean notifyCallbacks(Object key, int message) {
        return notifyCallbacks(key, message, (Object[]) null);
    }

    public boolean notifyCallbacks(int message, Object... params) {
        return notifyCallbacks(this, message, params);
    }

    public boolean notifyCallbacks(Object key, final int message, final Object... params) {
        CopyOnWriteArraySet<ICallback> set = getSet(key, false);
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                final ICallback callback = (ICallback) it.next();
                mThread.execute(new Runnable() {
                    public void run() {
                        callback.callback(message, params);
                    }
                });
            }
        }
        return true;
    }
}
