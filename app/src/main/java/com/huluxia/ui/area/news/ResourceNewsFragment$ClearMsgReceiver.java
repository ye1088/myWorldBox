package com.huluxia.ui.area.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

protected class ResourceNewsFragment$ClearMsgReceiver extends BroadcastReceiver {
    final /* synthetic */ ResourceNewsFragment aIi;

    protected ResourceNewsFragment$ClearMsgReceiver(ResourceNewsFragment this$0) {
        this.aIi = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.aIi.Fs();
    }
}
