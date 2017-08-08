package hlx.tips;

import android.app.Activity;
import com.MCWorld.framework.base.log.HLog;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/* compiled from: Tips */
public class a {
    public static String D(Activity aty) {
        return c(aty, "tips.txt");
    }

    public static String E(Activity aty) {
        return c(aty, "storyTips.txt");
    }

    private static String c(Activity aty, String tipsPath) {
        String _strTips = null;
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(aty.getResources().getAssets().open(tipsPath), "GBK"));
            int _lineno = Integer.parseInt(bufReader.readLine());
            int _random = new Random().nextInt(_lineno);
            for (int i = 0; i < _lineno; i++) {
                if (_random == i) {
                    _strTips = bufReader.readLine();
                    break;
                }
                bufReader.readLine();
            }
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        if (_strTips == null) {
            return (String) hlx.data.localstore.a.bKH.get(new Random().nextInt(hlx.data.localstore.a.bKH.size()));
        }
        return _strTips;
    }
}
