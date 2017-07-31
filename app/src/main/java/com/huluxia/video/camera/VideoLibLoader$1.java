package com.huluxia.video.camera;

import com.huluxia.video.camera.VideoLibLoader.a;

class VideoLibLoader$1 implements Runnable {
    final /* synthetic */ String boO;
    final /* synthetic */ a boP;
    final /* synthetic */ VideoLibLoader boQ;
    final /* synthetic */ String val$url;

    VideoLibLoader$1(VideoLibLoader this$0, String str, String str2, a aVar) {
        this.boQ = this$0;
        this.val$url = str;
        this.boO = str2;
        this.boP = aVar;
    }

    public void run() {
        VideoLibLoader.a(this.boQ, this.val$url, this.boO, this.boP);
    }
}
