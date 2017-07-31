package hlx.launch.game;

import android.app.Activity;
import com.huluxia.h;
import com.huluxia.r;

/* compiled from: GotoMCPreLaunchFromBbs */
public class a implements h {
    private static a bQT = null;

    public static synchronized a Sf() {
        a aVar;
        synchronized (a.class) {
            if (bQT == null) {
                bQT = new a();
            }
            aVar = bQT;
        }
        return aVar;
    }

    public void a(Activity context, String strVersions, int resType) {
        String _tmpTongjiEvent;
        switch (resType) {
            case 1:
                _tmpTongjiEvent = hlx.data.tongji.a.bNL;
                break;
            case 2:
                _tmpTongjiEvent = hlx.data.tongji.a.bNM;
                break;
            case 3:
                _tmpTongjiEvent = hlx.data.tongji.a.bNO;
                break;
            case 4:
                _tmpTongjiEvent = hlx.data.tongji.a.bNN;
                break;
            default:
                _tmpTongjiEvent = null;
                break;
        }
        if (_tmpTongjiEvent != null) {
            r.ck().K(_tmpTongjiEvent);
        }
        d.g(context, strVersions, resType);
    }
}
