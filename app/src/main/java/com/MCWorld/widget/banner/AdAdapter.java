package com.MCWorld.widget.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class AdAdapter<T> extends BaseAdapter {
    protected List<T> aab = new ArrayList();
    protected Context mContext;

    public abstract View getView(int i, View view, ViewGroup viewGroup);

    public AdAdapter(Context context) {
        this.mContext = context;
    }

    private void d(List<T> data, boolean clear) {
        if (data != null && data.size() != 0) {
            if (clear) {
                this.aab.clear();
            }
            this.aab.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void setData(List<T> data) {
        d(data, true);
    }

    public void K(List<T> data) {
        d(data, false);
    }

    public List<T> getData() {
        return this.aab;
    }

    public int getCount() {
        if (this.aab.size() <= 1) {
            return this.aab.size();
        }
        return Integer.MAX_VALUE;
    }

    public T getItem(int position) {
        if (this.aab.size() == 0) {
            return null;
        }
        return this.aab.get(position % this.aab.size());
    }

    public long getItemId(int position) {
        return (long) position;
    }
}
