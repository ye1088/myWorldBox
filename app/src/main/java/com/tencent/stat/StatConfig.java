package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatConstants;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.StatPreferences;
import java.util.Iterator;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

public class StatConfig {
    static String HIBERNATE = "__HIBERNATE__";
    private static String appkey;
    private static volatile int curSessionStatReportCount = 0;
    private static int currentDaySessionNumbers = 0;
    private static boolean enableSmartReporting = true;
    private static boolean enableStatService = true;
    private static String installChannel;
    public static boolean isAutoExceptionCaught = true;
    private static long lastReportStrategyCheckTime = 0;
    private static StatLogger logger = StatCommonHelper.getLogger();
    private static int maxBatchReportCount = 30;
    private static int maxDaySessionNumbers = 20;
    private static int maxLoadEventCount = 30;
    private static int maxParallelTimmingEvents = 1024;
    private static int maxReportEventLength = 4096;
    private static long maxReportStrategyCheckTime = 300000;
    private static int maxSendRetryCount = 3;
    private static int maxSessionStatReportCount = 0;
    private static int maxStoreEventCount = 1024;
    static String qq = "";
    private static boolean reportTrafficStat = false;
    static OnlineConfig sdkCfg = new OnlineConfig(1);
    private static int sendPeriodMinutes = 1440;
    private static int sessionTimoutMillis = 30000;
    private static String statReportUrl = "http://pingma.qq.com:80/mstat/report";
    private static StatReportStrategy statSendStrategy = StatReportStrategy.APP_LAUNCH;
    private static String ta_http_proxy = null;
    static OnlineConfig userCfg = new OnlineConfig(2);

    static void checkHibernate(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString(HIBERNATE);
            logger.d("hibernateVer:" + string + ", current version:" + StatConstants.VERSION);
            long sDKLongVersion = StatCommonHelper.getSDKLongVersion(string);
            if (StatCommonHelper.getSDKLongVersion(StatConstants.VERSION) <= sDKLongVersion) {
                StatPreferences.putLong(StatDispatcher.getApplicationContext(), HIBERNATE, sDKLongVersion);
                setEnableStatService(false);
                logger.warn("MTA has disable for SDK version of " + string + " or lower.");
            }
        } catch (JSONException e) {
            logger.d("__HIBERNATE__ not found.");
        }
    }

    public static String getAppKey(Context context) {
        if (appkey != null) {
            return appkey;
        }
        if (context != null && appkey == null) {
            appkey = StatCommonHelper.getAppKey(context);
        }
        if (appkey == null || appkey.trim().length() == 0) {
            logger.error("AppKey can not be null or empty, please read Developer's Guide first!");
        }
        return appkey;
    }

    public static int getCurSessionStatReportCount() {
        return curSessionStatReportCount;
    }

    static int getCurrentDaySessionNumbers() {
        return currentDaySessionNumbers;
    }

    public static String getCustomProperty(String str) {
        try {
            return userCfg.props.getString(str);
        } catch (JSONException e) {
            return null;
        }
    }

    public static String getCustomProperty(String str, String str2) {
        try {
            String string = userCfg.props.getString(str);
            return string != null ? string : str2;
        } catch (JSONException e) {
            return str2;
        }
    }

    public static String getInstallChannel(Context context) {
        if (installChannel != null) {
            return installChannel;
        }
        installChannel = StatCommonHelper.getInstallChannel(context);
        if (installChannel == null || installChannel.trim().length() == 0) {
            logger.e("installChannel can not be null or empty, please read Developer's Guide first!");
        }
        return installChannel;
    }

    public static int getMaxBatchReportCount() {
        return maxBatchReportCount;
    }

    public static int getMaxDaySessionNumbers() {
        return maxDaySessionNumbers;
    }

    static int getMaxLoadEventCount() {
        return maxLoadEventCount;
    }

    public static int getMaxParallelTimmingEvents() {
        return maxParallelTimmingEvents;
    }

    public static int getMaxReportEventLength() {
        return maxReportEventLength;
    }

    public static int getMaxSendRetryCount() {
        return maxSendRetryCount;
    }

    public static int getMaxSessionStatReportCount() {
        return maxSessionStatReportCount;
    }

    public static int getMaxStoreEventCount() {
        return maxStoreEventCount;
    }

    public static String getQQ() {
        return qq;
    }

    public static int getSendPeriodMinutes() {
        return sendPeriodMinutes;
    }

    public static int getSessionTimoutMillis() {
        return sessionTimoutMillis;
    }

    static HttpHost getStatHttpProxy() {
        if (ta_http_proxy == null || ta_http_proxy.length() <= 0) {
            return null;
        }
        String str = ta_http_proxy;
        String[] split = str.split(":");
        int i = 80;
        if (split.length == 2) {
            str = split[0];
            i = Integer.parseInt(split[1]);
        }
        return new HttpHost(str, i);
    }

    public static String getStatReportUrl() {
        return statReportUrl;
    }

    public static StatReportStrategy getStatSendStrategy() {
        return statSendStrategy;
    }

    static String getStoredAppkeys(Context context) {
        return StatCommonHelper.decode(StatPreferences.getString(context, "_mta_ky_tag_", null));
    }

    static synchronized void incCurSessionStatReportCount() {
        synchronized (StatConfig.class) {
            curSessionStatReportCount++;
        }
    }

    static void incCurrentDaySessionNumbers() {
        currentDaySessionNumbers++;
    }

    public static boolean isAutoExceptionCaught() {
        return isAutoExceptionCaught;
    }

    static boolean isBetween(int i, int i2, int i3) {
        return i >= i2 && i <= i3;
    }

    public static boolean isDebugEnable() {
        return StatCommonHelper.getLogger().isDebugEnable();
    }

    public static boolean isEnableSmartReporting() {
        return enableSmartReporting;
    }

    public static boolean isEnableStatService() {
        return enableStatService;
    }

    public static void setAppKey(Context context, String str) {
        if (context == null) {
            logger.error("ctx in StatConfig.setAppKey() is null");
        } else if (str == null || str.length() > 256) {
            logger.error("appkey in StatConfig.setAppKey() is null or exceed 256 bytes");
        } else {
            if (appkey == null) {
                appkey = getStoredAppkeys(context);
            }
            if ((updateAppkeys(str) | updateAppkeys(StatCommonHelper.getAppKey(context))) != 0) {
                storeAppkeys(context, appkey);
            }
        }
    }

    @Deprecated
    public static void setAppKey(String str) {
        if (str == null) {
            logger.error("appkey in StatConfig.setAppKey() is null");
        } else if (str.length() > 256) {
            logger.error("The length of appkey cann't exceed 256 bytes.");
        } else {
            appkey = str;
        }
    }

    public static void setAutoExceptionCaught(boolean z) {
        isAutoExceptionCaught = z;
    }

    static void setConfig(OnlineConfig onlineConfig) throws JSONException {
        if (onlineConfig.type == sdkCfg.type) {
            sdkCfg = onlineConfig;
            updateReportStrategy(sdkCfg.props);
        } else if (onlineConfig.type == userCfg.type) {
            userCfg = onlineConfig;
        }
    }

    static synchronized void setCurSessionStatReportCount(int i) {
        synchronized (StatConfig.class) {
            curSessionStatReportCount = i;
        }
    }

    static void setCurrentDaySessionNumbers(int i) {
        if (i >= 0) {
            currentDaySessionNumbers = i;
        }
    }

    public static void setDebugEnable(boolean z) {
        StatCommonHelper.getLogger().setDebugEnable(z);
    }

    public static void setEnableSmartReporting(boolean z) {
        enableSmartReporting = z;
    }

    public static void setEnableStatService(boolean z) {
        enableStatService = z;
        if (!z) {
            logger.warn("!!!!!!MTA StatService has been disabled!!!!!!");
        }
    }

    public static void setInstallChannel(String str) {
        if (str.length() > 128) {
            logger.error("the length of installChannel can not exceed the range of 128 bytes.");
        } else {
            installChannel = str;
        }
    }

    public static void setMaxBatchReportCount(int i) {
        if (isBetween(i, 2, 1000)) {
            maxBatchReportCount = i;
        } else {
            logger.error("setMaxBatchReportCount can not exceed the range of [2,1000].");
        }
    }

    public static void setMaxDaySessionNumbers(int i) {
        if (i <= 0) {
            logger.e("maxDaySessionNumbers must be greater than 0.");
        } else {
            maxDaySessionNumbers = i;
        }
    }

    public static void setMaxParallelTimmingEvents(int i) {
        if (isBetween(i, 1, 4096)) {
            maxParallelTimmingEvents = i;
        } else {
            logger.error("setMaxParallelTimmingEvents can not exceed the range of [1, 4096].");
        }
    }

    public static void setMaxReportEventLength(int i) {
        if (i <= 0) {
            logger.error("maxReportEventLength on setMaxReportEventLength() must greater than 0.");
        } else {
            maxReportEventLength = i;
        }
    }

    public static void setMaxSendRetryCount(int i) {
        if (isBetween(i, 1, 10)) {
            maxSendRetryCount = i;
        } else {
            logger.error("setMaxSendRetryCount can not exceed the range of [1,10].");
        }
    }

    public static void setMaxSessionStatReportCount(int i) {
        if (i < 0) {
            logger.error("maxSessionStatReportCount cannot be less than 0.");
        } else {
            maxSessionStatReportCount = i;
        }
    }

    public static void setMaxStoreEventCount(int i) {
        if (isBetween(i, 0, 500000)) {
            maxStoreEventCount = i;
        } else {
            logger.error("setMaxStoreEventCount can not exceed the range of [0, 500000].");
        }
    }

    public static void setQQ(Context context, String str) {
        StatService.reportQQ(context, str);
    }

    public static void setSendPeriodMinutes(int i) {
        if (isBetween(i, 1, 10080)) {
            sendPeriodMinutes = i;
        } else {
            logger.error("setSendPeriodMinutes can not exceed the range of [1, 7*24*60] minutes.");
        }
    }

    public static void setSessionTimoutMillis(int i) {
        if (isBetween(i, 1000, 86400000)) {
            sessionTimoutMillis = i;
        } else {
            logger.error("setSessionTimoutMillis can not exceed the range of [1000, 24 * 60 * 60 * 1000].");
        }
    }

    public static void setStatReportUrl(String str) {
        if (str == null || str.length() == 0) {
            logger.error("statReportUrl cannot be null or empty.");
        } else {
            statReportUrl = str;
        }
    }

    public static void setStatSendStrategy(StatReportStrategy statReportStrategy) {
        statSendStrategy = statReportStrategy;
        logger.d("Change to statSendStrategy: " + statReportStrategy);
    }

    static void storeAppkeys(Context context, String str) {
        if (str != null) {
            StatPreferences.putString(context, "_mta_ky_tag_", StatCommonHelper.encode(str));
        }
    }

    private static boolean updateAppkeys(String str) {
        if (str == null) {
            return false;
        }
        if (appkey == null) {
            appkey = str;
            return true;
        } else if (appkey.contains(str)) {
            return false;
        } else {
            appkey += "|" + str;
            return true;
        }
    }

    static void updateCfg(OnlineConfig onlineConfig, JSONObject jSONObject) {
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase("v")) {
                    onlineConfig.version = jSONObject.getInt(str);
                } else if (str.equalsIgnoreCase("c")) {
                    str = jSONObject.getString("c");
                    if (str.length() > 0) {
                        onlineConfig.props = new JSONObject(str);
                    }
                } else if (str.equalsIgnoreCase("m")) {
                    onlineConfig.md5sum = jSONObject.getString("m");
                }
            }
            StatStore instance = StatStore.getInstance(StatDispatcher.getApplicationContext());
            if (instance != null) {
                instance.storeCfg(onlineConfig);
            }
            if (onlineConfig.type == sdkCfg.type) {
                updateReportStrategy(onlineConfig.props);
                checkHibernate(onlineConfig.props);
            }
        } catch (Exception e) {
            logger.e(e);
        } catch (Throwable th) {
            logger.e(th);
        }
    }

    static void updateOnlineConfig(JSONObject jSONObject) {
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (str.equalsIgnoreCase(Integer.toString(sdkCfg.type))) {
                    updateCfg(sdkCfg, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase(Integer.toString(userCfg.type))) {
                    updateCfg(userCfg, jSONObject.getJSONObject(str));
                } else if (str.equalsIgnoreCase("rs")) {
                    StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt(str));
                    if (statReportStrategy != null) {
                        statSendStrategy = statReportStrategy;
                        logger.d("Change to ReportStrategy:" + statReportStrategy.name());
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            logger.e(e);
        }
    }

    static void updateReportStrategy(JSONObject jSONObject) {
        try {
            StatReportStrategy statReportStrategy = StatReportStrategy.getStatReportStrategy(jSONObject.getInt("rs"));
            if (statReportStrategy != null) {
                setStatSendStrategy(statReportStrategy);
                logger.debug("Change to ReportStrategy: " + statReportStrategy.name());
            }
        } catch (JSONException e) {
            logger.d("rs not found.");
        }
    }
}
