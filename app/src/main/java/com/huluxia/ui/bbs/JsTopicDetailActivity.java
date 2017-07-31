package com.huluxia.ui.bbs;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.b;
import com.huluxia.data.map.MapItem;
import com.huluxia.data.map.f;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.h;
import com.huluxia.i;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.DownFileType;
import hlx.data.localstore.a;

public class JsTopicDetailActivity extends ResTopicDetailActivity {
    private final String aKs = a.bJY;
    private OnClickListener aKt = new OnClickListener(this) {
        final /* synthetic */ JsTopicDetailActivity aKu;

        {
            this.aKu = this$0;
        }

        public void onClick(View v) {
            if (this.aKu.aMX != null) {
                h cb = b.bq().br();
                i mapCb = b.bq().bs();
                if (cb != null && mapCb != null) {
                    String jsPath = j.cT(true) + this.aKu.aMX.name + a.bJY;
                    if (j.isExist(jsPath)) {
                        mapCb.a(this.aKu.aMX.name, jsPath, this.aKu.aMX.createTime, 1, this.aKu.aMX.version, DownFileType.Js.ordinal());
                        cb.a(this.aKu.aMn, this.aKu.aMX.version, this.aKu.FX());
                        return;
                    }
                    MapItem item = f.a.convertMapItem(this.aKu.aMX);
                    if (j.eT(j.eN(this.aKu.aMX.name)) && j.aa(this.aKu.aMX.name, this.aKu.aMX.md5)) {
                        mapCb.a(this.aKu.aMn, item, DownFileType.Js.Value());
                    } else {
                        mapCb.a(this.aKu.aMn, item, DownFileType.Js.Value());
                    }
                }
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aMn = this;
    }

    protected boolean a(f.a info) {
        if (j.isExist(j.cT(true) + info.name + a.bJY)) {
            return true;
        }
        return false;
    }

    protected String FT() {
        return "JS";
    }

    protected String FU() {
        return "JS投稿";
    }

    protected String FV() {
        return HttpMgr.getInstance().getGameDownloadPath(DownFileType.Js).getAbsolutePath();
    }

    protected int FW() {
        return com.huluxia.controller.resource.factory.b.a.ny;
    }

    protected int FX() {
        return 2;
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
}
