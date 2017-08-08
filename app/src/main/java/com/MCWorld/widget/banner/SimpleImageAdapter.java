package com.MCWorld.widget.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery.LayoutParams;
import com.MCWorld.framework.base.image.PaintView;

public class SimpleImageAdapter<T> extends AdAdapter<T> {
    public a bvv;

    public interface a {
        void a(String str, PaintView paintView);
    }

    public SimpleImageAdapter(Context context) {
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b viewHolder;
        if (convertView == null) {
            convertView = new PaintView(this.mContext);
            convertView.setLayoutParams(new LayoutParams(-1, -1));
            viewHolder = new b(this);
            viewHolder.bvw = (PaintView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (b) convertView.getTag();
        }
        Object o = getItem(position);
        if (!(o == null || !(o instanceof a) || this.bvv == null)) {
            this.bvv.a(((a) o).url, viewHolder.bvw);
        }
        return convertView;
    }

    public void setLoader(a loader) {
        this.bvv = loader;
    }
}
