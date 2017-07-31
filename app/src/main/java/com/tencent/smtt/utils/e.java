package com.tencent.smtt.utils;

class e implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ c b;

    e(c cVar, int i) {
        this.b = cVar;
        this.a = i;
    }

    public void run() {
        this.b.e.setText("已下载" + this.a + "%");
    }
}
