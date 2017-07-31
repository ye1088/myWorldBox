package com.huluxia.widget.wheel.adapters;

import android.content.Context;

/* compiled from: ArrayWheelAdapter */
public class d<T> extends b {
    private T[] bGI;

    public d(Context context, T[] items) {
        super(context);
        this.bGI = items;
    }

    public CharSequence mz(int index) {
        if (index < 0 || index >= this.bGI.length) {
            return null;
        }
        T item = this.bGI[index];
        if (item instanceof CharSequence) {
            return (CharSequence) item;
        }
        return item.toString();
    }

    public int Qa() {
        return this.bGI.length;
    }
}
