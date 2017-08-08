package com.MCWorld.widget.cropimage.cropwindow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.MCWorld.widget.cropimage.cropwindow.edge.Edge;
import com.MCWorld.widget.cropimage.cropwindow.handle.Handle;
import com.MCWorld.widget.cropimage.util.a;
import com.MCWorld.widget.cropimage.util.b;
import com.MCWorld.widget.cropimage.util.d;

public class CropOverlayView extends ImageView {
    private static final float DEFAULT_CORNER_EXTENSION_DP = ((DEFAULT_CORNER_THICKNESS_DP / 2.0f) + DEFAULT_CORNER_OFFSET_DP);
    private static final float DEFAULT_CORNER_LENGTH_DP = 20.0f;
    private static final float DEFAULT_CORNER_OFFSET_DP = ((DEFAULT_CORNER_THICKNESS_DP / 2.0f) - (DEFAULT_LINE_THICKNESS_DP / 2.0f));
    private static final float DEFAULT_CORNER_THICKNESS_DP = d.getCornerThickness();
    private static final float DEFAULT_LINE_THICKNESS_DP = d.getLineThickness();
    private static final float DEFAULT_SHOW_GUIDELINES_LIMIT = 100.0f;
    private static final int GUIDELINES_OFF = 0;
    private static final int GUIDELINES_ON = 2;
    private static final int GUIDELINES_ON_TOUCH = 1;
    private static final int SNAP_RADIUS_DP = 6;
    private Handle bvU;
    private boolean initializedCropWindow = false;
    private int mAspectRatioX = 1;
    private int mAspectRatioY = 1;
    private Paint mBackgroundPaint;
    private Rect mBitmapRect;
    private Paint mBorderPaint;
    private float mCornerExtension;
    private float mCornerLength;
    private float mCornerOffset;
    private Paint mCornerPaint;
    private boolean mFixAspectRatio = false;
    private Paint mGuidelinePaint;
    private int mGuidelines;
    private float mHandleRadius;
    private float mSnapRadius;
    private float mTargetAspectRatio = (((float) this.mAspectRatioX) / ((float) this.mAspectRatioY));
    private Pair<Float, Float> mTouchOffset;

    public CropOverlayView(Context context) {
        super(context);
        init(context);
    }

    public CropOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mBitmapRect.right = w - this.mBitmapRect.left;
        this.mBitmapRect.bottom = h - this.mBitmapRect.top;
        initCropWindow(this.mBitmapRect);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        a(canvas, this.mBitmapRect);
        if (Oh()) {
            if (this.mGuidelines == 2) {
                e(canvas);
            } else if (this.mGuidelines == 1) {
                if (this.bvU != null) {
                    e(canvas);
                }
            } else if (this.mGuidelines == 0) {
            }
        }
        canvas.drawRect(Edge.LEFT.getCoordinate(), Edge.TOP.getCoordinate(), Edge.RIGHT.getCoordinate(), Edge.BOTTOM.getCoordinate(), this.mBorderPaint);
        f(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                d(event.getX(), event.getY());
                return true;
            case 1:
            case 3:
                getParent().requestDisallowInterceptTouchEvent(false);
                onActionUp();
                return true;
            case 2:
                onActionMove(event.getX(), event.getY());
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            default:
                return false;
        }
    }

    public void setBitmapRect(Rect bitmapRect) {
        this.mBitmapRect = bitmapRect;
        initCropWindow(this.mBitmapRect);
    }

    public void resetCropOverlayView() {
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setGuidelines(int guidelines) {
        if (guidelines < 0 || guidelines > 2) {
            throw new IllegalArgumentException("Guideline value must be set between 0 and 2. See documentation.");
        }
        this.mGuidelines = guidelines;
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setFixedAspectRatio(boolean fixAspectRatio) {
        this.mFixAspectRatio = fixAspectRatio;
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setAspectRatioX(int aspectRatioX) {
        if (aspectRatioX <= 0) {
            throw new IllegalArgumentException("Cannot set aspect ratio value to a_isRightVersion number less than or equal to 0.");
        }
        this.mAspectRatioX = aspectRatioX;
        this.mTargetAspectRatio = ((float) this.mAspectRatioX) / ((float) this.mAspectRatioY);
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setAspectRatioY(int aspectRatioY) {
        if (aspectRatioY <= 0) {
            throw new IllegalArgumentException("Cannot set aspect ratio value to a_isRightVersion number less than or equal to 0.");
        }
        this.mAspectRatioY = aspectRatioY;
        this.mTargetAspectRatio = ((float) this.mAspectRatioX) / ((float) this.mAspectRatioY);
        if (this.initializedCropWindow) {
            initCropWindow(this.mBitmapRect);
            invalidate();
        }
    }

    public void setInitialAttributeValues(int guidelines, boolean fixAspectRatio, int aspectRatioX, int aspectRatioY) {
        if (guidelines < 0 || guidelines > 2) {
            throw new IllegalArgumentException("Guideline value must be set between 0 and 2. See documentation.");
        }
        this.mGuidelines = guidelines;
        this.mFixAspectRatio = fixAspectRatio;
        if (aspectRatioX <= 0) {
            throw new IllegalArgumentException("Cannot set aspect ratio value to a_isRightVersion number less than or equal to 0.");
        }
        this.mAspectRatioX = aspectRatioX;
        this.mTargetAspectRatio = ((float) this.mAspectRatioX) / ((float) this.mAspectRatioY);
        if (aspectRatioY <= 0) {
            throw new IllegalArgumentException("Cannot set aspect ratio value to a_isRightVersion number less than or equal to 0.");
        }
        this.mAspectRatioY = aspectRatioY;
        this.mTargetAspectRatio = ((float) this.mAspectRatioX) / ((float) this.mAspectRatioY);
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mHandleRadius = b.getTargetRadius(context);
        this.mSnapRadius = TypedValue.applyDimension(1, 6.0f, displayMetrics);
        this.mBorderPaint = d.newBorderPaint(context);
        this.mGuidelinePaint = d.newGuidelinePaint();
        this.mBackgroundPaint = d.newBackgroundPaint(context);
        this.mCornerPaint = d.newCornerPaint(context);
        this.mCornerOffset = TypedValue.applyDimension(1, DEFAULT_CORNER_OFFSET_DP, displayMetrics);
        this.mCornerExtension = TypedValue.applyDimension(1, DEFAULT_CORNER_EXTENSION_DP, displayMetrics);
        this.mCornerLength = TypedValue.applyDimension(1, DEFAULT_CORNER_LENGTH_DP, displayMetrics);
        this.mGuidelines = 1;
    }

    private void initCropWindow(Rect bitmapRect) {
        if (!this.initializedCropWindow) {
            this.initializedCropWindow = true;
        }
        if (!this.mFixAspectRatio) {
            float horizontalPadding = 0.1f * ((float) bitmapRect.width());
            float verticalPadding = 0.1f * ((float) bitmapRect.height());
            Edge.LEFT.setCoordinate(((float) bitmapRect.left) + horizontalPadding);
            Edge.TOP.setCoordinate(((float) bitmapRect.top) + verticalPadding);
            Edge.RIGHT.setCoordinate(((float) bitmapRect.right) - horizontalPadding);
            Edge.BOTTOM.setCoordinate(((float) bitmapRect.bottom) - verticalPadding);
        } else if (a.calculateAspectRatio(bitmapRect) > this.mTargetAspectRatio) {
            Edge.TOP.setCoordinate((float) bitmapRect.top);
            Edge.BOTTOM.setCoordinate((float) bitmapRect.bottom);
            float centerX = ((float) getWidth()) / 2.0f;
            float cropWidth = Math.max(40.0f, a.calculateWidth(Edge.TOP.getCoordinate(), Edge.BOTTOM.getCoordinate(), this.mTargetAspectRatio));
            if (cropWidth == 40.0f) {
                this.mTargetAspectRatio = 40.0f / (Edge.BOTTOM.getCoordinate() - Edge.TOP.getCoordinate());
            }
            float halfCropWidth = cropWidth / 2.0f;
            Edge.LEFT.setCoordinate(centerX - halfCropWidth);
            Edge.RIGHT.setCoordinate(centerX + halfCropWidth);
        } else {
            Edge.LEFT.setCoordinate((float) bitmapRect.left);
            Edge.RIGHT.setCoordinate((float) bitmapRect.right);
            float centerY = ((float) getHeight()) / 2.0f;
            float cropHeight = Math.max(40.0f, a.calculateHeight(Edge.LEFT.getCoordinate(), Edge.RIGHT.getCoordinate(), this.mTargetAspectRatio));
            if (cropHeight == 40.0f) {
                this.mTargetAspectRatio = (Edge.RIGHT.getCoordinate() - Edge.LEFT.getCoordinate()) / 40.0f;
            }
            float halfCropHeight = cropHeight / 2.0f;
            Edge.TOP.setCoordinate(centerY - halfCropHeight);
            Edge.BOTTOM.setCoordinate(centerY + halfCropHeight);
        }
    }

    public static boolean Oh() {
        if (Math.abs(Edge.LEFT.getCoordinate() - Edge.RIGHT.getCoordinate()) < DEFAULT_SHOW_GUIDELINES_LIMIT || Math.abs(Edge.TOP.getCoordinate() - Edge.BOTTOM.getCoordinate()) < DEFAULT_SHOW_GUIDELINES_LIMIT) {
            return false;
        }
        return true;
    }

    private void e(Canvas canvas) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        float oneThirdCropWidth = Edge.getWidth() / 3.0f;
        float x1 = left + oneThirdCropWidth;
        canvas.drawLine(x1, top, x1, bottom, this.mGuidelinePaint);
        float x2 = right - oneThirdCropWidth;
        canvas.drawLine(x2, top, x2, bottom, this.mGuidelinePaint);
        float oneThirdCropHeight = Edge.getHeight() / 3.0f;
        float y1 = top + oneThirdCropHeight;
        canvas.drawLine(left, y1, right, y1, this.mGuidelinePaint);
        float y2 = bottom - oneThirdCropHeight;
        canvas.drawLine(left, y2, right, y2, this.mGuidelinePaint);
    }

    private void a(Canvas canvas, Rect bitmapRect) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        canvas.drawRect((float) bitmapRect.left, (float) bitmapRect.top, (float) bitmapRect.right, top, this.mBackgroundPaint);
        canvas.drawRect((float) bitmapRect.left, bottom, (float) bitmapRect.right, (float) bitmapRect.bottom, this.mBackgroundPaint);
        canvas.drawRect((float) bitmapRect.left, top, left, bottom, this.mBackgroundPaint);
        canvas.drawRect(right, top, (float) bitmapRect.right, bottom, this.mBackgroundPaint);
    }

    private void f(Canvas canvas) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        canvas.drawLine(left - this.mCornerOffset, top - this.mCornerExtension, left - this.mCornerOffset, top + this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(left, top - this.mCornerOffset, left + this.mCornerLength, top - this.mCornerOffset, this.mCornerPaint);
        canvas.drawLine(right + this.mCornerOffset, top - this.mCornerExtension, right + this.mCornerOffset, top + this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(right, top - this.mCornerOffset, right - this.mCornerLength, top - this.mCornerOffset, this.mCornerPaint);
        canvas.drawLine(left - this.mCornerOffset, bottom + this.mCornerExtension, left - this.mCornerOffset, bottom - this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(left, bottom + this.mCornerOffset, left + this.mCornerLength, bottom + this.mCornerOffset, this.mCornerPaint);
        canvas.drawLine(right + this.mCornerOffset, bottom + this.mCornerExtension, right + this.mCornerOffset, bottom - this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(right, bottom + this.mCornerOffset, right - this.mCornerLength, bottom + this.mCornerOffset, this.mCornerPaint);
    }

    private void d(float x, float y) {
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        this.bvU = b.a(x, y, left, top, right, bottom, this.mHandleRadius);
        if (this.bvU != null) {
            this.mTouchOffset = b.a(this.bvU, x, y, left, top, right, bottom);
            invalidate();
        }
    }

    private void onActionUp() {
        if (this.bvU != null) {
            this.bvU = null;
            invalidate();
        }
    }

    private void onActionMove(float x, float y) {
        if (this.bvU != null) {
            x += ((Float) this.mTouchOffset.first).floatValue();
            y += ((Float) this.mTouchOffset.second).floatValue();
            if (this.mFixAspectRatio) {
                this.bvU.updateCropWindow(x, y, this.mTargetAspectRatio, this.mBitmapRect, this.mSnapRadius);
            } else {
                this.bvU.updateCropWindow(x, y, this.mBitmapRect, this.mSnapRadius);
            }
            invalidate();
        }
    }
}
