package com.MCWorld.ui.itemadapter.server;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.data.server.a.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.l;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.y;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ServerListAdapter extends BaseAdapter implements b {
    protected static String TAG = "ServerListAdapter";
    Activity aTg;
    private boolean aTm;
    private DialogManager aWo;
    protected List<a> aab = new ArrayList();

    public /* synthetic */ Object getItem(int i) {
        return kO(i);
    }

    public List<a> getData() {
        return this.aab;
    }

    public ServerListAdapter(Activity context) {
        this.aTg = context;
        this.aTm = d.isDayMode();
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public a kO(int position) {
        return (a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    private void a(a info) {
        int colorGreen = d.getColor(this.aTg, R.attr.textColorGreen);
        int colorTitle = d.getColor(this.aTg, R.attr.dialog_title_label_color);
        View customView = LayoutInflater.from(this.aTg).inflate(R.layout.dialog_server, null);
        this.aWo = new DialogManager(this.aTg);
        this.aWo.showOkCancelColorDialog("服务器相关指南", colorTitle, customView, this.aTg.getString(R.string.done), colorGreen, this.aTg.getString(R.string.btn_cancel), colorGreen, true, new 1(this, info));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.aTg).inflate(R.layout.item_server_list, parent, false);
            holder = new a();
            holder.aUk = (ViewGroup) convertView.findViewById(R.id.container);
            holder.aWw = (ViewGroup) convertView.findViewById(R.id.tag_container);
            holder.aFM = (TextView) convertView.findViewById(R.id.tag1);
            holder.aFN = (TextView) convertView.findViewById(R.id.tag2);
            holder.aFO = (TextView) convertView.findViewById(R.id.tag3);
            holder.aFP = (TextView) convertView.findViewById(R.id.tag4);
            holder.aOJ = (PaintView) convertView.findViewById(R.id.map_image);
            holder.aWr = (TextView) convertView.findViewById(R.id.server_version);
            holder.aWs = (TextView) convertView.findViewById(R.id.server_name);
            holder.aWt = (TextView) convertView.findViewById(R.id.server_online_status);
            holder.aWu = (TextView) convertView.findViewById(R.id.server_online_count);
            holder.aWv = (ImageView) convertView.findViewById(R.id.server_enter);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a info = kO(position);
        holder.aWr.setText(info.ver);
        holder.aOJ.placeHolder(this.aTm ? R.drawable.discover_pic : R.drawable.discover_pic_night);
        holder.aOJ.setImageUrl(aw.gu(info.icon), l.cb().getImageLoader());
        holder.aWs.setText(info.name);
        holder.aWt.setText(info.status > 0 ? "在线" : "离线");
        if (info.status > 0) {
            holder.aWt.setBackgroundColor(this.aTg.getResources().getColor(R.color.text_start_color));
            if (info.maxCount > 0) {
                holder.aWu.setText(String.format(Locale.getDefault(), "最多%d人", new Object[]{Integer.valueOf(info.maxCount)}));
                holder.aWu.setVisibility(0);
            } else {
                holder.aWu.setVisibility(4);
            }
        } else {
            holder.aWt.setBackgroundColor(this.aTg.getResources().getColor(R.color.text_half_transparent));
            holder.aWu.setText(String.format(Locale.getDefault(), "最多%d人", new Object[]{Integer.valueOf(info.maxCount)}));
        }
        if (info.tagList != null) {
            String[] tagList = info.tagList.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
            TextView[] viewList = new TextView[]{holder.aFM, holder.aFN, holder.aFO, holder.aFP};
            for (int i = 0; i < 4; i++) {
                if (i < tagList.length) {
                    viewList[i].setText(tagList[i]);
                    viewList[i].setVisibility(0);
                    viewList[i].setBackgroundDrawable(y.H(this.aTg, tagList[i]));
                } else {
                    viewList[i].setVisibility(8);
                }
            }
            if (tagList.length < 1) {
                holder.aWw.setVisibility(8);
            } else {
                holder.aWw.setVisibility(0);
            }
        } else {
            holder.aWw.setVisibility(8);
        }
        holder.aUk.setOnClickListener(new 2(this, info));
        holder.aWv.setOnClickListener(new 3(this, info));
        return convertView;
    }

    public void setDayMode(boolean dayMode) {
        this.aTm = dayMode;
        notifyDataSetChanged();
    }

    public void a(List<a> data, boolean clear, boolean bsort) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        if (bsort) {
            Collections.sort(this.aab, new 4(this));
        }
        notifyDataSetChanged();
    }

    public void HA() {
        this.aab.clear();
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bh(R.id.server_version, R.attr.version).bh(R.id.server_online_status, R.attr.version).bh(R.id.server_name, R.attr.mapName).bh(R.id.server_online_count, R.attr.authorName).bh(R.id.tag1, R.attr.home_common_bg).bh(R.id.tag2, R.attr.home_common_bg).bh(R.id.tag3, R.attr.home_common_bg).bh(R.id.tag4, R.attr.home_common_bg).bf(R.id.dividing_line, R.attr.dividingLine).bf(R.id.bottom_dividing_line, R.attr.dividingLine).bg(R.id.container, R.attr.itemBackground).bh(R.id.join, R.attr.join).bg(R.id.server_enter, R.attr.serverJoin);
    }
}
