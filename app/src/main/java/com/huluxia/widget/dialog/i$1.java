package com.huluxia.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.bbs.b.g;

/* compiled from: GlobalDialogOne */
class i$1 implements OnClickListener {
    final /* synthetic */ i bxg;

    i$1(i this$0) {
        this.bxg = this$0;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == g.cb_tip) {
            if (i.a(this.bxg) != null) {
                i.a(this.bxg).ra();
            }
        } else if (id == g.tv_confirm) {
            if (!(i.b(this.bxg) == null || i.b(this.bxg).isFinishing())) {
                i.c(this.bxg).dismiss();
            }
            if (i.a(this.bxg) != null) {
                i.a(this.bxg).rd();
            }
        }
    }
}
