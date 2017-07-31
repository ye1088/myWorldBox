package com.huluxia.ui.itemadapter.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.data.map.MapPathItem;
import com.huluxia.framework.R;
import java.util.List;

public class PreExportMapItemAdapter extends BaseAdapter {
    private Context mCtx;
    private List<Object> mObjects;

    static class a {
        TextView aRh;
        RelativeLayout aUa;
        RadioButton aUh;

        a() {
        }
    }

    public PreExportMapItemAdapter(Context context, List<Object> objects) {
        this.mCtx = context;
        this.mObjects = objects;
    }

    public int getCount() {
        return this.mObjects.size();
    }

    public Object getItem(int i) {
        return this.mObjects.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mCtx).inflate(R.layout.item_map_import, null);
            holder = new a();
            holder.aUa = (RelativeLayout) convertView.findViewById(R.id.rl_cate);
            holder.aRh = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.aRh.setText(((MapPathItem) getItem(position)).name);
        return convertView;
    }
}
