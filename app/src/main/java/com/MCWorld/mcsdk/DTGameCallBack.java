package com.MCWorld.mcsdk;

import com.MCWorld.mcgame.g;
import com.MCWorld.mcjavascript.f;

public class DTGameCallBack {
    private static final boolean DEBUG = false;
    private static final String TAG = "mcsdk/DTGameCallBack";

    public static void setLevelFakeCallback(boolean hasLevel, boolean isRemoteAAAAAA) {
        b.setLevelFakeCallback(hasLevel, isRemoteAAAAAA);
    }

    public static void ddd(String paramString1, String paramString2) {
        b.ddd(paramString1, paramString2);
    }

    public static void dddd(String paramString1, String paramString2) {
        b.ddd(paramString1, paramString2);
    }

    public static void selectLevelCallback(String paramString1, String paramString2) {
        b.selectLevelCallback(paramString1, paramString2);
    }

    public static void eee(boolean paramBoolean) {
        b.eee(paramBoolean);
    }

    public static void leaveGameCallback(boolean paramBoolean) {
        b.leaveGameCallback(paramBoolean);
    }

    public static void setLevelCallback(boolean hasLevel, boolean isRemote) {
        b.setLevelCallback(hasLevel, isRemote);
    }

    public static void throwableHitCallback(long paramLong1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong2) {
        b.throwableHitCallback(paramLong1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramFloat1, paramFloat2, paramFloat3, paramLong2);
    }

    public static void tickCallback() {
        b.tickCallback();
    }

    public static void ccc() {
        b.ccc();
    }

    public static void cccc() {
        b.ccc();
    }

    public static void aaaa() {
        b.aaaa();
    }

    public static void useItemOnCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
        b.useItemOnCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
    }

    public static void frameCallback() {
        b.frameCallback();
    }

    public static void chatCallback(String str) {
        b.chatCallback(str);
    }

    public static void attackCallback(int paramInt1, int paramInt2) {
        b.attackCallback(paramInt1, paramInt2);
    }

    public static void attackCallback(long paramInt1, long paramInt2) {
        b.attackCallback(paramInt1, paramInt2);
    }

    public static void mobDieCallback(long paramInt1, long paramInt2) {
        b.mobDieCallback(paramInt1, paramInt2);
    }

    public static void bbb(long paramInt1, long paramInt2) {
        b.bbb(paramInt1, paramInt2);
    }

    public static void mobDieCallback(int paramInt1, int paramInt2) {
        b.mobDieCallback(paramInt1, paramInt2);
    }

    public static void blockEventCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        b.blockEventCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }

    public static void destroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        b.destroyBlockCallback(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public static void entityRemovedCallback(int paramInt) {
        b.entityRemovedCallback(paramInt);
    }

    public static void entityRemovedCallback(long paramInt) {
        b.entityRemovedCallback(paramInt);
    }

    public static void explodeCallback(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
        b.explodeCallback(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
    }

    public static void explodeCallback(long paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
        b.explodeCallback(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
    }

    public static void handleChatPacketCallback(String paramString) {
        b.handleChatPacketCallback(paramString);
    }

    public static void handleMessagePacketCallback(String sender, String str) {
        b.handleMessagePacketCallback(sender, str);
    }

    public static void levelEventCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
        b.levelEventCallback(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    }

    private static boolean isLocalAddress(String paramString) {
        return b.isLocalAddress(paramString);
    }

    public static void rakNetConnectCallback(String paramString, int paramInt) {
        f.bY(isLocalAddress(paramString));
        StringBuilder localStringBuilder = new StringBuilder().append("Scripting is now ");
        String str;
        if (f.Am()) {
            str = "enabled";
            g.cC(paramString);
            g.gp(paramInt);
        } else {
            str = "enabled";
            g.cC(paramString);
            g.gp(paramInt);
        }
    }

    public static void startDestroyBlockCallback(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        b.startDestroyBlockCallback(paramInt1, paramInt2, paramInt3, paramInt4);
    }
}
