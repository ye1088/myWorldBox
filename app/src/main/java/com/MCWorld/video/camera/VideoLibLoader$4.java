package com.MCWorld.video.camera;

import com.MCWorld.video.camera.VideoLibLoader.a;

class VideoLibLoader$4 implements Runnable {
    final /* synthetic */ a boP;
    final /* synthetic */ VideoLibLoader boQ;
    final /* synthetic */ boolean boS;

    VideoLibLoader$4(VideoLibLoader this$0, a aVar, boolean z) {
        this.boQ = this$0;
        this.boP = aVar;
        this.boS = z;
    }

    public void run() {
        this.boP.cw(this.boS);
    }
}
