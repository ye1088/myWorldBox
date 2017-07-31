package com.huluxia.data;

import com.huluxia.version.d;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UploadFileInfo */
public class m {
    private String fid;
    private int height;
    private long size;
    private int status;
    private String url;
    private int width;

    public m(JSONObject obj) throws JSONException {
        this.size = obj.optLong(d.SIZE);
        this.width = obj.optInt(b.KG);
        this.height = obj.optInt("height");
        this.fid = obj.optString("fid");
        this.url = obj.optString("url");
        this.status = obj.optInt("status");
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFid() {
        return this.fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
