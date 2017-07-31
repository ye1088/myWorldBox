package com.huluxia.ui.itemadapter.picture;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.y;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.huluxia.bbs.b.e;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.l;
import com.huluxia.module.picture.b;
import com.huluxia.ui.picture.PictureChooserFragment;
import com.simple.colorful.d;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends BaseAdapter {
    private static final int aUX = 0;
    private static final int aUY = 1;
    private int HF;
    private boolean aUZ = false;
    private boolean aVa;
    private final int aVb;
    private final ArrayList<b> aVc;
    private PictureChooserFragment.b aVd;
    private List<b> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    static class a {
        public PaintView aOJ;
        public ImageView aVe;

        a() {
        }
    }

    public PictureAdapter(Context context, boolean showCamera, int maxSelection, @y ArrayList<b> currentSelection) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.aUZ = showCamera;
        this.aVb = maxSelection;
        this.aVc = currentSelection;
    }

    public int getItemViewType(int position) {
        if (this.aUZ && position == 0) {
            return 0;
        }
        return 1;
    }

    public int getViewTypeCount() {
        return this.aUZ ? 2 : 1;
    }

    public int getCount() {
        int count = 0;
        if (this.aab != null) {
            count = this.aab.size();
        }
        if (this.aUZ) {
            return count + 1;
        }
        return count;
    }

    public Object getItem(int position) {
        return this.aab.get(position - (this.aUZ ? 1 : 0));
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (!this.aUZ || position != 0) {
            a holder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_pic_grid, parent, false);
                holder = new a();
                holder.aOJ = (PaintView) convertView.findViewById(g.image);
                holder.aVe = (ImageView) convertView.findViewById(g.check_image);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            b unit = (b) getItem(position);
            File imageFile = new File(unit.localPath);
            if (this.HF > 0) {
                holder.aOJ.placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).scaleType(ScaleType.CENTER_CROP).resize(this.HF, this.HF).radiusDimen(e.indicator_internal_padding).setUri(Uri.fromFile(imageFile)).tag(this.mContext).setImageLoader(l.cb().getImageLoader());
            }
            if (!this.aVa) {
                holder.aVe.setVisibility(8);
            } else if (this.aVc.contains(unit)) {
                holder.aVe.setImageResource(f.image_checkbox_1);
            } else {
                holder.aVe.setImageResource(f.image_checkbox_3);
            }
        } else if (convertView == null) {
            convertView = this.mInflater.inflate(i.item_grid_camera, parent, false);
        }
        LayoutParams lp = (LayoutParams) convertView.getLayoutParams();
        if (lp.width != this.HF) {
            lp.width = this.HF;
            lp.height = this.HF;
        }
        return convertView;
    }

    public void kL(int mItemSize) {
        this.HF = mItemSize;
        notifyDataSetChanged();
    }

    public void c(List<b> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void cL(boolean showCheckIndicator) {
        this.aVa = showCheckIndicator;
    }

    public void a(PictureChooserFragment.b listener) {
        this.aVd = listener;
    }

    public void kM(int position) {
        if (!this.aUZ || position != 0) {
            b unit = (b) getItem(position);
            if (this.aVc.remove(unit)) {
                if (this.aVd != null) {
                    this.aVd.c(unit);
                }
                notifyDataSetChanged();
            } else if (this.aVc.size() < this.aVb) {
                this.aVc.add(unit);
                if (this.aVd != null) {
                    this.aVd.b(unit);
                }
                notifyDataSetChanged();
            } else if (this.aVd != null) {
                this.aVd.kX(this.aVb);
            }
        }
    }

    public void a(b unit) {
        this.aab.add(0, unit);
        notifyDataSetChanged();
    }
}
