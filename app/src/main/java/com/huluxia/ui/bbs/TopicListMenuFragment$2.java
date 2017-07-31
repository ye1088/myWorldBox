package com.huluxia.ui.bbs;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

class TopicListMenuFragment$2 implements OnItemClickListener {
    final /* synthetic */ TopicListMenuFragment aQy;

    TopicListMenuFragment$2(TopicListMenuFragment this$0) {
        this.aQy = this$0;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TopicListMenuFragment$c info = (TopicListMenuFragment$c) TopicListMenuFragment.a(this.aQy).get(position);
        if (TopicListMenuFragment.b(this.aQy).id != info.id) {
            TopicListMenuFragment.a(this.aQy, info);
            TopicListMenuFragment.c(this.aQy).bk(info.id);
            TopicListMenuFragment.d(this.aQy);
        }
    }
}
