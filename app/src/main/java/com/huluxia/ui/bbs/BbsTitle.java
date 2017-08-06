package com.huluxia.ui.bbs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.huluxia.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.m;
import com.huluxia.data.j;
import com.huluxia.i;
import com.huluxia.r;
import com.huluxia.t;
import com.simple.colorful.c;
import hlx.data.tongji.a;

public class BbsTitle extends LinearLayout implements c {
    private RelativeLayout aJF;
    private RelativeLayout aJG;
    private OnClickListener aJH = new OnClickListener(this) {
        final /* synthetic */ BbsTitle aJI;

        {
            this.aJI = this$0;
        }

        public void onClick(View v) {
            int rid = ((Integer) v.getTag()).intValue();
            if (rid == m.publish_map) {
                i callback = b.bq().bs();
                if (callback != null) {
                    r.ck().K_umengEvent(a.bNp);
                    callback.e(this.aJI.mContext);
                }
            } else if (rid != m.my_map) {
            } else {
                if (j.ep().ey()) {
                    i cb = b.bq().bs();
                    if (cb != null) {
                        r.ck().K_umengEvent(a.bNq);
                        cb.f(this.aJI.mContext);
                        return;
                    }
                    return;
                }
                t.an(this.aJI.mContext);
            }
        }
    };
    private Activity mContext;

    public BbsTitle(Activity context) {
        super(context);
        this.mContext = context;
        LayoutInflater.from(context).inflate(com.huluxia.bbs.b.i.layout_mcfavor_items, this);
        this.aJF = (RelativeLayout) findViewById(g.spaceItem3);
        this.aJF.setTag(Integer.valueOf(m.publish_map));
        this.aJF.setOnClickListener(this.aJH);
        this.aJG = (RelativeLayout) findViewById(g.spaceItem4);
        this.aJG.setTag(Integer.valueOf(m.my_map));
        this.aJG.setOnClickListener(this.aJH);
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        builder.j(this.aJF, com.huluxia.bbs.b.c.listSelector).j(this.aJG, com.huluxia.bbs.b.c.listSelector).be(g.spaceIcon4, com.huluxia.bbs.b.c.valBrightness).be(g.spaceIcon3, com.huluxia.bbs.b.c.valBrightness).ba(g.spaceTitle3, 16842808).ba(g.spaceTitle4, 16842808).aY(g.header_split_top, com.huluxia.bbs.b.c.splitColor).aY(g.split_mid, com.huluxia.bbs.b.c.splitColor).aY(g.split_end, com.huluxia.bbs.b.c.splitColor);
        return builder;
    }

    public void FG() {
    }
}
