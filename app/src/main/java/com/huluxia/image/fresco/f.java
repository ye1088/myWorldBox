package com.huluxia.image.fresco;

import android.content.Context;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.base.imagepipeline.animated.factory.a;
import com.huluxia.image.base.imagepipeline.animated.factory.b;
import com.huluxia.image.core.common.executors.g;
import com.huluxia.image.drawee.controller.c;
import com.huluxia.image.pipeline.core.e;
import com.huluxia.image.pipeline.core.h;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: PipelineDraweeControllerBuilderSupplier */
public class f implements Supplier<e> {
    private final Set<c> Al;
    private final e Eh;
    private final g Ei;
    private final Context mContext;

    public /* synthetic */ Object get() {
        return lo();
    }

    public f(Context context) {
        this(context, null);
    }

    public f(Context context, @Nullable b draweeConfig) {
        this(context, h.mz(), draweeConfig);
    }

    public f(Context context, h imagePipelineFactory, @Nullable b draweeConfig) {
        this(context, imagePipelineFactory, null, draweeConfig);
    }

    public f(Context context, h imagePipelineFactory, Set<c> boundControllerListeners, @Nullable b draweeConfig) {
        this.mContext = context;
        this.Eh = imagePipelineFactory.lj();
        b animatedFactory = imagePipelineFactory.mA();
        a animatedDrawableFactory = null;
        if (animatedFactory != null) {
            animatedDrawableFactory = animatedFactory.aE(context);
        }
        boolean drawDebugOverlay = draweeConfig != null && draweeConfig.ld();
        this.Ei = new g(context.getResources(), com.huluxia.image.drawee.components.a.iX(), animatedDrawableFactory, g.ir(), this.Eh.lM(), draweeConfig != null ? draweeConfig.lc() : null, drawDebugOverlay);
        this.Al = boundControllerListeners;
    }

    public e lo() {
        return new e(this.mContext, this.Ei, this.Eh, this.Al);
    }
}
