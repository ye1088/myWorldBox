package com.MCWorld.ui.picture;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class PictureChooserFragment$3 implements OnGlobalLayoutListener {
    final /* synthetic */ PictureChooserFragment bea;

    PictureChooserFragment$3(PictureChooserFragment this$0) {
        this.bea = this$0;
    }

    @TargetApi(16)
    public void onGlobalLayout() {
        if (VERSION.SDK_INT >= 16) {
            PictureChooserFragment.d(this.bea).kL(PictureChooserFragment.f(this.bea).getColumnWidth());
            PictureChooserFragment.f(this.bea).getViewTreeObserver().removeOnGlobalLayoutListener(this);
            return;
        }
        PictureChooserFragment.d(this.bea).kL(PictureChooserFragment.g(this.bea));
        PictureChooserFragment.f(this.bea).getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }
}
