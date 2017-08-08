package com.MCWorld.widget.caseview.target;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.widget.PopupWindow;

/* compiled from: RectTarget */
public class a implements b {
    private RectF bvQ;
    private Context bvR;

    public a(RectF rectF, Context activity) {
        this.bvQ = rectF;
        this.bvR = activity;
    }

    public RectF a(PopupWindow window) {
        RectF rectF = new RectF(this.bvQ);
        if (this.bvR != null && (this.bvR instanceof Activity)) {
            int[] decorViewLocation = new int[2];
            int[] contentLocation = new int[2];
            ((Activity) this.bvR).getWindow().getDecorView().getLocationOnScreen(decorViewLocation);
            window.getContentView().getLocationOnScreen(contentLocation);
            rectF.left += (float) (decorViewLocation[0] - contentLocation[0]);
            rectF.right += (float) (decorViewLocation[0] - contentLocation[0]);
            rectF.top += (float) (decorViewLocation[1] - contentLocation[1]);
            rectF.bottom += (float) (decorViewLocation[1] - contentLocation[1]);
        }
        return rectF;
    }
}
