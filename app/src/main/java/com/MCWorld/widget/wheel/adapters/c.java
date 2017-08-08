package com.MCWorld.widget.wheel.adapters;

import android.content.Context;
import com.MCWorld.widget.wheel.e;

@Deprecated
/* compiled from: AdapterWheel */
public class c extends b {
    private e bGH;

    public c(Context context, e adapter) {
        super(context);
        this.bGH = adapter;
    }

    public e Qy() {
        return this.bGH;
    }

    public int Qa() {
        return this.bGH.Qa();
    }

    protected CharSequence mz(int index) {
        return this.bGH.kA(index);
    }
}
