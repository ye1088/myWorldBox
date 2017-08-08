package com.MCWorld.ui.bbs;

import android.content.Context;
import android.widget.ListView;
import com.MCWorld.widget.horizontalscroller.a;

class TopicListFragment$15 extends a {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$15(TopicListFragment this$0, Context context) {
        this.aQp = this$0;
        super(context);
    }

    public void GZ() {
        if (TopicListFragment.j(this.aQp).getVisibility() == 0 && ((ListView) this.aQp.aHX.getRefreshableView()).getFirstVisiblePosition() < 1) {
            TopicListFragment.k(this.aQp).b(TopicListFragment.j(this.aQp), 500, 0);
        }
        if (((ListView) this.aQp.aHX.getRefreshableView()).getFirstVisiblePosition() > 1 && TopicListFragment.j(this.aQp).getVisibility() != 0) {
            TopicListFragment.j(this.aQp).setVisibility(0);
            TopicListFragment.k(this.aQp).a(TopicListFragment.j(this.aQp), 500, 0);
        }
    }

    public void Ha() {
        if (TopicListFragment.j(this.aQp).getVisibility() == 0) {
            TopicListFragment.j(this.aQp).setVisibility(4);
            TopicListFragment.k(this.aQp).b(TopicListFragment.j(this.aQp), 500, 0);
        }
    }
}
