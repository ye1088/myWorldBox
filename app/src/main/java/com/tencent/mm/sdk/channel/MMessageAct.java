package com.tencent.mm.sdk.channel;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.platformtools.Log;
import org.bytedeco.javacpp.avutil;

public class MMessageAct {
    public static boolean send(Context context, String str, String str2) {
        return send(context, str, str + ".wxapi.WXEntryActivity", str2, null);
    }

    public static boolean send(Context context, String str, String str2, Bundle bundle) {
        return send(context, str, str + ".wxapi.WXEntryActivity", str2, bundle);
    }

    public static boolean send(Context context, String str, String str2, String str3, Bundle bundle) {
        if (context == null || str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            Log.e("MicroMsg.SDK.MMessageAct", "send fail, invalid arguments");
            return false;
        }
        Intent intent = new Intent();
        intent.setClassName(str, str2);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        String packageName = context.getPackageName();
        intent.putExtra(ConstantsMMessage.SDK_VERSION, 553910273);
        intent.putExtra(ConstantsMMessage.APP_PACKAGE, packageName);
        intent.putExtra(ConstantsMMessage.CONTENT, str3);
        intent.putExtra(ConstantsMMessage.CHECK_SUM, MMessageUtil.a(str3, packageName));
        intent.addFlags(268435456).addFlags(avutil.AV_CPU_FLAG_AVXSLOW);
        try {
            context.startActivity(intent);
            Log.d("MicroMsg.SDK.MMessageAct", "send mm message, intent=" + intent);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.e("MicroMsg.SDK.MMessageAct", "send fail, target ActivityNotFound");
            return false;
        }
    }

    public static boolean sendToWx(Context context, String str) {
        return send(context, "com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity", str, null);
    }

    public static boolean sendToWx(Context context, String str, Bundle bundle) {
        return send(context, "com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity", str, bundle);
    }
}
