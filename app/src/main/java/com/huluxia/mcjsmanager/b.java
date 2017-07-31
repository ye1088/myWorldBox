package com.huluxia.mcjsmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.huluxia.mcgame.g;
import com.huluxia.mcinterface.c;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.log.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: DTJSGoods */
public class b {
    private static final boolean DEBUG = false;
    private static final String TAG = "mcjsmanager/DTJSGoods";
    private static int ajL = 200;
    public static ArrayList<c> ajM = new ArrayList(ajL);
    private static int ajN = 0;
    private static Bitmap ajO = null;
    private static Bitmap ajP = null;
    public static final int ajQ = 0;
    public static final int ajR = 1;
    public static final int ajS = 2;
    public static final int ajT = 3;
    public static final int ajU = 4;
    public static final int ajV = 5;

    public static ArrayList<c> zA() {
        if (g.wc() == 1) {
            return ajM;
        }
        return null;
    }

    public static void Ag() {
        int i = 0;
        while (i < ajM.size()) {
            c pTmpDTJSItem = (c) ajM.get(i);
            i = (pTmpDTJSItem.zj() == null || pTmpDTJSItem.getType() == 2) ? i + 1 : i + 1;
        }
    }

    public static int hC(int index) {
        for (int i = 0; i < ajM.size(); i++) {
            if (((c) ajM.get(i)).getId() == index) {
                return 2;
            }
        }
        return 0;
    }

    public static int AA() {
        int nloadCnt = AB();
        try {
            String js_picName = "";
            for (int i = 0; i < ajM.size(); i++) {
                c pTmpDTJSItem = (c) ajM.get(i);
                if (pTmpDTJSItem.getType() == 2 && pTmpDTJSItem.zj() == null) {
                    js_picName = pTmpDTJSItem.zg();
                    int tmp_SonType = pTmpDTJSItem.zi();
                    int jsonObjLen;
                    int j;
                    JSONObject tmpJSONObj;
                    JSONArray tmpLocalJSONArray;
                    double left;
                    double top;
                    double right;
                    double bottom;
                    int width;
                    int heigh;
                    if ((tmp_SonType == 0 || tmp_SonType == 1) && ajP != null) {
                        jsonObjLen = DTSDKManagerEx.anO.aju.length();
                        for (j = 0; j < jsonObjLen; j++) {
                            tmpJSONObj = DTSDKManagerEx.anO.aju.getJSONObject(j);
                            if (tmpJSONObj.getString("name").equalsIgnoreCase(js_picName)) {
                                tmpLocalJSONArray = tmpJSONObj.getJSONArray("uvs").getJSONArray(0);
                                left = tmpLocalJSONArray.getDouble(0);
                                top = tmpLocalJSONArray.getDouble(1);
                                right = tmpLocalJSONArray.getDouble(2);
                                bottom = tmpLocalJSONArray.getDouble(3);
                                width = tmpLocalJSONArray.getInt(4);
                                heigh = tmpLocalJSONArray.getInt(5);
                                try {
                                    pTmpDTJSItem.p(Bitmap.createBitmap(ajP, (int) (((double) width) * left), (int) (((double) heigh) * top), ((int) (((double) width) * right)) - ((int) (((double) width) * left)), ((int) (((double) heigh) * bottom)) - ((int) (((double) heigh) * top))));
                                } catch (Exception e) {
                                }
                                nloadCnt++;
                                break;
                            }
                        }
                    } else {
                        if (tmp_SonType == 2) {
                            if (ajO != null) {
                                jsonObjLen = DTSDKManagerEx.anN.aju.length();
                                for (j = 0; j < jsonObjLen; j++) {
                                    tmpJSONObj = DTSDKManagerEx.anN.aju.getJSONObject(j);
                                    if (tmpJSONObj.getString("name").equalsIgnoreCase(js_picName)) {
                                        tmpLocalJSONArray = tmpJSONObj.getJSONArray("uvs").getJSONArray(0);
                                        left = tmpLocalJSONArray.getDouble(0);
                                        top = tmpLocalJSONArray.getDouble(1);
                                        right = tmpLocalJSONArray.getDouble(2);
                                        bottom = tmpLocalJSONArray.getDouble(3);
                                        width = tmpLocalJSONArray.getInt(4);
                                        heigh = tmpLocalJSONArray.getInt(5);
                                        pTmpDTJSItem.p(Bitmap.createBitmap(ajO, (int) (((double) width) * left), (int) (((double) heigh) * top), ((int) (((double) width) * right)) - ((int) (((double) width) * left)), ((int) (((double) heigh) * bottom)) - ((int) (((double) heigh) * top))));
                                        nloadCnt++;
                                        break;
                                    }
                                }
                            }
                        }
                        pTmpDTJSItem.p(null);
                        nloadCnt++;
                    }
                }
            }
        } catch (JSONException e2) {
            a.verbose("TAG", "DTPrint localJSONException localJSONException \n", new Object[0]);
        }
        hD(nloadCnt);
        return nloadCnt;
    }

    public static boolean a(int in_GoodId, int in_GoodDmg, String in_idx_name, String in_item_name, int in_son_type) {
        for (int i = 0; i < ajM.size(); i++) {
            c pTmpDTJSItem = (c) ajM.get(i);
            if (pTmpDTJSItem.getType() != 2) {
                pTmpDTJSItem.setType(2);
                pTmpDTJSItem.setId(in_GoodId);
                pTmpDTJSItem.cT(in_GoodDmg);
                pTmpDTJSItem.cU(in_idx_name);
                pTmpDTJSItem.cV(in_item_name);
                pTmpDTJSItem.hj(in_son_type);
                return true;
            }
        }
        return false;
    }

    public static void init(Context in_context, String path) {
        int i;
        if (ajM.size() == ajL) {
            for (i = 0; i < ajM.size(); i++) {
                ((c) ajM.get(i)).ze();
            }
        } else {
            ajM.clear();
            for (i = 0; i < ajL; i++) {
                c pTmpDTJSItem = new c();
                pTmpDTJSItem.ze();
                ajM.add(pTmpDTJSItem);
            }
        }
        try {
            ajP = null;
            ajP = BitmapFactory.decodeStream(new FileInputStream(path + "images" + File.separator + "items-opaque.png"));
        } catch (FileNotFoundException e) {
            ajP = null;
            e.printStackTrace();
        }
        try {
            ajO = null;
            ajO = BitmapFactory.decodeStream(in_context.getResources().getAssets().open("data/100008"));
        } catch (IOException e2) {
            ajO = null;
            e2.printStackTrace();
        }
    }

    public static int AB() {
        if (g.wc() == 1) {
            return ajN;
        }
        return 0;
    }

    public static void hD(int nLoadJSGoodsCnt) {
        ajN = nLoadJSGoodsCnt;
    }

    public static void hE(int addCnt) {
        ajN += addCnt;
    }
}
