package com.MCWorld.ui.bbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.utils.ad;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.a.a;
import com.simple.colorful.c;

public class TopicDetailTitle extends LinearLayout implements c {
    public TopicDetailTitle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_topic_top, this);
    }

    public void setTopicDetail(TopicItem data) {
        if (data != null) {
            ((EmojiTextView) findViewById(g.title)).setText(data.getTitle());
            TextView tv_class = (TextView) findViewById(g.tv_class);
            if (data.getCategory() != null) {
                tv_class.setVisibility(0);
                tv_class.setText(data.getCategory().getTitle());
                tv_class.setOnClickListener(new 1(this, data));
            }
            ((TextView) findViewById(g.hit_num)).setText(Long.toString(data.getHit()));
            ((TextView) findViewById(g.comment_num)).setText(Long.toString(data.getCommentCount()));
            if (ad.empty(data.getVoice())) {
                findViewById(g.iv_video).setVisibility(8);
                if (UtilsFunction.empty(data.getImages())) {
                    findViewById(g.iv_tu).setVisibility(8);
                } else {
                    findViewById(g.iv_tu).setVisibility(0);
                }
            } else {
                findViewById(g.iv_video).setVisibility(0);
                findViewById(g.iv_tu).setVisibility(8);
            }
            if (data.getCommentCount() > 200) {
                findViewById(g.iv_hot).setVisibility(0);
            } else {
                findViewById(g.iv_hot).setVisibility(8);
            }
            if (data.isGood()) {
                findViewById(g.iv_digest).setVisibility(0);
            } else {
                findViewById(g.iv_digest).setVisibility(8);
            }
            if (System.currentTimeMillis() - data.getCreateTime() < 43200000) {
                findViewById(g.iv_new).setVisibility(0);
            } else {
                findViewById(g.iv_new).setVisibility(8);
            }
        }
    }

    public a b(a builder) {
        TextView hitNum = (TextView) findViewById(g.hit_num);
        TextView commentNum = (TextView) findViewById(g.comment_num);
        TextView classBtn = (TextView) findViewById(g.tv_class);
        builder.a((TextView) findViewById(g.title), 16842806).a(hitNum, 16842808).a(commentNum, 16842808).a(hitNum, b.c.drawableViewCount, 1).a(commentNum, b.c.drawableCommentCount, 1).i(findViewById(g.topic_top), b.c.splitColorDim).a(classBtn, 16842809).a(classBtn, b.c.drawableClassArrowNext, 2).j(classBtn, b.c.backgroundTopicClass).i(findViewById(g.split_topic_top), b.c.splitColor).d((ImageView) findViewById(g.iv_tu), b.c.valBrightness).d((ImageView) findViewById(g.iv_video), b.c.valBrightness).d((ImageView) findViewById(g.iv_digest), b.c.valBrightness).d((ImageView) findViewById(g.iv_new), b.c.valBrightness).d((ImageView) findViewById(g.iv_hot), b.c.valBrightness);
        return builder;
    }

    public void FG() {
    }
}
