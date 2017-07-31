package com.tencent.mm.sdk.plugin;

import android.content.Context;
import android.content.Intent;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX.Req;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXMediaMessage.IMediaObject;
import com.tencent.mm.sdk.platformtools.Util;

public class MMPluginMsg {
    public static final String ACTION_AUTO_MSG = "ACTION_AUTO_MSG";
    public static final String RECV_MSG = "recv_msg";
    public static final String RECV_PKG = "recv_pkg";
    public static final String RECV_THUMB = "recv_thumb";
    public static final String SEND_ERR_CODE = "send_err_code";
    public static final String SEND_ERR_TYPE = "send_err_type";
    public static final String SEND_ID = "send_id";
    public static final String TYPE = "type";
    public static final int TYPE_RECV_MSG = 2;
    public static final int TYPE_SEND_RET = 1;
    public String content;
    public long msgClientId;

    public static class ReceiverHelper {
        Intent intent;
        int type;

        public ReceiverHelper(Intent intent) {
            this.type = intent.getIntExtra("type", 0);
            this.intent = intent;
        }

        public String getMsgContent() {
            return isRecvNew() ? this.intent.getStringExtra(MMPluginMsg.RECV_MSG) : null;
        }

        public Integer getSendErrCode() {
            return isSendRet() ? Integer.valueOf(this.intent.getIntExtra(MMPluginMsg.SEND_ERR_CODE, 0)) : null;
        }

        public Integer getSendErrType() {
            return isSendRet() ? Integer.valueOf(this.intent.getIntExtra(MMPluginMsg.SEND_ERR_TYPE, 0)) : null;
        }

        public Long getSendMsgId() {
            return isSendRet() ? Long.valueOf(this.intent.getLongExtra(MMPluginMsg.SEND_ID, 0)) : null;
        }

        public boolean isRecvNew() {
            return this.type == 2;
        }

        public boolean isSendRet() {
            return this.type == 1;
        }
    }

    public static MMPluginMsg WXAppExtentObjectToPluginMsg(WXAppExtendObject wXAppExtendObject) {
        if (wXAppExtendObject == null) {
            return null;
        }
        MMPluginMsg mMPluginMsg = new MMPluginMsg();
        mMPluginMsg.msgClientId = Util.getLong(wXAppExtendObject.extInfo, -1);
        if (mMPluginMsg.msgClientId == -1 || Util.isNullOrNil(wXAppExtendObject.fileData)) {
            return null;
        }
        mMPluginMsg.content = new String(wXAppExtendObject.fileData);
        return !Util.isNullOrNil(mMPluginMsg.content) ? mMPluginMsg : null;
    }

    public static WXAppExtendObject pluginMsgToWXAppExtendObject(MMPluginMsg mMPluginMsg) {
        if (mMPluginMsg == null) {
            return null;
        }
        WXAppExtendObject wXAppExtendObject = new WXAppExtendObject();
        wXAppExtendObject.extInfo = mMPluginMsg.msgClientId;
        wXAppExtendObject.fileData = mMPluginMsg.content.getBytes();
        return wXAppExtendObject;
    }

    public static long sendMessage(Context context, String str) {
        if (Util.isNullOrNil(str)) {
            return -1;
        }
        MMPluginMsg mMPluginMsg = new MMPluginMsg();
        mMPluginMsg.msgClientId = Util.nowMilliSecond();
        mMPluginMsg.content = str;
        IMediaObject pluginMsgToWXAppExtendObject = pluginMsgToWXAppExtendObject(mMPluginMsg);
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.mediaObject = pluginMsgToWXAppExtendObject;
        wXMediaMessage.description = "";
        IWXAPI createWXAPI = WXAPIFactory.createWXAPI(context, null);
        if (createWXAPI == null) {
            return -2;
        }
        BaseReq req = new Req();
        req.transaction = "appdata" + mMPluginMsg.msgClientId;
        req.message = wXMediaMessage;
        return !createWXAPI.sendReq(req) ? -3 : mMPluginMsg.msgClientId;
    }
}
