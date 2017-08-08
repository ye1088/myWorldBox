package com.MCWorld.ui.area.news;

import com.MCWorld.ui.area.news.NewsDetailTencentWebHeader.TransferObj;

class NewsDetailTencentWebHeader$TransferObj$1 implements Runnable {
    final /* synthetic */ String aHO;
    final /* synthetic */ TransferObj aHV;

    NewsDetailTencentWebHeader$TransferObj$1(TransferObj this$1, String str) {
        this.aHV = this$1;
        this.aHO = str;
    }

    public void run() {
        this.aHV.aHU.aHG = Integer.parseInt(this.aHO);
        this.aHV.aHU.Fn();
    }
}
