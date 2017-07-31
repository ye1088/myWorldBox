package com.huluxia.data.profile;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCredits implements Serializable {
    private static final long serialVersionUID = 4890197053529129703L;
    private long credits;
    private String nick;
    private long userID;

    public UserCredits(JSONObject obj) throws JSONException {
        this.userID = obj.optLong("userID");
        this.nick = obj.optString("nick");
        this.credits = obj.optLong("credits");
    }

    public long getUserID() {
        return this.userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getCredits() {
        return this.credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }
}
