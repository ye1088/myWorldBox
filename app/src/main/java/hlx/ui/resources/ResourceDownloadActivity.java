package hlx.ui.resources;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.huluxia.framework.R;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.framework.base.widget.pager.SelectedViewPager;
import com.huluxia.k;
import com.huluxia.r;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.d;
import hlx.data.tongji.a;
import hlx.ui.resources.fragment.ResourceAllFragment;
import hlx.ui.resources.fragment.ResourceRankingFragment;
import hlx.ui.resources.fragment.ResourceRecommendFragment;
import hlx.ui.resources.fragment.SubjectrFragment;

public class ResourceDownloadActivity extends HTBaseLoadingActivity {
    public static final String TAG = "ResourceDownloadActivity";
    public static final String cfV = "EXTRA_RES_TYPE";
    private ImageButton aQE;
    private int axr = 1;
    private String[] bco;
    private PagerSlidingTabStrip cfW;
    PagerSelectedAdapter cfX = new PagerSelectedAdapter(this, getSupportFragmentManager()) {
        final /* synthetic */ ResourceDownloadActivity cfY;

        public int getCount() {
            return this.cfY.bco.length;
        }

        public PagerFragment getItem(int position) {
            switch (position) {
                case 0:
                    return ResourceAllFragment.o(this.cfY.axr, this.cfY.VR());
                case 1:
                    return ResourceRecommendFragment.q(this.cfY.axr, this.cfY.VR());
                case 2:
                    return ResourceRankingFragment.p(this.cfY.axr, this.cfY.VR());
                case 3:
                    return SubjectrFragment.r(this.cfY.axr, this.cfY.VR());
                default:
                    return null;
            }
        }

        public CharSequence getPageTitle(int position) {
            return this.cfY.bco[position];
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_download);
        this.axr = getIntent().getIntExtra(cfV, 1);
        VQ();
        initViewPager();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    private void initViewPager() {
        this.bco = getResources().getStringArray(R.array.category);
        SelectedViewPager mViewPager = (SelectedViewPager) findViewById(R.id.map_down_viewpager);
        mViewPager.setAdapter(this.cfX);
        mViewPager.setOffscreenPageLimit(3);
        this.cfW = (PagerSlidingTabStrip) findViewById(R.id.sliding_tab);
        this.cfW.setViewPager(mViewPager);
        this.cfW.setDividerColor(getResources().getColor(R.color.transparent));
        this.cfW.setIndicatorTextColor(true);
        this.cfW.setShouldExpand(false);
        this.cfW.setIndicatorOffset(true);
        this.cfW.setIndicatorHeight(at.dipToPx(this, 2));
        this.cfW.setTabPaddingLeftRight(at.dipToPx(this, 13));
        this.cfW.setTextSize(at.dipToPx(this, 14));
        if (d.RB()) {
            this.cfW.setTextColor(getResources().getColor(R.color.home_common_text_color_night));
            this.cfW.setIndicatorColor(getResources().getColor(R.color.home_titlebar_bg_night));
            this.cfW.setBackgroundColor(getResources().getColor(R.color.home_common_bg_night));
            return;
        }
        this.cfW.setTextColor(getResources().getColor(R.color.js_text_color));
        this.cfW.setIndicatorColor(getResources().getColor(R.color.js_tab));
        this.cfW.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void VQ() {
        switch (this.axr) {
            case 2:
                ej("插件下载");
                break;
            case 3:
                ej("皮肤下载");
                break;
            case 4:
                ej("材质下载");
                break;
            default:
                ej("地图下载");
                break;
        }
        this.aQE = (ImageButton) findViewById(R.id.sys_header_flright_img);
        this.aQE.setVisibility(0);
        this.aIs.setVisibility(0);
        this.aQE.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResourceDownloadActivity cfY;

            {
                this.cfY = this$0;
            }

            public void onClick(View arg0) {
                switch (this.cfY.axr) {
                    case 2:
                        k.R(this.cfY);
                        r.ck().K(a.bMo);
                        return;
                    case 3:
                        k.T(this.cfY);
                        r.ck().K(a.bMq);
                        return;
                    case 4:
                        k.S(this.cfY);
                        r.ck().K(a.bMp);
                        return;
                    default:
                        k.Q(this.cfY);
                        r.ck().K(a.bMn);
                        return;
                }
            }
        });
    }

    public String VR() {
        switch (this.axr) {
            case 1:
                return "map";
            case 2:
                return "js";
            case 3:
                return "skin";
            case 4:
                return "wood";
            default:
                return "default";
        }
    }
}
