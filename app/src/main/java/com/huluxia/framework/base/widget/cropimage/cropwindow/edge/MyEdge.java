package com.huluxia.framework.base.widget.cropimage.cropwindow.edge;

import android.graphics.Rect;
import android.view.View;
import com.huluxia.framework.base.widget.cropimage.util.AspectRatioUtil;

public class MyEdge {
    public static int MIN_CROP_LENGTH_PX = 120;
    private float mCoordinate;
    private EdgeType mEdgeType;
    private MyEdges mEdges;

    public enum EdgeType {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    public MyEdge(EdgeType edgeType, MyEdges edges) {
        this.mEdgeType = edgeType;
        this.mEdges = edges;
    }

    public EdgeType getEdgeType() {
        return this.mEdgeType;
    }

    public MyEdges getEdges() {
        return this.mEdges;
    }

    public void setCoordinate(float coordinate) {
        this.mCoordinate = coordinate;
    }

    public void offset(float distance) {
        this.mCoordinate += distance;
    }

    public float getCoordinate() {
        return this.mCoordinate;
    }

    public void adjustCoordinate(float x, float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        switch (this.mEdgeType) {
            case LEFT:
                this.mCoordinate = adjustLeft(x, imageRect, imageSnapRadius, aspectRatio);
                return;
            case TOP:
                this.mCoordinate = adjustTop(y, imageRect, imageSnapRadius, aspectRatio);
                return;
            case RIGHT:
                this.mCoordinate = adjustRight(x, imageRect, imageSnapRadius, aspectRatio);
                return;
            case BOTTOM:
                this.mCoordinate = adjustBottom(y, imageRect, imageSnapRadius, aspectRatio);
                return;
            default:
                return;
        }
    }

    public void adjustCoordinate(float aspectRatio) {
        float left = this.mEdges.getLeft().getCoordinate();
        float top = this.mEdges.getTop().getCoordinate();
        float right = this.mEdges.getRight().getCoordinate();
        float bottom = this.mEdges.getBottom().getCoordinate();
        switch (this.mEdgeType) {
            case LEFT:
                this.mCoordinate = AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio);
                return;
            case TOP:
                this.mCoordinate = AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio);
                return;
            case RIGHT:
                this.mCoordinate = AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio);
                return;
            case BOTTOM:
                this.mCoordinate = AspectRatioUtil.calculateBottom(left, top, right, aspectRatio);
                return;
            default:
                return;
        }
    }

    public boolean isNewRectangleOutOfBounds(MyEdge edge, Rect imageRect, float aspectRatio) {
        float offset = edge.snapOffset(imageRect);
        float top;
        float bottom;
        float right;
        float left;
        switch (this.mEdgeType) {
            case LEFT:
                if (edge.mEdgeType.equals(EdgeType.TOP)) {
                    top = (float) imageRect.top;
                    bottom = this.mEdges.getBottom().getCoordinate() - offset;
                    right = this.mEdges.getRight().getCoordinate();
                    return isOutOfBounds(top, AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio), bottom, right, imageRect);
                } else if (edge.mEdgeType.equals(EdgeType.BOTTOM)) {
                    bottom = (float) imageRect.bottom;
                    top = this.mEdges.getTop().getCoordinate() - offset;
                    right = this.mEdges.getRight().getCoordinate();
                    return isOutOfBounds(top, AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio), bottom, right, imageRect);
                }
                break;
            case TOP:
                if (edge.mEdgeType.equals(EdgeType.LEFT)) {
                    left = (float) imageRect.left;
                    right = this.mEdges.getRight().getCoordinate() - offset;
                    bottom = this.mEdges.getBottom().getCoordinate();
                    return isOutOfBounds(AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio), left, bottom, right, imageRect);
                } else if (edge.mEdgeType.equals(EdgeType.RIGHT)) {
                    right = (float) imageRect.right;
                    left = this.mEdges.getLeft().getCoordinate() - offset;
                    bottom = this.mEdges.getBottom().getCoordinate();
                    return isOutOfBounds(AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio), left, bottom, right, imageRect);
                }
                break;
            case RIGHT:
                if (edge.mEdgeType.equals(EdgeType.TOP)) {
                    top = (float) imageRect.top;
                    bottom = this.mEdges.getBottom().getCoordinate() - offset;
                    left = this.mEdges.getLeft().getCoordinate();
                    return isOutOfBounds(top, left, bottom, AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio), imageRect);
                } else if (edge.mEdgeType.equals(EdgeType.BOTTOM)) {
                    bottom = (float) imageRect.bottom;
                    top = this.mEdges.getTop().getCoordinate() - offset;
                    left = this.mEdges.getLeft().getCoordinate();
                    return isOutOfBounds(top, left, bottom, AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio), imageRect);
                }
                break;
            case BOTTOM:
                if (edge.mEdgeType.equals(EdgeType.LEFT)) {
                    left = (float) imageRect.left;
                    right = this.mEdges.getRight().getCoordinate() - offset;
                    top = this.mEdges.getTop().getCoordinate();
                    return isOutOfBounds(top, left, AspectRatioUtil.calculateBottom(left, top, right, aspectRatio), right, imageRect);
                } else if (edge.mEdgeType.equals(EdgeType.RIGHT)) {
                    right = (float) imageRect.right;
                    left = this.mEdges.getLeft().getCoordinate() - offset;
                    top = this.mEdges.getTop().getCoordinate();
                    return isOutOfBounds(top, left, AspectRatioUtil.calculateBottom(left, top, right, aspectRatio), right, imageRect);
                }
                break;
        }
        return true;
    }

    private boolean isOutOfBounds(float top, float left, float bottom, float right, Rect imageRect) {
        return top < ((float) imageRect.top) || left < ((float) imageRect.left) || bottom > ((float) imageRect.bottom) || right > ((float) imageRect.right);
    }

    public float snapToRect(Rect imageRect) {
        float oldCoordinate = this.mCoordinate;
        switch (this.mEdgeType) {
            case LEFT:
                this.mCoordinate = (float) imageRect.left;
                break;
            case TOP:
                this.mCoordinate = (float) imageRect.top;
                break;
            case RIGHT:
                this.mCoordinate = (float) imageRect.right;
                break;
            case BOTTOM:
                this.mCoordinate = (float) imageRect.bottom;
                break;
        }
        return this.mCoordinate - oldCoordinate;
    }

    public float snapOffset(Rect imageRect) {
        float oldCoordinate = this.mCoordinate;
        float newCoordinate = oldCoordinate;
        switch (this.mEdgeType) {
            case LEFT:
                newCoordinate = (float) imageRect.left;
                break;
            case TOP:
                newCoordinate = (float) imageRect.top;
                break;
            case RIGHT:
                newCoordinate = (float) imageRect.right;
                break;
            case BOTTOM:
                newCoordinate = (float) imageRect.bottom;
                break;
        }
        return newCoordinate - oldCoordinate;
    }

    public void snapToView(View view) {
        switch (this.mEdgeType) {
            case LEFT:
                this.mCoordinate = 0.0f;
                return;
            case TOP:
                this.mCoordinate = 0.0f;
                return;
            case RIGHT:
                this.mCoordinate = (float) view.getWidth();
                return;
            case BOTTOM:
                this.mCoordinate = (float) view.getHeight();
                return;
            default:
                return;
        }
    }

    public float getWidth() {
        return this.mEdges.getRight().getCoordinate() - this.mEdges.getLeft().getCoordinate();
    }

    public float getHeight() {
        return this.mEdges.getBottom().getCoordinate() - this.mEdges.getTop().getCoordinate();
    }

    public boolean isOutsideMargin(Rect rect, float margin) {
        boolean result = false;
        switch (this.mEdgeType) {
            case LEFT:
                if (this.mCoordinate - ((float) rect.left) < margin) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case TOP:
                if (this.mCoordinate - ((float) rect.top) < margin) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case RIGHT:
                if (((float) rect.right) - this.mCoordinate < margin) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case BOTTOM:
                result = ((float) rect.bottom) - this.mCoordinate < margin;
                break;
        }
        return result;
    }

    public boolean isOutsideFrame(Rect rect) {
        boolean result = false;
        switch (this.mEdgeType) {
            case LEFT:
                if (((double) (this.mCoordinate - ((float) rect.left))) < 0.0d) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case TOP:
                if (((double) (this.mCoordinate - ((float) rect.top))) < 0.0d) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case RIGHT:
                if (((double) (((float) rect.right) - this.mCoordinate)) < 0.0d) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case BOTTOM:
                result = ((double) (((float) rect.bottom) - this.mCoordinate)) < 0.0d;
                break;
        }
        return result;
    }

    private float adjustLeft(float x, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultX = x;
        if (x - ((float) imageRect.left) < imageSnapRadius) {
            return (float) imageRect.left;
        }
        float resultXHoriz = Float.POSITIVE_INFINITY;
        float resultXVert = Float.POSITIVE_INFINITY;
        if (x >= this.mEdges.getRight().getCoordinate() - ((float) MIN_CROP_LENGTH_PX)) {
            resultXHoriz = this.mEdges.getRight().getCoordinate() - ((float) MIN_CROP_LENGTH_PX);
        }
        if ((this.mEdges.getRight().getCoordinate() - x) / aspectRatio <= ((float) MIN_CROP_LENGTH_PX)) {
            resultXVert = this.mEdges.getRight().getCoordinate() - (((float) MIN_CROP_LENGTH_PX) * aspectRatio);
        }
        return Math.min(resultX, Math.min(resultXHoriz, resultXVert));
    }

    private float adjustRight(float x, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultX = x;
        if (((float) imageRect.right) - x < imageSnapRadius) {
            return (float) imageRect.right;
        }
        float resultXHoriz = Float.NEGATIVE_INFINITY;
        float resultXVert = Float.NEGATIVE_INFINITY;
        if (x <= this.mEdges.getLeft().getCoordinate() + ((float) MIN_CROP_LENGTH_PX)) {
            resultXHoriz = this.mEdges.getLeft().getCoordinate() + ((float) MIN_CROP_LENGTH_PX);
        }
        if ((x - this.mEdges.getLeft().getCoordinate()) / aspectRatio <= ((float) MIN_CROP_LENGTH_PX)) {
            resultXVert = this.mEdges.getLeft().getCoordinate() + (((float) MIN_CROP_LENGTH_PX) * aspectRatio);
        }
        return Math.max(resultX, Math.max(resultXHoriz, resultXVert));
    }

    private float adjustTop(float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultY = y;
        if (y - ((float) imageRect.top) < imageSnapRadius) {
            return (float) imageRect.top;
        }
        float resultYVert = Float.POSITIVE_INFINITY;
        float resultYHoriz = Float.POSITIVE_INFINITY;
        if (y >= this.mEdges.getBottom().getCoordinate() - ((float) MIN_CROP_LENGTH_PX)) {
            resultYHoriz = this.mEdges.getBottom().getCoordinate() - ((float) MIN_CROP_LENGTH_PX);
        }
        if ((this.mEdges.getBottom().getCoordinate() - y) * aspectRatio <= ((float) MIN_CROP_LENGTH_PX)) {
            resultYVert = this.mEdges.getBottom().getCoordinate() - (((float) MIN_CROP_LENGTH_PX) / aspectRatio);
        }
        return Math.min(resultY, Math.min(resultYHoriz, resultYVert));
    }

    private float adjustBottom(float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultY = y;
        if (((float) imageRect.bottom) - y < imageSnapRadius) {
            return (float) imageRect.bottom;
        }
        float resultYVert = Float.NEGATIVE_INFINITY;
        float resultYHoriz = Float.NEGATIVE_INFINITY;
        if (y <= this.mEdges.getTop().getCoordinate() + ((float) MIN_CROP_LENGTH_PX)) {
            resultYVert = this.mEdges.getTop().getCoordinate() + ((float) MIN_CROP_LENGTH_PX);
        }
        if ((y - this.mEdges.getTop().getCoordinate()) * aspectRatio <= ((float) MIN_CROP_LENGTH_PX)) {
            resultYHoriz = this.mEdges.getTop().getCoordinate() + (((float) MIN_CROP_LENGTH_PX) / aspectRatio);
        }
        return Math.max(resultY, Math.max(resultYHoriz, resultYVert));
    }
}
