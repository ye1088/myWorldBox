package com.MCWorld.ui.itemadapter.map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.MCWorld.framework.R;
import com.MCWorld.mojang.entity.EntityItem;
import com.MCWorld.utils.at;
import com.MCWorld.widget.listview.GridViewNotScroll;
import java.util.ArrayList;
import java.util.List;

public class EntityItemListAdapter extends BaseAdapter {
    private int DIV = 2;
    private Activity aTX;
    private Boolean aTY = Boolean.valueOf(false);
    private List<Object> objs;

    public EntityItemListAdapter(Activity context, List<Object> objects) {
        this.objs = objects;
        this.aTX = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewNotScroll gridView = (GridViewNotScroll) convertView;
        if (gridView == null) {
            gridView = new GridViewNotScroll(this.aTX);
            gridView.setBackgroundResource(R.color.transparent);
            gridView.setNumColumns(this.DIV);
            int dp_1 = at.dipToPx(this.aTX, 1);
            int dp_10 = dp_1 * 10;
            gridView.setPadding(0, 0, 0, (int) (((double) dp_1) * 0.5d));
            gridView.setHorizontalSpacing(dp_10);
            gridView.setVerticalSpacing(0);
            gridView.setSelector(this.aTX.getResources().getDrawable(R.drawable.bglistitem_selector_topic));
        }
        List<EntityItem> cates = new ArrayList();
        for (int i = 0; i < this.DIV; i++) {
            int pos = (this.DIV * position) + i;
            if (pos < this.objs.size()) {
                cates.add((EntityItem) this.objs.get(pos));
            }
        }
        EntityItemAdapter adapter = new EntityItemAdapter(this.aTX, cates, this.aTY);
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

    public void b(Boolean isDeleting) {
        this.aTY = isDeleting;
    }
}
