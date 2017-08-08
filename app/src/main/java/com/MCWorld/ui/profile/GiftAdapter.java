package com.MCWorld.ui.profile;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.profile.GiftInfo;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.utils.at;
import java.util.List;

public class GiftAdapter extends BaseAdapter {
    private Context context;
    private List<GiftInfo> objs;

    public GiftAdapter(List<GiftInfo> objs, Context context) {
        this.objs = objs;
        this.context = context;
    }

    public int getCount() {
        return this.objs.size();
    }

    public Object getItem(int position) {
        return this.objs.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout ly = (RelativeLayout) convertView;
        if (ly == null) {
            ly = new RelativeLayout(parent.getContext());
            LayoutInflater.from(parent.getContext()).inflate(i.include_gift_item, ly);
        }
        GiftInfo info = (GiftInfo) getItem(position);
        PaintView image = (PaintView) ly.findViewById(g.imgGift);
        image.setLayoutParams(new LayoutParams(-1, (at.getScreenPixWidth(this.context) - at.dipToPx(this.context, 32)) / 3));
        t.a(image, info.getIcon(), 0.0f);
        ((TextView) ly.findViewById(g.title)).setText(info.getName());
        TextView credits = (TextView) ly.findViewById(g.credits);
        credits.setText(Html.fromHtml("<font color='#D62841'>" + info.getConvert() + "</font>"));
        credits.setVisibility(0);
        ly.setTag(info);
        return ly;
    }
}
