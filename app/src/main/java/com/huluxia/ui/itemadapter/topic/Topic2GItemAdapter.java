package com.huluxia.ui.itemadapter.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.utils.aw;
import com.huluxia.utils.az;
import com.huluxia.utils.ba;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class Topic2GItemAdapter extends BaseAdapter implements b {
    private static final int aWY = 1;
    private static final int aWZ = 2;
    private List<Object> aVy;
    private boolean aXa;
    private boolean aXb;
    private Context context;
    private LayoutInflater mInflater;

    public Topic2GItemAdapter(Context context, ArrayList<Object> objects) {
        this.mInflater = null;
        this.aXa = false;
        this.aXb = false;
        this.aVy = objects;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public Topic2GItemAdapter(Context context, ArrayList<Object> objects, boolean hideHit) {
        this(context, objects);
        this.aXa = hideHit;
    }

    public Topic2GItemAdapter(Context context, ArrayList<Object> objects, boolean hideHit, boolean showAuditState) {
        this(context, objects, hideHit);
        this.aXb = showAuditState;
    }

    public int getItemViewType(int position) {
        TopicItem item = (TopicItem) getItem(position);
        if (item.isNotice() || item.isWeight()) {
            return 1;
        }
        return 2;
    }

    public int getViewTypeCount() {
        return 3;
    }

    public int getCount() {
        return this.aVy.size();
    }

    public Object getItem(int position) {
        return this.aVy.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TopicItem data = (TopicItem) getItem(position);
        if (getItemViewType(position) == 1) {
            b holder;
            if (convertView == null) {
                holder = new b();
                convertView = this.mInflater.inflate(i.listitem_topic_top, null);
                holder.aXj = (EmojiTextView) convertView.findViewById(g.title_top);
                holder.aXk = (ImageView) convertView.findViewById(g.iv_tag);
                convertView.setTag(holder);
            } else {
                holder = (b) convertView.getTag();
            }
            a(holder, data);
        } else {
            a holder2;
            if (convertView == null) {
                holder2 = new a();
                convertView = this.mInflater.inflate(i.listitem_topic2g_other, null);
                holder2.aXc = convertView.findViewById(g.topicListLine);
                holder2.aXd = (TextView) convertView.findViewById(g.topic_flag);
                holder2.aFo = (EmojiTextView) convertView.findViewById(g.title);
                holder2.aJX = (EmojiTextView) convertView.findViewById(g.nick);
                holder2.aXe = (TextView) convertView.findViewById(g.publish_time);
                holder2.aXf = (TextView) convertView.findViewById(g.hit_num);
                holder2.aXg = (TextView) convertView.findViewById(g.praise_num);
                holder2.aXh = (TextView) convertView.findViewById(g.comment_num);
                holder2.aXi = (TextView) convertView.findViewById(g.audit_state_w);
                convertView.setTag(holder2);
            } else {
                holder2 = (a) convertView.getTag();
            }
            a(holder2, data);
        }
        return convertView;
    }

    private void a(b holder, TopicItem data) {
        holder.aXj.setText(data.getTitle());
        if (data.isNotice()) {
            holder.aXk.setImageResource(f.ic_notice);
        } else if (data.isWeight()) {
            holder.aXk.setImageResource(f.ic_weight);
        }
    }

    private void a(a holder, TopicItem data) {
        if (data.getLine() == 1) {
            holder.aXc.setVisibility(0);
        } else {
            holder.aXc.setVisibility(8);
        }
        holder.aXd.setText(ba.c(this.context, data));
        holder.aFo.setText(data.getTitle());
        holder.aJX.setText(aw.W(data.getUserInfo().nick, 8));
        holder.aXe.setText(az.bD(data.getActiveTime()));
        if (this.aXa) {
            holder.aXf.setText("");
        } else {
            holder.aXf.setText(Long.toString(data.getHit()));
        }
        if (this.aXa) {
            holder.aXg.setText("");
        } else {
            holder.aXg.setText(Long.toString(data.getPraise()));
        }
        if (this.aXa) {
            holder.aXh.setText("");
        } else {
            holder.aXh.setText(Long.toString(data.getCommentCount()));
        }
        if (this.aXb) {
            holder.aXi.setVisibility(0);
            a(holder.aXi, data);
            return;
        }
        holder.aXi.setVisibility(8);
    }

    private void a(TextView auditView, TopicItem data) {
        if (data.getStatus() == 6) {
            auditView.setText(m.audit_reject);
            auditView.setTextColor(this.context.getResources().getColor(d.audit_reject));
            auditView.setBackgroundResource(f.bg_stroke_rect_red);
        } else if (data.getStatus() == 1) {
            auditView.setText(m.auditing);
            auditView.setTextColor(this.context.getResources().getColor(d.auditing));
            auditView.setBackgroundResource(f.bg_stroke_rect_grey);
        } else {
            auditView.setVisibility(8);
        }
    }

    public void a(j setter) {
        if (setter != null) {
            setter.bg(g.item_container_top, c.listSelector).bg(g.item_container_other, c.listSelector).bf(g.item_split_top, c.splitColor).bh(g.title_top, 16842808).bf(g.item_split_other, c.splitColor).bf(g.topicListLine, c.splitColorDim).bh(g.title, 16842808).bh(g.nick, 16843282).bh(g.publish_time, 16843282).bh(g.hit_num, 16843282).ac(g.hit_num, c.drawableViewCount, 1).bh(g.comment_num, 16843282).ac(g.comment_num, c.drawableCommentCount, 1).bi(g.iv_tag, c.valBrightness);
        }
    }

    public void clear() {
        if (this.aVy != null) {
            this.aVy.clear();
        }
        notifyDataSetChanged();
    }
}
