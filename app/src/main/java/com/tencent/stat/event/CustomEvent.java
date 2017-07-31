package com.tencent.stat.event;

import android.content.Context;
import com.tencent.mm.sdk.platformtools.LocaleUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.Arrays;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomEvent extends Event {
    private long duration = -1;
    protected Key key = new Key();

    public static class Key {
        String[] args;
        String id;
        Properties prop = null;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            boolean z = this.id.equals(key.id) && Arrays.equals(this.args, key.args);
            return this.prop != null ? z && this.prop.equals(key.prop) : z && key.prop == null;
        }

        public int hashCode() {
            int i = 0;
            if (this.id != null) {
                i = this.id.hashCode();
            }
            if (this.args != null) {
                i ^= Arrays.hashCode(this.args);
            }
            return this.prop != null ? i ^ this.prop.hashCode() : i;
        }

        public String toString() {
            String str = this.id;
            String str2 = "";
            if (this.args != null) {
                String str3 = this.args[0];
                for (int i = 1; i < this.args.length; i++) {
                    str3 = str3 + MiPushClient.ACCEPT_TIME_SEPARATOR + this.args[i];
                }
                str2 = "[" + str3 + "]";
            }
            if (this.prop != null) {
                str2 = str2 + this.prop.toString();
            }
            return str + str2;
        }
    }

    public CustomEvent(Context context, int i, String str) {
        super(context, i);
        this.key.id = str;
    }

    public Key getKey() {
        return this.key;
    }

    public EventType getType() {
        return EventType.CUSTOM;
    }

    public boolean onEncode(JSONObject jSONObject) throws JSONException {
        jSONObject.put("ei", this.key.id);
        if (this.duration > 0) {
            jSONObject.put("du", this.duration);
        }
        if (this.key.args != null) {
            JSONArray jSONArray = new JSONArray();
            for (Object put : this.key.args) {
                jSONArray.put(put);
            }
            jSONObject.put(LocaleUtil.ARABIC, jSONArray);
        }
        if (this.key.prop != null) {
            jSONObject.put("kv", new JSONObject(this.key.prop));
        }
        return true;
    }

    public void setArgs(String[] strArr) {
        this.key.args = strArr;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public void setProperties(Properties properties) {
        this.key.prop = properties;
    }
}
