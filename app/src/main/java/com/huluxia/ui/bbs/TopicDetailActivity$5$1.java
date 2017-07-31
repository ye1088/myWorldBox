package com.huluxia.ui.bbs;

import android.widget.PopupWindow.OnDismissListener;
import com.huluxia.bbs.b.c;
import com.huluxia.ui.bbs.TopicDetailActivity.5;
import com.simple.colorful.d;

class TopicDetailActivity$5$1 implements OnDismissListener {
    final /* synthetic */ 5 aPl;

    TopicDetailActivity$5$1(5 this$1) {
        this.aPl = this$1;
    }

    public void onDismiss() {
        this.aPl.aNZ.setImageDrawable(d.o(TopicDetailActivity.d(this.aPl.aPk), c.drawableComplaintDown));
    }
}
