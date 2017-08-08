package com.MCWorld.ui.itemadapter.category;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.category.Daren;
import com.MCWorld.framework.base.image.Config.NetFormat;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.l;
import com.MCWorld.utils.as;
import com.MCWorld.utils.aw;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.List;

public class DarenItemAdapter extends BaseAdapter implements b {
    public static final int[] aSS = new int[]{f.ic_daren_ranking_1, f.ic_daren_ranking_2, f.ic_daren_ranking_3};
    private List<Daren> aab;
    private LayoutInflater mInflater;

    public DarenItemAdapter(Context context, List<Daren> objects) {
        this.aab = objects;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.listitem_daren, parent, false);
            holder = new a();
            holder.aST = (TextView) convertView.findViewById(g.tv_seq);
            holder.aSU = (ImageView) convertView.findViewById(g.iv_seq);
            holder.aJX = (EmojiTextView) convertView.findViewById(g.nick);
            holder.aFi = (PaintView) convertView.findViewById(g.avatar);
            holder.aSV = (TextView) convertView.findViewById(g.user_age);
            holder.aSW = convertView.findViewById(g.rl_sex_age);
            holder.aSY = convertView.findViewById(g.honor_flag);
            holder.aTa = (ImageView) convertView.findViewById(g.iv_role);
            holder.aTb = (TextView) convertView.findViewById(g.weektotal);
            holder.aSX = (ImageView) convertView.findViewById(g.userlist_gender_mark);
            holder.aSZ = (TextView) convertView.findViewById(g.tv_honor);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        Daren daren = (Daren) getItem(position);
        UserBaseInfo user = daren.getDaren();
        holder.aST.setText(String.valueOf(daren.getSeq()));
        if (position < 3) {
            holder.aSU.setVisibility(0);
            holder.aSU.setImageResource(aSS[position]);
            holder.aST.setVisibility(4);
        } else {
            holder.aST.setText(String.valueOf(position + 1));
            holder.aST.setVisibility(0);
            holder.aSU.setVisibility(4);
        }
        holder.aJX.setText(aw.go(user.getNick()));
        holder.aJX.setTextColor(as.g(convertView.getContext(), user.getRole(), user.getGender()));
        holder.aFi.setUri(UtilUri.getUriOrNull(user.getAvatar()), NetFormat.FORMAT_80).oval().placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
        a(holder, user);
        b(holder, user);
        as.b(holder.aTa, user.getRole());
        holder.aTb.setText(String.valueOf(daren.getWeektotal()));
        return convertView;
    }

    private void a(a holder, UserBaseInfo user) {
        holder.aSV.setText(Integer.toString(user.getAge()));
        if (user.getGender() == 1) {
            holder.aSW.setBackgroundResource(f.bg_gender_female);
            holder.aSX.setImageResource(f.user_female);
            return;
        }
        holder.aSW.setBackgroundResource(f.bg_gender_male);
        holder.aSX.setImageResource(f.user_male);
    }

    private void b(a holder, UserBaseInfo user) {
        if (user.getIdentityColor() != 0) {
            holder.aSZ.setText(user.getIdentityTitle());
            holder.aSY.setVisibility(0);
            ((GradientDrawable) holder.aSY.getBackground()).setColor(user.getIdentityColor());
            return;
        }
        holder.aSY.setVisibility(8);
    }

    public void c(List<Daren> data, boolean clear) {
        if (this.aab == null) {
            this.aab = data;
            notifyDataSetChanged();
            return;
        }
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bg(g.ly_daren, c.listSelector).bh(g.weektotal, c.textColorGreen).bh(g.tv_seq, 16842806).ac(g.weektotal, c.drawableHuluDaren, 2).bf(g.split, c.splitColor).bi(g.avatar, c.valBrightness);
    }
}
