package com.MCWorld.ui.itemadapter.news;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.module.news.d;
import com.MCWorld.t;

class NewsCommentItemAdapter$1 implements OnClickListener {
    final /* synthetic */ d aUu;
    final /* synthetic */ NewsCommentItemAdapter aUv;

    NewsCommentItemAdapter$1(NewsCommentItemAdapter this$0, d dVar) {
        this.aUv = this$0;
        this.aUu = dVar;
    }

    public void onClick(View v) {
        t.a(NewsCommentItemAdapter.a(this.aUv), this.aUu.user.userID, this.aUu.user);
    }
}
