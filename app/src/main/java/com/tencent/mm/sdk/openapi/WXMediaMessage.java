package com.tencent.mm.sdk.openapi;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import com.tencent.mm.sdk.platformtools.Log;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public final class WXMediaMessage {
    public static final String ACTION_WXAPPMESSAGE = "com.tencent.mm.sdk.openapi.Intent.ACTION_WXAPPMESSAGE";
    public String description;
    public IMediaObject mediaObject;
    public int sdkVer;
    public byte[] thumbData;
    public String title;

    public interface IMediaObject {
        public static final int TYPE_APPDATA = 7;
        public static final int TYPE_EMOJI = 8;
        public static final int TYPE_FILE = 6;
        public static final int TYPE_IMAGE = 2;
        public static final int TYPE_MUSIC = 3;
        public static final int TYPE_TEXT = 1;
        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_URL = 5;
        public static final int TYPE_VIDEO = 4;

        boolean checkArgs();

        void serialize(Bundle bundle);

        int type();

        void unserialize(Bundle bundle);
    }

    public static class Builder {
        public static final String KEY_IDENTIFIER = "_wxobject_identifier_";

        public static WXMediaMessage fromBundle(Bundle bundle) {
            WXMediaMessage wXMediaMessage = new WXMediaMessage();
            wXMediaMessage.sdkVer = bundle.getInt("_wxobject_sdkVer");
            wXMediaMessage.title = bundle.getString("_wxobject_title");
            wXMediaMessage.description = bundle.getString("_wxobject_description");
            wXMediaMessage.thumbData = bundle.getByteArray("_wxobject_thumbdata");
            String string = bundle.getString(KEY_IDENTIFIER);
            if (string == null || string.length() <= 0) {
                return wXMediaMessage;
            }
            try {
                wXMediaMessage.mediaObject = (IMediaObject) Class.forName(string).newInstance();
                wXMediaMessage.mediaObject.unserialize(bundle);
                return wXMediaMessage;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MicroMsg.SDK.WXMediaMessage", "get media object from bundle failed: unknown ident " + string);
                return wXMediaMessage;
            }
        }

        public static Bundle toBundle(WXMediaMessage wXMediaMessage) {
            Bundle bundle = new Bundle();
            bundle.putInt("_wxobject_sdkVer", wXMediaMessage.sdkVer);
            bundle.putString("_wxobject_title", wXMediaMessage.title);
            bundle.putString("_wxobject_description", wXMediaMessage.description);
            bundle.putByteArray("_wxobject_thumbdata", wXMediaMessage.thumbData);
            if (wXMediaMessage.mediaObject != null) {
                bundle.putString(KEY_IDENTIFIER, wXMediaMessage.mediaObject.getClass().getName());
                wXMediaMessage.mediaObject.serialize(bundle);
            }
            return bundle;
        }
    }

    public WXMediaMessage() {
        this(null);
    }

    public WXMediaMessage(IMediaObject iMediaObject) {
        this.mediaObject = iMediaObject;
    }

    final boolean checkArgs() {
        if (getType() == 8 && (this.thumbData == null || this.thumbData.length == 0)) {
            Log.e("MicroMsg.SDK.WXMediaMessage", "checkArgs fail, thumbData should not be null when send emoji");
            return false;
        } else if (this.thumbData != null && this.thumbData.length > 32768) {
            Log.e("MicroMsg.SDK.WXMediaMessage", "checkArgs fail, thumbData is invalid");
            return false;
        } else if (this.title != null && this.title.length() > 512) {
            Log.e("MicroMsg.SDK.WXMediaMessage", "checkArgs fail, title is invalid");
            return false;
        } else if (this.description != null && this.description.length() > 1024) {
            Log.e("MicroMsg.SDK.WXMediaMessage", "checkArgs fail, description is invalid");
            return false;
        } else if (this.mediaObject != null) {
            return this.mediaObject.checkArgs();
        } else {
            Log.e("MicroMsg.SDK.WXMediaMessage", "checkArgs fail, mediaObject is null");
            return false;
        }
    }

    public final int getType() {
        return this.mediaObject == null ? 0 : this.mediaObject.type();
    }

    public final void setThumbImage(Bitmap bitmap) {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 85, byteArrayOutputStream);
            this.thumbData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MicroMsg.SDK.WXMediaMessage", "put thumb failed");
        }
    }
}
