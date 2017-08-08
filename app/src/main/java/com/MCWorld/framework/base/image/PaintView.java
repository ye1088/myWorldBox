package com.MCWorld.framework.base.image;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.j;
import android.support.annotation.l;
import android.util.AttributeSet;

import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.image.Config.NetFormat;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.image.core.common.util.f;
import com.MCWorld.image.drawee.controller.AbstractDraweeControllerBuilder;
import com.MCWorld.image.drawee.controller.c;
import com.MCWorld.image.drawee.drawable.o;
import com.MCWorld.image.drawee.generic.RoundingParams;
import com.MCWorld.image.drawee.generic.RoundingParams.RoundingMethod;
import com.MCWorld.image.drawee.generic.a;
import com.MCWorld.image.drawee.generic.b;
import com.MCWorld.image.drawee.view.SimpleDraweeView;
import com.MCWorld.image.pipeline.request.ImageRequestBuilder;
import javax.annotation.Nullable;

public class PaintView extends SimpleDraweeView {
    private static final String TAG = "NetworkImageView";
    private final Config mConfig = new Config();
    private c mControllerListener;
    private boolean mInitialised = false;
    private Drawable mSetDrawable;
    private Uri mUri;

    public PaintView(Context context) {
        super(context);
        setHierarchy(getHierarchyBuilder(context, null).kQ());
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        scaleType(getScaleType());
        showSetDrawable();
        placeHolder(this.mConfig.placeHolder, true);
        errorHolder(this.mConfig.errorHolder, true);
        this.mInitialised = true;
    }

    protected void inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        setHierarchy(getHierarchyBuilder(context, attrs).kQ());
    }

    private b getHierarchyBuilder(Context context, @Nullable AttributeSet attrs) {
        b builder = com.MCWorld.image.drawee.generic.c.b(context, attrs);
        setAspectRatio(builder.kD());
        return builder;
    }

    @Deprecated
    public void setImageUrl(String url, ImageLoader imageLoader) {
        setUri(UtilUri.getUriOrNull(url));
        setImageLoader(imageLoader);
    }

    public PaintView setUri(Uri uri) {
        return setUri(uri, null);
    }

    public PaintView setUri(Uri uri, NetFormat format) {
        if (!(format == null || uri == null)) {
            if (format == NetFormat.FORMAT_80) {
                uri = Uri.parse(String.format("%s_80x80.jpeg", new Object[]{uri.toString()}));
            } else if (format == NetFormat.FORMAT_160) {
                uri = Uri.parse(String.format("%s_160x160.jpeg", new Object[]{uri.toString()}));
            }
        }
        this.mUri = uri;
        return this;
    }

    public void setImageResource(int resId) {
        this.mSetDrawable = getResources().getDrawable(resId);
        if (this.mInitialised) {
            showSetDrawable();
        } else {
            super.setImageResource(resId);
        }
    }

    public void setImageBitmap(Bitmap bm) {
        this.mSetDrawable = new BitmapDrawable(bm);
        if (this.mInitialised) {
            showSetDrawable();
        } else {
            super.setImageBitmap(bm);
        }
    }

    public void setImageDrawable(Drawable drawable) {
        this.mSetDrawable = drawable;
        if (this.mInitialised) {
            showSetDrawable();
        } else {
            super.setImageDrawable(drawable);
        }
    }

    private void showSetDrawable() {
        if (getHierarchy() != null && this.mSetDrawable != null) {
            ((a) getHierarchy()).a(this.mSetDrawable, 0.0f, true);
        }
    }

    public void setScaleType(ScaleType scaleType) {
        if (!this.mInitialised) {
            super.setScaleType(scaleType);
        } else if (getHierarchy() != null) {
            super.setScaleType(ScaleType.FIT_CENTER);
            scaleType(scaleType);
        }
    }

    public Uri getUri() {
        return this.mUri;
    }

    public ImageLoader getImageLoader() {
        return null;
    }

    public void setDefaultImageResId(int placeHolder) {
        placeHolder(placeHolder);
    }

    public PaintView controllerListener(com.MCWorld.image.drawee.controller.b controllerListener) {
        this.mControllerListener = controllerListener;
        return this;
    }

    public PaintView resize(int width, int height) {
        this.mConfig.targetWidth = width;
        this.mConfig.targetHeight = height;
        return this;
    }

    public PaintView resizeDp(int wDp, int hDP) {
        this.mConfig.targetWidth = UtilsScreen.dipToPx(AppConfig.getInstance().getAppContext(), wDp);
        this.mConfig.targetHeight = UtilsScreen.dipToPx(AppConfig.getInstance().getAppContext(), hDP);
        return this;
    }

    public PaintView resizeDimen(@l int dW, @l int dH) {
        this.mConfig.targetWidth = AppConfig.getInstance().getAppContext().getResources().getDimensionPixelSize(dW);
        this.mConfig.targetHeight = AppConfig.getInstance().getAppContext().getResources().getDimensionPixelSize(dH);
        return this;
    }

    public PaintView scaleType(ScaleType type) {
        if (this.mConfig.scaleType != type) {
            this.mConfig.scaleType = type;
            ((a) getHierarchy()).b(o.a(this.mConfig.scaleType));
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        return this;
    }

    public PaintView placeHolder(int placeHolder) {
        return placeHolder(placeHolder, false);
    }

    public PaintView placeHolder(int placeHolder, boolean force) {
        if (placeHolder > 0 && (this.mConfig.placeHolder != placeHolder || force)) {
            this.mConfig.placeHolder = placeHolder;
            ((a) getHierarchy()).a(this.mConfig.placeHolder, o.a(getScaleType()));
        }
        return this;
    }

    public PaintView errorHolder(int errHolder) {
        return errorHolder(errHolder, false);
    }

    public PaintView errorHolder(int errHolder, boolean force) {
        if (errHolder > 0 && (this.mConfig.errorHolder != errHolder || force)) {
            this.mConfig.errorHolder = errHolder;
            ((a) getHierarchy()).b(this.mConfig.errorHolder, o.a(getScaleType()));
        }
        return this;
    }

    public PaintView progressHolder() {
        return this;
    }

    public PaintView oval() {
        RoundingParams roundingParams = ((a) getHierarchy()).kB();
        if (roundingParams == null) {
            roundingParams = RoundingParams.kV().a(RoundingMethod.BITMAP_ONLY);
            ((a) getHierarchy()).a(roundingParams);
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        roundingParams.ad(true);
        return this;
    }

    public PaintView topLeft(float radius) {
        RoundingParams roundingParams = ((a) getHierarchy()).kB();
        if (roundingParams == null) {
            roundingParams = RoundingParams.b(0.0f, 0.0f, 0.0f, 0.0f).a(RoundingMethod.BITMAP_ONLY);
            ((a) getHierarchy()).a(roundingParams);
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        roundingParams.ad(false);
        roundingParams.a(radius, roundingParams.kS()[2], roundingParams.kS()[4], roundingParams.kS()[6]);
        return this;
    }

    public PaintView topRight(float radius) {
        RoundingParams roundingParams = ((a) getHierarchy()).kB();
        if (roundingParams == null) {
            roundingParams = RoundingParams.b(0.0f, 0.0f, 0.0f, 0.0f).a(RoundingMethod.BITMAP_ONLY);
            ((a) getHierarchy()).a(roundingParams);
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        roundingParams.ad(false);
        roundingParams.a(roundingParams.kS()[0], radius, roundingParams.kS()[4], roundingParams.kS()[6]);
        return this;
    }

    public PaintView bottomLeft(float radius) {
        RoundingParams roundingParams = ((a) getHierarchy()).kB();
        if (roundingParams == null) {
            roundingParams = RoundingParams.b(0.0f, 0.0f, 0.0f, 0.0f).a(RoundingMethod.BITMAP_ONLY);
            ((a) getHierarchy()).a(roundingParams);
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        roundingParams.ad(false);
        roundingParams.a(roundingParams.kS()[0], roundingParams.kS()[2], radius, roundingParams.kS()[6]);
        return this;
    }

    public PaintView bottomRight(float radius) {
        RoundingParams roundingParams = ((a) getHierarchy()).kB();
        if (roundingParams == null) {
            roundingParams = RoundingParams.b(0.0f, 0.0f, 0.0f, 0.0f).a(RoundingMethod.BITMAP_ONLY);
            ((a) getHierarchy()).a(roundingParams);
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        roundingParams.ad(false);
        roundingParams.a(roundingParams.kS()[0], roundingParams.kS()[2], roundingParams.kS()[6], radius);
        return this;
    }

    public PaintView radius(float radius) {
        RoundingParams roundingParams = ((a) getHierarchy()).kB();
        if (roundingParams == null) {
            roundingParams = RoundingParams.b(0.0f, 0.0f, 0.0f, 0.0f).a(RoundingMethod.BITMAP_ONLY);
            ((a) getHierarchy()).a(roundingParams);
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        roundingParams.ad(false);
        roundingParams.a(radius, radius, radius, radius);
        return this;
    }

    public PaintView radiusDimen(@l int dimen) {
        try {
            return radius(getResources().getDimension(dimen));
        } catch (NotFoundException e) {
            return this;
        }
    }

    public PaintView borderColor(@j int color, float width) {
        RoundingParams roundingParams = ((a) getHierarchy()).kB();
        if (roundingParams == null) {
            roundingParams = RoundingParams.b(0.0f, 0.0f, 0.0f, 0.0f).a(RoundingMethod.BITMAP_ONLY);
            ((a) getHierarchy()).a(roundingParams);
            placeHolder(this.mConfig.placeHolder, true);
            errorHolder(this.mConfig.errorHolder, true);
        }
        roundingParams.b(color, width);
        return this;
    }

    public PaintView fadeDuration(int fadeDuration) {
        if (this.mConfig.fadeDuration != fadeDuration) {
            this.mConfig.fadeDuration = fadeDuration;
            ((a) getHierarchy()).bW(fadeDuration);
        }
        return this;
    }

    public PaintView smoothSwitch(boolean smoothSwitch) {
        return this;
    }

    public PaintView tag(Object tag) {
        return this;
    }

    public PaintView brightness(int brightness) {
        if (brightness == 0) {
            ((a) getHierarchy()).a(null);
        } else {
            ColorMatrix cMatrix = new ColorMatrix();
            cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
            ((a) getHierarchy()).a(new ColorMatrixColorFilter(cMatrix));
        }
        return this;
    }

    public PaintView autoAnimated() {
        this.mConfig.autoAnimated = true;
        return this;
    }

    public PaintView hightQualityAnimated() {
        this.mConfig.highQualityAnimated = true;
        return this;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        if (this.mUri == null) {
            HLog.error(TAG, "not set uri", new Object[0]);
            return;
        }
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.w(this.mUri);
        if (f.isNetworkUri(this.mUri)) {
            imageRequestBuilder.au(true);
        }
        if (shouldResize()) {
            imageRequestBuilder.c(new com.MCWorld.image.base.imagepipeline.common.c(getResizeWidth(), getResizeHeight()));
        }
        imageRequestBuilder.au(true);
        imageRequestBuilder.b(com.MCWorld.image.base.imagepipeline.common.a.hs().O(this.mConfig.highQualityAnimated).hz());
        setController(((AbstractDraweeControllerBuilder) getControllerBuilder()).D(imageRequestBuilder.pM()).X(this.mConfig.autoAnimated).C(null).c(this.mControllerListener).a(getController()).jB());
    }

    private int getResizeWidth() {
        int width = getWidth();
        if (width != 0) {
            return width;
        }
        if (getLayoutParams() == null || getLayoutParams().width <= 0) {
            return this.mConfig.targetWidth;
        }
        return getLayoutParams().width;
    }

    private int getResizeHeight() {
        int height = getHeight();
        if (height != 0) {
            return height;
        }
        if (getLayoutParams() == null || getLayoutParams().height <= 0) {
            return this.mConfig.targetHeight;
        }
        return getLayoutParams().height;
    }

    private boolean shouldResize() {
        if (getWidth() > 0 && getHeight() > 0) {
            return true;
        }
        if (getLayoutParams() != null && getLayoutParams().width > 0 && getLayoutParams().height > 0) {
            return true;
        }
        if (this.mConfig.targetHeight <= 0 || this.mConfig.targetWidth <= 0) {
            return false;
        }
        return true;
    }
}
