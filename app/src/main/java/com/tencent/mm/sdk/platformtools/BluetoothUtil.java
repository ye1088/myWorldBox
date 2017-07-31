package com.tencent.mm.sdk.platformtools;

import android.media.AudioManager;
import android.os.Build.VERSION;

public class BluetoothUtil {
    public static boolean startBluetooth(AudioManager audioManager) {
        if (Integer.valueOf(VERSION.SDK).intValue() < 8) {
            return false;
        }
        if (!audioManager.isBluetoothScoAvailableOffCall() || PhoneStatusWatcher.isCalling()) {
            return false;
        }
        audioManager.startBluetoothSco();
        audioManager.setBluetoothScoOn(true);
        return true;
    }

    public static void stopBluetooth(AudioManager audioManager) {
        if (Integer.valueOf(VERSION.SDK).intValue() >= 8 && !PhoneStatusWatcher.isCalling()) {
            audioManager.stopBluetoothSco();
        }
    }
}
