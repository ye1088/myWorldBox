package com.MCWorld.mcfloat.animal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.MCWorld.framework.R;
import com.MCWorld.mojang.converter.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class PopEquipmentAdapter extends BaseAdapter {
    private List<ItemStack> aab = new ArrayList();
    private Context mContext;

    public PopEquipmentAdapter(Context context) {
        this.mContext = context;
    }

    public void c(List<ItemStack> data, boolean isClear) {
        if (isClear) {
            this.aab.clear();
        }
        if (data != null) {
            this.aab.addAll(data);
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        return this.aab.size();
    }

    public Object getItem(int position) {
        if (position < 0 || position >= this.aab.size()) {
            return null;
        }
        return this.aab.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ImageView(this.mContext);
            convertView.setBackgroundResource(R.drawable.bg_animal_strengthen_black_box);
        }
        ((ImageView) convertView).setImageDrawable(this.mContext.getResources().getDrawable(((ItemStack) this.aab.get(position)).getItemImgId()));
        return convertView;
    }
}
