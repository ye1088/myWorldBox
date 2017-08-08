package com.baidu.mapapi;

import android.os.Bundle;
import android.util.Log;
import com.MCWorld.module.p;
import com.tencent.mm.sdk.plugin.BaseProfile;
import com.tencent.open.SocialConstants;
import java.io.UnsupportedEncodingException;

public class MKSearch {
    public static final int EBUS_NO_SUBWAY = 6;
    public static final int EBUS_TIME_FIRST = 3;
    public static final int EBUS_TRANSFER_FIRST = 4;
    public static final int EBUS_WALK_FIRST = 5;
    public static final int ECAR_DIS_FIRST = 1;
    public static final int ECAR_FEE_FIRST = 2;
    public static final int ECAR_TIME_FIRST = 0;
    public static final int POI_DETAIL_SEARCH = 52;
    public static final int TYPE_AREA_MULTI_POI_LIST = 45;
    public static final int TYPE_AREA_POI_LIST = 21;
    public static final int TYPE_CITY_LIST = 7;
    public static final int TYPE_POI_LIST = 11;
    private static int b = 10;
    private Bundle a = null;
    private int c = 3;
    private int d = 0;

    public static int getPoiPageCapacity() {
        return b;
    }

    public static void setPoiPageCapacity(int i) {
        if (i > 0 && i <= 50) {
            b = i;
        }
    }

    public int busLineSearch(String str, String str2) {
        if (str == null || str.length() <= 0 || str2 == null || str2.length() <= 0 || str.length() > 31) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010105);
        try {
            this.a.putByteArray(BaseProfile.COL_CITY, str.getBytes("gb2312"));
        } catch (UnsupportedEncodingException e) {
            Log.d("MKSearch busLineSearch:", "busLine city error!!");
            Log.d("MKSearch busLineSearch:", e.getMessage());
        }
        try {
            this.a.putByteArray("busid", str2.getBytes("gb2312"));
        } catch (UnsupportedEncodingException e2) {
            Log.d("MKSearch busLineSearch:", "busLine name error!!");
            Log.d("MKSearch busLineSearch:", e2.getMessage());
        }
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int drivingSearch(String str, MKPlanNode mKPlanNode, String str2, MKPlanNode mKPlanNode2) {
        if (mKPlanNode == null || mKPlanNode2 == null) {
            return -1;
        }
        int i;
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11020203);
        this.a.putInt("opt", 11020102);
        if (mKPlanNode.pt != null) {
            this.a.putInt("start_x", mKPlanNode.pt.getLongitudeE6());
            this.a.putInt("start_y", mKPlanNode.pt.getLatitudeE6());
            i = 1;
        } else {
            i = 0;
        }
        if (mKPlanNode.name != null && mKPlanNode.name.length() <= 99) {
            try {
                this.a.putByteArray("start_name", mKPlanNode.name.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e) {
                Log.d("MKSearch drivingSearch:", "translate start name error!!");
                Log.d("MKSearch drivingSearch:", e.getMessage());
            }
            if (str == null || str.length() > 31) {
                return -1;
            }
            try {
                this.a.putByteArray("start_city", str.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e2) {
                Log.d("MKSearch drivingSearch:", "translate start city error!!");
                Log.d("MKSearch drivingSearch:", e2.getMessage());
            }
            i = 1;
        }
        if (i == 0) {
            return -1;
        }
        if (mKPlanNode2.pt != null) {
            this.a.putInt("end_x", mKPlanNode2.pt.getLongitudeE6());
            this.a.putInt("end_y", mKPlanNode2.pt.getLatitudeE6());
            i = 1;
        } else {
            i = 0;
        }
        if (mKPlanNode2.name != null && mKPlanNode2.name.length() <= 99) {
            try {
                this.a.putByteArray("end_name", mKPlanNode2.name.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e22) {
                Log.d("MKSearch drivingSearch:", "translate end name error!!");
                Log.d("MKSearch drivingSearch:", e22.getMessage());
            }
            if (str2 == null || str2.length() > 31) {
                return -1;
            }
            try {
                this.a.putByteArray("end_city", str2.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e222) {
                Log.d("MKSearch drivingSearch:", "translate end city error!!");
                Log.d("MKSearch drivingSearch:", e222.getMessage());
            }
            i = 1;
        }
        if (i == 0) {
            return -1;
        }
        this.a.putInt("policy", this.d);
        return Mj.sendBundle(this.a) == 0 ? -1 : 0;
    }

    public int geocode(String str, String str2) {
        if (str == null || str.length() == 0 || str.length() > 99) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010112);
        this.a.putString("addr", str);
        if (str2 != null && str2.length() <= 16) {
            try {
                this.a.putByteArray(BaseProfile.COL_CITY, str2.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int goToPoiPage(int i) {
        if (this.a == null || !this.a.containsKey("page_num")) {
            return -1;
        }
        this.a.putInt("cnt", b);
        this.a.putInt("page_num", i);
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public boolean init(BMapManager bMapManager, MKSearchListener mKSearchListener) {
        if (bMapManager == null) {
            return false;
        }
        if (mKSearchListener != null) {
            bMapManager.a.a(mKSearchListener);
        }
        return Mj.initSearchCC() == 1;
    }

    public int offlineSearch(int i) {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010108);
        this.a.putInt("cityid", i);
        if (Mj.i <= 180) {
            this.a.putInt(p.UID, 0);
        } else {
            this.a.putInt(p.UID, 1);
        }
        return Mj.sendBundle(this.a) == 0 ? -1 : 0;
    }

    public int poiDetailSearch(String str) {
        if (str == null) {
            return -1;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(SocialConstants.PARAM_ACT, 11010203);
        bundle.putInt("opt", 11010113);
        bundle.putString(p.UID, str);
        bundle.putString("key", null);
        bundle.putString("d_data_type", "cater");
        bundle.putInt("page_num", 0);
        bundle.putInt("cnt", b);
        return Mj.sendBundle(bundle) != 0 ? 0 : -1;
    }

    public int poiMultiSearchInbounds(String[] strArr, GeoPoint geoPoint, GeoPoint geoPoint2) {
        if (strArr == null || geoPoint == null || geoPoint2 == null) {
            return -1;
        }
        if (strArr.length < 2 || strArr.length > 10) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] == null) {
                return -1;
            }
            String trim = strArr[i].trim();
            if (trim.length() == 0 || trim.length() > 99 || trim.contains("$$")) {
                return -1;
            }
            stringBuilder.append(trim);
            if (i != strArr.length - 1) {
                stringBuilder.append("$$");
            }
        }
        if (stringBuilder.toString().length() > 99) {
            return -1;
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010111);
        this.a.putString("key", stringBuilder.toString());
        this.a.putInt("page_num", 0);
        this.a.putInt("cnt", b);
        this.a.putInt("start_x", geoPoint.getLongitudeE6());
        this.a.putInt("start_y", geoPoint.getLatitudeE6());
        this.a.putInt("end_x", geoPoint2.getLongitudeE6());
        this.a.putInt("end_y", geoPoint2.getLatitudeE6());
        this.a.putInt("key_num", strArr.length);
        return Mj.sendBundle(this.a) == 0 ? -1 : 0;
    }

    public int poiMultiSearchNearBy(String[] strArr, GeoPoint geoPoint, int i) {
        if (geoPoint == null || strArr == null) {
            return -1;
        }
        if (i <= 0) {
            return -1;
        }
        if (strArr.length < 2 || strArr.length > 10) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i2 = 0;
        while (i2 < strArr.length) {
            if (strArr[i2] == null) {
                return -1;
            }
            String trim = strArr[i2].trim();
            if (trim.length() == 0 || trim.length() > 99 || strArr[i2].contains("$$")) {
                return -1;
            }
            stringBuilder.append(trim);
            if (i2 != strArr.length - 1) {
                stringBuilder.append("$$");
            }
            i2++;
        }
        if (stringBuilder.toString().length() > 99) {
            return -1;
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010111);
        this.a.putString("key", stringBuilder.toString());
        this.a.putInt("start_x", geoPoint.getLongitudeE6());
        this.a.putInt("start_y", geoPoint.getLatitudeE6());
        this.a.putInt("radius", i);
        this.a.putInt("page_num", 0);
        this.a.putInt("cnt", b);
        this.a.putInt("key_num", strArr.length);
        return Mj.sendBundle(this.a) == 0 ? -1 : 0;
    }

    public int poiSearchInCity(String str, String str2) {
        if (str2 == null) {
            return -1;
        }
        if (str == null) {
            str = "";
        }
        String trim = str.trim();
        String trim2 = str2.trim();
        if (trim.length() > 16 || trim2.length() == 0 || trim2.length() > 99) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010101);
        try {
            this.a.putByteArray(BaseProfile.COL_CITY, trim.getBytes("gb2312"));
        } catch (UnsupportedEncodingException e) {
            Log.d("MKSearch poiSearchInCity:", "translate city error!!");
            Log.d("MKSearch poiSearchInCity:", e.getMessage());
        }
        this.a.putString("key", trim2);
        this.a.putInt("page_num", 0);
        this.a.putInt("cnt", b);
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int poiSearchInbounds(String str, GeoPoint geoPoint, GeoPoint geoPoint2) {
        if (str == null || geoPoint == null || geoPoint2 == null) {
            return -1;
        }
        String trim = str.trim();
        if (trim.length() == 0 || trim.length() > 99) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010104);
        this.a.putString("key", trim);
        this.a.putInt("page_num", 0);
        this.a.putInt("cnt", b);
        this.a.putInt("start_x", geoPoint.getLongitudeE6());
        this.a.putInt("start_y", geoPoint.getLatitudeE6());
        this.a.putInt("end_x", geoPoint2.getLongitudeE6());
        this.a.putInt("end_y", geoPoint2.getLatitudeE6());
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int poiSearchNearBy(String str, GeoPoint geoPoint, int i) {
        if (geoPoint == null || str == null || i <= 0) {
            return -1;
        }
        String trim = str.trim();
        if (trim.length() == 0 || trim.length() > 99) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010104);
        this.a.putString("key", trim);
        this.a.putInt("start_x", geoPoint.getLongitudeE6());
        this.a.putInt("start_y", geoPoint.getLatitudeE6());
        this.a.putInt("radius", i);
        this.a.putInt("page_num", 0);
        this.a.putInt("cnt", b);
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int rGCShareUrlSearch(GeoPoint geoPoint, String str, String str2) {
        if (str == null || str2 == null || geoPoint == null) {
            return -1;
        }
        String trim = str.trim();
        String trim2 = str2.trim();
        if (trim.length() > 99 || trim2.length() > 99) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010110);
        this.a.putString("key", "");
        this.a.putInt("cnt", b);
        this.a.putInt("page_num", 0);
        this.a.putInt("x", geoPoint.getLongitudeE6());
        this.a.putInt("y", geoPoint.getLatitudeE6());
        this.a.putString("name", trim);
        this.a.putString("addr", trim2);
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int reverseGeocode(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
        this.a.putInt("opt", 11010109);
        this.a.putInt("x", geoPoint.getLongitudeE6());
        this.a.putInt("y", geoPoint.getLatitudeE6());
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int setDrivingPolicy(int i) {
        if (i > 2 || i < 0) {
            return -1;
        }
        this.d = i;
        return 0;
    }

    public int setTransitPolicy(int i) {
        if (i > 6 || i < 3) {
            return -1;
        }
        this.c = i;
        return 0;
    }

    public int suggestionSearch(String str) {
        if (str == null) {
            return -1;
        }
        String trim = str.trim();
        if (trim.length() == 0 || trim.length() > 99) {
            return -1;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11020301);
        this.a.putInt("opt", 11010107);
        this.a.putString("key", trim);
        return Mj.sendBundle(this.a) != 0 ? 0 : -1;
    }

    public int transitSearch(String str, MKPlanNode mKPlanNode, MKPlanNode mKPlanNode2) {
        if (str == null || mKPlanNode == null || mKPlanNode2 == null) {
            return -1;
        }
        if (str.length() > 31) {
            return -1;
        }
        int i;
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11020203);
        this.a.putInt("opt", 11020101);
        try {
            this.a.putByteArray(BaseProfile.COL_CITY, str.getBytes("gb2312"));
        } catch (UnsupportedEncodingException e) {
            Log.d("MKSearch transitSearch:", "translate city error!!");
            Log.d("MKSearch transitSearch:", e.getMessage());
        }
        if (mKPlanNode.pt != null) {
            this.a.putInt("start_x", mKPlanNode.pt.getLongitudeE6());
            this.a.putInt("start_y", mKPlanNode.pt.getLatitudeE6());
            i = 1;
        } else {
            i = 0;
        }
        if (mKPlanNode.name != null && mKPlanNode.name.length() <= 99) {
            try {
                this.a.putByteArray("start_name", mKPlanNode.name.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e2) {
                Log.d("MKSearch transitSearch:", "translate start name error!!");
                Log.d("MKSearch transitSearch:", e2.getMessage());
            }
            i = 1;
        }
        if (i == 0) {
            return -1;
        }
        if (mKPlanNode2.pt != null) {
            this.a.putInt("end_x", mKPlanNode2.pt.getLongitudeE6());
            this.a.putInt("end_y", mKPlanNode2.pt.getLatitudeE6());
            i = 1;
        } else {
            i = 0;
        }
        if (mKPlanNode2.name != null && mKPlanNode2.name.length() <= 99) {
            try {
                this.a.putByteArray("end_name", mKPlanNode2.name.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e22) {
                Log.d("MKSearch transitSearch:", "translate end name error!!");
                Log.d("MKSearch transitSearch:", e22.getMessage());
            }
            i = 1;
        }
        if (i == 0) {
            return -1;
        }
        this.a.putInt("policy", this.c);
        return Mj.sendBundle(this.a) == 0 ? -1 : 0;
    }

    public int walkingSearch(String str, MKPlanNode mKPlanNode, String str2, MKPlanNode mKPlanNode2) {
        if (mKPlanNode == null || mKPlanNode2 == null) {
            return -1;
        }
        int i;
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11020203);
        this.a.putInt("opt", 11020103);
        if (mKPlanNode.pt != null) {
            this.a.putInt("start_x", mKPlanNode.pt.getLongitudeE6());
            this.a.putInt("start_y", mKPlanNode.pt.getLatitudeE6());
            i = 1;
        } else {
            i = 0;
        }
        if (mKPlanNode.name != null && mKPlanNode.name.length() <= 99) {
            try {
                this.a.putByteArray("start_name", mKPlanNode.name.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e) {
                Log.d("MKSearch walkingSearch:", "translate start name error!!");
                Log.d("MKSearch walkingSearch:", e.getMessage());
            }
            if (str == null || str.length() > 31) {
                return -1;
            }
            try {
                this.a.putByteArray("start_city", str.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e2) {
                Log.d("MKSearch walkingSearch:", "translate start city error!!");
                Log.d("MKSearch walkingSearch:", e2.getMessage());
            }
            i = 1;
        }
        if (i == 0) {
            return -1;
        }
        if (mKPlanNode2.pt != null) {
            this.a.putInt("end_x", mKPlanNode2.pt.getLongitudeE6());
            this.a.putInt("end_y", mKPlanNode2.pt.getLatitudeE6());
            i = 1;
        } else {
            i = 0;
        }
        if (mKPlanNode2.name != null && mKPlanNode2.name.length() <= 99) {
            try {
                this.a.putByteArray("end_name", mKPlanNode2.name.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e22) {
                Log.d("MKSearch walkingSearch:", "translate end name error!!");
                Log.d("MKSearch walkingSearch:", e22.getMessage());
            }
            if (str2 == null || str2.length() > 31) {
                return -1;
            }
            try {
                this.a.putByteArray("end_city", str2.getBytes("gb2312"));
            } catch (UnsupportedEncodingException e222) {
                Log.d("MKSearch walkingSearch:", "translate end city error!!");
                Log.d("MKSearch walkingSearch:", e222.getMessage());
            }
            i = 1;
        }
        return i == 0 ? -1 : Mj.sendBundle(this.a) == 0 ? -1 : 0;
    }
}
