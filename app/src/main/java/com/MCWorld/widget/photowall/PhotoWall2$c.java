package com.MCWorld.widget.photowall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.module.picture.b;
import java.io.File;
import java.util.List;

class PhotoWall2$c extends BaseAdapter {
    final /* synthetic */ PhotoWall2 bCP;
    private Context context;
    private final List<b> data;

    public PhotoWall2$c(PhotoWall2 photoWall2, Context context, List<b> data) {
        this.bCP = photoWall2;
        this.context = context;
        this.data = data;
    }

    public int getItemViewType(int position) {
        if (PhotoWall2.a(this.bCP) && position == getCount() - 1) {
            return 1;
        }
        return 0;
    }

    public int getViewTypeCount() {
        if (PhotoWall2.a(this.bCP)) {
            return 2;
        }
        return 1;
    }

    public int getCount() {
        int i = 0;
        int dataSize = this.data == null ? 0 : this.data.size();
        if (PhotoWall2.a(this.bCP)) {
            i = 1;
        }
        return i + dataSize;
    }

    public Object getItem(int position) {
        if (PhotoWall2.a(this.bCP) && position == getCount() - 1) {
            return null;
        }
        return this.data.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutParams lp;
        if (getItemViewType(position) != 1) {
            PhotoWall2$f holder;
            if (convertView == null) {
                holder = new PhotoWall2$f();
                convertView = LayoutInflater.from(this.context).inflate(i.item_photo_list, parent, false);
                holder.bCQ = (PaintView) convertView.findViewById(g.image);
                holder.bCS = (ImageView) convertView.findViewById(g.ic_tag_cover);
                holder.bCR = convertView.findViewById(g.btn_delete);
                convertView.setTag(holder);
                lp = (LayoutParams) holder.bCQ.getLayoutParams();
                lp.width = PhotoWall2.f(this.bCP);
                lp.height = PhotoWall2.h(this.bCP);
                holder.bCQ.setLayoutParams(lp);
            } else {
                holder = (PhotoWall2$f) convertView.getTag();
            }
            b unit = (b) getItem(position);
            if (UtilsFile.isExist(unit.localPath)) {
                PhotoWall2.a(this.bCP, new File(unit.localPath), holder.bCQ);
            } else if (!UtilsFunction.empty(unit.url)) {
                PhotoWall2.a(this.bCP, unit.url, holder.bCQ);
            }
            if (PhotoWall2.i(this.bCP)) {
                holder.bCR.setVisibility(0);
                holder.bCR.setTag(Integer.valueOf(position));
                holder.bCR.setOnClickListener(PhotoWall2.j(this.bCP));
            } else {
                holder.bCR.setVisibility(8);
            }
            if (PhotoWall2.k(this.bCP) && position == 0) {
                holder.bCS.setVisibility(0);
                return convertView;
            }
            holder.bCS.setVisibility(8);
            return convertView;
        } else if (convertView != null) {
            return convertView;
        } else {
            convertView = LayoutInflater.from(PhotoWall2.e(this.bCP)).inflate(i.footer_photo_add, parent, false);
            View imageAdd = convertView.findViewById(g.image_add);
            lp = (LayoutParams) imageAdd.getLayoutParams();
            lp.width = PhotoWall2.f(this.bCP);
            lp.height = PhotoWall2.h(this.bCP);
            imageAdd.setLayoutParams(lp);
            return convertView;
        }
    }
}
