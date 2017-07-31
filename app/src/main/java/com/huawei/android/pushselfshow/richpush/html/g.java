package com.huawei.android.pushselfshow.richpush.html;

class g implements Runnable {
    final /* synthetic */ HtmlViewer a;

    g(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void run() {
        if (HtmlViewer.b(this.a, HtmlViewer.b(this.a)) < 1000) {
            HtmlViewer.i(this.a).b();
        } else {
            this.a.b.sendEmptyMessage(1000);
        }
    }
}
