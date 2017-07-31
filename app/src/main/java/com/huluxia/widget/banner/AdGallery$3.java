package com.huluxia.widget.banner;

import android.os.Handler;
import android.os.Message;

class AdGallery$3 extends Handler {
    final /* synthetic */ AdGallery bvq;

    AdGallery$3(AdGallery this$0) {
        this.bvq = this$0;
    }

    public void handleMessage(Message msg) {
        if (msg.what == 1 && AdGallery.b(this.bvq)) {
            if (this.bvq.getSelectedItemPosition() >= this.bvq.getCount() - 1) {
                this.bvq.setSelection(0, true);
                this.bvq.onKeyDown(21, null);
            } else {
                this.bvq.onKeyDown(22, null);
            }
            sendMessageDelayed(obtainMessage(1), (long) this.bvq.bvl);
        }
    }
}
