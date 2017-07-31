package hlx.home.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.data.cdn.a;
import com.huluxia.data.map.MapResCountInfo;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.data.server.ServerResCountInfo;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.widget.pager.SelectedViewPager;
import com.huluxia.module.k;
import com.huluxia.module.n;
import com.huluxia.module.v;
import com.huluxia.service.i;
import com.huluxia.ui.base.BaseLoadingLayout;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.utils.ah;
import com.huluxia.widget.Constants;
import com.huluxia.widget.PullToRefreshScrollableLayout;
import com.huluxia.widget.banner.BannerGallery;
import com.huluxia.widget.menudrawer.MenuDrawer;
import com.huluxia.widget.title.TitleBar;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.d;
import hlx.mcstorymode.c;
import java.util.ArrayList;
import java.util.List;
import ru.noties.scrollable.ScrollableLayout;

public class HomeFragment extends BaseThemeFragment {
    private static final boolean DEBUG = false;
    private static final String TAG = "HomeFragment";
    private SelectedViewPager aED;
    private MenuDrawer aPB;
    private TextView bPA;
    private TextView bPB;
    private TextView bPC;
    private TextView bPD;
    private TextView bPE;
    private ImageView bPF;
    private ImageView bPG;
    private ImageView bPH;
    private ImageView bPI;
    private ImageView bPJ;
    private ImageView bPK;
    private ImageView bPL;
    private ImageView bPM;
    private TextView bPN;
    private TextView bPO;
    private TextView bPP;
    private TextView bPQ;
    private TextView bPR;
    private TextView bPS;
    private TextView bPT;
    private TextView bPU;
    private BannerGallery bPV = null;
    private LayoutParams bPW;
    private TitleBar bPX;
    private ImageButton bPY;
    private MsgtipReciver bPZ;
    private ScrollablePageAdapter bPt;
    private ScrollableLayout bPu;
    private PullToRefreshScrollableLayout bPv;
    private View bPw;
    private ViewGroup.LayoutParams bPx;
    private TextView bPy;
    private TextView bPz;
    private ClearMsgReceiver bQa;
    private PagerSlidingTabStrip bQb;
    private Resources bQc;
    View bQd;
    View bQe;
    View bQf;
    View bQg;
    View bQh;
    private ImageView bQi;
    private TextView bQj;
    private BaseLoadingLayout bQk;
    private ImageButton bbO;
    private TextView bbP;
    private TextView eN;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ HomeFragment bQl;

        {
            this.bQl = this$0;
        }

        @MessageHandler(message = 3344)
        public void onRecvCDNHostInfo(a info) {
            if (info != null) {
                HTApplication.fn = info.getCdnHost();
            } else {
                HTApplication.fn = hlx.data.localstore.a.bLF;
            }
            Constants.NR();
            hlx.data.localstore.a.NR();
            c.NR();
        }

        @MessageHandler(message = 529)
        public void onRecvResHomeBannerInfo(com.huluxia.data.banner.a info) {
            this.bQl.bPv.onRefreshComplete();
            if (info != null) {
                this.bQl.J(info.mcCarousellist);
                this.bQl.bQb.setVisibility(0);
                this.bQl.bPv.setPullToRefreshEnabled(true);
                this.bQl.bQk.setVisibility(8);
            } else if (this.bQl.bPW == null) {
                this.bQl.bPV.setVisibility(8);
                this.bQl.bPu.setMaxScrollY((int) this.bQl.mContext.getResources().getDimension(R.dimen.home_no_banner_header_height));
                this.bQl.bPx.height = (int) this.bQl.mContext.getResources().getDimension(R.dimen.home_no_banner_header_height);
            }
        }

        @MessageHandler(message = 273)
        public void onRecvServerCountInfo(ServerResCountInfo info) {
            this.bQl.bPv.onRefreshComplete();
            if (info != null) {
                HTApplication.fJ = info.getServerCount();
                this.bQl.RO();
            }
        }

        @MessageHandler(message = 518)
        public void onRecvResCountInfo(MapResCountInfo info) {
            this.bQl.bPv.onRefreshComplete();
            if (info != null) {
                int mapCount = info.getMapCount();
                int jsCount = info.getJsCount();
                int skinCount = info.getSkinCount();
                int woodCount = info.getWoodCount();
                int seedCount = info.getSeedCount();
                int adCount = info.getAdCount();
                HTApplication.fF = mapCount;
                HTApplication.fG = jsCount;
                HTApplication.fI = skinCount;
                HTApplication.fH = woodCount;
                HTApplication.fK = seedCount;
                HTApplication.fL = adCount;
                this.bQl.RO();
            }
        }

        @MessageHandler(message = 531)
        public void recommendDataSuccess(boolean success) {
            this.bQl.bPv.onRefreshComplete();
            if (success) {
                this.bQl.bPv.setPullToRefreshEnabled(true);
                this.bQl.bQk.setVisibility(8);
                if (8 == this.bQl.bQb.getVisibility() || 4 == this.bQl.bQb.getVisibility()) {
                    this.bQl.bQb.setVisibility(0);
                }
            } else if (this.bQl.bQb.getVisibility() != 0 && this.bQl.bPW == null) {
                this.bQl.bPu.setMaxScrollY(0);
                this.bQl.bQk.FB();
            }
        }

        @MessageHandler(message = 3586)
        public void recvMenuMsgTip(int count) {
            if (count > 0) {
                this.bQl.bQj.setVisibility(0);
            } else {
                this.bQl.bQj.setVisibility(8);
            }
        }
    };
    private OnClickListener mClickListener = new 7(this);
    private Activity mContext;

    public static HomeFragment RM() {
        return new HomeFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        this.bPZ = new MsgtipReciver(this);
        this.bQa = new ClearMsgReceiver(this);
        i.e(this.bPZ);
        i.f(this.bQa);
        EventNotifyCenter.add(n.class, this.mCallback);
        this.bQc = this.mContext.getResources();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.bPv = (PullToRefreshScrollableLayout) view.findViewById(R.id.scroll_layout);
        this.bPu = (ScrollableLayout) this.bPv.getRefreshableView();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_home_scrollable_header, this.bPu, true);
        this.aED = (SelectedViewPager) view.findViewById(R.id.home_view_pager);
        d(view);
        K(view);
        L(view);
        EQ();
        EZ();
        return view;
    }

    private void d(View view) {
        this.bPV = (BannerGallery) view.findViewById(R.id.iv_mainpage_banner);
        this.bQb = (PagerSlidingTabStrip) view.findViewById(R.id.pager_sliding_tab_home);
        this.bQb.setDividerColorResource(R.color.transparent);
        this.bQb.setIndicatorHeight(UtilsScreen.sp2px(this.mContext, 2.0f));
        this.bQb.setUnderlineHeight(0);
        this.bQb.setTextSize(UtilsScreen.sp2px(this.mContext, 14.0f));
        this.bQb.setIndicatorTextColor(true);
        this.bQb.setIndicatorOffset(true);
        this.bQb.setIndicatorTextSize(new 1(this));
        this.bPy = (TextView) view.findViewById(R.id.tvhomeMapCount);
        this.bPz = (TextView) view.findViewById(R.id.tvhomeJsCount);
        this.bPB = (TextView) view.findViewById(R.id.tvhomeWoodCount);
        this.bPA = (TextView) view.findViewById(R.id.tvhomeSkinCount);
        this.bPC = (TextView) view.findViewById(R.id.tvhomeServerCount);
        this.bPD = (TextView) view.findViewById(R.id.tvhomeSeedCount);
        this.bPE = (TextView) view.findViewById(R.id.tvhomeRecommendCount);
        this.bPF = (ImageView) view.findViewById(R.id.homeGameOptionImg);
        this.bPG = (ImageView) view.findViewById(R.id.homeMapImg);
        this.bPH = (ImageView) view.findViewById(R.id.homeJsImg);
        this.bPI = (ImageView) view.findViewById(R.id.homeWoodImg);
        this.bPJ = (ImageView) view.findViewById(R.id.homeSkinImg);
        this.bPK = (ImageView) view.findViewById(R.id.homeServerImg);
        this.bPL = (ImageView) view.findViewById(R.id.homeMapSeedImg);
        this.bPM = (ImageView) view.findViewById(R.id.homeRecommendImg);
        this.bPN = (TextView) view.findViewById(R.id.tv_home_game_option);
        this.bPO = (TextView) view.findViewById(R.id.tv_home_map);
        this.bPP = (TextView) view.findViewById(R.id.tv_home_js);
        this.bPQ = (TextView) view.findViewById(R.id.tv_home_wood);
        this.bPR = (TextView) view.findViewById(R.id.tv_home_skin);
        this.bPS = (TextView) view.findViewById(R.id.tv_home_server);
        this.bPT = (TextView) view.findViewById(R.id.tv_home_seed);
        this.bPU = (TextView) view.findViewById(R.id.tv_home_app_recommend);
        this.bPu.setDraggableView(this.bQb);
        this.bPw = view.findViewById(R.id.scroll_header_container);
        this.bQd = view.findViewById(R.id.home_split_line_mode_bg_1);
        this.bQe = view.findViewById(R.id.home_split_line_mode_bg_2);
        this.bQf = view.findViewById(R.id.home_split_line_mode_bg_3);
        this.bQg = view.findViewById(R.id.split_top);
        this.bQh = view.findViewById(R.id.home_interval_bg);
        this.bQk = (BaseLoadingLayout) view.findViewById(R.id.base_load_data_error_layout);
    }

    private void K(View view) {
        this.bPX = (TitleBar) view.findViewById(R.id.title_bar);
        this.bPX.setLeftLayout(R.layout.home_left_btn);
        this.bPX.setRightLayout(R.layout.home_right_btn);
        this.bPX.setCenterLayout(R.layout.home_center_btn);
        this.bPX.findViewById(R.id.header_title).setVisibility(8);
        this.bPX.findViewById(R.id.rly_header_left_img).setVisibility(0);
        this.eN = (TextView) this.bPX.findViewById(R.id.tv_header_title);
        this.bPY = (ImageButton) this.bPX.findViewById(R.id.sys_header_flright_img);
        this.bPY.setVisibility(0);
        this.bPY.setOnClickListener(new 10(this));
        this.bbP = (TextView) this.bPX.findViewById(R.id.tv_msg);
        this.bbO = (ImageButton) this.bPX.findViewById(R.id.img_msg);
        this.bbO.setOnClickListener(new 11(this));
        this.bQi = (ImageView) this.bPX.findViewById(R.id.sys_header_left_img);
        this.bQj = (TextView) this.bPX.findViewById(R.id.tv_msg_tip);
        this.bQi.setVisibility(0);
        this.bQi.setOnClickListener(new 12(this));
        if (d.isDayMode()) {
            this.eN.setTextColor(this.bQc.getColor(R.color.home_titlebar_title_text_color));
            this.bPY.setImageResource(R.drawable.home_local_resource_icon_selector);
            this.bbO.setImageResource(R.drawable.btn_main_msg_selector);
            this.bQi.setImageResource(R.drawable.home_more_selector);
            return;
        }
        this.eN.setTextColor(this.bQc.getColor(R.color.home_titlebar_title_text_color_night));
        this.bPY.setImageResource(R.drawable.home_local_resource_night_selector);
        this.bbO.setImageResource(R.drawable.btn_main_msg_selector_night);
        this.bQi.setImageResource(R.drawable.home_more_night_selector);
    }

    private void L(View view) {
        this.bPv.setOnRefreshListener(new 13(this));
        this.bPF.setOnClickListener(this.mClickListener);
        this.bPG.setOnClickListener(this.mClickListener);
        this.bPH.setOnClickListener(this.mClickListener);
        this.bPI.setOnClickListener(this.mClickListener);
        this.bPJ.setOnClickListener(this.mClickListener);
        this.bPK.setOnClickListener(this.mClickListener);
        this.bPL.setOnClickListener(this.mClickListener);
        this.bPM.setOnClickListener(this.mClickListener);
        view.findViewById(R.id.iv_studio_logo).setOnClickListener(this.mClickListener);
        this.aED.setOnPageChangeListener(new 6(this));
        this.bQk.setRetryClickListener(new 14(this));
    }

    private void EQ() {
        this.bQk.Fy();
        this.bPv.setPullToRefreshEnabled(false);
        this.bPx = this.bPw.getLayoutParams();
        this.bPV.setVisibility(8);
        this.bPu.setMaxScrollY((int) this.mContext.getResources().getDimension(R.dimen.home_no_banner_header_height));
        this.bPx.height = (int) this.mContext.getResources().getDimension(R.dimen.home_no_banner_header_height);
        if (this.bPt == null) {
            this.bPt = new 8(this, getChildFragmentManager());
        }
        this.aED.setAdapter(this.bPt);
        this.bQb.setViewPager(this.aED);
        this.bPu.setFriction(0.1f);
        this.bPu.setCloseAnimatorConfigurator(new ru.noties.scrollable.i(new DecelerateInterpolator()));
        this.bPu.setCanScrollVerticallyDelegate(new 15(this));
        this.bPu.setOnFlingOverListener(new 2(this));
        this.bPu.setOnScrollChangedListener(new 3(this));
        if (d.isDayMode()) {
            this.bQb.setTextColorResource(R.color.home_navigation_tab_common);
            this.bQb.setIndicatorColor(this.mContext.getResources().getColor(R.color.home_navigation_tab_click));
            return;
        }
        this.bQb.setTextColorResource(R.color.home_navigation_tab_common_night);
        this.bQb.setIndicatorColor(this.mContext.getResources().getColor(R.color.home_navigation_tab_click_night));
    }

    private void EZ() {
        RN();
    }

    public void RN() {
        k.DC().DF();
        v.DL().DM();
        k.DC().DE();
        k.DC().DD();
    }

    private void reload() {
        EZ();
        if (this.bPt != null && this.aED != null) {
            Object fragment = this.bPt.instantiateItem(this.aED, this.aED.getCurrentItem());
            if (fragment instanceof MapJsWoodSkinFragment) {
                ((MapJsWoodSkinFragment) fragment).EZ();
            }
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        if (this.bPZ != null) {
            i.unregisterReceiver(this.bPZ);
            this.bPZ = null;
        }
        if (this.bQa != null) {
            i.unregisterReceiver(this.bQa);
            this.bQa = null;
        }
    }

    protected void Fr() {
        MsgCounts msgCounts = HTApplication.bM();
        long allCount = msgCounts == null ? 0 : msgCounts.getAll();
        if (allCount > 0) {
            this.bbP.setVisibility(0);
            if (allCount > 99) {
                this.bbP.setText("99+");
                return;
            } else {
                this.bbP.setText(String.valueOf(msgCounts.getAll()));
                return;
            }
        }
        this.bbP.setVisibility(8);
    }

    protected void Fs() {
        this.bbP.setVisibility(8);
    }

    public void RO() {
        if (this.bPy != null) {
            int mapCount = HTApplication.fF;
            int jsCount = HTApplication.fG;
            int woodCount = HTApplication.fH;
            int skinCount = HTApplication.fI;
            int serverCount = HTApplication.fJ;
            int seedCount = HTApplication.fK;
            int recommendCount = HTApplication.fL;
            int mapCountLocal = ah.KZ().getMapCount();
            int jsCountLocal = ah.KZ().getJsCount();
            int skinCountLocal = ah.KZ().getSkinCount();
            int woodCountLocal = ah.KZ().getWoodCount();
            int serverCountLocal = ah.KZ().getServerCount();
            int seedCountLocal = ah.KZ().getSeedCount();
            int recommendCountLocal = ah.KZ().getAdCount();
            int moreMapCount = mapCount - mapCountLocal;
            c(this.bPy, moreMapCount);
            int moreJsCount = jsCount - jsCountLocal;
            c(this.bPz, moreJsCount);
            int moreSkinCount = skinCount - skinCountLocal;
            c(this.bPA, moreSkinCount);
            int moreWoodCount = woodCount - woodCountLocal;
            c(this.bPB, moreWoodCount);
            int moreServerCount = serverCount - serverCountLocal;
            c(this.bPC, moreServerCount);
            int moreSeedCount = seedCount - seedCountLocal;
            c(this.bPD, moreSeedCount);
            int moreRecommendCount = recommendCount - recommendCountLocal;
            c(this.bPE, moreRecommendCount);
        }
    }

    protected void c(TextView tap, int count) {
        if (count > 0) {
            tap.setVisibility(0);
            if (count > 99) {
                tap.setText("99+");
                return;
            } else {
                tap.setText(String.valueOf(count));
                return;
            }
        }
        tap.setVisibility(8);
    }

    private void RP() {
        if (this.bPV != null) {
            int w_pic = UtilsScreen.getScreenPixWidth(this.mContext) - (UtilsScreen.dipToPx(this.mContext, 4) * 2);
            this.bPW = (LayoutParams) this.bPV.getLayoutParams();
            this.bPW.height = (int) (((double) w_pic) * 0.375d);
        }
    }

    private void J(List<com.huluxia.data.banner.a.a> banners) {
        RP();
        if (banners.isEmpty()) {
            this.bPV.setVisibility(8);
            return;
        }
        List<com.huluxia.widget.banner.a> info = new ArrayList();
        info.addAll(banners);
        this.bPV.setVisibility(0);
        if (this.bPW != null) {
            int h = ((int) this.mContext.getResources().getDimension(R.dimen.home_no_banner_header_height)) + this.bPW.height;
            this.bPu.setMaxScrollY(h);
            this.bPx.height = h;
        } else {
            this.bPu.setMaxScrollY((int) this.mContext.getResources().getDimension(R.dimen.home_header_height));
            this.bPx.height = (int) this.mContext.getResources().getDimension(R.dimen.home_header_height);
        }
        this.bPV.setIndicatorVisible(false);
        this.bPV.getGallery().setLoader(new 4(this));
        this.bPV.getGallery().setInterval(3000);
        this.bPV.setData(info);
        this.bPV.setOnItemClickListener(new 5(this));
    }

    public void c(MenuDrawer mMenuDrawer) {
        this.aPB = mMenuDrawer;
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (themeId == AN()) {
            this.bQb.setTextColorResource(R.color.home_navigation_tab_common);
            this.bQb.setIndicatorColor(this.mContext.getResources().getColor(R.color.home_navigation_tab_click));
            dv(false);
        }
        if (themeId == AO()) {
            this.bQb.setTextColorResource(R.color.home_navigation_tab_common_night);
            this.bQb.setIndicatorColor(this.mContext.getResources().getColor(R.color.home_navigation_tab_click_night));
            dv(true);
        }
        this.bQk.FC();
        this.bQk.FB();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.j(this.bPF, R.attr.home_ic_game_option_bg).c(this.bPF, R.attr.home_ic_game_option).j(this.bPG, R.attr.home_map_bg).c(this.bPG, R.attr.home_map).j(this.bPH, R.attr.home_js_bg).c(this.bPH, R.attr.home_js).j(this.bPI, R.attr.home_wood_bg).c(this.bPI, R.attr.home_wood).j(this.bPJ, R.attr.home_skin_bg).c(this.bPJ, R.attr.home_skin).j(this.bPK, R.attr.home_server_bg).c(this.bPK, R.attr.home_server).j(this.bPL, R.attr.home_seed_bg).c(this.bPL, R.attr.home_seed).j(this.bPM, R.attr.home_app_recommend_bg).c(this.bPM, R.attr.home_app_recommend).aZ(R.id.iv_studio_logo, R.attr.home_bg_studio).bc(R.id.iv_studio_logo, R.attr.home_ic_studio).a(this.bPN, R.attr.home_common_text_color).a(this.bPO, R.attr.home_common_text_color).a(this.bPP, R.attr.home_common_text_color).a(this.bPQ, R.attr.home_common_text_color).a(this.bPR, R.attr.home_common_text_color).a(this.bPS, R.attr.home_common_text_color).a(this.bPT, R.attr.home_common_text_color).a(this.bPU, R.attr.home_common_text_color).ba(R.id.tv_studio, R.attr.home_common_text_color).i(this.bQd, R.attr.home_split_line_bg).i(this.bQe, R.attr.home_split_line_bg).i(this.bQf, R.attr.home_split_line_bg).i(this.bQg, R.attr.home_split_line_bg).i(this.bQh, R.attr.home_interval_bg).aY(R.id.home_fragment_bg, R.attr.home_common_bg).i(this.bQk, R.attr.home_interval_bg);
    }

    private void dv(boolean nightMode) {
        if (nightMode) {
            this.bPX.setBackgroundColor(this.bQc.getColor(R.color.home_titlebar_bg_night));
            this.eN.setTextColor(this.bQc.getColor(R.color.home_titlebar_title_text_color_night));
            this.bPY.setImageResource(R.drawable.home_local_resource_night_selector);
            this.bbO.setImageResource(R.drawable.btn_main_msg_selector_night);
            this.bQi.setImageResource(R.drawable.home_more_night_selector);
            return;
        }
        this.bPX.setBackgroundColor(this.bQc.getColor(R.color.home_titlebar_bg));
        this.eN.setTextColor(this.bQc.getColor(R.color.home_titlebar_title_text_color));
        this.bPY.setImageResource(R.drawable.home_local_resource_icon_selector);
        this.bbO.setImageResource(R.drawable.btn_main_msg_selector);
        this.bQi.setImageResource(R.drawable.home_more_selector);
    }
}
