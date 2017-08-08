package hlx.module.userguide;

import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.ab;
import java.util.HashMap;

/* compiled from: UserGuideModule */
public class b {
    public static void TT() {
        HashMap<String, String> params = new HashMap();
        params.put("type_id", String.valueOf(1));
        HttpMgr.getInstance().performStringRequest(ab.aAV, params, new 1(), new 2());
    }

    public static void TU() {
        HttpMgr.getInstance().performStringRequest(ab.aAV, new 3(), new 4());
    }
}
