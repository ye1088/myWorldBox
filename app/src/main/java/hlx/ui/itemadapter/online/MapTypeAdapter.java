package hlx.ui.itemadapter.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import java.util.ArrayList;

public class MapTypeAdapter extends BaseAdapter {
    private ArrayList<hlx.module.resources.a> bCz;
    private int bYG;
    private Context mContext;

    private static class a {
        private TextView bYH;
        private ImageView image;

        private a() {
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return nz(i);
    }

    public MapTypeAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        return this.bCz == null ? 0 : this.bCz.size();
    }

    public hlx.module.resources.a nz(int position) {
        return (hlx.module.resources.a) this.bCz.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a hoder;
        hlx.module.resources.a item = nz(position);
        if (convertView == null) {
            hoder = new a();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_map_type, parent, false);
            hoder.bYH = (TextView) convertView.findViewById(R.id.map_type);
            hoder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(hoder);
        } else {
            hoder = (a) convertView.getTag();
        }
        hoder.bYH.setText(item.catename);
        hoder.image.setVisibility(this.bYG == position ? 0 : 8);
        return convertView;
    }

    public void nA(int clickPosition) {
        this.bYG = clickPosition;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<hlx.module.resources.a> data) {
        this.bCz = data;
    }
}
