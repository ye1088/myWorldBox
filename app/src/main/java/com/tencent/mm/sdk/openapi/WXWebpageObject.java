package com.tencent.mm.sdk.openapi;

import android.os.Bundle;
import com.tencent.mm.sdk.openapi.WXMediaMessage.IMediaObject;
import com.tencent.mm.sdk.platformtools.Log;
import org.apache.tools.tar.TarBuffer;

public class WXWebpageObject implements IMediaObject {
    public String webpageUrl;

    public WXWebpageObject(String str) {
        this.webpageUrl = str;
    }

    public boolean checkArgs() {
        if (this.webpageUrl != null && this.webpageUrl.length() != 0 && this.webpageUrl.length() <= TarBuffer.DEFAULT_BLKSIZE) {
            return true;
        }
        Log.e("MicroMsg.SDK.WXWebpageObject", "checkArgs fail, webpageUrl is invalid");
        return false;
    }

    public void serialize(Bundle bundle) {
        bundle.putString("_wxwebpageobject_webpageUrl", this.webpageUrl);
    }

    public int type() {
        return 5;
    }

    public void unserialize(Bundle bundle) {
        this.webpageUrl = bundle.getString("_wxwebpageobject_webpageUrl");
    }
}
