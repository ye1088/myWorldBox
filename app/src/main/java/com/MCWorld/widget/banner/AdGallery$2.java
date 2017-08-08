package com.MCWorld.widget.banner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class AdGallery$2 extends BroadcastReceiver {
    final /* synthetic */ AdGallery bvq;

    AdGallery$2(AdGallery this$0) {
        this.bvq = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_OFF".equals(action)) {
            AdGallery.a(this.bvq, false);
            AdGallery.a(this.bvq);
        } else if ("android.intent.action.USER_PRESENT".equals(action)) {
            AdGallery.a(this.bvq, true);
            AdGallery.b(this.bvq, false);
        }
    }
}
