package com.huawei.android.pushselfshow.richpush.html;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import com.huawei.android.pushagent.c.a.e;

class i implements OnDismissListener {
    final /* synthetic */ Activity a;
    final /* synthetic */ HtmlViewer b;

    i(HtmlViewer htmlViewer, Activity activity) {
        this.b = htmlViewer;
        this.a = activity;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        e.a("PushSelfShowLog", "DialogInterface onDismiss,mClickDialogOkBtn:" + HtmlViewer.j(this.b));
        if (HtmlViewer.j(this.b)) {
            HtmlViewer.c(this.b, this.a);
            return;
        }
        HtmlViewer.e(this.b).b(HtmlViewer.k(this.b));
        HtmlViewer.a(this.b, false);
    }
}
