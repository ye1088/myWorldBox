package com.tencent.smtt.sdk.b;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.TbsShareManager;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.utils.o;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.util.List;

public class d {
    private static boolean a = true;
    private static long b = 0;
    private static long c = -1;
    private static DownloadManager d = null;

    private static class a {
        public int a;
        public int b;
        public String c;

        private a() {
            this.a = -1;
            this.b = -1;
            this.c = "";
        }
    }

    private static class b {
        public String a;
        public String b;

        private b() {
            this.a = "";
            this.b = "";
        }
    }

    private static a a(Context context) {
        PackageInfo packageInfo = null;
        a aVar = new a();
        try {
            PackageManager packageManager = context.getPackageManager();
            try {
                packageInfo = packageManager.getPackageInfo(TbsConfig.APP_QB, 0);
                aVar.a = 2;
                aVar.c = "ADRQB_";
                if (packageInfo != null && packageInfo.versionCode > 420000) {
                    aVar.b = packageInfo.versionCode;
                    aVar.c += packageInfo.versionName.replaceAll("\\.", "");
                    return aVar;
                }
            } catch (NameNotFoundException e) {
            }
            try {
                packageInfo = packageManager.getPackageInfo("com.tencent.qbx", 0);
                aVar.a = 0;
                aVar.c = "ADRQBX_";
            } catch (NameNotFoundException e2) {
                try {
                    packageInfo = packageManager.getPackageInfo("com.tencent.qbx5", 0);
                    aVar.a = 1;
                    aVar.c = "ADRQBX5_";
                } catch (NameNotFoundException e3) {
                    try {
                        packageInfo = packageManager.getPackageInfo(TbsConfig.APP_QB, 0);
                        aVar.a = 2;
                        aVar.c = "ADRQB_";
                    } catch (NameNotFoundException e4) {
                        try {
                            packageInfo = packageManager.getPackageInfo("com.tencent.mtt.x86", 0);
                            aVar.a = 2;
                            aVar.c = "ADRQB_";
                        } catch (Exception e5) {
                            try {
                                b a = a(context, Uri.parse("http://mdc.html5.qq.com/mh?channel_id=21094&u="));
                                if (!(a == null || a.b == null || a.b.length() == 0)) {
                                    packageInfo = packageManager.getPackageInfo(a.b, 0);
                                    aVar.a = 2;
                                    aVar.c = "ADRQB_";
                                }
                            } catch (Exception e6) {
                            }
                        }
                    }
                }
            }
            if (packageInfo != null) {
                aVar.b = packageInfo.versionCode;
                aVar.c += packageInfo.versionName.replaceAll("\\.", "");
            }
        } catch (Exception e7) {
        }
        return aVar;
    }

    private static b a(Context context, Uri uri) {
        Intent intent = new Intent("com.tencent.QQBrowser.action.VIEW");
        intent.setData(uri);
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities.size() <= 0) {
            return null;
        }
        b bVar = new b();
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            String str = resolveInfo.activityInfo.packageName;
            if (str.contains(TbsConfig.APP_QB)) {
                bVar.a = resolveInfo.activityInfo.name;
                bVar.b = resolveInfo.activityInfo.packageName;
                return bVar;
            } else if (str.contains("com.tencent.qbx")) {
                bVar.a = resolveInfo.activityInfo.name;
                bVar.b = resolveInfo.activityInfo.packageName;
            }
        }
        return bVar;
    }

    public static void a(WebView webView) {
        Throwable th;
        if (a) {
            Context context;
            long j;
            long currentTimeMillis;
            c cVar;
            String c;
            String d;
            try {
                context = webView.getContext();
                try {
                    if (TbsShareManager.isThirdPartyApp(context)) {
                        b.a(context);
                        j = a.b() ? 600000 : 3600000;
                        currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - b < j || b == 0) {
                            b = currentTimeMillis;
                            try {
                                cVar = new c();
                                c = com.tencent.smtt.utils.a.c(context);
                                d = com.tencent.smtt.utils.a.d(context);
                                if (c != null || "".equals(c)) {
                                    cVar.a("imei", "-1");
                                } else {
                                    cVar.a("imei", c);
                                }
                                if (d != null || "".equals(d)) {
                                    cVar.a("imsi", "-1");
                                } else {
                                    cVar.a("imsi", d);
                                }
                                cVar.a("paK", context.getPackageName());
                                cVar.a("from", "-1");
                                cVar.a("qb_ver", a(context).b + "");
                                cVar.a(Values.MODE, a.a(a.a()));
                                cVar.a("url", webView.getUrl());
                                d = "x5insertadnode";
                                webView.loadUrl("javascript:var insertJsNode=document.getElementById(\"" + d + "\");if(null==insertJsNode){insertJsNode=document.createElement('script');insertJsNode.setAttribute('id',\"" + d + "\");insertJsNode.setAttribute('charset','" + "gbk" + "');insertJsNode.setAttribute('src',\"" + cVar.a(o.a(context).d()) + "\");document.body.appendChild(insertJsNode);}");
                            } catch (Throwable th2) {
                                th2.printStackTrace();
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    th.printStackTrace();
                    if (a.b()) {
                    }
                    currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - b < j) {
                    }
                    b = currentTimeMillis;
                    cVar = new c();
                    c = com.tencent.smtt.utils.a.c(context);
                    d = com.tencent.smtt.utils.a.d(context);
                    if (c != null) {
                    }
                    cVar.a("imei", "-1");
                    if (d != null) {
                    }
                    cVar.a("imsi", "-1");
                    cVar.a("paK", context.getPackageName());
                    cVar.a("from", "-1");
                    cVar.a("qb_ver", a(context).b + "");
                    cVar.a(Values.MODE, a.a(a.a()));
                    cVar.a("url", webView.getUrl());
                    d = "x5insertadnode";
                    webView.loadUrl("javascript:var insertJsNode=document.getElementById(\"" + d + "\");if(null==insertJsNode){insertJsNode=document.createElement('script');insertJsNode.setAttribute('id',\"" + d + "\");insertJsNode.setAttribute('charset','" + "gbk" + "');insertJsNode.setAttribute('src',\"" + cVar.a(o.a(context).d()) + "\");document.body.appendChild(insertJsNode);}");
                }
            } catch (Throwable th22) {
                Throwable th4 = th22;
                context = null;
                th = th4;
                th.printStackTrace();
                if (a.b()) {
                }
                currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - b < j) {
                }
                b = currentTimeMillis;
                cVar = new c();
                c = com.tencent.smtt.utils.a.c(context);
                d = com.tencent.smtt.utils.a.d(context);
                if (c != null) {
                }
                cVar.a("imei", "-1");
                if (d != null) {
                }
                cVar.a("imsi", "-1");
                cVar.a("paK", context.getPackageName());
                cVar.a("from", "-1");
                cVar.a("qb_ver", a(context).b + "");
                cVar.a(Values.MODE, a.a(a.a()));
                cVar.a("url", webView.getUrl());
                d = "x5insertadnode";
                webView.loadUrl("javascript:var insertJsNode=document.getElementById(\"" + d + "\");if(null==insertJsNode){insertJsNode=document.createElement('script');insertJsNode.setAttribute('id',\"" + d + "\");insertJsNode.setAttribute('charset','" + "gbk" + "');insertJsNode.setAttribute('src',\"" + cVar.a(o.a(context).d()) + "\");document.body.appendChild(insertJsNode);}");
            }
        }
    }
}
