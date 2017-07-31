package com.huluxia.ui.mctool;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.huluxia.framework.R;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.framework.base.widget.pager.SelectedViewPager;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.a.a;
import com.simple.colorful.d;

public class ProfileResListActivity extends HTBaseActivity {
    protected SelectedViewPager aED;
    protected PagerSelectedAdapter aEN;
    protected PagerSlidingTabStrip baN;
    private ProfileResListActivity bbF;

    private static class ResourceGameAdapter extends PagerSelectedAdapter {
        private static final int[] bbG = new int[]{R.string.map, R.string.js, R.string.wood, R.string.skin};
        private Context context;

        public ResourceGameAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        public int getCount() {
            return bbG.length;
        }

        public CharSequence getPageTitle(int position) {
            return this.context.getString(bbG[position]);
        }

        public PagerFragment getItem(int i) {
            ProfileResFragment profileResFragment;
            switch (i) {
                case 0:
                    profileResFragment = new ProfileResFragment();
                    return ProfileResFragment.kR(1);
                case 1:
                    profileResFragment = new ProfileResFragment();
                    return ProfileResFragment.kR(2);
                case 2:
                    profileResFragment = new ProfileResFragment();
                    return ProfileResFragment.kR(4);
                case 3:
                    profileResFragment = new ProfileResFragment();
                    return ProfileResFragment.kR(3);
                default:
                    return null;
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_profile_res_list);
        this.bbF = this;
        EP();
        Iu();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void EP() {
        ej(getResources().getString(R.string.local_manage));
    }

    private void Iu() {
        this.aED = (SelectedViewPager) findViewById(R.id.view_pager);
        this.aEN = Iv();
        if (this.aEN == null) {
            throw new IllegalStateException("没有初始化Adapter");
        }
        this.aED.setAdapter(this.aEN);
        this.aED.setOffscreenPageLimit(4);
        this.baN = (PagerSlidingTabStrip) findViewById(R.id.sliding_tab);
        this.baN.setTextColorResource(d.p(this, 16842808));
        this.baN.setIndicatorColorResource(d.p(this, R.attr.textColorGreen));
        this.baN.setTextSize(at.dipToPx(this, 15));
        this.baN.setIndicatorTextColor(true);
        this.baN.setBackgroundResource(R.color.transparent);
        this.baN.setDividerColor(getResources().getColor(R.color.transparent));
        this.baN.setShouldExpand(false);
        this.baN.setIndicatorHeight(at.dipToPx(this.bbF, 4));
        this.baN.setViewPager(this.aED);
        this.baN.setUnderlineColorResource(d.p(this, R.attr.textColorGreen));
    }

    protected PagerSelectedAdapter Iv() {
        return new ResourceGameAdapter(this, getSupportFragmentManager());
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, R.attr.backgroundDefault);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.baN != null) {
            this.baN.FG();
        }
    }
}
