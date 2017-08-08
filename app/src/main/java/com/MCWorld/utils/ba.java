package com.MCWorld.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.BaseAdapter;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.c;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.ui.itemadapter.topic.SimpleTopicItemAdapter;
import com.MCWorld.ui.itemadapter.topic.Topic2GItemAdapter;
import com.MCWorld.ui.itemadapter.topic.TopicWifiItemAdapter;
import com.MCWorld.utils.ah.a;
import com.simple.colorful.d;
import java.util.ArrayList;

/* compiled from: UtilsTopic */
public class ba {
    public static BaseAdapter a(Context context, boolean showResType, ArrayList<Object> objects) {
        if (br(context)) {
            return new TopicWifiItemAdapter(context, showResType, (ArrayList) objects);
        }
        return new Topic2GItemAdapter(context, objects);
    }

    public static BaseAdapter c(Context context, ArrayList<Object> objects) {
        if (br(context)) {
            return new TopicWifiItemAdapter(context, objects);
        }
        return new Topic2GItemAdapter(context, objects);
    }

    public static BaseAdapter a(Context context, ArrayList<Object> objects, boolean hideHit) {
        if (br(context)) {
            return new TopicWifiItemAdapter(context, (ArrayList) objects, hideHit);
        }
        return new Topic2GItemAdapter(context, objects, hideHit);
    }

    public static Spannable c(Context context, TopicItem data) {
        a span;
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (!ad.empty(data.getVoice())) {
            span = new a(context, d.r(context, c.drawableTopicVd));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        } else if (!UtilsFunction.empty(data.getImages())) {
            span = new a(context, d.r(context, c.drawableTopicTu));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        }
        if (data.isGood()) {
            span = new a(context, d.r(context, c.drawableTopicDigest));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        }
        if (data.getCommentCount() > 200) {
            span = new a(context, d.r(context, c.drawableTopicHot));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        }
        if (System.currentTimeMillis() - data.getCreateTime() < 43200000) {
            span = new a(context, d.r(context, c.drawableTopicNew));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        }
        if (data.getScore() != 0) {
            String sScore;
            if (data.getScore() > 0) {
                sScore = "+" + Integer.toString(data.getScore());
            } else {
                sScore = Integer.toString(data.getScore());
            }
            span = new a(context, new b(sScore, context.getResources().getColor(b.d.red)));
            ssb.insert(0, sScore);
            ssb.setSpan(span, 0, sScore.length(), 33);
        }
        return ssb;
    }

    public static Spannable d(Context context, TopicItem data) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (data.isGood()) {
            a span = new a(context, d.r(context, c.drawableTopicDigest));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        }
        if (data.getCommentCount() > 200) {
            span = new a(context, d.r(context, c.drawableTopicHot));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        }
        if (System.currentTimeMillis() - data.getCreateTime() < 43200000) {
            span = new a(context, d.r(context, c.drawableTopicNew));
            ssb.insert(0, "1");
            ssb.setSpan(span, 0, 1, 33);
        }
        if (data.getScore() != 0) {
            String sScore;
            if (data.getScore() > 0) {
                sScore = "+" + Integer.toString(data.getScore());
            } else {
                sScore = Integer.toString(data.getScore());
            }
            b shapeDrawable = new b(sScore, d.getColor(context, c.backgroundColorTopicScore));
            shapeDrawable.setTextColor(d.getColor(context, c.textColorTopicScore));
            span = new a(context, shapeDrawable);
            ssb.insert(0, sScore);
            ssb.setSpan(span, 0, sScore.length(), 33);
        }
        ssb.append(data.getTitle());
        return ssb;
    }

    private static boolean br(Context ctx) {
        int mod = ah.KZ().Lm();
        if (mod == a.bmd) {
            return false;
        }
        if (mod == a.ALL) {
            return true;
        }
        if (ak.isWifiConnected(ctx)) {
            return true;
        }
        return false;
    }

    public static BaseAdapter d(Context context, ArrayList<Object> objects) {
        return new SimpleTopicItemAdapter(context, objects);
    }
}
