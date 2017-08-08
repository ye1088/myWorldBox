package com.MCWorld.studio;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.widget.pager.PagerFragment;
import com.MCWorld.framework.base.widget.pager.PagerSelectedAdapter;
import com.MCWorld.framework.base.widget.pager.SelectedViewPager;
import com.MCWorld.studio.fragment.AllStudioFragment;
import com.MCWorld.studio.fragment.DownloadRankFragment;
import com.MCWorld.studio.fragment.MonthRankFragment;
import com.MCWorld.studio.fragment.RecommendFragment;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.simple.colorful.a.a;

public class StudioActivity extends HTBaseThemeActivity {
    private static final String TAG = "StudioActivity";
    public static final String aEE = "PAST_MONTH";
    public static final String aEF = "CURRENT_MONTH";
    public static final String aEG = "IS_PAST";
    protected View aEA;
    protected CheckedTextView aEB;
    protected CheckedTextView aEC;
    protected SelectedViewPager aED;
    protected boolean aEH = false;
    protected String aEI = "";
    protected String aEJ = "";
    protected OnClickListener aEK = new OnClickListener(this) {
        final /* synthetic */ StudioActivity aEP;

        {
            this.aEP = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.rly_recommend_tab) {
                this.aEP.aED.setCurrentItem(0, false);
            } else if (id == R.id.rly_month_review_top_tab) {
                this.aEP.aED.setCurrentItem(1, false);
            } else if (id == R.id.rly_download_top_tab) {
                this.aEP.aED.setCurrentItem(2, false);
            } else if (id == R.id.rly_all_studio_tab) {
                this.aEP.aED.setCurrentItem(3, false);
            }
        }
    };
    protected OnClickListener aEL = new OnClickListener(this) {
        final /* synthetic */ StudioActivity aEP;

        {
            this.aEP = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.rly_month_review_past_tab) {
                this.aEP.aED.setCurrentItem(0, false);
            } else if (id == R.id.rly_download_past_tab) {
                this.aEP.aED.setCurrentItem(1, false);
            }
        }
    };
    protected OnPageChangeListener aEM = new OnPageChangeListener(this) {
        final /* synthetic */ StudioActivity aEP;

        {
            this.aEP = this$0;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            if (this.aEP.aEH) {
                switch (position) {
                    case 1:
                        this.aEP.kl(R.id.rly_download_past_tab);
                        return;
                    default:
                        this.aEP.kl(R.id.rly_month_review_past_tab);
                        return;
                }
            }
            switch (position) {
                case 1:
                    this.aEP.kk(R.id.rly_month_review_top_tab);
                    return;
                case 2:
                    this.aEP.kk(R.id.rly_download_top_tab);
                    return;
                case 3:
                    this.aEP.kk(R.id.rly_all_studio_tab);
                    return;
                default:
                    this.aEP.kk(R.id.rly_recommend_tab);
                    return;
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    };
    protected PagerSelectedAdapter aEN = new PagerSelectedAdapter(this, getSupportFragmentManager()) {
        final /* synthetic */ StudioActivity aEP;

        public PagerFragment getItem(int position) {
            switch (position) {
                case 1:
                    return MonthRankFragment.b(this.aEP.aEH, this.aEP.aEJ, this.aEP.aEI);
                case 2:
                    return DownloadRankFragment.a(this.aEP.aEH, this.aEP.aEJ, this.aEP.aEI);
                case 3:
                    return new AllStudioFragment();
                default:
                    return new RecommendFragment();
            }
        }

        public int getCount() {
            return 4;
        }
    };
    protected PagerSelectedAdapter aEO = new PagerSelectedAdapter(this, getSupportFragmentManager()) {
        final /* synthetic */ StudioActivity aEP;

        public PagerFragment getItem(int position) {
            switch (position) {
                case 1:
                    return DownloadRankFragment.a(this.aEP.aEH, this.aEP.aEJ, this.aEP.aEI);
                default:
                    return MonthRankFragment.b(this.aEP.aEH, this.aEP.aEJ, this.aEP.aEI);
            }
        }

        public int getCount() {
            return 2;
        }
    };
    private StudioActivity aEs;
    protected View aEt;
    protected CheckedTextView aEu;
    protected CheckedTextView aEv;
    protected CheckedTextView aEw;
    protected CheckedTextView aEx;
    protected final int aEy = 4;
    protected final int aEz = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studio);
        if (savedInstanceState != null) {
            this.aEH = savedInstanceState.getBoolean(aEG);
            this.aEI = savedInstanceState.getString(aEE);
            this.aEJ = savedInstanceState.getString(aEF);
        } else {
            this.aEH = getIntent().getBooleanExtra(aEG, false);
            this.aEI = getIntent().getStringExtra(aEE);
            this.aEJ = getIntent().getStringExtra(aEF);
        }
        initView();
        EQ();
    }

    private void initView() {
        this.aED = (SelectedViewPager) findViewById(R.id.vp_studio_content);
        this.aEt = findViewById(R.id.ll_bottom_tabs);
        this.aEu = (CheckedTextView) findViewById(R.id.tv_recommend_tab);
        this.aEv = (CheckedTextView) findViewById(R.id.tv_month_review_top_tab);
        this.aEw = (CheckedTextView) findViewById(R.id.tv_download_top_tab);
        this.aEx = (CheckedTextView) findViewById(R.id.tv_all_studio_tab);
        this.aEA = findViewById(R.id.ll_bottom_past_tabs);
        this.aEB = (CheckedTextView) findViewById(R.id.tv_month_review_past_tab);
        this.aEC = (CheckedTextView) findViewById(R.id.tv_download_past_tab);
    }

    private void EQ() {
        if (this.aEH) {
            kl(R.id.rly_month_review_past_tab);
            findViewById(R.id.rly_month_review_past_tab).setOnClickListener(this.aEL);
            findViewById(R.id.rly_download_past_tab).setOnClickListener(this.aEL);
            this.aEt.setVisibility(8);
            this.aEA.setVisibility(0);
            this.aED.setAdapter(this.aEO);
        } else {
            kk(R.id.rly_recommend_tab);
            findViewById(R.id.rly_recommend_tab).setOnClickListener(this.aEK);
            findViewById(R.id.rly_month_review_top_tab).setOnClickListener(this.aEK);
            findViewById(R.id.rly_download_top_tab).setOnClickListener(this.aEK);
            findViewById(R.id.rly_all_studio_tab).setOnClickListener(this.aEK);
            this.aEt.setVisibility(0);
            this.aEA.setVisibility(8);
            this.aED.setAdapter(this.aEN);
            this.aED.setOffscreenPageLimit(3);
        }
        this.aED.setOnPageChangeListener(this.aEM);
    }

    protected void kk(int id) {
        if (id == R.id.rly_recommend_tab) {
            this.aEu.setChecked(true);
            this.aEv.setChecked(false);
            this.aEw.setChecked(false);
            this.aEx.setChecked(false);
        } else if (id == R.id.rly_month_review_top_tab) {
            this.aEu.setChecked(false);
            this.aEv.setChecked(true);
            this.aEw.setChecked(false);
            this.aEx.setChecked(false);
        } else if (id == R.id.rly_download_top_tab) {
            this.aEu.setChecked(false);
            this.aEv.setChecked(false);
            this.aEw.setChecked(true);
            this.aEx.setChecked(false);
        } else if (id == R.id.rly_all_studio_tab) {
            this.aEu.setChecked(false);
            this.aEv.setChecked(false);
            this.aEw.setChecked(false);
            this.aEx.setChecked(true);
        }
    }

    protected void kl(int id) {
        if (id == R.id.rly_month_review_past_tab) {
            this.aEB.setChecked(true);
            this.aEC.setChecked(false);
        } else if (id == R.id.rly_download_past_tab) {
            this.aEB.setChecked(false);
            this.aEC.setChecked(true);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(aEG, this.aEH);
        outState.putString(aEE, this.aEI);
        outState.putString(aEF, this.aEJ);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void a(a builder) {
        builder.aY(R.id.rly_bottom_tabs, R.attr.backgroundDim).aY(R.id.view_studio_split, R.attr.home_split_line_bg).ba(R.id.tv_recommend_tab, R.attr.tab_text_color).ab(R.id.tv_recommend_tab, R.attr.studio_recommend, 0).ba(R.id.tv_month_review_top_tab, R.attr.tab_text_color).ab(R.id.tv_month_review_top_tab, R.attr.month_review_rank, 0).ba(R.id.tv_download_top_tab, R.attr.tab_text_color).ab(R.id.tv_download_top_tab, R.attr.download_rank, 0).ba(R.id.tv_all_studio_tab, R.attr.tab_text_color).ab(R.id.tv_all_studio_tab, R.attr.all_studio, 0).ba(R.id.tv_month_review_past_tab, R.attr.tab_text_color).ab(R.id.tv_month_review_past_tab, R.attr.month_review_rank, 0).ba(R.id.tv_download_past_tab, R.attr.tab_text_color).ab(R.id.tv_download_past_tab, R.attr.download_rank, 0);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
