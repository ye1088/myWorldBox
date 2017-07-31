package hlx.ui.itemadapter.news;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.t;
import com.huluxia.utils.y;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import hlx.module.news.e;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter implements b {
    public static final int bYC = 4;
    private List<hlx.module.news.c.a> aab = new ArrayList();
    private int bYD = this.mContext.getResources().getColor(R.color.tag_green);
    private int bYE = d.getColor(this.mContext, 16842808);
    private Context mContext;
    private LayoutInflater mInflater;

    static class a {
        PaintView aOJ;
        TextView aRh;
        TextView bYF;
        List<TextView> tags = new ArrayList(4);

        a() {
        }
    }

    public NewsAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public int getCount() {
        if (this.aab == null) {
            return 0;
        }
        return this.aab.size();
    }

    public Object getItem(int position) {
        return this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_news, parent, false);
            holder = new a();
            holder.aOJ = (PaintView) convertView.findViewById(R.id.image);
            holder.aRh = (TextView) convertView.findViewById(R.id.news_title);
            holder.bYF = (TextView) convertView.findViewById(R.id.news_content);
            holder.tags.add((TextView) convertView.findViewById(R.id.tag1));
            holder.tags.add((TextView) convertView.findViewById(R.id.tag2));
            holder.tags.add((TextView) convertView.findViewById(R.id.tag3));
            holder.tags.add((TextView) convertView.findViewById(R.id.tag4));
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, (hlx.module.news.c.a) getItem(position));
        return convertView;
    }

    private void a(a holder, hlx.module.news.c.a data) {
        t.b(holder.aOJ, data.imgurl, (float) t.dipToPx(this.mContext, 4));
        holder.aRh.setText(data.title);
        holder.bYF.setText(data.content);
        for (int i = 0; i < 4; i++) {
            TextView textView = (TextView) holder.tags.get(i);
            if (i < data.tags.size()) {
                e tag = (e) data.tags.get(i);
                if (tag != null) {
                    textView.setVisibility(0);
                    textView.setText(tag.tagName);
                    int tagColor = Y(tag.tagColor, this.bYD);
                    textView.setBackgroundDrawable(y.d(this.mContext, 3, tagColor));
                    textView.setTextColor(tagColor);
                }
            } else {
                textView.setVisibility(8);
            }
        }
    }

    public void setData(List<hlx.module.news.c.a> data) {
        this.aab.clear();
        this.aab.addAll(data);
        notifyDataSetChanged();
    }

    private int Y(String colorStr, int defaultValue) {
        try {
            return Color.parseColor(colorStr);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public void a(j setter) {
        setter.bg(R.id.news_item, R.attr.listSelector).bh(R.id.news_title, 16842808).bh(R.id.news_content, 16843282).bi(R.id.image, R.attr.valBrightness).bf(R.id.item_split, R.attr.splitColor);
    }
}
