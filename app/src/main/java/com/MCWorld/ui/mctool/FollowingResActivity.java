package com.MCWorld.ui.mctool;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.widget.PagerSlidingIndicator.IndicatorTextSizeChange;
import com.MCWorld.framework.base.widget.pager.PagerFragment;
import com.MCWorld.framework.base.widget.pager.PagerSelectedAdapter;
import com.MCWorld.framework.base.widget.pager.SelectedViewPager;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.utils.at;
import com.MCWorld.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.a.a;
import com.simple.colorful.d;

public class FollowingResActivity extends HTBaseActivity {
    protected SelectedViewPager aED;
    protected PagerSelectedAdapter aEN;
    private FollowingResActivity baM;
    protected PagerSlidingTabStrip baN;

    private static class LocResListAdapter extends PagerSelectedAdapter {
        private static final String[] baP = new String[]{"地图", "JS", "材质", "皮肤"};
        private static final int baQ = 1;
        private static final int baR = 2;
        private static final int baS = 3;

        public LocResListAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public PagerFragment getItem(int position) {
            switch (position) {
                case 1:
                    return FollowingResFragment.kQ(2);
                case 2:
                    return FollowingResFragment.kQ(4);
                case 3:
                    return FollowingResFragment.kQ(3);
                default:
                    return FollowingResFragment.kQ(1);
            }
        }

        public CharSequence getPageTitle(int position) {
            return baP[position];
        }

        public int getCount() {
            return baP.length;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_loc_res_list);
        this.baM = this;
        EP();
        Iu();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void EP() {
        ej(this.baM.getResources().getString(R.string.following_res));
    }

    private void Iu() {
        this.aED = (SelectedViewPager) findViewById(R.id.locres_view_pager);
        this.aEN = Iv();
        if (this.aEN == null) {
            throw new IllegalStateException("没有初始化Adapter");
        }
        this.aED.setAdapter(this.aEN);
        this.aED.setOffscreenPageLimit(3);
        this.baN = (PagerSlidingTabStrip) findViewById(R.id.locres_sliding_tab);
        this.baN.setTextColorResource(d.p(this, 16842808));
        this.baN.setIndicatorColorResource(d.p(this, R.attr.textColorGreen));
        this.baN.setTextSize(at.dipToPx(this, 14));
        this.baN.setIndicatorTextColor(true);
        this.baN.setBackgroundResource(R.color.transparent);
        this.baN.setDividerColor(getResources().getColor(R.color.transparent));
        this.baN.setShouldExpand(false);
        this.baN.setIndicatorHeight(at.dipToPx(this.baM, 1));
        this.baN.setDividerColorResource(R.color.transparent);
        this.baN.setUnderlineHeight(0);
        this.baN.setUnderlineColor(getResources().getColor(R.color.background_normal));
        this.baN.setIndicatorTextSize(new IndicatorTextSizeChange(this) {
            final /* synthetic */ FollowingResActivity baO;

            {
                this.baO = this$0;
            }

            public int getTextSizePx() {
                return at.sp2px(this.baO.baM, 16.0f);
            }
        });
        this.baN.setViewPager(this.aED);
    }

    protected PagerSelectedAdapter Iv() {
        return new LocResListAdapter(getSupportFragmentManager());
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, R.attr.backgroundDefault).bc(R.id.sys_header_flright_img, R.attr.drawableTitleSearch);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.baN != null) {
            this.baN.FG();
        }
    }
}
