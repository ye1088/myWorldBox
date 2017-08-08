package com.MCWorld.ui.area.news;

import android.content.Context;
import android.widget.RelativeLayout;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.module.news.c;

public class NewsDetailHeader extends RelativeLayout {
    private NewsDetailTencentWebHeader aHC;
    private NewsDetailOriginWebHeader aHD;

    public NewsDetailHeader(Context context) {
        super(context);
        if (UtilsVersion.hasHoneycomb()) {
            this.aHC = new NewsDetailTencentWebHeader(context);
            addView(this.aHC);
            return;
        }
        this.aHD = new NewsDetailOriginWebHeader(context);
        addView(this.aHD);
    }

    public void setNews(c news) {
        if (this.aHC != null) {
            this.aHC.setNews(news);
        } else {
            this.aHD.setNews(news);
        }
    }

    public void recycle() {
        if (this.aHC != null) {
            this.aHC.recycle();
        } else {
            this.aHD.recycle();
        }
    }

    public void pause() {
        if (this.aHC != null) {
            this.aHC.pause();
        } else {
            this.aHD.pause();
        }
    }

    public void resume() {
        if (this.aHC != null) {
            this.aHC.resume();
        } else {
            this.aHD.resume();
        }
    }

    public void refresh() {
        if (this.aHC != null) {
            this.aHC.refresh();
        } else {
            this.aHD.refresh();
        }
    }
}
