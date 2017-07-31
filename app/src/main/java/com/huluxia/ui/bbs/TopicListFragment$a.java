package com.huluxia.ui.bbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class TopicListFragment$a extends BroadcastReceiver {
    final /* synthetic */ TopicListFragment aQp;

    private TopicListFragment$a(TopicListFragment topicListFragment) {
        this.aQp = topicListFragment;
    }

    public void onReceive(Context context, Intent intent) {
        TopicListFragment.l(this.aQp);
        TopicListFragment.m(this.aQp).setVisibility(4);
    }
}
