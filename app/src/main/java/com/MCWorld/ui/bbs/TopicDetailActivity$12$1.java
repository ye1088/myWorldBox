package com.MCWorld.ui.bbs;

import android.widget.PopupWindow.OnDismissListener;
import com.MCWorld.bbs.b.c;
.12;
import com.simple.colorful.d;

class TopicDetailActivity$12$1 implements OnDismissListener {
    final /* synthetic */ 12 aPm;

    TopicDetailActivity$12$1(12 this$1) {
        this.aPm = this$1;
    }

    public void onDismiss() {
        this.aPm.aNZ.setImageDrawable(d.o(TopicDetailActivity.d(this.aPm.aPk), c.drawableComplaintDown));
    }
}
