package com.mojang.minecraftpe.input;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.input.InputManager;
import android.hardware.input.InputManager.InputDeviceListener;

@TargetApi(16)
public class JellyBeanDeviceManager extends a implements InputDeviceListener {
    private final InputManager bIE;

    native void onInputDeviceAddedNative(int i);

    native void onInputDeviceChangedNative(int i);

    native void onInputDeviceRemovedNative(int i);

    JellyBeanDeviceManager(Context paramContext) {
        this.bIE = (InputManager) paramContext.getSystemService("input");
    }

    public void onInputDeviceAdded(int paramInt) {
        onInputDeviceAddedNative(paramInt);
    }

    public void onInputDeviceChanged(int paramInt) {
        onInputDeviceChangedNative(paramInt);
    }

    public void onInputDeviceRemoved(int paramInt) {
        onInputDeviceRemovedNative(paramInt);
    }

    public void Rw() {
        this.bIE.getInputDeviceIds();
        this.bIE.registerInputDeviceListener(this, null);
    }

    public void unregister() {
        this.bIE.unregisterInputDeviceListener(this);
    }
}
