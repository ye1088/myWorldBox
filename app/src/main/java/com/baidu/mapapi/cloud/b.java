package com.baidu.mapapi.cloud;

import android.os.Bundle;
import com.baidu.mapapi.Mj;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.module.p;
import com.huluxia.version.d;
import com.tencent.mm.sdk.plugin.BaseProfile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

public class b implements c {
    GeoSearchListener a;

    public b(GeoSearchListener geoSearchListener) {
        this.a = geoSearchListener;
    }

    public void a(a aVar) {
        Bundle newBundle;
        int i;
        String[] stringArray;
        String[] stringArray2;
        String[] stringArray3;
        String[] stringArray4;
        String[] stringArray5;
        String[] stringArray6;
        int[] intArray;
        int[] intArray2;
        int[] intArray3;
        String[] stringArray7;
        String[] stringArray8;
        String[] stringArray9;
        float[] floatArray;
        float[] floatArray2;
        String[] stringArray10;
        String[] stringArray11;
        int i2;
        String str;
        JSONObject jSONObject;
        switch (aVar.a) {
            case 504:
                newBundle = Mj.getNewBundle(11010207, aVar.c, 0);
                if (newBundle != null) {
                    GeoSearchResult geoSearchResult = new GeoSearchResult();
                    geoSearchResult.status = newBundle.getInt("status");
                    geoSearchResult.message = newBundle.getString("message");
                    geoSearchResult.size = newBundle.getInt(d.SIZE);
                    geoSearchResult.total = newBundle.getInt(DownloadRecord.COLUMN_TOTAL);
                    i = newBundle.getInt("content_size");
                    if (i > 0) {
                        geoSearchResult.poiList = new ArrayList(i);
                        stringArray = newBundle.getStringArray(p.UID);
                        stringArray2 = newBundle.getStringArray("databox_id");
                        stringArray3 = newBundle.getStringArray("name");
                        stringArray4 = newBundle.getStringArray("addr");
                        stringArray5 = newBundle.getStringArray("tel");
                        stringArray6 = newBundle.getStringArray("zip");
                        intArray = newBundle.getIntArray("province_id");
                        intArray2 = newBundle.getIntArray("city_id");
                        intArray3 = newBundle.getIntArray("district_id");
                        stringArray7 = newBundle.getStringArray(BaseProfile.COL_PROVINCE);
                        stringArray8 = newBundle.getStringArray(BaseProfile.COL_CITY);
                        stringArray9 = newBundle.getStringArray("district");
                        floatArray = newBundle.getFloatArray("latitude");
                        floatArray2 = newBundle.getFloatArray("longitude");
                        stringArray10 = newBundle.getStringArray("tag");
                        stringArray11 = newBundle.getStringArray("ext");
                        for (i2 = 0; i2 < i; i2++) {
                            CustomPoiInfo customPoiInfo = new CustomPoiInfo();
                            customPoiInfo.uid = Integer.valueOf(stringArray[i2]).intValue();
                            customPoiInfo.databoxId = Integer.valueOf(stringArray2[i2]).intValue();
                            customPoiInfo.name = stringArray3[i2];
                            customPoiInfo.address = stringArray4[i2];
                            customPoiInfo.telephone = stringArray5[i2];
                            customPoiInfo.postCode = stringArray6[i2];
                            customPoiInfo.provinceId = intArray[i2];
                            customPoiInfo.cityId = intArray2[i2];
                            customPoiInfo.districtId = intArray3[i2];
                            customPoiInfo.provinceName = stringArray7[i2];
                            customPoiInfo.cityName = stringArray8[i2];
                            customPoiInfo.districtName = stringArray9[i2];
                            customPoiInfo.latitude = floatArray[i2];
                            customPoiInfo.longitude = floatArray2[i2];
                            customPoiInfo.tag = stringArray10[i2];
                            str = stringArray11[i2];
                            if (!(str == null || "".equals(str))) {
                                customPoiInfo.poiExtend = new HashMap();
                                try {
                                    jSONObject = new JSONObject(str);
                                    Iterator keys = jSONObject.keys();
                                    while (keys.hasNext()) {
                                        str = (String) keys.next();
                                        customPoiInfo.poiExtend.put(str, jSONObject.get(str));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            geoSearchResult.poiList.add(customPoiInfo);
                        }
                    }
                    if (geoSearchResult.poiList == null) {
                        this.a.onGetGeoResult(geoSearchResult, aVar.c, 100);
                        return;
                    } else {
                        this.a.onGetGeoResult(geoSearchResult, aVar.c, aVar.b);
                        return;
                    }
                }
                this.a.onGetGeoResult(null, aVar.c, 100);
                return;
            case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED /*505*/:
                newBundle = Mj.getNewBundle(11010207, aVar.c, 0);
                if (newBundle != null) {
                    DetailResult detailResult = new DetailResult();
                    detailResult.status = newBundle.getInt("status");
                    detailResult.message = newBundle.getString("message");
                    i = newBundle.getInt("content_size");
                    if (i == 1) {
                        detailResult.content = new CustomPoiInfo();
                        stringArray = newBundle.getStringArray(p.UID);
                        stringArray2 = newBundle.getStringArray("databox_id");
                        stringArray3 = newBundle.getStringArray("name");
                        stringArray4 = newBundle.getStringArray("addr");
                        stringArray5 = newBundle.getStringArray("tel");
                        stringArray6 = newBundle.getStringArray("zip");
                        intArray = newBundle.getIntArray("province_id");
                        intArray2 = newBundle.getIntArray("city_id");
                        intArray3 = newBundle.getIntArray("district_id");
                        stringArray7 = newBundle.getStringArray(BaseProfile.COL_PROVINCE);
                        stringArray8 = newBundle.getStringArray(BaseProfile.COL_CITY);
                        stringArray9 = newBundle.getStringArray("district");
                        floatArray = newBundle.getFloatArray("latitude");
                        floatArray2 = newBundle.getFloatArray("longitude");
                        stringArray10 = newBundle.getStringArray("tag");
                        stringArray11 = newBundle.getStringArray("ext");
                        for (i2 = 0; i2 < i; i2++) {
                            detailResult.content.uid = Integer.valueOf(stringArray[i2]).intValue();
                            detailResult.content.databoxId = Integer.valueOf(stringArray2[i2]).intValue();
                            detailResult.content.name = stringArray3[i2];
                            detailResult.content.address = stringArray4[i2];
                            detailResult.content.telephone = stringArray5[i2];
                            detailResult.content.postCode = stringArray6[i2];
                            detailResult.content.provinceId = intArray[i2];
                            detailResult.content.cityId = intArray2[i2];
                            detailResult.content.districtId = intArray3[i2];
                            detailResult.content.provinceName = stringArray7[i2];
                            detailResult.content.cityName = stringArray8[i2];
                            detailResult.content.districtName = stringArray9[i2];
                            detailResult.content.latitude = floatArray[i2];
                            detailResult.content.longitude = floatArray2[i2];
                            detailResult.content.tag = stringArray10[i2];
                            str = stringArray11[i2];
                            if (!(str == null || "".equals(str))) {
                                detailResult.content.poiExtend = new HashMap();
                                try {
                                    jSONObject = new JSONObject(str);
                                    Iterator keys2 = jSONObject.keys();
                                    while (keys2.hasNext()) {
                                        str = (String) keys2.next();
                                        detailResult.content.poiExtend.put(str, jSONObject.get(str));
                                    }
                                } catch (JSONException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                    if (detailResult.content == null) {
                        this.a.onGetGeoDetailsResult(detailResult, aVar.c, 100);
                        return;
                    } else {
                        this.a.onGetGeoDetailsResult(detailResult, aVar.c, aVar.b);
                        return;
                    }
                }
                this.a.onGetGeoDetailsResult(null, aVar.c, 100);
                return;
            default:
                return;
        }
    }
}
