package com.huluxia.utils;

import android.app.Activity;
import com.huluxia.service.j;
import com.huluxia.service.k;
import com.huluxia.widget.dialog.n;
import com.huluxia.widget.dialog.n.a;
import com.huluxia.widget.dialog.o;

/* compiled from: UtilsShare */
class au$3 implements a {
    final /* synthetic */ n aYQ;
    final /* synthetic */ Activity bki;
    final /* synthetic */ String bkl;
    final /* synthetic */ String bkm;
    final /* synthetic */ boolean bna;
    final /* synthetic */ String bnc;
    final /* synthetic */ String bnd;
    final /* synthetic */ long rI;

    au$3(boolean z, Activity activity, long j, String str, String str2, String str3, String str4, n nVar) {
        this.bna = z;
        this.bki = activity;
        this.rI = j;
        this.bnc = str;
        this.bkl = str2;
        this.bkm = str3;
        this.bnd = str4;
        this.aYQ = nVar;
    }

    public void a(o m) {
        switch (au$4.bkp[((UtilsMenu$MENU_SHARE_LIST) m.getTag()).ordinal()]) {
            case 1:
                if (this.bna) {
                    k.bc(this.bki).bi(this.rI);
                    au.e(this.rI, false);
                }
                k.bc(this.bki).b(this.bkl, aw.W(this.bkm, 30), this.bnd, au.O(this.bki, this.bnc), true);
                break;
            case 2:
                k.bc(this.bki).EO();
                k.bc(this.bki).b(this.bkl, aw.W(this.bkm, 30), this.bnd, au.O(this.bki, this.bnc), false);
                break;
            case 3:
                if (this.bna) {
                    au.e(this.rI, false);
                }
                j.m(this.bki).a(this.rI, this.bkl, aw.W(this.bkm, 30), this.bnc, this.bnd);
                break;
            case 4:
                j.m(this.bki).e(this.bkl, aw.W(this.bkm, 30), this.bnc, this.bnd);
                break;
        }
        this.aYQ.dismiss();
    }
}
