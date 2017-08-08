package com.MCWorld.ui.itemadapter.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.data.map.MapPathItem;
import com.MCWorld.framework.R;
import com.MCWorld.widget.Constants.FileType;
import java.util.HashMap;
import java.util.List;

public class ExportMapItemAdapter extends BaseAdapter {
    HashMap<String, Boolean> aTZ = new HashMap();
    private Context mCtx;
    private List<Object> mObjects;

    static class a {
        TextView aRh;
        RelativeLayout aUa;
        ImageView aUb;

        a() {
        }
    }

    public ExportMapItemAdapter(Context context, List<Object> objects) {
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
            holder.aUb = (ImageView) convertView.findViewById(R.id.logo);
            holder.aRh = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        MapPathItem item = (MapPathItem) getItem(position);
        holder.aRh.setText(item.name);
        if (this.aTZ.get(String.valueOf(position)) == null || !((Boolean) this.aTZ.get(String.valueOf(position))).booleanValue()) {
            this.aTZ.put(String.valueOf(position), Boolean.valueOf(false));
        }
        if (item.fileType == FileType.SUBDIR.Value()) {
            holder.aUb.setImageResource(R.drawable.ic_folder);
        } else {
            holder.aUb.setImageResource(R.drawable.ic_back);
        }
        return convertView;
    }
}
