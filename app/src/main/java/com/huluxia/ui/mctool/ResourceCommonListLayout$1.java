package com.huluxia.ui.mctool;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class ResourceCommonListLayout$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ ResourceCommonListLayout bbK;

    ResourceCommonListLayout$1(ResourceCommonListLayout this$0) {
        this.bbK = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        ResourceCommonListLayout.a(this.bbK).Z(this.bbK.bbI, 0, 20);
    }
}
