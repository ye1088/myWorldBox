package com.huluxia.framework.http;

import android.content.Context;
import com.huluxia.HTApplication;
import com.huluxia.data.j;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.AppConstant;
import com.huluxia.framework.BaseHttpMgr;
import com.huluxia.framework.base.http.datasource.cache.NoCache;
import com.huluxia.framework.base.http.dispatcher.RequestQueue;
import com.huluxia.framework.base.http.transport.DownloadNetwork;
import com.huluxia.framework.base.http.transport.HurlStack;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.utils.m;
import com.huluxia.utils.o;
import com.huluxia.widget.Constants.DownFileType;
import java.io.File;
import java.util.Map;

public class HttpMgr extends BaseHttpMgr {
    public static final String PARAM_SESSION_KEY = "_key";
    private static final String WAP_DOWNLOAD_PATERN = "%s---%s---%d";
    private static HttpMgr mMgr;
    private File mDownloadJsRoot;
    private File mDownloadSkinRoot;
    private File mDownloadWoodRoot;
    private File mGameDownloadPath;
    private File mGbaDownloadPath;
    private File mGbcDownloadPath;
    private File mIsoDownloadPath;
    private File mN64DownloadPath;
    private File mNdsDownloadPath;
    private File mNesDownloadPath;
    private File mNgpDownloadPath;
    private File mSfcDownloadPath;
    private File mSmdDownloadpath;

    private HttpMgr() {
        if (AppConfig.getInstance().getAppContext() == null) {
            throw new IllegalStateException("app config has not been initialized");
        }
    }

    public static synchronized HttpMgr getInstance() {
        HttpMgr httpMgr;
        synchronized (HttpMgr.class) {
            if (mMgr == null) {
                mMgr = new HttpMgr();
            }
            httpMgr = mMgr;
        }
        return httpMgr;
    }

    public synchronized void init(Context context) {
        super.init(context);
        String path = "0roms" + File.separator;
        this.mIsoDownloadPath = new File(m.bh(context));
        if (!this.mIsoDownloadPath.exists()) {
            this.mIsoDownloadPath.mkdirs();
        }
        this.mGameDownloadPath = UtilsFile.getDiskCacheDir(context, "huluxia/mctool/maps");
        if (!this.mGameDownloadPath.exists()) {
            this.mGameDownloadPath.mkdirs();
        }
        this.mGbaDownloadPath = UtilsFile.getDiskCacheDir(context, path + "GBA");
        if (!this.mGbaDownloadPath.exists()) {
            this.mGbaDownloadPath.mkdirs();
        }
        this.mGbcDownloadPath = UtilsFile.getDiskCacheDir(context, path + "GBC");
        if (!this.mGbcDownloadPath.exists()) {
            this.mGbcDownloadPath.mkdirs();
        }
        this.mNdsDownloadPath = UtilsFile.getDiskCacheDir(context, path + "NDS");
        if (!this.mNdsDownloadPath.exists()) {
            this.mNdsDownloadPath.mkdirs();
        }
        this.mNesDownloadPath = UtilsFile.getDiskCacheDir(context, path + "NES");
        if (!this.mNesDownloadPath.exists()) {
            this.mNesDownloadPath.mkdirs();
        }
        this.mSfcDownloadPath = UtilsFile.getDiskCacheDir(context, path + "SFC");
        if (!this.mSfcDownloadPath.exists()) {
            this.mSfcDownloadPath.mkdirs();
        }
        this.mSmdDownloadpath = UtilsFile.getDiskCacheDir(context, path + "SMD");
        if (!this.mSmdDownloadpath.exists()) {
            this.mSmdDownloadpath.mkdirs();
        }
        this.mN64DownloadPath = UtilsFile.getDiskCacheDir(context, path + "N64");
        if (!this.mN64DownloadPath.exists()) {
            this.mN64DownloadPath.mkdirs();
        }
        this.mNgpDownloadPath = UtilsFile.getDiskCacheDir(context, path + "NGP");
        if (!this.mNgpDownloadPath.exists()) {
            this.mNgpDownloadPath.mkdirs();
        }
        this.mDownloadJsRoot = new File(com.huluxia.utils.UtilsFile.Kt());
        this.mDownloadWoodRoot = new File(com.huluxia.utils.UtilsFile.Kv());
        this.mDownloadSkinRoot = new File(com.huluxia.utils.UtilsFile.Ku());
        if (!this.mDownloadJsRoot.exists()) {
            this.mDownloadJsRoot.mkdir();
        }
        if (!this.mDownloadWoodRoot.exists()) {
            this.mDownloadWoodRoot.mkdir();
        }
        if (!this.mDownloadSkinRoot.exists()) {
            this.mDownloadSkinRoot.mkdir();
        }
    }

    public String getStringReqCachePath() {
        return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_CACHE;
    }

    public String getDownloadCachePath() {
        return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_DOWNLOAD_CACHE;
    }

    protected String getVoiceCachePath() {
        return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_VOICE_CACHE;
    }

    protected void fillCommonParam(Map<String, String> params) {
        if (!params.containsKey(BaseHttpMgr.PARAM_APP_VERSION)) {
            params.put(BaseHttpMgr.PARAM_APP_VERSION, UtilsVersion.getVersionString(AppConfig.getInstance().getAppContext()));
        }
        if (!params.containsKey("platform")) {
            params.put("platform", "2");
        }
        if (!params.containsKey(BaseHttpMgr.PARAM_MARKET_ID)) {
            params.put(BaseHttpMgr.PARAM_MARKET_ID, String.valueOf(HTApplication.bJ_mctool_huluxia_string()));
        }
        if (!params.containsKey(BaseHttpMgr.PARAM_DEVICE_CODE)) {
            params.put(BaseHttpMgr.PARAM_DEVICE_CODE, o.getDeviceId());
        }
        if (!params.containsKey("versioncode")) {
            params.put("versioncode", String.valueOf(UtilsVersion.getVersionCode(AppConfig.getInstance().getAppContext())));
        }
        if (!params.containsKey("_key")) {
            params.put("_key", j.ep().getToken());
        }
    }

    public File getGameDownloadPath() {
        return this.mGameDownloadPath;
    }

    public static String getWapDownloadRecord(String url, String name, int status) {
        return String.format(WAP_DOWNLOAD_PATERN, new Object[]{url, name, Integer.valueOf(status)});
    }

    public static String[] getWapDownloadRecord(String record) {
        String[] strArr = null;
        if (!UtilsFunction.empty((CharSequence) record)) {
            try {
                strArr = record.split(AppConstant.WAP_DOWNLOAD_RECORD_SEPARATOR);
            } catch (Exception e) {
            }
        }
        return strArr;
    }

    public File getGameDownloadPath(DownFileType type) {
        if (type == DownFileType.Js) {
            return this.mDownloadJsRoot;
        }
        if (type == DownFileType.Wood) {
            return this.mDownloadWoodRoot;
        }
        if (type == DownFileType.Skin) {
            return this.mDownloadSkinRoot;
        }
        return this.mGameDownloadPath;
    }

    protected void initDownloadRequest(Context context) {
        this.mDownloadQueue = new RequestQueue(new NoCache(), new DownloadNetwork(new HurlStack()), 3);
        this.mDownloadQueue.start();
    }
}
