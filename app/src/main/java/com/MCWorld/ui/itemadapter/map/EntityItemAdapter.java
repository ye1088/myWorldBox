package com.MCWorld.ui.itemadapter.map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.mcmap.c;
import com.MCWorld.mojang.entity.EntityItem;
import com.MCWorld.service.i;
import com.MCWorld.utils.aj;
import java.util.List;

public class EntityItemAdapter extends ArrayAdapter<EntityItem> implements OnItemClickListener {
    private Activity aTX;
    private Boolean aTY = Boolean.valueOf(false);

    public EntityItemAdapter(Activity context, List<EntityItem> objects, Boolean isDeleting) {
        super(context, R.layout.item_stock, R.id.title, objects);
        setNotifyOnChange(true);
        this.aTX = context;
        this.aTY = isDeleting;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        EntityItem item = (EntityItem) getItem(position);
        int id = item.getEntity().getEntityTypeId();
        String title = aj.lt(id);
        String logoName = aj.lu(id);
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.number)).setText("数量: " + String.valueOf(item.getNum()));
        if (this.aTY.booleanValue()) {
            v.findViewById(R.id.cross).setVisibility(0);
        } else {
            v.findViewById(R.id.cross).setVisibility(8);
        }
        ImageView logo = (ImageView) v.findViewById(R.id.logo);
        int logoID = v.getResources().getIdentifier(logoName, "drawable", this.aTX.getPackageName());
        if (logoID != 0) {
            logo.setImageResource(logoID);
        }
        v.setTag(item);
        return v;
    }

    public void onItemClick(AdapterView<?> adapterView, View v, int arg2, long arg3) {
        EntityItem item = (EntityItem) v.getTag();
        if (((ImageView) v.findViewById(R.id.cross)).getVisibility() == 0) {
            i.a(item);
        } else {
            c.a(this.aTX, item);
        }
    }
}
