package com.baidu.android.pushservice.apiproxy;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import com.baidu.android.pushservice.internal.PushNotificationBuilder;

public abstract class BridgePushNotificationBuilder {
    protected PushNotificationBuilder mInstance;

    public BridgePushNotificationBuilder() {
        this.mInstance = new PushNotificationBuilder(this) {
            final /* synthetic */ BridgePushNotificationBuilder a;

            {
                this.a = r1;
            }

            public Notification construct(Context context) {
                return this.a.construct(context);
            }
        };
    }

    public BridgePushNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        this.mInstance = pushNotificationBuilder;
    }

    public abstract Notification construct(Context context);

    public PushNotificationBuilder getInner() {
        return this.mInstance;
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
