package com.MCWorld.mcsdk;

import android.content.Context;
import com.MCWorld.mcgame.h;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class DTSDKManager {
    private static final boolean DEBUG = false;
    private static final boolean PINGGLE_DEBUG = false;
    private static final String TAG = "DTSDKManager";
    public static Context androidContext;
    public static String currentScript;
    public static boolean isRemote;
    public static a requestJoinServer;
    public static boolean requestLeaveGame;
    public static b requestSelectLevel;
    private static boolean requestedGraphicsReset;
    public static List<Runnable> runOnMainThreadList = new ArrayList();
    public static String serverAddress;
    public static int serverPort;
    public static String worldDir;
    public static String worldName;

    public static class a {
        public String serverAddress;
        public int serverPort;
    }

    public static class b {
        public int anJ = 0;
        public String anK;
        public String dir;
        public String name;
    }

    public static class c {
        public int[] anL;
        public String[] anM;
    }

    public static native void nativeAddFurnaceRecipe(int i, int i2, int i3);

    public static native void nativeAddItemChest(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static native void nativeAddItemCreativeInv(int i, int i2, int i3);

    public static native void nativeAddItemFurnace(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static native void nativeAddItemInventory(int i, int i2, int i3);

    public static native void nativeAddShapedRecipe(int i, int i2, int i3, String[] strArr, int[] iArr);

    public static native String nativeBiomeIdToName(int i);

    public static native void nativeBlockSetColor(int i, int[] iArr);

    public static native void nativeBlockSetDestroyTime(int i, float f);

    public static native void nativeBlockSetExplosionResistance(int i, float f);

    public static native void nativeBlockSetLightLevel(int i, int i2);

    public static native void nativeBlockSetLightOpacity(int i, int i2);

    public static native void nativeBlockSetRenderLayer(int i, int i2);

    public static native void nativeBlockSetShape(int i, float f, float f2, float f3, float f4, float f5, float f6);

    public static native void nativeClearSlotInventory(int i);

    public static native void nativeClientMessage(String str);

    public static native void nativeDefineBlock(int i, String str, String[] strArr, int[] iArr, int i2, boolean z, int i3);

    public static native void nativeDefineFoodItem(int i, String str, int i2, int i3, String str2, int i4);

    public static native void nativeDefineItem(int i, String str, int i2, String str2, int i3);

    public static native void nativeDestroyBlock(int i, int i2, int i3);

    public static native int nativeDropItem(float f, float f2, float f3, float f4, int i, int i2, int i3);

    public static native String nativeEntityGetMobSkin(int i);

    public static native String nativeEntityGetNameTag(int i);

    public static native int nativeEntityGetRenderType(int i);

    public static native int nativeEntityGetRider(int i);

    public static native int nativeEntityGetRiding(int i);

    public static native long[] nativeEntityGetUUID(int i);

    public static native void nativeEntitySetNameTag(int i, String str);

    public static native void nativeEntitySetSize(int i, float f, float f2);

    public static native void nativeExplode(float f, float f2, float f3, float f4);

    public static native int nativeGetAnimalAge(int i);

    public static native int nativeGetBlockRenderShape(int i);

    public static native int nativeGetBrightness(int i, int i2, int i3);

    public static native int nativeGetCarriedItem(int i);

    public static native int nativeGetData(int i, int i2, int i3);

    public static native float nativeGetEntityLoc(int i, int i2);

    public static native int nativeGetEntityTypeId(int i);

    public static native float nativeGetEntityVel(int i, int i2);

    public static native int nativeGetGameType();

    public static native int nativeGetItemChest(int i, int i2, int i3, int i4);

    public static native int nativeGetItemCountChest(int i, int i2, int i3, int i4);

    public static native int nativeGetItemCountFurnace(int i, int i2, int i3, int i4);

    public static native int nativeGetItemDataChest(int i, int i2, int i3, int i4);

    public static native int nativeGetItemDataFurnace(int i, int i2, int i3, int i4);

    public static native int nativeGetItemFurnace(int i, int i2, int i3, int i4);

    public static native String nativeGetItemName(int i, int i2, boolean z);

    public static native long nativeGetLevel();

    public static native int nativeGetMobHealth(int i);

    public static native float nativeGetPitch(int i);

    public static native int nativeGetPlayerEnt();

    public static native float nativeGetPlayerLoc(int i);

    public static native String nativeGetPlayerName(int i);

    public static native int nativeGetSelectedSlotId();

    public static native String nativeGetSignText(int i, int i2, int i3, int i4);

    public static native int nativeGetSlotArmor(int i, int i2);

    public static native int nativeGetSlotInventory(int i, int i2);

    public static native int nativeGetTile(int i, int i2, int i3);

    public static native long nativeGetTime();

    public static native float nativeGetYaw(int i);

    public static native void nativeHurtTo(int i);

    public static native void nativeLeaveGame(boolean z);

    public static native void nativeLevelAddParticle(int i, float f, float f2, float f3, float f4, float f5, float f6, int i2);

    public static native int nativeLevelGetBiome(int i, int i2);

    public static native String nativeLevelGetBiomeName(int i, int i2);

    public static native int nativeLevelGetGrassColor(int i, int i2);

    public static native boolean nativeLevelIsRemote();

    public static native void nativeLevelSetGrassColor(int i, int i2, int i3);

    public static native void nativePlaySound(float f, float f2, float f3, String str, float f4, float f5);

    public static native boolean nativePlayerCanFly();

    public static native int nativePlayerGetPointedBlock(int i);

    public static native int nativePlayerGetPointedEntity();

    public static native boolean nativePlayerIsFlying();

    public static native void nativePlayerSetCanFly(boolean z);

    public static native void nativePlayerSetFlying(boolean z);

    public static native void nativePrePatch();

    public static native void nativePreventDefault();

    public static native void nativeRemoveEntity(int i);

    public static native void nativeRequestFrameCallback();

    public static native void nativeRideAnimal(int i, int i2);

    public static native void nativeSendChat(String str);

    public static native void nativeSetAnimalAge(int i, int i2);

    public static native void nativeSetArmorSlot(int i, int i2, int i3);

    public static native void nativeSetBlockRenderShape(int i, int i2);

    public static native void nativeSetCameraEntity(int i);

    public static native void nativeSetCarriedItem(int i, int i2, int i3, int i4);

    public static native void nativeSetEntityRenderType(int i, int i2);

    public static native void nativeSetEntityRenderType(long j, int i);

    public static native void nativeSetFov(float f, boolean z);

    public static native void nativeSetGameSpeed(float f);

    public static native void nativeSetGameType(int i);

    public static native void nativeSetI18NString(String str, String str2);

    public static native void nativeSetItemCategory(int i, int i2, int i3);

    public static native void nativeSetItemMaxDamage(int i, int i2);

    public static native void nativeSetMobHealth(int i, int i2);

    public static native void nativeSetMobSkin(int i, String str);

    public static native void nativeSetNightMode(boolean z);

    public static native void nativeSetOnFire(int i, int i2);

    public static native void nativeSetPosition(int i, float f, float f2, float f3);

    public static native void nativeSetPositionRelative(int i, float f, float f2, float f3);

    public static native void nativeSetRot(int i, float f, float f2);

    public static native void nativeSetSignText(int i, int i2, int i3, int i4, String str);

    public static native void nativeSetSneaking(int i, boolean z);

    public static native void nativeSetSneaking(long j, boolean z);

    public static native void nativeSetSpawn(int i, int i2, int i3);

    public static native void nativeSetTile(int i, int i2, int i3, int i4, int i5);

    public static native void nativeSetTime(long j);

    public static native void nativeSetVel(int i, float f, int i2);

    public static native void nativeSetupHooks(int i);

    public static native void nativeShowTipMessage(String str);

    public static native int nativeSpawnEntity(float f, float f2, float f3, int i, String str);

    public static void runOnMainThread(Runnable paramRunnable) {
        DTSDKManagerEx.runOnMainThread(paramRunnable);
    }

    public static String getSkinURL(String paramString) {
        return "http://blskins.herokuapp.com/blskins/" + paramString + hlx.data.localstore.a.bKa;
    }

    public static File getTextureOverrideFile(String paramString) {
        if (androidContext == null) {
            return null;
        }
        return new File(new File(h.wo().wp(), "textures"), paramString.replace("..", ""));
    }

    public static void clearTextureOverride(String paramString) {
        File localFile = getTextureOverrideFile(paramString);
        if (localFile != null && localFile.exists()) {
            localFile.delete();
        }
        requestedGraphicsReset = true;
    }

    public static void overrideTexture(String paramString1, String paramString2) {
        DTSDKManagerEx.overrideTexture(paramString1, paramString2);
    }

    public static boolean isValidStringParameter(String paramString) {
        return !DTSDKManagerEx.dy(paramString);
    }

    public static int[] expandShapelessRecipe(Scriptable inArrayScriptable) {
        int inArrayLength = ((Number) ScriptableObject.getProperty(inArrayScriptable, "length")).intValue();
        int[] endArray = null;
        if (ScriptableObject.getProperty(inArrayScriptable, 0) instanceof Number) {
            if (inArrayLength % 3 != 0) {
                throw new IllegalArgumentException("Array length must be multiple of 3 (this was changed in 1.6.8): [itemid, itemCount, itemdamage, ...]");
            }
            endArray = new int[inArrayLength];
            for (int i = 0; i < endArray.length; i++) {
                endArray[i] = ((Number) ScriptableObject.getProperty(inArrayScriptable, i)).intValue();
            }
        }
        return endArray;
    }

    public static c expandTexturesArray(Object inArrayObj) {
        int[] endArray = new int[96];
        String[] stringArray = new String[96];
        c retval = new c();
        retval.anL = endArray;
        retval.anM = stringArray;
        if (inArrayObj instanceof String) {
            Arrays.fill(stringArray, (String) inArrayObj);
        } else {
            Scriptable inArrayScriptable = (Scriptable) inArrayObj;
            int inArrayLength = ((Number) ScriptableObject.getProperty(inArrayScriptable, "length")).intValue();
            int wrap = inArrayLength % 6 == 0 ? 6 : 1;
            Object firstObj = ScriptableObject.getProperty(inArrayScriptable, 0);
            if ((inArrayLength == 1 || inArrayLength == 2) && (firstObj instanceof String)) {
                Arrays.fill(stringArray, (String) firstObj);
                if (inArrayLength == 2) {
                    Arrays.fill(endArray, ((Number) ScriptableObject.getProperty(inArrayScriptable, 1)).intValue());
                }
            } else {
                for (int i = 0; i < endArray.length; i++) {
                    Scriptable myObj;
                    if (i < inArrayLength) {
                        myObj = ScriptableObject.getProperty(inArrayScriptable, i);
                    } else {
                        myObj = ScriptableObject.getProperty(inArrayScriptable, i % wrap);
                    }
                    Scriptable myScriptable = myObj;
                    String texName = (String) ScriptableObject.getProperty(myScriptable, 0);
                    endArray[i] = ((Number) ScriptableObject.getProperty(myScriptable, 1)).intValue();
                    stringArray[i] = texName;
                }
            }
        }
        return retval;
    }

    public static int[] expandColorsArray(Scriptable inArrayScriptable) {
        int inArrayLength = ((Number) ScriptableObject.getProperty(inArrayScriptable, "length")).intValue();
        int[] endArray = new int[16];
        for (int i = 0; i < endArray.length; i++) {
            if (i < inArrayLength) {
                endArray[i] = (int) ((Number) ScriptableObject.getProperty(inArrayScriptable, i)).longValue();
            } else {
                endArray[i] = (int) ((Number) ScriptableObject.getProperty(inArrayScriptable, 0)).longValue();
            }
        }
        return endArray;
    }

    public static void quit() {
        DTSDKManagerEx.quit();
    }

    public static void init(Context cxt) {
        DTSDKManagerEx.init(cxt);
    }
}
