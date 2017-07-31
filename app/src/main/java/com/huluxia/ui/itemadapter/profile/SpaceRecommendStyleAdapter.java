package com.huluxia.ui.itemadapter.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.module.profile.h;
import com.huluxia.t;
import com.huluxia.utils.aw;
import com.huluxia.widget.HtImageView;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class SpaceRecommendStyleAdapter extends BaseAdapter implements b {
    private boolean aLf;
    private int aWe = 180;
    private ArrayList<h> aWf;
    private int aWg;
    private int aWh;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ SpaceRecommendStyleAdapter aWi;

        {
            this.aWi = this$0;
        }

        public void onClick(View v) {
            t.a(this.aWi.mContext, ((Integer) v.getTag()).intValue(), this.aWi.aLf, this.aWi.aWf);
        }
    };
    private Context mContext;
    private LayoutInflater mInflater;

    private class a {
        PaintView aRg;
        final /* synthetic */ SpaceRecommendStyleAdapter aWi;
        View aWj;
        HtImageView aWk;
        TextView aWl;
        TextView aWm;
        HtImageView aWn;
        TextView name;

        private a(SpaceRecommendStyleAdapter spaceRecommendStyleAdapter) {
            this.aWi = spaceRecommendStyleAdapter;
        }
    }

    public SpaceRecommendStyleAdapter(Context context, Boolean fromHome) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.aLf = fromHome.booleanValue();
    }

    public int getCount() {
        return this.aWf == null ? 0 : this.aWf.size();
    }

    public Object getItem(int position) {
        return this.aWf.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(i.item_space_recommend_style, parent, false);
            holder = new a();
            holder.aWj = convertView.findViewById(g.container_img);
            holder.aRg = (PaintView) convertView.findViewById(g.img);
            holder.aWk = (HtImageView) convertView.findViewById(g.selected_image);
            holder.name = (TextView) convertView.findViewById(g.name);
            holder.aWl = (TextView) convertView.findViewById(g.size);
            holder.aWm = (TextView) convertView.findViewById(g.use_condition);
            holder.aWn = (HtImageView) convertView.findViewById(g.bg_use_condition);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a((h) getItem(position), holder, position);
        LayoutParams lp = (LayoutParams) holder.aRg.getLayoutParams();
        lp.height = this.aWe;
        holder.aRg.setLayoutParams(lp);
        return convertView;
    }

    private void a(h spaceStyle, a holder, int position) {
        int useConditionRes;
        int useConditionColor;
        t.a(holder.aRg, spaceStyle.imgurl, 0, this.aWe);
        holder.name.setText(aw.W(spaceStyle.title, 4));
        holder.aWl.setText(bn(spaceStyle.size));
        holder.aWj.setTag(Integer.valueOf(position));
        holder.aWj.setOnClickListener(this.mClickListener);
        if (this.aWh == 2 && this.aWg == spaceStyle.id) {
            holder.aWk.setVisibility(0);
        } else {
            holder.aWk.setVisibility(8);
        }
        if (spaceStyle.model == 0) {
            useConditionRes = m.free;
            useConditionColor = d.getColor(this.mContext, 16842809);
            holder.aWn.setBackgroundResource(f.bg_space_style_free);
        } else if (spaceStyle.model == 1) {
            useConditionRes = m.contribution;
            holder.aWn.setBackgroundResource(f.bg_space_style_integral);
            useConditionColor = this.mContext.getResources().getColor(com.huluxia.bbs.b.d.text_space_style_exchanged);
        } else {
            useConditionRes = m.exchanged;
            useConditionColor = this.mContext.getResources().getColor(com.huluxia.bbs.b.d.text_space_style_exchanged);
        }
        holder.aWm.setText(useConditionRes);
        holder.aWm.setTextColor(useConditionColor);
    }

    @SuppressLint({"DefaultLocale"})
    private String bn(long bytes) {
        if (bytes > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            double size = (((double) bytes) * 1.0d) / 1024.0d;
            return String.format("%1$.1fM", new Object[]{Double.valueOf(size)});
        }
        return this.mContext.getResources().getString(m.format_photo_size, new Object[]{Long.valueOf(bytes)});
    }

    public void setImageHeight(int height) {
        this.aWe = height;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<h> spacelist) {
        this.aWf = spacelist;
        notifyDataSetChanged();
    }

    public void ax(int spaceId, int model) {
        this.aWg = spaceId;
        this.aWh = model;
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bi(g.img, c.valBrightness).bi(g.selected_image, c.valBrightness).bi(g.bg_use_condition, c.valBrightness).bh(g.name, 16842808).bh(g.size, 16843282);
    }
}
