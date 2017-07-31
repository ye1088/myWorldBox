package hlx.ui.recommendapp.downloadmanager;

import android.content.Context;
import com.huluxia.controller.resource.ResourceCtrl;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.bean.ResTaskInfo.State;
import com.huluxia.controller.resource.handler.impl.a;
import com.huluxia.module.q;
import com.huluxia.r;
import com.huluxia.utils.j;

/* compiled from: RecomAppDownManager */
public class b {
    private static b cfu;

    public static synchronized b VH() {
        b bVar;
        synchronized (b.class) {
            if (cfu == null) {
                cfu = new b();
            }
            bVar = cfu;
        }
        return bVar;
    }

    public b() {
        ResourceCtrl.getInstance().registerHandler(1000008, a.class);
    }

    public void a(Context context, hlx.ui.recommendapp.a.a item) {
        ResTaskInfo info = ResourceCtrl.getInstance().getTaskInfo(item.appdownurl, 1000008);
        ResTaskInfo tInfo;
        if (info == null) {
            tInfo = new ResTaskInfo();
            tInfo.url = item.appdownurl;
            tInfo.filename = item.filename;
            tInfo.mM = 1000008;
            tInfo.dir = j.getDownloadPath();
            tInfo.na = true;
            tInfo.nb = false;
            ResourceCtrl.getInstance().addTask(tInfo);
            r.ck().j(hlx.data.tongji.a.bOM, String.valueOf(item.id));
            q.aG((long) item.id);
        } else if (info.state == State.DOWNLOAD_PROGRESS.ordinal()) {
            ResourceCtrl.getInstance().pauseTask(info);
        } else if (info.state != State.WAITING.ordinal() && info.state != State.PREPARE.ordinal() && info.state != State.DOWNLOAD_ERROR_RETRY.ordinal()) {
            tInfo = new ResTaskInfo();
            tInfo.url = item.appdownurl;
            tInfo.filename = item.filename;
            tInfo.mM = 1000008;
            tInfo.dir = j.getDownloadPath();
            tInfo.na = true;
            tInfo.nb = false;
            ResourceCtrl.getInstance().addTask(tInfo);
        }
    }
}
