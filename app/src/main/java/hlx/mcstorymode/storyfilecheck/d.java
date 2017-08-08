package hlx.mcstorymode.storyfilecheck;

import com.MCWorld.mcgame.h;
import hlx.mcstorymode.c;
import java.io.File;

/* compiled from: MCStoryFile04Check */
public class d {
    private static final boolean DEBUG = false;
    private static int bUj = 1;
    private static String[][] bUk;
    private static d bXa;

    public static synchronized d TI() {
        d dVar;
        synchronized (d.class) {
            if (bXa == null) {
                bXa = new d();
            }
            dVar = bXa;
        }
        return dVar;
    }

    public boolean hh(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(c.bUL)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean hi(String path) {
        String tmp_md5 = "";
        String std_md5 = "";
        String tmp_path = "";
        int i = 0;
        while (i < bUj) {
            try {
                tmp_path = path + File.separator + bUk[i][0];
                if (!com.MCWorld.mcsdk.dtlib.c.getFileMD5(tmp_path).equalsIgnoreCase(bUk[i][1])) {
                    return false;
                }
                i++;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    static {
        String[][] strArr = new String[1][];
        strArr[0] = new String[]{c.bVu, "E2246763F779384D0C1FEA5BFDDE18F3"};
        bUk = strArr;
    }
}
