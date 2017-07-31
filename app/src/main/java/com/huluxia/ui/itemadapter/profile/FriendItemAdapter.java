package com.huluxia.ui.itemadapter.profile;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.module.profile.c;
import com.huluxia.t;
import com.huluxia.utils.as;
import com.huluxia.utils.aw;
import com.huluxia.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendItemAdapter extends BaseAdapter implements OnClickListener, OnCheckedChangeListener, b {
    private Set<Long> aMf;
    private boolean aVA;
    private a aVB;
    private List<c> aVy;
    private Set<Long> aVz;
    private Context mContext;
    private LayoutInflater mInflater;

    public interface a {
        boolean HC();

        void a(UserBaseInfo userBaseInfo);

        void b(UserBaseInfo userBaseInfo);

        boolean c(UserBaseInfo userBaseInfo);
    }

    public FriendItemAdapter(Context context) {
        this(context, false, null);
    }

    public FriendItemAdapter(Context context, boolean showPreview, a listener) {
        this.mInflater = null;
        this.aVy = new ArrayList();
        this.aVz = new HashSet();
        this.aMf = new HashSet();
        this.aVA = false;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.aVA = showPreview;
        this.aVB = listener;
    }

    public int getCount() {
        return this.aVy.size();
    }

    public Object getItem(int position) {
        return this.aVy.get(position);
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.listitem_profile, null);
            holder = new b(this);
            holder.aVC = convertView.findViewById(g.item_container);
            holder.aVD = (EmojiTextView) convertView.findViewById(g.nick);
            holder.aVE = (PaintView) convertView.findViewById(g.avatar);
            holder.aVF = (ImageView) convertView.findViewById(g.img_hulu);
            holder.aVG = convertView.findViewById(g.iv_role);
            holder.aVH = convertView.findViewById(g.moderator_flag);
            holder.aVI = convertView.findViewById(g.floor);
            holder.aVJ = convertView.findViewById(g.publish_time);
            holder.aVK = (CheckBox) convertView.findViewById(g.img_hook);
            holder.aVL = (TextView) convertView.findViewById(g.user_age);
            holder.aVM = convertView.findViewById(g.rl_sex_age);
            holder.aVN = (ImageView) convertView.findViewById(g.userlist_gender_mark);
            holder.aVO = convertView.findViewById(g.honor_flag);
            holder.aVP = (TextView) convertView.findViewById(g.tv_honor);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        UserBaseInfo user = ((c) getItem(position)).user;
        if (user != null) {
            holder.aVD.setText(aw.go(user.getNick()));
            holder.aVD.setTextColor(as.g(convertView.getContext(), user.getRole(), user.getGender()));
            t.a(holder.aVE, user.getAvatar(), (float) t.dipToPx(this.mContext, 5));
            holder.aVE.setTag(user);
            holder.aVF.setBackgroundResource(as.lv(user.getLevel()));
            a(holder, user);
            b(holder, user);
            holder.aVG.setVisibility(8);
            holder.aVH.setVisibility(8);
            holder.aVI.setVisibility(8);
            holder.aVJ.setVisibility(8);
            holder.aVK.setOnCheckedChangeListener(null);
            if (this.aVA && this.aVz != null && this.aVz.contains(Long.valueOf(user.userID))) {
                holder.aVK.setChecked(true);
            } else {
                holder.aVK.setChecked(false);
            }
            if (this.aVA && this.aMf != null && this.aMf.contains(Long.valueOf(user.userID))) {
                holder.aVK.setButtonDrawable(d.o(this.mContext, com.huluxia.bbs.b.c.drawableHookDisable));
            } else {
                holder.aVK.setButtonDrawable(d.o(this.mContext, com.huluxia.bbs.b.c.drawableHook));
            }
            if (!this.aVA) {
                holder.aVK.setVisibility(8);
                holder.aVK.setOnCheckedChangeListener(null);
                holder.aVC.setTag(user);
                holder.aVC.setOnClickListener(this);
                holder.aVC.setEnabled(true);
            } else if (this.aMf == null || !this.aMf.contains(Long.valueOf(user.userID))) {
                holder.aVK.setVisibility(0);
                holder.aVK.setTag(user);
                holder.aVK.setOnCheckedChangeListener(this);
                holder.aVC.setTag(user);
                holder.aVC.setOnClickListener(this);
                holder.aVC.setEnabled(true);
            } else {
                holder.aVK.setVisibility(0);
                holder.aVK.setOnCheckedChangeListener(null);
                holder.aVC.setOnClickListener(null);
                holder.aVC.setEnabled(false);
            }
        }
        return convertView;
    }

    private void a(b holder, UserBaseInfo user) {
        holder.aVL.setText(Integer.toString(user.getAge()));
        if (user.getGender() == 1) {
            holder.aVM.setBackgroundResource(f.bg_gender_female);
            holder.aVN.setImageResource(f.user_female);
            return;
        }
        holder.aVM.setBackgroundResource(f.bg_gender_male);
        holder.aVN.setImageResource(f.user_male);
    }

    @TargetApi(16)
    private void b(b holder, UserBaseInfo user) {
        if (user.getIdentityColor() != 0) {
            ((GradientDrawable) holder.aVO.getBackground()).setColor(user.getIdentityColor());
            holder.aVP.setText(user.getIdentityTitle());
            holder.aVO.setVisibility(0);
            return;
        }
        holder.aVO.setVisibility(8);
    }

    public void a(j setter) {
        setter.bg(g.item_container, com.huluxia.bbs.b.c.listSelector).bf(g.split_item, com.huluxia.bbs.b.c.splitColor).bh(g.nick, 16843282).bh(g.publish_time, 16843282).bh(g.hit_num, 16843282).bf(g.avatar, com.huluxia.bbs.b.c.valBrightness).bg(g.avatar_corner, com.huluxia.bbs.b.c.drawableHuluCorner);
    }

    public void c(List<c> data, boolean clear) {
        if (this.aVy == null) {
            this.aVy = data;
            notifyDataSetChanged();
            return;
        }
        if (clear) {
            this.aVy.clear();
        }
        this.aVy.addAll(data);
        notifyDataSetChanged();
    }

    public void e(List<UserBaseInfo> infos, List<UserBaseInfo> reservedInfos) {
        if (infos != null) {
            if (this.aVz == null) {
                this.aVz = new HashSet();
            }
            this.aVz.clear();
            for (UserBaseInfo info : infos) {
                this.aVz.add(Long.valueOf(info.userID));
            }
        }
        if (reservedInfos != null) {
            if (this.aMf == null) {
                this.aMf = new HashSet();
            }
            this.aMf.clear();
            for (UserBaseInfo info2 : reservedInfos) {
                this.aMf.add(Long.valueOf(info2.userID));
            }
        }
        notifyDataSetChanged();
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        UserBaseInfo info = (UserBaseInfo) compoundButton.getTag();
        compoundButton.setOnCheckedChangeListener(null);
        if (this.aVz == null) {
            this.aVz = new HashSet();
        }
        if (this.aVB != null) {
            if (b) {
                if (this.aVB.HC()) {
                    compoundButton.setChecked(false);
                } else if (!this.aVz.contains(Long.valueOf(info.userID))) {
                    this.aVz.add(Long.valueOf(info.userID));
                    this.aVB.a(info);
                }
            } else if (this.aVB.c(info)) {
                compoundButton.setChecked(true);
            } else {
                this.aVB.b(info);
                this.aVz.remove(Long.valueOf(info.userID));
            }
        }
        compoundButton.setOnCheckedChangeListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        UserBaseInfo info;
        if (id == g.avatar) {
            info = (UserBaseInfo) v.getTag();
            t.a(this.mContext, info.userID, info);
        } else if (id == g.item_container) {
            info = (UserBaseInfo) v.getTag();
            CheckBox img_hook = (CheckBox) v.findViewById(g.img_hook);
            if (this.aVA) {
                img_hook.setChecked(!img_hook.isChecked());
            } else {
                t.a(this.mContext, info.userID, info);
            }
        }
    }
}
