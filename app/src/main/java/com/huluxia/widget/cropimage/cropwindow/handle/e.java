package com.huluxia.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.huluxia.widget.cropimage.cropwindow.edge.Edge;
import com.huluxia.widget.cropimage.util.a;

/* compiled from: VerticalHandleHelper */
class e extends c {
    private Edge bwb;

    e(Edge edge) {
        super(null, edge);
        this.bwb = edge;
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.bwb.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        float halfDifference = (a.calculateHeight(left, right, targetAspectRatio) - (bottom - top)) / 2.0f;
        bottom += halfDifference;
        Edge.TOP.setCoordinate(top - halfDifference);
        Edge.BOTTOM.setCoordinate(bottom);
        if (Edge.TOP.isOutsideMargin(imageRect, snapRadius) && !this.bwb.isNewRectangleOutOfBounds(Edge.TOP, imageRect, targetAspectRatio)) {
            Edge.BOTTOM.offset(-Edge.TOP.snapToRect(imageRect));
            this.bwb.adjustCoordinate(targetAspectRatio);
        }
        if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius) && !this.bwb.isNewRectangleOutOfBounds(Edge.BOTTOM, imageRect, targetAspectRatio)) {
            Edge.TOP.offset(-Edge.BOTTOM.snapToRect(imageRect));
            this.bwb.adjustCoordinate(targetAspectRatio);
        }
    }
}
