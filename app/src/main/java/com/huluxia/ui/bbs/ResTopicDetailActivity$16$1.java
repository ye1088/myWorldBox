package com.huluxia.ui.bbs;

import android.widget.PopupWindow.OnDismissListener;
import com.huluxia.bbs.b.c;
import com.huluxia.ui.bbs.ResTopicDetailActivity.16;
import com.simple.colorful.d;

class ResTopicDetailActivity$16$1 implements OnDismissListener {
    final /* synthetic */ 16 aOb;

    ResTopicDetailActivity$16$1(16 this$1) {
        this.aOb = this$1;
    }

    public void onDismiss() {
        this.aOb.aNZ.setImageDrawable(d.o(this.aOb.aNS.aMn, c.drawableComplaintDown));
    }
}
