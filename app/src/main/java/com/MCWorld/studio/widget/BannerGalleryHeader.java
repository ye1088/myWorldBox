package com.MCWorld.studio.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.MCWorld.framework.R;
import com.MCWorld.widget.banner.BannerGallery;

public class BannerGalleryHeader extends LinearLayout {
    protected BannerGallery aGX;
    private Context mContext;

    public BannerGalleryHeader(Context context) {
        super(context);
        this.mContext = context;
        setOrientation(1);
        LayoutInflater.from(context).inflate(R.layout.header_view_banner_gallery, this);
        initView();
    }

    public BannerGalleryHeader(Context context, AttributeSet attrs) {
        this(context);
    }

    private void initView() {
        this.aGX = (BannerGallery) findViewById(R.id.banner_gallery);
    }

    public BannerGallery getBannerGallery() {
        return this.aGX;
    }
}
