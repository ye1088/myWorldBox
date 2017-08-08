package com.MCWorld.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.MCWorld.widget.cropimage.cropwindow.edge.Edge;

public enum Handle {
    TOP_LEFT(new b(Edge.TOP, Edge.LEFT)),
    TOP_RIGHT(new b(Edge.TOP, Edge.RIGHT)),
    BOTTOM_LEFT(new b(Edge.BOTTOM, Edge.LEFT)),
    BOTTOM_RIGHT(new b(Edge.BOTTOM, Edge.RIGHT)),
    LEFT(new e(Edge.LEFT)),
    TOP(new d(Edge.TOP)),
    RIGHT(new e(Edge.RIGHT)),
    BOTTOM(new d(Edge.BOTTOM)),
    CENTER(new a());
    
    private c mHelper;

    private Handle(c helper) {
        this.mHelper = helper;
    }

    public void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        this.mHelper.updateCropWindow(x, y, imageRect, snapRadius);
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mHelper.updateCropWindow(x, y, targetAspectRatio, imageRect, snapRadius);
    }
}
