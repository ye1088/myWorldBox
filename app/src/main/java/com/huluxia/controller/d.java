package com.huluxia.controller;

import android.content.Context;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.k;
import com.huluxia.module.n;
import hlx.ui.a;

/* compiled from: EnvironBridgeManager */
public class d {
    private static d mr;
    private CallbackHandler ms = new CallbackHandler(this) {
        final /* synthetic */ d mt;

        {
            this.mt = this$0;
        }

        @MessageHandler(message = 3585)
        public void startPersonalStudio(Context context, int studioId) {
            k.startPersonalStudio(context, studioId);
        }

        @MessageHandler(message = 3587)
        public void startSeedTopicDetail(Context context, long seedId, long postId) {
            a.startSeedTopicDetail(context, seedId, postId);
        }
    };

    private d() {
        EventNotifyCenter.add(n.class, this.ms);
    }

    public static synchronized d dJ() {
        d dVar;
        synchronized (d.class) {
            if (mr == null) {
                mr = new d();
            }
            dVar = mr;
        }
        return dVar;
    }
}
