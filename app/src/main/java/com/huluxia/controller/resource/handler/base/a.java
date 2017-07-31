package com.huluxia.controller.resource.handler.base;

import com.huluxia.controller.resource.bean.b;

/* compiled from: AbsResHandler */
public abstract class a<T extends b> implements c {
    private T info;

    public abstract void destroy();

    public abstract void pause(boolean z, boolean z2);

    public abstract boolean process(com.huluxia.controller.resource.dispatcher.a aVar);

    public a(T info) {
        this.info = info;
    }

    public T getInfo() {
        return this.info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
