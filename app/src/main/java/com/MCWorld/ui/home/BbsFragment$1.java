package com.MCWorld.ui.home;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class BbsFragment$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ BbsFragment aSb;

    BbsFragment$1(BbsFragment this$0) {
        this.aSb = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.aSb.Gy();
    }
}
