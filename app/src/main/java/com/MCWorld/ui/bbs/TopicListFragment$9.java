package com.MCWorld.ui.bbs;

import android.view.View;
import android.view.View.OnClickListener;

class TopicListFragment$9 implements OnClickListener {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$9(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onClick(View v) {
        if (TopicListFragment.a(this.aQp)) {
            TopicListFragment.b(this.aQp);
            return;
        }
        TopicListFragment.a(this.aQp, true);
        TopicListFragment.c(this.aQp).setChecked(true);
        TopicListFragment.d(this.aQp);
    }
}
