package hlx.ui.localresmgr.backupmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.data.map.b;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.utils.aw;
import java.util.List;

public class SpecifyMapBackupManagerListAdapter extends BaseAdapter {
    private List<b> aab;
    private Context mContext;

    public SpecifyMapBackupManagerListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<b> data) {
        this.aab = data;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.itm_local_resmgr_map, parent, false);
            holder = new a();
            holder.bZu = (PaintView) convertView.findViewById(R.id.rlyLocalResMgrMapItemImg);
            holder.aOj = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemTitle);
            holder.bZv = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemDate);
            holder.bZw = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemSize);
            convertView.findViewById(R.id.rlyChkSwitch).setVisibility(8);
            convertView.findViewById(R.id.rlyResMgrItemDel).setVisibility(8);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, (b) this.aab.get(position));
        return convertView;
    }

    public void a(a viewHolder, b item) {
        String tmpName = item.name;
        if (tmpName.contains(".zip")) {
            tmpName = tmpName.substring(0, tmpName.length() - 4);
        }
        viewHolder.aOj.setText(tmpName);
        viewHolder.bZv.setText(item.date);
        viewHolder.bZw.setText(String.format("%s", new Object[]{aw.bz(item.size)}));
        if (item.pR.startsWith("http")) {
            t.b(viewHolder.bZu, item.pR, 0.0f);
        }
    }
}
