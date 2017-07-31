package com.huluxia.controller.resource.handler.segments;

import android.os.SystemClock;
import com.huluxia.controller.resource.action.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.bean.ResTaskInfo$b;
import com.huluxia.controller.resource.handler.segments.f.a;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.io.Response.CancelListener;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.io.Response.ProgressListener;
import com.huluxia.framework.base.http.io.impl.request.Range;
import com.huluxia.framework.base.http.io.impl.request.SegmentRequestBuilder;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.http.toolbox.download.DownloadReporter;
import com.huluxia.framework.base.http.toolbox.error.ErrorCode;
import com.huluxia.framework.base.http.toolbox.error.SegmentError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.widget.UtilsAndroid;
import com.huluxia.jni.UtilsEncrypt;
import com.huluxia.jni.UtilsEncrypt$EncryptItem;
import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: SegmentDownloadAction */
public class d implements b {
    private static final String TAG = "SegmentDownloadAction";
    private int ERROR = 2;
    private int MASK = 15;
    private WeakReference<g> mE;
    private DownloadReporter mReporter = new DownloadReporter();
    private int mState = 0;
    private int mWeightTotal = 0;
    private f nU;
    private f nV;
    private Set<a> nW = new HashSet();
    private int nX = 8;
    private int nY = 4;
    private int nZ = 1;
    private Set<a> oa = new HashSet();
    private Set<a> ob = new HashSet();
    private Set<a> od = new HashSet();
    private a oe;
    private List<ResTaskInfo$b> of = new ArrayList();
    private boolean og = true;
    private ProgressListener oh = new ProgressListener(this) {
        final /* synthetic */ d ok;

        {
            this.ok = this$0;
        }

        public void onProgress(String id, long length, long progress, float speed) {
            ResTaskInfo info = null;
            if (this.ok.mE.get() != null) {
                ((g) this.ok.mE.get()).onProgress(id, length, progress, speed);
                info = (ResTaskInfo) ((g) this.ok.mE.get()).getInfo();
            }
            if (info == null) {
                HLog.info(d.TAG, "recv progress but the segment handler was null and couldn't get the taskInfo", new Object[0]);
            } else if (this.ok.oi) {
                this.ok.addMarker("fresh-progress-but-pause");
                HLog.info(d.TAG, "recv progress but pause before", new Object[0]);
            } else {
                long segmentEnd = 0;
                if (this.ok.og) {
                    this.ok.nU.total = length;
                    this.ok.oe.or = (((long) this.ok.oe.oq.weight) * length) / ((long) this.ok.mWeightTotal);
                    i.dW().a(a.am(info.mZ.id), this.ok.nU);
                    this.ok.og = false;
                    segmentEnd = 0 + this.ok.oe.or;
                    this.ok.addMarker("fresh-progress-first-write-" + length + "-head-end-" + this.ok.oe.or);
                }
                if (UtilsFunction.size(this.ok.of) > 0) {
                    Map<String, String> headers = new HashMap();
                    headers.put("Cookie", info.mW);
                    headers.putAll(this.ok.ah(info.url));
                    headers.putAll(this.ok.dL());
                    List<ResTaskInfo$b> arrayList = new ArrayList(this.ok.of);
                    for (int i = 0; i < arrayList.size(); i++) {
                        long end;
                        arrayList = new ArrayList(info.mZ.nc);
                        ResTaskInfo$b downloadingUrl = (ResTaskInfo$b) arrayList.get(i);
                        arrayList.remove(downloadingUrl);
                        String generateRecordId = a.generateRecordId(downloadingUrl.url);
                        long start = segmentEnd;
                        if (i == arrayList.size() - 1) {
                            end = length;
                        } else {
                            end = segmentEnd + ((((long) downloadingUrl.weight) * length) / ((long) this.ok.mWeightTotal));
                        }
                        segmentEnd = end;
                        a segment = new a();
                        segment.id = generateRecordId;
                        segment.oq = new f.b(downloadingUrl.url, downloadingUrl.weight, downloadingUrl.rate);
                        segment.start = start;
                        segment.or = end;
                        this.ok.nU.oo.add(segment);
                        i.dW().a(a.am(info.mZ.id), this.ok.nU);
                        this.ok.addMarker("begin-download-in-progress-seg-" + segment + "-table-\n" + this.ok.nU);
                        this.ok.a(segment, (List) arrayList);
                    }
                }
            }
        }
    };
    private volatile boolean oi = false;

    public d(f downloadedTable, f needDownloadTable, g handler) {
        this.nU = downloadedTable;
        this.nV = needDownloadTable;
        this.mE = new WeakReference(handler);
        this.of = new ArrayList(((ResTaskInfo) handler.getInfo()).mZ.nc);
        for (ResTaskInfo$b segmentUrl : this.of) {
            this.mWeightTotal += segmentUrl.weight;
        }
    }

    public Set<a> dR() {
        return new HashSet(this.nW);
    }

    public boolean run() {
        ResTaskInfo info = (ResTaskInfo) ((g) this.mE.get()).getInfo();
        if (UtilsFunction.empty(this.nV.oo) || this.nU == null || this.nU.total == 0) {
            ResTaskInfo$b downloadingUrl = (ResTaskInfo$b) this.of.get(0);
            List<ResTaskInfo$b> retryUrls = new ArrayList(this.of);
            retryUrls.remove(downloadingUrl);
            return a(downloadingUrl, retryUrls, 0, 0, 0, this.oh);
        }
        for (a download : this.nV.oo) {
            List retryUrls2 = new ArrayList(info.mZ.nc);
            retryUrls2.remove(new ResTaskInfo$b(download.oq.url, download.oq.weight, download.oq.rate));
            a(download, retryUrls2);
        }
        return false;
    }

    private void a(final a download, final List<ResTaskInfo$b> retryUrls) {
        if (this.mE.get() != null) {
            final ResTaskInfo info = (ResTaskInfo) ((g) this.mE.get()).getInfo();
            Map<String, String> headers = new HashMap();
            headers.put("Cookie", info.mW);
            headers.putAll(dL());
            headers.putAll(ah(download.oq.url));
            SegmentRequestBuilder builder = com.huluxia.controller.resource.http.a.dX().getSegmentReqBuilder(download.id, download.oq.url, info.dir, info.filename);
            addMarker("other-seg-download-" + download + "-retryurls-" + retryUrls);
            ((SegmentRequestBuilder) builder.setRename(false).setAdditionalHeaders(headers)).setContext(info).setRate(download.oq.rate).setRange(new Range(Long.valueOf(download.start), Long.valueOf(download.or))).setSuccListener(new Listener<String>(this) {
                final /* synthetic */ d ok;

                public void onResponse(String response) {
                    this.ok.oa.add(download);
                    this.ok.dS();
                    HLog.info(d.TAG, "[Segments]download action on response " + response + ", info " + info + ", handler " + this.ok.mE.get(), new Object[0]);
                    this.ok.addMarker("other-seg-recv-response-state-" + this.ok.mState);
                    this.ok.a((Object) response, null);
                }
            }).setProgressListener(new ProgressListener(this) {
                final /* synthetic */ d ok;

                {
                    this.ok = this$0;
                }

                public void onProgress(String url, long length, long progress, float speed) {
                    if (this.ok.mE.get() != null) {
                        ((g) this.ok.mE.get()).onProgress(url, length, progress, speed);
                    }
                }
            }).setErrListener(new ErrorListener(this) {
                final /* synthetic */ d ok;

                public void onErrorResponse(VolleyError error) {
                    HLog.debug(d.TAG, "[Segments]download action on err " + error + ",info " + info + ",handler " + this.ok.mE.get(), new Object[0]);
                    this.ok.addMarker("err-download-segment-" + download + "-retry-" + retryUrls);
                    DownloadRecord downloadRecord = DownloadMemCache.getInstance().getRecord(download.id);
                    if (downloadRecord != null) {
                        this.ok.addMarker("record-update-segment-err-" + downloadRecord.error);
                        downloadRecord.error = ErrorCode.generateSegmentErr(downloadRecord.error);
                        this.ok.mReporter.reportError(downloadRecord);
                    }
                    if (UtilsFunction.empty(retryUrls)) {
                        this.ok.od.add(download);
                        this.ok.dS();
                        this.ok.nU.on = true;
                        i.dW().a(a.am(info.mZ.id), this.ok.nU);
                        this.ok.addMarker("other-seg-recv-err-state-" + this.ok.mState + "-" + error);
                        this.ok.a(null, error);
                        return;
                    }
                    this.ok.nU.oo.remove(download);
                    ResTaskInfo$b segmentUrl = (ResTaskInfo$b) retryUrls.remove(0);
                    DownloadRecord record = DownloadMemCache.getInstance().getRecord(download.id);
                    HLog.error(d.TAG, "other segment download error " + error + ", segment " + download, new Object[0]);
                    HLog.error(d.TAG, "other segment download error record " + record, new Object[0]);
                    if (record == null || !ErrorCode.isResume(VolleyError.getErrorId(error))) {
                        if (record != null) {
                            this.ok.mReporter.deleteRecord(download.id);
                        }
                        String id = a.generateRecordId(segmentUrl.url);
                        a segment = new a(download);
                        segment.id = id;
                        segment.oq = new f.b(segmentUrl.url, segmentUrl.weight, segmentUrl.rate);
                        this.ok.nU.oo.remove(download);
                        this.ok.nU.oo.add(segment);
                        this.ok.nW.remove(download);
                        i.dW().a(a.am(info.mZ.id), this.ok.nU);
                        this.ok.addMarker("retry-other-seg-restart-err-" + download + "-table-\n" + this.ok.nU);
                        this.ok.a(segment, retryUrls);
                        return;
                    }
                    long start = download.start + record.progress;
                    long end = download.or;
                    download.or = start;
                    this.ok.nW.remove(download);
                    id = a.generateRecordId(segmentUrl.url);
                    segment = new a();
                    segment.id = id;
                    segment.start = start;
                    segment.or = end;
                    segment.oq = new f.b(segmentUrl.url, segmentUrl.weight, segmentUrl.rate);
                    this.ok.nU.oo.add(download);
                    this.ok.nU.oo.add(segment);
                    i.dW().a(a.am(info.mZ.id), this.ok.nU);
                    this.ok.addMarker("retry-other-seg-resume-err-" + download + "-table-\n" + this.ok.nU);
                    this.ok.a(segment, retryUrls);
                }
            }).setCancelListener(new CancelListener(this) {
                final /* synthetic */ d ok;

                public void onCancel() {
                    HLog.info(d.TAG, "[Segments]download action on cancel info " + info + ", handler " + this.ok.mE.get(), new Object[0]);
                    this.ok.ob.add(download);
                    this.ok.dS();
                    this.ok.addMarker("other-seg-cancel-recv-" + download);
                    this.ok.a(null, null);
                }
            }).execute();
            this.of.remove(new ResTaskInfo$b(download.oq.url, download.oq.weight, download.oq.rate));
            this.nW.add(download);
        }
    }

    private boolean a(ResTaskInfo$b downloadingUrl, final List<ResTaskInfo$b> retryUrls, long start, long end, long length, ProgressListener progressListener) {
        if (this.mE.get() == null) {
            return false;
        }
        final ResTaskInfo info = (ResTaskInfo) ((g) this.mE.get()).getInfo();
        Map<String, String> headers = new HashMap();
        headers.put("Cookie", info.mW);
        headers.putAll(dL());
        headers.putAll(ah(downloadingUrl.url));
        String id = a.generateRecordId(downloadingUrl.url);
        final a segment = new a();
        segment.id = id;
        segment.oq = new f.b(downloadingUrl.url, downloadingUrl.weight, downloadingUrl.rate);
        segment.start = start;
        segment.or = end;
        if (start == 0 && end == 0) {
            f table = new f();
            table.total = length;
            table.path = new File(info.dir, info.filename).getAbsolutePath();
            Set<a> segmentSet = new HashSet();
            segmentSet.add(segment);
            table.oo = segmentSet;
            this.nU = table;
            this.oe = segment;
            addMarker("download-head-" + segment + "-table-\n" + this.nU);
        } else {
            this.nU.oo.add(segment);
            addMarker("download-add-seg-" + segment + "-table-\n" + this.nU);
        }
        i.dW().a(a.am(info.mZ.id), this.nU);
        this.nW.add(segment);
        addMarker("begin-download-" + segment);
        SegmentRequestBuilder builder = com.huluxia.controller.resource.http.a.dX().getSegmentReqBuilder(id, downloadingUrl.url, info.dir, info.filename);
        ((SegmentRequestBuilder) builder.setRename(false).setAdditionalHeaders(headers)).setContext(info).setWeight(downloadingUrl.weight, this.mWeightTotal).setRate(downloadingUrl.rate).setRange(new Range(Long.valueOf(start), Long.valueOf(end))).setSuccListener(new Listener<String>(this) {
            final /* synthetic */ d ok;

            public void onResponse(String response) {
                HLog.info(d.TAG, "[Head]download action on response " + response + ", info " + ((ResTaskInfo) ((g) this.ok.mE.get()).getInfo()) + ", handler " + this.ok.mE.get(), new Object[0]);
                this.ok.oa.add(segment);
                this.ok.dS();
                this.ok.addMarker("head-recv-response-state-" + this.ok.mState);
                this.ok.a((Object) response, null);
            }
        }).setProgressListener(progressListener).setErrListener(new ErrorListener(this) {
            final /* synthetic */ d ok;

            public void onErrorResponse(VolleyError error) {
                HLog.debug(d.TAG, "[Head]download action on err " + error + ",info " + info + ",handler " + this.ok.mE.get() + ", recv progress before " + this.ok.og, new Object[0]);
                this.ok.addMarker("err-head-segment-" + segment + "-retry-" + retryUrls);
                DownloadRecord downloadRecord = DownloadMemCache.getInstance().getRecord(segment.id);
                if (downloadRecord != null) {
                    this.ok.addMarker("head-record-update-segment-err-" + downloadRecord.error);
                    downloadRecord.error = ErrorCode.generateSegmentErr(downloadRecord.error);
                    this.ok.mReporter.reportError(downloadRecord);
                }
                if (UtilsFunction.empty(retryUrls)) {
                    this.ok.od.add(segment);
                    this.ok.dS();
                    this.ok.nU.on = true;
                    i.dW().a(a.am(info.mZ.id), this.ok.nU);
                    this.ok.addMarker("head-recv-error-state-" + this.ok.mState + "-seg-" + segment);
                    this.ok.a(null, error);
                    return;
                }
                ResTaskInfo$b segmentUrl = (ResTaskInfo$b) retryUrls.remove(0);
                if (this.ok.og) {
                    HLog.error(d.TAG, "head segment download error when no progress before...", new Object[0]);
                    this.ok.nU.oo.remove(segment);
                    this.ok.nW.remove(segment);
                    this.ok.mWeightTotal = this.ok.mWeightTotal - segment.oq.weight;
                    this.ok.addMarker("retry-head-recv-error-first-write-seg-" + segment + "-table-\n" + this.ok.nU);
                    this.ok.a(segmentUrl, retryUrls, 0, 0, 0, this.ok.oh);
                    return;
                }
                DownloadRecord record = DownloadMemCache.getInstance().getRecord(segment.id);
                HLog.error(d.TAG, "head segment download error " + error + ", segment " + segment, new Object[0]);
                HLog.error(d.TAG, "head segment download error record " + record, new Object[0]);
                if (record == null || !ErrorCode.isResume(VolleyError.getErrorId(error))) {
                    if (record != null) {
                        this.ok.mReporter.deleteRecord(segment.id);
                    }
                    this.ok.nU.oo.remove(segment);
                    this.ok.nW.remove(segment);
                    this.ok.addMarker("retry-head-recv-restart-err-url-" + segmentUrl + "-segment-" + segment);
                    this.ok.a(segmentUrl, retryUrls, segment.start, segment.or, this.ok.nU.total, this.ok.getProgressListener());
                    return;
                }
                long start = segment.start + record.progress;
                long end = segment.or;
                segment.or = start;
                this.ok.nW.remove(segment);
                this.ok.addMarker("retry-head-recv-resume-err-url-" + segmentUrl + "-start-" + start + "-end-" + end);
                this.ok.a(segmentUrl, retryUrls, start, end, this.ok.nU.total, this.ok.getProgressListener());
            }
        }).setCancelListener(new CancelListener(this) {
            final /* synthetic */ d ok;

            public void onCancel() {
                HLog.info(d.TAG, "[Head]download action on cancel info " + info + ", handler " + this.ok.mE.get(), new Object[0]);
                this.ok.ob.add(segment);
                this.ok.dS();
                this.ok.addMarker("head-recv-cancel-state-" + this.ok.mState);
                this.ok.a(null, null);
            }
        });
        if (this.oi) {
            return true;
        }
        builder.execute();
        this.of.remove(downloadingUrl);
        return false;
    }

    private void dS() {
        int succSize = UtilsFunction.size(this.oa);
        int errSize = UtilsFunction.size(this.od);
        int cancelSize = UtilsFunction.size(this.ob);
        int downloadingSize = UtilsFunction.size(this.nW);
        if ((succSize + errSize) + cancelSize < downloadingSize) {
            this.mState = this.nZ & this.MASK;
        } else if (errSize > 0) {
            this.mState = this.ERROR & this.MASK;
        } else if (cancelSize > 0) {
            this.mState = this.nY & this.MASK;
        } else {
            this.mState = this.nX & this.MASK;
        }
        HLog.info(TAG, "compute state " + this.mState + ", succ " + succSize + ", err " + errSize + ", cancel " + cancelSize + ", downloading " + downloadingSize, new Object[0]);
    }

    private void a(Object response, VolleyError error) {
        HLog.info(TAG, "compute state response " + this.mState + ", response " + response + ", err " + error, new Object[0]);
        if ((this.mState & this.MASK) == this.nX) {
            if (this.mE.get() != null) {
                ((g) this.mE.get()).onResponse(response);
            }
        } else if ((this.mState & this.MASK) == this.ERROR) {
            if (this.mE.get() != null) {
                if (error == null) {
                    error = new SegmentError();
                }
                ((g) this.mE.get()).onErrorResponse(error);
            }
        } else if ((this.mState & this.MASK) == this.nY && this.mE.get() != null) {
            ((g) this.mE.get()).onCancel();
        }
    }

    private Map<String, String> ah(String url) {
        Map<String, String> headers = new HashMap();
        try {
            UtilsEncrypt$EncryptItem item = UtilsEncrypt.encrpytLogin(url, UtilsAndroid.fetchDeviceId(), UtilsApkPackage.getAppPackageName(AppConfig.getInstance().getAppContext()), String.valueOf(SystemClock.elapsedRealtime()));
            int randomFactor = UtilsEncrypt.radomInt();
            int assist_02 = randomFactor + 65;
            int assist_01 = item.len ^ randomFactor;
            HLog.debug(TAG, "download encode %s", new Object[]{String.format("%s_%d_%d", new Object[]{item.code, Integer.valueOf(assist_01), Integer.valueOf(assist_02)})});
            headers.put("Referer", URLEncoder.encode(downloadCode, "UTF-8"));
        } catch (Exception e) {
            HLog.error(TAG, "download encode err %s, url %s", new Object[]{e.getMessage(), url});
        }
        return headers;
    }

    private Map<String, String> dL() {
        Map<String, String> headers = new HashMap();
        headers.put("User-Agent", "huluxiadown");
        return headers;
    }

    private ProgressListener getProgressListener() {
        return new ProgressListener(this) {
            final /* synthetic */ d ok;

            {
                this.ok = this$0;
            }

            public void onProgress(String url, long length, long progress, float speed) {
                if (this.ok.mE.get() != null) {
                    ((g) this.ok.mE.get()).onProgress(url, length, progress, speed);
                }
            }
        };
    }

    public boolean pause() {
        HLog.info(TAG, "segment download action pause, fresh " + this.oe, new Object[0]);
        this.oi = true;
        if (this.oe != null) {
            return com.huluxia.controller.resource.http.a.dX().pauseDownloadRequest(this.oe.oq.url);
        }
        return false;
    }

    public void addMarker(String tag) {
        if (this.mE.get() != null) {
            ((g) this.mE.get()).addMarker(tag);
        }
    }
}
