package com.MCWorld.widget.cropimage.cropwindow.handle;

import android.graphics.Rect;
import com.MCWorld.widget.cropimage.cropwindow.edge.Edge;
import com.MCWorld.widget.cropimage.cropwindow.edge.a;

/* compiled from: HandleHelper */
abstract class c {
    private static final float UNFIXED_ASPECT_RATIO_CONSTANT = 1.0f;
    private Edge bvY;
    private Edge bvZ;
    private a bwa = new a(this.bvY, this.bvZ);

    abstract void updateCropWindow(float f, float f2, float f3, Rect rect, float f4);

    c(Edge horizontalEdge, Edge verticalEdge) {
        this.bvY = horizontalEdge;
        this.bvZ = verticalEdge;
    }

    void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        a activeEdges = Oi();
        Edge primaryEdge = activeEdges.bvW;
        Edge secondaryEdge = activeEdges.bvX;
        if (primaryEdge != null) {
            primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
        }
        if (secondaryEdge != null) {
            secondaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
        }
    }

    a Oi() {
        return this.bwa;
    }

    a e(float x, float y, float targetAspectRatio) {
        if (getAspectRatio(x, y) > targetAspectRatio) {
            this.bwa.bvW = this.bvZ;
            this.bwa.bvX = this.bvY;
        } else {
            this.bwa.bvW = this.bvY;
            this.bwa.bvX = this.bvZ;
        }
        return this.bwa;
    }

    private float getAspectRatio(float x, float y) {
        return com.MCWorld.widget.cropimage.util.a.calculateAspectRatio(this.bvZ == Edge.LEFT ? x : Edge.LEFT.getCoordinate(), this.bvY == Edge.TOP ? y : Edge.TOP.getCoordinate(), this.bvZ == Edge.RIGHT ? x : Edge.RIGHT.getCoordinate(), this.bvY == Edge.BOTTOM ? y : Edge.BOTTOM.getCoordinate());
    }
}
