package com.MCWorld.studio.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.j;
import com.MCWorld.data.profile.e.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.studio.StudioMembersActivity;
import com.MCWorld.t;
import com.MCWorld.utils.y;
import com.simple.colorful.b;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.List;

public class StudioMembersAdapter extends BaseAdapter implements b {
    StudioMembersActivity aFQ;
    private int aFR = 0;
    boolean aFS = false;
    private OnClickListener aFT = new 1(this);
    List<a> datas = new ArrayList();

    public /* synthetic */ Object getItem(int i) {
        return kn(i);
    }

    public StudioMembersAdapter(StudioMembersActivity activity) {
        this.aFQ = activity;
    }

    public int getCount() {
        return this.datas == null ? 0 : this.datas.size();
    }

    public a kn(int position) {
        return (a) this.datas.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        a studioUser = kn(position);
        UserBaseInfo user = studioUser.user;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.aFQ).inflate(R.layout.item_studio_members, parent, false);
            holder = new a();
            holder.aFW = (TextView) convertView.findViewById(R.id.name);
            holder.aFX = (TextView) convertView.findViewById(R.id.user_age);
            holder.eN = (TextView) convertView.findViewById(R.id.title);
            holder.aFY = (TextView) convertView.findViewById(R.id.chief);
            holder.aFZ = (TextView) convertView.findViewById(R.id.remove);
            holder.aGa = (PaintView) convertView.findViewById(R.id.image);
            holder.mRootView = convertView.findViewById(R.id.root_view);
            holder.aGb = (TextView) convertView.findViewById(R.id.tv_set_vice_leader);
            holder.aGc = (TextView) convertView.findViewById(R.id.tv_assign_studio);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        t.b(holder.aGa, user.avatar, (float) t.dipToPx(this.aFQ, 6));
        holder.aFW.setText(user.nick);
        a(holder, studioUser, user);
        a(holder, studioUser);
        b(holder, user);
        a(holder, user);
        return convertView;
    }

    private void a(a holder, a studioUser) {
        if (studioUser != null) {
            if (this.aFS && 1 != studioUser.isStudio) {
                switch (this.aFR) {
                    case 257:
                        holder.aGb.setVisibility(0);
                        holder.aGc.setVisibility(8);
                        holder.aFZ.setVisibility(8);
                        if (2 != studioUser.isStudio) {
                            holder.aGb.setText("设置");
                            holder.aGb.setTextColor(d.getColor(this.aFQ, R.attr.tabTextSelect));
                            holder.aGb.setBackgroundDrawable(d.o(this.aFQ, R.attr.buttonDownload));
                            break;
                        }
                        holder.aGb.setText(hlx.data.localstore.a.bKB_bt_cancel);
                        holder.aGb.setTextColor(d.getColor(this.aFQ, R.attr.textDelete));
                        holder.aGb.setBackgroundDrawable(d.o(this.aFQ, R.attr.buttonDelete));
                        break;
                    case 258:
                        holder.aGb.setVisibility(8);
                        holder.aGc.setVisibility(0);
                        holder.aFZ.setVisibility(8);
                        break;
                    case 259:
                        holder.aGb.setVisibility(8);
                        holder.aGc.setVisibility(8);
                        holder.aFZ.setVisibility(0);
                        break;
                }
            }
            holder.aFZ.setVisibility(8);
            holder.aGb.setVisibility(8);
            holder.aGc.setVisibility(8);
            holder.aGb.setTag(studioUser);
            holder.aGc.setTag(studioUser);
            holder.aFZ.setTag(studioUser);
            holder.aGb.setOnClickListener(this.aFT);
            holder.aGc.setOnClickListener(this.aFT);
            holder.aFZ.setOnClickListener(this.aFT);
        }
    }

    private void a(a holder, a studioUser, UserBaseInfo user) {
        if (studioUser != null && user != null) {
            if (user.getUserID() == j.ep().getUserid()) {
                holder.aFY.setVisibility(0);
                holder.aFY.setText("我");
                holder.aFY.setTextColor(d.getColor(this.aFQ, R.attr.text_studio_topic_detail));
            } else if (this.aFS) {
                holder.aFY.setVisibility(8);
            } else if (1 == studioUser.isStudio) {
                holder.aFY.setText("室长");
                holder.aFY.setVisibility(0);
                holder.aFY.setTextColor(d.getColor(this.aFQ, 16842808));
            } else if (2 == studioUser.isStudio) {
                holder.aFY.setText("副室长");
                holder.aFY.setVisibility(0);
                holder.aFY.setTextColor(d.getColor(this.aFQ, 16842808));
            } else {
                holder.aFY.setVisibility(8);
            }
        }
    }

    private void a(a holder, UserBaseInfo user) {
        if (UtilsFunction.empty(user.getIdentityTitle())) {
            holder.eN.setVisibility(8);
            return;
        }
        holder.eN.setVisibility(0);
        holder.eN.setText(user.getIdentityTitle());
        y.a(holder.eN, y.e(this.aFQ, user.getIdentityColor(), 2));
    }

    private void b(a holder, UserBaseInfo user) {
        holder.aFX.setText(String.valueOf(user.getAge()));
        if (user.getGender() == 1) {
            holder.aFX.setBackgroundResource(R.drawable.bg_gender_female);
            holder.aFX.setCompoundDrawablesWithIntrinsicBounds(this.aFQ.getResources().getDrawable(R.drawable.user_female), null, null, null);
            return;
        }
        holder.aFX.setBackgroundResource(R.drawable.bg_gender_male);
        holder.aFX.setCompoundDrawablesWithIntrinsicBounds(this.aFQ.getResources().getDrawable(R.drawable.user_male), null, null, null);
    }

    public void c(List<a> data, boolean clearData) {
        if (clearData) {
            this.datas.clear();
        }
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public void b(boolean isEdited, int operationType) {
        this.aFS = isEdited;
        this.aFR = operationType;
        notifyDataSetChanged();
    }

    public void a(a user) {
        this.datas.remove(user);
        notifyDataSetChanged();
    }

    public void a(com.simple.colorful.setter.j setter) {
        setter.bh(R.id.remove, R.attr.kick_out_text).bg(R.id.remove, R.attr.remove_bg).bh(R.id.name, 16842806).bf(R.id.divideline, R.attr.splitColor).bh(R.id.tv_set_vice_leader, R.attr.tabTextSelect).bg(R.id.tv_set_vice_leader, R.attr.buttonDownload).bh(R.id.tv_set_vice_leader, R.attr.textDelete).bg(R.id.tv_set_vice_leader, R.attr.buttonDelete).bh(R.id.tv_assign_studio, 16842808).bg(R.id.tv_assign_studio, R.attr.bg_assign_studio);
    }
}
