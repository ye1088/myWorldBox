package com.MCWorld.ui.itemadapter.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.l;
import com.MCWorld.module.news.c;
import com.MCWorld.module.news.f;
import com.MCWorld.t;
import com.MCWorld.utils.az;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class NewsSimpleItemAdapter extends NewsListAdapter {
    private static final int aUJ = 1;
    private static final int aUK = 2;
    private static final int aUL = 3;
    private Context mContext;
    private LayoutInflater mInflater = null;

    private static class a {
        public View aFF;
        public TextView aRh;
        public TextView aUE;
        public TextView aUG;
        public View aUI;
        public PaintView aUN;
        public View aUO;
        public TextView aUP;

        private a() {
        }
    }

    private static class b {
        public View aFF;
        public TextView aRh;
        public TextView aUG;
        public View aUI;
        public PaintView aUN;
        public TextView aUP;

        private b() {
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return kK(i);
    }

    public NewsSimpleItemAdapter(Context context, ArrayList<c> data) {
        this.aab = data;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemViewType(int position) {
        c item = kK(position);
        if (f.aCb.equals(item.coverType) || f.aBZ.equals(item.coverType) || f.aCa.equals(item.coverType)) {
            return 1;
        }
        if (f.aCd.equals(item.coverType) || f.aCc.equals(item.coverType)) {
            return 2;
        }
        return 3;
    }

    public int getViewTypeCount() {
        return 4;
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
        if (type == 1 || type == 2) {
            a holder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_news_small_img, null);
                holder = new a();
                holder.aUN = (PaintView) convertView.findViewById(g.video_img);
                holder.aUI = convertView.findViewById(g.split_item);
                holder.aFF = convertView.findViewById(g.root_container);
                holder.aUO = convertView.findViewById(g.iv_video_tag);
                holder.aUE = (TextView) convertView.findViewById(g.img_counts);
                holder.aRh = (TextView) convertView.findViewById(g.title);
                holder.aUP = (TextView) convertView.findViewById(g.timing);
                holder.aUG = (TextView) convertView.findViewById(g.comment_counts);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            a(holder, item);
        } else {
            b holder2;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_news_no_img, null);
                holder2 = new b();
                holder2.aRh = (TextView) convertView.findViewById(g.title);
                holder2.aFF = convertView.findViewById(g.root_container);
                holder2.aUP = (TextView) convertView.findViewById(g.timing);
                holder2.aUG = (TextView) convertView.findViewById(g.comment_counts);
                holder2.aUI = convertView.findViewById(g.split_item);
                convertView.setTag(holder2);
            } else {
                holder2 = (b) convertView.getTag();
            }
            a(holder2, item);
        }
        return convertView;
    }

    private void a(a holder, c item) {
        if (item != null) {
            if (f.aCd.equals(item.coverType)) {
                holder.aUO.setVisibility(0);
                holder.aUE.setVisibility(8);
            } else {
                holder.aUO.setVisibility(8);
                holder.aUE.setVisibility(0);
            }
            if (!UtilsFunction.empty(item.covers)) {
                a(holder.aUN, (String) item.covers.get(0));
                if (item.imgCount > 1) {
                    holder.aUE.setText(String.format("%d图", new Object[]{Integer.valueOf(item.imgCount)}));
                } else {
                    holder.aUE.setVisibility(8);
                }
            }
            a(holder.aRh, holder.aUP, holder.aUG, holder.aFF, item);
        }
    }

    private void a(b holder, c item) {
        if (item != null) {
            a(holder.aRh, holder.aUP, holder.aUG, holder.aFF, item);
        }
    }

    private void a(PaintView imageView, String url) {
        imageView.setUri(UtilUri.getUriOrNull(url)).scaleType(ScaleType.CENTER_CROP).placeHolder(d.isDayMode() ? com.MCWorld.bbs.b.f.place_holder_normal : com.MCWorld.bbs.b.f.place_holder_night_normal).errorHolder(d.isDayMode() ? com.MCWorld.bbs.b.f.err_holder_normal : com.MCWorld.bbs.b.f.err_holder_night_normal).fadeDuration(150).tag(this.mContext).setImageLoader(l.cb().getImageLoader());
    }

    private void a(TextView title, TextView time, TextView commentCounts, View root, final c item) {
        title.setText(item.title);
        time.setText(az.bD(item.publishTime));
        commentCounts.setText(String.format("%d评论", new Object[]{Integer.valueOf(item.cmtCount)}));
        root.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ NewsSimpleItemAdapter aUM;

            public void onClick(View v) {
                t.a(v.getContext(), item);
            }
        });
    }

    public void a(j setter) {
        setter.bg(g.root_container, com.MCWorld.bbs.b.c.listSelector).bh(g.img_counts, 16842809).bh(g.title, 16842808).bh(g.comment_counts, 16843282).bh(g.timing, 16843282).bf(g.split_item, com.MCWorld.bbs.b.c.splitColor);
    }
}
