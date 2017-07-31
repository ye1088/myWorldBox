package com.huawei.android.pushselfshow.richpush.html;

import android.app.Activity;

class h implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ HtmlViewer b;

    h(HtmlViewer htmlViewer, Activity activity) {
        this.b = htmlViewer;
        this.a = activity;
    }

    public void run() {
        if (HtmlViewer.b(this.b, this.a) < 1000) {
            HtmlViewer.i(this.b).b();
        } else {
            this.b.b.sendEmptyMessage(1000);
        }
    }
}
