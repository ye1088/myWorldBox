package com.huawei.android.pushselfshow.richpush.html;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.api.ExposedJsApi;
import com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider;
import com.huawei.android.pushselfshow.richpush.tools.Console;
import com.huawei.android.pushselfshow.richpush.tools.a;
import com.huawei.android.pushselfshow.utils.b.b;
import com.huawei.android.pushselfshow.utils.c;
import com.huawei.android.pushselfshow.utils.c$a;
import com.huawei.android.pushselfshow.utils.d;
import com.tencent.connect.common.Constants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import org.apache.tools.ant.taskdefs.XSLTLiaison;

public class HtmlViewer implements c$a {
    public static final String TAG = "PushSelfShowLog";
    PageProgressView a;
    c b = new c(this);
    c c = new c(this);
    private Activity d;
    public b dtl = null;
    private WebView e;
    private a f;
    private com.huawei.android.pushselfshow.b.a g = null;
    private String h;
    private ExposedJsApi i;
    private ImageView j;
    private ImageView k;
    private ImageView l;
    private ImageView m;
    private TextView n;
    private a o;
    private boolean p;
    private boolean q = false;
    private boolean r = false;
    private AlertDialog s = null;
    private AlertDialog t = null;
    private boolean u = false;

    private void a() {
        this.e.getSettings().setJavaScriptEnabled(true);
        if (VERSION.SDK_INT >= 11 && VERSION.SDK_INT <= 16) {
            this.e.removeJavascriptInterface("searchBoxJavaBridge_");
            this.e.removeJavascriptInterface("accessibility");
            this.e.removeJavascriptInterface("accessibilityTraversal");
        }
        if (VERSION.SDK_INT <= 18) {
            this.e.getSettings().setSavePassword(false);
        }
        this.e.getSettings().setPluginState(PluginState.ON);
        this.e.getSettings().setLoadsImagesAutomatically(true);
        this.e.getSettings().setDomStorageEnabled(true);
        this.e.getSettings().setSupportZoom(true);
        this.e.setScrollBarStyle(0);
        this.e.setHorizontalScrollBarEnabled(false);
        this.e.setVerticalScrollBarEnabled(false);
        this.e.getSettings().setSupportMultipleWindows(true);
        this.e.setDownloadListener(new b(this));
        this.e.setOnTouchListener(new c(this));
        this.e.setWebChromeClient(new d(this));
        this.e.setWebViewClient(new e(this));
    }

    private void a(Activity activity) {
        if (activity != null) {
            this.o.a(this.m);
            this.u = true;
            if (VERSION.SDK_INT < 23 || com.huawei.android.pushselfshow.utils.a.e(activity) || !com.huawei.android.pushselfshow.utils.a.f(activity) || activity.checkSelfPermission("com.huawei.pushagent.permission.RICHMEDIA_PROVIDER") == 0) {
                new Thread(new h(this, activity)).start();
                return;
            }
            a(new String[]{"com.huawei.pushagent.permission.RICHMEDIA_PROVIDER"});
        }
    }

    private void a(String[] strArr) {
        try {
            Intent intent = new Intent("huawei.intent.action.REQUEST_PERMISSIONS");
            intent.setPackage("com.huawei.systemmanager");
            intent.putExtra("KEY_HW_PERMISSION_ARRAY", strArr);
            intent.putExtra("KEY_HW_PERMISSION_PKG", this.d.getPackageName());
            if (com.huawei.android.pushselfshow.utils.a.a(this.d, "com.huawei.systemmanager", intent).booleanValue()) {
                try {
                    e.b("PushSelfShowLog", "checkAndRequestPermission: systemmanager permission activity is exist");
                    this.d.startActivityForResult(intent, PushConstants.ERROR_SERVICE_NOT_AVAILABLE_TEMP);
                    return;
                } catch (Throwable e) {
                    e.c("PushSelfShowLog", "checkAndRequestPermission: Exception", e);
                    this.d.requestPermissions(strArr, PushConstants.ERROR_SERVICE_NOT_AVAILABLE_TEMP);
                    return;
                }
            }
            e.b("PushSelfShowLog", "checkAndRequestPermission: systemmanager permission activity is not exist");
            this.d.requestPermissions(strArr, PushConstants.ERROR_SERVICE_NOT_AVAILABLE_TEMP);
        } catch (Throwable e2) {
            e.c("PushSelfShowLog", e2.toString(), e2);
        }
    }

    private int b(Activity activity) {
        Cursor cursor = null;
        int i = 0;
        if (activity != null) {
            try {
                cursor = com.huawei.android.pushselfshow.richpush.a.b.a().a(activity, RichMediaProvider.a.f, "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url order by pushmsg._id desc limit 1000;", null);
                if (cursor != null) {
                    i = cursor.getCount();
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable e) {
                e.c("PushSelfShowLog", e.toString(), e);
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
            e.a("PushSelfShowLog", "currentExistCount:" + i);
        }
        return i;
    }

    private void c(Activity activity) {
        this.t = new Builder(activity, com.huawei.android.pushselfshow.utils.a.h(activity)).setTitle(d.a(activity, "hwpush_dialog_limit_title")).setMessage(d.a(activity, "hwpush_dialog_limit_message")).setNegativeButton(17039360, null).setPositiveButton(d.a(activity, "hwpush_dialog_limit_ok"), new j(this)).setOnDismissListener(new i(this, activity)).create();
        this.t.show();
    }

    private void d(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent.putExtra("type", "favorite");
            if (this.g != null) {
                intent.putExtra("selfshow_info", this.g.c());
                intent.putExtra("selfshow_token", this.g.d());
            }
            intent.setFlags(268468240);
            intent.putExtra("selfshowMsgOutOfBound", true);
            intent.setPackage(activity.getPackageName());
            activity.finish();
            activity.startActivity(intent);
        }
    }

    public void downLoadFailed() {
        e.a("PushSelfShowLog", "downLoadFailed:");
        this.c = null;
        showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a(this.d, "富媒体文件下载失败", "Failed to load the message."));
    }

    public void downLoadSuccess(String str) {
        try {
            e.a("PushSelfShowLog", "downLoadSuccess:" + str + "，and start loadLocalZip");
            loadLocalZip(str);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "downLoadSuccess failed", e);
        }
    }

    public void enableJavaJS(String str) {
        try {
            e.a("PushSelfShowLog", "enable JavaJs support and indexFileUrl is " + str);
            String str2 = null;
            if (str != null) {
                str2 = str.substring(0, str.lastIndexOf("/"));
            }
            e.a("PushSelfShowLog", "m_activity is " + this.d);
            e.a("PushSelfShowLog", "webView is " + this.e);
            e.a("PushSelfShowLog", "localPath is " + str2);
            if (this.g.G != 0) {
                e.a("PushSelfShowLog", "pushmsg.needUserId true");
                this.i = new ExposedJsApi(this.d, this.e, str2, true);
            } else {
                e.a("PushSelfShowLog", "pushmsg.needUserId false");
                this.i = new ExposedJsApi(this.d, this.e, str2, false);
            }
            this.e.addJavascriptInterface(new Console(), "console");
            this.e.addJavascriptInterface(this.i, "_nativeApi");
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "enable JavaJs support failed ", e);
        }
    }

    public void handleMessage(Message message) {
        e.a("PushSelfShowLog", "handleMessage " + message.what + MiPushClient.ACCEPT_TIME_SEPARATOR + message.toString());
        switch (message.what) {
            case 1:
                downLoadSuccess((String) message.obj);
                return;
            case 2:
                downLoadFailed();
                return;
            case 1000:
                c(this.d);
                return;
            default:
                return;
        }
    }

    public void loadLocalZip(String str) {
        if (str != null && str.length() > 0) {
            this.h = com.huawei.android.pushselfshow.richpush.tools.d.a(this.d, str);
            if (this.h == null || this.h.length() <= 0) {
                e.d("PushSelfShowLog", "check index.html file failed");
                this.c = null;
            } else {
                Uri fromFile = Uri.fromFile(new File(this.h));
                enableJavaJS(this.h);
                this.g.C = fromFile.toString();
                this.g.E = "text/html_local";
                this.f.a(this.g);
                this.e.loadUrl(fromFile.toString());
                return;
            }
        }
        showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a(this.d, "富媒体内容不正确", "Invalid content."));
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            e.a("PushSelfShowLog", "run HtmlViewer onActivityResult");
            if (this.i != null) {
                this.i.onActivityResult(i, i2, intent);
            }
            if (PushConstants.ERROR_SERVICE_NOT_AVAILABLE_TEMP != i) {
                return;
            }
            if (i2 == 0) {
                e.b("PushSelfShowLog", "onActivityResult: RESULT_CANCELED");
                this.o.b(this.m);
                this.u = false;
            } else if (-1 == i2) {
                e.b("PushSelfShowLog", "onActivityResult: RESULT_OK");
                if (this.d.checkSelfPermission("com.huawei.pushagent.permission.RICHMEDIA_PROVIDER") == 0) {
                    e.b("PushSelfShowLog", "onActivityResult: Permission is granted");
                    new Thread(new f(this)).start();
                    return;
                }
                this.o.b(this.m);
                this.u = false;
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", e.toString(), e);
        }
    }

    public void onCreate(Intent intent) {
        if (intent == null) {
            e.b("PushSelfShowLog", "onCreate, intent is null");
            return;
        }
        try {
            this.p = intent.getBooleanExtra("selfshow_from_list", false);
            this.u = intent.getBooleanExtra("collect_img_disable", false);
            e.a("PushSelfShowLog", "mCollectImgDisable:" + this.u);
            this.f = new a(this.d);
            this.d.setRequestedOrientation(5);
            View relativeLayout = new RelativeLayout(this.d);
            RelativeLayout relativeLayout2 = (RelativeLayout) this.d.getLayoutInflater().inflate(d.c(this.d, "hwpush_msg_show"), null);
            relativeLayout.addView(relativeLayout2);
            this.d.setContentView(relativeLayout);
            this.o = new a(this.d);
            this.o.a(relativeLayout);
            this.o.a();
            this.j = (ImageView) relativeLayout2.findViewById(d.d(this.d, "hwpush_bt_back_img"));
            this.k = (ImageView) relativeLayout2.findViewById(d.d(this.d, "hwpush_bt_forward_img"));
            this.l = (ImageView) relativeLayout2.findViewById(d.d(this.d, "hwpush_bt_refresh_img"));
            this.a = (PageProgressView) relativeLayout2.findViewById(d.d(this.d, "hwpush_progressbar"));
            this.m = (ImageView) relativeLayout2.findViewById(d.d(this.d, "hwpush_bt_collect_img"));
            this.n = (TextView) relativeLayout2.findViewById(d.d(this.d, "hwpush_msg_title"));
            com.huawei.android.pushselfshow.utils.a.a(this.d, this.n);
            if (com.huawei.android.pushselfshow.utils.a.d()) {
                int j = com.huawei.android.pushselfshow.utils.a.j(this.d);
                if (-1 != j) {
                    this.n.setTextColor(j == 0 ? this.d.getResources().getColor(d.e(this.d, "hwpush_black")) : this.d.getResources().getColor(d.e(this.d, "hwpush_white")));
                    relativeLayout2.findViewById(d.d(this.d, "hwpush_title_bar_bottom_line")).setVisibility(0);
                }
            }
            this.j.setOnClickListener(new a(this, null));
            this.k.setOnClickListener(new c(this, null));
            this.l.setOnClickListener(new d(this, null));
            this.m.setOnClickListener(new b(this, this.d, null));
            if (this.p || this.u) {
                this.o.a(this.m);
                this.u = true;
            }
            this.e = (WebView) relativeLayout2.findViewById(d.d(this.d, "hwpush_msg_show_view"));
            a();
            if (intent.hasExtra("selfshow_info")) {
                this.g = new com.huawei.android.pushselfshow.b.a(intent.getByteArrayExtra("selfshow_info"), intent.getByteArrayExtra("selfshow_token"));
                if (this.g.b()) {
                    e.a("PushSelfShowLog", "pushmsg.rpct:" + this.g.E);
                    this.f.a(this.g);
                } else {
                    e.a("PushSelfShowLog", "parseMessage failed");
                    return;
                }
            }
            e.a("PushSelfShowLog", "pushmsg is null");
            showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a(this.d, "富媒体内容不正确", "Invalid content."));
            if (this.g != null) {
                e.a("PushSelfShowLog", "fileurl :" + this.g.C + ", the pushmsg is " + this.g.toString());
            } else {
                e.a("PushSelfShowLog", "pushmsg is null :");
                this.g = new com.huawei.android.pushselfshow.b.a();
            }
            e.b("PushSelfShowLog", "pushmsg.rpct:" + this.g.E);
            if ("application/zip".equals(this.g.E)) {
                if (-1 == com.huawei.android.pushagent.c.a.b.a(this.d)) {
                    e.a("PushSelfShowLog", "no network. can not load message");
                    return;
                }
                this.dtl = new b(this.c, this.d, this.g.C, com.huawei.android.pushselfshow.richpush.tools.b.a("application/zip"));
                this.dtl.b();
            } else if ("application/zip_local".equals(this.g.E)) {
                loadLocalZip(this.g.C);
            } else if ("text/html".equals(this.g.E) || "text/html_local".equals(this.g.E)) {
                enableJavaJS("text/html_local".equals(this.g.E) ? this.g.C : null);
                this.e.loadUrl(this.g.C);
            } else {
                showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a(this.d, "富媒体内容不正确", "Invalid content."));
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "call" + HtmlViewer.class.getName() + " onCreate(Intent intent) err: " + e.toString(), e);
        }
    }

    public void onDestroy() {
        try {
            if (this.s != null && this.s.isShowing()) {
                this.s.dismiss();
            }
            if (this.t != null && this.t.isShowing()) {
                this.t.dismiss();
            }
            if (this.i != null) {
                this.i.onDestroy();
            }
            if (!(this.h == null || this.r)) {
                String substring = this.h.substring(0, this.h.lastIndexOf("/"));
                e.a("PushSelfShowLog", "try to remove dir " + substring);
                com.huawei.android.pushselfshow.utils.a.a(new File(substring));
            }
            if (this.dtl != null && this.dtl.e) {
                e.a("PushSelfShowLog", "cancel ProgressDialog loading dialog when richpush file is downloading");
                this.dtl.a();
                this.c = null;
            }
            this.e.stopLoading();
            this.e = null;
        } catch (IndexOutOfBoundsException e) {
            e.a("PushSelfShowLog", "remove unsuccess ,maybe removed before");
        } catch (Exception e2) {
            e.a("PushSelfShowLog", "remove unsuccess ,maybe removed before");
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getAction() == 0) {
            if (this.p) {
                Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
                intent.putExtra("type", "favorite");
                intent.setPackage(this.d.getPackageName());
                this.d.finish();
                this.d.startActivity(intent);
            } else {
                this.d.finish();
            }
        }
        return true;
    }

    public void onPause() {
        if (this.i != null) {
            this.i.onPause();
        }
        try {
            this.e.getClass().getMethod("onPause", new Class[0]).invoke(this.e, (Object[]) null);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "htmlviewer onpause error", e);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        e.a("PushSelfShowLog", "enter HtmlViewer onRequestPermissionsResult");
        if (iArr == null || iArr.length <= 0 || iArr[0] != 0) {
            this.o.b(this.m);
            this.u = false;
            return;
        }
        new Thread(new g(this)).start();
    }

    public void onResume() {
        if (this.i != null) {
            this.i.onResume();
        }
        try {
            this.e.getClass().getMethod("onResume", new Class[0]).invoke(this.e, (Object[]) null);
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "htmlviewer onResume error", e);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("collect_img_disable", this.u);
    }

    public void onStop() {
        if (this.i != null) {
            this.i.onPause();
        }
    }

    public String prepareJS(String str) {
        try {
            String str2;
            String str3 = b.b(this.d) + File.separator + this.d.getPackageName().replace(".", "");
            if (!new File(str3).exists() && new File(str3).mkdirs()) {
                e.e("PushSelfShowLog", "mkdir true");
            }
            e.e("PushSelfShowLog", "prepareJS fileHeader is " + str3);
            String str4 = str3 + File.separator + "newpush.js";
            File file = new File(str4);
            if (!file.exists()) {
                if (com.huawei.android.pushagent.c.a.b.a(this.d) != -1 && new b().b(this.d, "http://open.hicloud.com/android/push1.0.js", str4) && new File(str4).exists()) {
                    e.e("PushSelfShowLog", "prepareJS new js isnot exist, so  downloaded  pushUrl is " + str4);
                }
                str4 = null;
            } else if (System.currentTimeMillis() - file.lastModified() > 1300000000) {
                e.a("PushSelfShowLog", "new push.js may be out of date ,or try to update");
                if (com.huawei.android.pushagent.c.a.b.a(this.d) != -1 && new b().b(this.d, "http://open.hicloud.com/android/push1.0.js", str4) && new File(str4).exists()) {
                    e.e("PushSelfShowLog", "prepareJS dlUtil.downLoadSgThread  pushUrl is " + str4);
                }
                str4 = null;
            } else {
                e.e("PushSelfShowLog", "prepareJS  not arrival update  pushUrl is " + str4);
            }
            if (str4 == null || str4.length() == 0) {
                e.e("PushSelfShowLog", "  pushUrl is " + str4);
                str2 = str3 + File.separator + "push1.0.js";
                e.e("PushSelfShowLog", "  pushjsPath is " + str2);
                if (new File(str2).exists()) {
                    if (new File(str2).delete()) {
                        e.a("PushSelfShowLog", "delete pushjsPath success");
                    }
                    e.e("PushSelfShowLog", "prepareJS new js  is not prepared so use local  pushUrl is " + str4);
                } else {
                    e.e("PushSelfShowLog", " new File(pushjsPath) not exists() ");
                }
                com.huawei.android.pushselfshow.utils.a.b(this.d, "pushresources" + File.separator + "push1.0.js", str2);
                str4 = str2;
            }
            if (str4.length() > 0) {
                e.e("PushSelfShowLog", "  pushUrl is " + str4);
                str2 = str4.substring(str4.lastIndexOf("/"));
                e.a("PushSelfShowLog", " pushUrlName is %s,destPath is %s ", str2, str.substring(XSLTLiaison.FILE_PROTOCOL_PREFIX.length(), str.lastIndexOf("/")) + str2);
                com.huawei.android.pushselfshow.utils.a.a(new File(str4), new File(str3));
                return "." + str2;
            }
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "prepareJS", e);
        } catch (Throwable e2) {
            e.d("PushSelfShowLog", "prepareJS", e2);
        }
        return "http://open.hicloud.com/android/push1.0.js";
    }

    public void setActivity(Activity activity) {
        this.d = activity;
    }

    public void setProgress(int i) {
        if (i >= 100) {
            this.a.a(10000);
            this.a.setVisibility(4);
            this.q = false;
            return;
        }
        if (!this.q) {
            this.a.setVisibility(0);
            this.q = true;
        }
        this.a.a((i * 10000) / 100);
    }

    public void showErrorHtmlURI(String str) {
        try {
            String a = new com.huawei.android.pushselfshow.richpush.tools.c(this.d, str).a();
            e.a("PushSelfShowLog", "showErrorHtmlURI,filePath is " + a);
            if (a != null && a.length() > 0) {
                Uri fromFile = Uri.fromFile(new File(a));
                enableJavaJS(null);
                this.e.loadUrl(fromFile.toString());
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "showErrorHtmlURI failed", e);
        }
        if (com.huawei.android.pushselfshow.utils.a.a(this.d, "富媒体文件下载失败", "Failed to load the message.").equals(str)) {
            com.huawei.android.pushselfshow.utils.a.a(this.d, Constants.VIA_REPORT_TYPE_SET_AVATAR, this.g);
        } else {
            com.huawei.android.pushselfshow.utils.a.a(this.d, Constants.VIA_SHARE_TYPE_INFO, this.g);
        }
    }
}
