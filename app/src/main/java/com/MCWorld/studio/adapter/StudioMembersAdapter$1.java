package com.MCWorld.studio.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.profile.e.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.MCWorld.framework.base.widget.dialog.DialogManager.StyleHolder;
import com.MCWorld.module.z;
import com.simple.colorful.d;

class StudioMembersAdapter$1 implements OnClickListener {
    final /* synthetic */ StudioMembersAdapter aFU;

    StudioMembersAdapter$1(StudioMembersAdapter this$0) {
        this.aFU = this$0;
    }

    public void onClick(View v) {
        long id = (long) v.getId();
        final a studioUser = (a) v.getTag();
        if (studioUser != null && studioUser.user != null) {
            if (id == 2131559899) {
                if (2 != studioUser.isStudio) {
                    z.DO().b(studioUser.sid, studioUser.user.userID, studioUser);
                } else {
                    z.DO().c(studioUser.sid, studioUser.user.userID, studioUser);
                }
            } else if (id == 2131559900) {
                DialogManager manager = new DialogManager(this.aFU.aFQ);
                StyleHolder styleHolder = new StyleHolder();
                styleHolder.colorButton = d.getColor(this.aFU.aFQ, R.attr.textColorQuaternary);
                styleHolder.colorMessage = d.getColor(this.aFU.aFQ, 16842808);
                manager.showOkCancelDialog(hlx.data.localstore.a.bKA_TIPS, String.format("工作室转让后给%s,你将无法再控制这个工作室，你确定要转让吗？", new Object[]{studioUser.user.nick}), "转让", hlx.data.localstore.a.bKB_bt_cancel, true, new OkCancelDialogListener(this) {
                    final /* synthetic */ StudioMembersAdapter$1 aFV;

                    public void onCancel() {
                    }

                    public void onOk() {
                        z.DO().c(studioUser.sid, studioUser.user.userID);
                    }
                }, styleHolder);
            } else if (id == 2131559898) {
                z.DO();
                z.a(studioUser.sid, studioUser.user.getUserID(), studioUser);
            }
        }
    }
}
