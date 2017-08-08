package com.MCWorld.controller.resource.handler.base;

import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;

/* compiled from: DownloadResHandler */
public abstract class b<T extends ResTaskInfo> extends a<T> implements CancelListener, ErrorListener, Listener, ProgressListener {
    public b(T info) {
        super(info);
    }
}
