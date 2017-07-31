package com.huluxia.framework.base.utils;

import android.app.Activity;
import android.view.View;

class ImeUtil$1 implements Runnable {
    final /* synthetic */ Activity val$activity;
    final /* synthetic */ View val$view;

    ImeUtil$1(Activity activity, View view) {
        this.val$activity = activity;
        this.val$view = view;
    }

    public void run() {
        ImeUtil.showIME(this.val$activity, this.val$view);
    }
}
