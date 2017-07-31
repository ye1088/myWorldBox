package com.huluxia.widget.x5web;

import com.huluxia.widget.x5web.BrowserActivity.9;

class BrowserActivity$9$1 implements Runnable {
    final /* synthetic */ 9 bHh;

    BrowserActivity$9$1(9 this$1) {
        this.bHh = this$1;
    }

    public void run() {
        try {
            BrowserActivity.d(this.bHh.bHg).destroy();
            BrowserActivity.a(this.bHh.bHg, null);
        } catch (Exception e) {
        }
    }
}
