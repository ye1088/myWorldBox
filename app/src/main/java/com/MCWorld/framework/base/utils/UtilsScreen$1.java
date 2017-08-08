package com.MCWorld.framework.base.utils;

import android.view.View;

class UtilsScreen$1 implements Runnable {
    final /* synthetic */ View val$view;

    UtilsScreen$1(View view) {
        this.val$view = view;
    }

    public void run() {
        UtilsScreen.showInputMethod(this.val$view);
    }
}
