package com.huluxia.framework.base.widget.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.framework.R;

public class SimpleTitleBar extends TitleBar {
    private ImageView mCenterImage;
    private TextView mCenterTitle;

    public SimpleTitleBar(Context context) {
        super(context);
        addChilds();
        setBg();
    }

    public SimpleTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        addChilds();
        setBg();
    }

    public SimpleTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addChilds();
        setBg();
    }

    private void addChilds() {
        setLeftLayout(R.layout.layout_simple_title_left);
        setCenterLayout(R.layout.layout_simple_title_center);
        setRightLayout(R.layout.layout_simple_title_right);
        this.mLeftView.setVisibility(8);
        this.mRightView.setVisibility(8);
        this.mCenterView.setVisibility(8);
        this.mCenterTitle = (TextView) this.mCenterView.findViewById(R.id.simple_title_center_text);
        this.mCenterImage = (ImageView) this.mCenterView.findViewById(R.id.simple_title_center_image);
    }

    private void setBg() {
        if (this.mBg > 0) {
            setBackgroundColor(getResources().getColor(this.mBg));
        } else {
            setBackgroundColor(getResources().getColor(R.color.title_default_color));
        }
    }

    public void setLeftBtn(int resId, OnClickListener listener) {
        this.mLeftView.setVisibility(0);
        ((ImageView) this.mLeftView.findViewById(R.id.simple_title_left)).setImageResource(resId);
        this.mLeftView.setOnClickListener(listener);
    }

    public void setLeftBtn(int resId) {
        this.mLeftView.setVisibility(0);
        ((ImageView) this.mLeftView.findViewById(R.id.simple_title_left)).setImageResource(resId);
    }

    public void setRightBtn(int resId, OnClickListener listener) {
        this.mRightView.setVisibility(0);
        ((ImageView) this.mRightView.findViewById(R.id.simple_title_right)).setImageResource(resId);
        this.mRightView.setOnClickListener(listener);
    }

    public void setRightBtn(int resId) {
        this.mRightView.setVisibility(0);
        ((ImageView) this.mRightView.findViewById(R.id.simple_title_right)).setImageResource(resId);
    }

    public void setTitlte(String title) {
        this.mCenterView.setVisibility(0);
        this.mCenterTitle.setVisibility(0);
        this.mCenterImage.setVisibility(8);
        this.mCenterTitle.setTextColor(getResources().getColor(R.color.white));
        this.mCenterTitle.setText(title);
    }

    public void setTitlte(String title, int color) {
        this.mCenterView.setVisibility(0);
        this.mCenterTitle.setVisibility(0);
        this.mCenterImage.setVisibility(8);
        this.mCenterTitle.setTextColor(color);
        this.mCenterTitle.setText(title);
    }

    public void setTitleImage(int drwableId) {
        this.mCenterView.setVisibility(0);
        this.mCenterImage.setVisibility(0);
        this.mCenterTitle.setVisibility(8);
        this.mCenterImage.setImageResource(drwableId);
    }
}
