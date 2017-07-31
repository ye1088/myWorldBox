package com.tencent.stat.event;

import android.content.Context;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.json.JSONException;
import org.json.JSONObject;

public class ErrorEvent extends Event {
    private String error;
    private int errorAttr;
    private int maxErrorLength = 100;

    public ErrorEvent(Context context, int i, int i2, Throwable th) {
        super(context, i);
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null && stackTrace.length > this.maxErrorLength) {
            StackTraceElement[] stackTraceElementArr = new StackTraceElement[this.maxErrorLength];
            for (int i3 = 0; i3 < this.maxErrorLength; i3++) {
                stackTraceElementArr[i3] = stackTrace[i3];
            }
            th.setStackTrace(stackTraceElementArr);
        }
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        this.error = stringWriter.toString();
        this.errorAttr = i2;
        printWriter.close();
    }

    public ErrorEvent(Context context, int i, String str) {
        super(context, i);
        this.error = str;
        this.errorAttr = 0;
    }

    public EventType getType() {
        return EventType.ERROR;
    }

    public boolean onEncode(JSONObject jSONObject) throws JSONException {
        jSONObject.put("er", this.error);
        jSONObject.put("ea", this.errorAttr);
        return true;
    }
}
