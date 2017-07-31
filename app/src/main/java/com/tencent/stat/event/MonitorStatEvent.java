package com.tencent.stat.event;

import android.content.Context;
import com.tencent.stat.StatAppMonitor;
import com.tencent.stat.common.StatCommonHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class MonitorStatEvent extends Event {
    private static String appVersion = null;
    private static String simOperator = null;
    private StatAppMonitor monitor = null;

    public MonitorStatEvent(Context context, int i, StatAppMonitor statAppMonitor) {
        super(context, i);
        this.monitor = statAppMonitor;
    }

    public EventType getType() {
        return EventType.MONITOR_STAT;
    }

    public boolean onEncode(JSONObject jSONObject) throws JSONException {
        if (this.monitor == null) {
            return false;
        }
        jSONObject.put("na", this.monitor.getInterfaceName());
        jSONObject.put("rq", this.monitor.getReqSize());
        jSONObject.put("rp", this.monitor.getRespSize());
        jSONObject.put("rt", this.monitor.getResultType());
        jSONObject.put("tm", this.monitor.getMillisecondsConsume());
        jSONObject.put("rc", this.monitor.getReturnCode());
        jSONObject.put("sp", this.monitor.getSampling());
        if (appVersion == null) {
            appVersion = StatCommonHelper.getAppVersion(this.ctx);
        }
        StatCommonHelper.jsonPut(jSONObject, "av", appVersion);
        if (simOperator == null) {
            simOperator = StatCommonHelper.getSimOperator(this.ctx);
        }
        StatCommonHelper.jsonPut(jSONObject, "op", simOperator);
        jSONObject.put("cn", StatCommonHelper.getLinkedWay(this.ctx));
        return true;
    }
}
