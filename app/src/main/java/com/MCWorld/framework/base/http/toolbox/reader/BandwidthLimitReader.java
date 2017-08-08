package com.MCWorld.framework.base.http.toolbox.reader;

import com.MCWorld.framework.base.http.io.impl.request.DownloadRequest;
import com.MCWorld.framework.base.http.io.impl.request.SegmentRequest;
import com.MCWorld.framework.base.http.toolbox.error.LocalFileError;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.utils.ByteArrayPool;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class BandwidthLimitReader extends DownloadReader {
    private static final int DEFALUT_BUFFER_SIZE = 8192;
    public static final long DEFAULT_READ_INTERVAL_IN_MS = 1000;
    private static final long LIMIT_TIME_INTERVAL = 1000;
    private static final String TAG = "BandwidthLimitReader";
    private byte[] buffer;
    private int bufferSize = 8192;
    private InputStream mInputStream;
    private int mLimitRate = Integer.MAX_VALUE;
    private IAdapterToStreamAndRaf mOutputStream;
    private WeakReference<DownloadRequest> mRequest;

    public BandwidthLimitReader(ByteArrayPool byteArrayPool) {
        super(byteArrayPool);
    }

    public void setRequest(DownloadRequest request) {
        this.mRequest = new WeakReference(request);
        if (request instanceof SegmentRequest) {
            setLimitRate(((SegmentRequest) request).getRate());
        }
    }

    private void setLimitRate(int rate) {
        if (rate > 0) {
            this.mLimitRate = rate * 1000;
        }
    }

    public <E extends Throwable, T extends Throwable> void copy(InputStream inputStream, IAdapterToStreamAndRaf outputStream, IReaderCallback<E, T> callback) throws IOException, Throwable, Throwable, VolleyError {
        this.buffer = this.mByteArrayPool.getBuf(this.bufferSize);
        this.mInputStream = inputStream;
        this.mOutputStream = outputStream;
        RateLimiter limiter = RateLimiter.create(this.mLimitRate);
        while (true) {
            int count = inputStream.read(this.buffer);
            if (count == -1) {
                break;
            }
            try {
                outputStream.write(this.buffer, 0, count);
                if (callback != null) {
                    callback.readLoop(count);
                }
                if (LimitFlag.RATE_LIMIT) {
                    limiter.acquire(count);
                }
            } catch (IOException e) {
                throw new LocalFileError(e);
            }
        }
        if (this.mRequest != null) {
            this.mRequest.clear();
        }
        if (callback != null) {
            callback.end();
        }
    }

    public void close() throws IOException {
        this.mOutputStream.flush();
        this.mOutputStream.close();
        this.mOutputStream = null;
        this.mByteArrayPool.returnBuf(this.buffer);
        this.mInputStream.close();
        this.mInputStream = null;
        if (this.mRequest != null) {
            this.mRequest.clear();
        }
    }
}
