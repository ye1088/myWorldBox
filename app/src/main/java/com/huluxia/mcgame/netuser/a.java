package com.huluxia.mcgame.netuser;

import com.huluxia.mcsdk.DTSDKManagerEx;
import java.util.ArrayList;

/* compiled from: DTNetUserManager */
public class a {
    private static a agH;
    private static ArrayList<b> agI = new ArrayList(5);
    private int agE = 0;
    private String agF;
    private long agG;

    public static synchronized a yQ() {
        a aVar;
        synchronized (a.class) {
            if (agH == null) {
                agH = new a();
                agH.yR();
            }
            aVar = agH;
        }
        return aVar;
    }

    private void yR() {
        for (int i = 0; i < 5; i++) {
            agI.add(new b(0, 0, 0, 0, null, false));
        }
    }

    public void W(long inputId) {
        if (0 != inputId) {
            for (int i = 0; i < 5; i++) {
                b pTmpDTBagItem = (b) agI.get(i);
                if (!(pTmpDTBagItem == null || pTmpDTBagItem.isValid())) {
                    pTmpDTBagItem.bI(true);
                }
            }
        }
    }

    public void X(long inputId) {
        if (0 != inputId) {
            for (int i = 0; i < 5; i++) {
                b pTmpDTBagItem = (b) agI.get(i);
                if (pTmpDTBagItem != null && pTmpDTBagItem.isValid() && pTmpDTBagItem.yX() == inputId) {
                    pTmpDTBagItem.bI(false);
                }
            }
        }
    }

    public void yS() {
        this.agG = DTSDKManagerEx.Cq();
        this.agF = DTSDKManagerEx.at(this.agG);
        if (this.agF == null) {
            for (int i = 0; i < 5; i++) {
                b pTmpDTBagItem = (b) agI.get(i);
                if (pTmpDTBagItem != null && pTmpDTBagItem.isValid()) {
                    this.agF = pTmpDTBagItem.getName();
                }
            }
        }
    }

    public void a(long[] inputNetPlayerArray) {
        for (int i = 0; i < 5; i++) {
            b pTmpDTBagItem = (b) agI.get(i);
            if (pTmpDTBagItem != null && pTmpDTBagItem.isValid()) {
                boolean bValid = false;
                long _tmpNetPlayerId = pTmpDTBagItem.yX();
                for (int j = 0; j < 5; j++) {
                    if (_tmpNetPlayerId == inputNetPlayerArray[j]) {
                        bValid = true;
                        break;
                    }
                }
                if (!bValid) {
                    pTmpDTBagItem.bI(false);
                }
            }
        }
    }

    public void yT() {
        for (int i = 0; i < 5; i++) {
            b pTmpDTBagItem = (b) agI.get(i);
            if (pTmpDTBagItem != null && pTmpDTBagItem.isValid()) {
                com.huluxia.mcsdk.log.a.verbose("TAG", "DTPrint NetPlayer[%d]:(%f,%f,%f) -- [%s] \n", new Object[]{Integer.valueOf(i), Float.valueOf(pTmpDTBagItem.yj()), Float.valueOf(pTmpDTBagItem.yk()), Float.valueOf(pTmpDTBagItem.yl()), pTmpDTBagItem.getName()});
            }
        }
    }

    public int yU() {
        int _tmpCnt = 0;
        if (agI == null) {
            return 0;
        }
        for (int i = 0; i < 5; i++) {
            b pTmpDTBagItem = (b) agI.get(i);
            if (pTmpDTBagItem != null && pTmpDTBagItem.isValid()) {
                _tmpCnt++;
            }
        }
        return _tmpCnt;
    }

    public b hf(int inputIndex) {
        if (inputIndex < 5) {
            return (b) agI.get(inputIndex);
        }
        return null;
    }

    public void a(long inputEntityId, float inputX, float inputY, float inputZ, String inputName) {
        if (0 != inputEntityId) {
            for (int i = 0; i < 5; i++) {
                b pTmpDTBagItem = (b) agI.get(i);
                if (pTmpDTBagItem != null) {
                    if (pTmpDTBagItem.isValid() && pTmpDTBagItem.yX() == inputEntityId) {
                        pTmpDTBagItem.u(inputX);
                        pTmpDTBagItem.v(inputY);
                        pTmpDTBagItem.w(inputZ);
                        pTmpDTBagItem.setName(inputName);
                        pTmpDTBagItem.bI(true);
                        return;
                    } else if (!pTmpDTBagItem.isValid()) {
                        pTmpDTBagItem.Y(inputEntityId);
                        pTmpDTBagItem.u(inputX);
                        pTmpDTBagItem.v(inputY);
                        pTmpDTBagItem.w(inputZ);
                        pTmpDTBagItem.setName(inputName);
                        pTmpDTBagItem.bI(true);
                        return;
                    }
                }
            }
        }
    }

    public int yV() {
        return this.agE;
    }

    public void hg(int mNetPlayerCnt) {
        this.agE = mNetPlayerCnt;
    }

    public String yW() {
        return this.agF;
    }
}
