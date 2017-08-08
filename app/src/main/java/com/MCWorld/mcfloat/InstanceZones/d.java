package com.MCWorld.mcfloat.InstanceZones;

import android.app.Activity;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mcfloat.InstanceZones.config.b;
import com.MCWorld.mcfloat.InstanceZones.config.c;
import com.MCWorld.mcfloat.InstanceZones.maze.a;
import com.MCWorld.mcfloat.e;
import com.MCWorld.mcfloat.i;
import com.MCWorld.mcfloat.m;
import com.MCWorld.mcfloat.n;
import com.MCWorld.mcfloat.q;
import com.MCWorld.mcgame.g;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mcsdk.DTSDKManagerEx;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.tar.TarConstants;

/* compiled from: InsZoneOperator */
public class d {
    private static final boolean Wv = true;
    private static boolean Ww = false;

    public static void aN(boolean isBuildEnv) {
        Ww = isBuildEnv;
    }

    public static b a(int insZoneMode, Activity aty) {
        String _insZonesType;
        String _configPath;
        InputStream _tmpIs;
        switch (insZoneMode) {
            case 1:
                _insZonesType = e.WH;
                _configPath = e.WG;
                break;
            case 2:
                _insZonesType = e.WJ;
                _configPath = e.WI;
                break;
            case 3:
                _insZonesType = e.WL;
                _configPath = e.WK;
                break;
            case 4:
                _insZonesType = e.WN;
                _configPath = e.WM;
                break;
            case 7:
                _insZonesType = e.WT;
                _configPath = e.WS;
                break;
            default:
                return null;
        }
        try {
            _tmpIs = aty.getResources().getAssets().open(_configPath);
        } catch (Exception e) {
            _tmpIs = null;
            HLog.verbose("Exception", "GET IT! %s", new Object[]{e.toString()});
        }
        return new com.MCWorld.mcfloat.InstanceZones.config.d().a(_insZonesType, _tmpIs, true);
    }

    private static void a(InputStream inputStream, String insZonesTypeName) {
    }

    public static void a(int insZoneMode, c curMissonPara) {
        i.rt().rg();
        e.rf().rg();
        m.rL().rg();
        h.ht(0);
        h.gs(insZoneMode);
        h.bc(true);
        switch (insZoneMode) {
            case 1:
            case 2:
            case 3:
            case 4:
                h.u(curMissonPara.mWidth, curMissonPara.XM, curMissonPara.XQ);
                return;
            default:
                return;
        }
    }

    public static void a(int insZoneMode, c curMissonPara, b time, f displayBoard) {
        switch (insZoneMode) {
            case 1:
                time.y(curMissonPara.XP, curMissonPara.XQ);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sw()), "退出\n体验");
                break;
            case 2:
                time.y(curMissonPara.XP, curMissonPara.XQ);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sw()), "退出\n训练");
                break;
            case 3:
                time.y(curMissonPara.XP, curMissonPara.XQ);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sw()), "退出\n闯关");
                break;
            case 4:
                time.y(curMissonPara.XP, curMissonPara.XQ);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sw()), "退出\n挑战");
                break;
            case 5:
                time.y(8, e.Wx);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sw()), "退出\n逃亡");
                break;
            case 6:
                time.y(8, e.Wz);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sv()), "退出\n陷阱");
                break;
            case 7:
                time.y(curMissonPara.XP, curMissonPara.XQ);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sv()), "退出\n跑酷");
                break;
            default:
                time.y(0, 0);
                displayBoard.b(TarConstants.VERSION_POSIX, TarConstants.VERSION_POSIX, hlx.utils.d.nQ(time.sw()), "");
                break;
        }
        d(insZoneMode, curMissonPara);
    }

    public static void a(int insZoneMode, c curMissionPara, int waitTime, int curTime) {
        switch (waitTime) {
            case 1:
                sB();
                sC();
                sC();
                sC();
                sC();
                sC();
                sC();
                dN(insZoneMode);
                sD();
                break;
            case 2:
                e(insZoneMode, curMissionPara);
                sE();
                dO(curTime + 15);
                f(insZoneMode, curMissionPara);
                break;
        }
        q.aJ(false);
        sA();
    }

    public static void a(b missionPara, f displayBoard) {
        int _tmpTime = missionPara.su();
        if (_tmpTime <= 3) {
            displayBoard.dP(_tmpTime);
        }
    }

    public static boolean a(b missionPara, c curMissonPara, int challengeTime, f displayBoard) {
        q.aJ(false);
        if (challengeTime == 0) {
            displayBoard.sI();
            n.rU().rg();
            h.bR(false);
        }
        if (missionPara.sn() == 5) {
            if (challengeTime == 0) {
                a.tB().tE();
                return false;
            }
            if (challengeTime % 15 == 0) {
                a.tB().e(new com.MCWorld.mcfb.b(1, 32, 0, 1, 0));
            }
            return a.tB().tD();
        } else if (missionPara.sn() == 6) {
            if (challengeTime != 0) {
                return com.MCWorld.mcfloat.InstanceZones.trap.a.tQ().tP();
            }
            com.MCWorld.mcfloat.InstanceZones.trap.a.tQ().tH();
            return false;
        } else if (missionPara.sn() == 7) {
            if (challengeTime != 0) {
                return com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().tI();
            }
            com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().tH();
            return false;
        } else if (curMissonPara.tA() != null && missionPara.sr() < curMissonPara.tA().size() && ((com.MCWorld.mcfloat.InstanceZones.structrue.c) curMissonPara.tA().get(missionPara.sr())).Zr == challengeTime + 8) {
            h.l(((com.MCWorld.mcfloat.InstanceZones.structrue.c) curMissonPara.tA().get(missionPara.sr())).Zq);
            missionPara.sq();
            return false;
        } else if (h.qh() && curMissonPara.tA() != null && missionPara.sr() < curMissonPara.tA().size()) {
            h.l(((com.MCWorld.mcfloat.InstanceZones.structrue.c) curMissonPara.tA().get(missionPara.sr())).Zq);
            missionPara.sq();
            return false;
        } else if (h.qh()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean a(b missionPara) {
        if (missionPara.sv() < 3) {
            return false;
        }
        switch (missionPara.sn()) {
            case 1:
            case 2:
            case 3:
            case 4:
                return h.sN().sQ();
            case 5:
                return a.tB().sQ();
            case 6:
                return com.MCWorld.mcfloat.InstanceZones.trap.a.tQ().sQ();
            case 7:
                return com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().sQ();
            default:
                return true;
        }
    }

    public static void a(b missionPara, f displayBoard, c mCurMissonPara, int countDownTime) {
        if (missionPara.sn() == 5) {
            displayBoard.ch(hlx.utils.d.nQ(countDownTime));
        } else if (missionPara.sn() == 6) {
            displayBoard.ch(hlx.utils.d.nQ(missionPara.sv()));
        } else if (missionPara.sn() == 7) {
            displayBoard.ch(hlx.utils.d.nQ(missionPara.sv()));
        } else {
            int _killMonsterSum = 0;
            int _chalengeScore = mCurMissonPara.XS * mCurMissonPara.XR;
            for (c.a _tmpCC : a(mCurMissonPara)) {
                _chalengeScore += _tmpCC.Wu;
                _killMonsterSum += _tmpCC.Wt;
            }
            displayBoard.g(_chalengeScore - ((mCurMissonPara.XS - h.zp()) * mCurMissonPara.XR), _killMonsterSum, countDownTime);
        }
    }

    public static boolean dM(int insZoneMode) {
        if (insZoneMode == 5) {
            return a.tB().tD();
        }
        if (insZoneMode == 6) {
            return com.MCWorld.mcfloat.InstanceZones.trap.a.tQ().tP();
        }
        if (insZoneMode == 7) {
            return com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().tI();
        }
        return h.qh();
    }

    public static boolean a(b insZoneAllPara, int insZoneMode, int curMission) {
        if (insZoneMode == 5) {
            return false;
        }
        if (insZoneMode == 6) {
            return false;
        }
        if (insZoneMode == 7) {
            return insZoneAllPara.eI(curMission + 1);
        }
        return insZoneAllPara.eI(curMission + 1);
    }

    public static List<c.a> a(c curMissonPara) {
        List<c.a> _tmpList = new ArrayList();
        if (curMissonPara != null) {
            Iterator it = h.qs().iterator();
            while (it.hasNext()) {
                com.MCWorld.mcfb.c _MCFBMonsterItem = (com.MCWorld.mcfb.c) it.next();
                int _killMonsterNumber = _MCFBMonsterItem.getCount();
                if (_killMonsterNumber > 0) {
                    c.a _tmpMonster = new c.a();
                    _tmpMonster.Wt = _killMonsterNumber;
                    switch (_MCFBMonsterItem.getId()) {
                        case 10:
                            _tmpMonster.Ws = "鸡";
                            _tmpMonster.Wu = curMissonPara.tb() * _killMonsterNumber;
                            break;
                        case 11:
                            _tmpMonster.Ws = "牛";
                            _tmpMonster.Wu = curMissonPara.tc() * _killMonsterNumber;
                            break;
                        case 12:
                            _tmpMonster.Ws = "猪";
                            _tmpMonster.Wu = curMissonPara.td() * _killMonsterNumber;
                            break;
                        case 13:
                            _tmpMonster.Ws = "羊";
                            _tmpMonster.Wu = curMissonPara.te() * _killMonsterNumber;
                            break;
                        case 14:
                            _tmpMonster.Ws = "狼";
                            _tmpMonster.Wu = curMissonPara.tf() * _killMonsterNumber;
                            break;
                        case 15:
                            _tmpMonster.Ws = "村民";
                            _tmpMonster.Wu = curMissonPara.tg() * _killMonsterNumber;
                            break;
                        case 16:
                            _tmpMonster.Ws = "哞菇";
                            _tmpMonster.Wu = curMissonPara.th() * _killMonsterNumber;
                            break;
                        case 17:
                            _tmpMonster.Ws = "鱿鱼";
                            _tmpMonster.Wu = curMissonPara.tr() * _killMonsterNumber;
                            break;
                        case 19:
                            _tmpMonster.Ws = "蝙蝠";
                            _tmpMonster.Wu = curMissonPara.sZ() * _killMonsterNumber;
                            break;
                        case 20:
                            _tmpMonster.Ws = "铁傀儡";
                            _tmpMonster.Wu = curMissonPara.ts() * _killMonsterNumber;
                            break;
                        case 21:
                            _tmpMonster.Ws = "雪傀儡";
                            _tmpMonster.Wu = curMissonPara.tt() * _killMonsterNumber;
                            break;
                        case 22:
                            _tmpMonster.Ws = "豹猫";
                            _tmpMonster.Wu = curMissonPara.tu() * _killMonsterNumber;
                            break;
                        case 32:
                            _tmpMonster.Ws = "僵尸";
                            _tmpMonster.Wu = curMissonPara.ti() * _killMonsterNumber;
                            break;
                        case 33:
                            _tmpMonster.Ws = "爬行者";
                            _tmpMonster.Wu = curMissonPara.tj() * _killMonsterNumber;
                            break;
                        case 34:
                            _tmpMonster.Ws = "骷髅";
                            _tmpMonster.Wu = curMissonPara.tk() * _killMonsterNumber;
                            break;
                        case 35:
                            _tmpMonster.Ws = "蜘蛛";
                            _tmpMonster.Wu = curMissonPara.tl() * _killMonsterNumber;
                            break;
                        case 36:
                            _tmpMonster.Ws = "僵尸猪人";
                            _tmpMonster.Wu = curMissonPara.tm() * _killMonsterNumber;
                            break;
                        case 37:
                            _tmpMonster.Ws = "史莱姆";
                            _tmpMonster.Wu = curMissonPara.tn() * _killMonsterNumber;
                            break;
                        case 38:
                            _tmpMonster.Ws = "末影人";
                            _tmpMonster.Wu = curMissonPara.to() * _killMonsterNumber;
                            break;
                        case 39:
                            _tmpMonster.Ws = "蠹虫";
                            _tmpMonster.Wu = curMissonPara.tp() * _killMonsterNumber;
                            break;
                        case 40:
                            _tmpMonster.Ws = "洞穴蜘蛛";
                            _tmpMonster.Wu = curMissonPara.tv() * _killMonsterNumber;
                            break;
                        case 41:
                            _tmpMonster.Ws = "恶魂";
                            _tmpMonster.Wu = curMissonPara.tq() * _killMonsterNumber;
                            break;
                        case 42:
                            _tmpMonster.Ws = "岩浆怪";
                            _tmpMonster.Wu = curMissonPara.tw() * _killMonsterNumber;
                            break;
                        case 43:
                            _tmpMonster.Ws = "烈焰人";
                            _tmpMonster.Wu = curMissonPara.ta() * _killMonsterNumber;
                            break;
                        case 44:
                            _tmpMonster.Ws = "僵尸村民";
                            _tmpMonster.Wu = curMissonPara.tx() * _killMonsterNumber;
                            break;
                    }
                    _tmpList.add(_tmpMonster);
                }
            }
        }
        return _tmpList;
    }

    public static void b(int insZoneMode, c curMissonPara, b missionPara, f displayBoard) {
        if (insZoneMode == 4) {
            missionPara.z(curMissonPara.XP, curMissonPara.XQ);
            displayBoard.ch(hlx.utils.d.nQ(missionPara.sw()));
        } else if (insZoneMode == 2) {
            h.qi();
            missionPara.y(curMissonPara.XP, curMissonPara.XQ);
            displayBoard.ch(hlx.utils.d.nQ(missionPara.sv()));
        } else {
            missionPara.y(curMissonPara.XP, curMissonPara.XQ);
            displayBoard.ch(hlx.utils.d.nQ(missionPara.sw()));
        }
    }

    public static void b(int insZoneMode, c curMissonPara) {
        if (insZoneMode == 5) {
            a.tB().sT();
        }
        if (insZoneMode == 6) {
            com.MCWorld.mcfloat.InstanceZones.trap.a.tQ().tJ();
        }
        if (insZoneMode == 7) {
            com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().tJ();
        } else {
            h.sN().sT();
        }
    }

    public static void c(int insZoneMode, c curMissonPara) {
        h.gs(0);
        h.bc(false);
        h.qi();
        b(insZoneMode, curMissonPara);
        sD();
        n.rU().rX();
        h.bR(true);
        sB();
    }

    private static void sA() {
        try {
            if (hlx.launch.game.c.Sg().Sh() == 3 || hlx.launch.game.c.Sg().Sh() == 4 || hlx.launch.game.c.Sg().Sh() == 5 || hlx.launch.game.c.Sg().Sh() == 7) {
                int _mapBiontSize = g.aea.size();
                for (int i = 0; _mapBiontSize > i; i++) {
                    long _nLongMapBionId = ((Long) g.aea.get(i)).longValue();
                    if (DTSDKManagerEx.ai(_nLongMapBionId) == 63) {
                        DTSDKManagerEx.i(_nLongMapBionId, 0);
                    }
                }
            }
        } catch (Exception e) {
            HLog.verbose("Exception", e.getMessage(), new Object[0]);
        }
    }

    private static void d(int insZoneMode, c curMissonPara) {
        if (insZoneMode == 5) {
            a.tB().C(0, 0);
        } else if (insZoneMode == 6) {
            com.MCWorld.mcfloat.InstanceZones.trap.a.tQ().tG();
        } else if (insZoneMode == 7) {
            com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().b(curMissonPara.mWidth, curMissonPara.mLength, curMissonPara.XM, curMissonPara.tz());
            com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().tG();
        } else {
            h.sN().i((int) h.zF(), (int) h.zG(), (int) h.zH());
            h.sN().C(curMissonPara.mWidth, curMissonPara.XM);
        }
    }

    private static void sB() {
        h.Q(-1, -1);
    }

    private static void sC() {
        h.vj();
    }

    private static void dN(int insZoneMode) {
        switch (insZoneMode) {
            case 1:
            case 2:
            case 3:
            case 4:
                h.sN().sS();
                return;
            case 5:
                a.tB().tC();
                return;
            case 6:
                com.MCWorld.mcfloat.InstanceZones.trap.a.tQ().tC();
                return;
            case 7:
                com.MCWorld.mcfloat.InstanceZones.parkour.a.tK().tC();
                return;
            default:
                return;
        }
    }

    private static void sD() {
        for (int i = 1; i <= 23; i++) {
            h.hm(i);
        }
        h.a(false, null);
    }

    private static void e(int insZoneMode, c curMissonPara) {
        List<com.MCWorld.mcinterface.c> mBagCacheList;
        if (insZoneMode == 5) {
            mBagCacheList = new ArrayList();
            mBagCacheList.add(new com.MCWorld.mcinterface.c(0, 323, 30, 0, 0));
            h.F(mBagCacheList);
        } else if (insZoneMode == 6) {
            mBagCacheList = new ArrayList();
            mBagCacheList.add(new com.MCWorld.mcinterface.c(0, 261, 1, 0, 0));
            mBagCacheList.add(new com.MCWorld.mcinterface.c(0, 262, 10, 0, 0));
            h.F(mBagCacheList);
        } else if (insZoneMode != 7) {
            w(curMissonPara.sW());
            ArrayList<com.MCWorld.mcfb.b> inFBMonsterList = new ArrayList();
            List<com.MCWorld.mcfb.b> tmpWareEquipment = curMissonPara.sX();
            List<com.MCWorld.mcfb.b> tmpBagEquipment = curMissonPara.sY();
            if (tmpWareEquipment != null) {
                inFBMonsterList.addAll(tmpWareEquipment);
            }
            if (tmpBagEquipment != null) {
                inFBMonsterList.addAll(tmpBagEquipment);
            }
            h.k(inFBMonsterList);
        }
    }

    private static void w(List<com.MCWorld.mcfb.b> itemList) {
        if (itemList != null) {
            int[] _GeneralAttrs = new int[25];
            int[] _EnchantLevel = new int[25];
            for (int i = 0; i < 25; i++) {
                _GeneralAttrs[i] = -1;
                _EnchantLevel[i] = 0;
            }
            _GeneralAttrs[6] = 17;
            _EnchantLevel[6] = 5;
            for (com.MCWorld.mcfb.b _tmp : itemList) {
                h.d(_tmp.getId(), _tmp.ql(), _tmp.qm(), _GeneralAttrs, _EnchantLevel);
            }
        }
    }

    private static void sE() {
    }

    private static void dO(int Effecttime) {
        h.v(16, Effecttime, 1);
    }

    private static void f(int insZoneMode, c curMissonPara) {
        h.e(20, false);
        h.hr(0);
        h.O(20, 20);
    }
}
