package hlx.mcfairy;

import android.os.Handler;
import android.os.Message;

/* compiled from: FairyManager */
class b$1 extends Handler {
    final /* synthetic */ b bTz;

    b$1(b this$0) {
        this.bTz = this$0;
    }

    public void handleMessage(Message msg) {
        if (b.a(this.bTz)) {
            switch (msg.what) {
                case 1:
                    this.bTz.tick();
                    this.bTz.Vo.sendEmptyMessageDelayed(1, 500);
                    return;
                default:
                    return;
            }
        }
    }
}
