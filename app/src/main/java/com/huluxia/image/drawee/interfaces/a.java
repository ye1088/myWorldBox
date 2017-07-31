package com.huluxia.image.drawee.interfaces;

import android.graphics.drawable.Animatable;
import android.view.MotionEvent;
import javax.annotation.Nullable;

/* compiled from: DraweeController */
public interface a {
    void U(boolean z);

    void bu(String str);

    @Nullable
    b getHierarchy();

    String ji();

    Animatable jo();

    void onAttach();

    void onDetach();

    boolean onTouchEvent(MotionEvent motionEvent);

    void setHierarchy(@Nullable b bVar);
}
