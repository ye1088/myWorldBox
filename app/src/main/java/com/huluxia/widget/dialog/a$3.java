package com.huluxia.widget.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;

/* compiled from: AppInfoDialog */
class a$3 extends BaseAdapter {
    final /* synthetic */ a bwh;

    a$3(a this$0) {
        this.bwh = this$0;
    }

    public long getItemId(int pos) {
        return 0;
    }

    public Object getItem(int pos) {
        return a.d(this.bwh).get(pos);
    }

    public int getCount() {
        return a.d(this.bwh).size();
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(a.e(this.bwh)).inflate(i.item_gridview_plugin, null);
        }
        a$b obj = (a$b) getItem(pos);
        if (obj == null) {
            return null;
        }
        ((ImageView) convertView.findViewById(g.ChildPluginItemImage)).setImageDrawable(obj.bwi);
        ((TextView) convertView.findViewById(g.ChildPluginItemName)).setText(obj.bwl);
        convertView.findViewById(g.ChildPluginItemRunning).setVisibility(8);
        return convertView;
    }
}
