package com.huluxia.ui.itemadapter.topic;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.t;

class TopicDetailItemAdapter$a implements OnClickListener {
    UserBaseInfo aUq;
    final /* synthetic */ TopicDetailItemAdapter aXA;
    Context context;
    long userID;

    TopicDetailItemAdapter$a(TopicDetailItemAdapter topicDetailItemAdapter, Context context, long userID, UserBaseInfo info) {
        this.aXA = topicDetailItemAdapter;
        this.context = context;
        this.userID = userID;
        this.aUq = info;
    }

    public void onClick(View v) {
        this.aXA.HP();
        t.a(this.context, this.userID, this.aUq);
    }
}
