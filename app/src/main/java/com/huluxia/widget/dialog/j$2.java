package com.huluxia.widget.dialog;

import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;

/* compiled from: HPopupWindow */
class j$2 implements OnDismissListener {
    final /* synthetic */ j bxn;
    final /* synthetic */ LayoutParams bxo;

    j$2(j this$0, LayoutParams layoutParams) {
        this.bxn = this$0;
        this.bxo = layoutParams;
    }

    public void onDismiss() {
        this.bxo.alpha = 1.0f;
        j.c(this.bxn).getWindow().setAttributes(this.bxo);
    }
}
