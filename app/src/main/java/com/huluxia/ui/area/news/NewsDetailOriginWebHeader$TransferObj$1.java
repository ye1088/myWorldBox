package com.huluxia.ui.area.news;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.ui.area.news.NewsDetailOriginWebHeader.TransferObj;

class NewsDetailOriginWebHeader$TransferObj$1 implements Runnable {
    final /* synthetic */ String aHO;
    final /* synthetic */ TransferObj aHP;

    NewsDetailOriginWebHeader$TransferObj$1(TransferObj this$1, String str) {
        this.aHP = this$1;
        this.aHO = str;
    }

    public void run() {
        this.aHP.aHK.aHG = Integer.parseInt(this.aHO);
        HLog.debug("NewsDetailOriginWebHeader", "js height " + this.aHP.aHK.aHG + ", scale " + this.aHP.aHK.mScale, new Object[0]);
        this.aHP.aHK.Fn();
    }
}
