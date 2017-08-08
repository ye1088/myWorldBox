package com.MCWorld.mcfb;

import com.MCWorld.mcgame.g;
import com.MCWorld.mcinterface.c;
import com.MCWorld.mcinterface.j;
import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;
import java.util.ArrayList;
import java.util.List;
import net.zhuoweizhang.mcpelauncher.ScriptManager;

/* compiled from: MCFB001 */
public class a {
    private static final boolean DEBUG = false;
    private static int NA;
    private static int NH;
    private static int NI;
    private static int NJ;
    private static int NK;
    private static a Oa;
    private int NB;
    private int NC;
    private int ND;
    private int NF;
    private int NG;
    private int NL;
    private int NM;
    private int NN;
    private int NP;
    private int NQ;
    private int NR;
    private int NT;
    private int NU;
    private int NV;
    private int NW;
    private int NX;
    private int NY;
    private int NZ;
    private int Nz;
    private boolean Ob = false;
    private boolean Oc = false;
    private String Od = null;
    private String Oe = null;
    private boolean Of = false;
    private int Og = 0;

    public static synchronized a qf() {
        a aVar;
        synchronized (a.class) {
            if (Oa == null) {
                Oa = new a();
                Oa.ND = -2;
                Oa.NF = 10;
                Oa.NG = 15;
            }
            aVar = Oa;
        }
        return aVar;
    }

    public void a(int inputRolePosX, int inputRolePosY, int inputRolePosZ, int inputCorner01PosX, int inputCorner01PosY, int inputCorner01PosZ, int inputCorner02PosX, int inputCorner02PosY, int inputCorner02PosZ, int inputCorner03PosX, int inputCorner03PosY, int inputCorner03PosZ, int inputCorner04PosX, int inputCorner04PosY, int inputCorner04PosZ, int inputCornerGap) {
        this.Nz = inputRolePosX;
        NA = inputRolePosY;
        this.NB = inputRolePosZ;
        this.NL = inputCorner01PosX;
        this.NM = inputCorner01PosY;
        this.NN = inputCorner01PosZ;
        this.NP = inputCorner02PosX;
        this.NQ = inputCorner02PosY;
        this.NR = inputCorner02PosZ;
        this.NT = inputCorner03PosX;
        this.NU = inputCorner03PosY;
        this.NV = inputCorner03PosZ;
        this.NW = inputCorner04PosX;
        this.NX = inputCorner04PosY;
        this.NY = inputCorner04PosZ;
        this.NZ = inputCornerGap;
        NH = this.NL;
        NI = this.NN;
        NJ = this.NW;
        NK = this.NY;
        this.Ob = false;
        this.Oc = false;
    }

    private static boolean e(int inputX, int inputY, int inputZ) {
        if (inputX <= NH || inputX >= NJ || inputZ <= NI || inputZ >= NK || inputY <= NA - 3 || inputY >= NA + 25) {
            return false;
        }
        return true;
    }

    private static boolean a(int origX, int origY, int origZ, int radius, int dstX, int dstY, int dstZ) {
        if (dstX < origX - radius || dstX > origX + radius || dstZ < origZ - radius || dstZ > origZ + radius) {
            return false;
        }
        return true;
    }

    public boolean qg() {
        return this.Ob;
    }

    public boolean qh() {
        return this.Oc;
    }

    public void qi() {
        this.Ob = false;
        this.Oc = false;
    }

    public void q(String inputString, String inputSplitString) {
        this.Od = inputString;
        this.Oe = inputSplitString;
        this.Of = true;
    }

    public void qj() {
        if (this.Of) {
            this.Of = false;
            DTSDKManagerEx.D(this.Od, this.Oe);
        }
    }

    public void qk() {
        if (h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CX() != 7 && h.CW().CZ() != 1) {
            return;
        }
        if (g.wm() != 1 && g.wm() != 2 && g.wm() != 3 && g.wm() != 4) {
            return;
        }
        if (this.Og < 13) {
            this.Og++;
            cS(0);
            return;
        }
        this.Oc = cS(0);
    }

    public boolean cS(int inputFBIndex) {
        boolean _tmpHaveNetUser = false;
        try {
            if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7 || h.CW().CZ() == 1) {
                int _mapBiontSize = g.aea.size();
                for (int i = 0; _mapBiontSize > i; i++) {
                    long _nLongMapBionId = ((Long) g.aea.get(i)).longValue();
                    int _nTmpId = DTSDKManagerEx.ai(_nLongMapBionId);
                    if (DTSDKManagerEx.ai(_nLongMapBionId) == 63) {
                        _tmpHaveNetUser = true;
                        DTSDKManagerEx.i(_nLongMapBionId, 0);
                    } else {
                        int entity_pos_x = (int) DTSDKManagerEx.e(_nLongMapBionId, 0);
                        int entity_pos_y = (int) DTSDKManagerEx.e(_nLongMapBionId, 1);
                        int entity_pos_z = (int) DTSDKManagerEx.e(_nLongMapBionId, 2);
                        if (_nTmpId > 10 && _nTmpId < 45 && e(entity_pos_x, entity_pos_y, entity_pos_z)) {
                            this.Ob = _tmpHaveNetUser;
                            return false;
                        }
                    }
                }
            }
            this.Ob = _tmpHaveNetUser;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void u(int inputWide, int inputHigh) {
        this.NF = inputHigh / 2;
        this.NG = inputWide / 2;
    }

    public void g(ArrayList<b> inputFBMonsterList) {
        ArrayList<j> _tmpFBMonsterList = new ArrayList();
        for (int i = 0; i < inputFBMonsterList.size(); i++) {
            b _fbItem = (b) inputFBMonsterList.get(i);
            if (_fbItem != null) {
                int _tmpItemCnt = _fbItem.qm();
                for (int j = 0; j < _tmpItemCnt; j++) {
                    if (_fbItem.qn() == 1) {
                        _tmpFBMonsterList.add(new j(_fbItem.getId(), _fbItem.ql(), this.NL + this.NZ, this.NM, this.NN + this.NZ, 1, _fbItem.getMaxHealth()));
                    } else if (_fbItem.qn() == 2) {
                        _tmpFBMonsterList.add(new j(_fbItem.getId(), _fbItem.ql(), this.NP + this.NZ, this.NQ, this.NR - this.NZ, 1, _fbItem.getMaxHealth()));
                    } else if (_fbItem.qn() == 3) {
                        _tmpFBMonsterList.add(new j(_fbItem.getId(), _fbItem.ql(), this.NT - this.NZ, this.NU, this.NV + this.NZ, 1, _fbItem.getMaxHealth()));
                    } else if (_fbItem.qn() == 4) {
                        _tmpFBMonsterList.add(new j(_fbItem.getId(), _fbItem.ql(), this.NW - this.NZ, this.NX, this.NY - this.NZ, 1, _fbItem.getMaxHealth()));
                    } else if (99 == _fbItem.qn()) {
                        _tmpFBMonsterList.add(new j(_fbItem.getId(), _fbItem.ql(), (int) (((float) NH) + (((float) Math.random()) * ((float) (NJ - NH)))), this.NX, (int) (((float) NI) + (((float) Math.random()) * ((float) (NK - NI)))), 1, _fbItem.getMaxHealth()));
                    }
                }
            }
        }
        com.MCWorld.mcinterface.h.D(_tmpFBMonsterList);
        this.Og = 0;
        this.Oc = false;
    }

    public void h(ArrayList<b> inputFBMonsterList) {
        ArrayList<j> _tmpFBMonsterList = new ArrayList();
        List<c> mBagCacheList = new ArrayList();
        for (int i = 0; i < inputFBMonsterList.size(); i++) {
            b _fbItem = (b) inputFBMonsterList.get(i);
            if (_fbItem != null) {
                if (_fbItem.qn() == 5) {
                    mBagCacheList.add(new c(0, _fbItem.getId(), _fbItem.qm(), _fbItem.ql(), 0));
                } else if (_fbItem.qn() == 6) {
                    ScriptManager.nativeMobSetArmor(DTSDKManagerEx.Cq(), _fbItem.ql(), _fbItem.getId(), 0);
                }
            }
        }
        com.MCWorld.mcinterface.h.F(mBagCacheList);
    }
}
