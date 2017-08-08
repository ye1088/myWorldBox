package com.MCWorld.utils;

import android.app.Activity;
import com.MCWorld.t;
import com.MCWorld.widget.dialog.g;
import com.MCWorld.widget.dialog.g.a;
import com.MCWorld.widget.dialog.n;
import com.MCWorld.widget.dialog.o;
import java.util.ArrayList;

/* compiled from: UtilsUrl */
public class bb {
    private Activity bns;
    private String bnt;
    a bnu = new a(this) {
        final /* synthetic */ bb bnw;

        {
            this.bnw = this$0;
        }

        public void ra() {
        }

        public void rb() {
            t.show_toast(this.bnw.bns, "复制成功");
        }

        public void rc() {
        }

        public void rd() {
            t.q(this.bnw.bns, this.bnw.mUrl);
        }
    };
    private String mUrl;

    public bb(Activity context, String txt) {
        this.bns = context;
        this.bnt = txt;
    }

    public void MJ() {
        if (this.bnt != null && this.bnt.length() != 0) {
            ArrayList<String> urls = aw.gs(this.bnt);
            if (!urls.isEmpty()) {
                final n urlDialogMenu = UtilsMenu.a(this.bns, urls);
                urlDialogMenu.show();
                urlDialogMenu.a(new n.a(this) {
                    final /* synthetic */ bb bnw;

                    public void a(o m) {
                        this.bnw.mUrl = (String) m.getTag();
                        o.bV(this.bnw.mUrl);
                        urlDialogMenu.dismiss();
                        if (this.bnw.mUrl != null && this.bnw.mUrl.length() > 0) {
                            this.bnw.b(this.bnw.bns, this.bnw.mUrl);
                        }
                    }
                });
            }
        }
    }

    private void b(Activity activity, String url) {
        g dialog = new g(activity, this.bnu);
        dialog.az(null, "是否打开所复制的链接？");
        dialog.u(hlx.data.localstore.a.bKB_bt_cancel, null, hlx.data.localstore.a.bKC_bt_ok);
    }
}
