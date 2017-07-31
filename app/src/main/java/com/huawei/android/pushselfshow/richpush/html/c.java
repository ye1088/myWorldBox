package com.huawei.android.pushselfshow.richpush.html;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.huawei.android.pushagent.c.a.e;

class c implements OnTouchListener {
    final /* synthetic */ HtmlViewer a;

    c(HtmlViewer htmlViewer) {
        this.a = htmlViewer;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (HtmlViewer.a(this.a) != null && HtmlViewer.a(this.a).requestFocus()) {
            e.e("PushSelfShowLog", "webView.requestFocus");
        }
        return false;
    }
}
