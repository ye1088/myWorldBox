package com.huluxia.mcjavascript;

import com.huluxia.mcgame.g;
import com.huluxia.mcsdk.DTSDKManagerEx;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativeLevelApi extends ScriptableObject {
    private static final boolean DEBUG = false;
    private static final long serialVersionUID = -3312389104818728428L;

    @JSStaticFunction
    public static void addParticle(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt2) {
        l.b(paramInt1, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramInt2);
    }

    @JSStaticFunction
    public static String biomeIdToName(int paramInt) {
        return DTSDKManagerEx.dtNativeBiomeIdToName(paramInt);
    }

    @JSStaticFunction
    public static void destroyBlock(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
        int i = getTile(paramInt1, paramInt2, paramInt3);
        int j = getData(paramInt1, paramInt2, paramInt3);
        DTSDKManagerEx.I(paramInt1, paramInt2, paramInt3);
        if (paramBoolean) {
            dropItem(((double) paramInt1) + 0.5d, (double) paramInt2, ((double) paramInt3) + 0.5d, 1.0d, i, 1, j);
        }
    }

    @JSStaticFunction
    public static Object dropItem(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt1, int paramInt2, int paramInt3) {
        return DTSDKManagerEx.a((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, (float) paramDouble4, paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static void explode(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
        DTSDKManagerEx.c((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, (float) paramDouble4);
    }

    @JSStaticFunction
    public static DTNativePointer getAddress() {
        return new DTNativePointer(DTSDKManagerEx.Cx());
    }

    @JSStaticFunction
    public static int getBiome(int paramInt1, int paramInt2) {
        return DTSDKManagerEx.dtNativeLevelGetBiome(paramInt1, paramInt2);
    }

    @JSStaticFunction
    public static String getBiomeName(int paramInt1, int paramInt2) {
        return DTSDKManagerEx.dtNativeLevelGetBiomeName(paramInt1, paramInt2);
    }

    @JSStaticFunction
    public static int getBrightness(int paramInt1, int paramInt2, int paramInt3) {
        return DTSDKManagerEx.J(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static int getChestSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return DTSDKManagerEx.i(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static int getChestSlotCount(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return DTSDKManagerEx.j(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static int getChestSlotData(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return DTSDKManagerEx.k(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static int getData(int paramInt1, int paramInt2, int paramInt3) {
        return DTSDKManagerEx.D(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static int getFurnaceSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return DTSDKManagerEx.l(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static int getFurnaceSlotCount(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return DTSDKManagerEx.m(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static int getFurnaceSlotData(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return DTSDKManagerEx.n(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static int getGameMode() {
        return DTSDKManagerEx.Cs();
    }

    @JSStaticFunction
    public static int getGrassColor(int paramInt1, int paramInt2) {
        return DTSDKManagerEx.dtNativeLevelGetGrassColor(paramInt1, paramInt2);
    }

    @JSStaticFunction
    public static String getSignText(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (paramInt4 >= 0 && paramInt4 < 4) {
            return DTSDKManagerEx.o(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        throw new RuntimeException("Invalid line for sign: must be in the range of 0 to 3");
    }

    @JSStaticFunction
    public static int getTile(int paramInt1, int paramInt2, int paramInt3) {
        return DTSDKManagerEx.C(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static int getTime() {
        return (int) DTSDKManagerEx.Cr();
    }

    @JSStaticFunction
    public static String getWorldDir() {
        return g.getWorldDir();
    }

    @JSStaticFunction
    public static String getWorldName() {
        return g.getWorldName();
    }

    @JSStaticFunction
    public static void playSound(double paramDouble1, double paramDouble2, double paramDouble3, String paramString, double paramDouble4, double paramDouble5) {
        DTSDKManagerEx.a((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, paramString, (float) paramDouble4, (float) paramDouble5);
    }

    @JSStaticFunction
    public static void playSoundEnt(int paramInt, String paramString, double paramDouble1, double paramDouble2) {
        DTSDKManagerEx.a(DTSDKManagerEx.W(paramInt, 0), DTSDKManagerEx.W(paramInt, 1), DTSDKManagerEx.W(paramInt, 2), paramString, (float) paramDouble1, (float) paramDouble2);
    }

    @JSStaticFunction
    public static void setChestSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
        DTSDKManagerEx.c(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
    }

    @JSStaticFunction
    public static void setFurnaceSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
        DTSDKManagerEx.d(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
    }

    @JSStaticFunction
    public static void setGameMode(int paramInt) {
        DTSDKManagerEx.g(paramInt, false);
    }

    @JSStaticFunction
    public static void setGrassColor(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.dtNativeLevelSetGrassColor(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static void setNightMode(boolean paramBoolean) {
        DTSDKManagerEx.ci(paramBoolean);
    }

    @JSStaticFunction
    public static void setSignText(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString) {
        if (paramInt4 < 0 || paramInt4 >= 4) {
            throw new RuntimeException("Invalid line for sign: must be in the range of 0 to 3");
        }
        DTSDKManagerEx.a(paramInt1, paramInt2, paramInt3, paramInt4, paramString);
    }

    @JSStaticFunction
    public static void setSpawn(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.K(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static void setSpawnerEntityType(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        l.g(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static void setTile(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        DTSDKManagerEx.l(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }

    @JSStaticFunction
    public static void setTime(int paramInt) {
        DTSDKManagerEx.ag((long) paramInt);
    }

    @JSStaticFunction
    public static int spawnChicken(double paramDouble1, double paramDouble2, double paramDouble3, String paramString) {
        if (DTSDKManagerEx.dy(paramString)) {
            paramString = "mob/chicken.png";
        }
        return DTSDKManagerEx.b((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, 10, paramString);
    }

    @JSStaticFunction
    public static int spawnCow(double paramDouble1, double paramDouble2, double paramDouble3, String paramString) {
        if (DTSDKManagerEx.dy(paramString)) {
            paramString = "mob/cow.png";
        }
        return DTSDKManagerEx.b((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, 11, paramString);
    }

    @JSStaticFunction
    public static Object spawnMob(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString) {
        return l.b(paramDouble1, paramDouble2, paramDouble3, paramInt, paramString);
    }

    public String getClassName() {
        return "Level";
    }
}
