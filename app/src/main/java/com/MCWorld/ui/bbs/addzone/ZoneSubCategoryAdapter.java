package com.MCWorld.ui.bbs.addzone;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsText;
import com.MCWorld.module.topic.d;
import com.MCWorld.t;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import hlx.data.localstore.a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ZoneSubCategoryAdapter extends BaseAdapter implements b {
    private List<d> aRb = new ArrayList();
    private Activity mContext;
    private LayoutInflater mInflater;

    public /* synthetic */ Object getItem(int i) {
        return kD(i);
    }

    public ZoneSubCategoryAdapter(Activity context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void c(List<d> items, boolean clear) {
        if (clear) {
            this.aRb.clear();
        }
        if (!UtilsFunction.empty((Collection) items)) {
            this.aRb.addAll(items);
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return UtilsFunction.size(this.aRb);
    }

    public d kD(int position) {
        return (d) this.aRb.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a viewHolder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.item_sub_zone_category, parent, false);
            viewHolder = new a(this, null);
            viewHolder.aRg = (PaintView) convertView.findViewById(g.icon);
            viewHolder.aRh = (TextView) convertView.findViewById(g.title);
            viewHolder.aFL = (TextView) convertView.findViewById(g.hot);
            viewHolder.aRi = (TextView) convertView.findViewById(g.topic);
            viewHolder.aRj = convertView.findViewById(g.bottom_split);
            viewHolder.aRk = (TextView) convertView.findViewById(g.attention);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (a) convertView.getTag();
        }
        d item = kD(position);
        t.a(viewHolder.aRg, item.icon, (float) t.dipToPx(this.mContext, 5));
        viewHolder.aRh.setText(item.title);
        viewHolder.aRk.setText(item.isSubscribe == 1 ? a.bKB_bt_cancel : "关注");
        viewHolder.aFL.setText(String.format("热度：%s", new Object[]{UtilsText.getNumText(item.viewCount)}));
        viewHolder.aRi.setText(String.format("话题：%s", new Object[]{UtilsText.getNumText(item.postCount)}));
        if (item.isSubscribe == 1) {
            viewHolder.aRk.setBackgroundDrawable(com.simple.colorful.d.o(this.mContext, c.drawableDownButtonGrey));
            viewHolder.aRk.setTextColor(com.simple.colorful.d.getColorStateList(this.mContext, c.colorDownButtonGrey));
        } else {
            viewHolder.aRk.setBackgroundDrawable(com.simple.colorful.d.o(this.mContext, c.drawableDownButtonGreen));
            viewHolder.aRk.setTextColor(com.simple.colorful.d.getColorStateList(this.mContext, c.colorDownButtonGreen));
        }
        viewHolder.aRk.setOnClickListener(new 1(this, item));
        return convertView;
    }

    public void a(j setter) {
        setter.bh(g.title, c.zoneSubcategoryTitleColor).bh(g.hot, c.zoneSubcategoryHotColor).bh(g.topic, c.zoneSubcategoryHotColor).bg(g.bottom_split, c.splitColor).bg(g.item_container, c.listSelector).bi(g.icon, c.valBrightness);
    }
}
