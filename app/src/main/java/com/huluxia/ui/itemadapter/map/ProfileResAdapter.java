package com.huluxia.ui.itemadapter.map;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.data.map.MapProfileInfo.MapProfileItem;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.k;
import com.huluxia.t;
import java.util.ArrayList;
import java.util.List;

public class ProfileResAdapter extends BaseAdapter {
    private Activity aTg;
    protected List<MapProfileItem> aab = new ArrayList();
    private int axr = 1;
    private int mState = 3;

    public static class a {
        public PaintView aOJ;
        public TextView aRh;
        public ViewGroup aUk;
        public ViewGroup aUl;
        public TextView aUm;
    }

    public /* synthetic */ Object getItem(int i) {
        return kH(i);
    }

    public ProfileResAdapter(Activity context) {
        this.aTg = context;
    }

    public int getCount() {
        return this.aab.size();
    }

    public MapProfileItem kH(int position) {
        return (MapProfileItem) this.aab.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.aTg).inflate(R.layout.item_res_profile, parent, false);
            holder = new a();
            holder.aUk = (ViewGroup) convertView.findViewById(R.id.container);
            holder.aUl = (ViewGroup) convertView.findViewById(R.id.image_container);
            holder.aOJ = (PaintView) convertView.findViewById(R.id.map_image);
            holder.aRh = (TextView) convertView.findViewById(R.id.map_name);
            holder.aUm = (TextView) convertView.findViewById(R.id.map_info);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        final MapProfileItem info = kH(position);
        if (!TextUtils.isEmpty(info.icon)) {
            t.b(holder.aOJ, info.icon, (float) t.dipToPx(this.aTg, 10));
        }
        holder.aRh.setText(info.name);
        holder.aUm.setText("简介: " + info.mapDesc);
        if (this.mState == 1) {
            holder.aUk.setEnabled(true);
            holder.aUk.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ ProfileResAdapter aUj;

                public void onClick(View v) {
                    this.aUj.a(info);
                }
            });
        } else if (this.mState == 2) {
            holder.aUk.setEnabled(true);
            holder.aUk.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ ProfileResAdapter aUj;

                public void onClick(View v) {
                    k.a(this.aUj.aTg, info, this.aUj.axr, this.aUj.mState);
                }
            });
        } else {
            holder.aUk.setEnabled(false);
            holder.aUk.setOnClickListener(null);
        }
        return convertView;
    }

    private void a(MapProfileItem info) {
        if (this.axr == 1) {
            k.b(this.aTg, (long) info.id, info.postID);
        } else if (this.axr == 2) {
            k.c(this.aTg, (long) info.id, info.postID);
        } else if (this.axr == 3) {
            k.d(this.aTg, (long) info.id, info.postID);
        } else if (this.axr == 4) {
            k.e(this.aTg, (long) info.id, info.postID);
        }
    }

    public void a(List<MapProfileItem> data, int state, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        this.mState = state;
        notifyDataSetChanged();
    }

    public void HA() {
        this.aab.clear();
        notifyDataSetChanged();
    }

    public void kI(int resType) {
        this.axr = resType;
    }
}
