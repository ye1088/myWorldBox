package com.MCWorld.image.drawee.controller;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.core.datasource.c;
import com.MCWorld.image.drawee.components.DraweeEventTracker;
import com.MCWorld.image.drawee.components.DraweeEventTracker.Event;
import com.MCWorld.image.drawee.components.a$a;
import com.MCWorld.image.drawee.components.b;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
/* compiled from: AbstractDraweeController */
public abstract class a<T, INFO> implements a$a, com.MCWorld.image.drawee.gestures.a$a, com.MCWorld.image.drawee.interfaces.a {
    private static final Class<?> tF = a.class;
    @Nullable
    private Drawable Aa;
    private boolean Ab;
    private boolean Ac;
    private boolean Ad;
    private boolean Ae;
    @Nullable
    private String Af;
    @Nullable
    private T Ag;
    @Nullable
    private c<INFO> mControllerListener;
    @Nullable
    private c<T> mDataSource;
    @Nullable
    private Drawable mDrawable;
    private String mId;
    private boolean mIsAttached;
    private Object ty;
    private final DraweeEventTracker zT = DraweeEventTracker.ja();
    private final com.MCWorld.image.drawee.components.a zU;
    private final Executor zV;
    @Nullable
    private b zW;
    @Nullable
    private com.MCWorld.image.drawee.gestures.a zX;
    @Nullable
    private d zY;
    @Nullable
    private com.MCWorld.image.drawee.interfaces.c zZ;

    protected abstract void B(@Nullable T t);

    protected abstract void c(@Nullable Drawable drawable);

    protected abstract c<T> jp();

    protected abstract Drawable x(T t);

    @Nullable
    protected abstract INFO y(T t);

    public a(com.MCWorld.image.drawee.components.a deferredReleaser, Executor uiThreadImmediateExecutor, String id, Object callerContext) {
        this.zU = deferredReleaser;
        this.zV = uiThreadImmediateExecutor;
        a(id, callerContext, true);
    }

    protected void g(String id, Object callerContext) {
        a(id, callerContext, false);
    }

    private void a(String id, Object callerContext, boolean justConstructed) {
        this.zT.a(Event.ON_INIT_CONTROLLER);
        if (!(justConstructed || this.zU == null)) {
            this.zU.b(this);
        }
        this.mIsAttached = false;
        this.Ac = false;
        jf();
        this.Ae = false;
        if (this.zW != null) {
            this.zW.init();
        }
        if (this.zX != null) {
            this.zX.init();
            this.zX.a(this);
        }
        if (this.mControllerListener instanceof a) {
            ((a) this.mControllerListener).jK();
        } else {
            this.mControllerListener = null;
        }
        this.zY = null;
        if (this.zZ != null) {
            this.zZ.reset();
            this.zZ.b(null);
            this.zZ = null;
        }
        this.Aa = null;
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x %s -> %s: initialize", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId, id}), new Object[0]);
        }
        this.mId = id;
        this.ty = callerContext;
    }

    public void release() {
        this.zT.a(Event.ON_RELEASE_CONTROLLER);
        if (this.zW != null) {
            this.zW.reset();
        }
        if (this.zX != null) {
            this.zX.reset();
        }
        if (this.zZ != null) {
            this.zZ.reset();
        }
        jf();
    }

    private void jf() {
        boolean wasRequestSubmitted = this.Ab;
        this.Ab = false;
        this.Ad = false;
        if (this.mDataSource != null) {
            this.mDataSource.close();
            this.mDataSource = null;
        }
        if (this.mDrawable != null) {
            c(this.mDrawable);
        }
        if (this.Af != null) {
            this.Af = null;
        }
        this.mDrawable = null;
        if (this.Ag != null) {
            h("release", this.Ag);
            B(this.Ag);
            this.Ag = null;
        }
        if (wasRequestSubmitted) {
            jj().bw(this.mId);
        }
    }

    public String getId() {
        return this.mId;
    }

    public Object gk() {
        return this.ty;
    }

    @Nullable
    protected b jg() {
        return this.zW;
    }

    protected void a(@Nullable b retryManager) {
        this.zW = retryManager;
    }

    @Nullable
    protected com.MCWorld.image.drawee.gestures.a jh() {
        return this.zX;
    }

    protected void a(@Nullable com.MCWorld.image.drawee.gestures.a gestureDetector) {
        this.zX = gestureDetector;
        if (this.zX != null) {
            this.zX.a(this);
        }
    }

    protected void T(boolean enabled) {
        this.Ae = enabled;
    }

    @Nullable
    public String ji() {
        return this.Af;
    }

    public void bu(@Nullable String contentDescription) {
        this.Af = contentDescription;
    }

    public void a(c<? super INFO> controllerListener) {
        Preconditions.checkNotNull(controllerListener);
        if (this.mControllerListener instanceof a) {
            ((a) this.mControllerListener).e(controllerListener);
        } else if (this.mControllerListener != null) {
            this.mControllerListener = a.a(this.mControllerListener, controllerListener);
        } else {
            this.mControllerListener = controllerListener;
        }
    }

    public void b(c<? super INFO> controllerListener) {
        Preconditions.checkNotNull(controllerListener);
        if (this.mControllerListener instanceof a) {
            ((a) this.mControllerListener).f(controllerListener);
        } else if (this.mControllerListener == controllerListener) {
            this.mControllerListener = null;
        }
    }

    protected c<INFO> jj() {
        if (this.mControllerListener == null) {
            return b.jI();
        }
        return this.mControllerListener;
    }

    public void a(@Nullable d controllerViewportVisibilityListener) {
        this.zY = controllerViewportVisibilityListener;
    }

    @Nullable
    public com.MCWorld.image.drawee.interfaces.b getHierarchy() {
        return this.zZ;
    }

    public void setHierarchy(@Nullable com.MCWorld.image.drawee.interfaces.b hierarchy) {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x %s: setHierarchy: %s", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId, hierarchy}), new Object[0]);
        }
        this.zT.a(hierarchy != null ? Event.ON_SET_HIERARCHY : Event.ON_CLEAR_HIERARCHY);
        if (this.Ab) {
            this.zU.b(this);
            release();
        }
        if (this.zZ != null) {
            this.zZ.b(null);
            this.zZ = null;
        }
        if (hierarchy != null) {
            Preconditions.checkArgument(hierarchy instanceof com.MCWorld.image.drawee.interfaces.c);
            this.zZ = (com.MCWorld.image.drawee.interfaces.c) hierarchy;
            this.zZ.b(this.Aa);
        }
    }

    protected void b(@Nullable Drawable controllerOverlay) {
        this.Aa = controllerOverlay;
        if (this.zZ != null) {
            this.zZ.b(this.Aa);
        }
    }

    @Nullable
    protected Drawable jk() {
        return this.Aa;
    }

    public void onAttach() {
        if (HLog.isImageLoggable(1)) {
            Class cls = tF;
            String str = "controller %x %s: onAttach: %s";
            Object[] objArr = new Object[3];
            objArr[0] = Integer.valueOf(System.identityHashCode(this));
            objArr[1] = this.mId;
            objArr[2] = this.Ab ? "request already submitted" : "request needs submit";
            HLog.verbose(cls, String.format(str, objArr), new Object[0]);
        }
        this.zT.a(Event.ON_ATTACH_CONTROLLER);
        Preconditions.checkNotNull(this.zZ);
        this.zU.b(this);
        this.mIsAttached = true;
        if (!this.Ab) {
            jn();
        }
    }

    public void onDetach() {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x %s: onDetach", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId}), new Object[0]);
        }
        this.zT.a(Event.ON_DETACH_CONTROLLER);
        this.mIsAttached = false;
        this.zU.a(this);
    }

    public void U(boolean isVisibleInViewportHint) {
        d listener = this.zY;
        if (listener != null) {
            if (isVisibleInViewportHint && !this.Ac) {
                listener.bx(this.mId);
            } else if (!isVisibleInViewportHint && this.Ac) {
                listener.by(this.mId);
            }
        }
        this.Ac = isVisibleInViewportHint;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x %s: onTouchEvent %s", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId, event}), new Object[0]);
        }
        if (this.zX == null) {
            return false;
        }
        if (!this.zX.kW() && !jl()) {
            return false;
        }
        this.zX.onTouchEvent(event);
        return true;
    }

    protected boolean jl() {
        return jd();
    }

    private boolean jd() {
        return this.Ad && this.zW != null && this.zW.jd();
    }

    public boolean jm() {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x %s: onClick", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId}), new Object[0]);
        }
        if (!jd()) {
            return false;
        }
        this.zW.je();
        this.zZ.reset();
        jn();
        return true;
    }

    protected void jn() {
        T closeableImage = jq();
        if (closeableImage != null) {
            this.mDataSource = null;
            this.Ab = true;
            this.Ad = false;
            this.zT.a(Event.ON_SUBMIT_CACHE_HIT);
            jj().i(this.mId, this.ty);
            a(this.mId, this.mDataSource, closeableImage, 1.0f, true, true);
            return;
        }
        this.zT.a(Event.ON_DATASOURCE_SUBMIT);
        jj().i(this.mId, this.ty);
        this.zZ.a(0.0f, true);
        this.Ab = true;
        this.Ad = false;
        this.mDataSource = jp();
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x %s: submitRequest: dataSource: %x", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId, Integer.valueOf(System.identityHashCode(this.mDataSource))}), new Object[0]);
        }
        this.mDataSource.a(new 1(this, this.mId, this.mDataSource.iN()), this.zV);
    }

    private void a(String id, c<T> dataSource, @Nullable T image, float progress, boolean isFinished, boolean wasImmediate) {
        if (a(id, dataSource)) {
            this.zT.a(isFinished ? Event.ON_DATASOURCE_RESULT : Event.ON_DATASOURCE_RESULT_INT);
            try {
                Drawable drawable = x(image);
                T previousImage = this.Ag;
                Drawable previousDrawable = this.mDrawable;
                this.Ag = image;
                this.mDrawable = drawable;
                if (isFinished) {
                    try {
                        h("set_final_result @ onNewResult", image);
                        this.mDataSource = null;
                        this.zZ.a(drawable, 1.0f, wasImmediate);
                        jj().a(id, y(image), jo());
                    } catch (Throwable th) {
                        if (!(previousDrawable == null || previousDrawable == drawable)) {
                            c(previousDrawable);
                        }
                        if (!(previousImage == null || previousImage == image)) {
                            h("release_previous_result @ onNewResult", previousImage);
                            B(previousImage);
                        }
                    }
                } else {
                    h("set_intermediate_result @ onNewResult", image);
                    this.zZ.a(drawable, progress, wasImmediate);
                    jj().j(id, y(image));
                }
                if (!(previousDrawable == null || previousDrawable == drawable)) {
                    c(previousDrawable);
                }
                if (previousImage != null && previousImage != image) {
                    h("release_previous_result @ onNewResult", previousImage);
                    B(previousImage);
                    return;
                }
                return;
            } catch (Throwable exception) {
                h("drawable_failed @ onNewResult", image);
                B(image);
                a(id, (c) dataSource, exception, isFinished);
                return;
            }
        }
        h("ignore_old_datasource @ onNewResult", image);
        B(image);
        dataSource.close();
    }

    private void a(String id, c<T> dataSource, Throwable throwable, boolean isFinished) {
        if (a(id, dataSource)) {
            this.zT.a(isFinished ? Event.ON_DATASOURCE_FAILURE : Event.ON_DATASOURCE_FAILURE_INT);
            if (isFinished) {
                b("final_failed @ onFailure", throwable);
                this.mDataSource = null;
                this.Ad = true;
                if (this.Ae && this.mDrawable != null) {
                    this.zZ.a(this.mDrawable, 1.0f, true);
                } else if (jd()) {
                    this.zZ.g(throwable);
                } else {
                    this.zZ.f(throwable);
                }
                jj().d(this.mId, throwable);
                return;
            }
            b("intermediate_failed @ onFailure", throwable);
            jj().c(this.mId, throwable);
            return;
        }
        b("ignore_old_datasource @ onFailure", throwable);
        dataSource.close();
    }

    private void a(String id, c<T> dataSource, float progress, boolean isFinished) {
        if (!a(id, dataSource)) {
            b("ignore_old_datasource @ onProgress", null);
            dataSource.close();
        } else if (!isFinished) {
            jj().a(id, progress);
            this.zZ.a(progress, false);
        }
    }

    private boolean a(String id, c<T> dataSource) {
        if (dataSource == null && this.mDataSource == null) {
            return true;
        }
        if (id.equals(this.mId) && dataSource == this.mDataSource && this.Ab) {
            return true;
        }
        return false;
    }

    private void h(String messageAndMethod, T image) {
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(tF, String.format("controller %x %s: %s: image: %s %x", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId, messageAndMethod, z(image), Integer.valueOf(A(image))}), new Object[0]);
        }
    }

    private void b(String messageAndMethod, Throwable throwable) {
        if (HLog.isImageLoggable(4)) {
            HLog.warn(tF, String.format("controller %x %s: %s: failure: %s", new Object[]{Integer.valueOf(System.identityHashCode(this)), this.mId, messageAndMethod, throwable}), new Object[0]);
        }
    }

    @Nullable
    public Animatable jo() {
        return this.mDrawable instanceof Animatable ? (Animatable) this.mDrawable : null;
    }

    protected String z(@Nullable T image) {
        return image != null ? image.getClass().getSimpleName() : "<null>";
    }

    protected int A(@Nullable T image) {
        return System.identityHashCode(image);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("isAttached", this.mIsAttached).add("isRequestSubmitted", this.Ab).add("hasFetchFailed", this.Ad).add("fetchedImage", A(this.Ag)).add("events", this.zT.toString()).toString();
    }

    protected T jq() {
        return null;
    }
}
