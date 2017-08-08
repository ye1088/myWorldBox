package com.MCWorld.ui.itemadapter.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.MCWorld.framework.R;
import java.util.List;

public class MapSelectItemAdapter extends ArrayAdapter<Object> implements OnItemClickListener {
    private a aUg;
    private Context ctx;

    public interface a {
        void ez(String str);
    }

    public MapSelectItemAdapter(Context context, List<Object> objects) {
        super(context, R.layout.item_map_select, R.id.title, objects);
        this.ctx = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        String name = (String) getItem(position);
        ((TextView) v.findViewById(R.id.title)).setText(name);
        v.setTag(name);
        return v;
    }

    public void onItemClick(AdapterView<?> adapterView, View v, int arg2, long arg3) {
        this.aUg.ez((String) v.getTag());
    }

    public void a(a selectMap) {
        this.aUg = selectMap;
    }
}
