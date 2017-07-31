package com.huluxia.widget.pulltorefresh;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.huluxia.bbs.b.f;
import java.util.Locale;

public abstract class BaseHeaderLayout extends LinearLayout implements a {
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    static final int ROTATION_ANIMATION_DURATION = 1200;
    private int bEe = 0;
    private ImageView mHeaderImage = null;
    protected final Matrix mHeaderImageMatrix = new Matrix();
    protected final Animation mRotateAnimation = new RotateAnimation(720.0f, 0.0f, 1, 0.5f, 1, 0.5f);
    private float mRotationPivotX;
    private float mRotationPivotY;
    private int scrollY = 0;

    public BaseHeaderLayout(Context context) {
        super(context);
        this.mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mRotateAnimation.setDuration(1200);
        this.mRotateAnimation.setRepeatCount(-1);
        this.mRotateAnimation.setRepeatMode(1);
    }

    protected void setContentHeight(int height) {
        this.bEe = height;
    }

    protected void w(View child) {
        int childHeightSpec;
        LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public int getContentHeight() {
        return this.bEe;
    }

    public void setScroll(int value) {
        this.scrollY = value;
        if (this.mHeaderImage != null) {
            this.mHeaderImageMatrix.setRotate((Math.abs((float) value) / ((float) getContentHeight())) * 360.0f, this.mRotationPivotX, this.mRotationPivotY);
            this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
            Log.i("SpaceHeaderLayout::setScroll", String.format(Locale.getDefault(), "%f %f", new Object[]{Float.valueOf(angle), Float.valueOf(val)}));
        }
    }

    public int getScroll() {
        return this.scrollY;
    }

    public void refreshingImpl() {
        if (this.mHeaderImage != null) {
            this.mHeaderImage.startAnimation(this.mRotateAnimation);
        }
    }

    public void resetImpl() {
        if (this.mHeaderImage != null) {
            this.mHeaderImage.clearAnimation();
            this.mHeaderImageMatrix.reset();
            this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
        }
    }

    public void setRotateImageView(ImageView iv) {
        this.mHeaderImage = iv;
        this.mHeaderImage.setScaleType(ScaleType.MATRIX);
        this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
        Drawable imageDrawable = this.mHeaderImage.getContext().getResources().getDrawable(f.loading_rotate);
        this.mHeaderImage.setImageDrawable(imageDrawable);
        this.mRotationPivotX = (float) Math.round(((float) imageDrawable.getIntrinsicWidth()) / 2.0f);
        this.mRotationPivotY = (float) Math.round(((float) imageDrawable.getIntrinsicHeight()) / 2.0f);
    }
}
