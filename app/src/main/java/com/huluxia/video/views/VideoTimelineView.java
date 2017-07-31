package com.huluxia.video.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.video.b.e;
import java.util.ArrayList;
import java.util.Iterator;

public class VideoTimelineView extends View {
    private Paint bql;
    private long brh = 0;
    private float bri = 0.0f;
    private float brj = 1.0f;
    private Paint brk;
    private boolean brl = false;
    private boolean brm = false;
    private float brn = 0.0f;
    private a bro = null;
    private ArrayList<Bitmap> brp = new ArrayList();
    private AsyncTask<Integer, Integer, Bitmap> brq = null;
    private long brr = 0;
    private int brs = 0;
    private int brt = 0;
    private int bru = 0;
    private Drawable brv = null;

    public interface a {
        void J(float f);

        void K(float f);

        void L(float f);

        void M(float f);

        void NG();
    }

    private void init(Context context) {
        this.bql = new Paint();
        this.bql.setColor(-10038802);
        this.brk = new Paint();
        this.brk.setColor(2130706432);
        this.brv = getResources().getDrawable(e.video_trimmer);
    }

    public VideoTimelineView(Context context) {
        super(context);
        init(context);
    }

    public VideoTimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoTimelineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public float getLeftProgress() {
        return this.bri;
    }

    public float getRightProgress() {
        return this.brj;
    }

    public void setProgressLeft(float progressLeft) {
        this.bri = progressLeft;
        invalidate();
        if (this.bro != null) {
            this.bro.J(progressLeft);
        }
    }

    public void setProgressRight(float progressRight) {
        this.brj = progressRight;
        invalidate();
        if (this.bro != null) {
            this.bro.K(progressRight);
        }
    }

    public void setVideoLength(long videoLength) {
        this.brh = videoLength;
        if (this.brp.isEmpty() && this.brq == null) {
            lJ(0);
        }
    }

    public long getFrameTimeOffset() {
        return this.brr;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        int width = getMeasuredWidth() - UtilsScreen.dipToPx(getContext(), 32);
        int startX = ((int) (((float) width) * this.bri)) + UtilsScreen.dipToPx(getContext(), 16);
        int endX = ((int) (((float) width) * this.brj)) + UtilsScreen.dipToPx(getContext(), 16);
        if (event.getAction() == 0) {
            int additionWidth = UtilsScreen.dipToPx(getContext(), 12);
            if (((float) (startX - additionWidth)) <= x && x <= ((float) (startX + additionWidth)) && y >= 0.0f && y <= ((float) getMeasuredHeight())) {
                this.brl = true;
                this.brn = (float) ((int) (x - ((float) startX)));
                getParent().requestDisallowInterceptTouchEvent(true);
                invalidate();
                return true;
            } else if (((float) (endX - additionWidth)) > x || x > ((float) (endX + additionWidth)) || y < 0.0f || y > ((float) getMeasuredHeight())) {
                return false;
            } else {
                this.brm = true;
                this.brn = (float) ((int) (x - ((float) endX)));
                getParent().requestDisallowInterceptTouchEvent(true);
                invalidate();
                return true;
            }
        } else if (event.getAction() == 1 || event.getAction() == 3) {
            if (this.brl) {
                this.brl = false;
                if (this.bro != null) {
                    this.bro.L(this.bri);
                }
                return true;
            } else if (!this.brm) {
                return false;
            } else {
                this.brm = false;
                if (this.bro != null) {
                    this.bro.M(this.brj);
                }
                return true;
            }
        } else if (event.getAction() != 2) {
            return false;
        } else {
            if (this.brl) {
                startX = (int) (x - this.brn);
                if (startX < UtilsScreen.dipToPx(getContext(), 16)) {
                    startX = UtilsScreen.dipToPx(getContext(), 16);
                } else if (startX > endX) {
                    startX = endX;
                }
                this.bri = ((float) (startX - UtilsScreen.dipToPx(getContext(), 16))) / ((float) width);
                if (this.bro != null) {
                    this.bro.J(this.bri);
                }
                invalidate();
                return true;
            } else if (!this.brm) {
                return false;
            } else {
                endX = (int) (x - this.brn);
                if (endX < startX) {
                    endX = startX;
                } else if (endX > UtilsScreen.dipToPx(getContext(), 16) + width) {
                    endX = width + UtilsScreen.dipToPx(getContext(), 16);
                }
                this.brj = ((float) (endX - UtilsScreen.dipToPx(getContext(), 16))) / ((float) width);
                if (this.bro != null) {
                    this.bro.K(this.brj);
                }
                invalidate();
                return true;
            }
        }
    }

    public void setDelegate(a delegate) {
        this.bro = delegate;
    }

    public void q(Bitmap bitmap) {
        this.brp.add(bitmap);
        invalidate();
    }

    public int getFramesNum() {
        return this.brp != null ? this.brp.size() : 0;
    }

    public int getFramesToLoad() {
        return this.bru;
    }

    public int getFrameHeight() {
        return this.brt;
    }

    public int getFrameWidth() {
        return this.brs;
    }

    public void destroy() {
        Iterator it = this.brp.iterator();
        while (it.hasNext()) {
            Bitmap bitmap = (Bitmap) it.next();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        this.brp.clear();
        if (this.brq != null) {
            this.brq.cancel(true);
            this.brq = null;
        }
    }

    public void NF() {
        Iterator it = this.brp.iterator();
        while (it.hasNext()) {
            Bitmap bitmap = (Bitmap) it.next();
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        this.brp.clear();
        if (this.brq != null) {
            this.brq.cancel(true);
            this.brq = null;
        }
        invalidate();
    }

    private void lJ(int frameNum) {
        if (frameNum == 0) {
            this.brt = UtilsScreen.dipToPx(getContext(), 40);
            this.bru = (getMeasuredWidth() - UtilsScreen.dipToPx(getContext(), 16)) / this.brt;
            this.brs = (int) Math.ceil((double) (((float) (getMeasuredWidth() - UtilsScreen.dipToPx(getContext(), 16))) / ((float) this.bru)));
            if (this.bru > 0) {
                this.brr = this.brh / ((long) this.bru);
            } else {
                HLog.error(this, "frame to load is zero", new Object[0]);
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth() - UtilsScreen.dipToPx(getContext(), 36);
        int startX = ((int) (((float) width) * this.bri)) + UtilsScreen.dipToPx(getContext(), 16);
        int endX = ((int) (((float) width) * this.brj)) + UtilsScreen.dipToPx(getContext(), 16);
        canvas.save();
        canvas.clipRect(UtilsScreen.dipToPx(getContext(), 16), 0, UtilsScreen.dipToPx(getContext(), 20) + width, UtilsScreen.dipToPx(getContext(), 44));
        if (this.brp.isEmpty() && this.brq == null) {
            int oldFramesLoad = this.bru;
            lJ(0);
            if (!(this.bru == oldFramesLoad || this.bro == null)) {
                this.bro.NG();
            }
        } else {
            int offset = 0;
            Iterator it = this.brp.iterator();
            while (it.hasNext()) {
                Bitmap bitmap = (Bitmap) it.next();
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, (float) (UtilsScreen.dipToPx(getContext(), 16) + (this.brs * offset)), (float) UtilsScreen.dipToPx(getContext(), 2), null);
                }
                offset++;
            }
        }
        canvas.drawRect((float) UtilsScreen.dipToPx(getContext(), 16), (float) UtilsScreen.dipToPx(getContext(), 2), (float) startX, (float) UtilsScreen.dipToPx(getContext(), 42), this.brk);
        canvas.drawRect((float) (UtilsScreen.dipToPx(getContext(), 4) + endX), (float) UtilsScreen.dipToPx(getContext(), 2), (float) ((UtilsScreen.dipToPx(getContext(), 16) + width) + UtilsScreen.dipToPx(getContext(), 4)), (float) UtilsScreen.dipToPx(getContext(), 42), this.brk);
        canvas.drawRect((float) startX, 0.0f, (float) (UtilsScreen.dipToPx(getContext(), 2) + startX), (float) UtilsScreen.dipToPx(getContext(), 44), this.bql);
        canvas.drawRect((float) (UtilsScreen.dipToPx(getContext(), 2) + endX), 0.0f, (float) (UtilsScreen.dipToPx(getContext(), 4) + endX), (float) UtilsScreen.dipToPx(getContext(), 44), this.bql);
        canvas.drawRect((float) (UtilsScreen.dipToPx(getContext(), 2) + startX), 0.0f, (float) (UtilsScreen.dipToPx(getContext(), 4) + endX), (float) UtilsScreen.dipToPx(getContext(), 2), this.bql);
        canvas.drawRect((float) (UtilsScreen.dipToPx(getContext(), 2) + startX), (float) UtilsScreen.dipToPx(getContext(), 42), (float) (UtilsScreen.dipToPx(getContext(), 4) + endX), (float) UtilsScreen.dipToPx(getContext(), 44), this.bql);
        canvas.restore();
        int drawableWidth = this.brv.getIntrinsicWidth();
        int drawableHeight = this.brv.getIntrinsicHeight();
        this.brv.setBounds(startX - (drawableWidth / 2), getMeasuredHeight() - drawableHeight, (drawableWidth / 2) + startX, getMeasuredHeight());
        this.brv.draw(canvas);
        this.brv.setBounds((endX - (drawableWidth / 2)) + UtilsScreen.dipToPx(getContext(), 4), getMeasuredHeight() - drawableHeight, ((drawableWidth / 2) + endX) + UtilsScreen.dipToPx(getContext(), 4), getMeasuredHeight());
        this.brv.draw(canvas);
    }
}
