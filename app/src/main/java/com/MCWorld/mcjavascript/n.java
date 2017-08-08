package com.MCWorld.mcjavascript;

import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.a;
import com.MCWorld.mcsdk.dtlib.h;

/* compiled from: DTNativePlayerApiEx */
public class n {
    public static Object Au() {
        try {
            if (h.CW().CX() == 0) {
                return Integer.valueOf(DTSDKManagerEx.Cu());
            }
            if (h.CW().CX() == 1) {
                return Long.valueOf(DTSDKManagerEx.Cv());
            }
            if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return Long.valueOf(DTSDKManagerEx.Cv());
            }
            return Integer.valueOf(0);
        } catch (Exception e) {
        }
    }

    public static Object Av() {
        try {
            if (h.CW().CX() == 0) {
                return Integer.valueOf(DTSDKManagerEx.Cp());
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return Long.valueOf(DTSDKManagerEx.Cq());
            }
            return Integer.valueOf(0);
        } catch (Exception e) {
        }
    }

    public static String al(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.iy(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.at(a.aq(paramObject));
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean am(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                if (DTSDKManagerEx.in(a.ar(paramObject)) == 63) {
                    return true;
                }
                return false;
            } else if (h.CW().CX() != 1 && h.CW().CX() != 2 && h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
                return false;
            } else {
                if (DTSDKManagerEx.ai(a.aq(paramObject)) != 63) {
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
