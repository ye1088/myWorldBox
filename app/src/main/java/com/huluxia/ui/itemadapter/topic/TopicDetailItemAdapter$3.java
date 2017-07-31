package com.huluxia.ui.itemadapter.topic;

import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import java.io.File;

class TopicDetailItemAdapter$3 implements Listener<String> {
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$3(TopicDetailItemAdapter this$0) {
        this.aXA = this$0;
    }

    public void onResponse(String response) {
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(TopicDetailItemAdapter.i(this.aXA).videourl);
        if (record != null) {
            File videoFile = new File(record.dir, record.name);
            if (videoFile != null && videoFile.exists()) {
                TopicDetailItemAdapter.a(this.aXA, videoFile.getAbsolutePath());
                this.aXA.HK();
            }
        }
    }
}
