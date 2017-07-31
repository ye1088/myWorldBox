package com.huluxia.controller.resource.handler.base;

import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.framework.base.http.io.Response.CancelListener;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.io.Response.ProgressListener;

/* compiled from: DownloadResHandler */
public abstract class b<T extends ResTaskInfo> extends a<T> implements CancelListener, ErrorListener, Listener, ProgressListener {
    public b(T info) {
        super(info);
    }
}
