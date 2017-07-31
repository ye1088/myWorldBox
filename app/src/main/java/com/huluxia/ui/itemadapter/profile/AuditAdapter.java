package com.huluxia.ui.itemadapter.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.profile.d.a;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.t;
import com.huluxia.ui.profile.StudioAuditActivity;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class AuditAdapter extends BaseAdapter implements b {
    private List<a> aVf = new ArrayList();
    StudioAuditActivity aVg;

    public enum AuditReslut {
        AGREE(1),
        DISAGREE(2),
        EXPIRED(3);
        
        private int mResult;

        public int getResult() {
            return this.mResult;
        }

        private AuditReslut(int result) {
            this.mResult = result;
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return kN(i);
    }

    public AuditAdapter(StudioAuditActivity context) {
        this.aVg = context;
    }

    public int getCount() {
        return this.aVf == null ? 0 : this.aVf.size();
    }

    public a kN(int position) {
        return (a) this.aVf.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        a item = kN(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.aVg).inflate(i.item_audit, parent, false);
            holder = new a(null);
            holder.aVk = (PaintView) convertView.findViewById(g.image);
            holder.aVl = (LinearLayout) convertView.findViewById(g.container);
            holder.aVo = (TextView) convertView.findViewById(g.nick);
            holder.aVm = (TextView) convertView.findViewById(g.agree);
            holder.aVn = (TextView) convertView.findViewById(g.disagree);
            holder.mResult = (TextView) convertView.findViewById(g.result);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.aVo.setText(item.getNick());
        t.b(holder.aVk, item.getAvatar(), (float) t.dipToPx(this.aVg, 3));
        holder.aVk.setOnClickListener(new 1(this, item));
        holder.aVm.setEnabled(true);
        holder.aVn.setEnabled(true);
        holder.aVm.setOnClickListener(new 2(this, holder, item, position));
        holder.aVn.setOnClickListener(new 3(this, holder, item, position));
        if (item.getStatus() == 0) {
            holder.aVl.setVisibility(0);
            holder.mResult.setVisibility(8);
        } else if (item.getStatus() == 1) {
            holder.aVl.setVisibility(8);
            holder.mResult.setVisibility(0);
            holder.mResult.setText("已同意");
        } else if (item.getStatus() == 2) {
            holder.aVl.setVisibility(8);
            holder.mResult.setVisibility(0);
            holder.mResult.setText("已拒绝");
        } else if (item.getStatus() == 3) {
            holder.aVl.setVisibility(8);
            holder.mResult.setVisibility(0);
            holder.mResult.setText("已过期");
        }
        return convertView;
    }

    public void c(List<a> list, boolean clearData) {
        if (clearData) {
            this.aVf.clear();
        }
        this.aVf.addAll(list);
        notifyDataSetChanged();
    }

    public void HA() {
        this.aVf.clear();
        notifyDataSetChanged();
    }

    public void aw(int position, int opt) {
        ((a) this.aVf.get(position)).setStatus(opt);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bh(g.agree, c.agree_text).bh(g.disagree, c.disagree_text).bh(g.result, c.textColorResDownloading).bf(g.divideline, c.splitColor);
    }
}
