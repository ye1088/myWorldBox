package com.huluxia.widget.cropimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.huluxia.mcfloat.InstanceZones.e;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ae;
import com.huluxia.widget.cropimage.cropwindow.CropOverlayView;
import com.huluxia.widget.cropimage.cropwindow.edge.Edge;
import com.huluxia.widget.cropimage.util.c;

public class CropImageView extends FrameLayout {
    public static final int DEFAULT_ASPECT_RATIO_X = 1;
    public static final int DEFAULT_ASPECT_RATIO_Y = 1;
    public static final boolean DEFAULT_FIXED_ASPECT_RATIO = false;
    public static final int DEFAULT_GUIDELINES = 1;
    private static final int DEFAULT_IMAGE_RESOURCE = 0;
    private static final String DEGREES_ROTATED = "DEGREES_ROTATED";
    private static final Rect EMPTY_RECT = new Rect();
    private CropOverlayView bvT;
    private int mAspectRatioX;
    private int mAspectRatioY;
    private Bitmap mBitmap;
    private int mDegreesRotated;
    private boolean mFixAspectRatio;
    private int mGuidelines;
    private int mImageResource;
    private int mLayoutHeight;
    private int mLayoutWidth;

    public CropImageView(Context context) {
        super(context);
        this.mDegreesRotated = 0;
        this.mGuidelines = 1;
        this.mFixAspectRatio = false;
        this.mAspectRatioX = 1;
        this.mAspectRatioY = 1;
        this.mImageResource = 0;
        init(context);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDegreesRotated = 0;
        this.mGuidelines = 1;
        this.mFixAspectRatio = false;
        this.mAspectRatioX = 1;
        this.mAspectRatioY = 1;
        this.mImageResource = 0;
        this.mGuidelines = 1;
        this.mFixAspectRatio = false;
        this.mAspectRatioX = 1;
        this.mAspectRatioY = 1;
        this.mImageResource = 0;
        init(context);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt(DEGREES_ROTATED, this.mDegreesRotated);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mDegreesRotated = bundle.getInt(DEGREES_ROTATED);
            int tempDegrees = this.mDegreesRotated;
            rotateImage(this.mDegreesRotated);
            this.mDegreesRotated = tempDegrees;
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (this.mBitmap != null) {
            int desiredWidth;
            int desiredHeight;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (heightSize == 0) {
                heightSize = this.mBitmap.getHeight();
            }
            double viewToBitmapWidthRatio = Double.POSITIVE_INFINITY;
            double viewToBitmapHeightRatio = Double.POSITIVE_INFINITY;
            if (widthSize < this.mBitmap.getWidth()) {
                viewToBitmapWidthRatio = ((double) widthSize) / ((double) this.mBitmap.getWidth());
            }
            if (heightSize < this.mBitmap.getHeight()) {
                viewToBitmapHeightRatio = ((double) heightSize) / ((double) this.mBitmap.getHeight());
            }
            if (viewToBitmapWidthRatio == Double.POSITIVE_INFINITY && viewToBitmapHeightRatio == Double.POSITIVE_INFINITY) {
                desiredWidth = this.mBitmap.getWidth();
                desiredHeight = this.mBitmap.getHeight();
            } else if (viewToBitmapWidthRatio <= viewToBitmapHeightRatio) {
                desiredWidth = widthSize;
                desiredHeight = (int) (((double) this.mBitmap.getHeight()) * viewToBitmapWidthRatio);
            } else {
                desiredHeight = heightSize;
                desiredWidth = (int) (((double) this.mBitmap.getWidth()) * viewToBitmapHeightRatio);
            }
            int width = getOnMeasureSpec(widthMode, widthSize, desiredWidth);
            int height = getOnMeasureSpec(heightMode, heightSize, desiredHeight);
            this.mLayoutWidth = width;
            this.mLayoutHeight = height;
            this.bvT.setBitmapRect(c.getBitmapRectCenterInside(this.mBitmap.getWidth(), this.mBitmap.getHeight(), this.mLayoutWidth, this.mLayoutHeight));
            setMeasuredDimension(this.mLayoutWidth, this.mLayoutHeight);
            return;
        }
        this.bvT.setBitmapRect(EMPTY_RECT);
        setMeasuredDimension(widthSize, heightSize);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mLayoutWidth > 0 && this.mLayoutHeight > 0) {
            LayoutParams origparams = getLayoutParams();
            origparams.width = this.mLayoutWidth;
            origparams.height = this.mLayoutHeight;
            setLayoutParams(origparams);
        }
    }

    public int getImageResource() {
        return this.mImageResource;
    }

    public int getRealBitmapWidth() {
        return this.mBitmap == null ? 0 : this.mBitmap.getWidth();
    }

    public int getRealBitmapHeight() {
        return this.mBitmap == null ? 0 : this.mBitmap.getHeight();
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.bvT.setImageBitmap(this.mBitmap);
        if (this.bvT != null) {
            this.bvT.resetCropOverlayView();
        }
    }

    public void setImageBitmap(Bitmap bitmap, ExifInterface exif) {
        if (bitmap != null) {
            if (exif == null) {
                setImageBitmap(bitmap);
                return;
            }
            Matrix matrix = new Matrix();
            int rotate = -1;
            switch (exif.getAttributeInt("Orientation", 1)) {
                case 3:
                    rotate = 180;
                    break;
                case 6:
                    rotate = 90;
                    break;
                case 8:
                    rotate = 270;
                    break;
            }
            if (rotate == -1) {
                setImageBitmap(bitmap);
                return;
            }
            matrix.postRotate((float) rotate);
            setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
            bitmap.recycle();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setImageBitmap(android.graphics.Bitmap r9, int r10) {
        /*
        r8 = this;
        r1 = 0;
        if (r9 != 0) goto L_0x0004;
    L_0x0003:
        return;
    L_0x0004:
        if (r10 != 0) goto L_0x000a;
    L_0x0006:
        r8.setImageBitmap(r9);
        goto L_0x0003;
    L_0x000a:
        switch(r10) {
            case 1: goto L_0x002c;
            case 2: goto L_0x002e;
            case 3: goto L_0x0030;
            default: goto L_0x000d;
        };
    L_0x000d:
        r5 = new android.graphics.Matrix;
        r5.<init>();
        r0 = (float) r10;
        r5.postRotate(r0);
        r3 = r9.getWidth();
        r4 = r9.getHeight();
        r6 = 1;
        r0 = r9;
        r2 = r1;
        r7 = android.graphics.Bitmap.createBitmap(r0, r1, r2, r3, r4, r5, r6);
        r8.setImageBitmap(r7);
        r9.recycle();
        goto L_0x0003;
    L_0x002c:
        r10 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
    L_0x002e:
        r10 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
    L_0x0030:
        r10 = 90;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.widget.cropimage.CropImageView.setImageBitmap(android.graphics.Bitmap, int):void");
    }

    public void setImageFile(String path) {
        try {
            setImageBitmap(ae.decodeByteArray(UtilsFile.getBytesFromSD(path)), new ExifInterface(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageResource(int resId) {
        if (resId != 0) {
            setImageBitmap(BitmapFactory.decodeResource(getResources(), resId));
        }
    }

    public Bitmap getCroppedImage() {
        if (this.mBitmap == null) {
            return null;
        }
        Rect displayedImageRect = c.getBitmapRectCenterInside(this.mBitmap, this.bvT);
        float scaleFactorWidth = ((float) this.mBitmap.getWidth()) / ((float) displayedImageRect.width());
        float scaleFactorHeight = ((float) this.mBitmap.getHeight()) / ((float) displayedImageRect.height());
        float cropWindowX = Edge.LEFT.getCoordinate() - ((float) displayedImageRect.left);
        float cropWindowY = Edge.TOP.getCoordinate() - ((float) displayedImageRect.top);
        int x = (int) (cropWindowX * scaleFactorWidth);
        int y = (int) (cropWindowY * scaleFactorHeight);
        int width = (int) (Edge.getWidth() * scaleFactorWidth);
        int height = (int) (Edge.getHeight() * scaleFactorHeight);
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x + width > this.mBitmap.getWidth()) {
            width = this.mBitmap.getWidth() - x;
        }
        if (y + height > this.mBitmap.getHeight()) {
            height = this.mBitmap.getHeight() - y;
        }
        return Bitmap.createBitmap(this.mBitmap, x, y, width, height);
    }

    public RectF getActualCropRect() {
        Rect displayedImageRect = c.getBitmapRectCenterInside(this.mBitmap, this.bvT);
        float scaleFactorWidth = ((float) this.mBitmap.getWidth()) / ((float) displayedImageRect.width());
        float scaleFactorHeight = ((float) this.mBitmap.getHeight()) / ((float) displayedImageRect.height());
        float displayedCropLeft = Edge.LEFT.getCoordinate() - ((float) displayedImageRect.left);
        float displayedCropTop = Edge.TOP.getCoordinate() - ((float) displayedImageRect.top);
        float actualCropLeft = displayedCropLeft * scaleFactorWidth;
        float actualCropTop = displayedCropTop * scaleFactorHeight;
        return new RectF(Math.max(0.0f, actualCropLeft), Math.max(0.0f, actualCropTop), Math.min((float) this.mBitmap.getWidth(), actualCropLeft + (Edge.getWidth() * scaleFactorWidth)), Math.min((float) this.mBitmap.getHeight(), actualCropTop + (Edge.getHeight() * scaleFactorHeight)));
    }

    public void setFixedAspectRatio(boolean fixAspectRatio) {
        this.bvT.setFixedAspectRatio(fixAspectRatio);
    }

    public void setGuidelines(int guidelines) {
        this.bvT.setGuidelines(guidelines);
    }

    public void setAspectRatio(int aspectRatioX, int aspectRatioY) {
        this.mAspectRatioX = aspectRatioX;
        this.bvT.setAspectRatioX(this.mAspectRatioX);
        this.mAspectRatioY = aspectRatioY;
        this.bvT.setAspectRatioY(this.mAspectRatioY);
    }

    public void rotateImage(int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) degrees);
        this.mBitmap = Bitmap.createBitmap(this.mBitmap, 0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight(), matrix, true);
        setImageBitmap(this.mBitmap);
        this.mDegreesRotated += degrees;
        this.mDegreesRotated %= e.Wx;
    }

    private void init(Context context) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        setImageResource(this.mImageResource);
        this.bvT = new CropOverlayView(context);
        this.bvT.setLayoutParams(params);
        addView(this.bvT);
        this.bvT.setInitialAttributeValues(this.mGuidelines, this.mFixAspectRatio, this.mAspectRatioX, this.mAspectRatioY);
    }

    private static int getOnMeasureSpec(int measureSpecMode, int measureSpecSize, int desiredSize) {
        if (measureSpecMode == 1073741824) {
            return measureSpecSize;
        }
        if (measureSpecMode == Integer.MIN_VALUE) {
            return Math.min(desiredSize, measureSpecSize);
        }
        return desiredSize;
    }
}
