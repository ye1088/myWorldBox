package com.huluxia.widget.wheel.adapters;

import android.content.Context;

/* compiled from: NumericWheelAdapter */
public class e extends b {
    public static final int bGJ = 9;
    private static final int bGK = 0;
    private int bGL;
    private String format;
    private int maxValue;

    public e(Context context) {
        this(context, 0, 9);
    }

    public e(Context context, int minValue, int maxValue) {
        this(context, minValue, maxValue, null);
    }

    public e(Context context, int minValue, int maxValue, String format) {
        super(context);
        this.bGL = minValue;
        this.maxValue = maxValue;
        this.format = format;
    }

    public CharSequence mz(int index) {
        if (index < 0 || index >= Qa()) {
            return null;
        }
        int value = this.bGL + index;
        if (this.format == null) {
            return Integer.toString(value);
        }
        return String.format(this.format, new Object[]{Integer.valueOf(value)});
    }

    public int Qa() {
        return (this.maxValue - this.bGL) + 1;
    }
}
