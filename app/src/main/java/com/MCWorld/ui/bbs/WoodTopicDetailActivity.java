package com.MCWorld.ui.bbs;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.b;
import com.MCWorld.data.map.f.a;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.h;
import com.MCWorld.i;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.DownFileType;

public class WoodTopicDetailActivity extends ResTopicDetailActivity {
    private final String aKs = ".zip";
    private OnClickListener aKt = new OnClickListener(this) {
        final /* synthetic */ WoodTopicDetailActivity aQJ;

        {
            this.aQJ = this$0;
        }

        public void onClick(View v) {
            if (this.aQJ.aMX != null) {
                h cb = b.bq().br();
                i mapCb = b.bq().bs();
                if (cb != null && mapCb != null) {
                    String woodPath = j.Kv() + this.aQJ.aMX.name + ".zip";
                    if (j.eT(woodPath)) {
                        mapCb.a(this.aQJ.aMX.name, woodPath, this.aQJ.aMX.createTime, 1, this.aQJ.aMX.version, DownFileType.Wood.ordinal());
                        cb.a(this.aQJ.aMn, this.aQJ.aMX.version, this.aQJ.FX());
                        return;
                    }
                    mapCb.a(this.aQJ.aMn, a.convertMapItem(this.aQJ.aMX), DownFileType.Wood.Value());
                }
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aMn = this;
    }

    protected String FT() {
        return "材质";
    }

    protected String FU() {
        return "材质投稿";
    }

    protected boolean a(a info) {
        if (j.eT(j.eP(info.name))) {
            return true;
        }
        return false;
    }

    protected String FV() {
        return HttpMgr.getInstance().getGameDownloadPath(DownFileType.Wood).getAbsolutePath();
    }

    protected int FW() {
        return com.MCWorld.controller.resource.factory.b.a.nz;
    }

    protected int FX() {
        return 4;
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
            mapCb.r((int) this.aMU);
            GL();
        }
    }
}
