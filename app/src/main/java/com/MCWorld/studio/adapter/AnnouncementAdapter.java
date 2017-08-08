package com.MCWorld.studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.data.studio.b.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnouncementAdapter extends BaseAdapter implements b {
    private int aDX;
    private int aDY;
    private List<a> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    public /* synthetic */ Object getItem(int i) {
        return km(i);
    }

    public AnnouncementAdapter(Context context, int studioId, int userRole) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.aDX = studioId;
        this.aDY = userRole;
    }

    public void c(List<a> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void a(a item) {
        if (this.aab != null && item != null) {
            int delPosition = -1;
            for (int i = 0; i < this.aab.size(); i++) {
                if (item.id == ((a) this.aab.get(i)).id) {
                    delPosition = i;
                    break;
                }
            }
            HLog.verbose("AnnouncementAdapter", "data.size = " + this.aab.size(), new Object[0]);
            if (-1 != delPosition) {
                this.aab.remove(delPosition);
            }
            HLog.verbose("AnnouncementAdapter", "after data.size = " + this.aab.size(), new Object[0]);
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        return UtilsFunction.empty(this.aab) ? 0 : this.aab.size();
    }

    public a km(int position) {
        return (a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            holder = new a(null);
            convertView = this.mInflater.inflate(R.layout.item_studio_announce, null);
            holder.aFn = convertView.findViewById(R.id.click_view);
            holder.aFo = (EmojiTextView) convertView.findViewById(R.id.tv_announce_title);
            holder.aFp = (EmojiTextView) convertView.findViewById(R.id.tv_announcer);
            holder.aFq = (TextView) convertView.findViewById(R.id.tv_publish_time);
            holder.aFr = (EmojiTextView) convertView.findViewById(R.id.tv_announce_content);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a item = km(position);
        if (item != null) {
            holder.aFo.setText(item.title);
            holder.aFp.setText(item.announcer);
            holder.aFq.setText(new SimpleDateFormat("MM月dd日 HH:mm").format(new Date(item.createTime)));
            holder.aFr.setText(item.content);
            holder.aFn.setOnClickListener(new 1(this, item));
        }
        return convertView;
    }

    public void a(j setter) {
        setter.bf(R.id.click_view, R.attr.item_studio_announce_bg).bh(R.id.tv_announce_title, R.attr.room_number).bh(R.id.tv_announcer, 16843282).bh(R.id.tv_publish_time, 16843282).bf(R.id.split, R.attr.splitColor).bh(R.id.tv_announce_content, 16842808);
    }
}
