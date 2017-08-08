package com.MCWorld.mcgame;

import com.MCWorld.mcsdk.DTSDKManagerEx;

/* compiled from: DTPlayerSpawn */
public class aa {
    private static volatile boolean afZ;
    private static volatile float aga;
    private static volatile float agb;
    private static volatile float agc;

    public static void yv() {
        if (yw()) {
            DTSDKManagerEx.a(DTSDKManagerEx.Cp(), yx(), yy(), yz());
        }
    }

    public static void e(int mode, int x, int y, int z) {
        if (g.wc() != 1) {
            return;
        }
        if (mode == 0) {
            D(y.gU(0));
            E(y.gU(1));
            F(y.gU(2));
            bG(true);
        } else if (1 == mode) {
            D((float) x);
            E((float) y);
            F((float) z);
            bG(true);
        }
    }

    public static boolean yw() {
        return afZ;
    }

    public static void bG(boolean enableSpawnPoint) {
        afZ = enableSpawnPoint;
    }

    public static float yx() {
        return aga;
    }

    public static void D(float spawn_x) {
        aga = spawn_x;
    }

    public static float yy() {
        return agb;
    }

    public static void E(float spawn_y) {
        agb = spawn_y;
    }

    public static float yz() {
        return agc;
    }

    public static void F(float spawn_z) {
        agc = spawn_z;
    }
}
