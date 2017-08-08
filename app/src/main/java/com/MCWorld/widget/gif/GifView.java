package com.MCWorld.widget.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GifView extends View {
    private String bzH;
    private b bzI;
    private Bitmap bzJ;
    private int bzK;
    private int bzL;
    private String bzM;
    private int bzN;
    private boolean pause;
    private Rect rect;

    public GifView(Context context) {
        super(context);
        this.bzH = "";
        this.bzI = null;
        this.bzJ = null;
        this.bzK = -1;
        this.bzL = -1;
        this.rect = null;
        this.bzM = "";
        this.pause = false;
        this.bzN = 0;
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bzH = "";
        this.bzI = null;
        this.bzJ = null;
        this.bzK = -1;
        this.bzL = -1;
        this.rect = null;
        this.bzM = "";
        this.pause = false;
        this.bzN = 0;
    }

    public String getExtendTag() {
        return this.bzH;
    }

    public void setExtendTag(String extendTag) {
        this.bzH = extendTag;
    }

    public void gQ(String url) {
        if (!this.bzM.equals(url)) {
            setGifDecoder(null);
            invalidate();
            d.OS().a(url, this);
        }
        this.bzM = url;
    }

    public void setGifDecoder(b gifDecoder) {
        this.pause = true;
        this.bzN = 0;
        this.bzI = gifDecoder;
        this.pause = false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.bzI != null) {
            if (this.bzJ == null) {
                this.bzJ = this.bzI.OA();
            }
            if (this.bzJ != null) {
                int saveCount = canvas.getSaveCount();
                canvas.save();
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
                if (this.bzK == -1) {
                    canvas.drawBitmap(this.bzJ, 0.0f, 0.0f, null);
                } else {
                    canvas.drawBitmap(this.bzJ, null, this.rect, null);
                }
                canvas.restoreToCount(saveCount);
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w;
        int h;
        int pleft = getPaddingLeft();
        int pright = getPaddingRight();
        int ptop = getPaddingTop();
        int pbottom = getPaddingBottom();
        if (this.bzI == null) {
            w = 1;
            h = 1;
        } else {
            w = this.bzI.width;
            h = this.bzI.height;
        }
        h += ptop + pbottom;
        setMeasuredDimension(resolveSize(Math.max(w + (pleft + pright), getSuggestedMinimumWidth()), widthMeasureSpec), resolveSize(Math.max(h, getSuggestedMinimumHeight()), heightMeasureSpec));
    }

    public void setAutoSize(boolean bAuto) {
        if (bAuto) {
            int pleft = getPaddingLeft();
            int pright = getPaddingRight();
            int ptop = getPaddingTop();
            int pbottom = getPaddingBottom();
            this.bzK = 170;
            this.bzL = 170;
            this.rect = new Rect();
            this.rect.left = 0;
            this.rect.top = 0;
            this.rect.right = this.bzK;
            this.rect.bottom = this.bzL;
            getLayoutParams().height = (this.bzL + ptop) + pbottom;
            getLayoutParams().width = (this.bzK + pleft) + pright;
            return;
        }
        this.bzK = -1;
        this.bzL = -1;
        this.rect = null;
    }

    public void aM(int width, int height) {
        if (width > 0 && height > 0) {
            this.bzK = width;
            this.bzL = height;
            this.rect = new Rect();
            this.rect.left = 0;
            this.rect.top = 0;
            this.rect.right = width;
            this.rect.bottom = height;
        }
    }

    public void OW() {
        if (!this.pause && this.bzI != null) {
            this.bzN %= this.bzI.getFrameCount();
            b bVar = this.bzI;
            int i = this.bzN;
            this.bzN = i + 1;
            c frame = bVar.mb(i);
            if (frame != null) {
                this.bzJ = frame.bzl;
                invalidate();
            }
        }
    }
}
