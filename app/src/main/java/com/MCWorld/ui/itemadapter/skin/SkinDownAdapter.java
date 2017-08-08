package com.MCWorld.ui.itemadapter.skin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.data.map.f;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.l;
import com.MCWorld.mctool.e;
import com.MCWorld.r;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.itemadapter.map.DownAdapter.b;
import com.MCWorld.utils.ah;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.j;
import com.MCWorld.widget.RoundProgress;
import hlx.data.localstore.a;

public class SkinDownAdapter extends DownAdapter {
    private final String aKs = a.bKa;
    Activity aTg;

    public SkinDownAdapter(Activity context) {
        super(context);
        this.aTg = context;
        TAG = "SkinDownAdapter";
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        f.a info = kG(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.aTg).inflate(R.layout.item_resource, parent, false);
            holder = new b();
            holder.aTy = (TextView) convertView.findViewById(R.id.map_name);
            holder.aTv = (TextView) convertView.findViewById(R.id.version);
            holder.aTw = (TextView) convertView.findViewById(R.id.text_time);
            holder.aTu = (TextView) convertView.findViewById(R.id.label);
            holder.aTz = (TextView) convertView.findViewById(R.id.map_type);
            holder.aTA = (TextView) convertView.findViewById(R.id.map_author_name);
            holder.aTB = (TextView) convertView.findViewById(R.id.text_progress);
            holder.aRg = (PaintView) convertView.findViewById(R.id.item_image);
            holder.aTE = (LinearLayout) convertView.findViewById(R.id.normal_line_layout);
            holder.aTx = (TextView) convertView.findViewById(R.id.studio_author_name);
            holder.aTI = (ImageView) convertView.findViewById(R.id.img_rank);
            holder.aFj = (TextView) convertView.findViewById(R.id.studio_name);
            holder.aTC = (TextView) convertView.findViewById(R.id.spectial_studio_name);
            holder.aTG = (RelativeLayout) convertView.findViewById(R.id.root_view);
            holder.aTH = (ImageView) convertView.findViewById(R.id.image_download);
            holder.aTJ = (RoundProgress) convertView.findViewById(R.id.progress);
            holder.aTK = convertView.findViewById(R.id.dividing_line);
            holder.aTM = convertView.findViewById(R.id.bottom_dividing_line);
            holder.aTL = convertView.findViewById(R.id.studio_dividing_line);
            holder.aTD = (TextView) convertView.findViewById(R.id.spectial_cate);
            holder.aTN = (RelativeLayout) convertView.findViewById(R.id.rly_container);
            holder.aTO = convertView.findViewById(R.id.sercond_bottom_dividing_line);
            holder.aTP = convertView.findViewById(R.id.third_bottom_dividing_line);
            holder.aTQ = convertView.findViewById(R.id.spectial_layout_bg);
            holder.aTR = (PaintView) convertView.findViewById(R.id.pv_studio_icon);
            holder.aTS = (PaintView) convertView.findViewById(R.id.pv_studio_icon_indivi);
            holder.aTT = (TextView) convertView.findViewById(R.id.tv_studio_hot);
            holder.aTU = (TextView) convertView.findViewById(R.id.tv_normal_label);
            holder.aTV = (TextView) convertView.findViewById(R.id.tv_map_down_count);
            holder.aTW = (TextView) convertView.findViewById(R.id.tv_map_down_count_spectial);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        b(holder, info);
        if (this.aTk) {
            a(holder, position);
        }
        a(holder, info);
        holder.aTy.setText(info.name);
        holder.aRg.setImageUrl(aw.gu(info.icon), l.cb().getImageLoader());
        holder.aTN.setOnClickListener(new a(this, holder, info));
        holder.aTv.setVisibility(8);
        holder.aTw.setText(d(info));
        holder.aTG.setOnClickListener(new 1(this, info));
        return convertView;
    }

    private void a(String name, String path, String date, int paraState, String ver) {
        ah.KZ().gb(path);
    }

    public void eu(String url) {
        if (this.aTn) {
            notifyDataSetChanged();
        }
        r.ck().K_umengEvent(hlx.data.tongji.a.bML);
        e.Dk().iS(1);
    }

    public boolean a(f.a info) {
        return j.isExist(j.cU(true) + info.name + a.bKa);
    }

    protected int FW() {
        return com.MCWorld.controller.resource.factory.b.a.nA;
    }
}
