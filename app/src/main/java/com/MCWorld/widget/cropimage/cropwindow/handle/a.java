package com.MCWorld.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.MCWorld.widget.cropimage.cropwindow.edge.Edge;

/* compiled from: CenterHandleHelper */
class a extends c {
    a() {
        super(null, null);
    }

    void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float offsetX = x - ((left + right) / 2.0f);
        float offsetY = y - ((top + Edge.BOTTOM.getCoordinate()) / 2.0f);
        Edge.LEFT.offset(offsetX);
        Edge.TOP.offset(offsetY);
        Edge.RIGHT.offset(offsetX);
        Edge.BOTTOM.offset(offsetY);
        if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius)) {
            Edge.RIGHT.offset(Edge.LEFT.snapToRect(imageRect));
        } else if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius)) {
            Edge.LEFT.offset(Edge.RIGHT.snapToRect(imageRect));
        }
        if (Edge.TOP.isOutsideMargin(imageRect, snapRadius)) {
            Edge.BOTTOM.offset(Edge.TOP.snapToRect(imageRect));
        } else if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius)) {
            Edge.TOP.offset(Edge.BOTTOM.snapToRect(imageRect));
        }
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        updateCropWindow(x, y, imageRect, snapRadius);
    }
}
