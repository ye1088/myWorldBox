package com.MCWorld.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

protected class BbsFragment$MsgtipReciver extends BroadcastReceiver {
    final /* synthetic */ BbsFragment aSb;

    protected BbsFragment$MsgtipReciver(BbsFragment this$0) {
        this.aSb = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.aSb.Fr();
    }
}
