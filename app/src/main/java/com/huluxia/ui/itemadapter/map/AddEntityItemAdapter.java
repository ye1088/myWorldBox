package com.huluxia.ui.itemadapter.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mojang.entity.EntityItem;
import com.huluxia.service.i;
import com.huluxia.utils.aj;
import java.util.List;

public class AddEntityItemAdapter extends ArrayAdapter<EntityItem> implements OnItemClickListener {
    private Context ctx;

    public AddEntityItemAdapter(Context context, List<EntityItem> objects) {
        super(context, R.layout.item_addstock, R.id.title, objects);
        this.ctx = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        EntityItem item = (EntityItem) getItem(position);
        int id = item.getEntity().getEntityTypeId();
        String title = aj.lt(id);
        String logoName = aj.lu(id);
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.id)).setText(String.valueOf(id));
        ImageView logo = (ImageView) v.findViewById(R.id.logo);
        int logoID = v.getResources().getIdentifier(logoName, "drawable", this.ctx.getPackageName());
        if (logoID != 0) {
            logo.setImageResource(logoID);
        }
        v.setTag(item);
        return v;
    }

    public void onItemClick(AdapterView<?> adapterView, View v, int arg2, long arg3) {
        ImageView hook = (ImageView) v.findViewById(R.id.hook);
        EntityItem item = (EntityItem) v.getTag();
        if (hook.getVisibility() == 0) {
            hook.setVisibility(8);
            i.a(item.getEntity(), 0);
            return;
        }
        hook.setVisibility(0);
        i.a(item.getEntity(), 1);
    }
}
