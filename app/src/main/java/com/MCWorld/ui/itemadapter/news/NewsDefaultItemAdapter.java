package com.MCWorld.ui.itemadapter.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.l;
import com.MCWorld.module.news.c;
import com.MCWorld.t;
import com.MCWorld.utils.az;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class NewsDefaultItemAdapter extends NewsListAdapter {
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_TEXT = 2;
    private LayoutInflater mInflater = null;

    private class a {
        public View aFF;
        public PaintView aRg;
        public TextView aRh;
        final /* synthetic */ NewsDefaultItemAdapter aUD;
        public TextView aUE;
        public ImageView aUF;
        public TextView aUG;
        public TextView aUH;
        public View aUI;

        private a(NewsDefaultItemAdapter newsDefaultItemAdapter) {
            this.aUD = newsDefaultItemAdapter;
        }
    }

    private class b {
        public View aFF;
        public TextView aRh;
        final /* synthetic */ NewsDefaultItemAdapter aUD;
        public TextView aUG;
        public TextView aUH;
        public View aUI;

        private b(NewsDefaultItemAdapter newsDefaultItemAdapter) {
            this.aUD = newsDefaultItemAdapter;
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return kK(i);
    }

    public NewsDefaultItemAdapter(Context context, ArrayList<c> data) {
        this.aab = data;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemViewType(int position) {
        if (UtilsFunction.empty(kK(position).covers)) {
            return 2;
        }
        return 1;
    }

    public int getViewTypeCount() {
        return 3;
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public c kK(int position) {
        return (c) this.aab.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        c item = kK(position);
        if (type == 1) {
            a holder;
            if (convertView == null) {
                holder = new a();
                convertView = this.mInflater.inflate(i.item_news_small_img, null);
                holder.aRg = (PaintView) convertView.findViewById(g.video_img);
                holder.aUE = (TextView) convertView.findViewById(g.img_counts);
                holder.aUF = (ImageView) convertView.findViewById(g.iv_video_tag);
                holder.aRh = (TextView) convertView.findViewById(g.title);
                holder.aUG = (TextView) convertView.findViewById(g.comment_counts);
                holder.aUH = (TextView) convertView.findViewById(g.timing);
                holder.aUI = convertView.findViewById(g.split_item);
                holder.aFF = convertView.findViewById(g.root_container);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            a(holder, item);
        } else {
            b holder2;
            if (convertView == null) {
                holder2 = new b();
                convertView = this.mInflater.inflate(i.item_news_no_img, null);
                holder2.aRh = (TextView) convertView.findViewById(g.title);
                holder2.aUG = (TextView) convertView.findViewById(g.comment_counts);
                holder2.aUH = (TextView) convertView.findViewById(g.timing);
                holder2.aUI = convertView.findViewById(g.split_item);
                holder2.aFF = convertView.findViewById(g.root_container);
                convertView.setTag(holder2);
            } else {
                holder2 = (b) convertView.getTag();
            }
            a(holder2, item);
        }
        return convertView;
    }

    private void a(b holder, final c item) {
        if (item != null) {
            holder.aRh.setText(item.title);
            holder.aUH.setText(az.bD(item.publishTime));
            holder.aUG.setText(String.format("%d评论", new Object[]{Integer.valueOf(item.cmtCount)}));
            holder.aFF.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ NewsDefaultItemAdapter aUD;

                public void onClick(View v) {
                    t.a(v.getContext(), item);
                }
            });
        }
    }

    private void a(a holder, final c item) {
        if (item != null) {
            if (!UtilsFunction.empty(item.covers)) {
                a(holder.aRg, (String) item.covers.get(0));
                if (item.imgCount > 1) {
                    holder.aUE.setVisibility(0);
                    holder.aUE.setText(String.format("%d图", new Object[]{Integer.valueOf(item.imgCount)}));
                } else {
                    holder.aUE.setVisibility(8);
                }
            }
            holder.aUF.setVisibility(8);
            holder.aRh.setText(item.title);
            holder.aUH.setText(az.bD(item.publishTime));
            holder.aUG.setText(String.format("%d评论", new Object[]{Integer.valueOf(item.cmtCount)}));
            holder.aFF.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ NewsDefaultItemAdapter aUD;

                public void onClick(View v) {
                    t.a(v.getContext(), item);
                }
            });
        }
    }

    private void a(PaintView imageView, String url) {
        imageView.setUri(UtilUri.getUriOrNull(url)).scaleType(ScaleType.CENTER_CROP).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).errorHolder(d.isDayMode() ? f.err_holder_normal : f.err_holder_night_normal).fadeDuration(150).setImageLoader(l.cb().getImageLoader());
    }

    public void a(j setter) {
    }
}
