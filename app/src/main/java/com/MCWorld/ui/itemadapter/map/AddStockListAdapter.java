package com.MCWorld.ui.itemadapter.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.MCWorld.framework.R;
import com.MCWorld.mojang.converter.ItemStack;
import com.MCWorld.utils.at;
import com.MCWorld.widget.listview.GridViewNotScroll;
import java.util.ArrayList;
import java.util.List;

public class AddStockListAdapter extends BaseAdapter {
    private int DIV = 3;
    private Context ctx;
    private List<Object> objs;

    public AddStockListAdapter(Context context, List<Object> objects) {
        this.objs = objects;
        this.ctx = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewNotScroll gridView = (GridViewNotScroll) convertView;
        if (gridView == null) {
            gridView = new GridViewNotScroll(this.ctx);
            gridView.setBackgroundResource(R.color.transparent);
            gridView.setNumColumns(this.DIV);
            int dp_1 = at.dipToPx(this.ctx, 1);
            int dp_10 = dp_1 * 10;
            gridView.setPadding(0, 0, 0, (int) (((double) dp_1) * 0.5d));
            gridView.setHorizontalSpacing(dp_10);
            gridView.setVerticalSpacing(dp_10);
            gridView.setSelector(this.ctx.getResources().getDrawable(R.drawable.bglistitem_selector_topic));
        }
        List<ItemStack> cates = new ArrayList();
        for (int i = 0; i < this.DIV; i++) {
            int pos = (this.DIV * position) + i;
            if (pos < this.objs.size()) {
                cates.add((ItemStack) this.objs.get(pos));
            }
        }
        AddStockItemAdapter adapter = new AddStockItemAdapter(this.ctx, cates);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(adapter);
        return gridView;
    }

    public int getCount() {
        int icount = this.objs.size() / this.DIV;
        if (this.objs.size() % this.DIV > 0) {
            return icount + 1;
        }
        return icount;
    }

    public Object getItem(int position) {
        return this.objs.get(this.DIV * position);
    }

    public long getItemId(int position) {
        return 0;
    }
}
