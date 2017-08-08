package com.MCWorld.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.simple.colorful.c;
import com.simple.colorful.d;

public class BaseLoadingLayout extends FrameLayout implements c {
    public static final int aIA = 1;
    public static final int aIB = 2;
    public static final int aIz = 0;
    private int aIE;
    private ImageView aIG;
    private TextView aIH;
    private TextView aII;
    private b aIJ;
    private a aIK;
    private Context mContext;

    public interface a {
        void FA();

        void FH();

        void showLoading();
    }

    public interface b {
        void onRetryClick(View view);
    }

    public BaseLoadingLayout(Context context) {
        this(context, null);
    }

    public BaseLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.aIE = 2;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View loading = inflater.inflate(i.include_view_loading, this, false);
        View retry = inflater.inflate(i.include_view_retry, this, false);
        this.aIG = (ImageView) retry.findViewById(g.iv_loading_failed);
        this.aIH = (TextView) retry.findViewById(g.tv_loading_failed_upline_tip);
        this.aII = (TextView) retry.findViewById(g.tv_loading_failed_downline_tip);
        loading.setVisibility(8);
        retry.setVisibility(8);
        addView(loading);
        addView(retry);
        c(context, attrs);
        retry.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BaseLoadingLayout aIL;

            {
                this.aIL = this$0;
            }

            public void onClick(View v) {
                this.aIL.EX();
                if (this.aIL.aIJ != null) {
                    this.aIL.aIJ.onRetryClick(v);
                }
            }
        });
    }

    protected void EX() {
        Fy();
    }

    protected void c(Context context, AttributeSet attrs) {
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getChildCount() > 3) {
            throw new IllegalStateException("BaseLoadingLayout必须且只能含有一个内容子视图");
        }
        FF();
    }

    private void FF() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == this.aIE) {
                child.setVisibility(0);
                if (i == 1) {
                    if (UtilsNetwork.isNetworkConnected(this.mContext)) {
                        this.aIG.setImageResource(d.r(this.mContext, com.MCWorld.bbs.b.c.drawableLoadingFailed));
                        this.aIH.setText(this.mContext.getString(m.loading_failed));
                        this.aII.setVisibility(8);
                    } else {
                        this.aIG.setImageResource(d.r(this.mContext, com.MCWorld.bbs.b.c.drawableLoadingNetworkError));
                        this.aIH.setText(this.mContext.getString(m.loading_network_error_upline));
                        this.aII.setVisibility(0);
                    }
                    this.aIH.setTextColor(d.getColor(this.mContext, 16843282));
                    this.aII.setTextColor(d.getColor(this.mContext, 16843282));
                }
            } else if (i == 2) {
                child.setVisibility(4);
            } else {
                child.setVisibility(8);
            }
        }
    }

    public void Fy() {
        if (getCurrentPage() != 0) {
            this.aIE = 0;
            FF();
            if (this.aIK != null) {
                this.aIK.showLoading();
            }
        }
    }

    public void FB() {
        if (getCurrentPage() != 1) {
            this.aIE = 1;
            FF();
            if (this.aIK != null) {
                this.aIK.FH();
            }
        }
    }

    public void FC() {
        if (getCurrentPage() != 2) {
            this.aIE = 2;
            FF();
            if (this.aIK != null) {
                this.aIK.FA();
            }
        }
    }

    public int getCurrentPage() {
        return this.aIE;
    }

    public void setCurrentPage(int page) {
        if (page >= 0 && page <= 2) {
            this.aIE = page;
            FF();
        }
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        ProgressBar progress = (ProgressBar) kp(0).findViewById(g.progress_loading);
        int size = UtilsScreen.dipToPx(getContext(), 28);
        builder.a(progress, com.MCWorld.bbs.b.c.drawableLoading, size, size).a(this.aIH, 16843282).a(this.aII, 16843282).c(this.aIG, com.MCWorld.bbs.b.c.drawableLoadingFailed).c(this.aIG, com.MCWorld.bbs.b.c.drawableLoadingNetworkError);
        return builder;
    }

    public void FG() {
    }

    private View kp(int page) {
        if (page < getChildCount()) {
            return getChildAt(page);
        }
        return null;
    }

    public void setRetryClickListener(b listener) {
        this.aIJ = listener;
    }

    public void setLoadingListener(a listener) {
        this.aIK = listener;
    }
}
