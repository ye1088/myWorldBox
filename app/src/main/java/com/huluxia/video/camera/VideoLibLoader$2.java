package com.huluxia.video.camera;

import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.video.camera.VideoLibLoader.a;

class VideoLibLoader$2 implements ErrorListener {
    final /* synthetic */ a boP;
    final /* synthetic */ VideoLibLoader boQ;
    final /* synthetic */ String val$url;

    VideoLibLoader$2(VideoLibLoader this$0, String str, a aVar) {
        this.boQ = this$0;
        this.val$url = str;
        this.boP = aVar;
    }

    public void onErrorResponse(VolleyError error) {
        HLog.error("VideoLibLoader", "download so zip failed, error " + error, new Object[0]);
        DownloadMemCache.getInstance().deleteRecord(this.val$url);
        VideoLibLoader.a(this.boQ, false, this.boP);
    }
}
