package com.MCWorld.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.j;
import com.MCWorld.data.profile.GiftInfo;
import com.MCWorld.data.profile.ProductList;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.simple.colorful.c;
import com.simple.colorful.setter.k;
import java.util.ArrayList;
import java.util.List;

public class GiftLayout extends BaseLoadingLayout implements OnItemClickListener, c {
    private List<GiftInfo> aab;
    private long bfk = 0;
    private a bfl;
    private GridView bfm;
    private Context context;
    private int mType = 0;
    private String nick;

    private class a extends BaseAdapter {
        final /* synthetic */ GiftLayout bfn;
        private List<GiftInfo> objs;

        public a(GiftLayout giftLayout, List<GiftInfo> objs) {
            this.bfn = giftLayout;
            this.objs = objs;
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
            a holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(i.item_credit_gift, parent, false);
                holder = new a(this);
                holder.aRg = (PaintView) convertView.findViewById(g.img_gift);
                holder.aRh = (TextView) convertView.findViewById(g.title);
                holder.bfo = (FrameLayout) convertView.findViewById(g.fl_credit);
                holder.bfp = (TextView) convertView.findViewById(g.credits);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            GiftInfo info = (GiftInfo) getItem(position);
            int size = (at.getScreenPixWidth(this.bfn.context) - at.dipToPx(this.bfn.context, 16)) / 3;
            holder.aRg.setLayoutParams(new LayoutParams(size, size));
            t.a(holder.aRg, info.getIcon(), 0.0f);
            holder.aRh.setText(info.getName());
            if (this.bfn.mType == 1) {
                holder.bfo.setBackgroundResource(f.bg_exchange_integral);
                holder.bfp.setCompoundDrawablesWithIntrinsicBounds(f.ic_cup_white_small, 0, 0, 0);
            }
            holder.bfp.setText(String.valueOf(info.getCredits()));
            return convertView;
        }

        public void setData(List<GiftInfo> data) {
            this.objs = data;
            notifyDataSetChanged();
        }
    }

    public GiftLayout(Context context, int type) {
        super(context);
        this.context = context;
        this.mType = type;
    }

    protected void c(Context context, AttributeSet attrs) {
        super.c(context, attrs);
        addView(LayoutInflater.from(context).inflate(i.include_video_detail_drama, this, false));
        this.bfm = (GridView) findViewById(g.drama);
        this.aab = new ArrayList();
        this.bfl = new a(this, this.aab);
        this.bfm.setAdapter(this.bfl);
        this.bfm.setOnItemClickListener(this);
        this.bfm.setSelector(d.transparent);
    }

    public void setGift(ProductList data) {
        this.aab = data.getGifts();
        this.bfl.setData(this.aab);
    }

    public void setUser(ProductList data) {
        this.bfk = data.getUser() == null ? 0 : data.getUser().getCredits();
        this.nick = data.getUser() == null ? "" : data.getUser().getNick();
        this.nick = aw.W(this.nick, 10);
    }

    public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
        GiftInfo info = (GiftInfo) this.bfl.getItem(position);
        if (this.mType == 0) {
            b(info);
        } else if (this.mType == 1) {
            c(info);
        }
    }

    public void Ff() {
        if (this.bfl != null) {
            this.bfl.notifyDataSetChanged();
        }
    }

    private void b(GiftInfo info) {
        if (!j.ep().ey()) {
            t.an(this.context);
        } else if (this.nick != null && info != null) {
            t.a(this.context, info, this.bfk);
        }
    }

    private void c(GiftInfo data) {
        String msg = data.getDesc();
        if (msg != null) {
            final Dialog dialog = new Dialog(getContext(), com.simple.colorful.d.RD());
            View layout = LayoutInflater.from(getContext()).inflate(i.include_dialog_one, null);
            ((TextView) layout.findViewById(g.tv_msg)).setText(msg);
            dialog.setContentView(layout);
            dialog.show();
            layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ GiftLayout bfn;

                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
        }
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        k setter = new com.simple.colorful.setter.j(this.bfm);
        setter.bg(g.item_gift, b.c.backgroundItemGift).bh(g.title, 16842806);
        builder.a(setter);
        return builder;
    }

    public void FG() {
    }
}
