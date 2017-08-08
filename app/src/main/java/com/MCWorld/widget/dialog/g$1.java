package com.MCWorld.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.bbs.b.g;

/* compiled from: GlobalDialog */
class g$1 implements OnClickListener {
    final /* synthetic */ g bwY;

    g$1(g this$0) {
        this.bwY = this$0;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == g.cb_tip) {
            if (g.a(this.bwY) != null) {
                g.a(this.bwY).ra();
            }
        } else if (id == g.tv_cancel) {
            this.bwY.Op();
        } else if (id == g.tv_other) {
            this.bwY.Oq();
        } else if (id == g.tv_confirm) {
            this.bwY.sK();
        }
    }
}
