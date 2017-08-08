package com.MCWorld.studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.k;
import com.MCWorld.t;
import com.MCWorld.utils.y;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends BaseAdapter implements b {
    private List<com.MCWorld.data.studio.g.a> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    private static class a {
        public TextView aFK;
        public TextView aFL;
        public TextView aFM;
        public TextView aFN;
        public TextView aFO;
        public TextView aFP;
        public PaintView aFi;
        public TextView name;

        private a() {
        }
    }

    public RecommendAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return UtilsFunction.empty(this.aab) ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return UtilsFunction.empty(this.aab) ? null : (com.MCWorld.data.studio.g.a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holer;
        if (convertView == null) {
            holer = new a();
            convertView = this.mInflater.inflate(R.layout.item_studio_recommend, null);
            holer.aFi = (PaintView) convertView.findViewById(R.id.avatar);
            holer.name = (TextView) convertView.findViewById(R.id.tv_studio_name);
            holer.aFK = (TextView) convertView.findViewById(R.id.tv_studio_member_count);
            holer.aFL = (TextView) convertView.findViewById(R.id.tv_studio_hot);
            holer.aFM = (TextView) convertView.findViewById(R.id.tv_studio_tag1);
            holer.aFN = (TextView) convertView.findViewById(R.id.tv_studio_tag2);
            holer.aFO = (TextView) convertView.findViewById(R.id.tv_studio_tag3);
            holer.aFP = (TextView) convertView.findViewById(R.id.tv_studio_tag4);
            convertView.setTag(holer);
        } else {
            holer = (a) convertView.getTag();
        }
        final com.MCWorld.data.studio.g.a item = (com.MCWorld.data.studio.g.a) getItem(position);
        a(holer, item);
        convertView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RecommendAdapter aFJ;

            public void onClick(View v) {
                k.startPersonalStudio(this.aFJ.mContext, item.sid);
            }
        });
        return convertView;
    }

    private void a(a holder, com.MCWorld.data.studio.g.a item) {
        holder.aFi.placeHolder(d.r(this.mContext, R.attr.default_discover_pic));
        if (UtilsFunction.empty(item.icon)) {
            holder.aFi.setImageResource(d.r(this.mContext, R.attr.default_discover_pic));
        } else {
            t.b(holder.aFi, item.icon, (float) t.dipToPx(this.mContext, 7));
        }
        holder.name.setText(item.name);
        if (1 == item.isRank) {
            holder.name.setTextColor(d.getColor(this.mContext, R.attr.studio_rank_first_text_color));
        } else {
            holder.name.setTextColor(d.getColor(this.mContext, 16842808));
        }
        holder.aFK.setText(String.valueOf(item.count));
        holder.aFL.setText(item.integral);
        if (UtilsFunction.empty(item.cates)) {
            holder.aFM.setVisibility(4);
            holder.aFN.setVisibility(4);
            holder.aFO.setVisibility(4);
            holder.aFP.setVisibility(4);
            return;
        }
        TextView[] viewList = new TextView[]{holder.aFM, holder.aFN, holder.aFO, holder.aFP};
        for (int i = 0; i < 4; i++) {
            if (i < item.cates.size()) {
                String name = ((hlx.module.resources.a) item.cates.get(i)).catename;
                viewList[i].setText(name);
                viewList[i].setTextColor(y.C(this.mContext, name));
                viewList[i].setBackgroundDrawable(y.K(this.mContext, name));
                viewList[i].setVisibility(0);
            } else {
                viewList[i].setVisibility(4);
            }
        }
    }

    public void c(List<com.MCWorld.data.studio.g.a> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bg(R.id.rly_studio_recommend, R.attr.listSelector).bj(R.id.avatar, R.attr.default_discover_pic).bh(R.id.tv_studio_name, 16842808).bh(R.id.tv_studio_name, R.attr.studio_rank_first_text_color).bh(R.id.tv_studio_member_count, 16842808).ac(R.id.tv_studio_member_count, R.attr.studio_member_count_icon, 1).bh(R.id.tv_studio_hot, R.attr.studio_hot_text_color).ac(R.id.tv_studio_hot, R.attr.studio_hot_icon, 1).bf(R.id.bottom_dividing_line, R.attr.dividingLine);
    }
}
