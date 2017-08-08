package com.MCWorld.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.MCWorld.data.js.JsItem;
import com.MCWorld.data.map.MapItem;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;
import com.MCWorld.utils.ai;
import com.MCWorld.utils.aq;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.DownFileType;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DownJsTaskManager */
public class b {
    private static b aDc = null;
    private Map<String, a> aDd = new HashMap();
    BroadcastReceiver aDe = new a();
    private String prefix = "js";

    /* compiled from: DownJsTaskManager */
    private class a extends BroadcastReceiver {
        final /* synthetic */ b aDf;

        private a(b bVar) {
            this.aDf = bVar;
        }

        public void onReceive(Context context, Intent intent) {
            String taskId = intent.getStringExtra("taskid");
            int status = intent.getIntExtra("success", 0);
            if (taskId != null) {
                a task = (a) this.aDf.aDd.get(taskId);
                if (status == 1 && task != null) {
                    String jsPath = j.cT(true) + (task.mTitle + hlx.data.localstore.a.bJY);
                    if (j.isExist(jsPath)) {
                        this.aDf.a(task.mTitle, jsPath, task.pN, aq.gk(task.ver), task.mapId, task.postId, task.aCW);
                    }
                }
                this.aDf.aDd.remove(taskId);
                try {
                    if (taskId.startsWith(this.aDf.prefix)) {
                        long id = Long.parseLong(taskId.replace(this.aDf.prefix, ""));
                        EventNotifyCenter.notifyEvent(n.class, 769, new Object[]{Long.valueOf(id)});
                    }
                } catch (Exception e) {
                    HLog.error(this, "unzip callback taskid =" + taskId, e, new Object[0]);
                }
            }
        }
    }

    public static b Er() {
        if (aDc == null) {
            aDc = new b();
            aDc.Es();
        }
        return aDc;
    }

    public void Es() {
        i.i(this.aDe);
    }

    public a bb(long id) {
        return (a) this.aDd.get(this.prefix + id);
    }

    public void a(MapItem item) {
        a task = new a(item);
        this.aDd.put(task.aCX, task);
    }

    public void bc(long id) {
        String tid = this.prefix + id;
        if (this.aDd.containsKey(tid)) {
            ((a) this.aDd.get(tid)).Ep();
        }
    }

    public void bd(long id) {
        String tid = this.prefix + id;
        if (this.aDd.containsKey(tid)) {
            ((a) this.aDd.get(tid)).Eq();
        }
    }

    private void a(String name, String path, String date, String ver, long map_id, long post_id, String img_url) {
        ai.b(new JsItem(name, path, date, 1, ver, map_id, post_id, img_url));
        i.ki(DownFileType.Js.Value());
    }
}
