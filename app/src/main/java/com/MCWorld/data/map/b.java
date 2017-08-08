package com.MCWorld.data.map;

import com.MCWorld.data.js.JsItem;
import com.MCWorld.data.skin.SkinItem;
import com.MCWorld.data.wood.WoodItem;
import com.MCWorld.utils.aw;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import org.apache.tools.ant.util.DateUtils;

/* compiled from: FileItem */
public class b implements Comparator {
    public String date;
    private long lastModified = 0;
    public long mapId = -1;
    public String name;
    public String pP;
    public long pQ = -1;
    public String pR = "";
    public String pS = "";
    public String pT = "";
    public String pU = "";
    public String path;
    public long size;
    public int state = -1;
    public String ver = "";

    public b(String path, String name, String date, long size) {
        this.path = path;
        this.name = name;
        this.date = date;
        this.size = size;
        this.state = -1;
        this.pP = name;
    }

    public b(String path, String name, String date, long size, int state, String ver, long map_id, long post_id, String img_url) {
        this.path = path;
        this.name = name;
        this.date = date;
        this.size = size;
        this.state = state;
        this.pP = name;
        this.mapId = map_id;
        this.pQ = post_id;
        this.pR = img_url;
        this.ver = ver;
    }

    public b(b item) {
        this.path = item.path;
        this.name = item.name;
        this.date = item.date;
        this.size = item.size;
        this.state = item.state;
        this.pP = item.name;
        this.mapId = item.mapId;
        this.pQ = item.pQ;
        this.pR = item.pR;
        this.ver = item.ver;
        this.pS = item.pS;
        this.pT = item.pT;
        this.pU = item.pU;
    }

    public void a(b item) {
        this.path = item.path;
        this.name = item.name;
        this.date = item.date;
        this.size = item.size;
        this.state = item.state;
        this.pP = item.name;
        this.mapId = item.mapId;
        this.pQ = item.pQ;
        this.pR = item.pR;
        this.ver = item.ver;
        this.pS = item.pS;
        this.pT = item.pT;
        this.pU = item.pU;
    }

    public int compare(Object arg0, Object arg1) {
        return 0;
    }

    public void aB(String name) {
        this.pP = name;
    }

    public static b a(WoodItem inItem) {
        File file = new File(inItem.path);
        return new b(inItem.path, inItem.name, new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN).format(new Date(file.lastModified())), file.length(), inItem.state, inItem.ver, inItem.mapId, inItem.postId, inItem.imgUrl);
    }

    public static WoodItem b(b inItem) {
        return new WoodItem(inItem.name, inItem.path, inItem.date, inItem.state, inItem.ver, inItem.mapId, inItem.pQ, inItem.pR);
    }

    public static b a(SkinItem inItem) {
        File file = new File(inItem.path);
        return new b(inItem.path, inItem.name, new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN).format(new Date(file.lastModified())), file.length(), inItem.state, inItem.ver, inItem.mapId, inItem.postId, inItem.imgUrl);
    }

    public static SkinItem c(b inItem) {
        return new SkinItem(inItem.name, inItem.path, inItem.date, inItem.state, inItem.ver, inItem.mapId, inItem.pQ, inItem.pR);
    }

    public static b a(JsItem inItem) {
        File file = new File(inItem.path);
        return new b(inItem.path, inItem.name, new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN).format(new Date(file.lastModified())), file.length(), inItem.state, inItem.ver, inItem.mapId, inItem.postId, inItem.imgUrl);
    }

    public static JsItem d(b inItem) {
        return new JsItem(inItem.name, inItem.path, inItem.date, inItem.state, inItem.ver, inItem.mapId, inItem.pQ, inItem.pR);
    }

    public void eC() {
        this.pS = aw.bA(this.size);
    }

    public void eD() {
        this.pT = this.date.substring(0, this.date.indexOf(" "));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
