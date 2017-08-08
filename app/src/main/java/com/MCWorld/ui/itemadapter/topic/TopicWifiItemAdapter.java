package com.MCWorld.ui.itemadapter.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.data.topic.VideoInfo;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.t;
import com.MCWorld.utils.ad;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.az;
import com.MCWorld.utils.ba;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class TopicWifiItemAdapter extends BaseAdapter implements b {
    private static final int aWY = 1;
    private static final int aWZ = 2;
    private List<Object> aVy;
    private int aXC;
    private boolean aXD;
    private boolean aXa;
    private boolean aXb;
    private Context context;
    private LayoutInflater mInflater;

    public TopicWifiItemAdapter(Context context, ArrayList<Object> objects) {
        this.mInflater = null;
        this.aXC = 0;
        this.aXa = false;
        this.aXb = false;
        this.aXD = false;
        this.aVy = objects;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.aXC = at.getScreenPixWidth(this.context) - at.dipToPx(this.context, 114);
    }

    public TopicWifiItemAdapter(Context context, ArrayList<Object> objects, boolean hideHit) {
        this(context, objects);
        this.aXa = hideHit;
    }

    public TopicWifiItemAdapter(Context context, ArrayList<Object> objects, boolean hideHit, boolean showAuditState) {
        this(context, (ArrayList) objects, hideHit);
        this.aXb = showAuditState;
    }

    public TopicWifiItemAdapter(Context context, boolean showResType, ArrayList<Object> objects) {
        this(context, objects);
        this.aXD = showResType;
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
        int type = getItemViewType(position);
        TopicItem data = (TopicItem) getItem(position);
        if (convertView == null) {
            if (type == 1) {
                convertView = this.mInflater.inflate(i.listitem_topic_top, null);
            } else {
                convertView = this.mInflater.inflate(i.listitem_topic_other, null);
            }
        }
        if (type == 1) {
            d(convertView, data);
        } else if ((ad.empty(data.getImages()) || data.getImages().get(0) == null) && ad.empty(data.getVoice())) {
            e(convertView, data);
        } else {
            f(convertView, data);
        }
        return convertView;
    }

    private void d(View view, TopicItem data) {
        ((EmojiTextView) view.findViewById(g.title_top)).setText(data.getTitle());
        ImageView iv_tag = (ImageView) view.findViewById(g.iv_tag);
        if (data.isNotice()) {
            iv_tag.setImageResource(f.ic_notice);
        } else if (data.isWeight()) {
            iv_tag.setImageResource(f.ic_weight);
        }
    }

    private void e(View view, TopicItem data) {
        view.findViewById(g.topic_w).setVisibility(0);
        view.findViewById(g.topic_pic).setVisibility(8);
        if (data.getLine() == 1) {
            view.findViewById(g.topicListLine).setVisibility(0);
        } else {
            view.findViewById(g.topicListLine).setVisibility(8);
        }
        view.setTag(Long.valueOf(data.getPostID()));
        ((EmojiTextView) view.findViewById(g.nick_w)).setText(aw.W(data.getUserInfo().nick, 4));
        ((TextView) view.findViewById(g.publish_time_w)).setText(az.bD(data.getActiveTime()));
        TextView hit_num = (TextView) view.findViewById(g.hit_num_w);
        if (this.aXa) {
            hit_num.setVisibility(8);
        } else {
            hit_num.setText(Long.toString(data.getHit()));
        }
        TextView comment_num = (TextView) view.findViewById(g.comment_num_w);
        if (this.aXa) {
            comment_num.setVisibility(8);
        } else {
            comment_num.setText(Long.toString(data.getCommentCount()));
        }
        TextView auditView = (TextView) view.findViewById(g.audit_state_w);
        if (this.aXb) {
            auditView.setVisibility(0);
            a(auditView, data);
        } else {
            auditView.setVisibility(8);
        }
        ((EmojiTextView) view.findViewById(g.title_w)).setText(ba.d(this.context, data));
        ((EmojiTextView) view.findViewById(g.tv_content_w)).setText(data.getDetail());
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

    private void f(View view, TopicItem data) {
        view.findViewById(g.topic_pic).setVisibility(0);
        view.findViewById(g.topic_w).setVisibility(8);
        if (data.getLine() == 1) {
            view.findViewById(g.topicListLine).setVisibility(0);
        } else {
            view.findViewById(g.topicListLine).setVisibility(8);
        }
        view.setTag(Long.valueOf(data.getPostID()));
        PaintView iv_pic = (PaintView) view.findViewById(g.iv_pic);
        TextView tv_pic = (TextView) view.findViewById(g.tv_pic);
        TextView tv_res_type = (TextView) view.findViewById(g.tv_resource_type);
        ImageView iv_video_tag = (ImageView) view.findViewById(g.iv_video_tag);
        tv_pic.setVisibility(8);
        if (!ad.empty(data.getVoice())) {
            iv_video_tag.setVisibility(0);
            tv_pic.setVisibility(8);
            VideoInfo videoInfo = VideoInfo.convertFromString(data.getVoice());
            if (videoInfo != null) {
                t.a(iv_pic, videoInfo.imgurl, 0.0f);
            }
        } else if (!UtilsFunction.empty(data.getImages())) {
            t.a(iv_pic, (String) data.getImages().get(0), 0.0f);
            iv_video_tag.setVisibility(8);
            int picNum = data.getImages().size();
            if (picNum > 1) {
                tv_pic.setVisibility(0);
                tv_pic.setText(String.valueOf(picNum) + "图");
            }
            if (data == null || data.ext == null || data.ext.biz == null || data.ext.subBiz == null || !data.ext.biz.equals("mc") || !this.aXD) {
                tv_res_type.setVisibility(8);
            } else if (data.ext.subBiz.equalsIgnoreCase("map")) {
                tv_res_type.setVisibility(0);
                tv_res_type.setText("地图");
            } else if (data.ext.subBiz.equalsIgnoreCase("js")) {
                tv_res_type.setVisibility(0);
                tv_res_type.setText("JS");
            } else if (data.ext.subBiz.equalsIgnoreCase("skin")) {
                tv_res_type.setVisibility(0);
                tv_res_type.setText("皮肤");
            } else if (data.ext.subBiz.equalsIgnoreCase("wood")) {
                tv_res_type.setVisibility(0);
                tv_res_type.setText("材质");
            }
        }
        ((EmojiTextView) view.findViewById(g.nick)).setText(aw.W(data.getUserInfo().nick, 4));
        ((TextView) view.findViewById(g.publish_time)).setText(az.bD(data.getActiveTime()));
        TextView hit_num = (TextView) view.findViewById(g.hit_num);
        if (this.aXa) {
            hit_num.setVisibility(8);
        } else {
            hit_num.setText(Long.toString(data.getHit()));
        }
        TextView comment_num = (TextView) view.findViewById(g.comment_num);
        if (this.aXa) {
            comment_num.setVisibility(8);
        } else {
            comment_num.setText(Long.toString(data.getCommentCount()));
        }
        TextView auditView = (TextView) view.findViewById(g.audit_state);
        if (this.aXb) {
            auditView.setVisibility(0);
            a(auditView, data);
        } else {
            auditView.setVisibility(8);
        }
        EmojiTextView title = (EmojiTextView) view.findViewById(g.title);
        title.setText(ba.d(this.context, data));
        EmojiTextView tv_content = (EmojiTextView) view.findViewById(g.tv_content);
        tv_content.setText(data.getDetail());
        EmojiTextView tv_content2 = (EmojiTextView) view.findViewById(g.tv_content2);
        tv_content2.setText(data.getDetail());
        if (((int) title.getPaint().measureText(title.getText().toString())) > this.aXC) {
            tv_content.setVisibility(0);
            tv_content2.setVisibility(8);
            return;
        }
        tv_content.setVisibility(8);
        tv_content2.setVisibility(0);
    }

    public void a(j setter) {
        if (setter != null) {
            setter.bg(g.item_container_top, c.listSelector).bf(g.item_split_top, c.splitColor).bg(g.topic_w, c.listSelector).bg(g.topic_pic, c.listSelector).bf(g.item_split_other, c.splitColor).bf(g.topicListLine, c.splitColorDim).bh(g.title_top, 16842808).bh(g.title_w, 16842808).bh(g.tv_content_w, 16843282).bh(g.nick_w, 16843282).bh(g.publish_time_w, 16843282).bh(g.hit_num_w, 16843282).ac(g.hit_num_w, c.drawableViewCount, 1).bh(g.comment_num_w, 16843282).ac(g.comment_num_w, c.drawableCommentCount, 1).bh(g.title, 16842808).bh(g.tv_content, 16843282).bh(g.nick, 16843282).bh(g.publish_time, 16843282).bh(g.hit_num, 16843282).ac(g.hit_num, c.drawableViewCount, 1).bh(g.comment_num, 16843282).ac(g.comment_num, c.drawableCommentCount, 1).bi(g.iv_pic, c.valBrightness).bi(g.iv_tag, c.valBrightness);
        }
    }

    public void clear() {
        if (this.aVy != null) {
            this.aVy.clear();
        }
        notifyDataSetChanged();
    }
}
