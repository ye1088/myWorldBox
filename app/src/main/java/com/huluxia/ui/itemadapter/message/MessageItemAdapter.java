package com.huluxia.ui.itemadapter.message;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.Medal;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.message.UserMsgItem;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.t;
import com.huluxia.utils.as;
import com.huluxia.utils.at;
import com.huluxia.utils.aw;
import com.huluxia.utils.az;
import com.huluxia.widget.photowall.PhotoWall;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageItemAdapter extends BaseAdapter implements b {
    private int aKj = 0;
    private UserMsgItem aUn;
    private OnClickListener aUo = new 1(this);
    private List<Object> akR = new ArrayList();
    private Context context;
    private LayoutInflater mInflater = null;

    public MessageItemAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.aKj = at.dipToPx(context, 5);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.include_message_item, null);
        }
        this.aUn = (UserMsgItem) getItem(position);
        a(convertView, this.aUn.getContent(), this.aUn.getContentType(), this.aUn.getOperateType());
        return convertView;
    }

    public void setData(List<UserMsgItem> msgItems) {
        this.akR.clear();
        this.akR.addAll(msgItems);
        notifyDataSetChanged();
    }

    private void a(View v, UserBaseInfo user) {
        EmojiTextView nick = (EmojiTextView) v.findViewById(g.nick);
        nick.setText(aw.W(user.getNick(), 10));
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

    private void d(View v, UserBaseInfo user) {
        if (user.getMedalList() == null || user.getMedalList().size() <= 0) {
            v.findViewById(g.ly_medal).setVisibility(8);
            return;
        }
        v.findViewById(g.ly_medal).setVisibility(0);
        PaintView medal0 = (PaintView) v.findViewById(g.iv_medal0);
        medal0.setVisibility(8);
        PaintView medal1 = (PaintView) v.findViewById(g.iv_medal1);
        medal1.setVisibility(8);
        PaintView medal2 = (PaintView) v.findViewById(g.iv_medal2);
        medal2.setVisibility(8);
        PaintView medal3 = (PaintView) v.findViewById(g.iv_medal3);
        medal3.setVisibility(8);
        PaintView medal4 = (PaintView) v.findViewById(g.iv_medal4);
        medal4.setVisibility(8);
        PaintView medal5 = (PaintView) v.findViewById(g.iv_medal5);
        medal5.setVisibility(8);
        for (int i = 0; i < user.getMedalList().size(); i++) {
            switch (i) {
                case 0:
                    medal0.setVisibility(0);
                    t.b(medal0, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 1:
                    medal1.setVisibility(0);
                    t.b(medal1, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 2:
                    medal2.setVisibility(0);
                    t.b(medal2, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 3:
                    medal3.setVisibility(0);
                    t.b(medal3, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 4:
                    medal4.setVisibility(0);
                    t.b(medal4, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 5:
                    medal5.setVisibility(0);
                    t.b(medal5, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                default:
                    break;
            }
        }
    }

    private void a(View v, CommentItem data, int contentType, int operateType) {
        v.findViewById(g.floor).setVisibility(8);
        v.findViewById(g.moderator_flag).setVisibility(8);
        v.findViewById(g.integral_title).setVisibility(8);
        EmojiTextView content = (EmojiTextView) v.findViewById(g.content);
        EmojiTextView retcontent = (EmojiTextView) v.findViewById(g.retcontent);
        RelativeLayout cately = (RelativeLayout) v.findViewById(g.cately);
        EmojiTextView credit = (EmojiTextView) v.findViewById(g.credit);
        retcontent.setVisibility(8);
        cately.setVisibility(8);
        credit.setVisibility(8);
        content.setVisibility(0);
        t.a((PaintView) v.findViewById(g.avatar), data.getUserInfo().getAvatar(), (float) this.aKj);
        ((FrameLayout) v.findViewById(g.layout_header)).setOnClickListener(new a(this.context, data.getUserInfo().getUserID(), data.getUserInfo()));
        ((TextView) v.findViewById(g.publish_time)).setText(az.bD(data.getCreateTime()));
        a(v, data.getUserInfo());
        b(v, data.getUserInfo());
        c(v, data.getUserInfo());
        d(v, data.getUserInfo());
        as.a(this.context, (ImageView) v.findViewById(g.iv_role), data.getUserInfo());
        if (operateType == 0) {
            cately.setVisibility(0);
            if (data.getState() == 2) {
                content.setText("此用户已经将评论删除");
            } else if (contentType == 203) {
                content.setText(aw.W(data.getTopicItem() != null ? data.getTopicItem().getDetail() : "", 100));
                retcontent.setText(aw.W("" + aw.go(data.getUserInfo() != null ? data.getUserInfo().nick : "") + "的主题@我\n" + data.getTopicItem().getTitle(), 100));
                retcontent.setVisibility(0);
            } else {
                content.setText(data.getText());
                String szRetContent = "";
                if (data.getRefComment() != null) {
                    String szRetText = data.getRefComment().getText();
                    if (data.getRefComment().getState() == 2) {
                        szRetText = "此评论已经删除";
                    }
                    szRetContent = szRetContent + "回复我的评论\n" + szRetText;
                } else {
                    szRetContent = szRetContent + "回复我的话题\n" + data.getTopicItem().getTitle();
                }
                retcontent.setText(aw.W(szRetContent, 100));
                retcontent.setVisibility(0);
            }
            ((EmojiTextView) v.findViewById(g.topic)).setText(aw.W("原帖：" + data.getTopicItem().getTitle(), 25));
            ((EmojiTextView) v.findViewById(g.category)).setText(aw.W("版块：" + data.getTopicCategory().getTitle(), 25));
            a((PhotoWall) v.findViewById(g.photoWall), data.getImages());
            if (data.getScore() > 0) {
                credit.setVisibility(0);
                credit.setText(Html.fromHtml("获得：<font color='#ff0000'>+" + String.valueOf(data.getScore()) + "</font>葫芦"));
                credit.setVisibility(0);
                return;
            } else if (data.getScore() < 0) {
                credit.setVisibility(0);
                credit.setText(Html.fromHtml("获得：<font color='#ff0000'>" + String.valueOf(data.getScore()) + "</font>葫芦"));
                credit.setVisibility(0);
                return;
            } else {
                credit.setVisibility(8);
                return;
            }
        }
        content.setText("不支持该类型的消息，请升级三楼客户端");
    }

    public int getCount() {
        return this.akR.size();
    }

    public Object getItem(int position) {
        return this.akR.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    private void a(PhotoWall photoWall, List<String> images) {
        photoWall.setReadOnly(true);
        if (UtilsFunction.empty((Collection) images)) {
            photoWall.setVisibility(8);
            return;
        }
        photoWall.PD();
        photoWall.setVisibility(0);
        a(photoWall, images.size());
        for (String url : images) {
            PhotoWall.b un = new PhotoWall.b();
            un.setUrl(url);
            photoWall.a(un);
        }
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
