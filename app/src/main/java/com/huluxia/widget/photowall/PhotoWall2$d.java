package com.huluxia.widget.photowall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.module.picture.b;
import java.io.File;
import java.util.List;

class PhotoWall2$d extends BaseAdapter {
    final /* synthetic */ PhotoWall2 bCP;
    private Context context;
    private final List<b> data;

    public PhotoWall2$d(PhotoWall2 photoWall2, Context context, List<b> data) {
        this.bCP = photoWall2;
        this.context = context;
        this.data = data;
    }

    public int getCount() {
        return (PhotoWall2.a(this.bCP) ? 1 : 0) + this.data.size();
    }

    public int getViewTypeCount() {
        if (PhotoWall2.a(this.bCP)) {
            return 2;
        }
        return 1;
    }

    public int getItemViewType(int position) {
        if (PhotoWall2.a(this.bCP) && position == 0) {
            return 1;
        }
        return 0;
    }

    public Object getItem(int position) {
        if (PhotoWall2.a(this.bCP) && position == 0) {
            return null;
        }
        return this.data.get(position - (PhotoWall2.a(this.bCP) ? 1 : 0));
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 1) {
            View imageView = (ImageView) LayoutInflater.from(this.context).inflate(i.item_photo_grid_add, parent, false);
            imageView.setImageResource(f.sl_photo_add);
            imageView.setScaleType(ScaleType.FIT_XY);
            convertView = imageView;
        } else {
            PhotoWall2$f holder;
            if (convertView == null) {
                holder = new PhotoWall2$f();
                convertView = LayoutInflater.from(this.context).inflate(i.item_photo_grid, parent, false);
                holder.bCQ = (PaintView) convertView.findViewById(g.image);
                convertView.setTag(holder);
            } else {
                holder = (PhotoWall2$f) convertView.getTag();
            }
            b unit = (b) getItem(position);
            if (UtilsFile.isExist(unit.localPath)) {
                PhotoWall2.a(this.bCP, new File(unit.localPath), holder.bCQ);
            } else if (!UtilsFunction.empty(unit.url)) {
                PhotoWall2.a(this.bCP, unit.url, holder.bCQ);
            }
        }
        LayoutParams lp = (LayoutParams) convertView.getLayoutParams();
        lp.width = PhotoWall2.f(this.bCP);
        lp.height = PhotoWall2.h(this.bCP);
        convertView.setLayoutParams(lp);
        return convertView;
    }
}
