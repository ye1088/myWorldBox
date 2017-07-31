package com.huluxia.video.camera;

import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.video.camera.VideoLibLoader.a;
import java.io.IOException;

class VideoLibLoader$3 implements Listener<String> {
    final /* synthetic */ String boO;
    final /* synthetic */ a boP;
    final /* synthetic */ VideoLibLoader boQ;
    final /* synthetic */ String val$url;

    VideoLibLoader$3(VideoLibLoader this$0, String str, String str2, a aVar) {
        this.boQ = this$0;
        this.val$url = str;
        this.boO = str2;
        this.boP = aVar;
    }

    public void onResponse(String response) {
        HLog.info("VideoLibLoader", "download so zip succ, response " + response, new Object[0]);
        AsyncTaskCenter.getInstance().executeSingleThread(new Runnable(this) {
            final /* synthetic */ VideoLibLoader$3 boR;

            {
                this.boR = this$1;
            }

            public void run() {
                String fileMd5 = null;
                try {
                    fileMd5 = VideoLibLoader.a(this.boR.boQ, this.boR.val$url);
                } catch (IOException e) {
                    HLog.error("VideoLibLoader", "verify so zip file faild", new Object[0]);
                }
                HLog.info("VideoLibLoader", "download file md5 " + fileMd5 + ", md5 " + this.boR.boO, new Object[0]);
                if (this.boR.boO == null || !this.boR.boO.equalsIgnoreCase(fileMd5)) {
                    DownloadMemCache.getInstance().deleteRecord(this.boR.val$url);
                    VideoLibLoader.a(this.boR.boQ, false, this.boR.boP);
                    return;
                }
                VideoLibLoader.a(this.boR.boQ, this.boR.val$url, this.boR.boO, this.boR.boP);
            }
        });
    }
}
