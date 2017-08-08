package com.MCWorld.framework.base.widget.hlistview.util.v16;

import android.annotation.TargetApi;
import android.view.View;
import com.MCWorld.framework.base.widget.hlistview.util.v14.ViewHelper14;

public class ViewHelper16 extends ViewHelper14 {
    public ViewHelper16(View view) {
        super(view);
    }

    @TargetApi(16)
    public void postOnAnimation(Runnable action) {
        this.view.postOnAnimation(action);
    }
}
