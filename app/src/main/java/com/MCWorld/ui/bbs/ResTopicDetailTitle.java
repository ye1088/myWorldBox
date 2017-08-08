package com.MCWorld.ui.bbs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.map.f.a;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.hlistview.HListView;
import com.MCWorld.l;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.az;
import com.simple.colorful.c;
import com.simple.colorful.d;
import java.util.List;

public class ResTopicDetailTitle extends LinearLayout implements c {
    private static final String TAG = "ResTopicDetailTitle";
    private static final int aOm = 0;
    private ProgressBar aMM = ((ProgressBar) findViewById(g.progress_bar_download));
    private HListView aOA = ((HListView) findViewById(g.photoWall));
    private LinearLayout aOB = ((LinearLayout) findViewById(g.ll_map_info));
    private TextView aOj = ((TextView) findViewById(g.map_name));
    private TextView aOn = ((TextView) findViewById(g.map_version));
    private TextView aOo = ((TextView) findViewById(g.map_type));
    private TextView aOp = ((TextView) findViewById(g.map_size));
    private TextView aOq = ((TextView) findViewById(g.map_author));
    private TextView aOr = ((TextView) findViewById(g.map_date));
    private TextView aOs = ((TextView) findViewById(g.map_studio));
    private TextView aOt = ((TextView) findViewById(g.map_download));
    private PaintView aOu = ((PaintView) findViewById(g.map_image));
    private TextView aOv = ((TextView) findViewById(g.tv_more_resource));
    private View aOw = findViewById(g.split_item);
    private Button aOx = ((Button) findViewById(g.btn_download));
    private LinearLayout aOy = ((LinearLayout) findViewById(g.ll_map_detail));
    private LinearLayout aOz = ((LinearLayout) findViewById(g.linear_list_view));
    private Context mContext;

    public ResTopicDetailTitle(Activity context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_resource_topic_title, this);
        this.mContext = context;
        findViewById(g.map_header).setVisibility(0);
    }

    public void setInfo(a data) {
        if (data != null) {
            this.aOj.setText(data.name);
            this.aOo.setText("类型：" + data.cateName);
            this.aOq.setText("作者：" + data.author);
            this.aOr.setText("时间：" + az.bF(Long.valueOf(data.createTime).longValue()));
            this.aOt.setText("下载：" + data.downCount);
            this.aOB.setVisibility(0);
            setMapSize(data);
            this.aOu.setDefaultImageResId(d.isDayMode() ? f.discover_pic : f.discover_pic_night);
            this.aOu.setImageUrl(data.icon, l.cb().getImageLoader());
            if (UtilsFunction.empty(data.version)) {
                this.aOn.setVisibility(8);
            } else {
                this.aOn.setVisibility(0);
                if (data.version.contains(hlx.data.localstore.a.bJr) && data.version.contains(hlx.data.localstore.a.bJs) && data.version.contains(hlx.data.localstore.a.bJt) && data.version.contains("0.13") && data.version.contains("0.14")) {
                    this.aOn.setText("通用");
                } else {
                    this.aOn.setText(data.version);
                }
            }
            a(this.aOA, data.resourceList);
            this.aOv.setOnClickListener(new 1(this, data));
        }
    }

    public void b(a data, int extendType) {
        if (data != null && extendType == 1) {
            this.aOj.setText(data.name);
            if (UtilsFunction.empty(data.cateName)) {
                this.aOo.setText("类型：无");
            } else {
                this.aOo.setText("类型：" + data.cateName);
            }
            this.aOp.setText("生成：" + data.downCount);
            this.aOu.setDefaultImageResId(d.isDayMode() ? f.discover_pic : f.discover_pic_night);
            this.aOu.setImageUrl(data.icon, l.cb().getImageLoader());
            this.aOB.setVisibility(8);
            if (UtilsFunction.empty(data.version)) {
                this.aOn.setVisibility(8);
            } else {
                this.aOn.setVisibility(0);
                if ((data.version.contains(hlx.data.localstore.a.bJr) && data.version.contains(hlx.data.localstore.a.bJs) && data.version.contains(hlx.data.localstore.a.bJt) && data.version.contains("0.13") && data.version.contains("0.14")) || data.version.length() == 0) {
                    this.aOn.setText("通用");
                } else {
                    this.aOn.setText(data.version);
                }
            }
            a(this.aOA, data.resourceList);
        }
    }

    public void setStudioName(String studioName) {
        this.aOs.setText("工作室：" + studioName);
    }

    public void setMapSize(a resInfo) {
        if (resInfo.mapSize != null && aw.validNumber(resInfo.mapSize)) {
            this.aOp.setText("大小：" + aw.bA(Long.valueOf(resInfo.mapSize).longValue()));
        }
    }

    private void a(HListView photoWall, List<String> images) {
        if (images.size() > 0) {
            this.aOz.setVisibility(0);
            ListAdapter adapter = new a(this.mContext);
            photoWall.setAdapter(adapter);
            adapter.a(images, 0, null);
            photoWall.setOnItemClickListener(null);
            return;
        }
        this.aOz.setVisibility(8);
    }

    public View getSplitTopView() {
        return this.aOw;
    }

    public Button getDownButton() {
        return this.aOx;
    }

    public ProgressBar getmProgressTop() {
        return this.aMM;
    }

    public View getMapDetail() {
        return this.aOy;
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        builder.aY(g.header_container, b.c.backgroundDefault).be(g.map_image, b.c.valBrightness).ba(g.map_name, 16842806).ba(g.map_type, 16842808).ba(g.map_size, 16842808).ba(g.map_author, 16842808).ba(g.map_date, 16842808).ba(g.map_studio, 16842808).ba(g.map_download, 16842808).i(findViewById(g.split_item), b.c.splitColor).i(findViewById(g.split_item1), b.c.splitColor).i(findViewById(g.split_item2), b.c.splitColor).i(findViewById(g.split_item3), b.c.splitColor).ba(g.tv_more_resource, b.c.textColorQuaternary).ab(g.tv_more_resource, b.c.ic_more_resource_arrow, 2);
        return builder;
    }

    public void FG() {
    }
}
