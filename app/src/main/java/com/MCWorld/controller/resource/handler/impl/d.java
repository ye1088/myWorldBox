package com.MCWorld.controller.resource.handler.impl;

import android.support.annotation.y;
import com.MCWorld.controller.resource.action.c;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.dispatcher.a;
import com.MCWorld.controller.resource.handler.base.b;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.download.DownloadReporter;
import com.MCWorld.framework.base.http.toolbox.error.ErrorCode;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import java.io.File;
import java.lang.ref.WeakReference;

/* compiled from: DownloadResImplHandler */
public abstract class d<T extends ResTaskInfo> extends b<T> {
    private static final String TAG = "DownloadResImplHandler";
    protected WeakReference<a> dispatcher;
    private com.MCWorld.controller.resource.action.a downloader = new com.MCWorld.controller.resource.action.a(this);
    protected volatile boolean mPauseDeleteFile = false;
    protected volatile boolean mPauseDeleteRecord = false;
    protected DownloadReporter mReporter = new DownloadReporter();
    private c pauseDownloadAction;

    public abstract void onDownloadComplete(Object obj, @y DownloadRecord downloadRecord);

    public d(T info) {
        super(info);
    }

    public boolean process(a dispatcher) {
        if (this.pauseDownloadAction != null) {
            HLog.info(TAG, "task pause before process url %s", ((ResTaskInfo) getInfo()).url);
            return true;
        }
        ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_START.ordinal();
        this.dispatcher = new WeakReference(dispatcher);
        return this.downloader.run();
    }

    public void pause(boolean delete, boolean deleteFile) {
        this.mPauseDeleteRecord = delete;
        this.mPauseDeleteFile = deleteFile;
        this.pauseDownloadAction = new c(((ResTaskInfo) getInfo()).url);
        this.pauseDownloadAction.run();
    }

    public boolean prepare() throws Exception {
        HLog.verbose(TAG, "prepare...", new Object[0]);
        ((ResTaskInfo) getInfo()).state = State.PREPARE.ordinal();
        EventNotifyCenter.notifyEventUiThread(com.MCWorld.controller.c.class, 256, ((ResTaskInfo) getInfo()).url);
        return false;
    }

    public void onProgress(String url, long length, long progress, float rate) {
        HLog.verbose(TAG, "progress length %d, progress %d, rate %f", Long.valueOf(length), Long.valueOf(progress), Float.valueOf(rate));
        ((ResTaskInfo) getInfo()).mN = DownloadMemCache.getInstance().getRecord(url);
        ((ResTaskInfo) getInfo()).dir = ((ResTaskInfo) getInfo()).mN.dir;
        ((ResTaskInfo) getInfo()).filename = ((ResTaskInfo) getInfo()).mN.name;
        ((ResTaskInfo) getInfo()).mO = rate;
        ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_PROGRESS.ordinal();
    }

    public void onResponse(Object response) {
        try {
            ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_COMPLETE.ordinal();
            EventNotifyCenter.notifyEvent(com.MCWorld.controller.c.class, 263, ((ResTaskInfo) getInfo()).url);
            DownloadRecord record = DownloadMemCache.getInstance().getRecord(((ResTaskInfo) getInfo()).url);
            if (record == null) {
                DownloadMemCache.getInstance().deleteRecord(((ResTaskInfo) getInfo()).url);
                throw new IllegalStateException("handle onResponse no record");
            }
            onDownloadComplete(response, record);
            destroy();
        } catch (Exception e) {
            HLog.error(TAG, "download complete process e " + e + ", info " + getInfo(), new Object[0]);
        }
    }

    public void onCancel() {
        if (this.mPauseDeleteRecord) {
            this.mReporter.deleteRecord(((ResTaskInfo) getInfo()).url);
            if (this.mPauseDeleteFile) {
                new File(((ResTaskInfo) getInfo()).dir, ((ResTaskInfo) getInfo()).filename).delete();
            }
            HLog.info(TAG, "delete handler, info " + getInfo() + ", delete record " + this.mPauseDeleteRecord + ", delete file " + this.mPauseDeleteFile, new Object[0]);
        } else {
            ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_PAUSE.ordinal();
        }
        destroy();
    }

    public void onErrorResponse(VolleyError error) {
        ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_ERROR.ordinal();
        ((ResTaskInfo) getInfo()).mN = DownloadMemCache.getInstance().getRecord(((ResTaskInfo) getInfo()).url);
        EventNotifyCenter.notifyEventUiThread(com.MCWorld.controller.c.class, 264, ((ResTaskInfo) getInfo()).url, Integer.valueOf(VolleyError.getErrorId(error)));
        destroy();
    }

    public a getDispatcher() {
        if (this.dispatcher != null) {
            return (a) this.dispatcher.get();
        }
        return null;
    }

    public void destroy() {
        a dispatcher = getDispatcher();
        if (dispatcher != null) {
            dispatcher.finish();
            return;
        }
        HLog.error(TAG, "destroy handler dispatcher null, info %s, may be task has not begun to process", getInfo());
    }

    private boolean canResume(VolleyError error) {
        return ErrorCode.isResume(VolleyError.getErrorId(error));
    }
}
