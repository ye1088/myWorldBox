package com.huluxia.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.huluxia.data.map.MapItem;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;
import com.huluxia.utils.ah;
import com.huluxia.utils.ai;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.DownFileType;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DownMapTaskManager */
public class d {
    private static d aDi = null;
    private Map<String, c> aDd = new HashMap();
    BroadcastReceiver aDe = new a();
    private String prefix = "map";

    /* compiled from: DownMapTaskManager */
    private class a extends BroadcastReceiver {
        final /* synthetic */ d aDj;

        private a(d dVar) {
            this.aDj = dVar;
        }

        public void onReceive(Context context, Intent intent) {
            String taskId = intent.getStringExtra("taskid");
            int status = intent.getIntExtra("success", 0);
            if (taskId != null) {
                c task = (c) this.aDj.aDd.get(taskId);
                if (task != null) {
                    ai.p(task.mTitle, ai.imgUrl, task.aCW);
                    ai.p(task.mTitle, ai.bmh, String.valueOf(task.mapId));
                    ai.p(task.mTitle, ai.bmg, String.valueOf(task.postId));
                    ah.KZ().am(j.Kq() + task.mTitle, task.pN);
                    this.aDj.aDd.remove(taskId);
                    try {
                        if (taskId.startsWith("map")) {
                            long id = Long.parseLong(taskId.replace("map", ""));
                            EventNotifyCenter.notifyEvent(n.class, 768, new Object[]{Long.valueOf(id)});
                            i.ki(DownFileType.defaultType.Value());
                        }
                    } catch (Exception e) {
                        HLog.error(this, "unzip callback taskid =" + taskId, e, new Object[0]);
                    }
                }
            }
        }
    }

    public static d Et() {
        if (aDi == null) {
            aDi = new d();
            aDi.Es();
        }
        return aDi;
    }

    public void Es() {
        i.i(this.aDe);
    }

    public c be(long id) {
        return (c) this.aDd.get(this.prefix + id);
    }

    public void a(MapItem item) {
        c task = new c(item);
        this.aDd.put(task.id, task);
    }

    public void bc(long id) {
        String tid = this.prefix + id;
        if (this.aDd.containsKey(tid)) {
            ((c) this.aDd.get(tid)).Ep();
        }
    }

    public void bd(long id) {
        String tid = this.prefix + id;
        if (this.aDd.containsKey(tid)) {
            ((c) this.aDd.get(tid)).Eq();
        }
    }

    public boolean bf(long id) {
        return this.aDd.get(Long.valueOf(id)) != null;
    }
}
