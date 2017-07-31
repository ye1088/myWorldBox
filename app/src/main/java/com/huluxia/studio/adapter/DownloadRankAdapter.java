package com.huluxia.studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.k;
import com.huluxia.l;
import com.huluxia.utils.aw;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class DownloadRankAdapter extends BaseAdapter implements b {
    private List<com.huluxia.data.studio.e.a> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    public static class a {
        public TextView aFj;
        public PaintView aFu;
        public EmojiTextView aFv;
        public TextView aFw;
        public ImageView aFx;
        public TextView aFy;
        public TextView name;
    }

    public DownloadRankAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return UtilsFunction.empty(this.aab) ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return UtilsFunction.empty(this.aab) ? null : (com.huluxia.data.studio.e.a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            holder = new a();
            convertView = this.mInflater.inflate(R.layout.item_down_load_rank, null);
            holder.aFu = (PaintView) convertView.findViewById(R.id.iv_cover);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.aFv = (EmojiTextView) convertView.findViewById(R.id.tv_author);
            holder.aFw = (TextView) convertView.findViewById(R.id.tv_down_count);
            holder.aFx = (ImageView) convertView.findViewById(R.id.iv_month_rank);
            holder.aFy = (TextView) convertView.findViewById(R.id.tv_month_rank);
            holder.aFj = (TextView) convertView.findViewById(R.id.tv_studio_name);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        final com.huluxia.data.studio.e.a item = (com.huluxia.data.studio.e.a) getItem(position);
        a(holder, item);
        convertView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ DownloadRankAdapter aFt;

            public void onClick(View v) {
                if (item != null && item.info != null) {
                    k.a(this.aFt.mContext, (long) item.info.id, item.info.postID, "default");
                }
            }
        });
        return convertView;
    }

    private void a(a holder, com.huluxia.data.studio.e.a item) {
        if (item != null) {
            if (UtilsFunction.empty(item.info.icon)) {
                holder.aFu.setImageResource(d.r(this.mContext, R.attr.default_discover_pic));
            } else {
                holder.aFu.placeHolder(d.isDayMode() ? R.drawable.place_holder_normal : R.drawable.place_holder_night_normal);
                holder.aFu.setImageUrl(aw.gu(item.info.icon), l.cb().getImageLoader());
            }
            holder.name.setText(item.info.name);
            holder.aFv.setText(item.info.author);
            holder.aFw.setText(String.valueOf(item.info.downCount));
            if (item.info.studio != null) {
                holder.aFj.setVisibility(0);
                holder.aFj.setText(item.info.studio.name);
            } else {
                holder.aFj.setVisibility(8);
            }
            switch (item.rank) {
                case 1:
                    holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_first_icon));
                    holder.aFy.setText(String.valueOf(item.rank));
                    return;
                case 2:
                    holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_second_icon));
                    holder.aFy.setText(String.valueOf(item.rank));
                    return;
                case 3:
                    holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_third_icon));
                    holder.aFy.setText(String.valueOf(item.rank));
                    return;
                default:
                    holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_other_icon));
                    holder.aFy.setText(String.valueOf(item.rank));
                    return;
            }
        }
    }

    public void c(List<com.huluxia.data.studio.e.a> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bg(R.id.rly_down_load_rank, R.attr.listSelector).bj(R.id.iv_cover, R.attr.default_discover_pic).bh(R.id.tv_name, R.attr.mapName).bh(R.id.tv_author, R.attr.authorName).bh(R.id.tv_down_count, R.attr.authorName).bj(R.id.iv_month_rank, R.attr.studio_rank_first_icon).bj(R.id.iv_month_rank, R.attr.studio_rank_second_icon).bj(R.id.iv_month_rank, R.attr.studio_rank_third_icon).bj(R.id.iv_month_rank, R.attr.studio_rank_other_icon).bh(R.id.tv_month_rank, R.attr.backgroundDefault).bh(R.id.tv_studio_name, 16842808).bg(R.id.tv_studio_name, R.attr.bg_studio_name).bf(R.id.divideline, R.attr.dividingLine);
    }
}
