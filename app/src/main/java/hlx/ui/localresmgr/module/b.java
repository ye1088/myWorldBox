package hlx.ui.localresmgr.module;

import com.huluxia.data.map.MapItem;
import com.huluxia.data.map.b$a;
import com.huluxia.data.map.b$c;
import com.huluxia.data.map.b$d;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ah;
import com.huluxia.utils.ai;
import com.huluxia.utils.j;
import hlx.data.localstore.a;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: ResManageInterface */
public class b {
    private static final String TAG = "ResManageInterface";

    public static List<com.huluxia.data.map.b> nF(int order) {
        Long lastModified = Long.valueOf(0);
        File[] files = new File(UtilsFile.Kq()).listFiles();
        ArrayList<com.huluxia.data.map.b> fileItems = new ArrayList();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                String realLevelName = null;
                if (j.eS(file.getName())) {
                    String realleveldat = file.getPath() + File.separator + a.bKd;
                    if (UtilsFile.isExist(realleveldat)) {
                        try {
                            BufferedReader bf = new BufferedReader(new FileReader(realleveldat));
                            String fname = bf.readLine();
                            bf.close();
                            if (fname != null) {
                                realLevelName = fname.trim();
                            }
                        } catch (IOException e) {
                        }
                    }
                    String _fileleveldat = file.getPath() + File.separator + "level.dat";
                    if (UtilsFile.isExist(_fileleveldat)) {
                        try {
                            lastModified = Long.valueOf(new File(_fileleveldat).lastModified());
                        } catch (Exception e2) {
                            lastModified = Long.valueOf(0);
                        }
                    }
                    String _date = null;
                    if (null == null) {
                        _date = new SimpleDateFormat(a.bKc).format(new Date(file.lastModified()));
                    }
                    com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), file.getName(), _date, j.getFileSizes(file));
                    item.eC();
                    item.eD();
                    item.setLastModified(lastModified.longValue());
                    if (realLevelName != null) {
                        item.aB(realLevelName);
                    }
                    String url = ai.ap(item.pP, ai.imgUrl);
                    String topic = ai.ap(item.pP, ai.bmg);
                    String mapId = ai.ap(item.pP, ai.bmh);
                    if (!url.isEmpty()) {
                        item.pR = url;
                    }
                    if (!topic.isEmpty()) {
                        item.pQ = (long) Integer.valueOf(topic).intValue();
                    }
                    if (!mapId.isEmpty()) {
                        item.mapId = (long) Integer.valueOf(mapId).intValue();
                    }
                    fileItems.add(item);
                }
            }
            if (order == 2) {
                Collections.sort(fileItems, new b$d());
            } else if (order == 1) {
                Collections.sort(fileItems, new b$a());
            } else {
                Collections.sort(fileItems, new b$c());
            }
        }
        return fileItems;
    }

    private static String hJ(String zipFullPath) throws Exception {
        int _tmpIndex;
        String _tmpName = null;
        ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFullPath)));
        while (true) {
            ZipEntry ze = zin.getNextEntry();
            if (ze == null) {
                break;
            } else if (!ze.isDirectory()) {
                _tmpName = ze.getName();
                _tmpIndex = _tmpName.indexOf("/", 0);
                if (_tmpIndex > 0) {
                    break;
                }
            }
        }
        _tmpName = _tmpName.substring(0, _tmpIndex);
        zin.closeEntry();
        return _tmpName;
    }

    public static com.huluxia.data.map.b aN(String mapPath, String zipFullPath) {
        Exception e;
        String realLevelName = null;
        File file = new File(mapPath);
        if (!file.exists()) {
            try {
                File file2 = new File(j.Kq() + hJ(zipFullPath));
                try {
                    if (file2.exists()) {
                        file = file2;
                    } else {
                        file = file2;
                        return null;
                    }
                } catch (Exception e2) {
                    e = e2;
                    file = file2;
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return null;
            }
        }
        if (j.eS(file.getName())) {
            String realleveldat = file.getPath() + File.separator + a.bKd;
            if (UtilsFile.isExist(realleveldat)) {
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(realleveldat));
                    String fname = bf.readLine();
                    bf.close();
                    if (fname != null) {
                        realLevelName = fname.trim();
                    }
                } catch (IOException e4) {
                }
            }
            String _date = null;
            if (null == null) {
                long modifyTime = file.lastModified();
                _date = new SimpleDateFormat(a.bKc).format(new Date(modifyTime));
            }
            com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), file.getName(), _date, j.getFileSizes(file));
            item.eC();
            item.eD();
            if (realLevelName != null) {
                item.aB(realLevelName);
            }
            String url = ai.ap(item.pP, ai.imgUrl);
            String topic = ai.ap(item.pP, ai.bmg);
            String mapId = ai.ap(item.pP, ai.bmh);
            if (!url.isEmpty()) {
                item.pR = url;
            }
            if (!topic.isEmpty()) {
                item.pQ = (long) Integer.valueOf(topic).intValue();
            }
            if (mapId.isEmpty()) {
                return item;
            }
            item.mapId = (long) Integer.valueOf(mapId).intValue();
            return item;
        }
        HLog.verbose(TAG, "DTPrint getSinggleMapFile file not 完整 ", new Object[0]);
        return null;
    }

    public static com.huluxia.data.map.b a(String jsPath, MapItem itemInfo) {
        if (!jsPath.endsWith(a.bJY)) {
            return null;
        }
        File file = new File(jsPath);
        if (!file.exists()) {
            return null;
        }
        String str = jsPath;
        String res_name = str.substring(jsPath.lastIndexOf("/") + 1, jsPath.indexOf(a.bJY));
        long modifyTime = file.lastModified();
        com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), res_name, new SimpleDateFormat(a.bKc).format(new Date(modifyTime)), j.w(file));
        item.eC();
        item.eD();
        String[] valList = ai.b("js", res_name, new String[]{ai.version, ai.bmh, ai.bmg, ai.imgUrl, ai.bmi});
        String version = valList[0];
        String mapId = valList[1];
        String topicId = valList[2];
        String imgUrl = valList[3];
        String ofState = valList[4];
        if (!version.isEmpty()) {
            item.ver = version.trim();
        }
        if (!mapId.isEmpty()) {
            item.mapId = (long) Integer.valueOf(mapId.trim()).intValue();
        }
        if (!topicId.isEmpty()) {
            item.pQ = (long) Integer.valueOf(topicId.trim()).intValue();
        }
        if (!imgUrl.isEmpty()) {
            item.pR = imgUrl.trim();
        }
        if (ofState.isEmpty()) {
            return item;
        }
        item.state = Integer.valueOf(ofState.trim()).intValue();
        return item;
    }

    public static List<com.huluxia.data.map.b> nE(int order) {
        ArrayList<com.huluxia.data.map.b> fileItems = new ArrayList();
        File[] files = new File(j.cT(true)).listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                String temp = file.getName().toLowerCase();
                if (temp.endsWith(a.bJY)) {
                    String res_name = temp.substring(0, temp.indexOf(a.bJY));
                    com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), res_name, new SimpleDateFormat(a.bKc).format(new Date(file.lastModified())), j.w(file));
                    item.eC();
                    item.eD();
                    String[] valList = ai.b("js", res_name, new String[]{ai.version, ai.bmh, ai.bmg, ai.imgUrl, ai.bmi});
                    String version = valList[0];
                    String mapId = valList[1];
                    String topicId = valList[2];
                    String imgUrl = valList[3];
                    String ofState = valList[4];
                    if (!version.isEmpty()) {
                        item.ver = version.trim();
                    }
                    if (!mapId.isEmpty()) {
                        item.mapId = (long) Integer.valueOf(mapId.trim()).intValue();
                    }
                    if (!topicId.isEmpty()) {
                        item.pQ = (long) Integer.valueOf(topicId.trim()).intValue();
                    }
                    if (!imgUrl.isEmpty()) {
                        item.pR = imgUrl.trim();
                    }
                    if (!ofState.isEmpty()) {
                        item.state = Integer.valueOf(ofState.trim()).intValue();
                    }
                    if ((order != 2 || item.state == 1) && !(order == 3 && item.state == 1)) {
                        fileItems.add(item);
                    }
                }
            }
            if (order == 2 || order == 3) {
                Collections.sort(fileItems, new b$c());
            } else if (order == 1) {
                Collections.sort(fileItems, new b$a());
            } else {
                Collections.sort(fileItems, new b$c());
            }
        }
        return fileItems;
    }

    public static com.huluxia.data.map.b b(String woodPath, MapItem itemInfo) {
        if (!woodPath.endsWith(".zip")) {
            return null;
        }
        File file = new File(woodPath);
        if (!file.exists()) {
            return null;
        }
        String str = woodPath;
        String res_name = str.substring(woodPath.lastIndexOf("/") + 1, woodPath.indexOf(".zip"));
        long modifyTime = file.lastModified();
        com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), res_name, new SimpleDateFormat(a.bKc).format(new Date(modifyTime)), j.w(file));
        item.eC();
        item.eD();
        String[] valList = ai.b("wood", res_name, new String[]{ai.version, ai.bmh, ai.bmg, ai.imgUrl, ai.bmi, ai.pU});
        String version = valList[0];
        String mapId = valList[1];
        String topicId = valList[2];
        String imgUrl = valList[3];
        String downUrlName = valList[5];
        if (!version.isEmpty()) {
            item.ver = version.trim();
        }
        if (!mapId.isEmpty()) {
            item.mapId = (long) Integer.valueOf(mapId.trim()).intValue();
        }
        if (!topicId.isEmpty()) {
            item.pQ = (long) Integer.valueOf(topicId.trim()).intValue();
        }
        if (!imgUrl.isEmpty()) {
            item.pR = imgUrl.trim();
        }
        if (!downUrlName.isEmpty()) {
            item.pU = downUrlName;
        }
        if (itemInfo == null) {
            String _tmpWoodPackId = "";
            try {
                _tmpWoodPackId = hlx.ui.localresmgr.cache.a.Un().hD(woodPath);
            } catch (Exception e) {
                _tmpWoodPackId = "";
                e.printStackTrace();
            }
            item.ver = "0";
            item.pU = _tmpWoodPackId;
        }
        item.state = 0;
        return item;
    }

    public static List<com.huluxia.data.map.b> nD(int order) {
        ArrayList<com.huluxia.data.map.b> fileItems = new ArrayList();
        File[] files = new File(j.Kv()).listFiles();
        if (!(files == null || files.length == 0)) {
            String using_wood = ah.KZ().LJ();
            if (using_wood == null) {
                using_wood = "";
            }
            for (File file : files) {
                String temp = file.getName().toLowerCase();
                if (temp.endsWith(".zip")) {
                    String res_name = temp.substring(0, temp.indexOf(".zip"));
                    com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), res_name, new SimpleDateFormat(a.bKc).format(new Date(file.lastModified())), j.w(file));
                    item.eC();
                    item.eD();
                    String[] valList = ai.b("wood", res_name, new String[]{ai.version, ai.bmh, ai.bmg, ai.imgUrl, ai.bmi, ai.pU});
                    String version = valList[0];
                    String mapId = valList[1];
                    String topicId = valList[2];
                    String imgUrl = valList[3];
                    String ofState = valList[4];
                    String downUrlName = valList[5];
                    if (!version.isEmpty()) {
                        item.ver = version.trim();
                    }
                    if (!mapId.isEmpty()) {
                        item.mapId = (long) Integer.valueOf(mapId.trim()).intValue();
                    }
                    if (!topicId.isEmpty()) {
                        item.pQ = (long) Integer.valueOf(topicId.trim()).intValue();
                    }
                    if (!imgUrl.isEmpty()) {
                        item.pR = imgUrl.trim();
                    }
                    if (ofState.isEmpty()) {
                        if (using_wood.equals(file.getPath())) {
                            item.state = 1;
                        }
                    } else {
                        item.state = Integer.valueOf(ofState.trim()).intValue();
                        if (item.state == 1) {
                            if (!using_wood.equals(file.getPath())) {
                                item.state = 0;
                            }
                        }
                    }
                    if (!downUrlName.isEmpty()) {
                        item.pU = downUrlName;
                    }
                    fileItems.add(item);
                }
            }
            if (order == 2) {
                Collections.sort(fileItems, new b$d());
            } else if (order == 1) {
                Collections.sort(fileItems, new b$a());
            } else {
                Collections.sort(fileItems, new b$c());
            }
        }
        return fileItems;
    }

    public static com.huluxia.data.map.b c(String skinPath, MapItem itemInfo) {
        if (!skinPath.endsWith(a.bKa)) {
            return null;
        }
        File file = new File(skinPath);
        if (!file.exists()) {
            return null;
        }
        String str = skinPath;
        String res_name = str.substring(skinPath.lastIndexOf("/") + 1, skinPath.indexOf(a.bKa));
        long modifyTime = file.lastModified();
        com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), res_name, new SimpleDateFormat(a.bKc).format(new Date(modifyTime)), j.w(file));
        item.eC();
        item.eD();
        String[] valList = ai.b("skin", res_name, new String[]{ai.version, ai.bmh, ai.bmg, ai.imgUrl, ai.bmi});
        String version = valList[0];
        String mapId = valList[1];
        String topicId = valList[2];
        String imgUrl = valList[3];
        if (!version.isEmpty()) {
            item.ver = version.trim();
        }
        if (!mapId.isEmpty()) {
            item.mapId = (long) Integer.valueOf(mapId.trim()).intValue();
        }
        if (!topicId.isEmpty()) {
            item.pQ = (long) Integer.valueOf(topicId.trim()).intValue();
        }
        if (!imgUrl.isEmpty()) {
            item.pR = imgUrl.trim();
        }
        item.state = 0;
        return item;
    }

    public static List<com.huluxia.data.map.b> nC(int order) {
        ArrayList<com.huluxia.data.map.b> fileItems = new ArrayList();
        File[] files = new File(j.cU(true)).listFiles();
        if (!(files == null || files.length == 0)) {
            String using_skin = ah.KZ().LI();
            if (using_skin == null) {
                using_skin = "";
            }
            for (File file : files) {
                String temp = file.getName().toLowerCase();
                if (temp.endsWith(a.bKa)) {
                    String res_name = temp.substring(0, temp.indexOf(a.bKa));
                    com.huluxia.data.map.b item = new com.huluxia.data.map.b(file.getPath(), res_name, new SimpleDateFormat(a.bKc).format(new Date(file.lastModified())), j.w(file));
                    item.eC();
                    item.eD();
                    String[] valList = ai.b("skin", res_name, new String[]{ai.version, ai.bmh, ai.bmg, ai.imgUrl, ai.bmi});
                    String version = valList[0];
                    String mapId = valList[1];
                    String topicId = valList[2];
                    String imgUrl = valList[3];
                    String ofState = valList[4];
                    if (!version.isEmpty()) {
                        item.ver = version.trim();
                    }
                    if (!mapId.isEmpty()) {
                        item.mapId = (long) Integer.valueOf(mapId.trim()).intValue();
                    }
                    if (!topicId.isEmpty()) {
                        item.pQ = (long) Integer.valueOf(topicId.trim()).intValue();
                    }
                    if (!imgUrl.isEmpty()) {
                        item.pR = imgUrl.trim();
                    }
                    if (ofState.isEmpty()) {
                        if (using_skin.equals(file.getPath())) {
                            item.state = 1;
                        }
                    } else {
                        item.state = Integer.valueOf(ofState.trim()).intValue();
                        if (item.state == 1) {
                            if (!using_skin.equals(file.getPath())) {
                                item.state = 0;
                            }
                        }
                    }
                    fileItems.add(item);
                }
            }
            if (order == 2) {
                Collections.sort(fileItems, new b$d());
            } else if (order == 1) {
                Collections.sort(fileItems, new b$a());
            } else {
                Collections.sort(fileItems, new b$c());
            }
        }
        return fileItems;
    }
}
