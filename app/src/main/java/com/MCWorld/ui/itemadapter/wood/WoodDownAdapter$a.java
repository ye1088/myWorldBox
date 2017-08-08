package com.MCWorld.ui.itemadapter.wood;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.MCWorld.data.map.f.a;
import com.MCWorld.framework.R;
import com.MCWorld.r;
import com.MCWorld.ui.itemadapter.map.DownAdapter$b;
import com.MCWorld.utils.aq;
import com.MCWorld.utils.i;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.DownFileType;
import hlx.launch.game.d;

class WoodDownAdapter$a implements OnClickListener {
    private DownAdapter$b aTj;
    final /* synthetic */ WoodDownAdapter aXE;
    private a info;

    public WoodDownAdapter$a(WoodDownAdapter woodDownAdapter, DownAdapter$b paraholder, a parainfo) {
        this.aXE = woodDownAdapter;
        this.aTj = paraholder;
        this.info = parainfo;
    }

    public void onClick(View arg0) {
        String zipPath = j.Kv() + this.info.name + ".zip";
        if (j.eT(j.eP(this.info.name)) && j.ac(this.info.name, this.info.md5)) {
            WoodDownAdapter.a(this.aXE, this.info.name, zipPath, this.info.createTime, 1, aq.gk(this.info.version));
            d.f(this.aXE.aTg, this.info.version, 4);
            r.ck().K_umengEvent(hlx.data.tongji.a.bMC);
            return;
        }
        int color;
        Resources resources = this.aXE.aTg.getResources();
        TextView textView = this.aTj.aTB;
        if (com.simple.colorful.d.isDayMode()) {
            color = resources.getColor(R.color.studio_text_color);
        } else {
            color = resources.getColor(R.color.item_resource_tab_default_night);
        }
        textView.setTextColor(color);
        this.aTj.aTB.setText("请稍候");
        i.Ko().a(this.aXE.aTg, a.convertMapItem(this.info), DownFileType.Wood.Value());
        arg0.setClickable(false);
    }
}
