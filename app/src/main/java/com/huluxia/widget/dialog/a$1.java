package com.huluxia.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AppInfoDialog */
class a$1 implements OnClickListener {
    final /* synthetic */ a bwh;

    a$1(a this$0) {
        this.bwh = this$0;
    }

    public void onClick(View v) {
        this.bwh.dismiss();
        if (a.a(this.bwh) != null) {
            a.a(this.bwh).sendMessage(a.a(this.bwh).obtainMessage(1));
        }
    }
}
