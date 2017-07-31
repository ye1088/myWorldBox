package com.tencent.mm.sdk.openapi;

import android.os.Bundle;
import com.tencent.mm.sdk.openapi.WXMediaMessage.Builder;
import com.tencent.mm.sdk.platformtools.Log;

public class SendMessageToWX {

    public static class Req extends BaseReq {
        public static final int WXSceneSession = 0;
        public static final int WXSceneTimeline = 1;
        public WXMediaMessage message;
        public int scene;

        public Req(Bundle bundle) {
            fromBundle(bundle);
        }

        final boolean checkArgs() {
            if (this.message != null) {
                return this.message.checkArgs();
            }
            Log.e("MicroMsg.SDK.SendMessageToWX.Req", "checkArgs fail ,message is null");
            return false;
        }

        public void fromBundle(Bundle bundle) {
            super.fromBundle(bundle);
            this.message = Builder.fromBundle(bundle);
            this.scene = bundle.getInt("_wxapi_sendmessagetowx_req_scene");
        }

        public int getType() {
            return 2;
        }

        public void toBundle(Bundle bundle) {
            super.toBundle(bundle);
            bundle.putAll(Builder.toBundle(this.message));
            bundle.putInt("_wxapi_sendmessagetowx_req_scene", this.scene);
        }
    }

    public static class Resp extends BaseResp {
        public Resp(Bundle bundle) {
            fromBundle(bundle);
        }

        final boolean checkArgs() {
            return true;
        }

        public void fromBundle(Bundle bundle) {
            super.fromBundle(bundle);
        }

        public int getType() {
            return 2;
        }

        public void toBundle(Bundle bundle) {
            super.toBundle(bundle);
        }
    }

    private SendMessageToWX() {
    }
}
