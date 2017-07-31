package com.huluxia.ui.maptool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.framework.R;

/* compiled from: MapListDialog */
class a$b extends BaseAdapter {
    final /* synthetic */ a aZL;
    private Context mContext;

    public a$b(a aVar, Context c) {
        this.aZL = aVar;
        this.mContext = c;
    }

    public int getCount() {
        return a.a(this.aZL).size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a$d holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.localmap_mapitem, null);
            holder = new a$d();
            convertView.setTag(holder);
            holder.aZN = (TextView) convertView.findViewById(R.id.mapConfigMapListTexName);
            holder.aZO = (TextView) convertView.findViewById(R.id.mapConfigMapListTexTime);
            holder.aZP = (TextView) convertView.findViewById(R.id.mapConfigMapListTexSize);
        } else {
            holder = (a$d) convertView.getTag();
        }
        holder.aZN.setText(((a$a) a.a(this.aZL).get(position)).name);
        holder.aZO.setText(((a$a) a.a(this.aZL).get(position)).aZM);
        holder.aZP.setText(((a$a) a.a(this.aZL).get(position)).size);
        return convertView;
    }
}
