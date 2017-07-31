package com.huluxia.framework.base.widget.cropimage.cropwindow.handle;

import com.huluxia.framework.base.widget.cropimage.cropwindow.edge.MyEdges;

public class HandleFactory {

    public enum HandleType {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        CENTER
    }

    public static HandleHelper createHelper(MyEdges edges, HandleType handleType) {
        switch (handleType) {
            case TOP_LEFT:
                return new CornerHandleHelper(edges.getTop(), edges.getLeft(), handleType);
            case TOP_RIGHT:
                return new CornerHandleHelper(edges.getTop(), edges.getRight(), handleType);
            case BOTTOM_LEFT:
                return new CornerHandleHelper(edges.getBottom(), edges.getLeft(), handleType);
            case BOTTOM_RIGHT:
                return new CornerHandleHelper(edges.getBottom(), edges.getRight(), handleType);
            case LEFT:
                return new VerticalHandleHelper(edges.getLeft(), handleType);
            case TOP:
                return new HorizontalHandleHelper(edges.getTop(), handleType);
            case RIGHT:
                return new VerticalHandleHelper(edges.getRight(), handleType);
            case BOTTOM:
                return new HorizontalHandleHelper(edges.getBottom(), handleType);
            case CENTER:
                return new CenterHandleHelper(edges, handleType);
            default:
                return null;
        }
    }
}
