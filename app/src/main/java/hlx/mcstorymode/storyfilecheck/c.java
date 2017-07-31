package hlx.mcstorymode.storyfilecheck;

import com.huluxia.mcgame.h;
import java.io.File;

/* compiled from: MCStoryFile03Check */
public class c {
    private static final boolean DEBUG = false;
    private static int bUj = 1;
    private static String[][] bUk;
    private static c bWZ;

    public static synchronized c TH() {
        c cVar;
        synchronized (c.class) {
            if (bWZ == null) {
                bWZ = new c();
            }
            cVar = bWZ;
        }
        return cVar;
    }

    public boolean hh(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(hlx.mcstorymode.c.bUK)) {
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
                if (!com.huluxia.mcsdk.dtlib.c.getFileMD5(tmp_path).equalsIgnoreCase(bUk[i][1])) {
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
        strArr[0] = new String[]{hlx.mcstorymode.c.bVt, "68322f4d56497eb0c2b3a09ca5afb27b"};
        bUk = strArr;
    }
}
