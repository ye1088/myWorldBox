package com.MCWorld.ui.profile;

import android.os.Handler;
import android.os.Message;

class ProfileOtherFragment$3 extends Handler {
    final /* synthetic */ ProfileOtherFragment bgR;

    ProfileOtherFragment$3(ProfileOtherFragment this$0) {
        this.bgR = this$0;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                ProfileOtherFragment.l(this.bgR).post(new Runnable(this) {
                    final /* synthetic */ ProfileOtherFragment$3 bgS;

                    {
                        this.bgS = this$1;
                    }

                    public void run() {
                        ProfileOtherFragment.l(this.bgS.bgR).smoothScrollBy(ProfileOtherFragment.k(this.bgS.bgR).getHeight(), 4000);
                    }
                });
                this.bgR.Vo.sendMessageDelayed(this.bgR.Vo.obtainMessage(2), 1500);
                return;
            case 2:
                ProfileOtherFragment.l(this.bgR).post(new Runnable(this) {
                    final /* synthetic */ ProfileOtherFragment$3 bgS;

                    {
                        this.bgS = this$1;
                    }

                    public void run() {
                        ProfileOtherFragment.l(this.bgS.bgR).smoothScrollBy(-ProfileOtherFragment.l(this.bgS.bgR).getHeight(), 4000);
                    }
                });
                return;
            default:
                return;
        }
    }
}
