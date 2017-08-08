package com.MCWorld.mcjavascript;

import org.mozilla.javascript.ScriptableObject;

public class DTNativePointer extends ScriptableObject {
    public long value;

    public DTNativePointer(long paramLong) {
        this.value = paramLong;
    }

    public String getClassName() {
        return "NativePointer";
    }
}
