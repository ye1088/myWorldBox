package com.MCWorld.utils;

import android.app.Activity;
import android.content.Context;
import com.MCWorld.data.js.JsItem;
import com.MCWorld.data.map.b;
import com.MCWorld.data.skin.SkinItem;
import com.MCWorld.data.wood.WoodItem;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.widget.Constants;
import hlx.data.localstore.a;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/* compiled from: UtilsMapHelper */
public class ai {
    private static final String TAG = "UtilsMapHelper";
    public static String bmg = "post_id";
    public static String bmh = "map_id";
    public static String bmi = "ofstate";
    public static String bmj = "res_active";
    public static String imgUrl = "img_url";
    public static String pU = "url_name";
    public static String version = "version";

    public static int an(String origDirAbsName, String origMapName) {
        String from = origDirAbsName;
        String dstMapName = UtilsFile.Kq() + origMapName;
        String dstName = "";
        for (int i = 1; i < 10000; i++) {
            dstName = dstMapName + "(备份" + String.valueOf(i) + ")";
            if (!UtilsFile.isExist(dstName)) {
                break;
            }
        }
        String realleveldat = dstName + File.separator + a.bKd;
        if (!UtilsFile.copyFolder(from, dstName)) {
            return -1;
        }
        if (UtilsFile.isExist(realleveldat)) {
            try {
                FileWriter f = new FileWriter(realleveldat);
                f.write(dstName);
                f.close();
            } catch (IOException e) {
                return -1;
            }
        }
        return 0;
    }

    public static String Md() {
        return j.cV(false);
    }

    public static void gf(String path) {
        for (File f : new File(path).listFiles()) {
            UtilsFile.y(f);
        }
    }

    public static void a(String gameMapPath, String backupPath, Activity context) throws Exception {
        String backupZip = backupPath;
        File f = new File(gameMapPath);
        if (!f.exists()) {
            UtilsFile.mkdir(gameMapPath);
        }
        String name = f.getName();
        String backupUnZipDir = backupPath + DiskFileUpload.postfix;
        p.ah(backupZip, backupUnZipDir);
        if (new File(backupUnZipDir + File.separator + name + File.separator + "level.dat").exists()) {
            gf(gameMapPath);
            Thread.sleep(3000);
            String dstDir = UtilsFile.Kq();
            UtilsFile.copyFolder(backupUnZipDir, dstDir);
            if (UtilsFile.isExist(dstDir + File.separator + name + File.separator + "level.dat")) {
                UtilsFile.deleteFile(backupUnZipDir);
                return;
            }
            throw new Exception("拷贝文件失败");
        }
        throw new Exception("地图文件缺失");
    }

    public static ArrayList<b> l(String mapName, boolean withDetail) {
        ArrayList<b> fmList = new ArrayList();
        String bakFilePath = Md() + File.separator + mapName + File.separator + Constants.bsk;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(a.bKc);
        try {
            String[] bakNameList = new String[]{mapName + ".zip", Constants.bsa, Constants.bsc, Constants.bse, Constants.bsg, Constants.bsi};
            String[] bakImageList = new String[]{null, Constants.bsb, Constants.bsd, Constants.bsf, Constants.bsh, Constants.bsj};
            for (int i = 0; i < 6; i++) {
                String bakName;
                String bakImage = null;
                if (i == 0) {
                    bakName = Md() + File.separator + mapName + ".zip";
                } else {
                    bakName = bakFilePath + File.separator + bakNameList[i];
                    bakImage = bakFilePath + File.separator + bakImageList[i];
                }
                if (UtilsFile.isExist(bakName)) {
                    if (withDetail) {
                        File f = new File(bakName);
                        b fm = new b(f.getAbsolutePath(), f.getName(), simpleDateFormat.format(new Date(f.lastModified())), f.length());
                        if (bakImage != null) {
                            String oldImagePath = bakFilePath + File.separator + bakImageList[i].replace(".ht", a.bKa);
                            if (UtilsFile.isExist(oldImagePath)) {
                                UtilsFile.rename(oldImagePath, bakImage);
                            }
                        }
                        if (bakImage != null && UtilsFile.isExist(bakImage)) {
                            fm.pR = bakImage;
                        } else if (bakImage == null) {
                            fm.pR = ap(mapName, imgUrl);
                            fm.name = "初始版本";
                        }
                        fmList.add(fm);
                    } else {
                        fmList.add(null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fmList;
    }

    public static String gg(String mapName) {
        return Md() + File.separator + mapName + File.separator + "map.ini";
    }

    public static String ao(String res_type, String mapName) {
        if (res_type.equals("js")) {
            return j.cT(true) + mapName + a.bKb;
        }
        if (res_type.endsWith("skin")) {
            return j.cU(true) + mapName + a.bKb;
        }
        if (res_type.equals("wood")) {
            return j.eP(mapName) + a.bKb;
        }
        return "";
    }

    public static String gh(String res_type) {
        if (res_type.equals("js")) {
            return j.cT(true) + "global.ini";
        }
        if (res_type.endsWith("skin")) {
            return j.cU(true) + "global.ini";
        }
        if (res_type.equals("wood")) {
            return j.Kv() + "global.ini";
        }
        return "";
    }

    public static String ap(String mapName, String keyName) {
        String mapIni = gg(mapName);
        if (UtilsFile.isExist(mapIni)) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(mapIni));
                String val = properties.getProperty(keyName);
                if (val == null) {
                    return "";
                }
                return val;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void p(String mapName, String keyName, String val) {
        String mapIni = gg(mapName);
        Properties properties = new Properties();
        try {
            if (UtilsFile.isExist(mapIni)) {
                properties.load(new FileInputStream(mapIni));
            } else {
                UtilsFile.mkdir(mapIni.substring(0, mapIni.lastIndexOf(47)));
            }
            properties.setProperty(keyName, val);
            FileOutputStream fos = new FileOutputStream(mapIni);
            properties.store(fos, "");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String q(String res_type, String mapName, String keyName) {
        String mapIni = ao(res_type, mapName);
        if (UtilsFile.isExist(mapIni)) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(mapIni));
                String val = properties.getProperty(keyName);
                if (val == null) {
                    return "";
                }
                return val;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String[] b(String res_type, String mapName, String[] keyList) {
        int i;
        String[] returnList = new String[keyList.length];
        for (i = 0; i < returnList.length; i++) {
            returnList[i] = "";
        }
        String mapIni = ao(res_type, mapName);
        if (UtilsFile.isExist(mapIni)) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(mapIni));
                for (i = 0; i < keyList.length; i++) {
                    String val = properties.getProperty(keyList[i]);
                    if (val == null) {
                        val = "";
                    }
                    returnList[i] = val;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnList;
    }

    public static void f(String res_type, String mapName, String keyName, String val) {
        String mapIni = ao(res_type, mapName);
        Properties properties = new Properties();
        try {
            if (UtilsFile.isExist(mapIni)) {
                properties.load(new FileInputStream(mapIni));
            } else {
                UtilsFile.mkdir(mapIni.substring(0, mapIni.lastIndexOf(47)));
            }
            properties.setProperty(keyName, val);
            FileOutputStream fos = new FileOutputStream(mapIni);
            properties.store(fos, "");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(String res_type, String mapName, Map<String, String> kvMap) {
        String mapIni = ao(res_type, mapName);
        Properties properties = new Properties();
        try {
            if (UtilsFile.isExist(mapIni)) {
                properties.load(new FileInputStream(mapIni));
            } else {
                UtilsFile.mkdir(mapIni.substring(0, mapIni.lastIndexOf(47)));
            }
            for (Entry<String, String> entry : kvMap.entrySet()) {
                properties.setProperty((String) entry.getKey(), (String) entry.getValue());
            }
            FileOutputStream fos = new FileOutputStream(mapIni);
            properties.store(fos, "");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String gi(String url) {
        String _tmpName = "";
        if (url == null) {
            return _tmpName;
        }
        if (url.endsWith(".zip")) {
            int _startIndex = url.lastIndexOf("/") + 1;
            return url.substring(_startIndex, _startIndex + ((url.length() - _startIndex) - ".zip".length()));
        }
        HLog.error(TAG, "DTPrint [%s] file tail is error !", url);
        return _tmpName;
    }

    public static void a(WoodItem item, String downUrl, String zippath) {
        Map<String, String> kvMap = new HashMap();
        kvMap.put(imgUrl, item.imgUrl);
        kvMap.put(bmh, String.valueOf(item.mapId));
        kvMap.put(bmg, String.valueOf(item.postId));
        kvMap.put(version, item.ver);
        kvMap.put(bmi, String.valueOf(item.state));
        kvMap.put(version, item.ver);
        String _tmpWoodPackId = "";
        try {
            _tmpWoodPackId = hlx.ui.localresmgr.cache.a.Un().hD(zippath);
        } catch (Exception e) {
            _tmpWoodPackId = "";
            e.printStackTrace();
        }
        kvMap.put(pU, _tmpWoodPackId);
        e("wood", item.name, kvMap);
    }

    public static void b(SkinItem item) {
        Map<String, String> kvMap = new HashMap();
        kvMap.put(imgUrl, item.imgUrl);
        kvMap.put(bmh, String.valueOf(item.mapId));
        kvMap.put(bmg, String.valueOf(item.postId));
        kvMap.put(version, item.ver);
        kvMap.put(bmi, String.valueOf(item.state));
        e("skin", item.name, kvMap);
    }

    public static void b(JsItem item) {
        Map<String, String> kvMap = new HashMap();
        kvMap.put(imgUrl, item.imgUrl);
        kvMap.put(bmh, String.valueOf(item.mapId));
        kvMap.put(bmg, String.valueOf(item.postId));
        kvMap.put(version, item.ver);
        kvMap.put(bmi, String.valueOf(item.state));
        e("js", item.name, kvMap);
    }

    public static int aq(String origName, String dstName) {
        String from = UtilsFile.Kq() + origName;
        String to = UtilsFile.Kq() + dstName;
        String realleveldat = to + File.separator + a.bKd;
        if (UtilsFile.isExist(to)) {
            return -2;
        }
        if (!UtilsFile.copyFolder(from, to)) {
            return -1;
        }
        if (UtilsFile.isExist(realleveldat)) {
            try {
                FileWriter f = new FileWriter(realleveldat);
                f.write(dstName);
                f.close();
            } catch (IOException e) {
                return -1;
            }
        }
        return 0;
    }

    public static void a(ArrayList<String> nameList, String dstDirName, Context context, boolean bShowToast, a cl) {
        new Thread(new 1(nameList, dstDirName, cl, bShowToast, context)).start();
    }
}
