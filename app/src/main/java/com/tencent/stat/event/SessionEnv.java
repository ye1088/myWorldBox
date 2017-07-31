package com.tencent.stat.event;

import android.content.Context;
import com.tencent.stat.common.Env;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionEnv extends Event {
    private JSONObject cfgJson = null;
    private Env env;

    public SessionEnv(Context context, int i, JSONObject jSONObject) {
        super(context, i);
        this.env = new Env(context);
        this.cfgJson = jSONObject;
    }

    public EventType getType() {
        return EventType.SESSION_ENV;
    }

    public boolean onEncode(JSONObject jSONObject) throws JSONException {
        jSONObject.put("ut", this.user.getType());
        if (this.cfgJson != null) {
            jSONObject.put("cfg", this.cfgJson);
        }
        this.env.encode(jSONObject);
        return true;
    }
}
