package com.huluxia.ui.area.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

protected class ResourceNewsFragment$MsgTipReceiver extends BroadcastReceiver {
    final /* synthetic */ ResourceNewsFragment aIi;

    protected ResourceNewsFragment$MsgTipReceiver(ResourceNewsFragment this$0) {
        this.aIi = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.aIi.Fr();
    }
}
