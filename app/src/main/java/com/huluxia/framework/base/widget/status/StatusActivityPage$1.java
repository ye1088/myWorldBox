package com.huluxia.framework.base.widget.status;

import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class StatusActivityPage$1 implements OnGlobalLayoutListener {
    final /* synthetic */ StatusActivityPage this$0;

    StatusActivityPage$1(StatusActivityPage this$0) {
        this.this$0 = this$0;
    }

    public void onGlobalLayout() {
        StatusActivityPage.access$000(this.this$0);
        StatusActivityPage.access$100(this.this$0).getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }
}
