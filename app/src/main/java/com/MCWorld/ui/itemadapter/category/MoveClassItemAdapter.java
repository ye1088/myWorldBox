package com.MCWorld.ui.itemadapter.category;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class MoveClassItemAdapter extends ArrayAdapter<Object> implements b {
    private static final int aTc = 1;
    private boolean aTd = true;
    private Context ctx;

    public MoveClassItemAdapter(Context context, ArrayList<Object> objects, boolean flag) {
        super(context, i.listitem_moveclass, g.title, objects);
        this.ctx = context;
        this.aTd = flag;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TopicCategory data = (TopicCategory) getItem(position);
        if (1 == data.type) {
            a(view, data);
        } else {
            b(view, data);
        }
        return view;
    }

    private void a(View view, TopicCategory data) {
        view.findViewById(g.rly_tag).setVisibility(0);
        view.findViewById(g.split_class_tag).setVisibility(0);
        view.findViewById(g.rly_class).setVisibility(8);
        view.findViewById(g.split_class).setVisibility(8);
        ((TextView) view.findViewById(g.tag_title)).setText(data.title);
    }

    private void b(View view, TopicCategory data) {
        if (data.categoryID == 0) {
            view.findViewById(g.rly_tag).setVisibility(8);
            view.findViewById(g.split_class_tag).setVisibility(8);
            view.findViewById(g.rly_class).setVisibility(8);
            return;
        }
        view.findViewById(g.rly_tag).setVisibility(8);
        view.findViewById(g.split_class_tag).setVisibility(8);
        view.findViewById(g.rly_class).setVisibility(0);
        view.findViewById(g.split_class).setVisibility(0);
        ((TextView) view.findViewById(g.title)).setText(data.title);
        ((TextView) view.findViewById(g.description)).setText(data.description);
        t.a((PaintView) view.findViewById(g.icon), data.icon, (float) t.dipToPx(this.ctx, 6));
        ImageView iv_tu = (ImageView) view.findViewById(g.iv_tu);
        if (data.isGood == 1) {
            iv_tu.setVisibility(0);
        } else {
            iv_tu.setVisibility(8);
        }
        if (this.aTd) {
            ((Button) view.findViewById(g.btn_info)).setVisibility(8);
            view.findViewById(g.rly_class).setOnClickListener(new 1(this, data));
        }
    }

    public void a(j setter) {
        setter.bf(g.rly_tag, c.backgroundDim).bh(g.title, 16842806).bh(g.tag_title, 16842808).bh(g.description, 16842808).bi(g.icon, c.valBrightness).bi(g.iv_tu, c.valBrightness).bf(g.split_class_tag, c.splitColorDim).bf(g.split_class, c.splitColor);
    }
}
