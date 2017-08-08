package hlx.mcfairy;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.mcfloat.q;
import com.MCWorld.mcinterface.h;
import hlx.mcfairy.messgenerator.a;
import hlx.mcfairy.messgenerator.a$a;

/* compiled from: FairyManager */
public class b implements a$a {
    private static b bTx;
    private static boolean bTy;
    protected Handler Vo = new 1(this);
    private f bTr = null;
    private boolean bTs = false;
    private i bTt;
    private a bTu;
    private int bTv;
    private int bTw;
    private Activity mContext = null;
    private boolean oi;

    public void a(i mess) {
        if (this.bTs) {
            this.bTt = mess;
        }
    }

    public static b SU() {
        return bTx;
    }

    public void dB(boolean pause) {
        this.oi = pause;
    }

    public static synchronized void aK(boolean isShowFairy) {
        synchronized (b.class) {
            if (bTx != null) {
                bTx.Vo.removeMessages(1);
                if (isShowFairy) {
                    bTx.bTu.Ti();
                    bTx.Vo.sendEmptyMessageDelayed(1, 500);
                    d.SY().SZ();
                } else {
                    bTx.bTu.Tj();
                }
            }
        }
    }

    public b(Activity aty, boolean isShow) {
        bTx = this;
        this.mContext = aty;
        this.bTv = 0;
        this.bTw = UtilsScreen.dipToPx(aty, 25);
        this.bTs = true;
        this.oi = false;
        this.Vo.sendEmptyMessageDelayed(1, 500);
        this.bTr = new f(aty);
        this.bTu = new a(this);
        if (isShow) {
            SV();
        }
    }

    public void SV() {
        this.bTu.Ti();
    }

    public void SW() {
        this.bTu.Tj();
    }

    public void recycle() {
        bTx = null;
        this.bTu.recycle();
    }

    public void tick() {
        if (this.bTt != null && !this.oi) {
            View logoView = q.sc();
            String tmp = d.SY().b(this.bTt);
            if (tmp != null) {
                this.bTr.a(tmp, logoView, 0, 0, 0);
                h.cW(tmp);
            }
            this.bTt = null;
        }
    }
}
