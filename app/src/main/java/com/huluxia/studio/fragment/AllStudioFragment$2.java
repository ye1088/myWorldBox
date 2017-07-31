package com.huluxia.studio.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class AllStudioFragment$2 implements OnRefreshListener<ListView> {
    final /* synthetic */ AllStudioFragment aGh;

    AllStudioFragment$2(AllStudioFragment this$0) {
        this.aGh = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        AllStudioFragment.b(this.aGh);
    }
}
