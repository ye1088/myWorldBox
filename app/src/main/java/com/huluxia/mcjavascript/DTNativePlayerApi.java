package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManagerEx;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativePlayerApi extends ScriptableObject {
    private static final boolean DEBUG = false;
    private static final long serialVersionUID = 1199654792070977012L;

    @JSStaticFunction
    public static void addItemCreativeInv(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.E(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static void addItemInventory(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.A(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static boolean canFly() {
        return DTSDKManagerEx.dtNativePlayerCanFly();
    }

    @JSStaticFunction
    public static void clearInventorySlot(int paramInt) {
        DTSDKManagerEx.ir(paramInt);
    }

    @JSStaticFunction
    public static int getArmorSlot(int paramInt) {
        return DTSDKManagerEx.ac(paramInt, 0);
    }

    @JSStaticFunction
    public static int getArmorSlotDamage(int paramInt) {
        return DTSDKManagerEx.ac(paramInt, 1);
    }

    @JSStaticFunction
    public static int getCarriedItem() {
        return DTSDKManagerEx.ip(0);
    }

    @JSStaticFunction
    public static int getCarriedItemCount() {
        return DTSDKManagerEx.ip(2);
    }

    @JSStaticFunction
    public static int getCarriedItemData() {
        return DTSDKManagerEx.ip(1);
    }

    @JSStaticFunction
    public static Object getEntity() {
        return n.Av();
    }

    @JSStaticFunction
    public static int getInventorySlot(int paramInt) {
        return DTSDKManagerEx.V(paramInt, 0);
    }

    @JSStaticFunction
    public static int getInventorySlotCount(int paramInt) {
        return DTSDKManagerEx.V(paramInt, 2);
    }

    @JSStaticFunction
    public static int getInventorySlotData(int paramInt) {
        return DTSDKManagerEx.V(paramInt, 1);
    }

    @JSStaticFunction
    public static String getName(Object paramObject) {
        if (isPlayer(paramObject)) {
            return n.al(paramObject);
        }
        return "Not a player";
    }

    @JSStaticFunction
    public static int getPointedBlockData() {
        return DTSDKManagerEx.dtNativePlayerGetPointedBlock(17);
    }

    @JSStaticFunction
    public static int getPointedBlockId() {
        return DTSDKManagerEx.dtNativePlayerGetPointedBlock(16);
    }

    @JSStaticFunction
    public static int getPointedBlockSide() {
        return DTSDKManagerEx.dtNativePlayerGetPointedBlock(18);
    }

    @JSStaticFunction
    public static int getPointedBlockX() {
        return DTSDKManagerEx.dtNativePlayerGetPointedBlock(0);
    }

    @JSStaticFunction
    public static int getPointedBlockY() {
        return DTSDKManagerEx.dtNativePlayerGetPointedBlock(1);
    }

    @JSStaticFunction
    public static int getPointedBlockZ() {
        return DTSDKManagerEx.dtNativePlayerGetPointedBlock(2);
    }

    @JSStaticFunction
    public static Object getPointedEntity() {
        return n.Au();
    }

    @JSStaticFunction
    public static int getSelectedSlotId() {
        return DTSDKManagerEx.Cw();
    }

    @JSStaticFunction
    public static double getX() {
        return (double) DTSDKManagerEx.ik(0);
    }

    @JSStaticFunction
    public static double getY() {
        return (double) DTSDKManagerEx.ik(1);
    }

    @JSStaticFunction
    public static double getZ() {
        return (double) DTSDKManagerEx.ik(2);
    }

    @JSStaticFunction
    public static boolean isFlying() {
        return DTSDKManagerEx.dtNativePlayerIsFlying();
    }

    @JSStaticFunction
    public static boolean isPlayer(Object paramObject) {
        return n.am(paramObject);
    }

    @JSStaticFunction
    public static void setArmorSlot(int paramInt1, int paramInt2, int paramInt3) {
        DTSDKManagerEx.B(paramInt1, paramInt2, paramInt3);
    }

    @JSStaticFunction
    public static void setCanFly(boolean paramBoolean) {
        DTSDKManagerEx.dtNativePlayerSetCanFly(paramBoolean);
    }

    @JSStaticFunction
    public static void setFlying(boolean paramBoolean) {
        DTSDKManagerEx.dtNativePlayerSetFlying(paramBoolean);
    }

    @JSStaticFunction
    public static void setHealth(int paramInt) {
        DTSDKManagerEx.il(paramInt);
    }

    public String getClassName() {
        return "Player";
    }
}
