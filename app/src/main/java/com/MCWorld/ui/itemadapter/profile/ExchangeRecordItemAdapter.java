package com.MCWorld.ui.itemadapter.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.profile.ExchangeRecord;
import com.MCWorld.data.profile.a;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.utils.aw;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExchangeRecordItemAdapter extends BaseAdapter implements b {
    private List<ExchangeRecord> aVp = new ArrayList();
    @SuppressLint({"SimpleDateFormat"})
    private SimpleDateFormat aVq = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private Context mContext;
    private LayoutInflater mInflater;

    public ExchangeRecordItemAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public int getCount() {
        return this.aVp.size();
    }

    public Object getItem(int position) {
        return this.aVp.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a viewHolder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.item_exchange_record, parent, false);
            viewHolder = new a(this);
            viewHolder.aVr = (PaintView) convertView.findViewById(g.img_gift);
            viewHolder.aVs = (TextView) convertView.findViewById(g.tv_gift_name);
            viewHolder.aVt = (TextView) convertView.findViewById(g.tv_user_info_type);
            viewHolder.aVu = (TextView) convertView.findViewById(g.tv_user_info);
            viewHolder.aVw = (TextView) convertView.findViewById(g.tv_record_date);
            viewHolder.aVv = (TextView) convertView.findViewById(g.tv_handle_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (a) convertView.getTag();
        }
        ExchangeRecord item = (ExchangeRecord) this.aVp.get(position);
        t.a(viewHolder.aVr, item.icon, (float) t.dipToPx(this.mContext, 5));
        viewHolder.aVw.setText(this.aVq.format(new Date(Long.parseLong(item.createTime))));
        viewHolder.aVv.setText(item.statusDesc);
        if (item.statusDesc.equals("提交中")) {
            viewHolder.aVv.setTextColor(this.mContext.getResources().getColor(d.exchange_green));
        } else if (item.statusDesc.equals("兑换成功")) {
            viewHolder.aVv.setTextColor(this.mContext.getResources().getColor(d.exchange_green_dark));
        } else {
            viewHolder.aVv.setTextColor(this.mContext.getResources().getColor(d.exchange_orange));
        }
        HashMap<String, String> ext = item.ext;
        if (ext.containsKey(a.qb)) {
            viewHolder.aVs.setText((String) ext.get(a.pZ));
            switch (Integer.parseInt((String) ext.get(a.qb))) {
                case 1:
                    viewHolder.aVt.setText("QQ号：");
                    viewHolder.aVu.setText((CharSequence) ext.get("QQ"));
                    break;
                case 2:
                    viewHolder.aVt.setText("手机号：");
                    viewHolder.aVu.setText((CharSequence) ext.get(a.qe));
                    break;
                case 3:
                    viewHolder.aVt.setText("支付宝帐号：");
                    viewHolder.aVu.setText((CharSequence) ext.get(a.qg));
                    break;
                case 4:
                    viewHolder.aVt.setText("收货人：");
                    viewHolder.aVu.setText(aw.W((String) ext.get(a.qd), 10));
                    break;
            }
        }
        return convertView;
    }

    public void setData(ArrayList<ExchangeRecord> data) {
        this.aVp = data;
        notifyDataSetChanged();
    }

    public void n(ArrayList<ExchangeRecord> data) {
        this.aVp.addAll(data);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bg(g.item_container, c.listSelector).bf(g.item_split, c.splitColor).bh(g.tv_gift_name, 16842806).bh(g.tv_user_info_type, 16842808).bh(g.tv_user_info, 16842808).bh(g.tv_record_date, 16842808);
    }
}
