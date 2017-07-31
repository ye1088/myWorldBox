package com.huluxia.widget.emoInput;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.huluxia.widget.emoInput.FaceItem.FACE_TYPE;
import com.huluxia.widget.viewpager.ViewPageIndicator;
import java.util.ArrayList;
import java.util.List;

public class FaceView extends RelativeLayout implements OnItemClickListener {
    private ViewPager byI;
    private ViewPageIndicator byJ;
    private FacePagerAdapter byK = new FacePagerAdapter();
    private FacePanelView byL = null;

    private static class FacePagerAdapter extends PagerAdapter {
        private List<FaceGrid> byM;

        private FacePagerAdapter() {
            this.byM = new ArrayList();
        }

        public void clear() {
            this.byM.clear();
        }

        public void a(FaceGrid grid) {
            this.byM.add(grid);
        }

        public int getCount() {
            return this.byM.size();
        }

        public int getItemPosition(Object object) {
            return -2;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            View grid = (View) this.byM.get(position);
            container.addView(grid);
            return grid;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public FaceView(Context context, FacePanelView parent) {
        super(context);
        this.byL = parent;
        init(context);
    }

    private void init(Context context) {
        this.byI = new ViewPager(context);
        this.byI.setLayoutParams(new LayoutParams(-1, -1));
        addView(this.byI);
        this.byJ = new ViewPageIndicator(context);
        LayoutParams params = new LayoutParams(-1, -1);
        params.addRule(12);
        this.byJ.setGravity(17);
        this.byJ.setLayoutParams(params);
        addView(this.byJ);
        this.byI.setAdapter(this.byK);
        this.byJ.setViewPager(this.byI);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.byJ.getLayoutParams().height = FacePanelView.byB;
        this.byI.getLayoutParams().height = FacePanelView.byA;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setData(FaceItem[] data) {
        this.byK.clear();
        this.byK.notifyDataSetChanged();
        this.byJ.notifyDataSetChanged();
        int numColumns = 7;
        int count = 21 - 1;
        if (data[0].bym == FACE_TYPE.TYPE_GIF) {
            numColumns = 4;
            count = 4 * 2;
        }
        int pageCount = data.length / count;
        if (data.length % count > 0) {
            pageCount++;
        }
        for (int i = 0; i < pageCount; i++) {
            FaceItem[] pageData;
            if ((i + 1) * count > data.length) {
                int icount = data.length - (i * count);
                pageData = new FaceItem[icount];
                System.arraycopy(data, i * count, pageData, 0, icount);
            } else {
                pageData = new FaceItem[count];
                System.arraycopy(data, i * count, pageData, 0, count);
            }
            FaceGrid faceGrid = new FaceGrid(getContext(), pageData);
            faceGrid.setNumColumns(numColumns);
            faceGrid.setGravity(17);
            faceGrid.setVerticalScrollBarEnabled(false);
            faceGrid.setVerticalFadingEdgeEnabled(false);
            faceGrid.setOnItemClickListener(this);
            this.byK.a(faceGrid);
            this.byI.setCurrentItem(0);
        }
        this.byK.notifyDataSetChanged();
        this.byJ.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
        FaceItem data = (FaceItem) arg1.getTag();
        if (!(this.byL == null || this.byL.getOnItemFaceClick() == null)) {
            this.byL.getOnItemFaceClick().a(data);
        }
        Log.i("click face", data.bym.name());
    }
}
