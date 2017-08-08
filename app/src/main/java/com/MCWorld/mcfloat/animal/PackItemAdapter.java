package com.MCWorld.mcfloat.animal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.mojang.converter.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class PackItemAdapter extends BaseAdapter {
    private List<ItemStack> aaO = new ArrayList();
    private int aaP = -1;

    private class a {
        int RY;
        TextView RZ;
        ImageView Sa;
        ImageView Tu;
        final /* synthetic */ PackItemAdapter aaQ;

        private a(PackItemAdapter packItemAdapter) {
            this.aaQ = packItemAdapter;
        }
    }

    public void b(List<ItemStack> data, boolean isClear) {
        if (isClear) {
            this.aaO.clear();
        }
        this.aaO.addAll(data);
        notifyDataSetChanged();
    }

    public void fB(int checkIndex) {
        this.aaP = checkIndex;
        notifyDataSetChanged();
    }

    public int ug() {
        return this.aaP;
    }

    public int getCount() {
        return this.aaO.size();
    }

    public Object getItem(int position) {
        if (position == -1 || position >= this.aaO.size()) {
            return null;
        }
        return this.aaO.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floatitem_exex, null);
            holder = new a();
            holder.RZ = (TextView) convertView.findViewById(R.id.floatItemItemName);
            holder.Sa = (ImageView) convertView.findViewById(R.id.floatItemItemLogo);
            holder.Tu = (ImageView) convertView.findViewById(R.id.floatItemItemSelectedFlag);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, position);
        return convertView;
    }

    public void a(a viewHolder, int position) {
        viewHolder.Tu.setVisibility(position == this.aaP ? 0 : 8);
        ItemStack info = (ItemStack) getItem(position);
        if (info.getItemId() == 0) {
            viewHolder.RY = -1;
            viewHolder.RZ.setText("");
            viewHolder.Sa.setVisibility(4);
            return;
        }
        viewHolder.RY = position;
        String itemName = info.getItemName();
        if (info.getItemCount() > 0) {
            itemName = itemName + String.format("x%d", new Object[]{Integer.valueOf(info.getItemCount())});
        }
        viewHolder.RZ.setText(itemName);
        viewHolder.Sa.setVisibility(0);
        viewHolder.Sa.setImageResource(info.getItemImgId());
    }
}
