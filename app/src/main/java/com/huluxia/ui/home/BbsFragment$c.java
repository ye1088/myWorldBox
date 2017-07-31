package com.huluxia.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class BbsFragment$c extends BroadcastReceiver {
    final /* synthetic */ BbsFragment aSb;

    private BbsFragment$c(BbsFragment bbsFragment) {
        this.aSb = bbsFragment;
    }

    public void onReceive(Context context, Intent intent) {
        this.aSb.Gy();
    }
}
