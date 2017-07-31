package com.baidu.mapapi.cloud;

import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.Mj;
import com.tencent.open.SocialConstants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GeoSearchManager {
    public static final int GEO_SEARCH = 50;
    public static final int GEO_SEARCH_DETAILS = 51;
    private static GeoSearchManager b;
    private Bundle a = null;

    public static GeoSearchManager getInstance() {
        if (b == null) {
            b = new GeoSearchManager();
        }
        return b;
    }

    public boolean init(BMapManager bMapManager, GeoSearchListener geoSearchListener) {
        if (bMapManager == null) {
            return false;
        }
        if (geoSearchListener != null) {
            try {
                Method declaredMethod = Class.forName("com.baidu.mapapi.BMapManager").getDeclaredMethod("getMj", new Class[0]);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                ((Mj) declaredMethod.invoke(bMapManager, new Object[0])).initGeoListener(geoSearchListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            } catch (ClassNotFoundException e6) {
                e6.printStackTrace();
            }
        }
        return Mj.initSearchCC() == 1;
    }

    public boolean searchBounds(BoundsSearchInfo boundsSearchInfo) {
        if (boundsSearchInfo.ak == null) {
            return false;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010206);
        this.a.putInt("opt", 11010123);
        this.a.putByteArray("postdata", null);
        this.a.putString("url", "http://api.map.baidu.com/geosearch/poi" + boundsSearchInfo.a());
        return Mj.sendBundle(this.a) != 0;
    }

    public boolean searchDetail(DetailSearchInfo detailSearchInfo) {
        if (detailSearchInfo.ak == null) {
            return false;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010206);
        this.a.putInt("opt", 11010124);
        this.a.putByteArray("postdata", null);
        this.a.putString("url", "http://api.map.baidu.com/geosearch/detail" + detailSearchInfo.a());
        Log.d("kal", detailSearchInfo.a());
        return Mj.sendBundle(this.a) != 0;
    }

    public boolean searchNearby(NearbySearchInfo nearbySearchInfo) {
        if (nearbySearchInfo.ak == null) {
            return false;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010206);
        this.a.putInt("opt", 11010123);
        this.a.putByteArray("postdata", null);
        this.a.putString("url", "http://api.map.baidu.com/geosearch/poi" + nearbySearchInfo.a());
        return Mj.sendBundle(this.a) != 0;
    }

    public boolean searchRegion(RegionSearchInfo regionSearchInfo) {
        if (regionSearchInfo.ak == null) {
            return false;
        }
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 11010206);
        this.a.putInt("opt", 11010123);
        this.a.putByteArray("postdata", null);
        this.a.putString("url", "http://api.map.baidu.com/geosearch/poi" + regionSearchInfo.a());
        return Mj.sendBundle(this.a) != 0;
    }
}
