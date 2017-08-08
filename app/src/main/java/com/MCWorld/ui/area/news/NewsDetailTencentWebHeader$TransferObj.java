package com.MCWorld.ui.area.news;

import android.webkit.JavascriptInterface;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.utils.DoNotStrip;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

@DoNotStrip
class NewsDetailTencentWebHeader$TransferObj {
    private HashMap<String, String> aHM;
    private HashMap<String, String> aHN;
    final /* synthetic */ NewsDetailTencentWebHeader aHU;

    @DoNotStrip
    NewsDetailTencentWebHeader$TransferObj(NewsDetailTencentWebHeader this$0, HashMap<String, String> params, HashMap<String, String> extras) {
        this.aHU = this$0;
        this.aHM = params;
        this.aHN = extras;
    }

    @JavascriptInterface
    @DoNotStrip
    public String getExtras() {
        if (this.aHN == null) {
            this.aHN = new HashMap();
        }
        try {
            return Json.toJson(this.aHN);
        } catch (Exception e) {
            return "{}";
        }
    }

    @JavascriptInterface
    @DoNotStrip
    public String getQueryStr() {
        if (this.aHM == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> e : this.aHM.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            try {
                sb.append(URLEncoder.encode((String) e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode((String) e.getValue(), "UTF-8"));
            } catch (Exception e2) {
            }
        }
        return sb.toString();
    }

    @JavascriptInterface
    @DoNotStrip
    public void openImage(String currImg, String imgList) {
        NewsDetailTencentWebHeader.b(this.aHU, currImg);
    }

    @JavascriptInterface
    @DoNotStrip
    public void downloadImage(String img) {
        NewsDetailTencentWebHeader.b(this.aHU, img);
    }

    @JavascriptInterface
    @DoNotStrip
    public void contentHeight(String contentHeight) {
        this.aHU.post(new 1(this, contentHeight));
    }
}
