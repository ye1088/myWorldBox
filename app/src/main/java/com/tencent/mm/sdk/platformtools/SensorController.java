package com.tencent.mm.sdk.platformtools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;

public class SensorController extends BroadcastReceiver implements SensorEventListener {
    private static float aX = 4.2949673E9f;
    private static float ba = 0.5f;
    private SensorManager aY;
    private float aZ;
    private SensorEventCallBack bb;
    private Sensor bc;
    private final boolean bd;
    private boolean be = false;
    private boolean bf = false;

    public interface SensorEventCallBack {
        void onSensorEvent(boolean z);
    }

    public SensorController(Context context) {
        this.aY = (SensorManager) context.getSystemService("sensor");
        this.bc = this.aY.getDefaultSensor(8);
        this.bd = this.bc != null;
        this.aZ = ba + 1.0f;
    }

    public boolean isSensorEnable() {
        return this.bd;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
            int intExtra = intent.getIntExtra(DownloadRecord.COLUMN_STATE, 0);
            if (intExtra == 1) {
                this.be = true;
            }
            if (intExtra == 0) {
                this.be = false;
            }
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (!this.be) {
            float f = sensorEvent.values[0];
            switch (sensorEvent.sensor.getType()) {
                case 8:
                    if (f < aX) {
                        aX = f;
                        ba = 0.5f + f;
                    }
                    if (this.aZ < ba || f >= ba) {
                        if (this.aZ <= ba && f > ba && this.bb != null) {
                            Log.v("MicroMsg.SensorController", "sensor event true");
                            this.bb.onSensorEvent(true);
                        }
                    } else if (this.bb != null) {
                        Log.v("MicroMsg.SensorController", "sensor event false");
                        this.bb.onSensorEvent(false);
                    }
                    this.aZ = f;
                    return;
                default:
                    return;
            }
        }
    }

    public void removeSensorCallBack() {
        Log.v("MicroMsg.SensorController", "sensor callback removed");
        this.aY.unregisterListener(this, this.bc);
        this.aY.unregisterListener(this);
        this.bf = false;
        this.bb = null;
    }

    public void setSensorCallBack(SensorEventCallBack sensorEventCallBack) {
        Log.v("MicroMsg.SensorController", "sensor callback set");
        if (!this.bf) {
            this.aY.registerListener(this, this.bc, 2);
            this.bf = true;
        }
        this.bb = sensorEventCallBack;
    }
}
