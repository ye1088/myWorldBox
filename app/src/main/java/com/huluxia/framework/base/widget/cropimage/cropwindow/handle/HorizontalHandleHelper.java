package com.huluxia.framework.base.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdge;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleFactory.HandleType;
import com.huluxia.framework.base.widget.cropimage.util.AspectRatioUtil;

class HorizontalHandleHelper extends HandleHelper {
    private MyEdge mEdge;

    HorizontalHandleHelper(MyEdge edge, HandleType handleType) {
        super(edge, null, handleType);
        this.mEdge = edge;
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        float left = this.mEdge.getEdges().getLeft().getCoordinate();
        float top = this.mEdge.getEdges().getTop().getCoordinate();
        float right = this.mEdge.getEdges().getRight().getCoordinate();
        float halfDifference = (AspectRatioUtil.calculateWidth(top, this.mEdge.getEdges().getBottom().getCoordinate(), targetAspectRatio) - (right - left)) / 2.0f;
        left -= halfDifference;
        right += halfDifference;
        MyEdge leftEdge = this.mEdge.getEdges().getLeft();
        MyEdge rightEdge = this.mEdge.getEdges().getRight();
        leftEdge.setCoordinate(left);
        rightEdge.setCoordinate(right);
        if (leftEdge.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(leftEdge, imageRect, targetAspectRatio)) {
            rightEdge.offset(-leftEdge.snapToRect(imageRect));
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }
        if (rightEdge.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(rightEdge, imageRect, targetAspectRatio)) {
            leftEdge.offset(-rightEdge.snapToRect(imageRect));
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
