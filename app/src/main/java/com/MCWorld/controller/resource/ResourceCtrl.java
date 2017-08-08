package com.MCWorld.controller.resource;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat$Builder;
import com.MCWorld.controller.e.e;
import com.MCWorld.controller.resource.DownloadService.a.a;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.dispatcher.TaskDispatcher;
import com.MCWorld.controller.resource.handler.base.b;
import com.MCWorld.controller.resource.handler.segments.f;
import com.MCWorld.controller.resource.zip.c;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.async.AsyncTaskCenter;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.download.DownloadReporter;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsApkPackage;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.avutil;

public class ResourceCtrl implements a {
    public static final String NOTIFICATION_REDIRECT = "notification_redirect";
    private static final int POOL_SIZE = 3;
    private static final String TAG = "ResourceCtrl";
    private Map<Integer, Class<? extends b<? extends ResTaskInfo>>> handleClz;
    private DownloadService.a mClient;
    private List<TaskDispatcher> mDispatchers;
    private CallbackHandler mDownloadCallback;
    private boolean mForeground;
    private Map<Integer, Long> mNotificationWhen;
    private Map<String, ResTaskInfo> mPauseDeleteRestartTask;
    private Map<String, ResTaskInfo> mPauseRestartTask;
    private Map<String, ResTaskInfo> mPausingDeleteTask;
    private Map<String, ResTaskInfo> mPausingTask;
    private Map<String, com.MCWorld.controller.resource.handler.base.a<? extends ResTaskInfo>> mResourceHandlers;
    private DownloadService mService;
    private CallbackHandler mTaskCallback;
    private final PriorityBlockingQueue<ResTaskInfo> mTaskQ;

    public void onConnected(DownloadService service) {
        HLog.info(TAG, "resource controller connected, service %s", service);
        this.mService = service;
        for (int i = 0; i < 3; i++) {
            TaskDispatcher<ResTaskInfo> dispatcher = new TaskDispatcher(this.mTaskQ);
            dispatcher.start();
            this.mDispatchers.add(dispatcher);
        }
    }

    public void onDisconnected() {
        HLog.info(TAG, "resource controller disconnected, service %s", this.mService);
        this.mService = null;
    }

    public void startServiceForeground(ResTaskInfo task) {
        if (this.mService == null) {
            HLog.error(TAG, "start foreground service null", new Object[0]);
            return;
        }
        Context context = AppConfig.getInstance().getAppContext();
        long when = System.currentTimeMillis();
        this.mNotificationWhen.put(Integer.valueOf(task.mS_appTitle.hashCode()), Long.valueOf(when));
        String className;
        if (UtilsApkPackage.getAppPackageName(AppConfig.getInstance().getAppContext()).equals("com.huati")) {
            className = "com.huluxia.ui.home.HomeActivity";
        } else {
            className = "com.huluxia.ui.home.ToolHomeActivity";
        }
        Intent notifyIntent = new Intent();
        notifyIntent.setClassName(context.getPackageName(), UtilsFunction.empty(task.mT) ? "com.huluxia.ui.home.ToolHomeActivity" : task.mT);
        notifyIntent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
        notifyIntent.putExtra(NOTIFICATION_REDIRECT, true);
        Notification notification = new NotificationCompat$Builder(context).setSmallIcon(task.mK > 0 ? task.mK : e.ic_launcher).setContentTitle(task.mS_appTitle).setContentText("准备下载....").setProgress(100, 0, false).setOngoing(false).setWhen(when).setDefaults(96).setPriority(0).setContentIntent(PendingIntent.getActivity(context, 0, notifyIntent, avutil.AV_CPU_FLAG_AVXSLOW)).build();
        this.mForeground = true;
        if (this.mForeground) {
            ((NotificationManager) AppConfig.getInstance().getAppContext().getSystemService("notification")).notify(task.mS_appTitle.hashCode(), notification);
        } else {
            this.mService.startForeground(task.mS_appTitle.hashCode(), notification);
        }
    }

    public void stopServiceForeground(ResTaskInfo task) {
        if (this.mService == null) {
            HLog.error(TAG, "stop mForeground service null", new Object[0]);
            return;
        }
        this.mNotificationWhen.remove(Integer.valueOf(task.mS_appTitle.hashCode()));
        ((NotificationManager) AppConfig.getInstance().getAppContext().getSystemService("notification")).cancel(task.mS_appTitle.hashCode());
        boolean running = false;
        for (TaskDispatcher dispatcher : this.mDispatchers) {
            if (running || dispatcher.isRunning()) {
                running = true;
            } else {
                running = false;
            }
        }
        if (!running) {
            this.mForeground = false;
            this.mService.stopForeground(true);
        }
    }

    public void updateNotification() {
        for (TaskDispatcher dispatcher : this.mDispatchers) {
            if (dispatcher.isRunning()) {
                ResTaskInfo task = dispatcher.dN();
                if (task.state == State.DOWNLOAD_PROGRESS.ordinal()) {
                    long when;
                    Context context = AppConfig.getInstance().getAppContext();
                    if (this.mNotificationWhen.containsKey(Integer.valueOf(task.mS_appTitle.hashCode()))) {
                        when = ((Long) this.mNotificationWhen.get(Integer.valueOf(task.mS_appTitle.hashCode()))).longValue();
                    } else {
                        when = System.currentTimeMillis();
                    }
                    String className = UtilsApkPackage.getAppPackageName(AppConfig.getInstance().getAppContext()).equals("com.huati") ? "com.huluxia.ui.home.HomeActivity" : "com.huluxia.ui.home.ToolHomeActivity";
                    Intent notifyIntent = new Intent();
                    notifyIntent.setClassName(context.getPackageName(), UtilsFunction.empty((CharSequence) task.mT) ? className : task.mT);
                    notifyIntent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
                    notifyIntent.putExtra(NOTIFICATION_REDIRECT, true);
                    ((NotificationManager) AppConfig.getInstance().getAppContext().getSystemService("notification")).notify(task.mS_appTitle.hashCode(), new NotificationCompat$Builder(context).setSmallIcon(task.mK > 0 ? task.mK : e.ic_launcher).setContentTitle(task.mS_appTitle).setContentText(String.format("正在下载(%d", new Object[]{Integer.valueOf((int) ((((float) task.mN.progress) / ((float) task.mN.total)) * 100.0f))}) + "%)").setProgress((int) task.mN.total, (int) task.mN.progress, false).setOngoing(false).setWhen(when).setDefaults(96).setPriority(0).setContentIntent(PendingIntent.getActivity(context, 0, notifyIntent, avutil.AV_CPU_FLAG_AVXSLOW)).build());
                }
            }
        }
    }

    private ResourceCtrl() {
        this.mTaskQ = new PriorityBlockingQueue();
        this.mResourceHandlers = new Hashtable();
        this.mPausingTask = new Hashtable();
        this.mPausingDeleteTask = new Hashtable();
        this.mPauseRestartTask = new Hashtable();
        this.mPauseDeleteRestartTask = new Hashtable();
        this.mDispatchers = new ArrayList();
        this.mForeground = false;
        this.mNotificationWhen = new HashMap();
        this.handleClz = new Hashtable();
        this.mDownloadCallback = new CallbackHandler(this) {
            final /* synthetic */ ResourceCtrl mC;

            {
                this.mC = this$0;
            }

            @MessageHandler(message = 258)
            public void onProgress(String url, String path, ProgressInfo progressInfo) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 256)
            public void onDownloadSucc(String url, String path) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 259)
            public void onDownloadCancel(String url, String path) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 257)
            public void onDownloadError(String url, String path, Object context) {
                this.mC.updateNotification();
            }
        };
        this.mTaskCallback = new CallbackHandler(this) {
            final /* synthetic */ ResourceCtrl mC;

            {
                this.mC = this$0;
            }

            @MessageHandler(message = 258)
            public void onFinish(String url) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 256)
            public void onTaskPrepare(String url) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 257)
            public void onTaskWaiting(String url) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 259)
            public void onDownloadErrorRetry(String oldUrl, String newUrl, long errid) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 260)
            public void onUnzipStart(String url) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 261)
            public void onUnzipProgress(String url) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 262)
            public void onUnzipComplete(String url) {
                this.mC.updateNotification();
            }

            @MessageHandler(message = 263)
            public void onDownloadComplete(String url) {
                this.mC.updateNotification();
            }
        };
        AppConfig.getInstance().setResCtrlHttpMgr(com.MCWorld.controller.resource.http.a.dX());
        com.MCWorld.controller.resource.http.a.dX().init(AppConfig.getInstance().getAppContext());
        c.ea().init(0.1f, getHpkUnzipHistoryDir());
        this.mClient = new DownloadService.a(AppConfig.getInstance().getAppContext(), this);
        this.mClient.connect();
        EventNotifyCenter.add(BaseEvent.class, this.mDownloadCallback);
        EventNotifyCenter.add(com.MCWorld.controller.c.class, this.mTaskCallback);
    }

    public DownloadService getService() {
        return this.mService;
    }

    public static ResourceCtrl getInstance() {
        return b.dK();
    }

    public boolean isDownloading(ResTaskInfo info) {
        if (info == null) {
            return false;
        }
        if (this.mTaskQ.contains(info) || this.mResourceHandlers.containsKey(info.url)) {
            return true;
        }
        return false;
    }

    public void addTask(ResTaskInfo info) {
        if (this.mTaskQ.contains(info)) {
            HLog.error(TAG, "task is in q waiting info %s", info);
        } else if (UtilsFunction.empty(info.url)) {
            HLog.error(TAG, "url should not be null " + info, new Object[0]);
        } else if (this.mResourceHandlers.containsKey(info.url)) {
            ResTaskInfo pausingTask = (ResTaskInfo) this.mPausingTask.get(info.url);
            if (pausingTask != null) {
                this.mPauseRestartTask.put(info.url, info);
            }
            HLog.warn(TAG, "task is running " + info + ", pausing task " + pausingTask, new Object[0]);
        } else if (((ResTaskInfo) this.mPausingDeleteTask.get(info.url)) != null) {
            this.mPauseDeleteRestartTask.put(info.url, info);
            HLog.warn(TAG, "task is deleting " + info, new Object[0]);
        } else {
            info.state = State.WAITING.ordinal();
            if (UtilsFunction.empty(info.mS_appTitle)) {
                info.mS_appTitle = info.filename;
            }
            this.mTaskQ.add(info);
            EventNotifyCenter.notifyEventUiThread(com.MCWorld.controller.c.class, 257, info.url);
            EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 269, new Object[0]);
        }
    }

    public List<ResTaskInfo> getDownloading() {
        List<ResTaskInfo> downloadingTasks = new ArrayList();
        Iterator it = this.mTaskQ.iterator();
        while (it.hasNext()) {
            downloadingTasks.add((ResTaskInfo) it.next());
        }
        for (com.MCWorld.controller.resource.handler.base.a<? extends ResTaskInfo> handler : this.mResourceHandlers.values()) {
            downloadingTasks.add(handler.getInfo());
        }
        return downloadingTasks;
    }

    public void pauseTask(ResTaskInfo info) {
        destroyTask(info, false, false);
    }

    public void deleteTask(ResTaskInfo info) {
        deleteTask(info, false);
    }

    public void deleteTask(ResTaskInfo info, boolean deleteFile) {
        destroyTask(info, true, deleteFile);
    }

    private void destroyTask(ResTaskInfo info, boolean delete, boolean deleteFile) {
        if (info == null) {
            HLog.error(TAG, "destroy task info is NULL, delete " + delete + ", deleteFile " + deleteFile, new Object[0]);
        } else if (this.mTaskQ.contains(info)) {
            this.mTaskQ.remove(info);
            HLog.info(TAG, "pause task int Q, info %s, delete %b", info, Boolean.valueOf(delete));
        } else if (info.url != null && this.mResourceHandlers.containsKey(info.url)) {
            HLog.info(TAG, "pause task in cache handlers, info %s, delete %b, deleteFile %b", info, Boolean.valueOf(delete), Boolean.valueOf(deleteFile));
            this.mPausingTask.put(info.url, info);
            ((com.MCWorld.controller.resource.handler.base.a) this.mResourceHandlers.get(info.url)).pause(delete, deleteFile);
        } else if (delete && info.url != null) {
            HLog.info(TAG, "pause task not in cache handlers, info %s, delete %b, deleteFile %b", info, Boolean.valueOf(delete), Boolean.valueOf(deleteFile));
            this.mPausingDeleteTask.put(info.url, info);
            AsyncTaskCenter.getInstance().executeSingleThread(new 1(this, deleteFile, info));
        }
    }

    private void deleteRecord(String url) {
        new DownloadReporter().deleteRecord(url);
    }

    public ResTaskInfo getTaskInfo(String url, int resourceType) {
        ResTaskInfo info = null;
        Iterator<? extends ResTaskInfo> it = this.mTaskQ.iterator();
        while (it.hasNext()) {
            ResTaskInfo item = (ResTaskInfo) it.next();
            if (item.url.equals(url)) {
                info = item;
                break;
            }
        }
        if (info != null) {
            return info;
        }
        if (this.mResourceHandlers.containsKey(url)) {
            return (ResTaskInfo) ((com.MCWorld.controller.resource.handler.base.a) this.mResourceHandlers.get(url)).getInfo();
        }
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(url);
        if (record != null) {
            return a.a(record, resourceType);
        }
        f table = com.MCWorld.controller.resource.handler.segments.a.an(url);
        if (table != null) {
            record = com.MCWorld.controller.resource.handler.segments.a.getRecord(url);
            if (record != null) {
                info = a.a(record, resourceType);
                ResTaskInfo.a segmentTable = new ResTaskInfo.a();
                segmentTable.id = url;
                Set<ResTaskInfo.b> urls = new HashSet();
                for (f.a segment : table.oo) {
                    urls.add(new ResTaskInfo.b(segment.oq));
                }
                segmentTable.nc.addAll(urls);
                info.mZ = segmentTable;
                return info;
            }
        }
        return null;
    }

    public <T extends com.MCWorld.controller.resource.handler.base.a<? extends ResTaskInfo>> void addRunningHandler(String url, T handler) {
        HLog.info(TAG, "add running handle url %s", url);
        this.mResourceHandlers.put(url, handler);
        EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 267, new Object[0]);
    }

    public com.MCWorld.controller.resource.handler.base.a<? extends ResTaskInfo> removeRunningHandler(String url) {
        HLog.info(TAG, "remove running handle url %s", url);
        com.MCWorld.controller.resource.handler.base.a<? extends ResTaskInfo> handler = (com.MCWorld.controller.resource.handler.base.a) this.mResourceHandlers.remove(url);
        EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 267, new Object[0]);
        this.mPausingTask.remove(url);
        ResTaskInfo taskInfo = (ResTaskInfo) this.mPauseRestartTask.remove(url);
        if (taskInfo != null) {
            HLog.info(TAG, "restart pausing task url %s", url);
            addTask(taskInfo);
        }
        return handler;
    }

    public void registerHandler(int resourceType, Class<? extends b<? extends ResTaskInfo>> handlerClass) {
        this.handleClz.put(Integer.valueOf(resourceType), handlerClass);
    }

    public void unregisterHandler(int resourceType) {
        this.handleClz.remove(Integer.valueOf(resourceType));
    }

    public Class<? extends b<? extends ResTaskInfo>> getHandleClz(int resourceType) {
        return (Class) this.handleClz.get(Integer.valueOf(resourceType));
    }

    private static String getHpkUnzipHistoryDir() {
        return UtilsFile.getDiskCacheDir(AppConfig.getInstance().getAppContext(), com.MCWorld.controller.resource.http.a.dX().getDownloadCachePath()) + File.separator + "unhpk-history";
    }
}
