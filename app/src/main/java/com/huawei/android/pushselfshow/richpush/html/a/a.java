package com.huawei.android.pushselfshow.richpush.html.a;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;

import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.utils.b.b;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import java.io.File;
import java.util.List;
import org.apache.tools.ant.util.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements SensorEventListener, h {
    public SoundPool a = null;
    public boolean b = false;
    private SensorManager c;
    private Sensor d;
    private Activity e;
    private String f;
    private NativeToJsMessageQueue g;
    private float h = 0.0f;
    private float i = 0.0f;
    private float j = 0.0f;
    private long k = 0;
    private int l;
    private int m = 0;
    private int n;
    private String o;
    private int p;
    private int q;
    private int r;
    private long s = 0;
    private Handler t = null;
    private Runnable u = new b(this);

    public a(Activity activity) {
        a(0);
        this.c = (SensorManager) activity.getSystemService("sensor");
        this.e = activity;
    }

    private void a(int i) {
        this.l = i;
    }

    private void a(com.huawei.android.pushselfshow.richpush.html.api.d.a aVar) {
        try {
            e.e("PushSelfShowLog", " accelListener fail,the status is " + d.c()[aVar.ordinal()]);
            this.g.a(this.f, aVar, DownloadRecord.COLUMN_ERROR, null);
        } catch (RuntimeException e) {
            e.e("PushSelfShowLog", " accelListener fail error");
        } catch (Exception e2) {
            e.e("PushSelfShowLog", " accelListener fail error");
        }
    }

    private void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has("minAccuracy")) {
                    this.n = jSONObject.getInt("minAccuracy");
                }
            } catch (Exception e) {
                e.e("PushSelfShowLog", "read OPTIONS_MIN_ACCURACY options error");
            }
            try {
                if (jSONObject.has("minAccelX")) {
                    this.p = jSONObject.getInt("minAccelX");
                }
                if (jSONObject.has("minAccelY")) {
                    this.q = jSONObject.getInt("minAccelY");
                }
                if (jSONObject.has("minAccelZ")) {
                    this.r = jSONObject.getInt("minAccelZ");
                }
            } catch (JSONException e2) {
                e.e("PushSelfShowLog", "read OPTIONS_MIN_ACCEL_X_Y_Z options error");
            }
        }
        e.b("PushSelfShowLog", "start with optins and the minAccuracy is %s  , minAccelX is %s ,minAccelY is %s ,minAccelZ is %s", new Object[]{Integer.valueOf(this.n), Integer.valueOf(this.p), Integer.valueOf(this.q), Integer.valueOf(this.r)});
        if (this.l != 2 && this.l != 1) {
            a(1);
            this.b = true;
            List sensorList = this.c.getSensorList(1);
            if (sensorList == null || sensorList.size() <= 0) {
                a(3);
                a(com.huawei.android.pushselfshow.richpush.html.api.d.a.ACCL_NO_SENSORS);
                return;
            }
            this.d = (Sensor) sensorList.get(0);
            this.c.registerListener(this, this.d, 2);
            a(1);
            e.e("PushSelfShowLog", "this.setStatus(AccelListener.STARTING);");
            e();
            this.t = new Handler(Looper.getMainLooper());
            this.t.postDelayed(this.u, FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
        }
    }

    private void b(JSONObject jSONObject) {
        if (System.currentTimeMillis() - this.s >= FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY) {
            this.s = System.currentTimeMillis();
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("soundType")) {
                        this.o = jSONObject.getString("soundType");
                    }
                } catch (Exception e) {
                    e.e("PushSelfShowLog", "read OPTIONS_PLAY_MUSIC ,SRC options error");
                }
            }
            e.b("PushSelfShowLog", "playSound with optins and the  soundType is %s ", new Object[]{this.o});
            try {
                if ("TYPE_SHAKE".equals(this.o)) {
                    String c = b.c(this.e);
                    if (c == null) {
                        a(com.huawei.android.pushselfshow.richpush.html.api.d.a.ACCL_NO_SDCARD);
                        return;
                    }
                    String str = c + File.separator + "pushresources/shake.mp3".substring(0, "pushresources/shake.mp3".indexOf("/"));
                    if (!new File(str).exists() && new File(str).mkdirs()) {
                        e.e("PushSelfShowLog", "resource mkdir true");
                    }
                    c = c + File.separator + "pushresources/shake.mp3";
                    com.huawei.android.pushselfshow.utils.a.b(this.e, "pushresources/shake.mp3", c);
                    if (new File(c).exists()) {
                        this.a = new SoundPool(1, 3, 0);
                        this.a.setOnLoadCompleteListener(new c(this));
                        this.a.load(c, 1);
                        return;
                    }
                    a(com.huawei.android.pushselfshow.richpush.html.api.d.a.IO_EXCEPTION);
                }
            } catch (Throwable e2) {
                e.c("PushSelfShowLog", " error", e2);
            }
        }
    }

    private void e() {
        if (this.t != null) {
            this.t.removeCallbacks(this.u);
        }
    }

    private void f() {
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
        this.b = false;
        e();
        e.e("PushSelfShowLog", " stop this.status" + this.l);
        if (this.c != null) {
            this.c.unregisterListener(this);
        }
        a(0);
        this.m = 0;
    }

    private void g() {
        if (this.l == 1) {
            a(3);
            a(com.huawei.android.pushselfshow.richpush.html.api.d.a.ACCL_CAN_NOT_START);
        }
    }

    private void h() {
        e.e("PushSelfShowLog", " accelListener success");
        this.g.a(this.f, com.huawei.android.pushselfshow.richpush.html.api.d.a.OK, "success", i());
    }

    private JSONObject i() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("x", (double) this.h);
            jSONObject.put("y", (double) this.i);
            jSONObject.put("z", (double) this.j);
            jSONObject.put("timestamp", this.k);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a() {
        e.e("PushSelfShowLog", "call acclListener init()");
        this.n = 2;
        this.o = "TYPE_SHAKE";
        this.p = 10;
        this.q = 10;
        this.r = 10;
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            e.a("PushSelfShowLog", "jsMessageQueue is null while run into App exec");
            return;
        }
        this.g = nativeToJsMessageQueue;
        if ("start".equals(str)) {
            e.e("PushSelfShowLog", "call acclListener exec and the method is start");
            d();
            if (str2 != null) {
                this.f = str2;
                if (this.l != 2) {
                    a(jSONObject);
                    return;
                }
                return;
            }
            e.a("PushSelfShowLog", "Audio exec callback is null ");
        } else if ("stop".equals(str)) {
            f();
        } else if ("playSound".equals(str)) {
            b(jSONObject);
        } else {
            a(com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION);
        }
    }

    public void b() {
        if (this.l != 0 && this.l != 3) {
            f();
            a(null);
        }
    }

    public void c() {
        d();
    }

    public void d() {
        e.e("PushSelfShowLog", "accel reset");
        f();
        a();
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
        if (sensor.getType() == 1 && this.l != 0) {
            this.m = i;
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1 && this.b && this.l != 0) {
            a(2);
            if (this.m >= this.n) {
                this.h = sensorEvent.values[0];
                this.i = sensorEvent.values[1];
                this.j = sensorEvent.values[2];
                this.k = System.currentTimeMillis();
                if (Math.abs(this.h) > ((float) this.p) || Math.abs(this.i) > ((float) this.q) || Math.abs(this.j) > ((float) this.r)) {
                    e.b("PushSelfShowLog", "onSensorChanged and x = %s , y=%s , z=%s ", new Object[]{Float.valueOf(this.h), Float.valueOf(this.i), Float.valueOf(this.j)});
                    h();
                }
            }
        }
    }
}
