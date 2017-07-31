package com.huluxia.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.huluxia.data.map.MapItem;
import com.huluxia.data.skin.SkinItem;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;
import com.huluxia.utils.ah;
import com.huluxia.utils.ai;
import com.huluxia.utils.aq;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.DownFileType;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DownSkinTaskManager */
public class f {
    private static f aDm = null;
    private Map<String, e> aDd = new HashMap();
    BroadcastReceiver aDe = new a();
    private String prefix = "skin";

    /* compiled from: DownSkinTaskManager */
    private class a extends BroadcastReceiver {
        final /* synthetic */ f aDn;

        private a(f fVar) {
            this.aDn = fVar;
        }

        public void onReceive(Context context, Intent intent) {
            String taskId = intent.getStringExtra("taskid");
            int status = intent.getIntExtra("success", 0);
            if (taskId != null) {
                e task = (e) this.aDn.aDd.get(taskId);
                if (status == 1 && task != null) {
                    String path = j.cU(true) + (task.mTitle + hlx.data.localstore.a.bKa);
                    if (j.isExist(path)) {
                        this.aDn.a(task.mTitle, task.pN, path, aq.gk(task.ver), task.mapId, task.postId, task.aCW);
                    }
                }
                this.aDn.aDd.remove(taskId);
                try {
                    if (taskId.startsWith(this.aDn.prefix)) {
                        long id = Long.parseLong(taskId.replace(this.aDn.prefix, ""));
                        EventNotifyCenter.notifyEvent(n.class, 770, new Object[]{Long.valueOf(id)});
                    }
                } catch (Exception e) {
                    HLog.error(this, "unzip callback taskid =" + taskId, e, new Object[0]);
                }
            }
        }
    }

    public static f Eu() {
        if (aDm == null) {
            aDm = new f();
            aDm.Es();
        }
        return aDm;
    }

    public void Es() {
        i.i(this.aDe);
    }

    public e bg(long id) {
        return (e) this.aDd.get(this.prefix + id);
    }

    public void a(MapItem item) {
        e task = new e(item);
        this.aDd.put(task.aCX, task);
    }

    public void bc(long id) {
        String tid = this.prefix + id;
        if (this.aDd.containsKey(tid)) {
            ((e) this.aDd.get(tid)).Ep();
        }
    }

    public void bd(long id) {
        String tid = this.prefix + id;
        if (this.aDd.containsKey(tid)) {
            ((e) this.aDd.get(tid)).Eq();
        }
    }

    private void a(String name, String date, String path, String ver, long map_id, long post_id, String img_url) {
        ai.b(new SkinItem(name, path, date, 1, ver, map_id, post_id, img_url));
        ah.KZ().gb(path);
        i.ki(DownFileType.Skin.Value());
    }
}
