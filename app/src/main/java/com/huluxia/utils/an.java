package com.huluxia.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.util.Log;
import com.huluxia.t;

/* compiled from: UtilsProximitySensor */
public class an {
    private SensorManager bmG = null;
    private Sensor bmH = null;
    private SensorEventListener bmI = null;
    private AudioManager bmJ;
    private boolean bmK;
    private Context mContext;
    private int mMode;
    private int mRate = 3;

    public an(Context mContext) {
        this.mContext = mContext;
        this.bmJ = (AudioManager) mContext.getSystemService("audio");
        this.mMode = this.bmJ.getMode();
        this.bmG = (SensorManager) mContext.getSystemService("sensor");
        this.bmH = this.bmG.getDefaultSensor(8);
        this.bmI = new SensorEventListener(this) {
            final /* synthetic */ an bmL;

            {
                this.bmL = this$0;
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == 8) {
                    if (event.values[0] < 3.0f) {
                        if (this.bmL.mMode == 0) {
                            this.bmL.bmJ.setSpeakerphoneOn(false);
                            this.bmL.bmJ.setBluetoothScoOn(true);
                            this.bmL.bmJ.setMode(2);
                            this.bmL.mMode = 2;
                            t.download_toast(this.bmL.mContext, "已经切换为听筒播放模式");
                        }
                    } else if (this.bmL.mMode == 2) {
                        this.bmL.bmJ.setSpeakerphoneOn(true);
                        this.bmL.bmJ.setBluetoothScoOn(false);
                        this.bmL.bmJ.setMode(0);
                        this.bmL.mMode = 0;
                        t.download_toast(this.bmL.mContext, "已经切换为扬声器播放模式");
                    }
                    Log.i("event0", String.valueOf(event.values[0]));
                }
            }
        };
    }

    public void start() {
        if (!this.bmK) {
            this.bmG.registerListener(this.bmI, this.bmH, this.mRate);
            this.bmK = true;
        }
    }

    public void stop() {
        if (this.bmK) {
            if (this.mMode == 2) {
                this.bmJ.setSpeakerphoneOn(true);
                this.bmJ.setBluetoothScoOn(false);
                this.bmJ.setMode(0);
                this.mMode = 0;
            }
            this.bmG.unregisterListener(this.bmI);
            this.bmK = false;
        }
    }
}
