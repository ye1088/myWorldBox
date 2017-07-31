package com.tencent.smtt.utils;

import android.widget.Toast;

class f implements Runnable {
    final /* synthetic */ c a;

    f(c cVar) {
        this.a = cVar;
    }

    public void run() {
        Toast.makeText(this.a.b, "下载失败，请检查网络", 0).show();
    }
}
