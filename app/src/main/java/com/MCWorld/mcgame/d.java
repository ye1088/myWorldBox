package com.MCWorld.mcgame;

import com.MCWorld.aa.mcspirit;
import com.MCWorld.mcinterface.c;
import com.MCWorld.mcjsmanager.b;
import com.MCWorld.mcsdk.dtlib.h;
import com.MCWorld.mcsdk.log.a;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.zhuoweizhang.mcpelauncher.ScriptManager;

/* compiled from: DTBagList */
public class d {
    private static final boolean DEBUG = false;
    private static final String TAG = "DTBagList";
    private static Object acQ = new Object();
    private static boolean acR = false;
    private static List<c> acS = null;
    private static boolean acT = false;
    private static int acU = 0;
    private static int acV = 0;
    private static int acW = 0;
    private static boolean acX = false;
    private static Set<Integer> acY;
    private static boolean acZ = false;
    private static int ada = 0;
    private static int adb = 0;
    private static int adc = 0;
    private static boolean ade = false;
    private static int adf = 0;
    private static int adg = 0;
    private static int adh = 0;
    private static int[] adi;
    private static int[] adj;
    private static boolean adk = false;
    private static int adl = 0;
    private static int adm = 0;
    private static boolean adn = false;
    public static ArrayList<c> ado = new ArrayList(36);
    private static Lock adp = new ReentrantLock();
    private static int adq = 0;
    private static int adr = 0;
    private static boolean ads = false;
    private static boolean adt = false;
    private static int adu = 0;
    private static long lastSyncTimeMillis = 0;

    private static void vg() {
        if (vu() || vm() || vq() || vs() || vk() || vw()) {
            synchronized (acQ) {
                if (m.getGameType() == 0 && g.vW() != 0) {
                    vv();
                    vr();
                    vp();
                    if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                        vt();
                    }
                    vn();
                    vl();
                    vy();
                }
                acQ.notify();
            }
        }
    }

    public static void vh() {
        if (m.getGameType() == 0) {
            vg();
        }
    }

    public static void quit() {
        try {
            lastSyncTimeMillis = 0;
            adr = 0;
            synchronized (acQ) {
                acQ.notifyAll();
            }
        } catch (Exception e) {
        }
    }

    public static void vi() {
        lastSyncTimeMillis = 0;
        adr = 0;
        ado.clear();
        for (int i = 0; i < 36; i++) {
            ado.add(new c(0, 0, 0, 0, 0));
        }
        aU(true);
    }

    public static void vj() {
        int i;
        if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
            for (i = 0; i < 45; i++) {
                c.fY(i);
            }
            return;
        }
        for (i = 0; i < 36; i++) {
            c.fY(i + 9);
        }
    }

    public static boolean vk() {
        return acR;
    }

    public static boolean x(List<c> list) {
        if (list == null || m.getGameType() == 1) {
            return false;
        }
        synchronized (acQ) {
            acS = list;
            acR = true;
            try {
                acQ.wait(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void y(List<c> list) {
        if (list != null) {
            int nInputListSize = list.size();
            for (int i = 0; i < nInputListSize; i++) {
                c pTmpMCBagItem = (c) list.get(i);
                if (pTmpMCBagItem.getId() > 0) {
                    c.o(pTmpMCBagItem.getId(), pTmpMCBagItem.getCount(), pTmpMCBagItem.ql());
                }
            }
        }
    }

    public static void vl() {
        if (acR) {
            y(acS);
            acR = false;
        }
    }

    public static boolean vm() {
        return acT;
    }

    public static boolean p(int id, int count, int dmg) {
        if (id == 0 || m.getGameType() == 1) {
            return false;
        }
        synchronized (acQ) {
            acU = id;
            acV = count;
            acW = dmg;
            acT = true;
            try {
                acQ.wait(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void q(int id, int count, int dmg) {
        if (id != 0) {
            c.o(id, count, dmg);
        }
    }

    public static void vn() {
        if (acT) {
            q(acU, acV, acW);
            acT = false;
        }
    }

    public static boolean vo() {
        return acX;
    }

    public static boolean d(Set<Integer> bagIndexSet) {
        if (bagIndexSet == null || m.getGameType() == 1) {
            return false;
        }
        synchronized (acQ) {
            acY = bagIndexSet;
            acX = true;
            try {
                acQ.wait(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void e(Set<Integer> bagIndexSet) {
        for (int i = 36; i >= 0; i--) {
            if (bagIndexSet.contains(Integer.valueOf(i))) {
                c.fY(i + 9);
            }
        }
    }

    public static void vp() {
        if (acX) {
            e(acY);
            acX = false;
        }
    }

    public static boolean vq() {
        return acZ;
    }

    public static boolean r(int id, int count, int gameBagIndex) {
        if (id == 0 || m.getGameType() == 1) {
            return false;
        }
        synchronized (acQ) {
            ada = id;
            adb = count;
            adc = gameBagIndex;
            acZ = true;
            try {
                acQ.wait(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void s(int id, int count, int gameBagIndex) {
        if (id != 0) {
            c.fY(gameBagIndex + 9);
        }
    }

    public static void vr() {
        if (acZ) {
            s(ada, adb, adc);
            acZ = false;
        }
    }

    public static boolean vs() {
        return ade;
    }

    public static boolean b(int in_ItemId, int in_ItemDmg, int in_ItemCnt, int[] enchantAttr, int[] enchantLevel) {
        if (in_ItemId == 0 || in_ItemCnt == 0 || m.getGameType() == 1) {
            return false;
        }
        synchronized (acQ) {
            adf = in_ItemId;
            adg = in_ItemDmg;
            adh = in_ItemCnt;
            adi = enchantAttr;
            adj = enchantLevel;
            ade = true;
            try {
                acQ.wait(100);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void c(int in_ItemId, int in_ItemDmg, int in_ItemCnt, int[] enchantAttr, int[] enchantLevel) {
        c.a(in_ItemId, in_ItemDmg, in_ItemCnt, enchantAttr, enchantLevel);
    }

    public static void vt() {
        if (ade) {
            c(adf, adg, adh, adi, adj);
            ade = false;
        }
    }

    public static boolean vu() {
        return adk;
    }

    public static boolean H(int bagIndex, int itemCnt) {
        boolean z = true;
        synchronized (acQ) {
            adl = bagIndex;
            adm = itemCnt;
            adk = true;
            try {
                acQ.wait(100);
            } catch (InterruptedException e) {
                z = false;
            }
        }
        return z;
    }

    public static void I(int bagIndex, int itemCnt) {
        int i;
        for (i = 0; i < 36; i++) {
            c.fY(i + 9);
        }
        for (i = 0; i < 36; i++) {
            c pTmpMCBagItem = (c) ado.get(i);
            if (i != bagIndex) {
                if (pTmpMCBagItem != null && pTmpMCBagItem.getId() > 0 && pTmpMCBagItem.getCount() > 0) {
                    c.o(pTmpMCBagItem.getId(), pTmpMCBagItem.getCount(), pTmpMCBagItem.ql());
                }
            } else if (pTmpMCBagItem != null && pTmpMCBagItem.getId() > 0 && itemCnt > 0) {
                c.o(pTmpMCBagItem.getId(), itemCnt, pTmpMCBagItem.ql());
            }
        }
    }

    public static void vv() {
        if (adk) {
            I(adl, adm);
            adk = false;
        }
    }

    public static boolean z(List<c> inputList) {
        if (1 == m.getGameType() || g.vW() == 0 || inputList.size() != 36) {
            return false;
        }
        int i;
        for (i = 0; i < 36; i++) {
            int itemType = 0;
            int bagIndex = i + 9;
            int id = c.fV(bagIndex);
            int dmg = c.fW(bagIndex);
            int count = c.fX(bagIndex);
            if (id > 0) {
                itemType = b.hC(id);
            }
            b(i, id, count, dmg, 0, itemType);
        }
        int k = 0;
        for (i = 0; i < 36; i++) {
            c pTmpMCBagItem = (c) ado.get(i);
            c pTmpMCUIThreadBagItem = (c) inputList.get(k);
            if (!(pTmpMCBagItem == null || pTmpMCBagItem.getId() <= 0 || pTmpMCUIThreadBagItem == null)) {
                pTmpMCUIThreadBagItem.a(pTmpMCBagItem);
                pTmpMCUIThreadBagItem.hk(k);
                k++;
            }
        }
        for (int j = k; j < 36; j++) {
            pTmpMCUIThreadBagItem = (c) inputList.get(j);
            pTmpMCUIThreadBagItem.ze();
            pTmpMCUIThreadBagItem.hk(k);
        }
        return true;
    }

    public static boolean vw() {
        return adn;
    }

    public static boolean A(List<c> inputList) {
        if (1 == m.getGameType() || g.vW() == 0 || inputList.size() != 36) {
            return false;
        }
        synchronized (acQ) {
            adn = true;
            try {
                c pTmpMCUIThreadBagItem;
                acQ.wait(100);
                int k = 0;
                for (int i = 0; i < 36; i++) {
                    c pTmpMCBagItem = (c) ado.get(i);
                    pTmpMCUIThreadBagItem = (c) inputList.get(k);
                    if (!(pTmpMCBagItem == null || pTmpMCBagItem.getId() <= 0 || pTmpMCUIThreadBagItem == null)) {
                        pTmpMCUIThreadBagItem.a(pTmpMCBagItem);
                        pTmpMCUIThreadBagItem.hl(i);
                        pTmpMCUIThreadBagItem.hk(k);
                        k++;
                    }
                }
                for (int j = k; j < 36; j++) {
                    pTmpMCUIThreadBagItem = (c) inputList.get(j);
                    pTmpMCUIThreadBagItem.ze();
                    pTmpMCUIThreadBagItem.hk(k);
                }
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    public static void vx() {
        for (int i = 0; i < 36; i++) {
            int itemType = 0;
            int bagIndex = i + 9;
            int id = c.fV(bagIndex);
            int dmg = c.fW(bagIndex);
            int count = c.fX(bagIndex);
            if (id > 0) {
                itemType = b.hC(id);
            }
            b(i, id, count, dmg, 0, itemType);
        }
    }

    public static void vy() {
        if (adn) {
            vx();
            adn = false;
        }
    }

    public static boolean ga(int nAddCnt) {
        if (adr + nAddCnt >= 36) {
            return true;
        }
        return false;
    }

    public static void vz() {
        if (m.getGameType() == 0) {
            for (int i = 0; i < 36; i++) {
                int bagIndex = i;
                int id = c.fV(bagIndex);
                int dmg = c.fW(bagIndex);
                int count = c.fX(bagIndex);
                a.verbose(TAG, "DTPrint DTBagList[%d][%d]: AddItemStackToList( mListItemV121New, new ItemStack(%d,%d,false,\"再生箭(0:05)\",R.drawable.item_439_0) );", new Object[]{Integer.valueOf(i), Integer.valueOf(count), Integer.valueOf(id), Integer.valueOf(dmg)});
                id = ScriptManager.nativeGetSlotInventory(bagIndex, 0);
                dmg = ScriptManager.nativeGetSlotInventory(bagIndex, 1);
                count = ScriptManager.nativeGetSlotInventory(bagIndex, 2);
                id = mcspirit.n(bagIndex, 0);
                dmg = mcspirit.n(bagIndex, 1);
                count = mcspirit.n(bagIndex, 2);
            }
        }
    }

    private static void b(int in_index, int in_id, int in_count, int in_dmg, int in_flag, int in_type) {
        if (in_index >= 0 && in_index < 36) {
            c pTmpDTBagItem = (c) ado.get(in_index);
            if (pTmpDTBagItem != null) {
                pTmpDTBagItem.setId(in_id);
                pTmpDTBagItem.setCount(in_count);
                pTmpDTBagItem.cT(in_dmg);
                pTmpDTBagItem.setFlag(in_flag);
                pTmpDTBagItem.setType(in_type);
            }
        }
    }

    private static void vA() {
        long curTimeMillis = System.currentTimeMillis();
        int tmp_bagItemCnt = 0;
        if ((curTimeMillis > 3000 + vD() && g.wd() == 0) || vF()) {
            U(curTimeMillis);
            aV(false);
            if (m.getGameType() == 0) {
                for (int i = 0; i < 36; i++) {
                    int itemType = 0;
                    int bagIndex = i + 9;
                    int id = c.fV(bagIndex);
                    int dmg = c.fW(bagIndex);
                    int count = c.fX(bagIndex);
                    if (id > 0) {
                        tmp_bagItemCnt++;
                        itemType = b.hC(id);
                    }
                    b(i, id, count, dmg, 0, itemType);
                }
                gc(tmp_bagItemCnt);
                if (1 == vC()) {
                    gb(2);
                }
            }
        }
    }

    public static void lock() {
        adp.lock();
    }

    public static void unlock() {
        adp.unlock();
    }

    public static boolean vB() {
        return adt;
    }

    public static void aU(boolean bagListInited) {
        adt = bagListInited;
    }

    public static int vC() {
        return adu;
    }

    public static void gb(int startSyncBagListToMenuList) {
        adu = startSyncBagListToMenuList;
    }

    private static long vD() {
        return lastSyncTimeMillis;
    }

    private static void U(long lastSyncTimeMillis) {
        lastSyncTimeMillis = lastSyncTimeMillis;
    }

    public static int vE() {
        return adr;
    }

    private static void gc(int bagItemCnt) {
        adr = bagItemCnt;
    }

    private static boolean vF() {
        return ads;
    }

    public static void aV(boolean bForceRefreshBagList) {
        ads = bForceRefreshBagList;
    }

    private static int vG() {
        return adq;
    }

    public static void gd(int nNeedSyncMenuListToGame) {
        adq = nNeedSyncMenuListToGame;
    }
}
