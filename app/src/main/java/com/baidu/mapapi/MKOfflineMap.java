package com.baidu.mapapi;

import android.os.Bundle;
import android.util.Log;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.version.d;
import com.tencent.open.SocialConstants;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MKOfflineMap {
    public static final int TYPE_DOWNLOAD_UPDATE = 0;
    public static final int TYPE_NEW_OFFLINE = 6;
    public static final int TYPE_VER_UPDATE = 4;
    private Bundle a = null;

    private boolean a() {
        if (Mj.f != null) {
            return true;
        }
        try {
            Mj.f = new ServerSocket(51232);
            return true;
        } catch (Exception e) {
            Log.d("baidumap", e.getMessage());
            return false;
        }
    }

    public ArrayList<MKOLUpdateElement> getAllUpdateInfo() {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16010400);
        if (Mj.sendBundle(this.a) == 0) {
            return null;
        }
        String[] stringArray = this.a.getStringArray("name");
        int[] intArray = this.a.getIntArray("id");
        int[] intArray2 = this.a.getIntArray(d.SIZE);
        int[] intArray3 = this.a.getIntArray("ratio");
        int[] intArray4 = this.a.getIntArray("cityptx");
        int[] intArray5 = this.a.getIntArray("citypty");
        int[] intArray6 = this.a.getIntArray("serversize");
        int[] intArray7 = this.a.getIntArray("download");
        ArrayList<MKOLUpdateElement> arrayList = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            MKOLUpdateElement mKOLUpdateElement = new MKOLUpdateElement();
            mKOLUpdateElement.cityName = stringArray[i];
            mKOLUpdateElement.cityID = intArray[i];
            mKOLUpdateElement.size = intArray2[i];
            mKOLUpdateElement.ratio = intArray3[i];
            if (mKOLUpdateElement.ratio == 10000) {
                mKOLUpdateElement.ratio = 100;
            }
            mKOLUpdateElement.geoPt = new GeoPoint(intArray5[i], intArray4[i]);
            mKOLUpdateElement.status = intArray7[i];
            mKOLUpdateElement.serversize = intArray6[i];
            arrayList.add(mKOLUpdateElement);
        }
        return arrayList;
    }

    public ArrayList<MKOLSearchRecord> getHotCityList() {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16011000);
        if (Mj.sendBundle(this.a) == 0) {
            return null;
        }
        String[] stringArray = this.a.getStringArray("name");
        int[] intArray = this.a.getIntArray("id");
        int[] intArray2 = this.a.getIntArray(d.SIZE);
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            MKOLSearchRecord mKOLSearchRecord = new MKOLSearchRecord();
            mKOLSearchRecord.cityName = stringArray[i];
            mKOLSearchRecord.cityID = intArray[i];
            mKOLSearchRecord.size = intArray2[i];
            arrayList.add(mKOLSearchRecord);
        }
        return arrayList;
    }

    public ArrayList<MKOLSearchRecord> getOfflineCityList() {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16011100);
        if (Mj.sendBundle(this.a) == 0) {
            return null;
        }
        String[] stringArray = this.a.getStringArray("name");
        int[] intArray = this.a.getIntArray("id");
        int[] intArray2 = this.a.getIntArray(d.SIZE);
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            MKOLSearchRecord mKOLSearchRecord = new MKOLSearchRecord();
            mKOLSearchRecord.cityName = stringArray[i];
            mKOLSearchRecord.cityID = intArray[i];
            mKOLSearchRecord.size = intArray2[i];
            arrayList.add(mKOLSearchRecord);
        }
        return arrayList;
    }

    public MKOLUpdateElement getUpdateInfo(int i) {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16010500);
        this.a.putInt("opt", i);
        if (Mj.sendBundle(this.a) == 0) {
            return null;
        }
        MKOLUpdateElement mKOLUpdateElement = new MKOLUpdateElement();
        mKOLUpdateElement.cityName = this.a.getString("name");
        mKOLUpdateElement.cityID = this.a.getInt("id");
        mKOLUpdateElement.size = this.a.getInt(d.SIZE);
        mKOLUpdateElement.ratio = this.a.getInt("ratio");
        if (mKOLUpdateElement.ratio == 10000) {
            mKOLUpdateElement.ratio = 100;
        }
        mKOLUpdateElement.geoPt = new GeoPoint(this.a.getInt("citypty"), this.a.getInt("cityptx"));
        mKOLUpdateElement.serversize = this.a.getInt("serversize");
        mKOLUpdateElement.status = this.a.getInt(DownloadRecord.COLUMN_STATE);
        return mKOLUpdateElement;
    }

    public boolean init(BMapManager bMapManager, MKOfflineMapListener mKOfflineMapListener) {
        if (bMapManager == null || !a()) {
            return false;
        }
        if (mKOfflineMapListener != null) {
            bMapManager.a.a(mKOfflineMapListener);
        }
        return Mj.initOfflineCC() == 1;
    }

    public boolean pause(int i) {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16010200);
        this.a.putInt("opt", i);
        return Mj.sendBundle(this.a) != 0;
    }

    public boolean remove(int i) {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16010300);
        this.a.putInt("opt", i);
        return Mj.sendBundle(this.a) != 0;
    }

    public int scan() {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16011500);
        return Mj.sendBundle(this.a) == 0 ? 0 : this.a.getInt("num");
    }

    public ArrayList<MKOLSearchRecord> searchCity(String str) {
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16011100);
        this.a.putString("key", str);
        if (Mj.sendBundle(this.a) == 0) {
            return null;
        }
        String[] stringArray = this.a.getStringArray("name");
        int[] intArray = this.a.getIntArray("id");
        int[] intArray2 = this.a.getIntArray(d.SIZE);
        if (stringArray == null || intArray == null || intArray2 == null) {
            return null;
        }
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            MKOLSearchRecord mKOLSearchRecord = new MKOLSearchRecord();
            mKOLSearchRecord.cityName = stringArray[i];
            mKOLSearchRecord.cityID = intArray[i];
            mKOLSearchRecord.size = intArray2[i];
            arrayList.add(mKOLSearchRecord);
        }
        return arrayList;
    }

    public boolean start(int i) {
        int i2;
        if (this.a == null) {
            this.a = new Bundle();
        } else {
            this.a.clear();
        }
        this.a.putInt(SocialConstants.PARAM_ACT, 16010500);
        this.a.putInt("opt", i);
        if (Mj.sendBundle(this.a) != 0) {
            switch (this.a.getInt(DownloadRecord.COLUMN_STATE)) {
                case 1:
                case 2:
                    this.a.clear();
                    this.a.putInt(SocialConstants.PARAM_ACT, 16011400);
                    Mj.sendBundle(this.a);
                    return true;
                case 3:
                    i2 = 2;
                    break;
                default:
                    i2 = 0;
                    break;
            }
        }
        this.a.clear();
        this.a.putInt(SocialConstants.PARAM_ACT, 16011300);
        this.a.putInt("opt", i);
        i2 = Mj.sendBundle(this.a);
        if (i2 == 0) {
            return false;
        }
        this.a.clear();
        if (i2 == 1) {
            this.a.putInt("opt", 11010108);
            this.a.putInt(SocialConstants.PARAM_ACT, 11010203);
            this.a.putInt("cityid", i);
        } else {
            this.a.putInt(SocialConstants.PARAM_ACT, 16010100);
            this.a.putInt("opt", i);
        }
        return Mj.sendBundle(this.a) != 0;
    }
}
