package com.MCWorld.studio.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class DownloadRankFragment$2 implements OnRefreshListener<ListView> {
    final /* synthetic */ DownloadRankFragment aGr;

    DownloadRankFragment$2(DownloadRankFragment this$0) {
        this.aGr = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        DownloadRankFragment.b(this.aGr);
    }
}
