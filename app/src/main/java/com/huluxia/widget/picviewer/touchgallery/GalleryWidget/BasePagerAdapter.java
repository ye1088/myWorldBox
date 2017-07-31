package com.huluxia.widget.picviewer.touchgallery.GalleryWidget;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class BasePagerAdapter<T> extends PagerAdapter {
    protected final List<T> bDb;
    protected a bDc;
    protected final Context mContext;
    protected int mCurrentPosition = -1;

    public BasePagerAdapter(Context context, List<T> resources) {
        this.bDb = resources;
        this.mContext = context;
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (this.mCurrentPosition != position) {
            GalleryViewPager galleryContainer = (GalleryViewPager) container;
            if (galleryContainer.bDe != null) {
                galleryContainer.bDe.PJ();
            }
            this.mCurrentPosition = position;
            if (this.bDc != null) {
                this.bDc.mm(this.mCurrentPosition);
            }
        }
    }

    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public int getCount() {
        return this.bDb.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void finishUpdate(ViewGroup arg0) {
    }

    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    public Parcelable saveState() {
        return null;
    }

    public void startUpdate(ViewGroup arg0) {
    }

    public int getCurrentPosition() {
        return this.mCurrentPosition;
    }

    public void a(a listener) {
        this.bDc = listener;
    }
}
