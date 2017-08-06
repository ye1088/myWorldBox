package com.huluxia.ui.itemadapter.skin;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.data.map.MapItem;
import com.huluxia.data.map.f.a;
import com.huluxia.framework.R;
import com.huluxia.r;
import com.huluxia.ui.itemadapter.map.DownAdapter$b;
import com.huluxia.utils.aq;
import com.huluxia.utils.i;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.DownFileType;
import hlx.launch.game.d;

class SkinDownAdapter$a implements OnClickListener {
    private DownAdapter$b aTj;
    final /* synthetic */ SkinDownAdapter aWx;
    private a info;

    public SkinDownAdapter$a(SkinDownAdapter skinDownAdapter, DownAdapter$b paraholder, a parainfo) {
        this.aWx = skinDownAdapter;
        this.aTj = paraholder;
        this.info = parainfo;
    }

    public void onClick(View arg0) {
        String jsPath = j.cU(true) + this.info.name + hlx.data.localstore.a.bKa;
        if (j.isExist(jsPath)) {
            SkinDownAdapter.a(this.aWx, this.info.name, jsPath, this.info.createTime, 1, aq.gk(this.info.version));
            d.f(this.aWx.aTg, this.info.version, 3);
            r.ck().K_umengEvent(hlx.data.tongji.a.bMD);
            return;
        }
        MapItem item = a.convertMapItem(this.info);
        if (j.eT(j.eO(this.info.name)) && j.ab(this.info.name, this.info.md5)) {
            i.Ko().a(this.aWx.aTg, item, DownFileType.Skin.Value());
            return;
        }
        int color;
        Resources resources = this.aWx.aTg.getResources();
        TextView textView = this.aTj.aTB;
        if (com.simple.colorful.d.isDayMode()) {
            color = resources.getColor(R.color.studio_text_color);
        } else {
            color = resources.getColor(R.color.item_resource_tab_default_night);
        }
        textView.setTextColor(color);
        this.aTj.aTB.setText("请稍候");
        i.Ko().a(this.aWx.aTg, item, DownFileType.Skin.Value());
        arg0.setClickable(false);
    }
}
