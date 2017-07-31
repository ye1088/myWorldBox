package hlx.mcfairy.messgenerator;

import android.os.Handler;
import android.os.Message;
import com.huluxia.mcinterface.c;
import com.huluxia.mcinterface.f;
import com.huluxia.mcinterface.h;
import hlx.mcfairy.i;
import java.util.List;

/* compiled from: MessGenerator */
public class a {
    protected Handler Vo = new Handler(this) {
        final /* synthetic */ a bUh;

        {
            this.bUh = this$0;
        }

        public void handleMessage(Message msg) {
            if (this.bUh.bUb) {
                switch (msg.what) {
                    case 0:
                        this.bUh.tick();
                        this.bUh.Vo.sendEmptyMessageDelayed(0, 1000);
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private int apL = -1;
    private a bUa;
    private boolean bUb = false;
    private f bUc = new f(this) {
        final /* synthetic */ a bUh;

        {
            this.bUh = this$0;
        }

        public void zm() {
            this.bUh.bUb = true;
            if (this.bUh.bUa != null) {
                this.bUh.bUa.a(new i("enterLiveMap"));
            }
            this.bUh.Vo.sendEmptyMessageDelayed(0, 1000);
        }

        public void qN() {
            this.bUh.bUb = false;
        }

        public void sd() {
            if (this.bUh.bUa != null) {
                this.bUh.bUa.a(new i("loadNetMap"));
            }
        }

        public void b(int id, int dmg, int x, int y, int z) {
            if (this.bUh.bUa != null) {
                this.bUh.bUa.a(new i("addItem", id, dmg));
            }
        }

        public void c(int id, int dmg, int x, int y, int z) {
            if (this.bUh.bUa != null) {
                this.bUh.bUa.a(new i("delItem", id, dmg));
            }
        }

        public void M(int activeAttack, int victim) {
            if (this.bUh.bUa != null) {
                this.bUh.bUa.a(new i("attackAnimal", victim));
            }
        }

        public void k(int x, int y, int z, int type, int data) {
        }

        public void N(int attacker, int victim) {
        }

        public void c(int foodPoints, float saturationRatio) {
            if (this.bUh.bUa != null) {
                this.bUh.bUa.a(new i("eatFood"));
            }
        }

        public void a(long entity, float x, float y, float z, float radius, boolean onFire) {
        }

        public void f(int x, int y, int z, int side) {
        }
    };
    private boolean bUd = true;
    private int bUe = -1;
    private boolean bUf = true;
    private List<c> bUg;

    public a(a cb) {
        this.bUa = cb;
    }

    public void Ti() {
        this.Vo.sendEmptyMessageDelayed(0, 1000);
        h.zM().a(this.bUc);
    }

    public void Tj() {
        recycle();
        h.zM().a(null);
    }

    public void recycle() {
        this.bUb = false;
        this.Vo.removeMessages(0);
    }

    private boolean Tk() {
        boolean tmpFlag = false;
        if (!this.bUb || !this.bUd) {
            return 0;
        }
        int tmpTime = h.wx();
        if (!(this.apL == -1 || 13000 > tmpTime || this.bUa == null)) {
            this.bUa.a(new i("becomeNight"));
            tmpFlag = true;
            this.bUd = false;
        }
        this.apL = tmpTime;
        return tmpFlag;
    }

    private boolean Tl() {
        boolean tmpFlag = false;
        if (!this.bUb || !this.bUf) {
            return 0;
        }
        if (h.getGameType() == 0) {
            int tmpBloodValue = h.zp();
            if (!(this.bUe == -1 || tmpBloodValue >= this.bUe || this.bUa == null)) {
                this.bUa.a(new i("lostFirstBlood"));
                tmpFlag = true;
                this.bUf = false;
            }
            this.bUe = tmpBloodValue;
        }
        return tmpFlag;
    }

    private void Tm() {
        if (!this.bUb) {
        }
    }

    private boolean Tn() {
        return !this.bUb ? false : false;
    }

    private boolean To() {
        return false;
    }

    private void tick() {
        if (Tk() || !Tl()) {
        }
    }
}
