package com.huluxia.mcsdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.mcgame.ab;
import com.huluxia.mcgame.ae;
import com.huluxia.mcgame.b;
import com.huluxia.mcgame.d;
import com.huluxia.mcgame.g;
import com.huluxia.mcgame.l;
import com.huluxia.mcgame.m;
import com.huluxia.mcgame.n;
import com.huluxia.mcgame.options.DTGameOptions;
import com.huluxia.mcgame.p;
import com.huluxia.mcgame.s;
import com.huluxia.mcgame.t;
import com.huluxia.mcgame.u;
import com.huluxia.mcgame.v;
import com.huluxia.mcgame.w;
import com.huluxia.mcgame.x;
import com.huluxia.mcgame.y;
import com.huluxia.mcjavascript.f;
import com.huluxia.mcsdk.dtlib.h;
import com.huluxia.mcsdk.log.a;
import io.netty.handler.codec.http2.Http2CodecUtil;
import java.io.File;

/* compiled from: DTTickCallBack */
public class e {
    private static long UL = 0;
    private static final long UM = 200;
    private static long anW = 0;
    private static final long anX = 10;
    private static long anY = 0;
    private static final long anZ = 500;
    private static long aoa = 0;
    private static final long aob = 1000;
    private static long aoc = 0;
    private static final long aod = 30000;

    public static long CA() {
        return anW;
    }

    public static void ay(long nPerTickCallBack) {
        anW = nPerTickCallBack;
    }

    public static long rY() {
        return UL;
    }

    public static void T(long nPerMirTickCallBack) {
        UL = nPerMirTickCallBack;
    }

    public static long CB() {
        return anY;
    }

    public static void az(long nPerMirTickCallBack) {
        anY = nPerMirTickCallBack;
    }

    public static long CC() {
        return aoa;
    }

    public static void aA(long nPerSecondTickCallBack) {
        aoa = nPerSecondTickCallBack;
    }

    public static long CD() {
        return aoc;
    }

    public static void aB(long perMinuteTickCallBack) {
        aoc = perMinuteTickCallBack;
    }

    private static void CE() {
        CJ();
        if (g.wd() == 0 && g.vW() != 0) {
            if (g.we()) {
                DTGameCallBack.setLevelFakeCallback(true, DTSDKManagerEx.dtNativeLevelIsRemote());
            }
            f.callScriptMethod("modTick", new Object[0]);
        }
    }

    public static void CF() {
        long curTimeMillis = System.currentTimeMillis();
        CI();
        if (g.wc() == 1 && g.vW() != 0 && g.wd() == 0) {
            ab.yC();
            d.vh();
            l.wy();
            y.ye();
            x.xX();
            t.xu();
            if (curTimeMillis > CC() + 1000) {
                aA(curTimeMillis);
                l.wz();
                n.vH();
                n.wV();
            }
        }
    }

    public static void CG() {
        long curTimeMillis = System.currentTimeMillis();
        CI();
        if (g.wc() == 1 && g.vW() != 0 && g.wd() == 0) {
            ab.yC();
            d.vh();
            t.xu();
            if (curTimeMillis > rY() + UM) {
                T(curTimeMillis);
                if (g.wc() == 1) {
                    l.wy();
                    y.ye();
                    x.xX();
                    m.wF();
                    n.vH();
                }
            }
            if (curTimeMillis > CC() + 1000) {
                aA(curTimeMillis);
                a.verbose("TAG", "DTPrint 回调每1秒调用的函数 000 \n", new Object[0]);
            }
        }
    }

    public static void CH() {
        if (DTGameOptions.isbOpenFloatWindow()) {
            long curTimeMillis = System.currentTimeMillis();
            long rY = rY();
            long CC = CC();
            if (curTimeMillis > CA() + anX) {
                ay(curTimeMillis);
                CE();
                if (g.wf() > 0 && g.vW() != 0 && g.wc() == 1 && g.wd() == 0) {
                    t.xu();
                    b.uX();
                    d.vh();
                    s.xq();
                    ab.yC();
                    w.xO();
                    com.huluxia.mcgame.e.vH();
                    p.xi();
                    u.xF();
                    n.vH();
                    m.wF();
                    if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                        x.xX();
                        ae.yH();
                    }
                    if (h.CW().CX() == 3 || h.CW().CX() == 5) {
                        com.huluxia.mcfb.a.qf().qj();
                    }
                    if (h.CW().CX() == 5 || h.CW().CX() == 7) {
                        com.huluxia.mcgame.perspective.a.zd();
                    }
                }
            }
            if (curTimeMillis > rY() + UM) {
                T(curTimeMillis);
                if (g.wc() == 1 && g.vW() != 0 && g.wd() == 0) {
                    v.xK();
                    l.wy();
                    y.ye();
                    if (h.CW().CX() == 2) {
                        DTGameOptions.DTTickFunc_AdjustGameOption();
                    }
                }
            }
            if (curTimeMillis > CB() + 500) {
                az(curTimeMillis);
                if (g.wc() == 1 && g.vW() != 0 && g.wd() == 0) {
                    n.wV();
                    f.Ap();
                    com.huluxia.mcjsmanager.a.Ay();
                }
            }
            if (curTimeMillis > CC() + 1000) {
                aA(curTimeMillis);
                if (g.wc() == 1) {
                    l.wz();
                    m.wG();
                    if (h.CW().CX() == 2) {
                    }
                    if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                        com.huluxia.mcfb.a.qf().qk();
                        n.wW();
                    }
                }
            }
            if (curTimeMillis > CD() + 30000) {
                aB(curTimeMillis);
                com.huluxia.mcinterface.h.zM().fw(30);
            }
        }
    }

    public static void CI() {
        if (g.wc() == 0) {
            try {
                if (g.vW() == 1) {
                    t.xv();
                    t.gH(0);
                    g.gk(2);
                    m.wH();
                    g.gm(1);
                    com.huluxia.mcinterface.h.zM().cd(g.getWorldName());
                    g.gn(0);
                    g.gr(0);
                } else {
                    g.go(2);
                    g.cB(g.adQ);
                    g.cA(g.adR);
                    g.gl(0);
                    s.bt(false);
                    d.vi();
                    com.huluxia.mcinterface.h.zM().sd();
                    g.gn(1);
                }
                g.gm(1);
            } catch (Exception e) {
                g.gm(0);
            }
        }
    }

    public static void CJ() {
        if (g.wc() == 0) {
            try {
                if (g.vW() == 1) {
                    g.gk(2);
                    g.gl(0);
                    m.wH();
                    l.wA();
                    t.xv();
                    ae.gX(0);
                    int nSetBloodVal = 0;
                    if (com.huluxia.mcinterface.h.zx() == 0) {
                        nSetBloodVal = DTSDKManagerEx.io(DTSDKManagerEx.Cp());
                    } else if (com.huluxia.mcinterface.h.zx() == 1 || h.CW().CX() == 2) {
                        nSetBloodVal = DTSDKManagerEx.al(DTSDKManagerEx.Cq());
                    }
                    if (nSetBloodVal <= 0) {
                        s.bt(true);
                    } else {
                        s.bt(false);
                    }
                    d.vi();
                    p.reset();
                    t.gH(0);
                    com.huluxia.mcinterface.h.zM().cd(g.getWorldName());
                    g.gn(0);
                    g.gr(0);
                    x.gT(0);
                } else {
                    g.go(2);
                    g.cB(g.adQ);
                    g.cA(g.adR);
                    g.gl(0);
                    s.bt(false);
                    d.vi();
                    p.reset();
                    t.gH(0);
                    com.huluxia.mcinterface.h.zM().sd();
                    g.gn(0);
                    g.gk(2);
                    g.gr(1);
                }
                if (h.CW().CX() == 1) {
                    com.huluxia.mcinterface.h.bQ(true);
                }
                g.gm(1);
            } catch (Exception e) {
                g.gm(0);
            }
        }
    }

    public static String aW(Context context) {
        Signature[] mSignatures = context.getPackageManager().getPackageArchiveInfo(context.getPackageResourcePath(), 64).signatures;
        Signature apkSignature = mSignatures.length > 0 ? mSignatures[0] : null;
        if (apkSignature == null) {
            return "";
        }
        return UtilsMD5.getMD5String(apkSignature.toByteArray());
    }

    public static void v(Context context, String packName) {
        if (packName != null && packName.length() != 0) {
            try {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(packName);
                intent.setFlags(268435456);
                context.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    public static void w(Context context, String strApkFile) {
        if (strApkFile != null && strApkFile.length() != 0 && new File(strApkFile).exists()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setDataAndType(Uri.fromFile(new File(strApkFile)), b.a(new short[]{(short) 0, (short) 225, (short) 241, (short) 242, (short) 239, (short) 237, (short) 230, (short) 231, (short) 243, (short) 225, (short) 230, (short) 228, (short) 164, (short) 250, (short) 227, (short) 234, (short) 161, (short) 241, Http2CodecUtil.MAX_UNSIGNED_BYTE, (short) 246, (short) 225, (short) 251, (short) 252, (short) 242, (short) 185, (short) 232, (short) 248, (short) 249, (short) 240, (short) 253, (short) 250, (short) 251, (short) 178, (short) 193, (short) 211, (short) 193, (short) 203, (short) 205, (short) 211, (short) 195}));
            context.startActivity(intent);
        }
    }

    public static boolean x(Context context, String uri) {
        try {
            context.getPackageManager().getPackageInfo(uri, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
