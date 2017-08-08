package com.MCWorld.framework.base.widget.status;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.z;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.widget.status.state.LoadingStatement;
import com.MCWorld.framework.base.widget.status.state.NoDataStatement;
import com.MCWorld.framework.base.widget.status.state.ReloadStatement;

public class StatusBaseFragment extends StatusAbsFragment implements IDataStatus {
    private OnGlobalLayoutListener globalLayoutListener = new OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            if (StatusBaseFragment.this.mBoundedView > 0) {
                View boundedView = StatusBaseFragment.this.getView().findViewById(StatusBaseFragment.this.mBoundedView);
                if (boundedView != null) {
                    Rect contentRect = new Rect();
                    StatusBaseFragment.this.getView().getGlobalVisibleRect(contentRect);
                    Rect bounedRect = new Rect();
                    boundedView.getGlobalVisibleRect(bounedRect);
                    StatusBaseFragment.this.mPage.setMargin(Math.abs(bounedRect.left - contentRect.left), Math.abs(bounedRect.top - contentRect.top), Math.abs(bounedRect.right - contentRect.right), Math.abs(bounedRect.bottom - contentRect.bottom));
                    StatusBaseFragment.this.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        }
    };
    private int mBoundedView;

    Supplier<StatusBasePage<?>> getStatusPage() {
        return new Supplier<StatusBasePage<?>>() {
            public StatusBasePage<?> get() {
                return new StatusBasePage$StatuFragmentImpl(StatusBaseFragment.this);
            }
        };
    }

    public void onViewCreated(View view, @z Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().getViewTreeObserver().addOnGlobalLayoutListener(this.globalLayoutListener);
    }

    public void onDestroyView() {
        super.onDestroyView();
        getView().getViewTreeObserver().removeGlobalOnLayoutListener(this.globalLayoutListener);
        hideStatus();
    }

    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        if (!(getChildFragmentManager() instanceof LayoutInflaterFactory)) {
            return super.getLayoutInflater(savedInstanceState);
        }
        StatusLayoutInflater result = new StatusLayoutInflater(getContext());
        LayoutInflaterCompat.setFactory(result, (LayoutInflaterFactory) getChildFragmentManager());
        return result;
    }

    public void setBoundedView(int boundedView) {
        this.mBoundedView = boundedView;
    }

    public OnClickListener getLoadListener() {
        return null;
    }

    public OnClickListener getNoDataListener() {
        return null;
    }

    public void hideStatus() {
        this.mPage.hideStatus();
    }

    public void showLoading() {
        this.mPage.showLoading(getView());
    }

    public void showLoading(LoadingStatement statement) {
        this.mPage.showLoading(getView(), statement);
    }

    public void showReload() {
        this.mPage.showReload(getView());
    }

    public void showReload(ReloadStatement statement) {
        this.mPage.showReload(getView(), statement);
    }

    public void showNoData() {
        this.mPage.showNoData(getView());
    }

    public void showNoData(NoDataStatement statement) {
        this.mPage.showNoData(getView(), statement);
    }

    public void showNetworkErr() {
        this.mPage.showNetworkErr(getView());
    }
}
