package com.huluxia.ui.bbs;

import android.widget.PopupWindow.OnDismissListener;

class TopicListFragment$5 implements OnDismissListener {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$5(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onDismiss() {
        TopicListFragment.a(this.aQp, false);
        TopicListFragment.c(this.aQp).setChecked(false);
    }
}
