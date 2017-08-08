package com.MCWorld.ui.bbs;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.t;

class TopicDetailTitle$1 implements OnClickListener {
    final /* synthetic */ TopicDetailTitle aPA;
    final /* synthetic */ TopicItem aPz;

    TopicDetailTitle$1(TopicDetailTitle this$0, TopicItem topicItem) {
        this.aPA = this$0;
        this.aPz = topicItem;
    }

    public void onClick(View arg0) {
        t.a(this.aPA.getContext(), this.aPz.getCategory());
    }
}
