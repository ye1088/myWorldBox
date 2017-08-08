package com.MCWorld.image.drawee.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.core.common.memory.c;
import com.MCWorld.image.core.common.memory.d;
import com.MCWorld.image.drawee.components.DraweeEventTracker;
import com.MCWorld.image.drawee.components.DraweeEventTracker.Event;
import com.MCWorld.image.drawee.drawable.s;
import com.MCWorld.image.drawee.drawable.t;
import com.MCWorld.image.drawee.interfaces.a;
import javax.annotation.Nullable;

/* compiled from: DraweeHolder */
public class b<DH extends com.MCWorld.image.drawee.interfaces.b> implements c, t {
    private boolean DI = false;
    private boolean DJ = false;
    private boolean DK = true;
    private boolean DL = false;
    private DH DM;
    private a DN = null;
    private final DraweeEventTracker zT = DraweeEventTracker.ja();

    public static <DH extends com.MCWorld.image.drawee.interfaces.b> b<DH> a(@Nullable DH hierarchy, Context context) {
        b<DH> holder = new b(hierarchy);
        holder.aG(context);
        d.a(holder);
        return holder;
    }

    public void aG(Context context) {
    }

    public b(@Nullable DH hierarchy) {
        if (hierarchy != null) {
            setHierarchy(hierarchy);
        }
    }

    public void onAttach() {
        this.zT.a(Event.ON_HOLDER_ATTACH);
        this.DJ = true;
        lb();
    }

    public boolean kX() {
        return this.DJ;
    }

    public void onDetach() {
        this.zT.a(Event.ON_HOLDER_DETACH);
        this.DJ = false;
        lb();
    }

    public void trim() {
        this.zT.a(Event.ON_HOLDER_TRIM);
        this.DL = true;
        lb();
    }

    public void is() {
        this.zT.a(Event.ON_HOLDER_UNTRIM);
        this.DL = false;
        lb();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.DN == null) {
            return false;
        }
        return this.DN.onTouchEvent(event);
    }

    public void ac(boolean isVisible) {
        if (this.DK != isVisible) {
            this.zT.a(isVisible ? Event.ON_DRAWABLE_SHOW : Event.ON_DRAWABLE_HIDE);
            this.DK = isVisible;
            lb();
        }
    }

    public void onDraw() {
        if (!this.DI) {
            if (!this.DL) {
                HLog.warn(DraweeEventTracker.class, String.format("%x: Draw requested for a_isRightVersion non-attached controller %x. %s", new Object[]{Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(this.DN)), toString()}), new Object[0]);
            }
            this.DL = false;
            this.DJ = true;
            this.DK = true;
            lb();
        }
    }

    private void a(@Nullable t visibilityCallback) {
        Drawable drawable = getTopLevelDrawable();
        if (drawable instanceof s) {
            ((s) drawable).a(visibilityCallback);
        }
    }

    public void setController(@Nullable a draweeController) {
        boolean wasAttached = this.DI;
        if (wasAttached) {
            la();
        }
        if (this.DN != null) {
            this.zT.a(Event.ON_CLEAR_OLD_CONTROLLER);
            this.DN.setHierarchy(null);
        }
        this.DN = draweeController;
        if (this.DN != null) {
            this.zT.a(Event.ON_SET_CONTROLLER);
            this.DN.setHierarchy(this.DM);
        } else {
            this.zT.a(Event.ON_CLEAR_CONTROLLER);
        }
        if (wasAttached) {
            kZ();
        }
    }

    @Nullable
    public a getController() {
        return this.DN;
    }

    public void setHierarchy(DH hierarchy) {
        this.zT.a(Event.ON_SET_HIERARCHY);
        a(null);
        this.DM = (com.MCWorld.image.drawee.interfaces.b) Preconditions.checkNotNull(hierarchy);
        Drawable drawable = this.DM.getTopLevelDrawable();
        boolean z = drawable == null || drawable.isVisible();
        ac(z);
        a(this);
        if (this.DN != null) {
            this.DN.setHierarchy(hierarchy);
        }
    }

    public DH getHierarchy() {
        return (com.MCWorld.image.drawee.interfaces.b) Preconditions.checkNotNull(this.DM);
    }

    public boolean hasHierarchy() {
        return this.DM != null;
    }

    public Drawable getTopLevelDrawable() {
        return this.DM == null ? null : this.DM.getTopLevelDrawable();
    }

    protected DraweeEventTracker kY() {
        return this.zT;
    }

    private void kZ() {
        if (!this.DI) {
            this.zT.a(Event.ON_ATTACH_CONTROLLER);
            this.DI = true;
            if (this.DN != null && this.DN.getHierarchy() != null) {
                this.DN.onAttach();
            }
        }
    }

    private void la() {
        if (this.DI) {
            this.zT.a(Event.ON_DETACH_CONTROLLER);
            this.DI = false;
            if (this.DN != null) {
                this.DN.onDetach();
            }
        }
    }

    private void lb() {
        if (this.DJ && this.DK && !this.DL) {
            kZ();
        } else {
            la();
        }
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("controllerAttached", this.DI).add("holderAttached", this.DJ).add("drawableVisible", this.DK).add("trimmed", this.DL).add("events", this.zT.toString()).toString();
    }
}
