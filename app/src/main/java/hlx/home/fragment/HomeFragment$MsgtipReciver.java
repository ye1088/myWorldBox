package hlx.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

protected class HomeFragment$MsgtipReciver extends BroadcastReceiver {
    final /* synthetic */ HomeFragment bQl;

    protected HomeFragment$MsgtipReciver(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.bQl.Fr();
    }
}
