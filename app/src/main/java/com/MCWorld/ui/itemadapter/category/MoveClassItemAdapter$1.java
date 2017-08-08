package com.MCWorld.ui.itemadapter.category;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.t;

class MoveClassItemAdapter$1 implements OnClickListener {
    final /* synthetic */ TopicCategory aTe;
    final /* synthetic */ MoveClassItemAdapter aTf;

    MoveClassItemAdapter$1(MoveClassItemAdapter this$0, TopicCategory topicCategory) {
        this.aTf = this$0;
        this.aTe = topicCategory;
    }

    public void onClick(View arg0) {
        t.a(MoveClassItemAdapter.a(this.aTf), this.aTe);
    }
}
