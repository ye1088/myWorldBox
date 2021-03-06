package com.MCWorld.ui.mctool;

import android.text.Html;
import com.MCWorld.data.server.a.a;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.utils.v;

class ServerDetailFragment$1 extends CallbackHandler {
    final /* synthetic */ ServerDetailFragment bck;

    ServerDetailFragment$1(ServerDetailFragment this$0) {
        this.bck = this$0;
    }

    @MessageHandler(message = 258)
    public void onRecvServerDetailData(boolean succ, a info) {
        if (succ && info != null) {
            ServerDetailFragment.a(this.bck).setText(Html.fromHtml(info.mapDesc));
            ServerDetailFragment.b(this.bck).setVisibility(8);
            ServerDetailFragment.c(this.bck).setText("上线时间：" + v.br(Long.parseLong(info.createTime)));
        }
    }
}
