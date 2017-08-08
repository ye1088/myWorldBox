package com.MCWorld.http.base.manager;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.MCWorld.HTApplication;
import com.MCWorld.framework.base.http.datasource.cache.DiskBasedCache;
import com.MCWorld.framework.base.http.dispatcher.RequestQueue;
import com.MCWorld.framework.base.http.io.Request;
import com.MCWorld.framework.base.http.transport.BasicNetwork;
import com.MCWorld.framework.base.http.transport.HttpStack;
import com.MCWorld.framework.base.http.transport.HurlStack;
import java.io.File;

/* compiled from: HttpManager */
public class a {
    private static a sj = new a();
    private static final String sk = "volley";
    private RequestQueue mQueue = null;

    public static a fk() {
        return sj;
    }

    private a() {
        fl();
    }

    private void fl() {
        this.mQueue = aC(HTApplication.getAppContext());
    }

    public <T> void a(Request<T> request) {
        this.mQueue.add(request);
    }

    public static RequestQueue a(Context context, HttpStack stack) {
        File cacheDir = new File(context.getCacheDir(), sk);
        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            userAgent = packageName + "/" + context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), new BasicNetwork(new HurlStack()));
        queue.start();
        return queue;
    }

    public static RequestQueue aC(Context context) {
        return a(context, null);
    }
}
