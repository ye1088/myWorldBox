package com.huluxia.mipush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.widget.pushserver.a;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import java.util.List;

public class HlxXiaomiMessageReceiver extends PushMessageReceiver {
    private static final String TAG = "XiaomiMessageReceiver";
    private static final int apX = 2;
    private static final int aqi = 0;
    private static final int aqj = 1;
    private static final int aqk = 2;
    private String apY;
    private long apZ = -1;
    private String aqa;
    private String aqb;
    private String aqc;
    private String aqd;
    private String aqe;
    private String aqf;
    private String aqg;
    private String aqh;

    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.v(TAG, "onReceivePassThroughMessage is called. " + message.toString());
        a(context, 2, message != null ? message.getContent() : "", 0, message != null ? message.getNotifyId() : 0);
    }

    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Log.v(TAG, "onNotificationMessageClicked is called. " + message.toString());
        a(context, 2, message != null ? message.getContent() : "", 2, message != null ? message.getNotifyId() : 0);
    }

    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        Log.v(TAG, "onNotificationMessageArrived is called. " + message.toString());
        a(context, 2, message != null ? message.getContent() : "", 1, message != null ? message.getNotifyId() : 0);
    }

    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String cmdArg1;
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        if (arguments == null || arguments.size() <= 0) {
            cmdArg1 = null;
        } else {
            cmdArg1 = (String) arguments.get(0);
        }
        Log.v(TAG, "onCommandResult is called. " + message.toString() + ", regid " + cmdArg1);
        if (arguments == null || arguments.size() <= 1) {
            Object obj = null;
        } else {
            String cmdArg2 = (String) arguments.get(1);
        }
        String log = "";
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == 0) {
                this.apY = cmdArg1;
                b(context, 2, this.apY);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() != 0) {
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() != 0) {
            }
        } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (message.getResultCode() != 0) {
            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (message.getResultCode() != 0) {
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() != 0) {
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() != 0) {
            }
        } else if (!MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command) || message.getResultCode() != 0) {
        }
    }

    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = (arguments == null || arguments.size() <= 0) ? null : (String) arguments.get(0);
        Log.i(TAG, "onReceiveRegisterResult is called. " + message.toString() + ", regid " + cmdArg1);
        if (MiPushClient.COMMAND_REGISTER.equals(command) && message.getResultCode() == 0) {
            this.apY = cmdArg1;
            b(context, 2, this.apY);
        }
    }

    public static void b(Context context, int model, String cloudUserID) {
        Intent intent = new Intent();
        intent.putExtra("model", model);
        intent.putExtra("cloud_user_id", cloudUserID);
        intent.setAction(context.getPackageName() + ".action.broadcast.binddevice");
        context.sendBroadcast(intent);
    }

    public static void a(Context context, int model, String msg, int type, int notifyid) {
        if (type == 0) {
            a.PV().c(model, msg, notifyid);
        } else if (1 == type) {
            a.PV().d(model, msg, notifyid);
        } else if (2 == type) {
            a.PV().e(model, msg, notifyid);
        } else {
            HLog.error(TAG, "invalid type %d", Integer.valueOf(type));
        }
    }
}
