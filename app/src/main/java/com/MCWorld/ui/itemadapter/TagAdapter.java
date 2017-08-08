package com.MCWorld.ui.itemadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.TagInfo;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.List;

public class TagAdapter<T> extends BaseAdapter implements b {
    private long aSw = 0;
    private a aSx;
    private List<T> aab;
    private OnClickListener mClickListener = new 1(this);
    private Context mContext;
    private LayoutInflater mInflater;

    public interface a {
        void j(long j, String str);
    }

    public TagAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setData(List<T> data) {
        this.aab = data;
        notifyDataSetChanged();
    }

    public a Hy() {
        return this.aSx;
    }

    public void a(a mTagSelectListener) {
        this.aSx = mTagSelectListener;
    }

    public int getCount() {
        if (this.aab == null) {
            return 0;
        }
        return this.aab.size();
    }

    public Object getItem(int position) {
        if (this.aab == null) {
            return null;
        }
        return this.aab.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.item_tag, parent, false);
            holder = new b(this);
            holder.aSz = (TextView) convertView.findViewById(g.txt);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        T data = getItem(position);
        long id = 0;
        String name = null;
        if (data instanceof TagInfo) {
            TagInfo info = (TagInfo) data;
            id = info.getID();
            name = info.getName();
            holder.aSz.setTag(info);
        } else if (data instanceof MyMapCateItem) {
            MyMapCateItem info2 = (MyMapCateItem) data;
            id = info2.id;
            name = info2.title;
            holder.aSz.setTag(info2);
        }
        holder.aSz.setText(name);
        holder.aSz.setOnClickListener(this.mClickListener);
        if (id == this.aSw) {
            holder.aSz.setBackgroundResource(f.tag_selected);
            holder.aSz.setTextColor(d.getColor(this.mContext, c.textColorGreen2));
        } else {
            holder.aSz.setBackgroundResource(f.tag_unselected);
            holder.aSz.setTextColor(d.getColor(this.mContext, 16842808));
        }
        return convertView;
    }

    public void a(j setter) {
        setter.bi(g.avatar, c.valBrightness).bh(g.nick, 16842808);
    }

    public void Hz() {
        notifyDataSetChanged();
    }

    public void clear() {
        this.aSw = 0;
    }
}
