package hlx.home.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import com.huluxia.HTApplication;
import com.huluxia.McApplication;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.framework.base.widget.pager.PagerSelectedAdapter;
import com.huluxia.framework.base.widget.pager.SelectedViewPager;
import com.huluxia.http.base.f;
import com.huluxia.http.other.j;
import com.huluxia.k;
import com.huluxia.login.LoginError.LoginErrCode;
import com.huluxia.mcgame.g;
import com.huluxia.module.n;
import com.huluxia.module.topic.TopicModule;
import com.huluxia.r;
import com.huluxia.service.HlxPushService;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.area.news.ResourceNewsFragment;
import com.huluxia.ui.base.BaseFragment;
import com.huluxia.ui.base.HTBaseThemeActivity;
import com.huluxia.ui.bbs.TopicDetailActivity;
import com.huluxia.ui.home.BbsFragment;
import com.huluxia.ui.home.ProfileMeFragment;
import com.huluxia.utils.ah;
import com.huluxia.utils.ax;
import com.huluxia.utils.u;
import com.huluxia.version.VersionDialog;
import com.huluxia.version.e;
import com.huluxia.version.h;
import com.huluxia.widget.Constants;
import com.huluxia.widget.Constants.Model;
import com.huluxia.widget.Constants.ReStartSoftFlag;
import com.huluxia.widget.menudrawer.MenuDrawer;
import com.huluxia.widget.menudrawer.MenuDrawer.Type;
import com.huluxia.widget.menudrawer.Position;
import com.simple.colorful.d;
import hlx.home.adapter.MenuAdapter;
import hlx.home.fragment.HomeFragment;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.ant.util.FileUtils;

public class HomeActivity extends HTBaseThemeActivity implements f {
    private static final String TAG = "HomeActivity";
    protected Handler Vo = new Handler(this) {
        final /* synthetic */ HomeActivity bQP;

        {
            this.bQP = this$0;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.bQP.Sb();
                    this.bQP.RV();
                    this.bQP.RW();
                    this.bQP.RX();
                    r.ck().dt();
                    this.bQP.Vo.sendMessageDelayed(this.bQP.Vo.obtainMessage(3), FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    ah.KZ().Q(g.adF, 0);
                    return;
                case 3:
                    String app_name = UtilsApkPackage.getAppPackageName(this.bQP.bQJ);
                    String channel = UtilsApkPackage.getAppMetadata(this.bQP.bQJ, "UMENG_CHANNEL");
                    if (channel == null) {
                        channel = "mctool_huluxia";
                    }
                    h.MU().ax(app_name, channel);
                    return;
                default:
                    return;
            }
        }
    };
    protected SelectedViewPager aED;
    protected OnClickListener aEK = new OnClickListener(this) {
        final /* synthetic */ HomeActivity bQP;

        {
            this.bQP = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.rly_home_tab || id == R.id.tv_home_tab) {
                this.bQP.aED.setCurrentItem(0, false);
            } else if (id == R.id.rly_community_tab || id == R.id.tv_community_tab) {
                this.bQP.aED.setCurrentItem(2, false);
            } else if (id == R.id.rly_news_tab || id == R.id.tv_news_tab) {
                this.bQP.aED.setCurrentItem(1, false);
            } else if (id == R.id.rly_space_tab || id == R.id.tv_space_tab) {
                this.bQP.aED.setCurrentItem(3, false);
            }
        }
    };
    protected OnPageChangeListener aEM = new OnPageChangeListener(this) {
        final /* synthetic */ HomeActivity bQP;

        {
            this.bQP = this$0;
        }

        public void onPageScrolled(int i, float v, int i2) {
        }

        public void onPageSelected(int position) {
            this.bQP.aPB.setTouchMode(position == 0 ? 1 : 0);
            switch (position) {
                case 1:
                    this.bQP.kk(R.id.rly_news_tab);
                    return;
                case 2:
                    this.bQP.kk(R.id.rly_community_tab);
                    return;
                case 3:
                    this.bQP.kk(R.id.rly_space_tab);
                    return;
                default:
                    this.bQP.kk(R.id.rly_home_tab);
                    return;
            }
        }

        public void onPageScrollStateChanged(int i) {
        }
    };
    protected PagerSelectedAdapter aEN = new PagerSelectedAdapter(this, getSupportFragmentManager()) {
        final /* synthetic */ HomeActivity bQP;

        public PagerFragment getItem(int position) {
            switch (position) {
                case 1:
                    return new ResourceNewsFragment();
                case 2:
                    return BbsFragment.Hq();
                case 3:
                    return ProfileMeFragment.c(true, 0);
                default:
                    BaseFragment frag = HomeFragment.RM();
                    this.bQP.bQD = (HomeFragment) frag;
                    this.bQP.bQD.c(this.bQP.aPB);
                    return frag;
            }
        }

        public int getCount() {
            return 4;
        }

        public void restoreState(Parcelable state, ClassLoader loader) {
            super.restoreState(state, loader);
            Fragment fragment = getPosFragment(0);
            if (fragment != null && (fragment instanceof HomeFragment)) {
                this.bQP.bQD = (HomeFragment) fragment;
                this.bQP.bQD.c(this.bQP.aPB);
            }
        }
    };
    protected final int aEy = 4;
    private BroadcastReceiver aKb;
    private MenuDrawer aPB;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ HomeActivity bQP;

        {
            this.bQP = this$0;
        }

        @MessageHandler(message = 773)
        public void onRecvVerinfo(boolean succ, e info, String tag) {
            if (succ && tag != null && tag.equals(HomeActivity.TAG) && info != null && info.updateType == 0) {
                long myVercode = (long) UtilsVersion.getVersionCode(this.bQP.bQJ);
                String myPackname = UtilsApkPackage.getAppPackageName(this.bQP.bQJ);
                if (info.versioncode > myVercode && myPackname != null && info.packname.equals(myPackname)) {
                    VersionDialog.a(info).show(this.bQP.bQJ.getSupportFragmentManager(), null);
                }
            }
        }

        @MessageHandler(message = 774)
        public void onRecConf(boolean succ, com.huluxia.version.a info) {
            boolean z = true;
            String str = HomeActivity.TAG;
            String str2 = "isSucc %s";
            Object[] objArr = new Object[1];
            objArr[0] = Boolean.valueOf(succ);
            HLog.verbose(str, str2, objArr);
            if (!succ || info == null) {
                ah.KZ().dd(true);
                this.bQP.dw(true);
                return;
            }
            boolean z2;
            ah KZ = ah.KZ();
            if (info.newUpdate == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            KZ.dd(z2);
            HomeActivity homeActivity = this.bQP;
            if (info.newUpdate != 1) {
                z = false;
            }
            homeActivity.dw(z);
        }
    };
    protected CheckedTextView bQA;
    protected CheckedTextView bQB;
    protected CheckedTextView bQC;
    private HomeFragment bQD;
    private ListView bQE;
    private MenuAdapter bQF;
    private CheckBox bQG;
    private j bQH = new j();
    protected BroadcastReceiver bQI;
    private HomeActivity bQJ;
    private String bQK = null;
    private com.huluxia.http.studio.b bQL = new com.huluxia.http.studio.b();
    private int bQM = 257;
    private OnCheckedChangeListener bQN = new OnCheckedChangeListener(this) {
        final /* synthetic */ HomeActivity bQP;

        {
            this.bQP = this$0;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                r.ck().K_umengEvent(hlx.data.tongji.a.bMj);
            }
            d.RC();
        }
    };
    private long bQO;
    protected CheckedTextView bQz;
    private CallbackHandler pl;

    protected class CountMsgReciver extends BroadcastReceiver {
        final /* synthetic */ HomeActivity bQP;

        protected CountMsgReciver(HomeActivity this$0) {
            this.bQP = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            HLog.verbose("CountMsgReciver", "onReceive", new Object[0]);
            this.bQP.RZ();
        }
    }

    private class a extends BroadcastReceiver {
        final /* synthetic */ HomeActivity bQP;

        private a(HomeActivity homeActivity) {
            this.bQP = homeActivity;
        }

        public void onReceive(Context context, Intent intent) {
            this.bQP.RU();
        }
    }

    static class b extends CallbackHandler {
        WeakReference<HomeActivity> akD;

        b(HomeActivity activity) {
            this.akD = new WeakReference(activity);
        }

        @MessageHandler(message = 1026)
        public void onLogin(boolean succ, String clientid, LoginErrCode errCode) {
            HomeActivity activity = (HomeActivity) this.akD.get();
            if (activity != null && succ) {
                activity.RU();
            }
            TopicModule.Ef().Eh();
            HTApplication.bR();
            com.huluxia.module.account.a.DU().DY();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bQJ = this;
        com.huluxia.controller.d.dJ();
        this.pl = new b(this);
        RT();
        sJ();
        kk(R.id.rly_home_tab);
        this.bQH.bb(1);
        this.bQH.a(this);
        this.bQL.bb(this.bQM);
        this.bQL.a(this);
        if (McApplication.bA() == ReStartSoftFlag.MC_RESTART_NORMAL.Value() && savedInstanceState == null) {
            this.Vo.sendMessageDelayed(this.Vo.obtainMessage(1), FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
        }
        this.bQI = new CountMsgReciver(this);
        i.b(this.bQI);
        this.aKb = new a();
        i.c(this.aKb);
        EventNotifyCenter.add(com.huluxia.login.e.class, this.pl);
        EventNotifyCenter.add(com.huluxia.module.h.class, this.aky);
        RY();
        c(getIntent());
    }

    private void RT() {
        this.aPB = MenuDrawer.a((Activity) this, Type.OVERLAY, Position.LEFT);
        this.aPB.setContentView((int) R.layout.aty_home);
        View menuView = LayoutInflater.from(this).inflate(R.layout.layout_home_menu_holder, null);
        this.bQE = (ListView) menuView.findViewById(R.id.lv_apply_guide);
        this.bQF = new MenuAdapter(this, hlx.data.home.menu.a.list);
        this.bQE.setAdapter(this.bQF);
        this.bQG = (CheckBox) menuView.findViewById(R.id.cb_night_mode);
        this.bQG.setChecked(!d.isDayMode());
        this.bQG.setOnCheckedChangeListener(this.bQN);
        this.aPB.setMenuView(menuView);
        this.aPB.setDropShadowSize(0);
        this.bQE.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ HomeActivity bQP;

            {
                this.bQP = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (hlx.data.home.menu.a.bIT.count > 0) {
                            hlx.data.home.menu.a.bIT.count = 0;
                            this.bQP.bQF.notifyDataSetChanged();
                            EventNotifyCenter.notifyEvent(n.class, n.axm, Integer.valueOf(0));
                        }
                        r.ck().K_umengEvent(hlx.data.tongji.a.bMg);
                        k.h(this.bQP.bQJ);
                        return;
                    case 2:
                        r.ck().K_umengEvent(hlx.data.tongji.a.bMh);
                        t.ar(this.bQP.bQJ);
                        return;
                    case 3:
                        r.ck().K_umengEvent(hlx.data.tongji.a.bMi);
                        t.f(this.bQP.bQJ, 0);
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void RU() {
        long userid = com.huluxia.data.j.ep().getUserid();
        if (userid > 0) {
            SimpleDateFormat _sdf = new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN);
            String _curDate = _sdf.format(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(5, -1);
            String _yesterday = _sdf.format(cal.getTime());
            com.huluxia.mctool.structure.a _ContunuousLogonDay = com.huluxia.mctool.e.Dk().Dl();
            if (_ContunuousLogonDay == null) {
                _ContunuousLogonDay = new com.huluxia.mctool.structure.a();
                _ContunuousLogonDay.pM = userid;
                _ContunuousLogonDay.apJ = 1;
                _ContunuousLogonDay.apH = _curDate;
                _ContunuousLogonDay.apI = _curDate;
            } else if (_ContunuousLogonDay.apI == null || !_ContunuousLogonDay.apI.equals(_curDate)) {
                if (_ContunuousLogonDay.apI == null || !_ContunuousLogonDay.apI.equals(_yesterday)) {
                    _ContunuousLogonDay.apJ = 1;
                    _ContunuousLogonDay.apH = _curDate;
                    _ContunuousLogonDay.apI = _curDate;
                } else {
                    _ContunuousLogonDay.apJ++;
                    _ContunuousLogonDay.apI = _curDate;
                }
            }
            com.huluxia.mctool.e.Dk().a(_ContunuousLogonDay);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.pl);
        EventNotifyCenter.remove(this.aky);
        if (this.bQI != null) {
            i.unregisterReceiver(this.bQI);
            this.bQI = null;
        }
        if (this.aKb != null) {
            i.unregisterReceiver(this.aKb);
            this.aKb = null;
        }
    }

    public void onBackPressed() {
        int drawerState = this.aPB.getDrawerState();
        if (drawerState == 8 || drawerState == 4) {
            this.aPB.Pv();
            return;
        }
        long current = SystemClock.elapsedRealtime();
        if (this.bQO == 0) {
            t.show_toast(this, "再按一次退出");
            this.bQO = current;
        } else if (current - this.bQO < FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY) {
            super.onBackPressed();
        } else {
            t.show_toast(this, "再按一次退出");
            this.bQO = current;
        }
    }

    private void RV() {
        if (com.huluxia.data.j.ep().ey()) {
            EventNotifyCenter.notifyEventUiThread(com.huluxia.login.e.class, 1026, Boolean.valueOf(true), "", LoginErrCode.AUTO_LOGIN_NONE);
            return;
        }
        CharSequence historyAccount = ah.KZ().pZ();
        CharSequence historyPwd = ah.KZ().getPassword();
        if (!UtilsFunction.empty(historyAccount) && !UtilsFunction.empty(historyPwd)) {
            com.huluxia.login.d.pR().aL(this);
        }
    }

    protected void RW() {
        if (!com.huluxia.g.bx()) {
            ax.a(this, MCStartActivity.class, R.string.app_name, R.drawable.app_icon);
        }
    }

    private void RX() {
        if (ax.MH() != Model.XIAOMI) {
            String key = u.ca("Baidu_Api_Key");
            if (key != null && key.length() == 0) {
            }
        }
    }

    public void a(com.huluxia.http.base.d response) {
    }

    public void b(com.huluxia.http.base.d response) {
    }

    protected void U(String szMessage, String szUrl) {
        if (!isFinishing()) {
            com.huluxia.widget.dialog.r dialog = new com.huluxia.widget.dialog.r(this, szMessage, szUrl, "mctool.apk");
        }
    }

    public void c(com.huluxia.http.base.d response) {
        if (response.getStatus() == 1) {
            com.huluxia.data.n verInfo = (com.huluxia.data.n) response.getData();
            if (verInfo == null || verInfo.getVersionCode() <= UtilsVersion.getVersionCode(this)) {
                if (response.fe() == 2) {
                    t.show_toast(this, "当前已是最新版本(" + UtilsVersion.getVersionString(this) + ")。");
                }
            } else if (response.fe() == 2) {
                U(verInfo.getMessage(), verInfo.getAddress());
                return;
            } else if (response.fe() == 1 && 1 == verInfo.ez()) {
                U(verInfo.getMessage(), verInfo.getAddress());
                return;
            }
            if (verInfo != null) {
                verInfo.a(McApplication.fM);
                EventNotifyCenter.notifyEvent(n.class, 521, new Object[0]);
            }
            if (this.bQM == response.fe() && this.bQL.getCount() > 0) {
                hlx.data.home.menu.a.bIT.count = this.bQL.getCount();
                EventNotifyCenter.notifyEvent(n.class, n.axm, Integer.valueOf(this.bQL.getCount()));
                this.bQF.notifyDataSetChanged();
            }
        }
    }

    private void sJ() {
        this.bQz = (CheckedTextView) findViewById(R.id.tv_home_tab);
        this.bQz.setOnClickListener(this.aEK);
        this.bQA = (CheckedTextView) findViewById(R.id.tv_community_tab);
        this.bQA.setOnClickListener(this.aEK);
        this.bQB = (CheckedTextView) findViewById(R.id.tv_news_tab);
        this.bQB.setOnClickListener(this.aEK);
        this.bQC = (CheckedTextView) findViewById(R.id.tv_space_tab);
        this.bQC.setOnClickListener(this.aEK);
        findViewById(R.id.iv_startgame).setOnClickListener(new hlx.utils.e(this) {
            final /* synthetic */ HomeActivity bQP;

            {
                this.bQP = this$0;
            }

            public void c(View v) {
                hlx.launch.game.d.bR(this.bQP);
                r.ck().K_umengEvent(hlx.data.tongji.a.bLQ);
            }
        });
        findViewById(R.id.rly_home_tab).setOnClickListener(this.aEK);
        findViewById(R.id.rly_community_tab).setOnClickListener(this.aEK);
        findViewById(R.id.rly_news_tab).setOnClickListener(this.aEK);
        findViewById(R.id.rly_space_tab).setOnClickListener(this.aEK);
    }

    protected void kk(int id) {
        if (id == R.id.rly_home_tab || id == R.id.tv_home_tab) {
            this.bQz.setChecked(true);
            this.bQA.setChecked(false);
            this.bQB.setChecked(false);
            this.bQC.setChecked(false);
            r.ck().K_umengEvent(hlx.data.tongji.a.bLO);
        } else if (id == R.id.rly_community_tab) {
            this.bQz.setChecked(false);
            this.bQA.setChecked(true);
            this.bQB.setChecked(false);
            this.bQC.setChecked(false);
            r.ck().K_umengEvent(hlx.data.tongji.a.bLP);
        } else if (id == R.id.rly_news_tab) {
            this.bQz.setChecked(false);
            this.bQA.setChecked(false);
            this.bQB.setChecked(true);
            this.bQC.setChecked(false);
            if (HTApplication.bN() > 0) {
                i.h(HTApplication.bN(), 0);
            }
            r.ck().K_umengEvent(hlx.data.tongji.a.bLS);
        } else if (id == R.id.rly_space_tab) {
            this.bQz.setChecked(false);
            this.bQA.setChecked(false);
            this.bQB.setChecked(false);
            this.bQC.setChecked(true);
            r.ck().K_umengEvent(hlx.data.tongji.a.bLT);
        }
    }

    private void RY() {
        this.aED = (SelectedViewPager) findViewById(R.id.ivp_home_content);
        this.aED.setAdapter(this.aEN);
        this.aED.setOnPageChangeListener(this.aEM);
        this.aED.setOffscreenPageLimit(3);
    }

    protected void RZ() {
        com.huluxia.http.message.a messageCountRequest = new com.huluxia.http.message.a();
        messageCountRequest.a(new f(this) {
            final /* synthetic */ HomeActivity bQP;

            {
                this.bQP = this$0;
            }

            public void a(com.huluxia.http.base.d response) {
            }

            public void b(com.huluxia.http.base.d response) {
            }

            public void c(com.huluxia.http.base.d response) {
                if (response.getStatus() == 1) {
                    MsgCounts msgCounts = (MsgCounts) response.getData();
                    long allCount = msgCounts == null ? 0 : msgCounts.getAll();
                    HLog.verbose("消息数量", Long.toString(allCount), new Object[0]);
                    if (allCount > 0) {
                        this.bQP.b(msgCounts);
                    }
                }
            }
        });
        messageCountRequest.execute();
    }

    protected void b(MsgCounts msgCounts) {
        if (msgCounts != null && 0 != msgCounts.getAll()) {
            HTApplication.a(msgCounts);
            com.huluxia.service.h.EC().a("消息提醒", String.format(Locale.getDefault(), "你有%d条新消息", new Object[]{Long.valueOf(msgCounts.getAll())}), msgCounts);
            i.EH();
        }
    }

    protected void onResume() {
        super.onResume();
        HlxPushService.az(HTApplication.getAppContext());
        if (com.huluxia.data.j.ep().ey()) {
            this.bQL.execute();
        }
    }

    protected void onStop() {
        super.onStop();
        int drawerState = this.aPB.getDrawerState();
        if (drawerState == 8 || drawerState == 4) {
            r.ck().K_umengEvent(hlx.data.tongji.a.bMf);
            this.aPB.Pv();
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        HLog.verbose(TAG, "onNewIntent happens", new Object[0]);
        Uri uri = intent.getData();
        c(intent);
    }

    private void c(Intent _intent) {
        String nextintent = _intent.getStringExtra("nextintent");
        if (nextintent != null) {
            this.bQK = nextintent;
            MsgCounts msgCounts = (MsgCounts) _intent.getSerializableExtra("msgCounts");
            if (!nextintent.equals("MessageHistoryActivity") || msgCounts == null) {
                long id = _intent.getLongExtra("id", 0);
                long resid = _intent.getLongExtra("resid", 0);
                int model = _intent.getIntExtra("model", 0);
                int type = _intent.getIntExtra("type", 0);
                int mcrestype = _intent.getIntExtra("mcrestype", 0);
                if (nextintent.equals("TopicDetailActivity")) {
                    Intent in = new Intent(HTApplication.getAppContext(), TopicDetailActivity.class);
                    in.putExtra(TopicDetailActivity.aOM, id);
                    in.putExtra(Constants.brP, true);
                    in.putExtra(Constants.brO, model);
                    startActivity(in);
                    r.ck().b(type, id, resid, mcrestype);
                    return;
                } else if (nextintent.equals("ResTopicDetailActivity")) {
                    t.b((Context) this, resid, id);
                    r.ck().b(type, id, resid, mcrestype);
                    return;
                } else {
                    return;
                }
            }
            t.a((Context) this, msgCounts);
        }
    }

    public String Sa() {
        return this.bQK;
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.bQG.setOnCheckedChangeListener(null);
        this.bQG.setChecked(!d.isDayMode());
        this.bQG.setOnCheckedChangeListener(this.bQN);
    }

    protected void a(com.simple.colorful.a.a builder) {
        builder.aY(R.id.bottom_tabs, R.attr.tab_bg).ba(R.id.tv_home_tab, R.attr.tab_text_color).ba(R.id.tv_news_tab, R.attr.tab_text_color).ba(R.id.tv_community_tab, R.attr.tab_text_color).ba(R.id.tv_space_tab, R.attr.tab_text_color).ab(R.id.tv_home_tab, R.attr.ico_tab_home, 0).ab(R.id.tv_news_tab, R.attr.ico_tab_news, 0).ab(R.id.tv_community_tab, R.attr.ico_tab_community, 0).ab(R.id.tv_space_tab, R.attr.ico_tab_space, 0).bc(R.id.iv_startgame, R.attr.ico_tab_startgame).aY(R.id.view_home_split, R.attr.home_split_line_bg);
    }

    private void Sb() {
        h.MU().MV();
    }

    protected void dw(boolean useNew) {
        String str = TAG;
        String str2 = "isNewUpdate %s";
        Object[] objArr = new Object[1];
        objArr[0] = useNew ? "true" : "false";
        HLog.verbose(str, str2, objArr);
        if (useNew) {
            String app_name = UtilsApkPackage.getAppPackageName(this.bQJ);
            String channel = UtilsApkPackage.getAppMetadata(this.bQJ, "UMENG_CHANNEL");
            if (channel == null) {
                channel = "mctool_huluxia";
            }
            h.MU().r(app_name, channel, TAG);
            return;
        }
        this.bQH.eX();
    }
}
