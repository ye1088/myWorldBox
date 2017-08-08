package com.MCWorld.ui.itemadapter.message;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.t;

class MessageItemAdapter$a implements OnClickListener {
    UserBaseInfo aUq;
    Context context;
    long userID;

    MessageItemAdapter$a(Context context, long userID, UserBaseInfo info) {
        this.context = context;
        this.userID = userID;
        this.aUq = info;
    }

    public void onClick(View v) {
        t.a(this.context, this.userID, this.aUq);
    }
}
