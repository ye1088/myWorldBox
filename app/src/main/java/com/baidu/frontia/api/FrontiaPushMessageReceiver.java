package com.baidu.frontia.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.baidu.android.pushservice.PushConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class FrontiaPushMessageReceiver extends BroadcastReceiver {
    public static final String TAG = FrontiaPushMessageReceiver.class.getSimpleName();
    private static String a = PushConstants.ACTION_LAPP_RECEIVE;
    private static String b = PushConstants.ACTION_LAPP_MESSAGE;

    public abstract void onBind(Context context, int i, String str, String str2, String str3, String str4);

    public void onCheckBindState(Context context, int i, String str, boolean z) {
    }

    public abstract void onDelTags(Context context, int i, List<String> list, List<String> list2, String str);

    public abstract void onListTags(Context context, int i, List<String> list, String str);

    public abstract void onMessage(Context context, String str, String str2);

    public abstract void onNotificationClicked(Context context, String str, String str2, String str3);

    public final void onReceive(Context context, Intent intent) {
        int i = 0;
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE) || intent.getAction().equals(PushConstants.ACTION_LAPP_MESSAGE)) {
            if (intent.getExtras() != null) {
                onMessage(context, intent.getExtras().getString(PushConstants.EXTRA_PUSH_MESSAGE_STRING), intent.getStringExtra(PushConstants.EXTRA_EXTRA));
            }
        } else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE) || intent.getAction().equals(a)) {
            String stringExtra = intent.getStringExtra("method");
            int intExtra = intent.getIntExtra(PushConstants.EXTRA_ERROR_CODE, 0);
            Object obj = "";
            if (intent.getByteArrayExtra("content") != null) {
                obj = new String(intent.getByteArrayExtra("content"));
            }
            JSONObject jSONObject;
            String string;
            if (stringExtra.equals(PushConstants.METHOD_BIND) || stringExtra.equals(PushConstants.METHOD_LAPP_BIND_INTENT)) {
                if (!TextUtils.isEmpty(obj)) {
                    try {
                        jSONObject = new JSONObject(obj);
                        String string2 = jSONObject.getString("request_id");
                        JSONObject jSONObject2 = jSONObject.getJSONObject("response_params");
                        stringExtra = jSONObject2.getString("appid");
                        string = jSONObject2.getString("channel_id");
                        onBind(context, intExtra, stringExtra, jSONObject2.getString("user_id"), string, string2);
                    } catch (Exception e) {
                        onBind(context, intExtra, null, null, null, null);
                    }
                }
            } else if (stringExtra.equals(PushConstants.METHOD_UNBIND) || stringExtra.equals(PushConstants.METHOD_LAPP_UNBIND)) {
                try {
                    onUnbind(context, intExtra, new JSONObject(obj).getString("request_id"));
                } catch (JSONException e2) {
                    onUnbind(context, intExtra, null);
                }
            } else if (stringExtra.equals(PushConstants.METHOD_GET_LAPP_BIND_STATE)) {
                onCheckBindState(context, intExtra, intent.getStringExtra(PushConstants.EXTRA_API_KEY), intent.getBooleanExtra(PushConstants.EXTRA_LAPP_BIND_STATE, false));
            } else if (stringExtra.equals(PushConstants.METHOD_SET_TAGS) || stringExtra.equals(PushConstants.METHOD_SET_LAPP_TAGS)) {
                try {
                    r3 = new JSONObject(obj);
                    string = r3.getString("request_id");
                    if (TextUtils.isEmpty(r3.optString(PushConstants.EXTRA_ERROR_CODE))) {
                        JSONArray jSONArray = r3.optJSONObject("response_params").getJSONArray("details");
                        r3 = new ArrayList();
                        r4 = new ArrayList();
                        while (i < jSONArray.length()) {
                            JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                            r7 = jSONObject3.getString("tag");
                            if (jSONObject3.getInt("result") == 0) {
                                r3.add(r7);
                            } else {
                                r4.add(r7);
                            }
                            i++;
                        }
                        onSetTags(context, intExtra, r3, r4, string);
                        return;
                    }
                    onSetTags(context, intExtra, new ArrayList(), new ArrayList(), string);
                } catch (JSONException e3) {
                    onSetTags(context, intExtra, null, null, null);
                }
            } else if (stringExtra.equals(PushConstants.METHOD_DEL_TAGS) || stringExtra.equals(PushConstants.METHOD_DEL_LAPP_TAGS)) {
                try {
                    r3 = new JSONObject(obj);
                    string = r3.getString("request_id");
                    JSONArray jSONArray2 = r3.getJSONObject("response_params").getJSONArray("details");
                    r3 = new ArrayList();
                    r4 = new ArrayList();
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        jSONObject = jSONArray2.getJSONObject(i2);
                        r7 = jSONObject.getString("tag");
                        if (jSONObject.getInt("result") == 0) {
                            r3.add(r7);
                        } else {
                            r4.add(r7);
                        }
                    }
                    onDelTags(context, intExtra, r3, r4, string);
                } catch (JSONException e4) {
                    onDelTags(context, intExtra, null, null, null);
                }
            } else if (stringExtra.equals(PushConstants.METHOD_LISTTAGS) || stringExtra.equals(PushConstants.METHOD_LIST_LAPP_TAGS)) {
                try {
                    onListTags(context, intExtra, intent.getStringArrayListExtra(PushConstants.EXTRA_TAGS_LIST), new JSONObject(obj).getString("request_id"));
                } catch (JSONException e5) {
                    onListTags(context, intExtra, null, null);
                }
            }
        } else if (intent.getAction().equals(PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK)) {
            onNotificationClicked(context, intent.getStringExtra(PushConstants.EXTRA_NOTIFICATION_TITLE), intent.getStringExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT), intent.getStringExtra(PushConstants.EXTRA_EXTRA));
        }
    }

    public abstract void onSetTags(Context context, int i, List<String> list, List<String> list2, String str);

    public abstract void onUnbind(Context context, int i, String str);
}
