package hlx.ui.heroslist;

import com.huluxia.framework.AppConfig;
import com.huluxia.framework.BaseHttpMgr;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.module.ab;
import hlx.ui.heroslist.c.a;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: RankingModuleWrapper */
public class d {
    private static final boolean DEBUG = false;
    private static d bYz;

    public static synchronized d Ua() {
        d dVar;
        synchronized (d.class) {
            if (bYz == null) {
                bYz = new d();
            }
            dVar = bYz;
        }
        return dVar;
    }

    private void a(int inputStart, int inputCount, ArrayList<a> inputHeroRankList) {
        if (inputHeroRankList != null) {
            for (int i = 0; i < inputHeroRankList.size(); i++) {
                a _tmpHeroRankInfoItem = (a) inputHeroRankList.get(i);
                if (_tmpHeroRankInfoItem != null) {
                    _tmpHeroRankInfoItem.setRank(inputStart + i);
                }
            }
        }
    }

    public void a(a inputHeroInfo, String inputCRC) {
        HashMap<String, String> params = new HashMap();
        params.put("user_id", String.valueOf(inputHeroInfo.userID));
        params.put("cat_id", inputHeroInfo.cate_id);
        params.put("integral", String.valueOf(inputHeroInfo.integral));
        params.put("head_imgurl", inputHeroInfo.headImgUrl);
        params.put("name", inputHeroInfo.userName);
        params.put("_key", inputCRC);
        params.put("type_id", String.valueOf(inputHeroInfo.orderType));
        params.put(BaseHttpMgr.PARAM_APP_VERSION, UtilsVersion.getVersionString(AppConfig.getInstance().getAppContext()));
        HttpMgr.getInstance().performPostStringRequest(ab.aAK, null, params, new 1(this), new 2(this), false, false, true, 50000);
    }

    public void C(int type, int start, int count, int orderType) {
        HashMap<String, String> params = new HashMap();
        params.put("cat_id", String.valueOf(type));
        params.put("start", String.valueOf(start));
        params.put("count", String.valueOf(count));
        params.put("type_id", String.valueOf(orderType));
        HttpMgr.getInstance().performStringRequest(ab.aAJ, params, new 3(this, start, count, type), new 4(this, type), false, false);
    }
}
