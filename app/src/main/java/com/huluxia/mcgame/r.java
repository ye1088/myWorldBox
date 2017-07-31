package com.huluxia.mcgame;

import com.huluxia.mcsdk.DTSDKManagerEx;

/* compiled from: DTNonameCard */
public class r {
    private static volatile boolean afj = false;

    public static void bs(boolean openNonameCard) {
        afj = openNonameCard;
    }

    public static void i(int x, int y, int z, int ii, int side) {
        try {
            if (xp() && ii == 323) {
                switch (side) {
                    case 0:
                        y--;
                        return;
                    case 1:
                        DTSDKManagerEx.l(x, y + 1, z, 63, 0);
                        return;
                    case 2:
                        DTSDKManagerEx.l(x, y, z - 1, 68, 2);
                        return;
                    case 3:
                        DTSDKManagerEx.l(x, y, z + 1, 68, 3);
                        return;
                    case 4:
                        DTSDKManagerEx.l(x - 1, y, z, 68, 4);
                        return;
                    case 5:
                        DTSDKManagerEx.l(x + 1, y, z, 68, 5);
                        return;
                    default:
                        return;
                }
            }
        } catch (Exception e) {
        }
    }

    public static boolean xp() {
        return afj;
    }
}
