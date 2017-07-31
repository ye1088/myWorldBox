package com.huluxia.mcgame.options;

import com.huluxia.mcgame.g;

/* compiled from: DTBlackScreen */
public class a {
    private static int agL = 0;

    public static int yY() {
        return agL;
    }

    public static void hh(int repairMode) {
        agL = repairMode;
    }

    public static void yZ() {
        if (yY() == 0) {
            g.gq(0);
        } else if (yY() == 1) {
            g.gq(1);
        }
    }
}
