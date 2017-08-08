package com.MCWorld.mcgame;

import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: DTMapBiontManage */
public class p {
    private static int afd;
    private static int afe;
    private static boolean aff;
    private static boolean afg = true;
    private static volatile boolean afh = true;
    private static Lock afi = new ReentrantLock();

    public static void xi() {
        xj();
    }

    public static void K(int id, int distance) {
        b(id, distance, true);
    }

    public static void reset() {
        gE(0);
        gF(0);
        bp(false);
    }

    private static boolean h(int origX, int origZ, int radius, int dstX, int dstZ) {
        if (dstX >= origX - radius && dstX <= origX + radius) {
            return true;
        }
        if (dstZ < origZ - radius || dstZ > origZ + radius) {
            return false;
        }
        return true;
    }

    private static void xj() {
        try {
            if (aff && afe != 0 && xo()) {
                lock();
                int curRoleX = (int) y.yj();
                int curRoleZ = (int) y.yl();
                int mapBiontSize;
                int i;
                int nTmpId;
                int entity_pos_x;
                int entity_pos_z;
                if (h.CW().CX() == 0) {
                    mapBiontSize = g.adZ.size();
                    for (i = 0; mapBiontSize > i; i++) {
                        int nMapBiontId = ((Integer) g.adZ.get(i)).intValue();
                        nTmpId = DTSDKManagerEx.in(nMapBiontId);
                        if (DTSDKManagerEx.in(nMapBiontId) != 63 && (afd == -1 || afd == nTmpId)) {
                            entity_pos_x = (int) DTSDKManagerEx.W(nMapBiontId, 0);
                            entity_pos_z = (int) DTSDKManagerEx.W(nMapBiontId, 2);
                            if (-1 == afe || h(curRoleX, curRoleZ, afe, entity_pos_x, entity_pos_z)) {
                                DTSDKManagerEx.it(nMapBiontId);
                            }
                        }
                    }
                } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                    mapBiontSize = g.aea.size();
                    for (i = 0; mapBiontSize > i; i++) {
                        long nLongMapBionId = ((Long) g.aea.get(i)).longValue();
                        nTmpId = DTSDKManagerEx.ai(nLongMapBionId);
                        if (DTSDKManagerEx.ai(nLongMapBionId) != 63 && (afd == -1 || afd == nTmpId)) {
                            entity_pos_x = (int) DTSDKManagerEx.e(nLongMapBionId, 0);
                            entity_pos_z = (int) DTSDKManagerEx.e(nLongMapBionId, 2);
                            if (-1 == afe || h(curRoleX, curRoleZ, afe, entity_pos_x, entity_pos_z)) {
                                DTSDKManagerEx.ao(nLongMapBionId);
                            }
                        }
                    }
                }
                reset();
                br(false);
                unlock();
            }
        } catch (Exception e) {
            unlock();
        }
    }

    private static void b(int inputId, int inputDistance, boolean inputRequest) {
        try {
            if (g.wc() == 1) {
                try {
                    lock();
                    gE(inputId);
                    gF(inputDistance);
                    bp(inputRequest);
                    br(true);
                    unlock();
                } catch (Exception e) {
                    unlock();
                }
            }
        } catch (Exception e2) {
        }
    }

    private static void lock() {
        afi.lock();
    }

    private static void unlock() {
        afi.unlock();
    }

    public static int xk() {
        return afd;
    }

    public static void gE(int del_id) {
        afd = del_id;
    }

    public static int xl() {
        return afe;
    }

    public static void gF(int del_distance) {
        afe = del_distance;
    }

    public static boolean xm() {
        return aff;
    }

    public static void bp(boolean requestOper) {
        aff = requestOper;
    }

    public static boolean xn() {
        return afg;
    }

    public static void bq(boolean addBiontRefreshFlag) {
        afg = addBiontRefreshFlag;
    }

    public static boolean xo() {
        return afh;
    }

    public static void br(boolean delBiontRefreshFlag) {
        afh = delBiontRefreshFlag;
    }
}
