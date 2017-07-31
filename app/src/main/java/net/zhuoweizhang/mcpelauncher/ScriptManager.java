package net.zhuoweizhang.mcpelauncher;

import android.content.Context;
import android.os.Environment;
import com.huluxia.mcjavascript.f;
import com.huluxia.mcsdk.DTGameCallBack;
import com.huluxia.mcsdk.DTSDKManager;
import com.huluxia.mcsdk.DTSDKManager.c;
import com.huluxia.mcsdk.b;
import com.mojang.minecraftpe.MainActivity;
import com.mojang.minecraftpe.a;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.zhuoweizhang.mcpelauncher.texture.d;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class ScriptManager {
    private static final int AMOUNT = 2;
    private static final int AXIS_X = 0;
    private static final int AXIS_Y = 1;
    private static final int AXIS_Z = 2;
    private static final int DAMAGE = 1;
    private static final boolean DEBUG = false;
    private static final int ITEMID = 0;
    public static int ITEM_ID_COUNT = 0;
    public static final int MAX_NUM_ERRORS = 5;
    private static final boolean PINGGLE_DEBUG = false;
    public static final String SCRIPTS_DIR = "modscripts";
    private static final String TAG = "ScriptManager";
    public static Context androidContext;
    private static boolean bPlayerDeathFlag = false;
    private static String currentScript = "Unknown";
    public static MainActivity dTMainActivity;
    public static Set<String> enabledScripts = new HashSet();
    private static NativeArray entityList;
    private static Map<Integer, String> entityUUIDMap = new HashMap();
    public static boolean hasLevel = false;
    public static boolean isRemote = false;
    private static long lastSyncTimeMillis = 0;
    private static int leaveGameFlag = 0;
    public static d modPkgTexturePack = new d("resourcepacks/vanilla/");
    private static long nUpdatePlayerPositionTimeMillis = 0;
    public static float newPlayerPitch = 0.0f;
    public static float newPlayerYaw = 0.0f;
    private static boolean nextTickCallsSetLevel = false;
    private static boolean requestLeaveGame = false;
    private static boolean requestReloadAllScripts = false;
    private static boolean requestedGraphicsReset = false;
    public static String screenshotFileName = "";
    private static boolean scriptingEnabled = true;
    private static boolean scriptingInitialized = false;
    public static boolean sensorEnabled = false;
    private static String serverAddress = null;
    private static int serverPort = 0;
    public static String worldDir;
    public static String worldName;

    public static native void nativeAddFurnaceRecipe(int i, int i2, int i3);

    public static native void nativeAddItemChest(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static native void nativeAddItemCreativeInv(int i, int i2, int i3);

    public static native void nativeAddItemFurnace(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static native void nativeAddItemInventory(int i, int i2, int i3);

    public static native void nativeAddShapedRecipe(int i, int i2, int i3, String[] strArr, int[] iArr);

    public static native String nativeBiomeIdToName(int i);

    public static native void nativeBlockSetCollisionEnabled(int i, boolean z);

    public static native void nativeBlockSetColor(int i, int[] iArr);

    public static native void nativeBlockSetDestroyTime(int i, float f);

    public static native void nativeBlockSetExplosionResistance(int i, float f);

    public static native void nativeBlockSetLightLevel(int i, int i2);

    public static native void nativeBlockSetLightOpacity(int i, int i2);

    public static native void nativeBlockSetRenderLayer(int i, int i2);

    public static native void nativeBlockSetShape(int i, float f, float f2, float f3, float f4, float f5, float f6);

    public static native void nativeBlockSetShape(int i, float f, float f2, float f3, float f4, float f5, float f6, int i2);

    public static native void nativeBlockSetStepSound(int i, int i2);

    public static native void nativeClearCapes();

    public static native void nativeClearSlotInventory(int i);

    public static native void nativeClientMessage(String str);

    public static native void nativeCloseScreen();

    public static native void nativeDefineArmor(int i, String str, int i2, String str2, String str3, int i3, int i4, int i5);

    public static native void nativeDefineBlock(int i, String str, String[] strArr, int[] iArr, int i2, boolean z, int i3);

    public static native void nativeDefineFoodItem(int i, String str, int i2, int i3, String str2, int i4);

    public static native void nativeDefineItem(int i, String str, int i2, String str2, int i3);

    public static native void nativeDefinePlaceholderBlocks();

    public static native void nativeDestroyBlock(int i, int i2, int i3);

    public static native long nativeDropItem(float f, float f2, float f3, float f4, int i, int i2, int i3);

    public static native void nativeDumpVtable(String str, int i);

    public static native String nativeEntityGetMobSkin(int i);

    public static native String nativeEntityGetMobSkin(long j);

    public static native String nativeEntityGetNameTag(int i);

    public static native String nativeEntityGetNameTag(long j);

    public static native int nativeEntityGetRenderType(int i);

    public static native int nativeEntityGetRenderType(long j);

    public static native int nativeEntityGetRider(int i);

    public static native int nativeEntityGetRider(long j);

    public static native int nativeEntityGetRiding(int i);

    public static native int nativeEntityGetRiding(long j);

    public static native long[] nativeEntityGetUUID(int i);

    public static native long[] nativeEntityGetUUID(long j);

    public static native void nativeEntitySetNameTag(int i, String str);

    public static native void nativeEntitySetNameTag(long j, String str);

    public static native void nativeEntitySetSize(int i, float f, float f2);

    public static native void nativeEntitySetSize(long j, float f, float f2);

    public static native void nativeExplode(float f, float f2, float f3, float f4);

    public static native void nativeExtinguishFire(int i, int i2, int i3, int i4);

    public static native void nativeForceCrash();

    public static native void nativeGetAllEntities();

    public static native int nativeGetAnimalAge(int i);

    public static native int nativeGetAnimalAge(long j);

    public static native int nativeGetArch();

    public static native int nativeGetBlockRenderShape(int i);

    public static native int nativeGetBrightness(int i, int i2, int i3);

    public static native int nativeGetCarriedItem(int i);

    public static native int nativeGetData(int i, int i2, int i3);

    public static native float nativeGetEntityLoc(int i, int i2);

    public static native float nativeGetEntityLoc(long j, int i);

    public static native int nativeGetEntityTypeId(int i);

    public static native int nativeGetEntityTypeId(long j);

    public static native float nativeGetEntityVel(int i, int i2);

    public static native float nativeGetEntityVel(long j, int i);

    public static native int nativeGetGameType();

    public static native int nativeGetItemChest(int i, int i2, int i3, int i4);

    public static native int nativeGetItemCountChest(int i, int i2, int i3, int i4);

    public static native int nativeGetItemCountFurnace(int i, int i2, int i3, int i4);

    public static native int nativeGetItemDataChest(int i, int i2, int i3, int i4);

    public static native int nativeGetItemDataFurnace(int i, int i2, int i3, int i4);

    public static native int nativeGetItemFurnace(int i, int i2, int i3, int i4);

    public static native int nativeGetItemIdCount();

    public static native String nativeGetItemName(int i, int i2, boolean z);

    public static native long nativeGetLevel();

    public static native int nativeGetMobHealth(int i);

    public static native int nativeGetMobHealth(long j);

    public static native float nativeGetPitch(int i);

    public static native float nativeGetPitch(long j);

    public static native long nativeGetPlayerEnt();

    public static native float nativeGetPlayerLoc(int i);

    public static native String nativeGetPlayerName(int i);

    public static native String nativeGetPlayerName(long j);

    public static native int nativeGetSelectedSlotId();

    public static native String nativeGetSignText(int i, int i2, int i3, int i4);

    public static native int nativeGetSlotArmor(int i, int i2);

    public static native int nativeGetSlotInventory(int i, int i2);

    public static native boolean nativeGetTextureCoordinatesForItem(int i, int i2, float[] fArr);

    public static native int nativeGetTile(int i, int i2, int i3);

    public static native long nativeGetTime();

    public static native float nativeGetYaw(int i);

    public static native float nativeGetYaw(long j);

    public static native void nativeHurtTo(int i);

    public static native boolean nativeIsSneaking(long j);

    public static native boolean nativeIsValidCommand(String str);

    public static native void nativeJoinServer(String str, int i);

    public static native void nativeLeaveGame(boolean z);

    public static native void nativeLevelAddParticle(int i, float f, float f2, float f3, float f4, float f5, float f6, int i2);

    public static native int nativeLevelGetBiome(int i, int i2);

    public static native String nativeLevelGetBiomeName(int i, int i2);

    public static native int nativeLevelGetGrassColor(int i, int i2);

    public static native boolean nativeLevelIsRemote();

    public static native void nativeLevelSetBiome(int i, int i2, int i3);

    public static native void nativeLevelSetGrassColor(int i, int i2, int i3);

    public static native void nativeLevelSetLightningLevel(float f);

    public static native void nativeLevelSetRainLevel(float f);

    public static native void nativeMobAddEffect(long j, int i, int i2, int i3, boolean z, boolean z2);

    public static native int nativeMobGetArmor(long j, int i, int i2);

    public static native void nativeMobRemoveAllEffects(long j);

    public static native void nativeMobRemoveEffect(long j, int i);

    public static native void nativeMobSetArmor(long j, int i, int i2, int i3);

    public static native void nativeOnGraphicsReset();

    public static native void nativePlaySound(float f, float f2, float f3, String str, float f4, float f5);

    public static native boolean nativePlayerCanFly();

    public static native int nativePlayerGetPointedBlock(int i);

    public static native long nativePlayerGetPointedEntity();

    public static native boolean nativePlayerIsFlying();

    public static native void nativePlayerSetCanFly(boolean z);

    public static native void nativePlayerSetFlying(boolean z);

    public static native void nativePrePatch();

    public static native void nativePrePatch(boolean z, MainActivity mainActivity);

    public static native void nativePrePatch(boolean z, MainActivity mainActivity, boolean z2);

    public static native void nativePrePatch(boolean z, MainActivity mainActivity, boolean z2, boolean z3, boolean z4);

    public static native void nativePreventDefault();

    public static native void nativeRemoveEntity(int i);

    public static native void nativeRemoveEntity(long j);

    public static native void nativeRemoveItemBackground();

    public static native void nativeRequestFrameCallback();

    public static native void nativeRideAnimal(int i, int i2);

    public static native void nativeRideAnimal(long j, long j2);

    public static native void nativeScreenChooserSetScreen(int i);

    public static native void nativeSelectLevel(String str);

    public static native void nativeSendChat(String str);

    public static native void nativeSetAnimalAge(int i, int i2);

    public static native void nativeSetAnimalAge(long j, int i);

    public static native void nativeSetArmorSlot(int i, int i2, int i3);

    public static native void nativeSetBlockRenderShape(int i, int i2);

    public static native void nativeSetCameraEntity(int i);

    public static native void nativeSetCape(long j, String str);

    public static native void nativeSetCarriedItem(int i, int i2, int i3, int i4);

    public static native void nativeSetCarriedItem(long j, int i, int i2, int i3);

    public static native void nativeSetEntityRenderType(int i, int i2);

    public static native void nativeSetEntityRenderType(long j, int i);

    public static native void nativeSetFov(float f, boolean z);

    public static native void nativeSetGameSpeed(float f);

    public static native void nativeSetGameType(int i);

    public static native void nativeSetHandEquipped(int i, boolean z);

    public static native void nativeSetI18NString(String str, String str2);

    public static native void nativeSetInventorySlot(int i, int i2, int i3, int i4);

    public static native void nativeSetIsRecording(boolean z);

    public static native void nativeSetItemCategory(int i, int i2, int i3);

    public static native void nativeSetItemMaxDamage(int i, int i2);

    public static native void nativeSetMobHealth(int i, int i2);

    public static native void nativeSetMobHealth(long j, int i);

    public static native void nativeSetMobMaxHealth(long j, int i);

    public static native void nativeSetMobSkin(int i, String str);

    public static native void nativeSetMobSkin(long j, String str);

    public static native void nativeSetNightMode(boolean z);

    public static native void nativeSetOnFire(int i, int i2);

    public static native void nativeSetOnFire(long j, int i);

    public static native void nativeSetPosition(int i, float f, float f2, float f3);

    public static native void nativeSetPosition(long j, float f, float f2, float f3);

    public static native void nativeSetPositionRelative(int i, float f, float f2, float f3);

    public static native void nativeSetPositionRelative(long j, float f, float f2, float f3);

    public static native void nativeSetRot(int i, float f, float f2);

    public static native void nativeSetRot(long j, float f, float f2);

    public static native void nativeSetSignText(int i, int i2, int i3, int i4, String str);

    public static native void nativeSetSneaking(int i, boolean z);

    public static native void nativeSetSneaking(long j, boolean z);

    public static native void nativeSetSpawn(int i, int i2, int i3);

    public static native void nativeSetStonecutterItem(int i, int i2);

    public static native void nativeSetTextParseColorCodes(boolean z);

    public static native void nativeSetTile(int i, int i2, int i3, int i4, int i5);

    public static native void nativeSetTime(long j);

    public static native void nativeSetUseController(boolean z);

    public static native void nativeSetVel(int i, float f, int i2);

    public static native void nativeSetVel(long j, float f, int i);

    public static native void nativeSetupHooks();

    public static native void nativeSetupHooks(int i);

    public static native void nativeShowProgressScreen();

    public static native void nativeShowTipMessage(String str);

    public static native long nativeSpawnEntity(float f, float f2, float f3, int i, String str);

    public static native void nativeSpawnerSetEntityType(int i, int i2, int i3, int i4);

    static {
        ITEM_ID_COUNT = 0;
        ITEM_ID_COUNT = 512;
    }

    public static long getnUpdatePlayerPositionTimeMillis() {
        return nUpdatePlayerPositionTimeMillis;
    }

    public static void setnUpdatePlayerPositionTimeMillis(long nUpdatePlayerPositionTimeMillis) {
        nUpdatePlayerPositionTimeMillis = nUpdatePlayerPositionTimeMillis;
    }

    public static byte[] getSoundBytes(String paramString) {
        return a.getSoundBytes(paramString);
    }

    public static InputStream getSoundInputStream(String paramString, long[] paramArrayOfLong) {
        return a.getSoundInputStream(paramString, paramArrayOfLong);
    }

    private static void appendApiMethodDescription(StringBuilder paramStringBuilder, Method paramMethod, String paramString) {
        if (paramString != null) {
            paramStringBuilder.append(paramString);
            paramStringBuilder.append('.');
        }
        paramStringBuilder.append(paramMethod.getName());
        paramStringBuilder.append('(');
        Class[] arrayOfClass = paramMethod.getParameterTypes();
        for (int i = 0; i < arrayOfClass.length; i++) {
            paramStringBuilder.append("par");
            paramStringBuilder.append(i + 1);
            paramStringBuilder.append(arrayOfClass[i].getSimpleName().replaceAll("Native", ""));
            if (i < arrayOfClass.length - 1) {
                paramStringBuilder.append(", ");
            }
        }
        paramStringBuilder.append(");\n");
    }

    private static void appendApiMethods(StringBuilder paramStringBuilder, Class<?> paramClass, String paramString) {
        for (Method localMethod : paramClass.getMethods()) {
            if (localMethod.getAnnotation(JSFunction.class) != null || localMethod.getAnnotation(JSStaticFunction.class) != null) {
                appendApiMethodDescription(paramStringBuilder, localMethod, paramString);
            }
        }
        paramStringBuilder.append(SpecilApiUtil.LINE_SEP);
    }

    public static void attackCallback(int paramInt1, int paramInt2) {
        DTGameCallBack.attackCallback(paramInt1, paramInt2);
    }

    public static void attackCallback(long paramInt1, long paramInt2) {
        DTGameCallBack.attackCallback(paramInt1, paramInt2);
    }

    public static void blockEventCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        DTGameCallBack.blockEventCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }

    public static void chatCallback(String str) {
        DTGameCallBack.chatCallback(str);
    }

    private static void debugDumpItems() throws IOException {
        PrintWriter localPrintWriter = new PrintWriter(new File(Environment.getExternalStorageDirectory(), "/items.csv"));
        float[] textureUVbuf = new float[6];
        for (int i = 0; i < 5120; i++) {
            String itemName = nativeGetItemName(i, 0, true);
            if (itemName != null) {
                boolean success = nativeGetTextureCoordinatesForItem(i, 0, textureUVbuf);
                localPrintWriter.println(i + MiPushClient.ACCEPT_TIME_SEPARATOR + itemName + MiPushClient.ACCEPT_TIME_SEPARATOR + Arrays.toString(textureUVbuf).replace("[", "").replace("]", "").replace(MiPushClient.ACCEPT_TIME_SEPARATOR, "|"));
            }
        }
        localPrintWriter.close();
    }

    public static void destroy() {
        scriptingInitialized = false;
        androidContext = null;
        f.ajG.clear();
        DTSDKManager.runOnMainThreadList.clear();
    }

    public static void destroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        DTGameCallBack.destroyBlockCallback(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static void entityAddedCallback(int paramInt) {
        b.entityAddedCallback(paramInt);
    }

    public static void entityRemovedCallback(int paramInt) {
        DTGameCallBack.entityRemovedCallback(paramInt);
    }

    public static void entityRemovedCallback(long paramInt) {
        DTGameCallBack.entityRemovedCallback(paramInt);
    }

    public static void explodeCallback(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
        DTGameCallBack.explodeCallback(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
    }

    public static void frameCallback() {
        f.Ao();
    }

    public static void handleChatPacketCallback(String paramString) {
        DTGameCallBack.handleChatPacketCallback(paramString);
    }

    public static void handleMessagePacketCallback(String sender, String str) {
        DTGameCallBack.handleMessagePacketCallback(sender, str);
    }

    public static void eatCallback(int paramInt, float paramFloat) {
        b.eatCallback(paramInt, paramFloat);
    }

    private static boolean isLocalAddress(String paramString) {
        try {
            InetAddress localInetAddress = InetAddress.getByName(paramString);
            if (localInetAddress.isLoopbackAddress() || localInetAddress.isLinkLocalAddress()) {
                return true;
            }
            return !localInetAddress.isSiteLocalAddress() ? false : false;
        } catch (UnknownHostException localUnknownHostException) {
            localUnknownHostException.printStackTrace();
            return false;
        }
    }

    public static void leaveGameCallback(boolean paramBoolean) {
        b.leaveGameCallback(paramBoolean);
    }

    public static void levelEventCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
        DTGameCallBack.levelEventCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    }

    public static void mobDieCallback(int paramInt1, int paramInt2) {
        DTGameCallBack.mobDieCallback(paramInt1, paramInt2);
    }

    public static void setEnabled(File paramFile, boolean paramBoolean) throws IOException {
    }

    public static void setEnabled(File[] paramArrayOfFile, boolean paramBoolean) throws IOException {
    }

    public static void callScriptMethod(String paramString, Object[] paramArrayOfObject) {
    }

    public static ScriptableObject classConstantsToJSObject(Class<?> cls) {
        return null;
    }

    private static void clearTextureOverride(String paramString) {
    }

    public static void clearTextureOverrides() {
    }

    public static void continueDestroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat) {
        b.continueDestroyBlockCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramFloat);
    }

    public static void playerAddLevelsCallback(long paramLong, int paramInt) {
        b.playerAddLevelsCallback(paramLong, paramInt);
    }

    public static void playerAddExperienceCallback(long paramLong, int paramInt) {
        b.playerAddExperienceCallback(paramLong, paramInt);
    }

    public static void dummyThrowableHitEntityCallback() {
    }

    public static void redstoneUpdateCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6) {
        b.redstoneUpdateCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, paramInt5, paramInt6);
    }

    public static void entityHurtCallback(long paramLong1, long paramLong2, int paramInt) {
        b.entityHurtCallback(paramLong1, paramLong2, paramInt);
    }

    public static void entityAddedCallback(long paramLong) {
        b.entityAddedCallback(paramLong);
    }

    public static int[] expandColorsArray(Scriptable paramScriptable) {
        return null;
    }

    public static c expandTexturesArray(Object paramObject) {
        return null;
    }

    public static void explodeCallback(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
        DTGameCallBack.explodeCallback(paramLong, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
    }

    public static String getAllApiMethodsDescriptions() {
        return null;
    }

    public static Set<String> getEnabledScripts() {
        return null;
    }

    public static File getScriptFile(String paramString) {
        return null;
    }

    public static File getTextureOverrideFile(String paramString) {
        return null;
    }

    public static void initJustLoadedScript(org.mozilla.javascript.Context paramContext, Script paramScript, String paramString) {
    }

    public static boolean isEnabled(File paramFile) {
        return true;
    }

    public static void loadEnabledScriptsNames(Context paramContext) {
    }

    public static void loadScript(File paramFile) throws IOException {
    }

    public static void loadScript(Reader paramReader, String paramString) throws IOException {
    }

    public static void loadScriptFromInstance(Script paramScript, String paramString) {
    }

    public static void mobDieCallback(long paramLong1, long paramLong2) {
        DTGameCallBack.mobDieCallback(paramLong1, paramLong2);
    }

    public static void processDebugCommand(String paramString) {
    }

    public static void reloadScript(File paramFile) throws IOException {
    }

    public static void removeDeadEntries(Collection<String> collection) {
    }

    public static void removeScript(String paramString) {
    }

    public static void runOnMainThread(Runnable paramRunnable) {
    }

    private static void scriptPrint(String paramString) {
    }

    public static void setupContext(org.mozilla.javascript.Context paramContext) {
    }

    private static boolean shouldLoadSkin() {
        return false;
    }

    public static void takeScreenshot(String paramString) {
    }

    public static void dtNativeAddShapedRecipe(int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString, int[] paramArrayOfInt) {
        nativeAddShapedRecipe(paramInt1, paramInt2, paramInt3, paramArrayOfString, paramArrayOfInt);
    }

    public static String dtNativeBiomeIdToName(int paramInt) {
        return nativeBiomeIdToName(paramInt);
    }

    public static void dtNativeEntitySetSize(int paramInt, float paramFloat1, float paramFloat2) {
        nativeEntitySetSize(paramInt, paramFloat1, paramFloat2);
    }

    public static void dtNativeJoinServer(String paramString, int paramInt) {
        nativeJoinServer(paramString, paramInt);
    }

    public static void dtNativeLeaveGame(boolean paramBoolean) {
        nativeLeaveGame(paramBoolean);
    }

    public static void dtNativeLevelAddParticle(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt2) {
        nativeLevelAddParticle(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramInt2);
    }

    public static int dtNativeLevelGetBiome(int paramInt1, int paramInt2) {
        return nativeLevelGetBiome(paramInt1, paramInt2);
    }

    public static String dtNativeLevelGetBiomeName(int paramInt1, int paramInt2) {
        return nativeLevelGetBiomeName(paramInt1, paramInt2);
    }

    public static int dtNativeLevelGetGrassColor(int paramInt1, int paramInt2) {
        return nativeLevelGetGrassColor(paramInt1, paramInt2);
    }

    public static boolean dtNativeLevelIsRemote() {
        return nativeLevelIsRemote();
    }

    public static void dtNativeLevelSetGrassColor(int paramInt1, int paramInt2, int paramInt3) {
        nativeLevelSetGrassColor(paramInt1, paramInt2, paramInt3);
    }

    public static boolean dtNativePlayerCanFly() {
        return nativePlayerCanFly();
    }

    public static int dtNativePlayerGetPointedBlock(int paramInt) {
        return nativePlayerGetPointedBlock(paramInt);
    }

    public static boolean dtNativePlayerIsFlying() {
        return nativePlayerIsFlying();
    }

    public static void dtNativePlayerSetCanFly(boolean paramBoolean) {
        nativePlayerSetCanFly(paramBoolean);
    }

    public static void dtNativePlayerSetFlying(boolean paramBoolean) {
        nativePlayerSetFlying(paramBoolean);
    }

    public static void dtNativeSetFov(float paramFloat, boolean paramBoolean) {
        nativeSetFov(paramFloat, paramBoolean);
    }

    public static void rakNetConnectCallback(String paramString, int paramInt) {
        DTGameCallBack.rakNetConnectCallback(paramString, paramInt);
    }

    public static void requestGraphicsReset() {
        requestedGraphicsReset = true;
    }

    public static void selectLevelCallback(String paramString1, String paramString2) {
        b.selectLevelCallback(paramString1, paramString2);
    }

    public static void setLevelCallback(boolean hasLevel, boolean isRemote) {
        DTGameCallBack.setLevelCallback(hasLevel, isRemote);
    }

    public static void setLevelFakeCallback(boolean hasLevel, boolean isRemoteAAAAAA) {
        DTGameCallBack.setLevelFakeCallback(hasLevel, isRemoteAAAAAA);
    }

    public static void startDestroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        DTGameCallBack.startDestroyBlockCallback(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static void throwableHitCallback(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong2) {
        DTGameCallBack.throwableHitCallback(paramLong1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramFloat1, paramFloat2, paramFloat3, paramLong2);
    }

    public static void tickCallback() {
        DTGameCallBack.tickCallback();
        if (requestedGraphicsReset) {
            nativeOnGraphicsReset();
            requestedGraphicsReset = false;
        }
    }

    private static void updatePlayerOrientation() {
        nativeSetRot(nativeGetPlayerEnt(), newPlayerYaw, newPlayerPitch);
    }

    public static void useItemOnCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
        DTGameCallBack.useItemOnCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
    }

    public static void screenChangeCallback(String paramString1, String paramString2, String paramString3) {
    }
}
