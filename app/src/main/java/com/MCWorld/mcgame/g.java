package com.MCWorld.mcgame;

import com.MCWorld.mcinterface.k;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DTGameData */
public class g {
    private static boolean adA = false;
    private static int adB = 0;
    private static boolean adC = true;
    private static boolean adD = true;
    private static boolean adE = true;
    public static final String adF = "ENTER_GAME_COUNT";
    public static final int adG = 0;
    public static final int adH = 1;
    public static final int adI = 3;
    private static boolean adJ = false;
    private static boolean adK = false;
    private static volatile boolean adL = false;
    public static List<k> adM = null;
    private static int adN = 0;
    private static volatile int adO = 0;
    private static int adP = 0;
    public static final String adQ = "NetWorldName";
    public static final String adR = "NetWorldDirName";
    private static volatile boolean adS = false;
    private static int adT = 0;
    private static boolean adU = false;
    private static boolean adV = false;
    private static boolean adW = false;
    public static boolean adX = false;
    private static volatile int adY = 0;
    public static List<Integer> adZ = new ArrayList();
    public static a adz;
    public static List<Long> aea = new ArrayList();
    public static List<Integer> aeb = new ArrayList();
    public static List<Long> aec = new ArrayList();
    public static Map<Long, String> aed = new HashMap();
    private static int aee = 0;
    private static String aef = null;
    private static volatile int aeg = 0;
    private static boolean aeh = true;
    private static int aei = 0;
    public static Map<Integer, String> entityUUIDMap = new HashMap();
    private static int heightPixels = 0;
    private static int leaveGameFlag = 0;
    private static boolean nextTickCallsSetLevel = false;
    private static String serverAddress;
    private static int serverPort;
    private static int widthPixels = 0;
    private static String worldDir = null;
    private static String worldName = null;

    /* compiled from: DTGameData */
    public static class a {
        public String serverAddress;
        public int serverPort;
    }

    public static int vL() {
        return adB;
    }

    public static void gg(int mconlineMode) {
        adB = mconlineMode;
    }

    public static boolean vM() {
        return adJ;
    }

    public static void aW(boolean expandFloatWindow) {
        adJ = expandFloatWindow;
    }

    public static boolean vN() {
        return adK;
    }

    public static void aX(boolean inputHadLoadJSScript) {
        if (inputHadLoadJSScript != adK) {
            adK = inputHadLoadJSScript;
        }
    }

    public static boolean vO() {
        return adL;
    }

    public static void aY(boolean useOptionWithAttack) {
        adL = useOptionWithAttack;
    }

    public static void E(List<k> mUsePotionList) {
        adM = mUsePotionList;
    }

    public static int vP() {
        return widthPixels;
    }

    public static void gh(int widthPixels) {
        widthPixels = widthPixels;
    }

    public static int vQ() {
        return heightPixels;
    }

    public static void gi(int heightPixels) {
        heightPixels = heightPixels;
    }

    public static boolean vR() {
        return adC;
    }

    public static void aZ(boolean dtEnableLoadJS) {
        adC = dtEnableLoadJS;
    }

    public static boolean vS() {
        return adD;
    }

    public static void ba(boolean dtEnableLoadWood) {
        adD = dtEnableLoadWood;
    }

    public static boolean vT() {
        return adE;
    }

    public static void bb(boolean dtEnableLoadSkin) {
        adE = dtEnableLoadSkin;
    }

    public static int vU() {
        return adP;
    }

    public static void gj(int v0111_ITEM_ID_COUNT) {
        adP = v0111_ITEM_ID_COUNT;
    }

    public static boolean vV() {
        return adS;
    }

    public static void bc(boolean forbiddenDamageBlockEnabled) {
        adS = forbiddenDamageBlockEnabled;
    }

    public static int vW() {
        return adT;
    }

    public static void gk(int val) {
        adT = val;
    }

    public static int vX() {
        return leaveGameFlag;
    }

    public static void gl(int leaveGameFlag) {
        leaveGameFlag = leaveGameFlag;
    }

    public static boolean vY() {
        return adU;
    }

    public static void bd(boolean nativeDefinePlaceholderBlocks_initflag) {
        adU = nativeDefinePlaceholderBlocks_initflag;
    }

    public static boolean vZ() {
        return adV;
    }

    public static void be(boolean mainActivitySuspend) {
        adV = mainActivitySuspend;
    }

    public static boolean wa() {
        return adW;
    }

    public static void bf(boolean enableDTThread) {
        adW = enableDTThread;
    }

    public static boolean wb() {
        return adX;
    }

    public static void bg(boolean needReStart) {
        adX = needReStart;
    }

    public static int wc() {
        return adY;
    }

    public static void gm(int val) {
        adY = val;
    }

    public static int wd() {
        return adO;
    }

    public static void gn(int nNetWorldMode) {
        adO = nNetWorldMode;
    }

    public static void quit() {
        adZ.clear();
        adZ = null;
        aea.clear();
        aea = null;
        aeb.clear();
        aec.clear();
        aec = null;
        aeb = null;
        entityUUIDMap.clear();
        entityUUIDMap = null;
        aed.clear();
        aed = null;
    }

    public static boolean we() {
        return nextTickCallsSetLevel;
    }

    public static void bh(boolean nextTickCallsSetLevel) {
        nextTickCallsSetLevel = nextTickCallsSetLevel;
    }

    public static String getWorldDir() {
        return worldDir;
    }

    public static void cA(String worldDir) {
        worldDir = worldDir;
    }

    public static String getWorldName() {
        return worldName;
    }

    public static void cB(String worldName) {
        worldName = worldName;
    }

    public static int wf() {
        return adN;
    }

    public static void go(int nStartGameFlag) {
        adN = nStartGameFlag;
    }

    public static String wg() {
        return serverAddress;
    }

    public static void cC(String serverAddress) {
        serverAddress = serverAddress;
    }

    public static int wh() {
        return serverPort;
    }

    public static void gp(int serverPort) {
        serverPort = serverPort;
    }

    public static int wi() {
        return aee;
    }

    public static void gq(int supportLanguage) {
        aee = supportLanguage;
    }

    public static String wj() {
        return aef;
    }

    public static void cD(String mP_UserName) {
        aef = mP_UserName;
    }

    public static int wk() {
        return aeg;
    }

    public static void gr(int inputGameModeFlag) {
        aeg = inputGameModeFlag;
    }

    public static boolean wl() {
        return aeh;
    }

    public static void bi(boolean mClearSystemMemoryFlag) {
        aeh = mClearSystemMemoryFlag;
    }

    public static int wm() {
        return aei;
    }

    public static void gs(int hlxMinecraftMode) {
        aei = hlxMinecraftMode;
    }

    public static boolean wn() {
        return adA;
    }

    public static void bj(boolean isOnlineMode) {
        adA = isOnlineMode;
    }
}
