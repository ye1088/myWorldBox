package com.huluxia.framework.base.widget.status;

import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.framework.R;

public class StatusBasePage$StatuFragmentImpl extends StatusBasePage<StatusBaseFragment> {
    private StatusBaseFragment fragment;

    StatusBasePage$StatuFragmentImpl(StatusBaseFragment fragment) {
        this.fragment = fragment;
    }

    StatusBaseFragment page() {
        return this.fragment;
    }

    public OnClickListener getLoadListener() {
        return this.fragment.getLoadListener();
    }

    public OnClickListener getNoDataListener() {
        return this.fragment.getNoDataListener();
    }

    public void setBoundedView(int boundedViewId) {
        if (boundedViewId > 0 && this.fragment != null && this.fragment.getView() != null) {
            View boundedView = this.fragment.getView().findViewById(boundedViewId);
            if (boundedView != null) {
                Rect contentRect = new Rect();
                this.fragment.getView().findViewById(R.id.status_layout).getGlobalVisibleRect(contentRect);
                Rect bounedRect = new Rect();
                boundedView.getGlobalVisibleRect(bounedRect);
                setMargin(Math.abs(bounedRect.left - contentRect.left), Math.abs(bounedRect.top - contentRect.top), Math.abs(bounedRect.right - contentRect.right), Math.abs(bounedRect.bottom - contentRect.bottom));
            }
        }
    }
}
