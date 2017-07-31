package com.huluxia.mcgame;

import com.huluxia.mcinterface.h;

/* compiled from: DTPlayerDeathHelper */
public class s {
    public static int afk = 4;
    public static int[] afl = new int[afk];
    private static volatile boolean bPlayerDeathFlag = false;

    public static void quit() {
        afl = null;
    }

    public static void bt(boolean bPlayerDeathFlag) {
        bPlayerDeathFlag = bPlayerDeathFlag;
    }

    public static void xq() {
        xr();
    }

    private static void xr() {
        boolean bDeathSwitchFlag = true;
        if (!xs() || v.xJ() <= 0) {
            bDeathSwitchFlag = false;
        }
        if (bDeathSwitchFlag) {
            bt(false);
            h.zM().sf();
        }
    }

    private static int gG(int nArmorSlotIndex) {
        if (nArmorSlotIndex < 0 || nArmorSlotIndex >= afk) {
            return 0;
        }
        return afl[nArmorSlotIndex];
    }

    private static boolean L(int in_SlotIndex, int in_ArmorSlotID) {
        if (in_SlotIndex < 0 || in_SlotIndex >= afk) {
            return false;
        }
        afl[in_SlotIndex] = in_ArmorSlotID;
        return true;
    }

    public static boolean xs() {
        return bPlayerDeathFlag;
    }
}
