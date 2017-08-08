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
import com.MCWorld.module.news.f;
import com.MCWorld.t;
import com.MCWorld.utils.az;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class NewsWifiItemAdapter extends NewsListAdapter {
    private static final int aUJ = 3;
    private static final int aUK = 5;
    private static final int aUL = 6;
    private static final int aUQ = 1;
    private static final int aUR = 2;
    private static final int aUS = 4;
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
        public TextView aUE;
        public TextView aUG;
        public View aUI;
        public PaintView aUN;
        public View aUO;
        public TextView aUP;

        private b() {
        }
    }

    private static class c {
        public View aFF;
        public TextView aRh;
        public TextView aUG;
        public View aUI;
        public PaintView aUN;
        public TextView aUP;

        private c() {
        }
    }

    private static class d {
        public View aFF;
        public TextView aRh;
        public TextView aUE;
        public TextView aUG;
        public View aUI;
        public TextView aUP;
        public PaintView aUU;
        public PaintView aUV;
        public PaintView aUW;

        private d() {
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return kK(i);
    }

    public NewsWifiItemAdapter(Context context, ArrayList<com.MCWorld.module.news.c> data) {
        this.aab = data;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemViewType(int position) {
        com.MCWorld.module.news.c item = kK(position);
        if (f.aBZ.equals(item.coverType)) {
            return 1;
        }
        if (f.aCa.equals(item.coverType)) {
            return 2;
        }
        if (f.aCb.equals(item.coverType)) {
            return 3;
        }
        if (f.aCc.equals(item.coverType)) {
            return 4;
        }
        if (f.aCd.equals(item.coverType)) {
            return 5;
        }
        return 6;
    }

    public int getViewTypeCount() {
        return 7;
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public com.MCWorld.module.news.c kK(int position) {
        return (com.MCWorld.module.news.c) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        com.MCWorld.module.news.c item = kK(position);
        if (type == 1 || type == 4) {
            a holder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_news_big_img, null);
                holder = new a();
                holder.aUN = (PaintView) convertView.findViewById(g.video_img);
                holder.aUO = convertView.findViewById(g.iv_video_tag);
                holder.aFF = convertView.findViewById(g.root_container);
                holder.aUI = convertView.findViewById(g.split_item);
                holder.aUE = (TextView) convertView.findViewById(g.img_counts);
                holder.aRh = (TextView) convertView.findViewById(g.title);
                holder.aUP = (TextView) convertView.findViewById(g.timing);
                holder.aUG = (TextView) convertView.findViewById(g.comment_counts);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            a(holder, item);
        } else if (type == 2) {
            d holder2;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_news_triple_imgs, null);
                holder2 = new d();
                holder2.aUU = (PaintView) convertView.findViewById(g.img1);
                holder2.aUV = (PaintView) convertView.findViewById(g.img2);
                holder2.aUW = (PaintView) convertView.findViewById(g.img3);
                holder2.aUE = (TextView) convertView.findViewById(g.img_counts);
                holder2.aUI = convertView.findViewById(g.split_item);
                holder2.aFF = convertView.findViewById(g.root_container);
                holder2.aRh = (TextView) convertView.findViewById(g.title);
                holder2.aUP = (TextView) convertView.findViewById(g.timing);
                holder2.aUG = (TextView) convertView.findViewById(g.comment_counts);
                convertView.setTag(holder2);
            } else {
                holder2 = (d) convertView.getTag();
            }
            a(holder2, item);
        } else if (type == 3 || type == 5) {
            b holder3;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_news_small_img, null);
                holder3 = new b();
                holder3.aUN = (PaintView) convertView.findViewById(g.video_img);
                holder3.aUI = convertView.findViewById(g.split_item);
                holder3.aFF = convertView.findViewById(g.root_container);
                holder3.aUO = convertView.findViewById(g.iv_video_tag);
                holder3.aUE = (TextView) convertView.findViewById(g.img_counts);
                holder3.aRh = (TextView) convertView.findViewById(g.title);
                holder3.aUP = (TextView) convertView.findViewById(g.timing);
                holder3.aUG = (TextView) convertView.findViewById(g.comment_counts);
                convertView.setTag(holder3);
            } else {
                holder3 = (b) convertView.getTag();
            }
            a(holder3, item);
        } else {
            c holder4;
            if (convertView == null) {
                convertView = this.mInflater.inflate(i.item_news_no_img, null);
                holder4 = new c();
                holder4.aRh = (TextView) convertView.findViewById(g.title);
                holder4.aFF = convertView.findViewById(g.root_container);
                holder4.aUP = (TextView) convertView.findViewById(g.timing);
                holder4.aUG = (TextView) convertView.findViewById(g.comment_counts);
                holder4.aUI = convertView.findViewById(g.split_item);
                convertView.setTag(holder4);
            } else {
                holder4 = (c) convertView.getTag();
            }
            a(holder4, item);
        }
        return convertView;
    }

    private void a(a holder, com.MCWorld.module.news.c item) {
        if (item != null) {
            if (f.aCc.equals(item.coverType)) {
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

    private void a(b holder, com.MCWorld.module.news.c item) {
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

    private void a(d holder, com.MCWorld.module.news.c item) {
        if (item != null) {
            if (!UtilsFunction.empty(item.covers)) {
                for (int i = 0; i < item.covers.size(); i++) {
                    switch (i) {
                        case 0:
                            a(holder.aUU, (String) item.covers.get(0));
                            break;
                        case 1:
                            a(holder.aUV, (String) item.covers.get(1));
                            break;
                        case 2:
                            a(holder.aUW, (String) item.covers.get(2));
                            break;
                        default:
                            break;
                    }
                }
                if (item.imgCount > 3) {
                    holder.aUE.setText(String.format("%d图", new Object[]{Integer.valueOf(item.imgCount)}));
                } else {
                    holder.aUE.setVisibility(8);
                }
            }
            a(holder.aRh, holder.aUP, holder.aUG, holder.aFF, item);
        }
    }

    private void a(c holder, com.MCWorld.module.news.c item) {
        if (item != null) {
            a(holder.aRh, holder.aUP, holder.aUG, holder.aFF, item);
        }
    }

    private void a(TextView title, TextView time, TextView commentCounts, View root, final com.MCWorld.module.news.c item) {
        title.setText(item.title);
        time.setText(az.bD(item.publishTime));
        commentCounts.setText(String.format("%d评论", new Object[]{Integer.valueOf(item.cmtCount)}));
        root.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ NewsWifiItemAdapter aUT;

            public void onClick(View v) {
                t.a(v.getContext(), item);
            }
        });
    }

    private void a(PaintView imageView, String url) {
        imageView.setUri(UtilUri.getUriOrNull(url)).scaleType(ScaleType.CENTER_CROP).placeHolder(com.simple.colorful.d.isDayMode() ? com.MCWorld.bbs.b.f.place_holder_normal : com.MCWorld.bbs.b.f.place_holder_night_normal).errorHolder(com.simple.colorful.d.isDayMode() ? com.MCWorld.bbs.b.f.err_holder_normal : com.MCWorld.bbs.b.f.err_holder_night_normal).fadeDuration(150).setImageLoader(l.cb().getImageLoader());
    }

    public void a(j setter) {
        setter.bg(g.root_container, com.MCWorld.bbs.b.c.listSelector).bh(g.img_counts, 16842809).bh(g.title, 16842808).bh(g.comment_counts, 16843282).bh(g.timing, 16843282).bh(g.news_type, 16843282).bf(g.split_item, com.MCWorld.bbs.b.c.splitColor);
    }
}
