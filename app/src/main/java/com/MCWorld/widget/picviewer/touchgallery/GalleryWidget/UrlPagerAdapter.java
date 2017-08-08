package com.MCWorld.widget.picviewer.touchgallery.GalleryWidget;

import android.content.Context;
import android.util.Pair;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.widget.picviewer.touchgallery.TouchView.UrlTouchImageView;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UrlPagerAdapter extends BasePagerAdapter<Pair<String, String>> {
    private OnClickListener bDj;
    private Context mContext;
    private int mOrientation = 1;

    public UrlPagerAdapter(Context context, List<String> resources) {
        super(context, M(resources));
        this.mContext = context;
        this.bDj = null;
    }

    public UrlPagerAdapter(Context context, List<Pair<String, String>> resources, int orientation, OnClickListener onClickListener) {
        super(context, resources);
        this.mContext = context;
        this.bDj = onClickListener;
        this.mOrientation = orientation;
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ((GalleryViewPager) container).bDe = ((UrlTouchImageView) object).getImageView();
    }

    public Object instantiateItem(ViewGroup collection, int position) {
        UrlTouchImageView iv = new UrlTouchImageView(this.mContext, this.bDj);
        iv.setProgressTextColor(d.getColor(this.mContext, 16842809));
        Pair<String, String> urls = (Pair) this.bDb.get(position);
        iv.e((String) urls.first, (String) urls.second, this.mOrientation);
        iv.setLayoutParams(new LayoutParams(-1, -1));
        collection.addView(iv, 0);
        return iv;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public static List<Pair<String, String>> M(List<String> resources) {
        List<Pair<String, String>> pairs = new ArrayList();
        if (UtilsFunction.notEmpty((Collection) resources)) {
            for (CharSequence url : resources) {
                String thumb;
                if (UtilsFunction.notEmpty(url)) {
                    thumb = String.format("%s_160x160.jpeg", new Object[]{url});
                } else {
                    thumb = "";
                }
                pairs.add(new Pair(url, thumb));
            }
        }
        return pairs;
    }
}
