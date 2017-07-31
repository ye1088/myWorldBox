package com.huluxia.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class ProfileMeFragment$a extends BroadcastReceiver {
    final /* synthetic */ ProfileMeFragment aSu;

    private ProfileMeFragment$a(ProfileMeFragment profileMeFragment) {
        this.aSu = profileMeFragment;
    }

    public void onReceive(Context context, Intent intent) {
        ProfileMeFragment.j(this.aSu);
    }
}
