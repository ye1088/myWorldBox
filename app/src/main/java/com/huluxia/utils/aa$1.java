package com.huluxia.utils;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: UtilsEndlessListScrollListener */
class aa$1 implements OnClickListener {
    final /* synthetic */ aa blK;

    aa$1(aa this$0) {
        this.blK = this$0;
    }

    public void onClick(View v) {
        boolean error = aa.a(this.blK);
        aa.a(this.blK, false);
        if (aa.b(this.blK) != null) {
            aa.b(this.blK).removeView(aa.c(this.blK));
        }
        if (aa.d(this.blK) != null) {
            aa.d(this.blK).removeFooterView(aa.c(this.blK));
        }
        if (aa.e(this.blK) != null) {
            aa.e(this.blK).setVisibility(8);
        }
        if (aa.f(this.blK) != null && aa.g(this.blK) && !aa.h(this.blK) && !aa.a(this.blK)) {
            if (error || aa.f(this.blK).shouldLoadData()) {
                this.blK.onLoading();
                aa.f(this.blK).onLoadData();
            }
        }
    }
}
