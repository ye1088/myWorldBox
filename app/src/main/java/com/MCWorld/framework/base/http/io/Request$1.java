package com.MCWorld.framework.base.http.io;

class Request$1 implements Runnable {
    final /* synthetic */ Request this$0;
    final /* synthetic */ String val$tag;
    final /* synthetic */ long val$threadId;

    Request$1(Request this$0, String str, long j) {
        this.this$0 = this$0;
        this.val$tag = str;
        this.val$threadId = j;
    }

    public void run() {
        Request.access$000(this.this$0).add(this.val$tag, this.val$threadId);
        Request.access$000(this.this$0).finish("Thread-" + String.valueOf(this.val$threadId) + " " + this.this$0.toString());
    }
}
