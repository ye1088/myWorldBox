package com.huluxia.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class ProfileMeFragment$b extends BroadcastReceiver {
    final /* synthetic */ ProfileMeFragment aSu;

    private ProfileMeFragment$b(ProfileMeFragment profileMeFragment) {
        this.aSu = profileMeFragment;
    }

    public void onReceive(Context context, Intent intent) {
        ProfileMeFragment.i(this.aSu).FC();
        ProfileMeFragment.f(this.aSu).logout();
        ProfileMeFragment.a(this.aSu, null);
        ProfileMeFragment.g(this.aSu).g(ProfileMeFragment.e(this.aSu));
        ProfileMeFragment.h(this.aSu);
    }
}
