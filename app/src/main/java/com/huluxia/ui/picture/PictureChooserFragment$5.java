package com.huluxia.ui.picture;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class PictureChooserFragment$5 implements OnTouchListener {
    final /* synthetic */ PictureChooserFragment bea;

    PictureChooserFragment$5(PictureChooserFragment this$0) {
        this.bea = this$0;
    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        if (action == 0) {
            if (PictureChooserFragment.h(this.bea).isShowing()) {
                return false;
            }
            return true;
        } else if (action != 1 || PictureChooserFragment.h(this.bea).isShowing()) {
            return true;
        } else {
            PictureChooserFragment.h(this.bea).showAsDropDown(PictureChooserFragment.i(this.bea));
            return true;
        }
    }
}
