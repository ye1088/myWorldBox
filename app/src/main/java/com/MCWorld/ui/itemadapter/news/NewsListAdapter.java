package com.MCWorld.ui.itemadapter.news;

import android.widget.BaseAdapter;
import com.MCWorld.module.news.c;
import com.simple.colorful.b;
import java.util.ArrayList;
import java.util.List;

public abstract class NewsListAdapter extends BaseAdapter implements b {
    protected List<c> aab = new ArrayList();

    public void a(ArrayList<c> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }
}
