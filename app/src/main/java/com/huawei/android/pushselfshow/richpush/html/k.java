package com.huawei.android.pushselfshow.richpush.html;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.huawei.android.pushagent.c.a.h;

class k implements OnClickListener {
    final /* synthetic */ h a;
    final /* synthetic */ HtmlViewer$b b;

    k(HtmlViewer$b htmlViewer$b, h hVar) {
        this.b = htmlViewer$b;
        this.a = hVar;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.a("isFirstCollect", true);
        HtmlViewer.a(this.b.a, HtmlViewer.b(this.b.a));
    }
}
