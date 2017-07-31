package com.baidu.android.pushservice.apiproxy;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import com.baidu.android.pushservice.internal.CustomPushNotificationBuilder;

public class BridgeCustomPushNotificationBuilder extends BridgePushNotificationBuilder {
    public BridgeCustomPushNotificationBuilder(int i, int i2, int i3, int i4) {
        this.mInstance = new CustomPushNotificationBuilder(i, i2, i3, i4);
    }

    public BridgeCustomPushNotificationBuilder(CustomPushNotificationBuilder customPushNotificationBuilder) {
        this.mInstance = customPushNotificationBuilder;
    }

    public Notification construct(Context context) {
        return this.mInstance.construct(context);
    }

    public CustomPushNotificationBuilder getInner() {
        return (CustomPushNotificationBuilder) this.mInstance;
    }

    public void setLayoutDrawable(int i) {
        ((CustomPushNotificationBuilder) this.mInstance).setLayoutDrawable(i);
    }

    public void setNotificationDefaults(int i) {
        this.mInstance.setNotificationDefaults(i);
    }

    public void setNotificationFlags(int i) {
        this.mInstance.setNotificationFlags(i);
    }

    public void setNotificationSound(Uri uri) {
        this.mInstance.setNotificationSound(uri);
    }

    public void setNotificationText(String str) {
        this.mInstance.setNotificationText(str);
    }

    public void setNotificationTitle(String str) {
        this.mInstance.setNotificationTitle(str);
    }

    public void setNotificationVibrate(long[] jArr) {
        this.mInstance.setNotificationVibrate(jArr);
    }

    public void setStatusbarIcon(int i) {
        this.mInstance.setStatusbarIcon(i);
    }
}
