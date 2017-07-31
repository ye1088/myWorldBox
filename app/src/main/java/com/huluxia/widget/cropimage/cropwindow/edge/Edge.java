package com.huluxia.widget.cropimage.cropwindow.edge;

import android.graphics.Rect;
import android.view.View;
import com.huluxia.widget.cropimage.util.a;

public enum Edge {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;
    
    public static final int MIN_CROP_LENGTH_PX = 40;
    private float mCoordinate;

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
        switch (this) {
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
        float left = LEFT.getCoordinate();
        float top = TOP.getCoordinate();
        float right = RIGHT.getCoordinate();
        float bottom = BOTTOM.getCoordinate();
        switch (this) {
            case LEFT:
                this.mCoordinate = a.calculateLeft(top, right, bottom, aspectRatio);
                return;
            case TOP:
                this.mCoordinate = a.calculateTop(left, right, bottom, aspectRatio);
                return;
            case RIGHT:
                this.mCoordinate = a.calculateRight(left, top, bottom, aspectRatio);
                return;
            case BOTTOM:
                this.mCoordinate = a.calculateBottom(left, top, right, aspectRatio);
                return;
            default:
                return;
        }
    }

    public boolean isNewRectangleOutOfBounds(Edge edge, Rect imageRect, float aspectRatio) {
        float offset = edge.snapOffset(imageRect);
        float top;
        float bottom;
        float right;
        float left;
        switch (this) {
            case LEFT:
                if (edge.equals(TOP)) {
                    top = (float) imageRect.top;
                    bottom = BOTTOM.getCoordinate() - offset;
                    right = RIGHT.getCoordinate();
                    return isOutOfBounds(top, a.calculateLeft(top, right, bottom, aspectRatio), bottom, right, imageRect);
                } else if (edge.equals(BOTTOM)) {
                    bottom = (float) imageRect.bottom;
                    top = TOP.getCoordinate() - offset;
                    right = RIGHT.getCoordinate();
                    return isOutOfBounds(top, a.calculateLeft(top, right, bottom, aspectRatio), bottom, right, imageRect);
                }
                break;
            case TOP:
                if (edge.equals(LEFT)) {
                    left = (float) imageRect.left;
                    right = RIGHT.getCoordinate() - offset;
                    bottom = BOTTOM.getCoordinate();
                    return isOutOfBounds(a.calculateTop(left, right, bottom, aspectRatio), left, bottom, right, imageRect);
                } else if (edge.equals(RIGHT)) {
                    right = (float) imageRect.right;
                    left = LEFT.getCoordinate() - offset;
                    bottom = BOTTOM.getCoordinate();
                    return isOutOfBounds(a.calculateTop(left, right, bottom, aspectRatio), left, bottom, right, imageRect);
                }
                break;
            case RIGHT:
                if (edge.equals(TOP)) {
                    top = (float) imageRect.top;
                    bottom = BOTTOM.getCoordinate() - offset;
                    left = LEFT.getCoordinate();
                    return isOutOfBounds(top, left, bottom, a.calculateRight(left, top, bottom, aspectRatio), imageRect);
                } else if (edge.equals(BOTTOM)) {
                    bottom = (float) imageRect.bottom;
                    top = TOP.getCoordinate() - offset;
                    left = LEFT.getCoordinate();
                    return isOutOfBounds(top, left, bottom, a.calculateRight(left, top, bottom, aspectRatio), imageRect);
                }
                break;
            case BOTTOM:
                if (edge.equals(LEFT)) {
                    left = (float) imageRect.left;
                    right = RIGHT.getCoordinate() - offset;
                    top = TOP.getCoordinate();
                    return isOutOfBounds(top, left, a.calculateBottom(left, top, right, aspectRatio), right, imageRect);
                } else if (edge.equals(RIGHT)) {
                    right = (float) imageRect.right;
                    left = LEFT.getCoordinate() - offset;
                    top = TOP.getCoordinate();
                    return isOutOfBounds(top, left, a.calculateBottom(left, top, right, aspectRatio), right, imageRect);
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
        switch (this) {
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
        switch (this) {
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
        switch (this) {
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

    public static float getWidth() {
        return RIGHT.getCoordinate() - LEFT.getCoordinate();
    }

    public static float getHeight() {
        return BOTTOM.getCoordinate() - TOP.getCoordinate();
    }

    public boolean isOutsideMargin(Rect rect, float margin) {
        boolean result = false;
        switch (this) {
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
        switch (this) {
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

    private static float adjustLeft(float x, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultX = x;
        if (x - ((float) imageRect.left) < imageSnapRadius) {
            return (float) imageRect.left;
        }
        float resultXHoriz = Float.POSITIVE_INFINITY;
        float resultXVert = Float.POSITIVE_INFINITY;
        if (x >= RIGHT.getCoordinate() - 40.0f) {
            resultXHoriz = RIGHT.getCoordinate() - 40.0f;
        }
        if ((RIGHT.getCoordinate() - x) / aspectRatio <= 40.0f) {
            resultXVert = RIGHT.getCoordinate() - (40.0f * aspectRatio);
        }
        return Math.min(resultX, Math.min(resultXHoriz, resultXVert));
    }

    private static float adjustRight(float x, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultX = x;
        if (((float) imageRect.right) - x < imageSnapRadius) {
            return (float) imageRect.right;
        }
        float resultXHoriz = Float.NEGATIVE_INFINITY;
        float resultXVert = Float.NEGATIVE_INFINITY;
        if (x <= LEFT.getCoordinate() + 40.0f) {
            resultXHoriz = LEFT.getCoordinate() + 40.0f;
        }
        if ((x - LEFT.getCoordinate()) / aspectRatio <= 40.0f) {
            resultXVert = LEFT.getCoordinate() + (40.0f * aspectRatio);
        }
        return Math.max(resultX, Math.max(resultXHoriz, resultXVert));
    }

    private static float adjustTop(float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultY = y;
        if (y - ((float) imageRect.top) < imageSnapRadius) {
            return (float) imageRect.top;
        }
        float resultYVert = Float.POSITIVE_INFINITY;
        float resultYHoriz = Float.POSITIVE_INFINITY;
        if (y >= BOTTOM.getCoordinate() - 40.0f) {
            resultYHoriz = BOTTOM.getCoordinate() - 40.0f;
        }
        if ((BOTTOM.getCoordinate() - y) * aspectRatio <= 40.0f) {
            resultYVert = BOTTOM.getCoordinate() - (40.0f / aspectRatio);
        }
        return Math.min(resultY, Math.min(resultYHoriz, resultYVert));
    }

    private static float adjustBottom(float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultY = y;
        if (((float) imageRect.bottom) - y < imageSnapRadius) {
            return (float) imageRect.bottom;
        }
        float resultYVert = Float.NEGATIVE_INFINITY;
        float resultYHoriz = Float.NEGATIVE_INFINITY;
        if (y <= TOP.getCoordinate() + 40.0f) {
            resultYVert = TOP.getCoordinate() + 40.0f;
        }
        if ((y - TOP.getCoordinate()) * aspectRatio <= 40.0f) {
            resultYHoriz = TOP.getCoordinate() + (40.0f / aspectRatio);
        }
        return Math.max(resultY, Math.max(resultYHoriz, resultYVert));
    }
}
