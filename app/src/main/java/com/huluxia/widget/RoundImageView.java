package com.huluxia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundImageView extends ImageView {
    private final RectF btZ = new RectF();
    private float bua = 8.0f;
    private final Paint bub = new Paint();
    private final Paint buc = new Paint();

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.bub.setAntiAlias(true);
        this.bub.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        this.buc.setAntiAlias(true);
        this.buc.setColor(-1);
        this.bua *= getResources().getDisplayMetrics().density;
    }

    public void setRectAdius(float adius) {
        this.bua = adius;
        invalidate();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.btZ.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
    }

    public void draw(Canvas canvas) {
        canvas.saveLayer(this.btZ, this.buc, 31);
        canvas.drawRoundRect(this.btZ, this.bua, this.bua, this.buc);
        canvas.saveLayer(this.btZ, this.bub, 31);
        super.draw(canvas);
        canvas.restore();
    }
}
