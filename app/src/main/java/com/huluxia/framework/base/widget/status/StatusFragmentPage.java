package com.huluxia.framework.base.widget.status;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.huluxia.framework.R;

public class StatusFragmentPage extends StatusBasePage<Fragment> {
    private int mBoundedView;
    private OnClickListener mClickListener;
    private Fragment mPage;

    public static class StatusPageFragmentBuilder extends StatusPageBuilder<Fragment, StatusFragmentPage, StatusPageFragmentBuilder> {
        public StatusPageFragmentBuilder(Fragment page) {
            super(page);
        }

        protected StatusFragmentPage newPage() {
            return new StatusFragmentPage((Fragment) this.mPage);
        }

        StatusPageFragmentBuilder getThis() {
            return this;
        }

        public StatusFragmentPage build() {
            StatusFragmentPage page = (StatusFragmentPage) super.build();
            page.setClickListener((OnClickListener) this.mClickListener.get());
            return page;
        }
    }

    protected StatusFragmentPage(Fragment fragment) {
        this.mPage = fragment;
    }

    Fragment page() {
        return this.mPage;
    }

    public void setBoundedView(int boundedView) {
        this.mBoundedView = boundedView;
        resetPageMargin();
    }

    private void setClickListener(OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public OnClickListener getLoadListener() {
        return this.mClickListener;
    }

    public View getView(int layout, ViewGroup container) {
        return StatusLayout.wrap(LayoutInflater.from(this.mPage.getActivity()).inflate(layout, container, false));
    }

    public StatusBasePage<Fragment> install() {
        showPending();
        this.mPage.getView().getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                StatusFragmentPage.this.resetPageMargin();
                StatusFragmentPage.this.mPage.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return this;
    }

    public void resetPageMargin() {
        if (this.mPage.getView() != null) {
            View statusLayout = this.mPage.getView().findViewById(R.id.status_layout);
            if (this.mBoundedView > 0 && statusLayout != null) {
                View boundedView = this.mPage.getView().findViewById(this.mBoundedView);
                if (boundedView != null) {
                    Rect contentRect = new Rect();
                    this.mPage.getView().getGlobalVisibleRect(contentRect);
                    Rect bounedRect = new Rect();
                    boundedView.getGlobalVisibleRect(bounedRect);
                    setMargin(Math.abs(bounedRect.left - contentRect.left), Math.abs(bounedRect.top - contentRect.top), Math.abs(bounedRect.right - contentRect.right), Math.abs(bounedRect.bottom - contentRect.bottom));
                }
            }
        }
    }
}
