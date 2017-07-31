package com.huluxia.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.huluxia.widget.cropimage.cropwindow.edge.Edge;
import com.huluxia.widget.cropimage.cropwindow.edge.a;

/* compiled from: CornerHandleHelper */
class b extends c {
    b(Edge horizontalEdge, Edge verticalEdge) {
        super(horizontalEdge, verticalEdge);
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        a activeEdges = e(x, y, targetAspectRatio);
        Edge primaryEdge = activeEdges.bvW;
        Edge secondaryEdge = activeEdges.bvX;
        primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        secondaryEdge.adjustCoordinate(targetAspectRatio);
        if (secondaryEdge.isOutsideMargin(imageRect, snapRadius)) {
            secondaryEdge.snapToRect(imageRect);
            primaryEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
