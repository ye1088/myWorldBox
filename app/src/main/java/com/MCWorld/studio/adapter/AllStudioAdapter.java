package com.MCWorld.studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.k;
import com.MCWorld.l;
import com.MCWorld.t;
import com.MCWorld.utils.y;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class AllStudioAdapter extends BaseAdapter implements b {
    private List<com.MCWorld.data.studio.a.a> aab = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    private static class a {
        public PaintView aFi;
        public TextView aFj;
        public TextView aFk;

        private a() {
        }
    }

    public AllStudioAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return UtilsFunction.empty(this.aab) ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return UtilsFunction.empty(this.aab) ? null : (com.MCWorld.data.studio.a.a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            holder = new a();
            convertView = this.mInflater.inflate(R.layout.item_all_studio, null);
            holder.aFi = (PaintView) convertView.findViewById(R.id.riv_avatar);
            holder.aFj = (TextView) convertView.findViewById(R.id.tv_studio_name);
            holder.aFk = (TextView) convertView.findViewById(R.id.tv_studio_identify);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        final com.MCWorld.data.studio.a.a item = (com.MCWorld.data.studio.a.a) getItem(position);
        a(holder, item);
        convertView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AllStudioAdapter aFh;

            public void onClick(View v) {
                k.startPersonalStudio(this.aFh.mContext, item.sid);
            }
        });
        return convertView;
    }

    private void a(a holder, com.MCWorld.data.studio.a.a item) {
        holder.aFi.scaleType(ScaleType.CENTER_CROP).placeHolder(d.r(this.mContext, R.attr.default_discover_pic)).radius((float) t.dipToPx(this.mContext, 7)).setUri(UtilUri.getUriOrNull(item.icon)).setImageLoader(l.cb().getImageLoader());
        holder.aFj.setText(item.name);
        if (UtilsFunction.empty(item.identityTitle)) {
            holder.aFk.setVisibility(8);
            return;
        }
        holder.aFk.setVisibility(0);
        holder.aFk.setText(item.identityTitle);
        if (item.getIdentityColor() != 0) {
            holder.aFk.setTextColor(item.getIdentityColor());
            holder.aFk.setBackgroundDrawable(y.z(UtilsScreen.dipToPx(this.mContext, 1), item.getIdentityColor(), UtilsScreen.dipToPx(this.mContext, 3), 0));
        }
    }

    public void c(List<com.MCWorld.data.studio.a.a> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bg(R.id.rly_all_studio, R.attr.listSelector).bj(R.id.riv_avatar, R.attr.default_discover_pic).bh(R.id.tv_studio_name, 16842808).bf(R.id.divideline, R.attr.dividingLine);
    }
}
