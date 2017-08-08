package com.MCWorld.widget;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class ImageSwitch$1 implements OnTouchListener {
    final /* synthetic */ ImageSwitch btP;

    ImageSwitch$1(ImageSwitch this$0) {
        this.btP = this$0;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                ImageSwitch.a(this.btP, !ImageSwitch.a(this.btP));
                break;
        }
        return false;
    }
}
