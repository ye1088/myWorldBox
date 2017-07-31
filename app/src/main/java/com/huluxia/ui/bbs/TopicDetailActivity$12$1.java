package com.huluxia.ui.bbs;

import android.widget.PopupWindow.OnDismissListener;
import com.huluxia.bbs.b.c;
import com.huluxia.ui.bbs.TopicDetailActivity.12;
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
