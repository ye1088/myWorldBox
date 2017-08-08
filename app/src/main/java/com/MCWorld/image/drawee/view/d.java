package com.MCWorld.image.drawee.view;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.drawee.interfaces.b;
import java.util.ArrayList;

/* compiled from: MultiDraweeHolder */
public class d<DH extends b> {
    @VisibleForTesting
    ArrayList<b<DH>> DW = new ArrayList();
    @VisibleForTesting
    boolean mIsAttached = false;

    public void onAttach() {
        if (!this.mIsAttached) {
            this.mIsAttached = true;
            for (int i = 0; i < this.DW.size(); i++) {
                ((b) this.DW.get(i)).onAttach();
            }
        }
    }

    public void onDetach() {
        if (this.mIsAttached) {
            this.mIsAttached = false;
            for (int i = 0; i < this.DW.size(); i++) {
                ((b) this.DW.get(i)).onDetach();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < this.DW.size(); i++) {
            if (((b) this.DW.get(i)).onTouchEvent(event)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        if (this.mIsAttached) {
            for (int i = 0; i < this.DW.size(); i++) {
                ((b) this.DW.get(i)).onDetach();
            }
        }
        this.DW.clear();
    }

    public void a(b<DH> holder) {
        a(this.DW.size(), holder);
    }

    public void a(int index, b<DH> holder) {
        Preconditions.checkNotNull(holder);
        Preconditions.checkElementIndex(index, this.DW.size() + 1);
        this.DW.add(index, holder);
        if (this.mIsAttached) {
            holder.onAttach();
        }
    }

    public void remove(int index) {
        b<DH> holder = (b) this.DW.get(index);
        if (this.mIsAttached) {
            holder.onDetach();
        }
        this.DW.remove(index);
    }

    public b<DH> cj(int index) {
        return (b) this.DW.get(index);
    }

    public int size() {
        return this.DW.size();
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < this.DW.size(); i++) {
            Drawable drawable = cj(i).getTopLevelDrawable();
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    public boolean verifyDrawable(Drawable who) {
        for (int i = 0; i < this.DW.size(); i++) {
            if (who == cj(i).getTopLevelDrawable()) {
                return true;
            }
        }
        return false;
    }
}
