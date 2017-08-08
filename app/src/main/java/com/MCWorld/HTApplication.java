package com.MCWorld;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.m;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.data.d;
import com.MCWorld.data.e;
import com.MCWorld.data.j;
import com.MCWorld.data.message.MsgCounts;
import com.MCWorld.data.n.a;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.AppConstant;
import com.MCWorld.framework.BaseHttpMgr;
import com.MCWorld.framework.BaseHttpMgr.Config;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.IConfig;
import com.MCWorld.framework.base.crash.CrashMgr;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.utils.UtilsProc;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.http.other.b;
import com.MCWorld.http.other.c;
import com.MCWorld.login.LoginService;
import com.MCWorld.service.g;
import com.MCWorld.service.i;
import com.MCWorld.ui.base.BaseActivity;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.UtilsWifiDatabase;
import com.MCWorld.utils.ah;
import com.MCWorld.utils.ax;
import com.MCWorld.utils.o;
import com.MCWorld.utils.u;
import com.MCWorld.widget.Constants.DownFileType;
import com.MCWorld.widget.Constants.PushWay;
import com.MCWorld.widget.Constants.ReStartSoftFlag;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTApplication extends MultiDexApplication {
    public static boolean DEBUG = false;
    private static final String PRODUCT = "mctool";
    private static final String TAG = HTApplication.class.getSimpleName();
    protected static Context context;
    public static String fA = null;
    public static HashMap<String, String> fB = new HashMap();
    private static Map<Long, String> fC = new HashMap();
    private static int fE = 0;
    public static int fF;
    public static int fG;
    public static int fH;
    public static int fI;
    public static int fJ;
    public static int fK;
    public static int fL;
    public static a fM = new a();
    public static DownFileType fN = DownFileType.defaultType;
    public static boolean fO = false;
    private static ReStartSoftFlag fP = ReStartSoftFlag.MC_RESTART_NORMAL;
    private static ReStartSoftFlag fQ = ReStartSoftFlag.MC_RESTART_NORMAL;
    private static e fT = null;
    public static String fl = "hlx";
    public static String fm = "HlxMc.db";
    public static String fn = hlx.data.localstore.a.bLF;
    private static String fo_mctool_huluxia_string = "mctool_huluxia";
    public static int fp;
    private static d fq = new d(0, 0);
    private static int fr = 0;
    private static MsgCounts ft = null;
    private static long fu = 0;
    public static boolean fv = false;
    private static boolean fy = false;
    private static boolean fz = false;
    private g fD;
    private IConfig fR = new IConfig(this) {
        final /* synthetic */ HTApplication fU;

        {
            this.fU = this$0;
        }

        public Context getContext() {
            return this.fU;
        }

        public String getAppName() {
            return HTApplication.PRODUCT;
        }

        public String getRootDir() {
            return AppConstant.getHlxName() + File.separator + AppConstant.getAppName();
        }

        public String getLogDir() {
            return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.LOG;
        }

        public Supplier<Integer> placeHolder() {
            return new Supplier<Integer>(this) {
                final /* synthetic */ AnonymousClass1 fV;

                {
                    this.fV = this$1;
                }

                public /* synthetic */ Object get() {
                    return bY();
                }

                public Integer bY() {
                    return Integer.valueOf(com.simple.colorful.d.isDayMode() ? f.discover_pic : f.discover_pic_night);
                }
            };
        }

        public Supplier<Integer> errorHolder() {
            return new Supplier<Integer>(this) {
                final /* synthetic */ AnonymousClass1 fV;

                {
                    this.fV = this$1;
                }

                public /* synthetic */ Object get() {
                    return bY();
                }

                public Integer bY() {
                    return Integer.valueOf(com.simple.colorful.d.isDayMode() ? f.err_holder_normal : f.err_holder_night_normal);
                }
            };
        }

        public BaseHttpMgr getHttp() {
            com.MCWorld.login.f.pT().init(this.fU, new Config(false));
            return com.MCWorld.login.f.pT();
        }

        public Supplier<Integer> getBrightness() {
            return null;
        }
    };
    private IConfig fS = new IConfig(this) {
        final /* synthetic */ HTApplication fU;

        {
            this.fU = this$0;
        }

        public Context getContext() {
            return this.fU;
        }

        public String getAppName() {
            return HTApplication.PRODUCT;
        }

        public String getRootDir() {
            return AppConstant.getHlxName() + File.separator + AppConstant.getAppName();
        }

        public String getLogDir() {
            return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.LOG;
        }

        public Supplier<Integer> placeHolder() {
            return new Supplier<Integer>(this) {
                final /* synthetic */ AnonymousClass2 fW;

                {
                    this.fW = this$1;
                }

                public /* synthetic */ Object get() {
                    return bY();
                }

                public Integer bY() {
                    return Integer.valueOf(com.simple.colorful.d.isDayMode() ? f.discover_pic : f.discover_pic_night);
                }
            };
        }

        public Supplier<Integer> errorHolder() {
            return new Supplier<Integer>(this) {
                final /* synthetic */ AnonymousClass2 fW;

                {
                    this.fW = this$1;
                }

                public /* synthetic */ Object get() {
                    return bY();
                }

                public Integer bY() {
                    return Integer.valueOf(com.simple.colorful.d.isDayMode() ? f.err_holder_normal : f.err_holder_night_normal);
                }
            };
        }

        public BaseHttpMgr getHttp() {
            HttpMgr.getInstance().init(this.fU);
            return HttpMgr.getInstance();
        }

        public Supplier<Integer> getBrightness() {
            return new Supplier<Integer>(this) {
                final /* synthetic */ AnonymousClass2 fW;

                {
                    this.fW = this$1;
                }

                public /* synthetic */ Object get() {
                    return bY();
                }

                public Integer bY() {
                    return Integer.valueOf(HTBaseThemeActivity.brightness);
                }
            };
        }
    };
    protected BroadcastReceiver fw;
    protected BroadcastReceiver fx;

    protected class BindDeviceReciver extends BroadcastReceiver {
        final /* synthetic */ HTApplication fU;

        protected BindDeviceReciver(HTApplication this$0) {
            this.fU = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            String cloudUserID = intent.getStringExtra("cloud_user_id");
            int model = intent.getIntExtra("model", 0);
            HLog.info(HTApplication.TAG, "BindDeviceReciver %d, %s, %s", Integer.valueOf(model), cloudUserID, o.getDeviceId());
            if (cloudUserID != null && model != 0) {
                g.EA().ee(cloudUserID);
                if (!g.EA().ef(cloudUserID) && !g.EA().EB()) {
                    g.EA().cm(true);
                    b bind = new b();
                    bind.ay(cloudUserID);
                    bind.bh(model);
                    bind.bi(g.fe);
                    bind.eY();
                }
            }
        }
    }

    protected class CheckPushReciver extends BroadcastReceiver {
        final /* synthetic */ HTApplication fU;

        protected CheckPushReciver(HTApplication this$0) {
            this.fU = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            String cloudId = g.EA().Ex();
            boolean isDeviceBind = g.EA().Ey();
            boolean isUserBind = g.EA().Ez();
            PushWay model = ax.ME();
            String access$000 = HTApplication.TAG;
            String str = "CheckPushReciver pushway %d, cloudId %s, device %s, user %s";
            Object[] objArr = new Object[4];
            objArr[0] = Integer.valueOf(model.Value());
            objArr[1] = cloudId;
            objArr[2] = isDeviceBind ? "true" : "false";
            objArr[3] = isUserBind ? "true" : "false";
            HLog.info(access$000, str, objArr);
            if (cloudId == null) {
                this.fU.bP();
                com.MCWorld.module.account.a.DU().DY();
            } else if (!isDeviceBind) {
                b bind = new b();
                bind.ay(cloudId);
                bind.bh(model.Value());
                bind.bi(g.fe);
                bind.eY();
                com.MCWorld.module.account.a.DU().DY();
            } else if (!isUserBind) {
                HTApplication.bR();
                com.MCWorld.module.account.a.DU().DY();
            }
        }
    }

    public static int bA() {
        return fP.Value();
    }

    public static void j(int flag) {
        if (flag == ReStartSoftFlag.MC_RESTART_V0105.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_V0105;
        } else if (flag == ReStartSoftFlag.MC_RESTART_V0111.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_V0111;
        } else if (flag == ReStartSoftFlag.MC_RESTART_BLACK.Value()) {
            fP = ReStartSoftFlag.MC_RESTART_BLACK;
        } else {
            fP = ReStartSoftFlag.MC_RESTART_NORMAL;
        }
    }

    public static int bB() {
        return fQ.Value();
    }

    public static void k(int flag) {
        if (flag == ReStartSoftFlag.MC_RESTART_V0105.Value()) {
            fQ = ReStartSoftFlag.MC_RESTART_V0105;
        } else if (flag == ReStartSoftFlag.MC_RESTART_V0111.Value()) {
            fQ = ReStartSoftFlag.MC_RESTART_V0111;
        } else if (flag == ReStartSoftFlag.MC_RESTART_BLACK.Value()) {
            fQ = ReStartSoftFlag.MC_RESTART_BLACK;
        } else {
            fQ = ReStartSoftFlag.MC_RESTART_NORMAL;
        }
    }

    public static void bC() {
        if (ax.ME() != PushWay.XIAOMI) {
        }
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        g.M(context);
        if (UtilsProc.getAppNameByPid(this, Process.myPid()).equals(context.getResources().getString(m.login_process_name))) {
            bD();
            f.init();
            return;
        }
        if (bS()) {
            u.aN(context);
            bE();
            fp = f.app_icon;
            fT = ah.KZ().bI();
            if (fT == null) {
                fT = new e();
            }
            UtilsFile.Kx();
            bH();
            ResourceCtrl.getInstance();
            bG();
            bF();
            UtilsWifiDatabase.by(context);
            r.ck().ad(context);
            this.fw = new BindDeviceReciver(this);
            this.fx = new CheckPushReciver(this);
            i.m(this.fw);
            i.s(this.fx);
            com.MCWorld.login.d.pR().aK(this);
            f.init();
            this.fD = g.EA();
            bP();
            TbsDownloader.needDownload(getApplicationContext(), false);
            bW();
        } else {
            u.aN(context);
            bE();
            this.fw = new BindDeviceReciver(this);
            bP();
        }
        com.MCWorld.aa.d.dD().a(this);
    }

    private void bD() {
        HLog.info(TAG, "------------init login proc---------------", new Object[0]);
        u.aN(context);
        bE();
        UtilsFile.Kx();
        AppConfig.getInstance().setAppContext(this.fR);
        r.ck().ad(context);
        LoginService.fo = fo_mctool_huluxia_string;
        CrashMgr.getInstance().init(AppConstant.CRASH_FILE).setListener(new e());
    }

    private void bE() {
        String channel = u.ca("UMENG_CHANNEL");
        if (ah.KZ().LC() != null) {
            channel = ah.KZ().LC();
        }
        ah.KZ().fY(channel);
        fo_mctool_huluxia_string = channel;
    }

    public void onTerminate() {
        super.onTerminate();
        if (this.fw != null) {
            i.unregisterReceiver(this.fw);
            this.fw = null;
        }
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (!UtilsProc.isMainProcess(this)) {
            return;
        }
        if (UtilsVersion.hasICS()) {
            if (level >= 20) {
                HLog.info(TAG, "*****onTrimMemory level " + level + ", free " + Runtime.getRuntime().freeMemory() + ", total " + Runtime.getRuntime().totalMemory() + "*****", new Object[0]);
                l.cb().onLowMemory();
            }
        } else if (level >= 20) {
            HLog.info(TAG, "*****onTrimMemory level " + level + ", free " + Runtime.getRuntime().freeMemory() + ", total " + Runtime.getRuntime().totalMemory() + "*****", new Object[0]);
            l.cb().onLowMemory();
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (UtilsProc.isMainProcess(this)) {
            HLog.info(TAG, "*****onLowMemory level free " + Runtime.getRuntime().freeMemory() + ", total " + Runtime.getRuntime().totalMemory() + "*****", new Object[0]);
            l.cb().onLowMemory();
        }
    }

    private void bF() {
        com.MCWorld.db.b.init(fm);
    }

    private void bG() {
        DownloadMemCache.getInstance();
        com.MCWorld.db.f.eO();
        com.MCWorld.controller.resource.handler.a.dO();
    }

    private void bH() {
        AppConfig.getInstance().setAppContext(this.fS);
        l.a(m.ac(this).D(AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_IMAGE_CACHE).cf());
        com.MCWorld.data.f.ek();
        CrashMgr.getInstance().init(AppConstant.CRASH_FILE).setListener(new e());
    }

    public static Context getAppContext() {
        return context;
    }

    public static e bI() {
        return fT;
    }

    public static String bJ_mctool_huluxia_string() {
        return fo_mctool_huluxia_string;
    }

    public static void z(String marketID) {
        fo_mctool_huluxia_string = marketID;
    }

    public static d bK() {
        if (fq == null) {
            fq = new d(0, 0);
        }
        return fq;
    }

    public static void a(d downloadCounts) {
        if (fq == null) {
            fq = downloadCounts;
            return;
        }
        fq.J(downloadCounts.ed());
        fq.K(downloadCounts.ee());
    }

    public static int bL() {
        return fr;
    }

    public static void l(int specIndex) {
        fr = specIndex;
    }

    public static MsgCounts bM() {
        return ft;
    }

    public static void a(MsgCounts msgCounts) {
        ft = msgCounts;
    }

    public static long bN() {
        return fu;
    }

    public static void i(long classMsgCount) {
        fu = classMsgCount;
    }

    public static int bO() {
        return fE;
    }

    public static void m(int count) {
        fE = count;
    }

    private void bP() {
        if (ax.ME() == PushWay.HUAWEI) {
            HLog.info(TAG, "init huawei push....", new Object[0]);
            com.MCWorld.hwpush.a.fY().init(this);
            return;
        }
        HLog.info(TAG, "init xiaomi push....", new Object[0]);
        com.MCWorld.mipush.a.Dw().init(this, "2882303761517316250", "5531731657250");
    }

    private boolean bQ() {
        List<RunningAppProcessInfo> processInfos = ((ActivityManager) getSystemService("activity")).getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static void bR() {
        if (j.ep().ey()) {
            c bind = new c();
            bind.ba(o.getDeviceId());
            bind.bi(g.fe);
            bind.eY();
        }
    }

    protected boolean bS() {
        ActivityManager am = (ActivityManager) getSystemService("activity");
        int myPid = Process.myPid();
        String mainProcessName = getPackageName();
        for (RunningAppProcessInfo info : am.getRunningAppProcesses()) {
            HLog.debug(TAG, "main %s, process name %s", mainProcessName, info.processName);
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean bT() {
        return fy;
    }

    public static void v(boolean bStartGame) {
        fy = bStartGame;
    }

    public static boolean bU() {
        return BaseActivity.bU();
    }

    public static String bV() {
        return fA;
    }

    public static void A(String shareUrl) {
        fA = shareUrl;
    }

    private void bW() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(context, null);
        }
    }

    public static void b(long cate, String name) {
        fC.put(Long.valueOf(cate), name);
    }

    public static Map<Long, String> bX() {
        return fC;
    }
}
