package hlx.ui.heroslist;

import com.huluxia.data.j;

/* compiled from: HeroInterface */
public class b {
    private static final boolean DEBUG = false;
    private static b bXV;

    public static synchronized b TZ() {
        b bVar;
        synchronized (b.class) {
            if (bXV == null) {
                bXV = new b();
            }
            bVar = bXV;
        }
        return bVar;
    }

    public void bo(int inputTotalScore, int orderType) {
        ak(4, inputTotalScore, orderType);
    }

    public void bp(int inputTotalScore, int orderType) {
        ak(3, inputTotalScore, orderType);
    }

    public void bq(int inputTotalScore, int orderType) {
        ak(2, inputTotalScore, orderType);
    }

    public void br(int inputTotalScore, int orderType) {
        ak(1, inputTotalScore, orderType);
    }

    public void bs(int inputTime, int orderType) {
        ak(5, inputTime, orderType);
    }

    public void bt(int inputTime, int orderType) {
        ak(6, inputTime, orderType);
    }

    public void bu(int inputTime, int orderType) {
        ak(7, inputTime, orderType);
    }

    private void ak(int inputFBType, int inputTotalScore, int inputOrderType) {
        try {
            String _tmpUsrName = j.ep().er().getNick();
            long _tmpUsrId = j.ep().er().getUserID();
            TZ().a(inputFBType, inputTotalScore, _tmpUsrName, _tmpUsrId, j.ep().er().getAvatar(), a.TY().aj(inputFBType, (int) _tmpUsrId, inputTotalScore), inputOrderType);
        } catch (Exception e) {
        }
    }

    private void a(int inputFBType, int inputIntegral, String inputUserName, long inputUserId, String inputUserImgUrl, String inputCRC, int inputOrderType) {
        c$a _tmpHeroInfo = new c$a();
        _tmpHeroInfo.cate_id = String.valueOf(inputFBType);
        _tmpHeroInfo.integral = inputIntegral;
        _tmpHeroInfo.userName = inputUserName;
        _tmpHeroInfo.userID = inputUserId;
        _tmpHeroInfo.headImgUrl = inputUserImgUrl;
        _tmpHeroInfo.orderType = inputOrderType;
        d.Ua().a(_tmpHeroInfo, inputCRC);
    }
}
