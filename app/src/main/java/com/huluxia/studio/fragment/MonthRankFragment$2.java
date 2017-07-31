package com.huluxia.studio.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class MonthRankFragment$2 implements OnRefreshListener<ListView> {
    final /* synthetic */ MonthRankFragment aGv;

    MonthRankFragment$2(MonthRankFragment this$0) {
        this.aGv = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        MonthRankFragment.b(this.aGv);
    }
}
