package com.MCWorld.studio.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class RecommendFragment$2 implements OnRefreshListener<ListView> {
    final /* synthetic */ RecommendFragment aGD;

    RecommendFragment$2(RecommendFragment this$0) {
        this.aGD = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        RecommendFragment.b(this.aGD);
    }
}
