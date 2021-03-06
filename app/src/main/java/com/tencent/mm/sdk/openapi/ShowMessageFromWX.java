package com.tencent.mm.sdk.openapi;

import android.os.Bundle;
import com.tencent.mm.sdk.openapi.WXMediaMessage.Builder;

public class ShowMessageFromWX {

    public static class Req extends BaseReq {
        public WXMediaMessage message;

        public Req(Bundle bundle) {
            fromBundle(bundle);
        }

        final boolean checkArgs() {
            return this.message == null ? false : this.message.checkArgs();
        }

        public void fromBundle(Bundle bundle) {
            super.fromBundle(bundle);
            this.message = Builder.fromBundle(bundle);
        }

        public int getType() {
            return 4;
        }

        public void toBundle(Bundle bundle) {
            Bundle toBundle = Builder.toBundle(this.message);
            super.toBundle(toBundle);
            bundle.putAll(toBundle);
        }
    }

    public static class Resp extends BaseResp {
        public Resp(Bundle bundle) {
            fromBundle(bundle);
        }

        final boolean checkArgs() {
            return true;
        }

        public int getType() {
            return 4;
        }
    }

    private ShowMessageFromWX() {
    }
}
