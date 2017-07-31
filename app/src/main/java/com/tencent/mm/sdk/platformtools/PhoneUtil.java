package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import android.os.Build.VERSION;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.List;

public final class PhoneUtil {
    public static final String CELL_CDMA = "cdma";
    public static final String CELL_GSM = "gsm";
    private static final int aI = 17;

    public static class CellInfo {
        public static final int MAX_CID = 65535;
        public static final int MAX_LAC = 65535;
        public String cellid;
        public String dbm;
        public String lac;
        public String mcc;
        public String mnc;
        public String networkId;
        public String stationId;
        public String systemId;
        public String type;

        public CellInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
            this.mcc = str;
            this.mnc = str2;
            this.lac = str3;
            this.type = str6;
            this.cellid = str4;
            this.stationId = str7;
            this.networkId = str8;
            this.systemId = str9;
            this.dbm = str5;
        }
    }

    public static class MacInfo {
        public String dbm;
        public String mac;

        public MacInfo(String str, String str2) {
            this.mac = str;
            this.dbm = str2;
        }
    }

    private PhoneUtil() {
    }

    public static List<CellInfo> getCellInfoList(Context context) {
        return VERSION.SDK_INT >= 5 ? new PhoneUtil20Impl().getCellInfoList(context) : new PhoneUtil16Impl().getCellInfoList(context);
    }

    public static String getCellXml(List<CellInfo> list) {
        String str = "";
        if (list == null || list.size() <= 0) {
            return "";
        }
        String str2 = str;
        int i = 0;
        while (i < list.size()) {
            i++;
            str2 = (((((((((((str2 + "<cell ") + "mcc=\"" + ((CellInfo) list.get(i)).mcc + "\" ") + "mnc=\"" + ((CellInfo) list.get(i)).mnc + "\" ") + "lac=\"" + ((CellInfo) list.get(i)).lac + "\" ") + "type=\"" + ((CellInfo) list.get(i)).type + "\" ") + "stationId=\"" + ((CellInfo) list.get(i)).stationId + "\" ") + "networkId=\"" + ((CellInfo) list.get(i)).networkId + "\" ") + "systemId=\"" + ((CellInfo) list.get(i)).systemId + "\" ") + "dbm=\"" + ((CellInfo) list.get(i)).dbm + "\" ") + " >") + ((CellInfo) list.get(i)).cellid) + "</cell>";
        }
        return str2;
    }

    public static String getMacXml(List<MacInfo> list) {
        String str = "";
        if (list == null || list.size() <= 0) {
            return "";
        }
        int i = 0;
        while (i < list.size()) {
            String str2;
            if (list.get(i) == null || ((MacInfo) list.get(i)).mac.length() != aI) {
                str2 = str;
            } else {
                str2 = ((((str + "<mac ") + "macDbm=\"" + ((MacInfo) list.get(i)).dbm + "\"") + SimpleComparison.GREATER_THAN_OPERATION) + ((MacInfo) list.get(i)).mac) + "</mac>";
            }
            i++;
            str = str2;
        }
        return str;
    }

    public static void getSignalStrength(Context context) {
        if (VERSION.SDK_INT >= 5) {
            new PhoneUtil20Impl().getSignalStrength(context);
        } else {
            new PhoneUtil16Impl().getSignalStrength(context);
        }
    }
}
