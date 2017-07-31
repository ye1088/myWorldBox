package hlx.launch.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.controller.download.handler.impl.DownloadHandler;
import com.huluxia.controller.resource.ResourceCtrl;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.data.map.h;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.R;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.async.AsyncTaskCenter.RunnableCallback;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.framework.base.widget.dialog.DialogManager.StyleHolder;
import com.huluxia.module.n;
import com.huluxia.r;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.u;
import com.huluxia.widget.Constants;
import com.huluxia.widget.Constants.ReStartSoftFlag;
import com.huluxia.widget.dialog.j;
import com.huluxia.widget.dialog.k;
import hlx.launch.game.MCLauncherActivity105;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

public class MCVersionSelect extends HTBaseActivity {
    private static final boolean DEBUG = false;
    private static final String TAG = "MCVersionSelect";
    private static final int bRA = 0;
    private static final int bRB = 1;
    private static final int bRC = 2;
    private static final int bRD = 3;
    private static final int bRE = 4;
    private static final int bRF = 1;
    private static final int bRG = 2;
    private static final int bRH = 3;
    private static final int bRI = 4;
    private static final int bRJ = 5;
    private static final int bRK = 6;
    private static final int bRp = 1;
    private static final int bRq = 2;
    private static final int bRr = 3;
    private static final int bRs = 4;
    private static final int bRt = 5;
    private static final int bRu = 13;
    private static final int bRv = 14;
    private static final int bRw = 15;
    private static final int bRx = 16;
    private static final int bRy = 17;
    private static final int bRz = 18;
    private static final String bSd = "http://wap.mc.huluxia.com/wap/mc/info/25.html";
    private static final String bSe = "http://wap.mc.huluxia.com/wap/mc/info/26.html";
    private static final String bSf = "http://wap.mc.huluxia.com/wap/mc/info/24.html";
    private static int bTf = 0;
    private static int bTg = 0;
    private static int bTh = 0;
    protected Handler Vo;
    private BroadcastReceiver aDe = new e();
    private MCVersionSelect bRL;
    private Button bRM;
    private Button bRN;
    private Button bRO;
    private Button bRP;
    private Button bRQ;
    private Button bRR;
    private Button bRS;
    private Button bRT;
    private Button bRU;
    private Button bRV;
    private int bRW;
    private String bRX;
    private boolean bRY;
    private boolean bRZ;
    private boolean bSA;
    private boolean bSB;
    private boolean bSC;
    private boolean bSD;
    private boolean bSE;
    private boolean bSF;
    private int bSG;
    private int bSH;
    private Button bSI;
    private Button bSJ;
    private Button bSK;
    private Button bSL;
    private Button bSM;
    private Button bSN;
    private Button bSO;
    HashMap<Integer, RelativeLayout> bSP = new HashMap();
    private RelativeLayout bSQ;
    private RelativeLayout bSR;
    private RelativeLayout bSS;
    private RelativeLayout bST;
    private RelativeLayout bSU;
    private RelativeLayout bSV;
    private RelativeLayout bSW;
    private RelativeLayout bSX;
    private int bSY = -1;
    private UninstallReceiver bSZ;
    private boolean bSa;
    private boolean bSb;
    private boolean bSc;
    private k bSg;
    private int bSh = -1;
    private Button bSi;
    private Button bSj;
    private Button bSk;
    private Button bSl;
    private Button bSm;
    private Button bSn;
    private Button bSo;
    private Button bSp;
    private Button bSq;
    private Button bSr;
    private Button bSs;
    private Button bSt;
    private Button bSu;
    private Button bSv;
    private Button bSw;
    private Button bSx;
    private Button bSy;
    private Button bSz;
    OnClickListener bTa = new OnClickListener(this) {
        final /* synthetic */ MCVersionSelect bTk;

        {
            this.bTk = this$0;
        }

        public void onClick(View v) {
            HLog.verbose("MCVersionSelect.onClick", "onClick", new Object[0]);
            if (this.bTk.bSP.containsKey(Integer.valueOf(v.getId()))) {
                HLog.verbose("MCVersionSelect.onClick", "iterator", new Object[0]);
                for (Entry<Integer, RelativeLayout> next : this.bTk.bSP.entrySet()) {
                    if (v.getId() == ((Integer) next.getKey()).intValue()) {
                        ((RelativeLayout) next.getValue()).getChildAt(0).setVisibility(0);
                        ((RelativeLayout) next.getValue()).setClickable(false);
                        this.bTk.a((RelativeLayout) next.getValue(), true);
                        this.bTk.M(v);
                        this.bTk.SH();
                    } else {
                        this.bTk.a((RelativeLayout) next.getValue(), false);
                        ((RelativeLayout) next.getValue()).setClickable(true);
                    }
                }
            }
        }
    };
    private CheckLocalDataAsnycTask bTb;
    private OnClickListener bTc = new OnClickListener(this) {
        final /* synthetic */ MCVersionSelect bTk;

        {
            this.bTk = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnclearGameVersion0131:
                    this.bTk.bSh = 4;
                    this.bTk.bT(this.bTk.bRL);
                    return;
                case R.id.btnclearGameVersion0130:
                    this.bTk.bSh = 3;
                    this.bTk.bT(this.bTk.bRL);
                    return;
                case R.id.btnclearGameVersion0121:
                    this.bTk.bSh = 2;
                    this.bTk.bT(this.bTk.bRL);
                    return;
                case R.id.btnclearGameVersion0111:
                    this.bTk.bSh = 1;
                    this.bTk.bT(this.bTk.bRL);
                    return;
                case R.id.btnclearGameVersion0105:
                    this.bTk.bSh = 0;
                    this.bTk.bT(this.bTk.bRL);
                    return;
                default:
                    return;
            }
        }
    };
    private OnClickListener bTd = new OnClickListener(this) {
        final /* synthetic */ MCVersionSelect bTk;

        {
            this.bTk = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDownMoreV0141:
                    this.bTk.mQ(4);
                    return;
                case R.id.btnInstallMoreV0141:
                    this.bTk.mR(4);
                    hlx.recorddata.a.dH(true);
                    return;
                case R.id.btnSelectGameVersion0141:
                    this.bTk.SP();
                    return;
                case R.id.btnClearMoreV0141:
                    this.bTk.mS(4);
                    return;
                case R.id.btnDownMoreV0159:
                    this.bTk.mQ(6);
                    return;
                case R.id.btnInstallMoreV0159:
                    this.bTk.mR(6);
                    hlx.recorddata.a.dF(true);
                    return;
                case R.id.btnSelectGameVersion0159:
                    this.bTk.SN();
                    return;
                case R.id.btnClearMoreV0159:
                    this.bTk.mS(6);
                    return;
                case R.id.btnDownMoreV0150:
                    this.bTk.mQ(5);
                    return;
                case R.id.btnInstallMoreV0150:
                    this.bTk.mR(5);
                    hlx.recorddata.a.dG(true);
                    return;
                case R.id.btnSelectGameVersion0150:
                    this.bTk.SO();
                    return;
                case R.id.btnClearMoreV0150:
                    this.bTk.mS(5);
                    return;
                case R.id.btnDownMoreFirearms0130:
                    this.bTk.mQ(2);
                    return;
                case R.id.btnInstallMoreFirearms0130:
                    this.bTk.mR(2);
                    return;
                case R.id.btnClearMoreFirearms0130:
                    this.bTk.mS(2);
                    return;
                case R.id.btnDownMoreFirearms0121:
                    this.bTk.mQ(1);
                    return;
                case R.id.btnInstallMoreFirearms0121:
                    this.bTk.mR(1);
                    return;
                case R.id.btnClearMoreFirearms0121:
                    this.bTk.mS(1);
                    return;
                default:
                    return;
            }
        }
    };
    private Runnable bTe = new Runnable(this) {
        final /* synthetic */ MCVersionSelect bTk;

        {
            this.bTk = this$0;
        }

        public void run() {
            try {
                this.bTk.SS();
            } catch (Exception e) {
            }
        }
    };
    private a bTi;
    private d bTj;
    private boolean beM;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ MCVersionSelect bTk;

        {
            this.bTk = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDownGameVersion0131:
                    this.bTk.bRW = 4;
                    this.bTk.bRX = hlx.data.localstore.a.bJm;
                    if (this.bTk.bSc) {
                        this.bTk.SH();
                        return;
                    } else {
                        this.bTk.SJ();
                        return;
                    }
                case R.id.btnDownGameVersion0130:
                    this.bTk.bRW = 3;
                    this.bTk.bRX = hlx.data.localstore.a.bJl;
                    if (this.bTk.bSb) {
                        this.bTk.SH();
                        return;
                    } else {
                        this.bTk.SJ();
                        return;
                    }
                case R.id.btnDownGameVersion0121:
                    this.bTk.bRW = 2;
                    this.bTk.bRX = VERSION.SDK_INT < 11 ? hlx.data.localstore.a.bJj : hlx.data.localstore.a.bJk;
                    if (this.bTk.bSa) {
                        this.bTk.SH();
                        return;
                    } else {
                        this.bTk.SJ();
                        return;
                    }
                case R.id.btnDownGameVersion0111:
                    this.bTk.bRW = 1;
                    this.bTk.bRX = hlx.data.localstore.a.bJi;
                    if (this.bTk.bRZ) {
                        this.bTk.SH();
                        return;
                    } else {
                        this.bTk.SJ();
                        return;
                    }
                case R.id.btnDownGameVersion0105:
                    this.bTk.bRW = 0;
                    this.bTk.bRX = hlx.data.localstore.a.bJh;
                    if (this.bTk.bRY) {
                        this.bTk.SH();
                        return;
                    } else {
                        this.bTk.SJ();
                        return;
                    }
                default:
                    return;
            }
        }
    };
    int mRequestCode = 0;

    public class CheckLocalDataAsnycTask extends AsyncTask<String, Integer, String> {
        final /* synthetic */ MCVersionSelect bTk;

        public CheckLocalDataAsnycTask(MCVersionSelect this$0) {
            this.bTk = this$0;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            this.bTk.bSg.show();
        }

        protected String c(String... params) {
            String retVal = "success";
            if (VERSION.SDK_INT >= 11) {
                this.bTk.bSD = this.bTk.mO(4);
                this.bTk.Vo.sendEmptyMessage(16);
            }
            if (VERSION.SDK_INT >= 11) {
                this.bTk.bSE = this.bTk.mO(5);
                this.bTk.Vo.sendEmptyMessage(17);
            }
            if (VERSION.SDK_INT >= 19) {
                this.bTk.bSF = this.bTk.mO(6);
                this.bTk.Vo.sendEmptyMessage(18);
            }
            if (!UtilsVersion.hasMarshmallow()) {
                if (VERSION.SDK_INT >= 11) {
                    this.bTk.bSc = this.bTk.mK(4);
                    this.bTk.Vo.sendEmptyMessage(5);
                } else {
                    this.bTk.bSb = this.bTk.mK(3);
                    this.bTk.Vo.sendEmptyMessage(4);
                }
                this.bTk.bSa = this.bTk.mK(2);
                this.bTk.Vo.sendEmptyMessage(3);
                this.bTk.bRZ = this.bTk.mK(1);
                this.bTk.Vo.sendEmptyMessage(2);
                this.bTk.bRY = this.bTk.mK(0);
                this.bTk.Vo.sendEmptyMessage(1);
                this.bTk.bSA = this.bTk.mO(1);
                this.bTk.Vo.sendEmptyMessage(13);
                this.bTk.bSB = this.bTk.mO(2);
                this.bTk.Vo.sendEmptyMessage(14);
            }
            return retVal;
        }

        protected void ce(String result) {
            if (this.bTk.bSg != null && this.bTk.bSg.isShowing()) {
                this.bTk.bSg.cancel();
            }
            this.bTk.SF();
        }
    }

    public class UninstallReceiver extends BroadcastReceiver {
        final /* synthetic */ MCVersionSelect bTk;

        public UninstallReceiver(MCVersionSelect this$0) {
            this.bTk = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getData() != null && "android.intent.action.PACKAGE_REMOVED".equals(intent.getAction()) && "com.mojang.minecraftpe".equals(intent.getData().getSchemeSpecificPart())) {
                if (this.bTk.bSY == 4) {
                    this.bTk.bSY = -1;
                    this.bTk.a(this.bTk.bSQ, 5);
                    UtilsFile.deleteFile(UtilsFile.getRootPath() + hlx.data.localstore.a.bKX + ".apk");
                    this.bTk.a(this.bTk.bSt, this.bTk.bSv, this.bTk.bSu, this.bTk.bSs);
                } else if (this.bTk.bSY == 5) {
                    this.bTk.bSY = -1;
                    this.bTk.a(this.bTk.bSR, 7);
                    UtilsFile.deleteFile(UtilsFile.getRootPath() + hlx.data.localstore.a.bKY + ".apk");
                    this.bTk.a(this.bTk.bSp, this.bTk.bSo, this.bTk.bSr, this.bTk.bSq);
                } else if (this.bTk.bSY == 6) {
                    this.bTk.bSY = -1;
                    this.bTk.a(this.bTk.bSR, 8);
                    UtilsFile.deleteFile(UtilsFile.getRootPath() + hlx.data.localstore.a.bKZ + ".apk");
                    this.bTk.a(this.bTk.bSx, this.bTk.bSw, this.bTk.bSz, this.bTk.bSy);
                }
            }
        }
    }

    static class a extends CallbackHandler {
        WeakReference<MCVersionSelect> bTo;

        public a(MCVersionSelect activity) {
            this.bTo = new WeakReference(activity);
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            MCVersionSelect self = (MCVersionSelect) this.bTo.get();
            if (self != null) {
                self.he(url);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            MCVersionSelect self = (MCVersionSelect) this.bTo.get();
            if (self != null) {
                self.hc(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            MCVersionSelect self = (MCVersionSelect) this.bTo.get();
            if (self != null) {
                t.l(self, "下载出错");
                self.hd(url);
            }
        }
    }

    static class b extends Handler {
        WeakReference<MCVersionSelect> akD;

        b(MCVersionSelect activity) {
            this.akD = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            MCVersionSelect activity = (MCVersionSelect) this.akD.get();
            if (activity != null && !activity.isFinishing()) {
                switch (msg.what) {
                    case 1:
                        if (activity.bRY) {
                            activity.mM(0);
                            activity.a(activity.bRR, activity.bSO, activity.bRM, null);
                            return;
                        }
                        activity.a(activity.bSO, activity.bRR, activity.bRM, null);
                        return;
                    case 2:
                        if (activity.bRZ) {
                            activity.mM(1);
                            activity.a(activity.bRS, activity.bSN, activity.bRN, null);
                            return;
                        }
                        activity.a(activity.bSN, activity.bRS, activity.bRN, null);
                        return;
                    case 3:
                        if (activity.bSa) {
                            activity.mM(2);
                            activity.a(activity.bRT, activity.bSM, activity.bRO, null);
                            return;
                        }
                        activity.a(activity.bSM, activity.bRT, activity.bRO, null);
                        return;
                    case 4:
                        if (activity.bSb) {
                            activity.mM(3);
                            activity.a(activity.bRU, activity.bSL, activity.bRP, null);
                            return;
                        }
                        activity.a(activity.bSL, activity.bRU, activity.bRP, null);
                        return;
                    case 5:
                        if (activity.bSc) {
                            activity.mM(4);
                            activity.a(activity.bRV, activity.bSK, activity.bRQ, null);
                            return;
                        }
                        activity.a(activity.bSK, activity.bRV, activity.bRQ, null);
                        return;
                    case 13:
                        if (!activity.bSA) {
                            activity.a(activity.bSj, activity.bSi, activity.bSk, activity.bSJ);
                            return;
                        } else if (activity.mP(1)) {
                            activity.a(activity.bSk, activity.bSj, activity.bSi, activity.bSJ);
                            return;
                        } else {
                            activity.a(activity.bSi, activity.bSj, activity.bSk, activity.bSJ);
                            return;
                        }
                    case 14:
                        if (!activity.bSB) {
                            activity.a(activity.bSm, activity.bSn, activity.bSl, activity.bSI);
                            return;
                        } else if (activity.mP(2)) {
                            activity.a(activity.bSn, activity.bSm, activity.bSl, activity.bSI);
                            return;
                        } else {
                            activity.a(activity.bSl, activity.bSm, activity.bSn, activity.bSI);
                            return;
                        }
                    case 16:
                        if (!activity.bSD) {
                            activity.a(activity.bSt, activity.bSv, activity.bSs, activity.bSu);
                            return;
                        } else if (activity.mP(4)) {
                            activity.mM(5);
                            activity.a(activity.bSu, activity.bSv, activity.bSt, activity.bSs);
                            return;
                        } else {
                            activity.a(activity.bSs, activity.bSv, activity.bSt, activity.bSu);
                            return;
                        }
                    case 17:
                        if (activity.bSE) {
                            if (!activity.mP(5)) {
                                activity.a(activity.bSo, activity.bSq, activity.bSr, activity.bSp);
                                break;
                            }
                            activity.mM(7);
                            activity.a(activity.bSq, activity.bSo, activity.bSr, activity.bSp);
                            break;
                        }
                        activity.a(activity.bSp, activity.bSq, activity.bSr, activity.bSo);
                        break;
                    case 18:
                        break;
                    default:
                        return;
                }
                if (!activity.bSF) {
                    activity.a(activity.bSx, activity.bSy, activity.bSz, activity.bSw);
                } else if (activity.mP(6)) {
                    activity.mM(8);
                    activity.a(activity.bSy, activity.bSw, activity.bSz, activity.bSx);
                } else {
                    activity.a(activity.bSw, activity.bSy, activity.bSz, activity.bSx);
                }
            }
        }
    }

    private class c implements com.huluxia.widget.dialog.j.a {
        final /* synthetic */ MCVersionSelect bTk;

        private c(MCVersionSelect mCVersionSelect) {
            this.bTk = mCVersionSelect;
        }

        public void kz(int position) {
            if (position == 0) {
                r.ck().K(hlx.data.tongji.a.bNn);
                if (VERSION.SDK_INT < 11) {
                    com.huluxia.k.e(this.bTk.bRL, MCVersionSelect.bSd, hlx.data.localstore.a.bKL);
                } else {
                    com.huluxia.k.e(this.bTk.bRL, MCVersionSelect.bSe, hlx.data.localstore.a.bKL);
                }
            } else if (position == 1) {
                r.ck().K(hlx.data.tongji.a.bNo);
                com.huluxia.k.e(this.bTk.bRL, MCVersionSelect.bSf, hlx.data.localstore.a.bKM);
            }
        }
    }

    static class d extends CallbackHandler {
        WeakReference<MCVersionSelect> bTo;

        public d(MCVersionSelect activity) {
            this.bTo = new WeakReference(activity);
        }

        @MessageHandler(message = 2825)
        public void onVersionUpdate() {
            MCVersionSelect self = (MCVersionSelect) this.bTo.get();
            if (self != null) {
                String strVersionName = hlx.launch.game.c.Sg().dx(false);
                if (!strVersionName.equals(hlx.data.localstore.a.bJg)) {
                    t.l(self, "已选择" + strVersionName + "版本");
                }
            }
        }
    }

    private class e extends BroadcastReceiver {
        final /* synthetic */ MCVersionSelect bTk;

        private e(MCVersionSelect mCVersionSelect) {
            this.bTk = mCVersionSelect;
        }

        public void onReceive(Context context, Intent intent) {
            String taskId = intent.getStringExtra("taskid");
            int status = intent.getIntExtra("success", 0);
            if (taskId != null && status == 1 && this.bTk.bRL != null && !this.bTk.bRL.isFinishing()) {
                if (taskId.equals("unzipMoreResFirearms")) {
                    this.bTk.a(this.bTk.bSk, this.bTk.bSj, this.bTk.bSi, this.bTk.bSJ);
                } else if (taskId.equals("unzipMoreResFirearms13")) {
                    this.bTk.a(this.bTk.bSn, this.bTk.bSm, this.bTk.bSl, this.bTk.bSI);
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.aty_version_select);
        ej(hlx.data.localstore.a.bKN);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme(com.umeng.analytics.onlineconfig.a.b);
        this.bSZ = new UninstallReceiver(this);
        registerReceiver(this.bSZ, filter);
        this.bRL = this;
        this.Vo = new b(this);
        this.aIs.setVisibility(8);
        this.bSg = new k(this.bRL);
        this.bSg.gM(this.bRL.getString(R.string.onloading));
        sJ();
        SD();
        this.beM = com.simple.colorful.d.isDayMode();
        SK();
        SL();
        this.bTi = new a(this.bRL);
        EventNotifyCenter.add(BaseEvent.class, this.bTi);
        this.bTj = new d(this.bRL);
        EventNotifyCenter.add(n.class, this.bTj);
        ResourceCtrl.getInstance().registerHandler(1000006, DownloadHandler.class);
        SE();
        SC();
        SB();
        SA();
    }

    private void SA() {
        this.bSQ = (RelativeLayout) findViewById(R.id.rlyMoreV0141);
        this.bSR = (RelativeLayout) findViewById(R.id.rlyMoreV0150);
        this.bSS = (RelativeLayout) findViewById(R.id.rlyMoreV0159);
        this.bST = (RelativeLayout) findViewById(R.id.ly_GameVersion0131);
        this.bSU = (RelativeLayout) findViewById(R.id.ly_GameVersion0130);
        this.bSV = (RelativeLayout) findViewById(R.id.ly_GameVersion0121);
        this.bSW = (RelativeLayout) findViewById(R.id.ly_GameVersion0111);
        this.bSX = (RelativeLayout) findViewById(R.id.ly_GameVersion0105);
        this.bSQ.setOnClickListener(this.bTa);
        this.bSR.setOnClickListener(this.bTa);
        this.bSS.setOnClickListener(this.bTa);
        this.bST.setOnClickListener(this.bTa);
        this.bSU.setOnClickListener(this.bTa);
        this.bSV.setOnClickListener(this.bTa);
        this.bSW.setOnClickListener(this.bTa);
        this.bSX.setOnClickListener(this.bTa);
    }

    private void mM(int version) {
        switch (version) {
            case 0:
                this.bSP.put(Integer.valueOf(R.id.ly_GameVersion0105), this.bSX);
                return;
            case 1:
                this.bSP.put(Integer.valueOf(R.id.ly_GameVersion0111), this.bSW);
                return;
            case 2:
                this.bSP.put(Integer.valueOf(R.id.ly_GameVersion0121), this.bSV);
                return;
            case 3:
                this.bSP.put(Integer.valueOf(R.id.ly_GameVersion0130), this.bSU);
                return;
            case 4:
                this.bSP.put(Integer.valueOf(R.id.ly_GameVersion0131), this.bST);
                return;
            case 5:
                this.bSP.put(Integer.valueOf(R.id.rlyMoreV0141), this.bSQ);
                return;
            case 7:
                this.bSP.put(Integer.valueOf(R.id.rlyMoreV0150), this.bSR);
                return;
            case 8:
                this.bSP.put(Integer.valueOf(R.id.rlyMoreV0159), this.bSS);
                return;
            default:
                return;
        }
    }

    private void mN(int version) {
        switch (version) {
            case 0:
                this.bSP.remove(Integer.valueOf(R.id.ly_GameVersion0105));
                a(this.bSX, false);
                return;
            case 1:
                this.bSP.remove(Integer.valueOf(R.id.ly_GameVersion0111));
                a(this.bSW, false);
                return;
            case 2:
                this.bSP.remove(Integer.valueOf(R.id.ly_GameVersion0121));
                a(this.bSV, false);
                return;
            case 3:
                this.bSP.remove(Integer.valueOf(R.id.ly_GameVersion0130));
                a(this.bSU, false);
                return;
            case 4:
                this.bSP.remove(Integer.valueOf(R.id.ly_GameVersion0131));
                a(this.bST, false);
                return;
            case 5:
                this.bSP.remove(Integer.valueOf(R.id.rlyMoreV0141));
                a(this.bSQ, false);
                return;
            case 7:
                this.bSP.remove(Integer.valueOf(R.id.rlyMoreV0150));
                a(this.bSR, false);
                return;
            case 8:
                this.bSP.remove(Integer.valueOf(R.id.rlyMoreV0159));
                a(this.bSS, false);
                return;
            default:
                return;
        }
    }

    private void a(RelativeLayout view, boolean isSelect) {
        int color;
        if (this.beM) {
            if (isSelect) {
                color = this.bRL.getResources().getColor(R.color.home_interval_bg);
            } else {
                color = this.bRL.getResources().getColor(R.color.home_common_bg);
            }
            view.setBackgroundColor(color);
        } else {
            if (isSelect) {
                color = this.bRL.getResources().getColor(R.color.home_interval_bg_night);
            } else {
                color = this.bRL.getResources().getColor(R.color.game_option_version_choice_bg_night);
            }
            view.setBackgroundColor(color);
        }
        view.getChildAt(0).setVisibility(isSelect ? 0 : 8);
    }

    private void M(View v) {
        switch (v.getId()) {
            case R.id.rlyMoreV0141:
                this.bRW = 5;
                this.bRX = hlx.data.localstore.a.bJn;
                return;
            case R.id.rlyMoreV0159:
                this.bRW = 8;
                this.bRX = hlx.data.localstore.a.bJp;
                return;
            case R.id.rlyMoreV0150:
                this.bRW = 7;
                this.bRX = hlx.data.localstore.a.bJo;
                return;
            case R.id.ly_GameVersion0131:
                this.bRW = 4;
                this.bRX = hlx.data.localstore.a.bJm;
                return;
            case R.id.ly_GameVersion0130:
                this.bRW = 3;
                this.bRX = hlx.data.localstore.a.bJl;
                return;
            case R.id.ly_GameVersion0121:
                this.bRW = 2;
                this.bRX = VERSION.SDK_INT < 11 ? hlx.data.localstore.a.bJj : hlx.data.localstore.a.bJk;
                return;
            case R.id.ly_GameVersion0111:
                this.bRW = 1;
                this.bRX = hlx.data.localstore.a.bJi;
                return;
            case R.id.ly_GameVersion0105:
                this.bRW = 0;
                this.bRX = hlx.data.localstore.a.bJh;
                return;
            default:
                return;
        }
    }

    private void SB() {
        this.bSI = (Button) findViewById(R.id.btnSelectGameMoreFirearms0130);
        this.bSJ = (Button) findViewById(R.id.btnSelectGameMoreFirearms0121);
        this.bSK = (Button) findViewById(R.id.btnDownGameVersion0131);
        this.bSL = (Button) findViewById(R.id.btnDownGameVersion0130);
        this.bSM = (Button) findViewById(R.id.btnDownGameVersion0121);
        this.bSN = (Button) findViewById(R.id.btnDownGameVersion0111);
        this.bSO = (Button) findViewById(R.id.btnDownGameVersion0105);
        this.bSK.setOnClickListener(this.mClickListener);
        this.bSL.setOnClickListener(this.mClickListener);
        this.bSM.setOnClickListener(this.mClickListener);
        this.bSN.setOnClickListener(this.mClickListener);
        this.bSO.setOnClickListener(this.mClickListener);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    private void a(Button button, Button button1, Button button2, Button button3) {
        if (button != null) {
            button.setVisibility(0);
        }
        if (button1 != null) {
            button1.setVisibility(8);
        }
        if (button2 != null) {
            button2.setVisibility(8);
        }
        if (button3 != null) {
            button3.setVisibility(8);
        }
    }

    private void SC() {
        boolean dayMode = com.simple.colorful.d.isDayMode();
        this.aIs.setVisibility(8);
        final ImageButton view = (ImageButton) findViewById(R.id.sys_header_right_img);
        view.setVisibility(0);
        view.setImageResource(dayMode ? R.drawable.more_day_selector : R.drawable.more_night_selector);
        int i = UtilsScreen.dipToPx(this, 11);
        view.setPadding(i, i, i, i);
        view.setVisibility(0);
        List<h> texts = new ArrayList();
        h item1 = new h(dayMode ? R.drawable.online_disk_day : R.drawable.online_disk_night, hlx.data.localstore.a.bKL, false);
        h item2 = new h(dayMode ? R.drawable.tutorial_day : R.drawable.tutorial_night, hlx.data.localstore.a.bKM, false);
        texts.add(item1);
        texts.add(item2);
        final j popup = new j(this.bRL, new c(), texts);
        view.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MCVersionSelect bTk;

            public void onClick(View v) {
                popup.t(view);
            }
        });
    }

    private void sJ() {
        this.bRM = (Button) findViewById(R.id.btnSelectGameVersion0105);
        this.bRN = (Button) findViewById(R.id.btnSelectGameVersion0111);
        this.bRO = (Button) findViewById(R.id.btnSelectGameVersion0121);
        this.bRP = (Button) findViewById(R.id.btnSelectGameVersion0130);
        this.bRQ = (Button) findViewById(R.id.btnSelectGameVersion0131);
        this.bRM.setOnClickListener(this.mClickListener);
        this.bRN.setOnClickListener(this.mClickListener);
        this.bRO.setOnClickListener(this.mClickListener);
        this.bRP.setOnClickListener(this.mClickListener);
        this.bRQ.setOnClickListener(this.mClickListener);
        ((TextView) findViewById(R.id.tv_GameVersion0121)).setText(VERSION.SDK_INT < 11 ? hlx.data.localstore.a.bJj : hlx.data.localstore.a.bJk);
        if (VERSION.SDK_INT < 11) {
            findViewById(R.id.ly_GameVersion0130).setVisibility(0);
            findViewById(R.id.ly_GameVersion0131).setVisibility(8);
        }
        this.bRR = (Button) findViewById(R.id.btnclearGameVersion0105);
        this.bRS = (Button) findViewById(R.id.btnclearGameVersion0111);
        this.bRT = (Button) findViewById(R.id.btnclearGameVersion0121);
        this.bRU = (Button) findViewById(R.id.btnclearGameVersion0130);
        this.bRV = (Button) findViewById(R.id.btnclearGameVersion0131);
        this.bRR.setOnClickListener(this.bTc);
        this.bRS.setOnClickListener(this.bTc);
        this.bRT.setOnClickListener(this.bTc);
        this.bRU.setOnClickListener(this.bTc);
        this.bRV.setOnClickListener(this.bTc);
    }

    private void SD() {
        this.bRX = hlx.launch.game.c.Sg().dx(false);
    }

    private void SE() {
        this.bTb = new CheckLocalDataAsnycTask(this);
        this.bTb.execute(new String[0]);
    }

    private void SF() {
        switch (hlx.launch.game.c.Sg().Sh()) {
            case 0:
                this.bSX.performClick();
                return;
            case 1:
                this.bSW.performClick();
                return;
            case 2:
                this.bSV.performClick();
                return;
            case 3:
                this.bSU.performClick();
                return;
            case 4:
                this.bST.performClick();
                return;
            case 5:
                this.bSQ.performClick();
                return;
            case 7:
                this.bSR.performClick();
                return;
            case 8:
                this.bSS.performClick();
                return;
            default:
                return;
        }
    }

    private boolean mK(int version) {
        String zipPath;
        String _MD5;
        switch (version) {
            case 0:
                zipPath = UtilsFile.getRootPath() + Constants.bsu + ".zip";
                _MD5 = com.huluxia.mcinterface.e.ajc;
                break;
            case 1:
                zipPath = UtilsFile.getRootPath() + Constants.bsv + ".zip";
                _MD5 = com.huluxia.mcinterface.e.ajd;
                break;
            case 2:
                zipPath = UtilsFile.getRootPath() + Constants.bsw + ".zip";
                if (VERSION.SDK_INT >= 11) {
                    _MD5 = com.huluxia.mcinterface.e.aja;
                    break;
                }
                _MD5 = com.huluxia.mcinterface.e.aiY;
                break;
            case 3:
                zipPath = UtilsFile.getRootPath() + Constants.bsx + ".zip";
                if (VERSION.SDK_INT >= 11) {
                    _MD5 = com.huluxia.mcinterface.e.ajb;
                    break;
                }
                _MD5 = com.huluxia.mcinterface.e.aiZ;
                break;
            case 4:
                zipPath = UtilsFile.getRootPath() + Constants.bsy + ".zip";
                if (VERSION.SDK_INT >= 11) {
                    _MD5 = com.huluxia.mcinterface.e.aje;
                    break;
                }
                _MD5 = com.huluxia.mcinterface.e.aiZ;
                break;
            default:
                zipPath = "";
                _MD5 = "";
                break;
        }
        if (com.huluxia.utils.j.isExist(zipPath) && com.huluxia.utils.j.getFileMD5(zipPath).equalsIgnoreCase(_MD5)) {
            return true;
        }
        return false;
    }

    private void SG() {
        switch (this.bSh) {
            case 0:
                this.bRY = false;
                a(this.bSX, 0);
                UtilsFile.deleteFile(UtilsFile.getRootPath() + Constants.bsu + ".zip");
                a(this.bSO, this.bRR, this.bRM, null);
                return;
            case 1:
                this.bRZ = false;
                a(this.bSW, 1);
                UtilsFile.deleteFile(UtilsFile.getRootPath() + Constants.bsv + ".zip");
                a(this.bSN, this.bRN, this.bRS, null);
                return;
            case 2:
                this.bSa = false;
                a(this.bSV, 2);
                UtilsFile.deleteFile(UtilsFile.getRootPath() + Constants.bsw + ".zip");
                a(this.bSM, this.bRO, this.bRT, null);
                return;
            case 3:
                this.bSb = false;
                a(this.bSU, 3);
                UtilsFile.deleteFile(UtilsFile.getRootPath() + Constants.bsx + ".zip");
                a(this.bSL, this.bRP, this.bRU, null);
                return;
            case 4:
                this.bSc = false;
                a(this.bST, 4);
                UtilsFile.deleteFile(UtilsFile.getRootPath() + Constants.bsy + ".zip");
                a(this.bSK, this.bRQ, this.bRV, null);
                return;
            default:
                return;
        }
    }

    private void a(RelativeLayout relativeLayout, int version) {
        if (!relativeLayout.isClickable()) {
            this.bRW = -1;
            SI();
        }
        mN(version);
    }

    private void SH() {
        if (this.bRW == hlx.launch.game.c.Sg().Sm() || hlx.launch.game.c.Sg().Sm() == -1) {
            t.l(this.bRL, "已选择" + this.bRX + "版本");
            SI();
            return;
        }
        gZ(this.bRX);
    }

    private void SI() {
        hlx.launch.game.c.Sg().mG(this.bRW);
    }

    private void gZ(String version) {
        new DialogManager(this.bRL).showOkCancelDialog(hlx.data.localstore.a.bKA, String.format(hlx.data.localstore.a.bKF, new Object[]{version}), hlx.data.localstore.a.bKC, hlx.data.localstore.a.bKB, true, new OkCancelDialogListener(this) {
            final /* synthetic */ MCVersionSelect bTk;

            {
                this.bTk = this$0;
            }

            public void onCancel() {
            }

            public void onOk() {
                t.l(this.bTk.bRL, "已选择" + this.bTk.bRX + "版本");
                this.bTk.SI();
                MCLauncherActivity105.t(this.bTk.bRL, ReStartSoftFlag.MC_RESTART_BLACK.Value());
            }
        });
    }

    private void SJ() {
        CharSequence _prompt = null;
        switch (this.bRW) {
            case 0:
            case 1:
                _prompt = this.bRL.getResources().getString(R.string.TipMCOldVerDown);
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 14680064) {
                    _prompt = _prompt + hlx.data.localstore.a.bKx;
                    break;
                }
                break;
            case 2:
            case 3:
                _prompt = hlx.data.localstore.a.bKO;
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 15728640) {
                    _prompt = _prompt + hlx.data.localstore.a.bKy;
                    break;
                }
                break;
            case 4:
                _prompt = hlx.data.localstore.a.bKP;
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 17825792) {
                    _prompt = _prompt + hlx.data.localstore.a.bKz;
                    break;
                }
                break;
        }
        new DialogManager(this.bRL).showOkCancelDialog(hlx.data.localstore.a.bKA, _prompt, hlx.data.localstore.a.bKC, hlx.data.localstore.a.bKB, true, new OkCancelDialogListener(this) {
            final /* synthetic */ MCVersionSelect bTk;

            {
                this.bTk = this$0;
            }

            public void onCancel() {
            }

            public void onOk() {
                this.bTk.Ep();
            }
        });
    }

    private void Ep() {
        String gamePackName = "";
        String url = "";
        switch (this.bRW) {
            case 0:
                gamePackName = Constants.bsu;
                url = Constants.bsl;
                a(this.bRM, this.bRR, this.bSO, null);
                break;
            case 1:
                gamePackName = Constants.bsv;
                url = Constants.bsm;
                a(this.bRN, this.bRS, this.bSN, null);
                break;
            case 2:
                gamePackName = Constants.bsw;
                url = Constants.bsn;
                if (VERSION.SDK_INT < 11) {
                    url = Constants.bsq;
                }
                a(this.bRO, this.bRT, this.bSM, null);
                break;
            case 3:
                gamePackName = Constants.bsx;
                url = Constants.bso;
                if (VERSION.SDK_INT < 11) {
                    url = Constants.bsr;
                }
                a(this.bRP, this.bRU, this.bSL, null);
                break;
            case 4:
                gamePackName = Constants.bsy;
                url = Constants.bsp;
                if (VERSION.SDK_INT < 11) {
                    url = Constants.bss;
                }
                a(this.bRQ, this.bRV, this.bSK, null);
                break;
        }
        v(url, UtilsFile.getRootPath(), gamePackName + ".zip");
    }

    private void bT(Context context) {
        new DialogManager(context).showOkCancelDialog(hlx.data.localstore.a.bKA, hlx.data.localstore.a.bKK, hlx.data.localstore.a.bKC, hlx.data.localstore.a.bKB, true, new OkCancelDialogListener(this) {
            final /* synthetic */ MCVersionSelect bTk;

            {
                this.bTk = this$0;
            }

            public void onCancel() {
            }

            public void onOk() {
                this.bTk.SG();
            }
        });
    }

    private void SK() {
        this.bSi = (Button) findViewById(R.id.btnInstallMoreFirearms0121);
        this.bSi.setOnClickListener(this.bTd);
        this.bSj = (Button) findViewById(R.id.btnDownMoreFirearms0121);
        this.bSj.setOnClickListener(this.bTd);
        this.bSk = (Button) findViewById(R.id.btnClearMoreFirearms0121);
        this.bSk.setOnClickListener(this.bTd);
        this.bSl = (Button) findViewById(R.id.btnInstallMoreFirearms0130);
        this.bSl.setOnClickListener(this.bTd);
        this.bSm = (Button) findViewById(R.id.btnDownMoreFirearms0130);
        this.bSm.setOnClickListener(this.bTd);
        this.bSn = (Button) findViewById(R.id.btnClearMoreFirearms0130);
        this.bSn.setOnClickListener(this.bTd);
        this.bSo = (Button) findViewById(R.id.btnInstallMoreV0150);
        this.bSo.setOnClickListener(this.bTd);
        this.bSp = (Button) findViewById(R.id.btnDownMoreV0150);
        this.bSp.setOnClickListener(this.bTd);
        this.bSq = (Button) findViewById(R.id.btnClearMoreV0150);
        this.bSq.setOnClickListener(this.bTd);
        this.bSr = (Button) findViewById(R.id.btnSelectGameVersion0150);
        this.bSw = (Button) findViewById(R.id.btnInstallMoreV0159);
        this.bSw.setOnClickListener(this.bTd);
        this.bSx = (Button) findViewById(R.id.btnDownMoreV0159);
        this.bSx.setOnClickListener(this.bTd);
        this.bSy = (Button) findViewById(R.id.btnClearMoreV0159);
        this.bSy.setOnClickListener(this.bTd);
        this.bSz = (Button) findViewById(R.id.btnSelectGameVersion0159);
        this.bSs = (Button) findViewById(R.id.btnInstallMoreV0141);
        this.bSs.setOnClickListener(this.bTd);
        this.bSt = (Button) findViewById(R.id.btnDownMoreV0141);
        this.bSt.setOnClickListener(this.bTd);
        this.bSu = (Button) findViewById(R.id.btnClearMoreV0141);
        this.bSu.setOnClickListener(this.bTd);
        this.bSv = (Button) findViewById(R.id.btnSelectGameVersion0141);
    }

    private void SL() {
        if (VERSION.SDK_INT < 11) {
            findViewById(R.id.rlyMoreV0150).setVisibility(8);
            findViewById(R.id.rlyMoreV0159).setVisibility(8);
            findViewById(R.id.rlyMoreV0141).setVisibility(8);
        }
        this.bSi.setVisibility(8);
        this.bSk.setVisibility(8);
        this.bSj.setVisibility(0);
        this.bSl.setVisibility(8);
        this.bSn.setVisibility(8);
        this.bSm.setVisibility(0);
        if (UtilsVersion.hasMarshmallow()) {
            findViewById(R.id.ly_GameVersion0131).setVisibility(8);
            findViewById(R.id.ly_GameVersion0121).setVisibility(8);
            findViewById(R.id.ly_GameVersion0111).setVisibility(8);
            findViewById(R.id.ly_GameVersion0105).setVisibility(8);
            findViewById(R.id.rlyMoreFirearms0130).setVisibility(8);
            findViewById(R.id.rlyMoreFirearms0121).setVisibility(8);
            findViewById(R.id.block).setVisibility(8);
        }
        if (VERSION.SDK_INT < 19) {
            findViewById(R.id.rlyMoreV0159).setVisibility(8);
        }
    }

    private boolean mO(int moreIndex) {
        String tmpPackMD5Value;
        boolean _tmpFlag;
        switch (moreIndex) {
            case 1:
                return hlx.mcspecialmode.firearms.a.Tp().hh(UtilsFile.getRootPath() + hlx.data.localstore.a.bKQ + ".zip");
            case 2:
                return hlx.mcspecialmode.firearms.b.Tq().hh(UtilsFile.getRootPath() + hlx.data.localstore.a.bKT + ".zip");
            case 4:
                tmpPackMD5Value = com.huluxia.mcgame.h.getFileMD5(new File(UtilsFile.getRootPath() + hlx.data.localstore.a.bKX + ".apk"));
                if (com.huluxia.utils.j.isExist(UtilsFile.getRootPath() + hlx.data.localstore.a.bKX + "" + ".apk") && hlx.data.localstore.a.bLa.equalsIgnoreCase(tmpPackMD5Value)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                if (_tmpFlag || hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahK)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                return _tmpFlag;
            case 5:
                tmpPackMD5Value = com.huluxia.mcgame.h.getFileMD5(new File(UtilsFile.getRootPath() + hlx.data.localstore.a.bKY + ".apk"));
                if (com.huluxia.utils.j.isExist(UtilsFile.getRootPath() + hlx.data.localstore.a.bKY + "" + ".apk") && hlx.data.localstore.a.bLb.equalsIgnoreCase(tmpPackMD5Value)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                if (_tmpFlag || hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahL)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                return _tmpFlag;
            case 6:
                tmpPackMD5Value = com.huluxia.mcgame.h.getFileMD5(new File(UtilsFile.getRootPath() + hlx.data.localstore.a.bKZ + ".apk"));
                if (com.huluxia.utils.j.isExist(UtilsFile.getRootPath() + hlx.data.localstore.a.bKZ + "" + ".apk") && hlx.data.localstore.a.bLc.equalsIgnoreCase(tmpPackMD5Value)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                if (_tmpFlag || hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahM)) {
                    _tmpFlag = true;
                } else {
                    _tmpFlag = false;
                }
                return _tmpFlag;
            default:
                return false;
        }
    }

    private boolean mP(int moreIndex) {
        switch (moreIndex) {
            case 1:
                return hlx.mcspecialmode.firearms.a.Tp().hi(UtilsFile.getRootPath() + hlx.data.localstore.a.bKW + "枪械js音效包");
            case 2:
                return hlx.mcspecialmode.firearms.b.Tq().hi(UtilsFile.getRootPath() + hlx.data.localstore.a.bKW + "枪械js音效包13");
            case 4:
                return hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahK);
            case 5:
                return hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahL);
            case 6:
                return hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahM);
            default:
                return false;
        }
    }

    private void mQ(int moreIndex) {
        this.bSG = moreIndex;
        CharSequence _prompt = null;
        switch (moreIndex) {
            case 1:
                _prompt = "将为您下载插件(2.2MB)以支持最新功能";
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 2099200) {
                    _prompt = _prompt + ", (手机剩余空间不足2.2M，请清理后重新下载)";
                    break;
                }
                break;
            case 2:
                _prompt = "将为您下载插件(2.2MB)以支持最新功能";
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 2099200) {
                    _prompt = _prompt + ", (手机剩余空间不足2.2M，请清理后重新下载)";
                    break;
                }
                break;
            case 4:
                _prompt = String.format(Locale.getDefault(), "将为您下载%s版本以支持最新功能", new Object[]{hlx.data.localstore.a.bJn});
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 19922944) {
                    _prompt = _prompt + ", (手机剩余空间不足19M，请清理后重新下载)";
                    break;
                }
                break;
            case 5:
                _prompt = String.format(Locale.getDefault(), "将为您下载%s版本以支持最新功能", new Object[]{hlx.data.localstore.a.bJo});
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 20971520) {
                    _prompt = _prompt + ", (手机剩余空间不足20M，请清理后重新下载)";
                    break;
                }
                break;
            case 6:
                _prompt = String.format(Locale.getDefault(), "将为您下载%s版本以支持最新功能", new Object[]{hlx.data.localstore.a.bJp});
                if (com.huluxia.utils.j.fk(UtilsFile.CU()) < 44040192) {
                    _prompt = _prompt + ", (手机剩余空间不足42M，请清理后重新下载)";
                    break;
                }
                break;
        }
        new DialogManager(this.bRL).showOkCancelDialog(hlx.data.localstore.a.bKA, _prompt, hlx.data.localstore.a.bKC, hlx.data.localstore.a.bKB, true, new OkCancelDialogListener(this) {
            final /* synthetic */ MCVersionSelect bTk;

            {
                this.bTk = this$0;
            }

            public void onCancel() {
            }

            public void onOk() {
                this.bTk.SM();
            }
        });
    }

    private void SM() {
        String url = "";
        String fileName = "";
        switch (this.bSG) {
            case 1:
                url = hlx.data.localstore.a.bKS;
                fileName = "more_sepack0121.zip";
                a(this.bSJ, this.bSk, this.bSi, this.bSj);
                break;
            case 2:
                url = hlx.data.localstore.a.bKV;
                fileName = "more_sepack0130.zip";
                a(this.bSI, this.bSn, this.bSl, this.bSm);
                break;
            case 4:
                url = hlx.data.localstore.a.bLd;
                fileName = "MC_0.14.1_huluxia.apk";
                a(this.bSv, this.bSs, this.bSu, this.bSt);
                break;
            case 5:
                url = hlx.data.localstore.a.bLe;
                fileName = "MC_0.15.1.2_huluxia.apk";
                a(this.bSr, this.bSo, this.bSq, this.bSp);
                break;
            case 6:
                url = hlx.data.localstore.a.bLf;
                fileName = "MC_0.15.90.2_hlx.apk";
                a(this.bSz, this.bSw, this.bSy, this.bSx);
                break;
        }
        v(url, UtilsFile.getRootPath(), fileName);
    }

    private void SN() {
        this.bRW = 8;
        this.bRX = hlx.data.localstore.a.bJp;
        SH();
    }

    private void SO() {
        this.bRW = 7;
        this.bRX = hlx.data.localstore.a.bJo;
        SH();
    }

    private void SP() {
        this.bRW = 5;
        this.bRX = hlx.data.localstore.a.bJn;
        SH();
    }

    private void mR(int moreResIndex) {
        if (!mP(moreResIndex) || moreResIndex == 3) {
            switch (moreResIndex) {
                case 1:
                    i.i(this.aDe);
                    com.huluxia.widget.h.NV().t("unzipMoreResFirearms", UtilsFile.getRootPath() + hlx.data.localstore.a.bKQ + ".zip", UtilsFile.getRootPath() + hlx.data.localstore.a.bKW);
                    return;
                case 2:
                    i.i(this.aDe);
                    com.huluxia.widget.h.NV().t("unzipMoreResFirearms13", UtilsFile.getRootPath() + hlx.data.localstore.a.bKT + ".zip", UtilsFile.getRootPath() + hlx.data.localstore.a.bKW);
                    return;
                case 4:
                    if (hlx.launch.game.b.a(u.getPackageManager(), "com.mojang.minecraftpe")) {
                        t.l(this.bRL, "需要先卸载旧版本！");
                        this.bSY = 5;
                        hb("com.mojang.minecraftpe");
                        return;
                    }
                    ha(UtilsFile.getRootPath() + hlx.data.localstore.a.bKX + ".apk");
                    return;
                case 5:
                    if (hlx.launch.game.b.a(u.getPackageManager(), "com.mojang.minecraftpe")) {
                        t.l(this.bRL, "需要先卸载原有版本！");
                        this.bSY = 4;
                        hb("com.mojang.minecraftpe");
                        return;
                    }
                    ha(UtilsFile.getRootPath() + hlx.data.localstore.a.bKY + ".apk");
                    return;
                case 6:
                    if (hlx.launch.game.b.a(u.getPackageManager(), "com.mojang.minecraftpe")) {
                        t.l(this.bRL, "需要先卸载原有版本！");
                        this.bSY = 4;
                        hb("com.mojang.minecraftpe");
                        return;
                    }
                    ha(UtilsFile.getRootPath() + hlx.data.localstore.a.bKZ + ".apk");
                    return;
                default:
                    return;
            }
        }
    }

    private void ha(String path) {
        try {
            UtilsFile.deleteFile(hlx.data.localstore.a.bJP);
        } catch (Exception e) {
        }
        File file = new File(path);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        if (UtilsApkPackage.availableIntent(this, intent)) {
            startActivityForResult(intent, this.mRequestCode);
        } else {
            HLog.verbose(TAG, "cann't open apk install", new Object[0]);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mRequestCode == 0) {
            SQ();
        }
    }

    private void hb(String packName) {
        Uri uri = Uri.parse("package:" + packName);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DELETE");
        intent.setData(uri);
        startActivity(intent);
    }

    private void SQ() {
        this.bSg.show();
        AsyncTaskCenter.getInstance().execute(this.bTe, new RunnableCallback(this) {
            final /* synthetic */ MCVersionSelect bTk;

            {
                this.bTk = this$0;
            }

            public void onCallback() {
                this.bTk.runOnUiThread(new 1(this));
            }
        });
    }

    private void SR() {
        if (this.bSg != null && this.bSg.isShowing()) {
            this.bSg.cancel();
        }
        if (bTf == 1) {
            a(this.bSq, this.bSo, this.bSp, this.bSr);
            mM(7);
            this.bSR.performClick();
        } else if (bTf == 2) {
            a(this.bSo, this.bSq, this.bSp, this.bSr);
        } else {
            a(this.bSp, this.bSq, this.bSo, this.bSr);
        }
        if (bTg == 1) {
            a(this.bSy, this.bSw, this.bSx, this.bSz);
            mM(8);
            this.bSS.performClick();
        } else if (bTg == 2) {
            a(this.bSw, this.bSy, this.bSx, this.bSz);
        } else {
            a(this.bSx, this.bSy, this.bSw, this.bSz);
        }
        if (bTh == 1) {
            a(this.bSu, this.bSt, this.bSs, this.bSv);
            mM(5);
            this.bSQ.performClick();
        } else if (bTh == 2) {
            a(this.bSs, this.bSt, this.bSu, this.bSv);
        } else {
            a(this.bSt, this.bSu, this.bSs, this.bSv);
        }
    }

    private void SS() {
        if (hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahL)) {
            bTf = 1;
        } else if (mO(5)) {
            bTf = 2;
        } else {
            bTf = 3;
        }
        if (hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahM)) {
            bTg = 1;
        } else if (mO(6)) {
            bTg = 2;
        } else {
            bTg = 3;
        }
        if (hlx.launch.game.b.a(getPackageManager(), "com.mojang.minecraftpe", com.huluxia.mcinterface.e.ahK)) {
            bTh = 1;
        } else if (mO(4)) {
            bTh = 2;
        } else {
            bTh = 3;
        }
    }

    private void mS(int moreResIndex) {
        this.bSH = moreResIndex;
        DialogManager manager = new DialogManager(this);
        StyleHolder styleHolder = new StyleHolder();
        styleHolder.colorUnMarkedButton = com.simple.colorful.d.getColor(this.bRL, 16842808);
        styleHolder.colorButton = this.bRL.getResources().getColor(R.color.dialog_ok_btn_color);
        manager.showOkCancelDialog(hlx.data.localstore.a.bKA, hlx.data.localstore.a.bKK, "删除", hlx.data.localstore.a.bKB, true, new OkCancelDialogListener(this) {
            final /* synthetic */ MCVersionSelect bTk;

            {
                this.bTk = this$0;
            }

            public void onCancel() {
            }

            public void onOk() {
                this.bTk.ST();
            }
        }, styleHolder);
    }

    private void ST() {
        switch (this.bSH) {
            case 1:
                UtilsFile.deleteFile(UtilsFile.getRootPath() + hlx.data.localstore.a.bKQ + "" + ".zip");
                a(this.bSj, this.bSi, this.bSk, null);
                return;
            case 2:
                UtilsFile.deleteFile(UtilsFile.getRootPath() + hlx.data.localstore.a.bKT + "" + ".zip");
                a(this.bSm, this.bSl, this.bSn, null);
                return;
            case 4:
                this.bSY = 4;
                hb("com.mojang.minecraftpe");
                return;
            case 5:
                this.bSY = 5;
                hb("com.mojang.minecraftpe");
                return;
            case 6:
                this.bSY = 6;
                hb("com.mojang.minecraftpe");
                return;
            default:
                return;
        }
    }

    private void v(String url, String dirPath, String fileName) {
        UtilsFile.deleteFile(dirPath + fileName);
        ResTaskInfo tInfo = new ResTaskInfo();
        tInfo.url = url;
        tInfo.filename = fileName;
        tInfo.mM = 1000006;
        tInfo.dir = dirPath;
        tInfo.na = true;
        tInfo.nb = false;
        ResourceCtrl.getInstance().addTask(tInfo);
        t.l(this.bRL, "后台下载中...");
    }

    private void hc(String url) {
        if (url.equals(Constants.bsl)) {
            this.bRW = 0;
            this.bRX = hlx.data.localstore.a.bJh;
            mK(this.bRW);
            SH();
            this.bRY = true;
            mM(this.bRW);
            this.bSX.performClick();
            a(this.bRR, this.bRM, this.bSO, null);
        } else if (url.equals(Constants.bsm)) {
            this.bRW = 1;
            this.bRX = hlx.data.localstore.a.bJi;
            mK(this.bRW);
            SH();
            this.bRZ = true;
            mM(this.bRW);
            this.bSW.performClick();
            a(this.bRS, this.bRN, this.bSN, null);
        } else if (url.equals(Constants.bsn) || url.equals(Constants.bsq)) {
            this.bRW = 2;
            this.bRX = hlx.data.localstore.a.bJj;
            mK(this.bRW);
            SH();
            this.bSa = true;
            mM(this.bRW);
            this.bSV.performClick();
            a(this.bRT, this.bRO, this.bSM, null);
        } else if (url.equals(Constants.bso) || url.equals(Constants.bsr)) {
            this.bRW = 3;
            this.bRX = hlx.data.localstore.a.bJl;
            mK(this.bRW);
            SH();
            this.bSb = true;
            mM(this.bRW);
            this.bSU.performClick();
            a(this.bRU, this.bRP, this.bSL, null);
        } else if (url.equals(Constants.bsp) || url.equals(Constants.bss)) {
            this.bRW = 4;
            this.bRX = hlx.data.localstore.a.bJm;
            mK(this.bRW);
            SH();
            this.bSc = true;
            mM(this.bRW);
            this.bST.performClick();
            a(this.bRV, this.bRQ, this.bSK, null);
        } else if (url.equals(hlx.data.localstore.a.bLe)) {
            this.bSE = true;
            a(this.bSo, this.bSp, this.bSr, this.bSq);
            mR(5);
            hlx.recorddata.a.dG(true);
        } else if (url.equals(hlx.data.localstore.a.bLf)) {
            this.bSF = true;
            a(this.bSw, this.bSx, this.bSz, this.bSy);
            mR(6);
            hlx.recorddata.a.dF(true);
        } else if (url.equals(hlx.data.localstore.a.bLd)) {
            this.bSD = true;
            a(this.bSs, this.bSt, this.bSv, this.bSu);
            mR(4);
            hlx.recorddata.a.dH(true);
        } else if (url.equals(hlx.data.localstore.a.bKS)) {
            this.bSA = true;
            if (mP(1)) {
                a(this.bSk, this.bSi, this.bSj, this.bSJ);
            } else {
                a(this.bSi, this.bSk, this.bSj, this.bSJ);
            }
        } else if (url.equals(hlx.data.localstore.a.bKV)) {
            this.bSB = true;
            if (mP(2)) {
                a(this.bSn, this.bSm, this.bSl, this.bSI);
            } else {
                a(this.bSl, this.bSn, this.bSm, this.bSI);
            }
        }
    }

    private void hd(String url) {
        if (url.equals(Constants.bsl)) {
            this.bRY = false;
            a(this.bSO, this.bRR, this.bRM, null);
        } else if (url.equals(Constants.bsm)) {
            this.bRZ = false;
            a(this.bSN, this.bRS, this.bRN, null);
        } else if (url.equals(Constants.bsn) || url.equals(Constants.bsq)) {
            this.bSa = false;
            a(this.bSM, this.bRT, this.bRO, null);
        } else if (url.equals(Constants.bso) || url.equals(Constants.bsr)) {
            this.bSb = false;
            a(this.bSL, this.bRU, this.bRP, null);
        } else if (url.equals(Constants.bsp) || url.equals(Constants.bss)) {
            this.bSc = false;
            a(this.bSK, this.bRV, this.bRQ, null);
        } else if (url.equals(hlx.data.localstore.a.bLe)) {
            this.bSE = false;
            a(this.bSp, this.bSq, this.bSo, this.bSr);
        } else if (url.equals(hlx.data.localstore.a.bLf)) {
            this.bSF = false;
            a(this.bSx, this.bSy, this.bSw, this.bSz);
        } else if (url.equals(hlx.data.localstore.a.bLd)) {
            this.bSD = false;
            a(this.bSt, this.bSu, this.bSs, this.bSv);
        } else if (url.equals(hlx.data.localstore.a.bKS)) {
            this.bSA = false;
            a(this.bSj, this.bSk, this.bSi, this.bSJ);
        } else if (url.equals(hlx.data.localstore.a.bKV)) {
            this.bSB = false;
            a(this.bSm, this.bSn, this.bSl, this.bSI);
        }
    }

    private void he(String url) {
        if (url.equals(Constants.bsl)) {
            a(this.bRM, this.bRR, this.bSO, null);
        } else if (url.equals(Constants.bsm)) {
            a(this.bRN, this.bRS, this.bSN, null);
        } else if (url.equals(Constants.bsn) || url.equals(Constants.bsq)) {
            a(this.bRO, this.bRT, this.bSM, null);
        } else if (url.equals(Constants.bso) || url.equals(Constants.bsr)) {
            a(this.bRP, this.bRU, this.bSL, null);
        } else if (url.equals(Constants.bsp) || url.equals(Constants.bss)) {
            a(this.bRQ, this.bRV, this.bSK, null);
        } else if (url.equals(hlx.data.localstore.a.bLe)) {
            a(this.bSr, this.bSo, this.bSp, this.bSq);
        } else if (url.equals(hlx.data.localstore.a.bLf)) {
            a(this.bSz, this.bSw, this.bSx, this.bSy);
        } else if (url.equals(hlx.data.localstore.a.bLd)) {
            a(this.bSv, this.bSs, this.bSt, this.bSu);
        } else if (url.equals(hlx.data.localstore.a.bKS)) {
            if (mP(1)) {
                a(this.bSJ, this.bSk, this.bSi, this.bSj);
            } else {
                a(this.bSJ, this.bSi, this.bSk, this.bSj);
            }
        } else if (url.equals(hlx.data.localstore.a.bKV)) {
            this.bSB = true;
            if (mP(2)) {
                a(this.bSI, this.bSn, this.bSm, this.bSl);
            } else {
                a(this.bSI, this.bSl, this.bSn, this.bSm);
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.bTi);
        EventNotifyCenter.remove(this.bTj);
        AsyncTaskCenter.getInstance().cancel(this.bTe);
        unregisterReceiver(this.bSZ);
        this.bTe = null;
        if (this.bTb != null) {
            this.bTb.cancel(true);
        }
    }
}
