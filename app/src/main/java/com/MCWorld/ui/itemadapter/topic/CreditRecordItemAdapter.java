package com.MCWorld.ui.itemadapter.topic;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.topic.ScoreItem;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class CreditRecordItemAdapter extends ArrayAdapter<Object> implements b {
    private Activity mContext;

    public CreditRecordItemAdapter(Activity context, ArrayList<Object> objects) {
        super(context, i.listitem_topic_hulu, g.tv_nick, objects);
        this.mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        final ScoreItem data = (ScoreItem) getItem(position);
        ((EmojiTextView) view.findViewById(g.tv_nick)).setText(aw.go(data.getUsername()));
        t.a((PaintView) view.findViewById(g.avatar), data.getAvatar(), (float) at.dipToPx(this.mContext, 5));
        view.findViewById(g.item_container).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CreditRecordItemAdapter aWB;

            public void onClick(View v) {
                t.a(this.aWB.mContext, data.getUserid(), null);
            }
        });
        EmojiTextView tv_reason = (EmojiTextView) view.findViewById(g.tv_reason);
        tv_reason.setText(aw.W(data.getScoreTxt(), 20));
        TextView tv_score = (TextView) view.findViewById(g.tv_score);
        tv_score.setText(String.valueOf(data.getScore()));
        ImageView iv_hulu = (ImageView) view.findViewById(g.iv_hulu);
        if (data.isIsadmin()) {
            int color = d.getColor(this.mContext, c.textColorTopicHuluDetailRed);
            tv_reason.setTextColor(color);
            tv_score.setTextColor(color);
            iv_hulu.setImageDrawable(d.o(this.mContext, c.drawableHuluRed));
        } else {
            tv_reason.setTextColor(d.getColor(this.mContext, 16843282));
            tv_score.setTextColor(d.getColor(this.mContext, c.textColorGreenTopic));
            iv_hulu.setImageDrawable(d.o(this.mContext, c.drawableHuluGreen));
        }
        return view;
    }

    public void a(j setter) {
        setter.bg(g.item_container, c.listSelector).bf(g.split_item, c.splitColor).bh(g.tv_nick, 16842808).bh(g.tv_reason, 16843282);
    }
}
