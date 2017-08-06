package com.huluxia.widget.dialog;

import android.app.Activity;
import com.huluxia.controller.download.handler.impl.UpdateDownloadHandler;
import com.huluxia.controller.resource.ResourceCtrl;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.t;
import com.huluxia.utils.UtilsFile;

/* compiled from: UpdateDialog */
public class r extends g {
    private String bwj;
    private Activity bxl;
    private String mUrl;

    public r(Activity context, String msg, String url, String apkName) {
        super(context, null);
        this.bxl = context;
        this.mUrl = url;
        this.bwj = apkName;
        if (!(this.bxl == null || this.bxl.isFinishing())) {
            show();
        }
        init(msg);
        setCancelable(false);
    }

    private void init(String msg) {
        az("版本更新", msg);
        u("下次再说", "浏览器下载", "更新");
    }

    protected void Oq() {
        super.Oq();
        t.q(this.bxl, this.mUrl);
    }

    protected void sK() {
        super.sK();
        UtilsFile.deleteFile(UtilsFile.get_mctool_path() + this.bwj);
        ResourceCtrl.getInstance().registerHandler(1000005, UpdateDownloadHandler.class);
        ResTaskInfo tInfo = new ResTaskInfo();
        tInfo.url = this.mUrl;
        tInfo.filename = this.bwj;
        tInfo.mM = 1000005;
        tInfo.mV_fileName = this.bwj;
        tInfo.dir = UtilsFile.get_mctool_path();
        tInfo.na = true;
        tInfo.nb = false;
        ResourceCtrl.getInstance().addTask(tInfo);
        t.show_toast(this.bxl, "后台下载中..");
    }
}
