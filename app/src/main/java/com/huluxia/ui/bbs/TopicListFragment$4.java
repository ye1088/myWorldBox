package com.huluxia.ui.bbs;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.utils.UtilsMenu$MENU_TOPIC_LIST;

class TopicListFragment$4 implements OnClickListener {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$4(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onClick(View v) {
        TopicListFragment.a(this.aQp, v, UtilsMenu$MENU_TOPIC_LIST.FILTER_CREATE_TIME);
    }
}
