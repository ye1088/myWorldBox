package com.MCWorld.ui.itemadapter.profile;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.profile.d$a;
import com.MCWorld.t;

class AuditAdapter$1 implements OnClickListener {
    final /* synthetic */ d$a aVh;
    final /* synthetic */ AuditAdapter aVi;

    AuditAdapter$1(AuditAdapter this$0, d$a com_huluxia_data_profile_d_a) {
        this.aVi = this$0;
        this.aVh = com_huluxia_data_profile_d_a;
    }

    public void onClick(View v) {
        t.a(this.aVi.aVg, this.aVh.getUserid(), null);
    }
}
