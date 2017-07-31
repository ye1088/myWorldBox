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
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.k;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class MonthReviewRankAdapter extends BaseAdapter implements b {
    private List<com.huluxia.data.studio.f.a> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    private static class a {
        public TextView aFB;
        public ImageView aFx;
        public TextView aFy;
        public TextView name;

        private a() {
        }
    }

    public MonthReviewRankAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return UtilsFunction.empty(this.aab) ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return UtilsFunction.empty(this.aab) ? null : (com.huluxia.data.studio.f.a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            holder = new a();
            convertView = this.mInflater.inflate(R.layout.item_month_review_rank, null);
            holder.aFx = (ImageView) convertView.findViewById(R.id.iv_month_rank);
            holder.aFy = (TextView) convertView.findViewById(R.id.tv_month_rank);
            holder.name = (TextView) convertView.findViewById(R.id.tv_studio_name);
            holder.aFB = (TextView) convertView.findViewById(R.id.tv_grade);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        final com.huluxia.data.studio.f.a item = (com.huluxia.data.studio.f.a) getItem(position);
        a(holder, item);
        convertView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MonthReviewRankAdapter aFA;

            public void onClick(View v) {
                k.startPersonalStudio(this.aFA.mContext, item.sid);
            }
        });
        return convertView;
    }

    private void a(a holder, com.huluxia.data.studio.f.a item) {
        switch (item.rank) {
            case 1:
                holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_first_icon));
                holder.aFy.setText(String.valueOf(item.rank));
                holder.name.setText(item.name);
                holder.name.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_first_text_color));
                holder.aFB.setText(item.integral + hlx.data.localstore.a.bLt);
                holder.aFB.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_first_text_color));
                return;
            case 2:
                holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_second_icon));
                holder.aFy.setText(String.valueOf(item.rank));
                holder.name.setText(item.name);
                holder.name.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_second_text_color));
                holder.aFB.setText(item.integral + hlx.data.localstore.a.bLt);
                holder.aFB.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_second_text_color));
                return;
            case 3:
                holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_third_icon));
                holder.aFy.setText(String.valueOf(item.rank));
                holder.name.setText(item.name);
                holder.name.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_third_text_color));
                holder.aFB.setText(item.integral + hlx.data.localstore.a.bLt);
                holder.aFB.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_third_text_color));
                return;
            default:
                holder.aFx.setImageResource(d.r(this.mContext, R.attr.studio_rank_other_icon));
                holder.aFy.setText(String.valueOf(item.rank));
                holder.name.setText(item.name);
                holder.name.setTextColor(d.getColor(this.mContext, 16842808));
                holder.aFB.setText(item.integral + hlx.data.localstore.a.bLt);
                holder.aFB.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_other_text_color));
                return;
        }
    }

    public void c(List<com.huluxia.data.studio.f.a> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bg(R.id.rly_month_review_rank, R.attr.listSelector).bj(R.id.iv_month_rank, R.attr.studio_rank_first_icon).bj(R.id.iv_month_rank, R.attr.studio_rank_second_icon).bj(R.id.iv_month_rank, R.attr.studio_rank_third_icon).bj(R.id.iv_month_rank, R.attr.studio_rank_other_icon).bh(R.id.tv_month_rank, R.attr.backgroundDefault).bh(R.id.tv_studio_name, 16842808).bh(R.id.tv_studio_name, R.attr.studio_rank_first_text_color).bh(R.id.tv_studio_name, R.attr.studio_rank_second_text_color).bh(R.id.tv_studio_name, R.attr.studio_rank_third_text_color).bh(R.id.tv_grade, R.attr.studio_rank_first_text_color).bh(R.id.tv_grade, R.attr.studio_rank_second_text_color).bh(R.id.tv_grade, R.attr.studio_rank_third_text_color).bh(R.id.tv_grade, R.attr.studio_rank_other_text_color).bf(R.id.divideline, R.attr.dividingLine);
    }
}
