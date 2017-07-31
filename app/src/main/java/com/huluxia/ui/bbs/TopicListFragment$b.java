package com.huluxia.ui.bbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class TopicListFragment$b extends BroadcastReceiver {
    final /* synthetic */ TopicListFragment aQp;

    private TopicListFragment$b(TopicListFragment topicListFragment) {
        this.aQp = topicListFragment;
    }

    public void onReceive(Context context, Intent intent) {
        this.aQp.Fr();
    }
}
