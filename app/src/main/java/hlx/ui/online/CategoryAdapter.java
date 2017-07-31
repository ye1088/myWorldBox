package hlx.ui.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.toolbox.entity.utils.TextUtils;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.l;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter implements b {
    private ArrayList<hlx.module.resources.a> aQR = new ArrayList();
    private final Context mContext;

    public static class a {
        public PaintView aVk;
        public TextView cch;

        public a(View rootView) {
            this.aVk = (PaintView) rootView.findViewById(R.id.image);
            this.cch = (TextView) rootView.findViewById(R.id.all_category);
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return nz(i);
    }

    public CategoryAdapter(Context context) {
        this.mContext = context;
    }

    public void a(ArrayList<hlx.module.resources.a> data, boolean clear) {
        if (clear) {
            this.aQR.clear();
        }
        this.aQR.addAll(data);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aQR.size();
    }

    public hlx.module.resources.a nz(int position) {
        return (hlx.module.resources.a) this.aQR.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        hlx.module.resources.a mapCateItem = nz(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_category, parent, false);
            holder = new a(convertView);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        if (!TextUtils.isEmpty(mapCateItem.imgurl)) {
            holder.aVk.setImageUrl(mapCateItem.imgurl, l.cb().getImageLoader());
        }
        holder.cch.setText(mapCateItem.catename);
        return convertView;
    }

    public void a(j setter) {
        setter.bi(R.id.image, R.attr.valBrightness).bf(R.id.root_view, R.attr.label);
    }
}
