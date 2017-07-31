package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import com.huluxia.data.profile.a;
import com.tencent.mm.sdk.platformtools.PhoneUtil.CellInfo;
import java.util.LinkedList;
import java.util.List;

class PhoneUtil16Impl {
    private static int aJ = 10000;
    private static int aK = 10000;
    private TelephonyManager aL;
    private PhoneStateListener aM = new PhoneStateListener(this) {
        final /* synthetic */ PhoneUtil16Impl aN;

        {
            this.aN = r1;
        }

        public void onSignalStrengthChanged(int i) {
            super.onSignalStrengthChanged(i);
            PhoneUtil16Impl.aK = (i * 2) - 113;
            if (this.aN.aL != null) {
                this.aN.aL.listen(this.aN.aM, 0);
            }
        }
    };

    PhoneUtil16Impl() {
    }

    public List<CellInfo> getCellInfoList(Context context) {
        List<CellInfo> linkedList = new LinkedList();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(a.qe);
        String networkOperator = telephonyManager.getNetworkOperator();
        if (networkOperator == null || networkOperator.equals("")) {
            return linkedList;
        }
        String str = "460";
        String str2 = "";
        try {
            str = networkOperator.substring(0, 3);
            str2 = networkOperator.substring(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
            if (gsmCellLocation != null) {
                int cid = gsmCellLocation.getCid();
                int lac = gsmCellLocation.getLac();
                if (!(lac >= 65535 || lac == -1 || cid == -1)) {
                    linkedList.add(new CellInfo(str, str2, String.valueOf(lac), String.valueOf(cid), aK == aJ ? "" : aK, PhoneUtil.CELL_GSM, "", "", ""));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        List<NeighboringCellInfo> neighboringCellInfo = telephonyManager.getNeighboringCellInfo();
        if (neighboringCellInfo != null && neighboringCellInfo.size() > 0) {
            for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                if (neighboringCellInfo2.getCid() != -1) {
                    linkedList.add(new CellInfo(str, str2, "", neighboringCellInfo2.getCid(), "", PhoneUtil.CELL_GSM, "", "", ""));
                }
            }
        }
        return linkedList;
    }

    public void getSignalStrength(Context context) {
        this.aL = (TelephonyManager) context.getSystemService(a.qe);
        this.aL.listen(this.aM, 256);
    }
}
