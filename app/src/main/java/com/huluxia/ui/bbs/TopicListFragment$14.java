package com.huluxia.ui.bbs;

import com.huluxia.utils.aa.a;

class TopicListFragment$14 implements a {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$14(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onLoadData() {
        String start = "0";
        if (!(TopicListFragment.i(this.aQp) == null || TopicListFragment.i(this.aQp).start == null)) {
            start = TopicListFragment.i(this.aQp).start;
        }
        TopicListFragment.a(this.aQp, start);
    }

    public boolean shouldLoadData() {
        if (TopicListFragment.i(this.aQp) == null) {
            this.aQp.aHb.onLoadComplete();
            return false;
        } else if (TopicListFragment.i(this.aQp).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
