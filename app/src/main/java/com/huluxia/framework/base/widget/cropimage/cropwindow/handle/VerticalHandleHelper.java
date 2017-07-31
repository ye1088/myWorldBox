package com.huluxia.framework.base.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdge;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleFactory.HandleType;
import com.huluxia.framework.base.widget.cropimage.util.AspectRatioUtil;

class VerticalHandleHelper extends HandleHelper {
    private MyEdge mEdge;

    VerticalHandleHelper(MyEdge edge, HandleType handleType) {
        super(null, edge, handleType);
        this.mEdge = edge;
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        float left = this.mEdge.getEdges().getLeft().getCoordinate();
        float top = this.mEdge.getEdges().getTop().getCoordinate();
        float right = this.mEdge.getEdges().getRight().getCoordinate();
        float bottom = this.mEdge.getEdges().getBottom().getCoordinate();
        float halfDifference = (AspectRatioUtil.calculateHeight(left, right, targetAspectRatio) - (bottom - top)) / 2.0f;
        top -= halfDifference;
        bottom += halfDifference;
        MyEdge topEdge = this.mEdge.getEdges().getLeft();
        MyEdge bottomEdge = this.mEdge.getEdges().getRight();
        topEdge.setCoordinate(top);
        bottomEdge.setCoordinate(bottom);
        if (topEdge.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(topEdge, imageRect, targetAspectRatio)) {
            bottomEdge.offset(-topEdge.snapToRect(imageRect));
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }
        if (bottomEdge.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(bottomEdge, imageRect, targetAspectRatio)) {
            topEdge.offset(-bottomEdge.snapToRect(imageRect));
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
