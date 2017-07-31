package com.huawei.android.pushselfshow.richpush.html;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.huawei.android.pushagent.c.a.h;
import com.huawei.android.pushselfshow.utils.d;

class HtmlViewer$b implements OnClickListener {
    final /* synthetic */ HtmlViewer a;
    private Context b;

    private HtmlViewer$b(HtmlViewer htmlViewer, Context context) {
        this.a = htmlViewer;
        this.b = context;
    }

    public void onClick(View view) {
        h hVar = new h(this.b, "push_client_self_info");
        String str = "isFirstCollect";
        Object obj = 1;
        if (hVar.e("isFirstCollect")) {
            obj = null;
        }
        if (obj != null) {
            HtmlViewer.a(this.a, new Builder(this.b).setPositiveButton(d.a(this.b, "hwpush_collect_tip_known"), new k(this, hVar)).setView(((LayoutInflater) this.b.getSystemService("layout_inflater")).inflate(d.c(this.b, "hwpush_collect_tip_dialog"), null)).create());
            HtmlViewer.c(this.a).show();
            return;
        }
        HtmlViewer.a(this.a, HtmlViewer.b(this.a));
    }
}
