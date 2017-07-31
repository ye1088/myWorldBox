package com.huluxia.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import com.huluxia.widget.banner.SimpleImageAdapter.a;
import java.util.List;

public class SimpleImageGallery extends AdGallery {
    private SimpleImageAdapter bvy;

    public SimpleImageGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bvy = new SimpleImageAdapter(context);
    }

    public SimpleImageGallery(Context context) {
        super(context);
        this.bvy = new SimpleImageAdapter(context);
    }

    public SimpleImageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bvy = new SimpleImageAdapter(context);
    }

    public void setData(List<a> data) {
        NY();
        this.bvy.setData(data);
        this.bvy.notifyDataSetChanged();
        setAdapter(this.bvy);
        NX();
    }

    public void K(List<a> data) {
        NY();
        this.bvy.K(data);
        setAdapter(this.bvy);
        NX();
    }

    public void a(a data) {
    }

    public List getData() {
        return this.bvy.getData();
    }

    public a lN(int position) {
        List<a> infos = getData();
        if (infos.size() <= 0) {
            return null;
        }
        return (a) infos.get(position % infos.size());
    }

    public void setLoader(a loader) {
        this.bvy.setLoader(loader);
    }

    public void setInterval(int interval) {
        this.bvl = interval;
    }
}
