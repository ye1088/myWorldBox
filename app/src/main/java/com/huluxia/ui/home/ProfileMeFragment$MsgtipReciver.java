package com.huluxia.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

protected class ProfileMeFragment$MsgtipReciver extends BroadcastReceiver {
    final /* synthetic */ ProfileMeFragment aSu;

    protected ProfileMeFragment$MsgtipReciver(ProfileMeFragment this$0) {
        this.aSu = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.aSu.Fr();
    }
}
