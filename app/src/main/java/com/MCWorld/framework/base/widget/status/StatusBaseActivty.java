package com.MCWorld.framework.base.widget.status;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.z;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.widget.status.state.LoadingStatement;
import com.MCWorld.framework.base.widget.status.state.NoDataStatement;
import com.MCWorld.framework.base.widget.status.state.ReloadStatement;

public class StatusBaseActivty extends FragmentActivity implements IDataStatus {
    private OnGlobalLayoutListener globalLayoutListener = new OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            if (StatusBaseActivty.this.mBoundedView > 0) {
                View boundedView = StatusBaseActivty.this.findViewById(StatusBaseActivty.this.mBoundedView);
                if (boundedView != null) {
                    Rect contentRect = new Rect();
                    StatusBaseActivty.this.findViewById(R.id.status_layout).getGlobalVisibleRect(contentRect);
                    Rect bounedRect = new Rect();
                    boundedView.getGlobalVisibleRect(bounedRect);
                    StatusBaseActivty.this.mPage.setMargin(Math.abs(bounedRect.left - contentRect.left), Math.abs(bounedRect.top - contentRect.top), Math.abs(bounedRect.right - contentRect.right), Math.abs(bounedRect.bottom - contentRect.bottom));
                }
            }
            StatusBaseActivty.this.getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    };
    private int mBoundedView;
    private StatusBasePage mPage;

    protected void onCreate(@z Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPage = new StatusBasePage$StatuActivityImpl(this);
    }

    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.mPage = new StatusBasePage$StatuActivityImpl(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mPage = null;
        getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this.globalLayoutListener);
    }

    public void setContentView(int layoutResID) {
        super.setContentView(StatusLayout.wrap(LayoutInflater.from(this).inflate(layoutResID, null)));
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this.globalLayoutListener);
    }

    public void setContentView(View view) {
        super.setContentView(StatusLayout.wrap(view));
    }

    public void setContentView(View view, LayoutParams params) {
        super.setContentView(StatusLayout.wrap(view, params));
    }

    protected void onResume() {
        super.onResume();
        this.mPage.restore();
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
        this.mPage.showLoading(getWindow().getDecorView());
    }

    public void showLoading(LoadingStatement statement) {
        this.mPage.showLoading(getWindow().getDecorView(), statement);
    }

    public void showReload() {
        this.mPage.showReload(getWindow().getDecorView());
    }

    public void showReload(ReloadStatement statement) {
        this.mPage.showReload(getWindow().getDecorView(), statement);
    }

    public void showNoData() {
        this.mPage.showNoData(getWindow().getDecorView());
    }

    public void showNoData(NoDataStatement statement) {
        this.mPage.showNoData(getWindow().getDecorView(), statement);
    }

    public void showNetworkErr() {
        this.mPage.showNetworkErr(getWindow().getDecorView());
    }
}
