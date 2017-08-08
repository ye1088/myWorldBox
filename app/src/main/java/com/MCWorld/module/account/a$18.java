package com.MCWorld.module.account;

import com.MCWorld.HTApplication;
import com.MCWorld.data.message.MsgCounts;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.service.h;
import com.MCWorld.service.i;
import java.util.Locale;
import org.json.JSONObject;

/* compiled from: AccountModule */
class a$18 implements Listener<String> {
    final /* synthetic */ a aBH;
    final /* synthetic */ boolean aBK;
    final /* synthetic */ boolean aBL;
    final /* synthetic */ boolean aBM;
    final /* synthetic */ boolean aBN;

    a$18(a this$0, boolean z, boolean z2, boolean z3, boolean z4) {
        this.aBH = this$0;
        this.aBK = z;
        this.aBL = z2;
        this.aBM = z3;
        this.aBN = z4;
    }

    public void onResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            if (1 == json.optInt("status")) {
                MsgCounts msgCounts = new MsgCounts(json.optJSONObject("counts"));
                HTApplication.i(msgCounts.getFollow());
                if (msgCounts.getFollow() > 0) {
                    i.h(msgCounts.getFollow(), 0);
                }
                MsgCounts oldCounts = HTApplication.bM();
                if (oldCounts != null && oldCounts.getAll() == msgCounts.getAll() && oldCounts.getReply() == msgCounts.getReply() && oldCounts.getSys() == msgCounts.getSys()) {
                    HLog.info("AccountModule", "oldCounts all(%d) reply(%d) sys(%d)   nowCounts all(%d) reply(%d) sys(%d)", new Object[]{Long.valueOf(oldCounts.getAll()), Long.valueOf(oldCounts.getReply()), Long.valueOf(oldCounts.getSys()), Long.valueOf(msgCounts.getAll()), Long.valueOf(msgCounts.getReply()), Long.valueOf(msgCounts.getSys())});
                    return;
                }
                HTApplication.a(msgCounts);
                h.EC().ED();
                HTApplication.bC();
                if (msgCounts.getAll() != 0) {
                    String szMsg = String.format(Locale.getDefault(), "你收到%d条新消息", new Object[]{Long.valueOf(msgCounts.getAll())});
                    if (!(HTApplication.bU() || !this.aBK || this.aBL)) {
                        h.EC().a("消息提醒", szMsg, msgCounts, this.aBM, this.aBN);
                    }
                    i.EH();
                }
            }
        } catch (Exception e) {
            HLog.error("AccountModule", "sendMsgCount error" + e.toString(), new Object[0]);
        }
    }
}
