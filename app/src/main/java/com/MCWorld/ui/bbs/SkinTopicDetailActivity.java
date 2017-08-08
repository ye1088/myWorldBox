package com.MCWorld.ui.bbs;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.b;
import com.MCWorld.data.map.MapItem;
import com.MCWorld.data.map.f;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.h;
import com.MCWorld.i;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.DownFileType;
import hlx.data.localstore.a;

public class SkinTopicDetailActivity extends ResTopicDetailActivity {
    private final String aKs = a.bKa;
    private OnClickListener aKt = new OnClickListener(this) {
        final /* synthetic */ SkinTopicDetailActivity aOL;

        {
            this.aOL = this$0;
        }

        public void onClick(View v) {
            if (this.aOL.aMX != null) {
                h cb = b.bq().br();
                i mapCb = b.bq().bs();
                if (cb != null && mapCb != null) {
                    String skinPath = j.cU(true) + this.aOL.aMX.name + a.bKa;
                    if (j.isExist(skinPath)) {
                        mapCb.a(this.aOL.aMX.name, skinPath, this.aOL.aMX.createTime, 1, this.aOL.aMX.version, DownFileType.Skin.ordinal());
                        cb.a(this.aOL.aMn, this.aOL.aMX.version, this.aOL.FX());
                        return;
                    }
                    MapItem item = f.a.convertMapItem(this.aOL.aMX);
                    if (j.eT(j.eO(this.aOL.aMX.name)) && j.ab(this.aOL.aMX.name, this.aOL.aMX.md5)) {
                        mapCb.a(this.aOL.aMn, item, DownFileType.Skin.Value());
                    } else {
                        mapCb.a(this.aOL.aMn, item, DownFileType.Skin.Value());
                    }
                }
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aMn = this;
    }

    protected String FT() {
        return "皮肤";
    }

    protected String FU() {
        return "皮肤投稿";
    }

    protected int FX() {
        return 3;
    }

    protected boolean a(f.a info) {
        if (j.isExist(j.cU(true) + info.name + a.bKa)) {
            return true;
        }
        return false;
    }

    protected String FV() {
        return HttpMgr.getInstance().getGameDownloadPath(DownFileType.Skin).getAbsolutePath();
    }

    protected int FW() {
        return com.MCWorld.controller.resource.factory.b.a.nA;
    }

    protected void FY() {
        this.aMI.setOnClickListener(this.aKt);
        this.aMw.setOnClickListener(this.aKt);
        if (this.aNg == 0) {
            this.aMq.getDownButton().setOnClickListener(this.aKt);
        } else if (this.aMx.getDownButton() != null) {
            this.aMx.getDownButton().setOnClickListener(this.aKt);
        }
    }

    public void FZ() {
        super.FZ();
        this.aMx.getDownButton().setOnClickListener(this.aKt);
    }

    protected void GN() {
        i mapCb = b.bq().bs();
        if (mapCb != null && this.aMX == null) {
            mapCb.p((int) this.aMU);
            GL();
        }
    }
}
