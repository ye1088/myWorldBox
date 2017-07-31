package com.huawei.android.pushagent.c;

public class g {
    private static final String[] a = new String[]{"com.huawei.android.push.intent.DEREGISTER", "android.intent.action.TIME_SET", "android.intent.action.TIMEZONE_CHANGED", "com.huawei.android.push.intent.HEARTBEAT_RSP_TIMEOUT", "com.huawei.intent.action.PUSH_OFF", "com.huawei.action.CONNECT_PUSHSRV", "com.huawei.action.CONNECT_PUSHSRV_POLLINGSRV", "com.huawei.android.push.intent.GET_PUSH_STATE", "android.ctrlsocket.all.allowed", "android.scroff.ctrlsocket.status"};

    public static String[] a() {
        Object obj = new String[a.length];
        System.arraycopy(a, 0, obj, 0, a.length);
        return obj;
    }
}
