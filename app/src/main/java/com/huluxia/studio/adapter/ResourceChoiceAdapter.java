package com.huluxia.studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;

public class ResourceChoiceAdapter extends BaseAdapter implements b {
    private Context mContext;
    private LayoutInflater mInflater;

    public ResourceChoiceAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    public void a(j setter) {
    }
}
