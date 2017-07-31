package com.huawei.android.pushselfshow.richpush.html;

import android.view.View;
import android.view.View.OnClickListener;
import com.huawei.android.pushagent.c.a.e;

class HtmlViewer$c implements OnClickListener {
    final /* synthetic */ HtmlViewer a;

    private HtmlViewer$c(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void onClick(View view) {
        if (HtmlViewer.a(this.a) != null && HtmlViewer.a(this.a).canGoForward()) {
            e.a("PushSelfShowLog", " can Go Forward " + HtmlViewer.a(this.a).canGoForward());
            HtmlViewer.a(this.a).goForward();
        }
    }
}
