package com.MCWorld.ui.itemadapter.message;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.Medal;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.message.SysMsgItem;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.utils.as;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.az;
import com.MCWorld.widget.photowall.PhotoWall;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class SysMsgItemAdapter extends BaseAdapter implements b {
    private int aKj = 0;
    private OnClickListener aUo = new 1(this);
    private SysMsgItem aUr;
    private List<Object> akR = new ArrayList();
    private Context context;
    private LayoutInflater mInflater = null;

    public SysMsgItemAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.aKj = at.dipToPx(context, 5);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.include_message_item, null);
        }
        this.aUr = (SysMsgItem) getItem(position);
        a(convertView, this.aUr);
        return convertView;
    }

    public void setData(List<SysMsgItem> msgItems) {
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
            ((GradientDrawable) tv_honor.getBackground()).setColor(user.getIdentityColor());
            tv_honor.setText(user.getIdentityTitle());
            tv_honor.setVisibility(0);
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

    private void a(View v, SysMsgItem data) {
        v.findViewById(g.floor).setVisibility(8);
        v.findViewById(g.moderator_flag).setVisibility(8);
        v.findViewById(g.retcontent).setVisibility(8);
        v.findViewById(g.cately).setVisibility(8);
        v.findViewById(g.integral_title).setVisibility(8);
        UserBaseInfo userInfo = data.getContent().getUserInfo();
        a(v, userInfo);
        b(v, userInfo);
        c(v, userInfo);
        d(v, userInfo);
        as.a(this.context, (ImageView) v.findViewById(g.iv_role), userInfo);
        t.a((PaintView) v.findViewById(g.avatar), userInfo.getAvatar(), (float) this.aKj);
        ((FrameLayout) v.findViewById(g.layout_header)).setOnClickListener(new a(this.context, userInfo.getUserID(), userInfo));
        ((TextView) v.findViewById(g.publish_time)).setText(az.bD(data.getCreateTime()));
        EmojiTextView content = (EmojiTextView) v.findViewById(g.content);
        content.setVisibility(0);
        content.setText(data.getContent().getText());
        a((PhotoWall) v.findViewById(g.photoWall), data.getContent().getImages());
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
            return;
        }
        photoWall.getLayoutParams().width = nitemW * 2;
        photoWall.setMaxPhotoNum(size);
        photoWall.setNumColumns(2);
    }

    public void a(j setter) {
        setter.bg(g.topic_other, c.listSelector).bh(g.floor, 16842808).bh(g.publish_time, 16842808).bh(g.content, 16842808).bg(g.retcontent, c.backgroundTopicReply).bh(g.retcontent, 16843282).bh(g.content, 16842808).bg(g.cately, c.backgroundTopicReply).bf(g.item_split, c.splitColor).bh(g.credit, 16843282).bh(g.topic, 16843282).bh(g.category, 16843282).bi(g.avatar, c.valBrightness);
    }
}
