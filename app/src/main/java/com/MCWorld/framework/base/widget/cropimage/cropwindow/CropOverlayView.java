package com.MCWorld.framework.base.widget.cropimage.cropwindow;

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
import com.MCWorld.framework.base.widget.cropimage.cropwindow.edge.MyEdge;
import com.MCWorld.framework.base.widget.cropimage.cropwindow.edge.MyEdges;
import com.MCWorld.framework.base.widget.cropimage.cropwindow.handle.HandleHelper;
import com.MCWorld.framework.base.widget.cropimage.util.AspectRatioUtil;
import com.MCWorld.framework.base.widget.cropimage.util.HandleUtil;
import com.MCWorld.framework.base.widget.cropimage.util.PaintUtil;

public class CropOverlayView extends ImageView {
    private static final float DEFAULT_CORNER_EXTENSION_DP = ((DEFAULT_CORNER_THICKNESS_DP / 2.0f) + DEFAULT_CORNER_OFFSET_DP);
    private static final float DEFAULT_CORNER_LENGTH_DP = 20.0f;
    private static final float DEFAULT_CORNER_OFFSET_DP = ((DEFAULT_CORNER_THICKNESS_DP / 2.0f) - (DEFAULT_LINE_THICKNESS_DP / 2.0f));
    private static final float DEFAULT_CORNER_THICKNESS_DP = PaintUtil.getCornerThickness();
    private static final float DEFAULT_LINE_THICKNESS_DP = PaintUtil.getLineThickness();
    private static final float DEFAULT_SHOW_GUIDELINES_LIMIT = 100.0f;
    private static final int GUIDELINES_OFF = 0;
    private static final int GUIDELINES_ON = 2;
    private static final int GUIDELINES_ON_TOUCH = 1;
    private static final int SNAP_RADIUS_DP = 6;
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
    private MyEdges mEdges = new MyEdges();
    private boolean mFixAspectRatio = false;
    private Paint mGuidelinePaint;
    private int mGuidelines;
    private float mHandleRadius;
    private HandleHelper mPressedHandleHelper;
    private float mSnapRadius;
    private float mTargetAspectRatio = (((float) this.mAspectRatioX) / ((float) this.mAspectRatioY));
    private Pair<Float, Float> mTouchOffset;
    private boolean mTouched = false;

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
        drawBackground(canvas, this.mBitmapRect, this.mEdges);
        if (showGuidelines(this.mEdges)) {
            if (this.mGuidelines == 2) {
                drawRuleOfThirdsGuidelines(canvas, this.mEdges);
            } else if (this.mGuidelines == 1) {
                if (this.mPressedHandleHelper != null) {
                    drawRuleOfThirdsGuidelines(canvas, this.mEdges);
                }
            } else if (this.mGuidelines == 0) {
            }
        }
        canvas.drawRect(this.mEdges.getLeft().getCoordinate(), this.mEdges.getTop().getCoordinate(), this.mEdges.getRight().getCoordinate(), this.mEdges.getBottom().getCoordinate(), this.mBorderPaint);
        drawCorners(canvas, this.mEdges);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                onActionDown(event.getX(), event.getY(), this.mEdges);
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

    public Rect getBitmapRect() {
        return this.mBitmapRect;
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
        this.mHandleRadius = HandleUtil.getTargetRadius(context);
        this.mSnapRadius = TypedValue.applyDimension(1, 6.0f, displayMetrics);
        this.mBorderPaint = PaintUtil.newBorderPaint(context);
        this.mGuidelinePaint = PaintUtil.newGuidelinePaint();
        this.mBackgroundPaint = PaintUtil.newBackgroundPaint(context);
        this.mCornerPaint = PaintUtil.newCornerPaint(context);
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
            this.mEdges.getLeft().setCoordinate(((float) bitmapRect.left) + horizontalPadding);
            this.mEdges.getTop().setCoordinate(((float) bitmapRect.top) + verticalPadding);
            this.mEdges.getRight().setCoordinate(((float) bitmapRect.right) - horizontalPadding);
            this.mEdges.getBottom().setCoordinate(((float) bitmapRect.bottom) - verticalPadding);
        } else if (AspectRatioUtil.calculateAspectRatio(bitmapRect) > this.mTargetAspectRatio) {
            this.mEdges.getTop().setCoordinate((float) bitmapRect.top);
            this.mEdges.getBottom().setCoordinate((float) bitmapRect.bottom);
            float centerX = ((float) getWidth()) / 2.0f;
            float cropWidth = Math.max((float) MyEdge.MIN_CROP_LENGTH_PX, AspectRatioUtil.calculateWidth(this.mEdges.getTop().getCoordinate(), this.mEdges.getBottom().getCoordinate(), this.mTargetAspectRatio));
            if (cropWidth == ((float) MyEdge.MIN_CROP_LENGTH_PX)) {
                this.mTargetAspectRatio = ((float) MyEdge.MIN_CROP_LENGTH_PX) / (this.mEdges.getBottom().getCoordinate() - this.mEdges.getTop().getCoordinate());
            }
            float halfCropWidth = cropWidth / 2.0f;
            this.mEdges.getLeft().setCoordinate(centerX - halfCropWidth);
            this.mEdges.getRight().setCoordinate(centerX + halfCropWidth);
        } else {
            this.mEdges.getLeft().setCoordinate((float) bitmapRect.left);
            this.mEdges.getRight().setCoordinate((float) bitmapRect.right);
            float centerY = ((float) getHeight()) / 2.0f;
            float cropHeight = Math.max((float) MyEdge.MIN_CROP_LENGTH_PX, AspectRatioUtil.calculateHeight(this.mEdges.getLeft().getCoordinate(), this.mEdges.getRight().getCoordinate(), this.mTargetAspectRatio));
            if (cropHeight == ((float) MyEdge.MIN_CROP_LENGTH_PX)) {
                this.mTargetAspectRatio = (this.mEdges.getRight().getCoordinate() - this.mEdges.getLeft().getCoordinate()) / ((float) MyEdge.MIN_CROP_LENGTH_PX);
            }
            float halfCropHeight = cropHeight / 2.0f;
            this.mEdges.getTop().setCoordinate(centerY - halfCropHeight);
            this.mEdges.getBottom().setCoordinate(centerY + halfCropHeight);
        }
    }

    public static boolean showGuidelines(MyEdges edges) {
        float left = edges.getLeft().getCoordinate();
        float top = edges.getTop().getCoordinate();
        float right = edges.getRight().getCoordinate();
        float bottom = edges.getBottom().getCoordinate();
        if (Math.abs(left - right) < DEFAULT_SHOW_GUIDELINES_LIMIT || Math.abs(top - bottom) < DEFAULT_SHOW_GUIDELINES_LIMIT) {
            return false;
        }
        return true;
    }

    private void drawRuleOfThirdsGuidelines(Canvas canvas, MyEdges edges) {
        float left = edges.getLeft().getCoordinate();
        float top = edges.getTop().getCoordinate();
        float right = edges.getRight().getCoordinate();
        float bottom = edges.getBottom().getCoordinate();
        float oneThirdCropWidth = edges.getLeft().getWidth() / 3.0f;
        float x1 = left + oneThirdCropWidth;
        canvas.drawLine(x1, top, x1, bottom, this.mGuidelinePaint);
        float x2 = right - oneThirdCropWidth;
        canvas.drawLine(x2, top, x2, bottom, this.mGuidelinePaint);
        float oneThirdCropHeight = edges.getLeft().getHeight() / 3.0f;
        float y1 = top + oneThirdCropHeight;
        canvas.drawLine(left, y1, right, y1, this.mGuidelinePaint);
        float y2 = bottom - oneThirdCropHeight;
        canvas.drawLine(left, y2, right, y2, this.mGuidelinePaint);
    }

    private void drawBackground(Canvas canvas, Rect bitmapRect, MyEdges edges) {
        float left = edges.getLeft().getCoordinate();
        float top = edges.getTop().getCoordinate();
        float right = edges.getRight().getCoordinate();
        float bottom = edges.getBottom().getCoordinate();
        canvas.drawRect((float) bitmapRect.left, (float) bitmapRect.top, (float) bitmapRect.right, top, this.mBackgroundPaint);
        canvas.drawRect((float) bitmapRect.left, bottom, (float) bitmapRect.right, (float) bitmapRect.bottom, this.mBackgroundPaint);
        canvas.drawRect((float) bitmapRect.left, top, left, bottom, this.mBackgroundPaint);
        canvas.drawRect(right, top, (float) bitmapRect.right, bottom, this.mBackgroundPaint);
    }

    private void drawCorners(Canvas canvas, MyEdges edges) {
        float left = edges.getLeft().getCoordinate();
        float top = edges.getTop().getCoordinate();
        float right = edges.getRight().getCoordinate();
        float bottom = edges.getBottom().getCoordinate();
        canvas.drawLine(left - this.mCornerOffset, top - this.mCornerExtension, left - this.mCornerOffset, top + this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(left, top - this.mCornerOffset, left + this.mCornerLength, top - this.mCornerOffset, this.mCornerPaint);
        canvas.drawLine(right + this.mCornerOffset, top - this.mCornerExtension, right + this.mCornerOffset, top + this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(right, top - this.mCornerOffset, right - this.mCornerLength, top - this.mCornerOffset, this.mCornerPaint);
        canvas.drawLine(left - this.mCornerOffset, bottom + this.mCornerExtension, left - this.mCornerOffset, bottom - this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(left, bottom + this.mCornerOffset, left + this.mCornerLength, bottom + this.mCornerOffset, this.mCornerPaint);
        canvas.drawLine(right + this.mCornerOffset, bottom + this.mCornerExtension, right + this.mCornerOffset, bottom - this.mCornerLength, this.mCornerPaint);
        canvas.drawLine(right, bottom + this.mCornerOffset, right - this.mCornerLength, bottom + this.mCornerOffset, this.mCornerPaint);
    }

    private void onActionDown(float x, float y, MyEdges edges) {
        float left = edges.getLeft().getCoordinate();
        float top = edges.getTop().getCoordinate();
        float right = edges.getRight().getCoordinate();
        float bottom = edges.getBottom().getCoordinate();
        this.mPressedHandleHelper = HandleUtil.getPressedHandleHelper(x, y, left, top, right, bottom, this.mHandleRadius, edges);
        if (this.mPressedHandleHelper != null) {
            this.mTouchOffset = HandleUtil.getOffset(this.mPressedHandleHelper, x, y, left, top, right, bottom);
            invalidate();
        }
    }

    private void onActionUp() {
        if (this.mPressedHandleHelper != null) {
            this.mPressedHandleHelper = null;
            invalidate();
        }
    }

    private void onActionMove(float x, float y) {
        if (this.mPressedHandleHelper != null) {
            x += ((Float) this.mTouchOffset.first).floatValue();
            y += ((Float) this.mTouchOffset.second).floatValue();
            if (this.mFixAspectRatio) {
                this.mPressedHandleHelper.updateCropWindow(x, y, this.mTargetAspectRatio, this.mBitmapRect, this.mSnapRadius);
            } else {
                this.mPressedHandleHelper.updateCropWindow(x, y, this.mBitmapRect, this.mSnapRadius);
            }
            invalidate();
            this.mTouched = true;
        }
    }

    public MyEdges getEdges() {
        return this.mEdges;
    }

    public boolean isTouched() {
        return this.mTouched;
    }
}
