package com.MCWorld.framework.base.widget.cropimage.cropwindow.edge;

import com.MCWorld.framework.base.widget.cropimage.cropwindow.edge.MyEdge.EdgeType;

public class MyEdges {
    private MyEdge mBottom = new MyEdge(EdgeType.BOTTOM, this);
    private MyEdge mLeft = new MyEdge(EdgeType.LEFT, this);
    private MyEdge mRight = new MyEdge(EdgeType.RIGHT, this);
    private MyEdge mTop = new MyEdge(EdgeType.TOP, this);

    public MyEdge getLeft() {
        return this.mLeft;
    }

    public MyEdge getTop() {
        return this.mTop;
    }

    public MyEdge getRight() {
        return this.mRight;
    }

    public MyEdge getBottom() {
        return this.mBottom;
    }
}
