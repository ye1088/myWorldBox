package com.huawei.android.pushagent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.baidu.android.pushservice.PushConstants;
import com.huawei.android.pushagent.c.a;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.f;
import com.huawei.android.pushagent.c.a.h;
import com.huluxia.ui.profile.FriendListActivity;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public abstract class PushReceiver extends BroadcastReceiver {

    public static class BOUND_KEY {
        public static final String PLUGINREPORTRESULT = "isReportSuccess";
        public static final String PLUGINREPORTTYPE = "reportType";
        public static final String deviceTokenKey = "deviceToken";
        public static final String pushMsgKey = "pushMsg";
        public static final String pushNotifyId = "pushNotifyId";
        public static final String pushStateKey = "pushState";
        public static final String receiveTypeKey = "receiveType";
    }

    public enum Event {
        NOTIFICATION_OPENED,
        PLUGINRSP,
        NOTIFICATION_CLICK_BTN
    }

    private class EventThread extends Thread {
        final /* synthetic */ PushReceiver a;
        private Context b;
        private Bundle c;

        public EventThread(PushReceiver pushReceiver, Context context, Bundle bundle) {
            this.a = pushReceiver;
            super("EventRunable");
            this.b = context;
            this.c = bundle;
        }

        public void run() {
            try {
                if (this.c != null) {
                    int i = this.c.getInt(BOUND_KEY.receiveTypeKey);
                    if (i < 0 || i >= ReceiveType.values().length) {
                        e.d("PushLogAC2705", "invalid receiverType:" + i);
                        return;
                    }
                    switch (ReceiveType.values()[i]) {
                        case ReceiveType_Token:
                            this.a.onToken(this.b, this.c.getString(BOUND_KEY.deviceTokenKey), this.c);
                            return;
                        case ReceiveType_Msg:
                            this.a.onPushMsg(this.b, this.c.getByteArray(BOUND_KEY.pushMsgKey), this.c);
                            return;
                        case ReceiveType_PushState:
                            this.a.onPushState(this.b, this.c.getBoolean(BOUND_KEY.pushStateKey));
                            return;
                        case ReceiveType_NotifyClick:
                            this.a.onEvent(this.b, Event.NOTIFICATION_OPENED, this.c);
                            return;
                        case ReceiveType_ClickBtn:
                            this.a.onEvent(this.b, Event.NOTIFICATION_CLICK_BTN, this.c);
                            return;
                        case ReceiveType_PluginRsp:
                            this.a.onEvent(this.b, Event.PLUGINRSP, this.c);
                            return;
                        default:
                            return;
                    }
                }
            } catch (Throwable e) {
                e.c("PushLogAC2705", "call EventThread(ReceiveType cause:" + e.toString(), e);
            }
        }
    }

    static class HandlePushTokenThread extends Thread {
        Context a;
        String b;

        public HandlePushTokenThread(Context context, String str) {
            this.a = context;
            this.b = str;
        }

        public void run() {
            h hVar = new h(this.a, "push_client_self_info");
            hVar.a("hasRequestToken", false);
            hVar.f("token_info");
            f.a(this.a, "push_client_self_info", "token_info", this.b);
        }
    }

    enum ReceiveType {
        ReceiveType_Init,
        ReceiveType_Token,
        ReceiveType_Msg,
        ReceiveType_PushState,
        ReceiveType_NotifyClick,
        ReceiveType_PluginRsp,
        ReceiveType_ClickBtn
    }

    private static int a() {
        int intValue;
        try {
            Class cls = Class.forName("android.os.UserHandle");
            intValue = ((Integer) cls.getDeclaredMethod("myUserId", new Class[0]).invoke(cls, new Object[0])).intValue();
            try {
                e.a("PushLogAC2705", "getUserId:" + intValue);
            } catch (ClassNotFoundException e) {
                e.a("PushLogAC2705", " getUserId wrong");
                return intValue;
            } catch (NoSuchMethodException e2) {
                e.a("PushLogAC2705", " getUserId wrong");
                return intValue;
            } catch (IllegalAccessException e3) {
                e.a("PushLogAC2705", " getUserId wrong");
                return intValue;
            } catch (IllegalArgumentException e4) {
                e.a("PushLogAC2705", " getUserId wrong");
                return intValue;
            } catch (InvocationTargetException e5) {
                e.a("PushLogAC2705", " getUserId wrong");
                return intValue;
            }
        } catch (ClassNotFoundException e6) {
            intValue = -999;
            e.a("PushLogAC2705", " getUserId wrong");
            return intValue;
        } catch (NoSuchMethodException e7) {
            intValue = -999;
            e.a("PushLogAC2705", " getUserId wrong");
            return intValue;
        } catch (IllegalAccessException e8) {
            intValue = -999;
            e.a("PushLogAC2705", " getUserId wrong");
            return intValue;
        } catch (IllegalArgumentException e9) {
            intValue = -999;
            e.a("PushLogAC2705", " getUserId wrong");
            return intValue;
        } catch (InvocationTargetException e10) {
            intValue = -999;
            e.a("PushLogAC2705", " getUserId wrong");
            return intValue;
        }
        return intValue;
    }

    private void b(Context context, Intent intent) throws UnsupportedEncodingException {
        g(context, intent);
        boolean a = new h(context, "push_switch").a("normal_msg_enable");
        e.a("PushLogAC2705", "closePush_Normal:" + a);
        if (a) {
            e.b("PushLogAC2705", "close switch is true, message not dispatch");
            return;
        }
        byte[] byteArrayExtra = intent.getByteArrayExtra("msg_data");
        String str = new String(intent.getByteArrayExtra("device_token"), "UTF-8");
        e.a("PushLogAC2705", "PushReceiver receive a message success");
        Bundle bundle = new Bundle();
        bundle.putString(BOUND_KEY.deviceTokenKey, str);
        bundle.putByteArray(BOUND_KEY.pushMsgKey, byteArrayExtra);
        bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_Msg.ordinal());
        new EventThread(this, context, bundle).start();
    }

    private void c(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("click");
        Bundle bundle = new Bundle();
        bundle.putString(BOUND_KEY.pushMsgKey, stringExtra);
        bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_NotifyClick.ordinal());
        new EventThread(this, context, bundle).start();
    }

    private void d(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("clickBtn");
        int intExtra = intent.getIntExtra("notifyId", 0);
        Bundle bundle = new Bundle();
        bundle.putString(BOUND_KEY.pushMsgKey, stringExtra);
        bundle.putInt(BOUND_KEY.pushNotifyId, intExtra);
        bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_ClickBtn.ordinal());
        new EventThread(this, context, bundle).start();
    }

    private void e(Context context, Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra("push_state", false);
        Bundle bundle = new Bundle();
        bundle.putBoolean(BOUND_KEY.pushStateKey, booleanExtra);
        bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_PushState.ordinal());
        new EventThread(this, context, bundle).start();
    }

    private void f(Context context, Intent intent) {
        int intExtra = intent.getIntExtra(BOUND_KEY.PLUGINREPORTTYPE, -1);
        boolean booleanExtra = intent.getBooleanExtra(BOUND_KEY.PLUGINREPORTRESULT, false);
        Bundle bundleExtra = intent.getBundleExtra("reportExtra");
        Bundle bundle = new Bundle();
        bundle.putBoolean(BOUND_KEY.PLUGINREPORTRESULT, booleanExtra);
        bundle.putInt(BOUND_KEY.PLUGINREPORTTYPE, intExtra);
        bundle.putBundle("reportExtra", bundleExtra);
        bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_PluginRsp.ordinal());
        new EventThread(this, context, bundle).start();
    }

    private void g(Context context, Intent intent) {
        if (context != null && intent != null) {
            Object stringExtra = intent.getStringExtra("msgIdStr");
            if (!TextUtils.isEmpty(stringExtra) && a.o(context)) {
                Intent intent2 = new Intent("com.huawei.android.push.intent.MSG_RESPONSE");
                intent2.putExtra("msgIdStr", stringExtra);
                intent2.setPackage("android");
                intent2.setFlags(32);
                e.a("PushLogAC2705", "send msg response broadcast to frameworkPush");
                context.sendBroadcast(intent2);
            }
        }
    }

    public static void getPushState(Context context) {
        e.a("PushLogAC2705", "enter PushEntity:getPushState() pkgName" + context.getPackageName());
        String r = a.r(context);
        if (TextUtils.isEmpty(r)) {
            if (a.o(context)) {
                e.a("PushLogAC2705", "votedPackage is null, getPushState from frameworkPush");
                r = "android";
            } else {
                e.a("PushLogAC2705", "votedPackage is null and not exist frameworkPush, can not getPushState");
                return;
            }
        }
        Intent intent = new Intent("com.huawei.android.push.intent.GET_PUSH_STATE");
        intent.putExtra(PushConstants.PACKAGE_NAME, context.getPackageName());
        intent.setPackage(r);
        intent.setFlags(32);
        context.sendBroadcast(intent, null);
    }

    public static final void getToken(Context context) {
        e.a("PushLogAC2705", "enter PushEntity:getToken() pkgName" + context.getPackageName());
        Intent intent = new Intent("com.huawei.android.push.intent.REGISTER");
        intent.putExtra(PushConstants.PACKAGE_NAME, context.getPackageName());
        int a = a();
        if (-999 != a) {
            intent.putExtra(FriendListActivity.EXTRA_USER_ID, String.valueOf(a));
        }
        intent.setFlags(32);
        if (a.o(context)) {
            intent.setPackage("android");
            e.a("PushLogAC2705", "send register broadcast to frameworkPush");
        }
        context.sendBroadcast(intent);
        new h(context, "push_client_self_info").a("hasRequestToken", true);
    }

    protected void a(Context context, Intent intent) throws UnsupportedEncodingException {
        String str = new String(intent.getByteArrayExtra("device_token"), "UTF-8");
        e.a("PushLogAC2705", "get a deviceToken:" + com.huawei.android.pushagent.c.a.a.e.a(str));
        if (TextUtils.isEmpty(str)) {
            e.c("PushLogAC2705", "get a deviceToken, but it is null");
            return;
        }
        boolean a = new h(context, "push_client_self_info").a("hasRequestToken");
        String a2 = f.a(context, "push_client_self_info", "token_info");
        e.a("PushLogAC2705", "my oldtoken is :" + com.huawei.android.pushagent.c.a.a.e.a(a2));
        if (a || !str.equals(a2)) {
            e.b("PushLogAC2705", "push client begin to receive the token");
            new HandlePushTokenThread(context, str).start();
            Bundle bundle = new Bundle();
            bundle.putString(BOUND_KEY.deviceTokenKey, str);
            bundle.putByteArray(BOUND_KEY.pushMsgKey, null);
            bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_Token.ordinal());
            if (intent.getExtras() != null) {
                bundle.putAll(intent.getExtras());
            }
            new EventThread(this, context, bundle).start();
            return;
        }
        e.c("PushLogAC2705", "get a deviceToken, but do not requested token, and new token is equals old token");
    }

    public void onEvent(Context context, Event event, Bundle bundle) {
    }

    public void onPushMsg(Context context, byte[] bArr, String str) {
    }

    public boolean onPushMsg(Context context, byte[] bArr, Bundle bundle) {
        String str = "";
        if (bundle != null) {
            str = bundle.getString(BOUND_KEY.deviceTokenKey);
        }
        onPushMsg(context, bArr, str);
        return true;
    }

    public void onPushState(Context context, boolean z) {
    }

    public final void onReceive(Context context, Intent intent) {
        try {
            e.a("PushLogAC2705", "enter PushMsgReceiver:onReceive(Intent:" + intent.getAction() + " pkgName:" + context.getPackageName() + ")");
            String action = intent.getAction();
            if ("com.huawei.android.push.intent.REGISTRATION".equals(action) && intent.hasExtra("device_token")) {
                a(context, intent);
            } else if ("com.huawei.android.push.intent.RECEIVE".equals(action) && intent.hasExtra("msg_data")) {
                b(context, intent);
            } else if ("com.huawei.android.push.intent.CLICK".equals(action) && intent.hasExtra("click")) {
                c(context, intent);
            } else if ("com.huawei.android.push.intent.CLICK".equals(action) && intent.hasExtra("clickBtn")) {
                d(context, intent);
            } else if ("com.huawei.intent.action.PUSH_STATE".equals(action)) {
                e(context, intent);
            } else if ("com.huawei.android.push.plugin.RESPONSE".equals(action)) {
                f(context, intent);
            } else {
                e.b("PushLogAC2705", "message can't be recognised:" + intent.toUri(0));
            }
        } catch (Throwable e) {
            e.c("PushLogAC2705", "call onReceive(intent:" + intent + ") cause:" + e.toString(), e);
        }
    }

    public void onToken(Context context, String str) {
    }

    public void onToken(Context context, String str, Bundle bundle) {
        onToken(context, str);
    }
}
