package com.huluxia;

import android.content.Context;
import com.huluxia.framework.base.image.ImageLoader;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.image.base.cache.disk.b;
import com.huluxia.image.pipeline.core.f;
import com.huluxia.image.pipeline.core.f.a;
import com.huluxia.image.pipeline.listener.d;
import com.huluxia.image.pipeline.memory.c;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/* compiled from: Painter */
public class l {
    private static final String TAG = "Painter";
    private static l fY = null;
    private f fZ;
    private final m ga;
    private Supplier<c> gb = new 2(this);

    public static l cb() {
        return (l) Preconditions.checkNotNull(fY, "Painter was not initialized!");
    }

    public static void a(m config) {
        if (fY == null) {
            fY = new l(config);
        }
    }

    private l(m config) {
        this.ga = (m) Preconditions.checkNotNull(config);
        com.huluxia.image.fresco.c.a(this.ga.getContext(), ab(this.ga.getContext()));
    }

    public ImageLoader getImageLoader() {
        return new ImageLoader(null, null, null);
    }

    public f ab(Context context) {
        if (this.fZ == null) {
            a configBuilder = f.aJ(context);
            a(configBuilder, context);
            a(configBuilder);
            b(configBuilder);
            this.fZ = configBuilder.mp();
        }
        return this.fZ;
    }

    private void a(a configBuilder, Context context) {
        com.huluxia.image.base.imagepipeline.cache.f bitmapCacheParams = new com.huluxia.image.base.imagepipeline.cache.f(d.eH, Integer.MAX_VALUE, d.eH, Integer.MAX_VALUE, Integer.MAX_VALUE);
        File diskCacheDir = UtilsFile.getDiskCacheDir(this.ga.getContext(), this.ga.ce());
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        configBuilder.d(new 1(this, bitmapCacheParams)).a(o.ci()).c(b.aD(context).r(diskCacheDir).bh("images-new").a(n.cg()).K(Math.min(UtilsFile.availableSpace(diskCacheDir.getPath()) / 2, 41943040)).gK());
    }

    private void a(a configBuilder) {
        Set requestListeners = new HashSet();
        requestListeners.add(new d());
        configBuilder.c(requestListeners);
    }

    private void b(a configBuilder) {
        configBuilder.ag(true);
        configBuilder.g(this.gb);
        configBuilder.a(new p());
    }

    public void onLowMemory() {
        o.ci().onLowMemory();
    }
}
