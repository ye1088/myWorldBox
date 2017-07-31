package com.huluxia.hwpush;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.huawei.android.pushagent.PushReceiver.BOUND_KEY;
import com.huawei.android.pushagent.PushReceiver.Event;
import com.huawei.android.pushagent.api.PushEventReceiver;
import com.huluxia.widget.pushserver.a;

public class HlxHuaweiPushReceiver extends PushEventReceiver {
    private static final String TAG = "HlxHuaweiPushReceiver";
    private static final int tv = 3;

    public void onToken(Context context, String token, Bundle extras) {
        Log.d(TAG, "获取token和belongId成功，token = " + token + ",belongId = " + extras.getString("belongId"));
        if (fX()) {
            b(context, 3, token);
        }
    }

    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String content = new String(msg, "UTF-8");
            Log.d(TAG, content);
            c(context, 3, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onEvent(Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (notifyId != 0) {
                ((NotificationManager) context.getSystemService("notification")).cancel(notifyId);
            }
            Log.d(TAG, "收到通知附加消息： " + extras.getString(BOUND_KEY.pushMsgKey));
        } else if (Event.PLUGINRSP.equals(event)) {
            int reportType = extras.getInt(BOUND_KEY.PLUGINREPORTTYPE, -1);
            boolean isSuccess = extras.getBoolean(BOUND_KEY.PLUGINREPORTRESULT, false);
            String message = "";
            if (1 == reportType) {
                message = "LBS report result :";
            } else if (2 == reportType) {
                message = "TAG report result :";
            }
            Log.d(TAG, message + isSuccess);
        }
        super.onEvent(context, event, extras);
    }

    public static void b(Context context, int model, String cloudUserID) {
        Intent intent = new Intent();
        intent.putExtra("model", model);
        intent.putExtra("cloud_user_id", cloudUserID);
        intent.setAction(context.getPackageName() + ".action.broadcast.binddevice");
        context.sendBroadcast(intent);
    }

    public static void c(Context context, int model, String msg) {
        a.PV().c(model, msg, 0);
    }

    private static boolean fX() {
        return "HUAWEI".equals(Build.MANUFACTURER);
    }
}
