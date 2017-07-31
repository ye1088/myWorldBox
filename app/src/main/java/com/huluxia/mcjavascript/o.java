package com.huluxia.mcjavascript;

import com.huluxia.mcgame.g;
import com.huluxia.mcsdk.DTSDKManager;
import com.huluxia.mcsdk.DTSDKManager.a;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.dtlib.h;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* compiled from: DTNativeServerApiEx */
public class o {
    public static String[] Aw() {
        try {
            String[] strArr;
            int i;
            if (h.CW().CX() == 0) {
                strArr = new String[g.aeb.size()];
                for (i = 0; strArr.length > i; i++) {
                    strArr[i] = DTSDKManagerEx.iy(((Integer) g.aeb.get(i)).intValue());
                }
                return strArr;
            } else if (h.CW().CX() != 1 && h.CW().CX() != 2 && h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
                return null;
            } else {
                strArr = new String[g.aec.size()];
                for (i = 0; strArr.length > i; i++) {
                    strArr[i] = DTSDKManagerEx.at(((Long) g.aec.get(i)).longValue());
                }
                return strArr;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Object[] Ax() {
        try {
            Object[] objArr;
            int i;
            if (h.CW().CX() == 0) {
                objArr = new Object[g.aeb.size()];
                for (i = 0; objArr.length > i; i++) {
                    objArr[i] = Integer.valueOf(((Integer) g.aeb.get(i)).intValue());
                }
                return objArr;
            } else if (h.CW().CX() != 1 && h.CW().CX() != 2 && h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
                return null;
            } else {
                objArr = new Object[g.aec.size()];
                for (i = 0; objArr.length > i; i++) {
                    objArr[i] = Long.valueOf(((Long) g.aec.get(i)).longValue());
                }
                return objArr;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void E(String paramString, int paramInt) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManager.requestLeaveGame = true;
                DTSDKManager.requestJoinServer = new a();
                DTSDKManager.requestJoinServer.serverAddress = InetAddress.getByName(paramString).getHostAddress();
                DTSDKManager.requestJoinServer.serverPort = paramInt;
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                throw new RuntimeException("FIXME 0.11");
            }
        } catch (UnknownHostException localUnknownHostException) {
            throw new RuntimeException(localUnknownHostException);
        } catch (Exception e) {
        }
    }

    public static void dh(String paramString) {
        try {
            if (h.CW().CX() == 0) {
                if (DTSDKManager.isRemote) {
                    DTSDKManagerEx.dC(paramString);
                }
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                throw new RuntimeException("FIXME 0.11");
            }
        } catch (Exception e) {
        }
    }
}
