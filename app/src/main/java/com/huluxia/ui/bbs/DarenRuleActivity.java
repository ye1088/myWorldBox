package com.huluxia.ui.bbs;

import android.os.Bundle;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.ui.base.HTBaseActivity;
import com.simple.colorful.a.a;

public class DarenRuleActivity extends HTBaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_daren_rule);
        Fd();
    }

    private void Fd() {
        ej(getResources().getString(m.daren_rule));
        this.aIs.setVisibility(8);
        this.aIT.setVisibility(8);
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault).aY(g.split1, c.splitColor).aY(g.split2, c.splitColor).ba(g.q1, 16842806).ba(g.q2, 16842806).ba(g.q3, 16842806).ba(g.a1, 16842808).ba(g.a2, 16842808).ba(g.a3, 16842808);
    }
}
