package hlx.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import hlx.data.home.menu.a.a;
import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private LayoutInflater bGD;
    private Context context;
    private List<Object> list;

    public MenuAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
        this.bGD = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public Object getItem(int position) {
        return this.list.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (!(getItem(position) instanceof a)) {
            return this.bGD.inflate(R.layout.item_menu_block, null);
        }
        a holder = new a(this, null);
        convertView = this.bGD.inflate(R.layout.item_menu_home, null);
        holder.bPn = (ImageView) convertView.findViewById(R.id.img_menu_title);
        holder.bPo = (TextView) convertView.findViewById(R.id.tv_menu_title);
        holder.bPp = (ImageView) convertView.findViewById(R.id.img_menu_go);
        holder.bPq = (TextView) convertView.findViewById(R.id.tv_count);
        holder.bPr = convertView.findViewById(R.id.view_split_line);
        if (position == 0 || position == getCount() - 2) {
            holder.bPr.setVisibility(8);
        }
        a(holder, (a) this.list.get(position));
        return convertView;
    }

    private void a(a holder, a item) {
        holder.bPn.setImageResource(item.bIU);
        holder.bPo.setText(item.title);
        if (item.bIV) {
            holder.bPp.setVisibility(8);
        } else {
            holder.bPp.setVisibility(0);
        }
        c(holder.bPq, item.count);
    }

    private void c(TextView tap, int count) {
        if (count > 0) {
            tap.setVisibility(0);
            if (count > 99) {
                tap.setText("99+");
                return;
            } else {
                tap.setText(String.valueOf(count));
                return;
            }
        }
        tap.setVisibility(8);
    }
}
