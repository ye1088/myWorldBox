package com.huluxia.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.bbs.b.g;
import com.huluxia.http.base.d;
import com.simple.colorful.a.a;

public class HTBaseLoadingActivity extends HTBaseActivity {
    public static final int aIA = 1;
    public static final int aIB = 2;
    public static final int aIz = 0;
    private BaseLoadingLayout aIy;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContentView(View view) {
        this.aIy = new BaseLoadingLayout(this);
        this.aIy.addView(view);
        this.aIy.findViewById(g.retry_view).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HTBaseLoadingActivity aJg;

            {
                this.aJg = this$0;
            }

            public void onClick(View v) {
                this.aJg.EX();
            }
        });
        super.setContentView(this.aIy);
        this.aIy.FC();
    }

    public void Fy() {
        if (getCurrentPage() != 0) {
            cs(false);
            this.aIy.Fy();
        }
    }

    public void FB() {
        if (getCurrentPage() != 1) {
            this.aIy.FB();
            cs(false);
        }
    }

    public void FC() {
        if (getCurrentPage() != 2 && this.aIy.getChildCount() == 3) {
            this.aIy.FC();
            cs(false);
        }
    }

    public int getCurrentPage() {
        return this.aIy.getCurrentPage();
    }

    protected void EX() {
        Fy();
    }

    public void a(d response) {
        if (getCurrentPage() == 2) {
            super.a(response);
        }
    }

    public void b(d response) {
        if (getCurrentPage() == 2) {
            super.b(response);
        }
    }

    protected void a(a builder) {
        super.a(builder);
        builder.a(this.aIy);
    }
}
