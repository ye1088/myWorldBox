package com.huluxia.ui.itemadapter.js;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.data.map.MapItem;
import com.huluxia.data.map.f.a;
import com.huluxia.framework.R;
import com.huluxia.r;
import com.huluxia.ui.itemadapter.map.DownAdapter$b;
import com.huluxia.utils.ai;
import com.huluxia.utils.i;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.DownFileType;
import hlx.launch.game.d;

class JsDownAdapter$a implements OnClickListener {
    final /* synthetic */ JsDownAdapter aTi;
    private DownAdapter$b aTj;
    private a info;

    public JsDownAdapter$a(JsDownAdapter jsDownAdapter, DownAdapter$b paraholder, a parainfo) {
        this.aTi = jsDownAdapter;
        this.aTj = paraholder;
        this.info = parainfo;
    }

    public void onClick(View arg0) {
        if (j.isExist(j.cT(true) + this.info.name + hlx.data.localstore.a.bJY)) {
            ai.f("js", this.info.name, ai.bmi, "1");
            d.f(this.aTi.aTg, this.info.version, 2);
            r.ck().K_umengEvent(hlx.data.tongji.a.bMB);
            return;
        }
        MapItem item = a.convertMapItem(this.info);
        if (j.eT(j.eN(this.info.name)) && j.aa(this.info.name, this.info.md5)) {
            i.Ko().a(this.aTi.aTg, item, DownFileType.Js.Value());
            return;
        }
        int color;
        Resources resources = this.aTi.aTg.getResources();
        TextView textView = this.aTj.aTB;
        if (com.simple.colorful.d.isDayMode()) {
            color = resources.getColor(R.color.studio_text_color);
        } else {
            color = resources.getColor(R.color.item_resource_tab_default_night);
        }
        textView.setTextColor(color);
        this.aTj.aTB.setText("请稍候");
        i.Ko().a(this.aTi.aTg, item, DownFileType.Js.Value());
        arg0.setClickable(false);
    }
}
