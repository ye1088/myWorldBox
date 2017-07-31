package com.huluxia.mcsdk;

import com.huluxia.mcinterface.e;
import com.huluxia.mcsdk.dtlib.h;
import com.mojang.minecraftpe.a;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Scanner;

/* compiled from: MaraudersMap */
public final class g {
    private static final boolean DEBUG = false;
    public static ByteBuffer aon = null;
    private static boolean aoo = false;

    public static boolean aC(long minecraftLibLength) throws Exception {
        String libTmpName = "";
        if (h.CW().CX() == 0) {
            libTmpName = "libminecraftpe.so";
        } else if (h.CW().CX() == 1) {
            libTmpName = e.aiU;
        } else if (h.CW().CX() == 2) {
            libTmpName = "libminecraftpo.so";
        } else if (h.CW().CX() == 3) {
            libTmpName = "libminecraftpo.so";
        } else if (h.CW().CX() == 5 || h.CW().CX() == 7) {
            libTmpName = "libminecraftpe.so";
        }
        if (aoo) {
            return true;
        }
        boolean success = true;
        aoo = true;
        Scanner scan = new Scanner(new File("/proc/self/maps"));
        DTPokerFace.init();
        if (a.Rm()) {
            return false;
        }
        String[] parts;
        long loc;
        long len;
        long newLoc;
        if (h.CW().CX() == 2) {
            while (scan.hasNextLine()) {
                parts = scan.nextLine().split(" ");
                if (parts[parts.length - 1].indexOf(libTmpName) >= 0) {
                    loc = Long.parseLong(parts[0].substring(0, parts[0].indexOf("-")), 16);
                    len = Long.parseLong(parts[0].substring(parts[0].indexOf("-") + 1), 16) - loc;
                    if (parts[1].indexOf("x") >= 0) {
                        newLoc = loc;
                        if (c.a(loc, len, 7) < 0) {
                            success = false;
                        }
                        success = success && newLoc >= 0;
                    } else if (c.a(loc, len, 3) < 0) {
                        success = false;
                    }
                }
            }
            scan.close();
            return success;
        } else if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
            while (scan.hasNextLine()) {
                parts = scan.nextLine().split(" ");
                if (parts[parts.length - 1].indexOf(libTmpName) >= 0) {
                    loc = Long.parseLong(parts[0].substring(0, parts[0].indexOf("-")), 16);
                    len = Long.parseLong(parts[0].substring(parts[0].indexOf("-") + 1), 16) - loc;
                    if (parts[1].indexOf("x") >= 0) {
                        newLoc = loc;
                        if (DTSDKManagerEx.b(loc, len, 7) < 0) {
                            success = false;
                        }
                        success = success && newLoc >= 0;
                    } else if (DTSDKManagerEx.b(loc, len, 3) < 0) {
                        success = false;
                    }
                }
            }
            scan.close();
            return success;
        } else {
            while (scan.hasNextLine()) {
                parts = scan.nextLine().split(" ");
                if (parts[parts.length - 1].indexOf(libTmpName) >= 0) {
                    loc = Long.parseLong(parts[0].substring(0, parts[0].indexOf("-")), 16);
                    len = Long.parseLong(parts[0].substring(parts[0].indexOf("-") + 1), 16) - loc;
                    if (parts[1].indexOf("x") >= 0) {
                        newLoc = loc;
                        if (c.a(loc, len, 7) < 0) {
                            success = false;
                        }
                        success = success && newLoc >= 0;
                    } else if (c.a(loc, len, 3) < 0) {
                        success = false;
                    }
                }
            }
            scan.close();
            return success;
        }
    }
}
