package com.huluxia.framework.base.utils;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.huluxia.framework.base.log.HLog;

public class SafeDispatchHandler extends Handler {
    public SafeDispatchHandler(Looper looper) {
        super(looper);
    }

    public SafeDispatchHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public SafeDispatchHandler(Callback callback) {
        super(callback);
    }

    public void dispatchMessage(Message msg) {
        try {
            super.dispatchMessage(msg);
        } catch (Exception e) {
            HLog.error(this, e.getMessage(), e, new Object[0]);
        } catch (Error error) {
            HLog.error(this, error.getMessage(), error, new Object[0]);
        }
    }
}
