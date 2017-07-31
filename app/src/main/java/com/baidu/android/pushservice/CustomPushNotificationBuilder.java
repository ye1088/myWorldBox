package com.baidu.android.pushservice;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import com.baidu.android.pushservice.apiproxy.BridgeCustomPushNotificationBuilder;

public class CustomPushNotificationBuilder extends PushNotificationBuilder {
    private boolean a = false;

    public CustomPushNotificationBuilder(Context context, int i, int i2, int i3, int i4) {
        super(context);
        if (LoadExecutor.isPushLibLoaded(context)) {
            this.mInstance = new BridgeCustomPushNotificationBuilder(i, i2, i3, i4);
            return;
        }
        final Context context2 = context;
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        new Thread(this) {
            final /* synthetic */ CustomPushNotificationBuilder f;

            public void run() {
                if (LoadExecutor.loadPush(context2)) {
                    this.f.mInstance = new BridgeCustomPushNotificationBuilder(i5, i6, i7, i8);
                    return;
                }
                this.f.a = true;
            }
        }.start();
    }

    public CustomPushNotificationBuilder(Context context, BridgeCustomPushNotificationBuilder bridgeCustomPushNotificationBuilder) {
        super(context);
        this.mInstance = bridgeCustomPushNotificationBuilder;
    }

    private void a(int i) {
        try {
            Thread.sleep((long) i);
        } catch (Exception e) {
        }
    }

    public Notification construct(Context context) {
        return !LoadExecutor.loadPush(context) ? null : this.mInstance.construct(context);
    }

    public BridgeCustomPushNotificationBuilder getInner() {
        while (this.mInstance == null && !this.a) {
            a(50);
        }
        return this.a ? null : (BridgeCustomPushNotificationBuilder) this.mInstance;
    }

    public void setLayoutDrawable(final int i) {
        if (this.mInstance != null) {
            ((BridgeCustomPushNotificationBuilder) this.mInstance).setLayoutDrawable(i);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        ((BridgeCustomPushNotificationBuilder) this.b.mInstance).setLayoutDrawable(i);
                    }
                }
            }.start();
        }
    }

    public void setNotificationDefaults(final int i) {
        if (this.mInstance != null) {
            this.mInstance.setNotificationDefaults(i);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        this.b.mInstance.setNotificationDefaults(i);
                    }
                }
            }.start();
        }
    }

    public void setNotificationFlags(final int i) {
        if (this.mInstance != null) {
            this.mInstance.setNotificationFlags(i);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        this.b.mInstance.setNotificationFlags(i);
                    }
                }
            }.start();
        }
    }

    public void setNotificationSound(final Uri uri) {
        if (this.mInstance != null) {
            this.mInstance.setNotificationSound(uri);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        this.b.mInstance.setNotificationSound(uri);
                    }
                }
            }.start();
        }
    }

    public void setNotificationText(final String str) {
        if (this.mInstance != null) {
            this.mInstance.setNotificationText(str);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        this.b.mInstance.setNotificationText(str);
                    }
                }
            }.start();
        }
    }

    public void setNotificationTitle(final String str) {
        if (this.mInstance != null) {
            this.mInstance.setNotificationTitle(str);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        this.b.mInstance.setNotificationTitle(str);
                    }
                }
            }.start();
        }
    }

    public void setNotificationVibrate(final long[] jArr) {
        if (this.mInstance != null) {
            this.mInstance.setNotificationVibrate(jArr);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        this.b.mInstance.setNotificationVibrate(jArr);
                    }
                }
            }.start();
        }
    }

    public void setStatusbarIcon(final int i) {
        if (this.mInstance != null) {
            this.mInstance.setStatusbarIcon(i);
        } else {
            new Thread(this) {
                final /* synthetic */ CustomPushNotificationBuilder b;

                public void run() {
                    while (this.b.mInstance == null && !this.b.a) {
                        this.b.a(50);
                    }
                    if (!this.b.a) {
                        this.b.mInstance.setStatusbarIcon(i);
                    }
                }
            }.start();
        }
    }
}
