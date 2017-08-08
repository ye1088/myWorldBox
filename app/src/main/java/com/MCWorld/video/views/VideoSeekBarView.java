package com.MCWorld.video.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.video.b.e;

public class VideoSeekBarView extends View {
    private static Drawable brb;
    private static Paint brc = new Paint();
    private static int brd;
    private static int bre;
    private int brf = 0;
    public a brg;
    private float pa = 0.0f;
    private boolean pressed = false;

    public interface a {
        void I(float f);
    }

    private void init(Context context) {
        if (brb == null) {
            brb = context.getResources().getDrawable(e.videolapse);
            brc.setColor(-1717986919);
            brd = brb.getIntrinsicWidth();
            bre = brb.getIntrinsicHeight();
        }
    }

    public VideoSeekBarView(Context context) {
        super(context);
        init(context);
    }

    public VideoSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        float thumbX = (float) ((int) (((float) (getMeasuredWidth() - brd)) * this.pa));
        if (event.getAction() == 0) {
            int additionWidth = (getMeasuredHeight() - brd) / 2;
            if (thumbX - ((float) additionWidth) > x || x > (((float) brd) + thumbX) + ((float) additionWidth) || y < 0.0f || y > ((float) getMeasuredHeight())) {
                return false;
            }
            this.pressed = true;
            this.brf = (int) (x - thumbX);
            getParent().requestDisallowInterceptTouchEvent(true);
            invalidate();
            return true;
        } else if (event.getAction() == 1 || event.getAction() == 3) {
            if (!this.pressed) {
                return false;
            }
            if (event.getAction() == 1 && this.brg != null) {
                this.brg.I(thumbX / ((float) (getMeasuredWidth() - brd)));
            }
            this.pressed = false;
            invalidate();
            return true;
        } else if (event.getAction() != 2 || !this.pressed) {
            return false;
        } else {
            thumbX = (float) ((int) (x - ((float) this.brf)));
            if (thumbX < 0.0f) {
                thumbX = 0.0f;
            } else if (thumbX > ((float) (getMeasuredWidth() - brd))) {
                thumbX = (float) (getMeasuredWidth() - brd);
            }
            this.pa = thumbX / ((float) (getMeasuredWidth() - brd));
            invalidate();
            return true;
        }
    }

    public void setProgress(float progress) {
        if (progress < 0.0f) {
            progress = 0.0f;
        } else if (progress > 1.0f) {
            progress = 1.0f;
        }
        this.pa = progress;
        invalidate();
    }

    public float getProgress() {
        return this.pa;
    }

    protected void onDraw(Canvas canvas) {
        int y = (getMeasuredHeight() - bre) / 2;
        int thumbX = (int) (((float) (getMeasuredWidth() - brd)) * this.pa);
        canvas.drawRect((float) (brd / 2), (float) ((getMeasuredHeight() / 2) - UtilsScreen.dipToPx(getContext(), 1)), (float) (getMeasuredWidth() - (brd / 2)), (float) ((getMeasuredHeight() / 2) + UtilsScreen.dipToPx(getContext(), 1)), brc);
        brb.setBounds(thumbX, y, brd + thumbX, bre + y);
        brb.draw(canvas);
    }
}
