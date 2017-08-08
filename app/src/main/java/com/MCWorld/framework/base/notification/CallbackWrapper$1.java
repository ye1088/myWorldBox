package com.MCWorld.framework.base.notification;

import com.MCWorld.framework.base.log.HLog;
import java.lang.reflect.Method;
import java.util.Arrays;

class CallbackWrapper$1 implements Runnable {
    final /* synthetic */ CallbackWrapper this$0;
    final /* synthetic */ Method val$method;
    final /* synthetic */ Object[] val$params;

    CallbackWrapper$1(CallbackWrapper this$0, Method method, Object[] objArr) {
        this.this$0 = this$0;
        this.val$method = method;
        this.val$params = objArr;
    }

    public void run() {
        try {
            this.val$method.invoke(CallbackWrapper.access$000(this.this$0), this.val$params);
        } catch (Throwable e) {
            HLog.error(this, "error happened on invoking %s, params = %s, listener = %s, error = %s", new Object[]{this.val$method, Arrays.toString(this.val$params), CallbackWrapper.access$000(this.this$0), e.toString()});
        }
    }
}
