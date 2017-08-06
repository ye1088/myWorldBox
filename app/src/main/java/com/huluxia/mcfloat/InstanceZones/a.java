package com.huluxia.mcfloat.InstanceZones;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import com.huluxia.mcfloat.InstanceZones.config.b;
import com.huluxia.mcfloat.InstanceZones.config.c;
import com.huluxia.mcfloat.q;
import com.huluxia.mcinterface.h;
import com.huluxia.mctool.e;
import com.huluxia.r;
import hlx.utils.d;

@SuppressLint({"HandlerLeak"})
/* compiled from: InsZoneManager */
public class a {
    private static final int VW = 0;
    private static final int VX = 1;
    private static a Wi;
    private Activity VY;
    private boolean VZ = false;
    private Handler Vo = new Handler(this) {
        final /* synthetic */ a Wj;

        {
            this.Wj = this$0;
        }

        public void handleMessage(Message msg) {
            if (this.Wj.VZ) {
                switch (msg.what) {
                    case 0:
                        if (!this.Wj.Wa.sy()) {
                            this.Wj.sg();
                        } else if (this.Wj.Wa.sz()) {
                            this.Wj.si();
                        } else {
                            this.Wj.sh();
                        }
                        this.Wj.dK(this.Wj.Wa.sx());
                        if (this.Wj.VZ && !this.Wj.We) {
                            if (this.Wj.Wa.su() != 4 || h.vK() <= 0) {
                                this.Wj.Wa.ss();
                            }
                            this.Wj.Vo.sendEmptyMessageDelayed(0, 1000);
                        }
                        this.Wj.We = false;
                        return;
                    case 1:
                        if (h.vK() > 0) {
                            this.Wj.Vo.sendEmptyMessageDelayed(1, 1000);
                            return;
                        } else {
                            this.Wj.Vo.sendEmptyMessageDelayed(0, 1000);
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    };
    private b Wa;
    private b Wb;
    private int Wc;
    private c Wd;
    private boolean We = false;
    private com.huluxia.mcfloat.InstanceZones.f.a Wf = new com.huluxia.mcfloat.InstanceZones.f.a(this) {
        final /* synthetic */ a Wj;

        {
            this.Wj = this$0;
        }

        public void sm() {
            this.Wj.aL(false);
        }
    };
    private f Wg = new f(this.Wf);
    private g Wh;

    public void dJ(int insZoneMode) {
        if (!this.VZ) {
            q.aJ(false);
            if (hlx.mcfairy.b.SU() != null) {
                hlx.mcfairy.b.SU().dB(true);
            }
            this.VY = (Activity) h.zu().get();
            this.VZ = true;
            this.Wa = new b();
            this.Wa.dL(insZoneMode);
            this.Wc = 1;
            this.Wb = d.a(insZoneMode, this.VY);
            if (this.Wb != null) {
                this.Wd = this.Wb.g(this.Wc, this.Wd);
            }
            d.a(insZoneMode, this.Wd);
            this.Wg.aC(true);
            d.a(insZoneMode, this.Wd, this.Wa, this.Wg);
            sj();
            this.Vo.sendEmptyMessage(0);
        }
    }

    private void sg() {
        d.a(this.Wa.sn(), this.Wd, this.Wa.st(), this.Wa.su() + this.Wa.sw());
        d.a(this.Wa, this.Wg);
        if (this.Wa.su() == 1 && this.Wh != null) {
            this.Wh.dismiss();
        }
    }

    private void sh() {
        boolean _isChallengeSuc = d.a(this.Wa, this.Wd, this.Wa.sv(), this.Wg);
        if (d.a(this.Wa)) {
            hlx.ui.a.n(this.VY, "抱歉，您已离开范围，小心跑到火星去哟!");
            aL(false);
        } else if (_isChallengeSuc) {
            si();
        }
        d.a(this.Wa, this.Wg, this.Wd, this.Wa.sw());
    }

    private void dK(int missionTimer) {
        if (this.Wd != null && this.Wa.sn() != 5 && this.Wa.sn() != 6 && this.Wd.ty() != null && this.Wa.sp() < this.Wd.ty().size() && missionTimer == ((com.huluxia.mcfloat.InstanceZones.structrue.a) this.Wd.ty().get(this.Wa.sp())).YP) {
            h.cW(((com.huluxia.mcfloat.InstanceZones.structrue.a) this.Wd.ty().get(this.Wa.sp())).YQ);
            this.Wa.so();
        }
    }

    private void si() {
        if (!d.dM(this.Wa.sn())) {
            aM(false);
            if (this.Wa.sn() == 2) {
                this.We = true;
                d.b(this.Wa.sn(), this.Wd, this.Wa, this.Wg);
                this.Vo.sendEmptyMessageDelayed(1, 1000);
                hlx.ui.a.n(this.VY, "时间结束了，8秒后将再次挑战...");
                return;
            }
            hlx.ui.a.n(this.VY, "很遗憾，时间到了，请再次挑战吧！");
            aL(false);
        } else if (d.a(this.Wb, this.Wa.sn(), this.Wc)) {
            if (this.Wa.sn() == 2) {
                aM(true);
            }
            this.Wc++;
            this.Wd = this.Wb.g(this.Wc, this.Wd);
            this.We = true;
            d.b(this.Wa.sn(), this.Wd, this.Wa, this.Wg);
            this.Vo.sendEmptyMessageDelayed(1, 1000);
            hlx.ui.a.o(this.VY, "继续挑战更强大的生物吧！");
        } else {
            String _tmpChallengeAllSuc;
            switch (this.Wa.sn()) {
                case 1:
                    _tmpChallengeAllSuc = "恭喜您！挑战成功！";
                    break;
                case 2:
                    _tmpChallengeAllSuc = "训练营挑战结束了，恭喜您！";
                    break;
                case 3:
                    _tmpChallengeAllSuc = String.format("恭喜您！挑战成功！耗时%S", new Object[]{d.nR(this.Wa.sv())});
                    e.Dk().iK(1);
                    break;
                case 4:
                case 5:
                    _tmpChallengeAllSuc = String.format("恭喜您！挑战成功！耗时%S", new Object[]{d.nR(this.Wa.sv())});
                    break;
                case 6:
                    _tmpChallengeAllSuc = String.format("恭喜您！挑战成功！耗时%S", new Object[]{d.nR(this.Wa.sv())});
                    break;
                case 7:
                    _tmpChallengeAllSuc = String.format("恭喜您！挑战成功！耗时%S", new Object[]{d.nR(this.Wa.sv())});
                    break;
                default:
                    _tmpChallengeAllSuc = "恭喜您！挑战成功！";
                    break;
            }
            hlx.ui.a.o(this.VY, _tmpChallengeAllSuc);
            aM(true);
            sk();
            aL(false);
        }
    }

    public void aL(boolean isNeedShowScore) {
        if (this.VZ) {
            if (isNeedShowScore) {
                aM(false);
            }
            d.c(this.Wa.sn(), this.Wd);
            this.Wg.aC(false);
            q.aK(true);
            this.VZ = false;
            this.Vo.removeMessages(0);
            this.Vo.removeMessages(1);
        }
        if (hlx.mcfairy.b.SU() != null) {
            hlx.mcfairy.b.SU().dB(false);
        }
    }

    private void aM(boolean isChalengSuccess) {
        if (this.Wa.sn() != 5 && this.Wa.sn() != 6 && this.Wa.sn() != 7) {
            if (this.Wh == null) {
                this.Wh = new g(this.VY, null, this.Wa.sn());
            }
            this.Wh.a(this.Wa.sn(), this.Wd, isChalengSuccess, this.Wc);
        } else if (isChalengSuccess) {
            if (this.Wh == null) {
                this.Wh = new g(this.VY, null, this.Wa.sn());
            }
            this.Wh.a(this.Wa.sn(), isChalengSuccess, this.Wa.sv());
        }
    }

    private void sj() {
        String _tmpTongjiStr = null;
        switch (this.Wa.sn()) {
            case 1:
                _tmpTongjiStr = com.huluxia.r.a.kC;
                break;
            case 2:
                _tmpTongjiStr = com.huluxia.r.a.kE;
                break;
            case 3:
                _tmpTongjiStr = com.huluxia.r.a.kG;
                break;
            case 4:
                _tmpTongjiStr = com.huluxia.r.a.kI;
                break;
            case 5:
                _tmpTongjiStr = com.huluxia.r.a.kK;
                break;
            case 6:
                _tmpTongjiStr = com.huluxia.r.a.kM;
                break;
            case 7:
                _tmpTongjiStr = com.huluxia.r.a.kO;
                break;
        }
        if (_tmpTongjiStr != null) {
            r.ck().K_umengEvent(_tmpTongjiStr);
        }
    }

    private void sk() {
        String _tmpTongjiStr = null;
        switch (this.Wa.sn()) {
            case 1:
                _tmpTongjiStr = com.huluxia.r.a.kD;
                break;
            case 2:
                _tmpTongjiStr = com.huluxia.r.a.kF;
                break;
            case 3:
                _tmpTongjiStr = com.huluxia.r.a.kH;
                break;
            case 4:
                _tmpTongjiStr = com.huluxia.r.a.kJ;
                break;
            case 5:
                _tmpTongjiStr = com.huluxia.r.a.kL;
                break;
            case 6:
                _tmpTongjiStr = com.huluxia.r.a.kN;
                break;
            case 7:
                _tmpTongjiStr = com.huluxia.r.a.kP;
                break;
        }
        if (_tmpTongjiStr != null) {
            r.ck().K_umengEvent(_tmpTongjiStr);
        }
    }

    public static synchronized a sl() {
        a aVar;
        synchronized (a.class) {
            if (Wi == null) {
                Wi = new a();
            }
            aVar = Wi;
        }
        return aVar;
    }
}
