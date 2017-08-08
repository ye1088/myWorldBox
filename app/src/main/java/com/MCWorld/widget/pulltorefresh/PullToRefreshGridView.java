package com.MCWorld.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

public class PullToRefreshGridView extends PullToRefreshListView {
    private int bEp;
    private boolean bEq = false;
    private int horizontalSpacing;
    private int numColumns = 1;
    private int verticalSpacing;
    private int width;

    public PullToRefreshGridView(Context context) {
        super(context);
        bL(context);
    }

    public PullToRefreshGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bL(context);
    }

    private void bL(Context context) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.width = dm.widthPixels;
        this.verticalSpacing = (int) TypedValue.applyDimension(1, (float) this.verticalSpacing, dm);
        this.horizontalSpacing = (int) TypedValue.applyDimension(1, (float) this.horizontalSpacing, dm);
        this.bEp = (this.width - ((this.numColumns - 1) * this.horizontalSpacing)) / this.numColumns;
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(b(adapter));
    }

    public void a(ListAdapter adapter) {
        setAdapter(adapter);
    }

    private ListAdapter b(final ListAdapter adapter) {
        final int size = adapter.getCount();
        final int remainder = size % this.numColumns;
        return new BaseAdapter(this) {
            final /* synthetic */ PullToRefreshGridView bEu;

            public View getView(int position, View convertView, ViewGroup parent) {
                int columns;
                int i = 0;
                View rlParent = (LinearLayout) convertView;
                if (rlParent == null) {
                    rlParent = new LinearLayout(this.bEu.getContext());
                    rlParent.setLayoutParams(new LayoutParams(-1, -2));
                    rlParent.setOrientation(0);
                }
                if (position == getCount() - 1 && remainder != 0) {
                    columns = remainder;
                } else if (position < getCount()) {
                    columns = this.bEu.numColumns;
                } else {
                    columns = -1;
                }
                View[] views = new View[columns];
                for (int i2 = 0; i2 < columns; i2++) {
                    View v = adapter.getView((this.bEu.numColumns * position) + i2, rlParent.getChildAt(i2), parent);
                    this.bEu.setLayoutParams(v);
                    views[i2] = v;
                }
                rlParent.removeAllViews();
                int length = views.length;
                while (i < length) {
                    rlParent.addView(views[i]);
                    i++;
                }
                return rlParent;
            }

            public long getItemId(int position) {
                return (long) position;
            }

            public Object getItem(int position) {
                return Integer.valueOf(position);
            }

            public int getCount() {
                int count = size / this.bEu.numColumns;
                if (remainder != 0) {
                    return count + 1;
                }
                return count;
            }
        };
    }

    private void setLayoutParams(View view) {
        if (PP()) {
            view.setLayoutParams(new RelativeLayout.LayoutParams(this.bEp, this.bEp));
        } else {
            view.setLayoutParams(new RelativeLayout.LayoutParams(this.bEp, -2));
        }
    }

    public int getNumColumns() {
        return this.numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        this.bEp = (this.width - ((numColumns - 1) * this.horizontalSpacing)) / numColumns;
    }

    public int getVerticalSpacing() {
        return this.verticalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public int getHorizontalSpacing() {
        return this.horizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        this.bEp = (this.width - ((this.numColumns - 1) * horizontalSpacing)) / this.numColumns;
    }

    public boolean PP() {
        return this.bEq;
    }

    public void setFlagWide(boolean flagWide) {
        this.bEq = flagWide;
    }
}
