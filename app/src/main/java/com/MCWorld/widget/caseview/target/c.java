package com.MCWorld.widget.caseview.target;

import android.app.Activity;
import android.graphics.RectF;
import android.view.View;
import android.widget.PopupWindow;

/* compiled from: ViewTarget */
public class c implements b {
    private View mView;

    public c(View view) {
        this.mView = view;
    }

    public RectF a(PopupWindow window) {
        if (this.mView == null) {
            return null;
        }
        int[] location = new int[2];
        this.mView.getLocationInWindow(location);
        if (this.mView.getContext() instanceof Activity) {
            int[] decorViewLocation = new int[2];
            int[] contentLocation = new int[2];
            ((Activity) this.mView.getContext()).getWindow().getDecorView().getLocationOnScreen(decorViewLocation);
            window.getContentView().getLocationOnScreen(contentLocation);
            location[0] = location[0] + (decorViewLocation[0] - contentLocation[0]);
            location[1] = location[1] + (decorViewLocation[1] - contentLocation[1]);
        }
        return new RectF((float) location[0], (float) location[1], (float) (location[0] + this.mView.getMeasuredWidth()), (float) (location[1] + this.mView.getMeasuredHeight()));
    }
}
