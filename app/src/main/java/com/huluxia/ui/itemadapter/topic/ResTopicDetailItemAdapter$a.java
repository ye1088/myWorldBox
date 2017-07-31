package com.huluxia.ui.itemadapter.topic;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.t;

class ResTopicDetailItemAdapter$a implements OnClickListener {
    UserBaseInfo aUq;
    final /* synthetic */ ResTopicDetailItemAdapter aWL;
    Context context;
    long userID;

    ResTopicDetailItemAdapter$a(ResTopicDetailItemAdapter resTopicDetailItemAdapter, Context context, long userID, UserBaseInfo info) {
        this.aWL = resTopicDetailItemAdapter;
        this.context = context;
        this.userID = userID;
        this.aUq = info;
    }

    public void onClick(View v) {
        t.a(this.context, this.userID, this.aUq);
    }
}
