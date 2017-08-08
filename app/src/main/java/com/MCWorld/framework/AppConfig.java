package com.MCWorld.framework;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import com.MCWorld.framework.BaseDbManager.DataConnectionHelper;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.log.HLog.LogOptions;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.utils.UtilsApkPackage;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsProc;
import java.io.File;

public class AppConfig {
    private static final String TAG = "AppConfig";
    private static AppConfig mInstance;
    private String channel;
    private boolean isDebuggable;
    private Supplier<Integer> mBrightness;
    private File mConfigDir;
    private Context mContext;
    private Supplier<Integer> mErrHolderImg;
    private BaseHttpMgr mHttpMgr;
    private File mLogDir;
    private Supplier<Integer> mPlaceHolderImg;
    private BaseHttpMgr mResCtrlHttpMgr;
    private File mRoot;
    private String mRootPath;
    private NetworkChangeReceiver networkChangeReceiver;
    private Handler uiHandler;
    private int versionCode;
    private String versionName;

    public static synchronized AppConfig getInstance() {
        AppConfig appConfig;
        synchronized (AppConfig.class) {
            if (mInstance == null) {
                mInstance = new AppConfig();
            }
            appConfig = mInstance;
        }
        return appConfig;
    }

    private AppConfig() {
    }

    public void setAppContext(IConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("app config can not be NULL");
        }
        this.mContext = config.getContext();
        setDebuggable(UtilsApkPackage.getApkDebugable(this.mContext));
        this.networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mContext.registerReceiver(this.networkChangeReceiver, intentFilter);
        this.uiHandler = new Handler(Looper.getMainLooper());
        setVersionCode(UtilsApkPackage.getApkVersion(this.mContext));
        setVersionName(UtilsApkPackage.getApkVersionName(this.mContext));
        placeHolderImg(config.placeHolder(), config.errorHolder());
        brightness(config.getBrightness());
        AppConstant.setAppName(config.getAppName());
        setRootDir(config.getRootDir());
        setLogDir(config.getLogDir());
        initLog();
        setHttpMgr(config.getHttp());
        if (isDebuggable()) {
            HLog.info(TAG, String.format("------%s init------", new Object[]{AppConstant.getAppName()}), new Object[0]);
            HLog.info(TAG, "version = %s, mainprocess = %b, proces = %s", UtilsApkPackage.getApkVersionName(this.mContext), Boolean.valueOf(UtilsProc.isMainProcess(this.mContext)), UtilsProc.getAppNameByPid(this.mContext, Process.myPid()));
            HLog.info(TAG, "device = " + Build.DEVICE + ", manufacturer " + Build.MANUFACTURER + ", model " + Build.MODEL + ", product " + Build.PRODUCT, new Object[0]);
            HLog.info(TAG, "--------------------", new Object[0]);
        } else {
            HLog.info(TAG, String.format("------%s init------", new Object[]{AppConstant.getAppName()}), new Object[0]);
            HLog.info(TAG, "version = %s, mainprocess = %b, proces = %s", UtilsApkPackage.getApkVersionName(this.mContext), Boolean.valueOf(UtilsProc.isMainProcess(this.mContext)), UtilsProc.getAppNameByPid(this.mContext, Process.myPid()));
            HLog.info(TAG, "device = " + Build.DEVICE + ", manufacturer " + Build.MANUFACTURER + ", model " + Build.MODEL + ", product " + Build.PRODUCT, new Object[0]);
            HLog.info(TAG, "--------------------", new Object[0]);
        }
    }

    private void initLog() {
        LogOptions options = new LogOptions();
        options.honorVerbose = false;
        options.logFileName = AppConstant.LOG_FILE;
        options.logLevel = 2;
        HLog.initialize(getLogDir().getAbsolutePath(), options);
    }

    public void setResCtrlHttpMgr(BaseHttpMgr httpMgr) {
        this.mResCtrlHttpMgr = httpMgr;
    }

    public BaseHttpMgr getResCtrlHttpMgr() {
        return this.mResCtrlHttpMgr;
    }

    private void setHttpMgr(BaseHttpMgr httpMgr) {
        this.mHttpMgr = httpMgr;
    }

    public BaseHttpMgr getHttpMgr() {
        return this.mHttpMgr;
    }

    public Handler getUiHandler() {
        return this.uiHandler;
    }

    public void initDbManager(String name, DataConnectionHelper helper, int dbVersion) {
        BaseDbManager.init(name, this.mContext, helper, dbVersion);
    }

    public NetworkChangeReceiver getNetworkChangeReceiver() {
        return this.networkChangeReceiver;
    }

    public Context getAppContext() {
        return this.mContext;
    }

    public boolean isDebuggable() {
        return this.isDebuggable;
    }

    private void setDebuggable(boolean debuggable) {
        this.isDebuggable = debuggable;
    }

    private void setLogDir(String dir) {
        try {
            this.mLogDir = UtilsFile.getDiskCacheDir(this.mContext, dir);
            if (!this.mLogDir.exists() && !this.mLogDir.mkdirs()) {
                HLog.error(this, "Can't create log dir " + this.mLogDir, new Object[0]);
            }
        } catch (Exception e) {
            HLog.error(this, "Set log dir error", e, new Object[0]);
        }
    }

    public File getLogDir() {
        return this.mLogDir;
    }

    private void setRootDir(String rootDir) {
        File f = UtilsFile.getDiskCacheDir(this.mContext, rootDir);
        if (!(f == null || f.exists())) {
            f.mkdirs();
        }
        this.mRoot = f;
        this.mRootPath = rootDir;
    }

    public String getRootPath() {
        return this.mRootPath;
    }

    public File getRootDir() {
        return this.mRoot;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return this.channel;
    }

    private void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    private void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return this.versionName;
    }

    private void placeHolderImg(Supplier<Integer> placeHolder, Supplier<Integer> errHolder) {
        this.mPlaceHolderImg = placeHolder;
        this.mErrHolderImg = errHolder;
    }

    public int getPlaceHolderImg() {
        if (this.mPlaceHolderImg != null) {
            return ((Integer) this.mPlaceHolderImg.get()).intValue();
        }
        return 0;
    }

    public int getErrHolderImg() {
        if (this.mErrHolderImg != null) {
            return ((Integer) this.mErrHolderImg.get()).intValue();
        }
        return 0;
    }

    public int getBrightness() {
        if (this.mBrightness != null) {
            return ((Integer) this.mBrightness.get()).intValue();
        }
        return 0;
    }

    private void brightness(Supplier<Integer> supplier) {
        this.mBrightness = supplier;
    }
}
