package com.huluxia.ui.itemadapter.map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mcmap.c;
import com.huluxia.mojang.converter.InventorySlot;
import com.huluxia.mojang.converter.ItemStack;
import com.huluxia.service.i;
import com.huluxia.utils.aj;
import java.util.List;

public class StockItemAdapter extends ArrayAdapter<InventorySlot> implements OnItemClickListener {
    private Activity aTX;
    private Boolean aTY = Boolean.valueOf(false);

    public StockItemAdapter(Activity context, List<InventorySlot> objects, Boolean isDeleting) {
        super(context, R.layout.item_stock, R.id.title, objects);
        this.aTX = context;
        this.aTY = isDeleting;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        InventorySlot slot = (InventorySlot) getItem(position);
        ItemStack item = slot.getContents();
        String title = aj.aD(item.getTypeId(), item.getDurability());
        String logoName = aj.aE(item.getTypeId(), item.getDurability());
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.number)).setText("数量：" + String.valueOf(item.getAmount()));
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
        v.setTag(slot);
        return v;
    }

    public void onItemClick(AdapterView<?> adapterView, View v, int arg2, long arg3) {
        InventorySlot slot = (InventorySlot) v.getTag();
        if (((ImageView) v.findViewById(R.id.cross)).getVisibility() == 0) {
            i.a(Byte.valueOf(slot.getSlot()));
        } else {
            c.a(this.aTX, slot);
        }
    }
}
