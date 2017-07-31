package com.huluxia.widget.emoInput;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.huluxia.utils.at;
import java.util.List;

/* compiled from: EmojiGrid */
public class b {
    public static final int PAGE_SIZE = 21;
    private List<String> aPH = this.byb.getTags();
    private d byb = d.Ou();
    private int byc;
    private GridView[] byd;
    private a[] bye;

    /* compiled from: EmojiGrid */
    private static class a extends BaseAdapter {
        private d byb;
        private List<String> data;
        private int startIndex;

        public /* synthetic */ Object getItem(int i) {
            return kA(i);
        }

        a(d fm) {
            this.byb = fm;
        }

        public void c(int startIndex, List<String> data) {
            this.startIndex = startIndex;
            this.data = data;
        }

        public int getCount() {
            int size = this.data == null ? 0 : this.data.size();
            if (size <= 0) {
                return 0;
            }
            size -= this.startIndex;
            if (size <= 0) {
                return 0;
            }
            if (size > 21) {
                return 21;
            }
            return size;
        }

        public String kA(int position) {
            return (String) this.data.get(this.startIndex + position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                View iv = new ImageView(parent.getContext());
                int dp_5 = at.dipToPx(parent.getContext(), 6);
                iv.setPadding(dp_5, dp_5, dp_5, dp_5);
                iv.setScaleType(ScaleType.CENTER_INSIDE);
                int columnW = (at.getScreenPixWidth(parent.getContext()) - (dp_5 * 2)) / 7;
                iv.setLayoutParams(new LayoutParams(columnW, columnW));
                convertView = iv;
            }
            ((ImageView) convertView).setImageResource(this.byb.gO(kA(position)));
            return convertView;
        }
    }

    public b(Context context) {
        int size;
        if (this.aPH == null) {
            size = 0;
        } else {
            size = this.aPH.size();
        }
        this.byc = size / 21;
        if (size % 21 > 0) {
            this.byc++;
        }
        this.byd = new GridView[this.byc];
        this.bye = new a[this.byc];
        for (int i = 0; i < this.byc; i++) {
            this.byd[i] = new GridView(context);
            this.byd[i].setNumColumns(7);
            int dp_5 = at.dipToPx(context, 10);
            this.byd[i].setPadding(dp_5, dp_5, dp_5, dp_5);
            this.byd[i].setVerticalScrollBarEnabled(false);
            this.byd[i].setVerticalFadingEdgeEnabled(false);
            this.bye[i] = new a(this.byb);
            this.bye[i].c(i * 21, this.aPH);
            this.byd[i].setAdapter(this.bye[i]);
            this.byd[i].setTag(Integer.valueOf(i));
        }
    }

    public int getPageCount() {
        return this.byc;
    }

    public View lX(int page) {
        return this.byd[page];
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        for (GridView grid : this.byd) {
            grid.setOnItemClickListener(l);
        }
    }
}
