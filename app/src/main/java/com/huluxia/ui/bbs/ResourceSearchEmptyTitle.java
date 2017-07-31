package com.huluxia.ui.bbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.simple.colorful.a.a;
import com.simple.colorful.c;

public class ResourceSearchEmptyTitle extends LinearLayout implements c {
    private TextView aOK;

    public ResourceSearchEmptyTitle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_resource_search_no_result, this);
        init();
    }

    private void init() {
        this.aOK = (TextView) findViewById(g.tv_recommend);
    }

    public TextView getRecommend() {
        return this.aOK;
    }

    public a b(a builder) {
        return builder.ba(g.tv_no_result_tip, 16843282).ba(g.tv_recommend, 16843282).aY(g.split_item, b.c.splitColor);
    }

    public void FG() {
    }
}
