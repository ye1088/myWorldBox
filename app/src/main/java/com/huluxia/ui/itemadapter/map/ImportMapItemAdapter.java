package com.huluxia.ui.itemadapter.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.data.map.MapPathItem;
import com.huluxia.framework.R;
import com.huluxia.widget.Constants.FileType;
import java.util.List;

public class ImportMapItemAdapter extends BaseAdapter {
    private Context mCtx;
    private List<Object> mObjects;

    static class a {
        TextView aRh;
        RelativeLayout aUa;
        ImageView aUb;

        a() {
        }
    }

    public ImportMapItemAdapter(Context context, List<Object> objects) {
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
        if (item.fileType == FileType.PARENTDIR.Value()) {
            holder.aUb.setImageResource(R.drawable.ic_back);
        } else if (item.fileType == FileType.SUBDIR.Value()) {
            holder.aUb.setImageResource(R.drawable.ic_folder);
        } else if (item.fileType == FileType.ZIP.Value() || item.fileType == FileType.JS.Value()) {
            holder.aUb.setImageResource(R.drawable.ic_file);
        }
        return convertView;
    }
}
