package com.huluxia.ui.itemadapter.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.topic.SimpleTopicItem;
import com.huluxia.utils.az;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class SimpleTopicItemAdapter extends BaseAdapter implements b {
    private List<Object> aVy;
    private Context context;
    private LayoutInflater mInflater = null;

    public SimpleTopicItemAdapter(Context context, ArrayList<Object> objects) {
        this.aVy = objects;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setData(ArrayList<Object> objects) {
        this.aVy.clear();
        this.aVy.addAll(objects);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aVy.size();
    }

    public Object getItem(int position) {
        return this.aVy.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.listitem_topic_simple, null);
            holder = new a(null);
            a.a(holder, (EmojiTextView) convertView.findViewById(g.title));
            a.a(holder, (TextView) convertView.findViewById(g.cat));
            a.b(holder, (TextView) convertView.findViewById(g.time));
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        SimpleTopicItem data = (SimpleTopicItem) getItem(position);
        a.a(holder).setText(data.title);
        a.b(holder).setText(data.categoryName);
        a.c(holder).setText(az.bD(data.activeTime));
        return convertView;
    }

    public void a(j setter) {
        setter.bg(g.item_container, c.listSelector).bf(g.split_item, c.splitColor).bh(g.title, 16842808).bh(g.time, 16843282).bh(g.cat, 16843282);
    }
}
