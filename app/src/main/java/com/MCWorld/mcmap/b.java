package com.MCWorld.mcmap;

import com.MCWorld.mojang.Mojang;
import com.MCWorld.utils.UtilsFile;
import java.io.IOException;

/* compiled from: MCMapMainAttr */
public class b {
    public static boolean di(String mapName) {
        if (!UtilsFile.isExist(UtilsFile.eQ(mapName))) {
            return false;
        }
        try {
            Mojang.instance().init(mapName, 0, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void setGameMode(int mode) {
        try {
            Mojang.instance().setGameMode(mode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getGameMode() {
        try {
            return Mojang.instance().getGameMode() == 0 ? 0 : 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void ca(boolean dayEnable) {
        if (dayEnable) {
            try {
                Mojang.instance().setDay();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        Mojang.instance().setNight();
    }

    public static boolean AD() {
        try {
            return Mojang.instance().isDay();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean AE() {
        try {
            return Mojang.instance().mayFly();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void cb(boolean flyMode) {
        try {
            Mojang.instance().setMayFlying(flyMode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean AF() {
        try {
            return Mojang.instance().isGameThirdPersionAngle();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void cc(boolean enable) {
        try {
            Mojang.instance().setGameThirdPersionAngle(enable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean AG() {
        try {
            return Mojang.instance().invulnerable();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void cd(boolean enable) {
        try {
            Mojang.instance().setInvulnerable(enable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
