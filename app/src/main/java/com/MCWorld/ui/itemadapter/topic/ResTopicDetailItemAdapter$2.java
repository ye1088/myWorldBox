package com.MCWorld.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.topic.CommentItem;

class ResTopicDetailItemAdapter$2 implements OnClickListener {
    final /* synthetic */ ResTopicDetailItemAdapter aWL;
    final /* synthetic */ CommentItem aWM;

    ResTopicDetailItemAdapter$2(ResTopicDetailItemAdapter this$0, CommentItem commentItem) {
        this.aWL = this$0;
        this.aWM = commentItem;
    }

    public void onClick(View arg0) {
        if (ResTopicDetailItemAdapter.b(this.aWL) != null) {
            ResTopicDetailItemAdapter.b(this.aWL).a(false, this.aWM);
        }
    }
}
