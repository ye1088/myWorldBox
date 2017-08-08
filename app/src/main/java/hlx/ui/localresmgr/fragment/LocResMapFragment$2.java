package hlx.ui.localresmgr.fragment;

import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;

class LocResMapFragment$2 extends CallbackHandler {
    final /* synthetic */ LocResMapFragment caO;

    LocResMapFragment$2(LocResMapFragment this$0) {
        this.caO = this$0;
    }

    @MessageHandler(message = 262)
    public void onRecvOptions() {
    }

    @MessageHandler(message = 263)
    public void onRecvLevelData(boolean succ, Object object) {
        if (LocResMapFragment.a(this.caO).isShowing()) {
            LocResMapFragment.a(this.caO).cancel();
        }
        if (succ) {
            this.caO.UJ();
        }
    }
}
