package com.MCWorld.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.MCWorld.widget.cropimage.cropwindow.edge.Edge;
import com.MCWorld.widget.cropimage.util.a;

/* compiled from: HorizontalHandleHelper */
class d extends c {
    private Edge bwb;

    d(Edge edge) {
        super(edge, null);
        this.bwb = edge;
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.bwb.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float halfDifference = (a.calculateWidth(top, Edge.BOTTOM.getCoordinate(), targetAspectRatio) - (right - left)) / 2.0f;
        right += halfDifference;
        Edge.LEFT.setCoordinate(left - halfDifference);
        Edge.RIGHT.setCoordinate(right);
        if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius) && !this.bwb.isNewRectangleOutOfBounds(Edge.LEFT, imageRect, targetAspectRatio)) {
            Edge.RIGHT.offset(-Edge.LEFT.snapToRect(imageRect));
            this.bwb.adjustCoordinate(targetAspectRatio);
        }
        if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius) && !this.bwb.isNewRectangleOutOfBounds(Edge.RIGHT, imageRect, targetAspectRatio)) {
            Edge.LEFT.offset(-Edge.RIGHT.snapToRect(imageRect));
            this.bwb.adjustCoordinate(targetAspectRatio);
        }
    }
}
