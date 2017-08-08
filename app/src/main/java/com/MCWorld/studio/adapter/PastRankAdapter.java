package com.MCWorld.studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.studio.utils.b.a;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class PastRankAdapter extends BaseAdapter implements b {
    private List<a> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    public PastRankAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return UtilsFunction.empty(this.aab) ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return UtilsFunction.empty(this.aab) ? null : (a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            holder = new a(null);
            convertView = this.mInflater.inflate(R.layout.item_past_rank, null);
            holder.aFE = convertView.findViewById(R.id.view_divider);
            holder.aFF = convertView.findViewById(R.id.fl_month);
            holder.aFG = (ImageView) convertView.findViewById(R.id.iv_month);
            holder.aFH = (TextView) convertView.findViewById(R.id.tv_past_time);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        if (position == 0) {
            holder.aFE.setVisibility(8);
        } else {
            holder.aFE.setVisibility(0);
        }
        a(holder, (a) getItem(position));
        return convertView;
    }

    private void a(a holder, a item) {
        holder.aFH.setText(item.year + "年" + item.month + "月");
        holder.aFG.setImageResource(com.MCWorld.studio.utils.a.j(this.mContext, item.month));
        holder.aFG.setBackgroundColor(com.MCWorld.studio.utils.a.i(this.mContext, item.month));
        holder.aFF.setOnClickListener(new 1(this, item));
    }

    public void c(List<a> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bj(R.id.iv_month, R.attr.ic_past_rank_january).bj(R.id.iv_month, R.attr.ic_past_rank_february).bj(R.id.iv_month, R.attr.ic_past_rank_march).bj(R.id.iv_month, R.attr.ic_past_rank_april).bj(R.id.iv_month, R.attr.ic_past_rank_may).bj(R.id.iv_month, R.attr.ic_past_rank_june).bj(R.id.iv_month, R.attr.ic_past_rank_july).bj(R.id.iv_month, R.attr.ic_past_rank_august).bj(R.id.iv_month, R.attr.ic_past_rank_september).bj(R.id.iv_month, R.attr.ic_past_rank_october).bj(R.id.iv_month, R.attr.ic_past_rank_november).bj(R.id.iv_month, R.attr.ic_past_rank_december).bf(R.id.iv_month, R.attr.studio_past_rank_january).bf(R.id.iv_month, R.attr.studio_past_rank_february).bf(R.id.iv_month, R.attr.studio_past_rank_march).bf(R.id.iv_month, R.attr.studio_past_rank_april).bf(R.id.iv_month, R.attr.studio_past_rank_may).bf(R.id.iv_month, R.attr.studio_past_rank_june).bf(R.id.iv_month, R.attr.studio_past_rank_july).bf(R.id.iv_month, R.attr.studio_past_rank_august).bf(R.id.iv_month, R.attr.studio_past_rank_september).bf(R.id.iv_month, R.attr.studio_past_rank_october).bf(R.id.iv_month, R.attr.studio_past_rank_november).bf(R.id.iv_month, R.attr.studio_past_rank_december).bh(R.id.tv_past_time, R.attr.backgroundDefault);
    }
}
