package com.MCWorld.ui.itemadapter.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.service.i;
import com.MCWorld.utils.aj;
import java.util.List;

public class AddStockItemAdapter extends ArrayAdapter<ItemStack> implements OnItemClickListener {
    private Context ctx;

    public AddStockItemAdapter(Context context, List<ItemStack> objects) {
        super(context, R.layout.item_addstock, R.id.title, objects);
        this.ctx = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        ItemStack item = (ItemStack) getItem(position);
        short id = item.getTypeId();
        String title = aj.aD(item.getTypeId(), item.getDurability());
        String logoName = aj.aE(item.getTypeId(), item.getDurability());
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
        ItemStack item = (ItemStack) v.getTag();
        if (hook.getVisibility() == 0) {
            hook.setVisibility(8);
            i.a(item, 0);
            return;
        }
        hook.setVisibility(0);
        i.a(item, 1);
    }
}
