package com.MCWorld.mcsdk.crypto;

import android.content.Context;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.tools.tar.TarConstants;

/* compiled from: DTCrypso */
public class c {
    private static byte[] aoq = new byte[]{BinaryMemcacheOpcodes.GATQ, (byte) -13, (byte) -92, (byte) -95, BinaryMemcacheOpcodes.TOUCH, (byte) 100, (byte) -62, (byte) 124, (byte) 77, (byte) -74, (byte) 119, (byte) 104, (byte) 3, (byte) 111, (byte) -88, (byte) 112, (byte) 96, (byte) -4, TarConstants.LF_CONTIG, (byte) -101, (byte) 6, (byte) -106, (byte) 16, (byte) -11, (byte) -45, (byte) -59, (byte) 97, (byte) -66, BinaryMemcacheOpcodes.INCREMENTQ, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, (byte) -93};
    private static byte[] aor = new byte[]{DefaultBinaryMemcacheRequest.REQUEST_MAGIC_BYTE, (byte) 27, (byte) -104, BinaryMemcacheOpcodes.SETQ, (byte) -46, (byte) 116, (byte) 2, (byte) -83, (byte) -109, (byte) 112, (byte) -57, (byte) -87, (byte) -2, (byte) -88, (byte) 64, (byte) -17, (byte) -88, BinaryMemcacheOpcodes.ADDQ, (byte) 27, (byte) -98, (byte) -86, (byte) -46, (byte) -11, (byte) -95};
    private static String aos = "";
    private static String aot = "";
    private Context context;

    public static void init(String assetPath, String soPath) {
        a.dF("ncwcbc2nmgx8");
        dI(soPath + new String(a.y(aor)));
    }

    public void aX(Context in_context) {
        this.context = in_context;
    }

    public void CM() {
        File file = new File(aot);
        try {
            InputStream in = this.context.getResources().getAssets().open("data/100005");
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while (true) {
                int length = in.read(buf);
                if (length != -1) {
                    out.write(buf, 0, length);
                } else {
                    out.flush();
                    in.close();
                    out.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CN() {
        try {
            e.dM(aot);
        } catch (Exception e) {
        }
    }

    public static void init(String soPath) {
        dI(soPath + "/xxx");
    }

    public void CO() {
        File file = new File(aot);
        try {
            InputStream in = this.context.getResources().getAssets().open("data/100009");
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while (true) {
                int length = in.read(buf);
                if (length != -1) {
                    out.write(buf, 0, length);
                } else {
                    out.flush();
                    in.close();
                    out.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String CP() {
        return aos;
    }

    public static void dH(String cryso_srcPath) {
        aos = cryso_srcPath;
    }

    public static String CQ() {
        return aot;
    }

    public static void dI(String cryso_dstPath) {
        aot = cryso_dstPath;
    }
}
