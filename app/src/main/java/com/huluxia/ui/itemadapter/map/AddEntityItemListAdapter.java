package com.huluxia.ui.itemadapter.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.huluxia.framework.R;
import com.huluxia.mojang.entity.EntityItem;
import com.huluxia.utils.at;
import com.huluxia.widget.listview.GridViewNotScroll;
import java.util.ArrayList;
import java.util.List;

public class AddEntityItemListAdapter extends BaseAdapter {
    private int DIV = 3;
    private Context ctx;
    private List<Object> objs;

    public AddEntityItemListAdapter(Context context, List<Object> objects) {
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
        List<EntityItem> cates = new ArrayList();
        for (int i = 0; i < this.DIV; i++) {
            int pos = (this.DIV * position) + i;
            if (pos < this.objs.size()) {
                cates.add((EntityItem) this.objs.get(pos));
            }
        }
        AddEntityItemAdapter adapter = new AddEntityItemAdapter(this.ctx, cates);
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
