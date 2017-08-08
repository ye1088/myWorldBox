package com.MCWorld.ui.home;

import android.os.Handler;
import android.os.Message;

class ProfileMeFragment$7 extends Handler {
    final /* synthetic */ ProfileMeFragment aSu;

    ProfileMeFragment$7(ProfileMeFragment this$0) {
        this.aSu = this$0;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                if (!ProfileMeFragment.k(this.aSu)) {
                    int height = ProfileMeFragment.l(this.aSu).getHeight();
                    if (ProfileMeFragment.m(this.aSu)) {
                        ProfileMeFragment.l(this.aSu).post(new Runnable(this) {
                            final /* synthetic */ ProfileMeFragment$7 aSv;

                            {
                                this.aSv = this$1;
                            }

                            public void run() {
                                ProfileMeFragment.l(this.aSv.aSu).smoothScrollBy(ProfileMeFragment.n(this.aSv.aSu).getHeight(), 4000);
                            }
                        });
                        if (!ProfileMeFragment.k(this.aSu)) {
                            this.aSu.Vo.sendMessageDelayed(this.aSu.Vo.obtainMessage(2), 1500);
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            case 2:
                if (!ProfileMeFragment.k(this.aSu)) {
                    ProfileMeFragment.l(this.aSu).post(new Runnable(this) {
                        final /* synthetic */ ProfileMeFragment$7 aSv;

                        {
                            this.aSv = this$1;
                        }

                        public void run() {
                            ProfileMeFragment.l(this.aSv.aSu).smoothScrollBy(-ProfileMeFragment.l(this.aSv.aSu).getHeight(), 4000);
                        }
                    });
                    return;
                }
                return;
            default:
                return;
        }
    }
}
