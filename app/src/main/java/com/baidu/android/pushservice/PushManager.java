package com.baidu.android.pushservice;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import com.baidu.android.pushservice.apiproxy.BridgePushManager;
import java.util.HashMap;
import java.util.List;

public class PushManager {
    private static final String a = "com.baidu.pushservice.PushSettings.connect_state";

    private static boolean a(Context context) {
        try {
            return System.getInt(context.getContentResolver(), a) == 1;
        } catch (SettingNotFoundException e) {
            return false;
        }
    }

    @Deprecated
    public static void activityStarted(Activity activity) {
        LoadExecutor.excuteMethod(new 4(activity), activity);
    }

    @Deprecated
    public static void activityStoped(Activity activity) {
        LoadExecutor.excuteMethod(new 5(activity), activity);
    }

    @Deprecated
    public static void bind(Context context, int i) {
        LoadExecutor.excuteMethod(new 9(context, i), context);
    }

    @Deprecated
    public static void bindGroup(Context context, String str) {
        LoadExecutor.excuteMethod(new 16(context, str), context);
    }

    public static void delLappTags(Context context, String str, List<String> list) {
        LoadExecutor.excuteMethod(new 24(context, str, list), context);
    }

    public static void delSDKTags(Context context, String str, List<String> list) {
        LoadExecutor.excuteMethod(new 22(context, str, list), context);
    }

    public static void delTags(Context context, List<String> list) {
        LoadExecutor.excuteMethod(new 21(context, list), context);
    }

    @Deprecated
    public static void deleteMessages(Context context, String[] strArr) {
        LoadExecutor.excuteMethod(new 14(context, strArr), context);
    }

    public static void disableLbs(Context context) {
        LoadExecutor.excuteMethod(new 36(context), context);
    }

    public static void enableLbs(Context context) {
        LoadExecutor.excuteMethod(new 35(context), context);
    }

    @Deprecated
    public static void fetchGroupMessages(Context context, String str, int i, int i2) {
        LoadExecutor.excuteMethod(new 28(context, str, i, i2), context);
    }

    @Deprecated
    public static void fetchMessages(Context context, int i, int i2) {
        LoadExecutor.excuteMethod(new 11(context, i, i2), context);
    }

    public static HashMap<String, Integer> getAppNotiMap(Context context) {
        return BridgePushManager.getAppNotiMap();
    }

    @Deprecated
    public static void getGroupInfo(Context context, String str) {
        LoadExecutor.excuteMethod(new 26(context, str), context);
    }

    @Deprecated
    public static void getGroupList(Context context) {
        LoadExecutor.excuteMethod(new 27(context), context);
    }

    @Deprecated
    public static void getGroupMessageCounts(Context context, String str) {
        LoadExecutor.excuteMethod(new 29(context, str), context);
    }

    public static void getLappBindState(Context context, String str) {
        LoadExecutor.excuteMethod(new 37(context, str), context);
    }

    @Deprecated
    public static void getMessageCounts(Context context) {
        LoadExecutor.excuteMethod(new 13(context), context);
    }

    @Deprecated
    public static void init(Context context, String str) {
        LoadExecutor.excuteMethod(new 12(context, str), context);
    }

    @Deprecated
    public static void init(Context context, String str, String str2) {
        LoadExecutor.excuteMethod(new 1(context, str, str2), context);
    }

    @Deprecated
    public static void initFromAKSK(Context context, String str) {
        LoadExecutor.excuteMethod(new 23(context, str), context);
    }

    public static boolean isConnected(Context context) {
        return context != null && a(context);
    }

    public static boolean isPushEnabled(Context context) {
        String string = context.getSharedPreferences("pst", 0).getString("s_e", "default");
        return "enabled".equals(string) || !"disabled".equals(string);
    }

    public static void listLappTags(Context context, String str) {
        LoadExecutor.excuteMethod(new 20(context, str), context);
    }

    public static void listSDKTags(Context context, String str) {
        LoadExecutor.excuteMethod(new 19(context, str), context);
    }

    public static void listTags(Context context) {
        LoadExecutor.excuteMethod(new 18(context), context);
    }

    public static void resumeWork(Context context) {
        LoadExecutor.excuteMethod(new 3(context), context);
    }

    public static void saveAppNotiMap(Context context, HashMap<String, Integer> hashMap) {
        LoadExecutor.excuteMethod(new 38(hashMap), context);
    }

    public static void sdkStartWork(Context context, String str, int i) {
        LoadExecutor.excuteMethod(new 41(context, str, i), context);
    }

    public static void sdkUnbind(Context context, String str) {
        LoadExecutor.excuteMethod(new 42(context, str), context);
    }

    @Deprecated
    public static void sendMsgToUser(Context context, String str, String str2, String str3, String str4) {
        LoadExecutor.excuteMethod(new 15(context, str, str2, str3, str4), context);
    }

    @Deprecated
    public static void setAccessToken(Context context, String str) {
        LoadExecutor.excuteMethod(new 6(context, str), context);
    }

    @Deprecated
    public static void setApiKey(Context context, String str) {
        LoadExecutor.excuteMethod(new 7(context, str), context);
    }

    @Deprecated
    public static void setBduss(Context context, String str) {
        LoadExecutor.excuteMethod(new 8(context, str), context);
    }

    public static void setDefaultNotificationBuilder(Context context, PushNotificationBuilder pushNotificationBuilder) {
        LoadExecutor.excuteMethod(new 30(context, pushNotificationBuilder), context);
    }

    public static void setLappTags(Context context, String str, List<String> list) {
        LoadExecutor.excuteMethod(new 44(context, str, list), context);
    }

    public static void setMediaNotificationBuilder(Context context, PushNotificationBuilder pushNotificationBuilder) {
        LoadExecutor.excuteMethod(new 32(context, pushNotificationBuilder), context);
    }

    public static void setNoDisturbMode(Context context, int i, int i2, int i3, int i4) {
        LoadExecutor.excuteMethod(new 39(context, i, i2, i3, i4), context);
    }

    public static void setNotificationBuilder(Context context, int i, PushNotificationBuilder pushNotificationBuilder) {
        LoadExecutor.excuteMethod(new 31(context, i, pushNotificationBuilder), context);
    }

    public static void setSDKTags(Context context, String str, List<String> list) {
        LoadExecutor.excuteMethod(new 43(context, str, list), context);
    }

    public static void setTags(Context context, List<String> list) {
        LoadExecutor.excuteMethod(new 17(context, list), context);
    }

    public static void startWork(Context context, int i, String str) {
        LoadExecutor.excuteMethod(new 40(context, i, str), context);
    }

    public static void startWork(Context context, String str, String str2) {
        LoadExecutor.excuteMethod(new 34(context, str, str2), context);
    }

    public static void stopWork(Context context) {
        LoadExecutor.excuteMethod(new 2(context), context);
    }

    public static void tryConnect(Context context) {
        LoadExecutor.excuteMethod(new 33(context), context);
    }

    @Deprecated
    public static void unbind(Context context) {
        LoadExecutor.excuteMethod(new 10(context), context);
    }

    @Deprecated
    public static void unbindGroup(Context context, String str) {
        LoadExecutor.excuteMethod(new 25(context, str), context);
    }
}
