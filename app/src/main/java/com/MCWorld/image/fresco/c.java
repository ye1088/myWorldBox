package com.MCWorld.image.fresco;

import android.content.Context;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.image.drawee.view.SimpleDraweeView;
import com.MCWorld.image.pipeline.core.e;
import com.MCWorld.image.pipeline.core.f;
import com.MCWorld.image.pipeline.core.h;
import javax.annotation.Nullable;

/* compiled from: Fresco */
public class c {
    private static f Ea;
    private static volatile boolean Eb = false;
    private static final Class<?> tF = c.class;

    private c() {
    }

    public static void aH(Context context) {
        a(context, null, null);
    }

    public static void a(Context context, @Nullable f imagePipelineConfig) {
        a(context, imagePipelineConfig, null);
    }

    public static void a(Context context, @Nullable f imagePipelineConfig, @Nullable b draweeConfig) {
        if (Eb) {
            HLog.warn(tF, "Fresco has already been initialized! `Fresco.initialize(...)` should only be called 1 single time to avoid memory leaks!", new Object[0]);
        } else {
            Eb = true;
        }
        context = context.getApplicationContext();
        if (imagePipelineConfig == null) {
            h.aH(context);
        } else {
            h.a(imagePipelineConfig);
        }
        a(context, draweeConfig);
    }

    private static void a(Context context, @Nullable b draweeConfig) {
        Ea = new f(context, draweeConfig);
        SimpleDraweeView.initialize(Ea);
    }

    public static f lg() {
        return Ea;
    }

    public static e lh() {
        return Ea.lo();
    }

    public static h li() {
        return h.mz();
    }

    public static e lj() {
        return li().lj();
    }

    public static void shutDown() {
        Ea = null;
        SimpleDraweeView.shutDown();
        h.shutDown();
    }

    public static boolean lk() {
        return Eb;
    }
}
