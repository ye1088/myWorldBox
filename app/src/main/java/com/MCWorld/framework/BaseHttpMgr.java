package com.MCWorld.framework;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.y;
import com.MCWorld.framework.base.http.datasource.cache.Cache;
import com.MCWorld.framework.base.http.datasource.cache.DiskBasedCache;
import com.MCWorld.framework.base.http.datasource.cache.NoCache;
import com.MCWorld.framework.base.http.deliver.ExecutorDelivery;
import com.MCWorld.framework.base.http.dispatcher.RequestQueue;
import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.io.impl.request.DownloadRequest;
import com.MCWorld.framework.base.http.io.impl.request.DownloadRequestBuilder;
import com.MCWorld.framework.base.http.io.impl.request.GsonObjRequestBuilder;
import com.MCWorld.framework.base.http.io.impl.request.SegmentRequest;
import com.MCWorld.framework.base.http.io.impl.request.SegmentRequestBuilder;
import com.MCWorld.framework.base.http.io.impl.request.StringRequestBuilder;
import com.MCWorld.framework.base.http.io.impl.request.UploadRequest;
import com.MCWorld.framework.base.http.toolbox.entity.mime.content.ProgressFileBody;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.MCWorld.framework.base.http.transport.BasicNetwork;
import com.MCWorld.framework.base.http.transport.DownloadNetwork;
import com.MCWorld.framework.base.http.transport.HurlStack;
import com.MCWorld.framework.base.http.transport.Network;
import com.MCWorld.framework.base.http.transport.SegmentNetwork;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsMD5;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseHttpMgr implements INetworkClient {
    protected static final int DEFAULT_DOWNLOAD_Q_COUNT = 3;
    protected static final int DEFAULT_UPLOAD_Q_COUNT = 2;
    private static final int DEFAULT_UPLOAD_SOCKET_TIMEOUT = 20000;
    private static final String FILE_FORM_DATA_KEY = "_key";
    private static final String FILE_FORM_DATA_NAME = "file";
    private static final int FILE_UPLOAD_SOCKET_TIMEOUT = 30000;
    public static final int HTTP_CACHE_MAX_MEMORY_SIZE = 524288;
    public static final String PARAM_APP_VERSION = "app_version";
    public static final String PARAM_DEVICE_CODE = "device_code";
    public static final String PARAM_KEY = "_key";
    public static final String PARAM_MARKET_ID = "market_id";
    public static final String PARAM_PLATFORM = "platform";
    public static final String PARAM_VERSIONCODE = "versioncode";
    private static final String TAG = "HttpMgr";
    private Cache mCache;
    protected Context mContext;
    protected File mDefaultDownloadRoot;
    protected RequestQueue mDownloadQueue;
    private RequestQueue mQueue;
    protected RequestQueue mSegmentDownloadQueue;
    private RequestQueue mUploadQueue;
    private File mVoiceDownloadRoot;
    private HandlerThread responsePoster;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public static class Config {
        public boolean voiceCache;

        public Config() {
            this.voiceCache = true;
        }

        public Config(boolean voiceCache) {
            this.voiceCache = voiceCache;
        }
    }

    protected abstract String getDownloadCachePath();

    protected abstract String getStringReqCachePath();

    protected abstract String getVoiceCachePath();

    private Config getDefaultConfig() {
        return new Config();
    }

    public synchronized void init(@y Context context) {
        init(context, getDefaultConfig());
    }

    public synchronized void init(@y Context context, @y Config config) {
        this.mContext = context;
        initRequest(context);
        if (config.voiceCache) {
            initVoiceCache(context);
        }
        AppConfig.getInstance().getNetworkChangeReceiver().registerClient(this);
        this.mDefaultDownloadRoot = UtilsFile.getDiskCacheDir(context, getDownloadCachePath());
        if (!this.mDefaultDownloadRoot.exists()) {
            this.mDefaultDownloadRoot.mkdir();
        }
    }

    public synchronized void uninit() {
        AppConfig.getInstance().getNetworkChangeReceiver().unregisterClient(this);
    }

    public void onWifiAvailble(boolean availble) {
        if (!availble) {
            if (this.mDownloadQueue == null || !this.mDownloadQueue.hasRequestRunning()) {
                HLog.verbose(TAG, "no quest is running ,no wifi", new Object[0]);
            } else {
                this.mDownloadQueue.cancelAll(new 1(this), true);
            }
            if (this.mSegmentDownloadQueue == null || !this.mSegmentDownloadQueue.hasRequestRunning()) {
                HLog.verbose(TAG, "no quest in segment q is running ,no wifi", new Object[0]);
            } else {
                this.mSegmentDownloadQueue.cancelAll(new 2(this), true);
            }
            EventNotifyCenter.notifyEvent(BaseEvent.class, 260, new Object[0]);
        }
    }

    protected void initDownloadRequest(Context context) {
        if (this.mDownloadQueue == null || this.mSegmentDownloadQueue == null) {
            Cache cache = new NoCache();
            Network network = new DownloadNetwork(new HurlStack());
            this.responsePoster = new HandlerThread("response-poster");
            this.responsePoster.start();
            this.mDownloadQueue = new RequestQueue(cache, network, 3, new ExecutorDelivery(new Handler(this.responsePoster.getLooper())));
            this.mDownloadQueue.start();
            this.mSegmentDownloadQueue = new RequestQueue(cache, new SegmentNetwork(new HurlStack()), 3, new ExecutorDelivery(new Handler(this.responsePoster.getLooper())));
            this.mSegmentDownloadQueue.start();
        }
    }

    public void stopDownloadQueue() {
        if (this.mDownloadQueue != null) {
            this.mDownloadQueue.stop();
            this.mDownloadQueue = null;
        }
        if (this.mSegmentDownloadQueue != null) {
            this.mSegmentDownloadQueue.stop();
            this.mSegmentDownloadQueue = null;
        }
    }

    private void initUploadRequest(Context context) {
        this.mUploadQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()), 2);
        this.mUploadQueue.start();
    }

    public void stopUploadQueue() {
        if (this.mUploadQueue != null) {
            this.mUploadQueue.stop();
            this.mUploadQueue = null;
        }
    }

    protected void fillCommonParam(Map<String, String> map) {
    }

    private void initRequest(Context context) {
        this.mCache = new DiskBasedCache(UtilsFile.getDiskCacheDir(context, getStringReqCachePath()), 524288);
        this.mQueue = new RequestQueue(this.mCache, new BasicNetwork(new HurlStack()));
        this.mQueue.start();
    }

    private void initVoiceCache(Context context) {
        this.mVoiceDownloadRoot = UtilsFile.getDiskCacheDir(context, getVoiceCachePath());
        if (!this.mVoiceDownloadRoot.exists()) {
            this.mVoiceDownloadRoot.mkdirs();
        }
    }

    public File getVoiceDownloadRoot() {
        return this.mVoiceDownloadRoot;
    }

    public StringRequestBuilder getStringReqBuilder(String url) {
        return getStringReqBuilder(url, true);
    }

    public StringRequestBuilder getStringReqBuilder(String url, boolean fillCommon) {
        StringRequestBuilder builder = new StringRequestBuilder(this.mQueue);
        Map<String, String> param = new HashMap();
        if (fillCommon) {
            fillCommonParam(param);
        }
        builder.setParams(param);
        builder.setUrl(url);
        return builder;
    }

    public <T> GsonObjRequestBuilder<T> getGsonObjReqBuilder(String url, Class<T> clz) {
        return getGsonObjReqBuilder(url, clz, true);
    }

    public <T> GsonObjRequestBuilder<T> getGsonObjReqBuilder(String url, Class<T> clz, boolean fillCommon) {
        GsonObjRequestBuilder<T> builder = new GsonObjRequestBuilder(this.mQueue, clz);
        Map<String, String> param = new HashMap();
        if (fillCommon) {
            fillCommonParam(param);
        }
        ((GsonObjRequestBuilder) builder.setParams(param)).setUrl(url);
        return builder;
    }

    public DownloadRequestBuilder getDownloadReqBuilder(String url, String dir, String fileName) {
        String downloadDirPath;
        if (this.mDownloadQueue == null) {
            initDownloadRequest(AppConfig.getInstance().getAppContext());
        }
        DownloadRequestBuilder builder = new DownloadRequestBuilder(this.mDownloadQueue);
        if (UtilsFunction.empty((CharSequence) dir)) {
            downloadDirPath = this.mDefaultDownloadRoot.getAbsolutePath();
        } else {
            downloadDirPath = dir;
        }
        if (UtilsFunction.empty((CharSequence) fileName)) {
            fileName = UtilsMD5.getMD5String(url);
        }
        ((DownloadRequestBuilder) builder.setUrl(url)).setFilename(fileName).setDir(downloadDirPath);
        return builder;
    }

    public static String generateRecordId(String url) {
        return UtilsMD5.getMD5String(url + String.valueOf(SystemClock.elapsedRealtime()));
    }

    public SegmentRequestBuilder getSegmentReqBuilder(String url, String dir, String fileName) {
        String downloadDirPath;
        if (this.mSegmentDownloadQueue == null) {
            initDownloadRequest(AppConfig.getInstance().getAppContext());
        }
        SegmentRequestBuilder builder = new SegmentRequestBuilder(this.mSegmentDownloadQueue);
        if (UtilsFunction.empty((CharSequence) dir)) {
            downloadDirPath = this.mDefaultDownloadRoot.getAbsolutePath();
        } else {
            downloadDirPath = dir;
        }
        if (UtilsFunction.empty((CharSequence) fileName)) {
            fileName = UtilsMD5.getMD5String(url);
        }
        ((SegmentRequestBuilder) builder.setId(generateRecordId(url)).setUrl(url)).setFilename(fileName).setDir(downloadDirPath);
        return builder;
    }

    public SegmentRequestBuilder getSegmentReqBuilder(String id, String url, String dir, String fileName) {
        String downloadDirPath;
        if (this.mSegmentDownloadQueue == null) {
            initDownloadRequest(AppConfig.getInstance().getAppContext());
        }
        SegmentRequestBuilder builder = new SegmentRequestBuilder(this.mSegmentDownloadQueue);
        if (UtilsFunction.empty((CharSequence) dir)) {
            downloadDirPath = this.mDefaultDownloadRoot.getAbsolutePath();
        } else {
            downloadDirPath = dir;
        }
        if (UtilsFunction.empty((CharSequence) fileName)) {
            fileName = UtilsMD5.getMD5String(url);
        }
        ((SegmentRequestBuilder) builder.setId(id).setUrl(url)).setFilename(fileName).setDir(downloadDirPath);
        return builder;
    }

    public UploadRequest performUpload(String url, String fileAbsPath) {
        return performUpload(url, fileAbsPath, null);
    }

    public UploadRequest performUpload(String url, String fileAbsPath, Map<String, String> urlParams) {
        return performUpload(url, fileAbsPath, urlParams, null, null, null, null, true);
    }

    public UploadRequest performUpload(String url, String fileAbsPath, Map<String, String> urlParams, Listener<String> succListener, ErrorListener errorlistener, ProgressListener progressListener, CancelListener cancelListener, boolean commonParam) {
        return performUpload(url, fileAbsPath, urlParams, null, succListener, errorlistener, progressListener, cancelListener, commonParam);
    }

    public UploadRequest performUpload(String url, String fileAbsPath, Map<String, String> urlParams, Map<String, String> postParam, Listener<String> succListener, ErrorListener errorlistener, ProgressListener progressListener, CancelListener cancelListener, boolean commonParam) {
        if (UtilsFunction.empty((CharSequence) url) || UtilsFunction.empty((CharSequence) fileAbsPath)) {
            HLog.error(this, "performupload error, url = " + url + ", fileAbsPath = " + fileAbsPath, new Object[0]);
            return null;
        }
        File uploadFile = new File(fileAbsPath);
        if (!uploadFile.exists() || uploadFile.length() == 0) {
            HLog.error(this, "performupload file not exist or file length is zero", new Object[0]);
            return null;
        }
        if (this.mUploadQueue == null) {
            initUploadRequest(AppConfig.getInstance().getAppContext());
        }
        url = fillParam(url, urlParams, commonParam);
        Listener<String> wrapListener = new 3(this, succListener);
        ProgressListener wrapProgressListener = new 4(this, progressListener);
        ErrorListener wrapError = new 5(this, errorlistener);
        CancelListener wrapCancel = new 6(this, cancelListener);
        ProgressFileBody fileBody = new ProgressFileBody(url, uploadFile);
        UploadRequest request = new UploadRequest(url, wrapListener, wrapError, wrapProgressListener, wrapCancel);
        if (postParam != null) {
            request.setPostParam(postParam);
        }
        fileBody.setRequest(request);
        request.putFileBody("file", fileBody);
        request.setRetryPolicy(new DefaultRetryPolicy(FILE_UPLOAD_SOCKET_TIMEOUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.mUploadQueue.add(request);
        return request;
    }

    private boolean cancelDownloadRequest(String url, String filename) {
        boolean applyCanceled = false;
        if (this.mDownloadQueue != null) {
            applyCanceled = this.mDownloadQueue.cancelAll(new 7(this, url), true);
            HLog.info(TAG, "download queue apply cancel " + applyCanceled, new Object[0]);
        }
        if (this.mSegmentDownloadQueue == null) {
            return applyCanceled;
        }
        boolean cancel = this.mSegmentDownloadQueue.cancelAll(new 8(this, url), true);
        HLog.info(TAG, "segment download queue apply cancel " + cancel, new Object[0]);
        if (applyCanceled || cancel) {
            return true;
        }
        return false;
    }

    public DownloadRequest findDownloadReq(String url) {
        DownloadRequest downloadRequest = null;
        if (this.mDownloadQueue != null) {
            downloadRequest = (DownloadRequest) this.mDownloadQueue.findRequest(new 9(this, url));
        }
        if (this.mSegmentDownloadQueue != null) {
            return (SegmentRequest) this.mSegmentDownloadQueue.findRequest(new 10(this, url));
        }
        return downloadRequest;
    }

    public boolean pauseDownloadRequest(String url) {
        return cancelDownloadRequest(url, null);
    }

    public boolean pauseDownloadRequest(String url, String fileName) {
        return cancelDownloadRequest(url, fileName);
    }

    @Deprecated
    public void performStringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        performStringRequest(url, null, listener, errorListener);
    }

    @Deprecated
    public void performStringRequest(String url, Map<String, String> param, Listener<String> listener, ErrorListener errorListener) {
        performStringRequest(url, param, listener, errorListener, true);
    }

    @Deprecated
    public void performStringRequest(String url, Map<String, String> param, Listener<String> listener, ErrorListener errorListener, boolean commonParam) {
        performStringRequest(url, param, listener, errorListener, commonParam, true);
    }

    @Deprecated
    public void performStringRequest(String url, Map<String, String> param, Listener<String> listener, ErrorListener errorListener, boolean commonParam, boolean cache) {
        StringRequestBuilder builder = new StringRequestBuilder(this.mQueue);
        if (UtilsFunction.empty((Map) param)) {
            param = new HashMap();
        }
        if (commonParam) {
            fillCommonParam(param);
        }
        ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) builder.setUrl(url)).setParams(param)).setCache(cache)).setSuccListener(listener)).setErrListener(errorListener);
        builder.execute();
    }

    @Deprecated
    public void performPostStringRequest(String url, Map<String, String> param, Map<String, String> postParam, Listener<String> listener, ErrorListener errorListener, boolean commonParam, boolean cache) {
        performPostStringRequest(url, param, postParam, listener, errorListener, commonParam, cache, true);
    }

    @Deprecated
    public void performPostStringRequest(String url, Map<String, String> param, Map<String, String> postParam, Listener<String> listener, ErrorListener errorListener, boolean commonParam, boolean cache, boolean retry) {
        performPostStringRequest(url, param, postParam, listener, errorListener, commonParam, cache, retry, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS);
    }

    @Deprecated
    public void performPostStringRequest(String url, Map<String, String> param, Map<String, String> postParam, Listener<String> listener, ErrorListener errorListener, boolean commonParam, boolean cache, boolean retry, int timeout) {
        StringRequestBuilder builder = new StringRequestBuilder(this.mQueue);
        if (UtilsFunction.empty((Map) param)) {
            param = new HashMap();
        }
        if (commonParam) {
            fillCommonParam(param);
        }
        builder.setTimeoutMs(timeout);
        if (!retry) {
            builder.setRetryCount(0);
        }
        ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) builder.setUrl(url)).setMethod(1)).setParams(param)).setPostParam(postParam)).setCache(cache)).setSuccListener(listener)).setErrListener(errorListener);
        builder.execute();
    }

    @Deprecated
    private String fillParam(String url, Map<String, String> param) {
        return fillParam(url, param, true);
    }

    @Deprecated
    private String fillParam(String url, Map<String, String> param, boolean commonParam) {
        if (UtilsFunction.empty((Map) param)) {
            param = new HashMap();
        }
        if (commonParam) {
            fillCommonParam(param);
        }
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        for (String key : param.keySet()) {
            try {
                if (!UtilsFunction.empty((CharSequence) param.get(key))) {
                    sb.append(key).append(SimpleComparison.EQUAL_TO_OPERATION).append(URLEncoder.encode((String) param.get(key), "UTF-8")).append("&");
                }
            } catch (UnsupportedEncodingException e) {
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Deprecated
    public void performDownloadRequest(String url, String fileName, Listener<String> succListener, ErrorListener errorlistener, ProgressListener progressListener, CancelListener cancelListener) {
        performDownloadRequest(url, null, fileName, succListener, errorlistener, progressListener, cancelListener);
    }

    @Deprecated
    public void performDownloadRequest(String url, String path, String fileName, Listener<String> succListener, ErrorListener errorlistener, ProgressListener progressListener, CancelListener cancelListener) {
        performDownloadRequest(url, path, fileName, succListener, errorlistener, progressListener, cancelListener, null);
    }

    @Deprecated
    public void performDownloadRequest(String url, String path, String fileName, Listener<String> succListener, ErrorListener errorlistener, ProgressListener progressListener, CancelListener cancelListener, Object context) {
        performDownloadRequest(url, path, fileName, succListener, errorlistener, progressListener, cancelListener, false, context);
    }

    @Deprecated
    public void performDownloadRequest(String url, String path, String fileName, Listener<String> succListener, ErrorListener errorlistener, ProgressListener progressListener, CancelListener cancelListener, boolean rename, Object context) {
        getDownloadReqBuilder(url, path, fileName).setRename(rename).setSuccListener(succListener).setErrListener(errorlistener).setProgressListener(progressListener).setCancelListener(cancelListener).setContext(context).execute();
    }

    private String testRefer(String url) {
        HLog.debug(this, "test refer host %s", Uri.parse(url).getHost());
        return url.replace(Uri.parse(url).getHost(), "test.down.huluxia.net");
    }
}
