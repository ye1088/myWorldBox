package com.huluxia.framework.base.http.io.impl.request;

import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.media.session.PlaybackStateCompat;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.datasource.cache.Cache.Entry;
import com.huluxia.framework.base.http.io.NetworkResponse;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.Request$PrepareListener;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.io.Response.CancelListener;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.io.Response.ProgressListener;
import com.huluxia.framework.base.http.toolbox.HttpHeaderParser;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord$State;
import com.huluxia.framework.base.http.toolbox.download.DownloadReporter;
import com.huluxia.framework.base.http.toolbox.error.AuthFailureError;
import com.huluxia.framework.base.http.toolbox.error.CancelError;
import com.huluxia.framework.base.http.toolbox.error.CreateDirectoryError;
import com.huluxia.framework.base.http.toolbox.error.InvalidParamError;
import com.huluxia.framework.base.http.toolbox.error.PrepareError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.huluxia.framework.base.http.toolbox.statis.CdnQualityTracker;
import com.huluxia.framework.base.http.toolbox.statis.IDownloadTracker;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class DownloadRequest extends Request<String> {
    private static final int DEFAULT_DOWNLOAD_TIMEOUT = 5000;
    private static final int DOWNLOAD_BACKOFF_MULT = 2;
    public static final String HEADER_COOKIE = "Cookie";
    public static final String HEADER_RANG = "Range";
    private static final String HEADER_RANG_VALUE = "bytes=%d-";
    private static final int MAX_DOWNLOAD_MAX_RETRIES = 4;
    private static final long MIN_PROGRESS_STEP = 4096;
    private static final long MIN_PROGRESS_TIME = 1500;
    public static final String RESPONSE_HEADER_RANG = "Content-Range";
    private static final String TAG = "DownloadRequest";
    private DownloadReporter DEFAULT_REPORTER;
    private long mBytesNotified;
    protected long mBytesSoFar;
    protected CancelListener mCancelListener;
    protected String mDir;
    protected int mEncodeType;
    protected String mFilename;
    private long mLastSectionTiming;
    protected boolean mLimitBandWidth;
    private long mLimitTime;
    protected Request$PrepareListener mPrepareListener;
    protected DownloadRecord mRecord;
    private DownloadReporter mReporter;
    private boolean mShouldNotify;
    private float mSpeed;
    private long mSpeedSampleStart;
    protected Listener<String> mSuccListener;
    private long mTimeLastNotification;
    private IDownloadTracker mTracker;

    DownloadRequest(String url, String dir, String filename, int encodeType, Listener<String> succListener, ErrorListener listener, ProgressListener progressListener, CancelListener cancelListener, Request$PrepareListener prepareListener) {
        this(0, url, dir, filename, encodeType, succListener, listener, progressListener, cancelListener, prepareListener);
    }

    DownloadRequest(int method, String url, String dir, String filename, int encodeType, Listener<String> succListener, ErrorListener listener, ProgressListener progressListener, CancelListener cancelListener, Request$PrepareListener prepareListener) {
        super(method, url, listener, progressListener);
        this.mSpeed = 0.0f;
        this.mShouldNotify = false;
        this.DEFAULT_REPORTER = new DownloadReporter();
        this.mReporter = this.DEFAULT_REPORTER;
        this.mLimitBandWidth = false;
        this.mSpeedSampleStart = 0;
        this.mBytesNotified = 0;
        this.mTimeLastNotification = 0;
        this.mLastSectionTiming = 0;
        this.mTracker = new CdnQualityTracker();
        if (url == null || url.length() <= 0) {
            throw new IllegalArgumentException("download request url should not be NULL");
        }
        setEnableMarker(true);
        this.mSuccListener = succListener;
        this.mCancelListener = cancelListener;
        this.mPrepareListener = prepareListener;
        this.mDir = dir;
        this.mFilename = filename;
        this.mEncodeType = encodeType;
        setRetryPolicy(new DefaultRetryPolicy(5000, 4, 2.0f));
        setTag(url);
    }

    protected void checkCancel(String tag) throws CancelError {
        if (isCanceled()) {
            HLog.info(TAG, "check cancel with tag " + tag, new Object[0]);
            addMarker(tag);
            throw new CancelError(tag);
        }
    }

    public void prepare() throws VolleyError {
        super.prepare();
        if (this.mPrepareListener != null) {
            this.mPrepareListener.onPrepare();
        }
        DownloadRecord record = verifyHistory();
        if (record == null) {
            record = new DownloadRecord();
            record.url = getUrl();
            record.dir = this.mDir;
            record.name = this.mFilename;
            this.mReporter.reportProgress(record);
        }
        this.mBytesSoFar = record.progress;
        this.mRecord = record;
        try {
            checkCancel("prepare-but-cancel");
            File directory = new File(this.mDir);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new CreateDirectoryError();
            }
        } catch (CancelError error) {
            pause("prepare-pause-request");
            throw error;
        }
    }

    private DownloadRecord verifyHistory() throws CancelError {
        long j = 0;
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(getUrl());
        if (record != null) {
            File file = new File(record.dir, record.name);
            if (!file.exists()) {
                HLog.warn(TAG, "download record but file not exist record %s ", new Object[]{record});
                DownloadMemCache.getInstance().deleteRecord(getUrl());
                record.progress = 0;
                record.total = 0;
                record.resetError();
                record.pause = false;
                record.state = DownloadRecord$State.INIT.state;
                checkCancel("verify-history-cancel");
                this.mReporter.reportProgress(record);
            } else if (record.progress > file.length()) {
                if (file.length() > PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                    j = file.length() - PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                }
                record.progress = j;
                record.resetError();
                record.pause = false;
                checkCancel("verify-history-cancel-2");
                this.mReporter.reportProgress(record);
                HLog.error(TAG, "recrod progress %d bigger, file length %d", new Object[]{Long.valueOf(record.progress), Long.valueOf(file.length())});
            } else {
                if (record.progress >= record.total) {
                    if (record.progress <= PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                        record.progress = 0;
                        new File(record.dir, record.name).delete();
                    } else {
                        record.progress -= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                    }
                }
                record.resetError();
                record.pause = false;
                checkCancel("verify-history-cancel-1");
                this.mReporter.reportProgress(record);
                HLog.info(TAG, "download record is valid, so is valid to resume download record %s ", new Object[]{record});
            }
        } else {
            HLog.info(TAG, "download record is NULL so download new file, url %s ", new Object[]{getUrl()});
        }
        return record;
    }

    public void setReporter(DownloadReporter reporter) {
        if (reporter != null) {
            this.mReporter = reporter;
        }
    }

    public DownloadRecord getRecord() {
        return this.mRecord;
    }

    public ErrorListener getErrorListener() {
        return super.getErrorListener();
    }

    public Listener<String> getSuccListener() {
        return this.mSuccListener;
    }

    public Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Entry headers = HttpHeaderParser.parseCacheHeaders(response);
        if (headers != null) {
            etag(headers.etag);
        }
        return Response.success(parsed, headers);
    }

    public void deliverResponse(String response) {
        HLog.info(TAG, "deliver response " + response, new Object[0]);
        addMarker("deliver-response-succ");
        completeDownload();
        this.mSuccListener.onResponse(response);
    }

    public void deliverError(VolleyError error) {
        HLog.error(TAG, "deliver error to download request error " + error, new Object[0]);
        addMarker("error-deliver-download-request-e-" + error);
        error(error);
        super.deliverError(error);
    }

    public void deliverCancel(VolleyError error) {
        HLog.info(TAG, "deliver cancel to download request " + error, new Object[0]);
        if (error == null) {
            return;
        }
        if (error == null || !(error instanceof CancelError)) {
            addMarker("deliver-cancel-because-error-" + error);
            deliverError(error);
        }
    }

    public void cancel(boolean mayInterruptIfRunning) {
        super.cancel(mayInterruptIfRunning);
        pause("cancel");
        if (this.mCancelListener != null) {
            this.mCancelListener.onCancel();
        }
    }

    public float getSpeed() {
        return this.mSpeed;
    }

    public long getBytesSoFar() {
        return this.mBytesSoFar;
    }

    public void setSpeedSampleStart(long speedSampleStart) {
        this.mSpeedSampleStart = speedSampleStart;
    }

    public void progress(long bytes, long total) throws CancelError {
        this.mBytesSoFar += bytes;
        this.mRecord.total = total;
        long now = SystemClock.elapsedRealtime();
        long sampleDelta = now - this.mSpeedSampleStart;
        if (sampleDelta > 500) {
            long sampleSpeed = ((this.mBytesSoFar - this.mRecord.progress) * 1000) / sampleDelta;
            if (this.mSpeed == 0.0f) {
                this.mSpeed = (float) sampleSpeed;
            } else {
                this.mSpeed = ((this.mSpeed * 3.0f) + ((float) sampleSpeed)) / 4.0f;
            }
            this.mSpeedSampleStart = now;
            this.mRecord.progress = this.mBytesSoFar;
        }
        if (this.mRecord.progress - this.mBytesNotified <= 4096 || now - this.mTimeLastNotification <= MIN_PROGRESS_TIME) {
            this.mShouldNotify = false;
        } else {
            this.mBytesNotified = this.mRecord.progress;
            this.mTimeLastNotification = now;
            this.mShouldNotify = true;
            this.mRecord.state = DownloadRecord$State.DOWNLOADING.state;
            this.mRecord.resetError();
            this.mRecord.pause = false;
            checkCancel("report-progress-after-cancel");
            this.mReporter.reportProgress(this.mRecord);
        }
        if (this.mTracker != null) {
            this.mTracker.trackSectionSpeed(this, bytes, this.mRecord.total == this.mBytesSoFar);
        }
    }

    public void etag(String etag) {
        this.mRecord.noIntegrity = UtilsFunction.empty(etag);
        this.mReporter.reportETag(this.mRecord);
    }

    public void networkComplete() {
        HLog.info(TAG, "net work complete bytesofar %d, record %s", new Object[]{Long.valueOf(this.mBytesSoFar), this.mRecord});
    }

    public void completeDownload() {
        this.mRecord.progress = this.mBytesSoFar;
        this.mRecord.state = DownloadRecord$State.COMPLETION.state;
        this.mRecord.resetError();
        this.mRecord.pause = false;
        HLog.info(TAG, "download complete %s", new Object[]{this.mRecord});
        addMarker("download-file-complete");
        this.mReporter.reportProgress(this.mRecord);
    }

    protected void pause(String tag) {
        if (this.mRecord == null) {
            HLog.info(TAG, "record is NULL when pause tag " + tag, new Object[0]);
            addMarker("not-prepare-but-pause");
            return;
        }
        if (this.mTracker != null) {
            File file = new File(this.mRecord.dir, this.mRecord.name);
            if (file.exists()) {
                this.mTracker.trackSectionSpeed(this, file.length() - this.mBytesSoFar, true);
            }
        }
        this.mRecord.pause = true;
        this.mRecord.resetError();
        HLog.verbose(TAG, "download pause tag " + tag + ", record " + this.mRecord, new Object[0]);
        addMarker("pause-download-" + tag);
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            this.mReporter.asyncReportPause(this.mRecord);
        } else {
            this.mReporter.reportPause(this.mRecord);
        }
    }

    public void error(VolleyError error) {
        HLog.verbose(TAG, "download error %s", new Object[]{this.mRecord});
        if (this.mTracker != null) {
            File file = new File(this.mRecord.dir, this.mRecord.name);
            if (file.exists()) {
                this.mTracker.trackSectionSpeed(this, file.length() - this.mBytesSoFar, true);
            }
            this.mTracker.trackError(this, error);
        }
        this.mRecord.error = VolleyError.getErrorId(error);
        this.mRecord.pause = true;
        this.mReporter.reportError(this.mRecord);
    }

    public void httpStatusCode(int statusCode) throws CancelError {
        checkCancel("update-http-code-after-cancel");
        this.mRecord.httpstatuscode = statusCode;
        addMarker(String.format("htt-status-code-%d", new Object[]{Integer.valueOf(statusCode)}));
        this.mReporter.reportHttpStatusCode(this.mRecord);
    }

    public void resetRecordProgress() throws PrepareError {
        if (this.mRecord == null) {
            HLog.error(TAG, "reset record progress null", new Object[0]);
            throw new PrepareError();
        }
        HLog.verbose(TAG, "download resetRecordProgress %s", new Object[]{this.mRecord});
        this.mBytesSoFar = 0;
        this.mBytesNotified = 0;
        this.mRecord.progress = 0;
        this.mRecord.total = 0;
        this.mRecord.error = -1;
    }

    public boolean getShouldNotify() {
        return this.mShouldNotify;
    }

    public Map<String, String> getHeaders() throws AuthFailureError, InvalidParamError {
        Map<String, String> headers = super.getHeaders();
        if (this.mRecord == null) {
            HLog.error(TAG, "get header wait interrupt", new Object[0]);
            throw new InvalidParamError("record is null");
        }
        HLog.info(TAG, "download request get head byte so for %d", new Object[]{Long.valueOf(this.mBytesSoFar)});
        if (this.mBytesSoFar > 0) {
            headers.put("Range", String.format(HEADER_RANG_VALUE, new Object[]{Long.valueOf(this.mBytesSoFar)}));
            addMarker(String.format("download-from-progress-%d", new Object[]{Long.valueOf(this.mBytesSoFar)}));
        } else {
            addMarker("download-fresh-file");
        }
        return headers;
    }

    public boolean shouldLimit(long interval) {
        long preLimit = this.mLimitTime;
        if (preLimit <= 0) {
            this.mLimitTime = SystemClock.elapsedRealtime();
        } else if (SystemClock.elapsedRealtime() - preLimit > interval) {
            this.mLimitTime = SystemClock.elapsedRealtime();
            return true;
        }
        return false;
    }

    public String getDir() {
        return this.mDir;
    }

    public String getFilename() {
        return this.mFilename;
    }

    public int getEncodeType() {
        return this.mEncodeType;
    }

    public int getReaderType() {
        if (this.mEncodeType == 3) {
            return 3;
        }
        if (this.mEncodeType == 4) {
            return 4;
        }
        if (this.mLimitBandWidth) {
            return 2;
        }
        return 1;
    }

    public CancelListener getCancelListener() {
        return this.mCancelListener;
    }
}
