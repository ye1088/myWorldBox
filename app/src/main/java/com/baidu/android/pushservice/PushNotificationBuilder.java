package com.baidu.android.pushservice;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import com.baidu.android.pushservice.apiproxy.BridgePushNotificationBuilder;

public abstract class PushNotificationBuilder {
    private boolean a = false;
    protected BridgePushNotificationBuilder mInstance = null;

    public PushNotificationBuilder(final Context context) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

            public void run() {
                this.b.a = !LoadExecutor.loadPush(context);
            }
        }.start();
    }

    public PushNotificationBuilder(Context context, BridgePushNotificationBuilder bridgePushNotificationBuilder) {
        this.mInstance = bridgePushNotificationBuilder;
    }

    private void a(int i) {
        try {
            Thread.sleep((long) i);
        } catch (Exception e) {
        }
    }

    public abstract Notification construct(Context context);

    public BridgePushNotificationBuilder getInner() {
        return this.mInstance;
    }

    public void setNotificationDefaults(final int i) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

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

    public void setNotificationFlags(final int i) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

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

    public void setNotificationSound(final Uri uri) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

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

    public void setNotificationText(final String str) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

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

    public void setNotificationTitle(final String str) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

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

    public void setNotificationVibrate(final long[] jArr) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

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

    public void setStatusbarIcon(final int i) {
        new Thread(this) {
            final /* synthetic */ PushNotificationBuilder b;

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
