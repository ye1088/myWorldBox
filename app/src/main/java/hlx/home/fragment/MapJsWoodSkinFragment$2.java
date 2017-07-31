package hlx.home.fragment;

import com.huluxia.data.map.f;
import com.huluxia.data.server.a;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.n;
import hlx.ui.mapseed.b;

class MapJsWoodSkinFragment$2 extends CallbackHandler {
    final /* synthetic */ MapJsWoodSkinFragment bQy;

    MapJsWoodSkinFragment$2(MapJsWoodSkinFragment this$0) {
        this.bQy = this$0;
    }

    @MessageHandler(message = 3073)
    public void onRecvMapListInfo(boolean succ, int tag, f info) {
        MapJsWoodSkinFragment.h(this.bQy).onRefreshComplete();
        MapJsWoodSkinFragment.g(this.bQy).onLoadComplete();
        if (tag != MapJsWoodSkinFragment.a(this.bQy)) {
            return;
        }
        if (succ && info != null) {
            if (info.start <= 20) {
                MapJsWoodSkinFragment.a(this.bQy, info);
            } else if (MapJsWoodSkinFragment.f(this.bQy) != null) {
                MapJsWoodSkinFragment.f(this.bQy).start = info.start;
                MapJsWoodSkinFragment.f(this.bQy).more = info.more;
                MapJsWoodSkinFragment.f(this.bQy).mapList.addAll(info.mapList);
            } else {
                return;
            }
            MapJsWoodSkinFragment.i(this.bQy).a(MapJsWoodSkinFragment.f(this.bQy).mapList, true);
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(true));
        } else if (MapJsWoodSkinFragment.f(this.bQy) == null) {
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(false));
        }
    }

    @MessageHandler(message = 3078)
    public void onRecvMapListInfo(boolean succ, int tag, a info) {
        MapJsWoodSkinFragment.h(this.bQy).onRefreshComplete();
        MapJsWoodSkinFragment.g(this.bQy).onLoadComplete();
        if (tag != MapJsWoodSkinFragment.a(this.bQy)) {
            return;
        }
        if (succ && info != null) {
            if (info.start <= 20) {
                MapJsWoodSkinFragment.a(this.bQy, info);
            } else if (MapJsWoodSkinFragment.b(this.bQy) != null) {
                MapJsWoodSkinFragment.b(this.bQy).start = info.start;
                MapJsWoodSkinFragment.b(this.bQy).more = info.more;
                MapJsWoodSkinFragment.b(this.bQy).serverList.addAll(info.serverList);
            } else {
                return;
            }
            MapJsWoodSkinFragment.j(this.bQy).a(MapJsWoodSkinFragment.b(this.bQy).serverList, true, false);
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(true));
        } else if (MapJsWoodSkinFragment.b(this.bQy) == null) {
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(false));
        }
    }

    @MessageHandler(message = 298)
    public void onRecvListInfo(boolean succ, int tag, b info) {
        MapJsWoodSkinFragment.h(this.bQy).onRefreshComplete();
        MapJsWoodSkinFragment.g(this.bQy).onLoadComplete();
        if (tag != MapJsWoodSkinFragment.a(this.bQy)) {
            return;
        }
        if (succ && info != null) {
            if (info.start <= 20) {
                MapJsWoodSkinFragment.a(this.bQy, info);
            } else if (MapJsWoodSkinFragment.d(this.bQy) != null) {
                MapJsWoodSkinFragment.d(this.bQy).start = info.start;
                MapJsWoodSkinFragment.d(this.bQy).more = info.more;
                MapJsWoodSkinFragment.d(this.bQy).seedList.addAll(info.seedList);
            } else {
                return;
            }
            MapJsWoodSkinFragment.k(this.bQy).c(MapJsWoodSkinFragment.d(this.bQy).seedList, true);
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(true));
        } else if (MapJsWoodSkinFragment.d(this.bQy) == null) {
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(false));
        }
    }

    @MessageHandler(message = 302)
    public void onRecvListInfo(boolean succ, int tag, hlx.ui.recommendapp.a info) {
        MapJsWoodSkinFragment.h(this.bQy).onRefreshComplete();
        if (tag != MapJsWoodSkinFragment.a(this.bQy)) {
            return;
        }
        if (succ && info != null) {
            if (info.start > 20) {
                if (MapJsWoodSkinFragment.e(this.bQy) != null) {
                    MapJsWoodSkinFragment.e(this.bQy).start = info.start;
                    MapJsWoodSkinFragment.e(this.bQy).more = info.more;
                    MapJsWoodSkinFragment.l(this.bQy).c(info.adList, false);
                } else {
                    return;
                }
            } else if (info.status == 0) {
                HLog.error("MapJsWoodSkinFragmeng.onRecvListInfo", info.status + "" + info.msg, new Object[0]);
                return;
            } else {
                MapJsWoodSkinFragment.a(this.bQy, info);
                MapJsWoodSkinFragment.l(this.bQy).c(MapJsWoodSkinFragment.e(this.bQy).adList, true);
            }
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(true));
        } else if (MapJsWoodSkinFragment.e(this.bQy) == null) {
            EventNotifyCenter.notifyEvent(n.class, 531, Boolean.valueOf(false));
        }
    }
}
