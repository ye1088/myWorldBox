package com.huluxia.ui.bbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.t;
import com.simple.colorful.a.a;
import com.simple.colorful.c;

public class TopicListTitle extends LinearLayout implements c {
    public TopicListTitle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_topiclist_title, this);
    }

    public void setTopicCategory(TopicCategory data) {
        t.a((PaintView) findViewById(g.avatar), data.getIcon(), (float) UtilsScreen.dipToPx(getContext(), 10));
        TextView topic_today_num = (TextView) findViewById(g.topic_today_num);
        ((TextView) findViewById(g.hot_today_num)).setText(data.getViewCountFormated());
        topic_today_num.setText(data.getPostCountFormated());
        String value = "";
        TextView moderator = (TextView) findViewById(g.moderator);
        if (data.getModerator().isEmpty()) {
            value = "暂无版主";
        } else {
            value = "";
        }
        for (UserBaseInfo user : data.getModerator()) {
            value = value + user.getNick() + "、";
        }
        moderator.setText("版主:" + value);
    }

    public a b(a builder) {
        builder.a((TextView) findViewById(g.topic_title), 16842806).a((TextView) findViewById(g.hot_today_num), 16842808).a((TextView) findViewById(g.moderator), 16842808).a((TextView) findViewById(g.tv_daren), 16842808).a((TextView) findViewById(g.tv_signin), 16842808).a((TextView) findViewById(g.tv_search), 16842808).d((ImageView) findViewById(g.avatar), b.c.valBrightness).j(findViewById(g.btn_daren), b.c.listSelector).j(findViewById(g.btn_signin), b.c.listSelector).j(findViewById(g.btn_search), b.c.listSelector).j(findViewById(g.ic_add_class), b.c.drawableSubscribe).i(findViewById(g.divider_1), b.c.splitColor).i(findViewById(g.block_split_top), b.c.splitColor).i(findViewById(g.block_split_bottom), b.c.splitColor).i(findViewById(g.split_vertical), b.c.splitColor).i(findViewById(g.split_vertical2), b.c.splitColor).i(findViewById(g.view_divider), b.c.splitColorDim).d((ImageView) findViewById(g.avatar), b.c.valBrightness);
        return builder;
    }

    public void FG() {
    }
}
