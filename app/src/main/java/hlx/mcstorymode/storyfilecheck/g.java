package hlx.mcstorymode.storyfilecheck;

import com.huluxia.mcgame.h;
import hlx.mcstorymode.c;
import java.io.File;

/* compiled from: MCStoryFile07Check */
public class g {
    private static final boolean DEBUG = false;
    private static int bUj = 1;
    private static String[][] bUk;
    private static g bXd;

    public static synchronized g TL() {
        g gVar;
        synchronized (g.class) {
            if (bXd == null) {
                bXd = new g();
            }
            gVar = bXd;
        }
        return gVar;
    }

    public boolean hh(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(c.bUO)) {
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
        try {
            for (int i = bUj - 1; i >= 0; i--) {
                tmp_path = path + File.separator + bUk[i][0];
                if (!com.huluxia.mcsdk.dtlib.c.getFileMD5(tmp_path).equalsIgnoreCase(bUk[i][1])) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static {
        String[][] strArr = new String[1][];
        strArr[0] = new String[]{c.bVx, "b283e17a6f33318f0ed6a876a8c281b6"};
        bUk = strArr;
    }
}
