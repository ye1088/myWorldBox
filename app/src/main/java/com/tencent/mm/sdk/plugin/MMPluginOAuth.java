package com.tencent.mm.sdk.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import com.tencent.mm.sdk.platformtools.Log;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.OAuth;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.PluginIntent;
import java.util.HashMap;
import java.util.Map;

public class MMPluginOAuth {
    private final IResult bG;
    private String bH;
    private String bI;
    private Handler handler;
    private final Context q;

    public interface IResult {
        void onResult(MMPluginOAuth mMPluginOAuth);

        void onSessionTimeOut();
    }

    public static class Receiver extends BroadcastReceiver {
        private static final Map<String, MMPluginOAuth> ah = new HashMap();
        private final MMPluginOAuth bK;

        public Receiver() {
            this(null);
        }

        public Receiver(MMPluginOAuth mMPluginOAuth) {
            this.bK = mMPluginOAuth;
        }

        public static void register(String str, MMPluginOAuth mMPluginOAuth) {
            ah.put(str, mMPluginOAuth);
        }

        public static void unregister(String str) {
            ah.remove(str);
        }

        public void onReceive(Context context, Intent intent) {
            MMPluginOAuth mMPluginOAuth;
            Log.d("MicroMsg.SDK.MMPluginOAuth", "receive oauth result");
            String stringExtra = intent.getStringExtra(PluginIntent.REQUEST_TOKEN);
            final String stringExtra2 = intent.getStringExtra(PluginIntent.ACCESS_TOKEN);
            if (this.bK != null) {
                mMPluginOAuth = this.bK;
            } else {
                mMPluginOAuth = (MMPluginOAuth) ah.get(stringExtra);
                if (mMPluginOAuth == null) {
                    Log.e("MicroMsg.SDK.MMPluginOAuth", "oauth unregistered, request token = " + stringExtra);
                    return;
                }
                unregister(mMPluginOAuth.bI);
            }
            new Handler().post(new Runnable(this) {
                final /* synthetic */ Receiver bN;

                public void run() {
                    MMPluginOAuth.a(mMPluginOAuth, stringExtra2);
                }
            });
        }
    }

    public MMPluginOAuth(Context context, IResult iResult) {
        this.q = context;
        this.bG = iResult;
    }

    static /* synthetic */ void a(MMPluginOAuth mMPluginOAuth, String str) {
        Receiver.unregister(mMPluginOAuth.bI);
        mMPluginOAuth.bH = str;
        Log.i("MicroMsg.SDK.MMPluginOAuth", "access token: " + str);
        if (mMPluginOAuth.bG != null) {
            mMPluginOAuth.bG.onResult(mMPluginOAuth);
        }
    }

    public String getAccessToken() {
        return this.bH;
    }

    public String getRequestToken() {
        return this.bI;
    }

    public void start() {
        start(null);
    }

    public boolean start(Handler handler) {
        if (handler == null) {
            handler = new Handler();
        }
        this.handler = handler;
        Cursor query = this.q.getContentResolver().query(OAuth.CONTENT_URI, null, null, new String[]{this.q.getPackageName(), OAuth.ACTION_REQUEST_TOKEN}, null);
        if (query != null) {
            if (query.moveToFirst() && query.getColumnCount() >= 2) {
                this.bI = query.getString(0);
                this.bH = query.getString(1);
            }
            query.close();
        }
        Log.i("MicroMsg.SDK.MMPluginOAuth", "request token = " + this.bI);
        if (this.bI == null) {
            Log.e("MicroMsg.SDK.MMPluginOAuth", "request token failed");
            return false;
        } else if (this.bH != null) {
            this.handler.post(new Runnable(this) {
                final /* synthetic */ MMPluginOAuth bJ;

                {
                    this.bJ = r1;
                }

                public void run() {
                    if (this.bJ.bG != null) {
                        this.bJ.bG.onResult(this.bJ);
                    }
                }
            });
            return true;
        } else {
            int i;
            Log.d("MicroMsg.SDK.MMPluginOAuth", "begin to show user oauth page");
            Intent intent = new Intent();
            intent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.PluginOAuthUI");
            intent.putExtra(PluginIntent.REQUEST_TOKEN, this.bI);
            intent.putExtra(PluginIntent.PACKAGE, this.q.getPackageName());
            if (this.q.getPackageManager().resolveActivity(intent, 65536) == null) {
                Log.e("MicroMsg.SDK.MMPluginOAuth", "show oauth page failed, activity not found");
                i = 0;
            } else {
                if (!(this.q instanceof Activity)) {
                    intent.setFlags(268435456);
                }
                this.q.startActivity(intent);
                i = 1;
            }
            if (i == 0) {
                return false;
            }
            Receiver.register(this.bI, this);
            return true;
        }
    }
}
