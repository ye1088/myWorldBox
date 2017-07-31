package com.huluxia.ui.itemadapter.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.topic.ScoreItem;
import com.huluxia.t;
import com.huluxia.utils.aw;
import com.huluxia.widget.textview.EmojiTextView;
import java.util.ArrayList;

public class CreditItemAdapter extends BaseAdapter {
    private ArrayList<Object> aWy;
    private Context context;
    private LayoutInflater mInflater = null;

    public CreditItemAdapter(Context context, ArrayList<Object> objects) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.aWy = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.listitem_credit, null);
        }
        final ScoreItem data = (ScoreItem) getItem(position);
        ((LinearLayout) convertView.findViewById(g.ly_sender)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CreditItemAdapter aWA;

            public void onClick(View arg0) {
                t.e(this.aWA.context, data.getUserid());
            }
        });
        EmojiTextView tv_nick = (EmojiTextView) convertView.findViewById(g.tv_nick);
        tv_nick.setText(aw.go(data.getUsername()));
        ((TextView) convertView.findViewById(g.tv_score)).setText(String.valueOf(data.getScore()));
        EmojiTextView tv_reason = (EmojiTextView) convertView.findViewById(g.tv_reason);
        tv_reason.setText(data.getScoreTxt());
        if (data.isIsadmin()) {
            tv_nick.setTextColor(this.context.getResources().getColor(d.red));
            tv_reason.setTextColor(this.context.getResources().getColor(d.red));
        } else {
            tv_nick.setTextColor(this.context.getResources().getColor(d.brown));
            tv_reason.setTextColor(this.context.getResources().getColor(d.black));
        }
        return convertView;
    }

    public int getCount() {
        return this.aWy.size();
    }

    public Object getItem(int position) {
        return this.aWy.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }
}
