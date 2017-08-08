package com.MCWorld.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageSwitch extends ImageView {
    private boolean btL = true;
    private int btM = 0;
    private int btN = 0;
    private OnTouchListener btO = new 1(this);

    public ImageSwitch(Context context) {
        super(context);
    }

    public ImageSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void g(int trueImage, int falseImage, boolean firstFlag) {
        if (trueImage != 0 && falseImage != 0) {
            this.btM = trueImage;
            this.btN = falseImage;
            this.btL = firstFlag;
            setImageBitmap(((BitmapDrawable) getResources().getDrawable(firstFlag ? this.btM : this.btN)).getBitmap());
            setOnTouchListener(this.btO);
        }
    }

    public void setChecked(int trueFlag) {
        setImageBitmap(((BitmapDrawable) getResources().getDrawable(trueFlag != 0 ? this.btM : this.btN)).getBitmap());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.btM != 0 && this.btN != 0) {
            setImageBitmap(((BitmapDrawable) getResources().getDrawable(this.btL ? this.btM : this.btN)).getBitmap());
        }
    }

    public void setFirstFlag(boolean flag) {
        this.btL = flag;
    }

    public boolean isFirst() {
        return this.btL;
    }
}
