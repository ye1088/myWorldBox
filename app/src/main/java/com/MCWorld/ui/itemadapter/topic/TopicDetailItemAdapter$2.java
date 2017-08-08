package com.MCWorld.ui.itemadapter.topic;

import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;

class TopicDetailItemAdapter$2 implements ErrorListener {
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$2(TopicDetailItemAdapter this$0) {
        this.aXA = this$0;
    }

    public void onErrorResponse(VolleyError error) {
        DownloadMemCache.getInstance().deleteRecord(TopicDetailItemAdapter.i(this.aXA).videourl);
        this.aXA.HO();
    }
}
