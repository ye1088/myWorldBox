package com.huluxia.framework.base.widget.status;

import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.huluxia.framework.R;

public class StatusActivityPage extends StatusBasePage<FragmentActivity> {
    private int mBoundedView;
    private OnClickListener mClickListener;
    private FragmentActivity mPage;

    public static class StatusPageActivityBuilder extends StatusPageBuilder<FragmentActivity, StatusActivityPage, StatusPageActivityBuilder> {
        public StatusPageActivityBuilder(FragmentActivity page) {
            super(page);
        }

        protected StatusActivityPage newPage() {
            return new StatusActivityPage((FragmentActivity) this.mPage);
        }

        StatusPageActivityBuilder getThis() {
            return this;
        }

        public StatusActivityPage build() {
            StatusActivityPage page = (StatusActivityPage) super.build();
            page.setClickListener((OnClickListener) this.mClickListener.get());
            return page;
        }
    }

    protected StatusActivityPage(FragmentActivity activity) {
        this.mPage = activity;
    }

    FragmentActivity page() {
        return this.mPage;
    }

    public void setBoundedView(int boundedView) {
        this.mBoundedView = boundedView;
        resetPageMargin();
    }

    public OnClickListener getLoadListener() {
        return this.mClickListener;
    }

    private void setClickListener(OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public StatusBasePage<FragmentActivity> inject() {
        FrameLayout decorView = (FrameLayout) this.mPage.getWindow().getDecorView();
        View contentView = decorView.getChildAt(0);
        decorView.removeView(contentView);
        decorView.addView(StatusLayout.wrap(contentView), new LayoutParams(-1, -1));
        return install();
    }

    public StatusBasePage<FragmentActivity> setContentView(int layoutResID) {
        this.mPage.setContentView(StatusLayout.wrap(LayoutInflater.from(this.mPage).inflate(layoutResID, null)));
        return install();
    }

    private StatusBasePage<FragmentActivity> install() {
        this.mPage.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new 1(this));
        return this;
    }

    private void resetPageMargin() {
        View statusLayout = this.mPage.findViewById(R.id.status_layout);
        if (this.mBoundedView > 0 && statusLayout != null) {
            View boundedView = this.mPage.findViewById(this.mBoundedView);
            if (boundedView != null) {
                Rect contentRect = new Rect();
                this.mPage.getWindow().getDecorView().getGlobalVisibleRect(contentRect);
                Rect bounedRect = new Rect();
                boundedView.getGlobalVisibleRect(bounedRect);
                setMargin(Math.abs(bounedRect.left - contentRect.left), Math.abs(bounedRect.top - contentRect.top), Math.abs(bounedRect.right - contentRect.right), Math.abs(bounedRect.bottom - contentRect.bottom));
            }
        }
    }
}
