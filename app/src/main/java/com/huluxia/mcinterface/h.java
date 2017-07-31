package com.huluxia.mcinterface;

import android.content.Context;
import android.graphics.Bitmap;
import com.huluxia.aa.mcspirit;
import com.huluxia.mcfb.a;
import com.huluxia.mcfb.b;
import com.huluxia.mcfb.c;
import com.huluxia.mcfb.d;
import com.huluxia.mcgame.ab;
import com.huluxia.mcgame.ad;
import com.huluxia.mcgame.ae;
import com.huluxia.mcgame.g;
import com.huluxia.mcgame.k;
import com.huluxia.mcgame.l;
import com.huluxia.mcgame.m;
import com.huluxia.mcgame.n;
import com.huluxia.mcgame.options.DTGameOptions;
import com.huluxia.mcgame.p;
import com.huluxia.mcgame.r;
import com.huluxia.mcgame.t;
import com.huluxia.mcgame.u;
import com.huluxia.mcgame.v;
import com.huluxia.mcgame.w;
import com.huluxia.mcgame.x;
import com.huluxia.mcgame.y;
import com.huluxia.mcgame.z;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.mojang.minecraftpe.MainActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: MCInterface */
public class h {
    private static h aji = null;
    private a ajj = null;
    private g ajk = null;
    private g ajl = null;
    private d ajm = null;
    private b ajn = null;
    private l ajo = null;
    private f ajp = null;

    public static boolean wn() {
        return g.wn();
    }

    public static void bj(boolean inputIsOnlineMode) {
        g.bj(inputIsOnlineMode);
    }

    public static void gg(int mconlineMode) {
        g.gg(mconlineMode);
    }

    public static void u(int inputWide, int inputHigh, int inputTimeSeconds) {
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 3 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 5 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 7) {
            a.qf().u(inputWide, inputHigh);
            x.gT(0);
        }
    }

    public static void gs(int inputHlxMinecraftMode) {
        g.gs(inputHlxMinecraftMode);
    }

    public static void k(ArrayList<b> inFBMonsterList) {
        a.qf().h(inFBMonsterList);
    }

    public static void l(ArrayList<b> inFBMonsterList) {
        a.qf().g(inFBMonsterList);
    }

    public static ArrayList<c> qs() {
        d.qq();
        return d.qs();
    }

    public static void b(int inputRolePosX, int inputRolePosY, int inputRolePosZ, int inputCorner01PosX, int inputCorner01PosY, int inputCorner01PosZ, int inputCorner02PosX, int inputCorner02PosY, int inputCorner02PosZ, int inputCorner03PosX, int inputCorner03PosY, int inputCorner03PosZ, int inputCorner04PosX, int inputCorner04PosY, int inputCorner04PosZ, int inputCornerGap) {
        a.qf().a(inputRolePosX, inputRolePosY, inputRolePosZ, inputCorner01PosX, inputCorner01PosY, inputCorner01PosZ, inputCorner02PosX, inputCorner02PosY, inputCorner02PosZ, inputCorner03PosX, inputCorner03PosY, inputCorner03PosZ, inputCorner04PosX, inputCorner04PosY, inputCorner04PosZ, inputCornerGap);
    }

    public static boolean qg() {
        return a.qf().qg();
    }

    public static boolean qh() {
        return a.qf().qh();
    }

    public static void cW(String inputString) {
        a.qf().q(inputString, "五神");
    }

    public static void qi() {
        d.qq();
        d.qr();
        a.qf().qi();
    }

    public static void bc(boolean input) {
        g.bc(input);
    }

    public static void O(int inCurHealth, int inMaxHealth) {
        long _tmpBiologicalId = DTSDKManagerEx.Cq();
        DTSDKManagerEx.j(_tmpBiologicalId, inMaxHealth);
        DTSDKManagerEx.i(_tmpBiologicalId, inCurHealth);
    }

    public static int wk() {
        return g.wk();
    }

    public static void bK(boolean inputClearFlag) {
        g.bi(inputClearFlag);
    }

    public static void t(Context in_context, String in_soPath) {
        System.loadLibrary("gnustl_shared");
        System.loadLibrary("mcpelauncher_tinysubstrate");
        com.huluxia.mcso.a.init(in_context);
        com.huluxia.mcso.a.load(in_soPath);
    }

    public static void m(byte[] inputByteArray, int inputDecodeLen) {
        com.huluxia.mcso.a.n(inputByteArray, inputDecodeLen);
    }

    public static void d(int mode, float level) {
        DTSDKManagerEx.f(mode, level);
    }

    public static void v(int inputId, int inputTime, int inputLevel) {
        DTSDKManagerEx.z(inputId, inputTime, inputLevel);
    }

    public static void hm(int inputId) {
        DTSDKManagerEx.ih(inputId);
    }

    public static void a(boolean bEnabled, List<k> list) {
        g.aY(bEnabled);
        g.E(list);
    }

    public static void zn() {
        int tmpLevel = x.yd();
        if (tmpLevel > 0) {
            x.gP(tmpLevel);
        }
    }

    public static void gP(int level) {
        x.gP(level);
    }

    public static int zo() {
        return x.yb();
    }

    public static void bL(boolean enable) {
        g.aZ(enable);
    }

    public static void bM(boolean enable) {
        g.ba(enable);
    }

    public static void bN(boolean enable) {
        g.bb(enable);
    }

    public static void e(int val, boolean lock) {
        v.d(val, lock);
    }

    public static int zp() {
        return v.xJ();
    }

    public static void hn(int nEnabled) {
        DTGameOptions.setbOpenFloatWindow(nEnabled != 0);
    }

    public static boolean zq() {
        return DTGameOptions.isbOpenFloatWindow();
    }

    public static int zr() {
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 0 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 1) {
            return 16;
        }
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 2 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 3) {
            return 26;
        }
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 5) {
            return 27;
        }
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 7) {
            return 34;
        }
        return 0;
    }

    public static void setmGameGUISize(float size) {
        DTGameOptions.setmGameGUISize(size);
    }

    public static void ho(int val) {
        com.huluxia.mcgame.options.a.hh(val);
    }

    public static void cX(String name) {
        g.cD(name);
    }

    public static String zs() {
        return g.getWorldDir();
    }

    public static String zt() {
        return g.getWorldName();
    }

    public static void bO(boolean bEnabled) {
        com.huluxia.mcsdk.c.Ce();
        r.bs(bEnabled);
    }

    public static void bP(boolean flag) {
        ad.bH(flag);
    }

    public static boolean g(String name, String ip, String port) {
        return ad.d(name, ip, port);
    }

    public static void yE() {
        ad.yE();
    }

    public static WeakReference<MainActivity> zu() {
        return MainActivity.getMainActivity();
    }

    public static boolean zv() {
        return false;
    }

    public static void bQ(boolean needReStart) {
        g.bg(needReStart);
    }

    public static int xH() {
        return u.xH();
    }

    public static void gK(int option) {
        u.gK(option);
    }

    public static int zw() {
        return com.huluxia.mcsdk.dtlib.h.CW().CZ();
    }

    public static void hp(int inputVal) {
        com.huluxia.mcsdk.dtlib.h.CW().iH(inputVal);
    }

    public static int zx() {
        return com.huluxia.mcsdk.dtlib.h.CW().CX();
    }

    public static void hq(int mode) {
        com.huluxia.mcsdk.dtlib.h.CW().iF(mode);
    }

    public static void aW(boolean val) {
        g.aW(val);
    }

    public static boolean vM() {
        return g.vM();
    }

    public static void zy() {
        n.wR();
    }

    public static ArrayList<i> wQ() {
        return n.wQ();
    }

    public static boolean P(int id, int count) {
        return n.J(id, count);
    }

    public static boolean c(float pos_x, float pos_y, float pos_z, int biological_id, int inputCount, String inputName, int inputMaxHealth, int inputHat, int inputCoat, int inputTrousers, int inputShoes, int inputWeapon) {
        return n.a(pos_x, pos_y, pos_z, biological_id, inputCount, inputName, inputMaxHealth, inputHat, inputCoat, inputTrousers, inputShoes, inputWeapon);
    }

    public static boolean a(int biological_id, int inputCount, String inputName, int inputMaxHealth, int inputHat, int inputCoat, int inputTrousers, int inputShoes, int inputWeapon) {
        return c(y.gU(0), y.gU(1), y.gU(2), biological_id, inputCount, inputName, inputMaxHealth, inputHat, inputCoat, inputTrousers, inputShoes, inputWeapon);
    }

    public static void Q(int id, int distance) {
        p.K(id, distance);
    }

    public static void D(List<j> list) {
        com.mojang.minecraftpe.a.bHY.D(list);
    }

    public static boolean C(List<j> list) {
        return com.mojang.minecraftpe.a.bHY.C(list);
    }

    public static boolean B(List<j> list) {
        return com.mojang.minecraftpe.a.bHY.B(list);
    }

    public boolean ge(int inputGameType) {
        return com.mojang.minecraftpe.a.bHY.ge(inputGameType);
    }

    public static int vK() {
        return com.mojang.minecraftpe.a.bHY.vK();
    }

    public static void hr(int speed) {
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 0 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 1) {
            if (speed == 0) {
                mcspirit.b(false);
            } else {
                mcspirit.b(true);
            }
        } else if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 2 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 3 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 5 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 7) {
            if (speed == 0) {
                ae.gX(0);
            } else {
                ae.gX(1);
            }
        }
        com.huluxia.mcsdk.c.Ch();
    }

    public static boolean xw() {
        return t.xw();
    }

    public static void zz() {
        w.gM(0);
    }

    public static void e(int mode, int x, int y, int z) {
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() != 1 && com.huluxia.mcsdk.dtlib.h.CW().CX() != 2 && com.huluxia.mcsdk.dtlib.h.CW().CX() != 3 && com.huluxia.mcsdk.dtlib.h.CW().CX() != 5 && com.huluxia.mcsdk.dtlib.h.CW().CX() != 7) {
            return;
        }
        if (x != 0 || y != 0 || z != 0) {
            DTSDKManagerEx.K(x, y, z);
        }
    }

    public static ArrayList<c> zA() {
        return com.huluxia.mcjsmanager.b.zA();
    }

    public static int zB() {
        return com.huluxia.mcjsmanager.b.AB();
    }

    public static boolean zC() {
        return ab.a(true, 0, 0);
    }

    public static Bitmap z(String inputFullPathName, int inputFlag) {
        return ab.v(inputFullPathName, inputFlag);
    }

    public static boolean zD() {
        return true;
    }

    public static void A(String filePath, String so_path) {
        com.huluxia.mcgame.h.wo().s(filePath, so_path);
    }

    public static boolean cY(String path) {
        return com.huluxia.mcgame.h.wo().cH(path);
    }

    public static boolean cZ(String fileName) {
        return com.huluxia.mcgame.h.cI(fileName);
    }

    public static void da(String filePath) {
        z.cP(filePath);
    }

    public static void db(String filePath) {
        k.cM(filePath);
    }

    public static boolean vN() {
        return g.vN();
    }

    public static void dc(String pJsFullPathName) {
        g.aX(true);
        com.huluxia.mcjavascript.d.a(pJsFullPathName, 0, 0, 0);
    }

    public static void zE() {
        com.huluxia.mcjsmanager.c.init();
    }

    public static float zF() {
        return y.gU(0);
    }

    public static float zG() {
        return y.gU(1);
    }

    public static float zH() {
        return y.gU(2);
    }

    public static float zI() {
        return y.gV(0);
    }

    public static float zJ() {
        return y.gV(1);
    }

    public static float zK() {
        return y.gV(2);
    }

    public static boolean b(float x, float y, float z) {
        return y.a(x, y, z);
    }

    public static void vj() {
        com.huluxia.mcgame.d.vj();
    }

    public static int[] va() {
        return com.huluxia.mcgame.b.va();
    }

    public static boolean c(int[] array) {
        return com.huluxia.mcgame.b.c(array);
    }

    public static boolean z(List<c> list) {
        return com.huluxia.mcgame.d.z(list);
    }

    public static boolean A(List<c> list) {
        return com.huluxia.mcgame.d.A(list);
    }

    public static boolean R(int bagIndex, int itemCnt) {
        com.huluxia.mcsdk.c.Ce();
        return com.huluxia.mcgame.d.H(bagIndex, itemCnt);
    }

    public static boolean d(int in_ItemId, int in_ItemDmg, int in_ItemCnt, int[] enchantAttr, int[] enchantLevel) {
        return com.huluxia.mcgame.d.b(in_ItemId, in_ItemDmg, in_ItemCnt, enchantAttr, enchantLevel);
    }

    public static boolean w(int id, int count, int gameBagIndex) {
        return com.huluxia.mcgame.d.r(id, count, gameBagIndex);
    }

    public static boolean f(Set<Integer> bagIndexSet) {
        return com.huluxia.mcgame.d.d(bagIndexSet);
    }

    public static boolean F(List<c> list) {
        return com.huluxia.mcgame.d.x(list);
    }

    public static boolean x(int id, int count, int dmg) {
        com.huluxia.mcsdk.c.Cf();
        return com.huluxia.mcgame.d.p(id, count, dmg);
    }

    public static int wx() {
        return l.wx();
    }

    public static boolean hs(int paramInt) {
        l.gt(paramInt);
        return true;
    }

    public static int getGameType() {
        return m.getGameType();
    }

    public static boolean ht(int paramInt) {
        m.setGameType(paramInt);
        return true;
    }

    public static boolean zL() {
        return t.xt();
    }

    public static boolean bR(boolean bEnable) {
        t.bu(bEnable);
        return true;
    }

    public static void bv(boolean bEnable) {
        t.bv(bEnable);
    }

    public static void e(boolean bEnable, boolean bUpdate) {
        com.huluxia.mcgame.perspective.a.e(bEnable, bUpdate);
    }

    public static void c(String path, boolean bEnable) {
        com.huluxia.mcjavascript.d.b(path, 0, bEnable);
        com.huluxia.mcsdk.c.Ch();
    }

    public static void bS(boolean bEnable) {
        com.huluxia.mcjavascript.d.c(3, 1, bEnable);
    }

    public static void bT(boolean bEnable) {
        com.huluxia.mcjavascript.d.c(1, 1, bEnable);
    }

    public static void bU(boolean bEnable) {
        if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 2 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 3 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 5 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 7) {
            mcspirit.xx(bEnable);
        }
    }

    public static synchronized h zM() {
        h hVar;
        synchronized (h.class) {
            if (aji == null) {
                aji = new h();
            }
            hVar = aji;
        }
        return hVar;
    }

    public a zN() {
        return this.ajj;
    }

    public void a(a callback) {
        this.ajj = callback;
    }

    public g zO() {
        return this.ajk;
    }

    public void a(g callback) {
        this.ajk = callback;
    }

    public void b(g callback) {
        this.ajl = callback;
    }

    public d zP() {
        return this.ajm;
    }

    public void a(d callback) {
        this.ajm = callback;
    }

    public b zQ() {
        return this.ajn;
    }

    public void a(b callback) {
        this.ajn = callback;
    }

    public l zR() {
        return this.ajo;
    }

    public void a(l callback) {
        this.ajo = callback;
    }

    public void se() {
        if (this.ajo != null) {
            this.ajo.se();
        }
    }

    public void sf() {
        if (this.ajo != null) {
            this.ajo.sf();
        }
    }

    public void sd() {
        if (this.ajk != null) {
            this.ajk.sd();
        }
        if (this.ajp != null) {
            this.ajp.sd();
        }
        if (this.ajl != null) {
            this.ajl.sd();
        }
    }

    public void cd(String mapName) {
        if (this.ajk != null) {
            this.ajk.cd(mapName);
        }
        if (this.ajp != null) {
            this.ajp.zm();
        }
        if (this.ajl != null) {
            this.ajl.cd(mapName);
        }
    }

    public void cg(String mapName) {
        if (this.ajk != null) {
            this.ajk.cg(mapName);
        }
        if (this.ajp != null) {
            this.ajp.qN();
        }
        if (this.ajl != null) {
            this.ajl.cg(mapName);
        }
    }

    public void b(int id, int dmg, int x, int y, int z) {
        if (this.ajm != null) {
            this.ajm.b(id, dmg, x, y, z);
        }
        if (this.ajp != null) {
            this.ajp.b(id, dmg, x, y, z);
        }
    }

    public void c(int id, int dmg, int x, int y, int z) {
        if (this.ajm != null) {
            this.ajm.c(id, dmg, x, y, z);
        }
        if (this.ajp != null) {
            this.ajp.c(id, dmg, x, y, z);
        }
    }

    public void j(ArrayList<c> list) {
        if (this.ajn != null) {
            this.ajn.j(list);
        }
    }

    public void fw(int inputSecond) {
        if (this.ajj != null) {
            this.ajj.fw(inputSecond);
        }
    }

    public void fx(int inputKillCnt) {
        if (this.ajj != null) {
            this.ajj.fx(inputKillCnt);
        }
    }

    public void fy(int inputCnt) {
        if (this.ajj != null) {
            this.ajj.fy(inputCnt);
        }
    }

    public void fz(int inputCnt) {
        if (this.ajj != null) {
            this.ajj.fz(inputCnt);
        }
    }

    public void fA(int inputCnt) {
        if (this.ajj != null) {
            this.ajj.fA(inputCnt);
        }
    }

    public void S(int paraint1, int paraint2) {
        if (this.ajp != null) {
            this.ajp.M(paraint1, paraint2);
        }
    }

    public void e(int paraInt, float paraFloat) {
        if (this.ajp != null) {
            this.ajp.c(paraInt, paraFloat);
        }
    }

    public f zS() {
        return this.ajp;
    }

    public void a(f callback) {
        this.ajp = callback;
    }
}
