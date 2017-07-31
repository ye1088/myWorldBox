package com.huluxia.ui.profile;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.j;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.ui.area.news.NewsFavorFragment;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.a.a;

public class ProfileFavorActivity extends HTBaseActivity {
    public static final String bgC = "user_id";
    private PagerSlidingTabStrip bfr;
    private ViewPager bgB;
    private boolean bgD;
    private long userid = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_resource_category);
        this.userid = getIntent().getLongExtra(FriendListActivity.EXTRA_USER_ID, 0);
        if (savedInstanceState == null) {
            this.userid = getIntent().getLongExtra(FriendListActivity.EXTRA_USER_ID, 0);
        } else {
            this.userid = savedInstanceState.getLong("user_id", 0);
        }
        if (this.userid > 0) {
            this.bgD = this.userid != j.ep().getUserid();
            if (this.bgD) {
                ej(getResources().getString(m.his_favorite));
            } else {
                ej(getResources().getString(m.my_favorite));
            }
            this.bgB = (ViewPager) findViewById(g.view_pager);
            this.bgB.setAdapter(new PagerSelectedAdapter(this, getSupportFragmentManager()) {
                final /* synthetic */ ProfileFavorActivity bgE;

                public PagerFragment getItem(int position) {
                    switch (position) {
                        case 0:
                            return TopicFavorFragment.bq(this.bgE.userid);
                        case 1:
                            return NewsFavorFragment.bj(this.bgE.userid);
                        default:
                            return null;
                    }
                }

                public int getCount() {
                    return 2;
                }

                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return "话题";
                        case 1:
                            return "资讯";
                        default:
                            return super.getPageTitle(position);
                    }
                }
            });
            this.bfr = (PagerSlidingTabStrip) findViewById(g.sliding_tab);
            this.bfr.setTextSize(at.dipToPx(this, 15));
            this.bfr.setIndicatorTextColor(true);
            this.bfr.setDividerColor(getResources().getColor(d.transparent));
            this.bfr.setShouldExpand(true);
            this.bfr.setTextColorResource(com.simple.colorful.d.p(this, 16842808));
            this.bfr.setIndicatorColorResource(com.simple.colorful.d.p(this, c.textColorGreen));
            this.bfr.setViewPager(this.bgB);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("user_id", this.userid);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public long getUserid() {
        return this.userid;
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
