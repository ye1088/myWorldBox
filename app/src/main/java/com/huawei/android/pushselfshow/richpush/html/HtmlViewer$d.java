package com.huawei.android.pushselfshow.richpush.html;

import android.view.View;
import android.view.View.OnClickListener;

class HtmlViewer$d implements OnClickListener {
    final /* synthetic */ HtmlViewer a;

    private HtmlViewer$d(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void onClick(View view) {
        this.a.setProgress(0);
        HtmlViewer.a(this.a).reload();
    }
}
