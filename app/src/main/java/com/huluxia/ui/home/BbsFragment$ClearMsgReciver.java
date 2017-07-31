package com.huluxia.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

protected class BbsFragment$ClearMsgReciver extends BroadcastReceiver {
    final /* synthetic */ BbsFragment aSb;

    protected BbsFragment$ClearMsgReciver(BbsFragment this$0) {
        this.aSb = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.aSb.Fs();
    }
}
