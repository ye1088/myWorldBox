package com.MCWorld.hwpush;

import android.content.Context;
import com.huawei.android.pushagent.api.PushManager;

/* compiled from: HwPushManager */
public class a {
    private Context mContext;

    private a() {
    }

    public static a fY() {
        return a.fZ();
    }

    public void init(Context context) {
        this.mContext = context;
        PushManager.enableReceiveNormalMsg(context, true);
        PushManager.enableReceiveNotifyMsg(this.mContext, false);
        PushManager.requestToken(context);
    }
}
