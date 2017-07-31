package hlx.ui.localresmgr.cache;

import com.huluxia.controller.c;
import com.huluxia.data.map.MapItem;
import com.huluxia.data.map.b.d;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.n;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.ReStartSoftFlag;
import hlx.data.localstore.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: LocResCacheManager */
public class b {
    private static final String TAG = "LocResCacheManager";
    public static final int bZJ = 0;
    public static final int bZK = 1;
    public static final int bZL = 2;
    public static final int bZM = 3;
    private static b bZN;
    CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ b bZZ;

        {
            this.bZZ = this$0;
        }

        @MessageHandler(message = 262)
        public void onResUnzip(String url) {
            if (this.bZZ.bZS.size() > 0) {
                for (String _tmpItem : this.bZZ.bZS) {
                    if (_tmpItem.equals(url)) {
                        break;
                    }
                }
            }
            this.bZZ.bZS.add(url);
        }
    };
    private List<com.huluxia.data.map.b> bZO = new ArrayList();
    private List<com.huluxia.data.map.b> bZP = new ArrayList();
    private List<com.huluxia.data.map.b> bZQ = new ArrayList();
    private List<com.huluxia.data.map.b> bZR = new ArrayList();
    private List<String> bZS = new ArrayList();
    private volatile int bZT = 1;
    private volatile int bZU = 1;
    private volatile int bZV = 1;
    private volatile int bZW = 1;
    private CallbackHandler bZX = new CallbackHandler(this) {
        final /* synthetic */ b bZZ;

        {
            this.bZZ = this$0;
        }

        @MessageHandler(message = 3329)
        public void onImportLocMap(boolean isCheckUnzipCache, String filePath, MapItem itemInfo, String zipFullPath) {
            if (this.bZZ.loadReocrd) {
                if (isCheckUnzipCache) {
                    if (this.bZZ.b(itemInfo)) {
                        this.bZZ.hE(itemInfo.url);
                    } else {
                        return;
                    }
                }
                synchronized (this.bZZ.bZO) {
                    com.huluxia.data.map.b _tmpItem = hlx.ui.localresmgr.module.b.aN(filePath, zipFullPath);
                    if (_tmpItem != null) {
                        this.bZZ.bZO.add(_tmpItem);
                    }
                }
            }
        }

        @MessageHandler(message = 3330)
        public void onImportLocJs(boolean isCheckUnzipCache, String filePath, MapItem itemInfo) {
            if (this.bZZ.loadReocrd) {
                if (isCheckUnzipCache) {
                    if (this.bZZ.b(itemInfo)) {
                        this.bZZ.hE(itemInfo.url);
                    } else {
                        return;
                    }
                }
                synchronized (this.bZZ.bZP) {
                    com.huluxia.data.map.b _tmpItem = hlx.ui.localresmgr.module.b.a(filePath, itemInfo);
                    if (_tmpItem != null) {
                        this.bZZ.bZP.add(_tmpItem);
                    }
                }
            }
        }

        @MessageHandler(message = 3331)
        public void onImportLocWood(boolean isCheckUnzipCache, String filePath, MapItem itemInfo) {
            if (this.bZZ.loadReocrd) {
                if (isCheckUnzipCache) {
                    if (this.bZZ.b(itemInfo)) {
                        this.bZZ.hE(itemInfo.url);
                    } else {
                        return;
                    }
                }
                synchronized (this.bZZ.bZQ) {
                    com.huluxia.data.map.b _tmpItem = hlx.ui.localresmgr.module.b.b(filePath, itemInfo);
                    if (_tmpItem != null) {
                        this.bZZ.bZQ.add(_tmpItem);
                        if (itemInfo == null && _tmpItem.pU.length() > 1) {
                            c.Uw().aK(filePath, j.Ks() + _tmpItem.pU);
                        }
                    }
                }
            }
        }

        @MessageHandler(message = 3332)
        public void onImportLocSkin(boolean isCheckUnzipCache, String filePath, MapItem itemInfo) {
            if (this.bZZ.loadReocrd) {
                if (isCheckUnzipCache) {
                    if (this.bZZ.b(itemInfo)) {
                        this.bZZ.hE(itemInfo.url);
                    } else {
                        return;
                    }
                }
                synchronized (this.bZZ.bZR) {
                    com.huluxia.data.map.b _tmpItem = hlx.ui.localresmgr.module.b.c(filePath, itemInfo);
                    if (_tmpItem != null) {
                        this.bZZ.bZR.add(_tmpItem);
                    }
                }
            }
        }
    };
    private Runnable bZY = new 4(this);
    private volatile boolean loadReocrd = false;

    public static synchronized b Us() {
        b bVar;
        synchronized (b.class) {
            if (bZN == null) {
                bZN = new b();
            }
            bVar = bZN;
        }
        return bVar;
    }

    private b() {
        EventNotifyCenter.add(c.class, this.aGO);
        EventNotifyCenter.add(n.class, this.bZX);
    }

    public void init() {
        Uu();
    }

    private boolean b(MapItem itemInfo) {
        boolean _isContain = false;
        synchronized (this.bZS) {
            if (this.bZS.size() > 0) {
                for (String _tmpItem : this.bZS) {
                    if (_tmpItem.equals(itemInfo.url)) {
                        _isContain = true;
                        break;
                    }
                }
            }
        }
        return _isContain;
    }

    private void hE(String url) {
        if (this.loadReocrd) {
            synchronized (this.bZS) {
                if (this.bZS.size() > 0) {
                    for (String _tmpItem : this.bZS) {
                        if (_tmpItem.equals(url)) {
                            this.bZS.remove(_tmpItem);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void n(com.huluxia.data.map.b item) {
        if (item != null && this.loadReocrd) {
            synchronized (this.bZR) {
                this.bZR.remove(item);
            }
            c.Uw().r(item);
        }
    }

    public void a(com.huluxia.data.map.b item, String srcPath, String srcName, String dstName) {
        if (this.loadReocrd) {
            synchronized (this.bZR) {
                a(this.bZR, item, dstName);
            }
            c.Uw().d(item, srcPath, srcName, dstName);
        }
    }

    private void a(List<com.huluxia.data.map.b> inList, com.huluxia.data.map.b curItem, String dstname) {
        for (com.huluxia.data.map.b tmpItem : inList) {
            if (tmpItem.path.equals(curItem.path)) {
                tmpItem.setName(dstname);
                tmpItem.aB(dstname);
                tmpItem.setPath(UtilsFile.cU(true) + dstname + a.bKa);
                return;
            }
        }
    }

    public List<com.huluxia.data.map.b> nC(int order) {
        if (!this.loadReocrd) {
            return null;
        }
        if (this.bZW != order) {
            synchronized (this.bZR) {
                if (order == 2) {
                    Collections.sort(this.bZR, new d());
                } else if (order == 1) {
                    Collections.sort(this.bZR, new com.huluxia.data.map.b.a());
                } else {
                    Collections.sort(this.bZR, new com.huluxia.data.map.b.c());
                }
                this.bZW = order;
            }
        }
        return this.bZR;
    }

    public void o(com.huluxia.data.map.b item) {
        if (item != null && this.loadReocrd) {
            synchronized (this.bZQ) {
                this.bZQ.remove(item);
            }
            c.Uw().s(item);
        }
    }

    public void b(com.huluxia.data.map.b item, String srcPath, String srcName, String dstName) {
        if (this.loadReocrd) {
            synchronized (this.bZQ) {
                b(this.bZQ, item, dstName);
            }
            c.Uw().e(item, srcPath, srcName, dstName);
        }
    }

    private void b(List<com.huluxia.data.map.b> inList, com.huluxia.data.map.b curItem, String dstname) {
        for (com.huluxia.data.map.b tmpItem : inList) {
            if (tmpItem.path.equals(curItem.path)) {
                tmpItem.setName(dstname);
                tmpItem.aB(dstname);
                tmpItem.setPath(UtilsFile.Kv() + dstname + ".zip");
                return;
            }
        }
    }

    public List<com.huluxia.data.map.b> nD(int order) {
        if (!this.loadReocrd) {
            return null;
        }
        if (this.bZV != order) {
            synchronized (this.bZQ) {
                if (order == 2) {
                    Collections.sort(this.bZQ, new d());
                } else if (order == 1) {
                    Collections.sort(this.bZQ, new com.huluxia.data.map.b.a());
                } else {
                    Collections.sort(this.bZQ, new com.huluxia.data.map.b.c());
                }
            }
            this.bZV = order;
        }
        return this.bZQ;
    }

    public void Ut() {
        if (this.loadReocrd) {
            synchronized (this.bZQ) {
                for (com.huluxia.data.map.b _tmpFileItem : this.bZQ) {
                    if (_tmpFileItem != null && _tmpFileItem.state == 1) {
                        if ((_tmpFileItem.ver.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0150.Value())) || _tmpFileItem.ver.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_NORMAL.Value()))) && _tmpFileItem.pU != null && _tmpFileItem.pU.length() > 1) {
                            a.Un().hx(_tmpFileItem.pU);
                        }
                    }
                }
            }
        }
    }

    public void p(com.huluxia.data.map.b item) {
        if (this.loadReocrd) {
            synchronized (this.bZP) {
                this.bZP.remove(item);
            }
            c.Uw().t(item);
        }
    }

    public void c(com.huluxia.data.map.b item, String srcPath, String srcName, String dstName) {
        if (this.loadReocrd) {
            synchronized (this.bZP) {
                c(this.bZP, item, dstName);
            }
            c.Uw().f(item, srcPath, srcName, dstName);
        }
    }

    private void c(List<com.huluxia.data.map.b> inList, com.huluxia.data.map.b curItem, String dstname) {
        for (com.huluxia.data.map.b tmpItem : inList) {
            if (tmpItem.path.equals(curItem.path)) {
                tmpItem.setName(dstname);
                tmpItem.aB(dstname);
                tmpItem.setPath(UtilsFile.cT(true) + dstname);
                return;
            }
        }
    }

    public List<com.huluxia.data.map.b> nE(int order) {
        if (!this.loadReocrd) {
            return null;
        }
        if (this.bZU != order) {
            synchronized (this.bZP) {
                if (order == 2) {
                    Collections.sort(this.bZP, new d());
                } else if (order == 1) {
                    Collections.sort(this.bZP, new com.huluxia.data.map.b.a());
                } else {
                    Collections.sort(this.bZP, new com.huluxia.data.map.b.c());
                }
                this.bZU = order;
            }
        }
        return this.bZP;
    }

    public void q(com.huluxia.data.map.b item) {
        if (this.loadReocrd) {
            synchronized (this.bZO) {
                this.bZO.remove(item);
            }
            c.Uw().u(item);
        }
    }

    public void a(com.huluxia.data.map.b item, String dstMapName) {
        if (this.loadReocrd) {
            synchronized (this.bZO) {
                com.huluxia.data.map.b newItem = new com.huluxia.data.map.b(item);
                newItem.aB(dstMapName);
                newItem.setName(dstMapName);
                this.bZO.add(newItem);
            }
            c.Uw().b(item, dstMapName);
        }
    }

    public void a(com.huluxia.data.map.b item, String srcMapPath, String dstMapName) {
        if (this.loadReocrd) {
            synchronized (this.bZO) {
                d(this.bZO, item, dstMapName);
            }
            c.Uw().b(item, srcMapPath, dstMapName);
        }
    }

    public List<com.huluxia.data.map.b> nF(int order) {
        if (!this.loadReocrd) {
            return null;
        }
        if (this.bZT != order) {
            synchronized (this.bZO) {
                if (order == 2) {
                    Collections.sort(this.bZO, new d());
                } else if (order == 1) {
                    Collections.sort(this.bZO, new com.huluxia.data.map.b.a());
                } else if (order == 3) {
                    Collections.sort(this.bZO, new com.huluxia.data.map.b.b());
                } else if (order == 0) {
                    Collections.sort(this.bZO, new com.huluxia.data.map.b.c());
                }
                this.bZT = order;
            }
        }
        return this.bZO;
    }

    private void d(List<com.huluxia.data.map.b> inList, com.huluxia.data.map.b curItem, String dstname) {
        for (com.huluxia.data.map.b tmpItem : inList) {
            if (tmpItem.path.equals(curItem.path)) {
                tmpItem.setName(dstname);
                tmpItem.aB(dstname);
                tmpItem.setPath(UtilsFile.Kq() + dstname);
                return;
            }
        }
    }

    private void Uu() {
        if (!this.loadReocrd) {
            AsyncTaskCenter.getInstance().execute(this.bZY, new 3(this));
        }
    }

    private void Uv() {
        this.loadReocrd = true;
        a.Un().init();
    }
}
