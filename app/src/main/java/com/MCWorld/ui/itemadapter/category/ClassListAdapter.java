package com.MCWorld.ui.itemadapter.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.MCWorld.HTApplication;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.l;
import com.MCWorld.service.i;
import com.MCWorld.t;
import com.MCWorld.utils.ah;
import com.MCWorld.utils.at;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.List;

public class ClassListAdapter extends BaseAdapter implements b {
    public static final int aSG = 1;
    public static final int aSH = 2;
    public static final int aSI = -2;
    public static final int aSJ = -3;
    private int aSK = 0;
    private Context context;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ ClassListAdapter aSL;

        {
            this.aSL = this$0;
        }

        public void onClick(View view) {
            View msgView = view.findViewById(g.msg);
            if (msgView != null) {
                msgView.setVisibility(8);
            }
            msgView = view.findViewById(g.msg_2);
            if (msgView != null) {
                msgView.setVisibility(8);
            }
            i.EK();
            HTApplication.i(0);
            t.a(this.aSL.context, (TopicCategory) view.getTag());
        }
    };
    private LayoutInflater mInflater;
    private List<Object> objs;

    static class a {
        a aSM;
        a aSN;

        static class a {
            View aSO;
            PaintView aSP;
            TextView aSQ;
            TextView aSR;

            a() {
            }
        }

        a() {
        }
    }

    public ClassListAdapter(Context context, List<Object> objects) {
        this.objs = objects;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.aSK = at.dipToPx(context, 5);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TopicCategory data = (TopicCategory) getItem(position);
        TopicCategory data2 = null;
        if ((position * 2) + 1 < this.objs.size()) {
            data2 = (TopicCategory) this.objs.get((position * 2) + 1);
        }
        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = this.mInflater.inflate(com.MCWorld.bbs.b.i.item_class_tag, parent, false);
            }
            ((TextView) convertView.findViewById(g.tv_tag)).setText(data.getTitle());
        } else {
            a holder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(com.MCWorld.bbs.b.i.item_class_line, parent, false);
                holder = new a();
                a item1 = new a();
                item1.aSO = convertView.findViewById(g.item_container);
                item1.aSP = (PaintView) convertView.findViewById(g.riv_class_logo);
                item1.aSQ = (TextView) convertView.findViewById(g.title);
                item1.aSR = (TextView) convertView.findViewById(g.msg);
                holder.aSM = item1;
                a item2 = new a();
                item2.aSO = convertView.findViewById(g.item_container2);
                item2.aSP = (PaintView) convertView.findViewById(g.riv_class_logo2);
                item2.aSQ = (TextView) convertView.findViewById(g.title2);
                item2.aSR = (TextView) convertView.findViewById(g.msg_2);
                holder.aSN = item2;
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            a(data, holder.aSM);
            a(data2, holder.aSN);
        }
        return convertView;
    }

    private void a(TopicCategory data, a item) {
        if (data == null || data.getType() != 2) {
            item.aSO.setVisibility(8);
            return;
        }
        item.aSO.setVisibility(0);
        item.aSP.setUri(UtilUri.getUriOrNull(data.getIcon())).radius((float) this.aSK).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
        item.aSQ.setText(data.getTitle());
        item.aSO.setTag(data);
        item.aSO.setOnClickListener(this.mClickListener);
        item.aSR.setVisibility(8);
        if (data.getType() == 2 && data.getCategoryID() == 0) {
            long count = ah.KZ().LX();
            if (count > 0) {
                item.aSR.setVisibility(0);
                item.aSR.setText(count > 99 ? "99+" : String.valueOf(count));
            }
        }
    }

    public int getCount() {
        return this.objs == null ? 0 : (this.objs.size() + 1) / 2;
    }

    public Object getItem(int position) {
        return this.objs.get(position * 2);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int getItemViewType(int position) {
        if (((TopicCategory) getItem(position)).getType() == 1) {
            return 0;
        }
        return 1;
    }

    public void a(j setter) {
        setter.bf(g.tag_split_bottom, c.splitColor).bf(g.tv_tag, c.splitColorDim).bg(g.item_container, c.listSelector).bh(g.title, 16842808).bg(g.item_container2, c.listSelector).bh(g.title2, 16842808).bf(g.item_split_horizontal, c.splitColor).bf(g.item_split_vertical, c.splitColor).bi(g.riv_class_logo, c.valBrightness).bi(g.riv_class_logo2, c.valBrightness);
    }
}
