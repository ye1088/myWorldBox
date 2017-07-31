package com.huluxia.widget.pulltorefresh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.a;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;

public class HeaderLayout extends BaseHeaderLayout {
    private ImageView bEf;
    private TextView bEg;
    private String bEh = "下拉刷新";
    private String bEi = "松开刷新";
    private String bEj = "正在刷新";
    private Animation bEk;
    private LinearLayout bEl;
    private ImageView bEm;
    private RotateAnimation bEn;
    private RotateAnimation bEo;

    public HeaderLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.pulllistview_head, this);
        this.bEl = (LinearLayout) findViewById(g.head_refresh_layout);
        this.bEg = (TextView) findViewById(g.head_tipsTextView);
        this.bEf = (ImageView) findViewById(g.head_progressBar);
        this.bEm = (ImageView) findViewById(g.head_arrowImageView);
        w(this);
        setContentHeight(this.bEl.getMeasuredHeight());
        this.bEn = new RotateAnimation(0.0f, 180.0f, 1, 0.5f, 1, 0.5f);
        this.bEn.setInterpolator(new LinearInterpolator());
        this.bEn.setDuration(250);
        this.bEn.setFillAfter(true);
        this.bEo = new RotateAnimation(180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.bEo.setInterpolator(new LinearInterpolator());
        this.bEo.setDuration(250);
        this.bEo.setFillAfter(true);
        this.bEk = AnimationUtils.loadAnimation(getContext(), a.common_loading);
    }

    public void setScroll(int value) {
        super.setScroll(value);
        setPadding(0, value, 0, 0);
    }

    public void pullToRefreshImpl() {
        Log.i("HeaderLayout", "pullToRefreshImpl");
        this.bEf.setVisibility(4);
        this.bEf.clearAnimation();
        this.bEg.setVisibility(0);
        this.bEm.setVisibility(0);
        if (this.bEn == this.bEm.getAnimation()) {
            this.bEm.clearAnimation();
            this.bEm.startAnimation(this.bEo);
        }
        this.bEg.setText(this.bEh);
    }

    public void refreshingImpl() {
        Log.i("HeaderLayout", "refreshingImpl");
        this.bEf.setVisibility(0);
        this.bEf.startAnimation(this.bEk);
        this.bEm.clearAnimation();
        this.bEm.setVisibility(8);
        this.bEg.setText(this.bEj);
    }

    public void releaseToRefreshImpl() {
        Log.i("HeaderLayout", "releaseToRefreshImpl");
        this.bEm.setVisibility(0);
        this.bEf.setVisibility(4);
        this.bEf.clearAnimation();
        this.bEg.setVisibility(0);
        if (this.bEo == this.bEm.getAnimation() || this.bEm.getAnimation() == null) {
            this.bEm.clearAnimation();
            this.bEm.startAnimation(this.bEn);
        }
        this.bEg.setText(this.bEi);
    }

    public void resetImpl() {
        Log.i("HeaderLayout", "resetImpl");
        this.bEf.setVisibility(4);
        this.bEf.clearAnimation();
        this.bEm.clearAnimation();
        this.bEm.setImageResource(f.list_arrow_down);
        this.bEg.setText("下拉刷新");
    }
}
