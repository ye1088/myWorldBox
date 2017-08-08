package com.MCWorld.framework.base.widget.title;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.MCWorld.framework.R;

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
        this.mInflater.inflate(R.layout.layout_title_bar_base, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.title_bar_style, defStyle, 0);
        this.mBg = ta.getResourceId(R.styleable.title_bar_style_bg, -1);
        int left = ta.getResourceId(R.styleable.title_bar_style_left, -1);
        int right = ta.getResourceId(R.styleable.title_bar_style_right, -1);
        int center = ta.getResourceId(R.styleable.title_bar_style_center, -1);
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
        setLeftLayout(leftLayout, null);
    }

    public void setLeftLayout(int leftLayout, LayoutParams params) {
        this.mLeftLayout = leftLayout;
        if (this.mLeftLayout > 0) {
            setLeftView(this.mInflater.inflate(this.mLeftLayout, null), params);
        }
    }

    public View getLeftView() {
        return this.mLeftView;
    }

    public void setLeftView(View leftView) {
        setLeftView(leftView, new LayoutParams(-2, -2));
    }

    public void setLeftView(View leftView, LayoutParams params) {
        if (leftView != null) {
            if (this.mLeftView != null) {
                removeView(this.mLeftView);
            }
            this.mLeftView = leftView;
            if (params == null) {
                params = new LayoutParams(-2, -2);
            }
            params.addRule(15);
            ((ViewGroup) findViewById(R.id.title_left)).addView(this.mLeftView, params);
        }
    }

    public int getRightLayout() {
        return this.mRightLayout;
    }

    public void setRightLayout(int rightLayout) {
        setRightLayout(rightLayout, null);
    }

    public void setRightLayout(int rightLayout, LayoutParams params) {
        this.mRightLayout = rightLayout;
        if (this.mRightLayout > 0) {
            setRightView(this.mInflater.inflate(this.mRightLayout, null), params);
        }
    }

    public View getRightView() {
        return this.mRightView;
    }

    public void setRightView(View rightView) {
        setRightView(rightView, new LayoutParams(-2, -2));
    }

    public void setRightView(View rightView, LayoutParams params) {
        if (rightView != null) {
            if (this.mRightView != null) {
                removeView(this.mRightView);
            }
            this.mRightView = rightView;
            if (params == null) {
                params = new LayoutParams(-2, -2);
            }
            params.addRule(15);
            params.addRule(11);
            ((ViewGroup) findViewById(R.id.title_right)).addView(this.mRightView, params);
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
            ((ViewGroup) findViewById(R.id.title_center)).addView(this.mCenterView, params);
        }
    }
}
