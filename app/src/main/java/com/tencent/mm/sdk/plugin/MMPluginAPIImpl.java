package com.tencent.mm.sdk.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import com.tencent.mm.algorithm.Base64;
import com.tencent.mm.sdk.ConstantsUI.Contact;
import com.tencent.mm.sdk.channel.MMessage.CallBack;
import com.tencent.mm.sdk.channel.MMessage.Receiver;
import com.tencent.mm.sdk.platformtools.Log;
import com.tencent.mm.sdk.platformtools.Util;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.PluginDB;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.PluginIntent;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.Resolver;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.bytedeco.javacpp.avformat;

public class MMPluginAPIImpl implements IMMPluginAPI {
    private static final String[] columns = new String[]{"key", "type", "value"};
    private String am;
    private Receiver bF;
    private final Context q;

    public MMPluginAPIImpl(Context context) {
        this.q = context;
        this.am = context.getPackageName();
    }

    private void a(String str, String str2, Intent intent) {
        Intent intent2 = new Intent(str2);
        intent2.setClassName("com.tencent.mm", "com.tencent.mm.plugin.PluginProxyUI");
        if (intent != null) {
            intent2.putExtras(intent);
        }
        intent2.putExtra(PluginIntent.ACCESS_TOKEN, str);
        this.q.startActivity(intent2);
    }

    private Object e(String str) {
        Object obj = null;
        Cursor query = this.q.getContentResolver().query(PluginDB.CONTENT_URI, columns, null, new String[]{str}, null);
        if (query != null) {
            int columnIndex = query.getColumnIndex("type");
            int columnIndex2 = query.getColumnIndex("value");
            if (query.moveToFirst()) {
                obj = Resolver.resolveObj(query.getInt(columnIndex), query.getString(columnIndex2));
            }
            query.close();
        }
        return obj;
    }

    public boolean appendNetStat(int i, int i2, int i3) {
        return Util.nullAsFalse((Boolean) e("plugindb://" + this.am + "/action.appnetstat?recv=" + i + "&send=" + i2 + "&type=" + i3));
    }

    public void createMsgController(String str) {
    }

    public void createQRCodeController(String str) {
        a(str, "qrcode", null);
    }

    public void createQRCodeController(String str, CallBack callBack) {
        createQRCodeController(str, callBack, WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD);
    }

    public void createQRCodeController(String str, CallBack callBack, String str2) {
        a(str, "qrcode", new Intent().putExtra("qrcode_pattern", str2));
    }

    public Profile getCurrentProfile(String str) {
        Profile profile = null;
        Cursor query = this.q.getContentResolver().query(Profile.CONTENT_URI, Profile.columns, null, new String[]{str}, null);
        if (query == null) {
            Log.e("MicroMsg.SDK.MMPluginMgrImpl", "get current profile failed");
        } else {
            if (query.moveToFirst()) {
                profile = new Profile();
                profile.convertFrom(query);
            }
            query.close();
        }
        return profile;
    }

    public String getPluginKey(String str) {
        return (String) e("plugindb://" + str + "/comm.pluginkey");
    }

    public boolean installPlugin(String str) {
        return Util.nullAsFalse((Boolean) e("plugindb://" + str + "/action.install"));
    }

    public boolean isPluginInstalled(String str) {
        return Util.nullAsFalse((Boolean) e("plugindb://" + str + "/comm.installed"));
    }

    public void jumpToBindEmail(String str) {
        a(str, BaseProfile.COL_BINDEMAIL, new Intent());
    }

    public void jumpToBindMobile(String str) {
        a(str, BaseProfile.COL_BINDMOBILE, new Intent());
    }

    public void jumpToBindQQ(String str) {
        a(str, BaseProfile.COL_BINDQQ, new Intent());
    }

    public void jumpToChattingUI(String str, String str2) {
        a(str, "chatting", new Intent().putExtra(Contact.KUser, str2).setFlags(avformat.AVFMT_SEEK_TO_PTS));
    }

    public void jumpToSettingView(String str, String str2) {
        a(str, "profile", new Intent().putExtra(Contact.KUser, str2).setFlags(avformat.AVFMT_SEEK_TO_PTS));
    }

    public boolean registerAutoMsg(String str, String str2) {
        return Util.nullAsFalse((Boolean) e("plugindb://" + this.am + "/action.automsg?pluginMsgUsername=" + str + "&pluginUsername=" + str2));
    }

    public boolean registerPattern(String str, CallBack callBack, String str2) {
        return false;
    }

    public boolean registerQRCodePattern(String str, CallBack callBack, String str2) {
        if (this.bF != null) {
            this.q.unregisterReceiver(this.bF);
        }
        this.bF = new Receiver(callBack);
        this.q.registerReceiver(this.bF, new IntentFilter(PluginIntent.ACTION_QRCODE_SCANNED));
        return Util.nullAsFalse((Boolean) e("plugindb://" + str + "/action.register_qrcode_result?pattern=" + str2));
    }

    public void release() {
        Log.d("MicroMsg.SDK.MMPluginMgrImpl", "release plugin mgr implemetation");
        if (this.bF != null) {
            this.q.unregisterReceiver(this.bF);
            Log.d("MicroMsg.SDK.MMPluginMgrImpl", "unregister qrcode scan result receiver");
        }
    }

    public boolean sendMsgNotify(String str, String str2, int i, String str3, Class<?> cls) {
        return cls == null ? false : Util.nullAsFalse((Boolean) e("plugindb://" + this.am + "/action.msgnotify?username=" + str + "&msgContent=" + str2 + "&msgType=" + i + "&customNotify=" + str3 + "&intentUri=" + Base64.encodeToString(new Intent(this.q, cls).toUri(0).getBytes(), false)));
    }

    public boolean unregisterAutoMsg(String str, String str2) {
        return Util.nullAsFalse((Boolean) e("plugindb://" + this.am + "/action.unautomsg?pluginMsgUsername=" + str + "&pluginUsername=" + str2));
    }
}
