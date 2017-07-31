package com.tencent.mm.sdk.channel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.platformtools.Log;
import java.util.HashMap;
import java.util.Map;

public class MMessage {

    public interface CallBack {
        void handleMessage(Intent intent);
    }

    public static final class Receiver extends BroadcastReceiver {
        public static final Map<String, CallBack> callbacks = new HashMap();
        private final CallBack o;

        public Receiver() {
            this(null);
        }

        public Receiver(CallBack callBack) {
            this.o = callBack;
        }

        public static void registerCallBack(String str, CallBack callBack) {
            callbacks.put(str, callBack);
        }

        public static void unregisterCallBack(String str) {
            callbacks.remove(str);
        }

        public final void onReceive(Context context, Intent intent) {
            Log.d("MicroMsg.SDK.MMessage", "receive intent=" + intent);
            if (this.o != null) {
                this.o.handleMessage(intent);
                Log.d("MicroMsg.SDK.MMessage", "mm message self-handled");
                return;
            }
            CallBack callBack = (CallBack) callbacks.get(intent.getAction());
            if (callBack != null) {
                callBack.handleMessage(intent);
                Log.d("MicroMsg.SDK.MMessage", "mm message handled");
            }
        }
    }

    public static void send(Context context, String str, String str2) {
        send(context, str, ConstantsMMessage.ACTION_MESSAGE, str2);
    }

    public static void send(Context context, String str, String str2, String str3) {
        send(context, str, str2, str3, null);
    }

    public static boolean send(Context context, String str, String str2, String str3, Bundle bundle) {
        String str4 = str + ".permission.MM_MESSAGE";
        Intent intent = new Intent(str2);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        String packageName = context.getPackageName();
        intent.putExtra(ConstantsMMessage.SDK_VERSION, 553910273);
        intent.putExtra(ConstantsMMessage.APP_PACKAGE, packageName);
        intent.putExtra(ConstantsMMessage.CONTENT, str3);
        intent.putExtra(ConstantsMMessage.CHECK_SUM, MMessageUtil.a(str3, packageName));
        context.sendBroadcast(intent, str4);
        Log.d("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str4);
        return true;
    }
}
