package com.huluxia.ui.profile;

import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.http.profile.c;

class ProfileOtherFragment$10 implements CommonMenuDialogListener {
    final /* synthetic */ ProfileOtherFragment bgR;

    ProfileOtherFragment$10(ProfileOtherFragment this$0) {
        this.bgR = this$0;
    }

    public void pressMenuById(int inIndex, Object object) {
        boolean z = false;
        switch (inIndex) {
            case 0:
                ProfileOtherFragment.i(this.bgR).setClickable(false);
                c f = ProfileOtherFragment.f(this.bgR);
                if (!ProfileOtherFragment.g(this.bgR)) {
                    z = true;
                }
                f.K(z);
                ProfileOtherFragment.f(this.bgR).execute();
                ProfileOtherFragment.a(this.bgR, true);
                ProfileOtherFragment.j(this.bgR).dismissDialog();
                return;
            default:
                return;
        }
    }
}
