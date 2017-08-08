package com.MCWorld.ui.bbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.j;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.l;
import com.simple.colorful.a.a;
import com.simple.colorful.c;
import com.simple.colorful.d;

public class DarenRankingTitle extends LinearLayout implements c {
    private TextView aKp = ((TextView) findViewById(g.tv_ranking));
    private PaintView aKq = ((PaintView) findViewById(g.iv_daren_avatar));
    private ViewSwitcher aKr;

    public DarenRankingTitle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.view_daren_title, this);
        if (j.ep().ey()) {
            this.aKq.setUri(UtilUri.getUriOrNull(j.ep().getAvatar())).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).oval().borderColor(getResources().getColor(b.d.DarenBackground), 2.0f).setImageLoader(l.cb().getImageLoader());
        } else {
            this.aKq.setImageResource(f.floor_app_icon);
        }
        this.aKr = (ViewSwitcher) findViewById(g.switcher);
    }

    public void setUserRanking(long ranking) {
        if (ranking > 0) {
            this.aKr.setDisplayedChild(1);
            this.aKp.setText(String.valueOf(ranking));
            return;
        }
        this.aKr.setDisplayedChild(0);
    }

    public a b(a builder) {
        builder.d(this.aKq, b.c.valBrightness).be(g.iv_title_background, b.c.valBrightness).be(g.iv_daren_ranking, b.c.valBrightness);
        return builder;
    }

    public void FG() {
    }
}
