package hlx.mcstorymode.storyfilecheck;

import com.huluxia.mcgame.h;
import hlx.mcstorymode.c;
import java.io.File;

/* compiled from: MCStoryFile01Check */
public class a {
    private static final boolean DEBUG = false;
    private static int bUj = 1;
    private static String[][] bUk;
    private static a bWX;

    public static synchronized a TF() {
        a aVar;
        synchronized (a.class) {
            if (bWX == null) {
                bWX = new a();
            }
            aVar = bWX;
        }
        return aVar;
    }

    public boolean hh(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(c.bUI)) {
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
        strArr[0] = new String[]{c.bVr, "c472bf9281f1b15b50b261561efc6e9"};
        bUk = strArr;
    }
}
