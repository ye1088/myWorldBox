package com.MCWorld.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.R;

/* compiled from: EditDialog */
class e$1 implements OnClickListener {
    final /* synthetic */ e bwM;

    e$1(e this$0) {
        this.bwM = this$0;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                if (!(e.a(this.bwM) == null || e.a(this.bwM).isFinishing())) {
                    e.b(this.bwM).dismiss();
                }
                if (e.c(this.bwM) != null) {
                    e.c(this.bwM).rb();
                    return;
                }
                return;
            case R.id.tv_confirm:
                if (!(e.a(this.bwM) == null || e.a(this.bwM).isFinishing())) {
                    e.b(this.bwM).dismiss();
                }
                if (e.c(this.bwM) != null) {
                    e.c(this.bwM).cf(e.d(this.bwM).getText().toString());
                    return;
                }
                return;
            default:
                return;
        }
    }
}
