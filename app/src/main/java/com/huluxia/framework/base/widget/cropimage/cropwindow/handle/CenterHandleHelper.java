package com.huluxia.framework.base.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdges;
import com.huluxia.framework.base.widget.cropimage.cropwindow.handle.HandleFactory.HandleType;

class CenterHandleHelper extends HandleHelper {
    private MyEdges edges;

    CenterHandleHelper(MyEdges paraEdges, HandleType handleType) {
        super(null, null, handleType);
        this.edges = paraEdges;
    }

    public void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        float left = this.edges.getLeft().getCoordinate();
        float top = this.edges.getTop().getCoordinate();
        float right = this.edges.getRight().getCoordinate();
        float offsetX = x - ((left + right) / 2.0f);
        float offsetY = y - ((top + this.edges.getBottom().getCoordinate()) / 2.0f);
        this.edges.getLeft().offset(offsetX);
        this.edges.getTop().offset(offsetY);
        this.edges.getRight().offset(offsetX);
        this.edges.getBottom().offset(offsetY);
        if (this.edges.getLeft().isOutsideMargin(imageRect, snapRadius)) {
            this.edges.getRight().offset(this.edges.getLeft().snapToRect(imageRect));
        } else if (this.edges.getRight().isOutsideMargin(imageRect, snapRadius)) {
            this.edges.getLeft().offset(this.edges.getRight().snapToRect(imageRect));
        }
        if (this.edges.getTop().isOutsideMargin(imageRect, snapRadius)) {
            this.edges.getBottom().offset(this.edges.getTop().snapToRect(imageRect));
        } else if (this.edges.getBottom().isOutsideMargin(imageRect, snapRadius)) {
            this.edges.getTop().offset(this.edges.getBottom().snapToRect(imageRect));
        }
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        updateCropWindow(x, y, imageRect, snapRadius);
    }
}
