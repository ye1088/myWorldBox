package com.mojang.minecraftpe.platforms;

import android.os.Build.VERSION;
import android.view.View;

/* compiled from: Platform */
public abstract class a {
    public abstract void H(View view);

    public abstract String Rx();

    public abstract void Ry();

    public abstract void du(boolean z);

    public static a dt(boolean paramBoolean) {
        if (VERSION.SDK_INT >= 19) {
            return new b(paramBoolean);
        }
        if (VERSION.SDK_INT >= 21) {
            return new c(paramBoolean);
        }
        return new d();
    }
}
