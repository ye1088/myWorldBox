package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.log.a;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativeEntityApi extends ScriptableObject {
    private static final boolean DEBUG = false;
    private static final long serialVersionUID = 8670700972082289230L;

    @JSStaticFunction
    public static void addEffect(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) {
        j.a(paramObject, paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2);
    }

    @JSStaticFunction
    public static Object[] getAll() {
        return j.As();
    }

    @JSStaticFunction
    public static int getAnimalAge(Object paramObject) {
        return j.R(paramObject);
    }

    @JSStaticFunction
    public static int getEntityTypeId(Object paramObject) {
        return j.S(paramObject);
    }

    @JSStaticFunction
    public static int getHealth(Object paramObject) {
        return j.T(paramObject);
    }

    @JSStaticFunction
    public static int getItemEntityCount(Object paramObject) {
        return j.ah(paramObject);
    }

    @JSStaticFunction
    public static int getItemEntityData(Object paramObject) {
        return j.ai(paramObject);
    }

    @JSStaticFunction
    public static int getItemEntityId(Object paramObject) {
        return j.aj(paramObject);
    }

    @JSStaticFunction
    public static String getMobSkin(Object paramObject) {
        return j.U(paramObject);
    }

    @JSStaticFunction
    public static String getNameTag(Object paramObject) {
        return j.V(paramObject);
    }

    @JSStaticFunction
    public static double getPitch(Object paramObject) {
        return j.Q(paramObject);
    }

    @JSStaticFunction
    public static int getRenderType(Object paramObject) {
        return j.W(paramObject);
    }

    @JSStaticFunction
    public static int getRider(Object paramObject) {
        return j.X(paramObject);
    }

    @JSStaticFunction
    public static int getRiding(Object paramObject) {
        return j.Y(paramObject);
    }

    @JSStaticFunction
    public static String getUniqueId(Object paramObject) {
        return j.Z(paramObject);
    }

    @JSStaticFunction
    public static double getVelX(Object paramObject) {
        return j.aa(paramObject);
    }

    @JSStaticFunction
    public static double getVelY(Object paramObject) {
        return j.ab(paramObject);
    }

    @JSStaticFunction
    public static double getVelZ(Object paramObject) {
        return j.ac(paramObject);
    }

    @JSStaticFunction
    public static double getX(Object paramObject) {
        return j.ad(paramObject);
    }

    @JSStaticFunction
    public static double getY(Object paramObject) {
        return j.ae(paramObject);
    }

    @JSStaticFunction
    public static double getYaw(Object paramObject) {
        return j.P(paramObject);
    }

    @JSStaticFunction
    public static double getZ(Object paramObject) {
        return j.af(paramObject);
    }

    @JSStaticFunction
    public static void remove(Object paramObject) {
        j.ag(paramObject);
    }

    @JSStaticFunction
    public static void removeAllEffects(Object paramObject) {
        j.ak(paramObject);
    }

    @JSStaticFunction
    public static void removeEffect(Object paramObject, int paramInt) {
        j.f(paramObject, paramInt);
    }

    @JSStaticFunction
    public static void rideAnimal(Object paramObject, Object paramInt2) {
        j.c(paramObject, paramInt2);
    }

    @JSStaticFunction
    public static void setAnimalAge(Object paramObject, int paramInt2) {
        j.a(paramObject, paramInt2);
    }

    @JSStaticFunction
    public static void setCape(Object paramObject, String paramString) {
        j.d(paramObject, paramString);
    }

    @JSStaticFunction
    public static void setCarriedItem(Object paramObject, int paramInt2, int paramInt3, int paramInt4) {
        j.a(paramObject, paramInt2, paramInt3, paramInt4);
    }

    @JSStaticFunction
    public static void setCollisionSize(Object paramObject, double paramDouble1, double paramDouble2) {
        j.b(paramObject, paramDouble1, paramDouble2);
    }

    @JSStaticFunction
    public static void setFireTicks(Object paramObject, int paramInt2) {
        j.b(paramObject, paramInt2);
    }

    @JSStaticFunction
    public static void setMaxHealth(Object paramObject, int paramInt) {
        j.c(paramObject, paramInt);
    }

    @JSStaticFunction
    public static void setHealth(Object paramObject, int paramInt2) {
        j.d(paramObject, paramInt2);
    }

    @JSStaticFunction
    public static void setMobSkin(Object paramObject, String paramString) {
        a.K("TAG", "DTPrint setMobSkin start " + paramObject + " -- " + paramString);
        j.e(paramObject, paramString);
        a.K("TAG", "DTPrint setMobSkin end ");
    }

    @JSStaticFunction
    public static void setNameTag(Object paramObject, String paramString) {
        j.f(paramObject, paramString);
    }

    @JSStaticFunction
    public static void setPosition(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3) {
        j.a(paramObject, paramDouble1, paramDouble2, paramDouble3);
    }

    @JSStaticFunction
    public static void setPositionRelative(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3) {
        j.b(paramObject, paramDouble1, paramDouble2, paramDouble3);
    }

    @JSStaticFunction
    public static void setRenderType(Object paramObject, int paramInt2) {
        j.e(paramObject, paramInt2);
    }

    @JSStaticFunction
    public static void setRot(Object paramObject, double paramDouble1, double paramDouble2) {
        j.a(paramObject, paramDouble1, paramDouble2);
    }

    @JSStaticFunction
    public static void setSneaking(Object paramObject, boolean paramBoolean) {
        j.f(paramObject, paramBoolean);
    }

    @JSStaticFunction
    public static void setVelX(Object paramObject, double paramDouble) {
        j.a(paramObject, paramDouble);
    }

    @JSStaticFunction
    public static void setVelY(Object paramObject, double paramDouble) {
        j.b(paramObject, paramDouble);
    }

    @JSStaticFunction
    public static void setVelZ(Object paramObject, double paramDouble) {
        j.c(paramObject, paramDouble);
    }

    @JSStaticFunction
    public static Object spawnMob(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString) {
        return j.b(paramDouble1, paramDouble2, paramDouble3, paramInt, paramString);
    }

    public String getClassName() {
        return "Entity";
    }
}
