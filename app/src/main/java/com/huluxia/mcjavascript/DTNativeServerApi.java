package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManager;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativeServerApi extends ScriptableObject {
    private static final boolean DEBUG = false;

    @JSStaticFunction
    public static String getAddress() {
        return DTSDKManager.serverAddress;
    }

    @JSStaticFunction
    public static String[] getAllPlayerNames() {
        return o.Aw();
    }

    @JSStaticFunction
    public static Object[] getAllPlayers() {
        return o.Ax();
    }

    @JSStaticFunction
    public static int getPort() {
        return DTSDKManager.serverPort;
    }

    @JSStaticFunction
    public static void joinServer(String paramString, int paramInt) {
        o.E(paramString, paramInt);
    }

    @JSStaticFunction
    public static void sendChat(String paramString) {
        o.dh(paramString);
    }

    public String getClassName() {
        return "Server";
    }
}
