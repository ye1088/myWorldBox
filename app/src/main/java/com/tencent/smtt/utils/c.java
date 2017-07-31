package com.tencent.smtt.utils;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.utils.b.a;

class c implements a {
    final /* synthetic */ WebView a;
    final /* synthetic */ Context b;
    final /* synthetic */ RelativeLayout c;
    final /* synthetic */ String d;
    final /* synthetic */ TextView e;
    final /* synthetic */ b f;

    c(b bVar, WebView webView, Context context, RelativeLayout relativeLayout, String str, TextView textView) {
        this.f = bVar;
        this.a = webView;
        this.b = context;
        this.c = relativeLayout;
        this.d = str;
        this.e = textView;
    }

    public void a() {
        this.a.post(new d(this));
    }

    public void a(int i) {
        this.a.post(new e(this, i));
    }

    public void a(Throwable th) {
        this.a.post(new f(this));
    }
}
