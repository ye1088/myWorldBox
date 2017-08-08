package com.MCWorld.ui.base;

import android.os.Bundle;
import android.support.annotation.af;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.widget.title.TitleBar;
import com.MCWorld.http.base.d;
import com.MCWorld.http.base.f;
import com.MCWorld.t;
import com.simple.colorful.a.a;

public abstract class BaseLoadingFragment extends BaseThemeFragment implements f {
    public static final int aIA = 1;
    public static final int aIB = 2;
    public static final int aIz = 0;
    private View aIC;
    private TextView aID;
    private int aIE = 2;
    public TitleBar aIw;
    private View aIx;
    private BaseLoadingLayout aIy;

    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        return new b(this, getContext()) {
            final /* synthetic */ BaseLoadingFragment aIF;

            ViewGroup FE() {
                return this.aIF.getContainer();
            }

            ViewGroup getContainer() {
                return this.aIF.aIy;
            }
        };
    }

    private ViewGroup getContainer() {
        View view = LayoutInflater.from(getContext()).inflate(i.include_app_loading_title_page, null, false);
        this.aIw = (TitleBar) view.findViewById(g.title_bar);
        this.aIx = view.findViewById(g.split_top);
        a(this.aIw);
        cr(false);
        this.aIy = (BaseLoadingLayout) view.findViewById(g.loading_layout);
        this.aIy.findViewById(g.retry_view).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BaseLoadingFragment aIF;

            {
                this.aIF = this$0;
            }

            public void onClick(View v) {
                this.aIF.EX();
            }
        });
        this.aIC = view.findViewById(g.loading);
        this.aIC.setVisibility(8);
        this.aID = (TextView) this.aIC.findViewById(g.progressTxt);
        return (ViewGroup) view;
    }

    public void onViewCreated(View view, @z Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.aIy.getCurrentPage() != this.aIE) {
            this.aIy.setCurrentPage(this.aIE);
        }
    }

    protected void a(TitleBar titleBar) {
    }

    public void cq(boolean show) {
        if (this.aIw != null) {
            this.aIw.setVisibility(show ? 0 : 8);
        }
    }

    public void cr(boolean show) {
        if (this.aIx != null) {
            this.aIx.setVisibility(show ? 0 : 8);
        }
    }

    public void Fy() {
        this.aIE = 0;
        if (this.aIy != null && getCurrentPage() != 0) {
            cs(false);
            this.aIy.setCurrentPage(0);
        }
    }

    public void cs(boolean show) {
        this.aIC.setVisibility(show ? 0 : 8);
    }

    public void ko(@af int stringRes) {
        this.aID.setText(stringRes);
    }

    public void FB() {
        this.aIE = 1;
        if (this.aIy != null && getCurrentPage() != 1) {
            this.aIy.setCurrentPage(1);
            cs(false);
        }
    }

    public void FC() {
        this.aIE = 2;
        if (this.aIy != null && getCurrentPage() != 2 && this.aIy.getChildCount() == 3) {
            this.aIy.setCurrentPage(2);
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
            cs(true);
        }
    }

    public void b(d response) {
        if (getCurrentPage() == 2) {
            cs(false);
            t.n(getContext(), "访问错误");
        }
    }

    public void c(d response) {
        cs(false);
    }

    public TitleBar FD() {
        return this.aIw;
    }

    protected void a(a builder) {
        super.a(builder);
        builder.a(this.aIy).i(this.aIw, c.backgroundTitleBar).i(this.aIx, c.splitColorDim);
    }
}
