package com.tencent.mm.sdk.plugin;

import com.tencent.mm.sdk.channel.MMessage.CallBack;

public interface IMMPluginAPI {
    boolean appendNetStat(int i, int i2, int i3);

    void createMsgController(String str);

    void createQRCodeController(String str);

    void createQRCodeController(String str, CallBack callBack);

    void createQRCodeController(String str, CallBack callBack, String str2);

    Profile getCurrentProfile(String str);

    String getPluginKey(String str);

    boolean installPlugin(String str);

    boolean isPluginInstalled(String str);

    void jumpToBindEmail(String str);

    void jumpToBindMobile(String str);

    void jumpToBindQQ(String str);

    void jumpToChattingUI(String str, String str2);

    void jumpToSettingView(String str, String str2);

    boolean registerAutoMsg(String str, String str2);

    boolean registerPattern(String str, CallBack callBack, String str2);

    boolean registerQRCodePattern(String str, CallBack callBack, String str2);

    void release();

    boolean sendMsgNotify(String str, String str2, int i, String str3, Class<?> cls);

    boolean unregisterAutoMsg(String str, String str2);
}
