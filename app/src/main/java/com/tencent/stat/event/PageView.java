package com.tencent.stat.event;

import android.content.Context;
import com.tencent.stat.common.StatCommonHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class PageView extends Event {
    Long duration = null;
    String pageId;
    String referPageId;

    public PageView(Context context, String str, int i, Long l) {
        super(context, i);
        this.referPageId = str;
        this.pageId = StatCommonHelper.getActivityName(context);
        this.duration = l;
    }

    public String getPageId() {
        return this.pageId;
    }

    public EventType getType() {
        return EventType.PAGE_VIEW;
    }

    public boolean onEncode(JSONObject jSONObject) throws JSONException {
        jSONObject.put("pi", this.pageId);
        StatCommonHelper.jsonPut(jSONObject, "rf", this.referPageId);
        if (this.duration != null) {
            jSONObject.put("du", this.duration);
        }
        return true;
    }
}
