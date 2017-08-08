package com.MCWorld.controller.resource.handler.segments;

import com.MCWorld.controller.c;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo$a;
import com.MCWorld.controller.resource.bean.ResTaskInfo$b;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.dispatcher.a;
import com.MCWorld.controller.resource.handler.base.b;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.download.DownloadReporter;
import com.MCWorld.framework.base.http.toolbox.error.CreateDirectoryError;
import com.MCWorld.framework.base.http.toolbox.error.ErrorCode;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.MarkerLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/* compiled from: SegmentsHandler */
public abstract class g extends b<ResTaskInfo> {
    private static final String TAG = "SegmentsHandler";
    protected volatile boolean mPauseDeleteFile = false;
    protected volatile boolean mPauseDeleteRecord = false;
    protected DownloadReporter mReporter = new DownloadReporter();
    private d nT;
    private f nU;
    protected volatile boolean oi = false;
    protected WeakReference<a> ot;
    private ResTaskInfo$a ou;
    private List<f.a> ov = new ArrayList();
    private MarkerLog ow = new MarkerLog();

    protected abstract void dQ();

    public g(ResTaskInfo info) {
        super(info);
        this.ou = info.mZ;
        Collections.sort(info.mZ.nc, new Comparator<ResTaskInfo$b>(this) {
            final /* synthetic */ g ox;

            {
                this.ox = this$0;
            }

            public /* synthetic */ int compare(Object obj, Object obj2) {
                return a((ResTaskInfo$b) obj, (ResTaskInfo$b) obj2);
            }

            public int a(ResTaskInfo$b lhs, ResTaskInfo$b rhs) {
                return rhs.weight - lhs.weight;
            }
        });
        if (UtilsFunction.empty(info.dir)) {
            info.dir = dV();
        }
        addMarker("segment-handler-" + this.ou);
    }

    protected String dV() {
        return com.MCWorld.controller.b.dE().getDownloadPath();
    }

    public boolean process(a dispatcher) {
        if (this.oi) {
            addMarker("pause-before-process");
            HLog.info(TAG, "task pause before process url %s", new Object[]{((ResTaskInfo) getInfo()).url});
            return true;
        } else if (new File(((ResTaskInfo) getInfo()).dir, ((ResTaskInfo) getInfo()).filename).exists() && UtilsFunction.empty(this.ov) && this.nU != null) {
            HLog.info(TAG, "handler has no segments to download", new Object[0]);
            addMarker("no-segment-to-download");
            ((ResTaskInfo) getInfo()).state = State.SUCC.ordinal();
            return true;
        } else {
            ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_START.ordinal();
            this.ot = new WeakReference(dispatcher);
            f table = new f();
            table.oo = new HashSet();
            if (!UtilsFunction.empty(this.ov)) {
                for (f.a segment : this.ov) {
                    table.oo.add(new f.a(segment));
                }
            }
            this.nT = new d(this.nU == null ? null : this.nU.dU(), table, this);
            addMarker("download-segment-urls-" + ((ResTaskInfo) getInfo()).mZ.nc);
            if (!this.oi) {
                return this.nT.run();
            }
            addMarker("pause-before-segment-download-action-run");
            HLog.info(TAG, "retry check task pause before process url " + ((ResTaskInfo) getInfo()).url, new Object[0]);
            return true;
        }
    }

    public void pause(boolean delete, boolean deleteFile) {
        this.mPauseDeleteRecord = delete;
        this.mPauseDeleteFile = deleteFile;
        this.oi = true;
        if (this.nT != null) {
            boolean canceled = new b(this.nT).run();
            addMarker("pause-segment-action-run-" + canceled);
            if (!canceled) {
                HLog.info(TAG, "pause but no request to cancel", new Object[0]);
                onCancel();
                return;
            }
            return;
        }
        addMarker("pause-but-no-download-action");
        HLog.info(TAG, "pause handler but no download action", new Object[0]);
    }

    public void destroy() {
        a dispatcher = getDispatcher();
        if (dispatcher != null) {
            addMarker("dispatcher-finish");
            this.ow.finish("handler-detroy");
            dispatcher.finish();
            return;
        }
        HLog.error(TAG, "destroy handler dispatcher null, info %s, may be task has not begun to process", new Object[]{getInfo()});
    }

    public void onCancel() {
        HLog.info(TAG, "segment handle recv cancel, pause delete " + this.mPauseDeleteRecord, new Object[0]);
        if (this.mPauseDeleteRecord) {
            a.delete(((ResTaskInfo) getInfo()).mZ.id);
            if (this.mPauseDeleteFile) {
                new File(((ResTaskInfo) getInfo()).dir, ((ResTaskInfo) getInfo()).filename).delete();
            }
            addMarker("pause-delete-file-" + this.mPauseDeleteRecord + "-" + this.mPauseDeleteFile);
        } else {
            ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_PAUSE.ordinal();
        }
        destroy();
    }

    public void onErrorResponse(VolleyError error) {
        HLog.info(TAG, "segment handler recv error " + error, new Object[0]);
        addMarker("handler-error-recv");
        ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_ERROR.ordinal();
        EventNotifyCenter.notifyEventUiThread(c.class, 264, new Object[]{((ResTaskInfo) getInfo()).url, Integer.valueOf(VolleyError.getErrorId(error))});
        destroy();
    }

    public void onResponse(Object response) {
        addMarker("handler-response-recv");
        HLog.info(TAG, "segments response " + response, new Object[0]);
        try {
            ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_COMPLETE.ordinal();
            EventNotifyCenter.notifyEvent(c.class, 263, new Object[]{((ResTaskInfo) getInfo()).url});
            dQ();
        } catch (Exception e) {
            HLog.error(TAG, "download complete process e " + e + ", info " + getInfo(), new Object[0]);
        }
        destroy();
    }

    public boolean prepare() throws Exception {
        addMarker("handler-prepare-begins");
        ((ResTaskInfo) getInfo()).state = State.PREPARE.ordinal();
        EventNotifyCenter.notifyEventUiThread(c.class, 256, new Object[]{((ResTaskInfo) getInfo()).url});
        File dir = new File(((ResTaskInfo) getInfo()).dir);
        if (!dir.exists()) {
            boolean succ = dir.mkdirs();
            if (!succ) {
                HLog.warn(TAG, "retry to mkdir " + dir, new Object[0]);
                succ = dir.mkdirs();
            }
            if (!succ) {
                addMarker("handler-prepare-create-directory-failed");
                HLog.error(TAG, "segment download mkdir failed...", new Object[0]);
                throw new CreateDirectoryError();
            }
        }
        File file = new File(dir, ((ResTaskInfo) getInfo()).filename);
        f downloadedTable = i.dW().u(a.am(((ResTaskInfo) getInfo()).mZ.id), 1);
        f.a segment;
        if (!file.exists()) {
            addMarker("prepare-no-file");
            HLog.info(TAG, "file not exist delete record and meta file", new Object[0]);
            if (!(downloadedTable == null || UtilsFunction.empty(downloadedTable.oo))) {
                for (f.a segment2 : downloadedTable.oo) {
                    this.mReporter.deleteRecord(segment2.id);
                }
            }
            i.dW().remove(a.am(((ResTaskInfo) getInfo()).mZ.id));
            HLog.info(TAG, "download segment file not exists", new Object[0]);
        } else if (!(downloadedTable == null || UtilsFunction.empty(downloadedTable.oo) || downloadedTable.total <= 0)) {
            int i;
            ResTaskInfo$b url;
            downloadedTable.on = false;
            this.nU = downloadedTable;
            for (f.a segment22 : this.nU.oo) {
                segment22.mN = DownloadMemCache.getInstance().getRecord(segment22.id);
            }
            List<f.a> arrayList = new ArrayList(this.nU.oo);
            Collections.sort(arrayList, new Comparator<f.a>(this) {
                final /* synthetic */ g ox;

                {
                    this.ox = this$0;
                }

                public /* synthetic */ int compare(Object obj, Object obj2) {
                    return a((f.a) obj, (f.a) obj2);
                }

                public int a(f.a lhs, f.a rhs) {
                    long result = lhs.start - rhs.start;
                    if (result < 0) {
                        return -1;
                    }
                    if (result > 0) {
                        return 1;
                    }
                    if (lhs.mN == null) {
                        if (rhs.mN == null) {
                            return 0;
                        }
                        return -1;
                    } else if (rhs.mN == null) {
                        return 1;
                    } else {
                        return (int) (lhs.mN.progress - rhs.mN.progress);
                    }
                }
            });
            addMarker("prepare-read-table-" + this.nU);
            List<ResTaskInfo$b> usedUrls = new ArrayList();
            int j = 0;
            for (i = 0; i < arrayList.size(); i++) {
                url = (ResTaskInfo$b) this.ou.nc.get(j % this.ou.nc.size());
                segment22 = (f.a) arrayList.get(i);
                if (segment22.mN != null && !ErrorCode.isRestart(segment22.mN.error)) {
                    long start = segment22.start + segment22.mN.progress;
                    long end = segment22.or;
                    if (start < end) {
                        j++;
                        f.a download = new f.a(new f.b(url.url, url.weight, url.rate));
                        download.start = start;
                        download.or = end;
                        usedUrls.add(url);
                        this.ov.add(download);
                        for (f.a s : this.nU.oo) {
                            if (s.id.equals(segment22.id)) {
                                HLog.debug(TAG, "reset segment end form " + s.or + " to " + start, new Object[0]);
                                s.or = start;
                                break;
                            }
                        }
                        this.nU.oo.add(download);
                        i.dW().a(a.am(((ResTaskInfo) getInfo()).mZ.id), this.nU);
                    }
                } else if (segment22.or > segment22.start) {
                    segment22.oq = new f.b(url.url, url.weight, url.rate);
                    usedUrls.add(url);
                    j++;
                    this.ov.add(segment22);
                } else {
                    HLog.info(TAG, "segment end is smaller than start", new Object[0]);
                }
            }
            addMarker("prepare-table-after-computing-" + this.nU);
            addMarker("prepare-need-download-" + this.ov);
            List<ResTaskInfo$b> allUrls = new ArrayList(this.ou.nc);
            allUrls.removeAll(usedUrls);
            if (allUrls.size() > 0 && this.ov.size() > 0) {
                HLog.debug(TAG, "remained urls are not used " + allUrls, new Object[0]);
                Collections.sort(this.ov, new Comparator<f.a>(this) {
                    final /* synthetic */ g ox;

                    {
                        this.ox = this$0;
                    }

                    public /* synthetic */ int compare(Object obj, Object obj2) {
                        return a((f.a) obj, (f.a) obj2);
                    }

                    public int a(f.a lhs, f.a rhs) {
                        return (int) ((rhs.or - rhs.start) - (lhs.or - lhs.start));
                    }
                });
                List<f.a> needDownload = new ArrayList(this.ov);
                i = 0;
                j = 0;
                while (i < this.ov.size() && j < allUrls.size()) {
                    segment22 = (f.a) this.ov.get(i);
                    needDownload.remove(segment22);
                    this.nU.oo.remove(segment22);
                    url = (ResTaskInfo$b) allUrls.get(j);
                    f.a head = new f.a(segment22.oq);
                    head.start = segment22.start;
                    head.or = (segment22.or + segment22.start) / 2;
                    f.a aVar = new f.a(new f.b(url.url, url.weight, url.rate));
                    aVar.start = (segment22.start + head.or) - head.start;
                    aVar.or = segment22.or;
                    needDownload.add(head);
                    needDownload.add(aVar);
                    this.nU.oo.add(head);
                    this.nU.oo.add(aVar);
                    i.dW().a(a.am(((ResTaskInfo) getInfo()).mZ.id), this.nU);
                    i++;
                    j++;
                }
                this.ov = needDownload;
                addMarker("prepare-table-after-cutting-" + this.nU);
                addMarker("prepare-cutting-need-download-" + this.ov);
            }
            HLog.debug(TAG, "download segments are " + this.ov, new Object[0]);
        }
        return false;
    }

    public void onProgress(String url, long length, long progress, float rate) {
        DownloadRecord record = a.getRecord(((ResTaskInfo) getInfo()).mZ.id);
        if (record != null) {
            ((ResTaskInfo) getInfo()).mN = record;
            ((ResTaskInfo) getInfo()).dir = ((ResTaskInfo) getInfo()).mN.dir;
            ((ResTaskInfo) getInfo()).filename = ((ResTaskInfo) getInfo()).mN.name;
            ((ResTaskInfo) getInfo()).mO = rate;
            ((ResTaskInfo) getInfo()).state = State.DOWNLOAD_PROGRESS.ordinal();
            return;
        }
        HLog.warn(TAG, "progress but record is NULL", new Object[0]);
    }

    public a getDispatcher() {
        if (this.ot != null) {
            return (a) this.ot.get();
        }
        return null;
    }

    public void addMarker(String tag) {
        MarkerLog.addMarker(this.ow, tag);
    }
}
