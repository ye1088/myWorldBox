package com.MCWorld.framework.base.http.io.impl.request;

import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.io.Request$PrepareListener;
import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord$State;
import com.MCWorld.framework.base.http.toolbox.download.DownloadReporter;
import com.MCWorld.framework.base.http.toolbox.error.AuthFailureError;
import com.MCWorld.framework.base.http.toolbox.error.CancelError;
import com.MCWorld.framework.base.http.toolbox.error.ExceedLimitedCancelError;
import com.MCWorld.framework.base.http.toolbox.error.InvalidParamError;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import java.util.Map;

public class SegmentRequest extends DownloadRequest {
    public static final String HEADER_RANG = "Range";
    private static final String HEADER_RANG_VALUE = "bytes=%d-";
    private static final String HEADER_RANG_VALUE_FULL = "bytes=%d-%d";
    private static final String TAG = "SegmentRequest";
    private DownloadReporter DEFAULT_REPORTER = new DownloadReporter();
    private long mEnd;
    private String mId;
    protected Request$PrepareListener mPrepareListener;
    private Range<Long> mRange;
    private int mRate;
    private DownloadReporter mReporter = this.DEFAULT_REPORTER;
    private long mStart;
    private int mWeight;
    private int mWeightTotal;

    SegmentRequest(int method, String id, String url, String dir, String filename, int encodeType, Range<Long> range, int weight, int weightTotal, int rate, Listener<String> succListener, ErrorListener listener, ProgressListener progressListener, CancelListener cancelListener, Request$PrepareListener prepareListener) {
        super(method, url, dir, filename, encodeType, succListener, listener, progressListener, cancelListener, prepareListener);
        this.mId = id;
        this.mWeight = weight;
        this.mWeightTotal = weightTotal;
        this.mRate = rate;
        this.mRange = range;
        this.mStart = this.mRange != null ? ((Long) this.mRange.getLower()).longValue() : 0;
        this.mEnd = this.mRange != null ? ((Long) this.mRange.getUpper()).longValue() : 0;
        this.mLimitBandWidth = true;
        setShouldCache(false);
    }

    public String getId() {
        return this.mId;
    }

    public Map<String, String> getHeaders() throws AuthFailureError, InvalidParamError {
        Map<String, String> headers = this.mAdditionalHeaders;
        HLog.info(TAG, "download request get head byte range " + this.mRange, new Object[0]);
        if (this.mEnd <= 0) {
            headers.put("Range", String.format(HEADER_RANG_VALUE, new Object[]{Long.valueOf(this.mStart)}));
        } else {
            headers.put("Range", String.format(HEADER_RANG_VALUE_FULL, new Object[]{Long.valueOf(this.mStart), Long.valueOf(this.mEnd)}));
        }
        addMarker(String.format("download-range-%s", new Object[]{this.mRange}));
        return headers;
    }

    public void prepare() throws VolleyError {
        try {
            checkCancel("prepare-but-cancel");
            if (this.mPrepareListener != null) {
                this.mPrepareListener.onPrepare();
            }
            if (DownloadMemCache.getInstance().getRecord(this.mId) != null) {
                this.mReporter.deleteRecord(this.mId);
            }
            DownloadRecord record = new DownloadRecord();
            record.url = this.mId;
            record.dir = this.mDir;
            record.name = this.mFilename;
            this.mReporter.reportProgress(record);
            this.mRecord = record;
        } catch (CancelError error) {
            pause("prepare-pause-request");
            throw error;
        }
    }

    public void progress(long bytes, long total) throws CancelError {
        long downloadLength;
        super.progress(bytes, total);
        if (this.mEnd <= 0) {
            downloadLength = (((long) this.mWeight) * total) / ((long) this.mWeightTotal);
            this.mEnd = downloadLength;
        } else {
            downloadLength = this.mEnd - this.mStart;
        }
        if (this.mBytesSoFar > downloadLength) {
            this.mRecord.state = DownloadRecord$State.COMPLETION.state;
            this.mRecord.resetError();
            this.mRecord.pause = false;
            this.mRecord.progress = this.mBytesSoFar;
            this.mReporter.reportProgress(this.mRecord);
            addMarker("cancel-" + this.mBytesSoFar + "-downloaded-exceed-" + downloadLength + "-[" + this.mStart + ", " + this.mEnd + "]");
            throw new ExceedLimitedCancelError("download-length-exceed-" + this.mBytesSoFar + "-" + downloadLength + "-[" + this.mStart + ", " + this.mEnd + "]");
        }
    }

    public long getStart() {
        return this.mStart;
    }

    public long getEnd() {
        return this.mEnd;
    }

    public int getReaderType() {
        if (this.mEncodeType == 3) {
            return 3;
        }
        if (this.mEncodeType == 4) {
            return 4;
        }
        if (!this.mLimitBandWidth) {
            return 1;
        }
        if (this.mEncodeType == 5) {
            return 5;
        }
        return 2;
    }

    public int getRate() {
        return this.mRate;
    }
}
