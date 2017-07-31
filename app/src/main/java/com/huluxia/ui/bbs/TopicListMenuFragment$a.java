package com.huluxia.ui.bbs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;

class TopicListMenuFragment$a extends BaseAdapter {
    final /* synthetic */ TopicListMenuFragment aQy;

    private class a {
        final /* synthetic */ TopicListMenuFragment$a aQA;
        public TextView aQz;

        private a(TopicListMenuFragment$a topicListMenuFragment$a) {
            this.aQA = topicListMenuFragment$a;
        }
    }

    private TopicListMenuFragment$a(TopicListMenuFragment topicListMenuFragment) {
        this.aQy = topicListMenuFragment;
    }

    public /* synthetic */ Object getItem(int i) {
        return kB(i);
    }

    public int getCount() {
        return (TopicListMenuFragment.a(this.aQy) == null || TopicListMenuFragment.a(this.aQy).size() == 0) ? 0 : TopicListMenuFragment.a(this.aQy).size();
    }

    public TopicListMenuFragment$c kB(int position) {
        return (TopicListMenuFragment$c) TopicListMenuFragment.a(this.aQy).get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        TopicListMenuFragment$c info = kB(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.aQy.getActivity()).inflate(i.listitem_topic_menu_item, parent, false);
            holder = new a();
            holder.aQz = (TextView) convertView.findViewById(g.menu_text);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.aQz.setText(info.text);
        if (info.selected) {
            convertView.setBackgroundColor(this.aQy.getResources().getColor(d.white_transparent_1));
        } else {
            convertView.setBackgroundDrawable(this.aQy.getResources().getDrawable(f.topic_list_menu_item_selector));
        }
        return convertView;
    }
}
