package com.huluxia.ui.itemadapter.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.l;
import com.huluxia.module.news.d;
import com.huluxia.utils.az;
import com.huluxia.widget.textview.EmojiTextView;
import com.huluxia.widget.textview.HyperlinkTextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewsCommentItemAdapter extends BaseAdapter {
    private boolean aUt = false;
    private List<d> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    public /* synthetic */ Object getItem(int i) {
        return kJ(i);
    }

    public NewsCommentItemAdapter(Context context, List<d> data, boolean showSplitTop) {
        this.mContext = context;
        this.aab = data;
        this.aUt = showSplitTop;
        this.mInflater = LayoutInflater.from(context);
    }

    public void c(List<d> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        if (!UtilsFunction.empty((Collection) data)) {
            List<d> newData = new ArrayList();
            for (d item : data) {
                if (!(item == null || item.user == null)) {
                    newData.add(item);
                }
            }
            this.aab.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public d kJ(int position) {
        return (d) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public boolean isEmpty() {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        d item = kJ(position);
        if (convertView == null) {
            holder = new a(null);
            convertView = this.mInflater.inflate(i.item_news_comment, null);
            a.a(holder, (PaintView) convertView.findViewById(g.avatar));
            a.a(holder, (EmojiTextView) convertView.findViewById(g.nick));
            a.a(holder, (TextView) convertView.findViewById(g.publish_time));
            a.a(holder, (LinearLayout) convertView.findViewById(g.ref_ll));
            a.b(holder, (EmojiTextView) convertView.findViewById(g.refauthor));
            a.a(holder, (HyperlinkTextView) convertView.findViewById(g.refcontent));
            a.b(holder, (HyperlinkTextView) convertView.findViewById(g.content));
            a.a(holder, convertView.findViewById(g.split_top));
            a.b(holder, convertView.findViewById(g.split_bottom));
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, item, position);
        return convertView;
    }

    private void a(a holder, d item, int position) {
        if (item != null) {
            View a = a.a(holder);
            int i = (this.aUt && position == 0) ? 0 : 8;
            a.setVisibility(i);
            a.b(holder).setOnClickListener(new 1(this, item));
            a(a.b(holder), item.user.avatar);
            a.c(holder).setText(item.user.nick);
            a.d(holder).setText(az.bD(item.createTime));
            if (item.refComment != null) {
                a.e(holder).setVisibility(0);
                a.f(holder).setText("回复: " + item.refComment.nick);
                String refText = item.refComment.text;
                if (item.refComment.state == 2) {
                    refText = "此评论已经删除";
                }
                a.g(holder).setText(refText);
            } else {
                a.e(holder).setVisibility(8);
            }
            a.h(holder).setText(item.text);
        }
    }

    private void a(PaintView imageView, String url) {
        imageView.setUri(UtilUri.getUriOrNull(url)).scaleType(ScaleType.CENTER_CROP).radius(3.0f).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).errorHolder(com.simple.colorful.d.isDayMode() ? f.err_holder_normal : f.err_holder_night_normal).fadeDuration(150).setImageLoader(l.cb().getImageLoader());
    }
}
