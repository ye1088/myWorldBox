package com.MCWorld.ui.area.news;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.module.news.c;
import com.MCWorld.t;

class NewsDetailFooter$1 implements OnClickListener {
    final /* synthetic */ c aHA;
    final /* synthetic */ NewsDetailFooter aHB;
    final /* synthetic */ Context val$context;

    NewsDetailFooter$1(NewsDetailFooter this$0, Context context, c cVar) {
        this.aHB = this$0;
        this.val$context = context;
        this.aHA = cVar;
    }

    public void onClick(View v) {
        t.b(this.val$context, this.aHA);
    }
}
