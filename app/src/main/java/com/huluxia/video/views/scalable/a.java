package com.huluxia.video.views.scalable;

import android.graphics.Matrix;

/* compiled from: ScaleManager */
public class a {
    private b brx;
    private b bry;

    public a(b viewSize, b videoSize) {
        this.brx = viewSize;
        this.bry = videoSize;
    }

    public Matrix a(ScalableType scalableType) {
        switch (scalableType) {
            case NONE:
                return NH();
            case FIT_XY:
                return NI();
            case FIT_CENTER:
                return NK();
            case FIT_START:
                return NJ();
            case FIT_END:
                return NL();
            case LEFT_TOP:
                return b(PivotPoint.LEFT_TOP);
            case LEFT_CENTER:
                return b(PivotPoint.LEFT_CENTER);
            case LEFT_BOTTOM:
                return b(PivotPoint.LEFT_BOTTOM);
            case CENTER_TOP:
                return b(PivotPoint.CENTER_TOP);
            case CENTER:
                return b(PivotPoint.CENTER);
            case CENTER_BOTTOM:
                return b(PivotPoint.CENTER_BOTTOM);
            case RIGHT_TOP:
                return b(PivotPoint.RIGHT_TOP);
            case RIGHT_CENTER:
                return b(PivotPoint.RIGHT_CENTER);
            case RIGHT_BOTTOM:
                return b(PivotPoint.RIGHT_BOTTOM);
            case LEFT_TOP_CROP:
                return c(PivotPoint.LEFT_TOP);
            case LEFT_CENTER_CROP:
                return c(PivotPoint.LEFT_CENTER);
            case LEFT_BOTTOM_CROP:
                return c(PivotPoint.LEFT_BOTTOM);
            case CENTER_TOP_CROP:
                return c(PivotPoint.CENTER_TOP);
            case CENTER_CROP:
                return c(PivotPoint.CENTER);
            case CENTER_BOTTOM_CROP:
                return c(PivotPoint.CENTER_BOTTOM);
            case RIGHT_TOP_CROP:
                return c(PivotPoint.RIGHT_TOP);
            case RIGHT_CENTER_CROP:
                return c(PivotPoint.RIGHT_CENTER);
            case RIGHT_BOTTOM_CROP:
                return c(PivotPoint.RIGHT_BOTTOM);
            case START_INSIDE:
                return NM();
            case CENTER_INSIDE:
                return NN();
            case END_INSIDE:
                return NO();
            default:
                return null;
        }
    }

    private Matrix d(float sx, float sy, float px, float py) {
        Matrix matrix = new Matrix();
        matrix.setScale(sx, sy, px, py);
        return matrix;
    }

    private Matrix a(float sx, float sy, PivotPoint pivotPoint) {
        switch (pivotPoint) {
            case LEFT_TOP:
                return d(sx, sy, 0.0f, 0.0f);
            case LEFT_CENTER:
                return d(sx, sy, 0.0f, ((float) this.brx.getHeight()) / 2.0f);
            case LEFT_BOTTOM:
                return d(sx, sy, 0.0f, (float) this.brx.getHeight());
            case CENTER_TOP:
                return d(sx, sy, ((float) this.brx.getWidth()) / 2.0f, 0.0f);
            case CENTER:
                return d(sx, sy, ((float) this.brx.getWidth()) / 2.0f, ((float) this.brx.getHeight()) / 2.0f);
            case CENTER_BOTTOM:
                return d(sx, sy, ((float) this.brx.getWidth()) / 2.0f, (float) this.brx.getHeight());
            case RIGHT_TOP:
                return d(sx, sy, (float) this.brx.getWidth(), 0.0f);
            case RIGHT_CENTER:
                return d(sx, sy, (float) this.brx.getWidth(), ((float) this.brx.getHeight()) / 2.0f);
            case RIGHT_BOTTOM:
                return d(sx, sy, (float) this.brx.getWidth(), (float) this.brx.getHeight());
            default:
                throw new IllegalArgumentException("Illegal PivotPoint");
        }
    }

    private Matrix NH() {
        return a(((float) this.bry.getWidth()) / ((float) this.brx.getWidth()), ((float) this.bry.getHeight()) / ((float) this.brx.getHeight()), PivotPoint.LEFT_TOP);
    }

    private Matrix a(PivotPoint pivotPoint) {
        float sx = ((float) this.brx.getWidth()) / ((float) this.bry.getWidth());
        float sy = ((float) this.brx.getHeight()) / ((float) this.bry.getHeight());
        float minScale = Math.min(sx, sy);
        return a(minScale / sx, minScale / sy, pivotPoint);
    }

    private Matrix NI() {
        return a(1.0f, 1.0f, PivotPoint.LEFT_TOP);
    }

    private Matrix NJ() {
        return a(PivotPoint.LEFT_TOP);
    }

    private Matrix NK() {
        return a(PivotPoint.CENTER);
    }

    private Matrix NL() {
        return a(PivotPoint.RIGHT_BOTTOM);
    }

    private Matrix b(PivotPoint pivotPoint) {
        return a(((float) this.bry.getWidth()) / ((float) this.brx.getWidth()), ((float) this.bry.getHeight()) / ((float) this.brx.getHeight()), pivotPoint);
    }

    private Matrix c(PivotPoint pivotPoint) {
        float sx = ((float) this.brx.getWidth()) / ((float) this.bry.getWidth());
        float sy = ((float) this.brx.getHeight()) / ((float) this.bry.getHeight());
        float maxScale = Math.max(sx, sy);
        return a(maxScale / sx, maxScale / sy, pivotPoint);
    }

    private Matrix NM() {
        if (this.bry.getHeight() > this.brx.getWidth() || this.bry.getHeight() > this.brx.getHeight()) {
            return NJ();
        }
        return b(PivotPoint.LEFT_TOP);
    }

    private Matrix NN() {
        if (this.bry.getHeight() > this.brx.getWidth() || this.bry.getHeight() > this.brx.getHeight()) {
            return NK();
        }
        return b(PivotPoint.CENTER);
    }

    private Matrix NO() {
        if (this.bry.getHeight() > this.brx.getWidth() || this.bry.getHeight() > this.brx.getHeight()) {
            return NL();
        }
        return b(PivotPoint.RIGHT_BOTTOM);
    }
}
