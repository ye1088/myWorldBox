package com.MCWorld.version;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.controller.c;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.handler.impl.b.a;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.error.ErrorCode;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsApkPackage;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.module.h;
import com.MCWorld.t;
import com.simple.colorful.d;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.File;

public class VersionDialog extends DialogFragment {
    private static String TAG = "VersionDialog";
    private static String bob = "PARA_INFO";
    private static final int boc = 0;
    private static final int bod = 22;
    private View boe;
    private View bof;
    private View bog;
    private View boh;
    private TextView boi;
    private TextView boj;
    private TextView bok;
    private TextView bol;
    private TextView bom;
    private TextView bon;
    private TextView boo;
    private TextView bop;
    private VersionDialog boq;
    private e bor;
    private CallbackHandler bos = new CallbackHandler(this) {
        final /* synthetic */ VersionDialog bow;

        {
            this.bow = this$0;
        }

        @MessageHandler(message = 773)
        public void onRecvVerinfo(boolean succ, e info, String tag) {
            if (!succ) {
                this.bow.dismissAllowingStateLoss();
                t.n(this.bow.getActivity(), "检查版本失败，请稍后重试");
                HLog.error(this, "emu onRecvGameDetail no recv, url = " + this.bow.mUrl, new Object[0]);
            } else if (info != null) {
                this.bow.bor = info;
                if (this.bow.c(this.bow.bor)) {
                    this.bow.b(this.bow.bor);
                    return;
                }
                t.show_toast(this.bow.boq.getActivity(), "当前已是最新版本");
                this.bow.dismissAllowingStateLoss();
            } else {
                t.show_toast(this.bow.boq.getActivity(), "当前已是最新版本");
                this.bow.dismissAllowingStateLoss();
            }
        }
    };
    private OnClickListener bot = new 3(this);
    private OnClickListener bou = new 4(this);
    private OnClickListener bov = new 5(this);
    private TextView eN;
    private TextView eP;
    private CallbackHandler eX = new CallbackHandler(this) {
        final /* synthetic */ VersionDialog bow;

        {
            this.bow = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(url)) {
                this.bow.gD(this.bow.mUrl);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(url) && url.equals(this.bow.bor.url)) {
                this.bow.dismissAllowingStateLoss();
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(url)) {
                this.bow.gD(this.bow.mUrl);
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(url)) {
                this.bow.gD(this.bow.mUrl);
            }
        }

        @MessageHandler(message = 261)
        public void onReload() {
            if (this.bow.getUserVisibleHint()) {
                this.bow.gD(this.bow.mUrl);
            }
        }
    };
    private CallbackHandler mTaskCallback = new CallbackHandler(this) {
        final /* synthetic */ VersionDialog bow;

        {
            this.bow = this$0;
        }

        @MessageHandler(message = 258)
        public void onFinish(String url) {
        }

        @MessageHandler(message = 256)
        public void onTaskPrepare(String url) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(url)) {
                this.bow.gD(url);
            }
        }

        @MessageHandler(message = 257)
        public void onTaskWaiting(String url) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(url)) {
                this.bow.gD(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadErrorRetry(String oldUrl, String newUrl, long errid) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(oldUrl)) {
                this.bow.gD(this.bow.mUrl);
            }
        }

        @MessageHandler(message = 263)
        public void onDownloadComplete(String url) {
            if (this.bow.getUserVisibleHint() && this.bow.mUrl.equals(url) && url.equals(this.bow.bor.url)) {
                this.bow.dismissAllowingStateLoss();
            }
        }

        @MessageHandler(message = 268)
        public void onPatchStart(String url) {
            HLog.info(VersionDialog.TAG, "onPatchStart mUrl(%s) url(%s)", this.bow.mUrl, url);
            if (this.bow.mUrl.equals(url)) {
                HLog.info(VersionDialog.TAG, "EVENT_PATCH_START", new Object[0]);
                this.bow.boi.setText("增量包校验中...请稍候");
            }
        }

        @MessageHandler(message = 269)
        public void onPatchComplete(String url) {
            HLog.info(VersionDialog.TAG, "onPatchComplete mUrl(%s) url(%s)", this.bow.mUrl, url);
            if (this.bow.mUrl.equals(url)) {
                HLog.info(VersionDialog.TAG, "EVENT_PATCH_COMPLETE", new Object[0]);
                this.bow.boi.setText("开始安装...请稍候");
                this.bow.dismissAllowingStateLoss();
            }
        }

        @MessageHandler(message = 270)
        public void onPatchFail(String url) {
            if (this.bow.mUrl.equals(url)) {
                d info = g.MS().gE(this.bow.bor.patchurl);
                if (info != null) {
                    info.downloadStatus = 2;
                    g.MS().b(info);
                }
                this.bow.boi.setText("增量包安装失败，请使用完整升级包");
                this.bow.a(LayoutIndex.Retry);
            }
        }
    };
    private String mUrl;
    private View view;

    public static VersionDialog a(e versionInfo) {
        VersionDialog dialog = new VersionDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(bob, versionInfo);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(BaseEvent.class, this.eX);
        EventNotifyCenter.add(h.class, this.bos);
        EventNotifyCenter.add(c.class, this.mTaskCallback);
        setCancelable(false);
        this.boq = this;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), d.RD());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setBackgroundDrawableResource(b.d.transparent);
        getDialog().setCanceledOnTouchOutside(false);
        this.view = inflater.inflate(i.dialog_version, null);
        this.boe = this.view.findViewById(g.ly_child_1);
        this.bof = this.view.findViewById(g.ly_child_2);
        this.bog = this.view.findViewById(g.ly_child_3);
        this.boh = this.view.findViewById(g.ly_child_4);
        this.eN = (TextView) this.view.findViewById(g.tv_title);
        this.boi = (TextView) this.view.findViewById(g.tv_msg);
        this.boj = (TextView) this.view.findViewById(g.tv_nexttime);
        this.bok = (TextView) this.view.findViewById(g.tv_browser);
        this.bol = (TextView) this.view.findViewById(g.tv_update);
        this.bom = (TextView) this.view.findViewById(g.tv_retry);
        this.bon = (TextView) this.view.findViewById(g.tv_reload);
        this.boo = (TextView) this.view.findViewById(g.tv_continue);
        this.eP = (TextView) this.view.findViewById(g.tv_cancel);
        this.bop = (TextView) this.view.findViewById(g.tv_pause);
        this.view.setVisibility(8);
        this.bor = (e) getArguments().getParcelable(bob);
        if (this.bor == null) {
            String app_name = UtilsApkPackage.getAppPackageName(this.boq.getActivity());
            String channel = UtilsApkPackage.getAppMetadata(this.boq.getActivity(), "UMENG_CHANNEL");
            if (channel == null) {
                channel = "mctool_huluxia";
            }
            h.MU().r(app_name, channel, TAG);
        } else {
            b(this.bor);
        }
        return this.view;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.eX);
        EventNotifyCenter.remove(this.bos);
        EventNotifyCenter.remove(this.mTaskCallback);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void b(e info) {
        this.mUrl = info.url;
        if (!UtilsFunction.empty(info.name)) {
            this.eN.setText(info.name);
        }
        if (info.content != null) {
            this.boi.setText(info.content.replace("\\n", SpecilApiUtil.LINE_SEP));
        }
        this.boj.setOnClickListener(new 8(this));
        this.bok.setOnClickListener(new 9(this, info));
        this.bol.setOnClickListener(new 10(this, info));
        this.bom.setOnClickListener(new 11(this, info));
        this.bon.setOnClickListener(new 12(this, info));
        this.boo.setOnClickListener(new 13(this, info));
        this.eP.setOnClickListener(new 2(this));
        this.bop.setTag(info);
        this.bop.setOnClickListener(this.bou);
        this.view.setVisibility(0);
    }

    private boolean c(e info) {
        if (info == null) {
            return false;
        }
        long myVercode = (long) UtilsVersion.getVersionCode(this.boq.getActivity());
        String myPackname = UtilsApkPackage.getAppPackageName(this.boq.getActivity());
        if (info.versioncode <= myVercode || myPackname == null || !info.packname.equals(myPackname)) {
            return false;
        }
        return true;
    }

    private void Z(String downloadUrl, int resType) {
        ResTaskInfo taskInfo = ResourceCtrl.getInstance().getTaskInfo(downloadUrl, resType);
        if (taskInfo != null) {
            a(LayoutIndex.Downloading);
            DownloadRecord record;
            if (taskInfo.state == State.DOWNLOAD_ERROR.ordinal()) {
                record = taskInfo.mN;
                HLog.verbose(TAG, "reloadProgress when error = " + record.error, new Object[0]);
                this.bop.setText("重试");
                if (record.total > 0) {
                    a(record, "下载失败请重试");
                }
                if (ErrorCode.isResume(record.error)) {
                    t.n(getActivity(), "下载中断啦，请继续下载");
                    a(record, "下载失败请重试");
                    return;
                }
                t.n(getActivity(), "出错啦, 请删除后重新下载");
                a(record, "下载失败请重试");
                return;
            } else if (taskInfo.state == State.INIT.ordinal() || taskInfo.state == State.WAITING.ordinal() || taskInfo.state == State.PREPARE.ordinal() || taskInfo.state == State.DOWNLOAD_START.ordinal()) {
                HLog.verbose(TAG, "reloadProgress when init ", new Object[0]);
                this.bop.setText("暂停");
                record = taskInfo.mN;
                if (record == null) {
                    a(record, "任务等待中...请稍候");
                    return;
                } else if (record.total == 0) {
                    a(record, "任务等待中...请稍候");
                    return;
                } else if (record.progress == 0) {
                    a(record, "任务等待中...请稍候");
                    return;
                } else {
                    return;
                }
            } else if (taskInfo.state == State.DOWNLOAD_PAUSE.ordinal()) {
                record = taskInfo.mN;
                HLog.verbose(TAG, "reloadProgress when pause ", new Object[0]);
                this.bop.setText("继续");
                if (record == null) {
                    a(record, "已暂停下载任务");
                    return;
                } else if (record.total > 0) {
                    a(record, "已暂停下载任务");
                    return;
                } else {
                    a(record, "已暂停下载任务");
                    return;
                }
            } else if (taskInfo.state == State.FILE_DELETE.ordinal()) {
                HLog.verbose(TAG, "reloadProgress when file not exist ", new Object[0]);
                a(LayoutIndex.Download);
                return;
            } else if (taskInfo.state == State.SUCC.ordinal()) {
                HLog.verbose(TAG, "reloadProgress when COMPLETION ", new Object[0]);
                a(taskInfo.mN, null);
                if (aa(downloadUrl, resType)) {
                    dismissAllowingStateLoss();
                    return;
                }
                d info = g.MS().gE(downloadUrl);
                if (info != null) {
                    info.downloadStatus = 2;
                    g.MS().b(info);
                }
                this.boi.setText("安装失败，请重试");
                a(LayoutIndex.Retry);
                return;
            } else {
                record = taskInfo.mN;
                this.bop.setText("暂停");
                if (record == null) {
                    a(record, "任务等待中...请稍候");
                    return;
                } else if (record.total > 0) {
                    a(record, null);
                    return;
                } else {
                    a(record, "任务等待中...请稍候");
                    return;
                }
            }
        }
        HLog.verbose(TAG, "reloadProgress when record null ", new Object[0]);
        a(LayoutIndex.Download);
    }

    private void d(e info) {
        d apkDbInfo = g.MS().gE(info.url);
        d patchDbInfo = null;
        if (!UtilsFunction.empty(info.patchurl)) {
            patchDbInfo = g.MS().gE(info.patchurl);
        }
        if (apkDbInfo != null) {
            ResTaskInfo apkTaskInfo = ResourceCtrl.getInstance().getTaskInfo(info.url, 0);
            if (!(apkTaskInfo == null || apkTaskInfo.mN == null || apkTaskInfo.mN.pause)) {
                ResourceCtrl.getInstance().pauseTask(apkTaskInfo);
            }
        }
        if (patchDbInfo != null) {
            ResTaskInfo patchTaskInfo = ResourceCtrl.getInstance().getTaskInfo(info.patchurl, 22);
            if (patchTaskInfo != null && patchTaskInfo.mN != null && !patchTaskInfo.mN.pause) {
                ResourceCtrl.getInstance().pauseTask(patchTaskInfo);
            }
        }
    }

    private void e(e info) {
        d apkDbInfo = g.MS().gE(info.url);
        d patchDbInfo = null;
        if (!UtilsFunction.empty(info.patchurl)) {
            patchDbInfo = g.MS().gE(info.patchurl);
        }
        ResTaskInfo patchTaskInfo;
        if (apkDbInfo != null) {
            if (patchDbInfo != null) {
                patchTaskInfo = ResourceCtrl.getInstance().getTaskInfo(info.patchurl, 22);
                if (!(patchTaskInfo == null || patchTaskInfo.mN == null)) {
                    ResourceCtrl.getInstance().pauseTask(patchTaskInfo);
                }
            }
            ResTaskInfo apkTaskInfo = ResourceCtrl.getInstance().getTaskInfo(info.url, 0);
            if (apkTaskInfo != null && apkTaskInfo.mN != null) {
                if (apkTaskInfo.mN.pause) {
                    a(info, 0);
                } else {
                    ResourceCtrl.getInstance().pauseTask(apkTaskInfo);
                }
                gD(info.url);
            }
        } else if (patchDbInfo != null) {
            patchTaskInfo = ResourceCtrl.getInstance().getTaskInfo(info.patchurl, 22);
            if (patchTaskInfo != null && patchTaskInfo.mN != null) {
                if (patchTaskInfo.mN.pause) {
                    a(info, 22);
                } else {
                    ResourceCtrl.getInstance().pauseTask(patchTaskInfo);
                }
                gD(info.patchurl);
            }
        }
    }

    private void a(e info, int resType) {
        d dbInfo;
        String downloadUrl;
        long allSize;
        String filename;
        String finalFileName;
        HLog.info(this, "performDownload url(%s) patchurl(%s) resType(%d)", info.url, info.patchurl, Integer.valueOf(resType));
        boolean isPatch = resType == 22;
        if (isPatch) {
            dbInfo = d.getDbInfo(info, isPatch, 22);
            downloadUrl = info.patchurl;
            allSize = info.patchsize;
            filename = info.packname + ".patch";
            finalFileName = info.packname + ".patch";
        } else {
            dbInfo = d.getDbInfo(info, isPatch, 0);
            downloadUrl = info.url;
            allSize = info.size;
            filename = info.packname + ".apk";
            finalFileName = info.packname + ".apk";
        }
        dbInfo.downloadStatus = 0;
        com.MCWorld.controller.b.dI();
        long freeSpace = UtilsFile.availableSpace(com.MCWorld.controller.b.dE().dH());
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(downloadUrl);
        long j = (record == null || ErrorCode.isRestart(record.error)) ? 0 : record.progress;
        if (((long) (((float) (allSize - j)) * 1.3f)) > freeSpace) {
            t.n(this.boq.getActivity(), "空间不足了，请清理空间再下载。");
            return;
        }
        if (resType == 22) {
            a patchTaskInfo = (a) com.MCWorld.controller.resource.bean.a.b(a.class);
            patchTaskInfo.url = downloadUrl;
            patchTaskInfo.mM = resType;
            patchTaskInfo.mS_appTitle = info.name;
            patchTaskInfo.filename = filename;
            patchTaskInfo.mV_fileName = finalFileName;
            patchTaskInfo.packagename = info.packname;
            patchTaskInfo.na = true;
            patchTaskInfo.nb = false;
            this.mUrl = downloadUrl;
            ResourceCtrl.getInstance().addTask(patchTaskInfo);
        } else {
            ResTaskInfo resTaskInfo = com.MCWorld.controller.resource.bean.a.dM();
            resTaskInfo.url = downloadUrl;
            resTaskInfo.mM = resType;
            resTaskInfo.mS_appTitle = info.name;
            resTaskInfo.filename = filename;
            resTaskInfo.mV_fileName = finalFileName;
            resTaskInfo.na = true;
            resTaskInfo.nb = false;
            this.mUrl = downloadUrl;
            ResourceCtrl.getInstance().addTask(resTaskInfo);
        }
        g.MS().b(dbInfo);
    }

    private void gD(String url) {
        d dbInfo = g.MS().gE(url);
        HLog.info(TAG, "dbinfo(%s)", dbInfo);
        if (dbInfo == null) {
            a(LayoutIndex.Download);
        } else {
            Z(url, dbInfo.restype);
        }
    }

    private void a(DownloadRecord record, String msg) {
        if (msg != null) {
            this.boi.setText(msg);
        } else if (record != null && record.total > 0) {
            this.boi.setText("安装包下载中：" + ((int) (100.0f * (((float) record.progress) / ((float) record.total)))) + "%");
        }
    }

    private boolean aa(String url, int resType) {
        d dbInfo = g.MS().gE(url);
        if (dbInfo != null) {
            ResTaskInfo taskInfo = ResourceCtrl.getInstance().getTaskInfo(url, resType);
            if (!(taskInfo == null || taskInfo.state != State.SUCC.ordinal() || taskInfo.mN == null)) {
                File existFile = new File(taskInfo.mN.dir, taskInfo.mN.name);
                if (taskInfo.mN.state == DownloadRecord.State.COMPLETION.state && existFile.exists()) {
                    this.boi.setText("开始安装...");
                    if (resType == 0) {
                        UtilsApkPackage.runInstallApp(getActivity(), existFile.getAbsolutePath());
                        return true;
                    } else if (UtilsFile.isExist(com.MCWorld.controller.resource.handler.impl.b.nN) && UtilsFile.getFileMD5(com.MCWorld.controller.resource.handler.impl.b.nN).equals(dbInfo.md5)) {
                        UtilsApkPackage.runInstallApp(getActivity(), com.MCWorld.controller.resource.handler.impl.b.nN);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void f(e info) {
        if (!UtilsFunction.empty(info.url)) {
            d dbInfo;
            if ((j(info) ? 22 : 0) == 22) {
                dbInfo = g.MS().gE(info.patchurl);
            } else {
                dbInfo = g.MS().gE(info.url);
            }
            if (dbInfo != null) {
                a(LayoutIndex.DownloadResume);
                this.boi.setText("检测到该安装包已存在下载任务中。您想继续上次下载吗？");
                return;
            }
            g(info);
        }
    }

    private void g(e info) {
        if (!UtilsFunction.empty(info.url)) {
            b(info, j(info) ? 22 : 0);
        }
    }

    private void h(e info) {
        if (!UtilsFunction.empty(info.url)) {
            b(info, 0);
            this.boi.setText(info.content);
        }
    }

    private void i(e info) {
        if (info != null) {
            ResTaskInfo resTaskInfo;
            DownloadRecord record;
            File file;
            HLog.verbose(TAG, "delete file ever download", new Object[0]);
            if (g.MS().gE(info.url) != null) {
                resTaskInfo = com.MCWorld.controller.resource.bean.a.dM();
                resTaskInfo.url = info.url;
                resTaskInfo.filename = info.name + ".apk";
                resTaskInfo.mM = 0;
                ResourceCtrl.getInstance().deleteTask(resTaskInfo);
                record = DownloadMemCache.getInstance().getRecord(info.url);
                if (record != null) {
                    file = new File(record.dir, record.name);
                    if (file.exists()) {
                        file.delete();
                    }
                    DownloadMemCache.getInstance().deleteRecord(info.url);
                } else {
                    g.MS().deleteRecord(info.url);
                }
            }
            if (g.MS().gE(info.patchurl) != null) {
                resTaskInfo = com.MCWorld.controller.resource.bean.a.dM();
                resTaskInfo.url = info.patchurl;
                resTaskInfo.filename = info.name + ".patch";
                resTaskInfo.mM = 22;
                ResourceCtrl.getInstance().deleteTask(resTaskInfo);
                record = DownloadMemCache.getInstance().getRecord(info.patchurl);
                if (record != null) {
                    file = new File(record.dir, record.name);
                    if (file.exists()) {
                        file.delete();
                    }
                    DownloadMemCache.getInstance().deleteRecord(info.patchurl);
                    return;
                }
                g.MS().deleteRecord(info.patchurl);
            }
        }
    }

    private void b(e info, int resType) {
        String downloadUrl;
        long allSize;
        String filename;
        String finalFileName;
        d dbInfo;
        if (resType == 22) {
            downloadUrl = info.patchurl;
            allSize = info.patchsize;
            filename = info.packname + ".patch";
            finalFileName = info.packname + ".patch";
            dbInfo = g.MS().gE(downloadUrl);
        } else {
            downloadUrl = info.url;
            allSize = info.size;
            filename = info.packname + ".apk";
            finalFileName = info.packname + ".apk";
            dbInfo = g.MS().gE(downloadUrl);
        }
        if (dbInfo == null) {
            a(info, resType);
        } else {
            ResTaskInfo taskInfo = ResourceCtrl.getInstance().getTaskInfo(downloadUrl, resType);
            a patchTaskInfo;
            if (taskInfo != null) {
                long available = UtilsFile.availableSpace(com.MCWorld.controller.b.dE().getDownloadPath());
                long j = (taskInfo.mN == null || ErrorCode.isRestart(taskInfo.mN.error)) ? 0 : taskInfo.mN.progress;
                if (available < ((long) (((float) (allSize - j)) * 1.3f))) {
                    t.n(getActivity(), "下载空间不足，请清理空间后重试");
                    return;
                }
                if (taskInfo.state == State.FILE_DELETE.ordinal() || taskInfo.state == State.DOWNLOAD_PAUSE.ordinal() || taskInfo.state == State.UNZIP_NOT_START.ordinal()) {
                    taskInfo.mS_appTitle = info.name;
                    taskInfo.mV_fileName = finalFileName;
                    if (resType == 22) {
                        patchTaskInfo = a.b(taskInfo);
                        patchTaskInfo.packagename = info.packname;
                        this.mUrl = downloadUrl;
                        patchTaskInfo.na = true;
                        patchTaskInfo.nb = false;
                        ResourceCtrl.getInstance().addTask(patchTaskInfo);
                    } else {
                        this.mUrl = downloadUrl;
                        taskInfo.na = true;
                        taskInfo.nb = false;
                        ResourceCtrl.getInstance().addTask(taskInfo);
                    }
                }
            } else if (UtilsFile.availableSpace(com.MCWorld.controller.b.dE().getDownloadPath()) < ((long) (((float) info.size) * 1.3f))) {
                t.n(getActivity(), "下载空间不足，请清理空间后重试");
                return;
            } else if (resType == 22) {
                patchTaskInfo = (a) com.MCWorld.controller.resource.bean.a.b(a.class);
                patchTaskInfo.url = downloadUrl;
                patchTaskInfo.mM = resType;
                patchTaskInfo.mS_appTitle = info.name;
                patchTaskInfo.filename = filename;
                patchTaskInfo.mV_fileName = finalFileName;
                patchTaskInfo.na = true;
                patchTaskInfo.nb = false;
                this.mUrl = downloadUrl;
                ResourceCtrl.getInstance().addTask(patchTaskInfo);
                return;
            } else {
                ResTaskInfo resTaskInfo = com.MCWorld.controller.resource.bean.a.dM();
                resTaskInfo.url = downloadUrl;
                resTaskInfo.mM = resType;
                resTaskInfo.mS_appTitle = info.name;
                resTaskInfo.filename = filename;
                resTaskInfo.mV_fileName = finalFileName;
                resTaskInfo.na = true;
                resTaskInfo.nb = false;
                this.mUrl = downloadUrl;
                ResourceCtrl.getInstance().addTask(resTaskInfo);
                return;
            }
        }
        gD(downloadUrl);
    }

    private boolean j(e info) {
        if (g.MS().gE(info.url) != null) {
            HLog.debug("VersionDialog", "isapk  apkDbInfo != null", new Object[0]);
            return false;
        } else if (UtilsFunction.empty(info.patchurl)) {
            HLog.debug("VersionDialog", "isapk  UtilsFunction.empty(info.patchurl)", new Object[0]);
            return false;
        } else {
            d apkDbInfo = g.MS().gE(info.patchurl);
            if (apkDbInfo == null || apkDbInfo.downloadStatus != 2) {
                return true;
            }
            HLog.debug("VersionDialog", "isapk  apkDbInfo.downloadStatus == 2", new Object[0]);
            return false;
        }
    }

    private void a(LayoutIndex index) {
        if (index == LayoutIndex.Download) {
            this.boe.setVisibility(0);
            this.bof.setVisibility(8);
            this.bog.setVisibility(8);
            this.boh.setVisibility(8);
        } else if (index == LayoutIndex.Retry) {
            this.boe.setVisibility(8);
            this.bof.setVisibility(0);
            this.bog.setVisibility(8);
            this.boh.setVisibility(8);
        } else if (index == LayoutIndex.DownloadResume) {
            this.boe.setVisibility(8);
            this.bof.setVisibility(8);
            this.bog.setVisibility(0);
            this.boh.setVisibility(8);
        } else {
            this.boe.setVisibility(8);
            this.bof.setVisibility(8);
            this.bog.setVisibility(8);
            this.boh.setVisibility(0);
        }
    }
}
