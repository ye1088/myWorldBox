package hlx.ui.heroslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import hlx.ui.heroslist.c.a;
import hlx.utils.d;
import java.util.ArrayList;
import java.util.List;

public class HerosListAdapter extends BaseAdapter {
    protected static String TAG = "HerosListAdapter";
    protected List<a> aab = new ArrayList();
    protected int bYj;
    protected boolean bYl = true;
    private Context context;

    public HerosListAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return this.aab.size();
    }

    public Object getItem(int position) {
        return this.aab.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        a _itemInfo = (a) this.aab.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.itm_ins_zones_ranking_list, parent, false);
            holder = new a();
            holder.bYo = (ImageView) convertView.findViewById(R.id.ivInsZonesItemRankNo);
            holder.bYp = (TextView) convertView.findViewById(R.id.tvInsZonesItemRankNo);
            holder.bYq = (PaintView) convertView.findViewById(R.id.rivInsZonesRankAvatar);
            holder.bYr = (TextView) convertView.findViewById(R.id.tvInsZonesItemRankNickname);
            holder.bYs = (TextView) convertView.findViewById(R.id.tvInsZonesItemRankLevel);
            holder.bYt = (TextView) convertView.findViewById(R.id.tvInsZonesItemRankScore);
            holder.bYu = (ImageView) convertView.findViewById(R.id.ivInsZonesItemRankReword);
            holder.bYv = (LinearLayout) convertView.findViewById(R.id.llyInsZonesRankMore);
            holder.bYw = (TextView) convertView.findViewById(R.id.tvInsZonesRankFollow);
            holder.bYx = (TextView) convertView.findViewById(R.id.tvInsZonesRankGift);
            holder.bYy = (ImageView) convertView.findViewById(R.id.ivInsZonesItemRankMore);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, position, (a) this.aab.get(position));
        holder.bYy.setOnClickListener(new 1(this, _itemInfo));
        return convertView;
    }

    public void a(a viewHolder, int position, a item) {
        try {
            viewHolder.bYr.setText(item.userName);
            if (this.bYj == 0) {
                viewHolder.bYt.setText(String.format("总分:%d", new Object[]{Integer.valueOf(item.integral)}));
            } else if (this.bYj == 1) {
                viewHolder.bYt.setText(String.format("耗时:%s", new Object[]{d.nR(item.integral)}));
            }
            viewHolder.bYp.setText(String.valueOf(item.rank + 1));
            if (item.headImgUrl == null || true != item.headImgUrl.endsWith(".ht")) {
                viewHolder.bYq.setImageDrawable(this.context.getResources().getDrawable(R.drawable.icon_default_hero));
            } else {
                t.b(viewHolder.bYq, item.headImgUrl, 0.0f);
            }
        } catch (Exception e) {
        }
    }

    public void a(List<a> data, int heroCateOrderType, boolean clear) {
        if (clear) {
            this.aab.clear();
        }
        this.aab.addAll(data);
        this.bYj = heroCateOrderType;
        notifyDataSetChanged();
    }

    public void HA() {
        this.aab.clear();
        notifyDataSetChanged();
    }
}
