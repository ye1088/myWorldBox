package com.MCWorld.ui.itemadapter.map;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.data.map.MapItem;
import com.MCWorld.data.map.f.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.entity.utils.TextUtils;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.l;
import com.MCWorld.mctool.e;
import com.MCWorld.r;
import com.MCWorld.utils.ac;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.az;
import com.MCWorld.utils.j;
import com.MCWorld.utils.o;
import com.MCWorld.utils.y;
import com.MCWorld.widget.Constants.DownFileType;
import com.MCWorld.widget.RoundProgress;
import com.simple.colorful.b;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownAdapter extends BaseAdapter implements b {
    protected static String TAG = "DownAdapter";
    private static long aTl = 0;
    protected String aMW;
    private Activity aTg;
    protected boolean aTk;
    private boolean aTm;
    protected boolean aTn;
    HashSet<String> aTo;
    protected List<a> aab;
    ExecutorService gp;

    public /* synthetic */ Object getItem(int i) {
        return kG(i);
    }

    public List<a> getData() {
        return this.aab;
    }

    public DownAdapter(Activity context) {
        this.aTk = false;
        this.aab = new ArrayList();
        this.aMW = "default";
        this.gp = Executors.newSingleThreadExecutor();
        this.aTn = true;
        this.aTo = new HashSet();
        this.aTg = context;
        TAG = "DownAdapter";
        aTl = o.v(context);
        this.aTm = d.isDayMode();
    }

    public DownAdapter(Activity context, String src) {
        this(context);
        this.aMW = src;
        TAG = "DownAdapter";
        this.aTm = d.isDayMode();
    }

    public void cJ(boolean ranking) {
        this.aTk = ranking;
    }

    public void ev(String url) {
        boolean needChange = false;
        for (int i = 0; i < getCount(); i++) {
            if (kG(i).downUrl.equals(url)) {
                needChange = true;
                break;
            }
        }
        if (needChange) {
            notifyDataSetChanged();
        }
    }

    public void setDayMode(boolean dayMode) {
        this.aTm = dayMode;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public a kG(int position) {
        return (a) this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        a info = kG(position);
        if (convertView == null) {
            holder = new b();
            convertView = LayoutInflater.from(this.aTg).inflate(R.layout.item_resource, parent, false);
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
        holder.aRg.setImageUrl(aw.gu(info.icon), l.cb().getImageLoader());
        holder.aTN.setOnClickListener(new a(this, holder, info));
        holder.aTy.setText(info.name);
        a(holder, info);
        if (this.aTk) {
            a(holder, position);
        }
        if (info.version == null || info.version.equals("")) {
            holder.aTv.setVisibility(8);
        } else {
            holder.aTv.setVisibility(0);
            if (info.version.contains(hlx.data.localstore.a.bJr) && info.version.contains(hlx.data.localstore.a.bJs) && info.version.contains(hlx.data.localstore.a.bJt) && info.version.contains("0.13") && info.version.contains("0.14")) {
                holder.aTv.setText("通用");
            } else {
                holder.aTv.setText(info.version);
            }
        }
        holder.aTw.setText(d(info));
        holder.aTG.setOnClickListener(new 1(this, info));
        return convertView;
    }

    protected void a(b holder, int position) {
        if (position == 0) {
            holder.aTI.setImageResource(this.aTm ? R.drawable.rank_01 : R.drawable.rank_01_night);
            holder.aTI.setVisibility(0);
        } else if (position == 1) {
            holder.aTI.setImageResource(this.aTm ? R.drawable.rank_02 : R.drawable.rank_02_night);
            holder.aTI.setVisibility(0);
        } else if (position == 2) {
            holder.aTI.setImageResource(this.aTm ? R.drawable.rank_03 : R.drawable.rank_03_night);
            holder.aTI.setVisibility(0);
        } else {
            holder.aTI.setVisibility(8);
        }
    }

    protected void a(b holder, a info) {
        if (info != null) {
            TextView textView;
            int i;
            if (info.istmp == 1) {
                holder.aTO.setVisibility(8);
                holder.aTM.setVisibility(8);
                holder.aTP.setVisibility(0);
                holder.aTQ.setVisibility(0);
                textView = holder.aTz;
                if (info.cateName != null) {
                    i = 0;
                } else {
                    i = 8;
                }
                textView.setVisibility(i);
                holder.aTD.setVisibility(8);
                holder.aTW.setVisibility(8);
                holder.aTA.setVisibility(0);
                holder.aTz.setText(info.cateName);
                holder.aTz.setTextColor(y.C(this.aTg, info.cateName));
                holder.aTz.setBackgroundDrawable(y.J(this.aTg, info.cateName));
                textView = holder.aTV;
                if (info.cateName != null) {
                    i = 0;
                } else {
                    i = 8;
                }
                textView.setVisibility(i);
                holder.aTV.setText("下载量：" + ac.bs(info.downCount));
                holder.aTA.setText(info.author);
                if (info.user == null || TextUtils.isEmpty(info.user.identityTitle)) {
                    holder.aTu.setVisibility(8);
                } else {
                    holder.aTu.setVisibility(0);
                    holder.aTu.setText(info.user.identityTitle);
                    holder.aTu.setBackgroundDrawable(y.e(this.aTg, info.user.getIdentityColor(), 2));
                }
                holder.aTE.setVisibility(8);
                if (info.studio != null) {
                    holder.aTC.setText(info.studio.name);
                    holder.aTS.setUri(UtilUri.getUriOrNull(info.studio.icon)).oval().placeHolder(d.r(this.aTg, R.attr.spectialStudio)).setImageLoader(l.cb().getImageLoader());
                    holder.aTT.setText(ac.bs(info.studio.integral));
                }
            } else if (info.istmp != 0) {
            } else {
                if (info.studio == null) {
                    holder.aTO.setVisibility(8);
                    holder.aTM.setVisibility(0);
                    holder.aTP.setVisibility(8);
                    holder.aTQ.setVisibility(8);
                    textView = holder.aTz;
                    if (info.cateName != null) {
                        i = 0;
                    } else {
                        i = 8;
                    }
                    textView.setVisibility(i);
                    holder.aTD.setVisibility(8);
                    holder.aTW.setVisibility(8);
                    holder.aTA.setVisibility(0);
                    holder.aTz.setText(info.cateName);
                    holder.aTz.setTextColor(y.C(this.aTg, info.cateName));
                    holder.aTz.setBackgroundDrawable(y.J(this.aTg, info.cateName));
                    textView = holder.aTV;
                    if (info.cateName != null) {
                        i = 0;
                    } else {
                        i = 8;
                    }
                    textView.setVisibility(i);
                    holder.aTV.setText("下载量：" + ac.bs(info.downCount));
                    holder.aTA.setText(info.author);
                    if (info.user == null || TextUtils.isEmpty(info.user.identityTitle)) {
                        holder.aTu.setVisibility(8);
                    } else {
                        holder.aTu.setVisibility(0);
                        holder.aTu.setText(info.user.identityTitle);
                        holder.aTu.setBackgroundDrawable(y.e(this.aTg, info.user.getIdentityColor(), 2));
                    }
                    holder.aTE.setVisibility(8);
                    return;
                }
                holder.aTO.setVisibility(0);
                holder.aTM.setVisibility(8);
                holder.aTP.setVisibility(8);
                holder.aTQ.setVisibility(8);
                holder.aTz.setVisibility(8);
                holder.aTV.setVisibility(8);
                holder.aTA.setVisibility(8);
                holder.aTD.setVisibility(0);
                holder.aTD.setText(info.cateName);
                holder.aTD.setTextColor(y.C(this.aTg, info.cateName));
                holder.aTD.setBackgroundDrawable(y.J(this.aTg, info.cateName));
                holder.aTW.setVisibility(0);
                holder.aTW.setText("下载量：" + ac.bs(info.downCount));
                holder.aTu.setVisibility(8);
                holder.aTE.setVisibility(0);
                holder.aTx.setText(info.author);
                if (info.studio != null) {
                    holder.aFj.setText(info.studio.name);
                    holder.aTR.setUri(UtilUri.getUriOrNull(info.studio.icon)).oval().placeHolder(d.r(this.aTg, R.attr.spectialStudio)).setImageLoader(l.cb().getImageLoader());
                }
                if (info.user == null || TextUtils.isEmpty(info.user.identityTitle)) {
                    holder.aTU.setVisibility(8);
                    return;
                }
                holder.aTU.setVisibility(0);
                holder.aTU.setText(info.user.identityTitle);
                holder.aTU.setBackgroundDrawable(y.e(this.aTg, info.user.getIdentityColor(), 2));
            }
        }
    }

    @z
    protected String d(a info) {
        if (info.createTime == null) {
            return "未知";
        }
        long time = System.currentTimeMillis() - Long.parseLong(info.createTime);
        return time > 0 ? az.bC(time / 1000) : "刚刚";
    }

    protected String er(String name) {
        return name + ".zip";
    }

    public void a(String url, ProgressInfo progressInfo) {
        notifyDataSetChanged();
    }

    public void cK(boolean refreshUI) {
        this.aTn = refreshUI;
    }

    public void eu(String url) {
        if (this.aTn) {
            notifyDataSetChanged();
        }
        r.ck().K_umengEvent(hlx.data.tongji.a.bMI);
        e.Dk().iQ(1);
    }

    public void ew(String url) {
        notifyDataSetChanged();
    }

    public void ex(String url) {
        notifyDataSetChanged();
    }

    protected int FW() {
        return 1000000;
    }

    protected void b(b holder, a info) {
        holder.aRg.setDefaultImageResId(this.aTm ? R.drawable.discover_pic : R.drawable.discover_pic_night);
        String sSize = null;
        if (info.mapSize != null && info.mapSize.length() > 0 && aw.validNumber(info.mapSize)) {
            sSize = aw.bA(Long.valueOf(info.mapSize).longValue());
        }
        if (a(info)) {
            holder.aTB.setText("启动");
            holder.aTJ.setVisibility(4);
            holder.aTN.setClickable(true);
            a(holder, this.aTm, R.color.text_start_color, R.color.home_titlebar_bg_night, R.drawable.btn_download_start_up_day_selector, R.drawable.btn_download_start_up_night_selector);
        } else if (info.downUrl != null) {
            ResTaskInfo rinfo = ResourceCtrl.getInstance().getTaskInfo(info.downUrl, FW());
            if (rinfo != null) {
                HLog.verbose(TAG, "file ever download" + info.name + DownloadRecord.COLUMN_STATE + rinfo.state, new Object[0]);
                if (rinfo.state == State.DOWNLOAD_PAUSE.ordinal() || rinfo.state == State.DOWNLOAD_ERROR.ordinal()) {
                    holder.aTB.setText("继续");
                    holder.aTJ.setVisibility(4);
                    holder.aTN.setClickable(true);
                    a(holder, this.aTm, R.color.text_continue_color, R.color.item_resource_continue_night, R.drawable.btn_download_continue_day_selector, R.drawable.btn_download_continue_night_selector);
                    return;
                } else if (rinfo.state == State.WAITING.ordinal() || rinfo.state == State.PREPARE.ordinal()) {
                    holder.aTB.setText("等待中");
                    holder.aTJ.setVisibility(4);
                    holder.aTN.setClickable(false);
                    a(holder, this.aTm, R.color.studio_text_color, R.color.item_resource_tab_default_night, R.drawable.btn_download_pause_day_selector, R.drawable.btn_download_pause_night_selector);
                    return;
                } else if (rinfo.state == State.DOWNLOAD_PROGRESS.ordinal() || rinfo.state == State.DOWNLOAD_ERROR_RETRY.ordinal()) {
                    holder.aTJ.setVisibility(0);
                    this.gp.submit(new 2(this, holder, rinfo));
                    float progress = 0.0f;
                    if (rinfo.mN.total > 0) {
                        progress = ((float) rinfo.mN.progress) / ((float) rinfo.mN.total);
                    }
                    holder.aTB.setText(((int) (100.0f * progress)) + "%");
                    holder.aTN.setClickable(true);
                    a(holder, this.aTm, R.color.studio_text_color, R.color.item_resource_tab_default_night, R.drawable.btn_download_pause_day_selector, R.drawable.btn_download_pause_night_selector);
                    return;
                } else if (rinfo.state == State.UNZIP_PROGRESSING.ordinal()) {
                    holder.aTB.setText("解压中");
                    holder.aTJ.setVisibility(4);
                    a(holder, this.aTm, R.color.text_continue_color, R.color.item_resource_continue_night, R.drawable.btn_download_install_day_selector, R.drawable.btn_download_install_night_selector);
                    holder.aTN.setClickable(false);
                    return;
                } else if (rinfo.state == State.FILE_DELETE.ordinal()) {
                    holder.aTB.setText(sSize);
                    holder.aTJ.setVisibility(4);
                    a(holder, this.aTm, R.color.studio_text_color, R.color.item_resource_tab_default_night, R.drawable.btn_download_start_day_selector, R.drawable.btn_download_start_night_selector);
                    holder.aTN.setClickable(true);
                    return;
                } else if (rinfo.state == State.SUCC.ordinal()) {
                    holder.aTB.setText("启动");
                    holder.aTJ.setVisibility(4);
                    holder.aTN.setClickable(true);
                    a(holder, this.aTm, R.color.text_start_color, R.color.home_titlebar_bg_night, R.drawable.btn_download_start_up_day_selector, R.drawable.btn_download_start_up_night_selector);
                    return;
                } else {
                    return;
                }
            }
            holder.aTB.setText(sSize);
            holder.aTJ.setVisibility(4);
            a(holder, this.aTm, R.color.studio_text_color, R.color.item_resource_tab_default_night, R.drawable.btn_download_start_day_selector, R.drawable.btn_download_start_night_selector);
            holder.aTN.setClickable(true);
        }
    }

    void a(b holder, boolean mIsDayMode, int dayColorId, int nightColorId, int dayImgResId, int nightImgResId) {
        int color;
        Resources resources = this.aTg.getResources();
        holder.aTB.setTextColor(mIsDayMode ? resources.getColor(dayColorId) : resources.getColor(nightColorId));
        ImageView imageView = holder.aTH;
        if (!mIsDayMode) {
            dayImgResId = nightImgResId;
        }
        imageView.setImageResource(dayImgResId);
        holder.aTJ.setDayMode(mIsDayMode);
        RoundProgress roundProgress = holder.aTJ;
        if (mIsDayMode) {
            color = resources.getColor(R.color.item_resource_progress_day);
        } else {
            color = resources.getColor(R.color.item_resource_progress_night);
        }
        roundProgress.setProgressColor(color);
    }

    protected String H(float speed) {
        if (speed > 1048576.0f) {
            return ((int) (speed / 1048576.0f)) + "m/s";
        }
        if (speed > 1024.0f) {
            return ((int) (speed / 1024.0f)) + "kb/s";
        }
        return ((int) speed) + "b/s";
    }

    public boolean a(a item) {
        if (j.eS(item.name)) {
            return true;
        }
        return false;
    }

    public void a(ArrayList<a> data, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    public void HA() {
        this.aab.clear();
        notifyDataSetChanged();
    }

    public void a(com.simple.colorful.setter.j setter) {
        setter.bh(R.id.version, R.attr.version).bh(R.id.text_time, R.attr.version).bh(R.id.map_name, R.attr.mapName).bh(R.id.map_author_name, R.attr.authorName).bh(R.id.label, R.attr.label).bh(R.id.text_progress, R.attr.progressText).bh(R.id.spectial_studio_name, R.attr.studioText).bh(R.id.studio_name, R.attr.progressText).bh(R.id.studio_author_name, R.attr.progressText).bg(R.id.root_view, R.attr.itemBackground).bf(R.id.dividing_line, R.attr.dividingLine).bf(R.id.studio_dividing_line, R.attr.dividingLine).bf(R.id.bottom_dividing_line, R.attr.dividingLine).bf(R.id.sercond_bottom_dividing_line, R.attr.dividingLine).bf(R.id.third_bottom_dividing_line, R.attr.dividingLine).bf(R.id.spectial_layout_bg, R.attr.studioBg).bh(R.id.tv_studio_hot, R.attr.studio_hot_text_color).ac(R.id.tv_studio_hot, R.attr.studio_hot_icon, 1).bh(R.id.tv_normal_label, R.attr.label).bg(R.id.item_image, R.attr.discover).bh(R.id.tv_map_down_count, 16843282).bh(R.id.tv_map_down_count_spectial, 16843282);
    }

    protected String FV() {
        return HttpMgr.getInstance().getGameDownloadPath(DownFileType.defaultType).getAbsolutePath();
    }

    protected ResTaskInfo b(MapItem item, int resourceType) {
        ResTaskInfo tInfo = new ResTaskInfo();
        DownloadRecord downloadRecord = tInfo.mN;
        String str = item.url;
        tInfo.url = str;
        downloadRecord.url = str;
        downloadRecord = tInfo.mN;
        str = er(item.name);
        tInfo.filename = str;
        downloadRecord.name = str;
        downloadRecord = tInfo.mN;
        str = FV();
        tInfo.dir = str;
        downloadRecord.dir = str;
        tInfo.mM = resourceType;
        return tInfo;
    }
}
