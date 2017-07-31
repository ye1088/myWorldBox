package com.huluxia.controller.resource.handler.impl;

import android.support.annotation.y;
import com.huluxia.controller.c;
import com.huluxia.controller.resource.bean.ResTaskInfo.State;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.utils.bc;
import com.huluxia.utils.j;
import hlx.ui.localresmgr.cache.a;
import java.io.File;

public class McWoodHandler extends McMapHandler {
    private static final String TAG = "McWoodHandler";

    public McWoodHandler(q info) {
        super(info);
    }

    protected void doUnzip(String zipPath) {
        try {
            if (bc.gC(zipPath) == 1) {
                bc.av(zipPath, zipPath + ".tmp.dec");
                File f = new File(zipPath);
                File fbak = new File(zipPath + ".orig.bak.xxx");
                f.renameTo(fbak);
                new File(zipPath + ".tmp.dec").renameTo(new File(zipPath));
                fbak.delete();
            }
        } catch (Exception e) {
        }
        ((q) getInfo()).state = State.SUCC.ordinal();
        EventNotifyCenter.notifyEvent(c.class, 262, new Object[]{((q) getInfo()).url});
    }

    public void onDownloadComplete(Object response, @y DownloadRecord record) {
        super.onDownloadComplete(response, record);
        String _tmpWoodPackId = "";
        String zipPath = j.Kv() + ((q) getInfo()).filename;
        try {
            _tmpWoodPackId = a.Un().hD(zipPath);
        } catch (Exception e) {
            _tmpWoodPackId = "";
            e.printStackTrace();
        }
        if (_tmpWoodPackId != null && _tmpWoodPackId.length() > 1) {
            String unzipPath = j.Ks() + _tmpWoodPackId;
            if (((q) getInfo()).version.contains(hlx.data.localstore.a.bJy)) {
                hlx.ui.localresmgr.cache.c.Uw().aK(zipPath, unzipPath);
            }
        }
    }
}
