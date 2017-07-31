package com.huluxia.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;

class TopicDetailItemAdapter$10 implements OnClickListener {
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$10(TopicDetailItemAdapter this$0) {
        this.aXA = this$0;
    }

    public void onClick(View v) {
        if (TopicDetailItemAdapter.c(this.aXA).isPlaying()) {
            TopicDetailItemAdapter.c(this.aXA).pause();
            TopicDetailItemAdapter.d(this.aXA).setVisibility(0);
        }
    }
}
