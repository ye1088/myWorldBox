package com.huluxia.ui.bbs;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.t;

class TopicListFragment$13 implements OnItemClickListener {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$13(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TopicItem data = (TopicItem) parent.getAdapter().getItem(position);
        if (data != null) {
            data.setCategoryName(TopicListFragment.f(this.aQp) == null ? "" : TopicListFragment.f(this.aQp).getTitle());
            t.a(TopicListFragment.e(this.aQp), data, TopicListFragment.h(this.aQp));
        }
    }
}
