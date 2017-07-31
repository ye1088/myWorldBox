package com.huluxia.widget.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.data.map.h;
import com.huluxia.framework.R;
import java.util.List;

public class GroupAdapter extends BaseAdapter {
    private Context context;
    private List<h> list;

    static class a {
        View aTK;
        TextView bxh;

        a() {
        }
    }

    public GroupAdapter(Context context, List<h> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.group_item_view, null);
            holder = new a();
            holder.bxh = (TextView) convertView.findViewById(R.id.groupItem);
            holder.aTK = convertView.findViewById(R.id.dividing_line);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.bxh.setText(((h) this.list.get(position)).title);
        if (position == this.list.size() - 1) {
            holder.aTK.setVisibility(8);
        } else {
            holder.aTK.setVisibility(0);
        }
        if (((h) this.list.get(position)).id != 0) {
            Drawable img_tip = this.context.getResources().getDrawable(((h) this.list.get(position)).id);
            if (img_tip != null) {
                img_tip.setBounds(0, 0, img_tip.getMinimumWidth(), img_tip.getMinimumHeight());
            }
            holder.bxh.setCompoundDrawables(img_tip, null, null, null);
        } else {
            holder.bxh.setCompoundDrawables(null, null, null, null);
        }
        return convertView;
    }
}
