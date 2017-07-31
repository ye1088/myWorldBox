package com.huluxia.mcsdk;

import android.os.Build.VERSION;
import com.huluxia.mcsdk.dtlib.h;
import com.mojang.minecraftpe.a;
import io.netty.handler.codec.http2.Http2CodecUtil;
import java.io.File;
import java.util.List;
import net.zhuoweizhang.pokerface.PokerFace;
import org.apache.http.NameValuePair;

/* compiled from: DTPokerFaceEx */
public class c {
    private static boolean anl = false;
    private static boolean anm = false;
    private static boolean ann = false;
    public static boolean ano = false;

    public static int a(long addr, long len, int prot) {
        if (h.CW().Da() == 1) {
            return DTPokerFace.mprotect(addr, len, prot);
        }
        return PokerFace.mprotect(addr, len, prot);
    }

    public static long if(int name) {
        if (h.CW().Da() == 1) {
            return DTPokerFace.sysconf(name);
        }
        return PokerFace.sysconf(name);
    }

    public static void init() {
        try {
            System.loadLibrary("mcpelauncher_tinysubstrate");
            if (h.CW().CX() == 0) {
                System.loadLibrary("mcpelauncher");
            } else if (h.CW().CX() == 1) {
                System.loadLibrary("mcpelauncher_011");
            } else if (h.CW().CX() == 2) {
                System.loadLibrary("mcpelauncher_012");
            } else if (h.CW().CX() == 3) {
                if (VERSION.SDK_INT >= 11) {
                    System.loadLibrary("mcpelauncher_013");
                }
            } else if (h.CW().CX() == 5) {
                System.loadLibrary("mcpelauncher_014");
            } else if (h.CW().CX() == 7 && a.bHS) {
                System.loadLibrary("mcpelauncher_0150");
            }
            com.huluxia.mcso.a.load(com.huluxia.mcgame.h.wo().ws());
            a.ds(false);
        } catch (UnsatisfiedLinkError e) {
            com.huluxia.mcsdk.log.a.info(com.huluxia.mcsdk.log.a.aoP, "System.loadLibrary Unsatisfied Link error: " + e.toString(), new Object[0]);
            a.ds(true);
        } catch (Exception e2) {
            com.huluxia.mcsdk.log.a.verbose(com.huluxia.mcsdk.log.a.aoP, "DTPrint is " + e2, new Object[0]);
            a.ds(true);
        }
    }

    public static void Ce() {
        if (d.anB == 1 && !anl) {
            anl = true;
            if (d.anA == 0) {
                d.anA = a.a(d.anG, d.anx);
            }
            if (d.anA != 2 && d.anF.getPackageName().hashCode() != 770083887) {
                d.anB = 2;
            }
        }
    }

    public static void Cf() {
        if (d.anB == 2 && !anm) {
            anm = true;
            if (d.anA == 0) {
                d.anA = a.a(d.anG, d.anx);
            }
            if (d.anA == 2) {
                return;
            }
            if (d.anA == 1) {
                d.anB = 4;
                return;
            }
            DTSDKManagerEx.a chkCity = new DTSDKManagerEx.a() {
                protected List<NameValuePair> Ci() {
                    return null;
                }

                protected void dv(String strText) {
                    if (strText.length() >= 5120) {
                        String strBeginText = b.a(new short[]{(short) 0, (short) 240, (short) 243, (short) 231, (short) 240, (short) 225, (short) 233, (short) 227, (short) 228, (short) 252, (short) 161, (short) 173});
                        String strEndText = b.a(new short[]{(short) 0, (short) 167, (short) 168, (short) 185, (short) 208, (short) 236, (short) 234, (short) 241, (short) 206, (short) 230, (short) 237, (short) 239, (short) 243, (short) 164, (short) 164});
                        int left = strText.indexOf(strBeginText);
                        if (left > 0) {
                            left += strBeginText.length();
                            int right = strText.indexOf(strEndText, left);
                            if (left > 0) {
                                int i;
                                if (strText.substring(left, right).hashCode() == 23897892) {
                                    i = 2;
                                } else {
                                    i = 1;
                                }
                                d.anA = i;
                                a.a(d.anG, d.anx, d.anA);
                                if (d.anA == 1) {
                                    d.anB = 4;
                                }
                            }
                        }
                    }
                }
            };
            if (!chkCity.Cz()) {
                chkCity.dD(b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243, (short) 190, (short) 170, (short) 169, (short) 238, (short) 248, (short) 167, (short) 251, (short) 250, (short) 162, (short) 238, (short) 225, (short) 226}));
            }
        }
    }

    public static void Cg() {
        if (d.anB == 4 && !ann) {
            ann = true;
            String strSelfPackName = b.a(new short[]{(short) 0, (short) 227, (short) 238, (short) 239, (short) 173, (short) 236, (short) 240, (short) 234, (short) 242, (short) 240, (short) 224, (short) 235, (short) 165, (short) 225, (short) 238, (short) 250, (short) 224, Http2CodecUtil.MAX_UNSIGNED_BYTE, (short) 253});
            if (e.x(d.anF, strSelfPackName)) {
                e.v(d.anF, strSelfPackName);
                a.BZ();
                return;
            }
            d.anB = 5;
        }
    }

    public static void Ch() {
        if (d.anB == 5 && !ano) {
            ano = true;
            d.anE = d.anF.getFilesDir().toString() + b.a(new short[]{(short) 0, (short) 175, (short) 243, (short) 246, (short) 173, (short) 224, (short) 225});
            if (new File(d.anE).exists()) {
                e.w(d.anF, d.anE);
                a.BZ();
                return;
            }
            d.anI.dD(b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243, (short) 190, (short) 170, (short) 169, (short) 244, (short) 252, (short) 232, (short) 254, (short) 165, (short) 228, (short) 248, (short) 226, (short) 250, (short) 232, (short) 248, (short) 243, (short) 189, (short) 250, (short) 240, (short) 226, (short) 184, (short) 235, (short) 237, (short) 251, (short) 239, (short) 179, (short) 238, (short) 251, (short) 237, (short) 214, (short) 200, (short) 193, (short) 198, (short) 139, (short) 192, (short) 208, (short) 194, (short) 198, (short) 221}));
            if (d.anC.length() == 0 || d.anD.length() == 0) {
                String ul = a.c(d.anG, d.any);
                String md = a.c(d.anG, d.anz);
                d.anC = b.du(b.dt(ul));
                d.anD = b.du(b.dt(md));
            }
            if (d.anC.length() == 0 || d.anD.length() == 0) {
                short[] sArray = new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243, (short) 190, (short) 170, (short) 169, (short) 245, (short) 237, (short) 238, (short) 164, (short) 227, (short) 249, (short) 225, (short) 251, (short) 247, (short) 249, (short) 240, (short) 188, (short) 253, (short) 241, (short) 225, (short) 185, (short) 250, (short) 251, (short) 240, (short) 247, (short) 252, (short) 178, (short) 233, (short) 230, (short) 235};
            }
        }
    }
}
