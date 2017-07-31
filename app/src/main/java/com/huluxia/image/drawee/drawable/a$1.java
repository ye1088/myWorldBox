package com.huluxia.image.drawee.drawable;

import android.graphics.drawable.Drawable;

/* compiled from: ArrayDrawable */
class a$1 implements c {
    final /* synthetic */ a Bj;
    final /* synthetic */ int val$index;

    a$1(a this$0, int i) {
        this.Bj = this$0;
        this.val$index = i;
    }

    public Drawable d(Drawable newDrawable) {
        return this.Bj.a(this.val$index, newDrawable);
    }

    public Drawable getDrawable() {
        return this.Bj.getDrawable(this.val$index);
    }
}
