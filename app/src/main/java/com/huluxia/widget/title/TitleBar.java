package com.huluxia.widget.title;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.o;

public class TitleBar extends RelativeLayout {
    protected int mBg;
    private int mCenterLayout;
    protected View mCenterView;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mLeftLayout;
    protected View mLeftView;
    private int mRightLayout;
    protected View mRightView;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mInflater.inflate(i.layout_title_bar_base, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, o.title_bar_style, defStyle, 0);
        this.mBg = ta.getResourceId(o.title_bar_style_bg, -1);
        int left = ta.getResourceId(o.title_bar_style_left, -1);
        int right = ta.getResourceId(o.title_bar_style_right, -1);
        int center = ta.getResourceId(o.title_bar_style_center, -1);
        setLeftLayout(left);
        setRightLayout(right);
        setCenterLayout(center);
        if (this.mBg > 0) {
            setBackgroundResource(this.mBg);
        }
        ta.recycle();
    }

    public int getLeftLayout() {
        return this.mLeftLayout;
    }

    public void setLeftLayout(int leftLayout) {
        this.mLeftLayout = leftLayout;
        if (this.mLeftLayout > 0) {
            setLeftView(this.mInflater.inflate(this.mLeftLayout, null));
        }
    }

    public View getLeftView() {
        return this.mLeftView;
    }

    public void setLeftView(View leftView) {
        if (leftView != null) {
            if (this.mLeftView != null) {
                removeView(this.mLeftView);
            }
            this.mLeftView = leftView;
            LayoutParams params = new LayoutParams(-2, -2);
            params.addRule(15);
            ((ViewGroup) findViewById(g.title_left)).addView(this.mLeftView, params);
        }
    }

    public int getRightLayout() {
        return this.mRightLayout;
    }

    public void setRightLayout(int rightLayout) {
        this.mRightLayout = rightLayout;
        if (this.mRightLayout > 0) {
            setRightView(this.mInflater.inflate(this.mRightLayout, null));
        }
    }

    public View getRightView() {
        return this.mRightView;
    }

    public void setRightView(View rightView) {
        if (rightView != null) {
            if (this.mRightView != null) {
                removeView(this.mRightView);
            }
            this.mRightView = rightView;
            LayoutParams params = new LayoutParams(-2, -2);
            params.addRule(15);
            ((ViewGroup) findViewById(g.title_right)).addView(this.mRightView, params);
        }
    }

    public int getCenterLayout() {
        return this.mCenterLayout;
    }

    public void setCenterLayout(int centerLayout) {
        this.mCenterLayout = centerLayout;
        if (this.mCenterLayout > 0) {
            setCenterView(this.mInflater.inflate(this.mCenterLayout, null));
        }
    }

    public View getCenterView() {
        return this.mCenterView;
    }

    public void setCenterView(View centerView) {
        if (centerView != null) {
            if (this.mCenterView != null) {
                removeView(this.mCenterView);
            }
            this.mCenterView = centerView;
            LayoutParams params = new LayoutParams(-2, -2);
            params.addRule(15);
            ((ViewGroup) findViewById(g.title_center)).addView(this.mCenterView, params);
        }
    }
}
