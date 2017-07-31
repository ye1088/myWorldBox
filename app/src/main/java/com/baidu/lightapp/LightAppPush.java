package com.baidu.lightapp;

import android.content.Context;
import android.os.AsyncTask;
import com.baidu.android.pushservice.PushManager;
import com.baidu.frontia.api.FrontiaPush;
import com.baidu.frontia.base.check.NetworkCheck;
import com.baidu.frontia.base.httpclient.DomainManager;
import com.baidu.frontia.base.impl.FrontiaCmd;
import com.baidu.frontia.base.impl.FrontiaCmd.CmdResult;
import com.baidu.frontia.module.push.FrontiaPushListenerImpl.PushMessageListenerImpl;
import com.baidu.frontia.module.push.FrontiaPushUtilImpl.MessageContentImpl;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class LightAppPush {
    private static LightAppPush a = null;
    private Context b;
    private String c;

    class a extends AsyncTask<Void, Void, CmdResult> {
        final /* synthetic */ LightAppPush a;
        private JSONObject b;
        private String c;
        private String d;
        private PushMessageListenerImpl e;
        private long f;

        public a(LightAppPush lightAppPush, JSONObject jSONObject, String str, String str2, PushMessageListenerImpl pushMessageListenerImpl) {
            this.a = lightAppPush;
            this.b = jSONObject;
            this.c = str;
            this.d = str2;
            this.e = pushMessageListenerImpl;
        }

        protected CmdResult a(Void... voidArr) {
            this.f = System.currentTimeMillis();
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("method", "create");
                jSONObject.put("type", "push");
                if (this.c != null) {
                    jSONObject.put("time", this.c);
                }
                if (this.d != null) {
                    jSONObject.put("crontab", this.d);
                }
                if (jSONObject != null) {
                    jSONObject.put("params", this.b);
                }
                return this.a.a().exec(jSONObject);
            } catch (JSONException e) {
                return null;
            }
        }

        protected void a(CmdResult cmdResult) {
            super.onPostExecute(cmdResult);
            if (cmdResult.errorOccur()) {
                this.e.onFailure(cmdResult.getErrorCode(), cmdResult.getErrorMessage());
                return;
            }
            try {
                this.e.onSuccess(cmdResult.getResponse().getString("timer_id"));
            } catch (JSONException e) {
                this.e.onFailure(-1, "server response bad format");
            }
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            a((CmdResult) obj);
        }
    }

    private LightAppPush(Context context, String str) {
        this.b = context;
        this.c = str;
    }

    private FrontiaCmd a() {
        return new FrontiaCmd(DomainManager.get().getPushUrl(), this.c);
    }

    private String b() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static LightAppPush newInstance(Context context, String str) {
        if (context == null || str == null || str.length() <= 0) {
            return null;
        }
        if (a == null) {
            synchronized (FrontiaPush.class) {
                if (a == null) {
                    a = new LightAppPush(context, str);
                }
            }
        }
        return a;
    }

    public void checkLightAppBindState() {
        PushManager.getLappBindState(this.b, this.c);
    }

    public void delLightAppTags(List<String> list) {
        PushManager.delLappTags(this.b, this.c, list);
    }

    public void lightAppBind() {
        PushManager.startWork(this.b, 4, this.c);
    }

    public void lightAppUnbind() {
    }

    public void listLightAppTags() {
        PushManager.listLappTags(this.b, this.c);
    }

    public void pushMessage(MessageContentImpl messageContentImpl, PushMessageListenerImpl pushMessageListenerImpl) {
        if (pushMessageListenerImpl != null) {
            if (messageContentImpl == null) {
                pushMessageListenerImpl.onFailure(-1, "Message body is null.");
            } else if (NetworkCheck.isNetworkAvailable(this.b)) {
                try {
                    JSONObject toJSON = messageContentImpl.toJSON();
                    toJSON.put("push_type", 3);
                    new a(this, toJSON, b(), null, pushMessageListenerImpl).execute(new Void[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    pushMessageListenerImpl.onFailure(-1, e.getMessage());
                }
            } else {
                pushMessageListenerImpl.onFailure(-1, "The network is not available.");
            }
        }
    }

    public void pushMessage(String str, MessageContentImpl messageContentImpl, PushMessageListenerImpl pushMessageListenerImpl) {
        if (pushMessageListenerImpl != null) {
            if (str == null || str.trim().length() == 0) {
                pushMessageListenerImpl.onFailure(-1, "tag is null or empty.");
            } else if (messageContentImpl == null) {
                pushMessageListenerImpl.onFailure(-1, "Message body is null.");
            } else if (NetworkCheck.isNetworkAvailable(this.b)) {
                try {
                    JSONObject toJSON = messageContentImpl.toJSON();
                    toJSON.put("push_type", 2);
                    toJSON.put("tag", str);
                    new a(this, toJSON, b(), null, pushMessageListenerImpl).execute(new Void[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    pushMessageListenerImpl.onFailure(-1, e.getMessage());
                }
            } else {
                pushMessageListenerImpl.onFailure(-1, "The network is not available.");
            }
        }
    }

    public void pushMessage(String str, String str2, MessageContentImpl messageContentImpl, PushMessageListenerImpl pushMessageListenerImpl) {
        if (pushMessageListenerImpl != null) {
            if (str == null || str.trim().length() == 0) {
                pushMessageListenerImpl.onFailure(-1, "uid is null or empty.");
            } else if (str.length() > 256) {
                pushMessageListenerImpl.onFailure(-1, "uid is longer than 256 bytes.");
            } else if (str2 != null && str2.trim().length() != 0 && str2.length() > 128) {
                pushMessageListenerImpl.onFailure(-1, "channelId is longer than 128 bytes.");
            } else if (messageContentImpl == null) {
                pushMessageListenerImpl.onFailure(-1, "Message body is null.");
            } else if (NetworkCheck.isNetworkAvailable(this.b)) {
                try {
                    JSONObject toJSON = messageContentImpl.toJSON();
                    toJSON.put("push_type", 1);
                    toJSON.put("user_id", str);
                    if (str2 != null) {
                        toJSON.put("channel_id", str2);
                    }
                    new a(this, toJSON, b(), null, pushMessageListenerImpl).execute(new Void[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    pushMessageListenerImpl.onFailure(-1, e.getMessage());
                }
            } else {
                pushMessageListenerImpl.onFailure(-1, "The network is not available.");
            }
        }
    }

    public void setLightAppTags(List<String> list) {
        PushManager.setLappTags(this.b, this.c, list);
    }
}
