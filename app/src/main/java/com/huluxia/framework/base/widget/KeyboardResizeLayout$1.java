package com.huluxia.framework.base.widget;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.widget.KeyboardResizeLayout.OnKeyboardShowListener;

class KeyboardResizeLayout$1 implements ResizeRelativeLayout$OnSizeChangedListener {
    final /* synthetic */ KeyboardResizeLayout this$0;

    KeyboardResizeLayout$1(KeyboardResizeLayout this$0) {
        this.this$0 = this$0;
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (h != 0 && oldh != 0) {
            if (oldh - h > 30) {
                HLog.debug(this, "keyboard h %d, old h %d show", new Object[]{Integer.valueOf(h), Integer.valueOf(oldh)});
                for (OnKeyboardShowListener listener : KeyboardResizeLayout.access$000(this.this$0)) {
                    listener.onShow(true);
                }
            } else if (h - oldh > 30) {
                HLog.debug(this, "keyboard h %d, old h %d", new Object[]{Integer.valueOf(h), Integer.valueOf(oldh)});
                for (OnKeyboardShowListener listener2 : KeyboardResizeLayout.access$000(this.this$0)) {
                    listener2.onShow(false);
                }
            }
        }
    }
}
