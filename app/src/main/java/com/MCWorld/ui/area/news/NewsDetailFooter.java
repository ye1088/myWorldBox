package com.MCWorld.ui.area.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.module.news.c;

public class NewsDetailFooter extends LinearLayout {
    public NewsDetailFooter(Context context, c item) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_news_detail_footer, this);
        findViewById(g.tv_more).setOnClickListener(new 1(this, context, item));
    }
}
