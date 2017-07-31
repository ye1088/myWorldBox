package com.huluxia.widget.emoInput;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView.ScaleType;
import com.huluxia.bbs.b.f;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.t;
import com.huluxia.utils.at;
import com.huluxia.widget.emoInput.FaceItem.FACE_TYPE;

public class FaceGrid extends GridView {
    private a byi = null;

    private static class a extends BaseAdapter {
        private FaceItem[] byj = null;
        private int byk = 1;
        private int byl = 0;
        private int pageSize = 0;

        public /* synthetic */ Object getItem(int i) {
            return lY(i);
        }

        public void setNumColumns(int mNumColumns) {
            this.byk = mNumColumns;
            if (mNumColumns == 7) {
                this.byl = (FacePanelView.byA - (FacePanelView.byE * 2)) / 3;
                this.pageSize = mNumColumns * 3;
                return;
            }
            this.byl = (FacePanelView.byA - (FacePanelView.byE * 2)) / 2;
            this.pageSize = this.byj.length;
        }

        public a(FaceItem[] data) {
            this.byj = data;
        }

        public int getCount() {
            return this.pageSize;
        }

        public FaceItem lY(int position) {
            if (this.byk == 7 && position == this.pageSize - 1) {
                return FaceItem.Ot();
            }
            return this.byj[position];
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            FaceItem data = lY(position);
            if (convertView == null) {
                View iv = new PaintView(parent.getContext());
                int dp_5 = at.dipToPx(parent.getContext(), 6);
                iv.setPadding(dp_5, dp_5, dp_5, dp_5);
                iv.setScaleType(ScaleType.CENTER_INSIDE);
                iv.setLayoutParams(new LayoutParams(this.byl, this.byl));
                convertView = iv;
            }
            PaintView iv2 = (PaintView) convertView;
            iv2.setTag(data);
            if (data.bym == FACE_TYPE.TYPE_EMOJI) {
                iv2.setImageResource(data.byn);
            } else {
                t.b(iv2, data.byo, 0.0f);
            }
            return convertView;
        }
    }

    public FaceGrid(Context context, FaceItem[] data) {
        super(context);
        this.byi = new a(data);
        setAdapter(this.byi);
        setSelector(getResources().getDrawable(f.btn_emote_selector));
    }

    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        if (this.byi != null) {
            this.byi.setNumColumns(numColumns);
            this.byi.notifyDataSetChanged();
        }
    }
}
