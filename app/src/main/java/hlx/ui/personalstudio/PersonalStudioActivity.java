package hlx.ui.personalstudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.j;
import com.huluxia.data.profile.c;
import com.huluxia.data.profile.d;
import com.huluxia.data.profile.e;
import com.huluxia.data.profile.e.a;
import com.huluxia.data.studio.b;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.widget.PagerSlidingIndicator.IndicatorTextSizeChange;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.http.base.f;
import com.huluxia.http.profile.l;
import com.huluxia.k;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.module.z;
import com.huluxia.studio.fragment.StudioResourceFragment;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.utils.ah;
import com.huluxia.widget.PullToRefreshScrollableLayout;
import com.huluxia.widget.textview.EmojiTextView;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import hlx.home.fragment.ScrollableFragment;
import hlx.home.fragment.ScrollablePageAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.noties.scrollable.ScrollableLayout;
import ru.noties.scrollable.i;

public class PersonalStudioActivity extends HTBaseLoadingActivity implements f {
    private static final int PAGE_SIZE = 10;
    public static final String aDW = "STUDIO_ID";
    private static final String cdt = "studio_id=";
    private final String TAG = "PersonalStudioActivity";
    private int aDX;
    private int aDY = 3;
    private CallbackHandler aGM = new CallbackHandler(this) {
        final /* synthetic */ PersonalStudioActivity cdB;

        {
            this.cdB = this$0;
        }

        @MessageHandler(message = 784)
        public void acceptStudioInfo(boolean isSucc, c studio, int studioId, Object ctx) {
            this.cdB.bPv.onRefreshComplete();
            if (isSucc && this.cdB.aDX == studioId) {
                this.cdB.big = studio;
                this.cdB.cdm.setmStudio(studio);
                if (studio == null) {
                    t.download_toast(this.cdB.mContext, "工作室已解散");
                    this.cdB.finish();
                }
            }
        }

        @MessageHandler(message = 12401)
        public void accepStudioMemberList(boolean isSucc, e info, int sid) {
            if (this.cdB.aDX == sid) {
                if (isSucc && info != null) {
                    this.cdB.cdA = info.studioUserList;
                    if (!UtilsFunction.empty(info.studioUserList)) {
                        a user = (a) info.studioUserList.get(0);
                        if (user == null || 1 != user.isStudio) {
                            if (user == null || 2 != user.isStudio) {
                                if (!(user == null || user.user == null || user.user.userID != j.ep().getUserid())) {
                                    this.cdB.aDY = 0;
                                    this.cdB.d(user.user);
                                }
                            } else if (user.user != null && user.user.userID == j.ep().getUserid()) {
                                this.cdB.aDY = 2;
                                this.cdB.d(user.user);
                            }
                        } else if (user.user != null && user.user.userID == j.ep().getUserid()) {
                            this.cdB.aDY = 1;
                            this.cdB.d(user.user);
                        }
                    }
                    this.cdB.Vk();
                    this.cdB.FC();
                    this.cdB.cdm.setData(info.studioUserList);
                } else if (this.cdB.cdl) {
                    if (info != null) {
                        t.n(this.cdB.mContext, info.msg);
                    }
                    if (this.cdB.cdA == null) {
                        this.cdB.FB();
                    }
                }
            }
        }

        @MessageHandler(message = 789)
        public void onRecvAuditResult(boolean succ, w info, int position, int opt, int sid) {
            if (this.cdB.aDX == sid) {
                if ((UtilsFunction.empty(this.cdB.cdA) || this.cdB.cdA.size() <= 5) && succ && info != null && opt == 1) {
                    z.DO();
                    z.v(0, 10, this.cdB.aDX, 257);
                }
            }
        }

        @MessageHandler(message = 777)
        public void onRecRemoveMembers(boolean succ, w info, a user, int studioId) {
            if (this.cdB.aDX == studioId) {
                if (this.cdB.cdy == user) {
                    if (!succ || info == null) {
                        t.n(this.cdB.mContext, info != null ? info.msg : this.cdB.getString(R.string.exit_failure));
                        return;
                    }
                    this.cdB.aDY = 3;
                    t.o(this.cdB.mContext, this.cdB.getString(R.string.exit_success));
                    this.cdB.cdp.execute();
                    z.DO();
                    z.v(0, 10, this.cdB.aDX, 257);
                } else if (succ && info != null) {
                    z.DO();
                    z.v(0, 10, this.cdB.aDX, 257);
                }
            }
        }

        @MessageHandler(message = 809)
        public void recvSetViceLeaderResult(int sid, boolean succ, w info, a studioUser) {
            if (studioUser != null && this.cdB.aDX == sid && succ) {
                z.DO();
                z.v(0, 10, this.cdB.aDX, 257);
            }
        }

        @MessageHandler(message = 816)
        public void recvCancelViceLeaderResult(int sid, boolean succ, w info, a studioUser) {
            if (studioUser != null && this.cdB.aDX == sid && succ) {
                z.DO();
                z.v(0, 10, this.cdB.aDX, 257);
            }
        }

        @MessageHandler(message = 817)
        public void recvStudioTransferResult(int sid, boolean succ, w info) {
            if (this.cdB.aDX == sid && succ) {
                z.DO();
                z.v(0, 10, this.cdB.aDX, 257);
            }
        }

        @MessageHandler(message = 786)
        public void onRecvStudioInfo(boolean succ, d info, int studioId) {
            if (succ && info != null && this.cdB.aDX == studioId) {
                this.cdB.nK(info.total);
            }
        }

        @MessageHandler(message = 818)
        public void recvNewestStudioAnnonce(int sid, boolean succ, b info) {
            if (this.cdB.aDX != sid || !this.cdB.cdl) {
                return;
            }
            if (succ && info != null) {
                this.cdB.b(info.info);
            } else if (info != null) {
                t.n(this.cdB.mContext, info.msg);
            }
        }
    };
    private ScrollablePageAdapter bPt;
    private ScrollableLayout bPu;
    private PullToRefreshScrollableLayout bPv;
    private PagerSlidingTabStrip bQb;
    private CommonMenuDialog biG = null;
    private c big;
    private List<a> cdA;
    private boolean cdl = true;
    private PersonalStudioHeaderLayout cdm;
    private TextView cdn;
    private TextView cdo;
    private com.huluxia.http.profile.a cdp = new com.huluxia.http.profile.a();
    private l cdq = new l();
    private final int cdr = 257;
    private final int cds = 258;
    private final int cdu = 1;
    private final int cdv = 2;
    private final int cdw = 4;
    private CommonMenuDialogListener cdx = new CommonMenuDialogListener(this) {
        final /* synthetic */ PersonalStudioActivity cdB;

        {
            this.cdB = this$0;
        }

        public void pressMenuById(int inIndex, Object object) {
            this.cdB.biG.dismissDialog();
            if (inIndex == 1 && this.cdB.big != null && this.cdB.big.studioInfo != null) {
                t.a(this.cdB.mContext, this.cdB.big, false, 1);
            } else if (inIndex == 2 && this.cdB.big != null && this.cdB.big.studioInfo != null) {
                this.cdB.cdo.setVisibility(8);
                t.g(this.cdB.mContext, this.cdB.big.studioInfo.id);
            } else if (inIndex == 4 && this.cdB.big != null) {
                new DialogManager(this.cdB.mContext).showOkCancelDialog("退出工作室", String.format("你将退出 %s，退出后120小时内禁止加入任何工作室！", new Object[]{this.cdB.big.studioInfo.name}), (CharSequence) "退出", hlx.data.localstore.a.bKB, true, new OkCancelDialogListener(this) {
                    final /* synthetic */ AnonymousClass1 cdC;

                    {
                        this.cdC = this$1;
                    }

                    public void onCancel() {
                    }

                    public void onOk() {
                        z.DO();
                        z.a(this.cdC.cdB.aDX, j.ep().getUserid(), this.cdC.cdB.cdy);
                    }
                });
            }
        }
    };
    private final a cdy = null;
    private boolean cdz = false;
    private Activity mContext;
    private ViewPager mPager;
    private int tk = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_personal_studio);
        this.mContext = this;
        if (savedInstanceState != null) {
            this.aDX = savedInstanceState.getInt("STUDIO_ID", 0);
        } else {
            this.aDX = getIntent().getIntExtra("STUDIO_ID", 0);
        }
        Vi();
        Vj();
        initView();
        EQ();
        EZ();
        Fy();
    }

    protected void onStart() {
        super.onStart();
        this.cdl = true;
    }

    protected void onStop() {
        super.onStop();
        this.cdl = false;
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aGM);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("STUDIO_ID", this.aDX);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            z.DO();
            z.b(this.aDX, null);
        }
    }

    private void Vi() {
        EventNotifyCenter.add(h.class, this.aGM);
    }

    private void Vj() {
        ej("");
        findViewById(R.id.fl_msg).setVisibility(8);
        this.aIQ.setVisibility(4);
        this.aIQ.setImageResource(com.simple.colorful.d.r(this, R.attr.ic_more_option));
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public void onClick(View v) {
                if (this.cdB.cdz) {
                    t.download_toast(this.cdB.mContext, "账户被锁，不能进行此操作");
                    return;
                }
                ArrayList<Object> mSortArrayList = new ArrayList();
                if (this.cdB.aDY == 0) {
                    mSortArrayList.add(new ResMenuItem("退出", 4, R.color.studio_me_in_member_list));
                } else if (1 == this.cdB.aDY) {
                    mSortArrayList.add(new ResMenuItem("编辑资料", 1, 0));
                    mSortArrayList.add(new ResMenuItem("待处理申请", 2, 0));
                } else if (2 == this.cdB.aDY) {
                    mSortArrayList.add(new ResMenuItem("编辑资料", 1, 0));
                    mSortArrayList.add(new ResMenuItem("待处理申请", 2, 0));
                    mSortArrayList.add(new ResMenuItem("退出", 4, R.color.studio_me_in_member_list));
                }
                this.cdB.biG = new CommonMenuDialog(this.cdB.mContext, this.cdB.cdx, com.simple.colorful.d.RB(), 1);
                this.cdB.biG.setMenuItems(mSortArrayList);
                this.cdB.biG.showMenu(null, null);
            }
        });
        ImageButton imbAnnounce = (ImageButton) findViewById(R.id.sys_header_right_second_img);
        imbAnnounce.setVisibility(0);
        imbAnnounce.setImageResource(com.simple.colorful.d.r(this, R.attr.ic_studio_announce));
        imbAnnounce.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public void onClick(View v) {
                k.b(this.cdB.mContext, this.cdB.aDX, this.cdB.aDY);
            }
        });
    }

    private void initView() {
        this.bPv = (PullToRefreshScrollableLayout) findViewById(R.id.scroll_layout);
        this.bPu = (ScrollableLayout) this.bPv.getRefreshableView();
        this.bQb = (PagerSlidingTabStrip) findViewById(R.id.pager_sliding_tab_studio);
        this.mPager = (ViewPager) findViewById(R.id.view_pager_studio);
        this.cdm = (PersonalStudioHeaderLayout) findViewById(R.id.studio_header_layout);
        this.cdn = this.cdm.getTvApplyStatus();
        this.cdo = (TextView) findViewById(R.id.tv_red_point);
    }

    private void EQ() {
        this.bPv.setOnRefreshListener(new OnRefreshListener<ScrollableLayout>(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public void onRefresh(PullToRefreshBase<ScrollableLayout> pullToRefreshBase) {
                this.cdB.reload();
            }
        });
        this.cdn.setVisibility(8);
        this.cdn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public void onClick(View v) {
                if (j.ep().ey()) {
                    this.cdB.cdq.execute();
                } else {
                    t.an(this.cdB.mContext);
                }
            }
        });
        this.cdp.J((long) this.aDX);
        this.cdp.bb(257);
        this.cdp.a(this);
        this.cdq.J((long) this.aDX);
        this.cdq.bb(258);
        this.cdq.a(this);
        this.bQb.setDividerColorResource(R.color.transparent);
        this.bQb.setIndicatorHeight(UtilsScreen.sp2px(this.mContext, 2.0f));
        this.bQb.setTextSize(UtilsScreen.sp2px(this.mContext, 14.0f));
        this.bQb.setIndicatorTextColor(true);
        this.bQb.setIndicatorOffset(true);
        this.bQb.setIndicatorTextSize(new IndicatorTextSizeChange(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public int getTextSizePx() {
                return UtilsScreen.sp2px(this.cdB.mContext, 16.0f);
            }
        });
        Vm();
        this.bPu.setDraggableView(this.bQb);
        this.mPager.setOnPageChangeListener(new SimpleOnPageChangeListener());
        if (this.bPt == null) {
            this.bPt = new ScrollablePageAdapter(this, getSupportFragmentManager()) {
                String[] bQm = this.cdB.mContext.getResources().getStringArray(R.array.home_recommend_list);
                final /* synthetic */ PersonalStudioActivity cdB;

                public /* synthetic */ PagerFragment getItem(int i) {
                    return mE(i);
                }

                public ScrollableFragment mE(int position) {
                    switch (position) {
                        case 0:
                            return StudioResourceFragment.av(1, this.cdB.aDX);
                        case 1:
                            return StudioResourceFragment.av(2, this.cdB.aDX);
                        case 2:
                            return StudioResourceFragment.av(4, this.cdB.aDX);
                        default:
                            return StudioResourceFragment.av(3, this.cdB.aDX);
                    }
                }

                public CharSequence getPageTitle(int position) {
                    return this.bQm[position];
                }

                public int getCount() {
                    return 4;
                }
            };
        }
        this.mPager.setOffscreenPageLimit(3);
        this.mPager.setAdapter(this.bPt);
        this.bQb.setViewPager(this.mPager);
        this.bPu.setAutoMaxScroll(true);
        this.bPu.setFriction(0.1f);
        this.bPu.setCloseAnimatorConfigurator(new i(new DecelerateInterpolator()));
        this.bPu.setCanScrollVerticallyDelegate(new ru.noties.scrollable.b(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public boolean canScrollVertically(int direction) {
                if (this.cdB.bPt != null) {
                    return this.cdB.bPt.bk(this.cdB.mPager.getCurrentItem(), direction);
                }
                return false;
            }
        });
        this.bPu.setOnFlingOverListener(new ru.noties.scrollable.j(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public void f(int y, long duration) {
                if (this.cdB.bPt != null) {
                    ((ScrollableFragment) this.cdB.bPt.getPosFragment(this.cdB.mPager.getCurrentItem())).f(y, duration);
                }
            }
        });
        this.bPu.setOnScrollChangedListener(new ru.noties.scrollable.k(this) {
            final /* synthetic */ PersonalStudioActivity cdB;

            {
                this.cdB = this$0;
            }

            public void ad(int y, int oldY, int maxY) {
                float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = 0.0f;
                } else {
                    tabsTranslationY = (float) (y - maxY);
                }
                if (VERSION.SDK_INT >= 11) {
                    this.cdB.bQb.setTranslationY(tabsTranslationY);
                    this.cdB.cdm.setTranslationY(tabsTranslationY);
                }
            }
        });
    }

    private void EZ() {
        z.DO();
        z.b(this.aDX, null);
        z.DO();
        z.v(0, 10, this.aDX, 257);
        this.cdp.execute();
        z.DO().ap(this.aDX, com.huluxia.pref.b.Em().getInt(cdt + this.aDX, 0));
    }

    protected void EX() {
        super.EX();
        EZ();
    }

    private void Vk() {
        if (3 == this.aDY) {
            ej("工作室");
            this.aIQ.setVisibility(4);
            return;
        }
        ej("我的工作室");
        this.aIQ.setVisibility(0);
        if (1 == this.aDY || 2 == this.aDY) {
            z.DO();
            z.U(0, 10, this.aDX);
        }
    }

    private void d(UserBaseInfo info) {
        if (info != null && 2 == info.state) {
            this.cdz = true;
        }
    }

    private void nK(int currentCount) {
        if (currentCount <= ah.KZ().LK() || currentCount <= 0 || this.big == null || this.big.studioInfo == null || ((long) this.big.studioInfo.userid) != j.ep().getUserid()) {
            this.cdo.setVisibility(8);
        } else {
            this.cdo.setVisibility(0);
        }
        ah.KZ().lm(currentCount);
    }

    public void reload() {
        z.DO();
        z.b(this.aDX, null);
        z.DO();
        z.v(0, 10, this.aDX, 257);
        this.cdp.execute();
        if (this.bPt != null && this.mPager != null) {
            Object fragment = this.bPt.instantiateItem(this.mPager, this.mPager.getCurrentItem());
            if (fragment instanceof StudioResourceFragment) {
                ((StudioResourceFragment) fragment).reload();
            }
        }
    }

    public void a(com.huluxia.http.base.d response) {
    }

    public void b(com.huluxia.http.base.d response) {
    }

    public void c(com.huluxia.http.base.d response) {
        switch (response.fe()) {
            case 257:
                this.tk = this.cdp.fR();
                Vl();
                return;
            case 258:
                if (104 == response.fg()) {
                    t.download_toast(this.mContext, response.fh());
                    return;
                }
                switch (this.tk) {
                    case 1:
                        this.tk = 4;
                        Vl();
                        return;
                    case 2:
                    case 3:
                    case 4:
                        this.tk = 0;
                        Vl();
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }

    private void Vl() {
        switch (this.tk) {
            case -1:
            case 5:
                this.cdn.setVisibility(8);
                return;
            case 0:
                this.cdn.setText("已申请");
                this.cdn.setVisibility(0);
                this.cdn.setClickable(false);
                this.cdn.setBackgroundResource(com.simple.colorful.d.r(this.mContext, R.attr.bg_studio_applied));
                return;
            case 1:
                this.cdn.setText("退出");
                this.cdn.setVisibility(8);
                this.cdn.setClickable(true);
                this.cdn.setBackgroundResource(com.simple.colorful.d.r(this.mContext, R.attr.bg_studio_apply_exit));
                return;
            case 2:
            case 3:
            case 4:
                this.cdn.setText("申请加入");
                this.cdn.setVisibility(0);
                this.cdn.setClickable(true);
                this.cdn.setBackgroundResource(com.simple.colorful.d.r(this.mContext, R.attr.bg_studio_apply_enter));
                return;
            default:
                return;
        }
    }

    protected void b(b.a item) {
        if (item != null && !UtilsFunction.empty(item.title) && !UtilsFunction.empty(item.content)) {
            final DialogManager dialog = new DialogManager(this.mContext);
            dialog.setCanceledOnClickOutside(false);
            View view = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_studio_announce, null);
            EmojiTextView tvTitle = (EmojiTextView) view.findViewById(R.id.tv_announce_title);
            TextView tvAnnouncer = (TextView) view.findViewById(R.id.tv_announcer);
            TextView tvIssueTime = (TextView) view.findViewById(R.id.tv_issue_time);
            EmojiTextView tvContent = (EmojiTextView) view.findViewById(R.id.tv_announce_content);
            view.findViewById(R.id.tv_more_announce).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PersonalStudioActivity cdB;

                public void onClick(View v) {
                    k.b(this.cdB.mContext, this.cdB.aDX, this.cdB.aDY);
                    dialog.dismissDialog();
                }
            });
            view.findViewById(R.id.tv_ok).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ PersonalStudioActivity cdB;

                public void onClick(View v) {
                    dialog.dismissDialog();
                }
            });
            tvTitle.setText(item.title);
            tvAnnouncer.setText(item.announcer);
            tvIssueTime.setText(new SimpleDateFormat("MM月dd日 HH:mm").format(new Date(item.createTime)));
            tvContent.setText(item.content);
            com.huluxia.pref.b.Em().putInt(cdt + this.aDX, item.id);
            dialog.showDefaultViewPopupDialog(view);
        }
    }

    private void Vm() {
        this.bQb.setUnderlineColor(com.simple.colorful.d.getColor(this.mContext, R.attr.splitColor));
        if (com.simple.colorful.d.isDayMode()) {
            this.bQb.setTextColorResource(R.color.home_navigation_tab_common);
            this.bQb.setIndicatorColor(this.mContext.getResources().getColor(R.color.home_navigation_tab_click));
            return;
        }
        this.bQb.setTextColorResource(R.color.home_navigation_tab_common_night);
        this.bQb.setIndicatorColor(this.mContext.getResources().getColor(R.color.home_navigation_tab_click_night));
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.a(this.cdm).c(this.aIQ, R.attr.ic_more_option).a(this.aIR, R.attr.backText).a(this.aIR, R.attr.back, 1).aY(R.id.root_view, R.attr.backgroundDefault).bc(R.id.sys_header_right_second_img, R.attr.ic_studio_announce);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.cdm.FG();
        Vm();
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
