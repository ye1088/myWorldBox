package com.MCWorld.data.message;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class MsgCounts implements Serializable {
    private static final long serialVersionUID = -5521506415279032604L;
    private long all;
    private long follow;
    private long reply;
    private long sys;

    public MsgCounts(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.all = obj.optLong("0");
            this.sys = obj.optLong("1");
            this.reply = obj.optLong("2");
            this.follow = obj.optLong("100");
        }
    }

    public MsgCounts(long all, long sys, long reply) {
        this.all = all;
        this.sys = sys;
        this.reply = reply;
    }

    public long getAll() {
        return this.all;
    }

    public long getSys() {
        return this.sys;
    }

    public long getReply() {
        return this.reply;
    }

    public void setAll(long all) {
        this.all = all;
    }

    public void setSys(long sys) {
        this.sys = sys;
    }

    public void setReply(long reply) {
        this.reply = reply;
    }

    public long getFollow() {
        return this.follow;
    }

    public void setFollow(long follow) {
        this.follow = follow;
    }
}
