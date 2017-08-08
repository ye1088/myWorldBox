package hlx.mcstorymode.storyfilecheck;

import com.MCWorld.mcgame.h;
import hlx.mcstorymode.c;
import java.io.File;

/* compiled from: MCStoryFile06Check */
public class f {
    private static final boolean DEBUG = false;
    private static int bUj = 1;
    private static String[][] bUk;
    private static f bXc;

    public static synchronized f TK() {
        f fVar;
        synchronized (f.class) {
            if (bXc == null) {
                bXc = new f();
            }
            fVar = bXc;
        }
        return fVar;
    }

    public boolean hh(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(c.bUN)) {
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
                if (!com.MCWorld.mcsdk.dtlib.c.getFileMD5(tmp_path).equalsIgnoreCase(bUk[i][1])) {
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
        strArr[0] = new String[]{c.bVw, "476B532FD3A3158C3A6A7593C66C7ED2"};
        bUk = strArr;
    }
}
