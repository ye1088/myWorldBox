package com.huawei.android.pushselfshow.richpush.html;

import android.view.View;
import android.view.View.OnClickListener;
import com.huawei.android.pushagent.c.a.e;

class HtmlViewer$a implements OnClickListener {
    final /* synthetic */ HtmlViewer a;

    private HtmlViewer$a(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public void onClick(View view) {
        if (HtmlViewer.a(this.a) != null && HtmlViewer.a(this.a).canGoBack()) {
            e.a("PushSelfShowLog", "can go back " + HtmlViewer.a(this.a).canGoBack());
            HtmlViewer.a(this.a).goBack();
        }
    }
}
