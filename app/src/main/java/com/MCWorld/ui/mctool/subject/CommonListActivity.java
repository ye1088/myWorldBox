package com.MCWorld.ui.mctool.subject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.map.MapCateItem;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.widget.pager.PagerFragment;
import com.MCWorld.framework.base.widget.pager.PagerSelectedAdapter;
import com.MCWorld.framework.base.widget.pager.SelectedViewPager;
import com.MCWorld.k;
import com.MCWorld.r;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.mctool.ResourceCommonListLayout.a;
import com.MCWorld.utils.at;
import com.MCWorld.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommonListActivity extends HTBaseLoadingActivity {
    protected SelectedViewPager aED;
    protected PagerSlidingTabStrip baN;
    protected String bcC = "";
    protected ViewPagerAdapter bcD;

    public class ViewPagerAdapter extends PagerSelectedAdapter {
        final /* synthetic */ CommonListActivity bcE;
        protected Map<Integer, TabListResourceFragment> bcF = new HashMap();
        protected ArrayList<MapCateItem> bcG = new ArrayList();

        public ViewPagerAdapter(CommonListActivity this$0, FragmentManager fm) {
            this.bcE = this$0;
            super(fm);
        }

        public void p(ArrayList<MapCateItem> da) {
            this.bcG = da;
        }

        public PagerFragment getItem(int position) {
            int id = (int) ((MapCateItem) this.bcG.get(position)).id;
            if (this.bcF.get(Integer.valueOf(id)) == null) {
                this.bcF.put(Integer.valueOf(id), this.bcE.ay(id, position));
            }
            return (PagerFragment) this.bcF.get(Integer.valueOf(id));
        }

        public int getCount() {
            return this.bcG.size();
        }

        public CharSequence getPageTitle(int position) {
            return ((MapCateItem) this.bcG.get(position % (this.bcG.size() > 0 ? this.bcG.size() : 1))).title;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.include_resource);
        Iu();
        this.aIs.setVisibility(8);
        findViewById(R.id.title_bar).setVisibility(8);
        this.aIQ.setImageResource(d.r(this, R.attr.drawableTitleSearch));
        this.aIQ.setVisibility(0);
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CommonListActivity bcE;

            {
                this.bcE = this$0;
            }

            public void onClick(View arg0) {
                k.Q(this.bcE);
                r.ck().cZ();
            }
        });
    }

    private void Iu() {
        this.bcD = new ViewPagerAdapter(this, getSupportFragmentManager());
        this.aED = (SelectedViewPager) findViewById(R.id.vp_content);
        this.aED.setAdapter(this.bcD);
        this.aED.setOffscreenPageLimit(2);
        this.baN = (PagerSlidingTabStrip) findViewById(R.id.vp_tabs);
        this.baN.setTextColor(-5592406);
        this.baN.setTextSize(at.dipToPx(this, 17));
        this.baN.setTabPaddingLeftRight(20);
        this.baN.setIndicatorColor(getResources().getColor(R.color.mctool_orange));
        this.baN.setIndicatorTextColor(true);
        this.baN.setIndicatorHeight(at.dipToPx(this, 4));
        this.baN.setDividerColor(getResources().getColor(R.color.background_normal));
        this.baN.setShouldExpand(true);
        this.baN.setViewPager(this.aED);
        this.baN.setUnderlineColor(getResources().getColor(R.color.background_normal));
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public TabListResourceFragment ay(int id, int pos) {
        return null;
    }

    public DownAdapter az(int tagId, int pos) {
        return null;
    }

    public a kV(int tagId) {
        return null;
    }

    protected void o(ArrayList<MapCateItem> da) {
        if (da.size() >= 6) {
            this.baN.setShouldExpand(false);
        }
        this.bcD.p(da);
        this.baN.notifyDataSetChanged();
        this.bcD.notifyDataSetChanged();
    }
}
