package com.huluxia.ui.itemadapter.topic;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.topic.a$a;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.t;
import com.huluxia.utils.ad;
import java.util.ArrayList;
import java.util.List;

class ResTopicDetailItemAdapter$e extends BaseAdapter {
    private OnTouchListener QP = new OnTouchListener(this) {
        final /* synthetic */ ResTopicDetailItemAdapter$e aWV;

        {
            this.aWV = this$0;
        }

        public boolean onTouch(View v, MotionEvent event) {
            ImageView view = (ImageView) v;
            a$a userInfo = (a$a) v.getTag();
            switch (event.getAction() & 255) {
                case 0:
                    if (view.getDrawable() != null) {
                        view.getDrawable().setColorFilter(1996488704, Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    break;
                case 1:
                    t.a(this.aWV.mContext, userInfo.userid, null);
                    break;
                case 3:
                    break;
            }
            if (view.getDrawable() != null) {
                view.getDrawable().clearColorFilter();
                view.invalidate();
            }
            return true;
        }
    };
    private ArrayList<a$a> aWT = new ArrayList();
    private Context mContext;

    private class a {
        PaintView aRg;
        final /* synthetic */ ResTopicDetailItemAdapter$e aWV;
        TextView aWW;
        TextView name;

        private a(ResTopicDetailItemAdapter$e resTopicDetailItemAdapter$e) {
            this.aWV = resTopicDetailItemAdapter$e;
        }
    }

    public /* synthetic */ Object getItem(int i) {
        return kP(i);
    }

    public ResTopicDetailItemAdapter$e(Context context) {
        this.mContext = context;
    }

    public void f(List<a$a> data) {
        this.aWT.clear();
        if (!ad.empty(data)) {
            this.aWT.addAll(data);
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aWT.size();
    }

    public a$a kP(int position) {
        return (a$a) this.aWT.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        final a$a userInfo = kP(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(i.item_studio_member_introduce, null);
            holder = new a();
            holder.aRg = (PaintView) convertView.findViewById(g.member_image);
            holder.name = (TextView) convertView.findViewById(g.tv_member_name);
            holder.aWW = (TextView) convertView.findViewById(g.tv_member_position);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        t.b(holder.aRg, userInfo.avatar, 0.0f);
        holder.aRg.setTag(userInfo);
        holder.aRg.setOnTouchListener(this.QP);
        holder.name.setText(userInfo.userName);
        switch (userInfo.isStudio) {
            case 1:
                holder.aWW.setVisibility(0);
                holder.aWW.setText("室长");
                break;
            case 2:
                holder.aWW.setVisibility(0);
                holder.aWW.setText("副室长");
                break;
            default:
                holder.aWW.setVisibility(4);
                break;
        }
        convertView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailItemAdapter$e aWV;

            public void onClick(View v) {
                t.a(this.aWV.mContext, userInfo.userid, null);
            }
        });
        return convertView;
    }
}
