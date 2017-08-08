package com.MCWorld.ui.itemadapter.topic;

import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class ResTopicDetailItemAdapter$5 implements OnGlobalLayoutListener {
    final /* synthetic */ ResTopicDetailItemAdapter aWL;

    ResTopicDetailItemAdapter$5(ResTopicDetailItemAdapter this$0) {
        this.aWL = this$0;
    }

    public void onGlobalLayout() {
        ResTopicDetailItemAdapter.c(this.aWL).getLayoutParams().width = ResTopicDetailItemAdapter.d(this.aWL).getWidth();
        ResTopicDetailItemAdapter.d(this.aWL).getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }
}
