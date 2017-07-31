package com.huluxia.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import java.util.List;

public class MenuItemArrayAdapter extends ArrayAdapter<o> {
    int checked = 0;
    Context context;

    public MenuItemArrayAdapter(Context context, int resourceId, List<o> objects) {
        super(context, resourceId, objects);
        this.context = context;
    }

    int On() {
        return this.checked;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        o line = (o) super.getItem(position);
        LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        if (convertView == null) {
            convertView = mInflater.inflate(i.listitem_dialog, null);
            holder = new a(this, null);
            holder.bxD = (TextView) convertView.findViewById(g.textview);
            holder.bxE = (ImageView) convertView.findViewById(g.imageview);
            holder.bxF = (CheckBox) convertView.findViewById(g.menu_check);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.bxD.setText(line.getTitle());
        if (line.Os() > 0) {
            holder.bxE.setImageResource(line.Os());
            holder.bxE.setVisibility(0);
        } else {
            holder.bxE.setVisibility(8);
        }
        if (line.On() > 0) {
            holder.bxF.setVisibility(0);
            holder.bxF.setChecked(false);
            holder.bxF.setOnCheckedChangeListener(new 1(this));
        } else {
            holder.bxF.setVisibility(8);
        }
        return convertView;
    }
}
