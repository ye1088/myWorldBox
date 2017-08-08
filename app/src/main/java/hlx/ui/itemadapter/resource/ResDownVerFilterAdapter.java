package hlx.ui.itemadapter.resource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import java.util.ArrayList;
import java.util.List;

public class ResDownVerFilterAdapter extends BaseAdapter {
    private int aaP = 0;
    private List<String> aab;
    private Context mContext;

    public static class a {
        TextView bYI;
        ImageView bYJ;
    }

    public /* synthetic */ Object getItem(int i) {
        return kA(i);
    }

    public ResDownVerFilterAdapter(Context c) {
        this.mContext = c;
        this.aab = new ArrayList();
        this.aab.add("全部");
        this.aab.add(hlx.data.localstore.a.bJr);
        this.aab.add(hlx.data.localstore.a.bJs);
        this.aab.add(hlx.data.localstore.a.bJt);
        this.aab.add("0.13");
        this.aab.add("0.14");
        this.aab.add(hlx.data.localstore.a.bJy);
    }

    public void nA(int position) {
        this.aaP = position;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aab.size();
    }

    public String kA(int position) {
        return (String) this.aab.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.itm_res_down_version_filter, parent, false);
            holder = new a();
            holder.bYI = (TextView) convertView.findViewById(R.id.tvResDownFilterName);
            holder.bYJ = (ImageView) convertView.findViewById(R.id.ivResDownFilterCheck);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, position);
        return convertView;
    }

    public void a(a viewHolder, int position) {
        viewHolder.bYI.setText(kA(position));
        viewHolder.bYJ.setVisibility(position == this.aaP ? 0 : 8);
    }
}
