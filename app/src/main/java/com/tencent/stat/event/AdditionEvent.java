package com.tencent.stat.event;

import android.content.Context;
import com.tencent.stat.StatConfig;
import com.tencent.stat.common.StatCommonHelper;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class AdditionEvent extends Event {
    Map<String, ?> map = null;

    public AdditionEvent(Context context, int i, Map<String, ?> map) {
        super(context, i);
        this.map = map;
    }

    public EventType getType() {
        return EventType.ADDITION;
    }

    public boolean onEncode(JSONObject jSONObject) throws JSONException {
        StatCommonHelper.jsonPut(jSONObject, "qq", StatConfig.getQQ());
        if (this.map != null && this.map.size() > 0) {
            for (Entry entry : this.map.entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
        }
        return true;
    }
}
