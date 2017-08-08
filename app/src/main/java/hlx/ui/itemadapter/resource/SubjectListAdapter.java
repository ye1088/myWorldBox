package hlx.ui.itemadapter.resource;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.r;
import com.MCWorld.t;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;
import java.util.List;

public class SubjectListAdapter extends BaseAdapter implements b {
    private static final SparseArrayCompat<String> bYK = new SparseArrayCompat();
    private static final SparseArrayCompat<String> bYL = new SparseArrayCompat();
    private boolean aTm = true;
    private List<hlx.module.resources.d.a> aab = new ArrayList();
    private int axr;
    private Context context;
    private LayoutInflater mInflater;

    public static class a {
        PaintView bYN;
        TextView bYO;
        LinearLayout bYP;
    }

    public List<hlx.module.resources.d.a> getData() {
        return this.aab;
    }

    public void setDayMode(boolean dayMode) {
        this.aTm = dayMode;
    }

    static {
        bYK.put(1, "map_subject");
        bYK.put(2, "js_subject");
        bYK.put(3, "skin_subject");
        bYK.put(4, "wood_subject");
        bYL.put(1, hlx.data.tongji.a.bMw);
        bYL.put(2, hlx.data.tongji.a.bMx);
        bYL.put(3, hlx.data.tongji.a.bMz);
        bYL.put(4, hlx.data.tongji.a.bMy);
    }

    public SubjectListAdapter(Context context, int resType) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.axr = resType;
        this.aTm = d.isDayMode();
    }

    public void c(List<hlx.module.resources.d.a> data, boolean clear) {
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

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void a(j setter) {
        setter.bf(R.id.root_view, R.attr.tabBackground).bg(R.id.guide_line_image, R.attr.guideLine).bh(R.id.topic_title, R.attr.tabText).bf(R.id.top_dividing_line, R.attr.dividingLine).bf(R.id.interval_block, R.attr.home_interval_bg).bf(R.id.bottom_dividing_line, R.attr.dividingLine);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.map_item_subject, parent, false);
            holder = new a();
            holder.bYN = (PaintView) convertView.findViewById(R.id.topic_item_image);
            holder.bYO = (TextView) convertView.findViewById(R.id.topic_title);
            holder.bYP = (LinearLayout) convertView.findViewById(R.id.root_view);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        t.b(holder.bYN, ((hlx.module.resources.d.a) this.aab.get(position)).icon, 0.0f);
        holder.bYO.setText(((hlx.module.resources.d.a) this.aab.get(position)).name);
        holder.bYP.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SubjectListAdapter bYM;

            public void onClick(View v) {
                hlx.module.resources.d.a item = (hlx.module.resources.d.a) this.bYM.aab.get(position);
                String className = (String) SubjectListAdapter.bYK.get(this.bYM.axr);
                String tongjiEvent = (String) SubjectListAdapter.bYL.get(this.bYM.axr);
                if (!UtilsFunction.empty(className)) {
                    r.ck().K_umengEvent(tongjiEvent + String.valueOf(item.id));
                    HLog.verbose(toString(), tongjiEvent + String.valueOf(item.id), new Object[0]);
                    com.MCWorld.ui.mctool.d.b(this.bYM.context, className, item.id, item.name);
                }
            }
        });
        return convertView;
    }
}
