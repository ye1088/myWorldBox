package com.MCWorld.ui.itemadapter.profile;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.profile.d$a;
import com.MCWorld.module.z;
import com.MCWorld.ui.itemadapter.profile.AuditAdapter.AuditReslut;

class AuditAdapter$2 implements OnClickListener {
    final /* synthetic */ d$a aVh;
    final /* synthetic */ AuditAdapter aVi;
    final /* synthetic */ AuditAdapter$a aVj;
    final /* synthetic */ int val$position;

    AuditAdapter$2(AuditAdapter this$0, AuditAdapter$a auditAdapter$a, d$a com_huluxia_data_profile_d_a, int i) {
        this.aVi = this$0;
        this.aVj = auditAdapter$a;
        this.aVh = com_huluxia_data_profile_d_a;
        this.val$position = i;
    }

    public void onClick(View v) {
        this.aVi.aVg.cn(true);
        this.aVj.aVm.setEnabled(false);
        this.aVj.aVn.setEnabled(false);
        z.DO();
        z.b((long) this.aVh.getId(), AuditReslut.AGREE.getResult(), this.val$position, this.aVh.getSid());
    }
}
