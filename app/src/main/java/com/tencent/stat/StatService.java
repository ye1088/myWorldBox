package com.tencent.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.stat.common.Env;
import com.tencent.stat.common.SdkProtection;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatConstants;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.StatPreferences;
import com.tencent.stat.event.AdditionEvent;
import com.tencent.stat.event.CustomEvent;
import com.tencent.stat.event.CustomEvent.Key;
import com.tencent.stat.event.ErrorEvent;
import com.tencent.stat.event.Event;
import com.tencent.stat.event.EventType;
import com.tencent.stat.event.MonitorStatEvent;
import com.tencent.stat.event.PageView;
import com.tencent.stat.event.SessionEnv;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import org.json.JSONObject;

public class StatService {
    private static boolean firstSession = true;
    private static Handler handler;
    private static volatile long lastActivityTimestamp = 0;
    private static volatile String lastReferPageId = "";
    private static volatile String last_pageId = "";
    private static StatLogger logger = StatCommonHelper.getLogger();
    private static volatile long nextDayStartTimestamp = 0;
    private static UncaughtExceptionHandler originalExceptionHandler = null;
    private static volatile int sessionId = 0;
    private static Map<Key, Long> timedEventMap = new WeakHashMap();
    private static Map<String, Long> timedPageEventMap = new WeakHashMap();

    static class StatTask implements Runnable {
        private Event ev;
        private StatReportStrategy strategy = null;

        public StatTask(Event event) {
            this.ev = event;
            this.strategy = StatConfig.getStatSendStrategy();
        }

        private void sendEvents() {
            if (StatStore.getInstance().getNumStoredEvents() > 0) {
                StatStore.getInstance().storeEvent(this.ev, null);
                StatStore.getInstance().loadEvents(-1);
                return;
            }
            sendEventsNow(true);
        }

        private void sendEventsNow(boolean z) {
            StatDispatchCallback statDispatchCallback = null;
            if (z) {
                statDispatchCallback = new StatDispatchCallback() {
                    public void onDispatchFailure() {
                        StatStore.getInstance().storeEvent(StatTask.this.ev, null);
                    }

                    public void onDispatchSuccess() {
                        StatStore.getInstance().loadEvents(-1);
                    }
                };
            }
            StatDispatcher.getInstance().send(this.ev, statDispatchCallback);
        }

        public void run() {
            if (!StatConfig.isEnableStatService()) {
                return;
            }
            if (this.ev.getType() == EventType.ERROR || this.ev.toJsonString().length() <= StatConfig.getMaxReportEventLength()) {
                if (StatConfig.getMaxSessionStatReportCount() > 0) {
                    if (StatConfig.getCurSessionStatReportCount() >= StatConfig.getMaxSessionStatReportCount()) {
                        StatService.logger.e((Object) "Times for reporting events has reached the limit of StatConfig.getMaxSessionStatReportCount() in current session.");
                        return;
                    }
                    StatConfig.incCurSessionStatReportCount();
                }
                StatService.logger.i("Lauch stat task in thread:" + Thread.currentThread().getName());
                Context context = this.ev.getContext();
                if (StatCommonHelper.isNetworkAvailable(context)) {
                    if (StatConfig.isEnableSmartReporting() && this.strategy != StatReportStrategy.ONLY_WIFI_NO_CACHE && StatCommonHelper.isWifiNet(context)) {
                        this.strategy = StatReportStrategy.INSTANT;
                    }
                    switch (this.strategy) {
                        case INSTANT:
                            sendEvents();
                            return;
                        case ONLY_WIFI:
                            if (StatCommonHelper.isWiFiActive(context)) {
                                sendEvents();
                                return;
                            } else {
                                StatStore.getInstance(context).storeEvent(this.ev, null);
                                return;
                            }
                        case APP_LAUNCH:
                        case DEVELOPER:
                            StatStore.getInstance(context).storeEvent(this.ev, null);
                            return;
                        case BATCH:
                            if (StatStore.getInstance(this.ev.getContext()) != null) {
                                StatStore.getInstance(context).storeEvent(this.ev, new StatDispatchCallback() {
                                    public void onDispatchFailure() {
                                    }

                                    public void onDispatchSuccess() {
                                        if (StatStore.getInstance().getNumStoredEvents() >= StatConfig.getMaxBatchReportCount()) {
                                            StatStore.getInstance().loadEvents(StatConfig.getMaxBatchReportCount());
                                        }
                                    }
                                });
                                return;
                            }
                            return;
                        case PERIOD:
                            try {
                                StatStore.getInstance(context).storeEvent(this.ev, null);
                                String str = "last_period_ts";
                                Long valueOf = Long.valueOf(StatPreferences.getLong(context, str, 0));
                                Long valueOf2 = Long.valueOf(System.currentTimeMillis());
                                if (Long.valueOf(Long.valueOf(valueOf2.longValue() - valueOf.longValue()).longValue() / 60000).longValue() > ((long) StatConfig.getSendPeriodMinutes())) {
                                    StatStore.getInstance(context).loadEvents(-1);
                                    StatPreferences.putLong(context, str, valueOf2.longValue());
                                    return;
                                }
                                return;
                            } catch (Exception e) {
                                StatService.logger.e(e);
                                return;
                            }
                        case ONLY_WIFI_NO_CACHE:
                            if (StatCommonHelper.isWiFiActive(context)) {
                                sendEventsNow(false);
                                return;
                            }
                            return;
                        default:
                            StatService.logger.error("Invalid stat strategy:" + StatConfig.getStatSendStrategy());
                            return;
                    }
                }
                StatStore.getInstance(context).storeEvent(this.ev, null);
                return;
            }
            StatService.logger.e("Event length exceed StatConfig.getMaxReportEventLength(): " + StatConfig.getMaxReportEventLength());
        }
    }

    public static void commitEvents(Context context, int i) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.commitEvents() can not be null!");
        } else if (i < -1 || i == 0) {
            logger.error((Object) "The maxNumber of StatService.commitEvents() should be -1 or bigger than 0.");
        } else {
            try {
                StatStore.getInstance(context).loadEvents(i);
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    static JSONObject getEncodeConfig() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (StatConfig.sdkCfg.version != 0) {
                jSONObject2.put("v", StatConfig.sdkCfg.version);
            }
            jSONObject.put(Integer.toString(StatConfig.sdkCfg.type), jSONObject2);
            jSONObject2 = new JSONObject();
            if (StatConfig.userCfg.version != 0) {
                jSONObject2.put("v", StatConfig.userCfg.version);
            }
            jSONObject.put(Integer.toString(StatConfig.userCfg.type), jSONObject2);
        } catch (Exception e) {
            logger.e(e);
        }
        return jSONObject;
    }

    private static Handler getHandler(Context context) {
        init(context);
        return handler;
    }

    static int getSessionID(Context context, boolean z) {
        int i = 1;
        long currentTimeMillis = System.currentTimeMillis();
        if (!z || currentTimeMillis - lastActivityTimestamp < ((long) StatConfig.getSessionTimoutMillis())) {
            boolean z2 = false;
        } else {
            int i2 = 1;
        }
        lastActivityTimestamp = currentTimeMillis;
        if (nextDayStartTimestamp == 0) {
            nextDayStartTimestamp = StatCommonHelper.getTomorrowStartMilliseconds();
        }
        if (currentTimeMillis >= nextDayStartTimestamp) {
            nextDayStartTimestamp = StatCommonHelper.getTomorrowStartMilliseconds();
            if (StatStore.getInstance(context).getUser(context).getType() != 1) {
                StatStore.getInstance(context).getUser(context).setType(1);
            }
            StatConfig.setCurrentDaySessionNumbers(0);
            i2 = 1;
        }
        if (!firstSession) {
            i = i2;
        }
        if (i != 0) {
            if (StatConfig.getCurrentDaySessionNumbers() < StatConfig.getMaxDaySessionNumbers()) {
                sendNewSessionEnv(context);
            } else {
                logger.e((Object) "Exceed StatConfig.getMaxDaySessionNumbers().");
            }
        }
        if (firstSession) {
            SdkProtection.endCheck(context);
            firstSession = false;
        }
        return sessionId;
    }

    static void init(Context context) {
        if (context == null || handler != null || !isServiceStatActive(context)) {
            return;
        }
        if (SdkProtection.beginCheck(context)) {
            StatStore.getInstance(context);
            HandlerThread handlerThread = new HandlerThread("StatService");
            handlerThread.start();
            StatDispatcher.setApplicationContext(context);
            handler = new Handler(handlerThread.getLooper());
            originalExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (StatConfig.isAutoExceptionCaught()) {
                final Context applicationContext = context.getApplicationContext();
                Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                    public void uncaughtException(Thread thread, Throwable th) {
                        if (StatConfig.isEnableStatService()) {
                            StatStore.getInstance(applicationContext).storeEvent(new ErrorEvent(applicationContext, StatService.getSessionID(applicationContext, false), 2, th), null);
                            StatService.logger.debug("MTA has caught the following uncaught exception:");
                            StatService.logger.error((Object) th);
                            if (StatService.originalExceptionHandler != null) {
                                StatService.logger.debug("Call the original uncaught exception handler.");
                                StatService.originalExceptionHandler.uncaughtException(thread, th);
                                return;
                            }
                            StatService.logger.debug("Original uncaught exception handler not set.");
                        }
                    }
                });
            } else {
                logger.warn("MTA SDK AutoExceptionCaught is disable");
            }
            if (StatConfig.getStatSendStrategy() == StatReportStrategy.APP_LAUNCH && StatCommonHelper.isNetworkAvailable(context)) {
                StatStore.getInstance(context).loadEvents(-1);
            }
            logger.d("Init MTA StatService success.");
            return;
        }
        logger.error((Object) "ooh, Compatibility problem was found in this device!");
        logger.error((Object) "If you are on debug mode, please delete apk and try again.");
        StatConfig.setEnableStatService(false);
    }

    static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    static boolean isServiceStatActive(Context context) {
        if (StatCommonHelper.getSDKLongVersion(StatConstants.VERSION) > StatPreferences.getLong(context, StatConfig.HIBERNATE, 0)) {
            return true;
        }
        StatConfig.setEnableStatService(false);
        return false;
    }

    public static void onPause(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.onPause() can not be null!");
            return;
        }
        try {
            String activityName = StatCommonHelper.getActivityName(context);
            Long l = (Long) timedPageEventMap.remove(activityName);
            if (l != null) {
                l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                if (l.longValue() == 0) {
                    l = Long.valueOf(1);
                }
                if (lastReferPageId.equals(activityName)) {
                    lastReferPageId = "-";
                }
                Event pageView = new PageView(context, lastReferPageId, getSessionID(context, false), l);
                if (!pageView.getPageId().equals(last_pageId)) {
                    logger.warn("Invalid invocation since previous onResume on diff page.");
                }
                if (getHandler(context) != null) {
                    getHandler(context).post(new StatTask(pageView));
                }
                lastReferPageId = activityName;
                return;
            }
            logger.e("Starttime for PageID:" + activityName + " not found, lost onResume()?");
        } catch (Throwable th) {
            reportSdkSelfException(context, th);
        }
    }

    public static void onResume(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.onResume() can not be null!");
            return;
        }
        try {
            if (timedPageEventMap.size() >= StatConfig.getMaxParallelTimmingEvents()) {
                logger.error("The number of page events exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                return;
            }
            last_pageId = StatCommonHelper.getActivityName(context);
            if (last_pageId == null) {
                return;
            }
            if (timedPageEventMap.containsKey(last_pageId)) {
                logger.e("Duplicate PageID : " + last_pageId + ", onResume() repeated?");
                return;
            }
            timedPageEventMap.put(last_pageId, Long.valueOf(System.currentTimeMillis()));
            getSessionID(context, true);
        } catch (Throwable th) {
            reportSdkSelfException(context, th);
        }
    }

    public static void reportAppMonitorStat(Context context, StatAppMonitor statAppMonitor) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.reportAppMonitorStat() can not be null!");
        } else if (statAppMonitor == null) {
            logger.error((Object) "The StatAppMonitor of StatService.reportAppMonitorStat() can not be null!");
        } else if (statAppMonitor.getInterfaceName() == null) {
            logger.error((Object) "The interfaceName of StatAppMonitor on StatService.reportAppMonitorStat() can not be null!");
        } else {
            try {
                Event monitorStatEvent = new MonitorStatEvent(context, getSessionID(context, false), statAppMonitor);
                if (getHandler(context) != null) {
                    getHandler(context).post(new StatTask(monitorStatEvent));
                }
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    public static void reportError(Context context, String str) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.reportError() can not be null!");
        } else if (isEmpty(str)) {
            logger.error((Object) "Error message in StatService.reportError() is empty.");
        } else {
            try {
                Event errorEvent = new ErrorEvent(context, getSessionID(context, false), str);
                if (getHandler(context) != null) {
                    getHandler(context).post(new StatTask(errorEvent));
                }
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    public static void reportException(Context context, Throwable th) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.reportException() can not be null!");
        } else if (th == null) {
            logger.error((Object) "The Throwable error message of StatService.reportException() can not be null!");
        } else {
            Event errorEvent = new ErrorEvent(context, getSessionID(context, false), 1, th);
            if (getHandler(context) != null) {
                getHandler(context).post(new StatTask(errorEvent));
            }
        }
    }

    public static void reportQQ(Context context, String str) {
        if (str == null) {
            str = "";
        }
        if (!StatConfig.qq.equals(str)) {
            StatConfig.qq = str;
            sendAdditionEvent(context, null);
        }
    }

    static void reportSdkSelfException(Context context, Throwable th) {
        try {
            if (!StatConfig.isEnableStatService()) {
                return;
            }
            if (context == null) {
                logger.error((Object) "The Context of StatService.reportSdkSelfException() can not be null!");
                return;
            }
            Event errorEvent = new ErrorEvent(context, getSessionID(context, false), 99, th);
            if (getHandler(context) != null) {
                getHandler(context).post(new StatTask(errorEvent));
            }
        } catch (Throwable th2) {
            logger.e("reportSdkSelfException error: " + th2);
        }
    }

    static void sendAdditionEvent(Context context, Map<String, ?> map) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.sendAdditionEvent() can not be null!");
            return;
        }
        try {
            Event additionEvent = new AdditionEvent(context, getSessionID(context, false), map);
            if (getHandler(context) != null) {
                getHandler(context).post(new StatTask(additionEvent));
            }
        } catch (Throwable th) {
            reportSdkSelfException(context, th);
        }
    }

    static void sendNewSessionEnv(Context context) {
        if (getHandler(context) != null) {
            logger.d("start new session.");
            sessionId = StatCommonHelper.getNextSessionID();
            StatConfig.setCurSessionStatReportCount(0);
            StatConfig.incCurrentDaySessionNumbers();
            getHandler(context).post(new StatTask(new SessionEnv(context, sessionId, getEncodeConfig())));
        }
    }

    public static void setEnvAttributes(Context context, Map<String, String> map) {
        if (map == null || map.size() > 512) {
            logger.error((Object) "The map in setEnvAttributes can't be null or its size can't exceed 512.");
            return;
        }
        try {
            Env.appendEnvAttr(context, map);
        } catch (Exception e) {
            logger.e(e);
        }
    }

    public static void startNewSession(Context context) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.startNewSession() can not be null!");
            return;
        }
        try {
            stopSession();
            getSessionID(context, true);
        } catch (Throwable th) {
            reportSdkSelfException(context, th);
        }
    }

    public static boolean startStatService(Context context, String str, String str2) throws MtaSDkException {
        if (StatConfig.isEnableStatService()) {
            String str3 = StatConstants.VERSION;
            logger.d("MTA SDK version, current: " + str3 + " ,required: " + str2);
            String str4 = "";
            if (context == null || str2 == null) {
                str3 = "Context or mtaSdkVersion in StatService.startStatService() is null, please check it!";
                logger.error((Object) str3);
                StatConfig.setEnableStatService(false);
                throw new MtaSDkException(str3);
            } else if (StatCommonHelper.getSDKLongVersion(str3) < StatCommonHelper.getSDKLongVersion(str2)) {
                str3 = ("MTA SDK version conflicted, current: " + str3 + ",required: " + str2) + ". please delete the current SDK and download the latest one. official website: http://mta.qq.com/ or http://mta.oa.com/";
                logger.error((Object) str3);
                StatConfig.setEnableStatService(false);
                throw new MtaSDkException(str3);
            } else {
                try {
                    str3 = StatConfig.getInstallChannel(context);
                    if (str3 == null || str3.length() == 0) {
                        StatConfig.setInstallChannel("-");
                    }
                    StatConfig.setAppKey(context, str);
                    getHandler(context);
                    return true;
                } catch (Object th) {
                    logger.e(th);
                    return false;
                }
            }
        }
        logger.error((Object) "MTA StatService is disable.");
        return false;
    }

    public static void stopSession() {
        lastActivityTimestamp = 0;
    }

    public static void trackCustomBeginEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
        } else if (isEmpty(str)) {
            logger.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
        } else {
            try {
                CustomEvent customEvent = new CustomEvent(context, getSessionID(context, false), str);
                customEvent.setArgs(strArr);
                Key key = customEvent.getKey();
                if (timedEventMap.containsKey(key)) {
                    logger.error("Duplicate CustomEvent key: " + key.toString() + ", trackCustomBeginEvent() repeated?");
                } else if (timedEventMap.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                    timedEventMap.put(key, Long.valueOf(System.currentTimeMillis()));
                } else {
                    logger.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                }
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    public static void trackCustomBeginKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.trackCustomBeginEvent() can not be null!");
        } else if (isEmpty(str)) {
            logger.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
        } else {
            try {
                CustomEvent customEvent = new CustomEvent(context, getSessionID(context, false), str);
                customEvent.setProperties(properties);
                Key key = customEvent.getKey();
                if (timedEventMap.containsKey(key)) {
                    logger.error("Duplicate CustomEvent key: " + key.toString() + ", trackCustomBeginKVEvent() repeated?");
                } else if (timedEventMap.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                    timedEventMap.put(key, Long.valueOf(System.currentTimeMillis()));
                } else {
                    logger.error("The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                }
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    public static void trackCustomEndEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
        } else if (isEmpty(str)) {
            logger.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
        } else {
            try {
                Event customEvent = new CustomEvent(context, getSessionID(context, false), str);
                customEvent.setArgs(strArr);
                Long l = (Long) timedEventMap.remove(customEvent.getKey());
                if (l != null) {
                    l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                    customEvent.setDuration(Long.valueOf(l.longValue() == 0 ? 1 : l.longValue()).longValue());
                    if (getHandler(context) != null) {
                        getHandler(context).post(new StatTask(customEvent));
                        return;
                    }
                    return;
                }
                logger.error("No start time found for custom event: " + customEvent.getKey().toString() + ", lost trackCustomBeginEvent()?");
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    public static void trackCustomEndKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.trackCustomEndEvent() can not be null!");
        } else if (isEmpty(str)) {
            logger.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
        } else {
            try {
                Event customEvent = new CustomEvent(context, getSessionID(context, false), str);
                customEvent.setProperties(properties);
                Long l = (Long) timedEventMap.remove(customEvent.getKey());
                if (l != null) {
                    l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                    customEvent.setDuration(Long.valueOf(l.longValue() == 0 ? 1 : l.longValue()).longValue());
                    if (getHandler(context) != null) {
                        getHandler(context).post(new StatTask(customEvent));
                        return;
                    }
                    return;
                }
                logger.error("No start time found for custom event: " + customEvent.getKey().toString() + ", lost trackCustomBeginKVEvent()?");
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    public static void trackCustomEvent(Context context, String str, String... strArr) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
        } else if (isEmpty(str)) {
            logger.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
        } else {
            try {
                Event customEvent = new CustomEvent(context, getSessionID(context, false), str);
                customEvent.setArgs(strArr);
                if (getHandler(context) != null) {
                    getHandler(context).post(new StatTask(customEvent));
                }
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }

    public static void trackCustomKVEvent(Context context, String str, Properties properties) {
        if (!StatConfig.isEnableStatService()) {
            return;
        }
        if (context == null) {
            logger.error((Object) "The Context of StatService.trackCustomEvent() can not be null!");
        } else if (isEmpty(str)) {
            logger.error((Object) "The event_id of StatService.trackCustomEvent() can not be null or empty.");
        } else {
            try {
                Event customEvent = new CustomEvent(context, getSessionID(context, false), str);
                customEvent.setProperties(properties);
                if (getHandler(context) != null) {
                    getHandler(context).post(new StatTask(customEvent));
                }
            } catch (Throwable th) {
                reportSdkSelfException(context, th);
            }
        }
    }
}
