package com.huluxia.framework.base.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdge;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdgePair;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleFactory.HandleType;

class CornerHandleHelper extends HandleHelper {
    CornerHandleHelper(MyEdge horizontalEdge, MyEdge verticalEdge, HandleType handleType) {
        super(horizontalEdge, verticalEdge, handleType);
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        MyEdgePair activeEdges = getActiveEdges(x, y, targetAspectRatio);
        MyEdge primaryEdge = activeEdges.primary;
        MyEdge secondaryEdge = activeEdges.secondary;
        primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        secondaryEdge.adjustCoordinate(targetAspectRatio);
        if (secondaryEdge.isOutsideMargin(imageRect, snapRadius)) {
            secondaryEdge.snapToRect(imageRect);
            primaryEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
