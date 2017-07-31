package com.huluxia.framework.base.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdge;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdge.EdgeType;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdgePair;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleFactory.HandleType;
import com.huluxia.framework.base.widget.cropimage.util.AspectRatioUtil;

public abstract class HandleHelper {
    private static final float UNFIXED_ASPECT_RATIO_CONSTANT = 1.0f;
    protected MyEdgePair mActiveEdges = new MyEdgePair(this.mHorizontalEdge, this.mVerticalEdge);
    protected HandleType mHandleType;
    protected MyEdge mHorizontalEdge;
    protected MyEdge mVerticalEdge;

    public abstract void updateCropWindow(float f, float f2, float f3, Rect rect, float f4);

    HandleHelper(MyEdge horizontalEdge, MyEdge verticalEdge, HandleType handleType) {
        this.mHorizontalEdge = horizontalEdge;
        this.mVerticalEdge = verticalEdge;
        this.mHandleType = handleType;
    }

    public HandleType getHandleType() {
        return this.mHandleType;
    }

    public void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        MyEdgePair activeEdges = getActiveEdges();
        MyEdge primaryEdge = activeEdges.primary;
        MyEdge secondaryEdge = activeEdges.secondary;
        if (primaryEdge != null) {
            primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
        }
        if (secondaryEdge != null) {
            secondaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
        }
    }

    MyEdgePair getActiveEdges() {
        return this.mActiveEdges;
    }

    MyEdgePair getActiveEdges(float x, float y, float targetAspectRatio) {
        if (getAspectRatio(x, y) > targetAspectRatio) {
            this.mActiveEdges.primary = this.mVerticalEdge;
            this.mActiveEdges.secondary = this.mHorizontalEdge;
        } else {
            this.mActiveEdges.primary = this.mHorizontalEdge;
            this.mActiveEdges.secondary = this.mVerticalEdge;
        }
        return this.mActiveEdges;
    }

    private float getAspectRatio(float x, float y) {
        return AspectRatioUtil.calculateAspectRatio(this.mVerticalEdge.getEdgeType() == EdgeType.LEFT ? x : this.mVerticalEdge.getEdges().getLeft().getCoordinate(), this.mHorizontalEdge.getEdgeType() == EdgeType.TOP ? y : this.mHorizontalEdge.getEdges().getTop().getCoordinate(), this.mVerticalEdge.getEdgeType() == EdgeType.RIGHT ? x : this.mVerticalEdge.getEdges().getRight().getCoordinate(), this.mHorizontalEdge.getEdgeType() == EdgeType.BOTTOM ? y : this.mHorizontalEdge.getEdges().getBottom().getCoordinate());
    }
}
