package com.huluxia.ui.itemadapter.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.utils.aw;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import hlx.data.tongji.a;
import java.util.ArrayList;
import java.util.List;

public class ProfileSpaceAdapter extends BaseAdapter implements com.simple.colorful.b {
    private ProfileInfo aKG;
    private boolean aKH;
    private boolean aLc;
    private int[] aVR;
    private int[] aVS;
    private List<com.huluxia.data.profile.b> aVT;
    private a aVU;
    private OnClickListener mClickListener;
    private Context mContext;
    private LayoutInflater mInflater;

    static class b {
        a aVW;
        a aVX;
        a aVY;
        View aVZ;
        View aWa;
        View aWb;

        static class a {
            TextView RZ;
            View aSO;
            ImageView aWc;
            TextView aWd;

            public a(View itemContainer, ImageView iconView, TextView nameView, TextView countView) {
                this.aSO = itemContainer;
                this.aWc = iconView;
                this.RZ = nameView;
                this.aWd = countView;
            }
        }

        b() {
        }
    }

    public ProfileSpaceAdapter(Context context, boolean isOthe) {
        this(context, isOthe, null);
    }

    public ProfileSpaceAdapter(Context context, boolean isOther, a callback) {
        int i;
        this.mInflater = null;
        this.aLc = false;
        this.mClickListener = new OnClickListener(this) {
            final /* synthetic */ ProfileSpaceAdapter aVV;

            {
                this.aVV = this$0;
            }

            public void onClick(View v) {
                int index = ((Integer) v.getTag()).intValue();
                long userId = this.aVV.aKG == null ? 0 : this.aVV.aKG.getUserID();
                switch (index) {
                    case 0:
                        t.f(this.aVV.mContext, userId);
                        break;
                    case 1:
                        t.g(this.aVV.mContext, userId);
                        break;
                    case 2:
                        t.h(this.aVV.mContext, userId);
                        break;
                    case 3:
                        t.i(this.aVV.mContext, userId);
                        break;
                    case 4:
                        t.j(this.aVV.mContext, userId);
                        break;
                    case 5:
                        if (!this.aVV.aKH) {
                            t.as(this.aVV.mContext);
                            break;
                        } else if (this.aVV.aKG != null) {
                            t.a(this.aVV.mContext, this.aVV.aKG, this.aVV.aKH);
                            break;
                        } else {
                            return;
                        }
                    case 6:
                        if (d.isDayMode()) {
                            r.ck().K(a.bMk);
                        }
                        d.RC();
                        break;
                    case 7:
                        r.ck().K(a.bMm);
                        t.b(this.aVV.mContext, this.aVV.aKG);
                        break;
                    case 8:
                        t.ao(this.aVV.mContext);
                        break;
                }
                if (this.aVV.aVU != null) {
                    this.aVV.aVU.Hx();
                }
            }
        };
        this.aVU = null;
        this.mContext = context;
        this.aKH = isOther;
        this.mInflater = LayoutInflater.from(context);
        int[] iArr = new int[9];
        iArr[0] = f.ic_profile_topic;
        iArr[1] = f.ic_profile_comment;
        iArr[2] = f.ic_profile_favor;
        iArr[3] = f.ic_profile_following;
        iArr[4] = f.ic_profile_fans;
        if (this.aKH) {
            i = f.ic_profile_albums;
        } else {
            i = f.ic_profile_read_history;
        }
        iArr[5] = i;
        iArr[6] = f.ic_profile_day_mode;
        iArr[7] = f.ic_profile_achievement_day;
        iArr[8] = f.ic_profile_setting;
        this.aVS = iArr;
        iArr = new int[9];
        if (this.aKH) {
            i = m.his_topics;
        } else {
            i = m.my_topics;
        }
        iArr[0] = i;
        if (this.aKH) {
            i = m.his_comments;
        } else {
            i = m.my_comments2;
        }
        iArr[1] = i;
        if (this.aKH) {
            i = m.his_favorite;
        } else {
            i = m.my_favorite;
        }
        iArr[2] = i;
        if (this.aKH) {
            i = m.his_idol;
        } else {
            i = m.my_idol2;
        }
        iArr[3] = i;
        if (this.aKH) {
            i = m.his_fans;
        } else {
            i = m.my_fans2;
        }
        iArr[4] = i;
        if (this.aKH) {
            i = m.his_ablum;
        } else {
            i = m.view_history;
        }
        iArr[5] = i;
        iArr[6] = m.day_mode;
        iArr[7] = m.user_achievement;
        iArr[8] = m.setting;
        this.aVR = iArr;
        HD();
        this.aVU = callback;
    }

    private void HD() {
        this.aVT = new ArrayList();
        int count = this.aKH ? 6 : 9;
        for (int i = 0; i < count; i++) {
            this.aVT.add(new com.huluxia.data.profile.b(this.mContext.getResources().getString(this.aVR[i]), i));
        }
    }

    public void g(ProfileInfo info) {
        this.aKG = info;
        if (info == null || info.model != 2 || info.space == null) {
            setTranslucent(false);
        } else {
            setTranslucent(true);
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aVT == null ? 0 : (this.aVT.size() + 1) / 3;
    }

    public Object getItem(int position) {
        return this.aVT.get(position * 3);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        if (convertView == null) {
            holder = new b();
            convertView = this.mInflater.inflate(i.item_profile_action_line, parent, false);
            View itemContainer = convertView.findViewById(g.item_container_1);
            View itemContainer2 = convertView.findViewById(g.item_container_2);
            View itemContainer3 = convertView.findViewById(g.item_container_3);
            ImageView iconView2 = (ImageView) convertView.findViewById(g.item_icon_2);
            ImageView iconView3 = (ImageView) convertView.findViewById(g.item_icon_3);
            TextView nameView2 = (TextView) convertView.findViewById(g.item_name_2);
            TextView nameView3 = (TextView) convertView.findViewById(g.item_name_3);
            TextView countView2 = (TextView) convertView.findViewById(g.item_count_2);
            TextView countView3 = (TextView) convertView.findViewById(g.item_count_3);
            holder.aVW = new a(itemContainer, (ImageView) convertView.findViewById(g.item_icon_1), (TextView) convertView.findViewById(g.item_name_1), (TextView) convertView.findViewById(g.item_count_1));
            holder.aVX = new a(itemContainer2, iconView2, nameView2, countView2);
            holder.aVY = new a(itemContainer3, iconView3, nameView3, countView3);
            holder.aVZ = convertView.findViewById(g.split);
            holder.aWa = convertView.findViewById(g.split_vertical);
            holder.aWb = convertView.findViewById(g.split_vertical2);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        a(holder);
        a(holder.aVW, (com.huluxia.data.profile.b) getItem(position));
        a(holder.aVX, (position * 3) + 1 < this.aVT.size() ? (com.huluxia.data.profile.b) this.aVT.get((position * 3) + 1) : null);
        a(holder.aVY, (position * 3) + 2 < this.aVT.size() ? (com.huluxia.data.profile.b) this.aVT.get((position * 3) + 2) : null);
        return convertView;
    }

    private void a(b holder) {
        int color = d.getColor(this.mContext, this.aLc ? c.splitColorProfileTranslucent : c.splitColor);
        holder.aVZ.setBackgroundColor(color);
        holder.aWa.setBackgroundColor(color);
        holder.aWb.setBackgroundColor(color);
    }

    public void setTranslucent(boolean translucent) {
        this.aLc = translucent;
        notifyDataSetChanged();
    }

    private void a(a item, com.huluxia.data.profile.b itemInfo) {
        if (itemInfo == null) {
            item.aSO.setVisibility(4);
            return;
        }
        item.aSO.setVisibility(0);
        item.aSO.setBackgroundDrawable(d.o(this.mContext, this.aLc ? c.listSelectorProfileTranslucent : c.listSelectorProfile));
        item.RZ.setText(itemInfo.getName());
        item.aWc.setImageResource(this.aVS[itemInfo.getIndex()]);
        item.aSO.setTag(Integer.valueOf(itemInfo.getIndex()));
        item.aSO.setOnClickListener(this.mClickListener);
        if (this.aKG == null) {
            item.aWd.setVisibility(8);
        } else if (itemInfo.getIndex() < 5) {
            item.aWd.setVisibility(0);
            long num = 0;
            String numStr = "";
            switch (itemInfo.getIndex()) {
                case 0:
                    num = this.aKG.postCount;
                    break;
                case 1:
                    num = this.aKG.commentCount;
                    break;
                case 2:
                    num = this.aKG.favoriteCount;
                    break;
                case 3:
                    numStr = this.aKG.getFollowingCountFormated();
                    break;
                case 4:
                    numStr = this.aKG.getFollowerCountFormated();
                    break;
            }
            if (itemInfo.getIndex() == 3 || itemInfo.getIndex() == 4) {
                item.aWd.setText(numStr);
            } else {
                item.aWd.setText(aw.W(String.valueOf(num), 5));
            }
        } else {
            item.aWd.setVisibility(4);
        }
        if (itemInfo.getIndex() == 6) {
            boolean isNightMode = d.RB();
            item.RZ.setText(isNightMode ? m.day_mode : m.night_mode);
            item.aWc.setImageResource(isNightMode ? f.ic_profile_day_mode : f.ic_profile_night_mode);
        }
    }

    public void a(j setter) {
        setter.bi(g.item_icon_1, c.valBrightness).bi(g.item_icon_2, c.valBrightness).bi(g.item_icon_3, c.valBrightness).bh(g.item_name_1, 16842808).bh(g.item_name_2, 16842808).bh(g.item_name_3, 16842808).bh(g.item_count_1, 16842808).bh(g.item_count_2, 16842808).bh(g.item_count_3, 16842808);
    }
}
