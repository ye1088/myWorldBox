package com.huluxia;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.controller.b;
import com.huluxia.controller.c;
import com.huluxia.controller.resource.ResourceCtrl;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.framework.base.utils.UtilsNetwork;
import com.huluxia.framework.base.utils.UtilsText;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.module.GameInfo;
import com.huluxia.module.h;
import com.huluxia.r.a;
import com.huluxia.widget.Constants;
import java.io.File;
import java.io.IOException;

public class DownloadDialog extends DialogFragment {
    private static String TAG = "DownloadDialog";
    private static long eR = Constants.brY;
    private static GameInfo eU;
    private static boolean eV = false;
    private static String mDir = b.dE().getDownloadPath();
    private static String mFileName = ("hlxmcgame_" + eR + ".apk");
    private TextView eN;
    private TextView eO;
    private TextView eP;
    private TextView eQ;
    private DownloadDialog eS;
    ProgressBar eT;
    protected a eW = new a(this);
    private CallbackHandler eX = new CallbackHandler(this) {
        final /* synthetic */ DownloadDialog eZ;

        {
            this.eZ = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            if (this.eZ.mUrl.equals(url)) {
                int progress = (int) ((((float) progressInfo.progress) / ((float) progressInfo.length)) * 100.0f);
                String progressText = String.valueOf(progress) + "%";
                String speedText = UtilsText.getFileLength((long) progressInfo.speed) + "/s";
                this.eZ.eO.setText(String.format("正在下载: %s(%s)", new Object[]{progressText, speedText}));
                this.eZ.eT.setProgress(progress);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            if (this.eZ.mUrl.equals(url)) {
                this.eZ.dismissAllowingStateLoss();
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.eZ.mUrl.equals(url)) {
                this.eZ.dismissAllowingStateLoss();
                Toast.makeText(this.eZ.getActivity(), "下载出错了", 1).show();
            }
        }
    };
    private CallbackHandler eY = new CallbackHandler(this) {
        final /* synthetic */ DownloadDialog eZ;

        {
            this.eZ = this$0;
        }

        @MessageHandler(message = 554)
        public void onRecvGameDetail(boolean succ, GameInfo nouse) {
            if (!succ || nouse.md5 == null) {
                this.eZ.dismissAllowingStateLoss();
                Toast.makeText(this.eZ.getActivity(), "获取游戏信息失败，请稍后重试", 1).show();
                HLog.error(this, "emu onRecvGameDetail no recv, url = " + this.eZ.mUrl, new Object[0]);
                return;
            }
            DownloadDialog.mFileName = "hlxmcgame_" + nouse.getAppTitle() + ".apk";
            File apkFile = new File(DownloadDialog.mDir, DownloadDialog.mFileName);
            String md5 = null;
            if (apkFile.exists()) {
                try {
                    md5 = UtilsMD5.getFileMD5String(apkFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String packName = nouse.packname;
            if (packName != null) {
                boolean installed = UtilsApkPackage.isApkInstalled(this.eZ.getActivity(), packName);
                boolean needUpdate = UtilsApkPackage.isApkNeedUpdate(this.eZ.getActivity(), packName, nouse.versionCode);
                if (!installed || needUpdate) {
                    this.eZ.a(apkFile, nouse);
                    return;
                } else if (md5 == null || !md5.equalsIgnoreCase(md5)) {
                    this.eZ.dismissAllowingStateLoss();
                    UtilsApkPackage.runApp(this.eZ.getActivity(), packName);
                    return;
                } else {
                    this.eZ.view.setVisibility(0);
                    DownloadDialog.eV = true;
                    r.ck().K(a.ly);
                    UtilsApkPackage.runInstallApp(this.eZ.eS.getActivity(), apkFile.getAbsolutePath());
                    return;
                }
            }
            this.eZ.dismissAllowingStateLoss();
            Toast.makeText(this.eZ.getActivity(), "没有找到相应的游戏", 1).show();
            HLog.error(this, "emu onRecvGameDetail url list is NULL, url = " + this.eZ.mUrl, new Object[0]);
        }
    };
    private CallbackHandler mTaskCallback = new CallbackHandler(this) {
        final /* synthetic */ DownloadDialog eZ;

        {
            this.eZ = this$0;
        }

        @MessageHandler(message = 258)
        public void onFinish(String url) {
            if (this.eZ.mUrl.equals(url)) {
                this.eZ.eO.setText("下载完成");
                r.ck().K(a.lx);
                this.eZ.dismissAllowingStateLoss();
            }
        }
    };
    private String mUrl;
    private View view;

    public String bt() {
        try {
            if (eU != null) {
                return eU.packname;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void a(String in_recommenturl, int in_versionCode, String in_apptitle, String in_md5, String in_packname) {
        if (eU == null) {
            eU = new GameInfo();
        }
        if (eU != null) {
            eU.downloadingUrl = in_recommenturl;
            eU.versionCode = in_versionCode;
            eU.setAppTitle(in_apptitle);
            eU.md5 = in_md5;
            eU.packname = in_packname;
        }
    }

    public static DownloadDialog bu() {
        DownloadDialog dialog = new DownloadDialog();
        if (eU == null) {
            eU = new GameInfo();
        }
        return dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(BaseEvent.class, this.eX);
        EventNotifyCenter.add(h.class, this.eY);
        EventNotifyCenter.add(c.class, this.mTaskCallback);
        setCancelable(false);
        this.eS = this;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setBackgroundDrawableResource(d.transparent);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
        this.view = inflater.inflate(i.download_dialog, null);
        this.eN = (TextView) this.view.findViewById(g.tv_title);
        this.eO = (TextView) this.view.findViewById(g.tv_msg);
        this.eP = (TextView) this.view.findViewById(g.tv_cancel);
        this.eQ = (TextView) this.view.findViewById(g.dlg_download_tv_close);
        this.eT = (ProgressBar) this.view.findViewById(g.download_progressbar);
        this.eP.setOnClickListener(this.eW);
        this.eQ.setOnClickListener(this.eW);
        EventNotifyCenter.notifyEvent(h.class, h.are, Boolean.valueOf(true), eU);
        this.view.setVisibility(4);
        return this.view;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void bv() {
        if (this.mUrl != null) {
            ResTaskInfo info = com.huluxia.controller.resource.bean.a.dM();
            info.filename = mFileName;
            info.url = this.mUrl;
            info.dir = b.dE().getDownloadPath();
            if (ResourceCtrl.getInstance().isDownloading(info)) {
                ResourceCtrl.getInstance().deleteTask(info);
                AppConfig.getInstance().getUiHandler().postDelayed(new 1(this, DownloadMemCache.getInstance().getRecord(this.mUrl)), 1000);
                DownloadMemCache.getInstance().deleteRecord(info.url);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.eX);
        EventNotifyCenter.remove(this.eY);
        EventNotifyCenter.remove(this.mTaskCallback);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void a(File apkFile, GameInfo nouse) {
        if (!UtilsNetwork.isNetworkConnected(getActivity())) {
            t.n(getActivity(), getActivity().getResources().getString(m.no_network));
            dismissAllowingStateLoss();
        } else if (UtilsNetwork.isWifiConnected(getActivity())) {
            new DialogManager(getActivity()).showOkCancelColorDialog(hlx.data.localstore.a.bKA, 0, LayoutInflater.from(getActivity()).inflate(i.textview_text_center, null, false), hlx.data.localstore.a.bKC, 0, hlx.data.localstore.a.bKB, 0, false, new 5(this, apkFile, nouse));
        } else {
            new DialogManager(getActivity()).showOkCancelDialog(null, (CharSequence) "非wifi环境下载需要消耗较多手机流量,确定下载吗?", hlx.data.localstore.a.bKC, hlx.data.localstore.a.bKB, false, new 6(this, apkFile, nouse));
        }
    }

    private void b(File apkFile, GameInfo nouse) {
        if (nouse.downloadingUrl != null) {
            this.view.setVisibility(0);
            this.eN.setText(nouse.getAppTitle());
            this.eO.setText("准备下载...");
            this.mUrl = nouse.downloadingUrl;
            if (apkFile.exists()) {
                apkFile.delete();
            }
            ResTaskInfo info = com.huluxia.controller.resource.bean.a.dM();
            info.filename = mFileName;
            info.url = this.mUrl;
            info.dir = mDir;
            info.mS = nouse.getAppTitle();
            info.mV = info.filename;
            info.na = true;
            info.nb = false;
            ResourceCtrl.getInstance().addTask(info);
            r.ck().K(a.lw);
            s.ae(HTApplication.getAppContext()).a(this.mUrl, nouse);
            return;
        }
        dismissAllowingStateLoss();
        Toast.makeText(getActivity(), "获取游戏信息失败，请稍后重试", 1).show();
        HLog.error(this, "emu onRecvGameDetail url NULL, url = " + this.mUrl, new Object[0]);
    }
}
