package com.MCWorld.ui.area.news;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.MCWorld.module.news.i;

class ResourceNewsFragment$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ ResourceNewsFragment aIi;

    ResourceNewsFragment$1(ResourceNewsFragment this$0) {
        this.aIi = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        i.Ea().ar(0, 20);
    }
}
