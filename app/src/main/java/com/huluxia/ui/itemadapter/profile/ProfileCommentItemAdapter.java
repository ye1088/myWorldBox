package com.huluxia.ui.itemadapter.profile;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.t;
import com.huluxia.utils.as;
import com.huluxia.utils.at;
import com.huluxia.utils.aw;
import com.huluxia.utils.az;
import com.huluxia.widget.photowall.PhotoWall;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.util.ArrayList;
import java.util.List;

public class ProfileCommentItemAdapter extends ArrayAdapter<Object> implements b {
    private int aKj = 0;
    private Context context;

    public ProfileCommentItemAdapter(Context context, ArrayList<Object> objects) {
        super(context, i.include_message_item, g.publish_time, objects);
        this.context = context;
        this.aKj = at.dipToPx(context, 5);
    }

    private void a(View v, UserBaseInfo user) {
        String nickname;
        EmojiTextView nick = (EmojiTextView) v.findViewById(g.nick);
        if (user.getMedalList() == null || user.getMedalList().size() <= 0) {
            nickname = aw.go(user.getNick());
        } else {
            nickname = aw.W(user.getNick(), 4);
        }
        nick.setText(nickname);
        nick.setTextColor(as.a(v.getContext(), user));
    }

    private void b(View view, UserBaseInfo user) {
        TextView user_age = (TextView) view.findViewById(g.user_age);
        user_age.setText(Integer.toString(user.getAge()));
        if (user.getGender() == 1) {
            user_age.setBackgroundResource(f.bg_gender_female);
            user_age.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(f.user_female), null, null, null);
            return;
        }
        user_age.setBackgroundResource(f.bg_gender_male);
        user_age.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(f.user_male), null, null, null);
    }

    @TargetApi(16)
    private void c(View v, UserBaseInfo user) {
        TextView tv_honor = (TextView) v.findViewById(g.tv_honor);
        if (user.getIdentityColor() != 0) {
            tv_honor.setText(user.getIdentityTitle());
            tv_honor.setVisibility(0);
            ((GradientDrawable) tv_honor.getBackground()).setColor(user.getIdentityColor());
            return;
        }
        tv_honor.setVisibility(8);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        CommentItem data = (CommentItem) getItem(position);
        v.setTag(Long.valueOf(data.getCommentID()));
        t.a((PaintView) v.findViewById(g.avatar), data.getUserInfo().getAvatar(), (float) this.aKj);
        TextView floor = (TextView) v.findViewById(g.floor);
        floor.setText(Long.toString(data.getSeq()) + "楼");
        floor.setVisibility(8);
        ((TextView) v.findViewById(g.publish_time)).setText(az.bD(data.getCreateTime()));
        a(v, data.getUserInfo());
        b(v, data.getUserInfo());
        v.findViewById(g.ly_medal).setVisibility(8);
        v.findViewById(g.moderator_flag).setVisibility(8);
        c(v, data.getUserInfo());
        as.a(this.context, (ImageView) v.findViewById(g.iv_role), data.getUserInfo());
        EmojiTextView content = (EmojiTextView) v.findViewById(g.content);
        EmojiTextView retcontent = (EmojiTextView) v.findViewById(g.retcontent);
        PhotoWall photoWall = (PhotoWall) v.findViewById(g.photoWall);
        retcontent.setVisibility(8);
        content.setVisibility(0);
        if (data.getState() == 2) {
            content.setText("此用户已经将评论删除");
            content.setBackgroundResource(f.ret_comment_bg);
            content.setTextColor(v.getContext().getResources().getColor(d.dark_background));
            photoWall.setVisibility(8);
        } else {
            retcontent.setVisibility(8);
            content.setVisibility(0);
            content.setText(data.getText());
            if (data.getUserInfo().getRole() == 2) {
                content.setAutoLinkMask(1);
            }
            if (data.getRefComment() != null) {
                String szRetText = data.getRefComment().getText();
                if (data.getRefComment().getState() == 2) {
                    szRetText = "此评论已经删除";
                }
                retcontent.setText(aw.W("回复 " + aw.W(data.getRefComment().getNick(), 10) + SpecilApiUtil.LINE_SEP + szRetText, 100));
                retcontent.setVisibility(0);
            }
            a(photoWall, data.getImages());
        }
        EmojiTextView credit = (EmojiTextView) v.findViewById(g.credit);
        if (data.getScore() != 0) {
            credit.setVisibility(0);
            credit.setText(Html.fromHtml("送出：<font color='#ff0000'>" + String.valueOf(data.getScore()) + "</font>葫芦"));
        } else {
            credit.setVisibility(8);
        }
        ((EmojiTextView) v.findViewById(g.topic)).setText(aw.W("原帖：" + data.getTopicItem().getTitle(), 25));
        ((EmojiTextView) v.findViewById(g.category)).setText(aw.W("版块：" + data.getTopicCategory().getTitle(), 25));
        return v;
    }

    private void a(PhotoWall photoWall, List<String> images) {
        photoWall.setReadOnly(true);
        if (images.size() > 0) {
            photoWall.PD();
            photoWall.setVisibility(0);
            a(photoWall, images.size());
            for (String url : images) {
                PhotoWall.b un = new PhotoWall.b();
                un.setUrl(url);
                photoWall.a(un);
            }
            return;
        }
        photoWall.setVisibility(8);
    }

    private void a(PhotoWall photoWall, int size) {
        int nitemW = at.getScreenPixWidth(photoWall.getContext()) / 4;
        if (size < 4) {
            photoWall.getLayoutParams().width = nitemW * size;
            photoWall.setMaxPhotoNum(size);
            photoWall.setNumColumns(size);
        } else if (size == 4) {
            photoWall.getLayoutParams().width = nitemW * 2;
            photoWall.setMaxPhotoNum(size);
            photoWall.setNumColumns(2);
        } else {
            photoWall.getLayoutParams().width = nitemW * 3;
            photoWall.setMaxPhotoNum(size);
            photoWall.setNumColumns(3);
        }
    }

    public void a(j setter) {
        setter.bg(g.topic_other, c.listSelector).bh(g.floor, 16842808).bh(g.publish_time, 16842808).bh(g.content, 16842808).bg(g.retcontent, c.backgroundTopicReply).bh(g.retcontent, 16843282).bh(g.content, 16842808).bg(g.cately, c.backgroundTopicReply).bf(g.item_split, c.splitColor).bh(g.credit, 16843282).bh(g.topic, 16843282).bh(g.category, 16843282).bi(g.avatar, c.valBrightness);
    }
}
