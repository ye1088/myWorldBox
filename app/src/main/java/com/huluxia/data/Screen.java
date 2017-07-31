package com.huluxia.data;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class Screen implements Serializable {
    private static final long serialVersionUID = 836729945500903790L;
    private long fid;
    private String fileurl;
    private String imageurl;
    private int showtime;

    public Screen(JSONObject obj) throws JSONException {
        this.fid = obj.optLong("fid");
        this.imageurl = obj.optString("imageurl");
        this.fileurl = obj.optString("fileurl");
        this.showtime = obj.optInt("showtime");
    }

    public Screen() {
        this.fid = 0;
        this.imageurl = "";
        this.fileurl = "";
        this.showtime = 0;
    }

    public long getFid() {
        return this.fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getImageurl() {
        return this.imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getFileurl() {
        return this.fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public int getShowtime() {
        return this.showtime;
    }

    public void setShowtime(int showtime) {
        this.showtime = showtime;
    }
}
