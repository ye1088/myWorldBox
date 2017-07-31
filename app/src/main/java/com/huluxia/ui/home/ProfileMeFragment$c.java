package com.huluxia.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class ProfileMeFragment$c extends BroadcastReceiver {
    final /* synthetic */ ProfileMeFragment aSu;

    private ProfileMeFragment$c(ProfileMeFragment profileMeFragment) {
        this.aSu = profileMeFragment;
    }

    public void onReceive(Context context, Intent intent) {
        ProfileMeFragment.a(this.aSu);
    }
}
