package com.huluxia.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.t;

class TopicDetailItemAdapter$6 implements OnClickListener {
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$6(TopicDetailItemAdapter this$0) {
        this.aXA = this$0;
    }

    public void onClick(View v) {
        TopicDetailItemAdapter$b clickInfo = (TopicDetailItemAdapter$b) v.getTag();
        t.a(TopicDetailItemAdapter.a(this.aXA), clickInfo.id, clickInfo.aKn);
    }
}
