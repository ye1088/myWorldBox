package com.mojang.minecraftpe.input;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;

/* compiled from: InputDeviceManager */
public abstract class a {

    /* compiled from: InputDeviceManager */
    public static class a extends a {
        public void Rw() {
            Log.w("MCPE", "INPUT Noop register device manager");
        }

        public void unregister() {
            Log.w("MCPE", "INPUT Noop unregister device manager");
        }
    }

    public abstract void Rw();

    public abstract void unregister();

    public static a bQ(Context paramContext) {
        if (VERSION.SDK_INT >= 16) {
            return new JellyBeanDeviceManager(paramContext);
        }
        return new a();
    }
}
