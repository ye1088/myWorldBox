package com.MCWorld.utils;

import android.view.View;

/* compiled from: UtilsScreen */
class at$1 implements Runnable {
    final /* synthetic */ View val$view;

    at$1(View view) {
        this.val$view = view;
    }

    public void run() {
        at.showInputMethod(this.val$view);
    }
}
