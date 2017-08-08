package com.MCWorld.utils;

import com.MCWorld.controller.resource.factory.b.a;
import java.util.ArrayList;
import java.util.List;

public enum UtilsEnumBiz {
    GAMEAPK("GAMEAPK", 0),
    MOVIE("MOVIE", 1),
    BOOK("BOOK", 2),
    PIC("PIC", 3),
    TOOL("TOOL", 4),
    GAMEHPK("GAMEHPK", 5),
    ISO("ISO", 6),
    CSO("CSO", 7),
    GBA("GBA", 8),
    GBC("GBC", 9),
    NDS("NDS", 10),
    NES("NES", 11),
    SFC("SFC", 12),
    SMD("SMD", 13),
    N64("N64", 14),
    NGP("NGP", 15),
    ONLINE("ONLINE", 101),
    NOTBROWSER("NOTBROWSER", 102),
    UNKNOWN("UNKNOWN", a.ny);
    
    public static final String ARC_PACKNAME = "com.tiger.game.arcade2";
    public static final String GBA_PACKNAME = "com.fastemulator.gba";
    public static final String GBC_PACKNAME = "com.explusalpha.GbcEmu";
    public static final String N64_PACKNAME = "com.androidemu.n64";
    public static final String NDS_PACKNAME = "com.dsemu.drasticcn";
    public static final String NES_PACKNAME = "com.explusalpha.NesEmu";
    public static final String NGP_PACKNAME = "com.explusalpha.NgpEmu";
    public static final String PS_PACKNAME = "com.epsxe.ePSXe";
    public static final String SFC_PACKNAME = "com.explusalpha.Snes9xPlus";
    public static final String SMD_PACKNAME = "com.explusalpha.MdEmu";
    private int index;
    private String name;

    private UtilsEnumBiz(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (UtilsEnumBiz c : values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public static UtilsEnumBiz getBiz(int index) {
        for (UtilsEnumBiz c : values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return UNKNOWN;
    }

    public static List<String> getAllName() {
        List<String> list = null;
        for (UtilsEnumBiz c : values()) {
            list = new ArrayList();
            list.add(c.getName());
        }
        return list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static boolean isTool(int index) {
        if (index == TOOL.index) {
            return true;
        }
        return false;
    }

    public static boolean isTool(UtilsEnumBiz biz) {
        if (biz.index == TOOL.index) {
            return true;
        }
        return false;
    }

    public static boolean isMovie(int index) {
        if (index == MOVIE.index) {
            return true;
        }
        return false;
    }

    public static boolean isGameHpk(int index) {
        if (index == GAMEHPK.index) {
            return true;
        }
        return false;
    }

    public static boolean isISOorCSO(int index) {
        if (index == ISO.ordinal() || index == CSO.index) {
            return true;
        }
        return false;
    }

    public static boolean isGameApk(int index) {
        if (index == GAMEAPK.index) {
            return true;
        }
        return false;
    }

    public static boolean isBrowser(int index) {
        if (index != NOTBROWSER.index) {
            return true;
        }
        return false;
    }

    public static String getGameToolName(int index) {
        switch (index) {
            case 6:
            case 7:
                return "ppsspp.apk";
            case 8:
                return "gba.apk";
            case 9:
                return "gbc.apk";
            case 10:
                return "nds.apk";
            case 11:
                return "nes.apk";
            case 12:
                return "sfc.apk";
            case 13:
                return "smd.apk";
            case 14:
                return "n64.apk";
            case 15:
                return "ngp.apk";
            default:
                return null;
        }
    }

    public static long getGameToolId(int index) {
        switch (index) {
            case 6:
            case 7:
                return 7601;
            case 8:
                return 10073;
            case 9:
                return 10817;
            case 10:
                return 10064;
            case 11:
                return 10816;
            case 12:
                return 10815;
            case 13:
                return 10818;
            case 14:
                return 10138;
            case 15:
                return 10819;
            default:
                return 0;
        }
    }
}
