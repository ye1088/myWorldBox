package com.MCWorld.ui.profile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

protected class ProfileOtherFragment$ClearMsgReciver extends BroadcastReceiver {
    final /* synthetic */ ProfileOtherFragment bgR;

    protected ProfileOtherFragment$ClearMsgReciver(ProfileOtherFragment this$0) {
        this.bgR = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.bgR.Fs();
    }
}
