package com.huluxia.widget.pulltorefresh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.a;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;

public class FooterLayout extends LinearLayout implements a {
    private ImageView bEf;
    private TextView bEg;
    private String bEh = "查看更多";
    private String bEi = "";
    private String bEj = "正在加载";
    private Animation bEk;

    public FooterLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.pulllistview_foot, this);
        this.bEg = (TextView) findViewById(g.footer_tipsTextView);
        this.bEf = (ImageView) findViewById(g.footer_progressBar);
        this.bEk = AnimationUtils.loadAnimation(getContext(), a.common_loading);
    }

    public void setScroll(int value) {
    }

    public int getContentHeight() {
        return getHeight();
    }

    public void pullToRefreshImpl() {
        Log.i("FooterLayout", "pullToRefreshImpl");
        this.bEf.setVisibility(4);
        this.bEf.clearAnimation();
        this.bEg.setVisibility(0);
        this.bEg.setText(this.bEh);
    }

    public void refreshingImpl() {
        Log.i("FooterLayout", "refreshingImpl");
        this.bEf.setVisibility(0);
        this.bEf.startAnimation(this.bEk);
        this.bEg.setText(this.bEj);
    }

    public void releaseToRefreshImpl() {
        Log.i("FooterLayout", "releaseToRefreshImpl");
        this.bEf.setVisibility(4);
        this.bEf.clearAnimation();
        this.bEg.setVisibility(0);
        this.bEg.setText(this.bEi);
    }

    public void resetImpl() {
        Log.i("FooterLayout", "resetImpl");
        this.bEf.setVisibility(4);
        this.bEf.clearAnimation();
        this.bEg.setText("下拉刷新");
    }

    public int getScroll() {
        return 0;
    }
}
