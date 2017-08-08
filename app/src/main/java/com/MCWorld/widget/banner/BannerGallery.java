package com.MCWorld.widget.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import java.util.ArrayList;
import java.util.List;

public class BannerGallery extends RelativeLayout {
    private List<a> aab = new ArrayList();
    private SimpleImageGallery bvr;
    private RadioGroup bvs;
    private OnItemSelectedListener bvt = new 1(this);

    public BannerGallery(Context context) {
        super(context);
        init();
    }

    public BannerGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(i.layout_banner_gallery, this, true);
        this.bvr = (SimpleImageGallery) findViewById(g.ad_gallery);
        this.bvs = (RadioGroup) findViewById(g.ad_indicator);
        this.bvr.setOnItemSelectedListener(this.bvt);
    }

    public void setData(List<a> data) {
        if (!this.aab.equals(data) && data != null) {
            this.aab.clear();
            this.aab.addAll(data);
            this.bvr.setData(data);
            L(data);
            this.bvs.check(this.bvs.getChildAt(0).getId());
        }
    }

    public void K(List<a> data) {
        if (data != null) {
            this.aab.addAll(data);
            this.bvr.K(data);
            L(this.bvr.getData());
        }
    }

    private void L(List data) {
        this.bvs.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            RadioButton _rb = new RadioButton(getContext());
            _rb.setPadding(5, 5, 5, 5);
            _rb.setId(i + 4660);
            _rb.setButtonDrawable(getContext().getResources().getDrawable(f.gallery_selector));
            this.bvs.addView(_rb, Oa());
        }
    }

    private LayoutParams Oa() {
        Bitmap b = BitmapFactory.decodeResource(getContext().getResources(), f.gallery_dot_1);
        return new LayoutParams(b.getWidth(), b.getHeight());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.bvr.setOnItemClickListener(listener);
    }

    public void NX() {
        this.bvr.NX();
    }

    public void NY() {
        this.bvr.NY();
    }

    public SimpleImageGallery getGallery() {
        return this.bvr;
    }

    public void setIndicatorVisible(boolean visible) {
        if (this.bvs != null) {
            if (visible) {
                this.bvs.setVisibility(0);
            } else {
                this.bvs.setVisibility(8);
            }
        }
    }

    public void FG() {
        this.bvr.setData(this.aab);
    }
}
