package hlx.ui.mapseed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.http.toolbox.entity.utils.TextUtils;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.l;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.az;
import com.MCWorld.utils.y;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import hlx.ui.mapseed.b.a;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SeedAdapter extends BaseAdapter implements b {
    private boolean aTm = d.isDayMode();
    private List<a> cbH = new ArrayList();
    private Context mContext;
    private LayoutInflater mInflater;

    public List<a> getData() {
        return this.cbH;
    }

    public SeedAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
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

    public void setDayMode(boolean dayMode) {
        this.aTm = dayMode;
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
        a holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_seed_download, parent, false);
            holder = new a();
            holder.cbK = (ViewGroup) convertView.findViewById(R.id.rly_container);
            holder.mRootView = (ViewGroup) convertView.findViewById(R.id.root_view);
            holder.cbL = (PaintView) convertView.findViewById(R.id.seed_image);
            holder.eN = (TextView) convertView.findViewById(R.id.seed_name);
            holder.cbM = (TextView) convertView.findViewById(R.id.seed_version);
            holder.cbQ = (TextView) convertView.findViewById(R.id.text_time);
            holder.cbN = (TextView) convertView.findViewById(R.id.seed_type);
            holder.cbO = (TextView) convertView.findViewById(R.id.label);
            holder.cbP = (TextView) convertView.findViewById(R.id.seed_generate_number);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, (a) this.cbH.get(position));
        return convertView;
    }

    public void a(a viewHolder, a item) {
        String tmpStr;
        viewHolder.cbL.placeHolder(this.aTm ? R.drawable.discover_pic : R.drawable.discover_pic_night);
        viewHolder.cbL.setImageUrl(aw.gu(item.icon), l.cb().getImageLoader());
        if (item.version == null || item.version.length() <= 0) {
            viewHolder.cbM.setVisibility(8);
        } else {
            viewHolder.cbM.setVisibility(0);
        }
        viewHolder.cbM.setText(item.version);
        viewHolder.eN.setText(item.name == null ? "" : item.name);
        if (TextUtils.isEmpty(item.cateName)) {
            viewHolder.cbN.setVisibility(8);
        } else {
            viewHolder.cbN.setVisibility(0);
            b(viewHolder, item);
            viewHolder.cbN.setText(item.cateName);
        }
        viewHolder.cbQ.setText(b(item));
        if (item.downCount > 9999) {
            long generationNumbe = item.downCount % 10000;
            tmpStr = String.format("生成%s万", new Object[]{bN(item.downCount / 10000)});
            if (generationNumbe / 1000 > 0) {
                tmpStr = tmpStr + String.format("%d千", new Object[]{Long.valueOf(generationNumbe / 1000)});
            }
            tmpStr = tmpStr + "次";
        } else {
            tmpStr = String.format("生成%s次", new Object[]{bN(item.downCount)});
        }
        viewHolder.cbP.setText(tmpStr);
        viewHolder.cbK.setOnClickListener(new 1(this, item));
        viewHolder.mRootView.setOnClickListener(new 2(this, item));
    }

    protected void b(a holder, a info) {
        holder.cbN.setBackgroundDrawable(y.I(this.mContext, info.cateName));
        holder.cbN.setTextColor(y.D(this.mContext, info.cateName));
    }

    public static String bN(long data) {
        return new DecimalFormat("#,###").format(data);
    }

    public void a(j setter) {
        setter.bh(R.id.seed_version, R.attr.version).bh(R.id.text_time, R.attr.version).bh(R.id.seed_name, R.attr.mapName).bh(R.id.seed_generate_number, R.attr.authorName).bh(R.id.text_progress, R.attr.join).bg(R.id.root_view, R.attr.itemBackground).bg(R.id.image_download, R.attr.seedGenerate).bf(R.id.bottom_dividing_line, R.attr.dividingLine).bf(R.id.dividing_line, R.attr.dividingLine);
    }

    protected String b(a info) {
        if (info.createTime == null) {
            return "未知";
        }
        long time = System.currentTimeMillis() - Long.parseLong(info.createTime);
        return time > 0 ? az.bC(time / 1000) : "刚刚";
    }
}
