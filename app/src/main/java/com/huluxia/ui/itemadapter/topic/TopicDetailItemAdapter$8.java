package com.huluxia.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;

class TopicDetailItemAdapter$8 implements OnClickListener {
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$8(TopicDetailItemAdapter this$0) {
        this.aXA = this$0;
    }

    public void onClick(View arg0) {
        if (TopicDetailItemAdapter.b(this.aXA) != null) {
            TopicDetailItemAdapter.b(this.aXA).a(true, null);
            this.aXA.HP();
        }
    }
}
