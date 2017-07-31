package hlx.ui.localresmgr;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.huluxia.framework.R;
import com.huluxia.framework.base.widget.PagerSlidingIndicator.IndicatorTextSizeChange;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.framework.base.widget.pager.SelectedViewPager;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.a.a;
import com.simple.colorful.d;
import hlx.ui.localresmgr.fragment.LocResJSFragment;
import hlx.ui.localresmgr.fragment.LocResMapFragment;
import hlx.ui.localresmgr.fragment.LocResSkinFragment;
import hlx.ui.localresmgr.fragment.LocResWoodFragment;

public class LocResListActivity extends HTBaseActivity {
    protected SelectedViewPager aED;
    protected PagerSelectedAdapter aEN;
    private LocResListActivity bYQ;
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
                    return LocResJSFragment.Ux();
                case 2:
                    return LocResWoodFragment.UQ();
                case 3:
                    return LocResSkinFragment.UM();
                default:
                    return LocResMapFragment.UI();
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
        this.bYQ = this;
        EP();
        Iu();
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

    private void EP() {
        ej(getResources().getString(R.string.local_res));
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
        this.baN.setIndicatorHeight(at.dipToPx(this.bYQ, 1));
        this.baN.setDividerColorResource(R.color.transparent);
        this.baN.setUnderlineHeight(0);
        this.baN.setUnderlineColor(getResources().getColor(R.color.background_normal));
        this.baN.setIndicatorTextSize(new IndicatorTextSizeChange(this) {
            final /* synthetic */ LocResListActivity bYR;

            {
                this.bYR = this$0;
            }

            public int getTextSizePx() {
                return at.sp2px(this.bYR.bYQ, 16.0f);
            }
        });
        this.baN.setViewPager(this.aED);
    }

    protected PagerSelectedAdapter Iv() {
        return new LocResListAdapter(getSupportFragmentManager());
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
