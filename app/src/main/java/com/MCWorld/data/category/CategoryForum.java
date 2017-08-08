package com.MCWorld.data.category;

import com.tencent.open.SocialConstants;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryForum implements Serializable {
    private static final long serialVersionUID = 5056026023510236297L;
    private int categorycount;
    private String description;
    private String icon;
    private long id;
    private long seq;
    private String title;

    public CategoryForum(JSONObject obj) throws JSONException {
        this.id = obj.optLong("id");
        this.title = obj.optString("title");
        this.icon = obj.optString("icon");
        this.description = obj.optString(SocialConstants.PARAM_COMMENT);
        this.seq = obj.optLong(Values.SEQ);
        this.categorycount = obj.optInt("categorycount");
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSeq() {
        return this.seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public int getCategorycount() {
        return this.categorycount;
    }

    public void setCategorycount(int categorycount) {
        this.categorycount = categorycount;
    }
}
