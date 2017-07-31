package com.huluxia.image.drawee.controller;

import android.graphics.drawable.Animatable;
import javax.annotation.Nullable;

class AbstractDraweeControllerBuilder$1 extends b<Object> {
    AbstractDraweeControllerBuilder$1() {
    }

    public void a(String id, @Nullable Object info, @Nullable Animatable anim) {
        if (anim != null) {
            anim.start();
        }
    }
}
