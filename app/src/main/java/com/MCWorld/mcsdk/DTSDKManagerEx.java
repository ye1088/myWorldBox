package com.MCWorld.mcsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.MCWorld.aa.mcspirit;
import com.MCWorld.mcgame.g;
import com.MCWorld.mcgame.m;
import com.MCWorld.mcjavascript.ChatColor;
import com.MCWorld.mcjavascript.d;
import com.MCWorld.mcjavascript.f;
import com.MCWorld.mcsdk.DTSDKManager.c;
import com.MCWorld.mcsdk.dtlib.h;
import com.mojang.minecraftpe.MainActivity;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import io.netty.handler.codec.http2.Http2CodecUtil;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import net.zhuoweizhang.mcpelauncher.ScriptTextureDownloader;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

public class DTSDKManagerEx {
    private static final boolean DEBUG = false;
    private static final String TAG = "DTSDKManagerEx";
    public static net.zhuoweizhang.mcpelauncher.texture.a anN;
    public static net.zhuoweizhang.mcpelauncher.texture.a anO;
    public static net.zhuoweizhang.mcpelauncher.texture.a anP;
    private static boolean anQ = false;

    public static abstract class a {
        private String anR = null;
        private List<NameValuePair> anS = null;
        private boolean anT = false;
        private Runnable anU = new Runnable(this) {
            final /* synthetic */ a anV;

            {
                this.anV = this$0;
            }

            public void run() {
                String strResult;
                if (this.anV.anS == null) {
                    strResult = this.anV.dE(this.anV.anR);
                } else {
                    strResult = this.anV.c(this.anV.anR, this.anV.anS);
                }
                Bundle bundle = new Bundle();
                bundle.putString("ret", strResult);
                Message msg = this.anV.mHandler.obtainMessage(1);
                msg.setData(bundle);
                this.anV.mHandler.sendMessage(msg);
            }
        };
        @SuppressLint({"HandlerLeak"})
        private Handler mHandler = new Handler(this) {
            final /* synthetic */ a anV;

            {
                this.anV = this$0;
            }

            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    this.anV.dv(msg.getData().getString("ret"));
                    this.anV.anT = false;
                }
            }
        };

        protected abstract List<NameValuePair> Ci();

        protected abstract void dv(String str);

        public void dD(String url) {
            if (!this.anT) {
                this.anT = true;
                this.anR = url;
                this.anS = Ci();
                new Thread(this.anU).start();
            }
        }

        public boolean Cz() {
            return this.anT;
        }

        private String c(String strUrl, List<NameValuePair> params) {
            if (params == null) {
                params = new ArrayList();
            }
            try {
                HttpPost httpPost = new HttpPost(strUrl);
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (IOException e) {
            }
            return "";
        }

        private String dE(String strUrl) {
            try {
                String strHead1 = b.a(new short[]{(short) 0, (short) 195, (short) 238, (short) 236, (short) 247, (short) 225, (short) 235, (short) 242, (short) 170, (short) 220, (short) 240, (short) 250, (short) 238});
                String strHead2 = b.a(new short[]{(short) 0, (short) 244, (short) 228, (short) 250, (short) 247, (short) 171, (short) 237, (short) 242, (short) 234, (short) 228, (short) 178, (short) 233, (short) 227, (short) 237, Http2CodecUtil.MAX_UNSIGNED_BYTE, (short) 253, (short) 234, (short) 228, (short) 172, (short) 245, (short) 241, (short) 166, (short) 166, (short) 167, (short) 165});
                HttpGet httpRequest = new HttpGet(strUrl);
                httpRequest.addHeader(strHead1, strHead2);
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(httpResponse.getEntity(), b.a(new short[]{(short) 0, (short) 231, (short) 227, (short) 176, (short) 176, (short) 181, (short) 183}));
                }
            } catch (Exception e) {
            }
            return "";
        }
    }

    private static native void nativePlayerSetFlying(boolean z);

    public static void ch(boolean val) {
        anQ = val;
    }

    public static boolean Cm() {
        return anQ;
    }

    public static void ig(int inputVal) {
    }

    public static byte g(byte inputVal) {
        try {
            return mcspirit.zj(inputVal);
        } catch (Exception e) {
            return (byte) 0;
        }
    }

    public static byte h(byte inputVal) {
        try {
            return mcspirit.zl(inputVal);
        } catch (Exception e) {
            return (byte) 0;
        }
    }

    public static void f(int mode, float level) {
        try {
            if (h.CW().CX() == 2) {
                mcspirit.zk(mode, level);
            } else if (h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
            } else {
                if (mode == 0) {
                    ScriptManager.nativeLevelSetRainLevel(level);
                } else if (1 == mode) {
                    ScriptManager.nativeLevelSetLightningLevel(level);
                }
            }
        } catch (Exception localException) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + localException, new Object[0]);
        }
    }

    public static void ih(int inputId) {
        try {
            if (h.CW().CX() == 2) {
                ScriptManager.nativeMobRemoveAllEffects(ScriptManager.nativeGetPlayerEnt());
            } else if (h.CW().CX() == 3) {
                if (VERSION.SDK_INT < 11) {
                    mcspirit.yy(inputId);
                } else {
                    ScriptManager.nativeMobRemoveAllEffects(ScriptManager.nativeGetPlayerEnt());
                }
            } else if (h.CW().CX() == 5 || h.CW().CZ() == 1) {
                mcspirit.yy(inputId);
            }
        } catch (Exception localException) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + localException, new Object[0]);
        }
    }

    public static void z(int inputId, int inputTime, int inputLevel) {
        try {
            if (h.CW().CX() == 2) {
                ScriptManager.nativeMobAddEffect(ScriptManager.nativeGetPlayerEnt(), inputId, inputTime * 20, inputLevel, false, true);
            } else if (h.CW().CX() == 3) {
                if (VERSION.SDK_INT < 11) {
                    mcspirit.zz(inputId, inputTime, inputLevel);
                    return;
                }
                ScriptManager.nativeMobAddEffect(ScriptManager.nativeGetPlayerEnt(), inputId, inputTime * 20, inputLevel, false, true);
            } else if (h.CW().CX() == 5 || h.CW().CZ() == 1) {
                mcspirit.zz(inputId, inputTime, inputLevel);
            }
        } catch (Exception localException) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + localException, new Object[0]);
        }
    }

    public static void ii(int invinciable) {
        try {
            if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                mcspirit.nativeSetInvinciable(invinciable);
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static int b(long addr, long len, int prot) {
        return mcspirit.d(addr, len, prot);
    }

    public static void a(c requests) {
        if (anN != null) {
            int i = 0;
            while (i < requests.anM.length) {
                if (anN.A(requests.anM[i], requests.anL[i])) {
                    i++;
                } else {
                    throw new IllegalArgumentException("The requested block texture " + requests.anM[i] + ":" + requests.anL[i] + " does not exist");
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void Cn() {
        /*
        r2 = 1;
        r3 = Cm();
        if (r2 != r3) goto L_0x0008;
    L_0x0007:
        return;
    L_0x0008:
        r1 = 0;
        r2 = com.mojang.minecraftpe.MainActivity.currentMainActivity;
        if (r2 == 0) goto L_0x0017;
    L_0x000d:
        r2 = com.mojang.minecraftpe.MainActivity.currentMainActivity;
        r1 = r2.get();
        r1 = (com.mojang.minecraftpe.MainActivity) r1;
        if (r1 != 0) goto L_0x0017;
    L_0x0017:
        r2 = "terrain.meta";
        r2 = a_isRightVersion(r1, r2);	 Catch:{ JSONException -> 0x002a }
        anN = r2;	 Catch:{ JSONException -> 0x002a }
        r2 = "items.meta";
        r2 = a_isRightVersion(r1, r2);	 Catch:{ JSONException -> 0x002a }
        anO = r2;	 Catch:{ JSONException -> 0x002a }
        goto L_0x0007;
    L_0x002a:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.mcsdk.DTSDKManagerEx.Cn():void");
    }

    private static net.zhuoweizhang.mcpelauncher.texture.a a(MainActivity paramMainActivity, String paramString) throws JSONException {
        byte[] data = paramMainActivity.getFileDataBytes("images/" + paramString);
        if (data != null) {
            return new net.zhuoweizhang.mcpelauncher.texture.a(new JSONArray(new String(data, Charset.forName("UTF-8"))));
        }
        return null;
    }

    public static boolean dx(String gameSoPath) {
        return true;
    }

    public static boolean dy(String paramString) {
        return paramString == null || paramString.equals("undefined") || paramString.equals("null");
    }

    public static void ij(int paramInt) {
        try {
            if (h.CW().Da() != 1) {
                if (h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
                    ScriptManager.nativeSetupHooks(paramInt);
                } else if (com.mojang.minecraftpe.a.bHT) {
                    ScriptManager.nativeSetupHooks();
                }
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static void Co() {
        if (h.CW().Da() != 1) {
            ScriptManager.nativePrePatch();
        }
    }

    public static void a(boolean paramBoolean, MainActivity paramMainActivity) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativePrePatch(paramBoolean, paramMainActivity);
        }
    }

    public static void a(boolean paramBoolean, MainActivity paramMainActivity, boolean paramBoolean2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativePrePatch(paramBoolean, paramMainActivity, paramBoolean2);
        }
    }

    public static void a(boolean paramBoolean, MainActivity paramMainActivity, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativePrePatch(paramBoolean, paramMainActivity, paramBoolean2, paramBoolean3, paramBoolean4);
        }
    }

    public static void quit() {
        DTSDKManager.runOnMainThreadList.clear();
        DTSDKManager.runOnMainThreadList = null;
        DTSDKManager.androidContext = null;
    }

    public static int Cp() {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetPlayerEnt();
        }
        return (int) ScriptManager.nativeGetPlayerEnt();
    }

    public static long Cq() {
        if (h.CW().Da() == 1) {
            return mcspirit.v();
        }
        return ScriptManager.nativeGetPlayerEnt();
    }

    public static void a(int entityId, float paramFloat1, float paramFloat2, float paramFloat3) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetPosition(entityId, paramFloat1, paramFloat2, paramFloat3);
        }
    }

    public static void a(long entityId, float paramFloat1, float paramFloat2, float paramFloat3) {
        if (h.CW().Da() == 1) {
            mcspirit.j(entityId, paramFloat1, paramFloat2, paramFloat3);
        } else {
            ScriptManager.nativeSetPosition(entityId, paramFloat1, paramFloat2, paramFloat3);
        }
    }

    public static void runOnMainThread(Runnable paramRunnable) {
        synchronized (DTSDKManager.runOnMainThreadList) {
            DTSDKManager.runOnMainThreadList.add(paramRunnable);
        }
    }

    public static float ik(int paramInt) {
        if (h.CW().Da() == 1) {
            return mcspirit.h(paramInt);
        }
        return ScriptManager.nativeGetPlayerLoc(paramInt);
    }

    public static void a(int paramInt1, float paramFloat, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetVel(paramInt1, paramFloat, paramInt2);
        } else {
            ScriptManager.nativeSetVel(paramInt1, paramFloat, paramInt2);
        }
    }

    public static void a(long paramLong, float paramFloat, int paramInt2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetVel(paramLong, paramFloat, paramInt2);
        }
    }

    public static long Cr() {
        if (h.CW().Da() == 1) {
            return mcspirit.i();
        }
        return ScriptManager.nativeGetTime();
    }

    public static void ag(long paramLong) {
        if (h.CW().Da() == 1) {
            mcspirit.g(paramLong);
        } else {
            ScriptManager.nativeSetTime(paramLong);
        }
    }

    public static void g(int paramInt, boolean useDTSDK) {
        if (h.CW().Da() == 1) {
            mcspirit.e(paramInt);
        } else if (useDTSDK) {
            mcspirit.e(paramInt);
        } else {
            ScriptManager.nativeSetGameType(paramInt);
        }
    }

    public static int Cs() {
        if (h.CW().Da() == 1) {
            return mcspirit.f();
        }
        return ScriptManager.nativeGetGameType();
    }

    public static void il(int paramInt) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeHurtTo(paramInt);
        }
    }

    public static void dtNativePlayerSetCanFly(boolean paramBoolean) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativePlayerSetCanFly(paramBoolean);
        }
    }

    public static boolean dtNativePlayerCanFly() {
        if (h.CW().Da() == 1) {
            return mcspirit.t();
        }
        return ScriptManager.nativePlayerCanFly();
    }

    public static void dtNativePlayerSetFlying(boolean paramBoolean) {
        if (h.CW().Da() == 1) {
            mcspirit.s(paramBoolean, m.getGameType());
        } else {
            ScriptManager.nativePlayerSetFlying(paramBoolean);
        }
    }

    public static boolean dtNativePlayerIsFlying() {
        if (h.CW().Da() == 1) {
            return false;
        }
        return ScriptManager.nativePlayerIsFlying();
    }

    public static void dz(String paramString) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeShowTipMessage(paramString);
        } else {
            ScriptManager.nativeShowTipMessage(paramString);
        }
    }

    public static void A(int addId, int addCount, int addDmg) {
        if (h.CW().Da() == 1) {
            mcspirit.m(addId, addCount, addDmg);
        } else {
            ScriptManager.nativeAddItemInventory(addId, addCount, addDmg);
        }
    }

    public static void B(int paramInt1, int paramInt2, int paramInt3) {
        try {
            if (h.CW().Da() != 1 && h.CW().CX() != 5 && h.CW().CX() != 7) {
                ScriptManager.nativeSetArmorSlot(paramInt1, paramInt2, paramInt3);
            }
        } catch (Exception e) {
        }
    }

    public static boolean dtNativeLevelIsRemote() {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeLevelIsRemote();
        }
        return true;
    }

    public static void G(float paramFloat) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetGameSpeed(paramFloat);
        } else {
            ScriptManager.nativeSetGameSpeed(paramFloat);
        }
    }

    public static void dA(String paramString) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeClientMessage(paramString);
            return;
        }
        com.MCWorld.mcsdk.log.a.verbose("TAG", "DTPrint dtNativeClientMessage is " + paramString, new Object[0]);
        ScriptManager.nativeClientMessage(paramString);
    }

    public static int C(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetTile(paramInt1, paramInt2, paramInt3);
        }
        return ScriptManager.nativeGetTile(paramInt1, paramInt2, paramInt3);
    }

    public static int D(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetData(paramInt1, paramInt2, paramInt3);
        }
        return ScriptManager.nativeGetData(paramInt1, paramInt2, paramInt3);
    }

    public static float im(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetYaw(paramInt);
        }
        return ScriptManager.nativeGetYaw(paramInt);
    }

    public static float ah(long paramLong) {
        if (h.CW().Da() == 1) {
            return 0.0f;
        }
        return ScriptManager.nativeGetYaw(paramLong);
    }

    public static void Ct() {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeRequestFrameCallback();
        } else {
            ScriptManager.nativeRequestFrameCallback();
        }
    }

    public static int in(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetEntityTypeId(paramInt);
        }
        return ScriptManager.nativeGetEntityTypeId(paramInt);
    }

    public static int ai(long paramInt) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeGetEntityTypeId(paramInt);
    }

    public static void aj(long paramInt) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeMobRemoveAllEffects(paramInt);
        }
    }

    public static void d(long paramLong, int paramInt) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeMobRemoveEffect(paramLong, paramInt);
        }
    }

    public static void e(long paramLong, String paramString) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetCape(paramLong, paramString);
        }
    }

    public static int io(int paramInt) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeGetMobHealth(paramInt);
    }

    public static int ak(long paramLong) {
        return h.CW().Da() == 1 ? 0 : 0;
    }

    public static int al(long paramLong) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeGetMobHealth(paramLong);
    }

    public static int Cu() {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativePlayerGetPointedEntity();
        }
        return (int) ScriptManager.nativePlayerGetPointedEntity();
    }

    public static long Cv() {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativePlayerGetPointedEntity();
    }

    public static void l(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetTile(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
        } else {
            ScriptManager.nativeSetTile(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
        }
    }

    public static void c(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeExplode(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        } else {
            ScriptManager.nativeExplode(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        }
    }

    public static int ip(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetCarriedItem(paramInt);
        }
        return ScriptManager.nativeGetCarriedItem(paramInt);
    }

    public static void ci(boolean paramBoolean) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetNightMode(paramBoolean);
        } else {
            ScriptManager.nativeSetNightMode(paramBoolean);
        }
    }

    public static void d(double paramDouble) {
        ScriptManager.nativeLevelSetLightningLevel((float) paramDouble);
    }

    public static void e(double paramDouble) {
        ScriptManager.nativeLevelSetRainLevel((float) paramDouble);
    }

    public static void dtNativeLeaveGame(boolean paramBoolean) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeLeaveGame(paramBoolean);
        } else {
            ScriptManager.nativeLeaveGame(paramBoolean);
        }
    }

    public static void e(int paramInt, String paramString) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeEntitySetNameTag(paramInt, paramString);
        } else {
            ScriptManager.nativeEntitySetNameTag(paramInt, paramString);
        }
    }

    public static void f(long paramLong, String paramString) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeEntitySetNameTag(paramLong, paramString);
        }
    }

    public static String iq(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeEntityGetMobSkin(paramInt);
        }
        return ScriptManager.nativeEntityGetMobSkin(paramInt);
    }

    public static String am(long paramLong) {
        if (h.CW().Da() == 1) {
            return "";
        }
        return ScriptManager.nativeEntityGetMobSkin(paramLong);
    }

    public static int V(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            return mcspirit.n(paramInt1, paramInt2);
        }
        return ScriptManager.nativeGetSlotInventory(paramInt1, paramInt2);
    }

    public static void ir(int paramInt) {
        if (h.CW().Da() == 1) {
            mcspirit.o(paramInt);
        } else {
            ScriptManager.nativeClearSlotInventory(paramInt);
        }
    }

    public static void e(int in_ItemId, int in_ItemDmg, int in_ItemCnt, int[] in_enchantAttr, int[] in_enchantLevel) {
        try {
            if (h.CW().Da() == 1) {
                mcspirit.x(in_ItemId, in_ItemDmg, in_ItemCnt, in_enchantAttr, in_enchantLevel);
            } else if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                mcspirit.x(in_ItemId, in_ItemDmg, in_ItemCnt, in_enchantAttr, in_enchantLevel);
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static void a(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeDefineItem(paramInt1, paramString1, paramInt2, paramString2, paramInt3);
        } else {
            ScriptManager.nativeDefineItem(paramInt1, paramString1, paramInt2, paramString2, paramInt3);
        }
    }

    public static void dtNativeAddShapedRecipe(int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString, int[] paramArrayOfInt) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeAddShapedRecipe(paramInt1, paramInt2, paramInt3, paramArrayOfString, paramArrayOfInt);
        } else {
            ScriptManager.nativeAddShapedRecipe(paramInt1, paramInt2, paramInt3, paramArrayOfString, paramArrayOfInt);
        }
    }

    public static Object a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            return Integer.valueOf(DTSDKManager.nativeDropItem(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramInt3));
        }
        if (h.CW().CX() == 0) {
            return Integer.valueOf((int) ScriptManager.nativeDropItem(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramInt3));
        }
        if (h.CW().CX() == 1 || h.CW().CX() == 2) {
            return Long.valueOf(ScriptManager.nativeDropItem(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramInt3));
        }
        return Integer.valueOf(0);
    }

    public static float W(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            return 0.0f;
        }
        return ScriptManager.nativeGetEntityLoc(paramInt1, paramInt2);
    }

    public static float e(long paramLong, int paramInt2) {
        if (h.CW().Da() == 1) {
            return 0.0f;
        }
        return ScriptManager.nativeGetEntityLoc(paramLong, paramInt2);
    }

    public static int is(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetAnimalAge(paramInt);
        }
        return ScriptManager.nativeGetAnimalAge(paramInt);
    }

    public static int an(long paramLong) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeGetAnimalAge(paramLong);
    }

    public static void X(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetAnimalAge(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeSetAnimalAge(paramInt1, paramInt2);
        }
    }

    public static void f(long paramLong, int paramInt2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetAnimalAge(paramLong, paramInt2);
        }
    }

    public static int b(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, String paramString, int inputCount) {
        if (h.CW().Da() == 1) {
            return (int) mcspirit.zex(paramFloat1, paramFloat2, paramFloat3, paramInt, paramString, inputCount);
        }
        return 0;
    }

    public static int b(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, String paramString) {
        try {
            if (h.CW().Da() != 1) {
                return (int) ScriptManager.nativeSpawnEntity(paramFloat1, paramFloat2, paramFloat3, paramInt, paramString);
            }
            if (h.CW().CX() != 5 && h.CW().CZ() != 1) {
                return (int) mcspirit.z(paramFloat1, paramFloat2, paramFloat3, paramInt, paramString);
            }
            if (15 == paramInt) {
                return 0;
            }
            return (int) mcspirit.z(paramFloat1, paramFloat2, paramFloat3, paramInt, paramString);
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
            return 0;
        }
    }

    public static long c(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, String paramString) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeSpawnEntity(paramFloat1, paramFloat2, paramFloat3, paramInt, paramString);
    }

    public static void a(float paramFloat1, float paramFloat2, float paramFloat3, String paramString, float paramFloat4, float paramFloat5) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativePlaySound(paramFloat1, paramFloat2, paramFloat3, paramString, paramFloat4, paramFloat5);
        } else {
            ScriptManager.nativePlaySound(paramFloat1, paramFloat2, paramFloat3, paramString, paramFloat4, paramFloat5);
        }
    }

    public static void dtNativeLevelAddParticle(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeLevelAddParticle(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramInt2);
        } else {
            ScriptManager.nativeLevelAddParticle(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramInt2);
        }
    }

    public static void E(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeAddItemCreativeInv(paramInt1, paramInt2, paramInt3);
        } else {
            ScriptManager.nativeAddItemCreativeInv(paramInt1, paramInt2, paramInt3);
        }
    }

    public static void a(int paramInt1, String paramString, String[] paramArrayOfString, int[] paramArrayOfInt, int paramInt2, boolean paramBoolean, int paramInt3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeDefineBlock(paramInt1, paramString, paramArrayOfString, paramArrayOfInt, paramInt2, paramBoolean, paramInt3);
        } else {
            ScriptManager.nativeDefineBlock(paramInt1, paramString, paramArrayOfString, paramArrayOfInt, paramInt2, paramBoolean, paramInt3);
        }
    }

    public static void dtNativeEntitySetSize(int paramInt, float paramFloat1, float paramFloat2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeEntitySetSize(paramInt, paramFloat1, paramFloat2);
        } else {
            ScriptManager.nativeEntitySetSize(paramInt, paramFloat1, paramFloat2);
        }
    }

    public static void a(long paramInt, float paramFloat1, float paramFloat2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeEntitySetSize(paramInt, paramFloat1, paramFloat2);
        }
    }

    public static void Y(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetEntityRenderType(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeSetEntityRenderType(paramInt1, paramInt2);
        }
    }

    public static void g(long paramInt1, int paramInt2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetEntityRenderType(paramInt1, paramInt2);
        }
    }

    public static void h(int paramInt, boolean paramBoolean) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetSneaking(paramInt, paramBoolean);
        } else {
            ScriptManager.nativeSetSneaking(paramInt, paramBoolean);
        }
    }

    public static void a(long paramInt, boolean paramBoolean) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetSneaking(paramInt, paramBoolean);
        }
    }

    public static void Z(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetOnFire(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeSetOnFire(paramInt1, paramInt2);
        }
    }

    public static void h(long paramInt1, int paramInt2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetOnFire(paramInt1, paramInt2);
        }
    }

    public static void aa(int paramInt1, int paramInt2) {
        try {
            if (h.CW().Da() == 1) {
                DTSDKManager.nativeSetMobHealth(paramInt1, paramInt2);
            } else {
                ScriptManager.nativeSetMobHealth(paramInt1, paramInt2);
            }
        } catch (Exception e) {
        }
    }

    public static void i(long paramInt1, int paramInt2) {
        try {
            if (h.CW().Da() != 1) {
                ScriptManager.nativeSetMobHealth(paramInt1, paramInt2);
            }
        } catch (Exception e) {
        }
    }

    public static void j(long paramInt1, int paramInt2) {
        try {
            if (h.CW().Da() != 1) {
                if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                    ScriptManager.nativeSetMobMaxHealth(paramInt1, paramInt2);
                }
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static void it(int paramInt) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeRemoveEntity(paramInt);
        } else {
            ScriptManager.nativeRemoveEntity(paramInt);
        }
    }

    public static void ao(long paramInt) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeRemoveEntity(paramInt);
        }
    }

    public static float ab(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetEntityVel(paramInt1, paramInt2);
        }
        return ScriptManager.nativeGetEntityVel(paramInt1, paramInt2);
    }

    public static float k(long paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            return 0.0f;
        }
        return ScriptManager.nativeGetEntityVel(paramInt1, paramInt2);
    }

    public static int iu(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeEntityGetRiding(paramInt);
        }
        return ScriptManager.nativeEntityGetRiding(paramInt);
    }

    public static int ap(long paramLong) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeEntityGetRiding(paramLong);
    }

    public static int iv(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeEntityGetRider(paramInt);
        }
        return ScriptManager.nativeEntityGetRider(paramInt);
    }

    public static int aq(long paramLong) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeEntityGetRider(paramLong);
    }

    public static int iw(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeEntityGetRenderType(paramInt);
        }
        return ScriptManager.nativeEntityGetRenderType(paramInt);
    }

    public static int ar(long paramLong) {
        if (h.CW().Da() == 1) {
            return 0;
        }
        return ScriptManager.nativeEntityGetRenderType(paramLong);
    }

    public static String ix(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeEntityGetNameTag(paramInt);
        }
        return ScriptManager.nativeEntityGetNameTag(paramInt);
    }

    public static String as(long paramLong) {
        if (h.CW().Da() == 1) {
            return null;
        }
        return ScriptManager.nativeEntityGetNameTag(paramLong);
    }

    public static int Cw() {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetSelectedSlotId();
        }
        return ScriptManager.nativeGetSelectedSlotId();
    }

    public static int dtNativePlayerGetPointedBlock(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativePlayerGetPointedBlock(paramInt);
        }
        return ScriptManager.nativePlayerGetPointedBlock(paramInt);
    }

    public static String iy(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetPlayerName(paramInt);
        }
        return ScriptManager.nativeGetPlayerName(paramInt);
    }

    public static String at(long paramInt) {
        if (h.CW().Da() == 1) {
            return null;
        }
        return ScriptManager.nativeGetPlayerName(paramInt);
    }

    public static int ac(int paramInt1, int paramInt2) {
        int i = 0;
        try {
            if (!(h.CW().Da() == 1 || h.CW().CX() == 3 || h.CW().CX() == 0 || h.CW().CX() == 5 || h.CW().CX() == 7)) {
                i = ScriptManager.nativeGetSlotArmor(paramInt1, paramInt2);
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
        return i;
    }

    public static void h(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetCarriedItem(paramInt1, paramInt2, paramInt3, paramInt4);
        } else {
            ScriptManager.nativeSetCarriedItem(paramInt1, paramInt2, paramInt3, paramInt4);
        }
    }

    public static void a(long paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetCarriedItem(paramInt1, paramInt2, paramInt3, paramInt4);
        }
    }

    public static void a(int paramInt, float paramFloat1, float paramFloat2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetRot(paramInt, paramFloat1, paramFloat2);
        } else {
            ScriptManager.nativeSetRot(paramInt, paramFloat1, paramFloat2);
        }
    }

    public static void b(long paramInt, float paramFloat1, float paramFloat2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetRot(paramInt, paramFloat1, paramFloat2);
        }
    }

    public static void b(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetPositionRelative(paramInt, paramFloat1, paramFloat2, paramFloat3);
        } else {
            ScriptManager.nativeSetPositionRelative(paramInt, paramFloat1, paramFloat2, paramFloat3);
        }
    }

    public static void b(long paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetPositionRelative(paramInt, paramFloat1, paramFloat2, paramFloat3);
        }
    }

    public static void ad(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeRideAnimal(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeRideAnimal(paramInt1, paramInt2);
        }
    }

    public static void d(long paramInt1, long paramInt2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeRideAnimal(paramInt1, paramInt2);
        }
    }

    public static long Cx() {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetLevel();
        }
        return ScriptManager.nativeGetLevel();
    }

    public static float iz(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetPitch(paramInt);
        }
        return ScriptManager.nativeGetPitch(paramInt);
    }

    public static float au(long paramLong) {
        if (h.CW().Da() == 1) {
            return 0.0f;
        }
        return ScriptManager.nativeGetPitch(paramLong);
    }

    public static void h(Object paramObject, String paramString) {
        try {
            if (h.CW().Da() != 1) {
                if (h.CW().CX() == 0) {
                    ScriptManager.nativeSetMobSkin(com.MCWorld.mcsdk.dtlib.a.ar(paramObject), paramString);
                } else if (h.CW().CX() == 1 || h.CW().CX() == 2) {
                    ScriptManager.nativeSetMobSkin(com.MCWorld.mcsdk.dtlib.a.ar(paramObject), paramString);
                }
            }
        } catch (Exception e) {
        }
    }

    public static void g(long paramInt, String paramString) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetMobSkin(paramInt, paramString);
        }
    }

    public static void dB(String msg) {
        String[] portions = msg.split(SpecilApiUtil.LINE_SEP);
        for (String line : portions) {
            String line2;
            if (msg.indexOf(ChatColor.BEGIN) >= 0) {
                dA(line2);
                while (line2.length() > 40) {
                    String newStr = line2.substring(0, 40);
                    dA(newStr);
                    line2 = line2.substring(newStr.length());
                }
                if (line2.length() > 0) {
                    dA(line2);
                }
            } else {
                dA(line2);
            }
        }
    }

    public static void D(String msg, String inputSplitString) {
        if (inputSplitString == null) {
            inputSplitString = SpecilApiUtil.LINE_SEP;
        }
        String[] portions = msg.split(inputSplitString);
        for (String line : portions) {
            String line2;
            if (msg.indexOf(ChatColor.BEGIN) >= 0) {
                dA(line2);
                while (line2.length() > 16) {
                    String newStr = line2.substring(0, 16);
                    dA(newStr);
                    line2 = line2.substring(newStr.length());
                }
                if (line2.length() > 0) {
                    dA(line2);
                }
            } else {
                dA(line2);
            }
        }
    }

    public static long[] iA(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeEntityGetUUID(paramInt);
        }
        return ScriptManager.nativeEntityGetUUID(paramInt);
    }

    public static long[] av(long paramInt) {
        if (h.CW().Da() == 1) {
            return null;
        }
        return ScriptManager.nativeEntityGetUUID(paramInt);
    }

    public static String iB(int paramInt) {
        String str1 = (String) g.entityUUIDMap.get(Integer.valueOf(paramInt));
        if (str1 != null) {
            return str1;
        }
        long[] arrayOfLong = iA(paramInt);
        if (arrayOfLong == null) {
            return null;
        }
        UUID localUUID = new UUID(Long.reverseBytes(arrayOfLong[0]), Long.reverseBytes(arrayOfLong[1]));
        System.out.println(localUUID);
        if (localUUID.version() != 4) {
            throw new RuntimeException("Invalid entity UUID");
        }
        String str2 = localUUID.toString();
        g.entityUUIDMap.put(Integer.valueOf(paramInt), str2);
        return str2;
    }

    private static String aw(long paramLong) {
        String str1 = (String) g.aed.get(Long.valueOf(paramLong));
        if (str1 != null) {
            return str1;
        }
        long[] arrayOfLong = av(paramLong);
        if (arrayOfLong == null) {
            return null;
        }
        UUID localUUID = new UUID(Long.reverseBytes(arrayOfLong[0]), Long.reverseBytes(arrayOfLong[1]));
        System.out.println(localUUID);
        if (localUUID.version() != 4) {
            throw new RuntimeException("Invalid entity UUID");
        }
        String str2 = localUUID.toString();
        g.aed.put(Long.valueOf(paramLong), str2);
        return str2;
    }

    public static String ax(long paramInt) {
        String str1 = (String) g.entityUUIDMap.get(Long.valueOf(paramInt));
        if (str1 != null) {
            return str1;
        }
        long[] arrayOfLong = av(paramInt);
        if (arrayOfLong == null) {
            return null;
        }
        UUID localUUID = new UUID(Long.reverseBytes(arrayOfLong[0]), Long.reverseBytes(arrayOfLong[1]));
        System.out.println(localUUID);
        if (localUUID.version() != 4) {
            throw new RuntimeException("Invalid entity UUID");
        }
        String str2 = localUUID.toString();
        g.entityUUIDMap.put(Integer.valueOf((int) paramInt), str2);
        return str2;
    }

    public static String dtNativeBiomeIdToName(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeBiomeIdToName(paramInt);
        }
        return ScriptManager.nativeBiomeIdToName(paramInt);
    }

    public static void I(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeDestroyBlock(paramInt1, paramInt2, paramInt3);
        } else {
            ScriptManager.nativeDestroyBlock(paramInt1, paramInt2, paramInt3);
        }
    }

    public static int dtNativeLevelGetBiome(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeLevelGetBiome(paramInt1, paramInt2);
        }
        return ScriptManager.nativeLevelGetBiome(paramInt1, paramInt2);
    }

    public static String dtNativeLevelGetBiomeName(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeLevelGetBiomeName(paramInt1, paramInt2);
        }
        return ScriptManager.nativeLevelGetBiomeName(paramInt1, paramInt2);
    }

    public static int J(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetBrightness(paramInt1, paramInt2, paramInt3);
        }
        return ScriptManager.nativeGetBrightness(paramInt1, paramInt2, paramInt3);
    }

    public static int i(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetItemChest(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        return ScriptManager.nativeGetItemChest(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static int j(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetItemCountChest(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        return ScriptManager.nativeGetItemCountChest(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static int k(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetItemDataChest(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        return ScriptManager.nativeGetItemCountChest(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static int l(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetItemFurnace(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        return ScriptManager.nativeGetItemFurnace(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static int m(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetItemCountFurnace(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        return ScriptManager.nativeGetItemCountFurnace(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static int n(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetItemDataFurnace(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        return ScriptManager.nativeGetItemDataFurnace(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static int dtNativeLevelGetGrassColor(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeLevelGetGrassColor(paramInt1, paramInt2);
        }
        return ScriptManager.nativeLevelGetGrassColor(paramInt1, paramInt2);
    }

    public static String o(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetSignText(paramInt1, paramInt2, paramInt3, paramInt4);
        }
        return ScriptManager.nativeGetSignText(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static void c(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeAddItemChest(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
        } else {
            ScriptManager.nativeAddItemChest(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
        }
    }

    public static void d(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeAddItemFurnace(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
        } else {
            ScriptManager.nativeAddItemFurnace(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
        }
    }

    public static void dtNativeLevelSetGrassColor(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeLevelSetGrassColor(paramInt1, paramInt2, paramInt3);
        } else {
            ScriptManager.nativeLevelSetGrassColor(paramInt1, paramInt2, paramInt3);
        }
    }

    public static void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetSignText(paramInt1, paramInt2, paramInt3, paramInt4, paramString);
        } else {
            ScriptManager.nativeSetSignText(paramInt1, paramInt2, paramInt3, paramInt4, paramString);
        }
    }

    public static void K(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeSetSpawn(paramInt1, paramInt2, paramInt3);
        }
    }

    public static void E(String paramString1, String paramString2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetI18NString(paramString1, paramString2);
        } else {
            ScriptManager.nativeSetI18NString(paramString1, paramString2);
        }
    }

    public static void dtNativeSetFov(float paramFloat, boolean paramBoolean) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetFov(paramFloat, paramBoolean);
        } else {
            ScriptManager.nativeSetFov(paramFloat, paramBoolean);
        }
    }

    public static void iC(int paramInt) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetCameraEntity(paramInt);
        } else {
            ScriptManager.nativeSetCameraEntity(paramInt);
        }
    }

    public static void a(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, int paramInt4) {
        try {
            if (h.CW().Da() == 1) {
                DTSDKManager.nativeDefineFoodItem(paramInt1, paramString1, paramInt2, paramInt3, paramString2, paramInt4);
            } else if (h.CW().CX() != 5 && h.CW().CZ() != 1) {
                ScriptManager.nativeDefineFoodItem(paramInt1, paramString1, paramInt2, paramInt3, paramString2, paramInt4);
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static void L(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeAddFurnaceRecipe(paramInt1, paramInt2, paramInt3);
        } else {
            ScriptManager.nativeAddFurnaceRecipe(paramInt1, paramInt2, paramInt3);
        }
    }

    public static String e(int paramInt1, int paramInt2, boolean paramBoolean) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetItemName(paramInt1, paramInt2, paramBoolean);
        }
        return ScriptManager.nativeGetItemName(paramInt1, paramInt2, paramBoolean);
    }

    public static void M(int paramInt1, int paramInt2, int paramInt3) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetItemCategory(paramInt1, paramInt2, paramInt3);
        } else {
            ScriptManager.nativeSetItemCategory(paramInt1, paramInt2, paramInt3);
        }
    }

    public static void ae(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetItemMaxDamage(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeSetItemMaxDamage(paramInt1, paramInt2);
        }
    }

    public static int iD(int paramInt) {
        if (h.CW().Da() == 1) {
            return DTSDKManager.nativeGetBlockRenderShape(paramInt);
        }
        return ScriptManager.nativeGetBlockRenderShape(paramInt);
    }

    public static void a(int paramInt, int[] paramArrayOfInt) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeBlockSetColor(paramInt, paramArrayOfInt);
        } else {
            ScriptManager.nativeBlockSetColor(paramInt, paramArrayOfInt);
        }
    }

    public static void g(int paramInt, float paramFloat) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeBlockSetDestroyTime(paramInt, paramFloat);
        } else {
            ScriptManager.nativeBlockSetDestroyTime(paramInt, paramFloat);
        }
    }

    public static void h(int paramInt, float paramFloat) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeBlockSetExplosionResistance(paramInt, paramFloat);
        } else {
            ScriptManager.nativeBlockSetExplosionResistance(paramInt, paramFloat);
        }
    }

    public static void af(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeBlockSetLightLevel(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeBlockSetLightLevel(paramInt1, paramInt2);
        }
    }

    public static void ag(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeBlockSetLightOpacity(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeBlockSetLightOpacity(paramInt1, paramInt2);
        }
    }

    public static void ah(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeBlockSetRenderLayer(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeBlockSetRenderLayer(paramInt1, paramInt2);
        }
    }

    public static void ai(int paramInt1, int paramInt2) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSetBlockRenderShape(paramInt1, paramInt2);
        } else {
            ScriptManager.nativeSetBlockRenderShape(paramInt1, paramInt2);
        }
    }

    public static void a(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeBlockSetShape(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
        } else {
            ScriptManager.nativeBlockSetShape(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
        }
    }

    public static void a(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt2) {
        if (h.CW().Da() != 1) {
            ScriptManager.nativeBlockSetShape(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramInt2);
        }
    }

    public static void dC(String paramString) {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativeSendChat(paramString);
        } else {
            ScriptManager.nativeSendChat(paramString);
        }
    }

    public static void Cy() {
        if (h.CW().Da() == 1) {
            DTSDKManager.nativePreventDefault();
        }
    }

    public static void init(Context cxt) {
        try {
            d.aV(cxt);
            f.bX(true);
            DTSDKManager.androidContext = cxt.getApplicationContext();
            ij(740110001);
            if (h.CW().CX() == 0) {
                ScriptManager.ITEM_ID_COUNT = 512;
                mcspirit.a(cxt.getApplicationContext(), 105, 0, "1", 0);
            } else if (h.CW().CX() == 1) {
                g.gj(ScriptManager.nativeGetItemIdCount());
                mcspirit.a(cxt.getApplicationContext(), 111, 0, "1", 0);
            } else if (h.CW().CX() == 2) {
                g.gj(ScriptManager.nativeGetItemIdCount());
                ScriptManager.ITEM_ID_COUNT = g.vU();
                mcspirit.a(cxt.getApplicationContext(), 121, 0, "1", 0);
            } else if (h.CW().CX() == 3) {
                if (VERSION.SDK_INT < 11) {
                    g.gj(512);
                    ScriptManager.ITEM_ID_COUNT = g.vU();
                    mcspirit.a(cxt.getApplicationContext(), 130, 23, "1", 0);
                } else {
                    g.gj(ScriptManager.nativeGetItemIdCount());
                    ScriptManager.ITEM_ID_COUNT = g.vU();
                    mcspirit.a(cxt.getApplicationContext(), 130, 0, "1", 0);
                }
            } else if (h.CW().CX() == 5) {
                g.gj(ScriptManager.nativeGetItemIdCount());
                ScriptManager.ITEM_ID_COUNT = g.vU();
                mcspirit.a(cxt.getApplicationContext(), 140, 23, "1", 0);
            } else if (h.CW().CX() == 7) {
                g.gj(ScriptManager.nativeGetItemIdCount());
                ScriptManager.ITEM_ID_COUNT = g.vU();
                mcspirit.a(cxt.getApplicationContext(), 150, 23, "1", g.vL());
            }
            f.ajG.clear();
            if (!(h.CW().CX() == 5 || h.CW().CX() == 7)) {
                Cn();
            }
            if (d.Af()) {
                com.MCWorld.mcjsmanager.c.AC();
            }
            Ct();
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static void overrideTexture(String paramString1, String paramString2) {
        if (DTSDKManager.androidContext != null) {
            if (paramString2.contains("terrain-atlas.tga") || paramString2.contains("items-opaque.png")) {
                f.scriptPrint("cannot override " + paramString2);
            } else if (paramString1 == "") {
                DTSDKManager.clearTextureOverride(paramString2);
            } else {
                try {
                    new Thread(new ScriptTextureDownloader(new URL(paramString1), DTSDKManager.getTextureOverrideFile(paramString2))).start();
                } catch (Exception localException) {
                    throw new RuntimeException(localException);
                }
            }
        }
    }
}
