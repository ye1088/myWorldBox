package com.huluxia.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.topic.CommentItem;

class TopicDetailItemAdapter$7 implements OnClickListener {
    final /* synthetic */ CommentItem aWM;
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$7(TopicDetailItemAdapter this$0, CommentItem commentItem) {
        this.aXA = this$0;
        this.aWM = commentItem;
    }

    public void onClick(View arg0) {
        if (TopicDetailItemAdapter.b(this.aXA) != null) {
            TopicDetailItemAdapter.b(this.aXA).a(false, this.aWM);
            this.aXA.HP();
        }
    }
}
