package hlx.home.fragment;

import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.q;
import com.MCWorld.r;
import hlx.ui.recommendapp.a.a;
import java.util.Iterator;

class MapJsWoodSkinFragment$4 extends CallbackHandler {
    final /* synthetic */ MapJsWoodSkinFragment bQy;

    MapJsWoodSkinFragment$4(MapJsWoodSkinFragment this$0) {
        this.bQy = this$0;
    }

    @MessageHandler(message = 256)
    public void onWorkPrepare(String url) {
        MapJsWoodSkinFragment.m(this.bQy);
        if (MapJsWoodSkinFragment.l(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            MapJsWoodSkinFragment.l(this.bQy).notifyDataSetChanged();
        }
    }

    @MessageHandler(message = 257)
    public void onWorkWait(String url) {
        MapJsWoodSkinFragment.m(this.bQy);
        if (MapJsWoodSkinFragment.l(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            MapJsWoodSkinFragment.l(this.bQy).notifyDataSetChanged();
        }
    }

    @MessageHandler(message = 262)
    public void onUnzipComplete(String url) {
        MapJsWoodSkinFragment.m(this.bQy);
    }

    @MessageHandler(message = 263)
    public void onDownloadComplete(String url) {
        if (MapJsWoodSkinFragment.l(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            MapJsWoodSkinFragment.l(this.bQy).notifyDataSetChanged();
        }
        if (url != null && MapJsWoodSkinFragment.e(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            Iterator it = MapJsWoodSkinFragment.e(this.bQy).adList.iterator();
            while (it.hasNext()) {
                a tmpItem = (a) it.next();
                if (url.equals(tmpItem.appdownurl)) {
                    r.ck().j(hlx.data.tongji.a.bON, String.valueOf(tmpItem.id));
                    q.aH((long) tmpItem.id);
                    return;
                }
            }
        }
    }

    @MessageHandler(message = 265)
    public void onInstallApk(String url, String apkPath, String signature) {
        if (url != null && MapJsWoodSkinFragment.e(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            Iterator it = MapJsWoodSkinFragment.e(this.bQy).adList.iterator();
            while (it.hasNext()) {
                a tmpItem = (a) it.next();
                if (url.equals(tmpItem.appdownurl)) {
                    hlx.ui.recommendapp.statisticsrecord.a.VI().a(tmpItem);
                    r.ck().j(hlx.data.tongji.a.bOO, String.valueOf(tmpItem.id));
                    q.aI((long) tmpItem.id);
                    return;
                }
            }
        }
    }
}
