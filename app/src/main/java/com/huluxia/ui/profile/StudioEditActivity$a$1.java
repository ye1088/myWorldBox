package com.huluxia.ui.profile;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.t;
import com.huluxia.ui.profile.StudioEditActivity.a;
import com.huluxia.utils.y;
import com.simple.colorful.d;

class StudioEditActivity$a$1 implements OnClickListener {
    final /* synthetic */ a biO;

    StudioEditActivity$a$1(a this$0) {
        this.biO = this$0;
    }

    public void onClick(View v) {
        TextView tv = (TextView) v;
        hlx.module.resources.a item = (hlx.module.resources.a) v.getTag();
        if (a.a(this.biO).contains(Long.valueOf(item.cateid))) {
            tv.setTextColor(d.getColor(a.b(this.biO), 16842808));
            tv.setBackgroundDrawable(d.o(a.b(this.biO), c.studio_resource_type));
            a.a(this.biO).remove(Long.valueOf(item.cateid));
        } else if (4 <= a.a(this.biO).size()) {
            t.show_toast(a.b(this.biO), "最多可选4个");
        } else {
            Drawable drawable = y.c(a.b(this.biO), item.catename, UtilsScreen.dipToPx(a.b(this.biO), 40));
            tv.setTextColor(d.getColor(a.b(this.biO), c.colorNormalWhite));
            tv.setBackgroundDrawable(drawable);
            a.a(this.biO).add(Long.valueOf(item.cateid));
        }
    }
}
