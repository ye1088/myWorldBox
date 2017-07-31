package com.huluxia.ui.mctool;

import android.content.Context;
import com.huluxia.controller.c;
import com.huluxia.data.js.JsItem;
import com.huluxia.data.map.MapItem;
import com.huluxia.data.skin.SkinItem;
import com.huluxia.data.wood.WoodItem;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;
import com.huluxia.utils.ah;
import com.huluxia.utils.ai;
import com.huluxia.utils.aq;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.DownFileType;
import hlx.data.localstore.a;

/* compiled from: MapDownloadInterceptor */
public class b {
    private CallbackHandler bbl;
    private MapItem bbm;
    private Context context;
    private int fileType;

    public b(Context context, MapItem item, int fileType) {
        this.fileType = fileType;
        this.bbm = item;
        this.context = context.getApplicationContext();
        cR(true);
    }

    void cR(boolean bAdd) {
        this.bbl = new 1(this);
        if (bAdd) {
            EventNotifyCenter.add(c.class, this.bbl);
        } else {
            EventNotifyCenter.remove(this.bbl);
        }
    }

    public static void a(Context context, MapItem item, int fileType) {
        b bVar = new b(context, item, fileType);
    }

    private void a(String name, String path, String createTime, String version, long map_id, long post_id, String img_url, String downUrl) {
        ai.a(new WoodItem(name, path, createTime, 1, version, map_id, post_id, img_url), downUrl, path);
        ah.KZ().gc(path);
    }

    private void b(String name, String path, String date, String ver, long map_id, long post_id, String img_url) {
        ai.b(new SkinItem(name, path, date, 1, ver, map_id, post_id, img_url));
        ah.KZ().gb(j.cU(true) + name + a.bKa);
    }

    private void c(String name, String path, String date, String ver, long map_id, long post_id, String img_url) {
        ai.b(new JsItem(name, path, date, 1, ver, map_id, post_id, img_url));
    }

    private void d(String name, String path, String date, String ver, long map_id, long post_id, String img_url) {
        ai.p(name, ai.imgUrl, img_url);
        ai.p(name, ai.bmh, String.valueOf(map_id));
        ai.p(name, ai.bmg, String.valueOf(post_id));
        ah.KZ().am(j.Kq() + name, date);
    }

    private void Iz() {
        String zipPath;
        String filePath;
        if (this.fileType == DownFileType.Skin.Value()) {
            zipPath = j.Ku() + this.bbm.name + ".zip";
            b(this.bbm.name, zipPath, this.bbm.creatTime, aq.gk(this.bbm.version), this.bbm.id, this.bbm.postid, this.bbm.photo);
            filePath = j.cU(true) + this.bbm.name + a.bKa;
            EventNotifyCenter.notifyEvent(n.class, n.axd, new Object[]{Boolean.valueOf(true), filePath, this.bbm});
        } else if (this.fileType == DownFileType.Wood.Value()) {
            zipPath = j.Kv() + this.bbm.name + ".zip";
            a(this.bbm.name, zipPath, this.bbm.creatTime, aq.gk(this.bbm.version), this.bbm.id, this.bbm.postid, this.bbm.photo, this.bbm.url);
            filePath = j.Kv() + this.bbm.name + ".zip";
            EventNotifyCenter.notifyEvent(n.class, n.axc, new Object[]{Boolean.valueOf(true), filePath, this.bbm});
        } else if (this.fileType == DownFileType.Js.Value()) {
            zipPath = j.Kt() + this.bbm.name + ".zip";
            c(this.bbm.name, zipPath, this.bbm.creatTime, aq.at(this.bbm.version, this.bbm.subVersion), this.bbm.id, this.bbm.postid, this.bbm.photo);
            filePath = j.cT(true) + this.bbm.name + a.bJY;
            EventNotifyCenter.notifyEvent(n.class, n.axb, new Object[]{Boolean.valueOf(true), filePath, this.bbm});
        } else {
            zipPath = j.cV(true) + this.bbm.name + ".zip";
            d(this.bbm.name, zipPath, this.bbm.creatTime, aq.gk(this.bbm.version), this.bbm.id, this.bbm.postid, this.bbm.photo);
            filePath = j.Kq() + this.bbm.name;
            EventNotifyCenter.notifyEvent(n.class, n.axa, new Object[]{Boolean.valueOf(true), filePath, this.bbm, null});
        }
    }
}
