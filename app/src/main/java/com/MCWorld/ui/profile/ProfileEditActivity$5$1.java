package com.MCWorld.ui.profile;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
.5;
import com.MCWorld.widget.dialog.d;

class ProfileEditActivity$5$1 implements OnClickListener {
    final /* synthetic */ d aYR;
    final /* synthetic */ 5 bfS;
    final /* synthetic */ AlertDialog val$dialog;

    ProfileEditActivity$5$1(5 this$1, AlertDialog alertDialog, d dVar) {
        this.bfS = this$1;
        this.val$dialog = alertDialog;
        this.aYR = dVar;
    }

    public void onClick(View arg0) {
        this.val$dialog.dismiss();
        ProfileEditActivity.d(this.bfS.bfR).setBirthday(this.aYR.getDate().getTime());
        ProfileEditActivity.g(this.bfS.bfR).setText(ProfileEditActivity.h(this.bfS.bfR).format(this.aYR.getDate()));
    }
}
