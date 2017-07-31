package com.huluxia.framework.base.notification;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import java.lang.reflect.Method;

public class CallbackWrapper implements ICallback {
    private static final Handler gHandler = new Handler(Looper.getMainLooper());
    private final SparseArray<Method> mHandlerMap = new SparseArray();
    private final Object mListener;

    public CallbackWrapper(Object listener) {
        this.mListener = listener;
        for (Method method : listener.getClass().getDeclaredMethods()) {
            MessageHandler an = (MessageHandler) method.getAnnotation(MessageHandler.class);
            if (an != null) {
                this.mHandlerMap.put(an.message(), method);
            }
        }
    }

    public boolean isValid() {
        return this.mListener != null && this.mHandlerMap.size() > 0;
    }

    public void callback(int msg, Object... params) {
        if (isValid()) {
            Method method = (Method) this.mHandlerMap.get(msg);
            if (method != null) {
                gHandler.post(new 1(this, method, params));
            }
        }
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof CallbackWrapper)) {
            return false;
        }
        CallbackWrapper dst = (CallbackWrapper) o;
        if (this.mListener == dst.mListener || (this.mListener != null && this.mListener.equals(dst.mListener))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.mListener == null ? 0 : this.mListener.hashCode();
    }
}
