package com.MCWorld.ui.picture;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.l;
import com.MCWorld.module.picture.PictureInfo;
import com.MCWorld.module.picture.b;
import com.MCWorld.utils.at;
import java.io.File;

class PictureChooserFragment$a extends BaseAdapter {
    final /* synthetic */ PictureChooserFragment bea;
    private int bec;
    private Context mContext;

    class a {
        PaintView bed;
        ImageView bee;
        final /* synthetic */ PictureChooserFragment$a bef;
        TextView name;

        a(PictureChooserFragment$a this$1) {
            this.bef = this$1;
        }
    }

    public PictureChooserFragment$a(PictureChooserFragment pictureChooserFragment, Context context) {
        this.bea = pictureChooserFragment;
        this.mContext = context;
        this.bec = at.dipToPx(context, 36);
    }

    public int getCount() {
        return PictureInfo.getInstance().getBucketCount();
    }

    public Object getItem(int position) {
        if (position == 0) {
            return null;
        }
        return PictureInfo.getInstance().getBucketAt(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        String path;
        if (convertView == null) {
            holder = new a(this);
            convertView = LayoutInflater.from(this.mContext).inflate(i.item_pic_bucket, parent, false);
            holder.bed = (PaintView) convertView.findViewById(g.icon);
            holder.name = (TextView) convertView.findViewById(g.text);
            holder.bee = (ImageView) convertView.findViewById(g.iv_check);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        if (position == 0) {
            path = PictureInfo.getInstance().getPicture(0).localPath;
            holder.name.setText(this.mContext.getString(m.bucket_name_all, new Object[]{Integer.valueOf(PictureInfo.getInstance().getSize())}));
            if (-1 == PictureChooserFragment.l(this.bea)) {
                holder.bee.setVisibility(0);
            } else {
                holder.bee.setVisibility(8);
            }
        } else {
            com.MCWorld.module.picture.a bucket = (com.MCWorld.module.picture.a) getItem(position);
            path = ((b) bucket.pictures.get(0)).localPath;
            holder.name.setText(this.mContext.getString(m.bucket_name, new Object[]{bucket.bucketName, Integer.valueOf(bucket.getBucketSize())}));
            if (bucket.bucketId == PictureChooserFragment.l(this.bea)) {
                holder.bee.setVisibility(0);
            } else {
                holder.bee.setVisibility(8);
            }
        }
        holder.bed.resize(this.bec, this.bec).scaleType(ScaleType.CENTER_CROP).tag(this.mContext).setUri(Uri.fromFile(new File(path))).setImageLoader(l.cb().getImageLoader());
        return convertView;
    }
}
