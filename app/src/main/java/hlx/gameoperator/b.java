package hlx.gameoperator;

import android.content.Context;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.u;
import com.MCWorld.mojang.Mojang;
import com.MCWorld.mojang.MojangMessage;
import com.MCWorld.r;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.j;
import hlx.data.tongji.a;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.tools.tar.TarConstants;

/* compiled from: GenerateMapOperator */
public class b {
    private static b bOZ;
    private static byte[] bPa = new byte[]{(byte) 4, (byte) 0, (byte) 0, (byte) 0, (byte) 99, (byte) 3, (byte) 0, (byte) 0, (byte) 10, (byte) 0, (byte) 0, (byte) 4, (byte) 16, (byte) 0, (byte) 100, (byte) 97, (byte) 121, (byte) 67, (byte) 121, (byte) 99, (byte) 108, (byte) 101, TarConstants.LF_GNUTYPE_SPARSE, (byte) 116, (byte) 111, (byte) 112, (byte) 84, (byte) 105, (byte) 109, (byte) 101, (byte) 78, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 8, (byte) 0, (byte) 71, (byte) 97, (byte) 109, (byte) 101, (byte) 84, (byte) 121, (byte) 112, (byte) 101, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 9, (byte) 0, (byte) 71, (byte) 101, (byte) 110, (byte) 101, (byte) 114, (byte) 97, (byte) 116, (byte) 111, (byte) 114, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 4, (byte) 10, (byte) 0, TarConstants.LF_GNUTYPE_LONGNAME, (byte) 97, (byte) 115, (byte) 116, (byte) 80, (byte) 108, (byte) 97, (byte) 121, (byte) 101, (byte) 100, (byte) -23, (byte) 80, (byte) -42, (byte) 86, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 8, (byte) 9, (byte) 0, TarConstants.LF_GNUTYPE_LONGNAME, (byte) 101, (byte) 118, (byte) 101, (byte) 108, (byte) 78, (byte) 97, (byte) 109, (byte) 101, (byte) 7, (byte) 0, TarConstants.LF_FIFO, TarConstants.LF_DIR, TarConstants.LF_SYMLINK, TarConstants.LF_NORMAL, TarConstants.LF_CHR, TarConstants.LF_NORMAL, TarConstants.LF_NORMAL, (byte) 3, (byte) 8, (byte) 0, (byte) 80, (byte) 108, (byte) 97, (byte) 116, (byte) 102, (byte) 111, (byte) 114, (byte) 109, (byte) 2, (byte) 0, (byte) 0, (byte) 0, (byte) 10, (byte) 6, (byte) 0, (byte) 80, (byte) 108, (byte) 97, (byte) 121, (byte) 101, (byte) 114, (byte) 2, (byte) 3, (byte) 0, (byte) 65, (byte) 105, (byte) 114, HttpConstants.COMMA, (byte) 1, (byte) 9, (byte) 5, (byte) 0, (byte) 65, (byte) 114, (byte) 109, (byte) 111, (byte) 114, (byte) 10, (byte) 4, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 5, (byte) 0, (byte) 67, (byte) 111, (byte) 117, (byte) 110, (byte) 116, (byte) 0, (byte) 2, (byte) 6, (byte) 0, (byte) 68, (byte) 97, (byte) 109, (byte) 97, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 101, (byte) 0, (byte) 0, (byte) 2, (byte) 2, (byte) 0, (byte) 105, (byte) 100, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 5, (byte) 0, (byte) 67, (byte) 111, (byte) 117, (byte) 110, (byte) 116, (byte) 0, (byte) 2, (byte) 6, (byte) 0, (byte) 68, (byte) 97, (byte) 109, (byte) 97, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 101, (byte) 0, (byte) 0, (byte) 2, (byte) 2, (byte) 0, (byte) 105, (byte) 100, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 5, (byte) 0, (byte) 67, (byte) 111, (byte) 117, (byte) 110, (byte) 116, (byte) 0, (byte) 2, (byte) 6, (byte) 0, (byte) 68, (byte) 97, (byte) 109, (byte) 97, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 101, (byte) 0, (byte) 0, (byte) 2, (byte) 2, (byte) 0, (byte) 105, (byte) 100, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 5, (byte) 0, (byte) 67, (byte) 111, (byte) 117, (byte) 110, (byte) 116, (byte) 0, (byte) 2, (byte) 6, (byte) 0, (byte) 68, (byte) 97, (byte) 109, (byte) 97, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 101, (byte) 0, (byte) 0, (byte) 2, (byte) 2, (byte) 0, (byte) 105, (byte) 100, (byte) 0, (byte) 0, (byte) 0, (byte) 2, (byte) 10, (byte) 0, (byte) 65, (byte) 116, (byte) 116, (byte) 97, (byte) 99, (byte) 107, (byte) 84, (byte) 105, (byte) 109, (byte) 101, (byte) 0, (byte) 0, (byte) 9, (byte) 10, (byte) 0, (byte) 65, (byte) 116, (byte) 116, (byte) 114, (byte) 105, (byte) 98, (byte) 117, (byte) 116, (byte) 101, (byte) 115, (byte) 10, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 12, (byte) 0, (byte) 66, (byte) 101, (byte) 100, (byte) 80, (byte) 111, (byte) 115, (byte) 105, (byte) 116, (byte) 105, (byte) 111, (byte) 110, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 12, (byte) 0, (byte) 66, (byte) 101, (byte) 100, (byte) 80, (byte) 111, (byte) 115, (byte) 105, (byte) 116, (byte) 105, (byte) 111, (byte) 110, (byte) 89, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 12, (byte) 0, (byte) 66, (byte) 101, (byte) 100, (byte) 80, (byte) 111, (byte) 115, (byte) 105, (byte) 116, (byte) 105, (byte) 111, (byte) 110, (byte) 90, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 2, (byte) 9, (byte) 0, (byte) 68, (byte) 101, (byte) 97, (byte) 116, (byte) 104, (byte) 84, (byte) 105, (byte) 109, (byte) 101, (byte) 0, (byte) 0, (byte) 3, (byte) 9, (byte) 0, (byte) 68, (byte) 105, (byte) 109, (byte) 101, (byte) 110, (byte) 115, (byte) 105, (byte) 111, (byte) 110, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 5, (byte) 12, (byte) 0, (byte) 70, (byte) 97, (byte) 108, (byte) 108, (byte) 68, (byte) 105, (byte) 115, (byte) 116, (byte) 97, (byte) 110, (byte) 99, (byte) 101, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 2, (byte) 4, (byte) 0, (byte) 70, (byte) 105, (byte) 114, (byte) 101, (byte) -20, (byte) -1, (byte) 2, (byte) 6, (byte) 0, (byte) 72, (byte) 101, (byte) 97, (byte) 108, (byte) 116, (byte) 104, BinaryMemcacheOpcodes.DELETEQ, (byte) 0, (byte) 2, (byte) 8, (byte) 0, (byte) 72, (byte) 117, (byte) 114, (byte) 116, (byte) 84, (byte) 105, (byte) 109, (byte) 101, (byte) 0, (byte) 0, (byte) 9, (byte) 9, (byte) 0, (byte) 73, (byte) 110, (byte) 118, (byte) 101, (byte) 110, (byte) 116, (byte) 111, (byte) 114, (byte) 121, (byte) 10, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 9, (byte) 6, (byte) 0, (byte) 77, (byte) 111, (byte) 116, (byte) 105, (byte) 111, (byte) 110, (byte) 5, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0, DefaultBinaryMemcacheRequest.REQUEST_MAGIC_BYTE, (byte) 46, (byte) -112, (byte) -96, HttpConstants.EQUALS, (byte) 1, (byte) 0, (byte) 0, DefaultBinaryMemcacheRequest.REQUEST_MAGIC_BYTE, (byte) 1, (byte) 8, (byte) 0, (byte) 79, (byte) 110, (byte) 71, (byte) 114, (byte) 111, (byte) 117, (byte) 110, (byte) 100, (byte) 1, (byte) 3, (byte) 11, (byte) 0, (byte) 80, (byte) 108, (byte) 97, (byte) 121, (byte) 101, (byte) 114, TarConstants.LF_GNUTYPE_LONGNAME, (byte) 101, (byte) 118, (byte) 101, (byte) 108, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 5, BinaryMemcacheOpcodes.REPLACEQ, (byte) 0, (byte) 80, (byte) 108, (byte) 97, (byte) 121, (byte) 101, (byte) 114, TarConstants.LF_GNUTYPE_LONGNAME, (byte) 101, (byte) 118, (byte) 101, (byte) 108, (byte) 80, (byte) 114, (byte) 111, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 114, (byte) 101, (byte) 115, (byte) 115, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 9, (byte) 3, (byte) 0, (byte) 80, (byte) 111, (byte) 115, (byte) 5, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 9, (byte) 8, (byte) 0, (byte) 82, (byte) 111, (byte) 116, (byte) 97, (byte) 116, (byte) 105, (byte) 111, (byte) 110, (byte) 5, (byte) 2, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 5, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 99, (byte) 111, (byte) 114, (byte) 101, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 2, (byte) 10, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 108, (byte) 101, (byte) 101, (byte) 112, (byte) 84, (byte) 105, (byte) 109, (byte) 101, (byte) 114, (byte) 0, (byte) 0, (byte) 1, (byte) 8, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 108, (byte) 101, (byte) 101, (byte) 112, (byte) 105, (byte) 110, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 0, (byte) 3, (byte) 6, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 112, (byte) 97, (byte) 119, (byte) 110, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) -62, (byte) 3, (byte) 0, (byte) 0, (byte) 3, (byte) 6, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 112, (byte) 97, (byte) 119, (byte) 110, (byte) 89, (byte) 67, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 6, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 112, (byte) 97, (byte) 119, (byte) 110, (byte) 90, BinaryMemcacheOpcodes.ADDQ, (byte) 0, (byte) 0, (byte) 0, (byte) 10, (byte) 9, (byte) 0, (byte) 97, (byte) 98, (byte) 105, (byte) 108, (byte) 105, (byte) 116, (byte) 105, (byte) 101, (byte) 115, (byte) 1, (byte) 6, (byte) 0, (byte) 102, (byte) 108, (byte) 121, (byte) 105, (byte) 110, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 0, (byte) 1, (byte) 10, (byte) 0, (byte) 105, (byte) 110, (byte) 115, (byte) 116, (byte) 97, (byte) 98, (byte) 117, (byte) 105, (byte) 108, (byte) 100, (byte) 0, (byte) 1, (byte) 12, (byte) 0, (byte) 105, (byte) 110, (byte) 118, (byte) 117, (byte) 108, (byte) 110, (byte) 101, (byte) 114, (byte) 97, (byte) 98, (byte) 108, (byte) 101, (byte) 0, (byte) 1, (byte) 6, (byte) 0, (byte) 109, (byte) 97, (byte) 121, (byte) 102, (byte) 108, (byte) 121, (byte) 0, (byte) 0, (byte) 0, (byte) 4, (byte) 10, (byte) 0, (byte) 82, (byte) 97, (byte) 110, (byte) 100, (byte) 111, (byte) 109, TarConstants.LF_GNUTYPE_SPARSE, (byte) 101, (byte) 101, (byte) 100, (byte) -20, (byte) 125, (byte) 99, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 4, (byte) 10, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 105, (byte) 122, (byte) 101, (byte) 79, (byte) 110, (byte) 68, (byte) 105, (byte) 115, (byte) 107, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 6, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 112, (byte) 97, (byte) 119, (byte) 110, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) -62, (byte) 3, (byte) 0, (byte) 0, (byte) 3, (byte) 6, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 112, (byte) 97, (byte) 119, (byte) 110, (byte) 89, (byte) 67, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 6, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 112, (byte) 97, (byte) 119, (byte) 110, (byte) 90, BinaryMemcacheOpcodes.ADDQ, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 14, (byte) 0, TarConstants.LF_GNUTYPE_SPARSE, (byte) 116, (byte) 111, (byte) 114, (byte) 97, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, (byte) 101, (byte) 86, (byte) 101, (byte) 114, (byte) 115, (byte) 105, (byte) 111, (byte) 110, (byte) 4, (byte) 0, (byte) 0, (byte) 0, (byte) 4, (byte) 4, (byte) 0, (byte) 84, (byte) 105, (byte) 109, (byte) 101, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 9, (byte) 0, (byte) 115, (byte) 112, (byte) 97, (byte) 119, (byte) 110, (byte) 77, (byte) 111, (byte) 98, (byte) 115, (byte) 1, (byte) 3, (byte) 9, (byte) 0, (byte) 68, (byte) 105, (byte) 109, (byte) 101, (byte) 110, (byte) 115, (byte) 105, (byte) 111, (byte) 110, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private CallbackHandler akz = new CallbackHandler(this) {
        final /* synthetic */ b bPb;

        {
            this.bPb = this$0;
        }

        @MessageHandler(message = 263)
        public void onRecvLevelData(boolean succ, Object object) {
            if (succ) {
                this.bPb.RJ();
            } else {
                HLog.verbose("TAG", "LSPrint reload level data flair!", new Object[0]);
            }
        }
    };
    private String bOS;
    private long bOT;
    private long bOU;
    private int bOV;
    private int bOW;
    private int bOX;
    private boolean bOY;
    private String bat;

    public static synchronized b RI() {
        b bVar;
        synchronized (b.class) {
            if (bOZ == null) {
                bOZ = new b();
            }
            bVar = bOZ;
        }
        return bVar;
    }

    public void a(Context context, long seedId, String mapDirName, long mapRandomSeed, int inputX, int inputY, int inputZ) {
        this.bat = mapDirName;
        this.bOS = mapDirName;
        this.bOT = seedId;
        this.bOU = mapRandomSeed;
        this.bOV = inputX;
        this.bOW = inputY;
        this.bOX = inputZ;
        String mcMapsPath = j.Kq();
        while (UtilsFile.isExist(mcMapsPath + this.bOS)) {
            this.bOS += "-";
        }
        UtilsFile.mkdir(mcMapsPath + this.bOS);
        try {
            FileOutputStream fos = new FileOutputStream(new File(mcMapsPath + this.bOS + File.separator + "level.dat"));
            fos.write(bPa);
            fos.close();
        } catch (Exception e) {
            HLog.verbose("Exception", e.getMessage(), new Object[0]);
        }
        EventNotifyCenter.add(MojangMessage.class, this.akz);
        try {
            Mojang.instance().init(mapDirName, j.Kq() + this.bOS);
        } catch (Exception e2) {
            HLog.verbose("Exception", e2.getMessage(), new Object[0]);
        }
    }

    private void RJ() {
        try {
            Mojang.instance().renameMap(this.bat);
            Mojang.instance().alterMapRandomSeed(this.bOU);
            Mojang.instance().alterMapSpawn(this.bOV, this.bOW, this.bOX);
            Mojang.instance().setLastPlayed(System.currentTimeMillis() / 1000);
            u.aL(this.bOT);
            r.ck().j(a.bOA, String.valueOf(this.bOT));
        } catch (Exception e) {
            HLog.verbose("Exception", e.getMessage(), new Object[0]);
        }
        EventNotifyCenter.remove(this.akz);
    }
}
