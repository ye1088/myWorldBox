package com.huluxia.ui.itemadapter.map;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.data.map.MapItem;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.r;
import com.huluxia.utils.i;
import com.huluxia.utils.j;
import com.huluxia.utils.o;
import com.huluxia.widget.Constants.DownFileType;
import com.huluxia.widget.dialog.g;
import hlx.launch.game.d;

class DownAdapter$a implements OnClickListener {
    private DownAdapter$b aTj;
    final /* synthetic */ DownAdapter aTp;
    private com.huluxia.data.map.f.a info;

    private class a implements com.huluxia.widget.dialog.g.a {
        private View aTs;
        final /* synthetic */ DownAdapter$a aTt;

        public a(DownAdapter$a downAdapter$a, View arg0) {
            this.aTt = downAdapter$a;
            this.aTs = arg0;
        }

        public void ra() {
        }

        public void rb() {
        }

        public void rc() {
        }

        public void rd() {
            this.aTt.k(this.aTs);
        }
    }

    public DownAdapter$a(DownAdapter downAdapter, DownAdapter$b paraholder, com.huluxia.data.map.f.a parainfo) {
        this.aTp = downAdapter;
        this.aTj = paraholder;
        this.info = parainfo;
    }

    public void k(View arg0) {
        boolean isMapExist = this.aTp.a(this.info);
        if (j.KH() == 740110001) {
            g dia = new g(DownAdapter.a(this.aTp), null);
            dia.az(hlx.data.localstore.a.bKA, DownAdapter.a(this.aTp).getResources().getString(R.string.TipMCOldMap));
            dia.u(null, null, hlx.data.localstore.a.bKC);
        } else if (isMapExist) {
            HLog.info(DownAdapter.TAG, "map exist in game info = " + this.info, new Object[0]);
            d.f(DownAdapter.a(this.aTp), this.info.version, 1);
            r.ck().K(hlx.data.tongji.a.bMA);
        } else {
            MapItem item = com.huluxia.data.map.f.a.convertMapItem(this.info);
            if (j.eT(j.eM(this.info.name)) && j.Z(this.info.name, this.info.md5)) {
                i.Ko().a(DownAdapter.a(this.aTp), item, DownFileType.defaultType.Value());
                return;
            }
            int color;
            Resources resources = DownAdapter.a(this.aTp).getResources();
            TextView textView = this.aTj.aTB;
            if (DownAdapter.b(this.aTp)) {
                color = resources.getColor(R.color.studio_text_color);
            } else {
                color = resources.getColor(R.color.item_resource_tab_default_night);
            }
            textView.setTextColor(color);
            this.aTj.aTB.setText("请稍候");
            i.Ko().a(DownAdapter.a(this.aTp), item, DownFileType.defaultType.Value());
            arg0.setClickable(false);
            if (!this.aTp.aMW.equals("default")) {
                r.ck().O(this.aTp.aMW);
            }
        }
    }

    public void onClick(View arg0) {
        try {
            a(true, arg0, 629145600, 11, Integer.parseInt(this.info.mapSize), 2097152);
        } catch (Exception e) {
        }
    }

    private void a(Activity inputContext, View arg0, String inputTitile, String inputMsg, String inputCancel, String inputConfirm) {
        g dia = new g(inputContext, new a(this, arg0));
        dia.az(inputTitile, inputMsg);
        dia.u(inputCancel, null, inputConfirm);
        dia.showDialog();
    }

    private boolean a(boolean bChecked, View arg0, int nNeedMemory, int nNeedSysVersion, int nInputParam1, int nInputParam2) {
        if (true != bChecked || nInputParam1 < nInputParam2) {
            k(arg0);
        } else {
            if (0 == DownAdapter.HB()) {
                DownAdapter.bm(o.v(DownAdapter.a(this.aTp)));
            }
            if (DownAdapter.HB() < ((long) nNeedMemory)) {
                a(DownAdapter.a(this.aTp), arg0, hlx.data.localstore.a.bKA, "您的机器内存太小,此地图可能会出现卡顿,是否要继续下载", hlx.data.localstore.a.bKB, "继续");
            } else if (VERSION.SDK_INT < nNeedSysVersion) {
                a(DownAdapter.a(this.aTp), arg0, hlx.data.localstore.a.bKA, "您的机器系统版本太低,此地图可能会出现卡顿,是否要继续下载", hlx.data.localstore.a.bKB, "继续");
            } else {
                k(arg0);
            }
        }
        return true;
    }
}
