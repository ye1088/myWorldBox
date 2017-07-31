package com.huluxia.framework.base.widget.cropimage.cropwindow.edge;

public class MyEdgePair {
    public MyEdge primary;
    public MyEdge secondary;

    public MyEdgePair(MyEdge edge1, MyEdge edge2) {
        this.primary = edge1;
        this.secondary = edge2;
    }
}
