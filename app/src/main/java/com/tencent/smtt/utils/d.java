package com.tencent.smtt.utils;

import android.widget.Toast;

class d implements Runnable {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public void run() {
        Toast.makeText(this.a.b, "下载成功", 0).show();
        this.a.c.setVisibility(4);
        this.a.f.b(this.a.d, this.a.a, this.a.b);
    }
}
