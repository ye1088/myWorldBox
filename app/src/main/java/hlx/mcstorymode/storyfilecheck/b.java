package hlx.mcstorymode.storyfilecheck;

import com.MCWorld.mcgame.h;
import hlx.mcstorymode.c;
import java.io.File;

/* compiled from: MCStoryFile02Check */
public class b {
    private static final boolean DEBUG = false;
    private static int bUj = 1;
    private static String[][] bUk;
    private static b bWY;

    public static synchronized b TG() {
        b bVar;
        synchronized (b.class) {
            if (bWY == null) {
                bWY = new b();
            }
            bVar = bWY;
        }
        return bVar;
    }

    public boolean hh(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(c.bUJ)) {
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
        strArr[0] = new String[]{c.bVs, "bdd9098a3057901141eb8aa785897f3f"};
        bUk = strArr;
    }
}
