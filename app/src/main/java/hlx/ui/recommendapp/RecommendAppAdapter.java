package hlx.ui.recommendapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.controller.resource.ResourceCtrl;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.bean.ResTaskInfo.State;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.t;
import com.huluxia.utils.y;
import com.huluxia.widget.RoundProgress;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import hlx.ui.recommendapp.a.a;
import hlx.utils.g;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecommendAppAdapter extends BaseAdapter implements b {
    private static final String TAG = "RecommendAppAdapter";
    private boolean aTm = true;
    private List<a> cbH;
    ExecutorService gp = Executors.newSingleThreadExecutor();
    private Context mContext;
    private LayoutInflater mInflater;

    public List<a> getData() {
        return this.cbH;
    }

    public void setDayMode(boolean dayMode) {
        this.aTm = dayMode;
        notifyDataSetChanged();
    }

    public RecommendAppAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.cbH = new ArrayList();
        this.aTm = d.isDayMode();
    }

    public void c(List<a> dataList, boolean isClear) {
        if (dataList != null) {
            if (isClear) {
                this.cbH.clear();
            }
            this.cbH.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void HA() {
        this.cbH.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.cbH == null ? 0 : this.cbH.size();
    }

    public Object getItem(int position) {
        return this.cbH.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_recommend_app_download, parent, false);
            holder = new b();
            holder.cfe = (PaintView) convertView.findViewById(R.id.round_image);
            holder.eN = (TextView) convertView.findViewById(R.id.title);
            holder.cff = (TextView) convertView.findViewById(R.id.type);
            holder.cfg = (TextView) convertView.findViewById(R.id.size);
            holder.cfh = (TextView) convertView.findViewById(R.id.text_progress);
            holder.cfj = (RoundProgress) convertView.findViewById(R.id.progress);
            holder.cfi = (ImageView) convertView.findViewById(R.id.image_download);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        a(holder, (a) this.cbH.get(position));
        return convertView;
    }

    public void a(b viewHolder, a item) {
        t.a(viewHolder.cfe, item.applogo, (float) t.dipToPx(this.mContext, 11));
        viewHolder.eN.setText(item.apptitle);
        viewHolder.cff.setText(item.categoryname);
        if (item.categoryname != null) {
            b(viewHolder, item);
        }
        viewHolder.cfg.setText(String.format(Locale.getDefault(), "%sMB", new Object[]{item.appsize}));
        viewHolder.cfi.setOnClickListener(new a(this, viewHolder, item));
        c(viewHolder, item);
    }

    protected void b(b holder, a info) {
        holder.cff.setBackgroundDrawable(y.G(this.mContext, info.categoryname));
        holder.cff.setTextColor(y.F(this.mContext, info.categoryname));
    }

    protected static String bO(long downloadCount) {
        if (downloadCount > 9999) {
            long generationNumber = downloadCount % 10000;
            String tmpStr = String.format("下载%s万", new Object[]{bN(downloadCount / 10000)});
            if (generationNumber / 1000 > 0) {
                tmpStr = tmpStr + String.format("%d千", new Object[]{Long.valueOf(generationNumber)});
            }
            return tmpStr + "次";
        }
        return String.format("下载%s次", new Object[]{bN(downloadCount)});
    }

    protected static String bN(long data) {
        return new DecimalFormat("#,###").format(data);
    }

    protected void c(b holder, a item) {
        if (g.b(this.mContext.getPackageManager(), item.packname, item.versionCode)) {
            ResTaskInfo rinfo = ResourceCtrl.getInstance().getTaskInfo(item.appdownurl, 1000008);
            if (rinfo == null || rinfo.state == State.FILE_DELETE.ordinal()) {
                holder.cfh.setText("下载");
                holder.cfj.setVisibility(4);
                a(holder, this.aTm, R.color.studio_text_color, R.color.item_resource_tab_default_night, R.drawable.btn_download_start_day_selector, R.drawable.btn_download_start_night_selector);
                holder.cfi.setClickable(true);
                return;
            } else if (rinfo.state == State.DOWNLOAD_PAUSE.ordinal() || rinfo.state == State.DOWNLOAD_ERROR.ordinal()) {
                if (rinfo.state == State.DOWNLOAD_ERROR.ordinal()) {
                    HLog.error("RecommendAppAdapter.setDownloadProgress", "下载出错" + rinfo.state + rinfo.filename + rinfo.dataDownUrl, new Object[0]);
                }
                holder.cfh.setText("继续");
                holder.cfj.setVisibility(4);
                a(holder, this.aTm, R.color.text_continue_color, R.color.item_resource_continue_night, R.drawable.btn_download_continue_day_selector, R.drawable.btn_download_continue_night_selector);
                holder.cfi.setClickable(true);
                return;
            } else if (rinfo.state == State.WAITING.ordinal() || rinfo.state == State.PREPARE.ordinal()) {
                holder.cfh.setText("等待中");
                holder.cfj.setVisibility(0);
                a(holder, this.aTm, R.color.studio_text_color, R.color.item_resource_tab_default_night, R.drawable.btn_download_pause_day_selector, R.drawable.btn_download_pause_night_selector);
                holder.cfi.setClickable(false);
                return;
            } else if (rinfo.state == State.DOWNLOAD_PROGRESS.ordinal() || rinfo.state == State.DOWNLOAD_ERROR_RETRY.ordinal()) {
                holder.cfj.setVisibility(0);
                float progress = 0.0f;
                if (rinfo.mN.total > 0) {
                    progress = ((float) rinfo.mN.progress) / ((float) rinfo.mN.total);
                }
                this.gp.submit(new 1(this, holder, rinfo));
                holder.cfh.setText(((int) (100.0f * progress)) + "%");
                a(holder, this.aTm, R.color.studio_text_color, R.color.item_resource_tab_default_night, R.drawable.btn_download_pause_day_selector, R.drawable.btn_download_pause_night_selector);
                holder.cfi.setClickable(true);
                return;
            } else if (rinfo.state == State.SUCC.ordinal()) {
                holder.cfh.setText("安装");
                holder.cfj.setVisibility(4);
                a(holder, this.aTm, R.color.text_continue_color, R.color.item_resource_continue_night, R.drawable.btn_download_install_day_selector, R.drawable.btn_download_install_night_selector);
                holder.cfi.setClickable(true);
                return;
            } else if (rinfo.state == State.DOWNLOAD_START.ordinal()) {
                holder.cfi.setClickable(true);
                return;
            } else {
                return;
            }
        }
        holder.cfh.setText("启动");
        holder.cfj.setVisibility(4);
        a(holder, this.aTm, R.color.text_start_color, R.color.home_titlebar_bg_night, R.drawable.btn_download_start_up_day_selector, R.drawable.btn_download_start_up_night_selector);
        holder.cfi.setClickable(true);
    }

    void a(b holder, boolean isDayMode, int dayColorId, int nightColorId, int dayImgResId, int nightImgResId) {
        int color;
        Resources resources = this.mContext.getResources();
        holder.cfh.setTextColor(isDayMode ? resources.getColor(dayColorId) : resources.getColor(nightColorId));
        ImageView imageView = holder.cfi;
        if (!isDayMode) {
            dayImgResId = nightImgResId;
        }
        imageView.setImageResource(dayImgResId);
        holder.cfj.setDayMode(isDayMode);
        RoundProgress roundProgress = holder.cfj;
        if (isDayMode) {
            color = resources.getColor(R.color.item_resource_progress_day);
        } else {
            color = resources.getColor(R.color.item_resource_progress_night);
        }
        roundProgress.setProgressColor(color);
    }

    public void a(String url, ProgressInfo progressInfo) {
        notifyDataSetChanged();
    }

    public void eu(String url) {
        notifyDataSetChanged();
    }

    public void ew(String url) {
        notifyDataSetChanged();
    }

    public void ex(String url) {
        notifyDataSetChanged();
    }

    public void a(j setter) {
        setter.bh(R.id.title, R.attr.mapName).bh(R.id.size, R.attr.authorName).bf(R.id.dividing_line, R.attr.dividingLine).bf(R.id.bottom_dividing_line, R.attr.dividingLine).bf(R.id.root_view, R.attr.home_common_bg).bf(R.id.divideline, R.attr.dividingLine);
    }
}
