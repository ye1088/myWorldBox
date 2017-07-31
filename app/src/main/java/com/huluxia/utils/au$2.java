package com.huluxia.utils;

import android.app.Activity;
import com.huluxia.module.GameInfo;
import com.huluxia.service.j;
import com.huluxia.service.k;
import com.huluxia.widget.dialog.n;
import com.huluxia.widget.dialog.n.a;
import com.huluxia.widget.dialog.o;

/* compiled from: UtilsShare */
class au$2 implements a {
    final /* synthetic */ n aYQ;
    final /* synthetic */ Activity bki;
    final /* synthetic */ boolean bna;
    final /* synthetic */ GameInfo bnb;

    au$2(boolean z, Activity activity, GameInfo gameInfo, n nVar) {
        this.bna = z;
        this.bki = activity;
        this.bnb = gameInfo;
        this.aYQ = nVar;
    }

    public void a(o m) {
        switch (au$4.bkp[((UtilsMenu$MENU_SHARE_LIST) m.getTag()).ordinal()]) {
            case 1:
                if (this.bna) {
                    k.bc(this.bki).bi(this.bnb.appid);
                    au.e(this.bnb.appid, false);
                }
                k.bc(this.bki).b(this.bnb.getAppTitle(), aw.W(this.bnb.appdesc, 30), this.bnb.shareurl, au.O(this.bki, this.bnb.applogo), true);
                break;
            case 2:
                k.bc(this.bki).EO();
                k.bc(this.bki).b(this.bnb.getAppTitle(), aw.W(this.bnb.appdesc, 30), this.bnb.shareurl, au.O(this.bki, this.bnb.applogo), false);
                break;
            case 3:
                if (this.bna) {
                    au.e(this.bnb.appid, false);
                }
                j.m(this.bki).a(this.bnb.appid, this.bnb.getAppTitle(), aw.W(this.bnb.appdesc, 30), this.bnb.applogo, this.bnb.shareurl);
                break;
            case 4:
                j.m(this.bki).e(this.bnb.getAppTitle(), aw.W(this.bnb.appdesc, 30), this.bnb.applogo, this.bnb.shareurl);
                break;
        }
        this.aYQ.dismiss();
    }
}
