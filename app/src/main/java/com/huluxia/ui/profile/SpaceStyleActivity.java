package com.huluxia.ui.profile;

import android.os.Bundle;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.framework.base.widget.pager.SelectedViewPager;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.a.a;

public class SpaceStyleActivity extends HTBaseActivity {
    public static final String bhA = "EXTRA_PAGE_INDEX";
    public static final String bhB = "EXTRA_PROFILE_INFO";
    public static final String bhC = "EXTRA_FROM_HOME";
    private ProfileInfo aKG;
    private boolean aLf;
    private PagerSlidingTabStrip bfr;
    private SelectedViewPager bhD;
    private int bhE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.include_viewpager_with_tabstrip);
        this.bhE = getIntent().getIntExtra(bhA, 0);
        this.aKG = (ProfileInfo) getIntent().getParcelableExtra(bhB);
        this.aLf = getIntent().getBooleanExtra("EXTRA_FROM_HOME", true);
        JH();
    }

    private void JH() {
        this.bhD = (SelectedViewPager) findViewById(g.pager);
        this.bhD.setOffscreenPageLimit(1);
        this.bhD.setAdapter(new PagerSelectedAdapter(this, getSupportFragmentManager()) {
            final /* synthetic */ SpaceStyleActivity bhF;

            public PagerFragment getItem(int position) {
                if (position == 0) {
                    return SpaceRecommendStyleFragment.a(this.bhF.aKG, this.bhF.aLf);
                }
                return new SpaceCustomStyleFragment();
            }

            public int getCount() {
                return 2;
            }

            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return this.bhF.getResources().getString(m.recommend);
                }
                return this.bhF.getResources().getString(m.custom);
            }
        });
        this.bfr = (PagerSlidingTabStrip) findViewById(g.sliding_tab);
        this.bfr.setTextSize(at.dipToPx(this, 15));
        this.bfr.setIndicatorTextColor(true);
        this.bfr.setDividerColor(getResources().getColor(d.transparent));
        this.bfr.setShouldExpand(true);
        this.bfr.setViewPager(this.bhD);
        this.bfr.setTextColorResource(com.simple.colorful.d.p(this, 16842808));
        this.bfr.setIndicatorColorResource(com.simple.colorful.d.p(this, c.textColorGreen));
        if (this.bhE > 0) {
            this.bhD.setCurrentItem(1);
        }
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.bfr != null) {
            this.bfr.FG();
        }
    }
}
