package com.MCWorld.ui.mctool;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.MCWorld.framework.R;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.utils.at;
import com.MCWorld.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.d;
import hlx.data.tongji.a;
import hlx.ui.resources.fragment.ServerAllFragment;
import hlx.ui.resources.fragment.ServerRecommendFragment;

public class ServerListExActivity extends HTBaseLoadingActivity {
    private String[] bco;
    FragmentPagerAdapter bcp = new FragmentPagerAdapter(this, getSupportFragmentManager()) {
        final /* synthetic */ ServerListExActivity bcq;

        public int getCount() {
            return this.bcq.bco.length;
        }

        public Fragment getItem(int position) {
            if (position == 0) {
                return ServerAllFragment.VV();
            }
            if (position == 1) {
                return ServerRecommendFragment.VY();
            }
            return null;
        }

        public CharSequence getPageTitle(int position) {
            return this.bcq.bco[position];
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("联机资源");
        setContentView((int) R.layout.activity_server_resource);
        ImageButton btn = (ImageButton) findViewById(R.id.sys_header_flright_img);
        btn.setImageDrawable(d.isDayMode() ? getResources().getDrawable(R.drawable.btn_open_server_selector) : getResources().getDrawable(R.drawable.btn_open_server_night_selector));
        btn.setVisibility(0);
        btn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ServerListExActivity bcq;

            {
                this.bcq = this$0;
            }

            public void onClick(View arg0) {
                r.ck().K_umengEvent(a.bMR);
                t.a(this.bcq, 5243116, false);
            }
        });
        initViewPager();
    }

    private void initViewPager() {
        this.bco = getResources().getStringArray(R.array.server_category);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.server_resource_viewpager);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.server_sliding_tab);
        tabStrip.setDividerColor(getResources().getColor(R.color.transparent));
        tabStrip.setTextSize(at.dipToPx(this, 15));
        tabStrip.setIndicatorTextColor(true);
        tabStrip.setIndicatorOffset(true);
        tabStrip.setShouldExpand(false);
        tabStrip.setIndicatorHeight(at.dipToPx(this, 2));
        tabStrip.setTabPaddingLeftRight(at.dipToPx(this, 13));
        tabStrip.setTextSize(at.dipToPx(this, 14));
        mViewPager.setAdapter(this.bcp);
        tabStrip.setViewPager(mViewPager);
        if (d.RB()) {
            tabStrip.setTextColor(getResources().getColor(R.color.home_common_text_color_night));
            tabStrip.setIndicatorColor(getResources().getColor(R.color.home_titlebar_bg_night));
            tabStrip.setBackgroundColor(getResources().getColor(R.color.home_common_bg_night));
            return;
        }
        tabStrip.setTextColor(getResources().getColor(R.color.js_text_color));
        tabStrip.setIndicatorColor(getResources().getColor(R.color.js_tab));
        tabStrip.setBackgroundColor(getResources().getColor(R.color.white));
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
