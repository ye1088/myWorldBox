package com.huluxia.widget.picviewer.touchgallery.GalleryWidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.huluxia.widget.FixedTouchViewPager;
import com.huluxia.widget.picviewer.touchgallery.TouchView.TouchImageView;

public class GalleryViewPager extends FixedTouchViewPager {
    private static final int bDi = 5;
    PointF bDd;
    public TouchImageView bDe;
    protected a bDf;
    private float bDg;
    private float bDh;

    public GalleryViewPager(Context context) {
        super(context);
    }

    public GalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(5)
    private float[] e(MotionEvent event) {
        switch (event.getAction() & 255) {
            case 0:
                this.bDd = new PointF(event.getX(0), event.getY(0));
                break;
            case 1:
            case 2:
                PointF curr = new PointF(event.getX(0), event.getY(0));
                return new float[]{curr.x - this.bDd.x, curr.y - this.bDd.y};
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if ((event.getAction() & 255) == 1) {
            if (!e(this.bDg, event.getX(), this.bDh, event.getY())) {
                super.onTouchEvent(event);
            } else if (this.bDf != null) {
                this.bDf.f(this.bDe, getCurrentItem());
            }
        }
        if ((event.getAction() & 255) == 0) {
            this.bDg = event.getX();
            this.bDh = event.getY();
        }
        float[] difference = e(event);
        if (this.bDe.PK()) {
            return super.onTouchEvent(event);
        }
        if (difference != null && this.bDe.bDP && difference[0] < 0.0f) {
            return super.onTouchEvent(event);
        }
        if (difference != null && this.bDe.bDN && difference[0] > 0.0f) {
            return super.onTouchEvent(event);
        }
        if (difference != null) {
            return false;
        }
        if (this.bDe.bDN || this.bDe.bDP) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if ((event.getAction() & 255) == 1) {
            if (!e(this.bDg, event.getX(), this.bDh, event.getY())) {
                super.onInterceptTouchEvent(event);
            } else if (this.bDf != null) {
                this.bDf.f(this.bDe, getCurrentItem());
            }
        }
        if ((event.getAction() & 255) == 0) {
            this.bDg = event.getX();
            this.bDh = event.getY();
        }
        float[] difference = e(event);
        if (this.bDe.PK()) {
            return super.onInterceptTouchEvent(event);
        }
        if (difference != null && this.bDe.bDP && difference[0] < 0.0f) {
            return super.onInterceptTouchEvent(event);
        }
        if (difference != null && this.bDe.bDN && difference[0] > 0.0f) {
            return super.onInterceptTouchEvent(event);
        }
        if (difference != null) {
            return false;
        }
        if (this.bDe.bDN || this.bDe.bDP) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    private boolean e(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        if (differenceX > 5.0f || differenceY > 5.0f) {
            return false;
        }
        return true;
    }

    public void setOnItemClickListener(a listener) {
        this.bDf = listener;
    }
}
