package com.huluxia.module.topic;

import com.huluxia.data.j;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsSystem;
import com.huluxia.module.h;
import com.huluxia.utils.ah;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: TopicModule2 */
class k$2 implements Listener<g> {
    final /* synthetic */ int aBJ;
    final /* synthetic */ long aCL;
    final /* synthetic */ k aCN;
    final /* synthetic */ boolean aCO;
    final /* synthetic */ Object aCP;
    final /* synthetic */ String val$tag;

    k$2(k this$0, int i, long j, String str, boolean z, Object obj) {
        this.aCN = this$0;
        this.aBJ = i;
        this.aCL = j;
        this.val$tag = str;
        this.aCO = z;
        this.aCP = obj;
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((g) obj);
    }

    public void a(g info) {
        long uid = j.ep().getUserid();
        g oldinfo = ah.KZ().bu(uid);
        if (oldinfo == null) {
            oldinfo = new g();
        }
        if (oldinfo.topicCats == null) {
            oldinfo.topicCats = new HashSet();
        }
        if (oldinfo.commentCats == null) {
            oldinfo.commentCats = new HashSet();
        }
        if (oldinfo.topicHours == null) {
            oldinfo.topicHours = new HashMap();
        }
        if (oldinfo.commentHours == null) {
            oldinfo.commentHours = new HashMap();
        }
        HLog.verbose("requestCreatePower", "oldinfo " + oldinfo, new Object[0]);
        if (info != null && info.isSucc()) {
            String hourstr = UtilsSystem.getHourStr();
            if (this.aBJ == 1) {
                if (info.ispower == 1) {
                    oldinfo.topicCats.add(Long.valueOf(this.aCL));
                } else {
                    oldinfo.topicCats.remove(Long.valueOf(this.aCL));
                    oldinfo.topicTipTitle = info.title;
                    oldinfo.topicTipMsg = info.message;
                }
                oldinfo.topicHours.put(Long.valueOf(this.aCL), hourstr);
            } else {
                if (info.ispower == 1) {
                    oldinfo.commentCats.add(Long.valueOf(this.aCL));
                } else {
                    oldinfo.commentCats.remove(Long.valueOf(this.aCL));
                    oldinfo.commentTipTitle = info.title;
                    oldinfo.commentTipMsg = info.message;
                }
                oldinfo.commentHours.put(Long.valueOf(this.aCL), hourstr);
            }
            oldinfo.isvideo = info.isvideo;
            oldinfo.videosourl = info.videosourl;
            oldinfo.videosomd5 = info.videosomd5;
            ah.KZ().a(uid, oldinfo);
        }
        HLog.verbose("requestCreatePower", "oldinfo " + oldinfo, new Object[0]);
        EventNotifyCenter.notifyEvent(h.class, h.arH, new Object[]{info, this.val$tag, Boolean.valueOf(this.aCO), this.aCP});
    }
}
