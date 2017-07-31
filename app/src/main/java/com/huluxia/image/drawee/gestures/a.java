package com.huluxia.image.drawee.gestures;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.huluxia.framework.base.utils.VisibleForTesting;

/* compiled from: GestureDetector */
public class a {
    @VisibleForTesting
    a DB;
    @VisibleForTesting
    final float DC;
    @VisibleForTesting
    boolean DD;
    @VisibleForTesting
    boolean DE;
    @VisibleForTesting
    long DF;
    @VisibleForTesting
    float DG;
    @VisibleForTesting
    float DH;

    public a(Context context) {
        this.DC = (float) ViewConfiguration.get(context).getScaledTouchSlop();
        init();
    }

    public static a aF(Context context) {
        return new a(context);
    }

    public void init() {
        this.DB = null;
        reset();
    }

    public void reset() {
        this.DD = false;
        this.DE = false;
    }

    public void a(a clickListener) {
        this.DB = clickListener;
    }

    public boolean kW() {
        return this.DD;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.DD = true;
                this.DE = true;
                this.DF = event.getEventTime();
                this.DG = event.getX();
                this.DH = event.getY();
                break;
            case 1:
                this.DD = false;
                if (Math.abs(event.getX() - this.DG) > this.DC || Math.abs(event.getY() - this.DH) > this.DC) {
                    this.DE = false;
                }
                if (this.DE && event.getEventTime() - this.DF <= ((long) ViewConfiguration.getLongPressTimeout()) && this.DB != null) {
                    this.DB.jm();
                }
                this.DE = false;
                break;
            case 2:
                if (Math.abs(event.getX() - this.DG) > this.DC || Math.abs(event.getY() - this.DH) > this.DC) {
                    this.DE = false;
                    break;
                }
            case 3:
                this.DD = false;
                this.DE = false;
                break;
        }
        return true;
    }
}
