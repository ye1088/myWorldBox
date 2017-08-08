package com.MCWorld.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.g;
import com.MCWorld.t;
import com.MCWorld.widget.Constants.AppType;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX.Req;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXMusicObject;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXVideoObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

/* compiled from: WeixinService */
public class k {
    private static final String APPID;
    private static final int aDL = 553779201;
    private static final int aDM = 150;
    private static k aDN;
    private static IWXAPI aDO;
    private static final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            t.n(k.mContext, msg.getData().getString(DownloadRecord.COLUMN_ERROR));
        }
    };
    private static Context mContext;
    private long aDP = 0;

    static {
        String str;
        if (g.fe == AppType.FLOOR.Value()) {
            str = "wx5f502e8037e6ad57";
        } else {
            str = "wxc12cbf3f43928a34";
        }
        APPID = str;
    }

    private k(Context context) {
        if (aDO == null) {
            aDO = WXAPIFactory.createWXAPI(context, APPID, false);
            aDO.registerApp(APPID);
        }
    }

    public static k bc(Context context) {
        mContext = context;
        if (aDN == null) {
            aDN = new k(context);
        }
        return aDN;
    }

    private boolean a(WXMediaMessage msg, boolean isTimeline) {
        boolean res = false;
        String error = "";
        if (!aDO.isWXAppInstalled()) {
            error = "微信未安装";
        } else if (!aDO.isWXAppSupportAPI()) {
            error = "当前微信版本不支持API";
        } else if (!isTimeline || aDO.getWXAppSupportAPI() >= 553779201) {
            Req req = new Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = isTimeline ? 1 : 0;
            res = aDO.sendReq(req);
        } else {
            error = "当前微信版本不支持分享到朋友圈";
        }
        if (error.length() > 0) {
            Message message = new Message();
            Bundle data = new Bundle();
            data.putString(DownloadRecord.COLUMN_ERROR, error);
            message.setData(data);
            handler.sendMessage(message);
        }
        return res;
    }

    private WXMediaMessage S(String title, String desc) {
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = title;
        msg.description = desc;
        return msg;
    }

    private void a(WXMediaMessage msg, Bitmap bmp) {
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        if (thumbBmp != null && !thumbBmp.isRecycled()) {
            msg.setThumbImage(thumbBmp);
        }
    }

    public boolean a(String title, String desc, String text, boolean isTimeline) {
        WXMediaMessage msg = S(title, desc);
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        msg.mediaObject = textObj;
        return a(msg, isTimeline);
    }

    public boolean a(String title, String desc, String img, Bitmap bmp, boolean isTimeline) {
        WXMediaMessage msg = S(title, desc);
        WXImageObject imgObj = new WXImageObject();
        if (img.startsWith("http://") || img.startsWith("https://")) {
            imgObj.imageUrl = img;
        } else {
            imgObj.setImagePath(img);
        }
        msg.mediaObject = imgObj;
        a(msg, bmp);
        return a(msg, isTimeline);
    }

    public boolean b(String title, String desc, String url, Bitmap bmp, boolean isTimeline) {
        WXMediaMessage msg = S(title, desc);
        WXWebpageObject webpageObj = new WXWebpageObject();
        webpageObj.webpageUrl = url;
        msg.mediaObject = webpageObj;
        if (!(bmp == null || bmp.isRecycled())) {
            a(msg, bmp);
        }
        return a(msg, isTimeline);
    }

    public boolean c(String title, String desc, String musicUrl, Bitmap bmp, boolean isTimeline) {
        WXMediaMessage msg = S(title, desc);
        WXMusicObject musicObj = new WXMusicObject();
        musicObj.musicUrl = musicUrl;
        msg.mediaObject = musicObj;
        if (!(bmp == null || bmp.isRecycled())) {
            a(msg, bmp);
        }
        return a(msg, isTimeline);
    }

    public boolean d(String title, String desc, String videoUrl, Bitmap bmp, boolean isTimeline) {
        WXMediaMessage msg = S(title, desc);
        WXVideoObject videoObj = new WXVideoObject();
        videoObj.videoUrl = videoUrl;
        msg.mediaObject = videoObj;
        if (!(bmp == null || bmp.isRecycled())) {
            a(msg, bmp);
        }
        return a(msg, isTimeline);
    }

    public boolean handleIntent(Intent intent, IWXAPIEventHandler handler) {
        return aDO.handleIntent(intent, handler);
    }

    public long EN() {
        return this.aDP;
    }

    public void bi(long shareAppID) {
        this.aDP = shareAppID;
    }

    public void EO() {
        this.aDP = 0;
    }
}
