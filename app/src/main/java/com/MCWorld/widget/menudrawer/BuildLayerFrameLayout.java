package com.MCWorld.widget.menudrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

class BuildLayerFrameLayout extends FrameLayout {
    private boolean bAf;
    private boolean bAg = true;
    private boolean biK = true;
    private boolean mAttached;

    public BuildLayerFrameLayout(Context context) {
        super(context);
        if (MenuDrawer.bAU) {
            setLayerType(2, null);
        }
    }

    public BuildLayerFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (MenuDrawer.bAU) {
            setLayerType(2, null);
        }
    }

    public BuildLayerFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (MenuDrawer.bAU) {
            setLayerType(2, null);
        }
    }

    void dj(boolean enabled) {
        this.bAg = enabled;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttached = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (MenuDrawer.bAU && this.bAg) {
            post(new Runnable(this) {
                final /* synthetic */ BuildLayerFrameLayout bAh;

                {
                    this.bAh = this$0;
                }

                public void run() {
                    this.bAh.bAf = true;
                    this.bAh.invalidate();
                }
            });
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.bAf && MenuDrawer.bAU) {
            post(new Runnable(this) {
                final /* synthetic */ BuildLayerFrameLayout bAh;

                {
                    this.bAh = this$0;
                }

                public void run() {
                    if (!this.bAh.mAttached) {
                        return;
                    }
                    if (this.bAh.getLayerType() != 2 || this.bAh.biK) {
                        this.bAh.biK = false;
                        this.bAh.setLayerType(2, null);
                        this.bAh.buildLayer();
                        this.bAh.setLayerType(0, null);
                    }
                }
            });
            this.bAf = false;
        }
    }
}
