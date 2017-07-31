package com.huluxia.mcsdk;

import com.huluxia.mcgame.g;
import com.huluxia.mcgame.r;
import com.huluxia.mcgame.s;
import com.huluxia.mcgame.x;
import com.huluxia.mcinterface.k;
import com.huluxia.mcjavascript.DTNativeEntityApi;
import com.huluxia.mcjavascript.DTNativePlayerApi;
import com.huluxia.mcjavascript.d;
import com.huluxia.mcjavascript.f;
import com.huluxia.mcsdk.dtlib.h;
import com.mojang.minecraftpe.MainActivity;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import net.zhuoweizhang.mcpelauncher.ScriptTextureDownloader;

/* compiled from: DTGameCallBackEx */
public class b {
    private static final boolean DEBUG = false;
    private static final String TAG = "mcsdk/DTGameCallBack";
    private static float anf;
    private static int ang;
    private static int anh;
    private static int ani;
    private static int anj;

    /* compiled from: DTGameCallBackEx */
    private static class a implements Runnable {
        private int ank;

        /* compiled from: DTGameCallBackEx */
        private static class a implements Runnable {
            private String afW;
            private int ank;

            public a(int paramInt, String paramString) {
                this.ank = paramInt;
                this.afW = paramString;
            }

            public void run() {
                File localFile = DTSDKManager.getTextureOverrideFile("images/" + this.afW);
                if (localFile != null && localFile.exists()) {
                    DTNativeEntityApi.setMobSkin(Integer.valueOf(this.ank), this.afW);
                }
            }
        }

        public a(int entityId) {
            this.ank = entityId;
        }

        private static boolean Cd() {
            return true;
        }

        public void run() {
            String playerName = DTSDKManagerEx.iy(this.ank);
            if (playerName != null && playerName.length() > 0) {
                if (Cd()) {
                    playerName = playerName.toLowerCase();
                }
                String skinName = "mob/" + playerName + hlx.data.localstore.a.bKa;
                File skinFile = DTSDKManager.getTextureOverrideFile("images/" + skinName);
                if (skinFile != null) {
                    try {
                        new Thread(new ScriptTextureDownloader(new URL(DTSDKManager.getSkinURL(playerName)), skinFile, new a(this.ank, skinName))).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* compiled from: DTGameCallBackEx */
    private static class b implements Runnable {
        private long agJ;

        /* compiled from: DTGameCallBackEx */
        private static class a implements Runnable {
            private String afW;
            private long agJ;

            public a(long paramInt, String paramString) {
                this.agJ = paramInt;
                this.afW = paramString;
            }

            public void run() {
                File localFile = DTSDKManager.getTextureOverrideFile("images/" + this.afW);
                if (localFile != null && localFile.exists()) {
                    DTNativeEntityApi.setMobSkin(Long.valueOf(this.agJ), this.afW);
                }
            }
        }

        public b(long entityId) {
            this.agJ = entityId;
        }

        private static boolean Cd() {
            return true;
        }

        public void run() {
            String playerName = DTSDKManagerEx.at(this.agJ);
            if (playerName != null && playerName.length() > 0) {
                if (Cd()) {
                    playerName = playerName.toLowerCase();
                }
                String skinName = "mob/" + playerName + hlx.data.localstore.a.bKa;
                File skinFile = DTSDKManager.getTextureOverrideFile("images/" + skinName);
                if (skinFile != null) {
                    try {
                        new Thread(new ScriptTextureDownloader(new URL(DTSDKManager.getSkinURL(playerName)), skinFile, new a(this.agJ, skinName))).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void setLevelFakeCallback(boolean hasLevel, boolean isRemoteAAAAAA) {
        try {
            g.bh(false);
            DTSDKManagerEx.G(20.0f);
            g.adZ.clear();
            g.aea.clear();
            g.aeb.clear();
            g.aec.clear();
            g.entityUUIDMap.clear();
            g.aed.clear();
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
                ScriptManager.nativeClearCapes();
            }
            entityAddedCallback(DTSDKManagerEx.Cp());
            f.callScriptMethod("newLevel", Boolean.valueOf(true));
        } catch (Exception e) {
            com.huluxia.mcsdk.log.a.verbose(com.huluxia.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static void setLevelCallback(boolean hasLevel, boolean isRemote) {
        try {
            if (h.CW().CX() == 1 && ScriptManager.nativeGetArch() != 1) {
            }
        } catch (Exception e) {
            com.huluxia.mcsdk.log.a.verbose(com.huluxia.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static void ddd(String paramString1, String paramString2) {
        g.cB(paramString1);
        g.cA(paramString2);
        g.go(1);
        s.bt(false);
        g.gk(1);
    }

    public static void eatCallback(int paramInt, float paramFloat) {
        f.callScriptMethod("eatHook", Integer.valueOf(paramInt), Float.valueOf(paramFloat));
        com.huluxia.mcinterface.h.zM().e(paramInt, paramFloat);
    }

    public static void selectLevelCallback(String paramString1, String paramString2) {
        g.cB(paramString1);
        g.cA(paramString2);
        g.bh(true);
        g.go(1);
        s.bt(false);
        f.callScriptMethod("selectLevelHook", new Object[0]);
        g.gk(1);
    }

    public static boolean isLocalAddress(String paramString) {
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

    public static void eee(boolean paramBoolean) {
        try {
            g.gm(0);
            h.iJ(200);
            com.huluxia.mcinterface.h.zM().cg(g.getWorldName());
        } catch (Exception e) {
        }
    }

    public static void leaveGameCallback(boolean paramBoolean) {
        try {
            d.c(3, 1, false);
            g.gk(0);
            com.huluxia.mcgame.d.quit();
            g.gl(2);
            c.Cf();
            f.callScriptMethod("leaveGame", new Object[0]);
            h.iJ(200);
            g.gm(0);
            com.huluxia.mcinterface.h.zM().cg(g.getWorldName());
        } catch (Exception e) {
            h.Dd();
        }
    }

    public static void throwableHitCallback(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong2) {
        if (paramInt1 == 0) {
            f.callScriptMethod("projectileHitBlockHook", Long.valueOf(paramLong1), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt2));
            return;
        }
        f.callScriptMethod("projectileHitEntityHook", Long.valueOf(paramLong1), Long.valueOf(paramLong2));
    }

    public static void tickCallback() {
        e.CH();
    }

    public static void ccc() {
        if (h.CW().CX() == 5 || h.CW().CX() == 7) {
            e.CF();
        } else {
            e.CG();
        }
    }

    public static void aaaa() {
        g.gl(1);
    }

    public static void useItemOnCallback(int inputX, int inputY, int inputZ, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
        f.callScriptMethod("useItem", Integer.valueOf(inputX), Integer.valueOf(inputY), Integer.valueOf(inputZ), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6), Integer.valueOf(paramInt7), Integer.valueOf(paramInt8));
        r.i(inputX, inputY, inputZ, paramInt4, paramInt6);
        if (paramInt4 != 0) {
            switch (paramInt6) {
                case 0:
                    inputY--;
                    break;
                case 1:
                    inputY++;
                    break;
                case 2:
                    inputZ--;
                    break;
                case 3:
                    inputZ++;
                    break;
                case 4:
                    inputX--;
                    break;
                case 5:
                    inputX++;
                    break;
            }
            com.huluxia.mcinterface.h.zM().b(paramInt4, paramInt7, inputX, inputY, inputZ);
            com.huluxia.mcinterface.h.zM().fA(1);
        }
    }

    public static void frameCallback() {
        f.Ao();
    }

    public static void chatCallback(String str) {
        com.huluxia.mcgame.f.cz(str);
    }

    public static void attackCallback(int attacker, int victim) {
        f.callScriptMethod("attackHook", Integer.valueOf(attacker), Integer.valueOf(victim));
    }

    private static void ac(long victim) {
        try {
            if (g.vO() && g.adM != null) {
                for (int i = 0; i < g.adM.size(); i++) {
                    k _tmpMCPotionItem = (k) g.adM.get(i);
                    if (_tmpMCPotionItem != null) {
                        int _tmpID = _tmpMCPotionItem.Ab();
                        int _tmpTime = _tmpMCPotionItem.getTime();
                        int _tmpLevel = _tmpMCPotionItem.getLevel();
                        if (_tmpID > 0 && _tmpTime > 0 && _tmpLevel > 0 && victim != 0 && _tmpID != 21) {
                            ScriptManager.nativeMobAddEffect(victim, _tmpID, _tmpTime * 20, _tmpLevel, false, true);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void attackCallback(long attacker, long victim) {
        ac(victim);
        f.callScriptMethod("attackHook", Long.valueOf(attacker), Long.valueOf(victim));
        com.huluxia.mcinterface.h.zM().S(DTSDKManagerEx.ai(attacker), DTSDKManagerEx.ai(victim));
    }

    public static void bbb(long paramLong1, long paramLong2) {
        x.gT(com.huluxia.mcinterface.h.zo());
        x.gS(0);
        s.bt(true);
        com.huluxia.mcinterface.h.zM().se();
        com.huluxia.mcinterface.h.zM().fy(1);
    }

    public static void mobDieCallback(long paramLong1, long paramLong2) {
        if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
            try {
                if (paramLong1 == DTSDKManagerEx.Cq() && paramLong2 != DTSDKManagerEx.Cq() && (g.wm() == 1 || g.wm() == 2 || g.wm() == 3 || g.wm() == 4)) {
                    int nTmpId = DTSDKManagerEx.ai(paramLong2);
                    com.huluxia.mcfb.d.qq();
                    com.huluxia.mcfb.d.cW(nTmpId);
                }
            } catch (Exception e) {
            }
        }
        if (DTSDKManagerEx.Cq() == paramLong2) {
            s.bt(true);
            com.huluxia.mcinterface.h.zM().se();
            com.huluxia.mcinterface.h.zM().fy(1);
        } else if (paramLong1 == DTSDKManagerEx.Cq()) {
            com.huluxia.mcinterface.h.zM().fx(DTSDKManagerEx.ai(paramLong2));
        }
        Object[] arrayOfObject = new Object[2];
        if (paramLong1 == -1) {
            paramLong1 = -1;
        }
        arrayOfObject[0] = Long.valueOf(paramLong1);
        arrayOfObject[1] = Long.valueOf(paramLong2);
        f.callScriptMethod("deathHook", arrayOfObject);
    }

    public static void mobDieCallback(int attacker, int victim) {
        if (DTSDKManagerEx.Cp() == victim) {
            s.bt(true);
            com.huluxia.mcinterface.h.zM().se();
        }
        String str = "deathHook";
        Object[] objArr = new Object[2];
        objArr[0] = attacker == -1 ? null : Integer.valueOf(attacker);
        objArr[1] = Integer.valueOf(victim);
        f.callScriptMethod(str, objArr);
    }

    public static void entityAddedCallback(int paramInt) {
        try {
            if (DTNativePlayerApi.isPlayer(Integer.valueOf(paramInt))) {
                ic(paramInt);
            }
            g.adZ.add(Integer.valueOf(paramInt));
            f.callScriptMethod("entityAddedHook", Integer.valueOf(paramInt));
        } catch (Exception e) {
        }
    }

    private static void ad(long paramInt) {
        c.Ch();
        g.aec.add(Long.valueOf(paramInt));
        if (shouldLoadSkin()) {
            DTSDKManager.runOnMainThread(new b(paramInt));
        }
    }

    public static void entityAddedCallback(long paramInt) {
        ae(paramInt);
    }

    public static void continueDestroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat) {
        if (paramInt1 != anh || paramInt2 != ani || paramInt3 != anj || paramInt4 != ang) {
            if (paramFloat == 0.0f && paramFloat != anf) {
                f.callScriptMethod("startDestroyBlock", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4));
            }
            anf = paramFloat;
            anh = paramInt1;
            ani = paramInt2;
            anj = paramInt3;
            ang = paramInt4;
            f.callScriptMethod("continueDestroyBlock", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Float.valueOf(paramFloat));
        }
    }

    public static void playerAddLevelsCallback(long paramLong, int paramInt) {
        f.callScriptMethod("playerExpLevelChangeHook", Long.valueOf(paramLong), Integer.valueOf(paramInt));
    }

    public static void playerAddExperienceCallback(long paramLong, int paramInt) {
        f.callScriptMethod("playerAddExpHook", Long.valueOf(paramLong), Integer.valueOf(paramInt));
    }

    public static void redstoneUpdateCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6) {
        f.callScriptMethod("redstoneUpdateHook", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6));
    }

    public static void entityHurtCallback(long paramLong1, long paramLong2, int paramInt) {
        f.callScriptMethod("entityHurtHook", Long.valueOf(paramLong1), Long.valueOf(paramLong2), Integer.valueOf(paramInt));
    }

    private static void ae(long paramLong) {
        if (DTNativePlayerApi.isPlayer(Long.valueOf(paramLong))) {
            ad(paramLong);
        }
        g.aea.add(Long.valueOf(paramLong));
        new Object[1][0] = Long.valueOf(paramLong);
        f.callScriptMethod("entityAddedHook", Long.valueOf(paramLong));
    }

    private static boolean shouldLoadSkin() {
        if (h.CW().CX() == 1 || h.CW().CX() == 2) {
            return false;
        }
        return true;
    }

    private static void ic(int paramInt) {
        g.aeb.add(Integer.valueOf(paramInt));
        if (shouldLoadSkin()) {
            DTSDKManager.runOnMainThread(new a(paramInt));
        }
    }

    public static void blockEventCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        f.callScriptMethod("blockEventHook", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5));
    }

    public static void destroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        int blockId = DTSDKManagerEx.C(paramInt1, paramInt2, paramInt3);
        int dmg = DTSDKManagerEx.D(paramInt1, paramInt2, paramInt3);
        f.callScriptMethod("destroyBlock", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4));
        if (true == g.vV()) {
            DTSDKManagerEx.Cy();
        }
        com.huluxia.mcinterface.h.zM().c(blockId, dmg, paramInt1, paramInt2, paramInt3);
        com.huluxia.mcinterface.h.zM().fz(1);
    }

    public static void explodeCallback(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
        f.callScriptMethod("explodeHook", Integer.valueOf(paramInt), Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Float.valueOf(paramFloat4), Boolean.valueOf(paramBoolean));
    }

    public static void explodeCallback(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
        f.callScriptMethod("explodeHook", Long.valueOf(paramLong), Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Float.valueOf(paramFloat4), Boolean.valueOf(paramBoolean));
    }

    private static void ie(int paramInt) {
        c.Ch();
        int i = g.aeb.indexOf(Integer.valueOf(paramInt));
        if (i >= 0) {
            g.aeb.remove(i);
        }
    }

    public static void entityRemovedCallback(int paramInt) {
        if (DTNativePlayerApi.isPlayer(Integer.valueOf(paramInt))) {
            ie(paramInt);
        }
        int i = g.adZ.indexOf(Integer.valueOf(paramInt));
        if (i >= 0) {
            g.adZ.remove(i);
        }
        f.callScriptMethod("entityRemovedHook", Integer.valueOf(paramInt));
    }

    private static void af(long paramInt) {
        c.Ch();
        int i = g.aec.indexOf(Long.valueOf(paramInt));
        if (i >= 0) {
            g.aec.remove(i);
        }
    }

    public static void entityRemovedCallback(long paramLong) {
        if (DTNativePlayerApi.isPlayer(Long.valueOf(paramLong))) {
            af(paramLong);
        }
        int i = g.aea.indexOf(Long.valueOf(paramLong));
        if (i >= 0) {
            g.aea.remove(i);
        }
        f.callScriptMethod("entityRemovedHook", Long.valueOf(paramLong));
    }

    public static void handleChatPacketCallback(String paramString) {
        if (paramString != null && paramString.length() >= 1) {
            f.callScriptMethod("serverMessageReceiveHook", paramString);
        }
    }

    public static void levelEventCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
        f.callScriptMethod("levelEventHook", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6));
    }

    public static void startDestroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        f.callScriptMethod("startDestroyBlock", Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4));
    }

    public static void handleMessagePacketCallback(String sender, String str) {
        if (str != null && str.length() >= 1) {
            f.callScriptMethod("chatReceiveHook", str, sender);
            if (str.equals("BlockLauncher, enable scripts, please and thank you") && sender.length() == 0) {
                f.bY(true);
                DTSDKManagerEx.Cy();
                if (MainActivity.currentMainActivity != null) {
                    MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
                    if (main != null) {
                        main.scriptPrintCallback("Scripts have been re-enabled", "");
                    }
                }
            }
        }
    }

    public static String a(short[] sArray) {
        if (sArray == null || sArray.length == 0) {
            return "";
        }
        char[] bArry = new char[(sArray.length - 1)];
        for (int n = 0; n < sArray.length - 1; n++) {
            bArry[n] = (char) (sArray[n + 1] ^ (n + 128));
        }
        return String.valueOf(bArry);
    }

    public static String dt(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        int left = text.indexOf("/");
        int right = text.lastIndexOf(".");
        if (left <= 0 || right <= 0) {
            return "";
        }
        return text.substring(left + 1, right);
    }

    public static String du(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        short[] sArray = new short[(text.length() / 3)];
        int numIndex = 0;
        for (int n = 0; n < text.length(); n += 3) {
            sArray[numIndex] = Integer.valueOf(text.substring(n, n + 3)).shortValue();
            numIndex++;
        }
        return a(sArray);
    }
}
