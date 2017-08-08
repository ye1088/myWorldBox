package com.MCWorld.framework.base.notification;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import java.lang.reflect.Method;

public abstract class CallbackHandler extends Handler implements ICallback {
    private SparseArray<Method> mHandlerMap;

    public CallbackHandler(Looper looper) {
        super(looper);
        init();
    }

    public CallbackHandler() {
        init();
    }

    private void init() {
        initHandlers();
    }

    private synchronized void initHandlers() {
        if (this.mHandlerMap == null) {
            this.mHandlerMap = new SparseArray();
        }
        for (Method method : getClass().getDeclaredMethods()) {
            MessageHandler an = (MessageHandler) method.getAnnotation(MessageHandler.class);
            if (an != null) {
                this.mHandlerMap.put(an.message(), method);
            }
        }
    }

    private synchronized Method getMessageHandler(int message) {
        if (this.mHandlerMap == null) {
            initHandlers();
        }
        return (Method) this.mHandlerMap.get(message);
    }

    public boolean canHandleMessage(int message) {
        return getMessageHandler(message) != null;
    }

    public void handleMessage(Message msg) {
        try {
            Method handler = getMessageHandler(msg.what);
            Object[] params = (Object[]) msg.obj;
            if (params != null) {
                handler.invoke(this, params);
                return;
            }
            handler.invoke(this, new Object[]{null});
        } catch (Exception e) {
            HLog.error(this, getLog(msg, null, null), new Object[0]);
            HLog.error(this, e);
        }
    }

    private String getLog(Message msg, Object[] params, Method handler) {
        try {
            StringBuilder log = new StringBuilder("handle msg ");
            log.append(msg.what);
            log.append(" error, params = [");
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    if (i > 0) {
                        log.append(", ");
                    }
                    log.append(params[i]);
                }
            }
            log.append("], ");
            log.append("handler = ");
            log.append(handler);
            return log.toString();
        } catch (Exception e) {
            HLog.error(this, "generate error log failed", new Object[0]);
            return "generate error log failed";
        }
    }

    public void callback(int msg, Object... params) {
        if (canHandleMessage(msg)) {
            Message message = obtainMessage();
            message.what = msg;
            message.obj = params;
            sendMessage(message);
        }
    }
}
