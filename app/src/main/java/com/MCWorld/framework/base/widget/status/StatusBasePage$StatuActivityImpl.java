package com.MCWorld.framework.base.widget.status;

import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.R;

public class StatusBasePage$StatuActivityImpl extends StatusBasePage<StatusBaseActivty> {
    private StatusBaseActivty activity;

    StatusBasePage$StatuActivityImpl(StatusBaseActivty activity) {
        this.activity = activity;
    }

    StatusBaseActivty page() {
        return this.activity;
    }

    public void setBoundedView(int boundedViewId) {
        if (boundedViewId > 0 && this.activity != null) {
            View boundedView = this.activity.findViewById(boundedViewId);
            if (boundedView != null) {
                Rect contentRect = new Rect();
                this.activity.findViewById(R.id.status_layout).getGlobalVisibleRect(contentRect);
                Rect bounedRect = new Rect();
                boundedView.getGlobalVisibleRect(bounedRect);
                setMargin(Math.abs(bounedRect.left - contentRect.left), Math.abs(bounedRect.top - contentRect.top), Math.abs(bounedRect.right - contentRect.right), Math.abs(bounedRect.bottom - contentRect.bottom));
            }
        }
    }

    public OnClickListener getLoadListener() {
        return this.activity.getLoadListener();
    }

    public OnClickListener getNoDataListener() {
        return this.activity.getNoDataListener();
    }
}
