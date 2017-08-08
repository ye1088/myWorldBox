package com.MCWorld.utils;

import android.app.Activity;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.factory.b.a;
import com.MCWorld.controller.resource.handler.impl.McJsHandler;
import com.MCWorld.controller.resource.handler.impl.McMapHandler;
import com.MCWorld.controller.resource.handler.impl.McSkinHandler;
import com.MCWorld.controller.resource.handler.impl.McWoodHandler;
import com.MCWorld.controller.resource.handler.impl.q;
import com.MCWorld.data.map.MapItem;
import com.MCWorld.data.skin.SkinItem;
import com.MCWorld.data.wood.WoodItem;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.ac;
import com.MCWorld.module.k;
import com.MCWorld.module.x;
import com.MCWorld.r;
import com.MCWorld.service.d;
import com.MCWorld.service.f;
import com.MCWorld.ui.mctool.b;
import com.MCWorld.widget.Constants.DownFileType;

/* compiled from: MapDownloader */
public class i implements com.MCWorld.i {
    private static i bkc = null;

    public static synchronized i Ko() {
        i iVar;
        synchronized (i.class) {
            if (bkc == null) {
                bkc = new i();
            }
            iVar = bkc;
        }
        return iVar;
    }

    public i() {
        ResourceCtrl.getInstance().registerHandler(1000000, McMapHandler.class);
        ResourceCtrl.getInstance().registerHandler(a.ny, McJsHandler.class);
        ResourceCtrl.getInstance().registerHandler(a.nz, McWoodHandler.class);
        ResourceCtrl.getInstance().registerHandler(a.nA, McSkinHandler.class);
    }

    public void a(Activity context, String strVersions, int resType) {
    }

    protected String a(DownFileType fileType) {
        return HttpMgr.getInstance().getGameDownloadPath(fileType).getAbsolutePath();
    }

    public ResTaskInfo a(MapItem item, int resourceType, DownFileType fileType, String unzipDir) {
        q tInfo = new q();
        tInfo.mN = new DownloadRecord();
        DownloadRecord downloadRecord = tInfo.mN;
        String str = item.url;
        tInfo.url = str;
        downloadRecord.url = str;
        downloadRecord = tInfo.mN;
        str = item.name;
        tInfo.filename = str;
        downloadRecord.name = str;
        downloadRecord = tInfo.mN;
        str = a(fileType);
        tInfo.dir = str;
        downloadRecord.dir = str;
        tInfo.mM = resourceType;
        tInfo.mR = unzipDir;
        tInfo.version = item.version;
        return tInfo;
    }

    public void a(Activity activity, MapItem item, int type) {
        int resType = -1;
        String unzipDir = "";
        DownFileType fileType = DownFileType.defaultType;
        if (type == DownFileType.defaultType.Value()) {
            resType = 1000000;
            unzipDir = j.Kq();
        } else if (type == DownFileType.Js.Value()) {
            resType = a.ny;
            fileType = DownFileType.Js;
            unzipDir = j.cT(false);
        } else if (type == DownFileType.Skin.Value()) {
            resType = a.nA;
            fileType = DownFileType.Skin;
            unzipDir = j.cU(false);
        } else if (type == DownFileType.Wood.Value()) {
            resType = a.nz;
            fileType = DownFileType.Wood;
            unzipDir = j.Kv();
        }
        ResTaskInfo info = ResourceCtrl.getInstance().getTaskInfo(item.url, resType);
        if (info == null) {
            ResourceCtrl.getInstance().addTask(a(item, resType, fileType, unzipDir));
            if (type == DownFileType.defaultType.Value()) {
                k.DC().aF(item.id);
                r.ck().K_umengEvent(hlx.data.tongji.a.bME);
            } else if (type == DownFileType.Js.Value()) {
                com.MCWorld.module.i.DB().ja((int) item.id);
                r.ck().K_umengEvent(hlx.data.tongji.a.bMF);
            } else if (type == DownFileType.Skin.Value()) {
                x.DN().aM(item.id);
                r.ck().K_umengEvent(hlx.data.tongji.a.bMH);
            } else if (type == DownFileType.Wood.Value()) {
                ac.DT().aO(item.id);
                r.ck().K_umengEvent(hlx.data.tongji.a.bMG);
            }
        } else if (info.state == State.DOWNLOAD_PROGRESS.ordinal()) {
            ResourceCtrl.getInstance().pauseTask(info);
        } else if (info.state != State.WAITING.ordinal() && info.state != State.PREPARE.ordinal() && info.state != State.DOWNLOAD_ERROR_RETRY.ordinal()) {
            ResourceCtrl.getInstance().addTask(a(item, resType, fileType, unzipDir));
        } else {
            return;
        }
        b.a(activity.getApplicationContext(), item, type);
    }

    public void a(MapItem item, int type) {
        if (type == DownFileType.defaultType.Value()) {
            d.Et().a(item);
            d.Et().bd(item.id);
        } else if (type == DownFileType.Js.Value()) {
            com.MCWorld.service.b.Er().a(item);
            com.MCWorld.service.b.Er().bd(item.id);
        } else if (type == DownFileType.Skin.Value()) {
            f.Eu().a(item);
            f.Eu().bd(item.id);
        }
    }

    private String er(String name) {
        return name + ".zip";
    }

    public void n(int id) {
        k.DC().jc(id);
    }

    public void p(int id) {
        x.DN().jm(id);
    }

    public void r(int id) {
        ac.DT().jw(id);
    }

    public void a(String paraName, String paraPath, String paraDate, int paraState, String version, int type) {
        if (type == DownFileType.Js.Value()) {
            ai.f("js", paraName, ai.bmi, "1");
        } else if (type == DownFileType.Skin.Value()) {
            SkinItem item = new SkinItem(paraName, paraPath, paraDate, paraState, version);
            ah.KZ().gb(paraPath);
        } else if (type == DownFileType.Wood.Value()) {
            WoodItem item2 = new WoodItem(paraName, paraPath, paraDate, paraState, version);
            ah.KZ().gc(paraPath);
        }
    }

    public void a(Activity activity, com.MCWorld.data.map.f.a item, String src) {
        if (item != null) {
            k.KJ().b(activity, item, src);
        }
    }

    public void e(Activity activity) {
        com.MCWorld.k.g(activity);
    }

    public void f(Activity activity) {
        com.MCWorld.k.Y(activity);
    }
}
