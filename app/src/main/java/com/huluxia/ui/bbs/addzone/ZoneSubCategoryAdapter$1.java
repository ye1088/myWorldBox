package com.huluxia.ui.bbs.addzone;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.j;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.module.topic.d;
import com.huluxia.module.topic.k;
import com.huluxia.t;
import hlx.data.localstore.a;

class ZoneSubCategoryAdapter$1 implements OnClickListener {
    final /* synthetic */ d aRc;
    final /* synthetic */ ZoneSubCategoryAdapter aRd;

    ZoneSubCategoryAdapter$1(ZoneSubCategoryAdapter this$0, d dVar) {
        this.aRd = this$0;
        this.aRc = dVar;
    }

    public void onClick(View v) {
        if (j.ep().ey()) {
            if (this.aRc.isSubscribe == 1) {
                k.Ej().i(this.aRc.categoryID, false);
                this.aRc.isSubscribe = 0;
            } else {
                k.Ej().i(this.aRc.categoryID, true);
                this.aRc.isSubscribe = 1;
            }
            this.aRd.notifyDataSetChanged();
            return;
        }
        final DialogManager dialogManager = new DialogManager(ZoneSubCategoryAdapter.a(this.aRd));
        dialogManager.showOkCancelDialog("需要登录以后才能进行操作", "登录", a.bKB_bt_cancel, new OkCancelDialogListener(this) {
            final /* synthetic */ ZoneSubCategoryAdapter$1 aRf;

            public void onCancel() {
                dialogManager.dismissDialog();
            }

            public void onOk() {
                t.an(ZoneSubCategoryAdapter.a(this.aRf.aRd));
            }
        });
    }
}
