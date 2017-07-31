package com.baidu.android.pushservice.apiproxy;

import android.app.Activity;
import android.content.Context;
import com.baidu.android.pushservice.internal.PushManager;
import java.util.HashMap;
import java.util.List;

public class BridgePushManager {
    public static void activityStarted(Activity activity) {
        PushManager.activityStarted(activity);
    }

    public static void activityStoped(Activity activity) {
        PushManager.activityStoped(activity);
    }

    public static void bind(Context context, int i) {
        PushManager.bind(context, i);
    }

    public static void bindGroup(Context context, String str) {
        PushManager.bindGroup(context, str);
    }

    public static void delLappTags(Context context, String str, List<String> list) {
        PushManager.delLappTags(context, str, list);
    }

    public static void delSDKTags(Context context, String str, List<String> list) {
        PushManager.delSDKTags(context, str, list);
    }

    public static void delTags(Context context, List<String> list) {
        PushManager.delTags(context, list);
    }

    public static void deleteMessages(Context context, String[] strArr) {
        PushManager.deleteMessages(context, strArr);
    }

    public static void disableLbs(Context context) {
        PushManager.disableLbs(context);
    }

    public static void enableLbs(Context context) {
        PushManager.enableLbs(context);
    }

    public static void fetchGroupMessages(Context context, String str, int i, int i2) {
        PushManager.fetchGroupMessages(context, str, i, i2);
    }

    public static void fetchMessages(Context context, int i, int i2) {
        PushManager.fetchMessages(context, i, i2);
    }

    public static HashMap<String, Integer> getAppNotiMap() {
        return PushManager.getAppNotiMap();
    }

    public static void getGroupInfo(Context context, String str) {
        PushManager.getGroupInfo(context, str);
    }

    public static void getGroupList(Context context) {
        PushManager.getGroupList(context);
    }

    public static void getGroupMessageCounts(Context context, String str) {
        PushManager.getGroupMessageCounts(context, str);
    }

    public static void getLappBindState(Context context, String str) {
        PushManager.getLappBindState(context, str);
    }

    public static void getMessageCounts(Context context) {
        PushManager.getMessageCounts(context);
    }

    public static void init(Context context, String str) {
        PushManager.init(context, str);
    }

    public static void init(Context context, String str, String str2) {
        PushManager.init(context, str, str2);
    }

    public static void initFromAKSK(Context context, String str) {
        PushManager.initFromAKSK(context, str);
    }

    public static boolean isConnected(Context context) {
        return PushManager.isConnected(context);
    }

    public static boolean isPushEnabled(Context context) {
        return PushManager.isPushEnabled(context);
    }

    public static void listLappTags(Context context, String str) {
        PushManager.listLappTags(context, str);
    }

    public static void listSDKTags(Context context, String str) {
        PushManager.listSDKTags(context, str);
    }

    public static void listTags(Context context) {
        PushManager.listTags(context);
    }

    public static void resumeWork(Context context) {
        PushManager.resumeWork(context);
    }

    public static void saveAppNotiMap(HashMap<String, Integer> hashMap) {
        PushManager.saveAppNotiMap(hashMap);
    }

    public static void sdkStartWork(Context context, String str, int i) {
        PushManager.sdkStartWork(context, str, i);
    }

    public static void sdkUnbind(Context context, String str) {
        PushManager.sdkUnbind(context, str);
    }

    public static void sendMsgToUser(Context context, String str, String str2, String str3, String str4) {
        PushManager.sendMsgToUser(context, str, str2, str3, str4);
    }

    public static void setAccessToken(Context context, String str) {
        PushManager.setAccessToken(context, str);
    }

    public static void setApiKey(Context context, String str) {
        PushManager.setApiKey(context, str);
    }

    public static void setBduss(Context context, String str) {
        PushManager.setBduss(context, str);
    }

    public static void setDefaultNotificationBuilder(Context context, BridgePushNotificationBuilder bridgePushNotificationBuilder) {
        PushManager.setDefaultNotificationBuilder(context, bridgePushNotificationBuilder.getInner());
    }

    public static void setLappTags(Context context, String str, List<String> list) {
        PushManager.setLappTags(context, str, list);
    }

    public static void setMediaNotificationBuilder(Context context, BridgePushNotificationBuilder bridgePushNotificationBuilder) {
        PushManager.setMediaNotificationBuilder(context, bridgePushNotificationBuilder.getInner());
    }

    public static void setNoDisturbMode(Context context, int i, int i2, int i3, int i4) {
        PushManager.setNoDisturbMode(context, i, i2, i3, i4);
    }

    public static void setNotificationBuilder(Context context, int i, BridgePushNotificationBuilder bridgePushNotificationBuilder) {
        try {
            PushManager.setNotificationBuilder(context, i, bridgePushNotificationBuilder.getInner());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSDKTags(Context context, String str, List<String> list) {
        PushManager.setSDKTags(context, str, list);
    }

    public static void setTags(Context context, List<String> list) {
        PushManager.setTags(context, list);
    }

    public static void startWork(Context context, int i, String str) {
        PushManager.startWork(context, i, str);
    }

    public static void startWork(Context context, String str, String str2) {
        PushManager.startWork(context, str, str2);
    }

    public static void stopWork(Context context) {
        PushManager.stopWork(context);
    }

    public static void tryConnect(Context context) {
        PushManager.tryConnect(context);
    }

    public static void unbind(Context context) {
        PushManager.unbind(context);
    }

    public static void unbindGroup(Context context, String str) {
        PushManager.unbindGroup(context, str);
    }
}
