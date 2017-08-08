package com.MCWorld.framework.base.widget;

import android.view.View;
import android.view.View.OnClickListener;

class PagerSlidingIndicator$2 implements OnClickListener {
    final /* synthetic */ PagerSlidingIndicator this$0;
    final /* synthetic */ int val$position;

    PagerSlidingIndicator$2(PagerSlidingIndicator this$0, int i) {
        this.this$0 = this$0;
        this.val$position = i;
    }

    public void onClick(View v) {
        this.this$0.pager.setCurrentItem(this.val$position, false);
    }
}
