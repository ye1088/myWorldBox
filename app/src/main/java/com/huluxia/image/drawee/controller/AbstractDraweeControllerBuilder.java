package com.huluxia.image.drawee.controller;

import android.content.Context;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.core.datasource.c;
import com.huluxia.image.core.datasource.f;
import com.huluxia.image.core.datasource.g;
import com.huluxia.image.drawee.components.b;
import com.huluxia.image.drawee.interfaces.a;
import com.huluxia.image.drawee.interfaces.d;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

public abstract class AbstractDraweeControllerBuilder<BUILDER extends AbstractDraweeControllerBuilder<BUILDER, REQUEST, IMAGE, INFO>, REQUEST, IMAGE, INFO> implements d {
    private static final c<Object> Aj = new 1();
    private static final NullPointerException Ak = new NullPointerException("No image request was specified!");
    private static final AtomicLong At = new AtomicLong();
    private boolean Ae;
    private String Af;
    private final Set<c> Al;
    @Nullable
    private REQUEST Am;
    @Nullable
    private REQUEST An;
    @Nullable
    private REQUEST[] Ao;
    private boolean Ap;
    @Nullable
    private Supplier<c<IMAGE>> Aq;
    private boolean Ar;
    @Nullable
    private a As;
    private final Context mContext;
    @Nullable
    private c<? super INFO> mControllerListener;
    @Nullable
    private Object ty;
    private boolean zQ;
    @Nullable
    private d zY;

    protected abstract c<IMAGE> a(REQUEST request, Object obj, CacheLevel cacheLevel);

    protected abstract a jF();

    protected abstract BUILDER jG();

    public /* synthetic */ d G(Object obj) {
        return C(obj);
    }

    public /* synthetic */ d b(@Nullable a aVar) {
        return a(aVar);
    }

    public /* synthetic */ a jH() {
        return jB();
    }

    protected AbstractDraweeControllerBuilder(Context context, Set<c> boundControllerListeners) {
        this.mContext = context;
        this.Al = boundControllerListeners;
        init();
    }

    private void init() {
        this.ty = null;
        this.Am = null;
        this.An = null;
        this.Ao = null;
        this.Ap = true;
        this.mControllerListener = null;
        this.zY = null;
        this.zQ = false;
        this.Ar = false;
        this.As = null;
        this.Af = null;
    }

    public BUILDER jr() {
        init();
        return jG();
    }

    public BUILDER C(Object callerContext) {
        this.ty = callerContext;
        return jG();
    }

    @Nullable
    public Object gk() {
        return this.ty;
    }

    public BUILDER D(REQUEST imageRequest) {
        this.Am = imageRequest;
        return jG();
    }

    @Nullable
    public REQUEST js() {
        return this.Am;
    }

    public BUILDER E(REQUEST lowResImageRequest) {
        this.An = lowResImageRequest;
        return jG();
    }

    @Nullable
    public REQUEST jt() {
        return this.An;
    }

    public BUILDER c(REQUEST[] firstAvailableImageRequests) {
        return a((Object[]) firstAvailableImageRequests, true);
    }

    public BUILDER a(REQUEST[] firstAvailableImageRequests, boolean tryCacheOnlyFirst) {
        this.Ao = firstAvailableImageRequests;
        this.Ap = tryCacheOnlyFirst;
        return jG();
    }

    @Nullable
    public REQUEST[] ju() {
        return this.Ao;
    }

    public void b(@Nullable Supplier<c<IMAGE>> dataSourceSupplier) {
        this.Aq = dataSourceSupplier;
    }

    @Nullable
    public Supplier<c<IMAGE>> jv() {
        return this.Aq;
    }

    public BUILDER V(boolean enabled) {
        this.zQ = enabled;
        return jG();
    }

    public boolean jw() {
        return this.zQ;
    }

    public BUILDER W(boolean enabled) {
        this.Ae = enabled;
        return jG();
    }

    public boolean jx() {
        return this.Ae;
    }

    public BUILDER X(boolean enabled) {
        this.Ar = enabled;
        return jG();
    }

    public boolean jy() {
        return this.Ar;
    }

    public BUILDER c(c<? super INFO> controllerListener) {
        this.mControllerListener = controllerListener;
        return jG();
    }

    @Nullable
    public c<? super INFO> jj() {
        return this.mControllerListener;
    }

    public BUILDER b(@Nullable d controllerViewportVisibilityListener) {
        this.zY = controllerViewportVisibilityListener;
        return jG();
    }

    @Nullable
    public d jz() {
        return this.zY;
    }

    public BUILDER bv(String contentDescription) {
        this.Af = contentDescription;
        return jG();
    }

    @Nullable
    public String ji() {
        return this.Af;
    }

    public BUILDER a(@Nullable a oldController) {
        this.As = oldController;
        return jG();
    }

    @Nullable
    public a jA() {
        return this.As;
    }

    public a jB() {
        validate();
        if (this.Am == null && this.Ao == null && this.An != null) {
            this.Am = this.An;
            this.An = null;
        }
        return jC();
    }

    protected void validate() {
        boolean z = false;
        boolean z2 = this.Ao == null || this.Am == null;
        Preconditions.checkState(z2, "Cannot specify both ImageRequest and FirstAvailableImageRequests!");
        if (this.Aq == null || (this.Ao == null && this.Am == null && this.An == null)) {
            z = true;
        }
        Preconditions.checkState(z, "Cannot specify DataSourceSupplier with other ImageRequests! Use one or the other.");
    }

    protected a jC() {
        a controller = jF();
        controller.T(jx());
        controller.bu(ji());
        controller.a(jz());
        b(controller);
        a(controller);
        return controller;
    }

    protected static String jD() {
        return String.valueOf(At.getAndIncrement());
    }

    protected Supplier<c<IMAGE>> jE() {
        if (this.Aq != null) {
            return this.Aq;
        }
        Supplier<c<IMAGE>> supplier = null;
        if (this.Am != null) {
            supplier = F(this.Am);
        } else if (this.Ao != null) {
            supplier = b(this.Ao, this.Ap);
        }
        if (!(supplier == null || this.An == null)) {
            List<Supplier<c<IMAGE>>> suppliers = new ArrayList(2);
            suppliers.add(supplier);
            suppliers.add(F(this.An));
            supplier = g.l(suppliers);
        }
        if (supplier == null) {
            return com.huluxia.image.core.datasource.d.e(Ak);
        }
        return supplier;
    }

    protected Supplier<c<IMAGE>> b(REQUEST[] imageRequests, boolean tryBitmapCacheOnlyFirst) {
        List<Supplier<c<IMAGE>>> suppliers = new ArrayList(imageRequests.length * 2);
        if (tryBitmapCacheOnlyFirst) {
            for (Object a : imageRequests) {
                suppliers.add(a(a, CacheLevel.BITMAP_MEMORY_CACHE));
            }
        }
        for (Object a2 : imageRequests) {
            suppliers.add(F(a2));
        }
        return f.k(suppliers);
    }

    protected Supplier<c<IMAGE>> F(REQUEST imageRequest) {
        return a((Object) imageRequest, CacheLevel.FULL_FETCH);
    }

    protected Supplier<c<IMAGE>> a(REQUEST imageRequest, CacheLevel cacheLevel) {
        return new 2(this, imageRequest, gk(), cacheLevel);
    }

    protected void a(a controller) {
        if (this.Al != null) {
            for (c listener : this.Al) {
                controller.a(listener);
            }
        }
        if (this.mControllerListener != null) {
            controller.a(this.mControllerListener);
        }
        if (this.Ar) {
            controller.a(Aj);
        }
    }

    protected void b(a controller) {
        if (this.zQ) {
            b retryManager = controller.jg();
            if (retryManager == null) {
                retryManager = new b();
                controller.a(retryManager);
            }
            retryManager.S(this.zQ);
            c(controller);
        }
    }

    protected void c(a controller) {
        if (controller.jh() == null) {
            controller.a(com.huluxia.image.drawee.gestures.a.aF(this.mContext));
        }
    }

    protected Context getContext() {
        return this.mContext;
    }
}
