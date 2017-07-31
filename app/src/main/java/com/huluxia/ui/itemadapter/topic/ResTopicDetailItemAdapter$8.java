package com.huluxia.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.t;

class ResTopicDetailItemAdapter$8 implements OnClickListener {
    final /* synthetic */ ResTopicDetailItemAdapter aWL;

    ResTopicDetailItemAdapter$8(ResTopicDetailItemAdapter this$0) {
        this.aWL = this$0;
    }

    public void onClick(View v) {
        ResTopicDetailItemAdapter$b clickInfo = (ResTopicDetailItemAdapter$b) v.getTag();
        t.a(ResTopicDetailItemAdapter.a(this.aWL), clickInfo.id, clickInfo.aKn);
    }
}
