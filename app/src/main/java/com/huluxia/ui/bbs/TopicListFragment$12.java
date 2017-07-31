package com.huluxia.ui.bbs;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class TopicListFragment$12 implements OnRefreshListener<ListView> {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$12(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        TopicListFragment.a(this.aQp, "0");
    }
}
