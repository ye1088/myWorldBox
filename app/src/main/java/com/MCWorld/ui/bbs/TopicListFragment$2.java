package com.MCWorld.ui.bbs;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.utils.UtilsMenu$MENU_TOPIC_LIST;

class TopicListFragment$2 implements OnClickListener {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$2(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onClick(View v) {
        TopicListFragment.a(this.aQp, v, UtilsMenu$MENU_TOPIC_LIST.FILTER_ACTIVE_TIME);
    }
}
