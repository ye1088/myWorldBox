package com.huluxia.ui.bbs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;

public class TopicDetailPageTurnLayout extends LinearLayout {
    private BaseAdapter RH = new BaseAdapter(this) {
        final /* synthetic */ TopicDetailPageTurnLayout aPw;

        {
            this.aPw = this$0;
        }

        public long getItemId(int pos) {
            return 0;
        }

        public int getCount() {
            return this.aPw.aPu;
        }

        public Object getItem(int pos) {
            if (pos == -1 || pos >= this.aPw.aPr) {
                return null;
            }
            return String.valueOf(this.aPw.aPo + pos);
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            b holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(i.topic_detail_item_page_number, null);
                holder = new b();
                holder.aPx = convertView.findViewById(g.LyContainer);
                holder.RZ = (TextView) convertView.findViewById(g.tvPageItemNumText);
                holder.aPy = (ImageView) convertView.findViewById(g.circle_bg);
                convertView.setTag(holder);
            } else {
                holder = (b) convertView.getTag();
            }
            holder.aPy.setVisibility(8);
            if (this.aPw.aPo + pos > this.aPw.aPq) {
                holder.RZ.setEnabled(false);
            }
            if (pos == -1 || pos >= this.aPw.aPr) {
                holder.RZ.setText("");
            } else if (this.aPw.aPo + pos <= 99) {
                holder.RZ.setText(String.format("%02d", new Object[]{Integer.valueOf(this.aPw.aPo + pos)}));
            } else {
                holder.RZ.setText(String.valueOf(this.aPw.aPo + pos));
            }
            if (this.aPw.aPo + pos == this.aPw.aPp) {
                holder.aPy.setVisibility(0);
                if (this.aPw.aPv) {
                    holder.aPy.setImageResource(f.page_button_green_back);
                    holder.RZ.setTextColor(this.aPw.getResources().getColor(d.white));
                } else {
                    holder.aPy.setImageResource(f.page_button_green_back_night);
                    holder.RZ.setTextColor(this.aPw.getResources().getColor(d.text_num_night_color));
                }
            }
            if ((pos < 0 || pos > 4) && (pos < 10 || pos > 14)) {
                if (this.aPw.aPv) {
                    holder.aPx.setBackgroundColor(this.aPw.getResources().getColor(d.page_num_double_color));
                } else {
                    holder.aPx.setBackgroundColor(this.aPw.getResources().getColor(d.pageturn_num_double_night_color));
                }
            } else if (this.aPw.aPv) {
                holder.aPx.setBackgroundColor(this.aPw.getResources().getColor(d.page_num_singgle_color));
            } else {
                holder.aPx.setBackgroundColor(this.aPw.getResources().getColor(d.pageturn_num_singgle_night_color));
            }
            holder.RZ.setTag(Integer.valueOf(this.aPw.aPo + pos));
            holder.RZ.setOnClickListener(this.aPw.Tp);
            return convertView;
        }
    };
    private OnClickListener Tp = new OnClickListener(this) {
        final /* synthetic */ TopicDetailPageTurnLayout aPw;

        {
            this.aPw = this$0;
        }

        public void onClick(View v) {
            int _tmpPressIndex = ((Integer) v.getTag()).intValue();
            if (_tmpPressIndex <= this.aPw.aPq && this.aPw.aPt != null) {
                this.aPw.aPt.kz(_tmpPressIndex);
            }
            this.aPw.RH.notifyDataSetChanged();
        }
    };
    private int aPo;
    private int aPp;
    private int aPq;
    private int aPr;
    private GridView aPs;
    private a aPt;
    private int aPu;
    private boolean aPv = false;
    private Activity mActivity;

    public interface a {
        void kz(int i);
    }

    private static class b {
        int RY;
        TextView RZ;
        View aPx;
        ImageView aPy;

        private b() {
        }
    }

    public TopicDetailPageTurnLayout(Activity context, int inputCurIndex, int inputStartIndex, int inputEndIndex, int inputDataPageCnt) {
        super(context);
        LayoutInflater.from(context).inflate(i.layout_topic_detail_pageturn, this);
        this.mActivity = context;
        this.aPp = inputCurIndex;
        this.aPo = inputStartIndex;
        this.aPq = inputEndIndex;
        this.aPu = inputDataPageCnt;
        this.aPr = (this.aPq - this.aPo) + 1;
        this.aPv = com.simple.colorful.d.isDayMode();
        sJ();
    }

    private void sJ() {
        this.aPs = (GridView) findViewById(g.gvPageItemList);
        this.aPs.setAdapter(this.RH);
    }

    public void setOnPageItemClick(a onPageItemClick) {
        this.aPt = onPageItemClick;
    }
}
