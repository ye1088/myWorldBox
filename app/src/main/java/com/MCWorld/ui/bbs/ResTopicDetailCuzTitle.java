package com.MCWorld.ui.bbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.map.f.a;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.t;
import com.MCWorld.widget.banner.BannerGallery;
import com.simple.colorful.c;
import java.util.ArrayList;
import java.util.List;

public class ResTopicDetailCuzTitle extends LinearLayout implements c {
    private BannerGallery aGX = ((BannerGallery) findViewById(g.pic_gallery));
    private RelativeLayout aOi = ((RelativeLayout) findViewById(g.rly_gallery));
    private TextView aOj = ((TextView) findViewById(g.map_name));
    private Context mContext;

    public ResTopicDetailCuzTitle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_resource_topic_cuz_title, this);
        this.mContext = context;
    }

    public void setMapGallery(a mapItem) {
        if (mapItem != null) {
            this.aOj.setText(mapItem.name);
            this.aGX.setIndicatorVisible(false);
            this.aGX.getGallery().setLoader(new 1(this));
            this.aGX.getGallery().setInterval(5000);
            this.aGX.setData(c(mapItem));
            this.aGX.setOnItemClickListener(new 2(this, mapItem));
        }
    }

    public void setGalleryVisibility(int visibility) {
        this.aOi.setVisibility(visibility);
    }

    private List<com.MCWorld.widget.banner.a> c(a mapItem) {
        List<com.MCWorld.widget.banner.a> banners = new ArrayList();
        for (CharSequence url : mapItem.resourceList) {
            if (!UtilsFunction.empty(url)) {
                banners.add(new com.MCWorld.module.topic.i(url));
            }
        }
        return banners;
    }

    private void a(a mapItem, int position) {
        ArrayList urls = new ArrayList();
        for (CharSequence url : mapItem.resourceList) {
            if (!UtilsFunction.empty(url)) {
                urls.add(url);
            }
        }
        t.a(this.mContext, urls, position);
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        builder.ba(g.map_name, b.c.text_title_topic_detail);
        return builder;
    }

    public void FG() {
    }
}
