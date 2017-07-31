package com.baidu.mapapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.huluxia.module.p;
import com.tencent.mm.sdk.plugin.BaseProfile;
import java.util.ArrayList;

class m implements j {
    private MKSearchListener a;

    public m(MKSearchListener mKSearchListener) {
        this.a = mKSearchListener;
    }

    private int a(Bundle bundle, MKRouteAddrResult mKRouteAddrResult) {
        Parcelable[] parcelableArray = bundle.getParcelableArray("aryAddr");
        Bundle bundle2 = (Bundle) parcelableArray[0];
        if (bundle2 != null) {
            ArrayList arrayList = new ArrayList();
            a(bundle2, arrayList);
            mKRouteAddrResult.mStartPoiList = arrayList;
        }
        bundle2 = (Bundle) parcelableArray[1];
        if (bundle2 != null) {
            arrayList = new ArrayList();
            b(bundle2, arrayList);
            mKRouteAddrResult.mStartCityList = arrayList;
        }
        bundle2 = (Bundle) parcelableArray[2];
        if (bundle2 != null) {
            arrayList = new ArrayList();
            a(bundle2, arrayList);
            mKRouteAddrResult.mEndPoiList = arrayList;
        }
        bundle2 = (Bundle) parcelableArray[3];
        if (bundle2 != null) {
            ArrayList arrayList2 = new ArrayList();
            b(bundle2, arrayList2);
            mKRouteAddrResult.mEndCityList = arrayList2;
        }
        return bundle.getInt("type");
    }

    private void a(Bundle bundle, MKAddrInfo mKAddrInfo) {
        mKAddrInfo.strAddr = bundle.getString("addr");
        mKAddrInfo.geoPt = new GeoPoint(bundle.getInt("y"), bundle.getInt("x"));
    }

    private void a(Bundle bundle, MKAddrInfo mKAddrInfo, int i) {
        mKAddrInfo.strAddr = bundle.getString("addr");
        mKAddrInfo.strBusiness = bundle.getString("business");
        mKAddrInfo.addressComponents = new MKGeocoderAddressComponent();
        mKAddrInfo.addressComponents.streetNumber = bundle.getString("streetNumber");
        mKAddrInfo.addressComponents.street = bundle.getString("street");
        mKAddrInfo.addressComponents.district = bundle.getString("district");
        mKAddrInfo.addressComponents.city = bundle.getString(BaseProfile.COL_CITY);
        mKAddrInfo.addressComponents.province = bundle.getString(BaseProfile.COL_PROVINCE);
        mKAddrInfo.geoPt = new GeoPoint(bundle.getInt("y"), bundle.getInt("x"));
        if (bundle.containsKey("aryCaption")) {
            String[] stringArray = bundle.getStringArray("aryCaption");
            String[] stringArray2 = bundle.getStringArray("aryAddr");
            String[] stringArray3 = bundle.getStringArray("aryTel");
            String[] stringArray4 = bundle.getStringArray("aryZip");
            int[] intArray = bundle.getIntArray("aryType");
            int[] intArray2 = bundle.getIntArray("aryX");
            int[] intArray3 = bundle.getIntArray("aryY");
            String string = bundle.getString(BaseProfile.COL_CITY);
            ArrayList arrayList = new ArrayList();
            int length = stringArray.length;
            for (int i2 = 0; i2 < length; i2++) {
                MKPoiInfo mKPoiInfo = new MKPoiInfo();
                mKPoiInfo.name = stringArray[i2];
                mKPoiInfo.address = stringArray2[i2];
                mKPoiInfo.city = string;
                mKPoiInfo.phoneNum = stringArray3[i2];
                mKPoiInfo.postCode = stringArray4[i2];
                mKPoiInfo.ePoiType = intArray[i2];
                mKPoiInfo.pt = new GeoPoint(intArray3[i2], intArray2[i2]);
                arrayList.add(mKPoiInfo);
            }
            mKAddrInfo.poiList = arrayList;
        }
    }

    private void a(Bundle bundle, MKDrivingRouteResult mKDrivingRouteResult, int i) {
        try {
            String string = bundle.getString("st_name");
            int i2 = bundle.getInt("st_pt_x");
            int i3 = bundle.getInt("st_pt_y");
            MKPlanNode mKPlanNode = new MKPlanNode();
            mKPlanNode.name = string;
            mKPlanNode.pt = new GeoPoint(i3, i2);
            mKDrivingRouteResult.a(mKPlanNode);
            string = bundle.getString("en_name");
            i2 = bundle.getInt("en_pt_x");
            i3 = bundle.getInt("en_pt_y");
            MKPlanNode mKPlanNode2 = new MKPlanNode();
            mKPlanNode2.name = string;
            mKPlanNode2.pt = new GeoPoint(i3, i2);
            mKDrivingRouteResult.b(mKPlanNode2);
            ArrayList arrayList = new ArrayList();
            if (bundle.getInt("planNum") > 0) {
                MKRoutePlan mKRoutePlan = new MKRoutePlan();
                mKRoutePlan.a(bundle.getInt("distance"));
                ArrayList arrayList2 = new ArrayList();
                Parcelable[] parcelableArray = bundle.getParcelableArray("aryRoute");
                for (Parcelable parcelable : parcelableArray) {
                    MKRoute mKRoute = new MKRoute();
                    Bundle bundle2 = (Bundle) parcelable;
                    mKRoute.a(bundle2.getInt("distance"));
                    mKRoute.b(1);
                    mKRoute.a(mKPlanNode.pt);
                    mKRoute.b(mKPlanNode2.pt);
                    if (bundle2.containsKey("link")) {
                        int i4 = bundle2.getInt("link");
                        ArrayList arrayList3 = new ArrayList();
                        ArrayList arrayList4 = new ArrayList();
                        for (i3 = 0; i3 < i4; i3++) {
                            int[] intArray = bundle2.getIntArray(String.format("aryX%d", new Object[]{Integer.valueOf(i3)}));
                            int[] intArray2 = bundle2.getIntArray(String.format("aryY%d", new Object[]{Integer.valueOf(i3)}));
                            if (intArray != null) {
                                ArrayList arrayList5 = new ArrayList();
                                i2 = 0;
                                while (i2 < intArray.length) {
                                    if (i2 == 0 || intArray2[i2] != intArray2[i2 - 1] || intArray[i2] != intArray[i2 - 1]) {
                                        arrayList5.add(new GeoPoint(intArray2[i2], intArray[i2]));
                                    }
                                    i2++;
                                }
                                arrayList3.add(arrayList5);
                                intArray = bundle2.getIntArray(String.format("aryMcX%d", new Object[]{Integer.valueOf(i3)}));
                                intArray2 = bundle2.getIntArray(String.format("aryMcY%d", new Object[]{Integer.valueOf(i3)}));
                                if (intArray != null) {
                                    arrayList5 = new ArrayList();
                                    i2 = 0;
                                    while (i2 < intArray.length) {
                                        if (i2 == 0 || intArray2[i2] != intArray2[i2 - 1] || intArray[i2] != intArray[i2 - 1]) {
                                            arrayList5.add(new GeoPoint(intArray2[i2], intArray[i2]));
                                        }
                                        i2++;
                                    }
                                    arrayList4.add(arrayList5);
                                }
                            }
                        }
                        mKRoute.b(arrayList3);
                        mKRoute.a = arrayList4;
                    }
                    if (bundle2.containsKey("aryStep")) {
                        ArrayList arrayList6 = new ArrayList();
                        Parcelable[] parcelableArray2 = bundle2.getParcelableArray("aryStep");
                        for (Parcelable parcelable2 : parcelableArray2) {
                            bundle2 = (Bundle) parcelable2;
                            MKStep mKStep = new MKStep();
                            mKStep.a(new GeoPoint(bundle2.getInt("y"), bundle2.getInt("x")));
                            mKStep.a(bundle2.getInt("dir"));
                            mKStep.a(bundle2.getString("tip"));
                            arrayList6.add(mKStep);
                        }
                        mKRoute.a(arrayList6);
                    }
                    arrayList2.add(mKRoute);
                }
                mKRoutePlan.a(arrayList2);
                arrayList.add(mKRoutePlan);
            }
            mKDrivingRouteResult.a(arrayList);
        } catch (Exception e) {
            Log.d("MKSearchNotifier", "parse DrivingRouteResult error!");
            Log.d("MKSearchNotifier", e.getMessage());
        }
    }

    private void a(Bundle bundle, MKPoiResult mKPoiResult, int i) {
        String[] stringArray;
        int length;
        int i2;
        int i3;
        int i4;
        String[] stringArray2;
        switch (i) {
            case 7:
                try {
                    stringArray = bundle.getStringArray("aryCity");
                    int[] intArray = bundle.getIntArray("aryNum");
                    ArrayList arrayList = new ArrayList();
                    length = stringArray.length;
                    for (i2 = 0; i2 < length; i2++) {
                        MKCityListInfo mKCityListInfo = new MKCityListInfo();
                        mKCityListInfo.city = stringArray[i2];
                        mKCityListInfo.num = intArray[i2];
                        arrayList.add(mKCityListInfo);
                    }
                    mKPoiResult.c(arrayList);
                    return;
                } catch (Exception e) {
                    Log.d("MKSearchNotifier", "parse PoiResult error!");
                    Log.d("MKSearchNotifier", e.getMessage());
                    return;
                }
            case 11:
            case 12:
            case 21:
                try {
                    i3 = bundle.getInt("iTotal");
                    i2 = bundle.getInt("iCurrNumPois");
                    i4 = bundle.getInt("iPageIndex");
                    mKPoiResult.b(i3);
                    mKPoiResult.a(i2);
                    mKPoiResult.d(i4);
                    i2 = i3 / MKSearch.getPoiPageCapacity();
                    if (i3 % MKSearch.getPoiPageCapacity() > 0) {
                        i2++;
                    }
                    mKPoiResult.c(i2);
                    stringArray = bundle.getStringArray("aryCaption");
                    String[] stringArray3 = bundle.getStringArray("aryUid");
                    String[] stringArray4 = bundle.getStringArray("aryAddr");
                    String[] stringArray5 = bundle.getStringArray("aryTel");
                    String[] stringArray6 = bundle.getStringArray("aryZip");
                    int[] intArray2 = bundle.getIntArray("aryType");
                    int[] intArray3 = bundle.getIntArray("aryX");
                    int[] intArray4 = bundle.getIntArray("aryY");
                    stringArray2 = bundle.getStringArray("srcName");
                    String string = bundle.getString(BaseProfile.COL_CITY);
                    ArrayList arrayList2 = new ArrayList();
                    int length2 = stringArray.length;
                    for (i2 = 0; i2 < length2; i2++) {
                        MKPoiInfo mKPoiInfo = new MKPoiInfo();
                        mKPoiInfo.name = stringArray[i2];
                        mKPoiInfo.address = stringArray4[i2];
                        mKPoiInfo.uid = stringArray3[i2];
                        mKPoiInfo.city = string;
                        mKPoiInfo.phoneNum = stringArray5[i2];
                        mKPoiInfo.postCode = stringArray6[i2];
                        mKPoiInfo.ePoiType = intArray2[i2];
                        mKPoiInfo.hasCaterDetails = "cater".equals(stringArray2[i2]);
                        mKPoiInfo.pt = new GeoPoint(intArray4[i2], intArray3[i2]);
                        arrayList2.add(mKPoiInfo);
                    }
                    mKPoiResult.a(arrayList2);
                    return;
                } catch (Exception e2) {
                    Log.d("MKSearchNotifier", "parse PoiResult error!");
                    Log.d("MKSearchNotifier", e2.getMessage());
                    return;
                }
            case 45:
                Parcelable[] parcelableArray = bundle.getParcelableArray("aryMultiPoiList");
                if (parcelableArray != null) {
                    ArrayList arrayList3 = new ArrayList();
                    for (Parcelable parcelable : parcelableArray) {
                        MKPoiResult mKPoiResult2 = new MKPoiResult();
                        Bundle bundle2 = (Bundle) parcelable;
                        if (bundle2 == null) {
                            arrayList3.add(mKPoiResult2);
                        } else {
                            ArrayList arrayList4 = new ArrayList();
                            int i5 = bundle2.getInt("iTotal");
                            i3 = bundle2.getInt("iCurrNumPois");
                            int i6 = bundle.getInt("iPageIndex");
                            mKPoiResult2.b(i5);
                            mKPoiResult2.a(i3);
                            mKPoiResult2.d(i6);
                            i3 = i5 / MKSearch.getPoiPageCapacity();
                            if (i5 % MKSearch.getPoiPageCapacity() > 0) {
                                i3++;
                            }
                            mKPoiResult2.c(i3);
                            stringArray = bundle2.getStringArray("aryCaption");
                            String[] stringArray7 = bundle2.getStringArray("aryAddr");
                            stringArray2 = bundle2.getStringArray("aryTel");
                            String[] stringArray8 = bundle2.getStringArray("aryZip");
                            int[] intArray5 = bundle2.getIntArray("aryType");
                            int[] intArray6 = bundle2.getIntArray("aryX");
                            int[] intArray7 = bundle2.getIntArray("aryY");
                            String string2 = bundle2.getString(BaseProfile.COL_CITY);
                            int length3 = stringArray.length;
                            for (i2 = 0; i2 < length3; i2++) {
                                MKPoiInfo mKPoiInfo2 = new MKPoiInfo();
                                mKPoiInfo2.name = stringArray[i2];
                                mKPoiInfo2.address = stringArray7[i2];
                                mKPoiInfo2.city = string2;
                                mKPoiInfo2.phoneNum = stringArray2[i2];
                                mKPoiInfo2.postCode = stringArray8[i2];
                                mKPoiInfo2.ePoiType = intArray5[i2];
                                mKPoiInfo2.pt = new GeoPoint(intArray7[i2], intArray6[i2]);
                                arrayList4.add(mKPoiInfo2);
                            }
                            mKPoiResult2.a(arrayList4);
                            arrayList3.add(mKPoiResult2);
                        }
                    }
                    mKPoiResult.b(arrayList3);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void a(Bundle bundle, MKSuggestionResult mKSuggestionResult, int i) {
        int i2 = 0;
        try {
            String[] stringArray = bundle.getStringArray("aryPoiName");
            String[] stringArray2 = bundle.getStringArray("aryCityName");
            ArrayList arrayList = new ArrayList();
            int length = stringArray != null ? stringArray.length : 0;
            while (i2 < length) {
                MKSuggestionInfo mKSuggestionInfo = new MKSuggestionInfo();
                mKSuggestionInfo.city = stringArray2[i2];
                mKSuggestionInfo.key = stringArray[i2];
                arrayList.add(mKSuggestionInfo);
                i2++;
            }
            mKSuggestionResult.a(arrayList);
            mKSuggestionResult.getSuggestionNum();
        } catch (Exception e) {
            Log.d("MKSearchNotifier", "parse SuggestionResult error!");
        }
    }

    private void a(Bundle bundle, MKTransitRouteResult mKTransitRouteResult, int i) {
        try {
            String string = bundle.getString("st_name");
            int i2 = bundle.getInt("st_pt_x");
            int i3 = bundle.getInt("st_pt_y");
            MKPlanNode mKPlanNode = new MKPlanNode();
            mKPlanNode.name = string;
            mKPlanNode.pt = new GeoPoint(i3, i2);
            mKTransitRouteResult.a(mKPlanNode);
            string = bundle.getString("en_name");
            i2 = bundle.getInt("en_pt_x");
            i3 = bundle.getInt("en_pt_y");
            MKPlanNode mKPlanNode2 = new MKPlanNode();
            mKPlanNode2.name = string;
            mKPlanNode2.pt = new GeoPoint(i3, i2);
            mKTransitRouteResult.b(mKPlanNode2);
            ArrayList arrayList = new ArrayList();
            Parcelable[] parcelableArray = bundle.getParcelableArray("aryPlan");
            if (parcelableArray == null) {
                mKTransitRouteResult.a(arrayList);
                return;
            }
            for (Parcelable parcelable : parcelableArray) {
                int[] intArray;
                int i4;
                ArrayList arrayList2;
                int[] intArray2;
                MKTransitRoutePlan mKTransitRoutePlan = new MKTransitRoutePlan();
                mKTransitRoutePlan.a(mKPlanNode.pt);
                mKTransitRoutePlan.b(mKPlanNode2.pt);
                Bundle bundle2 = (Bundle) parcelable;
                mKTransitRoutePlan.a(bundle2.getInt("distance"));
                ArrayList arrayList3 = new ArrayList();
                Parcelable[] parcelableArray2 = bundle2.getParcelableArray("aryLine");
                Parcelable[] parcelableArr = parcelableArray2 == null ? new Parcelable[0] : parcelableArray2;
                for (Parcelable parcelable2 : parcelableArr) {
                    MKLine mKLine = new MKLine();
                    Bundle bundle3 = (Bundle) parcelable2;
                    mKLine.b(bundle3.getInt("distance"));
                    mKLine.c(bundle3.getInt("type"));
                    mKLine.a(bundle3.getInt("numStops"));
                    mKLine.b(bundle3.getString("title"));
                    mKLine.c(bundle3.getString(p.UID));
                    mKLine.a(bundle3.getString("getOnTip"));
                    MKPoiInfo mKPoiInfo = new MKPoiInfo();
                    mKPoiInfo.name = bundle3.getString("getOnStopName");
                    if (bundle3.containsKey("getOnStopPtX")) {
                        mKPoiInfo.pt = new GeoPoint(bundle3.getInt("getOnStopPtY"), bundle3.getInt("getOnStopPtX"));
                    }
                    mKLine.a(mKPoiInfo);
                    mKPoiInfo = new MKPoiInfo();
                    mKPoiInfo.name = bundle3.getString("getOffStopName");
                    if (bundle3.containsKey("getOffStopPtX")) {
                        mKPoiInfo.pt = new GeoPoint(bundle3.getInt("getOffStopPtY"), bundle3.getInt("getOffStopPtX"));
                    }
                    mKLine.b(mKPoiInfo);
                    int[] intArray3 = bundle3.getIntArray("aryX");
                    if (intArray3 != null) {
                        ArrayList arrayList4 = new ArrayList();
                        intArray = bundle3.getIntArray("aryY");
                        i4 = 0;
                        while (i4 < intArray3.length) {
                            if (i4 == 0 || intArray[i4] != intArray[i4 - 1] || intArray3[i4] != intArray3[i4 - 1]) {
                                arrayList4.add(new GeoPoint(intArray[i4], intArray3[i4]));
                            }
                            i4++;
                        }
                        mKLine.a(arrayList4);
                    }
                    int[] intArray4 = bundle3.getIntArray("aryMcX");
                    if (intArray4 != null) {
                        arrayList2 = new ArrayList();
                        intArray2 = bundle3.getIntArray("aryMcY");
                        i2 = 0;
                        while (i2 < intArray4.length) {
                            if (i2 == 0 || intArray2[i2] != intArray2[i2 - 1] || intArray4[i2] != intArray4[i2 - 1]) {
                                arrayList2.add(new GeoPoint(intArray2[i2], intArray4[i2]));
                            }
                            i2++;
                        }
                        mKLine.a = arrayList2;
                    }
                    arrayList3.add(mKLine);
                }
                mKTransitRoutePlan.setLine(arrayList3);
                ArrayList arrayList5 = new ArrayList();
                Parcelable[] parcelableArray3 = bundle2.getParcelableArray("aryRoute");
                parcelableArray2 = parcelableArray3 == null ? new Parcelable[0] : parcelableArray3;
                for (Parcelable parcelable3 : parcelableArray2) {
                    MKRoute mKRoute = new MKRoute();
                    bundle2 = (Bundle) parcelable3;
                    mKRoute.a(bundle2.getInt("distance"));
                    mKRoute.b(2);
                    mKRoute.a(bundle2.getString("getOffTip"));
                    ArrayList arrayList6 = new ArrayList();
                    arrayList2 = new ArrayList();
                    if (bundle2.containsKey("startX")) {
                        mKRoute.a(new GeoPoint(bundle2.getInt("startY"), bundle2.getInt("startX")));
                    }
                    if (bundle2.containsKey("aryX")) {
                        intArray2 = bundle2.getIntArray("aryX");
                        intArray = bundle2.getIntArray("aryY");
                        i3 = 0;
                        while (i3 < intArray2.length) {
                            if (i3 == 0 || intArray[i3] != intArray[i3 - 1] || intArray2[i3] != intArray2[i3 - 1]) {
                                arrayList6.add(new GeoPoint(intArray[i3], intArray2[i3]));
                            }
                            i3++;
                        }
                    }
                    if (bundle2.containsKey("aryMcX")) {
                        intArray2 = bundle2.getIntArray("aryMcX");
                        intArray = bundle2.getIntArray("aryMcY");
                        i3 = 0;
                        while (i3 < intArray2.length) {
                            if (i3 == 0 || intArray[i3] != intArray[i3 - 1] || intArray2[i3] != intArray2[i3 - 1]) {
                                arrayList2.add(new GeoPoint(intArray[i3], intArray2[i3]));
                            }
                            i3++;
                        }
                    }
                    if (bundle2.containsKey("endX")) {
                        mKRoute.b(new GeoPoint(bundle2.getInt("endY"), bundle2.getInt("endX")));
                    }
                    ArrayList arrayList7 = new ArrayList();
                    arrayList7.add(arrayList6);
                    mKRoute.b(arrayList7);
                    arrayList7 = new ArrayList();
                    arrayList7.add(arrayList2);
                    mKRoute.a = arrayList7;
                    arrayList5.add(mKRoute);
                }
                mKTransitRoutePlan.a(arrayList5);
                arrayList.add(mKTransitRoutePlan);
            }
            mKTransitRouteResult.a(arrayList);
        } catch (Exception e) {
            Log.d("MKSearchNotifier", "parse TransitRouteResult error!");
            Log.d("MKSearchNotifier", e.getMessage());
        }
    }

    private void a(Bundle bundle, MKWalkingRouteResult mKWalkingRouteResult, int i) {
        try {
            String string = bundle.getString("st_name");
            int i2 = bundle.getInt("st_pt_x");
            int i3 = bundle.getInt("st_pt_y");
            MKPlanNode mKPlanNode = new MKPlanNode();
            mKPlanNode.name = string;
            mKPlanNode.pt = new GeoPoint(i3, i2);
            mKWalkingRouteResult.a(mKPlanNode);
            string = bundle.getString("en_name");
            i2 = bundle.getInt("en_pt_x");
            i3 = bundle.getInt("en_pt_y");
            MKPlanNode mKPlanNode2 = new MKPlanNode();
            mKPlanNode2.name = string;
            mKPlanNode2.pt = new GeoPoint(i3, i2);
            mKWalkingRouteResult.b(mKPlanNode2);
            ArrayList arrayList = new ArrayList();
            if (bundle.getInt("planNum") > 0) {
                MKRoutePlan mKRoutePlan = new MKRoutePlan();
                mKRoutePlan.a(bundle.getInt("distance"));
                ArrayList arrayList2 = new ArrayList();
                Parcelable[] parcelableArray = bundle.getParcelableArray("aryRoute");
                for (Parcelable parcelable : parcelableArray) {
                    MKRoute mKRoute = new MKRoute();
                    Bundle bundle2 = (Bundle) parcelable;
                    mKRoute.a(bundle2.getInt("distance"));
                    mKRoute.b(2);
                    mKRoute.a(mKPlanNode.pt);
                    mKRoute.b(mKPlanNode2.pt);
                    if (bundle2.containsKey("link")) {
                        int i4 = bundle2.getInt("link");
                        ArrayList arrayList3 = new ArrayList();
                        ArrayList arrayList4 = new ArrayList();
                        for (i3 = 0; i3 < i4; i3++) {
                            int[] intArray = bundle2.getIntArray(String.format("aryX%d", new Object[]{Integer.valueOf(i3)}));
                            int[] intArray2 = bundle2.getIntArray(String.format("aryY%d", new Object[]{Integer.valueOf(i3)}));
                            if (intArray != null) {
                                ArrayList arrayList5 = new ArrayList();
                                i2 = 0;
                                while (i2 < intArray.length) {
                                    if (i2 == 0 || intArray2[i2] != intArray2[i2 - 1] || intArray[i2] != intArray[i2 - 1]) {
                                        arrayList5.add(new GeoPoint(intArray2[i2], intArray[i2]));
                                    }
                                    i2++;
                                }
                                arrayList3.add(arrayList5);
                                intArray = bundle2.getIntArray(String.format("aryMcX%d", new Object[]{Integer.valueOf(i3)}));
                                intArray2 = bundle2.getIntArray(String.format("aryMcY%d", new Object[]{Integer.valueOf(i3)}));
                                if (intArray != null) {
                                    arrayList5 = new ArrayList();
                                    i2 = 0;
                                    while (i2 < intArray.length) {
                                        if (i2 == 0 || intArray2[i2] != intArray2[i2 - 1] || intArray[i2] != intArray[i2 - 1]) {
                                            arrayList5.add(new GeoPoint(intArray2[i2], intArray[i2]));
                                        }
                                        i2++;
                                    }
                                    arrayList4.add(arrayList5);
                                }
                            }
                        }
                        mKRoute.b(arrayList3);
                        mKRoute.a = arrayList4;
                    }
                    if (bundle2.containsKey("aryStep")) {
                        ArrayList arrayList6 = new ArrayList();
                        for (Parcelable parcelable2 : bundle2.getParcelableArray("aryStep")) {
                            bundle2 = (Bundle) parcelable2;
                            MKStep mKStep = new MKStep();
                            mKStep.a(new GeoPoint(bundle2.getInt("y"), bundle2.getInt("x")));
                            mKStep.a(bundle2.getInt("dir"));
                            mKStep.a(bundle2.getString("tip"));
                            arrayList6.add(mKStep);
                        }
                        mKRoute.a(arrayList6);
                    }
                    arrayList2.add(mKRoute);
                }
                mKRoutePlan.a(arrayList2);
                arrayList.add(mKRoutePlan);
            }
            mKWalkingRouteResult.a(arrayList);
        } catch (Exception e) {
            Log.d("MKSearchNotifier", "parse WalkingRouteResult error!");
            Log.d("MKSearchNotifier", e.getMessage());
        }
    }

    private void a(Bundle bundle, ArrayList<MKPoiInfo> arrayList) {
        String[] stringArray = bundle.getStringArray("aryCaption");
        String[] stringArray2 = bundle.getStringArray("aryAddr");
        int[] intArray = bundle.getIntArray("aryX");
        int[] intArray2 = bundle.getIntArray("aryY");
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            MKPoiInfo mKPoiInfo = new MKPoiInfo();
            mKPoiInfo.name = stringArray[i];
            mKPoiInfo.address = stringArray2[i];
            mKPoiInfo.pt = new GeoPoint(intArray2[i], intArray[i]);
            arrayList.add(mKPoiInfo);
        }
    }

    private boolean a(Bundle bundle, MKBusLineResult mKBusLineResult) {
        try {
            String string = bundle.getString("company");
            String string2 = bundle.getString("busName");
            String string3 = bundle.getString("startTime");
            String string4 = bundle.getString("endTime");
            mKBusLineResult.a(string, string2, bundle.getInt("monTicket"));
            mKBusLineResult.a(string3);
            mKBusLineResult.b(string4);
            MKRoute busRoute = mKBusLineResult.getBusRoute();
            busRoute.b(3);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (!bundle.containsKey("link")) {
                return false;
            }
            int i;
            int i2 = bundle.getInt("link");
            for (i = 0; i < i2; i++) {
                int[] intArray = bundle.getIntArray(String.format("aryX%d", new Object[]{Integer.valueOf(i)}));
                int[] intArray2 = bundle.getIntArray(String.format("aryY%d", new Object[]{Integer.valueOf(i)}));
                if (intArray != null) {
                    ArrayList arrayList3 = new ArrayList();
                    int length = intArray.length;
                    int i3 = 0;
                    while (i3 < length) {
                        if (i3 == 0 || intArray2[i3] != intArray2[i3 - 1] || intArray[i3] != intArray[i3 - 1]) {
                            arrayList3.add(new GeoPoint(intArray2[i3], intArray[i3]));
                        }
                        i3++;
                    }
                    arrayList.add(arrayList3);
                    intArray = bundle.getIntArray(String.format("aryMcX%d", new Object[]{Integer.valueOf(i)}));
                    intArray2 = bundle.getIntArray(String.format("aryMcY%d", new Object[]{Integer.valueOf(i)}));
                    if (intArray != null) {
                        arrayList3 = new ArrayList();
                        length = intArray.length;
                        i3 = 0;
                        while (i3 < length) {
                            if (i3 == 0 || intArray2[i3] != intArray2[i3 - 1] || intArray[i3] != intArray[i3 - 1]) {
                                arrayList3.add(new GeoPoint(intArray2[i3], intArray[i3]));
                            }
                            i3++;
                        }
                        arrayList2.add(arrayList3);
                    }
                }
            }
            busRoute.b(arrayList);
            busRoute.a = arrayList2;
            int i4 = bundle.getInt("stopSize");
            if (!bundle.containsKey("aryStep")) {
                return false;
            }
            arrayList2 = new ArrayList();
            Parcelable[] parcelableArray = bundle.getParcelableArray("aryStep");
            if (parcelableArray.length != i4) {
                return false;
            }
            for (i = 0; i < i4; i++) {
                Bundle bundle2 = (Bundle) parcelableArray[i];
                MKStep mKStep = new MKStep();
                mKStep.a(new GeoPoint(bundle2.getInt("y"), bundle2.getInt("x")));
                mKStep.a(bundle2.getString("name"));
                arrayList2.add(mKStep);
            }
            busRoute.a(arrayList2);
            busRoute.a(((MKStep) arrayList2.get(0)).getPoint());
            busRoute.b(((MKStep) arrayList2.get(i4 - 1)).getPoint());
            return true;
        } catch (Exception e) {
            Log.d("MKSearchNotifier", "parse BusDetail error!");
            Log.d("MKSearchNotifier", e.getMessage());
        }
    }

    private void b(Bundle bundle, ArrayList<MKCityListInfo> arrayList) {
        String[] stringArray = bundle.getStringArray("aryCity");
        int[] intArray = bundle.getIntArray("aryNum");
        int length = stringArray.length;
        for (int i = 0; i < length; i++) {
            MKCityListInfo mKCityListInfo = new MKCityListInfo();
            mKCityListInfo.city = stringArray[i];
            mKCityListInfo.num = intArray[i];
            arrayList.add(mKCityListInfo);
        }
    }

    public void a(MKEvent mKEvent) {
        int i = 1;
        int i2 = 0;
        Bundle newBundle;
        MKAddrInfo mKAddrInfo;
        switch (mKEvent.a) {
            case 1:
                if (mKEvent.b != 0) {
                    this.a.onGetPoiResult(null, 0, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11010204, mKEvent.c, 0);
                if (newBundle != null) {
                    MKPoiResult mKPoiResult = new MKPoiResult();
                    a(newBundle, mKPoiResult, mKEvent.c);
                    if (mKEvent.c == 45) {
                        ArrayList multiPoiResult = mKPoiResult.getMultiPoiResult();
                        if (multiPoiResult != null) {
                            int i3 = 0;
                            while (i3 < multiPoiResult.size()) {
                                if (((MKPoiResult) multiPoiResult.get(i3)).getAllPoi() == null) {
                                    i3++;
                                } else if (i != 0) {
                                    this.a.onGetPoiResult(null, mKEvent.c, 100);
                                    return;
                                } else {
                                    this.a.onGetPoiResult(mKPoiResult, mKEvent.c, 0);
                                    return;
                                }
                            }
                        }
                        i = 0;
                        if (i != 0) {
                            this.a.onGetPoiResult(mKPoiResult, mKEvent.c, 0);
                            return;
                        } else {
                            this.a.onGetPoiResult(null, mKEvent.c, 100);
                            return;
                        }
                    }
                    this.a.onGetPoiResult(mKPoiResult, mKEvent.c, 0);
                    return;
                }
                this.a.onGetPoiResult(null, 0, 100);
                return;
            case 2:
                if (mKEvent.b != 0) {
                    this.a.onGetTransitRouteResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11020204, mKEvent.c, 0);
                if (newBundle != null) {
                    MKTransitRouteResult mKTransitRouteResult = new MKTransitRouteResult();
                    a(newBundle, mKTransitRouteResult, mKEvent.c);
                    this.a.onGetTransitRouteResult(mKTransitRouteResult, 0);
                    return;
                }
                this.a.onGetTransitRouteResult(null, 100);
                return;
            case 3:
                if (mKEvent.b != 0) {
                    this.a.onGetDrivingRouteResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11020204, mKEvent.c, 0);
                if (newBundle != null) {
                    MKDrivingRouteResult mKDrivingRouteResult = new MKDrivingRouteResult();
                    a(newBundle, mKDrivingRouteResult, mKEvent.c);
                    this.a.onGetDrivingRouteResult(mKDrivingRouteResult, 0);
                    return;
                }
                this.a.onGetDrivingRouteResult(null, 100);
                return;
            case 4:
                if (mKEvent.b != 0) {
                    this.a.onGetWalkingRouteResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11020204, mKEvent.c, 0);
                if (newBundle != null) {
                    MKWalkingRouteResult mKWalkingRouteResult = new MKWalkingRouteResult();
                    a(newBundle, mKWalkingRouteResult, mKEvent.c);
                    this.a.onGetWalkingRouteResult(mKWalkingRouteResult, 0);
                    return;
                }
                this.a.onGetWalkingRouteResult(null, 100);
                return;
            case 6:
                if (mKEvent.b != 0) {
                    this.a.onGetAddrResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11010204, mKEvent.c, 0);
                if (newBundle != null) {
                    mKAddrInfo = new MKAddrInfo();
                    mKAddrInfo.type = 1;
                    a(newBundle, mKAddrInfo, mKEvent.c);
                    if (mKAddrInfo.strAddr == null || mKAddrInfo.strAddr.length() == 0) {
                        this.a.onGetAddrResult(null, 100);
                        return;
                    } else {
                        this.a.onGetAddrResult(mKAddrInfo, 0);
                        return;
                    }
                }
                this.a.onGetAddrResult(null, 100);
                return;
            case 10:
                if (mKEvent.b != 0) {
                    this.a.onGetAddrResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11010204, mKEvent.c, 0);
                if (newBundle != null) {
                    mKAddrInfo = new MKAddrInfo();
                    mKAddrInfo.type = 0;
                    a(newBundle, mKAddrInfo);
                    this.a.onGetAddrResult(mKAddrInfo, 0);
                    return;
                }
                this.a.onGetAddrResult(null, 100);
                return;
            case 11:
                newBundle = Mj.getNewBundle(11020204, mKEvent.c, 0);
                MKRouteAddrResult mKRouteAddrResult = new MKRouteAddrResult();
                switch (a(newBundle, mKRouteAddrResult)) {
                    case 0:
                        MKDrivingRouteResult mKDrivingRouteResult2 = new MKDrivingRouteResult();
                        mKDrivingRouteResult2.a(mKRouteAddrResult);
                        this.a.onGetDrivingRouteResult(mKDrivingRouteResult2, 4);
                        return;
                    case 1:
                        if (mKRouteAddrResult.mStartPoiList == null || mKRouteAddrResult.mEndPoiList == null) {
                            this.a.onGetTransitRouteResult(null, 100);
                            return;
                        }
                        MKTransitRouteResult mKTransitRouteResult2 = new MKTransitRouteResult();
                        mKTransitRouteResult2.a(mKRouteAddrResult);
                        this.a.onGetTransitRouteResult(mKTransitRouteResult2, 4);
                        return;
                    case 2:
                        MKWalkingRouteResult mKWalkingRouteResult2 = new MKWalkingRouteResult();
                        mKWalkingRouteResult2.a(mKRouteAddrResult);
                        this.a.onGetWalkingRouteResult(mKWalkingRouteResult2, 4);
                        return;
                    default:
                        return;
                }
            case 15:
                if (mKEvent.b != 0) {
                    this.a.onGetBusDetailResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11020204, mKEvent.c, 0);
                if (newBundle != null) {
                    MKBusLineResult mKBusLineResult = new MKBusLineResult();
                    if (!a(newBundle, mKBusLineResult)) {
                        i2 = 100;
                    }
                    this.a.onGetBusDetailResult(mKBusLineResult, i2);
                    return;
                }
                this.a.onGetBusDetailResult(null, 100);
                return;
            case 16:
                if (mKEvent.b != 0) {
                    this.a.onGetSuggestionResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11010107, mKEvent.c, 0);
                if (newBundle != null) {
                    MKSuggestionResult mKSuggestionResult = new MKSuggestionResult();
                    a(newBundle, mKSuggestionResult, mKEvent.c);
                    if (mKEvent.c == 506) {
                        if (mKSuggestionResult.getSuggestionNum() <= 0) {
                            i = 0;
                        }
                        if (i == 0) {
                            this.a.onGetSuggestionResult(null, 100);
                            return;
                        } else {
                            this.a.onGetSuggestionResult(mKSuggestionResult, 0);
                            return;
                        }
                    }
                    this.a.onGetSuggestionResult(mKSuggestionResult, 0);
                    return;
                }
                this.a.onGetSuggestionResult(null, 100);
                return;
            case 17:
                if (mKEvent.b != 0) {
                    this.a.onGetRGCShareUrlResult(null, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11010110, mKEvent.c, 0);
                if (newBundle != null) {
                    String string = newBundle.getString("shortUrl");
                    if (mKEvent.c == 500) {
                        int i4 = (string == null || string == "") ? 0 : 1;
                        if (i4 == 0) {
                            this.a.onGetRGCShareUrlResult(null, 100);
                            return;
                        } else {
                            this.a.onGetRGCShareUrlResult(string, 0);
                            return;
                        }
                    }
                    this.a.onGetRGCShareUrlResult(string, 0);
                    return;
                }
                this.a.onGetRGCShareUrlResult(null, 100);
                return;
            case 18:
                if (mKEvent.b != 0) {
                    this.a.onGetPoiDetailSearchResult(mKEvent.c, mKEvent.b);
                    return;
                }
                newBundle = Mj.getNewBundle(11010113, mKEvent.c, 0);
                if (newBundle != null) {
                    Intent intent = new Intent(BMapManager.b, PlaceCaterActivity.class);
                    intent.putExtras(newBundle);
                    intent.addFlags(268435456);
                    BMapManager.b.startActivity(intent);
                    this.a.onGetPoiDetailSearchResult(mKEvent.c, mKEvent.b);
                    return;
                }
                this.a.onGetPoiDetailSearchResult(mKEvent.c, mKEvent.b);
                return;
            default:
                return;
        }
    }
}
