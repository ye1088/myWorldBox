package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManagerEx;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.annotations.JSFunction;

public class DTBlockHostObject extends ImporterTopLevel {
    private static final boolean DEBUG = false;
    private long playerEnt = 0;

    @JSFunction
    public void addItemInventory(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.A(paramInt1, paramInt2, paramInt3);
    }

    @JSFunction
    public void bl_setMobSkin(Object paramInt, String paramString) {
        DTSDKManagerEx.h(paramInt, paramString);
    }

    @JSFunction
    public Object bl_spawnMob(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString) {
        return c.a(paramDouble1, paramDouble2, paramDouble3, paramInt, paramString);
    }

    @JSFunction
    public void clientMessage(String paramString) {
        DTSDKManagerEx.dB(paramString);
    }

    @JSFunction
    public void explode(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
        DTSDKManagerEx.c((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, (float) paramDouble4);
    }

    @JSFunction
    public int getCarriedItem() {
        return DTSDKManagerEx.ip(0);
    }

    public String getClassName() {
        return "BlockHostObject";
    }

    @JSFunction
    public DTNativePointer getLevel() {
        return new DTNativePointer(DTSDKManagerEx.Cx());
    }

    @JSFunction
    public double getPitch(Object paramObject) {
        return c.Q(paramObject);
    }

    @JSFunction
    public Object getPlayerEnt() {
        return i.Ar();
    }

    @JSFunction
    public double getPlayerX() {
        return (double) DTSDKManagerEx.ik(0);
    }

    @JSFunction
    public double getPlayerY() {
        return (double) DTSDKManagerEx.ik(1);
    }

    @JSFunction
    public double getPlayerZ() {
        return (double) DTSDKManagerEx.ik(2);
    }

    @JSFunction
    public int getTile(int paramInt1, int paramInt2, int paramInt3) {
        return DTSDKManagerEx.C(paramInt1, paramInt2, paramInt3);
    }

    @JSFunction
    public double getYaw(Object paramObject) {
        return c.P(paramObject);
    }

    @JSFunction
    public void preventDefault() {
        DTSDKManagerEx.Cy();
    }

    @JSFunction
    public void print(String paramString) {
        f.scriptPrint(paramString);
    }

    @JSFunction
    public void rideAnimal(Object paramObject1, Object paramObject2) {
        c.c(paramObject1, paramObject2);
    }

    @JSFunction
    public void setNightMode(boolean paramBoolean) {
        DTSDKManagerEx.ci(paramBoolean);
    }

    @JSFunction
    public static void setLightningLevel(double paramDouble) {
        DTSDKManagerEx.d(paramDouble);
    }

    @JSFunction
    public static void setRainLevel(double paramDouble) {
        DTSDKManagerEx.e(paramDouble);
    }

    @JSFunction
    public void setPosition(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3) {
        c.a(paramObject, paramDouble1, paramDouble2, paramDouble3);
    }

    @JSFunction
    public void setPositionRelative(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3) {
        c.b(paramObject, paramDouble1, paramDouble2, paramDouble3);
    }

    @JSFunction
    public void setRot(Object paramObject, double paramDouble1, double paramDouble2) {
        c.a(paramObject, paramDouble1, paramDouble2);
    }

    @JSFunction
    public void setTile(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        DTSDKManagerEx.l(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }

    @JSFunction
    public void setVelX(Object paramObject, double paramDouble) {
        c.a(paramObject, paramDouble);
    }

    @JSFunction
    public void setVelY(Object paramObject, double paramDouble) {
        c.b(paramObject, paramDouble);
    }

    @JSFunction
    public void setVelZ(Object paramObject, double paramDouble) {
        c.c(paramObject, paramDouble);
    }

    @JSFunction
    public int spawnChicken(double paramDouble1, double paramDouble2, double paramDouble3, String paramString) {
        if (DTSDKManagerEx.dy(paramString)) {
            paramString = "mob/chicken.png";
        }
        return DTSDKManagerEx.b((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, 10, paramString);
    }

    @JSFunction
    public int spawnCow(double paramDouble1, double paramDouble2, double paramDouble3, String paramString) {
        if (DTSDKManagerEx.dy(paramString)) {
            paramString = "mob/cow.png";
        }
        return DTSDKManagerEx.b((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, 11, paramString);
    }

    @JSFunction
    public int spawnPigZombie(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString) {
        if (DTSDKManagerEx.dy(paramString)) {
            paramString = "mob/pigzombie.png";
        }
        int i = DTSDKManagerEx.b((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, 36, paramString);
        DTSDKManagerEx.h(i, paramInt, 1, 0);
        return i;
    }
}
