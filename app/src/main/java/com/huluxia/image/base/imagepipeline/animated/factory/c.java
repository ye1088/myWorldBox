package com.huluxia.image.base.imagepipeline.animated.factory;

import com.huluxia.image.base.imagepipeline.bitmaps.a;
import com.huluxia.image.base.imagepipeline.core.b;

/* compiled from: AnimatedFactoryProvider */
public class c {
    private static boolean vF;
    private static b vG = null;

    public static b a(a platformBitmapFactory, b executorSupplier) {
        if (!vF) {
            try {
                vG = (b) Class.forName("com.huluxia.image.animated.factory.AnimatedFactoryImplSupport").getConstructor(new Class[]{a.class, b.class}).newInstance(new Object[]{platformBitmapFactory, executorSupplier});
            } catch (Throwable th) {
            }
            if (vG != null) {
                vF = true;
                return vG;
            }
            try {
                vG = (b) Class.forName("com.huluxia.image.animated.factory.AnimatedFactoryImpl").getConstructor(new Class[]{a.class, b.class}).newInstance(new Object[]{platformBitmapFactory, executorSupplier});
            } catch (Throwable th2) {
            }
            vF = true;
        }
        return vG;
    }
}
