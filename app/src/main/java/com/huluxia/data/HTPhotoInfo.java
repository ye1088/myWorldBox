package com.huluxia.data;

import java.io.Serializable;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

public class HTPhotoInfo implements Serializable {
    private static final long serialVersionUID = -3117784704418544873L;
    private String fid;
    private int height;
    private int likeCount;
    private String path;
    private long photoID;
    private int width;

    public HTPhotoInfo(JSONObject obj) throws JSONException {
        if (!obj.isNull("fid")) {
            this.fid = obj.getString("fid");
        }
        if (!obj.isNull(ClientCookie.PATH_ATTR)) {
            this.path = obj.getString(ClientCookie.PATH_ATTR);
        }
        if (!obj.isNull("photoID")) {
            this.photoID = obj.getLong("photoID");
        }
        if (!obj.isNull(b.KG)) {
            this.width = obj.getInt(b.KG);
        }
        if (!obj.isNull("height")) {
            this.height = obj.getInt("height");
        }
        if (!obj.isNull("likeCount")) {
            this.likeCount = obj.getInt("likeCount");
        }
    }

    public String getFid() {
        return this.fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getPhotoID() {
        return this.photoID;
    }

    public void setPhotoID(long photoID) {
        this.photoID = photoID;
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

    public int getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
